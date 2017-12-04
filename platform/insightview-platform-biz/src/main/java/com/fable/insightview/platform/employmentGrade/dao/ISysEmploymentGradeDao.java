package com.fable.insightview.platform.employmentGrade.dao;

import java.util.List;


import com.fable.insightview.platform.employmentGrade.entity.SysEmploymentGradeBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysEmploymentGradeDao extends GenericDao<SysEmploymentGradeBean> {
	/* 获得职务等级数据 */
	public List<SysEmploymentGradeBean> getEmploymentGradeList(SysEmploymentGradeBean employmentGrade ,FlexiGridPageInfo flexiGridPageInfo);
	
	/* 判断是否为叶子节点 */
	public boolean isLeaf(int organizationID );
	
	/* 获得子职务等级 */
	public List<Integer> getChildId(int organizationID);
	
	/* 根据单位Id获得子单位的Id */
	public List<Integer> getChildIdByOrgIDList(List<Integer> organizationIDList);
	
	/* 根据单位Id获得与所有下属单位 */
	public List<Integer> getAllOrgId(int organizationID);
	
	/* 获得数据总记录数 */
	public int getEmpGradeListCount(SysEmploymentGradeBean employmentGrade);
	
	/* 根据单位ID获得名称 */
	public String getOrganizationNameByID(int organizationID);
	
	/* 验证职务等级名称的唯一性 */
	boolean checkEmpGradeByOrgID(SysEmploymentGradeBean employmentGrade);
	
	/* 新增职务等级 */
	boolean addEmploymentGrade(SysEmploymentGradeBean employmentGrade);
	
	/* 更新职务等级 */
	boolean updateEmploymentGrade(SysEmploymentGradeBean employmentGrade);
	
	/* 根据条件获得职务等级 */
	public List<SysEmploymentGradeBean> getEmpGradeByConditions (String paramName,
			String paramValue);
	
	/* 根据职务ID获得单位员工 */
	public int getEmploymentByGradeID(int gradeID,int organizationID);
	
	/* 删除职务等级 */
	public boolean delEmpGradeByGradeID(int gradeID);
	
	/* 获得单位的深度级数 */
	public int getLevel(int organizationID);
	
	/*根据名称获得单位ID  */
	public List<Integer> getOrganizationIDByOrgName(String organizatioName);
	
	/* 根据单位名称获得职务等级 */
	public SysEmploymentGradeBean getEmpploymentByOrgName(String organizationName);
}
