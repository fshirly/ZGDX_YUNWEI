package com.fable.insightview.monitor.topo.entity;

import java.sql.Date;

public class TopoCurrentPerBean {

	private int deviceMOID ;
	private int  moid;
	private String kpiName;
	private String perfValue;
	private Date collectTime;


	public String getPerfValue() {
		return perfValue;
	}

	public void setPerfValue(String perfValue) {
		this.perfValue = perfValue;
	}

	public int getDeviceMOID() {
		return deviceMOID;
	}

	public void setDeviceMOID(int deviceMOID) {
		this.deviceMOID = deviceMOID;
	}

	public int getMoid() {
		return moid;
	}

	public void setMoid(int moid) {
		this.moid = moid;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	
}
