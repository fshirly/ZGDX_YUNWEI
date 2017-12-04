package com.fable.insightview.platform.common.dynamicdb.jdbc.work;

import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
/**
 * 新增字段工作
 * @author 郑自辉
 *
 */
public class AddWork extends BaseWork{
	
	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 新增字段名
	 */
	private String columnName;
	
	/**
	 * 新增字段类型
	 */
	private String columnType;
	
	/**
	 * 构造新增字段的工作
	 * @param workType 操作类型
	 * @param tableName 表名
	 * @param columnName 字段名
	 * @param columnType 字段类型
	 */
	public AddWork(WorkType workType,String tableName,String columnName,String columnType)
	{
		super(workType);
		this.tableName = tableName;
		this.columnName = columnName;
		this.columnType = columnType;
	}

	@Override
	protected String sql() {
		String addSql = SqlMapperUtil.getSqlMapper().getAdd();
		addSql = addSql.replace("?0", tableName)
						.replace("?1", columnName)
						.replace("?2", columnType);
		return addSql;
	}

}
