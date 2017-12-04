package com.fable.insightview.platform.manufacturer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * 厂商实体
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ManufacturerInfo")
public class ManufacturerInfoBean extends
		com.fable.insightview.platform.itsm.core.entity.Entity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "manufacturerinfo_gen")
	@TableGenerator(initialValue=10001, name = "manufacturerinfo_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "ManufacturerInfoPK", allocationSize = 1)
	@Column(name = "ResManufacturerId")
	private Integer resManuFacturerId; // 资源厂商ID

	@Column(name = "ResManufacturerName")
	private String resManuFacturerName;// 名称

	@Column(name = "ResManufacturerAlias")
	private String resManuFacturerAlias;// 别名

	@Column(name = "Contacts")
	private String contacts; // 联系人

	@Column(name = "ContactsTelCode")
	private String contactsTelCode; // ；联系电话

	@Column(name = "Fax")
	private String fax;

	@Column(name = "TechSupportTel")
	private String techSupportTel;

	@Column(name = "Email")
	private String email;

	@Column(name = "Address")
	private String address;

	@Column(name = "URL")
	private String uRL;

	@Column(name = "LogoFileName")
	private String logoFileName; // LOGO图标

	@Column(name = "Descr ")
	private String descr; // 备注

	@Column(name = "CreateTime")
	private Date createTime; // 创建时间

	@Column(name = "LastModifyTime")
	private Date lastModifyTime;// 上次更新时间
	
	@Transient
	private String checkResManuFacturerName;

	public ManufacturerInfoBean() {
	}

	public Integer getResManuFacturerId() {
		return resManuFacturerId;
	}

	public void setResManuFacturerId(Integer resManuFacturerId) {
		this.resManuFacturerId = resManuFacturerId;
	}

	public String getResManuFacturerName() {
		return resManuFacturerName;
	}

	public void setResManuFacturerName(String resManuFacturerName) {
		this.resManuFacturerName = resManuFacturerName;
	}

	public String getResManuFacturerAlias() {
		return resManuFacturerAlias;
	}

	public void setResManuFacturerAlias(String resManuFacturerAlias) {
		this.resManuFacturerAlias = resManuFacturerAlias;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsTelCode() {
		return contactsTelCode;
	}

	public void setContactsTelCode(String contactsTelCode) {
		this.contactsTelCode = contactsTelCode;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTechSupportTel() {
		return techSupportTel;
	}

	public void setTechSupportTel(String techSupportTel) {
		this.techSupportTel = techSupportTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getuRL() {
		return uRL;
	}

	public void setuRL(String uRL) {
		this.uRL = uRL;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getCheckResManuFacturerName() {
		return checkResManuFacturerName;
	}

	public void setCheckResManuFacturerName(String checkResManuFacturerName) {
		this.checkResManuFacturerName = checkResManuFacturerName;
	}
	
	
}
