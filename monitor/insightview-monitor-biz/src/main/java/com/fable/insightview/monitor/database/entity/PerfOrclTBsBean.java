package com.fable.insightview.monitor.database.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;


@Alias("PerfOrclTbsBean")
public class PerfOrclTBsBean {
    private Integer id;

    private Integer moid;

    private Date collecttime;

    private Double totalsize;

    private Double freesize;

    private Double tbusage;

    private String tbstatus;
    
    private String formatTime;

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

	public Double getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(Double totalsize) {
		this.totalsize = totalsize;
	}

	public Double getFreesize() {
		return freesize;
	}

	public void setFreesize(Double freesize) {
		this.freesize = freesize;
	}

	public Double getTbusage() {
		return tbusage;
	}

	public void setTbusage(Double tbusage) {
		this.tbusage = tbusage;
	}

	public String getTbstatus() {
		return tbstatus;
	}

	public void setTbstatus(String tbstatus) {
		this.tbstatus = tbstatus;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

    
}