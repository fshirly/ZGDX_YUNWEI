package com.fable.insightview.platform.dao;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysUserDao extends GenericDao<SysUserInfoBean> {
	List<SysUserInfoBean> queryUserByAuto(SysUserInfoBean sysUserBean);
	
	/**根据用户查找当前部门下所有人**/
	List<SysUserInfoBean> queryUserByDepartment(SysUserInfoBean sysUserBean);
	
	int getTotalCountByGroup(SysUserInfoBean sysUserBean);

	List<SysUserInfoBean> getSysUserByGroup(SysUserInfoBean sysUserBean,
			FlexiGridPageInfo flexiGridPageInfo);

	boolean updateSysUser(SysUserInfoBean sysUserBean);
	
	void updateSysUserSyn(SysUserInfoBean sysUserBean);
	
	//获取所有用户
	int getTotalCount(SysUserInfoBean user);
	
	//根据条件检查用户
	List<SysUserInfoBean> chkUserInfo(SysUserInfoBean sysUserBean);

	/* 新增部门组织 */
	boolean addSysUser(SysUserInfoBean sysUserBean);

	/* 删除部门组织 */
	int delSysUserById(SysUserInfoBean sysUserBean);

	/* 查询部门组织 */
	List<SysUserInfoBean> getSysUserByConditions(SysUserInfoBean sysUserBean,
			FlexiGridPageInfo flexiGridPageInfo);
	
	List<SysUserInfoBean> getSysUserByCondition(SysUserInfoBean sysUserBean);

	/* 查询部门组织 */
	List<SysUserInfoBean> getSysUserByConditions(String paramName,
			String paramValue);
	
	//根据用户ID获取用户信息
	public List<SysUserInfoBean> getUserNameByUserIds(List<Integer> userIds);
	
	//获取用户总是
	public List<SysUserInfoBean> getAllSysUsers();
	
	//根据部门信息获取用户
	List<SysUserInfoBean> getSysUserByDept(DepartmentBean departmentBean);
	
	//锁定用户
	boolean lockSysUser(SysUserInfoBean sysUserBean);
	
	//密码修改
	boolean modifyPwd(SysUserInfoBean sysUserBean);
	
	//根据ID 获取用户名
	public String getUserNameByUserId(int userId);
	
	SysUserInfoBean getTreeIdByTreeName(String treeName);
	
	//根据条件查询用户信息
	List<SysUserInfoBean> getSysUserByConditions(SysUserInfoBean sysUserBean);
	
	//根据用户名获取表OrganizationEntity数据
	int getOrganization(String userAccount);
	
	public List<SysUserInfoBean> getSysUserByNotifyFilter(SysUserInfoBean sysUserBean,
			FlexiGridPageInfo flexiGridPageInfo);
	
	public int getTotalCountByNotifyFilter(SysUserInfoBean sysUserBean);
	
	public void updateStuts(String userAccount);
	
	//根据导出用户信息获取数据
	List<SysUserInfoBean> querySysUser(SysUserInfoBean sysUserBean);

	List<SysUserInfoBean> queryUserByExact(SysUserInfoBean sysUserBean);
	
	//public List querySysUserByDeptId(Integer deptId);
	
	/**
	 * 依据用户类型及查询条件查询用户列表信息
	 * @param userType 1-单位用户 2-供应商用户
	 * @param conditions key-column,value-expressions
	 * @return
	 */
	List<Map<String, String>> queryUsers(String userType, String orgId, Map<String, String> conditions, FlexiGridPageInfo flexiGridPageInfo);
	Integer queryUsersCount(String userType, String orgId, Map<String, String> conditions);

	/**
	 * 根据用户ID获取所在组织ID
	 * @param userId
	 * @return
	 */
	Integer queryOrgIdByUserInfo(Integer userId);
	/***
	 * 根据身份证号码获取用户Id
	 * @param idCard
	 * @return
	 */
	Integer queryUserIdByIdCard(String idCard);
	
	/**
	 * 获取当前部门以及其子部门下的所有用户
	 * @param qryDept
	 * @return
	 */
	List<SysUserInfoBean> findUsersWithinChildDept(DepartmentBean qryDept);
}
