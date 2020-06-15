package com.sapphire.demo.controller;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/hello")
    // size = 一页显示7个问题
    public String hello() {
        return "hello";
    }
}
