package com.fable.insightview.platform.common.dao;

import java.util.List;

import com.fable.insightview.platform.common.entity.ResourcesVO;
import com.fable.insightview.platform.common.entity.SecurityRoleBean;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface ISecurityUserDao extends GenericDao<SecurityUserInfoBean> {

	List<SecurityUserInfoBean> chkUserInfo(SecurityUserInfoBean sysUserBean);

	public SecurityUserInfoBean getUserInfoByUsername(String username);
	
	public SecurityUserInfoBean getBaseInfoByUserId(Integer id);
	
	public List<ResourcesVO> queryAllURLRoles();
	
	public List<Object> getRolesByUserAccount(String userAccount);
}
