package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.SysEmploymentBean;


public interface ISysEmploymentService {
	public List<Integer> getUserIdByOrgId(int orgId);
	boolean addSysEmp(SysEmploymentBean sysEmploymentBean);
	List<SysEmploymentBean> getEmploymentByUserId(int userId);
	boolean updateSysEmp(SysEmploymentBean sysEmploymentBean);
	boolean delSysEmpByUserId(int userId);
	public List<Integer>  getUserIdByDeptId(String deptId);
}
