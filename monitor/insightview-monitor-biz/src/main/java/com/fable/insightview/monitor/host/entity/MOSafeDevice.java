package com.fable.insightview.monitor.host.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;
/**
 * 安全设备
 * */
@Alias("mosafedeviceInfo")
public class MOSafeDevice {
	private Integer mOID;
	private String mOName;
	private String deviceIP;
	private String hardwareVer;
	private String softwareVer;
	private Integer cpucount;
	private String memorySize;
	private String vMemorySize;
	private String diskSize;
	private Date lastUpdateTime;
	private Integer ifcount;//接口数
	private Integer connLower;//最大支持会话数
	
    //连接数
	private Integer connTopNum;//连接数
	private Integer connTopIndex;//索引
	private String connTopIP;//ip
	public Integer getmOID() {
		return mOID;
	}
	public void setmOID(Integer mOID) {
		this.mOID = mOID;
	}
	public String getmOName() {
		return mOName;
	}
	public void setmOName(String mOName) {
		this.mOName = mOName;
	}
	public String getDeviceIP() {
		return deviceIP;
	}
	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}
	public String getHardwareVer() {
		return hardwareVer;
	}
	public void setHardwareVer(String hardwareVer) {
		this.hardwareVer = hardwareVer;
	}
	public String getSoftwareVer() {
		return softwareVer;
	}
	public void setSoftwareVer(String softwareVer) {
		this.softwareVer = softwareVer;
	}
	public Integer getCpucount() {
		return cpucount;
	}
	public void setCpucount(Integer cpucount) {
		this.cpucount = cpucount;
	}
	public String getMemorySize() {
		return memorySize;
	}
	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}
	public String getvMemorySize() {
		return vMemorySize;
	}
	public void setvMemorySize(String vMemorySize) {
		this.vMemorySize = vMemorySize;
	}
	public String getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Integer getIfcount() {
		return ifcount;
	}
	public void setIfcount(Integer ifcount) {
		this.ifcount = ifcount;
	}
	public Integer getConnLower() {
		return connLower;
	}
	public void setConnLower(Integer connLower) {
		this.connLower = connLower;
	}
	public Integer getConnTopNum() {
		return connTopNum;
	}
	public void setConnTopNum(Integer connTopNum) {
		this.connTopNum = connTopNum;
	}
	public Integer getConnTopIndex() {
		return connTopIndex;
	}
	public void setConnTopIndex(Integer connTopIndex) {
		this.connTopIndex = connTopIndex;
	}
	public String getConnTopIP() {
		return connTopIP;
	}
	public void setConnTopIP(String connTopIP) {
		this.connTopIP = connTopIP;
	}
	@Override
	public String toString() {
		return "MOSafeDevice [mOID=" + mOID + ", mOName=" + mOName
				+ ", deviceIP=" + deviceIP + ", hardwareVer=" + hardwareVer
				+ ", softwareVer=" + softwareVer + ", cpucount=" + cpucount
				+ ", memorySize=" + memorySize + ", vMemorySize=" + vMemorySize
				+ ", diskSize=" + diskSize + ", lastUpdateTime="
				+ lastUpdateTime + ", ifcount=" + ifcount + ", connLower="
				+ connLower + ", connTopNum=" + connTopNum + ", connTopIndex="
				+ connTopIndex + ", connTopIP=" + connTopIP + "]";
	}
	
	
}
