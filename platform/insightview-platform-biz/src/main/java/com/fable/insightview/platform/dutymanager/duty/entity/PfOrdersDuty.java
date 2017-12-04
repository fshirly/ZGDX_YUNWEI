package com.fable.insightview.platform.dutymanager.duty.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class PfOrdersDuty {

	@NumberGenerator(name = "orderOfDutyId", begin = 10000, allocationSize = 1)
	private int id;
	private int dutyId;
	private String title;
	private String beginPoint;
	private String endPoint;
	private int orderId;
	private int intervalDays;
	private Date exchangeStart;
	private Date exchangeEnd;
	private Date forceTime;
	private Integer exchangeStatus = 1;

	public PfOrdersDuty() {
		super();
	}

	public PfOrdersDuty(int dutyId, String title, String beginPoint, String endPoint, int orderId) {
		super();
		this.dutyId = dutyId;
		this.title = title;
		this.beginPoint = beginPoint;
		this.endPoint = endPoint;
		this.orderId = orderId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDutyId() {
		return dutyId;
	}

	public void setDutyId(int dutyId) {
		this.dutyId = dutyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBeginPoint() {
		return beginPoint;
	}

	public void setBeginPoint(String beginPoint) {
		this.beginPoint = beginPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getIntervalDays() {
		return intervalDays;
	}

	public void setIntervalDays(int intervalDays) {
		this.intervalDays = intervalDays;
	}

	public Date getExchangeStart() {
		return exchangeStart;
	}

	public void setExchangeStart(Date exchangeStart) {
		this.exchangeStart = exchangeStart;
	}

	public Date getExchangeEnd() {
		return exchangeEnd;
	}

	public void setExchangeEnd(Date exchangeEnd) {
		this.exchangeEnd = exchangeEnd;
	}

	public Date getForceTime() {
		return forceTime;
	}

	public void setForceTime(Date forceTime) {
		this.forceTime = forceTime;
	}

	public Integer getExchangeStatus() {
		return exchangeStatus;
	}

	public void setExchangeStatus(Integer exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}

}
