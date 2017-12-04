package com.fable.insightview.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.ISysRoleDao;
import com.fable.insightview.platform.entity.SysRoleBean;
import com.fable.insightview.platform.entity.SysUserGroupRolesBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

/**
 * 角色Dao
 * 
 * @author 武林
 * 
 */
@Repository("sysRoleDao")
public class SysRoleDaoImpl extends GenericDaoHibernate<SysRoleBean> implements
		ISysRoleDao {

	public String commonConditions(String hql, SysRoleBean sysRoleBean) {
		if (null != sysRoleBean.getRoleName()
				&& !"".equals(sysRoleBean.getRoleName())) {
			hql += " and roleName LIKE '%" + sysRoleBean.getRoleName() + "%'";
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysRoleBean> getSysRoleBeanByConditions(String paramName,
			String paramValue, String paramNameT, String paramValueT) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from SysRoleBean where 1=1 ";
		if (null != paramValue && !"".equals(paramValue)
				&& !"-1".equals(paramValue)) {
			hql += " and " + paramName + "='" + paramValue + "'";
		}
		if (null != paramValueT && !"".equals(paramValueT)) {
			if (!"-1".equals(paramValue)) {
				hql += " and " + paramNameT + " not in(" + paramValueT + ")";
			} else {
				hql += " and " + paramNameT + " in(" + paramValueT + ")";
			}
		} else {
			if (null != paramValue && "-1".equals(paramValue)) {
				hql += " and 1=2";
			}
		}

		System.out.println(paramValue + "===");
		System.out.println(hql + "___");
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysRoleBean> sysRoleBeanLst = query.list();
		return sysRoleBeanLst;
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysRoleBean> getSysRoleBeanByConditions(String paramName,
			String paramValue) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from SysRoleBean where 1=1 ";
		if (null != paramValue && !"".equals(paramValue)) {
			hql += " and " + paramName + "='" + paramValue + "'";
		}

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysRoleBean> sysRoleBeanLst = query.list();
		return sysRoleBeanLst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSysRoleBean(SysRoleBean sysRoleBean) {
		try {
			super.update(sysRoleBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getTotalCount(SysRoleBean sysRoleBean) {
		String hql = "select count(1) as count from SysRole where 1=1";
		hql = commonConditions(hql, sysRoleBean);

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
	public boolean addSysRole(SysRoleBean sysRoleBean) {
		try {
			super.insert(sysRoleBean);
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
	public boolean delSysRoleById(SysRoleBean sysRoleBean) {
		try {
			super.delete(sysRoleBean);
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
	public List<SysRoleBean> getSysRoleByConditions(SysRoleBean sysRoleBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from SysRoleBean where 1=1 ";
		hql = commonConditions(hql, sysRoleBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query
				.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
						.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		List<SysRoleBean> sysRoleBeanLst = query.list();
		return sysRoleBeanLst;
	}

	@Override
	public List<SysRoleBean> getSysRoleByConditions(String paramName,
			String paramValue) {
		String hql = "from SysRoleBean where 1=1  and " + paramName + "='"
				+ paramValue + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysRoleBean> sysRoleBeanLst = query.list();
		return sysRoleBeanLst;
	}

	/**
	 * 验证角色名称唯一性
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkRoleName(SysRoleBean sysRoleBean) {
		String hql = " from SysRoleBean where 1=1";
		if ("add".equals(sysRoleBean.getDoName())) {
			hql += " and roleName = '" + sysRoleBean.getRoleName() + "'";
		} else if ("update".equals(sysRoleBean.getDoName())) {
			hql += " and roleName = '" + sysRoleBean.getRoleName()
					+ "' and roleId <>" + sysRoleBean.getRoleId();
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysRoleBean> roleList = query.list();
		if (roleList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除前的验证
	 */
	@Override
	public boolean checkBeforeDel(SysRoleBean sysRoleBean) {
		String hql = "from SysUserGroupRolesBean where roleID = "
				+ sysRoleBean.getRoleId();
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysUserGroupRolesBean> userGroupRolesList = query.list();
		if (userGroupRolesList.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
}
