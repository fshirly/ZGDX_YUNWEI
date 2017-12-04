package com.fable.insightview.monitor.mostorage.entity;

import org.apache.ibatis.type.Alias;

/**
 * 管理对象设备数据存储
 * 
 */

@Alias("moStorage")
public class MOStorageBean {
	private Integer moID;
	private String moName;
	private String moAlias;
	private Integer operStatus;
	private Integer adminStatus;
	private Integer alarmLevel;
	private Integer deviceMOID;
	private String instance;
	private String rawDescr;
	private String volumeType;
	private String capacity;
	private String mIBModule;

	private String deviceMOName;
	private String deviceIP;
	private String adminStatusName;
	private String operStatusName;
	 
	private Integer resid;
	
	public Integer getResid() {
		return resid;
	}

	public void setResid(Integer resid) {
		this.resid = resid;
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

	public String getMoAlias() {
		return moAlias;
	}

	public void setMoAlias(String moAlias) {
		this.moAlias = moAlias;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public Integer getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(Integer adminStatus) {
		this.adminStatus = adminStatus;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Integer getDeviceMOID() {
		return deviceMOID;
	}

	public void setDeviceMOID(Integer deviceMOID) {
		this.deviceMOID = deviceMOID;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getRawDescr() {
		return rawDescr;
	}

	public void setRawDescr(String rawDescr) {
		this.rawDescr = rawDescr;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	public String getmIBModule() {
		return mIBModule;
	}

	public void setmIBModule(String mIBModule) {
		this.mIBModule = mIBModule;
	}

	public String getDeviceMOName() {
		return deviceMOName;
	}

	public void setDeviceMOName(String deviceMOName) {
		this.deviceMOName = deviceMOName;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public String getAdminStatusName() {
		return adminStatusName;
	}

	public void setAdminStatusName(String adminStatusName) {
		this.adminStatusName = adminStatusName;
	}

	public String getOperStatusName() {
		return operStatusName;
	}

	public void setOperStatusName(String operStatusName) {
		this.operStatusName = operStatusName;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
}
