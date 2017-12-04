package com.fable.insightview.platform.common.dynamicdb.jdbc.work.query;

import java.util.List;

import com.fable.insightview.platform.common.dynamicdb.Column;
import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
/**
 * 单行查询工作
 * 主要用于配置项详情展示
 * @author 郑自辉
 *
 */
public class QueryDetailWork extends BaseQueryWork{
	
	/**
	 * 配置项ID
	 */
	private String id;
	
	public QueryDetailWork(WorkType workType,String id,String[] tableName,List<Column> columns)
	{
		super(workType,tableName,columns);
		this.id = id;
	}

	@Override
	protected String selectSql() {
		return SqlMapperUtil.getSqlMapper().getSelect();
	}

	@Override
	protected String genWhere(String tableName) {
		return tableName + ".CiId=" + id;
	}
}
