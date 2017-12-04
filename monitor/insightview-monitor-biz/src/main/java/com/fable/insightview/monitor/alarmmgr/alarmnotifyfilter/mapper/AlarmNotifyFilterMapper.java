package com.fable.insightview.monitor.alarmmgr.alarmnotifyfilter.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyFilterBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.monitor.alarmmgr.entity.MOSourceBean;
import com.fable.insightview.platform.page.Page;

public interface AlarmNotifyFilterMapper {
	/* 根据ID查询 */
	List<AlarmNotifyFilterBean> getAlarmNotifyFilterByID(int PolicyID);

	List<AlarmNotifyFilterBean> selectAlarmNotifyFilter(
			Page<AlarmNotifyFilterBean> page);

	int insertAlarmNotifyFilter(AlarmNotifyFilterBean alarmNotifyFilterBean);

	boolean delAlarmNotifyFilter(int filterID);

	boolean delFilterByPolicyID(List<Integer> policyIDs);

	int selectByConditions(AlarmNotifyFilterBean alarmNotifyFilterBean);

	/**
	 * 查询告警级别
	 * 
	 * @return
	 */
	List<AlarmLevelInfo> queryAlarmLevelList(Page<AlarmLevelInfo> page);

	/**
	 * 过滤条件：查询告警类型
	 * 
	 * @return
	 */
	List<AlarmTypeInfo> queryAlarmTypeList(Page<AlarmTypeInfo> page);

	/**
	 * 查询所有告警源对象
	 * 
	 * @return
	 */
	List<MOSourceBean> queryMOSourceList(Page<MOSourceBean> page);

	/**
	 * 过滤查询告警事件
	 * 
	 * @return
	 */
	List<AlarmEventDefineBean> queryEventList(Page<AlarmEventDefineBean> page);
}
