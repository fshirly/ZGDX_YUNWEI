package com.fable.insightview.platform.positionManagement.enitity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class PositionManagement {
	@NumberGenerator(name = "postID")
	private Integer postID;
	private String  postName;
	private Integer organizationID;
	private Integer isImportant;
	private String  postDivision;
	private Integer ID;
	private Integer userID;
	private String organizationName;
	private String employeeCode;
	private String userName;
	private String mobilePhone;
	private String email;
	private Integer postPersonalNum;
	
	public Integer getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(Integer organizationID) {
		this.organizationID = organizationID;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getPostDivision() {
		return postDivision;
	}
	public void setPostDivision(String postDivision) {
		this.postDivision = postDivision;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getPostID() {
		return postID;
	}
	public void setPostID(Integer postID) {
		this.postID = postID;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
     }
//	public Integer getOrganisationID() {
//		return organizationID;
//	}
//	public void setOrganisationID(Integer organizationID) {
//		this.organizationID = organizationID;
//	}
	public Integer getIsImportant() {
		return isImportant;
	}
	public void setIsImportant(Integer isImportant) {
		this.isImportant = isImportant;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Integer getPostPersonalNum() {
		return postPersonalNum;
	}
	public void setPostPersonalNum(Integer postPersonalNum) {
		this.postPersonalNum = postPersonalNum;
	}
}



