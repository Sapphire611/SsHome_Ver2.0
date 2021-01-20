package com.sapphire.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.ReplyDTO;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.QuestionService;
import com.sapphire.demo.service.ReplyService;

@Controller
public class ProfileController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ReplyService replyService;

	@GetMapping("/profile/{action}")
	public String profile(@PathVariable(name = "action") String action,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "7") Integer size, HttpServletRequest request, Model model) {

		// User user = (User) model.getAttribute("user");
		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser == null) {
			return "redirect:/login";
		}

		// 显示新消息数
		if (currentUser != null) {
			PaginationDTO paginationQuestionDTO = replyService.listAtNotice(currentUser.getId(), 1, 7);
			int countNewNotice = 0;
			for (ReplyDTO reply : paginationQuestionDTO.getReplies()) {
				if (reply.getGmtCreate() > reply.getGmtQuestionRead()) {
					countNewNotice++;
				}
			}
			model.addAttribute("countNewNotice", countNewNotice);
		}
		// 显示新消息数 End

		if ("questions".equals(action)) {
			// Question 页面
			model.addAttribute("section", "questions");
			model.addAttribute("sectionName", "个人提问");

			PaginationDTO paginationQuestionDTO = questionService.list(currentUser.getId(), page, size);
			if (paginationQuestionDTO != null) {
				model.addAttribute("paginationDTO", paginationQuestionDTO);
			} else {
				model.addAttribute("paginationDTO", null);
			}

		} else if ("replies".equals(action)) {
			// Reply 页面
			PaginationDTO paginationQuestionDTO = replyService.listAtProfile(currentUser.getId(), page, size);
			model.addAttribute("paginationDTO", paginationQuestionDTO);
			// System.out.println(paginationQuestionDTO.toString());
			model.addAttribute("section", "replies");
			model.addAttribute("sectionName", "个人回复");
		} else if ("personalInfo".equals(action)) {
			return "redirect:/userinfo/" + currentUser.getName();
		} else if ("settings".equals(action)) {
			model.addAttribute("section", "settings");
			model.addAttribute("sectionName", "个人设置");

			User infoUser = currentUser;
			model.addAttribute("infoUser", infoUser);

			return "settings";
		} else if ("notices".equals(action)) {
			model.addAttribute("section", "notices");
			model.addAttribute("sectionName", "他人答复");

			// 设置分页
			PaginationDTO paginationQuestionDTO = replyService.listAtNotice(currentUser.getId(), page, size);

			int countNewNotice = 0;
			for (ReplyDTO reply : paginationQuestionDTO.getReplies()) {
				// System.out.println(reply.getGmtCreate());
				// System.out.println(reply.getGmtQuestionRead());
				if (reply.getGmtCreate() > reply.getGmtQuestionRead()) {
					countNewNotice++;
					reply.setQuestionTitle("[New] " + reply.getQuestionTitle());
				}
			}
			model.addAttribute("paginationDTO", paginationQuestionDTO);
			model.addAttribute("countNewNotice", countNewNotice);
		}

		return "profile";
	}

}
