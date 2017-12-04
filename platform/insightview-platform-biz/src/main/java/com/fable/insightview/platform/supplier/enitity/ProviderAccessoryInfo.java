package com.fable.insightview.platform.supplier.enitity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class ProviderAccessoryInfo {
	@NumberGenerator(name = "providerAccessoryId")
	private Integer providerAccessoryId;
	private Integer providerId;
	private Integer id;
	private Integer accessoryType;
	private String accessoryUrl;
	private String accessoryName;
	
	public Integer getProviderAccessoryId() {
		return providerAccessoryId;
	}
	public void setProviderAccessoryId(Integer providerAccessoryId) {
		this.providerAccessoryId = providerAccessoryId;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAccessoryType() {
		return accessoryType;
	}
	public void setAccessoryType(Integer accessoryType) {
		this.accessoryType = accessoryType;
	}
	public String getAccessoryUrl() {
		return accessoryUrl;
	}
	public void setAccessoryUrl(String accessoryUrl) {
		this.accessoryUrl = accessoryUrl;
	}
	public String getAccessoryName() {
		return accessoryName;
	}
	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}
	
}
