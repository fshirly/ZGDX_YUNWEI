package com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity;

/**
 * 告警事件定义与监测对象类型关系
 *
 */
public class AlarmEventOfMOClassBean {
	private Integer id;
	private Integer alarmDefineId;
	private Integer moClassId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAlarmDefineId() {
		return alarmDefineId;
	}

	public void setAlarmDefineId(Integer alarmDefineId) {
		this.alarmDefineId = alarmDefineId;
	}

	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

}
