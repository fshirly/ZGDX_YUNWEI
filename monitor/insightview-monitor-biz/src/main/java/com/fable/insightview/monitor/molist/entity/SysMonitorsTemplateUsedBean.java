package com.fable.insightview.monitor.molist.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

@Alias("sysMoTemplateUsedClass")
public class SysMonitorsTemplateUsedBean {
	@NumberGenerator(name = "MonitorSysMoTemplateUsedBeanPK")
	private Integer id;
	private Integer templateID;
	private Integer moClassID;
	private Integer moID;
	private String moIDs;
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
	public Integer getMoClassID() {
		return moClassID;
	}
	public void setMoClassID(Integer moClassID) {
		this.moClassID = moClassID;
	}
	public Integer getMoID() {
		return moID;
	}
	public void setMoID(Integer moID) {
		this.moID = moID;
	}
	public String getMoIDs() {
		return moIDs;
	}
	public void setMoIDs(String moIDs) {
		this.moIDs = moIDs;
	}
	
	
}
