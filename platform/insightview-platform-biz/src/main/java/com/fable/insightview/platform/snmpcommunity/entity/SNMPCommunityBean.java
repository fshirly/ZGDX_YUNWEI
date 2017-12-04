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
 * 凭证
 * 
 * @author 王淑平
 */
@Entity
@Table(name = "SNMPCommunity")
public class SNMPCommunityBean extends
		com.fable.insightview.platform.itsm.core.entity.Entity implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("all")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "snmpcommunity_gen")
	@TableGenerator(initialValue=100001, name = "snmpcommunity_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SNMPCommunityPK", allocationSize = 1)
	@Column(name = "ID")
	private int id;

	@Column(name = "Alias")
	private String alias;

	@Column(name = "DomainID")
	private Integer domainID;

	@Transient
	private String flag;
	@Transient
	private String checkIP;


	@Column(name = "SnmpPort")
	private Integer snmpPort;

	@Column(name = "SnmpVersion")
	private int snmpVersion;

	@Column(name = "ReadCommunity")
	private String readCommunity;

	@Column(name = "WriteCommunity")
	private String writeCommunity;

	@Column(name = "USMUser")
	private String usmUser;

	@Column(name = "SecurityLevel")
	private String securityLevel;

	@Column(name = "AuthAlogrithm")
	private String authAlogrithm;

	@Column(name = "AuthKey")
	private String authKey;

	@Column(name = "EncryptionAlogrithm")
	private String encryptionAlogrithm;

	@Column(name = "EncryptionKey")
	private String encryptionKey;

	@Column(name = "ContexName")
	private String contexName;


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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getDomainID() {
		return domainID;
	}

	public void setDomainID(Integer domainID) {
		this.domainID = domainID;
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

}