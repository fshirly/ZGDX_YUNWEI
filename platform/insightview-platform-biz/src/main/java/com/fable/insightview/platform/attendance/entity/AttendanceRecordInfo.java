package com.fable.insightview.platform.attendance.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 人员签到信息
 * @author xue.antai
 *
 */
public class AttendanceRecordInfo {
	@NumberGenerator(name="ID")
	private Integer attRecordId;
	private Integer attPeopleId;
	private Date attTime;
	private Integer attPeriodId;
	private String attSignDate;
	/************查询使用*******************/
	private Integer attPlanId;
	private String startTime;
	private String endTime;
	private Date attStartTime;
	private Date attEndTime;
	// 签到日期
	private Date attDate;
	// 签到人姓名
	private String attPeopleName;
	// 是否只显示未签到记录,1表示只显示未签到记录
	private Integer hasCheckedIn;
	// 签到开始日期
	private Date checkInStartTime;
	// 签到结束时间
	private Date checkInEndTime;
	
	public Date getAttDate() {
		return attDate;
	}
	public void setAttDate(Date attDate) {
		this.attDate = attDate;
	}
	public Date getAttTime() {
		return attTime;
	}
	public void setAttTime(Date attTime) {
		this.attTime = attTime;
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
	public Integer getAttPeriodId() {
		return attPeriodId;
	}
	public void setAttPeriodId(Integer attPeriodId) {
		this.attPeriodId = attPeriodId;
	}
	public Date getAttStartTime() {
		return attStartTime;
	}
	public void setAttStartTime(Date attStartTime) {
		this.attStartTime = attStartTime;
	}
	public Date getAttEndTime() {
		return attEndTime;
	}
	public void setAttEndTime(Date attEndTime) {
		this.attEndTime = attEndTime;
	}
	public Integer getAttRecordId() {
		return attRecordId;
	}
	public void setAttRecordId(Integer attRecordId) {
		this.attRecordId = attRecordId;
	}
	public Integer getAttPeopleId() {
		return attPeopleId;
	}
	public void setAttPeopleId(Integer attPeopleId) {
		this.attPeopleId = attPeopleId;
	}
	public Integer getAttPlanId() {
		return attPlanId;
	}
	public void setAttPlanId(Integer attPlanId) {
		this.attPlanId = attPlanId;
	}
	public String getAttPeopleName() {
		return attPeopleName;
	}
	public void setAttPeopleName(String attPeopleName) {
		this.attPeopleName = attPeopleName;
	}
	public Integer getHasCheckedIn() {
		return hasCheckedIn;
	}
	public void setHasCheckedIn(Integer hasCheckedIn) {
		this.hasCheckedIn = hasCheckedIn;
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
	public String getAttSignDate() {
		return attSignDate;
	}
	public void setAttSignDate(String attSignDate) {
		this.attSignDate = attSignDate;
	}
	
}
