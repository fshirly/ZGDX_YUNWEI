package com.fable.insightview.monitor.alarmmgr.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 告警定义
 * 
 * @author hanl
 * 
 */

@Alias("alarmEventDefine")
public class AlarmEventDefineBean {
	/* 告警定义编号 */
	@NumberGenerator(name = "monitorAlarmViewPK")
	private Integer alarmDefineID;

	/* 告警名称 */
	private String alarmName;

	/* 告警标题 */
	private String alarmTitle;

	/* 告警类型编号 */
	private Integer alarmTypeID;

	/* 告警级别编号 */
	private Integer alarmLevelID;

	/* 告警分类 */
	private Integer categoryID;

	/* 告警标记 */
	private String alarmOID;

	/* 告警源 */
	private Integer alarmSourceMOID;

	/* 告警描述 */
	private String description;

	/* 告警手册 */
	private String alarmManual;

	/* 是否系统定义 */
	private Integer isSystem;

	/* 过滤表达式 */
	private String filterExpression;

	private String alarmTypeName;
	private String alarmLevelName;
	private String categoryName;
	private String moName;
	private String splitAlarmOID;
	private String deviceIP;
	private String trapDeviceIP;
	private String levelColor; // '等级颜色',
	private String levelColorName;	//等级颜色名

	public Integer getAlarmDefineID() {
		return alarmDefineID;
	}

	public void setAlarmDefineID(Integer alarmDefineID) {
		this.alarmDefineID = alarmDefineID;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public String getAlarmTitle() {
		return alarmTitle;
	}

	public void setAlarmTitle(String alarmTitle) {
		this.alarmTitle = alarmTitle;
	}

	public Integer getAlarmTypeID() {
		return alarmTypeID;
	}

	public void setAlarmTypeID(Integer alarmTypeID) {
		this.alarmTypeID = alarmTypeID;
	}

	public Integer getAlarmLevelID() {
		return alarmLevelID;
	}

	public void setAlarmLevelID(Integer alarmLevelID) {
		this.alarmLevelID = alarmLevelID;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public String getAlarmOID() {
		return alarmOID;
	}

	public void setAlarmOID(String alarmOID) {
		this.alarmOID = alarmOID;
	}

	public Integer getAlarmSourceMOID() {
		return alarmSourceMOID;
	}

	public void setAlarmSourceMOID(Integer alarmSourceMOID) {
		this.alarmSourceMOID = alarmSourceMOID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlarmManual() {
		return alarmManual;
	}

	public void setAlarmManual(String alarmManual) {
		this.alarmManual = alarmManual;
	}

	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}

	public String getAlarmTypeName() {
		return alarmTypeName;
	}

	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getSplitAlarmOID() {
		return splitAlarmOID;
	}

	public void setSplitAlarmOID(String splitAlarmOID) {
		this.splitAlarmOID = splitAlarmOID;
	}

	public String getFilterExpression() {
		return filterExpression;
	}

	public void setFilterExpression(String filterExpression) {
		this.filterExpression = filterExpression;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public String getTrapDeviceIP() {
		return trapDeviceIP;
	}

	public void setTrapDeviceIP(String trapDeviceIP) {
		this.trapDeviceIP = trapDeviceIP;
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
}
