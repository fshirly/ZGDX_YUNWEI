package com.fable.insightview.platform.supplier.enitity;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;
import com.fable.insightview.platform.itsm.core.format.CustomerDateDeserialize;

public class Supplier {
	@NumberGenerator(name = "providerId")
	private Integer providerId; //供应商ID
	private String providerName; //供应商名称
	private String contactsTelCode;//联系电话
	private String contacts;//联系人
	private String proxyFirmName;//代理厂商
	private String serviceFirmName;//服务厂商
	private Integer accessoryType;//附件类型
	private String AccessoryName;//附件名称
	private String proxyName;//代理名称
	private Integer productType; //代理产品类型
	private String certificateUrl;//相关证明文件路径
	private String fax;//传真
	private String email;//邮箱地址
	private String techSupportTel;//服务电话
	@JsonDeserialize(using = CustomerDateDeserialize.class)
	private Date establishedTime;//成立时间
	private Integer employNum;//公司人数
	private BigDecimal registeredFund;//注册资金
	private String uRL;//公司网址
	private String descr;//备注
	private String attachmentArray;//基本信息文件
	private String g_attachmentArrays;//硬件代理信息
	private String gs_attachmentArrays;//服务资质信息
	private String address;//公司地址
	private String strFund;
	
	
	public String getStrFund() {
		return strFund;
	}
	public void setStrFund(String strFund) {
		this.strFund = strFund;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTechSupportTel() {
		return techSupportTel;
	}
	public void setTechSupportTel(String techSupportTel) {
		this.techSupportTel = techSupportTel;
	}
	public Date getEstablishedTime() {
		return establishedTime;
	}
	public void setEstablishedTime(Date establishedTime) {
		this.establishedTime = establishedTime;
	}
	public Integer getEmployNum() {
		return employNum;
	}
	public void setEmployNum(Integer employNum) {
		this.employNum = employNum;
	}
	public BigDecimal getRegisteredFund() {
		return registeredFund;
	}
	public void setRegisteredFund(BigDecimal registeredFund) {
		this.registeredFund = registeredFund;
	}
	public String getuRL() {
		return uRL;
	}
	public void setuRL(String uRL) {
		this.uRL = uRL;
	}
	public String getAttachmentArray() {
		return attachmentArray;
	}
	public void setAttachmentArray(String attachmentArray) {
		this.attachmentArray = attachmentArray;
	}
	public String getG_attachmentArrays() {
		return g_attachmentArrays;
	}
	public void setG_attachmentArrays(String gAttachmentArrays) {
		g_attachmentArrays = gAttachmentArrays;
	}
	public String getGs_attachmentArrays() {
		return gs_attachmentArrays;
	}
	public void setGs_attachmentArrays(String gsAttachmentArrays) {
		gs_attachmentArrays = gsAttachmentArrays;
	}
	public String getProxyName() {
		return proxyName;
	}
	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getCertificateUrl() {
		return certificateUrl;
	}
	public void setCertificateUrl(String certificateUrl) {
		this.certificateUrl = certificateUrl;
	}
	public Integer getAccessoryType() {
		return accessoryType;
	}
	public void setAccessoryType(Integer accessoryType) {
		this.accessoryType = accessoryType;
	}
	public String getAccessoryName() {
		return AccessoryName;
	}
	public void setAccessoryName(String accessoryName) {
		AccessoryName = accessoryName;
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
	public String getContactsTelCode() {
		return contactsTelCode;
	}
	public void setContactsTelCode(String contactsTelCode) {
		this.contactsTelCode = contactsTelCode;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getProxyFirmName() {
		return proxyFirmName;
	}
	public void setProxyFirmName(String proxyFirmName) {
		this.proxyFirmName = proxyFirmName;
	}
	public String getServiceFirmName() {
		return serviceFirmName;
	}
	public void setServiceFirmName(String serviceFirmName) {
		this.serviceFirmName = serviceFirmName;
	}
	@Override
	public String toString() {
		return "Supplier [AccessoryName=" + AccessoryName + ", accessoryType="
				+ accessoryType + ", address=" + address + ", attachmentArray="
				+ attachmentArray + ", certificateUrl=" + certificateUrl
				+ ", contacts=" + contacts + ", contactsTelCode="
				+ contactsTelCode + ", descr=" + descr + ", email=" + email
				+ ", employNum=" + employNum + ", establishedTime="
				+ establishedTime + ", fax=" + fax + ", g_attachmentArrays="
				+ g_attachmentArrays + ", gs_attachmentArrays="
				+ gs_attachmentArrays + ", productType=" + productType
				+ ", providerId=" + providerId + ", providerName="
				+ providerName + ", proxyFirmName=" + proxyFirmName
				+ ", proxyName=" + proxyName + ", registeredFund="
				+ registeredFund + ", serviceFirmName=" + serviceFirmName
				+ ", strFund=" + strFund + ", techSupportTel=" + techSupportTel
				+ ", uRL=" + uRL + "]";
	}
	
}
