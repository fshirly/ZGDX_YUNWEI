package com.fable.insightview.monitor.alarmmgr.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class AlarmViewColCfgInfo {
	@NumberGenerator(name = "monitorAlarmViewColCfgPK")
	private Integer cfgID;
	private Integer viewCfgID;
	private Integer colID;
	private Integer  colWidth;
	private Integer  colOrder;
	private Integer isVisible;
	private Integer colValueOrder;
	//仅用于查询
	private String  colName;
	private String  colTitle;
	public Integer getCfgID() {
		return cfgID;
	}
	public void setCfgID(Integer cfgID) {
		this.cfgID = cfgID;
	}
	public Integer getViewCfgID() {
		return viewCfgID;
	}
	public void setViewCfgID(Integer viewCfgID) {
		this.viewCfgID = viewCfgID;
	}
	public Integer getColID() {
		return colID;
	}
	public void setColID(Integer colID) {
		this.colID = colID;
	}
	public Integer getColWidth() {
		return colWidth;
	}
	public void setColWidth(Integer colWidth) {
		this.colWidth = colWidth;
	}
	public Integer getColOrder() {
		return colOrder;
	}
	public void setColOrder(Integer colOrder) {
		this.colOrder = colOrder;
	}
	public Integer getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}
	public Integer getColValueOrder() {
		return colValueOrder;
	}
	public void setColValueOrder(Integer colValueOrder) {
		this.colValueOrder = colValueOrder;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getColTitle() {
		return colTitle;
	}
	public void setColTitle(String colTitle) {
		this.colTitle = colTitle;
	}
}
