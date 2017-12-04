package com.fable.insightview.platform.employmentGrade.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.employmentGrade.entity.SysEmploymentGradeBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysEmploymentGradeService {
	/* 获得职务等级数据 */
	public Map<String, Object> getEmploymentGradeList(SysEmploymentGradeBean employmentGrade ,FlexiGridPageInfo flexiGridPageInfo);
	
	/* 验证职务等级名称的唯一性 */
	boolean checkEmpGradeByOrgID(SysEmploymentGradeBean employmentGrade);
	
	/*  新增职务等级 */
	boolean addEmploymentGrade(SysEmploymentGradeBean employmentGrade);
	
	/* 更新职务等级 */
	boolean updateEmploymentGrade(SysEmploymentGradeBean employmentGrade);
	
	/* 根据条件获得职务等级 */
	public List<SysEmploymentGradeBean> getEmpGradeByConditions (String paramName,String paramValue);
	
	/* 删除职务等级 */
	public boolean deleteEmplomentGrade(int gradeID);
	
	/* 根据单位名称获得职务等级 */
	public SysEmploymentGradeBean getEmpploymentByOrgName(String organizationName);
	
	/* 验证职务是否被单位员工使用 */
	public int getEmploymentByGradeID(int gradeID,int organizationID);
}
