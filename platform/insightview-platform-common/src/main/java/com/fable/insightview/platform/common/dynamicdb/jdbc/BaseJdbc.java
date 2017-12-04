package com.fable.insightview.platform.common.dynamicdb.jdbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.common.dynamicdb.Column;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.BaseWork.WorkType;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.query.ListColumnWork;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.query.ListTotalWork;
import com.fable.insightview.platform.itsm.core.dao.hibernate.UniversalDaoHibernate;
/**
 * Jdbc实现基类
 * @author 郑自辉
 *
 */
@Repository("baseJdbc")
public class BaseJdbc extends UniversalDaoHibernate {
	
	/**
	 * 字段在列表显示的标识
	 */
	protected static final int TABLE_DISPLAY_COLUMN = 1;

	/**
	 * 查询表的字段
	 * @param tableName 数组结构，要查询字段的表名
	 * @return List<Column> 返回表的字段
	 */
	protected List<Column> listColumns(String... tableName)
	{
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		ListColumnWork work = new ListColumnWork(WorkType.SELECT, tableName);
		session.doWork(work);
		return work.getColumns();
	}
	
	/**
	 * 在列表显示的字段
	 * @param tableName 配置项类型对应表名
	 * @return List<Column> 需要在列表显示的字段
	 */
	protected List<Column> listDisplayColumns(String...tableName )
	{
		List<Column> columns = listColumns(tableName);
		if (null == columns || columns.isEmpty())
		{
			return Collections.emptyList();
		}
		List<Column> result = new ArrayList<Column>();
		for (Column column : columns)
		{
			if (null == column)
			{
				continue;
			}
			if (column.getIsTableDisplay() == TABLE_DISPLAY_COLUMN)
			{
				result.add(column);
			}
		}
		return result;
	}
	
	/**
	 * 获取列表分页查询总条数
	 * @param typeId 配置项类型ID
	 * @param conditions 查询条件，Map结构，key为字段名，value为查询关键字
	 * @return int 记录总数
	 */
	protected int listTotal(String typeId,Map<String, String> conditions)
	{
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		ListTotalWork work = new ListTotalWork(WorkType.SELECT, typeId, conditions);
		session.doWork(work);
		return work.getTotal();
	}
}
