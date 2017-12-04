package com.fable.insightview.monitor.alarmmgr.alarmsendorderunion.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmsendorderunion.service.AlarmSendOrderUnionService;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.platform.page.Page;

@Service
public class AlarmSendOrderUnionServiceImpl implements AlarmSendOrderUnionService {

	@Autowired
	public AlarmActiveMapper alarmActiveMapper;
	
	public List<AlarmNode> getAlarmListByAlarmId(Page<AlarmNode> page) {
		List<String> alarmIds = new ArrayList<String>();
		if(null != page.getParams().get("alarmIds") && "" != page.getParams().get("alarmIds")){
			for(String str : page.getParams().get("alarmIds").toString().split(","))
			{
				alarmIds.add(str);
			}
		}
		page.getParams().put("alarmIds", alarmIds);
		return alarmActiveMapper.getAlarmListByAlarmIds(page);

	}
	
	public AlarmNode getInfoById(int id){
		AlarmNode alarmNode = new AlarmNode();
		alarmNode = alarmActiveMapper.getInfoById(id);
		return alarmNode;
	}
	
	public List<Integer> getAlarmStatusById(ArrayList<String> alarmIds){
		return alarmActiveMapper.getAlarmStatusById(alarmIds);
	}

}
