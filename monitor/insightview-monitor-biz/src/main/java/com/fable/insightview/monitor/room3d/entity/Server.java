package com.fable.insightview.monitor.room3d.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Server {
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * ID
	 */
	private Integer ciId;
	
	/**
	 * 开始单位
	 */
	private Integer startUnit;
	
	/**
	 * 结束单位
	 */
	private Integer endUnit;

	public Integer getStartUnit() {
		return startUnit;
	}

	public void setStartUnit(Integer startUnit) {
		this.startUnit = startUnit;
	}

	public Integer getEndUnit() {
		return endUnit;
	}

	public void setEndUnit(Integer endUnit) {
		this.endUnit = endUnit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCiId() {
		return ciId;
	}

	public void setCiId(Integer ciId) {
		this.ciId = ciId;
	}




}
