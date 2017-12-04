package com.fable.insightview.monitor.room3d.entity;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 图元属性
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Attr {
	
	/**
	 * 告警颜色
	 */
	@JsonProperty("alarm.color")
	private String alarmColor;
	
	/**
	 * 标示
	 */
	@JsonProperty("tagname")
	private String tagName;

	public String getAlarmColor() {
		return alarmColor;
	}

	public void setAlarmColor(String alarmColor) {
		this.alarmColor = alarmColor;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	
}
