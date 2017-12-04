package com.fable.insightview.platform.notifypolicycfg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.notifypolicycfg.entity.NotifyToUsersBean;
import com.fable.insightview.platform.page.Page;

/**
 * 通知用户
 *
 */
public interface NotifyToUsersMapper {
	/**
	 *  根据策略id查询通知用户
	 */
	List<NotifyToUsersBean> getNotifyToUsersByID(int policyId);

	/**
	 * 分页查询通知用户
	 */
	List<NotifyToUsersBean> selectNotifyToUsers(Page<NotifyToUsersBean> page);

	/**
	 * 新增通知用户
	 */
	int insertNotifyToUsers(NotifyToUsersBean bean);

	/**
	 * 根据策略id删除通知用户
	 */
	boolean delByPolicyId(int policyId);
	
	/**
	 * 根据策略id批量删除通知用户
	 */
	boolean delByPolicyIds(@Param("policyIds")String policyIds);

	/**
	 * 根据策略id获得通知用户
	 */
	List<NotifyToUsersBean> getUsersByPolicyID(int policyId);
}
