package com.fable.insightview.monitor.alarmmgr.entity;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;


@Alias("alarmDispatchDetail")
public class AlarmDispatchDetail {
	private String dispatchID;
	private Integer alarmID;
	private Integer alarmStatus;
	private String dispatchJson;
	private Timestamp dispatchTime;
	private Integer dispatchType;
	private Integer dispatchStatus;
	private String resultDescr;
	private Timestamp resendTime;
	private Integer sendCount;
	private Integer workOrderId;

	public Integer getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Integer workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getDispatchID() {
		return dispatchID;
	}

	public void setDispatchID(String dispatchID) {
		this.dispatchID = dispatchID;
	}

	public Integer getAlarmID() {
		return alarmID;
	}

	public void setAlarmID(Integer alarmID) {
		this.alarmID = alarmID;
	}

	public Integer getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(Integer alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public String getDispatchJson() {
		return dispatchJson;
	}

	public void setDispatchJson(String dispatchJson) {
		this.dispatchJson = dispatchJson;
	}

	public Timestamp getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(Timestamp dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public Integer getDispatchStatus() {
		return dispatchStatus;
	}

	public void setDispatchStatus(Integer dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}

	public String getResultDescr() {
		return resultDescr;
	}

	public void setResultDescr(String resultDescr) {
		this.resultDescr = resultDescr;
	}

	public Timestamp getResendTime() {
		return resendTime;
	}

	public void setResendTime(Timestamp resendTime) {
		this.resendTime = resendTime;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}
	
	public Integer getDispatchType() {
		return dispatchType;
	}

	public void setDispatchType(Integer dispatchType) {
		this.dispatchType = dispatchType;
	}
}
