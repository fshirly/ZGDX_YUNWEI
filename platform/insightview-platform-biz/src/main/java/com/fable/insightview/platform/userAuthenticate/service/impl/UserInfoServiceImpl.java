package com.fable.insightview.platform.userAuthenticate.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.userAuthenticate.entity.UserInfoBean;
import com.fable.insightview.platform.userAuthenticate.mapper.UserInfoMapper;
import com.fable.insightview.platform.userAuthenticate.service.IUserInfoService;


@Service
public class UserInfoServiceImpl implements IUserInfoService{

	@Autowired
	UserInfoMapper userInfoMapper;
	
	public UserInfoBean getUserInfoByAccount(String userAccount) {
		// TODO Auto-generated method stub
		return userInfoMapper.getUserInfoByAccount(userAccount);
	}
	
	public List<UserInfoBean> indistinctQueryUserName(String userName) {
		// TODO Auto-generated method stub
		return userInfoMapper.indistinctQueryUserName(userName);
	}

	public List<UserInfoBean> indistinctQueryOrgaUserName(String userName) {
		// TODO Auto-generated method stub
		return userInfoMapper.indistinctQueryOrgaUserName(userName);
	}

	public int getUserTotalIndistinct(String userName) {
		// TODO Auto-generated method stub
		return userInfoMapper.getUserTotalIndistinct(userName);
	}

	public List<UserInfoBean> indistinctProvderUserName(String userName) {
		// TODO Auto-generated method stub
		return userInfoMapper.indistinctProvderUserName(userName);
	}

	public List<UserInfoBean> queryProvderUserName(Integer userId, String userName) {
		// TODO Auto-generated method stub
		return userInfoMapper.queryProvderUserName(userId, userName);
	}
	
	public int getUserTotalProvider(Integer userId, String userName) {
		// TODO Auto-generated method stub
		return userInfoMapper.getUserTotalProvider(userId, userName);
	}

	@Override
	public int getUserTotalByOrgaId(Integer orgaId, String userName) {
		// TODO Auto-generated method stub
		return userInfoMapper.getUserTotalByOrgaId(orgaId, userName);
	}

	@Override
	public int getUserTotalByProvId(Integer provId, String userName) {
		// TODO Auto-generated method stub
		return userInfoMapper.getUserTotalByProvId(provId, userName);
	}

	@Override
	public List<UserInfoBean> queryUsersByOrgaId(int organizationId,
			String userName) {
		// TODO Auto-generated method stub
		return userInfoMapper.queryUsersByOrgaId(organizationId, userName);
	}

	@Override
	public List<UserInfoBean> queryUsersByProvId(int provId, String userName) {
		// TODO Auto-generated method stub
		return userInfoMapper.queryUsersByProvId(provId, userName);
	}

	@Override
	public UserInfoBean getUserInfoById(int userId) {
		// TODO Auto-generated method stub
		return userInfoMapper.getUserInfoById(userId);
	}

	@Override
	public boolean updateUserInfo(UserInfoBean userInfoBean) {
		// TODO Auto-generated method stub
		return userInfoMapper.updateUserInfo(userInfoBean);
	}

	@Override
	public List<UserInfoBean> queryUserInfosById(int userId) {
		// TODO Auto-generated method stub
		return userInfoMapper.queryUserInfosById(userId);
	}

	@Override
	public List<UserInfoBean> isProviderByUserId(int userId) {
		// TODO Auto-generated method stub
		return userInfoMapper.isProviderByUserId(userId);
	}

	@Override
	public Map<String, Object> getUserByAccount(String userAccount) {
		Map<String, Object> user = userInfoMapper.getUserByAccount(userAccount);
		if (user == null || user.isEmpty()) {
			user = new HashMap<String, Object>();
			UserInfoBean userInfo = this.getUserInfoByAccount(userAccount);
			user.put("UserID", userInfo.getUserId());
			user.put("UserAccount", userInfo.getUserAccount());
			user.put("UserName", userInfo.getUserName());
			user.put("MobilePhone", userInfo.getMobile());
		} 
		return user;
	}

	@Override
	public List<Map<String, Object>> queryUsers(String deptId) {
		return userInfoMapper.queryUsers(deptId);
	}
}
