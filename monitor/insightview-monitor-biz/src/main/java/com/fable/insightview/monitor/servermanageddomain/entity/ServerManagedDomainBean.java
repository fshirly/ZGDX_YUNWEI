package com.fable.insightview.monitor.servermanageddomain.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class ServerManagedDomainBean {
	@NumberGenerator(name = "serverManagedDomainID")
	private Integer id;
	
	private Integer serverId;
	
	private Integer domainId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public Integer getDomainId() {
		return domainId;
	}

	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}
}
