package com.fable.insightview.platform.tongyipermission.service;
import java.util.List;

import com.fable.insightview.platform.entity.MenuNode;
import com.fable.insightview.platform.entity.SysMenuModuleBean;
public interface UomsPermissionService {
	List<SysMenuModuleBean> selectUomsPermissionServiceInfo();
	String getShifPermission();
	List<MenuNode>  getChilrenSysMenuModuleLst(SysMenuModuleBean sysMenuModuleBean);
    List<SysMenuModuleBean> selectUomsPermissionServiceInfoChildren(SysMenuModuleBean sysMenuModuleBean);
}
