package com.fable.insightview.monitor.perf.service.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.configuration.AlarmRuleConfig;
import com.fable.insightview.monitor.dispatcher.configuration.CollectPeriod;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.perf.entity.PerfCollectPeriodBean;
import com.fable.insightview.monitor.perf.service.IPerfGeneralConfigService;

@Service
public class PerfGeneralConfigServiceImpl implements IPerfGeneralConfigService {
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;

	/**
	 * 获得告警默认值
	 */
	@Override
	public AlarmRuleConfig getAlarmrRuleConfig() {
		AlarmRuleConfig alarmRule = new AlarmRuleConfig();
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();
		alarmRule = facade.getAlarmRuleConfig();
		return alarmRule;
	}

	@Override
	public boolean updateAlarmRuleConfig(AlarmRuleConfig alarmRuleConfig) {
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();
		return facade.updateAlarmRuleConfig(alarmRuleConfig);
	}

	@Override
	public int initCollectPeriodVal(String className) {
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();
		CollectPeriod collectPeriod = facade.getCollectPeriod();
		int collectPeriodVal = -1;
		if(collectPeriod != null){
			if(collectPeriod.getClazzCollectPeriod().get(className) != null){
				collectPeriodVal = collectPeriod.getClazzCollectPeriod().get(className);
			}
		}
		return collectPeriodVal;
	}

	@Override
	public boolean updateCollectPeriod(String className,int collectPeriodVal) {
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();
		CollectPeriod collectPeriod = facade.getCollectPeriod();
		if(collectPeriod == null){
			collectPeriod = new CollectPeriod();
		}
		collectPeriod.getClazzCollectPeriod().put(className, collectPeriodVal);
		return facade.updateCollectPeriod(collectPeriod);
		
	}

	@Override
	public List<PerfCollectPeriodBean> getAllCollectPeriod() {
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();
		CollectPeriod collectPeriod = facade.getCollectPeriod();
		List<PerfCollectPeriodBean> collectPeriodLst = new ArrayList<PerfCollectPeriodBean>();
		if(collectPeriod != null){
			for(Iterator iterator = collectPeriod.getClazzCollectPeriod().keySet().iterator();iterator.hasNext();){
				PerfCollectPeriodBean bean = new PerfCollectPeriodBean();
				String className = (String) iterator.next();
				int collectPeriodVal = collectPeriod.getClazzCollectPeriod().get(className);
				String classLable = mobjectInfoMapper.getByClassName(className).getClassLable();
				bean.setClassName(className);
				bean.setClassLable(classLable);
				bean.setCollectPeriodVal(collectPeriodVal);
				collectPeriodLst.add(bean);
			}
		}
		return collectPeriodLst;
	}

	@Override
	public boolean isExitSetting(String className) {
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();
		CollectPeriod collectPeriod = facade.getCollectPeriod();
		if(collectPeriod != null){
			for(Iterator iterator = collectPeriod.getClazzCollectPeriod().keySet().iterator();iterator.hasNext();){
				String key = (String) iterator.next();
				if(className.endsWith(key)){
					return false;
				}
			}
		}
		return true;
	}

}
