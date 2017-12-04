package com.fable.insightview.monitor.alarmmgr.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;


@Alias("alarmNotifyCfg")
public class AlarmNotifyCfgBean {
	@NumberGenerator(name="monitorNotifyCfgPK")
	private Integer policyID;
	private String policyName;
	private Integer isSms;
	private String smsAlarmContent;
	private String smsClearAlarmContent;
	private String smsDeleteAlarmContent;
	private Integer isEmail;
	private String emailAlarmContent;
	private String emailClearAlarmContent;
	private String emailDeleteAlarmContent;
	private Integer mFlag;
	private String descr;
	private Integer alarmOnCount;
	private Integer maxTimes;
	private Integer isPhone;
	private Integer voiceMessageType;
	private String phoneAlarmContent;
	private String phoneClearAlarmContent;
	private String phoneDeleteAlarmContent;
	private Integer alarmVoiceID;
	private Integer clearAlarmVoiceID;
	private Integer deleteAlarmVoiceID;
	private String alarmVoiceName;
	private String deleteAlarmVoiceName;
	private String clearAlarmVoiceName;
	private String alarmVoicePath;
	private String deleteAlarmVoicePath;
	private String clearAlarmVoicePath;
	
	public Integer getPolicyID() {
		return policyID;
	}
	public void setPolicyID(Integer policyID) {
		this.policyID = policyID;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public Integer getIsSms() {
		return isSms;
	}
	public void setIsSms(Integer isSms) {
		this.isSms = isSms;
	}
	public String getSmsAlarmContent() {
		return smsAlarmContent;
	}
	public void setSmsAlarmContent(String smsAlarmContent) {
		this.smsAlarmContent = smsAlarmContent;
	}
	public Integer getIsEmail() {
		return isEmail;
	}
	public void setIsEmail(Integer isEmail) {
		this.isEmail = isEmail;
	}
	public String getEmailAlarmContent() {
		return emailAlarmContent;
	}
	public void setEmailAlarmContent(String emailAlarmContent) {
		this.emailAlarmContent = emailAlarmContent;
	}
	
	public Integer getmFlag() {
		return mFlag;
	}
	public void setmFlag(Integer mFlag) {
		this.mFlag = mFlag;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getSmsClearAlarmContent() {
		return smsClearAlarmContent;
	}
	public void setSmsClearAlarmContent(String smsClearAlarmContent) {
		this.smsClearAlarmContent = smsClearAlarmContent;
	}
	public String getSmsDeleteAlarmContent() {
		return smsDeleteAlarmContent;
	}
	public void setSmsDeleteAlarmContent(String smsDeleteAlarmContent) {
		this.smsDeleteAlarmContent = smsDeleteAlarmContent;
	}
	public String getEmailClearAlarmContent() {
		return emailClearAlarmContent;
	}
	public void setEmailClearAlarmContent(String emailClearAlarmContent) {
		this.emailClearAlarmContent = emailClearAlarmContent;
	}
	public String getEmailDeleteAlarmContent() {
		return emailDeleteAlarmContent;
	}
	public void setEmailDeleteAlarmContent(String emailDeleteAlarmContent) {
		this.emailDeleteAlarmContent = emailDeleteAlarmContent;
	}
	public Integer getAlarmOnCount() {
		return alarmOnCount;
	}
	public void setAlarmOnCount(Integer alarmOnCount) {
		this.alarmOnCount = alarmOnCount;
	}
	public Integer getMaxTimes() {
		return maxTimes;
	}
	public void setMaxTimes(Integer maxTimes) {
		this.maxTimes = maxTimes;
	}
	public Integer getIsPhone() {
		return isPhone;
	}
	public void setIsPhone(Integer isPhone) {
		this.isPhone = isPhone;
	}
	public Integer getVoiceMessageType() {
		return voiceMessageType;
	}
	public void setVoiceMessageType(Integer voiceMessageType) {
		this.voiceMessageType = voiceMessageType;
	}
	public String getPhoneAlarmContent() {
		return phoneAlarmContent;
	}
	public void setPhoneAlarmContent(String phoneAlarmContent) {
		this.phoneAlarmContent = phoneAlarmContent;
	}
	public String getPhoneClearAlarmContent() {
		return phoneClearAlarmContent;
	}
	public void setPhoneClearAlarmContent(String phoneClearAlarmContent) {
		this.phoneClearAlarmContent = phoneClearAlarmContent;
	}
	public String getPhoneDeleteAlarmContent() {
		return phoneDeleteAlarmContent;
	}
	public void setPhoneDeleteAlarmContent(String phoneDeleteAlarmContent) {
		this.phoneDeleteAlarmContent = phoneDeleteAlarmContent;
	}
	public Integer getAlarmVoiceID() {
		return alarmVoiceID;
	}
	public void setAlarmVoiceID(Integer alarmVoiceID) {
		this.alarmVoiceID = alarmVoiceID;
	}
	public Integer getClearAlarmVoiceID() {
		return clearAlarmVoiceID;
	}
	public void setClearAlarmVoiceID(Integer clearAlarmVoiceID) {
		this.clearAlarmVoiceID = clearAlarmVoiceID;
	}
	public Integer getDeleteAlarmVoiceID() {
		return deleteAlarmVoiceID;
	}
	public void setDeleteAlarmVoiceID(Integer deleteAlarmVoiceID) {
		this.deleteAlarmVoiceID = deleteAlarmVoiceID;
	}
	public String getAlarmVoiceName() {
		return alarmVoiceName;
	}
	public void setAlarmVoiceName(String alarmVoiceName) {
		this.alarmVoiceName = alarmVoiceName;
	}
	public String getDeleteAlarmVoiceName() {
		return deleteAlarmVoiceName;
	}
	public void setDeleteAlarmVoiceName(String deleteAlarmVoiceName) {
		this.deleteAlarmVoiceName = deleteAlarmVoiceName;
	}
	public String getClearAlarmVoiceName() {
		return clearAlarmVoiceName;
	}
	public void setClearAlarmVoiceName(String clearAlarmVoiceName) {
		this.clearAlarmVoiceName = clearAlarmVoiceName;
	}
	public String getAlarmVoicePath() {
		return alarmVoicePath;
	}
	public void setAlarmVoicePath(String alarmVoicePath) {
		this.alarmVoicePath = alarmVoicePath;
	}
	public String getDeleteAlarmVoicePath() {
		return deleteAlarmVoicePath;
	}
	public void setDeleteAlarmVoicePath(String deleteAlarmVoicePath) {
		this.deleteAlarmVoicePath = deleteAlarmVoicePath;
	}
	public String getClearAlarmVoicePath() {
		return clearAlarmVoicePath;
	}
	public void setClearAlarmVoicePath(String clearAlarmVoicePath) {
		this.clearAlarmVoicePath = clearAlarmVoicePath;
	}
}
