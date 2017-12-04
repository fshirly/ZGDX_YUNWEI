package com.fable.insightview.monitor.perf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.configuration.AlarmRuleConfig;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;

/**
 * 采集通用配置中默认初始化告警配置
 *
 */
public class PerfGeneralConfigInit {
	private static final Logger logger = LoggerFactory
	.getLogger(PerfGeneralConfigInit.class);
	
	public void init(){
		logger.info("初始化设置默认的告警配置");
		AlarmRuleConfig alarmRule = new AlarmRuleConfig();
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();
		alarmRule = facade.getAlarmRuleConfig();
		
		if(alarmRule == null){
			AlarmRuleConfig alarmRuleInit = new AlarmRuleConfig();
			alarmRuleInit.setRepeatNum(1);
			alarmRuleInit.setUpgradeNum(0);
			alarmRuleInit.setTimeWindow(1800);
			facade.updateAlarmRuleConfig(alarmRuleInit);
		}
	}
}
