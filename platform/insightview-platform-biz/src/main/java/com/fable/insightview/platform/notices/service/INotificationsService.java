package com.fable.insightview.platform.notices.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.notices.entity.NotificationsBean;
import com.fable.insightview.platform.page.Page;

public interface INotificationsService {

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
	void delete(int noticeId);

	/**
	 * 删除多个消息提醒信息
	 * 
	 * @param noticeIds
	 */
	void deleteMulti(String noticeIds);

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
	Map<String, String> querySingle(int noticeId);

	/**
	 * 查询同事
	 * 
	 * @param userId
	 * @return
	 */
	List<Map<String, String>> queryColleagues(Map<String, String> params);

	/**
	 * 获取单位Id
	 * 
	 * @param userId
	 * @return
	 */
	int getOrgId(int userId);

	/**
	 * 查询用户移动手机信息
	 * 
	 * @param userIds
	 * @return
	 */
	List<Map<String, String>> queryUserPhone(String userIds);

	/**
	 * 新增短信提醒信息
	 * 
	 * @param from
	 * @param tos
	 * @param type 1-代表创建者发送到接收者、2-代表接收者发送创建者
	 */
	void sendMsg(String from, String tos, String title, int type);
}
