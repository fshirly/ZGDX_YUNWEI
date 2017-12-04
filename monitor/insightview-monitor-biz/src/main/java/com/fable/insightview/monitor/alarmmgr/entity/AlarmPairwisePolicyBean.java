package com.fable.insightview.monitor.alarmmgr.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 告警成对
 * @author hanl
 *
 */

@Alias("alarmPairwisePolicy")
public class AlarmPairwisePolicyBean {
	@NumberGenerator(name="monitorPairwisePK")
	private Integer ploicyID;
	private Integer causeAlarmDefineID;
	private Integer recoverAlarmDefineID;
	private Integer timeWindow; 
	private Integer isUsed;
	private Integer mFlag;
	
	private String causeAlarmDefineName;
	private String recoverAlarmDefineName;
	public Integer getPloicyID() {
		return ploicyID;
	}
	public void setPloicyID(Integer ploicyID) {
		this.ploicyID = ploicyID;
	}
	public Integer getCauseAlarmDefineID() {
		return causeAlarmDefineID;
	}
	public void setCauseAlarmDefineID(Integer causeAlarmDefineID) {
		this.causeAlarmDefineID = causeAlarmDefineID;
	}
	public Integer getRecoverAlarmDefineID() {
		return recoverAlarmDefineID;
	}
	public void setRecoverAlarmDefineID(Integer recoverAlarmDefineID) {
		this.recoverAlarmDefineID = recoverAlarmDefineID;
	}
	public Integer getTimeWindow() {
		return timeWindow;
	}
	public void setTimeWindow(Integer timeWindow) {
		this.timeWindow = timeWindow;
	}
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	public Integer getmFlag() {
		return mFlag;
	}
	public void setmFlag(Integer mFlag) {
		this.mFlag = mFlag;
	}
	public String getCauseAlarmDefineName() {
		return causeAlarmDefineName;
	}
	public void setCauseAlarmDefineName(String causeAlarmDefineName) {
		this.causeAlarmDefineName = causeAlarmDefineName;
	}
	public String getRecoverAlarmDefineName() {
		return recoverAlarmDefineName;
	}
	public void setRecoverAlarmDefineName(String recoverAlarmDefineName) {
		this.recoverAlarmDefineName = recoverAlarmDefineName;
	}
	
}
