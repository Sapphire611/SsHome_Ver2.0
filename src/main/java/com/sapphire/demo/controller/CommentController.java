package com.sapphire.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sapphire.demo.dto.CommentCreateDTO;
import com.sapphire.demo.dto.CommentDTO;
import com.sapphire.demo.dto.CommentDeleteDTO;
import com.sapphire.demo.dto.ResultDTO;
import com.sapphire.demo.enums.CommentTypeEnum;
import com.sapphire.demo.exception.CustomizeErrorCode;
import com.sapphire.demo.exception.CustomizeException;
import com.sapphire.demo.mapper.CommentMapper;
import com.sapphire.demo.mapper.LikeRecordMapper;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.model.Comment;
import com.sapphire.demo.model.CommentExample;
import com.sapphire.demo.model.LikeRecord;
import com.sapphire.demo.model.LikeRecordExample;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.QuestionExample;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private LikeRecordMapper likeRecordMapper;
	
	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public Object post(@RequestBody CommentCreateDTO commentcreateDTO, HttpServletRequest request) {

		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null) {
			return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
		}
		
		// 校验回复不能为空
		if(commentcreateDTO == null || StringUtils.isBlank(commentcreateDTO.getContent())) {
			return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
		}	

		Comment comment = new Comment();
		comment.setParentId(commentcreateDTO.getParentId());
		comment.setContent(commentcreateDTO.getContent());
		comment.setType(commentcreateDTO.getType());
		comment.setGmtcreate(System.currentTimeMillis());
		comment.setGmtmodified(comment.getGmtcreate());
		comment.setCommentator(currentUser.getId());
		comment.setCommentcount(0L);
		comment.setLikeCount(0);
		commentService.insert(comment,currentUser);

		return ResultDTO.okOf();
	}
	
	@ResponseBody
	@RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
	public ResultDTO <List> comments(@PathVariable(name = "id") Long id) {
		List<CommentDTO> commentDTOs = commentService.listByTargetId(CommentTypeEnum.COMMENT,id);

		return  ResultDTO.okOf(commentDTOs);
	}

	@ResponseBody
	@RequestMapping(value = "/commentDelete", method = RequestMethod.POST)
	public Object commentDelete(@RequestBody CommentDeleteDTO commentDeletetDTO, HttpServletRequest request) {
		Long id = commentDeletetDTO.getId();
		Integer type = commentDeletetDTO.getType();
		
		Comment comment = commentMapper.selectByPrimaryKey(id);
		
		if(type == CommentTypeEnum.QUESTION.getType()) {
			// 删除一级评论
			commentMapper.deleteByPrimaryKey(id);
			// 删除一级评论的同时，删除所有对应二级回复
			CommentExample example = new CommentExample();
			example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.COMMENT.getType());
			List<Comment> selectByExample = commentMapper.selectByExample(example);
			for (Comment comment2 : selectByExample) {
				commentMapper.deleteByPrimaryKey(comment2.getId());
			}
			// 问题的评论数 - 1
			Question deletedQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
			deletedQuestion.setCommentCount(deletedQuestion.getCommentCount() - 1);
			questionMapper.updateByPrimaryKey(deletedQuestion);
			
			return ResultDTO.okOf();
		}else if(type == CommentTypeEnum.COMMENT.getType()) {
			// 删除二级评论
			commentMapper.deleteByPrimaryKey(id);
			
			return ResultDTO.okOf();
		} else {
			// 评论类型错误或不存在
			throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
		}
		
	}
	
	@GetMapping("/comment/{id}/like/{questionId}")
	public String commentLike(@PathVariable(name = "id") Long id, HttpServletRequest request,@PathVariable(name = "questionId") Long questionId) {

		User currentUser = (User) request.getSession().getAttribute("user");

		LikeRecordExample example2 = new LikeRecordExample();
		example2.createCriteria().andUseridEqualTo(currentUser.getId()).andSourceidEqualTo(id).andTypeEqualTo(CommentTypeEnum.COMMENT.getType());
		List<LikeRecord> selectByExample2 = likeRecordMapper.selectByExample(example2);

		if (selectByExample2.size() == 0) {
			LikeRecord likeRecord = new LikeRecord();
			likeRecord.setSourceid(id);
			likeRecord.setType(CommentTypeEnum.COMMENT.getType());
			likeRecord.setUserid(currentUser.getId());
			likeRecord.setGmtcreate(System.currentTimeMillis());

			// 1.点赞数 + 1
			Comment updatedComment = new Comment();
			updatedComment.setId(id);
			updatedComment.setLikeCount(commentMapper.selectByPrimaryKey(id).getLikeCount() + 1);
			commentMapper.updateByPrimaryKeySelective(updatedComment);

			// 2.插入一条Like记录
			likeRecordMapper.insert(likeRecord);

		}

		return "redirect:/question/" + questionId + "";
	}
	
	@GetMapping("/comment/{id}/likeCancel/{questionId}")
	public String commentLikeCancel(@PathVariable(name = "id") Long id, HttpServletRequest request,@PathVariable(name = "questionId") Long questionId) {

		User currentUser = (User) request.getSession().getAttribute("user");

		LikeRecordExample example2 = new LikeRecordExample();
		example2.createCriteria().andUseridEqualTo(currentUser.getId()).andSourceidEqualTo(id).andTypeEqualTo(CommentTypeEnum.COMMENT.getType());
		List<LikeRecord> selectByExample2 = likeRecordMapper.selectByExample(example2);

		if (selectByExample2.size() != 0) {
			// 1.点赞数 - 1
			Comment updatedComment = new Comment();
			updatedComment.setId(id);
			updatedComment.setLikeCount(commentMapper.selectByPrimaryKey(id).getLikeCount() - 1);
			commentMapper.updateByPrimaryKeySelective(updatedComment);

			// 2.删除对应的likeRecord
			likeRecordMapper.deleteByPrimaryKey(selectByExample2.get(0).getId());

		}

		return "redirect:/question/" + questionId + "";
	}
}
