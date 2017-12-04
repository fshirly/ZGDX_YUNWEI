package com.fable.insightview.monitor.machineRoom.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class MoiemBean {
	@NumberGenerator(name = "MO")
	private int MOID;
	private int idRtDevice;
	private int type;
	private int address;
	private String hostName;
	private String serialNo;
	private String defDesc;
	private String oriDesc;
	private String ipAddr;
	private String createTime;
	private String lastUpdateTime;
	private int moClassID;
	private String classLable;
	public int getIdRtDevice() {
		return idRtDevice;
	}
	public void setIdRtDevice(int idRtDevice) {
		this.idRtDevice = idRtDevice;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getDefDesc() {
		return defDesc;
	}
	public void setDefDesc(String defDesc) {
		this.defDesc = defDesc;
	}
	public String getOriDesc() {
		return oriDesc;
	}
	public void setOriDesc(String oriDesc) {
		this.oriDesc = oriDesc;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public int getMOID() {
		return MOID;
	}
	public void setMOID(int mOID) {
		MOID = mOID;
	}
	public int getMoClassID() {
		return moClassID;
	}
	public void setMoClassID(int moClassID) {
		this.moClassID = moClassID;
	}
	public String getClassLable() {
		return classLable;
	}
	public void setClassLable(String classLable) {
		this.classLable = classLable;
	}
	
}
