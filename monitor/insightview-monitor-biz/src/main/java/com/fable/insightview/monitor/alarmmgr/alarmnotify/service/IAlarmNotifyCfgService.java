package com.fable.insightview.monitor.alarmmgr.alarmnotify.service;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyToUsersBean;

public interface IAlarmNotifyCfgService {
	/**
	 * 获得所有的通知用户
	 * @param policyID
	 * @return
	 */
	List<AlarmNotifyToUsersBean> getAllNotifyToUsersByID(int policyID);
}
