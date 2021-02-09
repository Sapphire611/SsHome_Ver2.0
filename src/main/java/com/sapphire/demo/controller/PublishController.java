package com.sapphire.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
			return "redirect:/login";
			// 这里似乎要防止别的用户进入这个地址，不然任何人都可以编辑问题
		} else if (questionAuthorId != currentUser.getId()) {
			return "redirect:/forum";
		} else {

			QuestionDTO question = questionService.getById(id);

			model.addAttribute("title", question.getTitle());
			model.addAttribute("description", question.getDescription());
			model.addAttribute("tag", question.getTag());
			model.addAttribute("id", question.getId());

			return "/publish";
		}

	}

	@GetMapping("/publish")
	public String publish(Model model, HttpServletRequest request) {

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


		if (title == null || title == "") {
			model.addAttribute("error", "Please input Title...");
			return "/publish";
		}

		if (description == null || description == "") {
			model.addAttribute("error", "Please input Description...");
			return "/publish";
		}

		if (tag == null || tag == "") {
			model.addAttribute("error", "Please input tags...");
			return "/publish";
		}

		if (tag.length() > 6) {
			model.addAttribute("error", "too long for tags...");
			return "/publish";
		}

		if (currentUser == null) {
			model.addAttribute("error", "Please Login first...");
			return "publish";
		}

		Question question = new Question();
		question.setTitle(title);
		question.setId(id);
		question.setDescription(description.trim());
		// System.out.println(question.getDescription());
		question.setTag(tag.trim());
		question.setCreator(currentUser.getId());
		questionService.createOrUpdate(question);

		return "redirect:/forum";
	}

}
