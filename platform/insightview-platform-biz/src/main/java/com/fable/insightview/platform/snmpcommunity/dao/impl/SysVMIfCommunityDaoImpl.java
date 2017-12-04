package com.fable.insightview.platform.snmpcommunity.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmpcommunity.dao.ISysVMIfCommunityDao;
import com.fable.insightview.platform.snmpcommunity.entity.MODeviceBean;
import com.fable.insightview.platform.snmpcommunity.entity.SysVMIfCommunityBean;

@Repository("sysVMIfCommunityDao")
public class SysVMIfCommunityDaoImpl extends
		GenericDaoHibernate<SysVMIfCommunityBean> implements
		ISysVMIfCommunityDao {

	public String commonConditions(String hql, SysVMIfCommunityBean snmpBean) {
		if (null != snmpBean.getDeviceIP()
				&& !"".equals(snmpBean.getDeviceIP())) {
			hql += " AND org.deviceIP LIKE '%" + snmpBean.getDeviceIP() + "%'";
		}
		if (null != snmpBean.getUserName()
				&& !"".equals(snmpBean.getUserName())) {
			hql += " AND org.userName LIKE '%" + snmpBean.getUserName() + "%'";
		}
		if (null != snmpBean.getMoName() && !"".equals(snmpBean.getMoName())) {
			hql += " AND org.moID in (" + snmpBean.getMoID() + ")";
		}
		return hql;
	}

	@Override
	public List<SysVMIfCommunityBean> getSysAuthCommunityByConditions(
			SysVMIfCommunityBean snmpBean, FlexiGridPageInfo flexiGridPageInfo,
			String type) {
		StringBuffer sbfSql = new StringBuffer();
		sbfSql
				.append("  select org.id,org.authType,org.deviceIP,org.moID,org.port,org.userName,org.password,org.moName,org.domainID from (");
		sbfSql
				.append("  select m.*,null moName  from SysVMIfCommunity m where m.id not in (");
		sbfSql.append("  select tp.id from (	");
		sbfSql
				.append("  select t.id from SysVMIfCommunity t, MODevice p where t.MOID=p.MOID and t.MOID is not null union all");
		sbfSql
				.append("  select t.id from SysVMIfCommunity t, MODevice p where  t.DeviceIP=p.DeviceIP and t.MOID is null) tp )union all ");
		sbfSql
				.append("  select t.*,p.moName from SysVMIfCommunity t, MODevice p where t.MOID=p.MOID and t.MOID is not null");
		sbfSql.append("  union all");
		sbfSql.append("  select t.*,p.moName from SysVMIfCommunity t, ");
		sbfSql
				.append("  MODevice p where  t.DeviceIP=p.DeviceIP and t.MOID is null");
		sbfSql.append("  )org where 1=1 ");
		System.out.println(sbfSql.toString());
		if (null != snmpBean.getDeviceIP()
				&& !"".equals(snmpBean.getDeviceIP())) {
			sbfSql.append(" AND org.deviceIP LIKE '%" + snmpBean.getDeviceIP()
					+ "%'");
		}
		if (null != snmpBean.getUserName()
				&& !"".equals(snmpBean.getUserName())) {
			sbfSql.append(" AND org.userName LIKE '%" + snmpBean.getUserName()
					+ "%'");
		}
		if (null != snmpBean.getMoName() && !"".equals(snmpBean.getMoName())) {
			sbfSql.append("AND org.moID in (" + snmpBean.getMoID() + ")");
		}
		// 创建session对象
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();

		/* 分页查询 */
		SQLQuery query = session.createSQLQuery(sbfSql.toString()).addScalar(
				"id", Hibernate.INTEGER)
				.addScalar("authType", Hibernate.INTEGER).addScalar("deviceIP",
						Hibernate.STRING).addScalar("moID", Hibernate.INTEGER)
				.addScalar("port", Hibernate.INTEGER).addScalar("userName",
						Hibernate.STRING).addScalar("password",
						Hibernate.STRING).addScalar("moName", Hibernate.STRING)
				.addScalar("domainID", Hibernate.INTEGER);
		// 设置分页参数
		query
				.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
						.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		// 获取分页数据
		List<SysVMIfCommunityBean> list = query.setResultTransformer(
				Transformers.aliasToBean(SysVMIfCommunityBean.class)).list();

		// 结果放入分页对象中
		return list;
	}

	@Override
	public int getTotalCount(SysVMIfCommunityBean snmpBean, String type) {
		StringBuffer sbfSql = new StringBuffer();
		sbfSql.append("  select count(1) from (");
		sbfSql
				.append("  select m.*,null moName  from SysVMIfCommunity m where m.id not in (");
		sbfSql.append("  select tp.id from (	");
		sbfSql
				.append("  select t.id from SysVMIfCommunity t, MODevice p where t.MOID=p.MOID and t.MOID is not null union all");
		sbfSql
				.append("  select t.id from SysVMIfCommunity t, MODevice p where  t.DeviceIP=p.DeviceIP and t.MOID is null) tp )union all ");
		sbfSql
				.append("  select t.*,p.moName from SysVMIfCommunity t, MODevice p where t.MOID=p.MOID and t.MOID is not null");
		sbfSql.append("  union all");
		sbfSql.append("  select t.*,p.moName from SysVMIfCommunity t, ");
		sbfSql
				.append("  MODevice p where  t.DeviceIP=p.DeviceIP and t.MOID is null");
		sbfSql.append("  )org where 1=1 ");
		// sbfSql = commonConditions(sbfSql, snmpBean);
		if (null != snmpBean.getDeviceIP()
				&& !"".equals(snmpBean.getDeviceIP())) {
			sbfSql.append(" AND org.deviceIP LIKE '%" + snmpBean.getDeviceIP()
					+ "%'");
		}
		if (null != snmpBean.getUserName()
				&& !"".equals(snmpBean.getUserName())) {
			sbfSql.append(" AND org.userName LIKE '%" + snmpBean.getUserName()
					+ "%'");
		}
		if (null != snmpBean.getMoName() && !"".equals(snmpBean.getMoName())) {
			sbfSql.append("AND org.moID in (" + snmpBean.getMoID() + ")");
		}
		SQLQuery query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sbfSql.toString());
		int count = ((Number) query.uniqueResult()).intValue();

		return count;
	}

	@Override
	public boolean addSysAuthCommunity(SysVMIfCommunityBean authBean) {
		try {
			super.insert(authBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delSysAuthCommunityById(SysVMIfCommunityBean authBean) {
		try {
			super.delete(authBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateSysAuthCommunity(SysVMIfCommunityBean authBean) {
		try {
			super.update(authBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<SysVMIfCommunityBean> getSysAuthCommunityByConditions(
			SysVMIfCommunityBean authBean) {
		String hql = "from SysVMIfCommunityBean as org where 1=1 and deviceIP='"
				+ authBean.getDeviceIP() + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysVMIfCommunityBean> snmpLst = query.list();
		return snmpLst;
	}

	@Override
	public List<String> getMoIDBymoName(String moName) {
		String hql = "SELECT MOID from MODevice WHERE MOName LIKE '%" + moName
				+ "%'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		List<String> snmpInfo = query.list();
		return snmpInfo;
	}

	@Override
	public List<MODeviceBean> getDeviceById(int moId) {
		String hql = "from MODeviceBean as org where 1=1 and org.moID=" + moId;

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<MODeviceBean> snmpInfo = query.list();
		return snmpInfo;
	}

	@Override
	public List<SysVMIfCommunityBean> getSysAuthCommunityById(int id) {
		/*
		 * String hql =
		 * "SELECT s.id,s.authType,s.deviceIP,s.domainID,d.moID,s.userName,s.password,s.port,m.domainName from SysVMIfCommunity s left join SysManagedDomain m "
		 * +
		 * "on s.DomainID=m.DomainID  left join MODevice d on d.DeviceIP=s.DeviceIP "
		 * + "where s.ID=" + id;
		 */
		String hql = " from SysVMIfCommunityBean as org where 1=1 and org.id="
				+ id;

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		/*
		 * List<SysVMIfCommunityBean> snmpInfo = query.setResultTransformer(
		 * Transformers.aliasToBean(SysVMIfCommunityBean.class)).list();
		 */
		List<SysVMIfCommunityBean> snmpInfo = query.list();
		return snmpInfo;
	}

	@Override
	public boolean checkDeviceIP(SysVMIfCommunityBean authBean) {
		String hql = "from SysVMIfCommunityBean as org where 1=1 and deviceIP <>'"
				+ authBean.getDeviceIP() + "'";

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysVMIfCommunityBean> snmpLst = query.list();
		String checkIP = authBean.getCheckIP();
		for (int i = 0; i < snmpLst.size(); i++) {
			if (checkIP.equals(snmpLst.get(i).getDeviceIP())) {

				return false;
			}
		}
		return true;
	}

	@Override
	public SysVMIfCommunityBean getObjFromDeviceIP(SysVMIfCommunityBean vo) {
		String hql = "from SysVMIfCommunityBean as org where 1=1 and deviceIP ='"
			+ vo.getDeviceIP() + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql);
		List<SysVMIfCommunityBean> snmpLst = query.list();
		if(snmpLst.isEmpty()){
			return null;
		}else{
			return snmpLst.get(0);
		}
	}

	@Override
	public List<SysVMIfCommunityBean> getByIp(String ip) {
		String hql = "from SysVMIfCommunityBean where 1=1 and deviceIP LIKE  '%" + ip + "%'";
		Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql);
		List<SysVMIfCommunityBean> vmLst = query.list();
		return vmLst;
	}

}
