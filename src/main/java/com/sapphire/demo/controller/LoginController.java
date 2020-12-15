package com.sapphire.demo.controller;

import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author : Sapphire L
 * @Date : 2020/6/1 5:18
 */
@Controller
public class LoginController {

	@Autowired
	private UserMapper userMapper;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String doLogin(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userName") String userName, @RequestParam("password") String password, Model model) {
			User user = userMapper.loginByUserName(userName, password);
			if (user != null && user.getPassword().equals(password)) {
				model.addAttribute("user", user);
				response.addCookie(new Cookie("token", user.getToken())); // 把Token放入Cookie中
				return "redirect:/";
			} else {
				model.addAttribute("wrongMsg", "AccountId or Password error ...");
				// System.out.println("应该是失败了");
				return "login";
			
		}
	}
}
