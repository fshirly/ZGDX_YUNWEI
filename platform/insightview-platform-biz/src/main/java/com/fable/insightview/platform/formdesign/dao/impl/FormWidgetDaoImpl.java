package com.fable.insightview.platform.formdesign.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.formdesign.dao.IFormWidgetDao;
import com.fable.insightview.platform.formdesign.entity.FdWidgetType;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

/**
 * 表单控件的Dao
 * 
 * @author Maowei
 * 
 */
@Repository("fromdesign.formWidgetDao")
public class FormWidgetDaoImpl extends GenericDaoHibernate<FdWidgetType>
		implements IFormWidgetDao {

	/**
	 * 查询所有表单控件类型
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FdWidgetType> getAllFormWidgetType() {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Criteria criteria = session.createCriteria(FdWidgetType.class, "e");

		return criteria.list();
	}

	/**
	 * 根据Category，查询表单控件类型
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FdWidgetType> getByCategory(Integer category) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Criteria criteria = session.createCriteria(FdWidgetType.class, "e");
		criteria.add(Restrictions.eq("category", category));

		return criteria.list();
	}

	/**
	 * 根据WidgetType，查询表单控件类型
	 */
	@Override
	public FdWidgetType getByWidgetType(String widgetType) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Criteria criteria = session.createCriteria(FdWidgetType.class, "e");
		criteria.add(Restrictions.eq("widgetType", widgetType));

		return (FdWidgetType) criteria.uniqueResult();
	}

}
