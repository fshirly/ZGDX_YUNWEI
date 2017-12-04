package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmEventRedefineBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.mapper.AlarmEventRedefineMapper;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.service.IAlarmEventRedefineService;
import com.fable.insightview.monitor.deleteMonitorObject.service.IDelCommon;

@Service("delCommon")
public class DelCommonImpl implements IDelCommon {
	private static final Logger logger = LoggerFactory.getLogger(DelCommonImpl.class);
	
	@Autowired
	IAlarmEventRedefineService redefineService;
	@Autowired
	AlarmEventRedefineMapper redefineMapper;

	@Override
	public void delAlarmEventRedefine(String moIds) {
		String[] splits = moIds.split(",");
		try {
			for (int i = 0; i < splits.length; i++) {
				int moId = Integer.parseInt(splits[i]);
				logger.info("删除id为监测对象：{}的重定义规则中添加的监测对象。",moId);
				//获得包含该对象的重定义规则
				List<AlarmEventRedefineBean> ruleList = redefineMapper.getRuleByMOID(moId);
				if(ruleList.size() > 0){
					for (int j = 0; j < ruleList.size(); j++) {
						//获得重定义规则的监测对象个数
						int resCount = ruleList.get(j).getResCount();
						logger.info("删除的监测对象对应的重定义规则id为：{}，该重定义规则的监测对象个数为：{}。",ruleList.get(j).getRuleId(),resCount);
						if(resCount == 1){
							//如果重定义规则中只有一个监测对象，则删除该重定义规则
							redefineService.delAlarmEventRedefine(ruleList.get(j).getRuleId().toString());
						}
						else{
							//如果重定义规则中有多个监测对象，则删除该监测对象的关系记录
							redefineMapper.delRuleOfResourceByMoIds(splits[i]);
						}
					}
				}
			}
			logger.info("删除监测对象的重定义规则。。。。。。END");
		} catch (Exception e) {
			logger.error("删除告警重定义中监测对象异常：",e);
		}
	}
	
}
