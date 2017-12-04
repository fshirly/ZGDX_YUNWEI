package com.fable.insightview.platform.common.dynamicdb.jdbc.work;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
/**
 * 删除数据的工作
 * @author 程大伟
 *
 */
public class DeleteWork extends BaseWork {

	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 删除条件
	 */
	private String conditions;
	/**
	 * 批量SQL
	 */
	private List<String> batchSql;
	
	/**
	 * 删除数据
	 * @param workType 工作类型
	 * @param tableName 表名
	 * @param conditions 删除条件
	 */
	public DeleteWork(WorkType workType,String tableName,String conditions) {
		super(workType);
		this.tableName = tableName;
		this.conditions = conditions;
	}

	public DeleteWork(WorkType workType) {
		super(workType);
	}

	@Override
	public String sql() {
		String sql = SqlMapperUtil.getSqlMapper().getDelete();
		if(StringUtils.isNotEmpty(conditions) && StringUtils.isNotEmpty(tableName)){
			sql = sql.replace("?tableName", tableName)
					.replace("?conditions", conditions);
		}
		return sql;
	}
	
	@Override
	protected List<String> batchSql(){
		return this.batchSql;
	}
	
	public void setBatchSql(List<String> batchSql) {
		this.batchSql = batchSql;
	}
	
	public String batchSql(String tableName,String conditions){
		String sql = SqlMapperUtil.getSqlMapper().getDelete();
		if(StringUtils.isNotEmpty(conditions) && StringUtils.isNotEmpty(tableName)){
			sql = sql.replace("?tableName", tableName)
					.replace("?conditions", conditions);
		}
		return sql;
	}


}
