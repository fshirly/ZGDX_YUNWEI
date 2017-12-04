package com.fable.insightview.platform.systemlog.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.platform.common.entity.DataBean;
import com.fable.insightview.platform.common.entity.MessageBean;
import com.fable.insightview.platform.common.util.Constants.OperationResult;
import com.fable.insightview.platform.common.util.Constants.OperationType;
import com.fable.insightview.platform.common.util.Tool;
import com.fable.insightview.platform.modulelog.entity.LogConfigBean;
import com.fable.insightview.platform.modulelog.service.SystemLogService;
import com.mysql.fabric.xmlrpc.base.Array;


/**
 * 系统日志管理控制器处理类
 * 
 * @author nimj
 */
@Controller
@RequestMapping("sys/systemLog/")
public class SystemLogController {
	private static final String PAGE = "platform/system/systemLog/{0}";
	@Autowired
	private SystemLogService systemLogService;

	@RequestMapping(value = "accessLogs/list/html", method = RequestMethod.GET)
	//@ApiOperation(value = "系统访问日志页面", notes = "前往系统访问日志页面")
	public String gotoAccessLogPage() {
		return MessageFormat.format(PAGE, "accessLog");
	}
	
	@RequestMapping(value = "sessionLogs/list/html", method = RequestMethod.GET)
	//@ApiOperation(value = "系统访问日志页面", notes = "前往系统访问日志页面")
	public String gotoSessionLogPage() {
		return MessageFormat.format(PAGE, "sessionLog");
	}

	@RequestMapping(value = "operationLogs/list/html", method = RequestMethod.GET)
	//@ApiOperation(value = "系统操作日志页面", notes = "前往系统操作日志页面")
	public String gotoOperationLogPage() {
		return MessageFormat.format(PAGE, "operationLog");
	}

	/**
	 * 初始化操作类型和操作结果（非REST接口，仅用于前台展示，避免在后台和前台维护两份数据）
	 */
	@RequestMapping(value = "initOperationTypeAndResult", method = RequestMethod.GET)
	public @ResponseBody
	JSONArray initOperationTypeAndResult() {
		return Tool.getJsonAry(OperationType.getOperationTypes(),
				OperationResult.getOperationResults());
	}

	@RequestMapping(value = "exceptionLogs/list/html", method = RequestMethod.GET)
	//@ApiOperation(value = "系统异常日志页面", notes = "前往系统异常日志页面")
	public String gotoExceptionLogPage() {
		return MessageFormat.format(PAGE, "exceptionLog");
	}

//	@RequestMapping(value = "exceptionLog/{id}/stackInfo", method = RequestMethod.GET)
//	//@ApiOperation(value = "根据ID获取异常堆栈信息", notes = "根据ID获取异常堆栈信息")
//	public @ResponseBody
//	JSONObject getExceptionStackInfo(@PathVariable String id) {
//		JSONObject jsonStackInfo = new JSONObject();
//		jsonStackInfo.put("stackInfo",
//				systemLogService.getExceptionStackInfo(id));
//		return jsonStackInfo;
//	}

	@RequestMapping(value = "logConfigs/html", method = RequestMethod.GET)
	//@ApiOperation(value = "系统日志配置页面", notes = "前往系统日志配置页面")
	public String gotoLogCfgPage() {
		return MessageFormat.format(PAGE, "logConfig");
	}

	@RequestMapping(value = "logConfigs", method = RequestMethod.GET)
	//@ApiOperation(value = "查询系统日志配置", notes = "查询系统日志配置")
	public @ResponseBody
	DataBean<LogConfigBean> queryLogConfigs() {
		DataBean<LogConfigBean> dataBean = new DataBean<LogConfigBean>();
		List<LogConfigBean> logConfigs = systemLogService.queryLogConfigs();
		if (logConfigs == null) {
			logConfigs = new ArrayList<LogConfigBean>(3);
		}
		dataBean.setData(logConfigs);
		return dataBean;
	}

	@RequestMapping(value = "logConfigs", method = RequestMethod.POST)
	//@ApiOperation(value = "保存系统日志配置", notes = "保存系统日志配置")
	public @ResponseBody
	MessageBean saveLogConfigs(
			//@ApiParam(required = true, value = "日志配置集") 
			@RequestBody LogConfigBean[] logCfgs) {
		List<LogConfigBean> list =  Arrays.asList(logCfgs);
		systemLogService.saveLogConfigs(list);
		return Tool.getSuccMsgBean("保存成功");
	}

	@RequestMapping(value = "cron/html", method = RequestMethod.GET)
	//@ApiOperation(value = "弹出Cron表达式简介页面", notes = "弹出Cron表达式简介页面")
	public String showAbountCronPage() {
		//return "platform/system/cron";
		return MessageFormat.format(PAGE, "cron");
	}
}
