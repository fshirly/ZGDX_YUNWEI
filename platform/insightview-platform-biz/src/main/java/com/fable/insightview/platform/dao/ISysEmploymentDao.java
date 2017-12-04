/**
 * 
 */
package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface ISysEmploymentDao extends GenericDao<SysEmploymentBean> {
	public List<Integer>  getUserIdByOrgId(int orgId);
	public List<Integer>  getUserIdByDeptId(String deptId);
	boolean addSysEmp(SysEmploymentBean sysEmploymentBean);
	List<SysEmploymentBean> getEmploymentByUserId(int userId);
	boolean updateSysEmp(SysEmploymentBean sysEmploymentBean);
	boolean updateSysEmpSyn(SysEmploymentBean sysEmploymentBean);
	boolean delSysEmpByUserId(int userId);
	public boolean delSysEmpByGradeID(int gradeID); 
}
