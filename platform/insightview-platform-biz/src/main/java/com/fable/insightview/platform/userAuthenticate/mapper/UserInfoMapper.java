package com.fable.insightview.platform.userAuthenticate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.userAuthenticate.entity.UserInfoBean;


public interface UserInfoMapper {

	UserInfoBean getUserInfoByAccount(String userAccount);
	
	Map<String, Object> getUserByAccount(@Param(value = "userAccount")String userAccount);
	
	UserInfoBean getUserInfoById(Integer userId);
	
	List<UserInfoBean> indistinctQueryUserName(@Param(value = "userName") String userName);
	
	List<UserInfoBean> indistinctQueryOrgaUserName(@Param(value = "userName") String userName);
	
	int getUserTotalIndistinct(@Param(value = "userName") String userName);
	
	List<UserInfoBean> indistinctProvderUserName(@Param(value = "userName") String userName);
	
	List<UserInfoBean> queryProvderUserName(@Param(value = "userId") Integer userId, @Param(value = "userName") String userName);

	int getUserTotalProvider(@Param(value = "userId") Integer userId, @Param(value = "userName") String userName);
	
	int getUserTotalByOrgaId(@Param(value = "orgaId") Integer orgaId, @Param(value = "userName") String userName);
	
	int getUserTotalByProvId(@Param(value = "provId") Integer provId, @Param(value = "userName") String userName);

	List<UserInfoBean> queryUsersByOrgaId(@Param(value = "orgaId") Integer orgaId, @Param(value = "userName") String userName);

	List<UserInfoBean> queryUsersByProvId(@Param(value = "provId") Integer provId, @Param(value = "userName") String userName);

	boolean updateUserInfo(UserInfoBean userInfoBean);

	List<UserInfoBean> updateUserInfo(int userId);

	List<UserInfoBean> queryUserInfosById(int userId);
	
	List<UserInfoBean> isProviderByUserId(int userId);
	
	/**
	 * 查询相应部门下的用户列表信息
	 * @param deptId
	 * @return
	 */
	List<Map<String, Object>> queryUsers(@Param("deptId")String deptId);
}
