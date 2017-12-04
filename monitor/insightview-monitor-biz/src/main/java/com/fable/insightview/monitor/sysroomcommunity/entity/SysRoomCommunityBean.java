package com.fable.insightview.monitor.sysroomcommunity.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 3D机房认证
 *@author zhurt
 */

@Alias("sysRoomCommunity")
public class SysRoomCommunityBean {
	@NumberGenerator(name = "monitorRoomCommunityPK")
	private Integer id;
	private String ipAddress;
	private Integer port;
	private String userName;
	private String passWord;
	private Integer domainID;
	
	private String domainName;
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
	
}