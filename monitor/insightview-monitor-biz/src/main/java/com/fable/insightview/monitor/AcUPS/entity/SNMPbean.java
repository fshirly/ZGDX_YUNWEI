package com.fable.insightview.monitor.AcUPS.entity;

public class SNMPbean {
	private Integer ID;
	private String deviceIP;
	private int  moID;
	private int domainID;
	private int snmpPort;
	private Integer snmpVersions;
	private String readCommunity;
	private String writeCommunity;
	private String USMUser;
	private String authAlogrithm;
	private String authKey;
	private String encryptionAlogrithm;
	private String encryptionKey;
	private String contexName;
	private int id;
	public int getID() {
		return ID;
	}
	 
	public void setID(Integer iD) {
		ID = iD;
	}

	public String getDeviceIP() {
		return deviceIP;
	}
	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}
	public int getMoID() {
		return moID;
	}
	public void setMoID(int moID) {
		this.moID = moID;
	}
	public int getDomainID() {
		return domainID;
	}
	public void setDomainID(int domainID) {
		this.domainID = domainID;
	}
	public int getSnmpPort() {
		return snmpPort;
	}
	public void setSnmpPort(int snmpPort) {
		this.snmpPort = snmpPort;
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
	public String getUSMUser() {
		return USMUser;
	}
	public void setUSMUser(String uSMUser) {
		USMUser = uSMUser;
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
	public Integer getSnmpVersions() {
		return snmpVersions;
	}
	public void setSnmpVersions(Integer snmpVersions) {
		this.snmpVersions = snmpVersions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	 
	
}
