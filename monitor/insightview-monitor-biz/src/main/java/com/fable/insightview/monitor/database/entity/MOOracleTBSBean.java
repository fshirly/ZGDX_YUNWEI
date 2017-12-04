package com.fable.insightview.monitor.database.entity;

import org.apache.ibatis.type.Alias;


@Alias("moOracleTbs")
public class MOOracleTBSBean {
	private Integer moid;

	private Integer dbmoid;

	private String tbsname;

	private String initialextent;

	private Integer nextextent;

	private Integer minextents;

	private Integer maxextents;

	private Integer pctextents;

	private String minextlen;

	private String tbsstatus;

	private String tbstype;

	private String logattr;

	private String alloctype;

	private String ip;
	
	private String dbName;
	
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public Integer getMoid() {
		return moid;
	}

	public void setMoid(Integer moid) {
		this.moid = moid;
	}

	public Integer getDbmoid() {
		return dbmoid;
	}

	public void setDbmoid(Integer dbmoid) {
		this.dbmoid = dbmoid;
	}

	public String getTbsname() {
		return tbsname;
	}

	public void setTbsname(String tbsname) {
		this.tbsname = tbsname == null ? null : tbsname.trim();
	}

	public Integer getNextextent() {
		return nextextent;
	}

	public void setNextextent(Integer nextextent) {
		this.nextextent = nextextent;
	}

	public Integer getMinextents() {
		return minextents;
	}

	public void setMinextents(Integer minextents) {
		this.minextents = minextents;
	}

	public Integer getMaxextents() {
		return maxextents;
	}

	public void setMaxextents(Integer maxextents) {
		this.maxextents = maxextents;
	}

	public Integer getPctextents() {
		return pctextents;
	}

	public void setPctextents(Integer pctextents) {
		this.pctextents = pctextents;
	}

	public String getTbsstatus() {
		return tbsstatus;
	}

	public void setTbsstatus(String tbsstatus) {
		this.tbsstatus = tbsstatus == null ? null : tbsstatus.trim();
	}

	public String getTbstype() {
		return tbstype;
	}

	public void setTbstype(String tbstype) {
		this.tbstype = tbstype == null ? null : tbstype.trim();
	}

	public String getLogattr() {
		return logattr;
	}

	public void setLogattr(String logattr) {
		this.logattr = logattr == null ? null : logattr.trim();
	}

	public String getAlloctype() {
		return alloctype;
	}

	public void setAlloctype(String alloctype) {
		this.alloctype = alloctype == null ? null : alloctype.trim();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getInitialextent() {
		return initialextent;
	}

	public void setInitialextent(String initialextent) {
		this.initialextent = initialextent;
	}

	public String getMinextlen() {
		return minextlen;
	}

	public void setMinextlen(String minextlen) {
		this.minextlen = minextlen;
	}

}