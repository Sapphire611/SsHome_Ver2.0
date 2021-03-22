package com.sapphire.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapphire.demo.dto.NotificationDTO;
import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.NotificationService;
import com.sapphire.demo.service.QuestionService;

@Controller
public class ProfileController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private NotificationService notificationService;

	@GetMapping("/profile/{action}")
	public String profile(@PathVariable(name = "action") String action,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "7") Integer size, HttpServletRequest request, Model model) {
		
		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser == null) {
			return "redirect:/login";
		}

		if ("questions".equals(action)) {
			// Question 页面
			model.addAttribute("section", "questions");
			model.addAttribute("sectionName", "个人提问");

			PaginationDTO<QuestionDTO> paginationQuestionDTO = questionService.list(currentUser.getId(), page, size);
			if (paginationQuestionDTO != null) {
				model.addAttribute("paginationDTO", paginationQuestionDTO);
			} else {
				model.addAttribute("paginationDTO", null);
			}

		} else if ("replies".equals(action)) {
			
			PaginationDTO<NotificationDTO> paginationDTO = notificationService.list(currentUser.getId(), page, size);
            model.addAttribute("paginationDTO", paginationDTO);
			model.addAttribute("section", "replies");
			model.addAttribute("sectionName", "个人回复");
		} else if ("personalInfo".equals(action)) {
			return "redirect:/userinfo/" + currentUser.getName();
		} else if ("settings".equals(action)) {
			model.addAttribute("section", "settings");
			model.addAttribute("sectionName", "个人设置");

			User infoUser = currentUser;
			model.addAttribute("infoUser", infoUser);

			return "profile";
		} else if ("notices".equals(action)) {
		
		}

		return "profile";
	}

}