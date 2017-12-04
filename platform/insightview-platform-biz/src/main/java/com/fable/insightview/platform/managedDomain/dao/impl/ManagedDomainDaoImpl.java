package com.fable.insightview.platform.managedDomain.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.core.exception.DaoException;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.managedDomain.dao.IManagedDomainDao;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;

@Repository("managedDomainDao")
public class ManagedDomainDaoImpl extends GenericDaoHibernate<ManagedDomain> implements IManagedDomainDao {
	private final Logger logger = LoggerFactory.getLogger(ManagedDomainDaoImpl.class);
	/**
	 * 根据管理域属性拼接hql
	 * @param hql
	 * @param constantItemDef
	 * @return
	 */
	public String commonConditions(String hql, ManagedDomain managedDomain) {
		if (null != managedDomain.getDomainId()
				&& !"".equals(managedDomain.getDomainId())) {
			hql += " and domainId = '" + managedDomain.getDomainId()
					+ "'";
		}
		if (null != managedDomain.getDomainName()
				&& !"".equals(managedDomain.getDomainName())) {
			hql += " and domainName like '%" + managedDomain.getDomainName() + "%'";
		}
		if (!"".equals(managedDomain.getDomainAlias())
				&& null != managedDomain.getDomainAlias()) {
			hql += " and domainAlias like '%" + managedDomain.getDomainAlias() + "%'";
		}
		if (!"".equals(managedDomain.getOrganizationId())
				&& null != managedDomain.getOrganizationId()) {
			if( managedDomain.getOrganizationId() != -1){
				hql += " and OrganizationId = '" + managedDomain.getOrganizationId() + "'";
			}
		}
		if (StringUtils.isNotEmpty(managedDomain.getParentId()) && !"-1".equals(managedDomain.getParentId())) {
			hql += " and (parentId in (" + managedDomain.getParentId() + ")";
			hql += " or domainId in (" + managedDomain.getParentId() + "))";
		}
		if (null != managedDomain.getShowOrder()
				&& !"".equals(managedDomain.getShowOrder())) {
			hql += " and showOrder='" + managedDomain.getShowOrder() + "'";
		}
		if (null != managedDomain.getDomainDescr()
				&& !"".equals(managedDomain.getDomainDescr())) {
			hql += " and domainDescr like '%" + managedDomain.getDomainDescr() + "%'";
		}
		return hql;
	}
	
	@Override
	public List<ManagedDomain> getManagedDomainList(
			ManagedDomain managedDomain, FlexiGridPageInfo flexiGridPageInfo) {
		String organizationName="";
		
		List<ManagedDomain> managedDomainList=new ArrayList<ManagedDomain>();
		String hql = "from ManagedDomain as org where 1=1";
		hql = commonConditions(hql, managedDomain);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		managedDomainList = query.list();
		for (int i = 0; i < managedDomainList.size(); i++) {
			String parentName="";
			ManagedDomain domainBean=managedDomainList.get(i);
			int level=this.getLevel(domainBean.getDomainId());
			if(domainBean.getOrganizationId()!=null){
				organizationName=this.getOrganizationName(domainBean.getOrganizationId());
			}
			if(domainBean.getParentId()!=null && Integer.parseInt(domainBean.getParentId()) !=0){
				parentName=this.getManagedDomainInfo(Integer.parseInt(domainBean.getParentId())).get(0).getDomainName();
			}
			domainBean.setOrganizationName(organizationName);
			domainBean.setParentDomainName(parentName);
			domainBean.setLevel(level);
		}
		return managedDomainList;
	}

	@Override
	public List<ManagedDomain> getManagedDomainTreeVal() {
		String hql = "FROM ManagedDomain";

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<ManagedDomain> managedDomainList = query.list();
		return managedDomainList;
	}

	@Override
	public int getManagedDomainListCount(ManagedDomain managedDomain) {
		String hql = "select count(1) as count from SysManagedDomain  a where 1=1 ";
		hql = commonConditions(hql, managedDomain);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	@Override
	public int getLevel(int domainId) {
		int i=1;
		int parentId=0;
		
		while(true){
			String hql="select ParentID from SysManagedDomain where DomainID="+domainId;
			Query query= this.getHibernateTemplate().getSessionFactory()
							.getCurrentSession().createSQLQuery(hql);
			parentId=((Number)query.uniqueResult()).intValue();
			if(parentId==0){
				break;
			}else{
				if(domainId==parentId){
					break;
				}else{
					domainId=parentId;
					i++;
				}
				
			}
		}
		
		return i;
	}

	@Override
	public String getOrganizationName(int organizationId){
		String organizationName="";
		try {
			String sql="select OrganizationName from SysOrganization where OrganizationID="+organizationId;
			Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			organizationName=query.uniqueResult().toString();
		} catch (Exception e) {
			logger.error("Organization is not exist! id= "+organizationId,new DaoException());
		}
		
		return organizationName;
	}

	@Override
	public boolean isLeaf(int domainId) {
		/**
		String sql1="select count(1) from SysManagedDomain where ParentID="+domainId;
		String sql2="select * from MODevice where DomainID="+domainId;
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql1);
		int count1=((Number)query.uniqueResult()).intValue();
		Query query2=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql2);
		
		int count2=((Number)query2.uniqueResult()).intValue();
		System.out.println(">>>>>>>>>>>>count1="+count1);
		System.out.println(">>>>>>>>>>>>count2="+count2);
		if(count1==0 && count2==0){
			return true;
		}else{
			return false;
		}
		*/
		String sql="select count(1) from SysManagedDomain  a where  exists ((select DomainID from SysManagedDomain  b where a.DomainID=b.DomainID and b.ParentID="+domainId+")) " +
				"or exists ((select DomainID from SysRoleManagedDomain  c where a.DomainID=c.DomainID and c.DomainID="+domainId+") ) " +
				"or exists ((select DomainID from MODevice  d where a.DomainID=d.DomainID and d.DomainID="+domainId+") ) " +
				"or exists ((select DomainID from SysManagedDomainIPScope  e where a.DomainID=e.DomainID and e.DomainID="+domainId+")) " +
				"or exists ((select DomainID from SysAuthCommunity  f where a.DomainID=f.DomainID and f.DomainID="+domainId+")) " +
				"or exists ((select DomainID from SNMPCommunity  g where a.DomainID=g.DomainID and g.DomainID="+domainId+")) ";
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		int count=((Number)query.uniqueResult()).intValue();
		if(count==0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean delManagedDomain(int domainId) {
		String hql="delete from ManagedDomain where DomainID="+domainId;
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		int i=query.executeUpdate();
		if(i>=0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean addManagedDomain(ManagedDomain managedDomain) {
		try {
			managedDomain.setShowOrder(0);
			super.insert(managedDomain);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<ManagedDomain> getManagedDomainInfo(int domainId) {
		String orgName="";
		String hql="from ManagedDomain as md where DomainID="+domainId;
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		List<ManagedDomain> domainList=query.list();
		for (int i = 0; i < domainList.size(); i++) {
			ManagedDomain bean=domainList.get(i);
			if(bean.getOrganizationId()!=null){
				orgName=this.getOrganizationName(bean.getOrganizationId());
			}
			bean.setOrganizationName(orgName);
		}
		return domainList;
	}

	@Override
	public boolean updateManagedDomainInfo(ManagedDomain managedDomain) {
		try {
			managedDomain.setShowOrder(0);
			super.update(managedDomain);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean checkDomainName(String domainName,String flag) {
		String sql="select count(1) from SysManagedDomain  a where  exists ((select DomainID from SysManagedDomain  b where a.DomainID=b.DomainID and b.DomainName='"+domainName+"')) " ;
		if("edit".equals(flag)){
			sql="select count(1) from SysManagedDomain where DomainName='"+domainName+"' and DomainID not in (select DomainID from SysManagedDomain where DomainName='"+domainName+"')";
		}
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		int count=((Number)query.uniqueResult()).intValue();
		if(count==0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public ManagedDomain getIdByDomainName(String domainName) {
		String hql="from ManagedDomain where DomainName like '%"+domainName+"%'";
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		List<ManagedDomain> domains=query.list();
		String parId="-1";
		for (int i = 0; i < domains.size(); i++) {
			parId=parId+","+domains.get(i).getDomainId();
		}
		ManagedDomain manageDomain=new ManagedDomain();
		manageDomain.setParentId(parId);
		return manageDomain;
	}




}
