package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.EmploymentGradeBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface IEmploymentGradeService {
	/* 新增单位组织 */
	boolean addEmploymentGrade(EmploymentGradeBean employmentGradeBean);

	/* 删除单位组织 */
	boolean delEmploymentGradeById(EmploymentGradeBean employmentGradeBean);

	/* 查询单位组织 */
	List<EmploymentGradeBean> getEmploymentGradeByConditions(
			EmploymentGradeBean employmentGradeBean,
			FlexiGridPageInfo flexiGridPageInfo);
	
	/* 查询单位组织 */
	List<EmploymentGradeBean> getEmploymentGradeByConditions(
			EmploymentGradeBean employmentGradeBean);
	
	public List<EmploymentGradeBean> getDepartmentGradeTreeVal(int deptId) ;
	public List<EmploymentGradeBean> getGradeName(int gradeId) ;
	
	//根据组织ID获取所有职位级别
	public List<EmploymentGradeBean> getGradeInfo(int organizationID) ;
}
