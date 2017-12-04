package com.fable.insightview.monitor.alarmmgr.entity;

import java.sql.Timestamp;


public class AlarmHistoryInfo {
	private int alarmID;
	private int alarmDefineID;
	private String alarmOID;
	private String alarmTitle;
	private int sourceMOID;
	private String sourceMOName;
	private String sourceMOIPAddress;
	private int moclassID;
	private int sourceMOClassID;
	private int moid;
	private String moName;
	private int alarmLevel;
	private int alarmType;
	private Timestamp startTime;
	private Timestamp lastTime;
	private int repeatCount;
	private int upgradeCount;
	private int alarmStatus;
	private String confirmer;
	private Timestamp confirmTime;
	private String confirmInfo;
	private String cleaner;
	private Timestamp cleanTime;
	private String cleanInfo;
	private String dispatchUser;
	private String dispatchID;
	private Timestamp dispatchTime;
	private String dispatchInfo;
	private String alarmContent;
	// 告警操作状态
	private int alarmOperateStatus;
	private String operateStatusName;
	
	//比活动告警多的两个字段
	private String deletedUser;
	private Timestamp deleteTime;
	
	//仅作为查询使用字段
	private String alarmLevelName;
	private String levelColor;
	private String alarmTypeName;
	private String statusName;
	private String timeBegin;
	private String timeEnd;
	public int getAlarmID() {
		return alarmID;
	}
	public void setAlarmID(int alarmID) {
		this.alarmID = alarmID;
	}
	public int getAlarmDefineID() {
		return alarmDefineID;
	}
	public void setAlarmDefineID(int alarmDefineID) {
		this.alarmDefineID = alarmDefineID;
	}
	public String getAlarmOID() {
		return alarmOID;
	}
	public void setAlarmOID(String alarmOID) {
		this.alarmOID = alarmOID;
	}
	public String getAlarmTitle() {
		return alarmTitle;
	}
	public void setAlarmTitle(String alarmTitle) {
		this.alarmTitle = alarmTitle;
	}
	public int getSourceMOID() {
		return sourceMOID;
	}
	public void setSourceMOID(int sourceMOID) {
		this.sourceMOID = sourceMOID;
	}
	public String getSourceMOName() {
		return sourceMOName;
	}
	public void setSourceMOName(String sourceMOName) {
		this.sourceMOName = sourceMOName;
	}
	public String getSourceMOIPAddress() {
		return sourceMOIPAddress;
	}
	public void setSourceMOIPAddress(String sourceMOIPAddress) {
		this.sourceMOIPAddress = sourceMOIPAddress;
	}
	public int getMoclassID() {
		return moclassID;
	}
	public void setMoclassID(int moclassID) {
		this.moclassID = moclassID;
	}
	public int getMoid() {
		return moid;
	}
	public void setMoid(int moid) {
		this.moid = moid;
	}
	public String getMoName() {
		return moName;
	}
	public void setMoName(String moName) {
		this.moName = moName;
	}
	public int getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public int getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getLastTime() {
		return lastTime;
	}
	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}
	public int getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}
	public int getUpgradeCount() {
		return upgradeCount;
	}
	public void setUpgradeCount(int upgradeCount) {
		this.upgradeCount = upgradeCount;
	}
	public int getAlarmStatus() {
		return alarmStatus;
	}
	public void setAlarmStatus(int alarmStatus) {
		this.alarmStatus = alarmStatus;
	}
	public String getConfirmer() {
		return confirmer;
	}
	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer;
	}
	public Timestamp getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getConfirmInfo() {
		return confirmInfo;
	}
	public void setConfirmInfo(String confirmInfo) {
		this.confirmInfo = confirmInfo;
	}
	public String getCleaner() {
		return cleaner;
	}
	public void setCleaner(String cleaner) {
		this.cleaner = cleaner;
	}
	public Timestamp getCleanTime() {
		return cleanTime;
	}
	public void setCleanTime(Timestamp cleanTime) {
		this.cleanTime = cleanTime;
	}
	public String getCleanInfo() {
		return cleanInfo;
	}
	public void setCleanInfo(String cleanInfo) {
		this.cleanInfo = cleanInfo;
	}
	public String getDispatchUser() {
		return dispatchUser;
	}
	public void setDispatchUser(String dispatchUser) {
		this.dispatchUser = dispatchUser;
	}
	public String getDispatchID() {
		return dispatchID;
	}
	public void setDispatchID(String dispatchID) {
		this.dispatchID = dispatchID;
	}
	public Timestamp getDispatchTime() {
		return dispatchTime;
	}
	public void setDispatchTime(Timestamp dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	public String getDispatchInfo() {
		return dispatchInfo;
	}
	public void setDispatchInfo(String dispatchInfo) {
		this.dispatchInfo = dispatchInfo;
	}
	public String getAlarmContent() {
		return alarmContent;
	}
	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}
	public String getDeletedUser() {
		return deletedUser;
	}
	public void setDeletedUser(String deletedUser) {
		this.deletedUser = deletedUser;
	}
	public Timestamp getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
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
	public String getAlarmTypeName() {
		return alarmTypeName;
	}
	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getTimeBegin() {
		return timeBegin;
	}
	public void setTimeBegin(String timeBegin) {
		this.timeBegin = timeBegin;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public int getSourceMOClassID() {
		return sourceMOClassID;
	}
	public void setSourceMOClassID(int sourceMOClassID) {
		this.sourceMOClassID = sourceMOClassID;
	}
	public int getAlarmOperateStatus() {
		return alarmOperateStatus;
	}
	public void setAlarmOperateStatus(int alarmOperateStatus) {
		this.alarmOperateStatus = alarmOperateStatus;
	}
	public String getOperateStatusName() {
		return operateStatusName;
	}
	public void setOperateStatusName(String operateStatusName) {
		this.operateStatusName = operateStatusName;
	}
	 
	
}
