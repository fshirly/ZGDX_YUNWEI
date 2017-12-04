package com.fable.insightview.monitor.alarmmgr.entity;

import org.apache.ibatis.type.Alias;

/**
 * 视图MOSource
 * 
 * @author hanl
 * 
 */

@Alias("moScource")
public class MOSourceBean {
	private Integer moID;

	private String moName;

	private String moAlias;

	private String deviceIP;

	private Integer operStatus;

	private Integer alarmLevel;

	private Integer domainID;

	private Integer moClassID;

	private String alarmLevelName;
	private String domainName;
	private String className;

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

	public String getMoAlias() {
		return moAlias;
	}

	public void setMoAlias(String moAlias) {
		this.moAlias = moAlias;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Integer getDomainID() {
		return domainID;
	}

	public void setDomainID(Integer domainID) {
		this.domainID = domainID;
	}

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Integer getMoClassID() {
		return moClassID;
	}

	public void setMoClassID(Integer moClassID) {
		this.moClassID = moClassID;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
