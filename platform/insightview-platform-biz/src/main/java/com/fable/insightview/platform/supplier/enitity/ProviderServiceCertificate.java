package com.fable.insightview.platform.supplier.enitity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;
import com.fable.insightview.platform.itsm.core.format.CustomerDateDeserialize;

public class ProviderServiceCertificate {
	private Integer id;
	@NumberGenerator(name = "serviceId")
	private Integer serviceId;
	private Integer providerId;
	private String serviceName;
	private String serviceFirmName;
	private String decription;
	private String certificateUrl;
	@JsonDeserialize(using = CustomerDateDeserialize.class)
	private Date serviceBeginTime;
	@JsonDeserialize(using = CustomerDateDeserialize.class)
	private Date serviceEndTime;
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
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceFirmName() {
		return serviceFirmName;
	}
	public void setServiceFirmName(String serviceFirmName) {
		this.serviceFirmName = serviceFirmName;
	}
	public String getCertificateUrl() {
		return certificateUrl;
	}
	public void setCertificateUrl(String certificateUrl) {
		this.certificateUrl = certificateUrl;
	}
	public Date getServiceBeginTime() {
		return serviceBeginTime;
	}
	public void setServiceBeginTime(Date serviceBeginTime) {
		this.serviceBeginTime = serviceBeginTime;
	}
	public Date getServiceEndTime() {
		return serviceEndTime;
	}
	public void setServiceEndTime(Date serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}
	@Override
	public String toString() {
		return "ProviderServiceCertificate [certificateUrl=" + certificateUrl
				+ ", decription=" + decription + ", description=" + description
				+ ", id=" + id + ", providerId=" + providerId
				+ ", serviceBeginTime=" + serviceBeginTime
				+ ", serviceEndTime=" + serviceEndTime + ", serviceFirmName="
				+ serviceFirmName + ", serviceId=" + serviceId
				+ ", serviceName=" + serviceName + "]";
	}
	
	
}

