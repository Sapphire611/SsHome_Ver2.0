package com.sapphire.demo.controller;

import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.model.User;
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

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           HttpServletRequest request,
                           Model model){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/login";
        }else{
            QuestionDTO questionDTO = questionService.getById(id);
            model.addAttribute("question",questionDTO);
            return "question";
        }
    }
}
