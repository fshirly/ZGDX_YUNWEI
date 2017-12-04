package com.fable.insightview.monitor.perf.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 监测对象与监测器关系
 * 
 * 
 */

@Alias("sysMonitorsOfMOClass")
public class SysMonitorsOfMOClassBean {
	@NumberGenerator(name = "MonitorSysMoOfMoClassPK")
	private Integer id;
	private Integer moClassId;
	private Integer mid;
	private String moName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

}
