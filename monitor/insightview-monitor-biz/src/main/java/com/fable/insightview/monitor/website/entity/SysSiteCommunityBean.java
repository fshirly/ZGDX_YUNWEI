package com.fable.insightview.monitor.website.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 站点监控认证信息表
 * 
 */
public class SysSiteCommunityBean {
	@NumberGenerator(name = "MonitorSiteCommunityPK")
	private Integer id;
	private String ipAddress;
	private Integer port;
	private String userName;
	private String password;
	private Integer domainId;
	private Integer siteType;
	private int requestMethod; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getDomainId() {
		return domainId;
	}

	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}

	public Integer getSiteType() {
		return siteType;
	}

	public void setSiteType(Integer siteType) {
		this.siteType = siteType;
	}

	public int getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(int requestMethod) {
		this.requestMethod = requestMethod;
	}

}
