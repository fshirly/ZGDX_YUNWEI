package com.fable.insightview.monitor.website.entity;

import java.sql.Timestamp;
import java.util.Date;

public class SitePort {
//	@NumberGenerator(name = "MO")
	private int moID;
	private int parentMOID;
	private String ipAddr;
	private int port;
	private int portType;
	private String siteName;
	private String siteInfo;
	private int alarmLevel;
	private Timestamp createTime;
	private Timestamp lastUpdateTime;
	private Date updateAlarmTime;
	private String available;
	private String levelIcon;
	private String durationTime;
	private long responseTime;
	private String formatTime;
	private String alarmLevelName;
	private double perfAvailable;
	private String availableTip;
	private String availablePng;
	
	private Integer templateID;
	private Integer taskId;
	private Integer doIntervals; 
	private Integer defDoIntervals;
	private Date collectTime;
	
	private String moClassID;
	
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
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getPortType() {
		return portType;
	}
	public void setPortType(int portType) {
		this.portType = portType;
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
	public int getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Date getUpdateAlarmTime() {
		return updateAlarmTime;
	}
	public void setUpdateAlarmTime(Date updateAlarmTime) {
		this.updateAlarmTime = updateAlarmTime;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getLevelIcon() {
		return levelIcon;
	}
	public void setLevelIcon(String levelIcon) {
		this.levelIcon = levelIcon;
	}
	public String getDurationTime() {
		return durationTime;
	}
	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
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
	public String getAlarmLevelName() {
		return alarmLevelName;
	}
	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}
	public double getPerfAvailable() {
		return perfAvailable;
	}
	public void setPerfAvailable(double perfAvailable) {
		this.perfAvailable = perfAvailable;
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
	public Integer getTemplateID() {
		return templateID;
	}
	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
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
	public String getMoClassID() {
		return moClassID;
	}
	public void setMoClassID(String moClassID) {
		this.moClassID = moClassID;
	}
	
	
}
