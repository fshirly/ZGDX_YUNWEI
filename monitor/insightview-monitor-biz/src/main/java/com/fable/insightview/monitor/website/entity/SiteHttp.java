package com.fable.insightview.monitor.website.entity;

import java.sql.Timestamp;
import java.util.Date;

public class SiteHttp {
//	@NumberGenerator(name = "MO")
	private int moID;
	private int parentMOID;
	private String httpUrl;
	private int requestMethod;
	private String siteName;
	private String siteInfo;
	private String available;
	private int alarmLevel;
	private String levelIcon;
	
	private Date updateAlarmTime;
	private String durationTime;
	private double perfAvailable;
	private long responseTime;
	private String formatTime;
	
	private Timestamp lastUpdateTime;
	private Timestamp createTime;
	private Integer period;

	private Integer templateID;
	private String siteAddr;
	private Integer taskId;
	private String alarmLevelName;
	private Integer doIntervals; 
	private Integer defDoIntervals;
	private Date collectTime;
	private String availableTip;
	private String availablePng;
	
	private String moClassID;
	
	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}
	public int getMoID() {
		return moID;
	}

	public void setMoID(int moID) {
		this.moID = moID;
	}

	public int getParentMOID() {
		return parentMOID;
	}

	public void setParentMOID(int parentMOID) {
		this.parentMOID = parentMOID;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public int getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(int requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteInfo() {
		return siteInfo;
	}

	public void setSiteInfo(String siteInfo) {
		this.siteInfo = siteInfo;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public int getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getLevelIcon() {
		return levelIcon;
	}

	public void setLevelIcon(String levelIcon) {
		this.levelIcon = levelIcon;
	}

	public Date getUpdateAlarmTime() {
		return updateAlarmTime;
	}

	public void setUpdateAlarmTime(Date updateAlarmTime) {
		this.updateAlarmTime = updateAlarmTime;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public double getPerfAvailable() {
		return perfAvailable;
	}

	public void setPerfAvailable(double perfAvailable) {
		this.perfAvailable = perfAvailable;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	
	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getTemplateID() {
		return templateID;
	}

	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
	}

	public String getSiteAddr() {
		return siteAddr;
	}

	public void setSiteAddr(String siteAddr) {
		this.siteAddr = siteAddr;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getDoIntervals() {
		return doIntervals;
	}

	public void setDoIntervals(Integer doIntervals) {
		this.doIntervals = doIntervals;
	}

	public Integer getDefDoIntervals() {
		return defDoIntervals;
	}

	public void setDefDoIntervals(Integer defDoIntervals) {
		this.defDoIntervals = defDoIntervals;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public String getAvailableTip() {
		return availableTip;
	}

	public void setAvailableTip(String availableTip) {
		this.availableTip = availableTip;
	}

	public String getAvailablePng() {
		return availablePng;
	}

	public void setAvailablePng(String availablePng) {
		this.availablePng = availablePng;
	}

	public String getMoClassID() {
		return moClassID;
	}

	public void setMoClassID(String moClassID) {
		this.moClassID = moClassID;
	}
	
}
