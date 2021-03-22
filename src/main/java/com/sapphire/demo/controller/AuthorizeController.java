package com.sapphire.demo.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapphire.demo.dto.AccessTokenDTO;
import com.sapphire.demo.dto.GithubUser;
import com.sapphire.demo.model.User;
import com.sapphire.demo.provider.GithubProvider;
import com.sapphire.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AuthorizeController {

	@Autowired
	private GithubProvider githubProvider;

	@Value("${github.client.id}")
	private String clientId;

	@Value("${github.client.secret}")
	private String clientSecret;

	@Value("${github.client.redirect.uri}")
	private String clientRedirectUri;

	@Autowired
	private UserService userService;

	@GetMapping("/callback")
	// 解决[Required String parameter 'code' is not present]
	public String callback(@RequestParam(name = "code",required = false) String code, 
				           @RequestParam(name = "state",required = false) String state,
		HttpServletRequest request, HttpServletResponse response) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO(); // 通过accesstoken接口得到accesstoken
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(clientRedirectUri);
		accessTokenDTO.setState(state);
		String accessToken = githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubUser = githubProvider.getUser(accessToken);// 通过accesstoken得到user

		if (githubUser != null) {
			User user = new User();
			String token = UUID.randomUUID().toString();
			user.setToken(token);

			// 为了得到GitHub中能够对应搜索到的名字
			user.setName(githubUser.getLogin());
			user.setAccountid(String.valueOf(githubUser.getId())); // Long -> String 强制转换
			user.setAvatarurl(githubUser.getAvatar_url());
			user.setBio(githubUser.getBio());
			user.setAdminboolean(0);
			user.setEmail(githubUser.getEmail());
			userService.createOrUpdate(user);

			// Cookie & Session
			Cookie cookie = new Cookie("token", token);
			cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
			response.addCookie(cookie); // 把Token放入Cookie中

			request.getSession().setAttribute("githubUser", githubUser);
			return "redirect:/";
		} else {
			// Login failed
			log.error("callback get github error,{}", githubUser);
			return "redirect:/";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("user");
		Cookie cookie = new Cookie("token", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);

		return "redirect:/";
	}
}
