package com.fable.insightview.platform.common.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.fable.insightview.platform.common.entity.SecurityRoleBean;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;

public interface ISecurityUserService extends UserDetailsService{
	
	public List<SecurityUserInfoBean> chkUserInfo(SecurityUserInfoBean sysUserBean);
	
	public void updateResourceMap();
	
	public List<SecurityRoleBean>  updateRoleInfos(String userAccount);
	
}
