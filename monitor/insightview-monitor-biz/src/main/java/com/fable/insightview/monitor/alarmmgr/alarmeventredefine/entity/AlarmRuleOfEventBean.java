package com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 重定义规则与事件关系
 * 
 */
public class AlarmRuleOfEventBean {
	@NumberGenerator(name = "monitorAlarmRuleOfEventPK")
	private Integer id;
	private Integer ruleId;
	private Integer alarmDefineId;
	private Integer alarmLevelId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getAlarmDefineId() {
		return alarmDefineId;
	}

	public void setAlarmDefineId(Integer alarmDefineId) {
		this.alarmDefineId = alarmDefineId;
	}

	public Integer getAlarmLevelId() {
		return alarmLevelId;
	}

	public void setAlarmLevelId(Integer alarmLevelId) {
		this.alarmLevelId = alarmLevelId;
	}
}
