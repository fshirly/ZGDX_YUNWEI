package com.fable.insightview.platform.formdesign.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.BasicTransformerAdapter;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.formdesign.dao.IBusinessAttributesDao;
import com.fable.insightview.platform.formdesign.entity.FdBusinessAttributes;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

@Repository("fromdesign.businessAttributesDao")
public class BusinessAttributesDaoImpl extends
		GenericDaoHibernate<FdBusinessAttributes> implements
		IBusinessAttributesDao {

	@Override
	public void deleteByAttributeId(Integer attributeId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "delete from FdBusinessAttributes b where b.attributeId="
				+ attributeId;
		session.createSQLQuery(sql).executeUpdate();
	}

	/**
	 * 查询流程实例列表
	 */
	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public List<Map<String, String>> queryProcessInstanceLst(String processId, List<Integer> processInstanceIds, String title,
			FlexiGridPageInfo flexiGridPageInfo) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select f.columnName,b.attributeTable from FdBusinessAttributes b left join FdFormAttributes f on b.attributeId=f.id where b.businessId='"
				+ processId + "'";
		List<Map<String, String>> list = session.createSQLQuery(sql)
				.setResultTransformer(new BasicTransformerAdapter() {

					@SuppressWarnings("rawtypes")
					@Override
					public Object transformTuple(Object[] tuple,
							String[] aliases) {
						Map result = new HashMap(tuple.length);
						for (int i = 0; i < tuple.length; i++) {
							String alias = aliases[i];
							if (alias != null) {
								result.put(alias.toLowerCase(), tuple[i]);
							}
						}
						return result;
					}

				}).list();

		if (list.size() > 0) {
			// 拼装select
			StringBuilder resultSql = new StringBuilder("select b.processInstanceId,b.title,");
			for (int i = 0; i < list.size(); i++) {
				if (!"title".equalsIgnoreCase(list.get(i).get("columnname")) && list.get(i).get("columnname") != null) {
					resultSql.append(list.get(i).get("columnname"));
					resultSql.append(", ");
				}
			}
			
			List<String> tableList = new ArrayList<String>();
			for (Map<String, String> map : list) {
				if (!tableList.contains(map.get("attributetable"))) {
					tableList.add(map.get("attributetable"));
				}
			}
			
			if (!tableList.isEmpty()) {
				resultSql.append(tableList.get(0) + ".id");
			}
			
			// 拼装from
			resultSql.append(" from ");
			for (int i = 0; i < tableList.size(); i++) {
				resultSql.append(tableList.get(i));
				if (i < tableList.size() - 1) {
					resultSql.append(", ");
				}
			}
			resultSql.append(" left join BusinessInProcess b on b.businessId=" + tableList.get(tableList.size()-1) + ".id");
			resultSql.append(" where b.businessType=7 ");
			// 拼装where
			if (tableList.size() > 1) {
				resultSql.append("and ");
				if (null != title && !title.isEmpty()) {
					resultSql.append(" b.title like '%" + title + "%' and "); // 查询条件title
				}
				for (int i = 0; i < tableList.size(); i++) {
					if (i < tableList.size() - 1) {
						resultSql.append(tableList.get(i) + ".id="
								+ tableList.get(i + 1) + ".id");
					}
					if (i < tableList.size() - 2) {
						resultSql.append(" and ");
					}
				}
			}
			if (processInstanceIds.size() > 0) {
				resultSql.append(" and b.processinstanceid in (");
				for (int i = 0; i < processInstanceIds.size(); i++) {
					if (i < processInstanceIds.size() -1) {
						resultSql.append(processInstanceIds.get(i) + ", ");
					} else {
						resultSql.append(processInstanceIds.get(i) + ")");
					}
				}
			}

			SQLQuery sqlQuery = session.createSQLQuery(resultSql.toString());
			sqlQuery.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
					.getRp()));
			sqlQuery.setMaxResults(flexiGridPageInfo.getRp());

			return (List<Map<String, String>>) sqlQuery.setResultTransformer(new BasicTransformerAdapter() {
				@Override
				public Object transformTuple(Object[] tuple,
						String[] aliases) {
					Map<String, String> result = new HashMap<String,String>(tuple.length);
					for (int i = 0; i < tuple.length; i++) {
						String alias = aliases[i];
						if (alias != null) {
							result.put(alias.toLowerCase(),(null == tuple[i] ? null : tuple[i].toString()));
						}
					}
					return result;
				}

			}).list();
		} else {
			return null;
		}
		
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public int queryProcessInstanceCount(String processId, List<Integer> processInstanceIds, String title) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select f.columnName,b.attributeTable from FdBusinessAttributes b left join FdFormAttributes f on b.attributeId=f.id where b.businessId='"
				+ processId + "'";
		List<Map<String, String>> list = session.createSQLQuery(sql)
				.setResultTransformer(new BasicTransformerAdapter() {

					@SuppressWarnings("rawtypes")
					@Override
					public Object transformTuple(Object[] tuple,
							String[] aliases) {
						Map result = new HashMap(tuple.length);
						for (int i = 0; i < tuple.length; i++) {
							String alias = aliases[i];
							if (alias != null) {
								result.put(alias.toLowerCase(), tuple[i]);
							}
						}
						return result;
					}

				}).list();

		if (list.size() > 0){
			// 拼装select
			StringBuilder resultSql = new StringBuilder("select count(*) ");
			// 拼装from
			resultSql.append(" from ");
			List<String> tableList = new ArrayList<String>();
			for (Map<String, String> map : list) {
				if (!tableList.contains(map.get("attributetable"))) {
					tableList.add(map.get("attributetable"));
				}
			}
			for (int i = 0; i < tableList.size(); i++) {
				resultSql.append(tableList.get(i));
				if (i < tableList.size() - 1) {
					resultSql.append(", ");
				}
			}
			resultSql.append(" left join BusinessInProcess b on b.businessId=" + tableList.get(tableList.size()-1) + ".id");
			resultSql.append(" where b.businessType=7 ");
			// 拼装where
			if (tableList.size() > 1) {
				resultSql.append("and ");
				if (null != title && !title.isEmpty()) {
					resultSql.append(" b.title like '%" + title + "%' and "); // 查询条件title
				}
				for (int i = 0; i < tableList.size(); i++) {
					if (i < tableList.size() - 1) {
						resultSql.append(tableList.get(i) + ".id="
								+ tableList.get(i + 1) + ".id");
					}
					if (i < tableList.size() - 2) {
						resultSql.append(" and ");
					}
				}
			}
			if (processInstanceIds.size() > 0) {
				resultSql.append(" and b.processinstanceid in (");
				for (int i = 0; i < processInstanceIds.size(); i++) {
					if (i < processInstanceIds.size() -1) {
						resultSql.append(processInstanceIds.get(i) + ", ");
					} else {
						resultSql.append(processInstanceIds.get(i) + ")");
					}
				}
			}

			SQLQuery sqlQuery = session.createSQLQuery(resultSql.toString());

			return Integer.valueOf(sqlQuery.uniqueResult().toString());
		} else {
			return 0;
		}
		
	}

}
