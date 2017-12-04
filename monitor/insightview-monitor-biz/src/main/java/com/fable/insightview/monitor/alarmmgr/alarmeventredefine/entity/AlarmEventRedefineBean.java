package com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity;

import java.sql.Timestamp;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 告警事件重定义规则
 * 
 */
public class AlarmEventRedefineBean {
	@NumberGenerator(name = "monitorEventRedefinePK")
	private Integer ruleId;
	private String ruleName;
	private Integer isEnable;
	private String ruleDesc;
	private Integer isSame;
	private Timestamp createTime;
	
	private Integer alarmLevelId;
	private String eventData;

	private Integer moId;
	private Integer alarmDefineId;
	private String moName;
	private String deviceIp;
	private String alarmTitle;
	private Integer moClassId;
	private String choosedMoIds;
	//添加的监测对象的个数
	private Integer resCount;
	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public Integer getIsSame() {
		return isSame;
	}

	public void setIsSame(Integer isSame) {
		this.isSame = isSame;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getAlarmLevelId() {
		return alarmLevelId;
	}

	public void setAlarmLevelId(Integer alarmLevelId) {
		this.alarmLevelId = alarmLevelId;
	}

	public String getEventData() {
		return eventData;
	}

	public void setEventData(String eventData) {
		this.eventData = eventData;
	}

	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public Integer getAlarmDefineId() {
		return alarmDefineId;
	}

	public void setAlarmDefineId(Integer alarmDefineId) {
		this.alarmDefineId = alarmDefineId;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getAlarmTitle() {
		return alarmTitle;
	}

	public void setAlarmTitle(String alarmTitle) {
		this.alarmTitle = alarmTitle;
	}

	public String getChoosedMoIds() {
		return choosedMoIds;
	}

	public void setChoosedMoIds(String choosedMoIds) {
		this.choosedMoIds = choosedMoIds;
	}

	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

	public Integer getResCount() {
		return resCount;
	}

	public void setResCount(Integer resCount) {
		this.resCount = resCount;
	}
}
