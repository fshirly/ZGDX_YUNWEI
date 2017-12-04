package com.fable.insightview.monitor.harvester.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("sysServerHostInfo")
public class SysServerHostInfo {
	private Integer serverid;

	private String servername;

	private String ipaddress;

	private String natipadress;

	private Short servertype;

	private String serverdesc;

	private Date registertime;

	private String collectkey;

	private Integer parentId;

	private Integer domainID;

	private String domainName;

	private String serviceName;

	private Integer count;
	
	private String isOffline;
	
	private Integer installServiceId;
	private Integer id;

	public Integer getServerid() {
		return serverid;
	}

	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername == null ? null : servername.trim();
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress == null ? null : ipaddress.trim();
	}

	public String getNatipadress() {
		return natipadress;
	}

	public void setNatipadress(String natipadress) {
		this.natipadress = natipadress == null ? null : natipadress.trim();
	}

	public Short getServertype() {
		return servertype;
	}

	public void setServertype(Short servertype) {
		this.servertype = servertype;
	}

	public String getServerdesc() {
		return serverdesc;
	}

	public void setServerdesc(String serverdesc) {
		this.serverdesc = serverdesc == null ? null : serverdesc.trim();
	}

	public Date getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	public String getCollectkey() {
		return collectkey;
	}

	public void setCollectkey(String collectkey) {
		this.collectkey = collectkey == null ? null : collectkey.trim();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getDomainID() {
		return domainID;
	}

	public void setDomainID(Integer domainID) {
		this.domainID = domainID;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}

	public Integer getInstallServiceId() {
		return installServiceId;
	}

	public void setInstallServiceId(Integer installServiceId) {
		this.installServiceId = installServiceId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}