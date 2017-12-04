package com.fable.insightview.monitor.molist.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

@Alias("sysMoTypeOfMoClass")
public class SysMoTypeOfMoClassBean {
	@NumberGenerator(name = "MonitorSysMoTypeOfMoClassPK")
	private Integer id;
	private Integer moClassID;
	private Integer monitorTypeID;
	private String monitorTypeName;
	private String monitorTypeIDs;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMoClassID() {
		return moClassID;
	}
	public void setMoClassID(Integer moClassID) {
		this.moClassID = moClassID;
	}
	public Integer getMonitorTypeID() {
		return monitorTypeID;
	}
	public void setMonitorTypeID(Integer monitorTypeID) {
		this.monitorTypeID = monitorTypeID;
	}
	public String getMonitorTypeName() {
		return monitorTypeName;
	}
	public void setMonitorTypeName(String monitorTypeName) {
		this.monitorTypeName = monitorTypeName;
	}
	public String getMonitorTypeIDs() {
		return monitorTypeIDs;
	}
	public void setMonitorTypeIDs(String monitorTypeIDs) {
		this.monitorTypeIDs = monitorTypeIDs;
	}
	
	
}
