package com.fable.insightview.monitor.alarmmgr.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * @Description:   告警发声定制
 * @author         zhengxh
 * @Date           2014-7-22
 */
public class AlarmViewSoundCfgInfo {
	@NumberGenerator(name = "monitorAlarmViewSoundPK")
	private Integer cfgID;
	private Integer viewCfgID;
	private Integer alarmLevelID;
	private String soundFileURL;
	private Integer loopTime;
	
	//仅作为查询使用字段
	private String alarmLevelName;
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
	public Integer getAlarmLevelID() {
		return alarmLevelID;
	}
	public void setAlarmLevelID(Integer alarmLevelID) {
		this.alarmLevelID = alarmLevelID;
	}
	public String getSoundFileURL() {
		return soundFileURL;
	}
	public void setSoundFileURL(String soundFileURL) {
		this.soundFileURL = soundFileURL;
	}
	public Integer getLoopTime() {
		return loopTime;
	}
	public void setLoopTime(Integer loopTime) {
		this.loopTime = loopTime;
	}
	public String getAlarmLevelName() {
		return alarmLevelName;
	}
	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}
}
