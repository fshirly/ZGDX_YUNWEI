package com.fable.insightview.monitor.alarmmgr.alarmactive.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.alarmdispatcher.core.SyncAlarmOperate;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmhistory.mapper.AlarmHistoryMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmHistoryInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmStatusInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.helper.RestHepler;
import com.fable.insightview.platform.common.util.DateUtil;
import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.service.ISysUserService;
import com.fable.insightview.platform.sysconf.service.SysConfigService;
import com.fable.insightview.platform.sysinit.SystemParamInit;

/**
 * @Description: 活动告警控制器
 * @author zhengxh
 * @Date 2014-7-17
 */
@Controller
@RequestMapping("/monitor/alarmActive")
public class AlarmActiveController {

	@Autowired
	private AlarmActiveMapper alarmActiveMapper;

	@Autowired
	private AlarmHistoryMapper alarmHistoryMapper;

	@Autowired
	ISysUserService sysUserService;

	@Autowired
	private SysConfigService sysConfigServiceImpl;

	private final static Logger logger = LoggerFactory.getLogger(AlarmActiveController.class);

	private int emergency = 0;
	private int severity = 0;
	private int ordinary = 0;
	private int prompt = 0;
	private int uncertain = 0;

	/**
	 * 菜单页面跳转
	 */
	@RequestMapping("/toAlarmActiveList")
	public ModelAndView toAlarmActiveList(ModelMap map, HttpServletRequest request, String navigationBar)
			throws Exception {
		// 获取告警级别下拉框数据
		List<AlarmLevelInfo> levelList = alarmActiveMapper.queryLevelInfo();
		// 获取告警类型下拉框数据
		List<AlarmTypeInfo> typeList = alarmActiveMapper.queryTypeInfo();
		// 获取告警状态下拉框数据
		List<AlarmStatusInfo> statusList = alarmActiveMapper.queryStatusInfo();

		List<AlarmStatusInfo> activeStatusList = new ArrayList<AlarmStatusInfo>();
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).getStatusID() != 24) {
				activeStatusList.add(statusList.get(i));
			}
		}

		String filtername = request.getParameter("filtername");
		String id = request.getParameter("id");

		SecurityUserInfoBean user = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		map.put("userId", user.getId());
		map.put("username", user.getUserName());

		map.put("levelList", levelList);
		map.put("typeList", typeList);
		map.put("statusList", activeStatusList);
		map.put("moclassID", request.getParameter("moClassID"));
		map.put("neManufacturerID", request.getParameter("neManufacturerID"));
		map.put("filtername", filtername);
		map.put("filterId", id);

		map.put("host", request.getParameter("host"));
		map.put("tzResType",request.getParameter("tzResType"));
		map.put("tzType",request.getParameter("tzType"));
		map.put("tzDeviceIP",request.getParameter("tzDeviceIP"));
		map.put("tzTimeBegin",request.getParameter("tzTimeBegin"));
		map.put("tzTimeEnd",request.getParameter("tzTimeEnd"));
		map.put("navigationBar", navigationBar);
		if (null != request.getParameter("moid")) {
			map.put("moid", request.getParameter("moid"));
		} else {
			map.put("moid", "-1");
		}
		// 是否启用新告警派单
		String workOrderVersion = this.sysConfigServiceImpl.getConfParamValue(
				SystemFinalValue.SYS_CONFIG_TYPE_PROCESS_WOA, SystemFinalValue.SCT_PROC_WOA_CITY_WORKORDER_FLAG);
		if (StringUtils.isNotEmpty(workOrderVersion)) {
			map.put("alarmOrderVersion", workOrderVersion);
		}
		map.put("transferTimeStr", DateUtil.date2String(new Date()));

		return new ModelAndView("monitor/alarmMgr/alarmactive/alarmActive_list");
	}

	/**
	 * 获取页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listAlarmActive")
	@ResponseBody
	public Map<String, Object> listAlarmActive(AlarmNode vo, HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmNode> page = new Page<AlarmNode>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("timeBegin", vo.getTimeBegin());
		paramMap.put("timeEnd", vo.getTimeEnd());
		paramMap.put("moName", vo.getMoName());
		paramMap.put("alarmLevel", vo.getAlarmLevel());
		paramMap.put("alarmTitle", vo.getAlarmTitle());
		paramMap.put("alarmStatus", vo.getAlarmStatus());
		// TODO 操作
		paramMap.put("alarmOperateStatus", vo.getAlarmOperateStatus());
		paramMap.put("sourceMOIPAddress", vo.getSourceMOIPAddress());
		paramMap.put("alarmType", vo.getAlarmType());
		paramMap.put("dispatchUser", vo.getDispatchUser());
		paramMap.put("dispatchTime", vo.getDispatchTime());

		if (!"-1".equals(request.getParameter("moid"))) {
			paramMap.put("moId", request.getParameter("moid"));
		}

		if (vo.getMoclassID() != 0) {
			paramMap.put("moclassID", vo.getMoclassID());
			String alarmSourceSubSql = "";	
			if (vo.getMoclassID() == 6) {
				alarmSourceSubSql = "and t.SourceMOClassID in (59,60)";
			} else {
				alarmSourceSubSql = "and t.SourceMOClassID in (" + vo.getMoclassID() + ")";
			}
			paramMap.put("alarmSourceSubSql", alarmSourceSubSql);
		} else {
			paramMap.put("moclassID", "");
		}
		paramMap.put("neManufacturerID", request.getParameter("neManufacturerID"));

		page.setParams(paramMap);

		// 设置查询参数

		// 查询分页数据
		List<AlarmNode> list = alarmActiveMapper.queryList(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");

		// AlarmNode a = new AlarmNode();
		// a.setAlarmLevel(1);
		// System.out.println(AlarmDispatchFilterUtils.isDispatch(a));

		return result;
	}

	/**
	 * 从设备快照中入口，跳转到告警列表
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryAlarmActivelist")
	@ResponseBody
	public Map<String, Object> queryAlarmActivelist(AlarmNode vo, HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmNode> page = new Page<AlarmNode>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("timeBegin", vo.getTimeBegin());
		paramMap.put("timeEnd", vo.getTimeEnd());
		paramMap.put("moName", vo.getMoName());
		paramMap.put("alarmLevel", vo.getAlarmLevel());
		paramMap.put("alarmTitle", vo.getAlarmTitle());
		paramMap.put("alarmStatus", vo.getAlarmStatus());
		// TODO 操作

		paramMap.put("alarmOperateStatus", vo.getAlarmOperateStatus());
		paramMap.put("alarmType", vo.getAlarmType());
		paramMap.put("dispatchUser", vo.getDispatchUser());
		paramMap.put("dispatchTime", vo.getDispatchTime());

		if (!"-1".equals(request.getParameter("moid"))) {
			paramMap.put("moId", request.getParameter("moid"));
		}
		String neManufacturerID = request.getParameter("neManufacturerID");
		if (vo.getMoclassID() != 0) {
			paramMap.put("moclassID", vo.getMoclassID());
			String alarmSourceSubSql = "";
			if (vo.getMoclassID() == 6) {
				if (neManufacturerID != null && !"".equals(neManufacturerID)) {
					alarmSourceSubSql = "NeManufacturerID=" + request.getParameter("neManufacturerID")
							+ " and MOClassID in(59,60,6)";
				} else {
					alarmSourceSubSql = "MOClassID in(59,60,6)";
				}
			} else {
				alarmSourceSubSql = "MOClassID = " + vo.getMoclassID() + "";
			}
			paramMap.put("alarmSourceSubSql", alarmSourceSubSql);
		} else {
			paramMap.put("moclassID", "");
		}
		paramMap.put("neManufacturerID", neManufacturerID);

		page.setParams(paramMap);

		// 设置查询参数

		// 查询分页数据
		List<AlarmNode> list = alarmActiveMapper.queryAlarmList(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}

	/**
	 * 告警确认页面跳转
	 */
	@RequestMapping("/toAlarmActiveConfirm")
	public ModelAndView toAlarmActiveConfirm(ModelMap map, AlarmNode vo) throws Exception {
		logger.info("告警确认id:" + vo.getAlarmID());
		vo = alarmActiveMapper.getInfoById(vo.getAlarmID());

		map.put("alarmVo", vo);
		return new ModelAndView("monitor/alarmMgr/alarmactive/alarmActive_confirm");
	}

	/**
	 * 处理确认告警
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/doAlarmActiveConfirm")
	@ResponseBody
	public boolean doAlarmActiveConfirm(AlarmNode vo, HttpServletRequest request) {
		logger.info("开始...处理确认告警");
		logger.debug("update by param AlarmID={}", vo.getAlarmID());
		// 获取当前用户对象
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		vo.setConfirmTime(new Timestamp(new Date().getTime()));
		vo.setConfirmer(userVo.getId() + "");

		try {
			alarmActiveMapper.doAlarmActiveConfirm(vo);
			SyncAlarmOperate.getInstance().sendAlarmMessage(vo.getAlarmID() + "");// 传送告警消息
		} catch (Exception e) {
			logger.error("告警确认异常：" + e.getMessage(), vo);
			return false;
		}

		logger.info("结束...处理确认告警");
		return true;
	}

	/**
	 * 取消确认告警
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/cancelAlarmActiveConfirm")
	@ResponseBody
	public boolean cancelAlarmActiveConfirm(AlarmNode vo, HttpServletRequest request) {
		logger.info("开始...取消确认告警");
		logger.debug("update by param AlarmID={}", vo.getAlarmID());

		try {
			alarmActiveMapper.cancelAlarmActiveConfirm(vo.getAlarmID());
			SyncAlarmOperate.getInstance().sendAlarmMessage(vo.getAlarmID() + "");// 传送告警消息
		} catch (Exception e) {
			logger.error("取消确认告警异常：" + e.getMessage(), vo);
			return false;
		}

		logger.info("结束...取消确认告警");
		return true;
	}

	/**
	 * 告警清除页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toClearAlarmActive")
	public ModelAndView toClearAlarmActive(ModelMap map, AlarmNode vo) throws Exception {
		logger.info("告警清除id:" + vo.getAlarmID());
		vo = alarmActiveMapper.getInfoById(vo.getAlarmID());

		map.put("alarmVo", vo);
		return new ModelAndView("monitor/alarmMgr/alarmactive/alarmActive_clear");
	}

	/**
	 * 处理告警清除
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/doClearAlarmActive")
	@ResponseBody
	public boolean doClearAlarmActive(AlarmNode vo, HttpServletRequest request) {
		logger.info("开始...告警清除");
		logger.debug("update by param AlarmID={}", vo.getAlarmID());
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");

		// 查询活动告警表中是否存在告警信息
		String newId = alarmActiveMapper.queryAlarmIdsByIds(vo.getAlarmID() + "");
		if (newId != "" && newId != null) {
			/* 告警清除后,将该告警信息插入历史告警表中 */
			try {
				clearAlarmInfo(vo, userVo, newId);
			} catch (Exception e) {
				logger.error("历史告警插入异常：" + e.getMessage(), newId);
				return false;
			}
		}
		logger.info("结束...告警清除");
		return true;
	}

	/**
	 * 告警清除后,将该告警信息插入历史告警表中
	 * 
	 * @param vo
	 * @param userVo
	 * @param id
	 */
	private void clearAlarmInfo(AlarmNode vo, SecurityUserInfoBean userVo, String id) {
		AlarmHistoryInfo historyVo = new AlarmHistoryInfo();
		historyVo.setMoName(id);
		historyVo.setCleanInfo(vo.getCleanInfo());
		historyVo.setCleanTime(new Timestamp(new Date().getTime()));// 当时时间
		historyVo.setCleaner(userVo.getUsername());// 当前用户
		// historyVo.setAlarmStatus(5);// 表示人工清除
		historyVo.setAlarmOperateStatus(24);// 表示人工清除
		alarmHistoryMapper.copyActiveClearInfo(historyVo);// 插入历史告警表
		alarmActiveMapper.deleteAlarmActive(id);// 删除活动告警表
		SyncAlarmOperate.getInstance().sendAlarmMessage(id);// 传送告警消息
	}

	/**
	 * 删除告警
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteAlarmActive")
	@ResponseBody
	public boolean deleteAlarmActive(AlarmNode vo, HttpServletRequest request) {
		logger.info("开始...删除告警");
		logger.debug("delete by param AlarmID={}", vo.getAlarmID());
		/* 告警删除前,将该告警信息插入历史告警表中 */
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");

		String newId = alarmActiveMapper.queryAlarmIdsByIds(vo.getAlarmID() + "");

		if (newId != "" && newId != null) {
			AlarmHistoryInfo historyVo = new AlarmHistoryInfo();
			historyVo.setMoName(vo.getAlarmID() + "");
			// historyVo.setAlarmStatus(24);// 表示人工删除
			historyVo.setAlarmOperateStatus(24);// 表示人工删除
			historyVo.setDeleteTime(new Timestamp(new Date().getTime()));
			historyVo.setDeletedUser(userVo.getUsername());

			try {
				alarmHistoryMapper.copyActiveDeleteInfo(historyVo);// 插入历史告警表
				alarmActiveMapper.deleteAlarmActive(vo.getAlarmID() + "");// 删除活动告警表
				SyncAlarmOperate.getInstance().sendAlarmMessage(vo.getAlarmID() + "");// 传送告警消息
			} catch (Exception e) {
				logger.error("历史告警插入异常：" + e.getMessage(), historyVo);
				return false;
			}
		}

		logger.info("结束...删除告警");
		return true;
	}

	/**
	 * 告警详情页面跳转
	 */
	@RequestMapping("/toAlarmActiveDetail")
	public ModelAndView toAlarmActiveView(ModelMap map, AlarmNode vo, HttpServletRequest request) throws Exception {
		logger.info("告警详情id:" + vo.getAlarmID());
		vo = alarmActiveMapper.getInfoById(vo.getAlarmID());

		map.put("alarmVo", vo);
		map.put("flag", request.getParameter("flag"));
		// 是否启用新告警派单
		String workOrderVersion = this.sysConfigServiceImpl.getConfParamValue(
				SystemFinalValue.SYS_CONFIG_TYPE_PROCESS_WOA, SystemFinalValue.SCT_PROC_WOA_CITY_WORKORDER_FLAG);
		if (StringUtils.isNotEmpty(workOrderVersion)) {
			map.put("alarmOrderVersion", workOrderVersion);
		}
		map.put("transferTimeStr", DateUtil.date2String(new Date()));
		//查询单子的当前处理人，调用接口
		String url = SystemParamInit.getValueByKey("rest.bpmConsole.machingAlarm")+"/rest/itsm/alarm/findAlarmNowPeopleInProcess/"+vo.getAlarmID();
		String processMan = RestHepler.exchange(url, "", new Object());
		map.put("processMan", processMan);
		return new ModelAndView("monitor/alarmMgr/alarmactive/alarmActive_detail");
	}

	/**
	 * 根据用户ID查询用户名
	 * 
	 * @param userId
	 * @return
	 */
	public String queryUserNameById(String userId) {
		SysUserInfoBean bean = sysUserService.findSysUserById(Integer.parseInt(userId));

		if (null == bean) {
			return "";
		}
		return bean.getUserName();
	}

	/**
	 * 批量告警确认页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toBathAlarmActiveConfirm")
	public ModelAndView toBathAlarmActiveConfirm(@RequestParam("id") String id, ModelMap map,
			HttpServletRequest request) throws Exception {
		logger.info("告警确认id:" + id);
		map.put("id", id);
		String flag = request.getParameter("flag");
		map.put("flag", flag);
		return new ModelAndView("monitor/alarmMgr/alarmactive/alarmActive_bathConfirm");
	}

	/**
	 * 首页入口告警确认页面
	 * 
	 * @param id
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAlarmConfirm")
	public ModelAndView toAlarmConfirm(@RequestParam("alarmID") String id, ModelMap map, HttpServletRequest request)
			throws Exception {
		logger.info("告警确认id:" + id);
		map.put("id", id);
		String flag = request.getParameter("flag");
		map.put("flag", flag);
		return new ModelAndView("monitor/alarmMgr/alarmactive/alarmConfirm");
	}

	/**
	 * 首页入口告警清除页面跳转
	 * 
	 * @param id
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toClearAlarm")
	public ModelAndView toClearAlarm(@RequestParam("id") String id, ModelMap map, HttpServletRequest request)
			throws Exception {
		logger.info("告警清除id:" + id);
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		map.put("id", id);
		map.put("flag", request.getParameter("flag"));
		map.put("dispIDs", request.getParameter("dispIDs"));
		map.put("userName", userVo.getUserAccount());
		return new ModelAndView("monitor/alarmMgr/alarmactive/alarmClear");
	}

	/**
	 * 批量处理确认告警
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/doBathAlarmActiveConfirm")
	@ResponseBody
	public boolean doBathAlarmActiveConfirm(AlarmNode vo, HttpServletRequest request) {
		logger.info("开始...批量处理确认告警");
		// 获取当前用户对象
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		vo.setConfirmTime(new Timestamp(new Date().getTime()));
		vo.setConfirmer(userVo.getUserName());
		String id = request.getParameter("id");

		try {
			if (id != "" && id != null) {
				vo.setMoName("(" + id + ")");
				alarmActiveMapper.bathAlarmActiveConfirm(vo);
				SyncAlarmOperate.getInstance().sendAlarmMessage(id);// 传送告警消息
			}
		} catch (Exception e) {
			logger.error("告警确认异常：" + e.getMessage(), vo);
			return false;
		}

		logger.info("结束...批量处理确认告警");
		return true;
	}

	/**
	 * 首页入口告警确认
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/doAlarmConfirm")
	@ResponseBody
	public boolean doAlarmConfirm(AlarmNode vo, HttpServletRequest request) {
		logger.info("开始...批量处理确认告警");
		// 获取当前用户对象
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		vo.setConfirmTime(new Timestamp(new Date().getTime()));
		vo.setConfirmer(userVo.getUserName());
		String id = request.getParameter("id");

		try {
			if (id != "" && id != null) {
				vo.setMoName("(" + id + ")");
				alarmActiveMapper.bathAlarmActiveConfirm(vo);
				SyncAlarmOperate.getInstance().sendAlarmMessage(id);// 传送告警消息
			}
		} catch (Exception e) {
			logger.error("告警确认异常：" + e.getMessage(), vo);
			return false;
		}

		logger.info("结束...批量处理确认告警");
		return true;
	}

	/**
	 * 批量取消确认告警
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/bathCancelAlarmActiveConfirm")
	@ResponseBody
	public boolean bathCancelAlarmActiveConfirm(@RequestParam("id") String id) {
		logger.info("开始...取消确认告警");

		try {
			if (id != "" && id != null) {
				alarmActiveMapper.bathCancelActiveConfirm(id);
				SyncAlarmOperate.getInstance().sendAlarmMessage(id);// 传送告警消息
			}
		} catch (Exception e) {
			logger.error("取消确认告警异常：" + e.getMessage(), id);
			return false;
		}

		logger.info("结束...取消确认告警");
		return true;
	}

	/**
	 * 批量告警清除页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toBathClearAlarmActive")
	public ModelAndView toBathClearAlarmActive(@RequestParam("id") String id, ModelMap map, HttpServletRequest request)
			throws Exception {
		logger.info("告警清除id:" + id);
		map.put("id", id);
		map.put("flag", request.getParameter("flag"));
		map.put("dispIds", request.getParameter("dispIds"));
		return new ModelAndView("monitor/alarmMgr/alarmactive/alarmActive_bathClear");
	}

	/**
	 * 批量处理告警清除
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/doBathClearAlarmActive")
	@ResponseBody
	public synchronized boolean doBathClearAlarmActive(AlarmNode vo, HttpServletRequest request) {
		logger.info("开始...告警清除");
		// 获取当前用户对象
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");

		String id = request.getParameter("id");
		// 查询活动告警表中是否存在告警信息
		String newId = alarmActiveMapper.queryAlarmIdsByIds(id);
		if (newId != "" && newId != null) {
			try {
				clearAlarmInfo(vo, userVo, newId);
				/*
				 * // 往历史表插入的共同部分 AlarmHistoryInfo historyVo = new
				 * AlarmHistoryInfo();
				 * historyVo.setCleanInfo(vo.getCleanInfo());
				 * historyVo.setCleanTime(new Timestamp(new
				 * Date().getTime()));// 当时时间
				 * historyVo.setCleaner(userVo.getUserName());// 当前用户
				 * //historyVo.setAlarmStatus(24);// 表示人工清除
				 * historyVo.setAlarmOperateStatus(24);// 表示人工清除
				 * historyVo.setMoName(newId);
				 * alarmHistoryMapper.copyActiveClearInfo(historyVo);// 插入历史告警表
				 * alarmActiveMapper.deleteAlarmActive(newId);// 删除活动告警表
				 * SyncAlarmOperate.getInstance().sendAlarmMessage(newId);//
				 * 传送告警消息
				 */ } catch (Exception e) {
				logger.error("历史告警插入异常：" + e.getMessage(), newId);
				return false;
			}
		}
		logger.info("结束...告警清除");
		return true;
	}

	private String invokeRestInterface(String url, String path, Map<String, String> data) {
		try {
			// url = "http://192.168.16.120:8080/insightview-bpmconsole-war";
			logger.info("Request parameter url = " + url);

			String username = SystemParamInit.getValueByKey("rest.username");
			String password = SystemParamInit.getValueByKey("rest.password");
			// 拼接获取单板接口URL
			StringBuffer basePath = new StringBuffer();
			basePath.append(url);
			basePath.append(path);
			// 设置请求头信息
			HttpHeaders requestHeaders = RestHepler.createHeaders(username, password);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(data, requestHeaders);
			RestTemplate rest = new RestTemplate();
			// 参数1：请求地址 ，参数2：请求方式，参数3：请求头信息，参数4：相应类
			ResponseEntity<String> rssResponse = rest.exchange(basePath.toString(), HttpMethod.POST, requestEntity,
					String.class);

			if (null != rssResponse) {
				logger.info(rssResponse.getBody());
			}
			return rssResponse.getBody();
		} catch (Exception e) {
			logger.error("调用接口同步,有错误!");
		}
		return "";
	}

	/**
	 * 批量删除告警
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/bathDeleteAlarmActive")
	@ResponseBody
	public boolean bathDeleteAlarmActive(AlarmNode vo, HttpServletRequest request) {
		logger.info("开始...删除告警");
		/* 告警删除前,将该告警信息插入历史告警表中 */
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");

		String id = request.getParameter("id");
		// 查询活动告警表中是否存在告警信息
		String newId = alarmActiveMapper.queryAlarmIdsByIds(id);

		if (newId != "" && newId != null) {
			try {
				// 往历史表插入的共同部分
				AlarmHistoryInfo historyVo = new AlarmHistoryInfo();
				// historyVo.setAlarmStatus(24);// 表示人工删除
				historyVo.setAlarmOperateStatus(24);// 表示人工删除
				historyVo.setDeleteTime(new Timestamp(new Date().getTime()));
				historyVo.setDeletedUser(userVo.getUsername());
				historyVo.setMoName(id);
				alarmHistoryMapper.copyActiveDeleteInfo(historyVo);// 插入历史告警表
				alarmActiveMapper.deleteAlarmActive(id);// 删除活动告警表
				SyncAlarmOperate.getInstance().sendAlarmMessage(id);// 传送告警消息
			} catch (Exception e) {
				logger.error("历史告警插入异常：" + e.getMessage(), newId);
				return false;
			}
		}

		logger.info("结束...删除告警");
		return true;
	}

	/**
	 * 3d机房－活动告警页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toRoomAlarmActiveList")
	public ModelAndView toRoomAlarmActiveList(ModelMap map, HttpServletRequest request) throws Exception {
		// 获取告警级别下拉框数据
		List<AlarmLevelInfo> levelList = alarmActiveMapper.queryLevelInfo();
		// 获取告警类型下拉框数据
		List<AlarmTypeInfo> typeList = alarmActiveMapper.queryTypeInfo();
		// 获取告警状态下拉框数据
		List<AlarmStatusInfo> statusList = alarmActiveMapper.queryStatusInfo();
		List<AlarmStatusInfo> activeStatusList = new ArrayList<AlarmStatusInfo>();
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).getStatusID() != 24) {
				activeStatusList.add(statusList.get(i));
			}
		}
		SecurityUserInfoBean user = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		map.put("userId", user.getId());
		map.put("username", user.getUserName());
		map.put("levelList", levelList);
		map.put("typeList", typeList);
		map.put("statusList", activeStatusList);
		map.put("ciId", request.getParameter("ciId"));
		map.put("alarmLevel", request.getParameter("alarmLevel"));
		map.put("proUrl", "/monitor/alarmActive/listRoomAlarmActive");
		String alarmOrderVersion = this.sysConfigServiceImpl.getConfParamValue(SystemFinalValue.SYS_CONFIG_TYPE_PROCESS_WOA, SystemFinalValue.SCT_PROC_WOA_CITY_WORKORDER_FLAG);
		if (StringUtils.isNotEmpty(alarmOrderVersion)) {
			map.put("alarmOrderVersion", alarmOrderVersion);
		}
		map.put("transferTimeStr", DateUtil.date2String(new Date()));
		return new ModelAndView("monitor/alarmMgr/alarmactive/roomAlarmActive_list");
	}

	/**
	 * 获取页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listRoomAlarmActive")
	@ResponseBody
	public Map<String, Object> listRoomAlarmActive(AlarmNode vo, HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("timeBegin", vo.getTimeBegin());
		paramMap.put("timeEnd", vo.getTimeEnd());
		paramMap.put("moName", vo.getMoName());
		paramMap.put("alarmLevel", vo.getAlarmLevel());
		paramMap.put("alarmTitle", vo.getAlarmTitle());
		paramMap.put("alarmStatus", vo.getAlarmStatus());
		paramMap.put("alarmType", vo.getAlarmType());
		paramMap.put("ciId", request.getParameter("ciId"));

		// 获得所有告警统计数据
		Page<AlarmNode> pageAll = new Page<AlarmNode>();
		pageAll.setPageNo(0);
		pageAll.setParams(paramMap);
		List<AlarmNode> listAll = alarmActiveMapper.queryListBy3dRoom(pageAll);

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmNode> page = new Page<AlarmNode>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		page.setParams(paramMap);
		// 查询分页数据
		List<AlarmNode> list = alarmActiveMapper.queryListBy3dRoom(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");

		emergency = 0;
		severity = 0;
		ordinary = 0;
		prompt = 0;
		uncertain = 0;
		for (AlarmNode alarmNode : listAll) {
			if ("#ff0000".equals(alarmNode.getLevelColor())) { // 紧急
				emergency += 1;
			} else if ("#ff9900".equals(alarmNode.getLevelColor())) { // 严重
				severity += 1;
			} else if ("#ffff00".equals(alarmNode.getLevelColor())) { // 一般
				ordinary += 1;
			} else if ("#0000ff".equals(alarmNode.getLevelColor())) { // 提示
				prompt += 1;
			} else if ("#c0c0c0".equals(alarmNode.getLevelColor())) { // 未确定
				uncertain += 1;
			}
		}
		return result;
	}

	/**
	 * 获取页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listRoomAlarmCount")
	@ResponseBody
	public Map<String, Integer> listRoomAlarmCount(HttpServletRequest request) throws Exception {
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("emergency", emergency);
		result.put("severity", severity);
		result.put("ordinary", ordinary);
		result.put("prompt", prompt);
		result.put("uncertain", uncertain);
		return result;
	}

	/**
	 * 拓扑－活动告警页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toTopoActiveList")
	public ModelAndView toTopoActiveList(ModelMap map, HttpServletRequest request) throws Exception {
		// 获取告警级别下拉框数据
		List<AlarmLevelInfo> levelList = alarmActiveMapper.queryLevelInfo();
		// 获取告警类型下拉框数据
		List<AlarmTypeInfo> typeList = alarmActiveMapper.queryTypeInfo();
		// 获取告警状态下拉框数据
		List<AlarmStatusInfo> statusList = alarmActiveMapper.queryStatusInfo();
		List<AlarmStatusInfo> activeStatusList = new ArrayList<AlarmStatusInfo>();
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).getStatusID() != 24) {
				activeStatusList.add(statusList.get(i));
			}
		}
		SecurityUserInfoBean user = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		if(user != null){
			map.put("userId", user.getId());
			map.put("username", user.getUserName());
		}else{
			map.put("userId", 1);
			map.put("username", "系统管理员");
		}

		map.put("levelList", levelList);
		map.put("typeList", typeList);
		map.put("statusList", activeStatusList);
		map.put("ciId", request.getParameter("moId"));
		map.put("alarmLevel", request.getParameter("alarmLevel"));
		map.put("proUrl", "/monitor/alarmActive/listTopoAlarmActive");
		// 是否启用新告警派单
		String workOrderVersion = this.sysConfigServiceImpl.getConfParamValue(
				SystemFinalValue.SYS_CONFIG_TYPE_PROCESS_WOA, SystemFinalValue.SCT_PROC_WOA_CITY_WORKORDER_FLAG);
		if (StringUtils.isNotEmpty(workOrderVersion)) {
			map.put("alarmOrderVersion", workOrderVersion);
		}
		map.put("transferTimeStr", DateUtil.date2String(new Date()));
		return new ModelAndView("monitor/alarmMgr/alarmactive/roomAlarmActive_list");
	}
	
	/**
	 * 获取页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listTopoAlarmActive")
	@ResponseBody
	public Map<String, Object> listTopoAlarmActive(AlarmNode vo, HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("timeBegin", vo.getTimeBegin());
		paramMap.put("timeEnd", vo.getTimeEnd());
		paramMap.put("moName", vo.getMoName());
		paramMap.put("alarmLevel", vo.getAlarmLevel());
		paramMap.put("alarmTitle", vo.getAlarmTitle());
		paramMap.put("alarmStatus", vo.getAlarmStatus());
		paramMap.put("alarmType", vo.getAlarmType());
		paramMap.put("moId", request.getParameter("ciId"));

		// 获得所有告警统计数据
		Page<AlarmNode> pageAll = new Page<AlarmNode>();
		pageAll.setPageNo(0);
		pageAll.setParams(paramMap);
		List<AlarmNode> listAll = alarmActiveMapper.queryListByTopo(pageAll);

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmNode> page = new Page<AlarmNode>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		page.setParams(paramMap);
		// 查询分页数据
		List<AlarmNode> list = alarmActiveMapper.queryListByTopo(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");

		emergency = 0;
		severity = 0;
		ordinary = 0;
		prompt = 0;
		uncertain = 0;
		for (AlarmNode alarmNode : listAll) {
			if ("#ff0000".equals(alarmNode.getLevelColor())) { // 紧急
				emergency += 1;
			} else if ("#ff9900".equals(alarmNode.getLevelColor())) { // 严重
				severity += 1;
			} else if ("#ffff00".equals(alarmNode.getLevelColor())) { // 一般
				ordinary += 1;
			} else if ("#0000ff".equals(alarmNode.getLevelColor())) { // 提示
				prompt += 1;
			} else if ("#c0c0c0".equals(alarmNode.getLevelColor())) { // 未确定
				uncertain += 1;
			}
		}
		return result;
	}

	/**
	 * 告警确认页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toRoomAlarmConfirm")
	public ModelAndView toRoomAlarmConfirm(@RequestParam("id") String id, ModelMap map) throws Exception {
		logger.info("告警确认id:" + id);
		map.put("id", id);
		return new ModelAndView("monitor/alarmMgr/alarmactive/roomAlarmConfirm");
	}

	/**
	 * 告警清除页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toRommAlarmClear")
	public ModelAndView toRommAlarmClear(@RequestParam("id") String id, ModelMap map) throws Exception {
		logger.info("告警清除id:" + id);
		map.put("id", id);
		return new ModelAndView("monitor/alarmMgr/alarmactive/roomAlarmClear");
	}

	/**
	 * 3d机房－活动告警页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toStatisAlarmActiveList")
	public ModelAndView toStatisAlarmActiveList(ModelMap map, HttpServletRequest request) throws Exception {
		// 获取告警级别下拉框数据
		List<AlarmLevelInfo> levelList = alarmActiveMapper.queryLevelInfo();
		// 获取告警类型下拉框数据
		List<AlarmTypeInfo> typeList = alarmActiveMapper.queryTypeInfo();
		// 获取告警状态下拉框数据
		List<AlarmStatusInfo> statusList = alarmActiveMapper.queryStatusInfo();

		List<AlarmStatusInfo> activeStatusList = new ArrayList<AlarmStatusInfo>();
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).getStatusID() != 24) {
				activeStatusList.add(statusList.get(i));
			}
		}
		SecurityUserInfoBean user = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		map.put("userId", user.getId());
		map.put("username", user.getUserName());

		map.put("levelList", levelList);
		map.put("typeList", typeList);
		map.put("statusList", activeStatusList);
		if (request.getParameter("filtername") != null) {
			map.put("filtername", request.getParameter("filtername"));
		}
		if (request.getParameter("id") != null) {
			map.put("filterId", request.getParameter("id"));
		}
		if (request.getParameter("timeBegin") != null) {
			map.put("timeBegin", request.getParameter("timeBegin"));
		}
		if (request.getParameter("timeEnd") != null) {
			map.put("timeEnd", request.getParameter("timeEnd"));
		}
		if (request.getParameter("alarmStatus") != null) {
			map.put("alarmStatus", request.getParameter("alarmStatus"));
		}
		if (request.getParameter("ciId") != null) {
			map.put("ciId", request.getParameter("ciId"));
		}
		map.put("proUrl", "/monitor/alarmActive/listStatisAlarmActive");
		String workOrderVersion = this.sysConfigServiceImpl.getConfParamValue(
				SystemFinalValue.SYS_CONFIG_TYPE_PROCESS_WOA, SystemFinalValue.SCT_PROC_WOA_CITY_WORKORDER_FLAG);
		if (StringUtils.isNotEmpty(workOrderVersion)) {
			map.put("alarmOrderVersion", workOrderVersion);
		}
		map.put("transferTimeStr", DateUtil.date2String(new Date()));
		return new ModelAndView("monitor/alarmMgr/alarmactive/statisAlarmActive_list");
	}

	/**
	 * 获取页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listStatisAlarmActive")
	@ResponseBody
	public Map<String, Object> listStatisAlarmActive(AlarmNode vo, HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmNode> page = new Page<AlarmNode>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("timeBegin", vo.getTimeBegin());
		paramMap.put("timeEnd", vo.getTimeEnd());
		paramMap.put("moName", vo.getMoName());
		paramMap.put("alarmLevel", vo.getAlarmLevel());
		paramMap.put("alarmTitle", vo.getAlarmTitle());
		paramMap.put("alarmStatus", vo.getAlarmStatus());
		paramMap.put("alarmType", vo.getAlarmType());
		paramMap.put("ciId", request.getParameter("ciId"));
		page.setParams(paramMap);
		// 查询分页数据
		List<AlarmNode> list = alarmActiveMapper.queryListByAlarmStatis(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}

	/**
	 * 查询该告警状态（该告警不存在历史表中）
	 */
	@RequestMapping("/queryHistoryAlarmData")
	public @ResponseBody boolean queryAlarmStatus(Integer alarmID) {

		// 该告警存在于实时告警中
		AlarmNode vo = alarmActiveMapper.getInfoById(alarmID);
		AlarmNode historyVo;
		if (vo != null) {
			return true;
		} else {
			// 不在实时告警表中，那么就到历史告警表查询相关告警信息
			historyVo = alarmActiveMapper.getHisInfoById(alarmID);
			if (historyVo != null) {
				return true;
			} else {
				// 若历史告警中也不存在
				return false;
			}
		}
	}

	/**
	 * 当需清除已派发的告警时，需关闭流程
	 * 
	 * @return
	 */
	@RequestMapping("/closeProcessService")
	public @ResponseBody void closeProcessService(HttpServletRequest request) {
		// 关闭其流程
		String dispIds = request.getParameter("dispIds");
		// 查询活动告警表中是否存在告警信息
		String[] arr = dispIds.split(",");
		for (int i = 0; i < arr.length; i++) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("alarmId", arr[i]);
			invokeRestInterface(SystemParamInit.getValueByKey("rest.resSychron.url"),
					"/rest/itsm/alarm/alarmClearCloseProcess", param);
		}
	}
}