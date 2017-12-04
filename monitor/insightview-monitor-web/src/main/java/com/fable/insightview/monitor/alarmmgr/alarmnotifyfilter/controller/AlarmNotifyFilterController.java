package com.fable.insightview.monitor.alarmmgr.alarmnotifyfilter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fable.insightview.monitor.alarmmgr.alarmeventdefine.mapper.AlarmEventDefineMapper;
import com.fable.insightview.monitor.alarmmgr.alarmlevel.entity.AlarmLevelBean;
import com.fable.insightview.monitor.alarmmgr.alarmlevel.mapper.AlarmLevelMapper;
import com.fable.insightview.monitor.alarmmgr.alarmnotifyfilter.mapper.AlarmNotifyFilterMapper;
import com.fable.insightview.monitor.alarmmgr.alarmtype.entity.AlarmTypeBean;
import com.fable.insightview.monitor.alarmmgr.alarmtype.mapper.AlarmTypeMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyFilterBean;
import com.fable.insightview.monitor.alarmmgr.entity.MOSourceBean;
import com.fable.insightview.monitor.alarmmgr.mosource.mapper.MOSourceMapper;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/alarmNotifyFilter")
public class AlarmNotifyFilterController {
	private Logger logger = LoggerFactory
			.getLogger(AlarmNotifyFilterController.class);

	@Autowired
	AlarmNotifyFilterMapper alarmNotifyFilterMapper;
	@Autowired
	AlarmLevelMapper alarmLevelMapper;
	@Autowired
	AlarmTypeMapper alarmTypeMapper;
	@Autowired
	MOSourceMapper moSourceMapper;
	@Autowired
	AlarmEventDefineMapper alarmEventDefineMapper;

	/**
	 * 查看详情
	 */
	@RequestMapping("/viewAlarmNotifyFilter")
	@ResponseBody
	public Map<String, Object> listAlarmFilter(
			AlarmNotifyFilterBean alarmNotifyFilterBean) {
		logger.info("初始化通知过滤列表。。。。");
		logger.info("通知规则的ID：" + alarmNotifyFilterBean.getPolicyID());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<AlarmNotifyFilterBean> page = new Page<AlarmNotifyFilterBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("policyID", alarmNotifyFilterBean.getPolicyID());
		page.setParams(paramMap);
		List<AlarmNotifyFilterBean> notifyFilterList = alarmNotifyFilterMapper
				.selectAlarmNotifyFilter(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", notifyFilterList);
		logger.info("加载数据结束。。。");
		return result;
	}

	/**
	 * 获得告警等级
	 */
	@RequestMapping("/getNotSelelctLevel")
	@ResponseBody
	public Map<String, Object> getNotSelelctLevel() {
		List<AlarmLevelBean> levelList = alarmLevelMapper.getAllAlarmLevel();
		String levelLstJson = JsonUtil.toString(levelList);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("levelLstJson", levelLstJson);
		return result;
	}

	/**
	 * 获得告警类型
	 */
	@RequestMapping("/getType")
	@ResponseBody
	public Map<String, Object> getType() {
		List<AlarmTypeBean> typeList = alarmTypeMapper.getAllAlarmType();
		String typeLstJson = JsonUtil.toString(typeList);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("typeLstJson", typeLstJson);
		return result;
	}

	/**
	 * 获得告警源
	 */
	@RequestMapping("/getMOSource")
	@ResponseBody
	public Map<String, Object> getMOSource() {
		List<MOSourceBean> moSourceList = moSourceMapper.getAllMOSource();
		String moSourceLstJson = JsonUtil.toString(moSourceList);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("moSourceLstJson", moSourceLstJson);
		return result;
	}

	/**
	 * 获得告警事件
	 */
	@RequestMapping("/getEvent")
	@ResponseBody
	public Map<String, Object> getEvent() {
		List<AlarmEventDefineBean> alarmEventList = alarmEventDefineMapper
				.getAllAlarmEvent();
		String alarmEventLstJson = JsonUtil.toString(alarmEventList);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("alarmEventLstJson", alarmEventLstJson);
		return result;
	}

	/**
	 * 添加通知过滤
	 * 
	 * @return
	 */
	@RequestMapping("addAlarmNotifyFilter")
	@ResponseBody
	public boolean addAlarmNotifyFilter(HttpServletRequest request,
			AlarmNotifyFilterBean alarmNotifyFilterBean) {
		String filterVals = request.getParameter("filterValeName");
		String arr[] = filterVals.split(",");
		try {
			for (int i = 0; i < arr.length; i++) {
				alarmNotifyFilterBean.setFilterKeyValue(Integer
						.parseInt(arr[i]));
				int insertCount = alarmNotifyFilterMapper
						.insertAlarmNotifyFilter(alarmNotifyFilterBean);
				logger.info("添加通知过滤的结果：" + insertCount);
			}
		} catch (Exception e) {
			logger.info("添加通知过滤失败" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 删除通知人
	 * 
	 * @param alarmNotifyToUsersBean
	 * @return
	 */
	@RequestMapping("/delNotifyFilter")
	@ResponseBody
	public boolean delNotifyFilter(AlarmNotifyFilterBean alarmNotifyFilterBean) {
		logger.info("删除。。。。start");
		return alarmNotifyFilterMapper
				.delAlarmNotifyFilter(alarmNotifyFilterBean.getFilterID());
	}

	@RequestMapping("/checkForAddFilter")
	@ResponseBody
	public boolean checkForAddFilter(AlarmNotifyFilterBean alarmNotifyFilterBean) {
		int checkRS = alarmNotifyFilterMapper
				.selectByConditions(alarmNotifyFilterBean);
		if (checkRS > 0) {
			return false;
		} else {
			return true;
		}
	}
}
