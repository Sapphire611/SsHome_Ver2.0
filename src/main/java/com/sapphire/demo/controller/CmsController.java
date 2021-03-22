package com.sapphire.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapphire.demo.model.User;

@Controller
public class CmsController {

	@GetMapping("/cms/{action}")
	public String cmsSelect(@PathVariable(name = "action") String action,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "7") Integer size, HttpServletRequest request, Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser.getAdminboolean() != 1) {
			return "redirect:/forum";
		}

		if ("home".equals(action)) {
			model.addAttribute("section", "home");
		} else if ("user".equals(action)) {
			model.addAttribute("section", "user");
		} else if ("question".equals(action)) {
			model.addAttribute("section", "question");
		} else if ("comment".equals(action)) {
			model.addAttribute("section", "comment");
		} else if ("notification".equals(action)) {
			model.addAttribute("section", "notification");
		} else if ("likeRecord".equals(action)) {
			model.addAttribute("section", "likeRecord");
		} else {

		}

		return "cms";
	}

	@GetMapping("/cms")
	public String cms(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "7") Integer size, HttpServletRequest request, Model model) {
		// 不指明 action 时，直接转向home
		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser == null || currentUser.getAdminboolean() != 1) {
			return "redirect:/forum";
		}

		return "redirect:/cms/home";
	}
}
