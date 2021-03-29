package com.sapphire.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapphire.demo.cache.TagCache;
import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.QuestionService;

@Controller
public class PublishController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionMapper questionMapper;

	@GetMapping("/publish/{id}")
	public String edit(@PathVariable(name = "id") Long id, Model model, HttpServletRequest request) {
		User currentUser = (User) request.getSession().getAttribute("user");
		Long questionAuthorId = questionMapper.selectByPrimaryKey(id).getCreator();

		if (currentUser == null) {
			return "redirect:/";
			// 管理员 + 问题编辑者可以编辑问题
		} else if (currentUser.getAdminboolean() == 1 || questionAuthorId != currentUser.getId()) {
			return "redirect:/forum";
		} else {
			QuestionDTO question = questionService.getById(id);
			model.addAttribute("title", question.getTitle());
			model.addAttribute("description", question.getDescription());
			model.addAttribute("tag", question.getTag());
			model.addAttribute("id", question.getId());
			model.addAttribute("tags", TagCache.get());

			return "publish";
		}

	}

	@GetMapping("/publish")
	public String publish(Model model, HttpServletRequest request) {
		model.addAttribute("tags", TagCache.get());
		return "publish";
	}

	@PostMapping("/publish")
	public String dopublish(@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "tag", required = false) String tag,
			@RequestParam(value = "id", required = false) Long id, HttpServletRequest request, Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");
		model.addAttribute("title", title);
		model.addAttribute("description", description);
		model.addAttribute("tag", tag);
		model.addAttribute("tags", TagCache.get());

		if (title == null || title == "") {
			model.addAttribute("error", "请输入标题！");
			return "publish";
		}

		if (description == null || description == "") {
			model.addAttribute("error", "请输入问题内容！");
			return "publish";
		}

		if (tag == null || tag == "") {
			model.addAttribute("error", "请输入标签！");
			return "publish";
		}
		
		if (tag.length() > 45) {
			model.addAttribute("error", "标签过多...");
			return "publish";
		}

		// 标签校验
		String invalid = TagCache.filterInvalid(tag);
		if (StringUtils.isNotBlank(invalid)) {
			model.addAttribute("error", "输入非法标签:" + invalid);
			return "publish";
		}

		if (currentUser == null) {
			model.addAttribute("error", "Please Login first...");
			return "publish";
		}

		Question question = new Question();
		question.setTitle(title);
		question.setId(id);
		question.setDescription(description.trim());
		question.setTag(tag.trim());
		question.setCreator(currentUser.getId());
		
		int flag = questionService.createOrUpdate(question);

		// 根据功能跳转至不同路径
		if(flag == 1) {
			// 1 = 发送新问题
			return "redirect:/forum/" ;
		}else {
			// 2 = 更新问题
			return "redirect:/question/" + id;
		}
		
	}

}
