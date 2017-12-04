package com.fable.insightview.monitor.database.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;


@Alias("db2InfoBean")
public class Db2InfoBean {
	private Integer moId;
	private Integer instanceMOID;
	private String instanceName;
	private String databaseName;
	private String databasePath;
	private Date connTime ;
	private Integer databaseStatus;
	private String ip;
	private String formatTime;
	private String moalias;
	private int port;
	
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public Integer getInstanceMOID() {
		return instanceMOID;
	}
	public void setInstanceMOID(Integer instanceMOID) {
		this.instanceMOID = instanceMOID;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getDatabasePath() {
		return databasePath;
	}
	public void setDatabasePath(String databasePath) {
		this.databasePath = databasePath;
	}
	
	public Date getConnTime() {
		return connTime;
	}
	public void setConnTime(Date connTime) {
		this.connTime = connTime;
	}
	public Integer getDatabaseStatus() {
		return databaseStatus;
	}
	public void setDatabaseStatus(Integer databaseStatus) {
		this.databaseStatus = databaseStatus;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getMoalias() {
		return moalias;
	}
	public void setMoalias(String moalias) {
		this.moalias = moalias;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
