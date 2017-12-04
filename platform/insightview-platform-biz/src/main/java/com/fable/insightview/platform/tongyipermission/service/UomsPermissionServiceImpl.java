package com.fable.insightview.platform.tongyipermission.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.entity.MenuNode;
import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.tongyipermission.mapper.UomsPermissionMapper;

@Service("uomsPermissionServiceImpl")
public class UomsPermissionServiceImpl implements UomsPermissionService {
    @Autowired
	private UomsPermissionMapper uomsPermissionMapper;
	@Override
	public List<SysMenuModuleBean> selectUomsPermissionServiceInfo() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		System.out.println("======"+sysUserInfoBeanTemp.getUserAccount());
		List<SysMenuModuleBean> list=uomsPermissionMapper.selectUomsPermissionServiceInfo(sysUserInfoBeanTemp.getUserAccount());
		return list;
	}
	@Override
	public String getShifPermission() {
		// TODO Auto-generated method stub
		return uomsPermissionMapper.getShifPermission();
	}
	@Override
	public List<MenuNode> getChilrenSysMenuModuleLst(SysMenuModuleBean sysMenuModuleBean) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		Map map=new HashMap<String,Object>();
		System.out.println("======"+sysMenuModuleBean.getParentMenuID());
		System.out.println("======"+sysUserInfoBeanTemp.getUserAccount());
		map.put("parentMenuID", sysMenuModuleBean.getParentMenuID());
		map.put("userAccount", sysUserInfoBeanTemp.getUserAccount());
		List<MenuNode> list=uomsPermissionMapper.getChilrenSysMenuModuleLst( map);
		return list;
	}
	public List<SysMenuModuleBean> selectUomsPermissionServiceInfoChildren(SysMenuModuleBean sysMenuModuleBean){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		Map map=new HashMap<String,Object>();
		map.put("parentMenuID", String.valueOf(sysMenuModuleBean.getParentMenuID()));
		map.put("userAccount", sysUserInfoBeanTemp.getUserAccount());
		List<SysMenuModuleBean> list=uomsPermissionMapper.selectUomsPermissionServiceInfoChildren(map);
		return list;
	}
   
}
