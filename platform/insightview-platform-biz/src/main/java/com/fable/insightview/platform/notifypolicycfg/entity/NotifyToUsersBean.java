package com.fable.insightview.platform.notifypolicycfg.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 通知策略通知人
 *
 */
public class NotifyToUsersBean {
	@NumberGenerator(name="pfNotifyToUsersPK")
	private Integer id;
	private Integer policyId;
	private Integer userId;
	private String mobileCode;
	private String email;
	private String userName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getMobileCode() {
		return mobileCode;
	}
	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
