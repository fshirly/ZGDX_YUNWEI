package com.fable.insightview.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.ISysEmploymentDao;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.service.ISysEmploymentService;

@Service("sysEmploymentService")
public class SysEmploymentServiceImpl implements ISysEmploymentService {
	@Autowired ISysEmploymentDao sysEmploymentDao;
	@Override
	public List<Integer> getUserIdByOrgId(int orgId) {
		return sysEmploymentDao.getUserIdByOrgId(orgId);
	}
	@Override
	public boolean addSysEmp(SysEmploymentBean sysEmploymentBean) {
		return sysEmploymentDao.addSysEmp(sysEmploymentBean);
	}
	@Override
	public List<SysEmploymentBean> getEmploymentByUserId(int userId) {
		return sysEmploymentDao.getEmploymentByUserId(userId);
	}
	@Override
	public boolean updateSysEmp(SysEmploymentBean sysEmploymentBean) {
		return sysEmploymentDao.updateSysEmp(sysEmploymentBean);
	}
	@Override
	public boolean delSysEmpByUserId(int userId) {
		return sysEmploymentDao.delSysEmpByUserId(userId);
	}
	@Override
	public List<Integer> getUserIdByDeptId(String deptId) {
		return sysEmploymentDao.getUserIdByDeptId(deptId);
	}
	
}
