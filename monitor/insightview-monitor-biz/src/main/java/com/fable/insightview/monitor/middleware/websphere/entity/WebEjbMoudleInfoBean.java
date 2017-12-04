package com.fable.insightview.monitor.middleware.websphere.entity;

import org.apache.ibatis.type.Alias;


@Alias("webEjbMoudleInfoBean")
public class WebEjbMoudleInfoBean {
	private Integer moId;
	private String ejbName;
	private String status;
	private String uri;
	public WebEjbMoudleInfoBean() {
	}
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public String getEjbName() {
		return ejbName;
	}
	public void setEjbName(String ejbName) {
		this.ejbName = ejbName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
