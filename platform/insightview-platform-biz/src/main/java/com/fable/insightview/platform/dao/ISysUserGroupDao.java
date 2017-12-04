package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.SysUserGroupBean;
import com.fable.insightview.platform.entity.SysUserGroupRolesBean;
import com.fable.insightview.platform.entity.SysUserInGroupsBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysUserGroupDao extends GenericDao<SysUserGroupBean> {
	List<SysUserGroupBean> getSysUserGroupByConditions(
			SysUserGroupBean sysUserGroupBean);

	List<String> getGroupByUserId(SysUserInfoBean sysUserInfoBean);

	boolean insertGroupRole(List<SysUserGroupRolesBean> groupRoleLst);

	int delGroupRoleByCond(SysUserGroupRolesBean groupRole);

	List<SysUserGroupRolesBean> getSysUserGroupRole(String paramName,
			String paramValue);

	List<SysUserGroupBean> getSysUserGroupByConditions(String paramName,
			String paramValue);

	boolean updateSysUserGroup(SysUserGroupBean sysUserGroupBean);

	/* 获取总记录数 */
	int getTotalCount(SysUserGroupBean sysUserGroup);

	/* 新增部门组织 */
	boolean addSysUserGroup(SysUserGroupBean sysUserGroupBean);

	/* 删除部门组织 */
	boolean delSysUserGroupById(SysUserGroupBean sysUserGroupBean);

	/* 查询部门组织 */
	List<SysUserGroupBean> getSysUserGroupByConditions(
			SysUserGroupBean sysUserGroupBean,
			FlexiGridPageInfo flexiGridPageInfo);

	/* 根据组织ID查询其下的组group */
	List<SysUserGroupBean> getSysUserGroupByOrganizationId(int OrganizationId);

	List<SysUserGroupBean> findAllSysUserGroup();
	
	boolean checkGroupName(SysUserGroupBean sysUserGroupBean);
	
	List<SysUserInGroupsBean> getUserInGroupsByGroupID(int groupID);
	
	public List<Integer> getOrganizationIDByOrgName(String organizatioName);

	public List<SysUserGroupBean> getKeXinGroup();
}
