package com.fable.insightview.platform.employmentGrade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.ISysEmploymentDao;
import com.fable.insightview.platform.employmentGrade.dao.ISysEmploymentGradeDao;
import com.fable.insightview.platform.employmentGrade.entity.SysEmploymentGradeBean;
import com.fable.insightview.platform.employmentGrade.service.ISysEmploymentGradeService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
/**
 * 职务等级service
 * 
 * @author hanl
 *
 */
@Service("sysEmploymentGradeService")
public class SysEmploymentGradeServiceImpl implements ISysEmploymentGradeService {
	@Autowired
	private ISysEmploymentGradeDao sysEmploymentGradeDao;
	
	@Autowired
	private ISysEmploymentDao sysEmploymentDao;
	
	/*
	 * 加载职务等级列表
	 */
	@Override
	public Map<String, Object> getEmploymentGradeList(SysEmploymentGradeBean employmentGrade ,FlexiGridPageInfo flexiGridPageInfo){
		Map<String, Object> result = new HashMap<String, Object>();
		List<SysEmploymentGradeBean> empGradeList = sysEmploymentGradeDao.getEmploymentGradeList(employmentGrade, flexiGridPageInfo);
		int count = sysEmploymentGradeDao.getEmpGradeListCount(employmentGrade);
		result.put("total", count);
		result.put("empGradeList", empGradeList);
		return result;
		
	}
	
	/*
	 * 验证职务名称
	 */
	@Override
	public boolean checkEmpGradeByOrgID(SysEmploymentGradeBean employmentGrade) {
		boolean flag = sysEmploymentGradeDao.checkEmpGradeByOrgID(employmentGrade);
		return flag;
	}
	
	/*
	 * 新增职务
	 */
	@Override
	public boolean addEmploymentGrade(SysEmploymentGradeBean employmentGrade) {
		return sysEmploymentGradeDao.addEmploymentGrade(employmentGrade);
	}

	/*
	 * 更新职务
	 */
	@Override
	public boolean updateEmploymentGrade(SysEmploymentGradeBean employmentGrade) {
		return sysEmploymentGradeDao.updateEmploymentGrade(employmentGrade);
	}

	/*
	 * 根据条件查询职务等级
	 */
	@Override
	public List<SysEmploymentGradeBean> getEmpGradeByConditions(
			String paramName, String paramValue) {
		return sysEmploymentGradeDao.getEmpGradeByConditions(paramName, paramValue);
	}

	/*
	 * 删除职务等级
	 */
	@Override
	public boolean deleteEmplomentGrade(int gradeID) {
//		boolean flag1 ;
//		boolean flag2 = false;
//		int count = sysEmploymentGradeDao.getEmploymentByGradeID(gradeID,organizationID);
//		if(count >0 ){
//			flag1 = false;
//		}else{
//			flag1 = true;
//		}
//		if(flag1){
//			flag2 = sysEmploymentGradeDao.delEmpGradeByGradeID(gradeID);
//		}
//		if(flag1 && flag2){
//			return true;
//		}else{
//			return false;
//		}
		sysEmploymentDao.delSysEmpByGradeID(gradeID);
		return sysEmploymentGradeDao.delEmpGradeByGradeID(gradeID);
	}

	/*
	 * 树单位获得职务
	 */
	@Override
	public SysEmploymentGradeBean getEmpploymentByOrgName(
			String organizationName) {
		return sysEmploymentGradeDao.getEmpploymentByOrgName(organizationName);
	}

	/*
	 * 验证职务是否被单位员工使用
	 */
	@Override
	public int getEmploymentByGradeID(int gradeID, int organizationID) {
		return sysEmploymentGradeDao.getEmploymentByGradeID(gradeID, organizationID);
	}

}
