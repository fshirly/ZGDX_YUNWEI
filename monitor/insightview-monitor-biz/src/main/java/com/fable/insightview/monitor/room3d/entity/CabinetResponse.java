package com.fable.insightview.monitor.room3d.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class CabinetResponse {
	
	/**
	 * 机柜集合
	 */
	@JsonProperty("cabinet")
	private List<Cabinet> cabinetList ;

	public List<Cabinet> getCabinetList() {
		return cabinetList;
	}

	public void setCabinetList(List<Cabinet> cabinetList) {
		this.cabinetList = cabinetList;
	}


	
	

}
