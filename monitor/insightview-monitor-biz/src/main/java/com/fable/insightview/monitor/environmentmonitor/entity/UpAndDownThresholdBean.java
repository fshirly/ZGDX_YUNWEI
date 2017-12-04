package com.fable.insightview.monitor.environmentmonitor.entity;

public class UpAndDownThresholdBean {
	private Double upThreshold;
	private Double downThreshold;
	private int parentClassId;
	private String relationPath;
	private int classID;
	private int sourceMOID;
	private int MOID;
	private int kpiId;
	private int moClassId;
	public Double getUpThreshold() {
		return upThreshold;
	}
	public void setUpThreshold(Double upThreshold) {
		this.upThreshold = upThreshold;
	}
	public Double getDownThreshold() {
		return downThreshold;
	}
	public void setDownThreshold(Double downThreshold) {
		this.downThreshold = downThreshold;
	}
	public int getParentClassId() {
		return parentClassId;
	}
	public void setParentClassId(int parentClassId) {
		this.parentClassId = parentClassId;
	}
	public String getRelationPath() {
		return relationPath;
	}
	public void setRelationPath(String relationPath) {
		this.relationPath = relationPath;
	}
	public int getClassID() {
		return classID;
	}
	public void setClassID(int classID) {
		this.classID = classID;
	}
	public int getSourceMOID() {
		return sourceMOID;
	}
	public void setSourceMOID(int sourceMOID) {
		this.sourceMOID = sourceMOID;
	}
	public int getMOID() {
		return MOID;
	}
	public void setMOID(int mOID) {
		MOID = mOID;
	}
	public int getKpiId() {
		return kpiId;
	}
	public void setKpiId(int kpiId) {
		this.kpiId = kpiId;
	}
	public int getMoClassId() {
		return moClassId;
	}
	public void setMoClassId(int moClassId) {
		this.moClassId = moClassId;
	}
	
}
