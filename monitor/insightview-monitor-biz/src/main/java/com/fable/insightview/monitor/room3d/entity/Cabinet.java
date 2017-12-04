package com.fable.insightview.monitor.room3d.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Cabinet {
	
	/**
	 * 机框名称
	 */
	private String name;
	
	/**
	 * 单位
	 */
	private Integer unit;
	
	
	/**
	 * 机柜ID
	 */
	private Integer ciId;
	
	/**
	 * 服务器
	 */
	@JsonProperty("server")
	private List<Server> serverList;
	
	

	public List<Server> getServerList() {
		return serverList;
	}

	public void setServerList(List<Server> serverList) {
		this.serverList = serverList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Integer getCiId() {
		return ciId;
	}

	public void setCiId(Integer ciId) {
		this.ciId = ciId;
	}


	
	


}
