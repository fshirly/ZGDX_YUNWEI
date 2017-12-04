package com.fable.insightview.platform.common.dynamicdb.jdbc.work;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
/**
 * 生成主键的工作
 * @author 程大伟
 *
 */
public class QueryPrimaryIdWork  extends BaseWork {
	private static final Logger logger = LogManager.getLogger();
	
	private Integer id;
	
	private String tableName;
	
	/**
	 * 生成主键
	 * @param workType 工作类型
	 */
	public QueryPrimaryIdWork(WorkType workType, String tableName) {
		super(workType);
		this.tableName = tableName;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String sql() {
		String sql = SqlMapperUtil.getSqlMapper().getQueryPrimaryId();
		sql = sql.replace("?tableName", tableName);
		return sql;
	}
	
	@Override
	protected void processData(ResultSet rs){
		try {
			while(rs.next()){
				this.id = rs.getInt("CiId");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQL执行语句错误",e);
		}
	}
	
	public Integer getId() {
		return id;
	}
}
