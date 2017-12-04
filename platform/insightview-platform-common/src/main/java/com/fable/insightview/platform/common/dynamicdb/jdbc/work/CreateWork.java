package com.fable.insightview.platform.common.dynamicdb.jdbc.work;

import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
/**
 * 创建表的工作
 * @author 郑自辉
 *
 */
public class CreateWork extends BaseWork{
	
	private String tableName;
	
	private String primaryKey;
	
	/**
	 * 创建表
	 * @param workType 工作类型
	 * @param tableName 表名
	 */
	public CreateWork(WorkType workType,String tableName,String primaryKey)
	{
		super(workType);
		this.tableName = tableName;
		this.primaryKey = primaryKey;
	}

	@Override
	protected String sql() {
		String createSql = SqlMapperUtil.getSqlMapper().getCreate();
		createSql = createSql.replace("?", tableName);
		createSql = createSql.replace("#{primaryKey}", primaryKey);
		return createSql;
	}

}
