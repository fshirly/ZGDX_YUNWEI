package com.fable.insightview.platform.common.dynamicdb.jdbc.work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jdbc.Work;
/**
 * 工作单元的基类，封装sql处理流程
 * 主要提供基本的sql执行模板
 * @author 郑自辉
 *
 */
public abstract class BaseWork implements Work{
	
	private static final Logger logger = LogManager.getLogger();
	
	private WorkType workType;
	
	/**
	 * 构造基础工作
	 * @param workType 工作类型
	 */
	public BaseWork(WorkType workType)
	{
		this.workType = workType;
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		String sql = sql();
		if (StringUtils.isEmpty(sql))
		{
			logger.error("查询语句为空，操作类型：{}",workType);
			return;
		}
		PreparedStatement ps = connection.prepareStatement(sql);
		switch (workType)
		{
			case CREATE:
			case DROP:
			case ADD:
			case REMOVE:
				ps.execute();
				break;
			case INSERT:
			case UPDATE:
			case DELETE:
				ps.executeUpdate();
				break;
			case BATCH:
				List<String> batchSql = batchSql();
				if (null != batchSql && !batchSql.isEmpty())
				{
					for (String batch : batchSql)
					{
						ps.addBatch(batch);
					}
				}
				ps.executeBatch();
				break;
			case SELECT:
				ResultSet resultSet = ps.executeQuery();
				processData(resultSet);
				resultSet.close();
				break;
		}
		ps.close();
	}

	/**
	 * 组装执行的SQL语句
	 * @return String 要执行的sql语句
	 */
	protected abstract String sql();
	
	/**
	 * 将ResultSet原始数据处理为最终数据
	 * @param rs 查询返回的原始数据
	 */
	protected void processData(ResultSet rs)
	{
		
	}
	
	/**
	 * 批量SQL
	 * @return List<String> 需要批量执行的SQL语句
	 */
	protected List<String> batchSql()
	{
		return Collections.emptyList();
	}
	
	/**
	 * 操作类型
	 * @author 郑自辉
	 *
	 */
	public static enum WorkType
	{
		/**
		 * 建表
		 */
		CREATE,
		/**
		 * 删表
		 */
		DROP,
		/**
		 * 添加字段
		 */
		ADD,
		/**
		 * 删除字段
		 */
		REMOVE,
		/**
		 * 添加数据
		 */
		INSERT,
		/**
		 * 删除数据
		 */
		DELETE,
		/**
		 * 更新数据
		 */
		UPDATE,
		/**
		 * 查询数据
		 */
		SELECT,
		/**
		 * 批量操作
		 */
		BATCH
	}
}
