package com.sapphire.demo.advice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.sapphire.demo.dto.ResultDTO;
import com.sapphire.demo.exception.CustomizeErrorCode;
import com.sapphire.demo.exception.CustomizeException;

@ControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {

	// @ExceptionHandler(Exception.class)
	ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {

		String contentType = request.getContentType();
		if ("application/json".equals(contentType)) {
			ResultDTO resultDTO;

			if (e instanceof CustomizeException) {
				resultDTO = ResultDTO.errorOf((CustomizeException) e);
			} else {
				resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
			}

			try {
				response.setContentType("application/json");
				response.setStatus(200);
				response.setCharacterEncoding("utf-8");
				PrintWriter writer = response.getWriter();
				writer.write(JSON.toJSONString(resultDTO)); // 手动给前端写值
				writer.close();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		} else {
			if (e instanceof CustomizeException) {
				model.addAttribute("message", e.getMessage());
			} else {
				// 添加通用错误信息
				model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
			}
			return new ModelAndView("error");
		}

	}

}
