package com.sapphire.demo.controller;

import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.model.LikeRecord;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.User;
import com.sapphire.demo.model.ViewRecord;
import com.sapphire.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : Sapphire L
 * @Date : 2020/6/1 5:18
 */
@Controller
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionMapper questionMapper;

	@GetMapping("/question/{id}")
	public String question(@PathVariable(name = "id") Integer id, HttpServletRequest request, Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");
		
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			// 判断当前用户是否已阅读，如果已阅读，浏览数不加一
			boolean currentUser_view = false;
			currentUser_view = judgeViewed(id,request,currentUser);
			
			if(currentUser_view == false) {
				questionMapper.viewAdd(id); // 访问数 + 1
				ViewRecord viewRecord = new ViewRecord();
				viewRecord.setQuestionId(id);
				viewRecord.setUserId(currentUser.getId());
				viewRecord.setGmtCreate(System.currentTimeMillis());
				questionMapper.viewQuestion(viewRecord); // 写入
			}
			
			// 判断当前用户是否已点赞，如果点了，传一个“Button” -> 按钮变成绿色
			boolean currentUser_like = false;
			
			currentUser_like = judgeLike(id,request,currentUser);
			if(currentUser_like == true) {
				model.addAttribute("Button","Liked");
			}
			
			QuestionDTO questionDTO = questionService.getById(id);
			model.addAttribute("question", questionDTO);
			return "question";
		}
	}

	@GetMapping("/question/{id}/like")
	public String questionLike(@PathVariable(name = "id") Integer id, HttpServletRequest request, Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");
		boolean currentUser_viewed = false;
		currentUser_viewed = judgeLike(id,request,currentUser);
		
		if(currentUser_viewed == false) {
			LikeRecord likeRecord = new LikeRecord();
			likeRecord.setQuestionId(id);
			likeRecord.setUserId(currentUser.getId());
			likeRecord.setGmtCreate(System.currentTimeMillis());
			questionMapper.likeAdd(id);
			questionMapper.likeQuestion(likeRecord); // 写入
		}else {
			// 重定向的话，设置Attribute无效～
			// model.addAttribute("wrongMsg","Each User could only like once...");
		}

		return "redirect:/question/" + id + "";
	}
	
	public Boolean judgeLike(Integer id,HttpServletRequest request,User currentUser) {
		LikeRecord likeId = questionMapper.checkLikeQuestion(id,currentUser.getId());
		
		boolean currentUser_like = false;
		if(likeId != null) {
			currentUser_like = true;
		}
		return currentUser_like;
	}
	
	public Boolean judgeViewed(Integer id,HttpServletRequest request,User currentUser) {
		ViewRecord viewedId = questionMapper.checkViewQuestion(id,currentUser.getId());
		
		boolean currentUser_view = false;
		if(viewedId != null) {
			currentUser_view = true;
		}
		
		return currentUser_view;
	}
}
