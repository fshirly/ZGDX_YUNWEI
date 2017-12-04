package com.fable.insightview.monitor.database.entity;

import org.apache.ibatis.type.Alias;

/**
 * ORACLE数据库SGA资源
 * 
 */

@Alias("moOracleSGA")
public class MOOracleSGABean {
	/* 监测对象ID */
	private Integer moID;
	/* 数据库实例对象ID */
	private Integer instanceMOID;
	/* SGA大小 */
	private String totalSize;
	/* 固定区大小 */
	private String fixedSize;
	/* 数据库缓冲区 */
	private String bufferSize;
	/* 重做日志缓存 */
	private String redologBuf;
	/* Pool大小 */
	private String poolSize;
	/* 共享池大小 */
	private String sharedPool;
	/* 大池大小 */
	private String largePool;
	/* Java池大小 */
	private String javaPool;
	/* 流池大小 */
	private String streamPool;
	/* 库缓存 */
	private String libraryCache;
	/* 数据字典缓存 */
	private String dicaCache;

	private String moName;
	
	private String ip;
	
	private String dbName;
	
	private String instanceName;
	public Integer getMoID() {
		return moID;
	}

	public void setMoID(Integer moID) {
		this.moID = moID;
	}

	public Integer getInstanceMOID() {
		return instanceMOID;
	}

	public void setInstanceMOID(Integer instanceMOID) {
		this.instanceMOID = instanceMOID;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getFixedSize() {
		return fixedSize;
	}

	public void setFixedSize(String fixedSize) {
		this.fixedSize = fixedSize;
	}

	public String getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(String bufferSize) {
		this.bufferSize = bufferSize;
	}

	public String getRedologBuf() {
		return redologBuf;
	}

	public void setRedologBuf(String redologBuf) {
		this.redologBuf = redologBuf;
	}

	public String getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(String poolSize) {
		this.poolSize = poolSize;
	}

	public String getSharedPool() {
		return sharedPool;
	}

	public void setSharedPool(String sharedPool) {
		this.sharedPool = sharedPool;
	}

	public String getLargePool() {
		return largePool;
	}

	public void setLargePool(String largePool) {
		this.largePool = largePool;
	}

	public String getJavaPool() {
		return javaPool;
	}

	public void setJavaPool(String javaPool) {
		this.javaPool = javaPool;
	}

	public String getStreamPool() {
		return streamPool;
	}

	public void setStreamPool(String streamPool) {
		this.streamPool = streamPool;
	}

	public String getLibraryCache() {
		return libraryCache;
	}

	public void setLibraryCache(String libraryCache) {
		this.libraryCache = libraryCache;
	}

	public String getDicaCache() {
		return dicaCache;
	}

	public void setDicaCache(String dicaCache) {
		this.dicaCache = dicaCache;
	}

	

}
