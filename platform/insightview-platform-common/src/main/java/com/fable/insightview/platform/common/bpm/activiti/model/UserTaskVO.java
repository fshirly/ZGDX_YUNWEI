package com.fable.insightview.platform.common.bpm.activiti.model;

import java.io.Serializable;

public class UserTaskVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8392014487181046993L;

	private String username;
	private String password;	
	private TaskNodeRS taskRS = new TaskNodeRS(); 
	
	
	
	public TaskNodeRS getTaskRS() {
		return taskRS;
	}
	public void setTaskRS(TaskNodeRS taskRS) {
		this.taskRS = taskRS;
	}
	public UserTaskVO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
