package com.fable.insightview.monitor.alarmdispatcher.eneity;

import java.sql.Timestamp;
import java.util.Date;

public class CustomAlarmNode {
	//private String workOrderNum;
	private int alarmID;
	private String sourceMOName;
	private String sourceMOIpAdress;
	private int alarmLevel;
	private String alarmLevelName;
	private String alarmTitle;
	private int moClassID;
	private String moClassName;
	private String alarmDescription;
	private int sourceMoClassID;
 
	private Timestamp startTime;
	private int alarmStatus;
	private String statusName;
	private int alarmOperateStatus; // 1-未确认 2-已确认 3-已派发 4-人工清除
	private String operateStatusName;
	private int repeatCount;
	//自动告警派发匹配--zhangjw新增2016.1.5
	private int alarmDefineID;
	private String mOName;
	private Date lastTime;
	
	public int getAlarmDefineID() {
		return alarmDefineID;
	}

	public void setAlarmDefineID(int alarmDefineID) {
		this.alarmDefineID = alarmDefineID;
	}

	public String getmOName() {
		return mOName;
	}

	public void setmOName(String mOName) {
		this.mOName = mOName;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public int getSourceMoClassID() {
		return sourceMoClassID;
	}

	public void setSourceMoClassID(int sourceMoClassID) {
		this.sourceMoClassID = sourceMoClassID;
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

	public String getAlarmDescription() {
		return alarmDescription;
	}

	public void setAlarmDescription(String alarmDescription) {
		this.alarmDescription = alarmDescription;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}
 
	/*public String getWorkOrderNum() {
		return workOrderNum;
	}

	public void setWorkOrderNum(String workOrderNum) {
		this.workOrderNum = workOrderNum;
	}*/

	public int getAlarmID() {
		return alarmID;
	}

	public void setAlarmID(int alarmID) {
		this.alarmID = alarmID;
	}

	public String getSourceMOName() {
		return sourceMOName;
	}

	public void setSourceMOName(String sourceMOName) {
		this.sourceMOName = sourceMOName;
	}

	public String getSourceMOIpAdress() {
		return sourceMOIpAdress;
	}

	public void setSourceMOIpAdress(String sourceMOIpAdress) {
		this.sourceMOIpAdress = sourceMOIpAdress;
	}

	public int getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}

	public String getAlarmTitle() {
		return alarmTitle;
	}

	public void setAlarmTitle(String alarmTitle) {
		this.alarmTitle = alarmTitle;
	}

	public int getMoClassID() {
		return moClassID;
	}

	public void setMoClassID(int moClassID) {
		this.moClassID = moClassID;
	}

	public String getMoClassName() {
		return moClassName;
	}

	public void setMoClassName(String moClassName) {
		this.moClassName = moClassName;
	}
 
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public int getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(int alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}