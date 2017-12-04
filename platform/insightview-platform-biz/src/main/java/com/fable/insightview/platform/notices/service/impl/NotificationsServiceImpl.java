package com.fable.insightview.platform.notices.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.message.service.IPfMessageService;
import com.fable.insightview.platform.notices.PfFileNameUtil;
import com.fable.insightview.platform.notices.entity.NoticePersonsBean;
import com.fable.insightview.platform.notices.entity.NotificationsBean;
import com.fable.insightview.platform.notices.mapper.NoticePersonsMapper;
import com.fable.insightview.platform.notices.mapper.NotificationsMapper;
import com.fable.insightview.platform.notices.service.INotificationsService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.sysconf.mapper.SysConfigMapper;

@Service
public class NotificationsServiceImpl implements INotificationsService {

	@Autowired
	private NotificationsMapper notiMapper;

	@Autowired
	private NoticePersonsMapper personMapper;

	@Autowired
	private SysConfigMapper configMapper;

	@Autowired
	private IPfMessageService msgService;

	@Override
	public void insert(NotificationsBean notifications) {
		notiMapper.insert(notifications);
		int noticeId = notifications.getId();
		if (StringUtils.isNotEmpty(notifications.getUserIds())) {
			NoticePersonsBean person = null;
			for (String userId : notifications.getUserIds().split(",")) {/* 添加信息提醒接收人 */
				person = new NoticePersonsBean();
				person.setNoticeId(noticeId);
				person.setUserId(Integer.parseInt(userId));
				personMapper.insert(person);
			}
		}
		if (notifications.getIsNote() == 2) {/* 生成短信信息 */
			this.sendMsg(notifications.getCreator() + "", notifications.getUserIds(), notifications.getTitle(), 1);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<NotificationsBean> list(Page page) {
		List<NotificationsBean> notifications = notiMapper.list(page);
		String noticeIds = null;
		for (NotificationsBean notif : notifications) {
			if (StringUtils.isEmpty(noticeIds)) {
				noticeIds = notif.getId() + "";
			} else {
				noticeIds += "," + notif.getId();
			}
		}
		if (null == noticeIds) {
			return notifications;
		}
		List<NoticePersonsBean> persons = personMapper.queryMulti(noticeIds);
		List<NoticePersonsBean> cons = null;
		for (NotificationsBean notif : notifications) {/* 查询消息接收人信息 */
			for (NoticePersonsBean per : persons) {
				if (notif.getId() == per.getNoticeId()) {
					cons = notif.getPersons();
					if (null == cons) {
						cons = new ArrayList<NoticePersonsBean>();
					}
					cons.add(per);
					notif.setPersons(cons);
				}
			}
		}
		return notiMapper.list(page);
	}

	@Override
	public Map<String, String> querySingle(int noticeId) {
		Map<String, String> notifications = notiMapper.querySingle(noticeId);
		List<NoticePersonsBean> persons = personMapper.queryMulti(noticeId + "");
		int count = 10;
		String userIds = null, userNames = null, userTit = null;
		NoticePersonsBean personCache = null;
		for (int i = 0, size = persons.size(); i < size; i++) {
			personCache = persons.get(i);
			if (StringUtils.isEmpty(userIds)) {
				userIds = personCache.getUserId() + ":" + personCache.getUserName();
			} else {
				userIds += "," + personCache.getUserId() + ":" + personCache.getUserName();
			}
			if (StringUtils.isEmpty(userNames)) {
				userNames = personCache.getUserName();
			} else {
				userNames += "," + personCache.getUserName();
			}
			if (StringUtils.isEmpty(userTit) && i < count && size > count) {/* 10设置人员数量及展示格式 */
				userTit = personCache.getUserName();
			} else if (i < count && size > count) {
				userTit += "," + personCache.getUserName();
			}
		}
		if (notifications.containsKey("File")) {
			notifications.put("FileName", notifications.get("File"));
			notifications.put("filePath", PfFileNameUtil.getRemoteFilePath(notifications.get("File")));
			notifications.put("File", PfFileNameUtil.getFileName(notifications.get("File")));
		}
		notifications.put("UserIds", userIds);
		notifications.put("UserNames", userNames);
		if (StringUtils.isNotEmpty(userTit)) {
			notifications.put("UserTit", userTit + "...");
		}
		return notifications;
	}

	@Override
	public void update(NotificationsBean notifications) {
		notiMapper.update(notifications);
		personMapper.deleteMulti(notifications.getId(), null);/* 删除以前的信息提醒接收人 */
		if (StringUtils.isNotEmpty(notifications.getUserIds())) {
			NoticePersonsBean person = null;
			for (String userId : notifications.getUserIds().split(",")) {/* 添加信息提醒接收人 */
				person = new NoticePersonsBean();
				person.setNoticeId(notifications.getId());
				person.setUserId(Integer.parseInt(userId));
				personMapper.insert(person);
			}
		}
		if (notifications.getIsNote() == 2) {/* 生成短信信息 */
			this.sendMsg(notifications.getCreator() + "", notifications.getUserIds(), notifications.getTitle(), 1);
		}
	}

	@Override
	public void delete(int noticeId) {
		personMapper.deleteMulti(noticeId, null);/* 删除以前的信息提醒接收人 */
		notiMapper.delete(noticeId);
	}

	@Override
	public void deleteMulti(String noticeIds) {
		personMapper.deleteMultiPersons(noticeIds);
		notiMapper.deleteMulti(noticeIds);
	}

	@Override
	public List<Map<String, String>> queryColleagues(Map<String, String> params) {
		return notiMapper.queryColleagues(params);
	}

	@Override
	public int getOrgId(int userId) {
		return notiMapper.getOrgId(userId);
	}

	@Override
	public List<Map<String, String>> queryUserPhone(String userIds) {
		return notiMapper.queryUserPhone(userIds);
	}

	@Override
	public void sendMsg(String from, String tos, String title, int type) {
		String msgInfo = null, mobile = null;
		List<Map<String, String>> fromUser = this.queryUserPhone(from);
		List<Map<String, String>> toUsers = this.queryUserPhone(tos);
		if (fromUser.isEmpty() || toUsers.isEmpty()) {
			return;
		}
		if (1 == type) {/* 创建者发送短信到接收者 */
			msgInfo = configMapper.getConfParamValue(203, "CreatorMsg");
		} else {/* 接收者发送短信到创建者 */
			msgInfo = configMapper.getConfParamValue(203, "ReceiveMsg");
		}
		msgInfo = msgInfo.replace("{2}", "[" + title + "]");
		msgInfo = msgInfo.replace("{1}", fromUser.get(0).get("UserName"));

		/* 新增短信信息 */
		for (Map<String, String> toUser : toUsers) {
			mobile = toUser.get("MobilePhone");
			if (StringUtils.isNotEmpty(mobile)) {
				msgInfo = msgInfo.replace("{0}", toUser.get("UserName"));
				msgService.sendSmg(msgInfo, mobile);
			}
		}
	}

}
