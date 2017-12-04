package com.fable.insightview.platform.providerOrg.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

@Alias("sysProviderOrganization")
public class SysProviderOrganization {

	@NumberGenerator(name = "sysProviderOrganizationPK")
    private Integer id;
	
	private Integer providerId;
	
	private Integer organizationId;

	private String orgIdAttr;
	
	public SysProviderOrganization(){
		
	}
	
	public SysProviderOrganization(Integer providerId, Integer organizationId) {
		super();
		this.providerId = providerId;
		this.organizationId = organizationId;
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

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrgIdAttr() {
		return orgIdAttr;
	}

	public void setOrgIdAttr(String orgIdAttr) {
		this.orgIdAttr = orgIdAttr;
	}
	
	

	
}
