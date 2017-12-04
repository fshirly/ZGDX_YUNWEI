/**
 * 
 */
package com.fable.insightview.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.ISystemParamDao;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.entity.SystemParamBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

/**
 * 
 * @author wul
 * 
 */
@Repository("systemParamDao")
public class SystemParamDaoImpl extends GenericDaoHibernate<SystemParamBean>
		implements ISystemParamDao {
	public String commonConditions(String hql, SystemParamBean systemParamBean) {
		if (null != systemParamBean.getParamName()
				&& !"".equals(systemParamBean.getParamName())) {
			hql += " and org.paramName LIKE  '%"
					+ systemParamBean.getParamName() + "%'";
		}
		if (null != systemParamBean.getParamClass()
				&& !"".equals(systemParamBean.getParamClass())) {
			hql += " and org.paramClass LIKE '%"
					+ systemParamBean.getParamClass() + "%'";
		}
		return hql;
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@Override
	public List<SystemParamBean> querySystemParam(
			SystemParamBean systemParamBean) {
		String hql = "from SystemParamBean  org where 1=1";

		hql = commonConditions(hql, systemParamBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);

		List<SystemParamBean> systemParamLst = query.list();
		return systemParamLst;
	}

	/*
	 * 总记录数
	 * 
	 * @author wsp
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemParamBean> sysParamInfo(SystemParamBean systemParamBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		String hql = "from SystemParamBean  org where 1=1";

		hql = commonConditions(hql, systemParamBean);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query
				.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
						.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		List<SystemParamBean> systemParamLst = query.list();

		return systemParamLst;

	}

	
	/**
	 * 获取总数
	 * 
	 * @param systemParamBean
	 * @return
	 */
	@Override
	public int getTotalCount(SystemParamBean systemParamBean) {
		String hql = "select count(1)  count from SystemParam  org where 1=1";

		hql = commonConditions(hql, systemParamBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();

		return count;

	}

	/**
	 * 修改
	 */
	@Override
	public int updateDepartmentBean(String paramValue,int parID) {
		int rtVal = 0;
		try {
			String hql = "UPDATE SystemParam SET ParamValue = '"+paramValue+"' WHERE ParamID ="+parID;
			SQLQuery query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(hql);
			rtVal = query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return rtVal;
		
		
	}
	/**
	 * 根据参数ID获取相关信息 用于修改
	 * 
	 * @param paramID
	 * @return
	 */

	@Override
	public List<SystemParamBean> getSysParamBeanByConditions(String paramName,
			String paramValue) {
		String hql = "from SystemParamBean  org where 1=1 ";
		if (null != paramValue && !"".equals(paramValue)) {
			hql += " and " + paramName + "='" + paramValue + "'";
		}

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SystemParamBean> sysParamInfo = query.list();
		return sysParamInfo;
	}

}
