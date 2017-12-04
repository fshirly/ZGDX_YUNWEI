package com.fable.insightview.monitor.environmentmonitor.entity;

public class PerfSnapshotRoom {
	private int id;
	private int deviceMOID;
	private int moID;
	private String kpiName;
	private String perfValue;
	private int classID;
	private int resID;
	private String descr;
	private String tagID;
	private String kpiChnName;

	// 新增上下限
	private int kpiID;
	private String name;
	private int MOClassID;
	private int ParentMOID;
	private Double upThreshold;
	private Double downThreshold;
	
	public String getKpiChnName() {
		return kpiChnName;
	}

	public void setKpiChnName(String kpiChnName) {
		this.kpiChnName = kpiChnName;
	}

	public String getTagID() {
		return tagID;
	}

	public void setTagID(String tagID) {
		this.tagID = tagID;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeviceMOID() {
		return deviceMOID;
	}

	public void setDeviceMOID(int deviceMOID) {
		this.deviceMOID = deviceMOID;
	}

	public int getMoID() {
		return moID;
	}

	public void setMoID(int moID) {
		this.moID = moID;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getPerfValue() {
		return perfValue;
	}

	public void setPerfValue(String perfValue) {
		this.perfValue = perfValue;
	}

	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

	public int getResID() {
		return resID;
	}

	public void setResID(int resID) {
		this.resID = resID;
	}

	public int getKpiID() {
		return kpiID;
	}

	public void setKpiID(int kpiID) {
		this.kpiID = kpiID;
	}

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

	public int getMOClassID() {
		return MOClassID;
	}

	public void setMOClassID(int mOClassID) {
		MOClassID = mOClassID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentMOID() {
		return ParentMOID;
	}

	public void setParentMOID(int parentMOID) {
		ParentMOID = parentMOID;
	}
	
}