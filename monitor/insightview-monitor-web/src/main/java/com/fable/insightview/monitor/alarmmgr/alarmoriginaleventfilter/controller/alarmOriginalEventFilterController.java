package com.fable.insightview.monitor.alarmmgr.alarmoriginaleventfilter.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.monitor.alarmmgr.alarmoriginaleventfilter.mapper.AlarmOriginalEventFilterMapper;
import com.fable.insightview.monitor.alarmmgr.alarmoriginaleventfilter.service.IAlarmOriginalEventFilterService;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmOriginalEventFilterBean;
import com.fable.insightview.platform.common.util.JsonUtil;

@Controller
@Transactional
@RequestMapping("/monitor/alarmOriginalEventFilter")
public class alarmOriginalEventFilterController {
	private final Logger logger = LoggerFactory
			.getLogger(alarmOriginalEventFilterController.class);

	@Autowired
	AlarmOriginalEventFilterMapper alarmOriginalEventFilterMapper;
	@Autowired
	IAlarmOriginalEventFilterService alarmFilterService;

	/**
	 * 获得过滤策略列表
	 */
	@RequestMapping("/viewAlarmOriginalEventFilter")
	@ResponseBody
	public Map<String, Object> listAlarmFilter(
			AlarmEventDefineBean alarmEventDefineBean) {
		logger.info("初始化过滤策略列表。。。。");
		logger.info("过滤策略的ID：" + alarmEventDefineBean.getAlarmDefineID());
		AlarmOriginalEventFilterBean alarmFilter = new AlarmOriginalEventFilterBean();
		alarmFilter.setAlarmDefineID(alarmEventDefineBean.getAlarmDefineID());
		Map<String, Object> result = new HashMap<String, Object>();
		result = alarmFilterService.listAlarmFilter(alarmFilter);
		return result;
	}

	/**
	 * 获得过滤条件
	 */
	@RequestMapping("/viewAlarmFilterCondition")
	@ResponseBody
	public Map<String, Object> getAlarmFilterCondition(
			AlarmEventDefineBean alarmEventDefineBean) {
		logger.info("初始化过滤策略条件。。。。");
		logger.info("过滤策略的ID：" + alarmEventDefineBean.getAlarmDefineID());
		Map<String, Object> result = alarmFilterService
				.getAlarmFilterByAlarmDefineId(alarmEventDefineBean
						.getAlarmDefineID());
		return result;
	}

	/**
	 * 添加或更新过滤策略
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateAlarmFilter")
	@ResponseBody
	public boolean updateAlarmFilter(HttpServletRequest request) {
		logger.info("添加或更新过滤策略====start");
		boolean flag = true;
		String defineId = request.getParameter("alarmDefineID");
		int alarmDefineID = Integer.parseInt(defineId);
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			String paramValue = request.getParameter(paramName);
			if (!"editing".equals(paramName)) {
				map.put(paramName, paramValue);
			}
		}
		logger.info("新增====map的alarmDefineID:" + map.get("alarmDefineID"));
		AlarmOriginalEventFilterBean alarmFilter = new AlarmOriginalEventFilterBean();
		alarmFilter.setKeyWord((String) map.get("keyWord"));
		alarmFilter.setKeyOperator((String) map.get("keyOperator"));
		alarmFilter.setKeyValue((String) map.get("keyValue"));
		// alarmDefineID不为为空是表示更新
		if (map.get("filterID") != null) {
			logger.info("更新====map的FilterID:" + map.get("filterID")
					+ "=============keyWord:" + map.get("keyWord"));
			alarmFilter.setFilterID(Integer.parseInt((String) map
					.get("filterID")));
			alarmFilter.setmFlag(2);
			logger.info("解析后的过滤对象：" + alarmFilter.getFilterID() + "==="
					+ alarmFilter.getKeyWord() + "======="
					+ alarmFilter.getKeyOperator() + "===="
					+ alarmFilter.getKeyValue());
			try {
				alarmOriginalEventFilterMapper.updateAlarmFilter(alarmFilter);
				flag = true;
			} catch (Exception e) {
				logger.error("更新过滤策略异常：" + e.getMessage());
				flag = false;
			}
		} else {
			// alarmFilter.setAlarmDefineID(Integer.parseInt((String)
			// map.get("alarmDefineID")));
			alarmFilter.setmFlag(1);
			alarmFilter.setAlarmDefineID(alarmDefineID);
			try {
				alarmOriginalEventFilterMapper.insertAlarmFilter(alarmFilter);
				flag = true;
			} catch (Exception e) {
				logger.error("新增过滤策略异常：" + e.getMessage());
				flag = false;
			}
		}
		return flag;

	}

	/**
	 * 删除过滤策略
	 * 
	 * @param filterID
	 * @return
	 */
	@RequestMapping("/delAlarmFilter")
	@ResponseBody
	public boolean delAlarmFilter(int filterID) {
		logger.info("开始删除过滤策略。。。");
		try {
			alarmOriginalEventFilterMapper
					.delAlarmFilterByAlarmFilterID(filterID);
			return true;
		} catch (Exception e) {
			logger.error("删除过滤策略异常===" + e.getMessage());
			return false;
		}
	}

	/**
	 * 验证过滤策略变量名的唯一性
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkKeyWord")
	@ResponseBody
	public boolean checkKeyWord(HttpServletRequest request) {
		logger.info("验证过滤策略的唯一性====start");
		boolean flag = true;
		String defineId = request.getParameter("alarmDefineID");
		int alarmDefineID = Integer.parseInt(defineId);
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			String paramValue = request.getParameter(paramName);
			if (!"editing".equals(paramName)) {
				map.put(paramName, paramValue);
			}
		}
		logger.info("新增====map的alarmDefineID:" + map.get("alarmDefineID"));
		AlarmOriginalEventFilterBean alarmFilter = new AlarmOriginalEventFilterBean();
		alarmFilter.setKeyWord((String) map.get("keyWord"));
		alarmFilter.setKeyOperator((String) map.get("keyOperator"));
		alarmFilter.setKeyValue((String) map.get("keyValue"));
		alarmFilter.setAlarmDefineID(alarmDefineID);
		// alarmDefineID不为为空是表示更新
		if (map.get("filterID") != null) {
			alarmFilter.setFilterID(Integer.parseInt((String) map
					.get("filterID")));
			int count = alarmOriginalEventFilterMapper
					.getByKeyWordAndId(alarmFilter);
			if (count > 0) {
				flag = false;
			} else {
				flag = true;
			}
		} else {
			int count = alarmOriginalEventFilterMapper
					.getByKeyWord(alarmFilter);
			if (count > 0) {
				flag = false;
			} else {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 验证告警事件是否有过滤策略
	 * 
	 * @param alarmDefineID
	 * @return
	 */
	@RequestMapping("/checkAlarmFilter")
	@ResponseBody
	public boolean checkAlarmFilter(int alarmDefineID) {
		List<AlarmOriginalEventFilterBean> filterList = alarmOriginalEventFilterMapper
				.getAlarmFilterByAlarmDefineId(alarmDefineID);
		if (filterList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得过滤策略
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/getFilters")
	@ResponseBody
	public Map<String, Object> getFilters(int alarmDefineID) {
		logger.info("获得过滤策略");
		Map<String, Object> result = new HashMap<String, Object>();
		List<AlarmOriginalEventFilterBean> filterLst = alarmOriginalEventFilterMapper
				.getAlarmFilterByAlarmDefineId(alarmDefineID);
		String filterLstJson = JsonUtil.toString(filterLst);
		result.put("filterLstJson", filterLstJson);
		return result;
	}
}
