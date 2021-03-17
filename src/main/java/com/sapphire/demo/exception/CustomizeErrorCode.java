package com.sapphire.demo.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

	QUESTION_NOT_FOUND(2001, "你找的问题不存在，要不要换个试试？"), 
	TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
	NO_LOGIN(2003, "当前操作需要登陆，请登陆后重试，点击确定可进行登陆！"),
	SYS_ERROR(2004, "系统好像出了一些问题？！"),
	TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
	COMMENT_NOT_FOUND(2006,"你回复的评论不存在了,要不要换个试试？"),
	COMMENT_IS_EMPTY(2007,"回复的内容不能为空"),
	READ_NOTIFICATION_FAIL(2008, "兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),
    INVALID_INPUT(2011, "非法输入"),
    INVALID_OPERATION(2012, "兄弟，是不是走错房间了？");

	private String message;
	private Integer code;

	private CustomizeErrorCode(Integer code, String message) {
		this.message = message;
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Integer getCode() {
		return code;
	}

}
