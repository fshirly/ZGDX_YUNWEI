package com.fable.insightview.platform.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.TableGenerator;
/**
  * 
  * @TABLE_NAME: SysExpert
  * @Description: 
  * @author: 郑珍
  * @Create at: Mon Jul 14 11:20:53 CST 2014
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SysExpert")
public class SysExpertBean extends com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sysExpert_gen")
	@TableGenerator(initialValue=10001, name = "sysExpert_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysExpertPK", allocationSize = 1)
	@Column(name = "ID")
	private int id;
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Gender")
	private Integer gender;
	
	@Column(name = "Birthday")
	private Date birthday;
	
	@Column(name = "IDNumber")
	private String IDNumber;
	
	@Column(name = "Affiliation")
	private String affiliation;
	
	@Column(name = "Profession")
	private String profession;
	
	@Column(name = "Academy")
	private String academy;
	
	@Column(name = "Specialty")
	private String specialty;
	
	@Column(name = "Post")
	private String post;
	
	@Column(name = "PostTitle")
	private String postTitle;
	
	@Column(name = "PoliticeStatus")
	private String politiceStatus;
	
	@Column(name = "HighestDegree")
	private Integer highestDegree;
	
	@Column(name = "Phone")
	private String phone;
	
	@Column(name = "MobileNumber")
	private String mobileNumber;
	
	@Column(name = "HomePhone")
	private String homePhone;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "ProfQualifications")
	private String profQualifications;
	
	@Column(name = "WorkExperience")
	private String workExperience;
	
	@Column(name = "ProfessionalSkill")
	private String professionalSkill;
	
	
	
	public SysExpertBean (){
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
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

	public String getPoliticeStatus() {
		return politiceStatus;
	}

	public void setPoliticeStatus(String politiceStatus) {
		this.politiceStatus = politiceStatus;
	}

	public Integer getHighestDegree() {
		return highestDegree;
	}

	public void setHighestDegree(Integer highestDegree) {
		this.highestDegree = highestDegree;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getProfQualifications() {
		return profQualifications;
	}

	public void setProfQualifications(String profQualifications) {
		this.profQualifications = profQualifications;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getProfessionalSkill() {
		return professionalSkill;
	}

	public void setProfessionalSkill(String professionalSkill) {
		this.professionalSkill = professionalSkill;
	}
	
	
}

