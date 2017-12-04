package com.fable.insightview.monitor.perf.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * KPI定义表
 * 
 * @author hanl
 * 
 */

@Alias("perfKPIDef")
public class PerfKPIDefBean {
	@NumberGenerator(name = "monitorPerfKPIDefPK")
	private Integer kpiID;
	private String kpiCategory;
	private Integer classID;
	private String name;
	private String enName;
	private String quantifier;
	private Integer valueType;
	private Integer amountType;
	private Integer processFlag;
	private String valueRange;
	private String note;
	private Integer isSupport;
	private String className;

	public Integer getKpiID() {
		return kpiID;
	}

	public void setKpiID(Integer kpiID) {
		this.kpiID = kpiID;
	}

	public String getKpiCategory() {
		return kpiCategory;
	}

	public void setKpiCategory(String kpiCategory) {
		this.kpiCategory = kpiCategory;
	}

	public Integer getClassID() {
		return classID;
	}

	public void setClassID(Integer classID) {
		this.classID = classID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getQuantifier() {
		return quantifier;
	}

	public void setQuantifier(String quantifier) {
		this.quantifier = quantifier;
	}

	public Integer getValueType() {
		return valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	public Integer getAmountType() {
		return amountType;
	}

	public void setAmountType(Integer amountType) {
		this.amountType = amountType;
	}

	public Integer getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(Integer processFlag) {
		this.processFlag = processFlag;
	}

	public String getValueRange() {
		return valueRange;
	}

	public void setValueRange(String valueRange) {
		this.valueRange = valueRange;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getIsSupport() {
		return isSupport;
	}

	public void setIsSupport(Integer isSupport) {
		this.isSupport = isSupport;
	}

}
