package com.fable.insightview.platform.common.dynamicdb.jdbc.work.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fable.insightview.platform.common.dynamicdb.Column;
import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.BaseWork;

/**
 * 查询表字段
 * @author 郑自辉
 *
 */
public class ListColumnWork extends BaseWork{
	
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 查询出的字段集合
	 */
	private List<Column> columns = new ArrayList<Column>();

	private String[] tableName;
	
	public ListColumnWork(WorkType workType,String[] tableName)
	{
		super(workType);
		this.tableName = tableName;
	}
	
	public List<Column> getColumns() {
		return columns;
	}

	@Override
	protected String sql() {
		String listColumnSql = SqlMapperUtil.getSqlMapper().getListcolumn();
		StringBuilder param = new StringBuilder();
		if (null != tableName && tableName.length > 0)
		{
			for (int i = 0,length = tableName.length;i < length;i++)
			{
				param.append("'").append(tableName[i]).append("'");
				if (i != length -1)
				{
					param.append(",");
				}
			}
		}
		listColumnSql = listColumnSql.replace("?", param.toString());
		return listColumnSql;
	}

	@Override
	protected void processData(ResultSet rs) {
		Column column = null;
		try {
			while(rs.next())
			{
				column = new Column();
				column.setAttributeId(rs.getInt("attributeId"));
				column.setDataInitValue(rs.getInt("dataInitValue"));
				column.setWidgetValue(rs.getString("widgetValue"));
				column.setColumnName(rs.getString("columnname"));
				column.setDataType(rs.getString("datatype"));
				column.setIsTableDisplay(rs.getInt("isTableDisplay"));
				column.setTableName(rs.getString("Tablename"));
				column.setAttributeName(rs.getString("attributeName"));
				columns.add(column);
			}
		} catch (SQLException e) {
			logger.error("查询表字段错误，表：{}",(Object[])tableName);
		}
	}
}
