package com.fable.insightview.monitor.database.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * ORACLE实例SGA性能
 * 
 */

@Alias("perfOrclSGA")
public class PerfOrclSGABean {
	private Double id;
	private Double moId;
	private Date collectTime;
	private Double fullSize;
	private Double bufferCache;
	private Double sharedPool;
	private Double redoLog;
	private Double libraryCache;
	private Double dictionaryCache;
	private Double fixedArea;
	private Double javaPool;
	private Double largePool;
	private Double variableSize;
	private Double streamsPool;
	private Double freeMemory;
	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public Double getMoId() {
		return moId;
	}

	public void setMoId(Double moId) {
		this.moId = moId;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public Double getFullSize() {
		return fullSize;
	}

	public void setFullSize(Double fullSize) {
		this.fullSize = fullSize;
	}

	public Double getBufferCache() {
		return bufferCache;
	}

	public void setBufferCache(Double bufferCache) {
		this.bufferCache = bufferCache;
	}

	public Double getSharedPool() {
		return sharedPool;
	}

	public void setSharedPool(Double sharedPool) {
		this.sharedPool = sharedPool;
	}

	public Double getRedoLog() {
		return redoLog;
	}

	public void setRedoLog(Double redoLog) {
		this.redoLog = redoLog;
	}

	public Double getLibraryCache() {
		return libraryCache;
	}

	public void setLibraryCache(Double libraryCache) {
		this.libraryCache = libraryCache;
	}

	public Double getDictionaryCache() {
		return dictionaryCache;
	}

	public void setDictionaryCache(Double dictionaryCache) {
		this.dictionaryCache = dictionaryCache;
	}

	public Double getFixedArea() {
		return fixedArea;
	}

	public void setFixedArea(Double fixedArea) {
		this.fixedArea = fixedArea;
	}

	public Double getJavaPool() {
		return javaPool;
	}

	public void setJavaPool(Double javaPool) {
		this.javaPool = javaPool;
	}

	public Double getLargePool() {
		return largePool;
	}

	public void setLargePool(Double largePool) {
		this.largePool = largePool;
	}

	public Double getVariableSize() {
		return variableSize;
	}

	public void setVariableSize(Double variableSize) {
		this.variableSize = variableSize;
	}

	public Double getStreamsPool() {
		return streamsPool;
	}

	public void setStreamsPool(Double streamsPool) {
		this.streamsPool = streamsPool;
	}

	public Double getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(Double freeMemory) {
		this.freeMemory = freeMemory;
	}

}
