package com.fable.insightview.platform.message.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.message.entity.PfEmailNotificationTaskBean;
import com.fable.insightview.platform.message.entity.PfMessage;

public interface IPfMessageService {

	/**
	 * 生成通知短信
	 * 
	 * @param msg
	 */
	void insert(PfMessage msg);

	/**
	 * 向指定人发送短信信息
	 * 
	 */
	void sendSmg(String msg, String mobile);
	
	/**
	 * 批量新增短信信息
	 * @param msgs 
	 * MAP key:mobile-电话；key:msg-提示信息
	 */
	void batchSends(List<Map<String, String>> msgs);
	
	/**
	 * 根据用户Id发送短信信息
	 * @param userId 用户Id
	 * @param msg 短信信息
	 * @param expertTime 期望发送时间 如果为null, 则为系统当前时间
	 * @param priority 优先级 值越大优先级越高
	 * @param project 短信隶属工程 1: itsm 2: cmdb 3:monitor 4:platform
	 */
	void sendByUserId (int userId, String msg, Date expertTime, int priority, int project);
	
	/**
	 * 根据用户Id发送短信信息
	 * @param userId 用户Id
	 * @param msg 短信信息
	 */
	void sendByUserId (int userId, String msg);
	
	/**
	 * 根据用户IDs,批量发送短信息
	 * @param userIds 所有用户Ids
	 * @param msg 短信信息
	 * @param expertTime 期望发送时间 如果为null, 则为系统当前时间
	 * @param priority 优先级 值越大优先级越高 
	 * @param project 短信隶属工程 1: itsm 2: cmdb 3:monitor 4:platform
	 */
	void sengByUserIds (List<Integer> userIds, String msg, Date expertTime, int priority, int project);
	
	/**
	 * 根据用户IDs,批量发送短信息
	 * @param userIds 所有用户Ids
	 * @param msg 短信信息
	 */
	void sengByUserIds (List<Integer> userIds, String msg);
	
	/**
	 * 获得所有需要发送的邮件通知任务
	 * @return
	 */
	List<PfEmailNotificationTaskBean> getNotificationTask();

	/**
	 * 新增邮件通知任务
	 */
	int insertNotificationTask(PfEmailNotificationTaskBean bean);

	/**
	 * 更新邮件通知任务
	 */
	int updateNotificationTask(PfEmailNotificationTaskBean bean);
	
}
