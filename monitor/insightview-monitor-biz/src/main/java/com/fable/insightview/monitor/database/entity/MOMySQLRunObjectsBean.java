package com.fable.insightview.monitor.database.entity;

public class MOMySQLRunObjectsBean {
	private Integer moId;
	private Integer serverMOID;
	private String moName;
	private String descInfo;
	private Integer type;
	
	private String serverName;
	private String typeName;
	private String ip;
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public Integer getServerMOID() {
		return serverMOID;
	}
	public void setServerMOID(Integer serverMOID) {
		this.serverMOID = serverMOID;
	}
	public String getMoName() {
		return moName;
	}
	public void setMoName(String moName) {
		this.moName = moName;
	}
	public String getDescInfo() {
		return descInfo;
	}
	public void setDescInfo(String descInfo) {
		this.descInfo = descInfo;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
