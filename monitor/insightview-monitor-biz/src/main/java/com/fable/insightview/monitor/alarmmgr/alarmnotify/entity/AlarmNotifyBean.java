package com.fable.insightview.monitor.alarmmgr.alarmnotify.entity;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyCfgBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyFilterBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyToUsersBean;

public class AlarmNotifyBean {
	private AlarmNotifyCfgBean notifyCfg;
	private AlarmNotifyFilterBean notifyFilter;
	private AlarmNotifyToUsersBean notifyToUsers;

	public AlarmNotifyCfgBean getNotifyCfg() {
		return notifyCfg;
	}

	public void setNotifyCfg(AlarmNotifyCfgBean notifyCfg) {
		this.notifyCfg = notifyCfg;
	}

	public AlarmNotifyFilterBean getNotifyFilter() {
		return notifyFilter;
	}

	public void setNotifyFilter(AlarmNotifyFilterBean notifyFilter) {
		this.notifyFilter = notifyFilter;
	}

	public AlarmNotifyToUsersBean getNotifyToUsers() {
		return notifyToUsers;
	}

	public void setNotifyToUsers(AlarmNotifyToUsersBean notifyToUsers) {
		this.notifyToUsers = notifyToUsers;
	}

}
