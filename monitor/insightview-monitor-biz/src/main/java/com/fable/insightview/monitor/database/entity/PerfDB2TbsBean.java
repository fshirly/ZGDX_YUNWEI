package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfDB2TbsBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private Integer totalSize;
	private Integer freeSize;
	private Integer usedSize;
	private double tbUsage;
	private String tbStatus;
	private String tbsType;
	private String tbsName;
	private String databaseName;
	private String perfValue;
	private String formatTotalSize;
	private String formatUsedSize;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public Integer getFreeSize() {
		return freeSize;
	}

	public void setFreeSize(Integer freeSize) {
		this.freeSize = freeSize;
	}

	public Integer getUsedSize() {
		return usedSize;
	}

	public void setUsedSize(Integer usedSize) {
		this.usedSize = usedSize;
	}

	public double getTbUsage() {
		return tbUsage;
	}

	public void setTbUsage(double tbUsage) {
		this.tbUsage = tbUsage;
	}

	public String getTbStatus() {
		return tbStatus;
	}

	public void setTbStatus(String tbStatus) {
		this.tbStatus = tbStatus;
	}

	public String getTbsType() {
		return tbsType;
	}

	public void setTbsType(String tbsType) {
		this.tbsType = tbsType;
	}

	public String getTbsName() {
		return tbsName;
	}

	public void setTbsName(String tbsName) {
		this.tbsName = tbsName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getPerfValue() {
		return perfValue;
	}

	public void setPerfValue(String perfValue) {
		this.perfValue = perfValue;
	}

	public String getFormatTotalSize() {
		return formatTotalSize;
	}

	public void setFormatTotalSize(String formatTotalSize) {
		this.formatTotalSize = formatTotalSize;
	}

	public String getFormatUsedSize() {
		return formatUsedSize;
	}

	public void setFormatUsedSize(String formatUsedSize) {
		this.formatUsedSize = formatUsedSize;
	}

}
