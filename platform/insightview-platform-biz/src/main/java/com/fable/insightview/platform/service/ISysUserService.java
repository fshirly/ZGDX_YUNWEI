package com.fable.insightview.platform.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrganizationalEntityBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysUserService {
	List<SysUserInfoBean> queryUserByAuto(SysUserInfoBean sysUserBean);
	
	/**根据用户查找当前部门下所有人**/
	List<SysUserInfoBean> queryUserByDepartment(SysUserInfoBean sysUserBean);

	int getTotalCountByGroup(SysUserInfoBean sysUserBean);

	List<SysUserInfoBean> getSysUserByGroup(SysUserInfoBean sysUserBean,
			FlexiGridPageInfo flexiGridPageInfo);

	boolean updateSysUser(SysUserInfoBean sysUserBean);

	/* 数据总记录数 */
	int getTotalCount(SysUserInfoBean user);

	List<SysUserInfoBean> checkUserInfo(SysUserInfoBean user);

	/* 新增单位组织 */
	boolean addSysUser(SysUserInfoBean sysUserBean);

	/* 删除单位组织 */
	boolean delSysUserById(SysUserInfoBean sysUserBean);

	/* 查询单位组织 */
	List<SysUserInfoBean> getSysUserByConditions(SysUserInfoBean sysUserBean,
			FlexiGridPageInfo flexiGridPageInfo);

	List<SysUserInfoBean> getSysUserByCondition(SysUserInfoBean sysUserBean);
	
	/* 查询单位组织 */
	List<SysUserInfoBean> getSysUserByConditions(String paramName,
			String paramValue);

	/* 查询用户信息 */
	SysUserInfoBean findSysUserById(int sysUserId);

	/* 获取所有用户 */
	List<SysUserInfoBean> getAllSysUsers();

	/**
	 * 根据部门查询人员
	 * 
	 * @param departmentBean
	 * @return
	 */
	List<SysUserInfoBean> getSysUserByDept(DepartmentBean departmentBean);
	
	boolean lockSysUser(SysUserInfoBean sysUserBean);
	boolean modifyPwd(SysUserInfoBean sysUserBean);
	
	SysUserInfoBean getTreeIdByTreeName(String treeName);
	
	List<SysUserInfoBean> getSysUserByConditions(SysUserInfoBean sysUserBean);
	
	//根据用户名获取表OrganizationEntity数据
	int getOrganization(String userAccount);
	
	public List<SysUserInfoBean> getSysUserByNotifyFilter(SysUserInfoBean sysUserBean,
			FlexiGridPageInfo flexiGridPageInfo);
	
	public int getTotalCountByNotifyFilter(SysUserInfoBean sysUserBean);
	
	public void updateStuts(String userAccount);
	
	//根据导出用户信息获取数据
	List<SysUserInfoBean> querySysUser(SysUserInfoBean sysUserBean);
	
	//表OrganizationalEntity相关操作
	boolean addOrganization(OrganizationalEntityBean organizationalEntityBean);
	List<OrganizationalEntityBean> getAllOrganizationalEntity();

	/**
	 * 根据属性精确查询
	 * @param sysUserBean
	 * @return
	 */
	List<SysUserInfoBean> queryUserByExact(SysUserInfoBean sysUserBean);
	
	/**
	 * 依据用户类型及查询条件查询用户列表信息
	 * @param userType 1-单位用户 2-供应商用户
	 * @param conditions key-column,value-expressions
	 * @return
	 */
	List<Map<String, String>> queryUsers(String userType, String orgId, Map<String, String> conditions, FlexiGridPageInfo flexiGridPageInfo);
	Integer queryUsersCount(String userType, String orgId, Map<String, String> conditions);
	
	/**
	 * 根据用户ID获取组织ID
	 * @param userId
	 * @return
	 */
	public Integer queryOrgIdByUserInfo(Integer userId);

	String getUserNameByUserId(Integer id);
	
	/***
	 * 根据身份证号码获取用户Id
	 * @param idCard
	 * @return
	 */
	Integer queryUserIdByIdCard(String idCard);

	/**
	 * 获取当前部门下以及当前部门下子部门的所有用户列表
	 * @param qryDept
	 * @return
	 */
	List<SysUserInfoBean> findUsersWithinChildDept(DepartmentBean qryDept);
}
