package com.sapphire.demo.controller;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.QuestionService;
import com.sapphire.demo.service.ReplyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SettingController {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ReplyService replyService;

	@GetMapping("/profile/settings/changeInfo")
	public String profile(HttpServletRequest request, Model model) {

		// User user = (User) model.getAttribute("user");
		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser == null) {
			return "redirect:/";
		} else {
			
			return "/";
		}

	}
}
