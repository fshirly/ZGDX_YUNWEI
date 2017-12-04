package com.fable.insightview.platform.dutymanager.duty.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class DutyChangeRecord {

	@NumberGenerator(name="ID")
	private Integer id;
	private Integer dutyId;
	private String handler;
	private String fromUser;
	private String toUser;
	private Date happen;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDutyId() {
		return dutyId;
	}
	public void setDutyId(Integer dutyId) {
		this.dutyId = dutyId;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public Date getHappen() {
		return happen;
	}
	public void setHappen(Date happen) {
		this.happen = happen;
	}
	
}
