package com.fable.insightview.platform.common.dynamicdb.jdbc.work;

import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
/**
 * 删除表的工作
 * @author 郑自辉
 *
 */
public class DropWork extends BaseWork{

	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 删除表
	 * @param workType 工作类型
	 * @param tableName 表名
	 */
	public DropWork(WorkType workType,String tableName)
	{
		super(workType);
		this.tableName = tableName;
	}
	
	@Override
	protected String sql() {
		String dropSql = SqlMapperUtil.getSqlMapper().getDrop();
		dropSql = dropSql.replace("?", tableName);
		return dropSql;
	}

}
