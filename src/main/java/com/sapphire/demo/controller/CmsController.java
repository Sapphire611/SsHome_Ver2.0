package com.sapphire.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CmsController {

    @GetMapping("cms")
    public String cms() {
        return "cms";
    }
    
}
