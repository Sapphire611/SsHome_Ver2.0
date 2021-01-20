package com.sapphire.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.ReplyDTO;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.ReplyService;

@Controller
public class HelloController {
	@Autowired
	private ReplyService replyService;

	@GetMapping("/")
	public String hello(HttpServletRequest request, Model model) {
		User currentUser = (User) request.getSession().getAttribute("user");
		//  显示新消息数
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

		return "hello";
	}
}
