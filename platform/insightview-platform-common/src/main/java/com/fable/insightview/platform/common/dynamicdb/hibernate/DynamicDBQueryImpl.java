package com.fable.insightview.platform.common.dynamicdb.hibernate;

import static com.fable.insightview.platform.common.dynamicdb.hibernate.DynamicDBConstants.CUSTOM_COMPONENT_NAME;
import static com.fable.insightview.platform.common.dynamicdb.hibernate.DynamicDBConstants.PRIMARY_KEY_NAME;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.Property;

import com.fable.insightview.platform.common.dynamicdb.Column;
import com.fable.insightview.platform.common.dynamicdb.api.AttrInitData;
import com.fable.insightview.platform.common.dynamicdb.api.IDynamicDBQuery;
/**
 * 动态模型数据操作的具体实现
 * 主要实现新增数据、删除数据、修改数据和数据查询
 * 注意：Hibernate的动态模型不需要具体的POJO或者JavaBean，所有的
 * 数据映射结构均为Map结构(类似DOM4J的树形结构)
 * @author 郑自辉
 *
 */
public class DynamicDBQueryImpl extends DynamicDBImpl implements IDynamicDBQuery{

	public String save(Map<String, Object> params,String... tableName) {
		Session session = DynamicDBUtil.getInstance().getCurrentSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(CUSTOM_COMPONENT_NAME, params);
		Serializable result = session.save(tableName[0], data);
		tx.commit();
		session.close();
		return result.toString();
	}

	public void delete(String id,String... tableName) {
		Session session = DynamicDBUtil.getInstance().getCurrentSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String querySql = "delete from " + tableName[0] + " where " + PRIMARY_KEY_NAME + "=" + id;
		session.createSQLQuery(querySql).executeUpdate();
		tx.commit();
		session.close();
	}

	public void update(Map<String, Object> params,String... tableName) {
		Session session = DynamicDBUtil.getInstance().getCurrentSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(CUSTOM_COMPONENT_NAME, params);
		data.put(PRIMARY_KEY_NAME,params.get(PRIMARY_KEY_NAME));
		session.update(tableName[0], data);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> query(String id,String... tableName) {
		Session session = DynamicDBUtil.getInstance().getCurrentSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String querySql = "from " + tableName[0] + " where " + PRIMARY_KEY_NAME + "=" + id;
		Map<String, Object> result = (Map<String, Object>) session.createQuery(querySql).uniqueResult();
		tx.commit();
		session.close();
		return convertQueryResult(tableName[0],result);
	}

	public Map<String, Object> list(String typeId,int start,int pageSize,String... tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> list(String typeId,Map<String, String> condition,int start,int pageSize,String... tableName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 转换查询到的数据
	 * 这里统一转换成String类型供前台展现
	 * @param src
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> convertQueryResult(String tableName,Map<String, Object> src)
	{
		Map<String, String> dest = new HashMap<String, String>();
		//加入主键
		dest.put(PRIMARY_KEY_NAME, src.get(PRIMARY_KEY_NAME).toString());
		Component customProperties = getCustomProperties(tableName);
		Iterator<Property> pI = customProperties.getPropertyIterator();
		while(pI.hasNext())
		{
			Property property = pI.next();
			String name = property.getName();
			String type = property.getType().getName();
			System.out.println(name + "-----" + type);
			Map<String, Object> customResult = (Map<String, Object>) src.get(CUSTOM_COMPONENT_NAME);
			dest.put(name, customResult.get(name).toString());
		}
		return dest;
	}

	@Override
	public List<AttrInitData> initByDB(String tableName,
			String labelColumn, String valueColumn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer queryPrimaryIdWork(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> listComp(List<String> compIds,
			String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Column> listColumn(String... tableName) {
		// TODO Auto-generated method stub
		return null;
	}
}
