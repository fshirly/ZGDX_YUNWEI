package com.fable.insightview.monitor.database.entity;

public class MoDb2BufferPoolBean {
	private Integer moId;
	private Integer dbMoId;
	private String bufferPoolName;
	private Integer bufferPoolID;
	private Integer bufferSize;
	private Integer npages;
	private Integer pageSize;
	private String databaseName;
	private double bufferPoolHits;
	private double indexHits;
	private double dataPageHits;
	private String ip;
	private String formatBufferSize;
	private String formatPageSize;
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

	public String getBufferPoolName() {
		return bufferPoolName;
	}

	public void setBufferPoolName(String bufferPoolName) {
		this.bufferPoolName = bufferPoolName;
	}

	public Integer getBufferPoolID() {
		return bufferPoolID;
	}

	public void setBufferPoolID(Integer bufferPoolID) {
		this.bufferPoolID = bufferPoolID;
	}

	public Integer getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}

	public Integer getNpages() {
		return npages;
	}

	public void setNpages(Integer npages) {
		this.npages = npages;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public double getBufferPoolHits() {
		return bufferPoolHits;
	}

	public void setBufferPoolHits(double bufferPoolHits) {
		this.bufferPoolHits = bufferPoolHits;
	}

	public double getIndexHits() {
		return indexHits;
	}

	public void setIndexHits(double indexHits) {
		this.indexHits = indexHits;
	}

	public double getDataPageHits() {
		return dataPageHits;
	}

	public void setDataPageHits(double dataPageHits) {
		this.dataPageHits = dataPageHits;
	}

	public String getFormatBufferSize() {
		return formatBufferSize;
	}

	public void setFormatBufferSize(String formatBufferSize) {
		this.formatBufferSize = formatBufferSize;
	}

	public String getFormatPageSize() {
		return formatPageSize;
	}

	public void setFormatPageSize(String formatPageSize) {
		this.formatPageSize = formatPageSize;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

}
