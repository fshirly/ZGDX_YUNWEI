package com.fable.insightview.platform.userAuthenticate.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.userAuthenticate.entity.UserInfoBean;

public interface IUserInfoService {

	UserInfoBean getUserInfoByAccount(String userAccount);
	
	Map<String, Object> getUserByAccount(String userAccount);
	
	UserInfoBean getUserInfoById(int userId);
	
	List<UserInfoBean> indistinctQueryUserName(String userName);
	
	List<UserInfoBean> indistinctQueryOrgaUserName(String userName);
	
	int getUserTotalIndistinct(String userName);
	
	List<UserInfoBean> indistinctProvderUserName(String userName);

	List<UserInfoBean> queryProvderUserName(Integer userId, String userName);

	int getUserTotalProvider(Integer userId, String userName);
	
	int getUserTotalByOrgaId(@Param(value = "orgaId") Integer orgaId, @Param(value = "userName") String userName);
	
	int getUserTotalByProvId(@Param(value = "provId") Integer provId, @Param(value = "userName") String userName);

	List<UserInfoBean> queryUsersByOrgaId(int organizationId, String userName);

	List<UserInfoBean> queryUsersByProvId(int provId, String userName);

	boolean updateUserInfo(UserInfoBean userInfoBean);

	List<UserInfoBean> queryUserInfosById(int userId);

	List<UserInfoBean> isProviderByUserId(int userId);
	
	/**
	 * 查询相应部门下的用户列表信息
	 * @param deptId
	 * @return
	 */
	List<Map<String, Object>> queryUsers(String deptId);
}
