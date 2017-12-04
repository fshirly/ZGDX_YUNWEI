package com.fable.insightview.monitor.middleware.tomcat.entity;

public class MOMiddleWareServletBean {
	private Integer moId;
	private Integer parentMOID;
	private String servletName;
	private String parentMoName;
	private String warName;
	private String ip;

	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public Integer getParentMOID() {
		return parentMOID;
	}

	public void setParentMOID(Integer parentMOID) {
		this.parentMOID = parentMOID;
	}

	public String getServletName() {
		return servletName;
	}

	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	public String getParentMoName() {
		return parentMoName;
	}

	public void setParentMoName(String parentMoName) {
		this.parentMoName = parentMoName;
	}

	public String getWarName() {
		return warName;
	}

	public void setWarName(String warName) {
		this.warName = warName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	

}
