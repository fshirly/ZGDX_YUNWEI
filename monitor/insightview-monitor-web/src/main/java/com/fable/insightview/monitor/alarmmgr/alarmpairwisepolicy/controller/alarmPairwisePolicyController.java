package com.fable.insightview.monitor.alarmmgr.alarmpairwisepolicy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.monitor.alarmmgr.alarmpairwisepolicy.mapper.AlarmPairwisePolicyMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmPairwisePolicyBean;

@Controller
@Transactional
@RequestMapping("/monitor/alarmPairwisePolicy")
public class alarmPairwisePolicyController {
	private final Logger logger = LoggerFactory
			.getLogger(alarmPairwisePolicyController.class);

	@Autowired
	AlarmPairwisePolicyMapper alarmPairwisePolicyMapper;

	@RequestMapping("/viewAlarmPairwisePolicy")
	@ResponseBody
	public AlarmPairwisePolicyBean initAlarmRepeatPolicy(
			AlarmEventDefineBean alarmEventDefineBean) {
		logger.info("初始化清除策略详情页面。。。。");
		logger.info("初始化清除策略的ID：" + alarmEventDefineBean.getAlarmDefineID());
		AlarmPairwisePolicyBean pairPolicy = alarmPairwisePolicyMapper
				.getByCauseIDAndRecoverID(alarmEventDefineBean
						.getAlarmDefineID());
		return pairPolicy;
	}
}
