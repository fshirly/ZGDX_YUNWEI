package com.fable.insightview.platform.log.entity;

import java.io.Serializable;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class SysLog implements Serializable {
	
	//序列
	private static final long serialVersionUID = -2238633568349592958L;

	
	//主键ID
	@NumberGenerator(name="logId")
	private int id;
	
	//操作人
	private String actor;
	
	//操作模块
	private String module;
	
	//操作内容
	private String content;
	
	//操作时间
	private String optionTime;
	
	//开始时间
	private String startTime;
	
	//结束时间
	private String endTime;
	
	//操作结果
	private String result;
	
	private String operation;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOptionTime() {
		return optionTime;
	}

	public void setOptionTime(String optionTime) {
		this.optionTime = optionTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
