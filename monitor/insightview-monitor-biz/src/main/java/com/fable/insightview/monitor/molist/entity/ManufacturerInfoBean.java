package com.fable.insightview.monitor.molist.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

@Alias("manufacturerInfoClass")
public class ManufacturerInfoBean {
	@NumberGenerator(name = "MonitorManufacturerPK")
	private Integer resManufacturerID;
	private String resManufacturerName;
	private String resManufacturerAlias;
	public Integer getResManufacturerID() {
		return resManufacturerID;
	}
	public void setResManufacturerID(Integer resManufacturerID) {
		this.resManufacturerID = resManufacturerID;
	}
	public String getResManufacturerName() {
		return resManufacturerName;
	}
	public void setResManufacturerName(String resManufacturerName) {
		this.resManufacturerName = resManufacturerName;
	}
	public String getResManufacturerAlias() {
		return resManufacturerAlias;
	}
	public void setResManufacturerAlias(String resManufacturerAlias) {
		this.resManufacturerAlias = resManufacturerAlias;
	}
	
}
