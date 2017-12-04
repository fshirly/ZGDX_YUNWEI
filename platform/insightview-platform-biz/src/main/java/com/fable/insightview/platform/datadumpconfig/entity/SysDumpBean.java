package com.fable.insightview.platform.datadumpconfig.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;
/**
 * 转储
 *
 */
@Alias("sysdump")
public class SysDumpBean {
	@NumberGenerator(name="platformSysDump")
	private Integer id;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 时间列名
	 */
	private String timeColumnName;
	
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
	public String getTimeColumnName() {
		return timeColumnName;
	}
	public void setTimeColumnName(String timeColumnName) {
		this.timeColumnName = timeColumnName;
	}
	
}
