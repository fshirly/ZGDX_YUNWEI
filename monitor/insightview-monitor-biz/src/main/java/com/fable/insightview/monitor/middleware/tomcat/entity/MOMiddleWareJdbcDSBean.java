package com.fable.insightview.monitor.middleware.tomcat.entity;

public class MOMiddleWareJdbcDSBean {
	private Integer moId;
	private Integer parentMOID;
	private String dSName;
	private String jdbcDriver;
	private String status;
	private String userName;
	private String passWord;
	private String dBUrl;
	private Integer initialSize;
	private Integer maxActive;
	private Integer minIdle;
	private Integer maxIdle;
	private String maxWait;
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

	public String getdSName() {
		return dSName;
	}

	public void setdSName(String dSName) {
		this.dSName = dSName;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getdBUrl() {
		return dBUrl;
	}

	public void setdBUrl(String dBUrl) {
		this.dBUrl = dBUrl;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(String maxWait) {
		this.maxWait = maxWait;
	}

}
