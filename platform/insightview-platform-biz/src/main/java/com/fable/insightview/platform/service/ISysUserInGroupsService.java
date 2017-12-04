package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.SysUserInGroupsBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;

/**
 * 用户与组的关联表的信息处理接口
 * 
 * @author Administrator sanyou
 * 
 */
public interface ISysUserInGroupsService {
	boolean addSysUserInGroupsBean(SysUserInGroupsBean sysUserInGroupsBean);
	
	public boolean removeSysUserInGroupsBean(SysUserInGroupsBean sysUserInGroupsBean);

	/* 查询用户信息通过groupId */
	List<SysUserInfoBean> findSysUserInfoByGroupId(int groupId);

	/* 根据用户Id查询SysUserInGroupsBean */
	List<SysUserInGroupsBean> findGroupsByUserId(int userId);

	/* 根据用户Id查询SysUserInGroupsBean */
	List<SysUserInGroupsBean> findGroupsByUserId(String userId);
}
