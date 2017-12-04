package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface IDepartmentDao extends GenericDao<DepartmentBean> {

	List<DepartmentBean> getDepartmentBeanByConditions(String paramName,
			String paramValue);

	boolean updateDepartmentBean(DepartmentBean departmentBean);

	int getTotalCount(DepartmentBean departmentBean);

	/* 新增部门组织 */
	boolean addDepartment(DepartmentBean departmentBean);

	/* 删除部门组织 */
	boolean delDepartmentById(DepartmentBean departmentBean);

	/* 查询部门组织 */
	List<DepartmentBean> getDepartmentByConditions(
			DepartmentBean departmentBean, FlexiGridPageInfo flexiGridPageInfo);

	/* 根据userID查询部门组织信息 */
	List<DepartmentBean> getDepartmentByUserID(Integer id);

	List<SysUserInfoBean> findSysUserInfoByDeptId(int deptId);

	List<DepartmentBean> getDepartmentByConditions(String paramName,
			String paramValue);

	List<DepartmentBean> getDepartmentByParendID(DepartmentBean departmentBean);

	List<SysEmploymentBean> getSysEmploymentByDeptID(
			DepartmentBean departmentBean);

	public List<DepartmentBean> getDepartmentTreeVal();
	
	public List<DepartmentBean> getDepartmentTreeSelf(String deptId);

	List<Integer> getDeptIdsByOrdId(int orgId);

	public List<Integer> getAllDeptID(int departmentID);

	public List<Integer> getChildIdByDeptIDList(List<Integer> departmentIDList);

	public List<Integer> getOrganizationIDByOrgName(String organizatioName);

	public int getOrganizationIDByDeptID(int departmentID);

	public int getDeptBYOrgIDAndParentDeptID(DepartmentBean departmentBean);

	public boolean checkDeptName(DepartmentBean departmentBean);

	public String getHeadNameByHeadID(int headID);

	public List<SysEmploymentBean> getEmploymentListByOrgID(
			DepartmentBean departmentBean);

	public DepartmentBean getDepartmentByDeptName(String deptName);

	public List<DepartmentBean> getDepartmentTreeList();

	public String getUserIDs();

	/* 根据导入对象查询数据 */
	List<DepartmentBean> querySysDept(DepartmentBean departmentBean);

	public boolean checkDeptCode(String flag, DepartmentBean departmentBean);

	List<DepartmentBean> getDepartmentByOrgIDs(String organizationIds);

	List<DepartmentBean> getDepartmentByOrgId(Integer organizationId);

	List<Integer> getDepartmentByUserId(String userId);
	
	List<DepartmentBean> getByOrgAndDeptName(DepartmentBean departmentBean);

	DepartmentBean getDepartmentByDeptCode(String deptCode);
	
	Integer getOrgByUserId(Integer userId);
	
	List<DepartmentBean> getDepartmentByConditions(DepartmentBean departmentBean);
	
	/**
	 * 获取部门中所属的单位Ids或 父部门Ids
	 * @return
	 */
	List<Integer> getExistIds (boolean isOrg);
	
	List<DepartmentBean> getDepartments(String paramName,
			String paramValue);
}
