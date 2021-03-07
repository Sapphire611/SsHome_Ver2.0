package com.sapphire.demo.dto;

import java.util.List;

import com.sapphire.demo.exception.CustomizeErrorCode;
import com.sapphire.demo.exception.CustomizeException;

public class ResultDTO<T> {
	private Integer code;
	private String message;
	private T data;

	// 返回json格式的错误信息
	public static ResultDTO errorOf(Integer code, String message) {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(code);
		resultDTO.setMessage(message);

		return resultDTO;
	}

	// 返回json格式的错误信息
	public static ResultDTO okOf() {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(200);
		resultDTO.setMessage("请求成功");
		return resultDTO;
	}

	// 返回
	public static <T> ResultDTO okOf(T t) {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(200);
		resultDTO.setMessage("请求成功");
		resultDTO.setData(t);
		return resultDTO;
	}

	public static ResultDTO errorOf(CustomizeErrorCode sysError) {
		return errorOf(sysError.getCode(), sysError.getMessage());
	}

	public static ResultDTO errorOf(CustomizeException e) {
		return errorOf(e.getCode(), e.getMessage());
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	
}
