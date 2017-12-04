package com.fable.insightview.platform.attendanceconf.entity;

import java.util.Date;


import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class AttendancePeriodConf {
  @NumberGenerator(name="AttPeriodId")
  private Integer attPeriodId;
  private Integer attendanceId;
  private String startTime;
  private String endTime;
public Integer getAttPeriodId() {
	return attPeriodId;
}
public void setAttPeriodId(Integer attPeriodId) {
	this.attPeriodId = attPeriodId;
}
public Integer getAttendanceId() {
	return attendanceId;
}
public void setAttendanceId(Integer attendanceId) {
	this.attendanceId = attendanceId;
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
}
