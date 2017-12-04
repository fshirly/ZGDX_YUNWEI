package com.fable.insightview.monitor.alarmmgr.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * @Description:   告警过滤器参数
 * @author         zhengxh
 * @Date           2014-7-17
 */
public class AlarmViewFilterInfo {
	@NumberGenerator(name = "monitorAlarmViewFltPK")
	private Integer filterID;
	private String  viewCfgID;
	private String  filterKey;
	private Integer filterKeyValue;
	//仅作为查询使用字段
	private String  filterValeName;
	public Integer getFilterID() {
		return filterID;
	}
	public void setFilterID(Integer filterID) {
		this.filterID = filterID;
	}
	public String getViewCfgID() {
		return viewCfgID;
	}
	public void setViewCfgID(String viewCfgID) {
		this.viewCfgID = viewCfgID;
	}
	public String getFilterKey() {
		return filterKey;
	}
	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}
	public Integer getFilterKeyValue() {
		return filterKeyValue;
	}
	public void setFilterKeyValue(Integer filterKeyValue) {
		this.filterKeyValue = filterKeyValue;
	}
	public String getFilterValeName() {
		return filterValeName;
	}
	public void setFilterValeName(String filterValeName) {
		this.filterValeName = filterValeName;
	}
	
}
