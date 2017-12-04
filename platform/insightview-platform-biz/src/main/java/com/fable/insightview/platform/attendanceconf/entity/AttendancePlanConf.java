package com.fable.insightview.platform.attendanceconf.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class AttendancePlanConf {
  @NumberGenerator(name="AttendanceId")
  private Integer attendanceId;
  private String  title;
  private Date attStartTime;
  private Date attEndTime;
  private Integer planner;
  private Integer status;
  private Date launchTime;
  private Date stopTime;
  private Date configTime;
  /*
   * 额外参数
   */
  private Date attStartTime_start;
  private Date attStartTime_end;
  private Date attEndTime_start;
  private Date attEndTime_end;
  private Integer attcount;
  private String plantimes;
  private String statename;
  private String plannerName;
	public Integer getAttendanceId() {
		return attendanceId;
	}
	public void setAttendanceId(Integer attendanceId) {
		this.attendanceId = attendanceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Integer getPlanner() {
		return planner;
	}
	public void setPlanner(Integer planner) {
		this.planner = planner;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getLaunchTime() {
		return launchTime;
	}
	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}
	public Date getStopTime() {
		return stopTime;
	}
	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}
	public Date getAttStartTime_start() {
		return attStartTime_start;
	}
	public void setAttStartTime_start(Date attStartTimeStart) {
		attStartTime_start = attStartTimeStart;
	}
	public Date getAttStartTime_end() {
		return attStartTime_end;
	}
	public void setAttStartTime_end(Date attStartTimeEnd) {
		attStartTime_end = attStartTimeEnd;
	}
	public Date getAttEndTime_start() {
		return attEndTime_start;
	}
	public void setAttEndTime_start(Date attEndTimeStart) {
		attEndTime_start = attEndTimeStart;
	}
	public Date getAttEndTime_end() {
		return attEndTime_end;
	}
	public void setAttEndTime_end(Date attEndTimeEnd) {
		attEndTime_end = attEndTimeEnd;
	}
	public Integer getAttcount() {
		return attcount;
	}
	public void setAttcount(Integer attcount) {
		this.attcount = attcount;
	}
	public String getPlantimes() {
		return plantimes;
	}
	public void setPlantimes(String plantimes) {
		this.plantimes = plantimes;
	}
	public String getStatename() {
		return statename;
	}
	public void setStatename(String stateName) {
		this.statename = stateName;
	}
	public Date getConfigTime() {
		return configTime;
	}
	public void setConfigTime(Date configTime) {
		this.configTime = configTime;
	}
	public String getPlannerName() {
		return plannerName;
	}
	public void setPlannerName(String plannerName) {
		this.plannerName = plannerName;
	}
	}
