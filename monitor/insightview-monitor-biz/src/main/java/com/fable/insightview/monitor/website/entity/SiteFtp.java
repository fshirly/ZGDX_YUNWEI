package com.fable.insightview.monitor.website.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class SiteFtp {
//	@NumberGenerator(name = "MO")
	private int moID;
	private int parentMOID;
	private String ipAddr;
	private int port;
	private int isAuth;
	private String siteName;
	private String siteInfo;
	private String available;
	private String levelIcon;
	
	private int alarmLevel;
	private Timestamp createTime;
	private Timestamp lastUpdateTime;
	
	private Date updateAlarmTime;
	private String durationTime;
	private double perfAvailable;
	private long responseTime;
	private String formatTime;
	
	private String userName;
	private String password;
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
	

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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

	public int getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(int isAuth) {
		this.isAuth = isAuth;
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
	
		public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
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
	
}
