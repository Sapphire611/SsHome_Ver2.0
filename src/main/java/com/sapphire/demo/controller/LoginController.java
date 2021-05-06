package com.sapphire.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author : Sapphire L
 * @Date : 2020/6/1 5:18
 */
@Controller
public class LoginController {

	// @Autowired
	// private UserMapper userMapper;

	@Value("${url}")
	private String url;

	@Value("${github.client.id}")
	private String clientId;

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("url", url);
		model.addAttribute("clientId", clientId);
		return "login";
	}

	// @PostMapping("/login")
	// public String doLogin(HttpServletRequest request, HttpServletResponse
	// response,
	// @RequestParam("username") String userName, @RequestParam("password") String
	// password, Model model) {
	// UserExample userExample = new UserExample();
	// userExample.createCriteria().andNameEqualTo(userName);
	// userExample.createCriteria().andPasswordEqualTo(password);
	// List<User> users = userMapper.selectByExample(userExample);
	// User user = users.get(0);
	//
	// if (user != null && user.getPassword().equals(password)) {
	// model.addAttribute("user", user);
	// response.addCookie(new Cookie("token", user.getToken())); // 把Token放入Cookie中
	// return "redirect:/";
	// } else {
	// model.addAttribute("wrongMsg", "AccountId or Password error ...");
	// // System.out.println("应该是失败了");
	// return "login";
	//
	// }
	// }
}
