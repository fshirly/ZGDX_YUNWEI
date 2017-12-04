package com.fable.insightview.platform.dutymanager.duty.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class PfUsersOrder {

	@NumberGenerator(name = "userOrderId", begin = 10000, allocationSize = 1)
	private int id;
	private int orderIdOfDuty;
	private int dutyId;
	private int userId;
	private int userType;

	public PfUsersOrder() {
		super();
	}

	public PfUsersOrder(int orderIdOfDuty, int dutyId, int userId, int userType) {
		super();
		this.orderIdOfDuty = orderIdOfDuty;
		this.dutyId = dutyId;
		this.userId = userId;
		this.userType = userType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderIdOfDuty() {
		return orderIdOfDuty;
	}

	public void setOrderIdOfDuty(int orderIdOfDuty) {
		this.orderIdOfDuty = orderIdOfDuty;
	}

	public int getDutyId() {
		return dutyId;
	}

	public void setDutyId(int dutyId) {
		this.dutyId = dutyId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
}
