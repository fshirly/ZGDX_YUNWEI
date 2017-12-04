package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfOrclRollbackBean {
	private Integer id;
	private Integer moid;
	private Date collecttime;
	private String segstatus;
	private Double currsize;
	private Double initialextent;
	private Double nextextent;
	private Double minextent;
	private Double maxextent;
	private Double hwmsize;
	private Double shrinks;
	private Double wraps;
	private Double extend;
	private Double increase;
	
	private String colTime;
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
	public Date getCollecttime() {
		return collecttime;
	}
	public void setCollecttime(Date collecttime) {
		this.collecttime = collecttime;
	}
	public String getSegstatus() {
		return segstatus;
	}
	public void setSegstatus(String segstatus) {
		this.segstatus = segstatus;
	}
	public Double getCurrsize() {
		return currsize;
	}
	public void setCurrsize(Double currsize) {
		this.currsize = currsize;
	}
	public Double getInitialextent() {
		return initialextent;
	}
	public void setInitialextent(Double initialextent) {
		this.initialextent = initialextent;
	}
	public Double getNextextent() {
		return nextextent;
	}
	public void setNextextent(Double nextextent) {
		this.nextextent = nextextent;
	}
	public Double getMinextent() {
		return minextent;
	}
	public void setMinextent(Double minextent) {
		this.minextent = minextent;
	}
	public Double getMaxextent() {
		return maxextent;
	}
	public void setMaxextent(Double maxextent) {
		this.maxextent = maxextent;
	}
	public Double getHwmsize() {
		return hwmsize;
	}
	public void setHwmsize(Double hwmsize) {
		this.hwmsize = hwmsize;
	}
	public Double getShrinks() {
		return shrinks;
	}
	public void setShrinks(Double shrinks) {
		this.shrinks = shrinks;
	}
	public Double getWraps() {
		return wraps;
	}
	public void setWraps(Double wraps) {
		this.wraps = wraps;
	}
	public Double getExtend() {
		return extend;
	}
	public void setExtend(Double extend) {
		this.extend = extend;
	}
	public Double getIncrease() {
		return increase;
	}
	public void setIncrease(Double increase) {
		this.increase = increase;
	}
	public String getColTime() {
		return colTime;
	}
	public void setColTime(String colTime) {
		this.colTime = colTime;
	}
	
}
