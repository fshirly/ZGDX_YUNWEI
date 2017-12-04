package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.entity.SysRoleMenusBean;

public interface ISysRoleMenuDao {
	List<SysRoleMenusBean> getSysMenuByRole(SysMenuModuleBean sysMenuModule);

	int delRoleMenuByCond(SysRoleMenusBean sysRoleMenusBean);

	boolean addSysRoleMenu(SysRoleMenusBean sysRoleMenusBean);
}
