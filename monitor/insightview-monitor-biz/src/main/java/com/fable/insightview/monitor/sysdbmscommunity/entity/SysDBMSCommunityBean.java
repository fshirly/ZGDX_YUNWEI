package com.fable.insightview.monitor.sysdbmscommunity.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 数据库认证
 * 
 */
@Alias("sysDBMSCommunity")
public class SysDBMSCommunityBean {
	@NumberGenerator(name = "monitorDBMSCommunityPK")
	private Integer id;
	private String ip;
	private String dbName;
	private String dbmsType;
	private Integer domainID;
	private String userName;
	private String password;
	private Integer port;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbmsType() {
		return dbmsType;
	}

	public void setDbmsType(String dbmsType) {
		this.dbmsType = dbmsType;
	}

	public Integer getDomainID() {
		return domainID;
	}

	public void setDomainID(Integer domainID) {
		this.domainID = domainID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}
