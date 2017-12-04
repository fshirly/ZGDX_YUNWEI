package com.fable.insightview.monitor.alarmmgr.devicewblistsetting.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackPortListBean;

public interface AlarmBlackPortListMapper {
	int insertIntoAlarmBlackPortList(AlarmBlackPortListBean alarmBlackPortListBean);

	/**
	 * 设备黑白名单接口/端口列表中删除行
	 * @param row
	 */
	int deleteAlarmBlackPortListRow(AlarmBlackPortListBean row);

	int updateIntoAlarmBlackPortList(AlarmBlackPortListBean row);
	
	/**
	 * check重复的BlackID和与其对应的port
	 * @param row
	 * @return
	 */
	List<AlarmBlackPortListBean> checkBlackIDAndPort(AlarmBlackPortListBean row);
}
