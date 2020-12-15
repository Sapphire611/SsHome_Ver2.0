package com.sapphire.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CmsController {

    @GetMapping("/functions/admin/cms")
    public String hello() {
        return "cms";
    }
}
