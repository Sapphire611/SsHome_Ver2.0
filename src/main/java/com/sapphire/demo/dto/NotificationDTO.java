package com.sapphire.demo.dto;

public class NotificationDTO {
	private Long id;
	private Long gmtcreate;
	private Integer status;
	private Long notifier;
	private String notifiername;
	private String outertitle;
	private Long outerid;
	private String typeName;
	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getNotifier() {
		return notifier;
	}

	public void setNotifier(Long notifier) {
		this.notifier = notifier;
	}

	public Long getOuterid() {
		return outerid;
	}

	public void setOuterid(Long outerid) {
		this.outerid = outerid;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getGmtcreate() {
		return gmtcreate;
	}

	public void setGmtcreate(Long gmtcreate) {
		this.gmtcreate = gmtcreate;
	}

	public String getNotifiername() {
		return notifiername;
	}

	public void setNotifiername(String notifiername) {
		this.notifiername = notifiername;
	}

	public String getOutertitle() {
		return outertitle;
	}

	public void setOutertitle(String outertitle) {
		this.outertitle = outertitle;
	}
	

}