package com.fable.insightview.platform.common.dynamicdb.jdbc.work;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fable.insightview.platform.common.dynamicdb.api.AttrInitData;

/**
 * 绑定数据库方式获取属性初始化值的工作
 * @author 郑自辉
 *
 */
public class DataInitByDBWork extends BaseWork{
	
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 最终初始化数据
	 */
	private List<AttrInitData> data = new ArrayList<AttrInitData>();
	
	private String tableName;
	
	private String labelColumn;
	
	private String valueColumn;
	
	/**
	 * 构造器
	 * @param workType 工作类型
	 * @param tableName 表名
	 * @param labelColumn 文本字段
	 * @param valueColumn 值字段
	 */
	public DataInitByDBWork(WorkType workType,String tableName,String labelColumn,String valueColumn)
	{
		super(workType);
		this.tableName = tableName;
		this.labelColumn = labelColumn;
		this.valueColumn = valueColumn;
	}

	public List<AttrInitData> getData() {
		return data;
	}

	@Override
	protected String sql() {
		StringBuilder sql = new StringBuilder();
		sql.append("select ")
			.append(labelColumn)
			.append(",")
			.append(valueColumn)
			.append(" ")
			.append("from ")
			.append(tableName)
			.append(" ")
			.append("where 1=1 ");
		return sql.toString();
	}

	@Override
	protected void processData(ResultSet rs) {
		try {
			while (rs.next())
			{
				AttrInitData row = new AttrInitData();
				row.setValue(rs.getInt(valueColumn));
				row.setLabelText(rs.getString(labelColumn));
				data.add(row);
			}
		} catch (SQLException e) {
			logger.error("绑定数据库获取属性初始化值失败，表名：{}，文本字段：{}，值字段：{}", 
					tableName,labelColumn,valueColumn);
		}
	}

}
