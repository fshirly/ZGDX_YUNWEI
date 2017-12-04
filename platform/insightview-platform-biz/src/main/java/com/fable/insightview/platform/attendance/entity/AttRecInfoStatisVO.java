package com.fable.insightview.platform.attendance.entity;

import java.util.Date;

/**
 * 签到统计VO
 * @author xue.antai
 *
 */
public class AttRecInfoStatisVO {
	private String userName;
	private Date attDate;
	private Date attTime;
	private String startTime;
	private String endTime;
	private Integer uncheckInCount;
	private Integer sumCount;
	private Integer userId;
	private Integer attPlanId;
	private Integer hasUncheckedIn;
	private Date checkInStartTime;
	private Date checkInEndTime;
	private String attTimes;
	private String signTimes;
	
	public Date getAttTime() {
		return attTime;
	}
	public void setAttTime(Date attTime) {
		this.attTime = attTime;
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
	public Date getAttDate() {
		return attDate;
	}
	public void setAttDate(Date attDate) {
		this.attDate = attDate;
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
	public Integer getUncheckInCount() {
		return uncheckInCount;
	}
	public void setUncheckInCount(Integer uncheckInCount) {
		this.uncheckInCount = uncheckInCount;
	}
	public Integer getSumCount() {
		return sumCount;
	}
	public void setSumCount(Integer sumCount) {
		this.sumCount = sumCount;
	}
	public Integer getAttPlanId() {
		return attPlanId;
	}
	public void setAttPlanId(Integer attPlanId) {
		this.attPlanId = attPlanId;
	}
	public Integer getHasUncheckedIn() {
		return hasUncheckedIn;
	}
	public void setHasUncheckedIn(Integer hasUncheckedIn) {
		this.hasUncheckedIn = hasUncheckedIn;
	}
	public Date getCheckInStartTime() {
		return checkInStartTime;
	}
	public void setCheckInStartTime(Date checkInStartTime) {
		this.checkInStartTime = checkInStartTime;
	}
	public Date getCheckInEndTime() {
		return checkInEndTime;
	}
	public void setCheckInEndTime(Date checkInEndTime) {
		this.checkInEndTime = checkInEndTime;
	}
	public String getAttTimes() {
		return attTimes;
	}
	public void setAttTimes(String attTimes) {
		this.attTimes = attTimes;
	}
	public String getSignTimes() {
		return signTimes;
	}
	public void setSignTimes(String signTimes) {
		this.signTimes = signTimes;
	}
	
	
}
