package com.fable.insightview.monitor.manageddomainipscope.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 管理域ip范围
 * 
 */
@Alias("domainIPScope")
public class ManagedDomainIPScopeBean {
	@NumberGenerator(name = "monitorDomainIPScopePK")
	private Integer scopeID;
	private Integer domainID;
	private Integer scopeType;
	private String ip1;
	private String ip2;
	private Integer status;

	private String domainName;
	private String serverName;
	private String domainDescr;
	private String node;

	public Integer getScopeID() {
		return scopeID;
	}

	public void setScopeID(Integer scopeID) {
		this.scopeID = scopeID;
	}

	public Integer getDomainID() {
		return domainID;
	}

	public void setDomainID(Integer domainID) {
		this.domainID = domainID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDomainDescr() {
		return domainDescr;
	}

	public void setDomainDescr(String domainDescr) {
		this.domainDescr = domainDescr;
	}

	public Integer getScopeType() {
		return scopeType;
	}

	public void setScopeType(Integer scopeType) {
		this.scopeType = scopeType;
	}

	public String getIp1() {
		return ip1;
	}

	public void setIp1(String ip1) {
		this.ip1 = ip1;
	}

	public String getIp2() {
		return ip2;
	}

	public void setIp2(String ip2) {
		this.ip2 = ip2;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

}
