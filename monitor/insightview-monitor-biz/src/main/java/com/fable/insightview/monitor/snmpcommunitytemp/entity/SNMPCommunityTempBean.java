package com.fable.insightview.monitor.snmpcommunitytemp.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class SNMPCommunityTempBean {
	@NumberGenerator(name = "SNMPCommunityTempPK")
	private int id;

	private String deviceIp;

	private Integer domainId;

	private Integer moId;
	
	private Integer snmpPort;

	private int snmpVersion;

	private String readCommunity;

	private String writeCommunity;

	private String usmUser;

	private String securityLevel;

	private String authAlogrithm;

	private String authKey;

	private String encryptionAlogrithm;

	private String encryptionKey;

	private String contexName;
	
	private String moName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public Integer getDomainId() {
		return domainId;
	}

	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}

	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public Integer getSnmpPort() {
		return snmpPort;
	}

	public void setSnmpPort(Integer snmpPort) {
		this.snmpPort = snmpPort;
	}

	public int getSnmpVersion() {
		return snmpVersion;
	}

	public void setSnmpVersion(int snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public String getReadCommunity() {
		return readCommunity;
	}

	public void setReadCommunity(String readCommunity) {
		this.readCommunity = readCommunity;
	}

	public String getWriteCommunity() {
		return writeCommunity;
	}

	public void setWriteCommunity(String writeCommunity) {
		this.writeCommunity = writeCommunity;
	}

	public String getUsmUser() {
		return usmUser;
	}

	public void setUsmUser(String usmUser) {
		this.usmUser = usmUser;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getAuthAlogrithm() {
		return authAlogrithm;
	}

	public void setAuthAlogrithm(String authAlogrithm) {
		this.authAlogrithm = authAlogrithm;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getEncryptionAlogrithm() {
		return encryptionAlogrithm;
	}

	public void setEncryptionAlogrithm(String encryptionAlogrithm) {
		this.encryptionAlogrithm = encryptionAlogrithm;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public String getContexName() {
		return contexName;
	}

	public void setContexName(String contexName) {
		this.contexName = contexName;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}
	
	
}
