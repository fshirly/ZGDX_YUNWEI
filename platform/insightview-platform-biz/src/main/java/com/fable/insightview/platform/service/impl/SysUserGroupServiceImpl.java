package com.fable.insightview.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.IOrganizationalEntityDao;
import com.fable.insightview.platform.dao.ISysUserGroupDao;
import com.fable.insightview.platform.dao.ISysUserInGroupsDao;
import com.fable.insightview.platform.entity.SysUserGroupBean;
import com.fable.insightview.platform.entity.SysUserGroupRolesBean;
import com.fable.insightview.platform.entity.SysUserInGroupsBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.service.ISysUserGroupService;
import com.fable.insightview.platform.common.bpm.activiti.service.Process;

/**
 * 用户组组织Service
 * 
 * @author 武林
 * 
 */
@Service("sysUserGroupService")
public class SysUserGroupServiceImpl implements ISysUserGroupService {

	@Autowired
	protected ISysUserGroupDao sysUserGroupDao;
	@Autowired
	protected IOrganizationalEntityDao organizationalEntityDao;
	@Autowired
	protected ISysUserInGroupsDao sysUserInGroupsDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getGroupByUserId(SysUserInfoBean sysUserInfoBean) {
		return sysUserGroupDao.getGroupByUserId(sysUserInfoBean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean insertGroupRole(SysUserGroupRolesBean groupRole) {
		String[] roleIdArr = groupRole.getRoleIdArr().split(",");
		List<SysUserGroupRolesBean> groupRoleLst = new ArrayList<SysUserGroupRolesBean>();
		for (String roleId : roleIdArr) {
			SysUserGroupRolesBean sysUserGroupRolesBean = new SysUserGroupRolesBean(
					groupRole.getGroupID(), Integer.parseInt(roleId));
			groupRoleLst.add(sysUserGroupRolesBean);
		}
		return sysUserGroupDao.insertGroupRole(groupRoleLst);
	}

	@Override
	public boolean delGroupRoleByCond(SysUserGroupRolesBean groupRole) {

		return sysUserGroupDao.delGroupRoleByCond(groupRole) > 0 ? true : false;
	}

	@Override
	public List<SysUserGroupBean> getSysUserGroupByConditions(String paramName,
			String paramValue) {
		List<SysUserGroupBean> sysUserGroupLst = sysUserGroupDao
				.getSysUserGroupByConditions(paramName, paramValue);
		return sysUserGroupLst;
	}

	@Override
	public List<SysUserGroupRolesBean> getSysUserGroupRole(String paramName,
			String paramValue) {
		List<SysUserGroupRolesBean> groupRoleLst = sysUserGroupDao
				.getSysUserGroupRole(paramName, paramValue);
		return groupRoleLst;
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSysUserGroup(SysUserGroupBean sysUserGroupBean) {
		return sysUserGroupDao.updateSysUserGroup(sysUserGroupBean);
	}

	@Override
	public int getTotalCount(SysUserGroupBean sysUserGroup) {
		String groupName= sysUserGroup.getGroupName();
		String organizationName = sysUserGroup.getOrganizationName();
		if(groupName!=null && !"".equals(groupName)){
			if(groupName.contains("%")){
				groupName = groupName.replace("%", "\\%");
			}
		}
		if(organizationName!=null && !"".equals(organizationName)){
			if(organizationName.contains("%")){
				organizationName = organizationName.replace("%", "\\%");
			}
		}
		return sysUserGroupDao.getTotalCount(sysUserGroup);
	}

	/*
	 * 新增用户组组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addSysUserGroup(SysUserGroupBean sysUserGroup) {
		boolean addUserGroup = sysUserGroupDao.addSysUserGroup(sysUserGroup);
		if(addUserGroup == true){
//			OrganizationalEntityBean orgE = new OrganizationalEntityBean();
//			orgE.setDtype("Group");
//			orgE.setId(sysUserGroup.getGroupID() + "");
//			organizationalEntityDao.insert(orgE);
			try {
				Process.instance().identityAddGroup(String.valueOf(sysUserGroup.getGroupID()), sysUserGroup.getGroupName(), 
						Process.ID_GROUP_TYPE_ASSIGNMENT);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 删除用户组组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean delSysUserGroupById(SysUserGroupBean sysUserGroup) {
		List<SysUserGroupRolesBean> userGroupRolesList = sysUserGroupDao.getSysUserGroupRole("groupID",sysUserGroup.getGroupID()+"");
		List<SysUserInGroupsBean> userInGroupsList = sysUserGroupDao.getUserInGroupsByGroupID(sysUserGroup.getGroupID());
		if((null == userGroupRolesList || userGroupRolesList.size()<=0) &&(null == userInGroupsList || userInGroupsList.size()<=0)){
			try {
				Process.instance().identityDelGroup(String.valueOf(sysUserGroup.getGroupID()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sysUserGroupDao.delSysUserGroupById(sysUserGroup);
		}
		return false;
	}

	/*
	 * 查询用户组组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean checkUserGroupByConditions(SysUserGroupBean sysUserGroup) {
		// TODO Auto-generated method stub
		return  sysUserGroupDao.checkGroupName(sysUserGroup);
	}

	/*
	 * 查询用户组组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<SysUserGroupBean> getSysUserGroupByConditions(
			SysUserGroupBean sysUserGroup, FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		String groupName= sysUserGroup.getGroupName();
		String organizationName = sysUserGroup.getOrganizationName();
		if(groupName!=null && !"".equals(groupName)){
			if(groupName.contains("%")){
				groupName = groupName.replace("%", "\\%");
			}
		}
		if(organizationName!=null && !"".equals(organizationName)){
			if(organizationName.contains("%")){
				organizationName = organizationName.replace("%", "\\%");
			}
		}
		sysUserGroup.setGroupName(groupName);
		sysUserGroup.setOrganizationName(organizationName);
		return sysUserGroupDao.getSysUserGroupByConditions(sysUserGroup,
				flexiGridPageInfo);
	}

	/*
	 * 根据组织id查询组group
	 * 
	 * @author sanyou
	 */
	@Override
	public List<SysUserGroupBean> getSysUserGroupByOrganizationId(
			int organizationId) {
		return sysUserGroupDao.getSysUserGroupByOrganizationId(organizationId);
	}

	/*
	 * 查询所有的组group信息
	 * 
	 * @author sanyou
	 */
	@Override
	public List<SysUserGroupBean> getAllGroups() {
		return sysUserGroupDao.findAllSysUserGroup();
	}

	/*
	 * 查询所有的组group信息
	 * 
	 * @author sanyou
	 */
	@Override
	public SysUserGroupBean findGroupById(int groupId) {
		return sysUserGroupDao.getById(groupId);
	}

	/**
	 * 删除正在被使用的用户组
	 */
	@Override
	public boolean delGroupAndReleationById(SysUserGroupBean sysUserGroup) {
		try {
			SysUserGroupRolesBean groupRole = new SysUserGroupRolesBean();
			groupRole.setGroupID(sysUserGroup.getGroupID());
			//删除用户组角色权限
			sysUserGroupDao.delGroupRoleByCond(groupRole);
			//删除用户与组关系
			sysUserInGroupsDao.deleteByGroupID(sysUserGroup.getGroupID());
			//删除用户组
			sysUserGroupDao.delSysUserGroupById(sysUserGroup);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 批量删除正在被使用的用户组
	 */
	@Override
	public boolean delGroupAndReleationByIds(String groupIDs) {
		String[] spilitIds = groupIDs.split(",");
		for (String splitId : spilitIds) {
			int groupId = Integer.parseInt(splitId);
			try {
				SysUserGroupRolesBean groupRole = new SysUserGroupRolesBean();
				groupRole.setGroupID(groupId);
				//删除用户组角色权限
				sysUserGroupDao.delGroupRoleByCond(groupRole);
				//删除用户与组关系
				sysUserInGroupsDao.deleteByGroupID(groupId);
				//删除用户组
				SysUserGroupBean sysUserGroup = new SysUserGroupBean();
				sysUserGroup.setGroupID(groupId);
				sysUserGroupDao.delSysUserGroupById(sysUserGroup);
				Process.instance().identityDelGroup(String.valueOf(groupId));
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<SysUserGroupBean> getKeXinGroup() {
		// TODO Auto-generated method stub
		return sysUserGroupDao.getKeXinGroup();
	}

}
