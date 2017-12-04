package com.fable.insightview.platform.notices.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.notices.entity.NoticePersonsBean;
import com.fable.insightview.platform.page.Page;

public interface INoticePersonsService {

	/**
	 * 新增消息接收人
	 * 
	 * @param noticePersons
	 */
	void insert(NoticePersonsBean noticePersons);

	/**
	 * 查询消息接收人接收的接收消息列表
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<NoticePersonsBean> list(Page page);

	/**
	 * 删除消息提醒的接收用户信息
	 * 
	 * @param noticeId
	 * @param userIds
	 */
	void deleteMulti(int noticeId, String userIds);

	/**
	 * 修改人员提醒信息
	 * 
	 * @param noticePersons
	 */
	void update(NoticePersonsBean noticePersons);

	/**
	 * 查询通知人员列表信息
	 * 
	 * @param noticeId
	 * @return
	 */
	List<NoticePersonsBean> queryMulti(String noticeId);

	/**
	 * 查询通知人员列表信息
	 * 
	 * @param noticeId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<NoticePersonsBean> queryMultis(Page page);

	/**
	 * 查询消息提醒信息
	 * 
	 * @param id
	 * @return
	 */
	Map<String, String> querySingle(int id);
}
