package com.fable.insightview.platform.common.dynamicdb.jdbc.work;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
/**
 * 更新数据的工作
 * @author 程大伟
 *
 */
public class UpdateWork extends BaseWork {

	private String tableName;
	private String columnNameAndValues;
	private String condition;
	private List<String> batchSql;

	/**
	 * 更新数据
	 * @param workType 工作类型
	 * @param tableName 表名
	 * @param columnNameAndValues 字段值
	 * @param condition 更新条件
	 */
	public UpdateWork(WorkType workType, String tableName,
			String columnNameAndValues, String condition) {
		super(workType);
		this.tableName = tableName;
		this.columnNameAndValues = columnNameAndValues;
		this.condition = condition;
	}

	public UpdateWork(WorkType workType) {
		super(workType);
	}

	@Override
	public String sql() {
		String sql = SqlMapperUtil.getSqlMapper().getUpdate();
		if (StringUtils.isNotEmpty(tableName)
				&& StringUtils.isNotEmpty(columnNameAndValues)
				&& StringUtils.isNotEmpty(condition)) {
			sql = sql.replace("?tableName", tableName)
					.replace("?columnNameAndValues", columnNameAndValues)
					.replace("?condition", condition);
		}
		
		return sql;
	}

	@Override
	protected List<String> batchSql() {
		return this.batchSql;
	}

	public void setBatchSql(List<String> batchSql) {
		this.batchSql = batchSql;
	}

	
	/**
	 * 组装批量sql
	 * @param tableName 表名
	 * @param columnNameAndValues 字段和值
	 * @param condition 条件
	 * @return String 批量SQL语句
	 */
	public String batchSql(String tableName, String columnNameAndValues,
			String condition) {
		String sql = SqlMapperUtil.getSqlMapper().getUpdate();
		if (StringUtils.isNotEmpty(tableName)
				&& StringUtils.isNotEmpty(columnNameAndValues)
				&& StringUtils.isNotEmpty(condition)) {
			sql = sql.replace("?tableName", tableName)
					.replace("?columnNameAndValues", columnNameAndValues)
					.replace("?condition", condition);
		}
		return sql;
	}

}
