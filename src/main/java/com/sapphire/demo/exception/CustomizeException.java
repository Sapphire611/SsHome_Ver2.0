package com.sapphire.demo.exception;

public class CustomizeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	private Integer code;

	public CustomizeException(ICustomizeErrorCode errorCode) {
		this.message = errorCode.getMessage();
		this.code = errorCode.getCode();
	}
	
	
	public Integer getCode() {
		return code;
	}


	public String getMessage() {
		return message;
	}	

}
