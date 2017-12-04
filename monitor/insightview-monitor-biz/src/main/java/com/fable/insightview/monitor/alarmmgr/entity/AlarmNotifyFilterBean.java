package com.fable.insightview.monitor.alarmmgr.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;


@Alias("alarmNotifyFilter")
public class AlarmNotifyFilterBean {
	@NumberGenerator(name = "monitorNotifyFilterPK")
	private Integer filterID;
	private Integer policyID;
	private String filterKey;
	private Integer filterKeyValue;
	private String filterValeName;

	public Integer getFilterID() {
		return filterID;
	}

	public void setFilterID(Integer filterID) {
		this.filterID = filterID;
	}

	public Integer getPolicyID() {
		return policyID;
	}

	public void setPolicyID(Integer policyID) {
		this.policyID = policyID;
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
