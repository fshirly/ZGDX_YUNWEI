package com.fable.insightview.monitor.alarmmgr.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * @Description:   告警视图自定义
 * @author         zhengxh
 * @Date           2014-7-17
 */

@Alias("alarmView")
public class AlarmViewInfo {
	@NumberGenerator(name = "monitorAlarmViewPK")
	private Integer viewCfgID;
	private String  cfgName;
	private Integer	userID;
	private Integer userDefault;
	private String  descr;
	private Integer isSystem;
	private Integer defaultRows;
	private Integer defaultInterval;
	
	public Integer getViewCfgID() {
		return viewCfgID;
	}
	public void setViewCfgID(Integer viewCfgID) {
		this.viewCfgID = viewCfgID;
	}
	public String getCfgName() {
		return cfgName;
	}
	public void setCfgName(String cfgName) {
		this.cfgName = cfgName;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getUserDefault() {
		return userDefault;
	}
	public void setUserDefault(Integer userDefault) {
		this.userDefault = userDefault;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Integer getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
	public Integer getDefaultRows() {
		return defaultRows;
	}
	public void setDefaultRows(Integer defaultRows) {
		this.defaultRows = defaultRows;
	}
	public Integer getDefaultInterval() {
		return defaultInterval;
	}
	public void setDefaultInterval(Integer defaultInterval) {
		this.defaultInterval = defaultInterval;
	}
}
