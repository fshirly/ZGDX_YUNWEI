package com.fable.insightview.monitor.alarmmgr.alarmeventdefine.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmcategory.entity.AlarmCategoryBean;
import com.fable.insightview.monitor.alarmmgr.alarmcategory.mapper.AlarmCategoryMapper;
import com.fable.insightview.monitor.alarmmgr.alarmeventdefine.entity.AlarmBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventdefine.mapper.AlarmEventDefineMapper;
import com.fable.insightview.monitor.alarmmgr.alarmoriginaleventfilter.mapper.AlarmOriginalEventFilterMapper;
import com.fable.insightview.monitor.alarmmgr.alarmpairwisepolicy.mapper.AlarmPairwisePolicyMapper;
import com.fable.insightview.monitor.alarmmgr.alarmrepeatpolicy.mapper.AlarmRepeatPolicyMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmOriginalEventFilterBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmPairwisePolicyBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmRepeatPolicyBean;
import com.fable.insightview.monitor.alarmmgr.entity.SysTrapTaskBean;
import com.fable.insightview.monitor.alarmmgr.traptask.mapper.SysTrapTaskMapper;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.harvester.mapper.HarvesterMapper;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@Transactional
@RequestMapping("/monitor/alarmEventDefine")
public class AlarmEventDefineController {
	private final Logger logger = LoggerFactory
			.getLogger(AlarmEventDefineController.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	AlarmEventDefineMapper alarmEventDefineMapper;

	@Autowired
	AlarmPairwisePolicyMapper alarmPairwisePolicyMapper;

	@Autowired
	AlarmRepeatPolicyMapper alarmRepeatPolicyMapper;

	@Autowired
	AlarmOriginalEventFilterMapper alarmOriginalEventFilterMapper;

	@Autowired
	AlarmCategoryMapper alarmGategoryMapper;

	@Autowired
	AlarmActiveMapper activeMapper;
	
	@Autowired
	HarvesterMapper harvesterMapper;
	@Autowired
	SysTrapTaskMapper trapTaskMapper;

	/**
	 * 告警定义界面弹出
	 * 
	 * @return
	 */
	@RequestMapping("/toAlarmEventDefine")
	@ResponseBody
	public ModelAndView toAlarmEventDefineList(String navigationBar) {
		logger.info("进入告警定义界面");
		ModelAndView mv = new ModelAndView("monitor/alarmMgr/alarmeventdefine/alarmEventDefine_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 加载表格数据
	 */
	@RequestMapping("/listAlarmEventDefine")
	@ResponseBody
	public Map<String, Object> listAlarmEventDefine(
			AlarmEventDefineBean alarmEventDefineBean) {
		logger.debug("开始加载数据。。。。。。。。");
		logger.info("查询的内容：" + alarmEventDefineBean.getAlarmName());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<AlarmEventDefineBean> page = new Page<AlarmEventDefineBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("alarmName", alarmEventDefineBean.getAlarmName());
		page.setParams(paramMap);
		List<AlarmEventDefineBean> alarmEventDefineList = alarmEventDefineMapper
				.selectAlarmEventDefine(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", alarmEventDefineList);
		logger.info("加载数据结束。。。");
		return result;
	}

	/**
	 * 打开查看页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/toShowAlarmEventDefineDetail")
	@ResponseBody
	public ModelAndView toShowAlarmEventDefineDetail(HttpServletRequest request) {
		logger.info("打开查看页面");
		String alarmDefineID = request.getParameter("alarmDefineID");
		String recoverAlarmDefineID = request
				.getParameter("recoverAlarmDefineID");
		String index = request.getParameter("index");
		request.setAttribute("alarmDefineID", alarmDefineID);
		request.setAttribute("recoverAlarmDefineID", recoverAlarmDefineID);
		request.setAttribute("index", index);
		return new ModelAndView(
				"monitor/alarmMgr/alarmeventdefine/alarmEventDefine_detail");
	}

	/**
	 * 查看页面
	 */
	@RequestMapping("/viewAlarmEventDefine")
	@ResponseBody
	public AlarmEventDefineBean initAlarmEventDefine(
			AlarmEventDefineBean alarmEventDefineBean) {
		logger.info("初始化查看页面。。。。");
		logger.info("查看行的ID：" + alarmEventDefineBean.getAlarmDefineID());
		AlarmEventDefineBean alarmEventDefine = alarmEventDefineMapper
				.getAlarmEventDefineByID(alarmEventDefineBean
						.getAlarmDefineID());
		if (alarmEventDefine.getCategoryID() != null) {

			AlarmCategoryBean category = alarmGategoryMapper
					.getAlarmGategoryById(alarmEventDefine.getCategoryID());
			String alarmOIDDefualt = category.getAlarmOID();
			String alarmOID = alarmEventDefine.getAlarmOID();
			String splitAlarmOID = alarmOID.substring(alarmOIDDefualt.length(),
					alarmOID.length());
			alarmEventDefine.setSplitAlarmOID(splitAlarmOID);
		} else {
			alarmEventDefine.setSplitAlarmOID(alarmEventDefine.getAlarmOID());
		}
		return alarmEventDefine;
	}

	/**
	 * 删除前的验证是否为系统自定义事件
	 * 
	 * @param alarmEventDefineBean
	 * @return
	 */
	@RequestMapping("/checkIsSystem")
	@ResponseBody
	public boolean checkIsSystem(AlarmEventDefineBean alarmEventDefineBean) {
		logger.info("删除是否为系统自定义事件.......start");
		int isSystem = alarmEventDefineMapper.getAlarmEventDefineByID(
				alarmEventDefineBean.getAlarmDefineID()).getIsSystem();
		if (isSystem == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除前验证是否被使用
	 * 
	 * @param alarmEventDefineBean
	 * @return
	 */
	@RequestMapping("/checkBeforeDel")
	@ResponseBody
	public boolean checkBeforeDel(AlarmEventDefineBean alarmEventDefineBean) {
		logger.info("删除前验证是否被活动告警被使用........start");
		int activeCount = activeMapper.getByAlarmDefineID(alarmEventDefineBean
				.getAlarmDefineID());
		if (activeCount == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除
	 * 
	 * @param alarmEventDefineBean
	 * @return
	 */
	@RequestMapping("/delAlarmDefine")
	@ResponseBody
	@Transactional(rollbackFor = { Exception.class })
	public boolean delAlarmDefine(AlarmEventDefineBean alarmEventDefineBean) {
		AlarmPairwisePolicyBean alarmPairwisePolicy = alarmPairwisePolicyMapper
				.getByCauseIDAndRecoverID(alarmEventDefineBean
						.getAlarmDefineID());
		List<Integer> alarmDefineIds = new ArrayList<Integer>();
		alarmDefineIds.add(alarmEventDefineBean.getAlarmDefineID());
		try {
			if (alarmPairwisePolicy != null) {
				// logger.info("成对的ID："+alarmPairwisePolicy.getPloicyID()+"causeID:"+alarmPairwisePolicy.getCauseAlarmDefineID()+"=====recoverID:"+alarmPairwisePolicy.getRecoverAlarmDefineID());
				if (alarmPairwisePolicy.getCauseAlarmDefineID() != null) {
					alarmDefineIds.add(alarmPairwisePolicy
							.getCauseAlarmDefineID());
				}
				if (alarmPairwisePolicy.getRecoverAlarmDefineID() != null) {
					alarmDefineIds.add(alarmPairwisePolicy
							.getRecoverAlarmDefineID());
				}
				alarmPairwisePolicyMapper.delPairwisePolicy(alarmPairwisePolicy
						.getPloicyID());
			}
			alarmRepeatPolicyMapper
					.delRepeatPolicyByAlarmDefineID(alarmDefineIds);
			alarmOriginalEventFilterMapper
					.delAlarmFilterByAlarmDefineID(alarmDefineIds);
			AlarmEventDefineBean alarmEvent = alarmEventDefineMapper
					.getAlarmEventDefineByID(alarmEventDefineBean
							.getAlarmDefineID());
			SysTrapTaskBean trapTask = alarmOriginalEventFilterMapper
					.getTrapTaskByMOID(alarmEvent.getAlarmOID());
			if (trapTask != null) {
				trapTask.setOperateStatus(3);
				trapTask.setProgressStatus(1);
				alarmOriginalEventFilterMapper.updateSysTrapTask(trapTask);
				Dispatcher dispatch = Dispatcher.getInstance();
				ManageFacade facade = dispatch.getManageFacade();
				List<TaskDispatcherServer> servers = facade
						.listActiveServersOf(TaskDispatcherServer.class);
				if (servers.size() > 0) {
					String topic = "TaskDispatch";
					TaskDispatchNotification message = new TaskDispatchNotification();
					message.setDispatchTaskType(TaskType.TASK_TRAP);
					facade.sendNotification(servers.get(0).getIp(), topic,
							message);
				}
			}
			logger.info("开始删除定义表。。。。。。。。");
			alarmEventDefineMapper.delAlarmEventDefine(alarmDefineIds);
			return true;
		} catch (Exception e) {
			logger.error("删除异常" + e.getMessage());
			return false;
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param alarmEventDefineBean
	 * @return
	 */
	@RequestMapping("/delAlarmDefines")
	@ResponseBody
	public Map<String, Object> delAlarmDefines(HttpServletRequest request) {
		logger.info("批量删除前的验证........start");
		String alarmDefineIDs = request.getParameter("alarmDefineIDs");
		logger.info("批量删除的ID=====" + alarmDefineIDs);
		String[] splitIds = alarmDefineIDs.split(",");
		String defineName = "";
		String useName = "";
		boolean flag = false;
		boolean isSystem = true;
		boolean isUse = true;
		boolean mark = true;
		/* 能够被删的ID数组 */
		List<Integer> delDefine = new ArrayList<Integer>();
		/* 不能被删保留的ID数组 */
		List<Integer> reserveDefine = new ArrayList<Integer>();
		for (String strId : splitIds) {
			int alarmDefineID = Integer.parseInt(strId);
			AlarmEventDefineBean alarmDefine = alarmEventDefineMapper
					.getAlarmEventDefineByID(alarmDefineID);
			// 判断是否为系统自定义
			if (alarmDefine.getIsSystem() == 1) {
				reserveDefine.add(alarmDefineID);
				defineName += alarmDefine.getAlarmName() + ",";
			} else {
				int activeCount = activeMapper
						.getByAlarmDefineID(alarmDefineID);
				// 判断是否被使用
				if (activeCount > 0) {
					reserveDefine.add(alarmDefineID);
					useName += alarmDefine.getAlarmName() + ",";
				} else {
					delDefine.add(alarmDefineID);
				}
			}
		}

		for (int i = 0; i < delDefine.size(); i++) {
			AlarmPairwisePolicyBean alarmPairwisePolicy = alarmPairwisePolicyMapper
					.getByCauseIDAndRecoverID(delDefine.get(i));
			List<Integer> alarmDefineIds = new ArrayList<Integer>();
			alarmDefineIds.add(delDefine.get(i));
			try {
				if (alarmPairwisePolicy != null) {
					if (alarmPairwisePolicy.getCauseAlarmDefineID() != null) {
						alarmDefineIds.add(alarmPairwisePolicy
								.getCauseAlarmDefineID());
					}
					if (alarmPairwisePolicy.getRecoverAlarmDefineID() != null) {
						alarmDefineIds.add(alarmPairwisePolicy
								.getRecoverAlarmDefineID());
					}
					alarmPairwisePolicyMapper
							.delPairwisePolicy(alarmPairwisePolicy
									.getPloicyID());
				}
				alarmRepeatPolicyMapper
						.delRepeatPolicyByAlarmDefineID(alarmDefineIds);
				alarmOriginalEventFilterMapper
						.delAlarmFilterByAlarmDefineID(alarmDefineIds);
				AlarmEventDefineBean alarmEvent = alarmEventDefineMapper
						.getAlarmEventDefineByID(delDefine.get(i));
				if (alarmEvent != null) {
					List<SysTrapTaskBean> trapTaskList = alarmOriginalEventFilterMapper
							.getTrapTaskByMOID2(alarmEvent.getAlarmOID());
					if (trapTaskList != null) {
						for (int j = 0; j < trapTaskList.size(); j++) {
							SysTrapTaskBean trapTask = new SysTrapTaskBean();
							trapTask.setOperateStatus(3);
							trapTask.setProgressStatus(1);
							alarmOriginalEventFilterMapper.updateSysTrapTask(trapTask);
						}
						Dispatcher dispatch = Dispatcher.getInstance();
						ManageFacade facade = dispatch.getManageFacade();
						List<TaskDispatcherServer> servers = facade
								.listActiveServersOf(TaskDispatcherServer.class);
						if (servers.size() > 0) {
							String topic = "TaskDispatch";
							TaskDispatchNotification message = new TaskDispatchNotification();
							message.setDispatchTaskType(TaskType.TASK_TRAP);
							facade.sendNotification(servers.get(0).getIp(), topic, message);
						}
					}
				}
				alarmEventDefineMapper.delAlarmEventDefine(alarmDefineIds);
				flag = true;
			} catch (Exception e) {
				flag = false;
				mark = false;
				logger.error("删除异常" ,e);
			}
		}

		if (defineName.length() > 0) {
			isSystem = false;
			flag = false;
			defineName = defineName.substring(0, defineName.lastIndexOf(","));
		}
		if (useName.length() > 0) {
			isUse = false;
			flag = false;
			useName = useName.substring(0, useName.lastIndexOf(","));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		defineName = " 【 " + defineName + " 】 ";
		useName = " 【 " + useName + " 】 ";
		logger.info("flag=-==" + flag);
		result.put("flag", flag);
		result.put("isSystem", isSystem);
		result.put("isUse", isUse);
		result.put("defineName", defineName);
		result.put("useName", useName);
		result.put("mark", mark);
		return result;
	}

	/**
	 * 打开新增页面
	 */
	@RequestMapping("/toShowAlarm")
	@ResponseBody
	public ModelAndView toShowAlarmAdd(HttpServletRequest request) {
		logger.info("加载新增页面");
		// String alarmDefineID = request.getParameter("alarmDefineID");
		// request.setAttribute("alarmDefineID", alarmDefineID);
		return new ModelAndView(
				"monitor/alarmMgr/alarmeventdefine/alarmEventDefine_add");
	}

	/**
	 * 打开详情页面前的判断
	 * 
	 * @param alarmEventDefineBean
	 * @return
	 */
	@RequestMapping("/checkForOpenDetail")
	@ResponseBody
	public Map<String, Object> checkForOpenDetail(
			AlarmEventDefineBean alarmEventDefineBean) {
		logger.info("打开详情页面前的判断...........start");
		List<AlarmPairwisePolicyBean> alarmPairwisePolicyLst = alarmPairwisePolicyMapper
				.getByCauseIDAndRecoverID2(alarmEventDefineBean
						.getAlarmDefineID());
		AlarmPairwisePolicyBean alarmPairwisePolicy = null; 
		if(alarmPairwisePolicyLst.size() > 0){
			alarmPairwisePolicy = alarmPairwisePolicyLst.get(0);
		}
		// logger.info(alarmPairwisePolicy.getCauseAlarmDefineID()+"===="+alarmPairwisePolicy.getRecoverAlarmDefineID());
		int index = 0;
		int alarmDefineID = -1;
		int recoverAlarmDefineID = -1;
		if (alarmPairwisePolicy == null) {
			index = 0;
			alarmDefineID = alarmEventDefineBean.getAlarmDefineID();
		} else if ((alarmPairwisePolicy.getCauseAlarmDefineID())
				.equals(alarmEventDefineBean.getAlarmDefineID())
				|| alarmPairwisePolicy.getCauseAlarmDefineID() == alarmEventDefineBean
						.getAlarmDefineID()) {
			alarmDefineID = alarmEventDefineBean.getAlarmDefineID();
			recoverAlarmDefineID = alarmPairwisePolicy
					.getRecoverAlarmDefineID();
			index = 0;
		} else if ((alarmPairwisePolicy.getRecoverAlarmDefineID())
				.equals(alarmEventDefineBean.getAlarmDefineID())
				|| alarmPairwisePolicy.getRecoverAlarmDefineID() == alarmEventDefineBean
						.getAlarmDefineID()) {
			alarmDefineID = alarmPairwisePolicy.getCauseAlarmDefineID();
			recoverAlarmDefineID = alarmEventDefineBean.getAlarmDefineID();
			index = 1;
		}
		logger.info("index=======" + index + ",alarmDefineID========="
				+ alarmDefineID + ",recoverAlarmDefineID======="
				+ recoverAlarmDefineID);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("index", index);
		result.put("alarmDefineID", alarmDefineID);
		result.put("recoverAlarmDefineID", recoverAlarmDefineID);
		return result;

	}

	/**
	 * 打开告警源页面
	 * 
	 * @return
	 */
	@RequestMapping("/toSelectMoRescource")
	@ResponseBody
	public ModelAndView toSelectMoRescource(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String dif = request.getParameter("dif");
		if (flag != null && !"".equals(flag)) {
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("flag", "doInit");
		}
		request.setAttribute("dif", dif);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/alarmMgr/mosource/moSource_list");
		return mv;
	}

	/**
	 * 告警事件名称唯一性的验证
	 */
	@RequestMapping("/checkAlarmName")
	@ResponseBody
	public boolean checkAlarmName(String alarmName) {
		logger.info("验证告警名称的唯一性......" + alarmName);
		int checkRs = alarmEventDefineMapper
				.getAlarmEventDefineByName(alarmName);
		if (checkRs > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping("/addAlarm")
	@ResponseBody
	@Transactional
	public boolean addAlarm(AlarmBean alarmBean) {
		logger.info("新增告警事件。。。。。。。。。");
		int causeDefineInsert;
		int recoverDefineInsert;
		int repeatInsert;
		int pairInsert;
		int causeDefineID = -1;
		String filterExpression = "";
		AlarmEventDefineBean causeAlarmEvent = alarmBean.getCauseAlarmEvent();
		if (null == causeAlarmEvent.getAlarmDefineID()
				|| "".equals(causeAlarmEvent.getAlarmDefineID())) {
			Map<String, Object> rs = addCauseAlarm(causeAlarmEvent);
			causeDefineID = Integer
					.parseInt(String.valueOf(rs.get("defineId")));
		} else {
			causeDefineID = causeAlarmEvent.getAlarmDefineID();
		}
		try {

			logger.info("新增清除事件");
			AlarmEventDefineBean recoverAlarmEvent = alarmBean
					.getRecoverAlamEvent();
			if (recoverAlarmEvent.getAlarmName() != null
					&& recoverAlarmEvent.getAlarmName() != "") {
				recoverAlarmEvent.setIsSystem(0);
				if (recoverAlarmEvent.getAlarmSourceMOID() == null
						|| "".equals(recoverAlarmEvent.getAlarmSourceMOID())) {
					recoverAlarmEvent.setAlarmSourceMOID(0);
				}
				recoverDefineInsert = alarmEventDefineMapper
						.insertAlarmEventDefine(recoverAlarmEvent);
				int recoverId = recoverAlarmEvent.getAlarmDefineID();
				logger.info("新增清除事件的结果：" + recoverDefineInsert + ",新增清除事件的id："
						+ recoverAlarmEvent.getAlarmDefineID());

				logger.info("新增成对事件。。。。。。。");
				AlarmPairwisePolicyBean alarmPairPolicy = alarmBean
						.getAlarmPairwisePolicy();
				alarmPairPolicy.setCauseAlarmDefineID(causeDefineID);
				alarmPairPolicy.setRecoverAlarmDefineID(recoverId);
				alarmPairPolicy.setmFlag(1);
				pairInsert = alarmPairwisePolicyMapper
						.insertAlarmPairPolicy(alarmPairPolicy);
				logger.info("新增成对事件的结果====" + pairInsert);
			}

			logger.info("新增重复策略。。");
			AlarmRepeatPolicyBean alarmReapeat = alarmBean.getAlarmRepeat();
			if (alarmReapeat.getTimeWindow() != null
					&& alarmReapeat.getUpgradeOnCount() != null
					&& alarmReapeat.getAlarmOnCount() != null) {
				alarmReapeat.setmFlag(1);
				alarmReapeat.setAlarmDefineID(causeDefineID);
				repeatInsert = alarmRepeatPolicyMapper
						.insertAlarmRepeatPolicy(alarmReapeat);
				logger.info("新增重复策略的结果：" + repeatInsert);
			}

			logger.info("新增过滤策略。。。。。");
			AlarmOriginalEventFilterBean alarmFilter = alarmBean
					.getAlarmFilter();
			List<AlarmOriginalEventFilterBean> filterList = alarmOriginalEventFilterMapper
					.getAlarmFilters(causeDefineID);
			String match = alarmFilter.getMatch();
			String action = alarmFilter.getAction();
			if (filterList != null && filterList.size() > 0) {
				AlarmOriginalEventFilterBean alarmMatchFilter = new AlarmOriginalEventFilterBean();
				alarmMatchFilter.setKeyWord("Match");
				alarmMatchFilter.setKeyOperator("等于");
				alarmMatchFilter.setKeyValue(match);
				alarmMatchFilter.setmFlag(1);
				alarmMatchFilter.setAlarmDefineID(causeDefineID);
				int matchRS = alarmOriginalEventFilterMapper
						.insertAlarmFilter(alarmMatchFilter);
				logger.info("match的新增结果========" + matchRS);

				AlarmOriginalEventFilterBean alarmActionFilter = new AlarmOriginalEventFilterBean();
				alarmActionFilter.setKeyWord("Action");
				alarmActionFilter.setKeyOperator("等于");
				alarmActionFilter.setKeyValue(action);
				alarmActionFilter.setmFlag(1);
				alarmActionFilter.setAlarmDefineID(causeDefineID);
				int actionRS = alarmOriginalEventFilterMapper
						.insertAlarmFilter(alarmActionFilter);
				logger.info("Action的新增结果========" + actionRS);

				String link = "";
				if (match.equals("all")) {
					link = " and ";
				} else {
					link = " or ";
				}
				for (int i = 0; i < filterList.size() - 1; i++) {
					String keyWord = filterList.get(i).getKeyWord();
					int key = Integer.parseInt(keyWord.substring(1, 2));
					int location = key - 1;
					String keyOperator = filterList.get(i).getKeyOperator();
					String keyValue = filterList.get(i).getKeyValue();
					String expression = "";
					if (keyOperator.equals("等于")) {
						expression = "#origin.vbs[" + location + "].value eq "
								+ keyValue;
					} else if (keyOperator.equals("不等于")) {
						expression = "#origin.vbs[" + location + "].value neq "
								+ keyValue;
					} else if (keyOperator.equals("包含")) {
						expression = "#origin.vbs[" + location
								+ "].value .indexOf('" + keyValue + "')>=0";
					} else {
						expression = "#origin.vbs[" + location
								+ "].value .indexOf('" + keyValue + "')<0";
					}
					filterExpression = filterExpression + expression + link;
				}
				int n = filterList.size() - 1;
				String lastKeyWord = filterList.get(n).getKeyWord();
				int lastKey = Integer.parseInt(lastKeyWord.substring(1, 2));
				int lastLocation = lastKey - 1;
				String lastKeyOperator = filterList.get(n).getKeyOperator();
				String lastKeyValue = filterList.get(n).getKeyValue();
				String s = "";
				if (lastKeyOperator.equals("等于")) {
					s = "#origin.vbs[" + lastLocation + "].value eq "
							+ lastKeyValue;
				} else if (lastKeyOperator.equals("不等于")) {
					s = "#origin.vbs[" + lastLocation + "].value neq "
							+ lastKeyValue;
				} else if (lastKeyOperator.equals("包含")) {
					s = "#origin.vbs[" + lastLocation + "].value .indexOf('"
							+ lastKeyValue + "')>=0";
				} else {
					s = "#origin.vbs[" + lastLocation + "].value .indexOf('"
							+ lastKeyValue + "')<0";
				}
				filterExpression += s;
				if (action.equals("0")) {
					filterExpression = "!(" + filterExpression + ")";
				}

				logger.info("新增trap任务.....");
				SysTrapTaskBean trapTask = new SysTrapTaskBean();
				trapTask.setFilterExpression(filterExpression);
				if (causeAlarmEvent.getAlarmSourceMOID() == null
						|| "".equals(causeAlarmEvent.getAlarmSourceMOID())
						|| causeAlarmEvent.getAlarmSourceMOID() == -1
						|| causeAlarmEvent.getAlarmSourceMOID() == 0) {
					trapTask.setServerIP("*");
				} else {
					trapTask.setServerIP(causeAlarmEvent.getDeviceIP());
				}
				trapTask.setProgressStatus(1);
				trapTask.setOperateStatus(1);
				trapTask.setCollectorID(-1);
				trapTask.setCreateTime(dateFormat.format(new Date()));
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
						.getRequestAttributes()).getRequest();
				SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
						.getSession().getAttribute("sysUserInfoBeanOfSession");
				trapTask.setCreator(sysUserInfoBeanTemp.getId());
				trapTask.setAlarmOID(causeAlarmEvent.getAlarmOID());
				String isOffline = alarmBean.getTrapTask().getIsOffline();
				if("1".equals(isOffline)){
					String collectorIds = alarmBean.getTrapTask().getCollectorIds();
					if(!"".equals(collectorIds) && collectorIds != null){
						String[] splits = collectorIds.split(",");
						logger.info("下发trap离线任务的采集机id为："+collectorIds);
						for (int i = 0; i < splits.length; i++) {
							int collectorId = Integer.parseInt(splits[i]);
							SysTrapTaskBean offlineTrapTask = trapTask;
							offlineTrapTask.setIsOffline(isOffline);
							offlineTrapTask.setCollectorID(collectorId);
							alarmOriginalEventFilterMapper.insertTrapTask(offlineTrapTask);
						}
					}
					trapTask.setIsOffline(isOffline);
				}else{
					int trapTaskInsert = alarmOriginalEventFilterMapper.insertTrapTask(trapTask);
					logger.info("新增trapTask的结果：" + trapTaskInsert + ",新增trapTask的id：" + trapTask.getTaskID());
				}

				logger.info("trap任务通知。。。");
				Dispatcher dispatch = Dispatcher.getInstance();
				ManageFacade facade = dispatch.getManageFacade();
				List<TaskDispatcherServer> servers = facade
						.listActiveServersOf(TaskDispatcherServer.class);
				if (servers.size() > 0) {
					String topic = "TaskDispatch";
					TaskDispatchNotification message = new TaskDispatchNotification();
					message.setDispatchTaskType(TaskType.TASK_TRAP);
					facade.sendNotification(servers.get(0).getIp(), topic, message);
				}

			} else {
				List<Integer> alarmDefineIds = new ArrayList<Integer>();
				alarmDefineIds.add(alarmFilter.getAlarmDefineID());
				alarmOriginalEventFilterMapper
						.delAlarmFilterByAlarmDefineID(alarmDefineIds);
			}

			logger.info("新增告警事件。。。。。");
			causeAlarmEvent.setAlarmDefineID(causeDefineID);
			if (causeAlarmEvent.getAlarmSourceMOID() == null
					|| "".equals(causeAlarmEvent.getAlarmSourceMOID())) {
				causeAlarmEvent.setAlarmSourceMOID(0);
			}
			causeAlarmEvent.setIsSystem(0);
			causeAlarmEvent.setFilterExpression(filterExpression);
			causeDefineInsert = alarmEventDefineMapper
					.updateAlarmEventDefine(causeAlarmEvent);
			logger.info("新增告警事件的结果：" + causeDefineInsert + ",新增告警事件的id："
					+ causeAlarmEvent.getAlarmDefineID());
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 打开更新页面
	 */
	@RequestMapping("toShowAlarmEventDefineModify")
	@ResponseBody
	public ModelAndView toShowAlarmEventDefineModify(HttpServletRequest request) {
		logger.info("打开编辑页面");
		String alarmDefineID = request.getParameter("alarmDefineID");
		String recoverAlarmDefineID = request
				.getParameter("recoverAlarmDefineID");
		String index = request.getParameter("index");
		request.setAttribute("alarmDefineID", alarmDefineID);
		request.setAttribute("recoverAlarmDefineID", recoverAlarmDefineID);
		request.setAttribute("index", index);
		return new ModelAndView(
				"monitor/alarmMgr/alarmeventdefine/alarmEventDefine_modify");
	}

	/**
	 * 告警事件名称唯一性的验证
	 */
	@RequestMapping("/checkNameBeforeUpdate")
	@ResponseBody
	public boolean checkNameBeforeUpdate(int alarmDefineID, String alarmName) {
		logger
				.info("验证告警名称的唯一性......" + alarmDefineID + "========"
						+ alarmName);
		AlarmEventDefineBean define = new AlarmEventDefineBean();
		define.setAlarmDefineID(alarmDefineID);
		define.setAlarmName(alarmName);
		int checkRs = alarmEventDefineMapper.getDefineByNameAndID(define);
		if (checkRs > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 更新告警事件
	 */
	@RequestMapping("/updateAlarm")
	@ResponseBody
	public boolean updateAlarm(AlarmBean alarmBean) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("更新告警事件......");
		int causeDefineUpdate;
		int recoverDefineUpdate;
		int repeatUpdate;
		int pairIUpdate;
		// 该告警事件之前是否有重复策略 ，0：没有 1：有
		String repeatFlag = request.getParameter("repeatFlag");
		try {
			AlarmEventDefineBean causeAlarmEvent = alarmBean
					.getCauseAlarmEvent();
			logger.info("更新清除事件");
			AlarmEventDefineBean recoverAlarmEvent = alarmBean
					.getRecoverAlamEvent();
			if (recoverAlarmEvent.getAlarmSourceMOID() == null
					|| "".equals(recoverAlarmEvent.getAlarmSourceMOID())) {
				recoverAlarmEvent.setAlarmSourceMOID(0);
			}
			recoverDefineUpdate = alarmEventDefineMapper
					.updateAlarmEventDefine(recoverAlarmEvent);
			logger.info("更新清除事件的结果：" + recoverDefineUpdate + ",新增清除事件的id："
					+ recoverAlarmEvent.getAlarmDefineID());

			logger.info("更新重复策略。。");
			AlarmRepeatPolicyBean alarmReapeat = alarmBean.getAlarmRepeat();
			boolean curRepeat = alarmReapeat.getTimeWindow() != null
					&& alarmReapeat.getUpgradeOnCount() != null
					&& alarmReapeat.getAlarmOnCount() != null;
			if ("1".equals(repeatFlag) && curRepeat == true) {
				alarmReapeat.setmFlag(2);
				repeatUpdate = alarmRepeatPolicyMapper
						.updateAlarmRepeatPolicy(alarmReapeat);
				logger.info("更新重复策略的结果：" + repeatUpdate);
			} else if ("1".equals(repeatFlag) && curRepeat == false) {
				logger.info("修改时去除重复策略，删除对应的重复策略");
				List<Integer> alarmDefineIds = new ArrayList<Integer>();
				alarmDefineIds.add(alarmReapeat.getAlarmDefineID());
				alarmRepeatPolicyMapper
						.delRepeatPolicyByAlarmDefineID(alarmDefineIds);
			} else if ("0".equals(repeatFlag) && curRepeat == true) {
				alarmReapeat.setmFlag(1);
				alarmReapeat.setAlarmDefineID(causeAlarmEvent
						.getAlarmDefineID());
				alarmRepeatPolicyMapper.insertAlarmRepeatPolicy(alarmReapeat);
			}

			logger.info("更新成对事件。。。。。。。");
			AlarmPairwisePolicyBean alarmPairPolicy = alarmBean
					.getAlarmPairwisePolicy();
			alarmPairPolicy.setmFlag(2);
			pairIUpdate = alarmPairwisePolicyMapper
					.updateAlarmPairPolicy(alarmPairPolicy);
			logger.info("更新成对事件的结果====" + pairIUpdate);

			logger.info("查询对应的Trap任务。。。");
			SysTrapTaskBean trapTask = alarmBean.getTrapTask();
			String trapalarmOID = trapTask.getAlarmOID();
			SysTrapTaskBean sysTrapTask = alarmOriginalEventFilterMapper
					.getTaskByMOIDAndStatus(trapalarmOID);

			logger.info("更新过滤策略。。。。。");
			AlarmOriginalEventFilterBean alarmFilter = alarmBean
					.getAlarmFilter();
			List<AlarmOriginalEventFilterBean> filterList = alarmOriginalEventFilterMapper
					.getAlarmFilters(alarmFilter.getAlarmDefineID());
			String filterExpression = "";
			String match = alarmFilter.getMatch();
			String action = alarmFilter.getAction();
			if (filterList != null && filterList.size() > 0) {
				AlarmOriginalEventFilterBean alarmMatchFilter = new AlarmOriginalEventFilterBean();
				alarmMatchFilter.setKeyWord("Match");
				alarmMatchFilter.setKeyOperator("等于");
				alarmMatchFilter.setKeyValue(match);
				alarmMatchFilter.setAlarmDefineID(alarmFilter
						.getAlarmDefineID());
				alarmMatchFilter.setmFlag(2);
				int matchRS = alarmOriginalEventFilterMapper
						.updateFilterByMatch(alarmMatchFilter);
				logger.info("match的更新结果========" + matchRS);
				if (matchRS == 0) {
					alarmOriginalEventFilterMapper
							.insertAlarmFilter(alarmMatchFilter);
				}

				AlarmOriginalEventFilterBean alarmActionFilter = new AlarmOriginalEventFilterBean();
				alarmActionFilter.setKeyWord("Action");
				alarmActionFilter.setKeyOperator("等于");
				alarmActionFilter.setKeyValue(action);
				alarmActionFilter.setAlarmDefineID(alarmFilter
						.getAlarmDefineID());
				alarmActionFilter.setmFlag(2);
				int actionRS = alarmOriginalEventFilterMapper
						.updateFilterByMatch(alarmActionFilter);
				logger.info("Action的更新结果========" + actionRS);
				if (actionRS == 0) {
					alarmOriginalEventFilterMapper
							.insertAlarmFilter(alarmActionFilter);
				}
				String link = "";
				if (match.equals("all")) {
					link = " and ";
				} else {
					link = " or ";
				}
				for (int i = 0; i < filterList.size() - 1; i++) {
					String keyWord = filterList.get(i).getKeyWord();
					int key = Integer.parseInt(keyWord.substring(1, 2));
					int location = key - 1;
					String keyOperator = filterList.get(i).getKeyOperator();
					String keyValue = filterList.get(i).getKeyValue();
					String expression = "";
					if (keyOperator.equals("等于")) {
						expression = "#origin.vbs[" + location + "].value eq "
								+ keyValue;
					} else if (keyOperator.equals("不等于")) {
						expression = "#origin.vbs[" + location + "].value neq "
								+ keyValue;
					} else if (keyOperator.equals("包含")) {
						expression = "#origin.vbs[" + location
								+ "].value .indexOf('" + keyValue + "')>=0";
					} else {
						expression = "#origin.vbs[" + location
								+ "].value .indexOf('" + keyValue + "')<0";
					}
					filterExpression = filterExpression + expression + link;
				}
				int n = filterList.size() - 1;
				String lastKeyWord = filterList.get(n).getKeyWord();
				int lastKey = Integer.parseInt(lastKeyWord.substring(1, 2));
				int lastLocation = lastKey - 1;

				String lastKeyOperator = filterList.get(n).getKeyOperator();
				String lastKeyValue = filterList.get(n).getKeyValue();
				String s = "";
				if (lastKeyOperator.equals("等于")) {
					s = "#origin.vbs[" + lastLocation + "].value eq "
							+ lastKeyValue;
				} else if (lastKeyOperator.equals("不等于")) {
					s = "#origin.vbs[" + lastLocation + "].value neq "
							+ lastKeyValue;
				} else if (lastKeyOperator.equals("包含")) {
					s = "#origin.vbs[" + lastLocation + "].value .indexOf('"
							+ lastKeyValue + "')>=0";
				} else {
					s = "#origin.vbs[" + lastLocation + "].value .indexOf('"
							+ lastKeyValue + "')<0";
				}
				filterExpression += s;
				if (action.equals("0")) {
					filterExpression = "!(" + filterExpression + ")";
				}

				logger.info("更新trap任务.....");
				int trapTaskUpdate = 0;

				// 之前没有trap过滤
				if (sysTrapTask == null) {
					SysTrapTaskBean bean = new SysTrapTaskBean();
					bean.setFilterExpression(filterExpression);
					if (causeAlarmEvent.getAlarmSourceMOID() == null
							|| "".equals(causeAlarmEvent.getAlarmSourceMOID())
							|| causeAlarmEvent.getAlarmSourceMOID() == -1
							|| causeAlarmEvent.getAlarmSourceMOID() == 0) {
						bean.setServerIP("*");
					} else {
						bean.setServerIP(causeAlarmEvent.getDeviceIP());
					}
					bean.setProgressStatus(1);
					bean.setOperateStatus(1);
					bean.setCollectorID(-1);
					bean.setCreateTime(dateFormat.format(new Date()));
					SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
							.getSession().getAttribute(
									"sysUserInfoBeanOfSession");
					bean.setCreator(sysUserInfoBeanTemp.getId());
					bean.setAlarmOID(causeAlarmEvent.getAlarmOID());
					trapTaskUpdate = alarmOriginalEventFilterMapper
							.insertTrapTask(bean);
				} else {
					sysTrapTask.setFilterExpression(filterExpression);
					if (causeAlarmEvent.getAlarmSourceMOID() == null
							|| "".equals(causeAlarmEvent.getAlarmSourceMOID())
							|| causeAlarmEvent.getAlarmSourceMOID() == -1
							|| causeAlarmEvent.getAlarmSourceMOID() == 0) {
						sysTrapTask.setServerIP("*");
					} else {
						sysTrapTask.setServerIP(causeAlarmEvent.getDeviceIP());
					}
					sysTrapTask.setProgressStatus(1);
					sysTrapTask.setOperateStatus(2);
					sysTrapTask.setCreateTime(dateFormat.format(new Date()));
					SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
							.getSession().getAttribute(
									"sysUserInfoBeanOfSession");
					sysTrapTask.setCreator(sysUserInfoBeanTemp.getId());
					sysTrapTask.setAlarmOID(causeAlarmEvent.getAlarmOID());
					trapTaskUpdate = alarmOriginalEventFilterMapper
							.updateSysTrapTask(sysTrapTask);
				}
				logger.info("更新trapTask的结果：" + trapTaskUpdate);

				String expression = causeAlarmEvent.getFilterExpression();
				String trapDeviceIP = causeAlarmEvent.getTrapDeviceIP();
				String deviceIP = causeAlarmEvent.getDeviceIP();

				if (filterExpression.equals(expression) == false) {
					// 如果过滤条件发生变化，发送通知
					logger.info("修改trap任务通知。。。");
					Dispatcher dispatch = Dispatcher.getInstance();
					ManageFacade facade = dispatch.getManageFacade();
					List<TaskDispatcherServer> servers = facade
							.listActiveServersOf(TaskDispatcherServer.class);
					if (servers.size() > 0) {
						String topic = "TaskDispatch";
						TaskDispatchNotification message = new TaskDispatchNotification();
						message.setDispatchTaskType(TaskType.TASK_TRAP);
						facade.sendNotification(servers.get(0).getIp(), topic,
								message);
					}
				} else if (!"".equals(expression) && expression != null) {
					// 告警源变化，发送通知
					if (!deviceIP.equals(trapDeviceIP)) {
						logger.info("告警源变化修改trap任务通知。。。");
						Dispatcher dispatch = Dispatcher.getInstance();
						ManageFacade facade = dispatch.getManageFacade();
						List<TaskDispatcherServer> servers = facade
								.listActiveServersOf(TaskDispatcherServer.class);
						if (servers.size() > 0) {
							String topic = "TaskDispatch";
							TaskDispatchNotification message = new TaskDispatchNotification();
							message.setDispatchTaskType(TaskType.TASK_TRAP);
							facade.sendNotification(servers.get(0).getIp(),
									topic, message);
						}
					}
				}

			} else {
				String expression = causeAlarmEvent.getFilterExpression();
				List<Integer> alarmDefineIds = new ArrayList<Integer>();
				alarmDefineIds.add(alarmFilter.getAlarmDefineID());
				alarmOriginalEventFilterMapper
						.delAlarmFilterByAlarmDefineID(alarmDefineIds);

				if (!"".equals(expression) && expression != null) {
					logger.info("删除trap任务");
					int taskID = sysTrapTask.getTaskID();
					sysTrapTask.setOperateStatus(3);
					sysTrapTask.setProgressStatus(1);
					int delTask = alarmOriginalEventFilterMapper
							.updateSysTrapTask(sysTrapTask);
					logger.info("删除trap任务的结果====" + delTask);
					// 如果过滤条件删除，发送通知
					logger.info("删除trap任务通知。。。");
					Dispatcher dispatch = Dispatcher.getInstance();
					ManageFacade facade = dispatch.getManageFacade();
					List<TaskDispatcherServer> servers = facade
							.listActiveServersOf(TaskDispatcherServer.class);
					if (servers.size() > 0) {
						String topic = "TaskDispatch";
						TaskDispatchNotification message = new TaskDispatchNotification();
						message.setDispatchTaskType(TaskType.TASK_TRAP);
						facade.sendNotification(servers.get(0).getIp(), topic,
								message);
					}
				}

			}
			causeAlarmEvent.setFilterExpression(filterExpression);
			if (causeAlarmEvent.getAlarmSourceMOID() == null
					|| "".equals(causeAlarmEvent.getAlarmSourceMOID())) {
				causeAlarmEvent.setAlarmSourceMOID(0);
			}
			causeDefineUpdate = alarmEventDefineMapper
					.updateAlarmEventDefine(causeAlarmEvent);
			logger.info("更新告警事件的结果：" + causeDefineUpdate + ",更新告警事件的id："
					+ causeAlarmEvent.getAlarmDefineID());
			return true;
		} catch (Exception e) {
			logger.error("数据库操作异常：" + e.getMessage());
			return false;
		}
	}

	@RequestMapping("/getAlarmDefineID")
	@ResponseBody
	public Map<String, Object> getAlarmDefineID() {
		logger.info("新增前创建空告警事件。。。");
		boolean flag = false;
		AlarmEventDefineBean alarmEvent = new AlarmEventDefineBean();
		alarmEvent.setAlarmName("");
		alarmEvent.setAlarmTitle("");
		alarmEvent.setAlarmOID("");
		alarmEvent.setAlarmSourceMOID(0);
		alarmEvent.setIsSystem(0);
		int insertCount = alarmEventDefineMapper
				.insertAlarmEventDefine(alarmEvent);
		int alarmDefineID = alarmEvent.getAlarmDefineID();
		logger.info("告警事件的id为=====" + alarmDefineID);
		if (insertCount > 0) {
			flag = true;
		} else {
			flag = false;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("flag", flag);
		result.put("alarmDefineID", alarmDefineID);
		return result;
	}

	@RequestMapping("/checkBeforeCancle")
	@ResponseBody
	public Map<String, Object> checkBeforeCancle() {
		boolean flag = false;
		String alarmDefineIDs = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Integer> ids = alarmOriginalEventFilterMapper.getAlarmDefineIDs();
		for (int i = 0; i < ids.size(); i++) {
			AlarmOriginalEventFilterBean bean = new AlarmOriginalEventFilterBean();
			bean.setAlarmDefineID(ids.get(i));
			bean.setKeyWord("Match");
			int count = alarmOriginalEventFilterMapper.getByKeyWord(bean);
			if (count <= 0) {
				alarmDefineIDs += ids.get(i) + ",";
			}
		}
		if (alarmDefineIDs.length() > 0) {
			flag = false;
			alarmDefineIDs = alarmDefineIDs.substring(0, alarmDefineIDs
					.lastIndexOf(","));
		} else {
			flag = true;
		}
		result.put("alarmDefineIDs", alarmDefineIDs);
		result.put("flag", flag);
		return result;
	}

	@RequestMapping("/toCancelAdd")
	@ResponseBody
	public boolean toCancelAdd(HttpServletRequest request) {
		String alarmDefineIDs = request.getParameter("alarmDefineIDs");
		String[] splitIds = alarmDefineIDs.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (String strId : splitIds) {
			idList.add(Integer.parseInt(strId));
		}
		try {
			alarmOriginalEventFilterMapper
					.delAlarmFilterByAlarmDefineID(idList);
			alarmEventDefineMapper.delAlarmEventDefine(idList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RequestMapping("/addCauseAlarm")
	@ResponseBody
	public Map<String, Object> addCauseAlarm(AlarmEventDefineBean bean) {
		logger.info("同步新增告警定义事件");
		Map<String, Object> result = new HashMap<String, Object>();
		if (bean.getAlarmName() != null && bean.getAlarmName() != "") {
			if (bean.getAlarmSourceMOID() == null
					|| "".equals(bean.getAlarmSourceMOID())) {
				bean.setAlarmSourceMOID(0);
			}
			bean.setIsSystem(0);
			int causeInsert = alarmEventDefineMapper
					.insertAlarmEventDefine(bean);
			int defineId = bean.getAlarmDefineID();
			logger.info("同步新增告警定义的结果：" + causeInsert + ",新增清除事件的id："
					+ bean.getAlarmDefineID());
			result.put("defineId", defineId);
		}
		return result;
	}

	/**
	 * 验证告警标识唯一性（更新）
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/checkAlarmOIDForUpdate")
	@ResponseBody
	public boolean checkAlarmOIDForUpdate(AlarmEventDefineBean bean) {
		logger.info("验证告警标识（更新）");
		int count = alarmEventDefineMapper.getByIDAndAlarmOID(bean);
		logger.info("验证告警标识（更新）结果======" + count);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 验证告警标识唯一性（新增）
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/checkAlarmOID")
	@ResponseBody
	public boolean checkAlarmOID(AlarmEventDefineBean bean) {
		logger.info("验证告警标识（新增）");
		int count = alarmEventDefineMapper.getByAlarmOID(bean);
		logger.info("验证告警标识（新增）结果======" + count);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 取消更新时的操作
	 * 
	 * @return
	 */
	@RequestMapping("/toCancleUpdate")
	@ResponseBody
	public boolean toCancleUpdate(HttpServletRequest request) {
		boolean flag = false;
		String defineId = request.getParameter("alarmDefineID");
		String filterLstJson = request.getParameter("filterLst");
		String currentFilterJson = request.getParameter("currentFilter");
		int alarmDefineID = Integer.parseInt(defineId);

		if (("[]".equals(filterLstJson) && "[]".equals(currentFilterJson))
				|| (!"[]".equals(filterLstJson)
						&& !"[]".equals(currentFilterJson) && filterLstJson
						.equals(currentFilterJson))) {
			flag = true;
		} else {
			// 删除更新后的过滤策略
			List<Integer> alarmDefineIds = new ArrayList<Integer>();
			alarmDefineIds.add(alarmDefineID);
			try {
				alarmOriginalEventFilterMapper
						.delAlarmFilterByAlarmDefineID(alarmDefineIds);
				if (!"[]".equals(filterLstJson)) {
					// 插入更新前的过滤策略
					List<AlarmOriginalEventFilterBean> filterList = JSON
							.parseArray(filterLstJson,
									AlarmOriginalEventFilterBean.class);
					for (int i = 0; i < filterList.size(); i++) {
						AlarmOriginalEventFilterBean bean = filterList.get(i);
						bean.setmFlag(1);
						alarmOriginalEventFilterMapper.insertAlarmFilter(bean);
					}
				}
				flag = true;
			} catch (Exception e) {
				logger.error("取消更新异常：" + e.getMessage());
				flag = false;
				return false;
			}
		}
		return flag;
	}
	
	/**
	 * 获得trap离线采集机
	 */
	@RequestMapping("/listOfflineTrapCollector")
	@ResponseBody
	public Map<String, Object> listOfflineTrapCollector(){
		String processName = "TrapServer";
		List<SysServerHostInfo> hostList = harvesterMapper.getAllServerHost(processName);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", hostList);
		return result;
	}
	
	/**
	 * 获得已经选择的trap离线采集机
	 */
	@RequestMapping("/listSelectedCollector")
	@ResponseBody
	public Map<String, Object> listSelectedCollector(HttpServletRequest request){
		int alarmDefineID = Integer.parseInt(request.getParameter("alarmDefineID"));
		List<SysTrapTaskBean> taskList = trapTaskMapper.getByAlarmDefineID(alarmDefineID);
		List<SysServerHostInfo> hostList = new ArrayList<SysServerHostInfo>();
		String isOffline = "0";
		if(taskList.size() > 0){
			if("1".equals(taskList.get(0).getIsOffline())){
				isOffline = "1";
				hostList = harvesterMapper.getOfflineTrapServer(alarmDefineID);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", hostList);
		result.put("isOffline", isOffline);
		return result;
	}
}
