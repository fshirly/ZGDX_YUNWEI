package com.fable.insightview.monitor.alarmmgr.alarmlevel.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;
/**
 * 告警等级
 * @Description:AlarmLevelBean
 * @author zhurt
 * @date 2014-7-16
 */

@Alias("levelInfo")
public class AlarmLevelBean {
	@NumberGenerator(name = "AlarmLevelID")
	private int alarmLevelID; // '告警等级编号',
	private int alarmLevelValue;	// 告警等级
	private String levelIcon;		//告警等级图标
	private String alarmLevelName;//告警等级名称
	private String levelColor; // '等级颜色',
	private String levelColorName;	//等级颜色名
	private int isSystem;// 1：系统定义 0：用户自定义'

	public AlarmLevelBean() {
	}

	public int getAlarmLevelID() {
		return alarmLevelID;
	}

	public void setAlarmLevelID(int alarmLevelID) {
		this.alarmLevelID = alarmLevelID;
	}

	public int getAlarmLevelValue() {
		return alarmLevelValue;
	}

	public void setAlarmLevelValue(int alarmLevelValue) {
		this.alarmLevelValue = alarmLevelValue;
	}

	public String getLevelIcon() {
		return levelIcon;
	}

	public void setLevelIcon(String levelIcon) {
		this.levelIcon = levelIcon;
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

	public String getLevelColorName() {
		return levelColorName;
	}

	public void setLevelColorName(String levelColorName) {
		this.levelColorName = levelColorName;
	}

	public int getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(int isSystem) {
		this.isSystem = isSystem;
	}

}
