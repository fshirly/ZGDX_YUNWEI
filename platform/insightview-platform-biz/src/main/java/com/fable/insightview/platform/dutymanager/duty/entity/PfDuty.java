package com.fable.insightview.platform.dutymanager.duty.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class PfDuty {

	@NumberGenerator(name = "dutyId", begin = 10000, allocationSize = 1)
	private int id;
	private Integer leaderId;
	private Date dutyDate;
	private Integer level;

	private String readys;
	private List<Map<String, Object>> orders;
	private String orderIds;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		if (leaderId < 0) {
			this.leaderId = null;
		} else {
			this.leaderId = leaderId;
		}
	}

	public Date getDutyDate() {
		return dutyDate;
	}

	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}

	public String getReadys() {
		return readys;
	}

	public void setReadys(String readys) {
		this.readys = readys;
	}

	public List<Map<String, Object>> getOrders() {
		return orders;
	}

	public void setOrders(List<Map<String, Object>> orders) {
		this.orders = orders;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
