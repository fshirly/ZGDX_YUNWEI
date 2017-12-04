package com.fable.insightview.platform.common.dynamicdb.jdbc.work;

import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;

/**
 * 物理删除字段工作
 * @author 郑自辉
 *
 */
public class RemoveWork extends BaseWork{
	
	private String tableName;
	
	private String columnName;
	
	/**
	 * 删除字段
	 * @param workType 工作类型
	 * @param tableName 表名
	 * @param columnName 要删除的字段
	 */
	public RemoveWork(WorkType workType,String tableName,String columnName)
	{
		super(workType);
		this.tableName = tableName;
		this.columnName = columnName;
	}

	@Override
	protected String sql() {
		String removeSql = SqlMapperUtil.getSqlMapper().getRemove();
		removeSql = removeSql.replace("?0", tableName)
								.replace("?1", columnName);
		return removeSql;
	}

	
}
