package com.fable.insightview.monitor.discover.entity;

import java.util.Date;

public class SafeDeviceObj {
	
	private Integer id;

	private Integer moid;

	private String deviceip;
	
	private String moname;//设备名称
//
//	private Integer restype;
//
//	private Integer resid;
//
	private String moalias;//设备别名
//
	
	private String operaTip;
	private Integer operstatus;
	private String operstatusdetail;//持续时间前的状态图标
	private Date updateAlarmTime;
	private String durationTime;// 持续时间,根据updateAlarmTime计算
	
//	private Integer adminstatus;

	private Integer alarmlevel;

	private String alarmlevelInfo;
	
	private Date createtime;//发现时间
	private String createtimeLong;//发现天数
	
	private Integer nemanufacturerid;
	private String nemanufacturername;//设备厂商
	
	private Integer moClassId;//类型
	private String necategoryname;//设备类型名称

	private Integer ismanage;//是否被管
	private String ismanageinfo;//管理域
	
	private int necollectorid;//所属采集机id
	private String iPAddress;//采集机ip
	
	private Integer domainid;
	private String domainName;//管理域
	
	private Integer doIntervals; //采集间隔
	private Integer defDoIntervals;//默认采集间隔
	private Date collectTime;//采集时间
	
	
	
	public String getCreatetimeLong() {
		return createtimeLong;
	}
	public void setCreatetimeLong(String createtimeLong) {
		this.createtimeLong = createtimeLong;
	}
	public String getOperaTip() {
		return operaTip;
	}
	public void setOperaTip(String operaTip) {
		this.operaTip = operaTip;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public Integer getDoIntervals() {
		return doIntervals;
	}
	public void setDoIntervals(Integer doIntervals) {
		this.doIntervals = doIntervals;
	}
	public Integer getDefDoIntervals() {
		return defDoIntervals;
	}
	public void setDefDoIntervals(Integer defDoIntervals) {
		this.defDoIntervals = defDoIntervals;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMoid() {
		return moid;
	}
	public void setMoid(Integer moid) {
		this.moid = moid;
	}
	public String getDeviceip() {
		return deviceip;
	}
	public void setDeviceip(String deviceip) {
		this.deviceip = deviceip;
	}
	public String getMoname() {
		return moname;
	}
	public void setMoname(String moname) {
		this.moname = moname;
	}
	public String getMoalias() {
		return moalias;
	}
	public void setMoalias(String moalias) {
		this.moalias = moalias;
	}
	public String getOperstatusdetail() {
		return operstatusdetail;
	}
	public void setOperstatusdetail(String operstatusdetail) {
		this.operstatusdetail = operstatusdetail;
	}
	public Date getUpdateAlarmTime() {
		return updateAlarmTime;
	}
	public void setUpdateAlarmTime(Date updateAlarmTime) {
		this.updateAlarmTime = updateAlarmTime;
	}
	public String getDurationTime() {
		return durationTime;
	}
	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}
	public Integer getAlarmlevel() {
		return alarmlevel;
	}
	public void setAlarmlevel(Integer alarmlevel) {
		this.alarmlevel = alarmlevel;
	}
	public String getAlarmlevelInfo() {
		return alarmlevelInfo;
	}
	public void setAlarmlevelInfo(String alarmlevelInfo) {
		this.alarmlevelInfo = alarmlevelInfo;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getNemanufacturerid() {
		return nemanufacturerid;
	}
	public void setNemanufacturerid(Integer nemanufacturerid) {
		this.nemanufacturerid = nemanufacturerid;
	}
	public String getNemanufacturername() {
		return nemanufacturername;
	}
	public void setNemanufacturername(String nemanufacturername) {
		this.nemanufacturername = nemanufacturername;
	}
	public Integer getMoClassId() {
		return moClassId;
	}
	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}
	public String getNecategoryname() {
		return necategoryname;
	}
	public void setNecategoryname(String necategoryname) {
		this.necategoryname = necategoryname;
	}
	
	public Integer getIsmanage() {
		return ismanage;
	}
	public void setIsmanage(Integer ismanage) {
		this.ismanage = ismanage;
	}
	public String getIsmanageinfo() {
		return ismanageinfo;
	}
	public void setIsmanageinfo(String ismanageinfo) {
		this.ismanageinfo = ismanageinfo;
	}
	public Integer getOperstatus() {
		return operstatus;
	}
	public void setOperstatus(Integer operstatus) {
		this.operstatus = operstatus;
	}
	public int getNecollectorid() {
		return necollectorid;
	}
	public void setNecollectorid(int necollectorid) {
		this.necollectorid = necollectorid;
	}
	public String getiPAddress() {
		return iPAddress;
	}
	public void setiPAddress(String iPAddress) {
		this.iPAddress = iPAddress;
	}
	public Integer getDomainid() {
		return domainid;
	}
	public void setDomainid(Integer domainid) {
		this.domainid = domainid;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	
}
