package com.fable.insightview.platform.worklog.entity;

import java.util.Date;

public class WorkLogParams {

	private String title;
	private Date logStart1;
	private Date logStart2;
	private Integer starLevel;
	private Date logEnd1;
	private Date logEnd2;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getLogStart1() {
		return logStart1;
	}
	public void setLogStart1(Date logStart1) {
		this.logStart1 = logStart1;
	}
	public Date getLogStart2() {
		return logStart2;
	}
	public void setLogStart2(Date logStart2) {
		this.logStart2 = logStart2;
	}
	public Integer getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}
	public Date getLogEnd1() {
		return logEnd1;
	}
	public void setLogEnd1(Date logEnd1) {
		this.logEnd1 = logEnd1;
	}
	public Date getLogEnd2() {
		return logEnd2;
	}
	public void setLogEnd2(Date logEnd2) {
		this.logEnd2 = logEnd2;
	}
	
}
