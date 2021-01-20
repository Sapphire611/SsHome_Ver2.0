package com.sapphire.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapphire.demo.mapper.AdminMapper;
import com.sapphire.demo.model.Admin;

@Controller
public class AdminController {

	@Autowired
	private AdminMapper adminMapper;
	
    @GetMapping("/functions/admin")
    // size = 一页显示7个问题
    public String admin() {
        return "admin";
    }
    
    @PostMapping("/functions/admin")
	public String doAdminLogin(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("adminName") String adminName, @RequestParam("adminPassword") String adminPassword, Model model) {
			
    		// 模仿User的登陆过程，登陆Admin后台
    		Admin admin = adminMapper.loginByUserName(adminName, adminPassword);
			if (admin != null && admin.getPassword().equals(adminPassword)) {
				model.addAttribute("admin", admin);
				
				return "cms";
			} else {
				model.addAttribute("wrongMsg", "AccountId or Password error ...");
				// System.out.println("应该是失败了");
				return "admin";
			
		}
}
}
