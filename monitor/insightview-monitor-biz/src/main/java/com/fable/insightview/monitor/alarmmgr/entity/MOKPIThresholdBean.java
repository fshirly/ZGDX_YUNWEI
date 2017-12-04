package com.fable.insightview.monitor.alarmmgr.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 管理对象性能阈值规则
 * 
 * @author hanl
 * 
 */

@Alias("threshold")
public class MOKPIThresholdBean {
	/* 阈值规则ID */
	@NumberGenerator(name = "monitorThresholdPK")
	private Integer ruleID;

	/* 源对象ID */
	private Integer sourceMOID;

	/* 对象类型 */
	private Integer classID;

	/* 管理对象ID */
	private Integer moID; // 如果MOID为-1表示对KPIID默认的阈值规则'

	/* 性能指标ID */
	private Integer kpiID;

	/* 上限阈值 */
	private Float upThreshold;

	/* 下限阈值 */
	private Float downThreshold;

	/* 备注 */
	private String descr;

	/* 上限回归阈值 */
	private Float upRecursiveThreshold;

	/* 下限回归阈值 */
	private Float downRecursiveThreshold;

	private String kpiName;
	private String className;
	private String moName;
	private String sourceMOName;
	private String attributeTableName;
	private String jmxType;
	
	private Integer parentClassID;
	private Integer kpiClassID;
	public Integer getRuleID() {
		return ruleID;
	}

	public void setRuleID(Integer ruleID) {
		this.ruleID = ruleID;
	}

	public Integer getClassID() {
		return classID;
	}

	public void setClassID(Integer classID) {
		this.classID = classID;
	}

	public Integer getMoID() {
		return moID;
	}

	public void setMoID(Integer moID) {
		this.moID = moID;
	}

	public Integer getKpiID() {
		return kpiID;
	}

	public void setKpiID(Integer kpiID) {
		this.kpiID = kpiID;
	}

	public Float getUpThreshold() {
		return upThreshold;
	}

	public void setUpThreshold(Float upThreshold) {
		this.upThreshold = upThreshold;
	}

	public Float getDownThreshold() {
		return downThreshold;
	}

	public void setDownThreshold(Float downThreshold) {
		this.downThreshold = downThreshold;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getAttributeTableName() {
		return attributeTableName;
	}

	public void setAttributeTableName(String attributeTableName) {
		this.attributeTableName = attributeTableName;
	}

	public Integer getSourceMOID() {
		return sourceMOID;
	}

	public void setSourceMOID(Integer sourceMOID) {
		this.sourceMOID = sourceMOID;
	}

	public String getSourceMOName() {
		return sourceMOName;
	}

	public void setSourceMOName(String sourceMOName) {
		this.sourceMOName = sourceMOName;
	}

	public Float getUpRecursiveThreshold() {
		return upRecursiveThreshold;
	}

	public void setUpRecursiveThreshold(Float upRecursiveThreshold) {
		this.upRecursiveThreshold = upRecursiveThreshold;
	}

	public Float getDownRecursiveThreshold() {
		return downRecursiveThreshold;
	}

	public void setDownRecursiveThreshold(Float downRecursiveThreshold) {
		this.downRecursiveThreshold = downRecursiveThreshold;
	}

	public String getJmxType() {
		return jmxType;
	}

	public void setJmxType(String jmxType) {
		this.jmxType = jmxType;
	}

	public Integer getParentClassID() {
		return parentClassID;
	}

	public void setParentClassID(Integer parentClassID) {
		this.parentClassID = parentClassID;
	}

	public Integer getKpiClassID() {
		return kpiClassID;
	}

	public void setKpiClassID(Integer kpiClassID) {
		this.kpiClassID = kpiClassID;
	}
	
}
