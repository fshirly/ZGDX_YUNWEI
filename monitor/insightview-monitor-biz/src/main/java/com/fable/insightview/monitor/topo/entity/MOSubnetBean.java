package com.fable.insightview.monitor.topo.entity;

public class MOSubnetBean {
	private Integer moId;
	private Integer domainId;
	private String subnetIp;
	private String subnetMask;
	private String subnetType;
	private String domainDesc;
	private Integer SubnetNum;
	
	
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public Integer getDomainId() {
		return domainId;
	}
	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}
	public String getSubnetIp() {
		return subnetIp;
	}
	public void setSubnetIp(String subnetIp) {
		this.subnetIp = subnetIp;
	}
	public String getSubnetMask() {
		return subnetMask;
	}
	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}
	public String getSubnetType() {
		return subnetType;
	}
	public void setSubnetType(String subnetType) {
		this.subnetType = subnetType;
	}
	public String getDomainDesc() {
		return domainDesc;
	}
	public void setDomainDesc(String domainDesc) {
		this.domainDesc = domainDesc;
	}
	public Integer getSubnetNum() {
		return SubnetNum;
	}
	public void setSubnetNum(Integer subnetNum) {
		SubnetNum = subnetNum;
	}	
}
