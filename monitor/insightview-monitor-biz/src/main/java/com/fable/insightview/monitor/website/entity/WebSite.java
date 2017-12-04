package com.fable.insightview.monitor.website.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class WebSite {
//	@NumberGenerator(name = "MO")
	private int moID;
	private int moClassID;
	private int domainID;
	private int siteType;  // 站点类型 1--FTP 2--DNS 3--HTTP
	private String siteName;
	private String siteInfo;
	private String available;
	private int alarmLevel;
	private String siteAddr;
	private long responseTime;
	private String responseTimeFormat;
	private String levelIcon;
	private Date updateAlarmTime;
	private String durationTime;
	
	private SiteHttp siteHttp;
	private SiteFtp siteFtp;
	private SiteDns siteDns;
	private SitePort sitePort;
	
	private String moList;
	private String moIntervalList;
	private String moTimeUnitList;
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

	public int getMoClassID() {
		return moClassID;
	}

	public void setMoClassID(int moClassID) {
		this.moClassID = moClassID;
	}

	public int getDomainID() {
		return domainID;
	}

	public void setDomainID(int domainID) {
		this.domainID = domainID;
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

	public String getSiteAddr() {
		return siteAddr;
	}

	public void setSiteAddr(String siteAddr) {
		this.siteAddr = siteAddr;
	}

	public int getSiteType() {
		return siteType;
	}

	public void setSiteType(int siteType) {
		this.siteType = siteType;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponseTimeFormat() {
		return responseTimeFormat;
	}

	public void setResponseTimeFormat(String responseTimeFormat) {
		this.responseTimeFormat = responseTimeFormat;
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

	public SiteHttp getSiteHttp() {
		return siteHttp;
	}

	public void setSiteHttp(SiteHttp siteHttp) {
		this.siteHttp = siteHttp;
	}

	public SiteFtp getSiteFtp() {
		return siteFtp;
	}

	public void setSiteFtp(SiteFtp siteFtp) {
		this.siteFtp = siteFtp;
	}

	public SiteDns getSiteDns() {
		return siteDns;
	}

	public void setSiteDns(SiteDns siteDns) {
		this.siteDns = siteDns;
	}

	public String getMoList() {
		return moList;
	}

	public void setMoList(String moList) {
		this.moList = moList;
	}

	public String getMoIntervalList() {
		return moIntervalList;
	}

	public void setMoIntervalList(String moIntervalList) {
		this.moIntervalList = moIntervalList;
	}

	public String getMoTimeUnitList() {
		return moTimeUnitList;
	}

	public void setMoTimeUnitList(String moTimeUnitList) {
		this.moTimeUnitList = moTimeUnitList;
	}

	public SitePort getSitePort() {
		return sitePort;
	}

	public void setSitePort(SitePort sitePort) {
		this.sitePort = sitePort;
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
