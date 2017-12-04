package com.fable.insightview.monitor.SToneU.entity;

public class PerfStoneuBean {

	private String moName;
	private float temp;
	private float humidity;
	private int moClassID;
	private Integer status;
	private Integer devStatus;
	private Integer slStatus;
	private Integer linkStatus;
	private Integer consStatus;
	private Integer deviceStatus;
	
	public String getMoName() {
		return moName;
	}
	public void setMoName(String moName) {
		this.moName = moName;
	}
	public float getTemp() {
		return temp;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public float getHumidity() {
		return humidity;
	}
	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getDevStatus() {
		return devStatus;
	}
	public void setDevStatus(Integer devStatus) {
		this.devStatus = devStatus;
	}
	public Integer getSlStatus() {
		return slStatus;
	}
	public void setSlStatus(Integer slStatus) {
		this.slStatus = slStatus;
	}
	public Integer getLinkStatus() {
		return linkStatus;
	}
	public void setLinkStatus(Integer linkStatus) {
		this.linkStatus = linkStatus;
	}
	 
	public Integer getConsStatus() {
		return consStatus;
	}
	public void setConsStatus(Integer consStatus) {
		this.consStatus = consStatus;
	}
	public int getMoClassID() {
		return moClassID;
	}
	public void setMoClassID(int moClassID) {
		this.moClassID = moClassID;
	}
	public Integer getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	 
}
