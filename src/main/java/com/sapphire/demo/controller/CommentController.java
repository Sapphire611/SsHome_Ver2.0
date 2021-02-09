package com.sapphire.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sapphire.demo.dto.CommentDTO;
import com.sapphire.demo.dto.ResultDTO;
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
	public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {

		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null) {
			return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
		}

		Comment comment = new Comment();
		comment.setParentId(commentDTO.getParentId());
		comment.setContent(commentDTO.getContent());
		comment.setType(commentDTO.getType());
		comment.setGmtcreate(System.currentTimeMillis());
		comment.setGmtmodified(comment.getGmtcreate());
		comment.setCommentator(currentUser.getId());
		comment.setLikecount(0L);
		commentService.insert(comment);

		return ResultDTO.okOf();
	}

}
