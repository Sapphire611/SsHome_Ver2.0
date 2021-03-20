package com.sapphire.demo.provider;

import com.alibaba.fastjson.JSON;
import com.sapphire.demo.dto.AccessTokenDTO;
import com.sapphire.demo.dto.GithubUser;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class GithubProvider {
	public String getAccessToken(AccessTokenDTO accessTokenDTO) {
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");

		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
		Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
		try (Response response = client.newCall(request).execute()) {
			String string = response.body().string();
			// access_token=d9a6f4f12987394d0440e1a0c4427bb51a843101&scope=user&token_type=bearer
			String token = string.split("&")[0].split("=")[1];
			// System.out.println("Token = " + token);
			return token;
		} catch (Exception e) {
			log.error("getAccessToken error,{}", accessTokenDTO, e);
		}
		return null;
	}

	public GithubUser getUser(String accessToken) {
		OkHttpClient client = new OkHttpClient();

		// https://niter.cn/p/115 之前的方法已被废弃，
		Request request = new Request.Builder().url("https://api.github.com/user")
				.header("Authorization", "token " + accessToken).build();

		try {
			Response response = client.newCall(request).execute();
			String string = response.body().string();
			// 不需要自己解析了,json -> class
			GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
			System.out.println(githubUser.toString());
			return githubUser;

		} catch (IOException e) {
			log.error("getUser error,{}", accessToken, e);
		}
		return null;
	}
}
