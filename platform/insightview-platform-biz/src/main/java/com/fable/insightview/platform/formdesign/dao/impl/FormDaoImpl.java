package com.fable.insightview.platform.formdesign.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.BasicTransformerAdapter;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.common.dynamicdb.jdbc.work.BaseWork.WorkType;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.InsertWork;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.QueryPrimaryIdWork;
import com.fable.insightview.platform.common.util.CTD;
import com.fable.insightview.platform.formdesign.dao.IFormDao;
import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

@Repository("fromdesign.formDao")
public class FormDaoImpl extends GenericDaoHibernate<FdForm> implements
		IFormDao {

	private static final Logger logger = LogManager.getLogger();

	@Override
	public FdForm getByFormId(String formId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Criteria criteria = session.createCriteria(FdForm.class, "e");
		criteria.add(Restrictions.eq("formId", formId));

		return (FdForm) criteria.uniqueResult();
	}

	/**
	 * Save the form instance information
	 * 
	 * @author Maowei
	 */
	@Override
	public int saveFormInstanceInfo(String id, String tableName,
			Map<String, String> attrsMap) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		// 字段名称
		List<String> columnNameList = new ArrayList<String>();
		String columnNames = null;
		// 字段值
		List<String> columnValList = new ArrayList<String>();
		String columnVals = null;
		if (StringUtils.isEmpty(id)) {
			if (CTD.isMySQL()) {
				QueryPrimaryIdWork work = new QueryPrimaryIdWork(
						WorkType.SELECT, tableName);
				session.doWork(work);
				id = String.valueOf(work.getId());
			} else if (CTD.isOracle()) {
				id = String.valueOf(session.createSQLQuery(
						"SELECT " + "seq" + tableName + ".nextval FROM DUAL")
						.uniqueResult());
			}
		}

		if (null != id) {
			// 给主键赋值
			columnNameList.add("id");
			columnValList.add(id);
			for (String columnName : attrsMap.keySet()) {
				if (null != columnName && !"".equals(columnName)) {
					columnNameList.add(columnName);
					columnValList.add(attrsMap.get(columnName));
				}
			}
			columnNames = this.castList(columnNameList);
			columnVals = this.castListToVal(columnValList);

			InsertWork insertWork = new InsertWork(WorkType.INSERT, tableName,
					columnNames, columnVals);
			session.doWork(insertWork);
		} else {
			logger.info("动态表参数值为空，保存失败！");
		}

		return Integer.valueOf(id);

	}

	private String castList(List<String> list) {
		return list.toString().replace("[", "").replace("]", "");
	}

	private String castListToVal(List<String> list) {
		StringBuilder result = new StringBuilder();
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				result.append("'").append(list.get(i)).append("'");
				if (i != list.size() - 1) {
					result.append(",");
				}
			}
		}
		return result.toString();
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public Map<String, String> queryFormInstanceInfo(String tableName, int id) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select * from " + tableName + " t where t.id=" + id;
		return (Map<String, String>) session.createSQLQuery(sql)
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

				}).uniqueResult();
	}

	/**
	 * Update the form instance information
	 * 
	 * @author Maowei
	 */
	@Override
	public void updateFormInstanceInfo(int id, String tableName,
			Map<String, String> formData) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		if (id > 0) {
			StringBuilder sql = new StringBuilder("update ");
			sql.append(tableName);
			sql.append(" set ");
			for (Iterator<String> it = formData.keySet().iterator(); it
					.hasNext();) {
				String columnName = it.next();
				sql.append(columnName + "='" + formData.get(columnName) + "'");
				if (it.hasNext()) {
					sql.append(",");
				}
			}
			sql.append(" where id=" + id);

			session.createSQLQuery(sql.toString()).executeUpdate();
		} else {
			logger.info("动态表id值为空，更新失败！");
		}
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public List<Map<String, String>> valueInit(String initSQL) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return (List<Map<String, String>>) session.createSQLQuery(initSQL)
				.setResultTransformer(new BasicTransformerAdapter() {

					@Override
					public Object transformTuple(Object[] tuple,
							String[] aliases) {
						Map<String, String> result = new HashMap<String, String>(tuple.length);
						for (int i = 0; i < tuple.length; i++) {
							String alias = aliases[i];
							if (alias != null) {
								result.put(alias.toLowerCase(), tuple[i].toString());
							}
						}
						return result;
					}

				}).list();
	}

	/**
	 * 查询人员的关联信息（关联控件）
	 */
	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public Map<String, Object> findLinkFieldsForUser(String linkSql,
			String userId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = linkSql + userId;
		return (Map<String, Object>) session.createSQLQuery(sql)
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

				}).uniqueResult();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Map<String, Object>> queryLinkList(String sql,
			Map<String, String> queryInfo, FlexiGridPageInfo flexiGridPageInfo) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		if (null != queryInfo && !queryInfo.isEmpty()) {
			for (String key : queryInfo.keySet()) {
				if (!key.isEmpty() && !queryInfo.get(key).isEmpty()) {
					sql += " and " + key + " like '%" + queryInfo.get(key)
							+ "%'";
				}
			}
		}
		SQLQuery query = session.createSQLQuery(sql);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());

		return (List<Map<String, Object>>) query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public int queryCountForLink(String sql, Map<String, String> queryInfo) {
		String disPart = sql.substring(sql
				.toLowerCase().indexOf("from"),sql.length());
		String realSql = "select count(*) " + disPart;
		
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		if (null != queryInfo && !queryInfo.isEmpty()) {
			for (String key : queryInfo.keySet()) {
				if (!key.isEmpty() && !queryInfo.get(key).isEmpty()) {
					realSql += " and " + key + " like '%" + queryInfo.get(key)
							+ "%'";
				}
			}
		}
		SQLQuery query = session.createSQLQuery(realSql);
		
		return Integer.valueOf(query.uniqueResult().toString());
	}

}
