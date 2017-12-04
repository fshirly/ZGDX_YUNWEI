package com.fable.insightview.platform.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.ISysMenuModuleDao;
import com.fable.insightview.platform.dao.ISysRoleDao;
import com.fable.insightview.platform.dao.ISysRoleMenuDao;
import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.entity.SysRoleBean;
import com.fable.insightview.platform.entity.SysRoleMenusBean;
import com.fable.insightview.platform.entity.SysUserGroupRolesBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.service.ISysRoleService;

/**
 * 单位组织Service
 * 
 * @author 武林
 * 
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements ISysRoleService {
	private final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);
	@Autowired
	protected ISysRoleDao sysRoleDao;
	@Autowired
	protected ISysRoleMenuDao sysRoleMenuDao;
	@Autowired
	protected ISysMenuModuleDao sysMenuModuleDao;

	@Override
	public String queryMenuIdByRole(SysMenuModuleBean sysMenuModule) {
		String menuIdArr = "";
		List<SysRoleMenusBean> sysRoleMenusBeanLst = sysRoleMenuDao
				.getSysMenuByRole(sysMenuModule);
		for (int i = 0; i < sysRoleMenusBeanLst.size(); i++) {
			
			int menuId = sysRoleMenusBeanLst.get(i).getMenuID();
			boolean flag = isLeaf(menuId);
			logger.info(menuId+"========="+flag);
			if(flag == true){
				menuIdArr += menuId+",";
			}
		}
		logger.info("默认被选中的ID："+menuIdArr);
		return menuIdArr;
	}

	@Override
	public int getTotalCount(SysRoleBean sysRoleBean) {
		return sysRoleDao.getTotalCount(sysRoleBean);
	}

	@Override
	public boolean addSysRoleMenu(SysRoleMenusBean sysRoleMenusBean) {
		logger.info("menuIdArr====="+sysRoleMenusBean.getMenuIdAttr());
		SysMenuModuleBean sysMenuModule = new SysMenuModuleBean();
		sysMenuModule.setRoleId(sysRoleMenusBean.getRoleID()+"");
		int count = sysRoleMenuDao.getSysMenuByRole(sysMenuModule).size();
		int delFlag = sysRoleMenuDao.delRoleMenuByCond(sysRoleMenusBean);
		String[] menuIdArr = sysRoleMenusBean.getMenuIdAttr().split(",");
		if((menuIdArr.length >0 && count >0 &&delFlag > 0) || count==0){
			for (String menuId : menuIdArr) {
				if (menuId.trim().length() > 0) {
					logger.info("menuId======="+menuId);
					if(Integer.parseInt(menuId)!=0){
						SysRoleMenusBean sysRoleMenusBeanTemp = new SysRoleMenusBean(
								sysRoleMenusBean.getRoleID(), Integer.parseInt(menuId));
						sysRoleMenuDao.addSysRoleMenu(sysRoleMenusBeanTemp);
					}
				}
			}
			delFlag =0;
		}
		return true;
	}

	@Override
	public List<SysRoleBean> getSysRoleBeanByConditions(String paramName,
			String paramValue, List<SysUserGroupRolesBean> groupRoleLst) {
		String roleIdArr = "";
		for (int i = 0; i < groupRoleLst.size(); i++) {
			SysUserGroupRolesBean sysUserGroupRolesBeanTemp = groupRoleLst
					.get(i);
			if (i != 0) {
				roleIdArr += "," + sysUserGroupRolesBeanTemp.getRoleID();
			} else {
				roleIdArr += sysUserGroupRolesBeanTemp.getRoleID();
			}
		}
		logger.info("roleIdArr====="+roleIdArr);
		List<SysRoleBean> sysRoleLst = sysRoleDao.getSysRoleBeanByConditions(
				paramName, paramValue, "roleID", roleIdArr);
		return sysRoleLst;
	}

	@Override
	public List<SysRoleBean> getSysRoleBeanByConditions(String paramName,
			String paramValue) {
		List<SysRoleBean> sysRoleLst = sysRoleDao.getSysRoleBeanByConditions(
				paramName, paramValue);
		return sysRoleLst;
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSysRole(SysRoleBean sysRoleBean) {
		return sysRoleDao.updateSysRoleBean(sysRoleBean);
	}

	/*
	 * 新增单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addSysRole(SysRoleBean sysRoleBean) {
		return sysRoleDao.addSysRole(sysRoleBean);
	}

	/*
	 * 删除单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean delSysRoleById(SysRoleBean sysRoleBean) {
		SysRoleMenusBean roleMenuBean = new SysRoleMenusBean();
		roleMenuBean.setRoleID(sysRoleBean.getRoleId());
		sysRoleMenuDao.delRoleMenuByCond(roleMenuBean);
		return sysRoleDao.delSysRoleById(sysRoleBean);
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<SysRoleBean> getSysRoleByConditions(SysRoleBean sysRoleBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		return sysRoleDao
				.getSysRoleByConditions(sysRoleBean, flexiGridPageInfo);
	}

	@Override
	public List<SysRoleBean> getSysRoleByconditions(String paramName,
			String paramValue) {
		return sysRoleDao.getSysRoleBeanByConditions(paramName, paramValue);
	}

	/*
	 * 验证角色名称唯一性
	 */
	@Override
	public boolean checkRoleName(SysRoleBean sysRoleBean) {
		return sysRoleDao.checkRoleName(sysRoleBean);
	}

	@Override
	public boolean checkBeforeDel(SysRoleBean sysRoleBean) {
		// TODO Auto-generated method stub
		return sysRoleDao.checkBeforeDel(sysRoleBean);
	}

	@Override
	public boolean isLeaf(int menuID) {
		 List<SysMenuModuleBean> menuModuleList = sysMenuModuleDao.getMenuModuleByParentMenuID(menuID);
		 if(menuModuleList.size() > 0){
			 return false;
		 }else{
			 return true;
		 }
	}

}
