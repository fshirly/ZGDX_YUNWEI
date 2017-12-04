package com.fable.insightview.platform.tasklog.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class PfTaskLog {

	@NumberGenerator(name = "pfTaskLogPK", begin = 10000, allocationSize = 1)
	private Integer id;
	private String title;
	private Date taskTime;
	
	// 1-为星标 2-已星标
	private Integer starLevel;
	private String logContent;
	private Date writeTime;
	private String file;
	// 1-未填写 2-已填写
	private Integer status;
	private Integer userId;

	public PfTaskLog() {
		super();
	}

	public PfTaskLog(String title, Date taskTime, Integer starLevel, Integer status, Integer userId) {
		super();
		this.title = title;
		this.taskTime = taskTime;
		this.starLevel = starLevel;
		this.status = status;
		this.userId = userId;
	}

	/*查询使用*/
	private String userName;
	
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

	public Date getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}

	public Integer getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public Date getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
