package com.fable.insightview.platform.supplier.enitity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;
import com.fable.insightview.platform.itsm.core.format.CustomerDateDeserialize;

public class ProviderSoftHardwareProxy {
	@NumberGenerator(name = "proxyId")
	private Integer proxyId;
	private Integer id;
	private Integer providerId;
	private Integer productType;
	@JsonDeserialize(using = CustomerDateDeserialize.class)
	private Date proxyBeginTime;
	@JsonDeserialize(using = CustomerDateDeserialize.class)
	private Date proxyEndTime;
	private String proxyName;
	private String proxyFirmName;
	private String decription;
	private String certificateUrl;
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDecription() {
		return decription;
	}
	public void setDecription(String decription) {
		this.decription = decription;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public Integer getProxyId() {
		return proxyId;
	}
	public void setProxyId(Integer proxyId) {
		this.proxyId = proxyId;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public Date getProxyBeginTime() {
		return proxyBeginTime;
	}
	public void setProxyBeginTime(Date proxyBeginTime) {
		this.proxyBeginTime = proxyBeginTime;
	}
	public Date getProxyEndTime() {
		return proxyEndTime;
	}
	public void setProxyEndTime(Date proxyEndTime) {
		this.proxyEndTime = proxyEndTime;
	}
	public String getProxyName() {
		return proxyName;
	}
	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}
	public String getProxyFirmName() {
		return proxyFirmName;
	}
	public void setProxyFirmName(String proxyFirmName) {
		this.proxyFirmName = proxyFirmName;
	}
	public String getCertificateUrl() {
		return certificateUrl;
	}
	public void setCertificateUrl(String certificateUrl) {
		this.certificateUrl = certificateUrl;
	}
	@Override
	public String toString() {
		return "ProviderSoftHardwareProxy [certificateUrl=" + certificateUrl
				+ ", decription=" + decription + ", description=" + description
				+ ", id=" + id + ", productType=" + productType
				+ ", providerId=" + providerId + ", proxyBeginTime="
				+ proxyBeginTime + ", proxyEndTime=" + proxyEndTime
				+ ", proxyFirmName=" + proxyFirmName + ", proxyId=" + proxyId
				+ ", proxyName=" + proxyName + "]";
	}
	
	
}
