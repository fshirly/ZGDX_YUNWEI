package com.fable.insightview.platform.formdesign.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.formdesign.dao.IFormAttributesDao;
import com.fable.insightview.platform.formdesign.entity.FdFormAttributes;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

@Repository("fromdesign.formAttributesDao")
public class FormAttributesDaoImpl extends
		GenericDaoHibernate<FdFormAttributes> implements IFormAttributesDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<FdFormAttributes> getByFormId(Integer formId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(FdFormAttributes.class, "e");
		criteria.add(Restrictions.eq("formId", formId));
		
		return criteria.list();
	}

}
