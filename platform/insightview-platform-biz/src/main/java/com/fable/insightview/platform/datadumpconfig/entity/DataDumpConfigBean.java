package com.fable.insightview.platform.datadumpconfig.entity;

/**
 * 数据转储
 *
 */
public class DataDumpConfigBean {
	/**
	 * 原始数据保留时间
	 */
	private Integer originalDataRetentionTime;
	
	/**
	 * 转储数据保留时间
	 */
	private Integer dumpDataRetentionTime;
	
	/**
	 * 执行时间
	 */
	private String executeTime;

	public Integer getOriginalDataRetentionTime() {
		return originalDataRetentionTime;
	}

	public void setOriginalDataRetentionTime(Integer originalDataRetentionTime) {
		this.originalDataRetentionTime = originalDataRetentionTime;
	}

	public Integer getDumpDataRetentionTime() {
		return dumpDataRetentionTime;
	}

	public void setDumpDataRetentionTime(Integer dumpDataRetentionTime) {
		this.dumpDataRetentionTime = dumpDataRetentionTime;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}
	
}
