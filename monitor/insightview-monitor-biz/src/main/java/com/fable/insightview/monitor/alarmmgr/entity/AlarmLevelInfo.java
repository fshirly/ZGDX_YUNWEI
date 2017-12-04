package com.fable.insightview.monitor.alarmmgr.entity;

public class AlarmLevelInfo {
	private Integer alarmLevelID;
	private Integer alarmLevelValue;
	private String  alarmLevelName;
	private String  levelColor;	
	private String levelColorName;	//等级颜色名
	//仅作为查询使用字段
	private Integer  totalNum;//		用于统计个数
	public Integer getAlarmLevelID() {
		return alarmLevelID;
	}
	public void setAlarmLevelID(Integer alarmLevelID) {
		this.alarmLevelID = alarmLevelID;
	}
	public Integer getAlarmLevelValue() {
		return alarmLevelValue;
	}
	public void setAlarmLevelValue(Integer alarmLevelValue) {
		this.alarmLevelValue = alarmLevelValue;
	}
	public String getAlarmLevelName() {
		return alarmLevelName;
	}
	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}
	public String getLevelColor() {
		return levelColor;
	}
	public void setLevelColor(String levelColor) {
		this.levelColor = levelColor;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public String getLevelColorName() {
		return levelColorName;
	}
	public void setLevelColorName(String levelColorName) {
		this.levelColorName = levelColorName;
	}
}
