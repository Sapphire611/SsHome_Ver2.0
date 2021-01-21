package com.sapphire.demo.controller;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.ReplyDTO;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.QuestionService;
import com.sapphire.demo.service.ReplyService;

@Controller
public class UserInfoController {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private QuestionMapper questionMapper;

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private ReplyService replyService;

	@GetMapping("/userinfo/{userName}")

	public String userInfo(@PathVariable(name = "userName") String userName,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "3") Integer size, HttpServletRequest request, Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");

		if (currentUser == null) {
			return "redirect:/login";
		} else {
			// 个人信息
			User infoUser = userMapper.findByName(userName);
			model.addAttribute("infoUser", infoUser);

			// 个人信息 - 相关问题分页
			PaginationDTO paginationDTO = questionService.list(infoUser.getId(), page, size);
			model.addAttribute("paginationDTO", paginationDTO);

			// 个人信息 - 相关标签, 先得到个人相关问题
			List<Question> questions = questionMapper.listByUser(infoUser.getId());
			HashSet<String> tags = new HashSet<String>();

			for (Question question : questions) {
				tags.add(question.getTag());
			}

			model.addAttribute("tags", tags);

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

		}

		return "userinfo";
	}

}
