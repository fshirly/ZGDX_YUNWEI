package com.fable.insightview.monitor.alarmmgr.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class AlarmViewColDefInfo {
	@NumberGenerator(name = "monitorAlarmViewColDefPK")
	private Integer colID;	
	private Integer colIndex;	
	private String colName;
	private String colTitle;	
	private Integer colWidth;
	private Integer isVisible;
	private Integer colValueOrder;
	public Integer getColID() {
		return colID;
	}
	public void setColID(Integer colID) {
		this.colID = colID;
	}
	public Integer getColIndex() {
		return colIndex;
	}
	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
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
	public Integer getColWidth() {
		return colWidth;
	}
	public void setColWidth(Integer colWidth) {
		this.colWidth = colWidth;
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
	
}
