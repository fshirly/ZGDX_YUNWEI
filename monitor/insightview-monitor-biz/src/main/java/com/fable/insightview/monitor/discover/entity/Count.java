package com.fable.insightview.monitor.discover.entity;

public class Count {
	private String deviceType; 
	private Integer count;
	private Integer moClassID;
	public Integer getMoClassID() {
		return moClassID;
	}
	public void setMoClassID(Integer moClassID) {
		this.moClassID = moClassID;
	}
	private String resCategoryIDs;
	private String neCategoryID; 
	
	public String getNeCategoryID() {
		return neCategoryID;
	}
	public void setNeCategoryID(String neCategoryID) {
		this.neCategoryID = neCategoryID;
	}
	public String getResCategoryIDs() {
		return resCategoryIDs;
	}
	public void setResCategoryIDs(String resCategoryIDs) {
		this.resCategoryIDs = resCategoryIDs;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

}
