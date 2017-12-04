package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.entity.SysRoleBean;
import com.fable.insightview.platform.entity.SysRoleMenusBean;
import com.fable.insightview.platform.entity.SysUserGroupRolesBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysRoleService {
	String queryMenuIdByRole(SysMenuModuleBean sysMenuModule);

	boolean addSysRoleMenu(SysRoleMenusBean sysRoleMenusBean);

	int getTotalCount(SysRoleBean sysRoleBean);

	List<SysRoleBean> getSysRoleBeanByConditions(String paramName,
			String paramValue, List<SysUserGroupRolesBean> groupRoleLst);

	List<SysRoleBean> getSysRoleBeanByConditions(String paramName,
			String paramValue);

	boolean updateSysRole(SysRoleBean sysRoleBean);

	/* 新增单位组织 */
	boolean addSysRole(SysRoleBean sysRoleBean);

	/* 删除单位组织 */
	boolean delSysRoleById(SysRoleBean sysRoleBean);

	/* 查询单位组织 */
	List<SysRoleBean> getSysRoleByConditions(SysRoleBean sysRoleBean,
			FlexiGridPageInfo flexiGridPageInfo);
	
	List<SysRoleBean> getSysRoleByconditions(String paramName,String paramValue);
	
	/* 验证角色名称 */
	boolean checkRoleName(SysRoleBean sysRoleBean);
	
	/* 删除前的验证 */
	public boolean checkBeforeDel(SysRoleBean sysRoleBean);
	
	boolean isLeaf(int menuID);
}
