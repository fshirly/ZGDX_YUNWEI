package com.fable.insightview.monitor.room3d.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Administrator
 * 3D ROOM 请求模型
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class DataRequest {
	
	/**
	 * 版本
	 */
	@JsonProperty("v")
	private String version;
	
	/**
	 * 图元属性
	 */
	@JsonProperty("d")
	private List<Data> descList;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Data> getDescList() {
		return descList;
	}

	public void setDescList(List<Data> descList) {
		this.descList = descList;
	}

	

}
