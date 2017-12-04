package com.fable.insightview.platform.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 
 * @TABLE_NAME: SysUserInfo
 * @Description:
 * @author: 武林
 * @Create at: Wed Dec 04 10:39:09 CST 2013
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SysUserInfo")
public class SysUserInfoBean extends com.fable.insightview.platform.itsm.core.entity.Entity {
	
	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	@NumberGenerator(name = "SysUserInfoPK")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sysuserinfo_gen")
	@TableGenerator(initialValue=10001, name = "sysuserinfo_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysUserInfoPK", allocationSize = 1)
	@Column(name = "USERID")
	private int userID;

	@Column(name = "UserAccount")
	private String userAccount;

	@Column(name = "UserName")
	private String userName;

	@Column(name = "UserPassword")
	private String userPassword;

	@Column(name = "MobilePhone")
	private String mobilePhone;

	@Column(name = "Email")
	private String email;

	@Column(name = "Telephone")
	private String telephone;

	@Column(name = "IsAutoLock")
	private int isAutoLock;

	@Column(name = "Status")
	private int status;

	@Column(name = "UserType")
	private int userType;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "LastModifyTime")
	private Timestamp lastModifyTime;

	@Column(name = "lockedTime")
	private Timestamp lockedTime;

	@Column(name = "LockedReason")
	private String lockedReason;

	@Column(name = "State")
	private int state;
	
	@Column(name = "UserIcon")
	private String UserIcon;
	
	@Column(name = "IDCard")
	private String iDCard;


	public String getIDCard() {
		return iDCard;
	}

	public void setIDCard(String iDCard) {
		this.iDCard = iDCard;
	}

	@Transient
	private String groupIdFilter;
	
	@Transient
	private String belongGroupId;
	
	
	public String getUserIcon() {
		return UserIcon;
	}

	public void setUserIcon(String userIcon) {
		UserIcon = userIcon;
	}

	public String getBelongGroupId() {
		return belongGroupId;
	}

	public void setBelongGroupId(String belongGroupId) {
		this.belongGroupId = belongGroupId;
	}

	@Transient
	private String treeType;
	
	@Transient
	private String userIds;
	
	@Transient
	private SysEmploymentBean sysEmploymentBean;
	
	@Transient
	SysProviderUserBean sysProviderUserBean;
	
	public SysProviderUserBean getSysProviderUserBean() {
		return sysProviderUserBean;
	}

	public void setSysProviderUserBean(SysProviderUserBean sysProviderUserBean) {
		this.sysProviderUserBean = sysProviderUserBean;
	}

	@Transient
	private String organizationName;
	
	@Transient
	private String deptName;
	
	@Transient
	private String gradeName;
	
	@Transient
	private int deptId;
	
	@Transient
	private String providerName;
	
	@Transient
	private int providerId;
	
	@Transient
	private int proUserId;
	
	@Transient
	private int organizationId;
	
	@Transient
	private String notifyToUserFilter;
	
	@Transient
	private String exceptUserIds;
	public SysUserInfoBean(String userAccount, String userName,
			String userPassword, String mobilePhone) {
		super();
		this.userAccount = userAccount;
		this.userName = userName;
		this.userPassword = userPassword;
		this.mobilePhone = mobilePhone;
	}

	public int getProUserId() {
		return proUserId;
	}

	public void setProUserId(int proUserId) {
		this.proUserId = proUserId;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	@Transient
	private int empId;
	
	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	@Transient
	private int gradeId;
	
	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	@Transient
	private String employeeCode;
	
	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public SysEmploymentBean getSysEmploymentBean() {
		return sysEmploymentBean;
	}

	public void setSysEmploymentBean(SysEmploymentBean sysEmploymentBean) {
		this.sysEmploymentBean = sysEmploymentBean;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public String getGroupIdFilter() {
		return groupIdFilter;
	}

	public void setGroupIdFilter(String groupIdFilter) {
		this.groupIdFilter = groupIdFilter;
	}

	public SysUserInfoBean() {
		super();
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
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

	public int getIsAutoLock() {
		return isAutoLock;
	}

	public void setIsAutoLock(int isAutoLock) {
		this.isAutoLock = isAutoLock;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
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

	public String getNotifyToUserFilter() {
		return notifyToUserFilter;
	}

	public void setNotifyToUserFilter(String notifyToUserFilter) {
		this.notifyToUserFilter = notifyToUserFilter;
	}

	public String getExceptUserIds() {
		return exceptUserIds;
	}

	public void setExceptUserIds(String exceptUserIds) {
		this.exceptUserIds = exceptUserIds;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
}
