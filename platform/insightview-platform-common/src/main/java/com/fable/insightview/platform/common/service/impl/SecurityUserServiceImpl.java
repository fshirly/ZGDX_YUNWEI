package com.fable.insightview.platform.common.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.dao.ISecurityUserDao;
import com.fable.insightview.platform.common.entity.ResourcesVO;
import com.fable.insightview.platform.common.entity.SecurityRoleBean;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.service.ISecurityUserService;
import com.fable.insightview.platform.common.util.SysStaticValue;

@Service("securityUserService")
public class SecurityUserServiceImpl implements ISecurityUserService {

	@Autowired
	ISecurityUserDao securityUserDao;
	
	@Override
	public List<SecurityUserInfoBean> chkUserInfo(
			SecurityUserInfoBean sysUserBean) {
		return this.securityUserDao.chkUserInfo(sysUserBean);
	}
	

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return this.securityUserDao.getUserInfoByUsername(username);
	}
	
	@Override
	public void updateResourceMap(){
//		if (SysStaticValue.getResourceMap() == null) {
			Map<String, Collection<ConfigAttribute>> resMap = null;
			List<ResourcesVO> voList = this.securityUserDao.queryAllURLRoles();
			if(voList != null && !voList.isEmpty()){
				resMap = new HashMap<String, Collection<ConfigAttribute>>();
				Collection<ConfigAttribute> collection = null;
				for(ResourcesVO vo : voList){
					if(vo.getLinkURL() != null && !vo.getLinkURL().equals("")){
						String url = null;
						//数据库中的url目前格式固定为3种：1、javascript:dorefreshMenu(304,'/eventManage/toBlockEventQueryList');
						//2、javascript:dorefreshMenu(305);
						//3、/eventManage/eventManageView
						String[] urls = vo.getLinkURL().split("'");
						//第一种url
						if(urls.length>1){
							url = urls[1];
						}else if(vo.getLinkURL().indexOf(";")>0){
							//第二种url
							url = "/commonLogin/refreshMenu";
						}else{
							//第三种url
							url = vo.getLinkURL();
						}
						if(!resMap.containsKey(url)){
							collection = new HashSet<ConfigAttribute>();
							collection.add(new SecurityConfig(String.valueOf(vo.getRoleID())));
							resMap.put(url, collection);
						}else{
							resMap.get(url).add(new SecurityConfig(String.valueOf(vo.getRoleID())));
						}
					}
				}
			}
			SysStaticValue.setResourceMap(resMap);
//		}
	}
	
	@Override
	public List<SecurityRoleBean> updateRoleInfos(String userAccount){
		List<Object> list = this.securityUserDao.getRolesByUserAccount(userAccount);
		List<SecurityRoleBean> lists = new ArrayList<SecurityRoleBean>();
		if(list != null && !list.isEmpty()){
			for(Object obj : list){
				SecurityRoleBean bean = new SecurityRoleBean();
				bean.setId(Long.parseLong(obj.toString()));
				lists.add(bean);
			}
		}
		return lists;
	}
}
