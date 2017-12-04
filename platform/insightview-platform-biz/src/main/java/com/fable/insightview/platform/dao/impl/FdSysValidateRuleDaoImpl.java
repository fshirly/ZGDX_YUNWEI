package com.fable.insightview.platform.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.IFdSysValidateRuleDao;
import com.fable.insightview.platform.entity.FdSysValidateRule;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

@Repository("fdSysValidateRuleDao")
public class FdSysValidateRuleDaoImpl extends
		GenericDaoHibernate<FdSysValidateRule> implements IFdSysValidateRuleDao {

	@Override
	public List<FdSysValidateRule> queryAllList() {
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Criteria criteria = sess.createCriteria(FdSysValidateRule.class);
		return criteria.list();
	}
}
