package com.fable.insightview.platform.notices.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.notices.PfFileNameUtil;
import com.fable.insightview.platform.notices.entity.NoticePersonsBean;
import com.fable.insightview.platform.notices.mapper.NoticePersonsMapper;
import com.fable.insightview.platform.notices.service.INoticePersonsService;
import com.fable.insightview.platform.notices.service.INotificationsService;
import com.fable.insightview.platform.page.Page;

@Service
public class NoticePersonsServiceImpl implements INoticePersonsService {

	@Autowired
	private NoticePersonsMapper personMapper;
	
	@Autowired
	private INotificationsService notifService;

	@Override
	public void insert(NoticePersonsBean noticePersons) {
		personMapper.insert(noticePersons);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<NoticePersonsBean> list(Page page) {
		return personMapper.list(page);
	}

	@Override
	public void deleteMulti(int noticeId, String userIds) {
		personMapper.deleteMulti(noticeId, userIds);
	}

	@Override
	public void update(NoticePersonsBean noticePersons) {
		personMapper.update(noticePersons);
		if (noticePersons.getIsNote() == 2) {/* 生成短信信息 */
			notifService.sendMsg(noticePersons.getUserId() + "", noticePersons.getNotifications().getCreator() + "", noticePersons.getNotifications().getTitle(), 2);
		}
	}

	@Override
	public List<NoticePersonsBean> queryMulti(String noticeId) {
		return personMapper.queryMulti(noticeId);
	}

	@Override
	public Map<String, String> querySingle(int id) {
		Map<String, String> info = personMapper.querySingle(id);
		if (null != info) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (!info.containsKey("ConfirmTime")) {
				info.put("ConfirmTime", df.format(new Date()));
			} else {
				info.put("ConfirmTime", df.format(info.get("ConfirmTime")));
			}
			if (info.containsKey("CreateTime")) {
				info.put("CreateTime", df.format(info.get("CreateTime")));
			}
			if (info.containsKey("File")) {
				info.put("filePath", PfFileNameUtil.getRemoteFilePath(info.get("File")));
				info.put("File", PfFileNameUtil.getFileName(info.get("File")));
			}
			if (info.containsKey("personFile")) {
				info.put("personFilePath", PfFileNameUtil.getRemoteFilePath(info.get("personFile")));
				info.put("personFileName", PfFileNameUtil.getFileName(info.get("personFile")));
			}
		}
		return info;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<NoticePersonsBean> queryMultis(Page page) {
		List<NoticePersonsBean> persons = personMapper.queryMultis(page);
		for (NoticePersonsBean person : persons) {
			if (StringUtils.isNotEmpty(person.getFile())) {
				person.setFilePath(PfFileNameUtil.getRemoteFilePath(person.getFile()));
				person.setFile(PfFileNameUtil.getFileName(person.getFile()));
			}
		}
		return persons;
	}
}
