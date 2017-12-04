package com.fable.insightview.monitor.alarmmgr.entity;


import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;


/**
 * @Description:   活动告警bean
 * @author         郑小辉
 * @Date           2014-7-16
 */
public class AlarmActiveInfo {
	@NumberGenerator(name = "monitorAlarmActivePK")
	private Integer alarmID;
	private Integer alarmDefineID;
	private String  alarmOID;
	private String  alarmTitle;
	private Integer mOID;
	private String  mOName;
	private Integer  mOClassID;
	private Integer sourceMOID;
	private String  sourceMOName;
	private String  sourceMOIPAddress;
	private String  confirmer;
	private String  confirmInfo;
	private String  cleaner;
	private String  cleanInfo;
	private String  dispatchUser;
	private String  dispatchID;
	private String  dispatchInfo;
	private String  alarmContent;	
	private Integer alarmLevel;
	private Integer alarmType;
	private Integer repeatCount;
	private Integer upgradeCount;
	private Integer alarmStatus;	
	private String startTime;
	private String lastTime;
	private String confirmTime;
	private String cleanTime;
	private String dispatchTime;
	
	//仅作为查询使用字段
	private String alarmLevelName;
	private String levelColor;
	private String alarmTypeName;
	private String statusName;
	private String timeBegin;
	private String timeEnd;
	private Integer viewPic;
	public Integer getAlarmID() {
		return alarmID;
	}
	public void setAlarmID(Integer alarmID) {
		this.alarmID = alarmID;
	}
	public Integer getAlarmDefineID() {
		return alarmDefineID;
	}
	public void setAlarmDefineID(Integer alarmDefineID) {
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
	public Integer getmOID() {
		return mOID;
	}
	public void setmOID(Integer mOID) {
		this.mOID = mOID;
	}
	public String getmOName() {
		return mOName;
	}
	public void setmOName(String mOName) {
		this.mOName = mOName;
	}
	public Integer getSourceMOID() {
		return sourceMOID;
	}
	public void setSourceMOID(Integer sourceMOID) {
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
	public String getConfirmer() {
		return confirmer;
	}
	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer;
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
	public Integer getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public Integer getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
	public Integer getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}
	public Integer getUpgradeCount() {
		return upgradeCount;
	}
	public void setUpgradeCount(Integer upgradeCount) {
		this.upgradeCount = upgradeCount;
	}
	public Integer getAlarmStatus() {
		return alarmStatus;
	}
	public void setAlarmStatus(Integer alarmStatus) {
		this.alarmStatus = alarmStatus;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getCleanTime() {
		return cleanTime;
	}
	public void setCleanTime(String cleanTime) {
		this.cleanTime = cleanTime;
	}
	public String getDispatchTime() {
		return dispatchTime;
	}
	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	public String getAlarmLevelName() {
		return alarmLevelName;
	}
	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
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
	public String getLevelColor() {
		return levelColor;
	}
	public void setLevelColor(String levelColor) {
		this.levelColor = levelColor;
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
	public Integer getmOClassID() {
		return mOClassID;
	}
	public void setmOClassID(Integer mOClassID) {
		this.mOClassID = mOClassID;
	}
	public Integer getViewPic() {
		return viewPic;
	}
	public void setViewPic(Integer viewPic) {
		this.viewPic = viewPic;
	}
}
