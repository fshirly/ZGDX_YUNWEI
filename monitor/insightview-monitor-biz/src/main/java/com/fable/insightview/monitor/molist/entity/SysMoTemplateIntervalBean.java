package com.fable.insightview.monitor.molist.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

@Alias("sysMoTemplateIntervalClass")
public class SysMoTemplateIntervalBean {
	@NumberGenerator(name = "MonitorSysMoTemplateIntervalPK")
	private Integer id;
	private Integer templateID;
	private Integer monitorTypeID;
	private Integer timeInterval;
	private String moName;
	private Integer timeUnit;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTemplateID() {
		return templateID;
	}
	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
	}
	public Integer getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(Integer timeInterval) {
		this.timeInterval = timeInterval;
	}
	public String getMoName() {
		return moName;
	}
	public void setMoName(String moName) {
		this.moName = moName;
	}
	public Integer getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(Integer timeUnit) {
		this.timeUnit = timeUnit;
	}
	public Integer getMonitorTypeID() {
		return monitorTypeID;
	}
	public void setMonitorTypeID(Integer monitorTypeID) {
		this.monitorTypeID = monitorTypeID;
	}
	
}
