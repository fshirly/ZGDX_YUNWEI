package com.fable.insightview.monitor.database.entity;

public class MOMySQLDBBean {
	private Integer moId;
	private Integer serverMoid;
	private String dbName;
	
	 private String serverName;
	 private String ip;
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public Integer getServerMoid() {
		return serverMoid;
	}
	public void setServerMoid(Integer serverMoid) {
		this.serverMoid = serverMoid;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
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
