package com.fable.insightview.platform.provider.entity;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * 供应商实体
 * 
 * @author Administrator
 * 
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "ProviderInfo")
public class ProviderInfoBean extends
		com.fable.insightview.platform.itsm.core.entity.Entity implements
		Serializable {

	@Id
	@NumberGenerator(name = "ProviderInfoPK")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "providerinfo_gen")
	@TableGenerator(initialValue=10001, name = "providerinfo_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "ProviderInfoPK", allocationSize = 1)
	@Column(name = "ProviderID")
	private Integer providerId; // 供应商ID

	@Column(name = "ProviderName")
	private String providerName;// 供应商名称

	@Column(name = "Contacts")
	private String contacts; // 联系人

	@Column(name = "ContactsTelCode")
	private String contactsTelCode; // 联系电话

	@Column(name = "Fax")
	private String fax;// 传真

	@Column(name = "TechSupportTel")
	private String techSupportTel; // 技术服务电话

	@Column(name = "Email")
	private String email;

	@Column(name = "Address")
	private String address;

	@Column(name = "URL")
	private String uRL; // 网址

	@Column(name = "LogoFileName")
	private String logoFileName; // LOGO图标

	@Column(name = "Descr")
	private String descr; // 备注
	
	@Transient
	private BigDecimal bidPrice;
	
	@Transient
	private byte isWin;

	
	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	public byte getIsWin() {
		return isWin;
	}

	public void setIsWin(byte isWin) {
		this.isWin = isWin;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
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

}
