package com.fable.insightview.monitor.alarmmgr.alarmrepeatpolicy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.monitor.alarmmgr.alarmeventdefine.mapper.AlarmEventDefineMapper;
import com.fable.insightview.monitor.alarmmgr.alarmoriginaleventfilter.mapper.AlarmOriginalEventFilterMapper;
import com.fable.insightview.monitor.alarmmgr.alarmpairwisepolicy.mapper.AlarmPairwisePolicyMapper;
import com.fable.insightview.monitor.alarmmgr.alarmrepeatpolicy.mapper.AlarmRepeatPolicyMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmRepeatPolicyBean;

@Controller
@Transactional
@RequestMapping("/monitor/alarmRepeatPolicy")
public class alarmRepeatPolicyController {
	private final Logger logger = LoggerFactory
			.getLogger(alarmRepeatPolicyController.class);

	@Autowired
	AlarmEventDefineMapper alarmEventDefineMapper;

	@Autowired
	AlarmPairwisePolicyMapper alarmPairwisePolicyMapper;

	@Autowired
	AlarmRepeatPolicyMapper alarmRepeatPolicyMapper;

	@Autowired
	AlarmOriginalEventFilterMapper alarmOriginalEventFilterMapper;

	@RequestMapping("/viewAlarmRepeatPolicy")
	@ResponseBody
	public AlarmRepeatPolicyBean initAlarmRepeatPolicy(
			AlarmEventDefineBean alarmEventDefineBean) {
		logger.info("初始化重复策略详情页面。。。。");
		logger.info("初始化重复策略的ID：" + alarmEventDefineBean.getAlarmDefineID());
		AlarmRepeatPolicyBean alarmRepeatPolicy = alarmRepeatPolicyMapper
				.getAlarmRepeatByAlarmDefineID(alarmEventDefineBean
						.getAlarmDefineID());
		return alarmRepeatPolicy;
	}
}
