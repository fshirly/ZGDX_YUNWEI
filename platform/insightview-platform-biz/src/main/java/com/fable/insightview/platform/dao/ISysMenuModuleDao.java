package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.MenuNode;
import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysMenuModuleDao {
	List<SysMenuModuleBean> getSysMenuTreeVal();

	boolean updateSysMenu(SysMenuModuleBean sysMenuModuleBean);

	List<SysMenuModuleBean> getSysMenuByConditions(String paramName,
			String paramValue);

	/* 数据总记录数 */
	int getTotalCount(SysMenuModuleBean sysMenuModuleBean);

	/* 获取用户菜单 */
	List<SysMenuModuleBean> getUserSysMenuModule(
			SysMenuModuleBean sysMenuModuleBean, SysUserInfoBean userBean);
	
	/* 获取用户菜单 */
	List<MenuNode> getUserSubMenu(
			SysMenuModuleBean sysMenuModuleBean, SysUserInfoBean userBean);

	/* 新增部门组织 */
	boolean addSysMenuModule(SysMenuModuleBean sysMenuModuleBean);

	/* 删除部门组织 */
	boolean delSysMenuModuleById(int menuId);

	/* 菜单列表 */
	List<SysMenuModuleBean> getSysMenuModuleByConditions(
			SysMenuModuleBean sysMenuModuleBean,
			FlexiGridPageInfo flexiGridPageInfo);
	public SysMenuModuleBean getIdBymenuName(String menuName);
	
	public List<Integer> getAllMenuId(int menuId) ;
	
	public List<Integer> getChildIdByMenuIDList(List<Integer> MenuIDList);
	
	/*获得所有上级菜单id*/
	public String getparentMenuIDsByMenuID(int menuId);
	
	/*根据ID 获得菜单*/
	public SysMenuModuleBean getMenuModuleByMenuID(int menuId);
	
	/* 获得角色管理中的菜单树*/
	public List<SysMenuModuleBean> getSysMenuTreeValInSysrole();
	
	public boolean getSysRoleMenusByMenuID(int menuId) ;
	
	public  List<SysMenuModuleBean> getMenuModuleByParentMenuID(int parentMenuID);
}
