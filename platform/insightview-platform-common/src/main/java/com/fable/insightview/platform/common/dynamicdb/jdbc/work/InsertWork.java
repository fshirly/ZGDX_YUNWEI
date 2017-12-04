package com.fable.insightview.platform.common.dynamicdb.jdbc.work;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
/**
 * 新增数据的工作
 * @author 程大伟
 *
 */
public class InsertWork extends BaseWork {

	private String tableName;

	private String columnNames;

	private String columnValues;
	
	private List<String> batchSql;

	/**
	 * 保存数据
	 * @param workType 工作类型
	 * @param tableName 表名
	 * @param columnNames 字段
	 * @param columnValues 字段值
	 */
	public InsertWork(WorkType workType, String tableName, String columnNames,
			String columnValues) {
		super(workType);
		this.tableName = tableName;
		this.columnNames = columnNames;
		this.columnValues = columnValues;
	}

	public InsertWork(WorkType workType) {
		super(workType);
	}

	public String sql() {
		String sql = SqlMapperUtil.getSqlMapper().getInsert();
		if(!StringUtils.isEmpty(tableName) && !StringUtils.isEmpty(columnNames) && !StringUtils.isEmpty(columnValues)){
			sql = sql.replace("?tableName", tableName)
					.replace("?columnNames", columnNames)
					.replace("?columnValues", columnValues);
		}
		return sql;
	}
	
	@Override
	protected List<String> batchSql() {
		return batchSql;
	}
	
	public void setBatchSql(List<String> batchSql) {
		this.batchSql = batchSql;
	}

	
	public String batchSql(String tableName, String columnNames,
			String columnValues){
		String sql = SqlMapperUtil.getSqlMapper().getInsert();
		if(!StringUtils.isEmpty(tableName) && !StringUtils.isEmpty(columnNames) && !StringUtils.isEmpty(columnValues)){
			sql = sql.replace("?tableName", tableName)
					.replace("?columnNames", columnNames)
					.replace("?columnValues", columnValues);
		}
		return sql;
	}

}
