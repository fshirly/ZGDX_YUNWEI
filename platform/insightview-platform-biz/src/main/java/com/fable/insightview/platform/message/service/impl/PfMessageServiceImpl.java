package com.fable.insightview.platform.message.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.message.entity.PfEmailNotificationTaskBean;
import com.fable.insightview.platform.message.entity.PfMessage;
import com.fable.insightview.platform.message.mapper.PfEmailNotificationTaskMapper;
import com.fable.insightview.platform.message.mapper.PfMessageMapper;
import com.fable.insightview.platform.message.service.IPfMessageService;

@Service
public class PfMessageServiceImpl implements IPfMessageService {

	@Autowired
	private PfMessageMapper msgMapper;

	@Autowired
	private ISysUserDao userDao;
	
	@Autowired
	PfEmailNotificationTaskMapper emailNotificationTaskMapper;

	@Override
	public void insert(PfMessage msg) {
		msgMapper.insert(msg);
	}

	@Override
	public void sendSmg(String msg, String mobile) {
		msgMapper.insert(new PfMessage(mobile, msg));
	}

	@Override
	public void batchSends(List<Map<String, String>> msgs) {
		if (null == msgs || msgs.isEmpty()) {
			return;
		}
		List<PfMessage> pfMess = new ArrayList<PfMessage>();
		for (Map<String, String> msg : msgs) {
			if (StringUtils.isNotEmpty(msg.get("mobile")) && StringUtils.isNotEmpty(msg.get("msg"))) {
				pfMess.add(new PfMessage(msg.get("mobile"), msg.get("msg")));
			}
		}
		if (!pfMess.isEmpty()) {
			msgMapper.batchInsert(pfMess);
		}
	}

	@Override
	public void sendByUserId(int userId, String msg, Date expertTime, int priority, int project) {
		SysUserInfoBean userInfo = userDao.getById(userId);
		if (null == userInfo || StringUtils.isEmpty(userInfo.getMobilePhone())) {
			return;
		}
		PfMessage pfMes = packageInfo(userInfo.getMobilePhone(), msg, expertTime, priority, project);
		this.insert(pfMes);
	}

	@Override
	public void sengByUserIds(List<Integer> userIds, String msg, Date expertTime, int priority, int project) {
		List<SysUserInfoBean> userInfos = userDao.getUserNameByUserIds(userIds);
		if (null == userInfos || userInfos.isEmpty()) {
			return;
		}
		List<PfMessage> pfMess = new ArrayList<PfMessage>();
		for (SysUserInfoBean userInfo : userInfos) {
			if (StringUtils.isNotEmpty(userInfo.getMobilePhone())) {
				pfMess.add(packageInfo(userInfo.getMobilePhone(), msg, expertTime, priority, project));
			}
		}
		if (!pfMess.isEmpty()) {
			msgMapper.batchInsert(pfMess);
		}
	}

	@Override
	public void sendByUserId(int userId, String msg) {
		SysUserInfoBean userInfo = userDao.getById(userId);
		if (null == userInfo || StringUtils.isEmpty(userInfo.getMobilePhone())) {
			return;
		}
		this.insert(new PfMessage(userInfo.getMobilePhone(), msg));
	}

	@Override
	public void sengByUserIds(List<Integer> userIds, String msg) {
		List<SysUserInfoBean> userInfos = userDao.getUserNameByUserIds(userIds);
		if (null == userInfos || userInfos.isEmpty()) {
			return;
		}
		List<PfMessage> pfMess = new ArrayList<PfMessage>();
		for (SysUserInfoBean userInfo : userInfos) {
			if (StringUtils.isNotEmpty(userInfo.getMobilePhone())) {
				pfMess.add(new PfMessage(userInfo.getMobilePhone(), msg));
			}
		}
		if (!pfMess.isEmpty()) {
			msgMapper.batchInsert(pfMess);
		}
	}

	private PfMessage packageInfo(String mobile, String msg, Date expertTime, int priority, int project) {
		PfMessage pfMes = new PfMessage(mobile, msg);
		if (null != expertTime) {
			pfMes.setExpectSendTime(expertTime);
		}
		pfMes.setSource(project);
		pfMes.setNotifyType(priority);
		return pfMes;
	}

	@Override
	public List<PfEmailNotificationTaskBean> getNotificationTask() {
		return emailNotificationTaskMapper.getNotificationTask();
	}

	@Override
	public int insertNotificationTask(PfEmailNotificationTaskBean bean) {
		return emailNotificationTaskMapper.insertNotificationTask(bean);
	}

	@Override
	public int updateNotificationTask(PfEmailNotificationTaskBean bean) {
		return emailNotificationTaskMapper.updateNotificationTask(bean);
	}
}
