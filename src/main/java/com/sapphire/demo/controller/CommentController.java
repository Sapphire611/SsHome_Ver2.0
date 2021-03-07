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
import com.sapphire.demo.dto.ResultDTO;
import com.sapphire.demo.enums.CommentTypeEnum;
import com.sapphire.demo.exception.CustomizeErrorCode;
import com.sapphire.demo.model.Comment;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public Object post(@RequestBody CommentCreateDTO commencreatetDTO, HttpServletRequest request) {

		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null) {
			return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
		}
		
		// 校验回复不能为空
		if(commencreatetDTO == null || StringUtils.isBlank(commencreatetDTO.getContent())) {
			return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
		}	

		Comment comment = new Comment();
		comment.setParentId(commencreatetDTO.getParentId());
		comment.setContent(commencreatetDTO.getContent());
		comment.setType(commencreatetDTO.getType());
		comment.setGmtcreate(System.currentTimeMillis());
		comment.setGmtmodified(comment.getGmtcreate());
		comment.setCommentator(currentUser.getId());
		comment.setLikecount(0L);
		commentService.insert(comment);

		return ResultDTO.okOf();
	}
	
	@ResponseBody
	@RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
	public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
		List<CommentDTO> commentDTOs = commentService.listByTargetId(CommentTypeEnum.COMMENT,id);
		
		return  ResultDTO.okOf(commentDTOs);
	}

}
