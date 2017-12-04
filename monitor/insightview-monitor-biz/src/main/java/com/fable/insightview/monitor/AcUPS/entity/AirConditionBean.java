package com.fable.insightview.monitor.AcUPS.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;


public class AirConditionBean {
//	@NumberGenerator(name = "MO")
	private Integer moID;
	 private Integer Id;
	private String moName;
	private String mOAlias;

	private int pefOperStatus;

	private int adminStatus;

	private int alarmLevel;

	private int domainID;

	private String createTime;

	private String lastUpdateTime;

	private int createBy;

	private int updateBy;

	private String deviceIP;

	private int neCollectorID;

	private int neManufacturerID;

	private int neCategoryID;

	private String neVersion;

	private String oS;

	private String oSVersion;

	private int moClassID;
	
	private Integer snmpVersion;

	private int isManage;

	private String ismanageinfo;
	private int taskId;

	private String sysObjectID;
	private String updateAlarmTime;
	private String resManufacturerName;
	private String alarmlevelname;
	private String securityName;
	private String levelIcon;
	private String moList;
	private String moIntervalList;
	private String moTimeUnitList;
	private int DoIntervals;
	private int defDoIntervals;
	private String DomainName;
	private String operstatusdetail;
	private String operaTip;
	private String durationTime;//持续时间
	private String CollectTime;
	private Integer templateID;
	private SNMPbean SNMPbean;
	public Integer getMoID() {
		return moID;
	}

	public void setMoID(Integer moID) {
		this.moID = moID;
	}
	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getmOAlias() {
		return mOAlias;
	}

	public void setmOAlias(String mOAlias) {
		this.mOAlias = mOAlias;
	}

	public int getPefOperStatus() {
		return pefOperStatus;
	}

	public void setPefOperStatus(int pefOperStatus) {
		this.pefOperStatus = pefOperStatus;
	}

	public int getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(int adminStatus) {
		this.adminStatus = adminStatus;
	}

	public int getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public int getDomainID() {
		return domainID;
	}

	public void setDomainID(int domainID) {
		this.domainID = domainID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getCreateBy() {
		return createBy;
	}

	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}

	public int getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(int updateBy) {
		this.updateBy = updateBy;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public int getNeCollectorID() {
		return neCollectorID;
	}

	public void setNeCollectorID(int neCollectorID) {
		this.neCollectorID = neCollectorID;
	}

	public int getNeManufacturerID() {
		return neManufacturerID;
	}

	public void setNeManufacturerID(int neManufacturerID) {
		this.neManufacturerID = neManufacturerID;
	}

	public int getNeCategoryID() {
		return neCategoryID;
	}

	public void setNeCategoryID(int neCategoryID) {
		this.neCategoryID = neCategoryID;
	}

	public String getNeVersion() {
		return neVersion;
	}

	public void setNeVersion(String neVersion) {
		this.neVersion = neVersion;
	}

	public String getoS() {
		return oS;
	}

	public void setoS(String oS) {
		this.oS = oS;
	}

	public String getoSVersion() {
		return oSVersion;
	}

	public void setoSVersion(String oSVersion) {
		this.oSVersion = oSVersion;
	}

	public int getMoClassID() {
		return moClassID;
	}

	public void setMoClassID(int moClassID) {
		this.moClassID = moClassID;
	}

	public Integer getSnmpVersion() {
		return snmpVersion;
	}

	public void setSnmpVersion(Integer snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public int getIsManage() {
		return isManage;
	}

	public void setIsManage(int isManage) {
		this.isManage = isManage;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getSysObjectID() {
		return sysObjectID;
	}

	public void setSysObjectID(String sysObjectID) {
		this.sysObjectID = sysObjectID;
	}

	public String getUpdateAlarmTime() {
		return updateAlarmTime;
	}

	public void setUpdateAlarmTime(String updateAlarmTime) {
		this.updateAlarmTime = updateAlarmTime;
	}

	public String getResManufacturerName() {
		return resManufacturerName;
	}

	public void setResManufacturerName(String resManufacturerName) {
		this.resManufacturerName = resManufacturerName;
	}

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}

	public SNMPbean getSNMPbean() {
		return SNMPbean;
	}

	public void setSNMPbean(SNMPbean sNMPbean) {
		SNMPbean = sNMPbean;
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
 
	public int getDoIntervals() {
		return DoIntervals;
	}

	public void setDoIntervals(int doIntervals) {
		DoIntervals = doIntervals;
	}

	public int getDefDoIntervals() {
		return defDoIntervals;
	}

	public void setDefDoIntervals(int defDoIntervals) {
		this.defDoIntervals = defDoIntervals;
	}

	public String getDomainName() {
		return DomainName;
	}

	public void setDomainName(String domainName) {
		DomainName = domainName;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getIsmanageinfo() {
		return ismanageinfo;
	}

	public void setIsmanageinfo(String ismanageinfo) {
		this.ismanageinfo = ismanageinfo;
	}

	public String getOperstatusdetail() {
		return operstatusdetail;
	}

	public void setOperstatusdetail(String operstatusdetail) {
		this.operstatusdetail = operstatusdetail;
	}

	public String getOperaTip() {
		return operaTip;
	}

	public void setOperaTip(String operaTip) {
		this.operaTip = operaTip;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public String getCollectTime() {
		return CollectTime;
	}

	public void setCollectTime(String collectTime) {
		CollectTime = collectTime;
	}

	public Integer getTemplateID() {
		return templateID;
	}

	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
	}

	public String getLevelIcon() {
		return levelIcon;
	}

	public void setLevelIcon(String levelIcon) {
		this.levelIcon = levelIcon;
	}

	public String getAlarmlevelname() {
		return alarmlevelname;
	}

	public void setAlarmlevelname(String alarmlevelname) {
		this.alarmlevelname = alarmlevelname;
	}
	 
}
