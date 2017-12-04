package com.fable.insightview.platform.notices.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.notices.entity.NotificationsBean;
import com.fable.insightview.platform.page.Page;

public interface NotificationsMapper {

	/**
	 * 新增消息提醒信息
	 * 
	 * @param notifications
	 */
	void insert(NotificationsBean notifications);

	/**
	 * 修改消息提醒信息
	 * 
	 * @param notifications
	 */
	void update(NotificationsBean notifications);

	/**
	 * 删除消息提醒信息
	 * 
	 * @param noticeId
	 */
	void delete(@Param("noticeId") int noticeId);

	/**
	 * 删除多个消息提醒信息
	 * 
	 * @param noticeIds
	 */
	void deleteMulti(@Param("noticeIds") String noticeIds);

	/**
	 * 查询消息提醒列表信息
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<NotificationsBean> list(Page page);

	/**
	 * 查询信息提醒信息
	 * 
	 * @param noticeId
	 * @return
	 */
	Map<String, String> querySingle(@Param("noticeId") int noticeId);

	/**
	 * 查询同事
	 * 
	 * @param userId
	 * @return
	 */
	List<Map<String, String>> queryColleagues(Map<String, String> params);

	/**
	 * 当前用户所处单位ID
	 * 
	 * @param userId
	 * @return
	 */
	int getOrgId(@Param("userId") int userId);

	/**
	 * 查询用户移动手机信息
	 * 
	 * @param userIds
	 * @return
	 */
	List<Map<String, String>> queryUserPhone(@Param("userIds") String userIds);
}
