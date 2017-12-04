package com.fable.insightview.monitor.molist.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

@Alias("resCategoryDefineClass")
public class ResCategoryDefineBean {
	@NumberGenerator(name = "MonitorResCategoryPK")
	private Integer resCategoryID;
	private String resCategoryName;
	private String resCategoryAlias;
	private String resCategoryDescr;
	private	Integer resManufacturerID;
	public Integer getResCategoryID() {
		return resCategoryID;
	}
	public void setResCategoryID(Integer resCategoryID) {
		this.resCategoryID = resCategoryID;
	}
	public String getResCategoryName() {
		return resCategoryName;
	}
	public void setResCategoryName(String resCategoryName) {
		this.resCategoryName = resCategoryName;
	}
	public String getResCategoryAlias() {
		return resCategoryAlias;
	}
	public void setResCategoryAlias(String resCategoryAlias) {
		this.resCategoryAlias = resCategoryAlias;
	}
	public String getResCategoryDescr() {
		return resCategoryDescr;
	}
	public void setResCategoryDescr(String resCategoryDescr) {
		this.resCategoryDescr = resCategoryDescr;
	}
	public Integer getResManufacturerID() {
		return resManufacturerID;
	}
	public void setResManufacturerID(Integer resManufacturerID) {
		this.resManufacturerID = resManufacturerID;
	}
	
}
