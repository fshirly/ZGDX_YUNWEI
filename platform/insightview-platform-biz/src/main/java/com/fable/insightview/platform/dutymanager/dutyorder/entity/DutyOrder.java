package com.fable.insightview.platform.dutymanager.dutyorder.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 值班班次
 * @author chenly
 *
 */
public class DutyOrder {
	@NumberGenerator(name="ID")
	private Integer id;
	private String title;
	private String beginPoint;
	private String endPoint;
	private Integer intervalDays;
	private String exchangeStart;
	private String exchangeEnd;
	private String forceTime;
	
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
	public Integer getIntervalDays() {
		return intervalDays;
	}
	public void setIntervalDays(Integer intervalDays) {
		this.intervalDays = intervalDays;
	}
	public String getExchangeStart() {
		return exchangeStart;
	}
	public void setExchangeStart(String exchangeStart) {
		this.exchangeStart = exchangeStart;
	}
	public String getExchangeEnd() {
		return exchangeEnd;
	}
	public void setExchangeEnd(String exchangeEnd) {
		this.exchangeEnd = exchangeEnd;
	}
	public String getForceTime() {
		return forceTime;
	}
	public void setForceTime(String forceTime) {
		this.forceTime = forceTime;
	}
	
}
