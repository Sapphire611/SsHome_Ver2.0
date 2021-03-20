package com.sapphire.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.model.Comment;
import com.sapphire.demo.model.CommentExample;
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
}
