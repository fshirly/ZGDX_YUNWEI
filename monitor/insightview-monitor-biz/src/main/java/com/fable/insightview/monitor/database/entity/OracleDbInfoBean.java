package com.fable.insightview.monitor.database.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;


@Alias("oracleDbInfo")
public class OracleDbInfoBean {
	private Integer moId;
	private String dbName;
	private String ip;
	private Date createTime;
	private String openMode;
	private String logMode;
	private String formatTime;
	public OracleDbInfoBean() {
	}
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOpenMode() {
		return openMode;
	}
	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}
	public String getLogMode() {
		return logMode;
	}
	public void setLogMode(String logMode) {
		this.logMode = logMode;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	
}
