package com.fable.insightview.platform.snmpcommunity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * SSH TELNET
 * 
 * @author 王淑平
 */
@Entity
@Table(name = "SysAuthCommunity")
public class SysAuthCommunityBean extends
		com.fable.insightview.platform.itsm.core.entity.Entity implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("all")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sysauthcommunity_gen")
	@TableGenerator(initialValue=10001, name = "sysauthcommunity_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysAuthCommunityPK", allocationSize = 1)
	@Column(name = "ID")
	private int id;

	@Column(name = "AuthType")
	private Integer authType;

	@Column(name = "DeviceIP")
	private String deviceIP;

	@Column(name = "DomainID")
	private Integer domainID;

	@Column(name = "MOID")
	private Integer moID;

	@Transient
	private String moName;
	@Transient
	private String checkIP;
	@Transient
	private String flag;


	@Column(name = "Port")
	private Integer port;

	@Column(name = "UserName")
	private String userName;

	@Column(name = "Password")
	private String password;

	@Column(name = "LoginPattern")
	private String loginPattern;

	@Column(name = "EnLoginPattern")
	private String enLoginPattern;

	@Column(name = "EnablePassword")
	private String enablePassword;

	@Column(name = "EnableUserName")
	private String enableUserName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public Integer getDomainID() {
		return domainID;
	}

	public void setDomainID(Integer domainID) {
		this.domainID = domainID;
	}

	public String getCheckIP() {
		return checkIP;
	}

	public void setCheckIP(String checkIP) {
		this.checkIP = checkIP;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getMoID() {
		return moID;
	}

	public void setMoID(Integer moID) {
		this.moID = moID;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
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

	public String getLoginPattern() {
		return loginPattern;
	}

	public void setLoginPattern(String loginPattern) {
		this.loginPattern = loginPattern;
	}

	public String getEnLoginPattern() {
		return enLoginPattern;
	}

	public void setEnLoginPattern(String enLoginPattern) {
		this.enLoginPattern = enLoginPattern;
	}

	public String getEnablePassword() {
		return enablePassword;
	}

	public void setEnablePassword(String enablePassword) {
		this.enablePassword = enablePassword;
	}

	public String getEnableUserName() {
		return enableUserName;
	}

	public void setEnableUserName(String enableUserName) {
		this.enableUserName = enableUserName;
	}

}