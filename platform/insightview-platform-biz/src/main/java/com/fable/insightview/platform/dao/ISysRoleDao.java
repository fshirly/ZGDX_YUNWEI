package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.SysRoleBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysRoleDao extends GenericDao<SysRoleBean> {

	List<SysRoleBean> getSysRoleBeanByConditions(String paramName,
			String paramValue, String paramNameT, String paramValueT);

	List<SysRoleBean> getSysRoleBeanByConditions(String paramName,
			String paramValue);

	boolean updateSysRoleBean(SysRoleBean sysRoleBean);

	int getTotalCount(SysRoleBean sysRoleBean);

	/* 新增单位组织 */
	boolean addSysRole(SysRoleBean sysRoleBean);

	/* 删除单位组织 */
	boolean delSysRoleById(SysRoleBean sysRoleBean);

	/* 查询单位组织 */
	List<SysRoleBean> getSysRoleByConditions(SysRoleBean sysRoleBean,
			FlexiGridPageInfo flexiGridPageInfo);
	
	List<SysRoleBean> getSysRoleByConditions(String paramName,String paramValue);
	
	/* 验证角色名称唯一性 */
	boolean checkRoleName(SysRoleBean sysRoleBean);
	
	/* 删除前的验证 */
	public boolean checkBeforeDel(SysRoleBean sysRoleBean);
}
