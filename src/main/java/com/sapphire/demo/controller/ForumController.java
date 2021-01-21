package com.sapphire.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.ReplyDTO;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.QuestionService;
import com.sapphire.demo.service.ReplyService;

@Controller
public class ForumController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ReplyService replyService;

	@GetMapping("/forum")
	// size = 一页显示7个问题
	public String forum(Model model, @RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "7") Integer size, HttpServletRequest request) {

		User currentUser = (User) request.getSession().getAttribute("user");

		PaginationDTO paginationDTO = questionService.list(page, size);
		model.addAttribute("paginationDTO", paginationDTO);

		List<User> adminUsers = userMapper.findAdmin();
		model.addAttribute("adminUsers", adminUsers);

		// 显示新消息数
		if (currentUser != null) {
			PaginationDTO paginationQuestionDTO = replyService.listAtNotice(currentUser.getId(), 1, 7);
			int countNewNotice = 0;
			if (paginationQuestionDTO.getTotalCount() != 0) {
				for (ReplyDTO reply : paginationQuestionDTO.getReplies()) {
					if (reply.getGmtCreate() > reply.getGmtQuestionRead()) {
						countNewNotice++;
					}
				}
			}

			model.addAttribute("countNewNotice", countNewNotice);
		}
		// 显示新消息数 End

		return "forum";
	}

//    @GetMapping("/forum/{forumName}")
//    // size = 一页显示7个问题
//    public String forumName(Model model,
//    					@PathVariable(name = "forumName") String forumName,
//                        @RequestParam(name = "page",defaultValue = "1") Integer page,
//                        @RequestParam(name = "size",defaultValue = "7") Integer size) {
//
//
//        PaginationDTO paginationDTO = questionService.list(page,size);
//        model.addAttribute("paginationDTO",paginationDTO);
//        
//        List<User> adminUsers = userMapper.findAdmin(); 
//        model.addAttribute("adminUsers",adminUsers);
//        
//        return "index";
//    }
}
