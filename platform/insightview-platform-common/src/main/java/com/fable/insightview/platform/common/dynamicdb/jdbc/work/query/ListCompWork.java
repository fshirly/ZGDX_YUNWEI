package com.fable.insightview.platform.common.dynamicdb.jdbc.work.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fable.insightview.platform.common.dynamicdb.Column;
import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.BaseWork;
import com.fable.insightview.platform.common.util.CTD;
/**
 * 批量查询组件信息的工作
 * @author 郑自辉
 *
 */
public class ListCompWork extends BaseWork{
	
	private static final Logger logger = LogManager.getLogger();
	
	private List<Map<String, String>> data = new ArrayList<Map<String,String>>();
	
	private String tableName;
	
	private List<String> compIds;
	
	private List<Column> columns;
	
	public ListCompWork(WorkType workType,String tableName,List<String> compIds,List<Column> columns)
	{
		super(workType);
		this.tableName = tableName;
		this.compIds = compIds;
		this.columns = columns;
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	@Override
	protected String sql() {
		if (StringUtils.isEmpty(tableName)
				|| (null == columns || columns.isEmpty())
				|| (null == compIds || compIds.isEmpty()))
		{
			logger.error("拼装组件信息SQL时发生错误，表名={}",tableName);
			return null;
		}
		String sql = SqlMapperUtil.getSqlMapper().getListComp();
		//拼装Select
		StringBuilder select = new StringBuilder();
		for (int i = 0,length = columns.size();i < length;i++)
		{
			Column column = columns.get(i);
			if (null == column)
			{
				continue;
			}
			select.append(column.getColumnName());
			if (i != length - 1)
			{
				select.append(",");
			}
		}
		sql = sql.replace("#{select}", select);
		//拼装From
		sql = sql.replace("#{from}", tableName);
		//拼装Where
		StringBuilder where = new StringBuilder();
		for (int i = 0,size = compIds.size();i < size;i++)
		{
			String compId = compIds.get(i);
			where.append(compId);
			if (i != size - 1)
			{
				where.append(",");
			}
		}
		sql = sql.replace("#{condition}", where);
		return sql.toString();
	}

	@Override
	protected void processData(ResultSet rs) {
		int isMySQL = 0;
		if (CTD.isMySQL()) {
			isMySQL = 1;
		}
		try {
			while (rs.next())
			{
				Map<String, String> row = new HashMap<String, String>();
				if (isMySQL == 1) {
					String val = null;
					for (Column c : columns)
					{ 
//						row.put(c.getColumnName(), StringEscapeUtils.escapeJava(rs.getString(c.getColumnName())));
						// MySql数据库中，反斜杠不会被转义，导致其本身在程序中会被当做转义字符处理，导致错误出现，所以这里做特殊处理
						if (null != rs.getString(c.getColumnName()) && rs.getString(c.getColumnName()).contains("\\")) {
							val = rs.getString(c.getColumnName()).replace("\\", "\\\\");
							row.put(c.getColumnName(), val);
						} else {
							row.put(c.getColumnName(), rs.getString(c.getColumnName()));
						}
					}
				} else {
					for (Column c : columns)
					{
						row.put(c.getColumnName(), rs.getString(c.getColumnName()));
					}
				}
				row.put("CiId", rs.getString("CiId"));
				data.add(row);
			}
		} catch (SQLException e) {
			logger.error("转换组件信息数据时发生错误，组件表名={}",tableName);
		}
	}

}
