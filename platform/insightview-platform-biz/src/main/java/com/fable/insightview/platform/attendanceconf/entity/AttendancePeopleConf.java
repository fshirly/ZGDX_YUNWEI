package com.fable.insightview.platform.attendanceconf.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class AttendancePeopleConf {
@NumberGenerator(name="Id")
  private Integer Id;
  private Integer userId;
  private Integer attendanceId;
  private String userIdStr;
public Integer getId() {
	return Id;
}
public void setId(Integer id) {
	Id = id;
}

public Integer getAttendanceId() {
	return attendanceId;
}
public void setAttendanceId(Integer attendanceId) {
	this.attendanceId = attendanceId;
}
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public String getUserIdStr() {
	return userIdStr;
}
public void setUserIdStr(String userIdStr) {
	this.userIdStr = userIdStr;
}
}
