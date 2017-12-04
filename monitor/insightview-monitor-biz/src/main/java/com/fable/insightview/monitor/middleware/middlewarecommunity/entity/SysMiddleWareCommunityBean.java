package com.fable.insightview.monitor.middleware.middlewarecommunity.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 中间件认证信息
 * 
 */

@Alias("middleWareCommunity")
public class SysMiddleWareCommunityBean {
	@NumberGenerator(name = "monitorPMiddleWareCommunityPK")
	private Integer id;
	/* 中间件名称 */
	private Integer middleWareName;

	/* 中间件类型 */
	private String middleWareType;

	/* IP地址 */
	private String ipAddress;

	/* 端口 */
	private String port;

	/* 用户名 */
	private String userName;

	/* 密码 */
	private String passWord;

	/* 所属管理域 */
	private Integer domainID;

	private String domainName;
	
	/*web应用可用性采集测试URL*/
	private String url;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMiddleWareName() {
		return middleWareName;
	}

	public void setMiddleWareName(Integer middleWareName) {
		this.middleWareName = middleWareName;
	}

	public String getMiddleWareType() {
		return middleWareType;
	}

	public void setMiddleWareType(String middleWareType) {
		this.middleWareType = middleWareType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
