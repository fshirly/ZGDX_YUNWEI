package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class MoDbTabsBean {
	private Integer moId;
	private Integer dbMoId;
	private String tbsName;
	private Integer bufferPoolMoId;
	private String definer;
	private Date createTIme;
	private Integer tbsID;
	private String tbsType;
	private String dataType;
	private Integer extentSize;
	private Integer preFetchSize;
	private long overHead;
	private long transferRate;
	private Integer pageSize;
	private String dropRecorvery;
	private String ip;
	private String databaseName;
	private String formatTime;
	private String bufferPoolName;
	private String formatExtentSize;
	private String instanceName;
	
	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public Integer getDbMoId() {
		return dbMoId;
	}

	public void setDbMoId(Integer dbMoId) {
		this.dbMoId = dbMoId;
	}

	public String getTbsName() {
		return tbsName;
	}

	public void setTbsName(String tbsName) {
		this.tbsName = tbsName;
	}

	public Integer getBufferPoolMoId() {
		return bufferPoolMoId;
	}

	public void setBufferPoolMoId(Integer bufferPoolMoId) {
		this.bufferPoolMoId = bufferPoolMoId;
	}

	public String getDefiner() {
		return definer;
	}

	public void setDefiner(String definer) {
		this.definer = definer;
	}

	public Date getCreateTIme() {
		return createTIme;
	}

	public void setCreateTIme(Date createTIme) {
		this.createTIme = createTIme;
	}

	public Integer getTbsID() {
		return tbsID;
	}

	public void setTbsID(Integer tbsID) {
		this.tbsID = tbsID;
	}

	public String getTbsType() {
		return tbsType;
	}

	public void setTbsType(String tbsType) {
		this.tbsType = tbsType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getExtentSize() {
		return extentSize;
	}

	public void setExtentSize(Integer extentSize) {
		this.extentSize = extentSize;
	}

	public Integer getPreFetchSize() {
		return preFetchSize;
	}

	public void setPreFetchSize(Integer preFetchSize) {
		this.preFetchSize = preFetchSize;
	}

	public long getOverHead() {
		return overHead;
	}

	public void setOverHead(long overHead) {
		this.overHead = overHead;
	}

	public long getTransferRate() {
		return transferRate;
	}

	public void setTransferRate(long transferRate) {
		this.transferRate = transferRate;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getDropRecorvery() {
		return dropRecorvery;
	}

	public void setDropRecorvery(String dropRecorvery) {
		this.dropRecorvery = dropRecorvery;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

	public String getBufferPoolName() {
		return bufferPoolName;
	}

	public void setBufferPoolName(String bufferPoolName) {
		this.bufferPoolName = bufferPoolName;
	}

	public String getFormatExtentSize() {
		return formatExtentSize;
	}

	public void setFormatExtentSize(String formatExtentSize) {
		this.formatExtentSize = formatExtentSize;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	
}
