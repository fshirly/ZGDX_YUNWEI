package com.fable.insightview.platform.workplan.entity;

import java.sql.Timestamp;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class YdjwUserSync {
	
	@NumberGenerator(name = "SysUserSyncPK")
	private Integer userID;

	private String userAccount;

	private String userName;

	private String userPassword;

	private String mobilePhone;

	private String email;

	private String telephone;

	private Integer isAutoLock;

	private Integer status;

	private Integer userType;

	private Timestamp createTime;

	private Timestamp lastModifyTime;

	private Timestamp lockedTime;

	private String lockedReason;

	private int state;
	
	private String UserIcon;
	
	private String iDCard;

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getIsAutoLock() {
		return isAutoLock;
	}

	public void setIsAutoLock(Integer isAutoLock) {
		this.isAutoLock = isAutoLock;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Timestamp getLockedTime() {
		return lockedTime;
	}

	public void setLockedTime(Timestamp lockedTime) {
		this.lockedTime = lockedTime;
	}

	public String getLockedReason() {
		return lockedReason;
	}

	public void setLockedReason(String lockedReason) {
		this.lockedReason = lockedReason;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUserIcon() {
		return UserIcon;
	}

	public void setUserIcon(String userIcon) {
		UserIcon = userIcon;
	}

	public String getiDCard() {
		return iDCard;
	}

	public void setiDCard(String iDCard) {
		this.iDCard = iDCard;
	}

}
