package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.SysUserGroupBean;
import com.fable.insightview.platform.entity.SysUserGroupRolesBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysUserGroupService {
	boolean checkUserGroupByConditions(SysUserGroupBean sysUserGroup);

	List<String> getGroupByUserId(SysUserInfoBean sysUserInfoBean);

	boolean insertGroupRole(SysUserGroupRolesBean groupRole);

	boolean delGroupRoleByCond(SysUserGroupRolesBean groupRole);

	List<SysUserGroupRolesBean> getSysUserGroupRole(String paramName,
			String paramValue);

	List<SysUserGroupBean> getSysUserGroupByConditions(String paramName,
			String paramValue);

	boolean updateSysUserGroup(SysUserGroupBean sysUserGroupBean);

	/* 数据总记录数 */
	int getTotalCount(SysUserGroupBean sysUserGroup);

	/* 新增用户组 */
	boolean addSysUserGroup(SysUserGroupBean sysUserGroup);

	/* 删除用户组 */
	boolean delSysUserGroupById(SysUserGroupBean sysUserGroup);
	
	boolean delGroupAndReleationById(SysUserGroupBean sysUserGroup);

	/* 查询用户组 */
	List<SysUserGroupBean> getSysUserGroupByConditions(
			SysUserGroupBean sysUserGroup, FlexiGridPageInfo flexiGridPageInfo);

	/* 根据组织Id查询其下的组group */
	List<SysUserGroupBean> getSysUserGroupByOrganizationId(int organizationId);

	/* 查询所有用户组 */
	List<SysUserGroupBean> getAllGroups();

	/* 根据groupId查询用户组 */
	SysUserGroupBean findGroupById(int groupId);
	
	boolean delGroupAndReleationByIds(String groupIDs);

	public List<SysUserGroupBean> getKeXinGroup();

}
