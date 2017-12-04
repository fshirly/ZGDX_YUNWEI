package com.fable.insightview.monitor.perf.service;

import java.util.List;

import com.fable.insightview.monitor.dispatcher.configuration.AlarmRuleConfig;
import com.fable.insightview.monitor.dispatcher.configuration.CollectPeriod;
import com.fable.insightview.monitor.perf.entity.PerfCollectPeriodBean;

/**
 * 采集通用配置
 * @author 123
 *
 */
public interface IPerfGeneralConfigService {
	
	/**
	 * 获得告警默认值
	 */
	AlarmRuleConfig getAlarmrRuleConfig();
	
	/**
	 * 更新告警默认值
	 */
	boolean updateAlarmRuleConfig(AlarmRuleConfig alarmRuleConfig);
	
	/**
	 * 获得对象默认的采集周期 
	 */
	int initCollectPeriodVal(String className);
	
	/**
	 * 更新采集周期默认值
	 */
	boolean updateCollectPeriod(String className,int collectPeriodVal);
	
	/**
	 * 获得所有的采集周期配置
	 */
	List<PerfCollectPeriodBean> getAllCollectPeriod();
	
	boolean isExitSetting(String className);
}
