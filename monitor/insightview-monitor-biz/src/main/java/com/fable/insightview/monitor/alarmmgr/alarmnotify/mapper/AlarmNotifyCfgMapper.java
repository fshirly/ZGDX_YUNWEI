package com.fable.insightview.monitor.alarmmgr.alarmnotify.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyCfgBean;
import com.fable.insightview.platform.page.Page;

public interface AlarmNotifyCfgMapper {
	/* 查询所有 */
	List<AlarmNotifyCfgBean> selectAlarmNotifyCfg(Page<AlarmNotifyCfgBean> page);

	/* 根据ID查询 */
	AlarmNotifyCfgBean getAlarmNotifyCfgByID(int policyID);

	/* 删除 */
	boolean delAlarmNotifyCfg(List<Integer> policyIDs);

	int insertAlarmNotifyCfg(AlarmNotifyCfgBean alarmNotifyCfgBean);

	int getAlarmNotifyCfgByName(AlarmNotifyCfgBean alarmNotifyCfgBean);

	int getByName(AlarmNotifyCfgBean alarmNotifyCfgBean);

	//	
	// int getDefineByNameAndID(AlarmEventDefineBean bean);

	int updateAlarmNotifyCfg(AlarmNotifyCfgBean alarmNotifyCfgBean);

	List<AlarmNotifyCfgBean> getNotifyByName();

	List<AlarmNotifyCfgBean> getAllAlarmNotifyCfg();
}
