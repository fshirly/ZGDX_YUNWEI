package com.fable.insightview.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.IEmploymentGradeDao;
import com.fable.insightview.platform.entity.EmploymentGradeBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.service.IEmploymentGradeService;

/**
 * 单位组织Service
 * 
 * @author 武林
 * 
 */
@Service("employmentGradeService")
public class EmploymentGradeServiceImpl implements IEmploymentGradeService {

	@Autowired
	protected IEmploymentGradeDao employmentGradeDao;

	public List<EmploymentGradeBean> getEmploymentGradeByConditions(
			EmploymentGradeBean employmentGradeBean){
		return employmentGradeDao.getEmploymentGradeByConditions(
				employmentGradeBean);
	}
	/*
	 * 新增单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addEmploymentGrade(EmploymentGradeBean employmentGradeBean) {
		return employmentGradeDao.addEmploymentGrade(employmentGradeBean);
	}

	/*
	 * 删除单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean delEmploymentGradeById(
			EmploymentGradeBean employmentGradeBean) {
		return employmentGradeDao.delEmploymentGradeById(employmentGradeBean);
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<EmploymentGradeBean> getEmploymentGradeByConditions(
			EmploymentGradeBean employmentGradeBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		return employmentGradeDao.getEmploymentGradeByConditions(
				employmentGradeBean, flexiGridPageInfo);
	}

	@Override
	public List<EmploymentGradeBean> getDepartmentGradeTreeVal(int deptId) {
		return employmentGradeDao.getDepartmentGradeTreeVal(deptId);
	}

	@Override
	public List<EmploymentGradeBean> getGradeName(int gradeId) {
		return employmentGradeDao.getGradeName(gradeId);
	}
	@Override
	public List<EmploymentGradeBean> getGradeInfo(int organizationID) {
		return employmentGradeDao.getGradeInfo(organizationID);
	}

}
