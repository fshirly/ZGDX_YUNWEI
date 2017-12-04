
package com.fable.insightview.monitor.discover.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.discover.entity.SysNetworkDiscoverTask;
import com.fable.insightview.monitor.discover.mapper.SysNetworkDiscoverTaskMapper;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.perfTask.PerfTaskNotify;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

/**
 * 发现任务
 * @author hanl
 *
 */
@Controller
@RequestMapping("/monitor/discoverTask")
public class DiscoverTaskController {
	@Autowired
	SysNetworkDiscoverTaskMapper discoverTaskMapper;
	
	private final Logger logger = LoggerFactory
			.getLogger(DiscoverTaskController.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	PerfTaskNotify perfTaskNotify = new PerfTaskNotify();

	/**
	 * 加载列表页面
	 * 
	 */
	@RequestMapping("/toDiscoverfTaskList")
	public ModelAndView toPerfTaskList(String navigationBar) {
		ModelAndView mv= new ModelAndView("monitor/discover/discover_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 加载列表页面数据
	 * 
	 */
	@RequestMapping("/listDiscoverTasks")
	@ResponseBody
	public Map<String, Object> listDiscoverTasks(SysNetworkDiscoverTask discoverTaskBean) {
		
		logger.info("加载列表页面数据");
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
			List<SysNetworkDiscoverTask> taskList = discoverTaskMapper
					.selectDiscoverTasks(page);
			for (int i = 0; i < taskList.size(); i++) {
				SysNetworkDiscoverTask task = taskList.get(i);
				if (task.getLaststatustime() != null) {
					String laststatustimeStr = dateFormat.format(task
							.getLaststatustime());
					task.setLaststatustimeStr(laststatustimeStr);
				}
				
				if (task.getCreatetime() != null) {
					String createtimeStr = dateFormat.format(task.getCreatetime());
					task.setCreatetimeStr(createtimeStr);
				}
				
				//设置发现范围
				int taskType = task.getTasktype();
				String discoverRange = "";
				if (taskType == 1) {
					discoverRange = task.getIpaddress1() + "-"
							+ task.getIpaddress2();
				} else if (taskType == 2) {
					discoverRange = task.getIpaddress1() + "-" + task.getNetmask();
				} else if (taskType == 3) {
					discoverRange = task.getIpaddress1() + "-" + task.getHops();
				}
				task.setDiscoverRange(discoverRange);
			}
			int total = page.getTotalRecord();
			result.put("total", total);
			result.put("rows", taskList);
		} catch (Exception e) {
			logger.error("获得发现任务异常："+e);
		}
		logger.info("加载列表页面数据over");
		return result;
	}

	/**
	 * 打开查看页面
	 */
	@RequestMapping("/toShowTaskDetail")
	@ResponseBody
	public ModelAndView toShowTaskDetail(HttpServletRequest request) {
		
		logger.info("打开查看页面");
		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		return new ModelAndView("monitor/discover/discover_detail");
	}

	/**
	 * 查看详情
	 * 
	 */
	@RequestMapping("/initTaskDetail")
	@ResponseBody
	public SysNetworkDiscoverTask initTaskDetail(int taskid) {
		SysNetworkDiscoverTask discoverTask = discoverTaskMapper
				.getTaskInfoByTaskId(taskid);
		if(discoverTask!=null){
			if (discoverTask.getLaststatustime() != null) {
				String laststatustimeStr = dateFormat.format(discoverTask
						.getLaststatustime());
				discoverTask.setLaststatustimeStr(laststatustimeStr);
			}
			if (discoverTask.getCreatetime() != null) {
				String createtimeStr = dateFormat.format(discoverTask
						.getCreatetime());
				discoverTask.setCreatetimeStr(createtimeStr);
			}
			
			int tasktype = discoverTask.getTasktype();
			if (tasktype == 1) {
				discoverTask.setTasktypeInfo("地址段");
			} else if (tasktype == 2) {
				discoverTask.setTasktypeInfo("子网");
			} else if (tasktype == 3) {
				discoverTask.setTasktypeInfo("路由表");
			} else if (tasktype == 4) {
				discoverTask.setTasktypeInfo("单对象");
			}
			
			if (discoverTask.getStatus() != null) {
				int status = discoverTask.getStatus();
				if (status == 0) {
					discoverTask.setStatusInfo("运行中");
				} else if (status == 1) {
					discoverTask.setStatusInfo("已停止");
				} else if (tasktype == -1) {
					discoverTask.setStatusInfo("已删除");
				}
			}
			
			if (discoverTask.getProgressstatus() != null) {
				int progressstatus = discoverTask.getProgressstatus();
				if (progressstatus == 1) {
					discoverTask.setProgressStatusInfo("等待分发");
				} else if (progressstatus == 2) {
					discoverTask.setProgressStatusInfo("正在分发");
				} else if (progressstatus == 3) {
					discoverTask.setProgressStatusInfo("已分发");
				} else if (progressstatus == 4) {
					discoverTask.setProgressStatusInfo("已接收");
				} else if (progressstatus == 5) {
					discoverTask.setProgressStatusInfo("已完成");
				}
			}
		}
		return discoverTask;
	}

	/**
	 * 根据任务ID获取操作进度
	 * 
	 */
	@RequestMapping("/getProcessByTaskId")
	@ResponseBody
	public Map<String, Object> getProcessByTaskId(String taskIds){
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> processLst = new ArrayList<String>();
		List<String> errorinfoLst = new ArrayList<String>();
		try {
			if (taskIds != "") {
				if (taskIds.contains(",") == true) {
					String[] taskIdLst = taskIds.split(",");
					for (int i = 0; i < taskIdLst.length; i++) {
						SysNetworkDiscoverTask task = discoverTaskMapper
								.getTaskInfoByTaskId(Integer.parseInt(taskIdLst[i]
										.split(":")[0]));
						processLst.add(task.getProgressstatus() + ":"
								+ taskIdLst[i].split(":")[1]);
						errorinfoLst.add(task.getErrorinfo() + ":"
								+ taskIdLst[i].split(":")[1]);
					}
				} 
				
				//只有一个任务
				else {
					SysNetworkDiscoverTask task = discoverTaskMapper
							.getTaskInfoByTaskId(Integer.parseInt(taskIds
									.split(":")[0]));
					processLst.add(task.getProgressstatus() + ":"
							+ taskIds.split(":")[1]);
					errorinfoLst.add(task.getErrorinfo() + ":" + taskIds.split(":")[1]);
				}
			}
			result.put("processLst", processLst);
			result.put("errorinfoLst", errorinfoLst);
		} catch (NumberFormatException e) {
			logger.error("根据id获得发现任务异常："+e);
		}
		return result;
	}

	/**
	 * 重发任务
	 * 
	 */
	@RequestMapping("/resendTask")
	@ResponseBody
	public boolean resendTask(SysNetworkDiscoverTask taskBean){
		try {
			discoverTaskMapper.updateTaskProgressStatus(taskBean);
			Dispatcher dispatch = Dispatcher.getInstance();
			ManageFacade facade = dispatch.getManageFacade();
			List<TaskDispatcherServer> servers = facade
					.listActiveServersOf(TaskDispatcherServer.class);
			if (servers.size() > 0) {
				String topic = "TaskDispatch";
				TaskDispatchNotification message = new TaskDispatchNotification();
				message.setDispatchTaskType(TaskType.TASK_DISCOVERY);
				facade.sendNotification(servers.get(0).getIp(), topic, message);
			}
			return true;
		} catch (Exception e) {
			logger.error("任务重发失败：" + e);
			return false;
		}
	}

	/**
	 * 根据任务ID获取采集机
	 * 
	 */
	@RequestMapping("/getCollectorNameByTaskId")
	@ResponseBody
	public List<String> getCollectorNameByTaskId(String taskIds){
		List<String> collectorNameLst = new ArrayList<String>();
		try {
			if (taskIds != "") {
				if (taskIds.contains(",") == true) {
					String[] taskIdLst = taskIds.split(",");
					for (int i = 0; i < taskIdLst.length; i++) {
						String collectorName = discoverTaskMapper
								.getTaskInfoByTaskId(
										Integer
												.parseInt(taskIdLst[i].split(":")[0]))
								.getCollectorName();
						collectorNameLst.add(collectorName + ":"
								+ taskIdLst[i].split(":")[1]);
					}
				} else {
					String collectorName = discoverTaskMapper.getTaskInfoByTaskId(
							Integer.parseInt(taskIds.split(":")[0]))
							.getCollectorName();
					collectorNameLst.add(collectorName + ":"
							+ taskIds.split(":")[1]);
				}
			}
		} catch (Exception e) {
			logger.error("根据id获得发现任务异常："+e);
		}
		return collectorNameLst;
	}
}
