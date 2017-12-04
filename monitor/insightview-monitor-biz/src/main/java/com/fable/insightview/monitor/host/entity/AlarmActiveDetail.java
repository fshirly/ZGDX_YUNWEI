package com.fable.insightview.monitor.host.entity;

import java.util.Date;

public class AlarmActiveDetail {
	private Integer alarmid;

	private Integer alarmdefineid;

	private String alarmoid;

	private String alarmtitle;

	private Integer sourcemoid;

	private String sourcemoname;

	private String sourcemoipaddress;

	private Integer moclassid;

	private Integer moid;

	private String moname;

	private Integer alarmlevel;

	private Integer alarmtype;

	private Date starttime;

	private Date lasttime;

	private Integer repeatcount;

	private Integer upgradecount;

	private Integer alarmstatus;

	private String confirmer;

	private Date confirmtime;

	private String confirminfo;

	private String cleaner;

	private Date cleantime;

	private String cleaninfo;

	private String dispatchuser;

	private String dispatchid;

	private Date dispatchtime;

	private String dispatchinfo;

	private String alarmcontent;

	private String levelicon;// 级别图标
	
	private String statusname;//告警状态
	
	private String alarmLevelName;
	

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public String getLevelicon() {
		return levelicon;
	}

	public void setLevelicon(String levelicon) {
		this.levelicon = levelicon;
	}

	public Integer getAlarmid() {
		return alarmid;
	}

	public void setAlarmid(Integer alarmid) {
		this.alarmid = alarmid;
	}

	public Integer getAlarmdefineid() {
		return alarmdefineid;
	}

	public void setAlarmdefineid(Integer alarmdefineid) {
		this.alarmdefineid = alarmdefineid;
	}

	public String getAlarmoid() {
		return alarmoid;
	}

	public void setAlarmoid(String alarmoid) {
		this.alarmoid = alarmoid == null ? null : alarmoid.trim();
	}

	public String getAlarmtitle() {
		return alarmtitle;
	}

	public void setAlarmtitle(String alarmtitle) {
		this.alarmtitle = alarmtitle == null ? null : alarmtitle.trim();
	}

	public Integer getSourcemoid() {
		return sourcemoid;
	}

	public void setSourcemoid(Integer sourcemoid) {
		this.sourcemoid = sourcemoid;
	}

	public String getSourcemoname() {
		return sourcemoname;
	}

	public void setSourcemoname(String sourcemoname) {
		this.sourcemoname = sourcemoname == null ? null : sourcemoname.trim();
	}

	public String getSourcemoipaddress() {
		return sourcemoipaddress;
	}

	public void setSourcemoipaddress(String sourcemoipaddress) {
		this.sourcemoipaddress = sourcemoipaddress == null ? null
				: sourcemoipaddress.trim();
	}

	public Integer getMoclassid() {
		return moclassid;
	}

	public void setMoclassid(Integer moclassid) {
		this.moclassid = moclassid;
	}

	public Integer getMoid() {
		return moid;
	}

	public void setMoid(Integer moid) {
		this.moid = moid;
	}

	public String getMoname() {
		return moname;
	}

	public void setMoname(String moname) {
		this.moname = moname == null ? null : moname.trim();
	}

	public Integer getAlarmlevel() {
		return alarmlevel;
	}

	public void setAlarmlevel(Integer alarmlevel) {
		this.alarmlevel = alarmlevel;
	}

	public Integer getAlarmtype() {
		return alarmtype;
	}

	public void setAlarmtype(Integer alarmtype) {
		this.alarmtype = alarmtype;
	}


	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	public Integer getRepeatcount() {
		return repeatcount;
	}

	public void setRepeatcount(Integer repeatcount) {
		this.repeatcount = repeatcount;
	}

	public Integer getUpgradecount() {
		return upgradecount;
	}

	public void setUpgradecount(Integer upgradecount) {
		this.upgradecount = upgradecount;
	}

	public Integer getAlarmstatus() {
		return alarmstatus;
	}

	public void setAlarmstatus(Integer alarmstatus) {
		this.alarmstatus = alarmstatus;
	}

	public String getConfirmer() {
		return confirmer;
	}

	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer == null ? null : confirmer.trim();
	}

	public Date getConfirmtime() {
		return confirmtime;
	}

	public void setConfirmtime(Date confirmtime) {
		this.confirmtime = confirmtime;
	}

	public String getConfirminfo() {
		return confirminfo;
	}

	public void setConfirminfo(String confirminfo) {
		this.confirminfo = confirminfo == null ? null : confirminfo.trim();
	}

	public String getCleaner() {
		return cleaner;
	}

	public void setCleaner(String cleaner) {
		this.cleaner = cleaner == null ? null : cleaner.trim();
	}

	public Date getCleantime() {
		return cleantime;
	}

	public void setCleantime(Date cleantime) {
		this.cleantime = cleantime;
	}

	public String getCleaninfo() {
		return cleaninfo;
	}

	public void setCleaninfo(String cleaninfo) {
		this.cleaninfo = cleaninfo == null ? null : cleaninfo.trim();
	}

	public String getDispatchuser() {
		return dispatchuser;
	}

	public void setDispatchuser(String dispatchuser) {
		this.dispatchuser = dispatchuser == null ? null : dispatchuser.trim();
	}

	public String getDispatchid() {
		return dispatchid;
	}

	public void setDispatchid(String dispatchid) {
		this.dispatchid = dispatchid == null ? null : dispatchid.trim();
	}

	public Date getDispatchtime() {
		return dispatchtime;
	}

	public void setDispatchtime(Date dispatchtime) {
		this.dispatchtime = dispatchtime;
	}

	public String getDispatchinfo() {
		return dispatchinfo;
	}

	public void setDispatchinfo(String dispatchinfo) {
		this.dispatchinfo = dispatchinfo == null ? null : dispatchinfo.trim();
	}

	public String getAlarmcontent() {
		return alarmcontent;
	}

	public void setAlarmcontent(String alarmcontent) {
		this.alarmcontent = alarmcontent == null ? null : alarmcontent.trim();
	}

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}
	
}