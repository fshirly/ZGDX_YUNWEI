package com.fable.insightview.platform.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.ItsmEventTypeDictDao;
import com.fable.insightview.platform.entity.ItsmEventTypeDict;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

@Repository("itsmEventTypeDictDao")
public class ItsmEventTypeDictDaoImpl
		extends
			GenericDaoHibernate<ItsmEventTypeDict>
		implements
			ItsmEventTypeDictDao {

	/**
	 * 根据id查询ItsmEventType表字典项
	 */
	@SuppressWarnings("unchecked")
	public List<ItsmEventTypeDict> getEventTypeItems(Integer id) {
		Session sess = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteria = sess.createCriteria(ItsmEventTypeDict.class);
		criteria.add(Restrictions.eq("parentTypeId", id));
		List<ItsmEventTypeDict> list = criteria.list();  
		return list;
	}

}
