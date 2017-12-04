package com.fable.insightview.platform.snmppen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * @ClassName:     SnmpPenInfo.java
 * @Description:   PEN维护
 * @author         郑小辉
 * @Date           2014-6-16 下午01:39:03 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SNMPPEN")
public class SnmpPenInfo extends com.fable.insightview.platform.itsm.core.entity.Entity{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "snmppen_gen")
	@TableGenerator(initialValue=10001, name = "snmppen_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "PlatformSnmpPenPK", allocationSize = 1)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "PEN")
	private Integer pen;
	
	@Column(name = "EnterpriseOID")
	private String enterpriseOID;
	
	@Column(name = "Organization")
	private String organization;
	
	@Column(name = "OrgAddress")
	private String orgAddress;
	
	@Column(name = "PostCode")
	private String postCode;
	
	@Column(name = "ContactTelephone")
	private String contactTelephone;
	
	@Column(name = "ContactPerson")
	private String contactPerson;
	
	@Column(name = "OrgEmail")
	private String orgEmail;
	
	@Column(name = "ResManufacturerID")
	private Integer resManufacturerID;
	
	@Transient
	private String resManufacturerName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPen() {
		return pen;
	}

	public void setPen(Integer pen) {
		this.pen = pen;
	}

	public String getEnterpriseOID() {
		return enterpriseOID;
	}

	public void setEnterpriseOID(String enterpriseOID) {
		this.enterpriseOID = enterpriseOID;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}	

	public Integer getResManufacturerID() {
		return resManufacturerID;
	}

	public void setResManufacturerID(Integer resManufacturerID) {
		this.resManufacturerID = resManufacturerID;
	}

	public String getResManufacturerName() {
		return resManufacturerName;
	}

	public void setResManufacturerName(String resManufacturerName) {
		this.resManufacturerName = resManufacturerName;
	}	
		
}
