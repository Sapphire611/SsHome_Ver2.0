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

import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.mapper.LikeRecordMapper;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.mapper.ViewRecordMapper;
import com.sapphire.demo.model.LikeRecord;
import com.sapphire.demo.model.LikeRecordExample;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.User;
import com.sapphire.demo.model.ViewRecord;
import com.sapphire.demo.model.ViewRecordExample;
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
	private ViewRecordMapper viewRecordMapper;

	@Autowired
	private LikeRecordMapper likeRecordMapper;
	
	@Value("${url}")
	private String url;
	
	@Value("${github.client.id}")
	private String clientId;

	@GetMapping("/question/{id}")
	public String question(@PathVariable(name = "id") Long id,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "7") Integer size, HttpServletRequest request, Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");

		if (currentUser == null) {
			// 用于显示对应问题的内容和Publisher
			QuestionDTO questionDTO = questionService.getById(id);
			model.addAttribute("question", questionDTO);

			return "question2";
		} else {
			
			// 用于ajax异步登录
			String login_url = "https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=http://" + url + "/callback&scope=user&state=1";
			model.addAttribute("login_url",login_url);
			
			// 用于显示对应问题的内容和Publisher
			QuestionDTO questionDTO = questionService.getById(id);
			model.addAttribute("question", questionDTO);

			// 修改作者的阅读本文时间，这句话是根据主键找到当前问题的
			Long creator = questionMapper.selectByPrimaryKey(id).getCreator();

			if (currentUser.getId() == creator) {
				Question updatedQuestion = new Question();
				updatedQuestion.setId(id);
				updatedQuestion.setGmtauthorread(System.currentTimeMillis());
				questionMapper.updateByPrimaryKeySelective(updatedQuestion);
			}

			// 判断当前用户是否已阅读，如果已阅读，浏览数不加一
			ViewRecordExample example = new ViewRecordExample();

			example.createCriteria().andUseridEqualTo(currentUser.getId()).andQuestionidEqualTo(id);
			List<ViewRecord> selectByExample = viewRecordMapper.selectByExample(example);

			if (selectByExample.size() == 0) {
				Question updatedQuestion = new Question();
				updatedQuestion.setId(id);
				updatedQuestion.setViewCount(questionMapper.selectByPrimaryKey(id).getViewCount() + 1);
				questionMapper.updateByPrimaryKeySelective(updatedQuestion); // 1.访问数 + 1

				ViewRecord viewRecord = new ViewRecord();
				viewRecord.setQuestionid(id);
				viewRecord.setUserid(currentUser.getId());
				viewRecord.setGmtcreate(System.currentTimeMillis());
				viewRecordMapper.insertSelective(viewRecord); // 2. 插入一条 ViewRecord
			}

			// 判断当前用户是否已点赞，如果点了，传一个“Button” -> 按钮变成绿色
			LikeRecordExample example2 = new LikeRecordExample();
			example2.createCriteria().andUseridEqualTo(currentUser.getId()).andQuestionidEqualTo(id);
			List<LikeRecord> selectByExample2 = likeRecordMapper.selectByExample(example2);

			if (selectByExample2.size() != 0) {
				model.addAttribute("Button", "Liked");
			}


			model.addAttribute("currentUser", currentUser);

			return "question";
		}
	}

	@GetMapping("/question/{id}/like")
	public String questionLike(@PathVariable(name = "id") Long id, HttpServletRequest request, Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");

		// 判断当前用户是否已点赞，如果点了，传一个“Button” -> 按钮变成绿色
		LikeRecordExample example2 = new LikeRecordExample();
		example2.createCriteria().andUseridEqualTo(currentUser.getId()).andQuestionidEqualTo(id);
		List<LikeRecord> selectByExample2 = likeRecordMapper.selectByExample(example2);

		if (selectByExample2.size() == 0) {
			LikeRecord likeRecord = new LikeRecord();
			likeRecord.setQuestionid(id);
			likeRecord.setUserid(currentUser.getId());
			likeRecord.setGmtcreate(System.currentTimeMillis());

			// 1.点赞数 + 1
			Question updatedQuestion = new Question();
			updatedQuestion.setId(id);
			updatedQuestion.setLikeCount(questionMapper.selectByPrimaryKey(id).getLikeCount() + 1);
			questionMapper.updateByPrimaryKeySelective(updatedQuestion);

			// 2.插入一条Like记录
			likeRecordMapper.insert(likeRecord);

		} else {
			// 重定向的话，设置Attribute无效～
			// model.addAttribute("wrongMsg","Each User could only like once...");
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
			questionMapper.deleteByPrimaryKey(questionId);
		}

		return "redirect:/forum";

	}

}
