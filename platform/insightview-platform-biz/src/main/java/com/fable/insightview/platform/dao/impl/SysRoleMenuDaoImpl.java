package com.fable.insightview.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.ISysRoleMenuDao;
import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.entity.SysRoleMenusBean;
import com.fable.insightview.platform.entity.SysUserGroupRolesBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

/**
 * 角色与菜单关联的DAO
 * 
 * @author Administrator wul
 * 
 */
@Repository("sysRoleMenuDao")
public class SysRoleMenuDaoImpl extends GenericDaoHibernate<SysRoleMenusBean>
		implements ISysRoleMenuDao {

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysRoleMenusBean> getSysMenuByRole(
			SysMenuModuleBean sysMenuModule) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from SysRoleMenusBean as org where roleID ="
				+ sysMenuModule.getRoleId() + "";

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysRoleMenusBean> sysMenuRoleLst = query.list();
		return sysMenuRoleLst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delRoleMenuByCond(SysRoleMenusBean sysRoleMenusBean) {
		String hql = "delete from SysRoleMenusBean where roleID ="
				+ sysRoleMenusBean.getRoleID() + "";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		return query.executeUpdate();
	}

	@Override
	public boolean addSysRoleMenu(SysRoleMenusBean sysRoleMenusBean) {
		try {
			super.insert(sysRoleMenusBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
