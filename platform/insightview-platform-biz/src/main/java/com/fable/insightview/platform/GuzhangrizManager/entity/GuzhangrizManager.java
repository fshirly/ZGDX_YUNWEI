package com.fable.insightview.platform.GuzhangrizManager.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 数据备份日志信息
 * @author duy 20170812
 */
public class GuzhangrizManager {
    private Integer id;
    /**
     * 备份表名称
     */
    private String tableName;
    /**
     * 操作时间
     */
    private String operatTime;
    /**
     * 备份结果（1成功2失败）
     */
    private String result;
    /**
     * 备份执行时间
     */
    private String originalTime;
    /**
     * 数据还原开始时间
     */
    private Date dataRestoreStartTime;
    /**
     * 数据还原结束时间
     */
    //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dataRestoreEndTime;
    /**
     * 数据还原结果（1成功2失败）
     */
    private String dataRestoreResult;
    /**
     * 数据还原总数
     */
    private int dataRestoreTotal;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getOperatTime() {
		return operatTime;
	}
	public void setOperatTime(String operatTime) {
		this.operatTime = operatTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOriginalTime() {
		return originalTime;
	}
	public void setOriginalTime(String originalTime) {
		this.originalTime = originalTime;
	}
	public Date getDataRestoreStartTime() {
		return dataRestoreStartTime;
	}
	public void setDataRestoreStartTime(java.util.Date date) {
		this.dataRestoreStartTime = date;
	}
	public Date getDataRestoreEndTime() {
		return dataRestoreEndTime;
	}
	public void setDataRestoreEndTime(Date dataRestoreEndTime) {
		this.dataRestoreEndTime = dataRestoreEndTime;
	}
	public String getDataRestoreResult() {
		return dataRestoreResult;
	}
	public void setDataRestoreResult(String dataRestoreResult) {
		this.dataRestoreResult = dataRestoreResult;
	}
	public int getDataRestoreTotal() {
		return dataRestoreTotal;
	}
	public void setDataRestoreTotal(int dataRestoreTotal) {
		this.dataRestoreTotal = dataRestoreTotal;
	}
}
