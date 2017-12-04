package com.fable.insightview.monitor.room3d.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 告警图标
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Icons {
	
	/**
	 * 告警
	 */
	private Alarm alarm;

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}
	
	

}
