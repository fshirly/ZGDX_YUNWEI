package com.fable.insightview.monitor.alarmmgr.alarmsendorderunion.service;

import java.util.ArrayList;
import java.util.List;

import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.platform.page.Page;

/**
 * 统一告警派单service
 * @author Administrator
 *
 */
public interface AlarmSendOrderUnionService {

	//根据告警ID查询告警列表信息
	List<AlarmNode> getAlarmListByAlarmId(Page<AlarmNode> page);
	
	//根据告警ID查详情
	 AlarmNode getInfoById(int id);
	
	 List<Integer> getAlarmStatusById(ArrayList<String> alarmIds);
}
