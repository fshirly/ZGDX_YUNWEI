package com.fable.insightview.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.CTD;
import com.fable.insightview.platform.dao.ISysMenuModuleDao;
import com.fable.insightview.platform.entity.MenuNode;
import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

/**
 * 单位组织Dao
 * 
 * @author 武林
 * 
 */
@Repository("sysMenuModule")
public class SysMenuModuleDaoImpl extends
		GenericDaoHibernate<SysMenuModuleBean> implements ISysMenuModuleDao {
	private final Logger logger = LoggerFactory
			.getLogger(SysMenuModuleDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSysMenu(SysMenuModuleBean sysMenuModuleBean) {
		try {
			super.update(sysMenuModuleBean);
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
	public List<SysMenuModuleBean> getSysMenuTreeValInSysrole() {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from SysMenuModuleBean ";
		List<Integer> menuIDList = new ArrayList<Integer>();
		menuIDList.add(3);
		List<Integer> childIDList = getAllMenuId(3);
		for (int id : childIDList) {
			menuIDList.add(id);
		}
		hql += " where menuId  in(" + menuIDList.get(0);
		for (int i = 1; i < menuIDList.size(); i++) {
			logger.info("子菜单有：" + menuIDList.get(i));
			hql += "," + menuIDList.get(i);
		}
		hql += ")";
		logger.info("加载菜单的hql语句：" + hql);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysMenuModuleBean> sysMenuModuleBean = query.list();
		return sysMenuModuleBean;
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenuModuleBean> getSysMenuByConditions(String paramName,
			String paramValue) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from SysMenuModuleBean  org where 1=1";
		if (null != paramValue && !"".equals(paramValue)) {
			hql += " and " + paramName + " =" + paramValue + "";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysMenuModuleBean> sysMenuModuleBean = query.list();
		return sysMenuModuleBean;
	}

	/*
	 * 递归查询用户有权限的菜单
	 * 
	 * @author 武林
	 */
	@Override
	public List<SysMenuModuleBean> getUserSysMenuModule(
			SysMenuModuleBean sysMenuModuleBean, SysUserInfoBean userBean) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		// String hql = "SELECT * FROM SysMenuModule WHERE 1=1";
		String hql = "select distinct smm.* from SysMenuModule smm "
				+ "left join SysRoleMenus srm on smm.MenuID=srm.MenuID "
				+ "left join SysUserGroupRoles sugr on sugr.RoleID=srm.RoleID "
				+ "left join SysUserInGroups suig on suig.GroupID=sugr.GroupID where 1=1 ";
		if (null != sysMenuModuleBean
				&& sysMenuModuleBean.getParentMenuID() != 0) {
			hql += " and ParentMenuID=" + sysMenuModuleBean.getParentMenuID();
		} else {
			hql += " and MenuLevel=1  and ParentMenuID=0";
		}
		hql += " and suig.UserID=" + sysUserInfoBeanTemp.getId();
		if (null != sysMenuModuleBean
				&& sysMenuModuleBean.getParentMenuID() != 0) {
			hql += " order by smm.ShowOrder asc ";
		} else {
			hql += " order by smm.ShowOrder desc ";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addEntity(SysMenuModuleBean.class);
		List<SysMenuModuleBean> sysMenuModuleBeanLst = query.list();
		// Set set = new HashSet();
		// List newList = new ArrayList();
		// for (Iterator iter = sysMenuModuleBeanLst.iterator();
		// iter.hasNext();){
		// Object element = iter.next();
		// if (set.add(element))newList.add(element);
		// }
		// sysMenuModuleBeanLst.clear();
		// sysMenuModuleBeanLst.addAll(newList);
		return sysMenuModuleBeanLst;
	}

	/*
	 * 递归查询用户有权限的菜单
	 * 
	 * @author 武林
	 */
	@Override
	public List<MenuNode> getUserSubMenu(SysMenuModuleBean sysMenuModuleBean,
			SysUserInfoBean userBean) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		String hql = "";
		if (CTD.isMySQL()) {
			hql += "SELECT distinct smm.MenuID   id ,smm.MenuName   text,smm.ParentMenuID,smm.LinkURL,smm.Icon   iconCls,smm.ShowOrder   showOrder,smm.Descr descr "
					+ "FROM SysMenuModule smm "
					+ "left join SysRoleMenus srm on smm.MenuID=srm.MenuID "
					+ "left join SysUserGroupRoles sugr on sugr.RoleID=srm.RoleID "
					+ "left join SysUserInGroups suig on suig.GroupID=sugr.GroupID where 1=1 ";
			if (null != sysMenuModuleBean
					&& null != sysMenuModuleBean.getParentMenuID() + ""
					&& sysMenuModuleBean.getParentMenuID() != 0
					&& !"".equals(sysMenuModuleBean.getParentMenuID())) {
				hql += " and suig.UserID=" + sysUserInfoBeanTemp.getId()
						+ " And FIND_IN_SET(smm.MenuID, getChildLst("
						+ sysMenuModuleBean.getParentMenuID()
						+ ")) order by smm.ParentMenuID";// 此处添加 order by
															// ParentMenuID
			} else {
				hql += " and smm.MenuLevel=1  and ParentMenuID=0";
				// 0:企业内IT部门用户 1:企业业务部门用户 2:外部供应商用户
			}
		} else {
			hql += "SELECT smm.MenuID   id ,smm.MenuName   text,smm.ParentMenuID,smm.LinkURL,smm.Icon   iconCls,smm.ShowOrder   showOrder "
					+ "FROM SysMenuModule smm "
					+ "left join SysRoleMenus srm on smm.MenuID=srm.MenuID "
					+ "left join SysUserGroupRoles sugr on sugr.RoleID=srm.RoleID "
					+ "left join SysUserInGroups suig on suig.GroupID=sugr.GroupID where 1=1 ";
			if (null != sysMenuModuleBean
					&& null != sysMenuModuleBean.getParentMenuID() + ""
					&& sysMenuModuleBean.getParentMenuID() != 0
					&& !"".equals(sysMenuModuleBean.getParentMenuID())) {
				hql += " and suig.UserID="
						+ sysUserInfoBeanTemp.getId()
						+ " And smm.MenuID in (select MenuID from SysMenuModule t start with t.menuid="
						+ sysMenuModuleBean.getParentMenuID()
						+ " connect by prior t.menuid=t.parentmenuid) order by smm.ParentMenuID";
			} else {
				hql += " and smm.MenuLevel=1  and ParentMenuID=0";
				// 0:企业内IT部门用户 1:企业业务部门用户 2:外部供应商用户
			}
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addScalar("id", IntegerType.INSTANCE).addScalar("text")
				.addScalar("parentMenuID", IntegerType.INSTANCE)
				.addScalar("linkURL").addScalar("iconCls")
				.addScalar("showOrder", IntegerType.INSTANCE).addScalar("descr");

		List<MenuNode> sysMenuModuleBeanLst = query.setResultTransformer(
				Transformers.aliasToBean(MenuNode.class)).list();
		return sysMenuModuleBeanLst;
	}

	/*
	 * 新增单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addSysMenuModule(SysMenuModuleBean sysMenuModuleBean) {
		try {
			super.insert(sysMenuModuleBean);
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
	public boolean delSysMenuModuleById(int menuId) {
		/*
		 * try { super.delete(sysMenuModuleBean); } catch (Exception e) {
		 * e.printStackTrace(); return false; } return true;
		 */
		String hql = "delete from SysMenuModule where MenuID=" + menuId;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int i = query.executeUpdate();
		if (i >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getTotalCount(SysMenuModuleBean sysMenuModuleBean) {
		String hql = "select count(1)   count from SysMenuModule   a where 1=1";
		hql = commonConditions(hql, sysMenuModuleBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();

		return count;
	}

	/*
	 * 菜单列表
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenuModuleBean> getSysMenuModuleByConditions(
			SysMenuModuleBean sysMenuModuleBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		String hql = "from SysMenuModuleBean   org where 1=1";
		hql = commonConditions(hql, sysMenuModuleBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		List<SysMenuModuleBean> sysMenuModuleBeanLst = query.list();
		return sysMenuModuleBeanLst;
	}

	public String commonConditions(String hql,
			SysMenuModuleBean sysMenuModuleBean) {
		List<Integer> menuIDList = new ArrayList<Integer>();
		if (null != sysMenuModuleBean.getMenuNameFilter()
				&& !"".equals(sysMenuModuleBean.getMenuNameFilter())) {
			hql += " and menuName LIKE '%"
					+ sysMenuModuleBean.getMenuNameFilter() + "%'";
		}
		if (null != sysMenuModuleBean.getMenuLevelFilter()
				&& !"".equals(sysMenuModuleBean.getMenuLevelFilter())) {
			hql += " and menuLevel='" + sysMenuModuleBean.getMenuLevelFilter()
					+ "'";
		}
		if (null != sysMenuModuleBean.getParentMenuIDFilter()
				&& !"".equals(sysMenuModuleBean.getParentMenuIDFilter())) {
			menuIDList.add(Integer.parseInt(sysMenuModuleBean.getParentMenuIDFilter()));
			List<Integer> childIDList=getChildIdByMenuIDList(menuIDList);
			for (int id : childIDList) {
				menuIDList.add(id);
			}
			hql += " and menuId  in(" + menuIDList.get(0);
			for (int i = 1; i < menuIDList.size(); i++) {
				hql += "," + menuIDList.get(i);
			}
			hql += ")";
			
		/*	hql += " and parentMenuID='"
					+ sysMenuModuleBean.getParentMenuIDFilter()
					+ "' or menuId="
					+ sysMenuModuleBean.getParentMenuIDFilter();*/
		}
		if (null != sysMenuModuleBean.getParID()
				&& !"".equals(sysMenuModuleBean.getParID())
				&& !"-1".equals(sysMenuModuleBean.getParID())) {
			hql += " and menuId  in(" + sysMenuModuleBean.getParID() + ")";
		}
		return hql;
	}

	@Override
	public SysMenuModuleBean getIdBymenuName(String menuName) {
		String hql = "from SysMenuModuleBean where menuName like '%" + menuName
				+ "%'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysMenuModuleBean> menus = query.list();
		String parId = "-1";
		for (int i = 0; i < menus.size(); i++) {
			parId = parId + "," + menus.get(i).getMenuId();
		}
		SysMenuModuleBean menuBean = new SysMenuModuleBean();
		menuBean.setParID(parId);
		return menuBean;
	}

	@Override
	public List<Integer> getAllMenuId(int menuId) {
		List<Integer> allMenuIDList = new ArrayList<Integer>();
		List<Integer> childIdList = new ArrayList<Integer>();
		childIdList.add(menuId);
		while (true) {
			List<Integer> moreChildIDList = getChildIdByMenuIDList(childIdList);
			for (int i = 0; i < childIdList.size(); i++) {
				childIdList.remove(i);
			}
			if (moreChildIDList.size() != 0) {
				for (int i = 0; i < moreChildIDList.size(); i++) {
					childIdList.add(moreChildIDList.get(i));
					allMenuIDList.add(moreChildIDList.get(i));
				}
			} else {
				break;
			}
		}
		return allMenuIDList;
	}

	@Override
	public List<Integer> getChildIdByMenuIDList(List<Integer> MenuIDList) {
		String menuIds = "";
		for (int i = 0; i < MenuIDList.size(); i++) {
			menuIds += MenuIDList.get(i) + ",";
		}
		menuIds = menuIds.substring(0, menuIds.lastIndexOf(","));
		String sql = "select MenuID from SysMenuModule where ParentMenuID in("
				+ menuIds + ")";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Integer> childIDList = new ArrayList<Integer>();
		childIDList = query.list();
		return childIDList;
	}

	@Override
	public SysMenuModuleBean getMenuModuleByMenuID(int menuId) {
		String hql = "from SysMenuModuleBean   org where 1=1";
		if (menuId != 0) {
			hql += " and MenuID=" + menuId;
		}

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysMenuModuleBean> sysMenuModuleList = query.list();
		SysMenuModuleBean sysMenuModuleBean = sysMenuModuleList.get(0);
		return sysMenuModuleBean;
	}

	@Override
	public String getparentMenuIDsByMenuID(int menuId) {
		String parentMenuIDs = "";
		int parentMenuID = getMenuModuleByMenuID(menuId).getParentMenuID();
		;
		int menuLevel = getMenuModuleByMenuID(menuId).getMenuLevel();
		while (true) {
			// menuLevel=getMenuModuleByMenuID(parentMenuID).getMenuLevel();
			if (menuLevel != 0) {
				parentMenuIDs += parentMenuID + ",";
				menuLevel = getMenuModuleByMenuID(parentMenuID).getMenuLevel();
				parentMenuID = getMenuModuleByMenuID(parentMenuID)
						.getParentMenuID();
			} else {
				break;
			}
		}
		logger.info(menuId + "所有的上级菜单：" + parentMenuIDs);
		return parentMenuIDs;
	}

	@Override
	public List<SysMenuModuleBean> getSysMenuTreeVal() {
		String hql = "from SysMenuModuleBean   org where menuLevel in(0,1,2,3) ";

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysMenuModuleBean> sysMenuModuleBean = query.list();
		return sysMenuModuleBean;
	}

	@Override
	public boolean getSysRoleMenusByMenuID(int menuId) {
		String sql = "select count(1) from SysRoleMenus where menuID=" + menuId;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		int count = ((Number) query.uniqueResult()).intValue();
		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<SysMenuModuleBean> getMenuModuleByParentMenuID(int parentMenuID) {
		String hql = "from SysMenuModuleBean   org where 1=1";
		if (parentMenuID != 0) {
			hql += " and parentMenuID=" + parentMenuID;
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysMenuModuleBean> sysMenuModuleList = query.list();
		return sysMenuModuleList;
	}

}
