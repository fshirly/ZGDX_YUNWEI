package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfSnapshotDatabaseBean {
	
	private Integer id;
	private Integer devicemoid;
	private Integer moid;
	private String kpiname;
	private String perfvalue;
	private Date collecttime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDevicemoid() {
		return devicemoid;
	}
	public void setDevicemoid(Integer devicemoid) {
		this.devicemoid = devicemoid;
	}
	public Integer getMoid() {
		return moid;
	}
	public void setMoid(Integer moid) {
		this.moid = moid;
	}
	public String getKpiname() {
		return kpiname;
	}
	public void setKpiname(String kpiname) {
		this.kpiname = kpiname;
	}
	public String getPerfvalue() {
		return perfvalue;
	}
	public void setPerfvalue(String perfvalue) {
		this.perfvalue = perfvalue;
	}
	public Date getCollecttime() {
		return collecttime;
	}
	public void setCollecttime(Date collecttime) {
		this.collecttime = collecttime;
	}
	@Override
	public String toString() {
		return "PerfSnapshotDatabaseBean [collecttime=" + collecttime
				+ ", devicemoid=" + devicemoid + ", id=" + id + ", kpiname="
				+ kpiname + ", moid=" + moid + ", perfvalue=" + perfvalue + "]";
	}
	
	
}
