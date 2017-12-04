package com.fable.insightview.platform.mybatis.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * @author Li jiuwei
 * @date 2015年4月2日 下午4:44:05
 */
@Alias("STUserResume")
public class STUserResume {
	@NumberGenerator(name = "STUserResumePK")
	private Integer resumeID;
	private Integer userID;
	private Integer gender;
	private String nation;
	private String nativePlace;
	private String IDNumber;
	private Date birthday;
	private String education;
	private String major;
	private String post;
	private String postTitle;
	private String politicsStatus;
	private Date tenure;
	private Date duringTime;
	private Date beOnTheJob;
	private Date engageTime;
	private String politicalInfoOne;
	private String politicalInfoTwo;
	private String politicalInfoThree;
	private String professionInfo;
	
	private String userIcon;
	private String userName;
	private String mobilePhone;
	private String email;
	
	private String userIconURL;
	
	public Integer getResumeID() {
		return resumeID;
	}
	public void setResumeID(Integer resumeID) {
		this.resumeID = resumeID;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getIDNumber() {
		return IDNumber;
	}
	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}
	public Date getBirthday() {
		return getDate(birthday);
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPoliticsStatus() {
		return politicsStatus;
	}
	public void setPoliticsStatus(String politicsStatus) {
		this.politicsStatus = politicsStatus;
	}
	public Date getTenure() {
		return getDate(tenure);
	}
	public void setTenure(Date tenure) {
		this.tenure = tenure;
	}
	public Date getDuringTime() {
		return getDate(duringTime);
	}
	public void setDuringTime(Date duringTime) {
		this.duringTime = duringTime;
	}
	public Date getBeOnTheJob() {
		return getDate(beOnTheJob);
	}
	public void setBeOnTheJob(Date beOnTheJob) {
		this.beOnTheJob = beOnTheJob;
	}
	public Date getEngageTime() {
		return getDate(engageTime);
	}
	public void setEngageTime(Date engageTime) {
		this.engageTime = engageTime;
	}
	public String getPoliticalInfoOne() {
		return politicalInfoOne;
	}
	public void setPoliticalInfoOne(String politicalInfoOne) {
		this.politicalInfoOne = politicalInfoOne;
	}
	public String getPoliticalInfoTwo() {
		return politicalInfoTwo;
	}
	public void setPoliticalInfoTwo(String politicalInfoTwo) {
		this.politicalInfoTwo = politicalInfoTwo;
	}
	public String getPoliticalInfoThree() {
		return politicalInfoThree;
	}
	public void setPoliticalInfoThree(String politicalInfoThree) {
		this.politicalInfoThree = politicalInfoThree;
	}
	public String getProfessionInfo() {
		return professionInfo;
	}
	public void setProfessionInfo(String professionInfo) {
		this.professionInfo = professionInfo;
	}
	public String getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	
	private Date getDate (Date date) {
		return date == null ? null : new YYYYMMDDDate(date);
	}
	public String getUserIconURL() {
		return userIconURL;
	}
	public void setUserIconURL(String userIconURL) {
		this.userIconURL = userIconURL;
	}
	
}
