package com.fable.insightview.monitor.AcUPS.entity;

public class DomainBean {
	private int DomainID;
	private String IP1;
	private String IP2;
	private int ScopeType;	//1-子网；2-ip范围
	
	public int getDomainID() {
		return DomainID;
	}
	public void setDomainID(int domainID) {
		DomainID = domainID;
	}
	public String getIP1() {
		return IP1;
	}
	public void setIP1(String iP1) {
		IP1 = iP1;
	}
	public String getIP2() {
		return IP2;
	}
	public void setIP2(String iP2) {
		IP2 = iP2;
	}
	public int getScopeType() {
		return ScopeType;
	}
	public void setScopeType(int scopeType) {
		ScopeType = scopeType;
	}
	
}