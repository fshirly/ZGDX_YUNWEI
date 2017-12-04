package com.fable.insightview.platform.formdesign.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.formdesign.dao.IBusinessFormsDao;
import com.fable.insightview.platform.formdesign.entity.FdBusinessForms;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

@Repository("fromdesign.businessFormsDao")
public class BusinessFormsDaoImpl extends GenericDaoHibernate<FdBusinessForms>
		implements IBusinessFormsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<FdBusinessForms> getListByBusinessNodeId(String businessNodeId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(FdBusinessForms.class, "e");
		if (null != businessNodeId && !businessNodeId.equals("-1")) {
			criteria.add(Restrictions.eq("businessNodeId", businessNodeId));
		}
		
		return criteria.list();
	}

}
