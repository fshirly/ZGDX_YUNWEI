package com.fable.insightview.platform.notifypolicycfg.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 通知策略
 *
 */
public class NotifyPolicyCfgBean {
	@NumberGenerator(name="pfNotifyCfgPK")
	private Integer policyId;
	private Integer policyType;
	private String policyName;
	private Integer maxTimes;
	private Integer isSms;
	private Integer isEmail;
	private Integer isPhone;
	private String descr;
	private String mFlag;
	
	private String typeName;
	private String smsArrayData;
	private String emailArrayData;
	private String phoneArrayData;
	private String userArrayData;
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	public Integer getPolicyType() {
		return policyType;
	}
	public void setPolicyType(Integer policyType) {
		this.policyType = policyType;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public Integer getMaxTimes() {
		return maxTimes;
	}
	public void setMaxTimes(Integer maxTimes) {
		this.maxTimes = maxTimes;
	}
	public Integer getIsSms() {
		return isSms;
	}
	public void setIsSms(Integer isSms) {
		this.isSms = isSms;
	}
	public Integer getIsEmail() {
		return isEmail;
	}
	public void setIsEmail(Integer isEmail) {
		this.isEmail = isEmail;
	}
	public Integer getIsPhone() {
		return isPhone;
	}
	public void setIsPhone(Integer isPhone) {
		this.isPhone = isPhone;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getSmsArrayData() {
		return smsArrayData;
	}
	public void setSmsArrayData(String smsArrayData) {
		this.smsArrayData = smsArrayData;
	}
	public String getEmailArrayData() {
		return emailArrayData;
	}
	public void setEmailArrayData(String emailArrayData) {
		this.emailArrayData = emailArrayData;
	}
	public String getPhoneArrayData() {
		return phoneArrayData;
	}
	public void setPhoneArrayData(String phoneArrayData) {
		this.phoneArrayData = phoneArrayData;
	}
	public String getUserArrayData() {
		return userArrayData;
	}
	public void setUserArrayData(String userArrayData) {
		this.userArrayData = userArrayData;
	}
	public String getmFlag() {
		return mFlag;
	}
	public void setmFlag(String mFlag) {
		this.mFlag = mFlag;
	}
}
