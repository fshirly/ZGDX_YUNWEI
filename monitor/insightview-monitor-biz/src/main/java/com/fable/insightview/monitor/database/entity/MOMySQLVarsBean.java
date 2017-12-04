package com.fable.insightview.monitor.database.entity;

public class MOMySQLVarsBean {
	private Integer moId;
	private String varName;
	private String varValue;
	private String varChnName;
	private String varClass;
	private String dynamicVarType;
	
	private String ip;
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public String getVarValue() {
		return varValue;
	}
	public void setVarValue(String varValue) {
		this.varValue = varValue;
	}
	public String getVarChnName() {
		return varChnName;
	}
	public void setVarChnName(String varChnName) {
		this.varChnName = varChnName;
	}
	public String getVarClass() {
		return varClass;
	}
	public void setVarClass(String varClass) {
		this.varClass = varClass;
	}
	public String getDynamicVarType() {
		return dynamicVarType;
	}
	public void setDynamicVarType(String dynamicVarType) {
		this.dynamicVarType = dynamicVarType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
