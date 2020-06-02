package com.sapphire.demo.controller;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "7") Integer size,
                          HttpServletRequest request,
                          Model model) {

        //User user = (User) model.getAttribute("user");
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "My Questions");
        } else if("replies".equals(action)){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "Replies");
        }


        PaginationDTO paginationDTO = questionService.list(user.getId(),page,size);
        model.addAttribute("paginationDTO",paginationDTO);

        return "profile";
    }


}
