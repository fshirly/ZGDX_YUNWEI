package com.fable.insightview.monitor.middleware.tomcat.entity;

public class MOMiddleWareJMSBean {
	private Integer moId;
	private Integer parentMOID;
	private String name;
	private String status;
	private String serverName ;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
