package com.fable.insightview.platform.snmpcommunity.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmpcommunity.dao.ISnmpCommunityDao;
import com.fable.insightview.platform.snmpcommunity.entity.MODeviceBean;
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;

@Repository("snmpCommunityDao")
public class SnmpCommunityDaoImpl extends
		GenericDaoHibernate<SNMPCommunityBean> implements ISnmpCommunityDao {

	@Override
	public List<SNMPCommunityBean> getSnmpCommunityByConditions(
			SNMPCommunityBean snmpBean, FlexiGridPageInfo flexiGridPageInfo) {
		StringBuffer sbfSql= new StringBuffer();
		sbfSql.append("  select * from SNMPCommunity org where 1=1");
		System.out.println(sbfSql.toString());
		if (null != snmpBean.getAlias()
				&& !"".equals(snmpBean.getAlias())) {
			sbfSql.append(" and org.alias LIKE '%" + snmpBean.getAlias() + "%'");
		}
		//创建session对象
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		/*分页查询 */
		SQLQuery query = session.createSQLQuery(sbfSql.toString()).addScalar("id", Hibernate.INTEGER)
				.addScalar("snmpVersion", Hibernate.INTEGER).addScalar("alias",Hibernate.STRING).addScalar("readCommunity",Hibernate.STRING)
				.addScalar("writeCommunity",Hibernate.STRING).addScalar("snmpPort", Hibernate.INTEGER);
		//设置分页参数
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		//获取分页数据
		List<SNMPCommunityBean> list = query.setResultTransformer(Transformers.aliasToBean(SNMPCommunityBean.class)).list();		
		
		//结果放入分页对象中
		return list;
	
	}

	@Override
	public int getTotalCount(SNMPCommunityBean snmpBean) {
		StringBuffer sbfSql= new StringBuffer();
		sbfSql.append("  select count(1) from SNMPCommunity org where 1=1");	
		if (null != snmpBean.getAlias()	&& !"".equals(snmpBean.getAlias())) {
			sbfSql.append(" AND org.alias LIKE '%" + snmpBean.getAlias() + "%'");
		}
		SQLQuery query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sbfSql.toString());
		int count = ((Number) query.uniqueResult()).intValue();

		return count;
	}

	@Override
	public boolean addSnmpCommunity(SNMPCommunityBean snmpBean) {
		try {
			super.insert(snmpBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delSnmpCommunityById(SNMPCommunityBean snmpBean) {
		try {
			super.delete(snmpBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateSnmpCommunity(SNMPCommunityBean snmpBean) {
		try {
			super.update(snmpBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean getSnmpCommunityByConditions(SNMPCommunityBean snmpBean) {
		String hql =  "from SNMPCommunityBean as org where 1=1 and readCommunity='"
					+ snmpBean.getReadCommunity() + "' and snmpVersion="+snmpBean.getSnmpVersion() + " and snmpPort=" + snmpBean.getSnmpPort();
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SNMPCommunityBean> snmpLst = query.list();
		if (snmpLst.size() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<MODeviceBean> getMoIDBymoName(String moName) {
		List<MODeviceBean> snmpInfo = new ArrayList<MODeviceBean>();
		String hql = "from MODeviceBean  WHERE MOName LIKE '%" + moName + "%' ";
		// String hql = "from MODeviceBean WHERE MOName = '192.168.1.4'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		snmpInfo = query.list();

		return snmpInfo;
	}

	@Override
	public List<SNMPCommunityBean> findSnmpCommunityByID(int id) {
		String hql = "from SNMPCommunityBean as org where 1=1 and org.id=" + id;

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SNMPCommunityBean> snmpInfo = query.list();
		return snmpInfo;
	}

//	@Override
//	public List<SNMPCommunityBean> checkDeviceIP(SNMPCommunityBean snmpBean) {
//		String hql = "from SNMPCommunityBean as org where 1=1 and deviceIP='"
//				+ snmpBean.getDeviceIP() + "'";
//		Query query = this.getHibernateTemplate().getSessionFactory()
//				.getCurrentSession().createQuery(hql);
//		List<SNMPCommunityBean> snmpInfo = query.list();
//		return snmpInfo;
//	}
//
//	@Override
//	public SNMPCommunityBean getObjFromDeviceIP(SNMPCommunityBean snmpBean) {
//		String hql = "from SNMPCommunityBean as org where 1=1 and deviceIP='"
//			+ snmpBean.getDeviceIP() + "'";
//		Query query = this.getHibernateTemplate().getSessionFactory()
//			.getCurrentSession().createQuery(hql);
//		List<SNMPCommunityBean> snmpInfo = query.list();
//		if(snmpInfo.isEmpty()){
//			return null;
//		}else{
//			return snmpInfo.get(0);
//		}
//	}

}
