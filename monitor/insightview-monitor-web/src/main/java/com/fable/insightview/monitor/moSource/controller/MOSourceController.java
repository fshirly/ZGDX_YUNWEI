package com.fable.insightview.monitor.moSource.controller;

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

import com.fable.insightview.monitor.alarmmgr.entity.MOSourceBean;
import com.fable.insightview.monitor.alarmmgr.mosource.mapper.MOSourceMapper;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/moSource")
public class MOSourceController {
	private Logger logger = LoggerFactory.getLogger(MOSourceController.class);
	@Autowired
	MOSourceMapper moSourceMapper;

	/**
	 * 加载数据表格
	 */
	@RequestMapping("/selectMOSource")
	@ResponseBody
	public Map<String, Object> listMOSource(MOSourceBean moSourceBean) {
		logger.info("开始加载告警源数据。。。。。。。。");
		logger.info("查询的内容：" + moSourceBean.getMoName());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOSourceBean> page = new Page<MOSourceBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moName", moSourceBean.getMoName());
		page.setParams(paramMap);
		List<MOSourceBean> moSourceList = moSourceMapper.selectMOScource(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", moSourceList);
		logger.info("加载数据结束。。。");
		return result;
	}

	@RequestMapping("/findMOSourceInfo")
	@ResponseBody
	public MOSourceBean findMOSourceInfo(MOSourceBean moSourceBean) {
		MOSourceBean moSource = moSourceMapper.getMOSourceById(moSourceBean
				.getMoID());
		return moSource;
	}
}
