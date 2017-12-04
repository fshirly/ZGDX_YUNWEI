package com.fable.insightview.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.ISysUserGroupDao;
import com.fable.insightview.platform.entity.SysUserGroupBean;
import com.fable.insightview.platform.entity.SysUserGroupRolesBean;
import com.fable.insightview.platform.entity.SysUserInGroupsBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

/**
 * 用户组组织Dao
 * 
 * @author 武林
 * 
 */
@Repository("sysUserGroupDao")
public class SysUserGroupDaoImpl extends GenericDaoHibernate<SysUserGroupBean>
		implements ISysUserGroupDao {

	public String commonConditions(String hql, SysUserGroupBean sysUserGroup) {
		if (null != sysUserGroup.getGroupName()
				&& !"".equals(sysUserGroup.getGroupName())) {
			hql += " and groupName like '%" + sysUserGroup.getGroupName()
					+ "%'";
		}
		if (!"".equals(sysUserGroup.getOrganizationName())
				&& null != sysUserGroup.getOrganizationName()) {
			List<Integer> orgIDByorgNameList = getOrganizationIDByOrgName(sysUserGroup
					.getOrganizationName());
			if (orgIDByorgNameList.size() > 0) {
				hql += " and organizationID  in(" + orgIDByorgNameList.get(0);
				for (int i = 1; i < orgIDByorgNameList.size(); i++) {
					hql += "," + orgIDByorgNameList.get(i);
				}
				hql += ")";
			} else {
				List<SysUserGroupBean> userGroupList = getSysUserGroupByConditions(
						"", "");
				List<Integer> allGroupIDLst = new ArrayList<Integer>();
				for (int i = 0; i < userGroupList.size(); i++) {
					allGroupIDLst.add(userGroupList.get(i).getGroupID());
				}
				hql += " and groupID not  in(" + allGroupIDLst.get(0);
				for (int i = 1; i < allGroupIDLst.size(); i++) {
					hql += "," + allGroupIDLst.get(i);
				}
				hql += ")";
			}
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean insertGroupRole(List<SysUserGroupRolesBean> groupRoleLst) {
		try {
			for (SysUserGroupRolesBean groupRole : groupRoleLst) {
				super.insert(groupRole);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delGroupRoleByCond(SysUserGroupRolesBean groupRole) {
		String hql = "delete from SysUserGroupRolesBean where groupID ="
				+ groupRole.getGroupID() + "";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserGroupRolesBean> getSysUserGroupRole(String paramName,
			String paramValue) {
		String hql = "from SysUserGroupRolesBean where 1=1 and " + paramName
				+ "=" + paramValue + "";
		logger.info("执行的hql语句===" + hql);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysUserGroupRolesBean> groupRoleLst = query.list();
		return groupRoleLst;
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserGroupBean> getSysUserGroupByConditions(String paramName,
			String paramValue) {
		String hql = "from SysUserGroupBean where 1=1";
		if (null != paramName && !"".equals(paramName.trim())) {
			hql += " and " + paramName + "='" + paramValue + "'";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysUserGroupBean> sysUserGroupBeanLst = query.list();
		return sysUserGroupBeanLst;
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSysUserGroup(SysUserGroupBean sysUserGroupBean) {
		try {
			super.update(sysUserGroupBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 用户组总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getTotalCount(SysUserGroupBean sysUserGroup) {
		String hql = "select count(1) as count from SysUserGroup where 1=1";
		hql = commonConditions(hql, sysUserGroup);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();

		return count;
	}

	/*
	 * 新增用户组组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addSysUserGroup(SysUserGroupBean sysUserGroupBean) {
		try {
			System.out.println("名称："
					+ sysUserGroupBean.getGroupName()
					+ ";描述："
					+ sysUserGroupBean.getDescr()
					+ ";  组织ID："
					+ sysUserGroupBean.getOrganizationBean()
							.getOrganizationID());
			super.insert(sysUserGroupBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 删除用户组组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean delSysUserGroupById(SysUserGroupBean sysUserGroupBean) {
		try {
			super.delete(sysUserGroupBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 查询用户组组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserGroupBean> getSysUserGroupByConditions(
			SysUserGroupBean sysUserGroupBean) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from SysUserGroupBean where 1=1 ";
		hql = commonConditions(hql, sysUserGroupBean);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);

		List<SysUserGroupBean> sysUserGroupBeanLst = query.list();
		return sysUserGroupBeanLst;
	}

	/*
	 * 查询用户组组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserGroupBean> getSysUserGroupByConditions(
			SysUserGroupBean sysUserGroupBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from SysUserGroupBean as org where 1=1 ";
		hql = commonConditions(hql, sysUserGroupBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query
				.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
						.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		List<SysUserGroupBean> sysUserGroupBeanLst = query.list();
		return sysUserGroupBeanLst;
	}

	/**
	 * 根据组织Id查询其下的组group信息
	 * 
	 * @author sanyou
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SysUserGroupBean> getSysUserGroupByOrganizationId(
			int OrganizationId) {
		return this.getHibernateTemplate().find(
				"from SysUserGroupBean where organizationID=" + OrganizationId);
	}

	/**
	 * 查询所有的组group信息
	 * 
	 * @author sanyou
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SysUserGroupBean> findAllSysUserGroup() {
		return this.getHibernateTemplate().find("from SysUserGroupBean");
	}

	/**
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getGroupByUserId(SysUserInfoBean sysUserInfoBean) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "SELECT GroupName FROM  SysUserGroup t WHERE t.GroupID IN (SELECT GroupID FROM SysUserInGroups WHERE UserId = "
				+ sysUserInfoBean.getUserID() + ")";

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		List<String> sysUserGroupBeanLst = query.list();
		return sysUserGroupBeanLst;
	}

	/**
	 * 验证用户组名称
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkGroupName(SysUserGroupBean sysUserGroupBean) {
		String hql = null;
		if ("add".equals(sysUserGroupBean.getDoName())) {
			hql = "from SysUserGroupBean where organizationID="
					+ sysUserGroupBean.getOrganizationBean()
							.getOrganizationID() + " and groupName= '"
					+ sysUserGroupBean.getGroupName() + "'";
		} else if ("update".equals(sysUserGroupBean.getDoName())) {
			hql = "from SysUserGroupBean where organizationID="
					+ sysUserGroupBean.getOrganizationBean()
							.getOrganizationID() + " and groupName= '"
					+ sysUserGroupBean.getGroupName() + "' and groupID <>"
					+ sysUserGroupBean.getGroupID();
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysUserGroupBean> sysUserGroupList = query.list();
		if (null != sysUserGroupList && sysUserGroupList.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<SysUserInGroupsBean> getUserInGroupsByGroupID(int groupID) {
		String hql = "from SysUserInGroupsBean where groupId=" + groupID;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysUserInGroupsBean> userInGroupsList = query.list();
		return userInGroupsList;
	}

	@Override
	public List<Integer> getOrganizationIDByOrgName(String organizatioName) {
		List<Integer> orgIDList = new ArrayList<Integer>();
		if (null != organizatioName && !"".equals(organizatioName)) {
			String sql = "select OrganizationID from SysOrganization where OrganizationName like '%"
					+ organizatioName + "%'";
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			orgIDList = query.list();
		}
		return orgIDList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserGroupBean> getKeXinGroup() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().find("from SysUserGroupBean where groupID=3");
	}
}
