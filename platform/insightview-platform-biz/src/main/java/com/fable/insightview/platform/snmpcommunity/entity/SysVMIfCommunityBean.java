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
 * Vmware
 * 
 * @author 王淑平
 */
@Entity
@Table(name = "SysVMIfCommunity")
public class SysVMIfCommunityBean extends
		com.fable.insightview.platform.itsm.core.entity.Entity implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("all")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sysvmifcommunity_gen")
	@TableGenerator(initialValue=10001, name = "sysvmifcommunity_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysVMIfCommunityPK", allocationSize = 1)
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
	private String domainName;
	@Column(name = "Port")
	private Integer port;

	@Column(name = "UserName")
	private String userName;

	@Column(name = "Password")
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
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

}