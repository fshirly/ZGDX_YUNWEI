package com.fable.insightview.platform.dutymanager.duty.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class PfDutyReserve {

	@NumberGenerator(name = "dutyReserveId", begin = 10000, allocationSize=1)
	private int id;
	private int dutyId;
	private int userId;

	public PfDutyReserve() {
		super();
	}

	public PfDutyReserve(int dutyId, int userId) {
		super();
		this.dutyId = dutyId;
		this.userId = userId;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
