package com.fable.insightview.monitor.middleware.websphere.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;


@Alias("webSphereOperSituationBean")
public class WebSphereOperSituationBean {
	private Integer moId;
	
	private Integer alarmLevel;
	
	private Integer useStatus;
	
	private String avgAvailability;
	
	private String deviceStatus;
	
	private String levelIcon;
	
	private Date updateAlarmTime;
	private String durationTime;//持续时间
	private Integer doIntervals; 
	private Integer defDoIntervals;
	private Date collectTime;
	private String operStatusName;
	private String operaTip;
	
	public WebSphereOperSituationBean() {
	}

	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Integer getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

	public String getAvgAvailability() {
		return avgAvailability;
	}

	public void setAvgAvailability(String avgAvailability) {
		this.avgAvailability = avgAvailability;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getLevelIcon() {
		return levelIcon;
	}

	public void setLevelIcon(String levelIcon) {
		this.levelIcon = levelIcon;
	}

	public Date getUpdateAlarmTime() {
		return updateAlarmTime;
	}

	public void setUpdateAlarmTime(Date updateAlarmTime) {
		this.updateAlarmTime = updateAlarmTime;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public Integer getDoIntervals() {
		return doIntervals;
	}

	public void setDoIntervals(Integer doIntervals) {
		this.doIntervals = doIntervals;
	}

	public Integer getDefDoIntervals() {
		return defDoIntervals;
	}

	public void setDefDoIntervals(Integer defDoIntervals) {
		this.defDoIntervals = defDoIntervals;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public String getOperStatusName() {
		return operStatusName;
	}

	public void setOperStatusName(String operStatusName) {
		this.operStatusName = operStatusName;
	}

	public String getOperaTip() {
		return operaTip;
	}

	public void setOperaTip(String operaTip) {
		this.operaTip = operaTip;
	}
	
	
}
