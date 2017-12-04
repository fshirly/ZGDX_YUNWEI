package com.fable.insightview.monitor.molist.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

@Alias("sysMonitorsTypeClass")
public class SysMonitorsTypeBean {
	@NumberGenerator(name = "MonitorsTypePK")
	private Integer monitorTypeID;
	private String monitorTypeName;
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
	
}
