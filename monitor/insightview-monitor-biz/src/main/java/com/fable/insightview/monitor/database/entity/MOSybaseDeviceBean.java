package com.fable.insightview.monitor.database.entity;

import org.apache.ibatis.type.Alias;


@Alias("MOSybaseDevice")
public class MOSybaseDeviceBean {
    private Integer moId;

    private Integer serverMoId;
    
    private String ip;

    private String deviceName;

    private String physicalName;

    private Integer deviceId;

    private String deviceDescr;

    private String totalSize;

    private String usedSize;

    private String freeSize;

	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public Integer getServerMoId() {
		return serverMoId;
	}

	public void setServerMoId(Integer serverMoId) {
		this.serverMoId = serverMoId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getPhysicalName() {
		return physicalName;
	}

	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceDescr() {
		return deviceDescr;
	}

	public void setDeviceDescr(String deviceDescr) {
		this.deviceDescr = deviceDescr;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getUsedSize() {
		return usedSize;
	}

	public void setUsedSize(String usedSize) {
		this.usedSize = usedSize;
	}

	public String getFreeSize() {
		return freeSize;
	}

	public void setFreeSize(String freeSize) {
		this.freeSize = freeSize;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
	
}