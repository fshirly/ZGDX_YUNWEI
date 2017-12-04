package com.fable.insightview.monitor.environmentmonitor.entity;

import org.apache.ibatis.type.Alias;

/**
 * 管理对象ZoneManager
 * 
 */
@Alias("zoneManager")
public class MOZoneManagerBean {
	private Integer moID;
	private String ipAddress;
	private Integer port;
	private Integer moClassId;
	private Integer domainID;
	
	private String domainName;
	private String moClassName;
	private Integer readerCount;//阅读器个数
	
	private String userName;
	private String passWord;
	
	private Integer id;//机房环境凭证
	
	private Integer templateID;

	public Integer getMoID() {
		return moID;
	}

	public void setMoID(Integer moID) {
		this.moID = moID;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}


	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

	public Integer getDomainID() {
		return domainID;
	}

	public void setDomainID(Integer domainID) {
		this.domainID = domainID;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getMoClassName() {
		return moClassName;
	}

	public void setMoClassName(String moClassName) {
		this.moClassName = moClassName;
	}

	public Integer getReaderCount() {
		return readerCount;
	}

	public void setReaderCount(Integer readerCount) {
		this.readerCount = readerCount;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTemplateID() {
		return templateID;
	}

	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
	}

}
