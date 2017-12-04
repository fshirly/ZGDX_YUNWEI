package com.fable.insightview.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.dao.IOrganizationDao;
import com.fable.insightview.platform.employmentGrade.entity.SysEmploymentGradeBean;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserGroupBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;

/**
 * 单位组织Dao
 * 
 * @author 武林
 * 
 */
@Repository("organizationDao")
public class OrganizationDaoImpl extends GenericDaoHibernate<OrganizationBean>
		implements IOrganizationDao {
	private final Logger logger = LoggerFactory
			.getLogger(OrganizationDaoImpl.class);

	/**
	 * 条件拼接hql
	 * 
	 * @param hql
	 * @param organizationBean
	 * @return
	 */
	public String commonConditions(String hql, OrganizationBean organizationBean) {
		List<Integer> organizationIDList = new ArrayList<Integer>();
		if (null != organizationBean.getOrganizationName()
				&& !"".equals(organizationBean.getOrganizationName())) {
			hql += " and organizationName LIKE '%"
					+ organizationBean.getOrganizationName() + "%'";
		}
		if (!"".equals(organizationBean.getParentOrganizationName())
				&& null != organizationBean.getParentOrganizationName()) {
			List<Integer> parentIDList = getOrganizationIDByOrgName(organizationBean
					.getParentOrganizationName());
			hql += " and parentOrgID  in(" + parentIDList.get(0);
			for (int i = 1; i < parentIDList.size(); i++) {
				hql += "," + parentIDList.get(i);
			}
			hql += ")";
		}
		if (null != organizationBean.getOrganizationID()
				&& !"".equals(organizationBean.getOrganizationID())
				&& organizationBean.getOrganizationID() != -1) {
			organizationIDList.add(organizationBean.getOrganizationID());
			List<Integer> childIDList = getChildIdByOrgIDList(organizationIDList);
			for (int id : childIDList) {
				organizationIDList.add(id);
			}
			hql += " and organizationID  in(" + organizationIDList.get(0);
			for (int i = 1; i < organizationIDList.size(); i++) {
				hql += "," + organizationIDList.get(i);
			}
			hql += ")";
		}

		if (null != organizationBean.getNodeID()
				&& !"".equals(organizationBean.getNodeID())
				&& !"-1".equals(organizationBean.getNodeID())) {
			String[] nodeIds = organizationBean.getNodeID().split(",");
			List<Integer> orgIDList = new ArrayList<Integer>();
			for (int i = 1; i < nodeIds.length; i++) {
				int orgID = Integer.parseInt(nodeIds[i]);
				orgIDList.add(orgID);
			}
			for (int i = 0; i < orgIDList.size(); i++) {
				List<Integer> childIDList = getAllOrgId(orgIDList.get(i));
				for (int id : childIDList) {
					orgIDList.add(id);
				}
			}
			hql += " and organizationID  in(" + orgIDList.get(0);
			for (int i = 1; i < orgIDList.size(); i++) {
				hql += "," + orgIDList.get(i);
			}
			hql += ")";
		}
		return hql;
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationBean> getOrganizationBeanByConditions(
			String paramName, String paramValue) {
		// TODO Auto-generated method stub
		String hql = "from OrganizationBean where 1=1 ";
		if (null != paramName && !"".equals(paramName.trim())) {
			hql += " and " + paramName + "='" + paramValue + "'";
		}

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<OrganizationBean> organizationBeanLst = query.list();
		return organizationBeanLst;
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateOrganizationBean(OrganizationBean organizationBean) {
		try {
			super.update(organizationBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getTotalCount(OrganizationBean organizationBean) {
		if(!"".equals(organizationBean.getParentOrganizationName()) && organizationBean.getParentOrganizationName()!=null){
			List<Integer> parentIDList   = getOrganizationIDByOrgName(organizationBean.getParentOrganizationName());
			if(parentIDList.size() <= 0){
				return 0;
			}
		}
		String hql = "select count(1) as count from SysOrganization where 1=1";
		hql = commonConditions(hql, organizationBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();

		return count;
	}

	/*
	 * 新增单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addOrganization(OrganizationBean organizationBean) {
		try {
			super.insert(organizationBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 删除单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean delOrganizationById(OrganizationBean organizationBean) {
		try {
			super.delete(organizationBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationBean> getOrganizationByConditions(
			OrganizationBean organizationBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		List<OrganizationBean> organizationBeanLst = new ArrayList<OrganizationBean>();
		if(!"".equals(organizationBean.getParentOrganizationName()) && organizationBean.getParentOrganizationName()!=null){
			List<Integer> parentIDList   = getOrganizationIDByOrgName(organizationBean.getParentOrganizationName());
			if(parentIDList.size() <= 0){
				return organizationBeanLst;
			}
		}
		
		String hql = "from OrganizationBean where 1=1 ";
		hql = commonConditions(hql, organizationBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query
				.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
						.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		organizationBeanLst = query.list();
		return organizationBeanLst;
	}

	/*
	 * 根据上级单位Id查询单位
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<OrganizationBean> findByParentId(int parentId) {
		return (List<OrganizationBean>) this.getHibernateTemplate().find(
				"from OrganizationBean where parentOrgID=" + parentId);
	}

	/*
	 * 查询单位组织
	 */
	@Override
	public List<OrganizationBean> getOrganizationByConditions(String paramName,
			String paramValue) {
		String hql = "from OrganizationBean where 1=1 and " + paramName + "='"
				+ paramValue + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<OrganizationBean> OrganizationBeanLst = query.list();
		return OrganizationBeanLst;
	}

	/*
	 * 根据组织Id获得单位员工
	 */
	@Override
	public List<SysEmploymentBean> getSysEmployByOrganizationID(
			OrganizationBean organizationBean) {
		String hql = "from SysEmploymentBean where 1=1 and organizationID ='"
				+ organizationBean.getOrganizationID() + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysEmploymentBean> sysEmploymentBeanList = query.list();
		return sysEmploymentBeanList;
	}

	/*
	 * 根据单位Id获得管理域
	 */
	@Override
	public List<ManagedDomain> getSysManagedDomainByOrganizationID(
			OrganizationBean organizationBean) {
		String hql = "from ManagedDomain where 1=1 and organizationID ='"
				+ organizationBean.getOrganizationID() + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<ManagedDomain> managedDomainList = query.list();
		return managedDomainList;
	}

	/*
	 * 根据组织Id获得用户组
	 */
	@Override
	public List<SysUserGroupBean> getSysUserGroupByOrganizationID(
			OrganizationBean organizationBean) {
		String hql = "from SysUserGroupBean where 1=1 and organizationBean.organizationID ='"
				+ organizationBean.getOrganizationID() + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysUserGroupBean> sysUserGroupBeanList = query.list();
		return sysUserGroupBeanList;
	}

	/**
	 * 查询所有非外来单位
	 */
	@Override
	public List<OrganizationBean> getOrganizationTreeVal() {
		//String hql = "FROM OrganizationBean ";
		String sql ="select distinct o.OrganizationID, o.ParentOrgID, o.OrganizationName, o.OrganizationCode, o.Descr from SysOrganization  o  left join SysEmployment e on o.OrganizationID = e.OrganizationID where o.OrganizationID not in( select e.OrganizationID from SysEmployment e where e.UserID in (  select u.UserID from SysUserInfo u where u.UserType = "+SystemFinalValue.FOREIGN_USER_TYPE+" ))";
//		Query query = this.getHibernateTemplate().getSessionFactory()
//				.getCurrentSession().createQuery(sql);
		//List<OrganizationBean> organizationList = query.list();
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		SQLQuery  sqlQuery = sess.createSQLQuery(sql);
		List<OrganizationBean> organizationList = sqlQuery.setResultTransformer(Transformers.aliasToBean(OrganizationBean.class)).list();
		return organizationList;
	}
	
	/**
	 * 查询所有非外来本单位
	 */
	@Override
	public List<OrganizationBean> getOrganizationTree(String deptId) {
		String sql ="select o.OrganizationID, o.ParentOrgID, o.OrganizationName, o.OrganizationCode, o.Descr from SysOrganization  o , SysDepartment dept where o.OrganizationID in( select e.OrganizationID from SysEmployment e where e.UserID in (  select u.UserID from SysUserInfo u where u.UserType != 3 )) and o.OrganizationID = dept.OrganizationID ";
		sql += " and o.OrganizationID = (select d1.OrganizationID from SysDepartment d1 where d1.DeptID = " + deptId + ")" ;
		sql += " GROUP BY o.OrganizationID";
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		SQLQuery  sqlQuery = sess.createSQLQuery(sql);
		List<OrganizationBean> organizationList = sqlQuery.setResultTransformer(Transformers.aliasToBean(OrganizationBean.class)).list();
		return organizationList;
	}

	/*
	 * 根据单位Id获得上级部门名称
	 */
	@Override
	public List<OrganizationBean> getParentnameByIds(List<Integer> ids) {
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Criteria criteria = sess.createCriteria(OrganizationBean.class);
		criteria.add(Restrictions.in("organizationID", ids));
		return criteria.list();
	}

	/*
	 * 根据单位Id获得与所有下属单位
	 */
	@Override
	public List<Integer> getAllOrgId(int organizationID) {
		List<Integer> allOrgIDList = new ArrayList<Integer>();
		List<Integer> childIdList = new ArrayList<Integer>();
		childIdList.add(organizationID);
		while (true) {
			List<Integer> moreChildIDList = getChildIdByOrgIDList(childIdList);
			for (int i = 0; i < childIdList.size(); i++) {
				childIdList.remove(i);
			}
			if (moreChildIDList.size() != 0) {
				for (int i = 0; i < moreChildIDList.size(); i++) {
					childIdList.add(moreChildIDList.get(i));
					allOrgIDList.add(moreChildIDList.get(i));
				}
			} else {
				break;
			}
		}
		return allOrgIDList;
	}

	/*
	 * 获得子单位的Id
	 */
	@Override
	public List<Integer> getChildIdByOrgIDList(List<Integer> organizationIDList) {
		String ordIds = "";
		for (int i = 0; i < organizationIDList.size(); i++) {
			ordIds += organizationIDList.get(i) + ",";
		}
		ordIds = ordIds.substring(0, ordIds.lastIndexOf(","));
		String sql = "select OrganizationID from SysOrganization where ParentOrgID in("
				+ ordIds + ")";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Integer> childIDs = new ArrayList<Integer>();
		List<Integer> childIDList = new ArrayList<Integer>();
		childIDs = query.list();
		for (int i = 0; i < childIDs.size(); i++) {
			int id = Integer.valueOf(String.valueOf(childIDs.get(i)));
			childIDList.add(id);
		}
		return childIDList;
	}

	/*
	 * 根据单位名称获得单位Id
	 */
	@Override
	public List<Integer> getOrganizationIDByOrgName(String organizationName) {
		List<Integer> orgIDList = new ArrayList<Integer>();
		if (null != organizationName && !"".equals(organizationName)) {
			String sql = "select OrganizationID from SysOrganization where OrganizationName like '%"
					+ organizationName + "%'";
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			orgIDList = query.list();
		}
		return orgIDList;
	}

	/*
	 * 验证单位名称的唯一性
	 */
	@Override
	public boolean checkOrganizationName(OrganizationBean organizationBean) {
		String hql = null;
		if ("add".equals(organizationBean.getDoName())) {
			hql = "from OrganizationBean where parentOrgID='"
					+ organizationBean.getParentOrgID() + "'";
		} else if ("update".equals(organizationBean.getDoName())) {
			hql = "from OrganizationBean where parentOrgID='"
					+ organizationBean.getParentOrgID()
					+ "' and organizationID<>'"
					+ organizationBean.getOrganizationID() + "'";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		String checkOrgName = organizationBean.getOrganizationName();
		List<OrganizationBean> orgList = query.list();
		for (int i = 0; i < orgList.size(); i++) {
			if (orgList.get(i).getOrganizationName().equals(checkOrgName)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * 根据单位名称获得单位
	 */
	@Override
	public OrganizationBean getOrganizationBeanByOrgName(String organizationName) {
		List<OrganizationBean> orgIDList = new ArrayList<OrganizationBean>();
		if (null != organizationName && !"".equals(organizationName)) {
			String hql = " from OrganizationBean where organizationName like '%"
					+ organizationName + "%'";
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql);
			orgIDList = query.list();
		}
		OrganizationBean organizationBean = new OrganizationBean();
		String nodeID = "-1";
		for (int i = 0; i < orgIDList.size(); i++) {
			nodeID = nodeID + "," + orgIDList.get(i).getOrganizationID();
		}
		organizationBean.setNodeID(nodeID);
		return organizationBean;
	}

	/*
	 * 根据单位Id获得部门
	 */
	@Override
	public List<DepartmentBean> getDepartmentByOrganizationID(
			OrganizationBean organizationBean) {
		String hql = "from DepartmentBean where 1=1 and organizationID ='"
				+ organizationBean.getOrganizationID() + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<DepartmentBean> departmentBeanList = query.list();
		return departmentBeanList;
	}

	@Override
	public List<SysEmploymentGradeBean> getSysEmployGradeByOrganizationID(
			OrganizationBean organizationBean) {
		String hql = "from SysEmploymentGradeBean where 1=1 and organizationID ='"
				+ organizationBean.getOrganizationID() + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysEmploymentGradeBean> employmentGradeList = query.list();
		return employmentGradeList;
	}

	@Override
	public List<Integer> getUserIdsByOrgId(Integer branchId) {
		StringBuilder sql = new StringBuilder("SELECT su.UserID FROM SysUserInfo su ");
		sql.append("LEFT JOIN SysEmployment se ON su.UserID=se.UserID ");
		sql.append("WHERE se.DeptID ="+branchId);
//		sql.append(" UNION SELECT DISTINCT su.UserAccount FROM SysProviderOrganization spo ");
//		sql.append("LEFT JOIN SysProviderUser spu ON spo.ProviderId=spo.ProviderId ");
//		sql.append("LEFT JOIN SysUserInfo su ON su.UserID=spu.UserID ");
//		sql.append("WHERE spo.OrganizationId="+branchId);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql.toString());
		List<Integer> userIdList = query.list();
		return userIdList;
	}

	@Override
	public List<OrganizationBean> listOrganizationByUserId(int currentUserId) {
		// TODO Auto-generated method stub
		String sql = "SELECT DISTINCT so.* FROM SysOrganization so LEFT JOIN SysProviderOrganization spo ON so.OrganizationID= spo.OrganizationId LEFT JOIN SysProviderUser sp ON  sp.ProviderID=spo.ProviderId where sp.UserID = "+currentUserId;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<OrganizationBean> orgList = query.setResultTransformer(Transformers.aliasToBean(OrganizationBean.class)).list();
		return orgList;
	}

	@Override
	public boolean checkOrganizationCode(OrganizationBean organizationBean) {
		String hql = null;
		if ("add".equals(organizationBean.getDoName())) {
			hql = "from OrganizationBean where organizationCode='"
					+ organizationBean.getOrganizationCode() + "'";
		} else if ("update".equals(organizationBean.getDoName())) {
			hql = "from OrganizationBean where organizationCode='"
					+ organizationBean.getOrganizationCode()
					+ "' and organizationID<>'"
					+ organizationBean.getOrganizationID() + "'";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		String checkOrgCode = organizationBean.getOrganizationCode();
		List<OrganizationBean> orgList = query.list();
		for (int i = 0; i < orgList.size(); i++) {
			
			if (orgList.get(i).getOrganizationCode()!=null && orgList.get(i).getOrganizationCode().equals(checkOrgCode)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public OrganizationBean getOrganizationByCode(String organizationCode) {
		List<OrganizationBean> orgIDList = new ArrayList<OrganizationBean>();
		if (null != organizationCode && !"".equals(organizationCode)) {
			String hql = " from OrganizationBean where organizationCode = '"
					+ organizationCode+"'";
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql);
			orgIDList = query.list();
		}
		if(orgIDList.size() == 0){
			return null; 
		}else{
			return orgIDList.get(0);
		}
	}

	@Override
	public List<OrganizationBean> getOrgByUserId(int userId) {
		// TODO Auto-generated method stub
		String sql = "SELECT so.* FROM SysOrganization so, SysEmployment se WHERE so.OrganizationID=se.OrganizationID AND se.UserID= " + userId;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<OrganizationBean> orgList = query.setResultTransformer(Transformers.aliasToBean(OrganizationBean.class)).list();
		return orgList;
	}
}
