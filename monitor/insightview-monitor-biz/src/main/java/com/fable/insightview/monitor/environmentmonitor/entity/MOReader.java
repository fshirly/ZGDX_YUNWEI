package com.fable.insightview.monitor.environmentmonitor.entity;

public class MOReader {
	private Integer moID;// 监测对象ID
	private String readerID;// 阅读器编号
	private String readerLabel;// 阅读器名称
	private Integer port;// 端口
	private String enabled;// 是否启用
	private Integer noiseA;// A频道噪声
	private Integer noiseB;// B频道噪声
	private Integer eventRateA;// A频道事件率
	private Integer eventRateB;// B频道事件率
	private String firmwareVersion;// 固件版本
	private String iPAddress;// 阅读器 IP
	private String connectionEncrypted;// 连接是否加密
	private String readerStatus;//阅读器状态
	private Integer parentID;//父管理对象ID
	private String moname;
	private Integer moClassId;//对象类型
	private Integer resID;//资源ID
	private String deviceIP;
	private Integer id;

	public String getMoname() {
		return moname;
	}

	public void setMoname(String moname) {
		this.moname = moname;
	}

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}

	public Integer getMoID() {
		return moID;
	}

	public void setMoID(Integer moID) {
		this.moID = moID;
	}

	public String getReaderID() {
		return readerID;
	}

	public void setReaderID(String readerID) {
		this.readerID = readerID;
	}

	public String getReaderLabel() {
		return readerLabel;
	}

	public void setReaderLabel(String readerLabel) {
		this.readerLabel = readerLabel;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public Integer getNoiseA() {
		return noiseA;
	}

	public void setNoiseA(Integer noiseA) {
		this.noiseA = noiseA;
	}

	public Integer getNoiseB() {
		return noiseB;
	}

	public void setNoiseB(Integer noiseB) {
		this.noiseB = noiseB;
	}

	public Integer getEventRateA() {
		return eventRateA;
	}

	public void setEventRateA(Integer eventRateA) {
		this.eventRateA = eventRateA;
	}

	public Integer getEventRateB() {
		return eventRateB;
	}

	public void setEventRateB(Integer eventRateB) {
		this.eventRateB = eventRateB;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getiPAddress() {
		return iPAddress;
	}

	public void setiPAddress(String iPAddress) {
		this.iPAddress = iPAddress;
	}

	public String getConnectionEncrypted() {
		return connectionEncrypted;
	}

	public void setConnectionEncrypted(String connectionEncrypted) {
		this.connectionEncrypted = connectionEncrypted;
	}

	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReaderStatus() {
		return readerStatus;
	}

	public void setReaderStatus(String readerStatus) {
		this.readerStatus = readerStatus;
	} 

	public Integer getResID() {
		return resID;
	}

	public void setResID(Integer resID) {
		this.resID = resID;
	}

}
