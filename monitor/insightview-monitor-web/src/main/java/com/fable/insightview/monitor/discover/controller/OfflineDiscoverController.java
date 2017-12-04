package com.fable.insightview.monitor.discover.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.discover.entity.SysNetworkDiscoverTask;
import com.fable.insightview.monitor.discover.service.IOfflineDiscoverService;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.dispatcher.utils.DispatcherUtils;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * 
 * 离线发现
 * 
 */
@Controller
@RequestMapping("/monitor/offlineDiscover")
public class OfflineDiscoverController {
	private static final long serialVersionUID = -8697049781798812644L;

	private static final Logger logger = LoggerFactory.getLogger(OfflineDiscoverController.class);

	private static final String OFFLINE = "1";
	
	@Autowired
	IOfflineDiscoverService offlineDiscoverService;

	/**
	 * 跳转至离线发现页面
	 */
	@RequestMapping("/toOfflineDiscover")
	public ModelAndView discoverDevice(HttpServletRequest result,
			HttpServletResponse response,String navigationBar) {
		ModelAndView mv = new ModelAndView("monitor/discover/offlineDiscover");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 获得发现的离线采集机
	 */
	@RequestMapping("/findHostLst")
	@ResponseBody
	public Map<String, Object> findHostLst(){
		logger.info("获得所有发现的离线采集机");
		return offlineDiscoverService.findHostLst();
	}
	
	/**
	 * 新增起始ip发现、子网发现、路由发现
	 */
	@RequestMapping("/saveDiscover1")
	@ResponseBody
	public void saveDiscover1(HttpServletRequest request,HttpServletResponse response){
		boolean addTaskFlag = false;
		SysNetworkDiscoverTask discover = new SysNetworkDiscoverTask();
		int way = Integer.parseInt(request.getParameter("way"));
		if (way == 1) {
			discover.setIpaddress1(request.getParameter("startIP1"));
			discover.setIpaddress2(request.getParameter("endIP1"));
		} else if (way == 2) {
			discover.setIpaddress1(request.getParameter("startIP2"));
			discover.setNetmask(request.getParameter("netMask2"));
		} else if (way == 3) {
			discover.setIpaddress1(request.getParameter("startIP3"));
			discover.setHops(Integer.parseInt(request.getParameter("step3")));
		}
		 int collectorid = Integer.parseInt(request.getParameter("collectorid"));
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		// 1:地址段 2:子网 3:路由表
		discover.setTasktype(way);
		discover.setWebipaddress(DispatcherUtils.getLocalAddress());
		discover.setCreator(sysUserInfoBeanTemp.getId().intValue());
		discover.setCreatetime(new Date());
		discover.setProgressstatus(1);
		discover.setOperatestatus(1);
		discover.setCollectorid(collectorid);
		discover.setIsOffline(OFFLINE);
			addTaskFlag = offlineDiscoverService.addOfflineTask(discover);
			if(addTaskFlag){
				logger.info("保存任务结束 taskID=" + discover.getTaskid());
				logger.info("发送消息通知发现程序!");
				sendMeg();
			}else{
				try {
					response.setContentType("application/json");
					String errorInfo = "保存入库错误!";
					//返回错误信息
					response.getWriter().write(
							"{\"errorInfo\":\"" + errorInfo + "\"}");
				} catch (IOException e1) {
					logger.error("返回错误信息错误：",e1);
				}
			}
	}
	
	/**
	 * 发送消息给zookeeper,通知任务分发
	 */
	public static void sendMeg() {
		try {
			logger.info("开始发送消息给zookeeper!");
			Dispatcher dispatcher = Dispatcher.getInstance();
			ManageFacade facade = dispatcher.getManageFacade();
			List<TaskDispatcherServer> servers = facade
					.listActiveServersOf(TaskDispatcherServer.class);
			if (servers.size() > 0) {
				String topic = "TaskDispatch";
				TaskDispatchNotification message = new TaskDispatchNotification();
				message.setDispatchTaskType(TaskType.TASK_DISCOVERY);
				facade.sendNotification(servers.get(0).getIp(), topic, message);
			}
		} catch (Exception e) {
			logger.error("发送消息给zookeeper出错", e);
		}
	}
	
	/**
	 * 跳转到离线任务列表页面
	 * 
	 */
	@RequestMapping("/toOfflineTaskList")
	public ModelAndView toPerfTaskList(String navigationBar) {
		ModelAndView mv= new ModelAndView("monitor/discover/offlineDiscover_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 离线任务列表
	 */
	@RequestMapping("/listOfflineTasks")
	@ResponseBody
	public Map<String, Object> listOfflineTasks(SysNetworkDiscoverTask discoverTaskBean){
		logger.info("加载离线任务列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysNetworkDiscoverTask> page = new Page<SysNetworkDiscoverTask>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ipaddress1", discoverTaskBean.getIpaddress1());
		paramMap.put("tasktype", discoverTaskBean.getTasktype());
		paramMap.put("status", discoverTaskBean.getStatus());
		paramMap.put("collectorName", discoverTaskBean.getCollectorName());
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<SysNetworkDiscoverTask> taskList = offlineDiscoverService.selectOfflineTasks(page);
			int total = page.getTotalRecord();
			result.put("total", total);
			result.put("rows", taskList);
			
		} catch (Exception e) {
			logger.error("获得离线发现任务异常："+e);
		}
		logger.info("加载离线任务列表页面数据over");
		return result;
	}
}