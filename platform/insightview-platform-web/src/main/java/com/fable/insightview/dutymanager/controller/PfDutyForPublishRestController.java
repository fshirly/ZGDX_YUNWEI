package com.fable.insightview.dutymanager.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.platform.dutymanager.duty.service.IDutyForPublish;

@Controller
@RequestMapping("/rest/pfduty/publish")
public class PfDutyForPublishRestController {

	@Autowired
	private IDutyForPublish dutyPublish;

	private static final Logger LOGGER = LoggerFactory.getLogger(PfDutyForPublishRestController.class);

	/**
	 * 获取当前值班信息
	 * 
	 * @return
	 */
	@RequestMapping("/dutying/{current}")
	@ResponseBody
	public Map<String, Object> getDutying(@PathVariable Date current) {
		try {
			return dutyPublish.getPublishDuty(new Date());
		} catch (Exception e) {
			LOGGER.error("值班管理：信息网站提供参数：{}", current);
			LOGGER.error("值班管理：值班为信息网站发布提供rest接口异常[查询首页中的值班人信息],{}", e);
			return new HashMap<String, Object>();
		}
	}

	/**
	 * 获取当前值班信息
	 * 
	 * @return
	 */
	@RequestMapping("/calendar/{start}/{end}")
	@ResponseBody
	public List<Map<String, Object>> getDutying(@PathVariable String start, @PathVariable String end, @RequestParam(required=false) String current) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("begin", start);
			params.put("end", end);
			if (StringUtils.isNotEmpty(current)) {
				params.put("current", current);
			}
			return dutyPublish.getDuties(params);
		} catch (Exception e) {
			LOGGER.error("值班管理：信息网站提供参数：{}", start + ":" + end + ":" + current);
			LOGGER.error("值班管理：值班为信息网站发布提供rest接口异常[查询日历值班信息],{}", e);
			return new ArrayList<Map<String, Object>>();
		}
	}
}
