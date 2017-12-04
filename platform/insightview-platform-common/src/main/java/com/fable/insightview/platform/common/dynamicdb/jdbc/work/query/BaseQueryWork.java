package com.fable.insightview.platform.common.dynamicdb.jdbc.work.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fable.insightview.platform.common.dynamicdb.Column;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.BaseWork;
/**
 * 查询工作的基类
 * 主要用于单行查询和多行查询
 * 处理共用SQL语句的拼接
 * @author 郑自辉
 *
 */
public abstract class BaseQueryWork extends BaseWork {
	
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 单行查询的数据
	 */
	private Map<String, String> data = new HashMap<String, String>();
	
	/**
	 * 要查询的表名
	 */
	protected String[] tableName;
	
	/**
	 * 要查询的字段
	 */
	protected List<Column> columns;
	
	public BaseQueryWork(WorkType workType,String[] tableName,List<Column> columns)
	{
		super(workType);
		this.tableName = tableName;
		this.columns = columns;
	}

	public Map<String, String> getData() {
		return data;
	}

	@Override
	protected String sql() {
		//modify code by sanyou time：2014年9月12日 下午3:57:03
		//Begin Place
		if (null == tableName || tableName.length == 0) {
			logger.error("查询组装SQL语句时发生错误，表名={}", (Object[]) tableName);
			return null;
		}
		if (null == columns) {
			columns = new ArrayList<Column>();
		}
		columns.add(new Column("CiId", 0));
		//End Place
		String qSql = selectSql();
		//拼装Select
		StringBuilder selectColumns = new StringBuilder();
		for (int i = 0,size = columns.size();i < size;i++)
		{
			Column column = columns.get(i);
			if (null == column)
			{
				continue;
			}
			if ("CiId".equals(column.getColumnName()))
			{
				selectColumns.append(tableName[0]).append(".CiId");
			}
			else
			{
				selectColumns.append(column.getTableName()).append(".").append(column.getColumnName());
			}
			if (i != size -1)
			{
				selectColumns.append(",");
			}
		}
		qSql = qSql.replace("#{select}", selectColumns.toString());
		//拼装From
		StringBuilder froms = new StringBuilder();
		for (int i = 0,length = tableName.length;i < length;i++)
		{
			froms.append(tableName[i] + " " + tableName[i]);
			if (i != length - 1)
			{
				froms.append(",");
			}
		}
		qSql = qSql.replace("#{from}", froms.toString());
		//拼装Where
		StringBuilder wheres = new StringBuilder();
		for (int i = 0,length = tableName.length;i < length;i++)
		{
			if (i > 0)
			{
				wheres.append(tableName[i - 1]).append(".CiId=").append(tableName[i]).append(".CiId");
				wheres.append(" and ");
			}
			if (i == length - 1)
			{
				wheres.append(genWhere(tableName[i]));
			}
		}
		qSql = qSql.replace("#{where}", wheres.toString());
		logger.info("组装查询语句：{}",qSql);
		return qSql;
	}
	
	@Override
	protected void processData(ResultSet rs) {
		try {
			while(rs.next())
			{
				for (Column c : columns)
				{
					data.put(c.getColumnName(), rs.getString(c.getColumnName()));
				}
			}
		} catch (SQLException e) {
			logger.error("查询结果转换失败,表名：{}",(Object[])tableName);
		}
	}

	/**
	 * 处理查询的基本SQL，可能为单行
	 * 也可能为多行，具体由子类提供
	 * @return String 基本查询SQL语句
	 */
	protected abstract String selectSql();
	
	/**
	 * 单行和多行查询where条件的具体处理
	 * 有子类提供
	 * @param tableName 表名
	 * @return String where条件语句
	 */
	protected abstract String genWhere(String tableName);
}
