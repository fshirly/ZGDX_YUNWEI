package com.fable.insightview.monitor.host.entity;

public class PerfSnapsBean {
	
	private  int neManufacturerID;
	
	private String resManufacturerName;
	
	private  String moClassID;
	
	private int alarmCount; // 告警总数
	
	private int countDevice; // 设备总数
	
	private int  alarmDeviceCount; //严重的告警设备数
	
	private String classlable;

	public int getNeManufacturerID() {
		return neManufacturerID;
	}

	public void setNeManufacturerID(int neManufacturerID) {
		this.neManufacturerID = neManufacturerID;
	}

	public String getResManufacturerName() {
		return resManufacturerName;
	}

	public void setResManufacturerName(String resManufacturerName) {
		this.resManufacturerName = resManufacturerName;
	}


	public String getMoClassID() {
		return moClassID;
	}

	public void setMoClassID(String moClassID) {
		this.moClassID = moClassID;
	}

	public int getAlarmCount() {
		return alarmCount;
	}

	public void setAlarmCount(int alarmCount) {
		this.alarmCount = alarmCount;
	}

	public int getCountDevice() {
		return countDevice;
	}

	public void setCountDevice(int countDevice) {
		this.countDevice = countDevice;
	}

	public String getClasslable() {
		return classlable;
	}

	public void setClasslable(String classlable) {
		this.classlable = classlable;
	}

	public int getAlarmDeviceCount() {
		return alarmDeviceCount;
	}

	public void setAlarmDeviceCount(int alarmDeviceCount) {
		this.alarmDeviceCount = alarmDeviceCount;
	}
	
}
