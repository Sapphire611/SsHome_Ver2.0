package com.sapphire.demo.dto;

import com.sapphire.demo.exception.CustomizeErrorCode;
import com.sapphire.demo.exception.CustomizeException;

public class ResultDTO {
	private Integer code;
	private String message;

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

	

}
