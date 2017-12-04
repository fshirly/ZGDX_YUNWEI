package com.fable.insightview.platform.worklog.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class WorkLog {

	@NumberGenerator(name="ID")
	private Integer id;
	private String title;
	//默认不星标    1-表示不星标   2-表示星标
	private Integer starLevel = 1;
	private Date startTime;
	private Date endTime;
	private String logContent;
	private Integer creater;
	private String createTime;
	private String taskFile;
	private String resultFile;
	
	private String createrName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public Integer getCreater() {
		return creater;
	}
	public void setCreater(Integer creater) {
		this.creater = creater;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getTaskFile() {
		return taskFile;
	}
	public void setTaskFile(String taskFile) {
		this.taskFile = taskFile;
	}
	public String getResultFile() {
		return resultFile;
	}
	public void setResultFile(String resultFile) {
		this.resultFile = resultFile;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	
}
