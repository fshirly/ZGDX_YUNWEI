package com.fable.insightview.monitor.molist.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

@Alias("sysMonitorsTemplateClass")
public class SysMonitorsTemplateBean {
	@NumberGenerator(name = "MonitorSysMoTemplatePK")
	private Integer templateID;
	private String templateName;
	private Integer moClassID;
	private String descr;
	private String moClassLable;
	private String moList;
	private String moIntervalList;
	private String moTimeUnitList;
	public Integer getTemplateID() {
		return templateID;
	}
	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Integer getMoClassID() {
		return moClassID;
	}
	public void setMoClassID(Integer moClassID) {
		this.moClassID = moClassID;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getMoList() {
		return moList;
	}
	public void setMoList(String moList) {
		this.moList = moList;
	}
	public String getMoIntervalList() {
		return moIntervalList;
	}
	public void setMoIntervalList(String moIntervalList) {
		this.moIntervalList = moIntervalList;
	}
	public String getMoClassLable() {
		return moClassLable;
	}
	public void setMoClassLable(String moClassLable) {
		this.moClassLable = moClassLable;
	}
	public String getMoTimeUnitList() {
		return moTimeUnitList;
	}
	public void setMoTimeUnitList(String moTimeUnitList) {
		this.moTimeUnitList = moTimeUnitList;
	}
	
}
