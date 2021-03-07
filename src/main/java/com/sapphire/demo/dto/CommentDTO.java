package com.sapphire.demo.dto;

import com.sapphire.demo.model.User;

public class CommentDTO {

	private Long id;

	private Long parentId;

	private Integer type;

	private Long commentator;

	private Long gmtcreate;

	private Long gmtmodified;

	private Long commentcount;

	private String content;

	private User user;


	public Long getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(Long commentcount) {
		this.commentcount = commentcount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCommentator() {
		return commentator;
	}

	public void setCommentator(Long commentator) {
		this.commentator = commentator;
	}

	public Long getGmtcreate() {
		return gmtcreate;
	}

	public void setGmtcreate(Long gmtcreate) {
		this.gmtcreate = gmtcreate;
	}

	public Long getGmtmodified() {
		return gmtmodified;
	}

	public void setGmtmodified(Long gmtmodified) {
		this.gmtmodified = gmtmodified;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
