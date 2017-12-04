package com.fable.insightview.platform.attendance.entity;

import java.util.Date;

/**
 * 签到计划VO
 * @author xue.antai
 *
 */
public class AttendancePlanConfVO {
	private Integer attendanceId;
	private String planName;
	private Date attStartTime;
	private Date attEndTime;
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
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
	public Integer getAttendanceId() {
		return attendanceId;
	}
	public void setAttendanceId(Integer attendanceId) {
		this.attendanceId = attendanceId;
	}
	
}
