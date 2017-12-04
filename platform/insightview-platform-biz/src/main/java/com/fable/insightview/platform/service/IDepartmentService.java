package com.fable.insightview.platform.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface IDepartmentService {
	boolean updateSysUser(DepartmentBean departmentBean);

	List<DepartmentBean> getDepartmentBeanByConditions(String paramName,
			String paramValue);

	int getTotalCount(DepartmentBean departmentBean);

	/* 新增单位组织 */
	boolean addDepartment(DepartmentBean departmentBean);

	/* 删除单位组织 */
	boolean delDepartmentById(DepartmentBean departmentBean);

	/* 查询单位组织 */
	List<DepartmentBean> getDepartmentByConditions(
			DepartmentBean departmentBean, FlexiGridPageInfo flexiGridPageInfo);
	/* 查询单位组织 */
	
	List<DepartmentBean> getDepartmentByConditions(DepartmentBean departmentBean);

	/* 根据userId查询单位组织 */
	List<DepartmentBean> getDepartmentByUserId(Integer id);

	List<SysUserInfoBean> findSysUserInfoByDeptId(int deptId);

	List<DepartmentBean> getDepartmentByConditions(String paramName,
			String paramValue);

	public List<DepartmentBean> getDepartmentTreeVal();
	
	public List<DepartmentBean> getDepartmentTreeSelf(String deptId);

	public List<DepartmentBean> getDepartmentTreeList();

	public boolean getDeptByOrgIDAndParentDeptID(DepartmentBean departmentBean);

	public boolean checkDeptName(DepartmentBean departmentBean);

	List<Integer> getDeptIdsByOrdId(int orgId);

	public List<SysEmploymentBean> getEmploymentListByOrgID(
			DepartmentBean departmentBean);

	public DepartmentBean getDepartmentByDeptName(String deptName);

	/* 删除前的验证 */
	public boolean checkBeforeDel(DepartmentBean departmentBean);

	DepartmentBean getDepartmentById(Integer id);

	/* 根据导入对象查询数据 */
	List<DepartmentBean> querySysDept(DepartmentBean departmentBean);

	/**
	 * 验证部门编码是否存在
	 * 
	 * @param flag
	 * @param departmentBean
	 */
	public boolean checkDeptCode(String flag, DepartmentBean departmentBean);

	List<DepartmentBean> getDepartmentByOrgID(int organizationId);

	List<DepartmentBean> getDepartmentByOrgId(Integer organizationId);

	/**
	 * 获取当前用户所在单位下的部门
	 * @param valueOf
	 * @return
	 */
	List<Integer> getDepartmentByUserId(String userId);
	
	/**
	 * 根据当前用户获取org
	 * @param userId
	 * @return  nlb
	 */
	int getOrgByUserId(Integer userId);
	
	/**
	 * 获取部门中所属的单位Ids或 父部门Ids
	 * @return
	 */
	List<Integer> getExistIds (boolean isOrg);
	
	List<DepartmentBean> getDepartments(String paramName,
			String paramValue);
}
