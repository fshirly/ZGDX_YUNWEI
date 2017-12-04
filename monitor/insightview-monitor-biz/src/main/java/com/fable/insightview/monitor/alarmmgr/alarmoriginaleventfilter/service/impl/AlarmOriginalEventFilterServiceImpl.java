package com.fable.insightview.monitor.alarmmgr.alarmoriginaleventfilter.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fable.insightview.monitor.alarmmgr.alarmoriginaleventfilter.mapper.AlarmOriginalEventFilterMapper;
import com.fable.insightview.monitor.alarmmgr.alarmoriginaleventfilter.service.IAlarmOriginalEventFilterService;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmOriginalEventFilterBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Service
public class AlarmOriginalEventFilterServiceImpl implements
		IAlarmOriginalEventFilterService {
	private Logger logger = LoggerFactory
			.getLogger(AlarmOriginalEventFilterServiceImpl.class);
	@Autowired
	AlarmOriginalEventFilterMapper alarmFilterMapper;

	@Override
	public Map<String, Object> listAlarmFilter(
			AlarmOriginalEventFilterBean alarmOriginalEventFilterBean) {
		logger.info("开始加载过滤策略数据。。。。。。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<AlarmOriginalEventFilterBean> page = new Page<AlarmOriginalEventFilterBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("alarmDefineID", alarmOriginalEventFilterBean
				.getAlarmDefineID());
		page.setParams(paramMap);
		List<AlarmOriginalEventFilterBean> alarmFilterList = alarmFilterMapper
				.selectAlarmFilter(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", alarmFilterList);
		logger.info("加载数据结束。。。");
		return result;
	}

	@Override
	public Map<String, Object> getAlarmFilterByAlarmDefineId(int alarmDefineId) {
		List<AlarmOriginalEventFilterBean> alarmFilterList = alarmFilterMapper
				.getAlarmFilterByAlarmDefineId(alarmDefineId);
		String match = "";
		String action = "";
		for (int i = 0; i < alarmFilterList.size(); i++) {
			if ("Match".equals(alarmFilterList.get(i).getKeyWord())) {
				match = alarmFilterList.get(i).getKeyValue();
			} else if ("Action".equals(alarmFilterList.get(i).getKeyWord())) {
				action = alarmFilterList.get(i).getKeyValue();
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("match", match);
		result.put("action", action);
		return result;
	}

}
