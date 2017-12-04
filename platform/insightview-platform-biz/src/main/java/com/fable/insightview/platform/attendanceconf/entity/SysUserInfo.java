package com.fable.insightview.platform.attendanceconf.entity;

public class SysUserInfo {
   private 	Integer userID;
   private  String userAccount;
   private String userName;
   private String provide_Org;
   private Integer userType;
   private String userTypeName;
   private Integer id;
   private Integer attendanceId;
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
public String getProvide_Org() {
	return provide_Org;
}
public void setProvide_Org(String provideOrg) {
	provide_Org = provideOrg;
}
public Integer getUserType() {
	return userType;
}
public void setUserType(Integer userType) {
	this.userType = userType;
}
public String getUserTypeName() {
	return userTypeName;
}
public void setUserTypeName(String userTypeName) {
	this.userTypeName = userTypeName;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getAttendanceId() {
	return attendanceId;
}
public void setAttendanceId(Integer attendanceId) {
	this.attendanceId = attendanceId;
}
  
}
