package com.fable.insightview.platform.formdesign.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.formdesign.dao.IFormDefaultDao;
import com.fable.insightview.platform.formdesign.entity.FdFormDefault;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

/**
 * 特殊属性初始化值预置表Dao
 * 
 * @author zhengz
 * 
 */
@Repository("fromdesign.formDefaultDao")
public class FormDefaultDaoImpl extends GenericDaoHibernate<FdFormDefault>
		implements IFormDefaultDao {

	@Override
	public List<FdFormDefault> getByWidgetType(String widgetType) {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Criteria criteria = session.createCriteria(FdFormDefault.class, "e");
		criteria.add(Restrictions.eq("widgetType", widgetType));

		return criteria.list();
	}


}
