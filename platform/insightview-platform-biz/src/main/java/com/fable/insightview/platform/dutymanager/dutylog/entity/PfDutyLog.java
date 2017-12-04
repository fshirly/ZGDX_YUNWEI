package com.fable.insightview.platform.dutymanager.dutylog.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class PfDutyLog {

	@NumberGenerator(name = "DutyLogId", begin = 10000, allocationSize = 1)
	private int id;
	private int dutyId;
	private String title;
	private Date leaderRegisterDate;
	private String noticePoints;
	private String leaderLog;
	private Date dutyerRegisterDate;
	private String advices;
	private String dutyLog;

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

	public Date getLeaderRegisterDate() {
		return leaderRegisterDate;
	}

	public void setLeaderRegisterDate(Date leaderRegisterDate) {
		this.leaderRegisterDate = leaderRegisterDate;
	}

	public String getNoticePoints() {
		return noticePoints;
	}

	public void setNoticePoints(String noticePoints) {
		this.noticePoints = noticePoints;
	}

	public String getLeaderLog() {
		return leaderLog;
	}

	public void setLeaderLog(String leaderLog) {
		this.leaderLog = leaderLog;
	}

	public Date getDutyerRegisterDate() {
		return dutyerRegisterDate;
	}

	public void setDutyerRegisterDate(Date dutyerRegisterDate) {
		this.dutyerRegisterDate = dutyerRegisterDate;
	}

	public String getAdvices() {
		return advices;
	}

	public void setAdvices(String advices) {
		this.advices = advices;
	}

	public String getDutyLog() {
		return dutyLog;
	}

	public void setDutyLog(String dutyLog) {
		this.dutyLog = dutyLog;
	}

}
