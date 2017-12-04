package com.fable.insightview.platform.tongyipermission.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.entity.MenuNode;
import com.fable.insightview.platform.entity.SysMenuModuleBean;

public interface UomsPermissionMapper {
	List<SysMenuModuleBean> selectUomsPermissionServiceInfo(@Param(value="account")String account);
	String getShifPermission();
	List<MenuNode> getChilrenSysMenuModuleLst(Map map);
	List<SysMenuModuleBean> selectUomsPermissionServiceInfoChildren(Map map);
}
