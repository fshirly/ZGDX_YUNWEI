package com.fable.insightview.monitor.room3d.entity;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Alarm {
	
	/**
	 * 包含多个字符串的数组，每个字符串对应一张图片或矢量
	 */
	private List<String> names;
	
	private Boolean autorotate;
	
	private String face;
	
	/**
	 * 显示位置
	 */
	private Integer position;
	
	private List<Integer> t3;

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public Boolean getAutorotate() {
		return autorotate;
	}

	public void setAutorotate(Boolean autorotate) {
		this.autorotate = autorotate;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public List<Integer> getT3() {
		return t3;
	}

	public void setT3(List<Integer> t3) {
		this.t3 = t3;
	}

	
	

}
