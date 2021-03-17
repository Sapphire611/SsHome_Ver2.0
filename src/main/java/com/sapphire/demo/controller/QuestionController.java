package com.sapphire.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapphire.demo.dto.CommentDTO;
import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.enums.CommentTypeEnum;
import com.sapphire.demo.mapper.CommentMapper;
import com.sapphire.demo.mapper.LikeRecordMapper;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.model.CommentExample;
import com.sapphire.demo.model.LikeRecord;
import com.sapphire.demo.model.LikeRecordExample;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.CommentService;
import com.sapphire.demo.service.QuestionService;

/**
 * @Author : Sapphire L
 * @Date : 2020/6/1 5:18
 */
@Controller
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionMapper questionMapper;

	@Autowired
	private LikeRecordMapper likeRecordMapper;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CommentMapper commentMapper;

	@Value("${url}")
	private String url;

	@Value("${github.client.id}")
	private String clientId;

	@GetMapping("/question/{id}")
	public String question(@PathVariable(name = "id") Long id,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "7") Integer size, HttpServletRequest request, Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");

		// 用于ajax异步登录
		String login_url = "https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=http://"
				+ url + "/callback&scope=user&state=1";
		model.addAttribute("login_url", login_url);

		// 用于显示对应问题的内容和Publisher
		QuestionDTO questionDTO = questionService.getById(id);
		model.addAttribute("question", questionDTO);

		if (currentUser != null) {
			Question updatedQuestion = new Question();
			updatedQuestion.setId(id);
			updatedQuestion.setViewCount(questionMapper.selectByPrimaryKey(id).getViewCount() + 1);
			questionMapper.updateByPrimaryKeySelective(updatedQuestion); // 访问数 + 1
			

			// 判断当前用户是否已点赞，如果点了，传一个“Button” -> 按钮变成绿色
			LikeRecordExample example2 = new LikeRecordExample();
			example2.createCriteria().andUseridEqualTo(currentUser.getId()).andSourceidEqualTo(id).andTypeEqualTo(1);
			List<LikeRecord> selectByExample2 = likeRecordMapper.selectByExample(example2);

			if (selectByExample2.size() != 0) {
				model.addAttribute("Button", "Liked");
			}

			model.addAttribute("currentUser", currentUser);
		}
		
		// 根据问题id找到对应回复
		List <CommentDTO> comments = commentService.listByQuestionId(CommentTypeEnum.QUESTION,id);
		model.addAttribute("comments", comments);
		
		List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
		model.addAttribute("relatedQuestions", relatedQuestions);
		return "question";

	}

	@GetMapping("/question/{id}/like")
	public String questionLike(@PathVariable(name = "id") Long id, HttpServletRequest request, Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");

		// 判断当前用户是否已点赞，如果点了，传一个“Button” -> 按钮变成绿色
		LikeRecordExample example2 = new LikeRecordExample();
		example2.createCriteria().andUseridEqualTo(currentUser.getId()).andSourceidEqualTo(id).andTypeEqualTo(1);
		List<LikeRecord> selectByExample2 = likeRecordMapper.selectByExample(example2);

		if (selectByExample2.size() == 0) {
			LikeRecord likeRecord = new LikeRecord();
			likeRecord.setSourceid(id);
			likeRecord.setType(1);
			likeRecord.setUserid(currentUser.getId());
			likeRecord.setGmtcreate(System.currentTimeMillis());

			// 1.点赞数 + 1
			Question updatedQuestion = new Question();
			updatedQuestion.setId(id);
			updatedQuestion.setLikeCount(questionMapper.selectByPrimaryKey(id).getLikeCount() + 1);
			questionMapper.updateByPrimaryKeySelective(updatedQuestion);

			// 2.插入一条Like记录
			likeRecordMapper.insert(likeRecord);

		}

		return "redirect:/question/" + id + "";
	}
	
	@GetMapping("/question/{id}/likeCancel")
	public String questionLikeCancel(@PathVariable(name = "id") Long id, HttpServletRequest request, Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");

		// 判断当前用户是否没有点赞，如果点了，传一个“Button” -> 按钮变成绿色
		LikeRecordExample example2 = new LikeRecordExample();
		example2.createCriteria().andUseridEqualTo(currentUser.getId()).andSourceidEqualTo(id).andTypeEqualTo(1);
		List<LikeRecord> selectByExample2 = likeRecordMapper.selectByExample(example2);

		if (selectByExample2.size() != 0) {
			// 1.点赞数 - 1
			Question updatedQuestion = new Question();
			updatedQuestion.setId(id);
			updatedQuestion.setLikeCount(questionMapper.selectByPrimaryKey(id).getLikeCount() - 1);
			questionMapper.updateByPrimaryKeySelective(updatedQuestion);

			// 2.删除对应的likeRecord
			likeRecordMapper.deleteByPrimaryKey(selectByExample2.get(0).getId());

		}

		return "redirect:/question/" + id + "";
	}
	

	@GetMapping("/question/deleteMyQuestion")
	public String profileDelete(@RequestParam(value = "questionId", required = false) Long questionId,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "7") Integer size, HttpServletRequest request, Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			// 1.删除对应的点赞记录
			LikeRecordExample example = new LikeRecordExample();
			example.createCriteria().andSourceidEqualTo(questionId);
			likeRecordMapper.deleteByExample(example);
			
			// 2.删除对应的问题
			questionMapper.deleteByPrimaryKey(questionId);
			
			// 3.删除对应问题的所有评论
			CommentExample example2 = new CommentExample();
			example2.createCriteria().andParentIdEqualTo(questionId);
			commentMapper.deleteByExample(example2);
			
		}

		return "redirect:/forum";

	}

}
