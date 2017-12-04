package com.fable.insightview.monitor.deviceresview.entity;
/**
 * 设备资源视图
 *
 */
public class DeviceResViewBean {
	private Integer moId;
	private String moName;
	private String moAlias;
	private String deviceIp;
	private Integer moClassId;
	private Integer domainId;
	private Integer resId;
	private Integer resLevel;
	private String resLevelName;
	private String classLable;
	private String domainName;
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	
	public String getMoName() {
		return moName;
	}
	public void setMoName(String moName) {
		this.moName = moName;
	}
	public String getMoAlias() {
		return moAlias;
	}
	public void setMoAlias(String moAlias) {
		this.moAlias = moAlias;
	}
	public String getDeviceIp() {
		return deviceIp;
	}
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	public Integer getMoClassId() {
		return moClassId;
	}
	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}
	public Integer getDomainId() {
		return domainId;
	}
	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}
	public Integer getResId() {
		return resId;
	}
	public void setResId(Integer resId) {
		this.resId = resId;
	}
	public Integer getResLevel() {
		return resLevel;
	}
	public void setResLevel(Integer resLevel) {
		this.resLevel = resLevel;
	}
	public String getResLevelName() {
		return resLevelName;
	}
	public void setResLevelName(String resLevelName) {
		this.resLevelName = resLevelName;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getClassLable() {
		return classLable;
	}
	public void setClassLable(String classLable) {
		this.classLable = classLable;
	}
	
}
