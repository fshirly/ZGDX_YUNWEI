package com.fable.insightview.platform.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.IOrganizationalEntityDao;
import com.fable.insightview.platform.entity.Dict;
import com.fable.insightview.platform.entity.OrganizationalEntityBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

/**
 * 单位组织Dao
 * 
 * @author 武林
 * 
 */
@Repository("organizationalEntityDao")
public class OrganizationalEntityDaoImpl extends
		GenericDaoHibernate<OrganizationalEntityBean> implements
		IOrganizationalEntityDao {

	/*
	 * 新增单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addOrganization(
			OrganizationalEntityBean organizationalEntityBean) {
		try {
			super.insert(organizationalEntityBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<OrganizationalEntityBean> getAllOrganizationalEntity() {
		String hql = "from OrganizationalEntityBean  org where 1 = 1 ";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		return query.list();
	}

	

}
