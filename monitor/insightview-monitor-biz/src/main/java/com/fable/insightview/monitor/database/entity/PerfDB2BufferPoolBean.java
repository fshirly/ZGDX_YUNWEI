package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfDB2BufferPoolBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private Integer bufferPoolHits;
	private Integer indexHits;
	private Integer dataPageHits;
	private Integer directReads;
	private Integer directWrites;
	private Integer directReadTime;
	private Integer directWriteTime;
	private String bufferPoolName;
	private Integer bufferPoolID;
	private Integer bufferSize;
	private Integer npages;
	private Integer pageSize;
	private String formatTime;
	private String perfValue;
	private String formatBufferSize;
	private String formatPageSize;
	
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

	public Integer getBufferPoolHits() {
		return bufferPoolHits;
	}

	public void setBufferPoolHits(Integer bufferPoolHits) {
		this.bufferPoolHits = bufferPoolHits;
	}

	public Integer getIndexHits() {
		return indexHits;
	}

	public void setIndexHits(Integer indexHits) {
		this.indexHits = indexHits;
	}

	public Integer getDataPageHits() {
		return dataPageHits;
	}

	public void setDataPageHits(Integer dataPageHits) {
		this.dataPageHits = dataPageHits;
	}

	public Integer getDirectReads() {
		return directReads;
	}

	public void setDirectReads(Integer directReads) {
		this.directReads = directReads;
	}

	public Integer getDirectWrites() {
		return directWrites;
	}

	public void setDirectWrites(Integer directWrites) {
		this.directWrites = directWrites;
	}

	public Integer getDirectReadTime() {
		return directReadTime;
	}

	public void setDirectReadTime(Integer directReadTime) {
		this.directReadTime = directReadTime;
	}

	public Integer getDirectWriteTime() {
		return directWriteTime;
	}

	public void setDirectWriteTime(Integer directWriteTime) {
		this.directWriteTime = directWriteTime;
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

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

	public String getPerfValue() {
		return perfValue;
	}

	public void setPerfValue(String perfValue) {
		this.perfValue = perfValue;
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

}
