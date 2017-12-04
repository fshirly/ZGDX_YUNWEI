package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.MenuNode;
import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysMenuModuleService {

	List<SysMenuModuleBean> getSysMenuTreeVal();

	List<SysMenuModuleBean> getSysMenuByConditions(String paramName,
			String paramValue);

	boolean updateSysUser(SysMenuModuleBean sysMenuBean);

	/* 数据总记录数 */
	int getTotalCount(SysMenuModuleBean sysMenuModuleBean);

	List<SysMenuModuleBean> getUserSysMenuModule(
			SysMenuModuleBean sysMenuModuleBean, SysUserInfoBean userBean);
	
	List<MenuNode> getUserSubMenu(SysMenuModuleBean sysMenuModuleBean, SysUserInfoBean userBean);

	/* 新增单位组织 */
	boolean addSysMenuModule(SysMenuModuleBean sysMenuModuleBean);

	/* 删除单位组织 */
	boolean delSysMenuModuleById(int menuId);

	/* 查询单位组织 */
	List<SysMenuModuleBean> getSysMenuModuleByConditions(
			SysMenuModuleBean sysMenuModuleBean,
			FlexiGridPageInfo flexiGridPageInfo);
	public SysMenuModuleBean getIdBymenuName(String menuName);
	
	/* 获得角色管理中的菜单树*/
	public List<SysMenuModuleBean> getSysMenuTreeValInSysrole();

	public String getparentMenuIDsByMenuID(int menuId);
	public boolean getSysRoleMenusByMenuID(int menuId);
}
