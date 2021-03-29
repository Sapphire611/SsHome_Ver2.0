package com.sapphire.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.mapper.QuestionExtMapper;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.User;
import com.sapphire.demo.model.UserExample;
import com.sapphire.demo.service.QuestionService;

@Controller
public class ForumController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private QuestionExtMapper questionExtMapper;

	@GetMapping("/forum")
	// size = 一页显示7个问题
	public String forum(Model model, @RequestParam(name = "page", defaultValue = "1") Integer page,
									 @RequestParam(name = "size", defaultValue = "5") Integer size, 
									 @RequestParam(name = "search", required = false) String search,HttpServletRequest request) {

		// User currentUser = (User) request.getSession().getAttribute("user");

		PaginationDTO<QuestionDTO> paginationDTO = questionService.list(search,page, size);
		model.addAttribute("paginationDTO", paginationDTO);
		model.addAttribute("search", search);

		
		// 创建管理员列表
		UserExample example = new UserExample();
		example.createCriteria().andAdminbooleanEqualTo(1);
		List<User> adminUsers = userMapper.selectByExample(example);
		model.addAttribute("adminUsers", adminUsers);
		
		// 热门问题，按照访问量排列		
		List<Question> hotQuestions = questionExtMapper.selectHot();
		model.addAttribute("hotQuestions", hotQuestions);

		return "forum";
	}

}
