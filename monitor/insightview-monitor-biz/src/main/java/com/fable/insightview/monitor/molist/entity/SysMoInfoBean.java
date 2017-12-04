package com.fable.insightview.monitor.molist.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 监测器信息
 * @author caoj
 */

@Alias("sysMoInfoClass")
public class SysMoInfoBean {
	@NumberGenerator(name = "MonitorSysMoInfoPK")
	private Integer mid;              //监测器编号
	private String moName;            //监测器名称
	private String moClass;           //监测器调度类名
	private Integer doIntervals;      //默认采集周期，单位：秒
	private String moDescr;           //监测器描述
	private Integer monitorTypeID;
	private String monitorTypeName;   //监测器类型名称
	private Integer monitorProperty;  //0:设备 1:其它
	private String resManufacturerName;
	private String resCategoryName;
	private String moClassName;
	private String moClassLable;
	private Integer moClassId;
	private Integer timeUnit;
	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getMonitorTypeID() {
		return monitorTypeID;
	}

	public void setMonitorTypeID(Integer monitorTypeID) {
		this.monitorTypeID = monitorTypeID;
	}

	public String getMonitorTypeName() {
		return monitorTypeName;
	}

	public void setMonitorTypeName(String monitorTypeName) {
		this.monitorTypeName = monitorTypeName;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getMoClass() {
		return moClass;
	}

	public void setMoClass(String moClass) {
		this.moClass = moClass;
	}

	public Integer getDoIntervals() {
		return doIntervals;
	}

	public void setDoIntervals(Integer doIntervals) {
		this.doIntervals = doIntervals;
	}

	public String getMoDescr() {
		return moDescr;
	}

	public void setMoDescr(String moDescr) {
		this.moDescr = moDescr;
	}

	public Integer getMonitorProperty() {
		return monitorProperty;
	}

	public void setMonitorProperty(Integer monitorProperty) {
		this.monitorProperty = monitorProperty;
	}

	public String getResManufacturerName() {
		return resManufacturerName;
	}

	public void setResManufacturerName(String resManufacturerName) {
		this.resManufacturerName = resManufacturerName;
	}

	public String getResCategoryName() {
		return resCategoryName;
	}

	public void setResCategoryName(String resCategoryName) {
		this.resCategoryName = resCategoryName;
	}

	public String getMoClassName() {
		return moClassName;
	}

	public void setMoClassName(String moClassName) {
		this.moClassName = moClassName;
	}

	public String getMoClassLable() {
		return moClassLable;
	}

	public void setMoClassLable(String moClassLable) {
		this.moClassLable = moClassLable;
	}

	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

	public Integer getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(Integer timeUnit) {
		this.timeUnit = timeUnit;
	}

}
