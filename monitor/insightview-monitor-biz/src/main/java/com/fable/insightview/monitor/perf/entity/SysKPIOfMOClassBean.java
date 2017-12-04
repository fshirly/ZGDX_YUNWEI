package com.fable.insightview.monitor.perf.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 指标与对象类型关系
 * 
 */
@Alias("kpiOfMoClass")
public class SysKPIOfMOClassBean {
	@NumberGenerator(name="monitorKPIOfMOClassPK")
	private Integer id;
	/* 指标id */
	private Integer kpiID;
	/* 父对象类型id */
	private Integer moClassID;
	/* 是否支持阈值 */
	private Integer isSupport;
	
	/* 指标名称 */
	private String name;
	/* 对象类型名称 */
	private String moClassName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getKpiID() {
		return kpiID;
	}

	public void setKpiID(Integer kpiID) {
		this.kpiID = kpiID;
	}

	public Integer getMoClassID() {
		return moClassID;
	}

	public void setMoClassID(Integer moClassID) {
		this.moClassID = moClassID;
	}

	public Integer getIsSupport() {
		return isSupport;
	}

	public void setIsSupport(Integer isSupport) {
		this.isSupport = isSupport;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoClassName() {
		return moClassName;
	}

	public void setMoClassName(String moClassName) {
		this.moClassName = moClassName;
	}
	
}
