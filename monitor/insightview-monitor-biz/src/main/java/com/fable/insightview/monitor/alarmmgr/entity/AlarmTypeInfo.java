package com.fable.insightview.monitor.alarmmgr.entity;

public class AlarmTypeInfo {
	private Integer alarmTypeID;
	private String  alarmTypeName;
	private Integer isSystem;
	
	//仅作为查询使用字段
	private Integer  totalNum;//		用于统计个数
	public Integer getAlarmTypeID() {
		return alarmTypeID;
	}
	public void setAlarmTypeID(Integer alarmTypeID) {
		this.alarmTypeID = alarmTypeID;
	}
	public String getAlarmTypeName() {
		return alarmTypeName;
	}
	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}
	public Integer getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
}
