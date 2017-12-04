package com.fable.insightview.tasklog.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.sysconf.entity.SysConfig;
import com.fable.insightview.platform.sysconf.service.SysConfigService;
import com.fable.insightview.platform.tasklog.PfOrgTree;
import com.fable.insightview.platform.tasklog.service.IPfTaskLogUsersService;
import com.fable.insightview.platform.tasklog.service.TaskLogService;

@Controller
@RequestMapping("/platform/taskLogConfig")
public class PfTaskLogConfigController {

	@Autowired
	private SysConfigService configService;

	@Autowired
	private TaskLogService taskService;

	@Autowired
	private IPfTaskLogUsersService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(PfTaskLogConfigController.class);

	/**
	 * 跳转到任务日志配置页面
	 * 
	 * @return
	 */
	@RequestMapping("/toConfig")
	public ModelAndView toConfigPage() {
		ModelAndView mv = new ModelAndView("/tasklog/tasklog_config");
		mv.addObject("config", configService.getConfByTypeID(204));
		mv.addObject("userIds", userService.queryUserIdsToStr());
		return mv;
	}

	/**
	 * 修改值班信息系统配置
	 * 
	 * @param value
	 * @return
	 */
	@RequestMapping("/updateConfig")
	@ResponseBody
	public boolean updateDutyConfig(String paraKey, String paraValue) {
		try {
			if (StringUtils.isEmpty(paraKey)) {
				return true;
			}
			String[] keys = paraKey.split("\\|");
			String[] vals = paraValue.split("\\|");
			SysConfig config = null;
			for (int i = 0, length = keys.length; i < length; i++) {
				config = new SysConfig();
				config.setParaKey(keys[i]);
				config.setParaValue(vals[i]);
				config.setType(204);
				configService.updateVal(config);
			}
		} catch (Exception e) {
			LOGGER.error("修改任务日志系统配置异常,{}", e);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param dutyDate
	 * @return
	 */
	@RequestMapping("/manual")
	@ResponseBody
	public boolean manual(String taskTime) {
		try {
			taskService.manual(new SimpleDateFormat("yyyy-MM-dd").parse(taskTime));
		} catch (Exception e) {
			LOGGER.error("手工任务日志信息异常,{}", e);
			return false;
		}
		return true;
	}

	/**
	 * 查询配置人员列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryUsers")
	@ResponseBody
	public Map<String, Object> queryUsers(HttpServletRequest request) {
		Page<Map<String, String>> page = new Page<Map<String, String>>();
		Map<String, Object> params = new HashMap<String, Object>();
		page.setPageNo(Integer.parseInt(request.getParameter("page")));
		page.setPageSize(Integer.parseInt(request.getParameter("rows")));
		params.put("deptId", request.getParameter("deptId"));
		params.put("orgId", request.getParameter("orgId"));
		page.setParams(params);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, String>> persons = userService.queryUsers(page);
			result.put("total", page.getTotalRecord());
			result.put("rows", persons);
		} catch (Exception e) {
			result.put("total", 0);
			result.put("rows", new ArrayList<Map<String, Object>>());
			LOGGER.error("任务日志：查询配置人员列表异常,{}", e);
		}
		return result;
	}

	/**
	 * 删除配置人员
	 * 
	 * @param userId
	 */
	@RequestMapping("/deleteUser")
	@ResponseBody
	public boolean deleteUser(int userId) {
		try {
			userService.delete(userId);
		} catch (Exception e) {
			LOGGER.error("任务日志：删除配置人员异常,{}", e);
			return false;
		}
		return true;
	}

	/**
	 * 查询单位树结构
	 * 
	 * @return
	 */
	@RequestMapping("/queryTree")
	@ResponseBody
	public List<PfOrgTree> queryTree() {
		try {
			return userService.queryTrees();
		} catch (Exception e) {
			LOGGER.error("任务日志：查询配置单位树结构异常,{}", e);
			return new ArrayList<PfOrgTree>();
		}
	}

	/**
	 * 添加配置人员
	 * 
	 * @param userIds
	 * @return
	 */
	@RequestMapping("/addUsers")
	@ResponseBody
	public boolean add(String userIds) {
		try {
			userService.addMulti(userIds);
		} catch (Exception e) {
			LOGGER.error("任务日志：添加配置人员异常,{}", e);
			return false;
		}
		return true;
	}
	
	/**
	 * 添加配置人员
	 * 
	 * @param userIds
	 * @return
	 */
	@RequestMapping("/queryUserIds")
	@ResponseBody
	public List<Integer> queryUserIds() {
		try {
			return userService.queryUserIds();
		} catch (Exception e) {
			LOGGER.error("任务日志：查询配置人员Ids,{}", e);
			return new ArrayList<Integer>();
		}
	}
}
