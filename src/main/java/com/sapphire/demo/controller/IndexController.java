package com.sapphire.demo.controller;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.QuestionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    // size = 一页显示7个问题
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "7") Integer size) {


        PaginationDTO paginationDTO = questionService.list(page,size);
        model.addAttribute("paginationDTO",paginationDTO);
        
        List<User> adminUsers = userMapper.findAdmin(); 
        model.addAttribute("adminUsers",adminUsers);
        
        return "index";
    }
}
