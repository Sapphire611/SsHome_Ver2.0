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
import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.QuestionExample;
import com.sapphire.demo.model.User;
import com.sapphire.demo.model.UserExample;
import com.sapphire.demo.service.QuestionService;

@Controller
public class UserInfoController {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private QuestionMapper questionMapper;

	@Autowired
	private QuestionService questionService;

	@GetMapping("/userinfo/{userName}")

	public String userInfo(@PathVariable(name = "userName") String userName,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "3") Integer size, HttpServletRequest request, Model model) {

		// 根据URL中的userName 找到个人信息对应用户
		UserExample userExample = new UserExample();
		userExample.createCriteria().andNameEqualTo(userName);

		User infoUser = userMapper.selectByExample(userExample).get(0);
		model.addAttribute("infoUser", infoUser);

		// 个人信息 - 相关问题分页
		PaginationDTO<QuestionDTO> paginationDTO = questionService.list(infoUser.getId(), page, size);
		model.addAttribute("paginationDTO", paginationDTO);

		// 个人信息 - 相关标签, 先得到个人相关问题
		// List<Question> questions = questionMapper.listByUser(infoUser.getId());

		QuestionExample example2 = new QuestionExample();
		example2.createCriteria().andCreatorEqualTo(infoUser.getId());
		List<Question> questions = questionMapper.selectByExample(example2);

		HashSet<String> tags = new HashSet<String>();

		for (Question question : questions) {
			tags.add(question.getTag());
		}

		model.addAttribute("tags", tags);

		return "userinfo";
	}

}
