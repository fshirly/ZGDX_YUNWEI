package com.fable.insightview.monitor.alarmmgr.alarmnotifytousers.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyToUsersBean;
import com.fable.insightview.platform.page.Page;

public interface AlarmNotifyToUsersMapper {
	/* 根据ID查询 */
	List<AlarmNotifyToUsersBean> getNotifyToUsersByID(int policyID);

	List<AlarmNotifyToUsersBean> selectAlarmNotifyToUsers(
			Page<AlarmNotifyToUsersBean> page);

	int insertAlarmNotifyToUsers(AlarmNotifyToUsersBean alarmNotifyToUsersBean);

	boolean delAlarmNotifyToUsers(int filterID);

	List<Integer> getUserIdsByPolicyID(int PolicyID);

	boolean delToUsersByPolicyID(List<Integer> policyIDs);

	int selectByConditions(AlarmNotifyToUsersBean alarmNotifyToUsersBean);
	
	int selectByIdAndUserID(AlarmNotifyToUsersBean alarmNotifyToUsersBean);
}
