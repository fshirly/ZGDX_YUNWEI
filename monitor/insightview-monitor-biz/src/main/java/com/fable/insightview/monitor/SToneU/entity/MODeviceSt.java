package com.fable.insightview.monitor.SToneU.entity;

import com.fable.insightview.monitor.AcUPS.entity.SNMPbean;

public class MODeviceSt {
	
	private Integer id;
	
    private Integer MOid;

    private String moName;

    private String moalias;

    private Integer operStatus;
    
    private Integer adminStatus;

    private Integer alarmLevel;

    private Integer domainid;
    
    private Integer MOClassID;
    
	private String createTime;

    private String lastUpdateTime;

    private int createby;

    private int updateby;

    private String deviceIp;
    
    private int necollectorId;
    
    private String neversion;

    private String os; 

    private String osversion;

    private Integer snmpVersion;

    private Integer ismanage; 
   
    private String updateAlarmTime;
    private int neManufacturerID;
	private int neCategoryID;
	private String moList;
	private String moIntervalList;
	private String moTimeUnitList;
	private SNMPbean SNMPbean;
    private int taskId;
    private int sourceMOID;
    private Integer templateID;
    private String collectTime;
    private Integer doIntervals; 
	private Integer defDoIntervals;
	private String operaTip;
	private String operstatusdetail;
	private String durationTime;//持续时间
	
	private String alarmLevelName;
	private String levelIcon;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getMoalias() {
		return moalias;
	}

	public void setMoalias(String moalias) {
		this.moalias = moalias;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public Integer getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(Integer adminStatus) {
		this.adminStatus = adminStatus;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Integer getDomainid() {
		return domainid;
	}

	public void setDomainid(Integer domainid) {
		this.domainid = domainid;
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

	public int getCreateby() {
		return createby;
	}

	public void setCreateby(int createby) {
		this.createby = createby;
	}

	public int getUpdateby() {
		return updateby;
	}

	public void setUpdateby(int updateby) {
		this.updateby = updateby;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public int getNecollectorId() {
		return necollectorId;
	}

	public void setNecollectorId(int necollectorId) {
		this.necollectorId = necollectorId;
	}

	public String getNeversion() {
		return neversion;
	}

	public void setNeversion(String neversion) {
		this.neversion = neversion;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsversion() {
		return osversion;
	}

	public void setOsversion(String osversion) {
		this.osversion = osversion;
	}

	public Integer getSnmpVersion() {
		return snmpVersion;
	}

	public void setSnmpVersion(Integer snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public Integer getIsmanage() {
		return ismanage;
	}

	public void setIsmanage(Integer ismanage) {
		this.ismanage = ismanage;
	}

	public String getUpdateAlarmTime() {
		return updateAlarmTime;
	}

	public void setUpdateAlarmTime(String updateAlarmTime) {
		this.updateAlarmTime = updateAlarmTime;
	}

	public Integer getMOid() {
		return MOid;
	}

	public void setMOid(Integer mOid) {
		MOid = mOid;
	}

	public Integer getMOClassID() {
		return MOClassID;
	}

	public void setMOClassID(Integer mOClassID) {
		MOClassID = mOClassID;
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

	public SNMPbean getSNMPbean() {
		return SNMPbean;
	}

	public void setSNMPbean(SNMPbean sNMPbean) {
		SNMPbean = sNMPbean;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Integer getTemplateID() {
		return templateID;
	}

	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
	}

	public int getSourceMOID() {
		return sourceMOID;
	}

	public void setSourceMOID(int sourceMOID) {
		this.sourceMOID = sourceMOID;
	}

	public String getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
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

	public String getOperaTip() {
		return operaTip;
	}

	public void setOperaTip(String operaTip) {
		this.operaTip = operaTip;
	}

	public String getOperstatusdetail() {
		return operstatusdetail;
	}

	public void setOperstatusdetail(String operstatusdetail) {
		this.operstatusdetail = operstatusdetail;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}

	public String getLevelIcon() {
		return levelIcon;
	}

	public void setLevelIcon(String levelIcon) {
		this.levelIcon = levelIcon;
	}
	 
}