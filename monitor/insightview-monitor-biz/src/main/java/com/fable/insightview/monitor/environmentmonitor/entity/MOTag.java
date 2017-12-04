package com.fable.insightview.monitor.environmentmonitor.entity;

public class MOTag {
	private Integer moID;// 监测对象ID
	private String tagID;// 标签ID
	private String tagGroupID;// 标签组ID
	private String tagType;// 标签类型
	private Integer resID;// 资源ID
	private Integer moClassId;// 对象类型
	private Integer parentMOID;// 父对象ID 
	private Integer deviceMOID;// 父对象ID
	private String ipAddress;
	//温度
	private String lowBattery;
	private double temperature;
	private double messageLossRate;
	private String sensorDisconnected;
	//水带
	private String fluidDetected;
	private String tamper;
	//温湿度
	private double dewPoint;
	private double humidity;
	//门磁感应
	private String motion;
	private String doorOpen;
	//干节点
	private String dryContactOpen;
	
	public Integer getDeviceMOID() {
		return deviceMOID;
	}

	public void setDeviceMOID(Integer deviceMOID) {
		this.deviceMOID = deviceMOID;
	}

	private String classLable;
	private String iPAddress;

	public Integer getMoID() {
		return moID;
	}

	public void setMoID(Integer moID) {
		this.moID = moID;
	}

	public String getTagID() {
		return tagID;
	}

	public void setTagID(String tagID) {
		this.tagID = tagID;
	}

	public String getTagGroupID() {
		return tagGroupID;
	}

	public void setTagGroupID(String tagGroupID) {
		this.tagGroupID = tagGroupID;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public Integer getResID() {
		return resID;
	}

	public void setResID(Integer resID) {
		this.resID = resID;
	}


	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

	public Integer getParentMOID() {
		return parentMOID;
	}

	public void setParentMOID(Integer parentMOID) {
		this.parentMOID = parentMOID;
	}
	public String getClassLable() {
		return classLable;
	}

	public void setClassLable(String classLable) {
		this.classLable = classLable;
	}

	public String getiPAddress() {
		return iPAddress;
	}

	public void setiPAddress(String iPAddress) {
		this.iPAddress = iPAddress;
	}

	public String getLowBattery() {
		return lowBattery;
	}

	public void setLowBattery(String lowBattery) {
		this.lowBattery = lowBattery;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getMessageLossRate() {
		return messageLossRate;
	}

	public void setMessageLossRate(double messageLossRate) {
		this.messageLossRate = messageLossRate;
	}

	public String getSensorDisconnected() {
		return sensorDisconnected;
	}

	public void setSensorDisconnected(String sensorDisconnected) {
		this.sensorDisconnected = sensorDisconnected;
	}

	public String getFluidDetected() {
		return fluidDetected;
	}

	public void setFluidDetected(String fluidDetected) {
		this.fluidDetected = fluidDetected;
	}

	public String getTamper() {
		return tamper;
	}

	public void setTamper(String tamper) {
		this.tamper = tamper;
	}

	public double getDewPoint() {
		return dewPoint;
	}

	public void setDewPoint(double dewPoint) {
		this.dewPoint = dewPoint;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public String getMotion() {
		return motion;
	}

	public void setMotion(String motion) {
		this.motion = motion;
	}

	public String getDoorOpen() {
		return doorOpen;
	}

	public void setDoorOpen(String doorOpen) {
		this.doorOpen = doorOpen;
	}

	public String getDryContactOpen() {
		return dryContactOpen;
	}

	public void setDryContactOpen(String dryContactOpen) {
		this.dryContactOpen = dryContactOpen;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
