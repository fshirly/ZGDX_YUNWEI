package com.fable.insightview.monitor.networkchannel.controller;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.alarmdispatcher.core.SyncAlarmOperate;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmdispatch.service.IAlarmDispatchService;
import com.fable.insightview.monitor.alarmmgr.alarmhistory.mapper.AlarmHistoryMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmDispatchDetail;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmHistoryInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmStatusInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.helper.RestHepler;
import com.fable.insightview.platform.common.util.DateUtil;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.service.ISysUserService;
import com.fable.insightview.platform.sysconf.service.SysConfigService;
import com.fable.insightview.platform.sysinit.SystemParamInit;

@Controller
@RequestMapping("/rest/monitor/alarmActive")
public class NetworkAlarmController {

	@Autowired
	private AlarmActiveMapper alarmActiveMapper;

	@Autowired
	private AlarmHistoryMapper alarmHistoryMapper;
	
	@Autowired
	private IAlarmDispatchService alarmDispatchService;

	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private SysConfigService sysConfigServiceImpl;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static Logger logger = LoggerFactory.getLogger(NetworkAlarmController.class);
	
	@RequestMapping("/queryAlarmLevelTypeStatus")
	@ResponseBody
	public Map<String ,Object> queryAlarmLevelTypeStatus(){
		// 获取告警级别下拉框数据
		List<AlarmLevelInfo> levelList = alarmActiveMapper.queryLevelInfo();
		// 获取告警类型下拉框数据
		List<AlarmTypeInfo> typeList = alarmActiveMapper.queryTypeInfo();
		// 获取告警状态下拉框数据
		List<AlarmStatusInfo> statusList = alarmActiveMapper.queryStatusInfo();
		
		AlarmStatusInfo status = null;
		for(AlarmStatusInfo statusInfo : statusList){
			if(statusInfo.getStatusName().equals("人工清除")){
				status = statusInfo;
				break;
			}
		}
		statusList.remove(status);
		
		List<AlarmLevelInfo> deleteLevel = new ArrayList<AlarmLevelInfo>();
		for(AlarmLevelInfo levelInfo : levelList){
			if(levelInfo.getAlarmLevelName().equals("提示") || levelInfo.getAlarmLevelName().equals("未确定")){
				deleteLevel.add(levelInfo);
			}
		}
		levelList.removeAll(deleteLevel);
		
		Map<String ,Object> resultMap = new HashMap<String ,Object>();
		resultMap.put("level", levelList);
		resultMap.put("type", typeList);
		resultMap.put("status", statusList);
		
		return resultMap;
	}

	@RequestMapping("/queryAlarmActivelist")
	@ResponseBody
	public Map<String, Object> queryAlarmActivelist(@RequestBody String queryParam){
		JSONObject json = JSONObject.parseObject(queryParam);
		if(StringUtils.isEmpty(json.getString("deviceMoids"))){
			Map<String, Object> result = new HashMap<String, Object>();
			// 设置至前台显示
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("data", new ArrayList<Map<String ,Object>>());
			return result;
		}
		logger.info("开始...获取页面显示数据");
		Page<AlarmNode> page = new Page<AlarmNode>();
		// 设置分页参数
		page.setPageNo(json.getIntValue("pageStart")+1);
		page.setPageSize(json.getIntValue("pageSize"));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("timeBegin", json.getString("timeBegin"));
		paramMap.put("timeEnd", json.getString("timeEnd"));
		paramMap.put("moName", json.getString("moName"));
		paramMap.put("alarmLevel", json.getInteger("alarmLevel"));
		paramMap.put("alarmTitle", json.getString("alarmTitle"));
		paramMap.put("alarmStatus", json.getIntValue("alarmStatus"));
		// 操作 状态
		paramMap.put("alarmOperateStatus", json.getInteger("alarmOperateStatus"));
		paramMap.put("sourceMOIPAddress", json.getString("sourceMOIPAddress"));
		paramMap.put("alarmType", json.getInteger("alarmType"));
		paramMap.put("dispatchUser", json.getString("dispatchUser"));
		paramMap.put("dispatchTime", json.getString("dispatchTime"));
		paramMap.put("deviceMoids", json.getString("deviceMoids"));
		paramMap.put("sourceMoName", json.getString("sourceMoName"));
		paramMap.put("sortParam", json.getString("sortParam"));

		if (null != json.getString("moid") && 
				!"-1".equals(json.getString("moid"))) {
			paramMap.put("moId", json.getString("moid"));
		}
		
		String neManufacturerID = json.getString("neManufacturerID");
		
		if (json.getIntValue("moClassId") != 0) {
			paramMap.put("moclassID", json.getIntValue("moClassId"));
			String alarmSourceSubSql = "";
			
			if (json.getIntValue("moClassId") == 6) {
				if (neManufacturerID != null 
						&& !"".equals(neManufacturerID)) {
					alarmSourceSubSql = "NeManufacturerID=" + neManufacturerID
							+ " and MOClassID in(59,60,6)";
				} 
				else {
					alarmSourceSubSql = "MOClassID in(59,60,6)";
				}
			}
			else {
				alarmSourceSubSql = "MOClassID = " + json.getIntValue("moClassId") + "";
			}
			paramMap.put("alarmSourceSubSql", alarmSourceSubSql);
		} 
		else {
			paramMap.put("moclassID", "");
		}
		paramMap.put("neManufacturerID", neManufacturerID);

		page.setParams(paramMap);
		// 查询分页数据
		List<AlarmNode> list = alarmActiveMapper.queryAlarmListWithDevice(page);
		List<Map<String ,Object>> alarmList = new ArrayList<Map<String ,Object>>();
		Field []fields = AlarmNode.class.getDeclaredFields();
		for(AlarmNode node : list){
			Map<String ,Object> alarmMap = new HashMap<String ,Object>();
			for(Field f : fields){
				f.setAccessible(true);
				Object obj = null;
				try {
					obj = f.get(node);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				if(obj != null){
					if(obj instanceof Date){
						alarmMap.put(f.getName(), dateFormat.format((Date)obj));
					}
					else{
						alarmMap.put(f.getName(), obj);
					}
				}
				
			}
			alarmList.add(alarmMap);
		}
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("recordsTotal", totalCount);
		result.put("recordsFiltered", totalCount);
		result.put("data", alarmList);
		logger.info("结束...获取页面显示数据");
		return result;
	}

	/***
	 * 告警派发
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajaxSendSingleAlarm")
	@ResponseBody
	public String ajaxSendSingleAlarm(@RequestBody String queryParam){
		JSONObject jsonObj = JSONObject.parseObject(queryParam);
		Random random = new Random();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String dispatchID = formatter.format(curDate) + String.valueOf(random.nextInt(1000));
		String json = "";
		String ids = jsonObj.getString("alarmIDs");
		if(!ids.contains(",")){
			AlarmNode alarmNode = alarmActiveMapper.getInfoById(Integer.parseInt(ids));
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("alarmID", alarmNode.getAlarmID());
			map.put("sourceMOName", alarmNode.getSourceMOName());
			map.put("sourceMOIpAdress", alarmNode.getSourceMOIPAddress());
			map.put("alarmLevel", alarmNode.getAlarmLevel());
			map.put("alarmLevelName", alarmNode.getAlarmLevelName());
			map.put("alarmTitle", alarmNode.getAlarmTitle());
			map.put("moClassID", alarmNode.getMoclassID());
			map.put("moClassName", alarmNode.getMoClassName());
			map.put("alarmDescription", alarmNode.getAlarmContent());
			map.put("sourceMoClassID", alarmNode.getSourceMOClassID());
			map.put("startTime", alarmNode.getStartTime());
			map.put("alarmStatus", alarmNode.getAlarmStatus());
			map.put("statusName", alarmNode.getStatusName());
			map.put("repeatCount", alarmNode.getRepeatCount());
			map.put("alarmOperateStatus", alarmNode.getAlarmOperateStatus());
			map.put("operateStatusName", alarmNode.getOperateStatusName());
			json = JsonUtil.toString(map);
		}
		Map<String, String> param = new HashMap<String, String>();
		
		param.put("orderTitle", jsonObj.getString("title")); // （消息来源）：inner,
		param.put("transferor", "inner"); // （消息来源）：inner,
		param.put("transferTime", jsonObj.getString("transferTime")); // （发送时间）:,
		// =======不确定
		param.put("opType", "1"); // （操作类型）：1.新告警4.自动清除 5.人工清除 7.次数重复 8.级别升级
		param.put("acceptTime", jsonObj.getString("transferTime")); // :
																		// Date（受理(派单)时间）,
		param.put("currentCount", "1"); // (当前次数):,
		param.put("alarmOrderStatus", "1"); // : （此处传1）,Int
											// （派单状态）【1、已登记2、处理中3、已恢复4、已关闭】

		param.put("dispatchID", dispatchID); // (int),
		param.put("firstTransferTime", jsonObj.getString("transferTime")); // (首次发送时间):
		param.put("remark", jsonObj.getString("remark")); // : String （派单备注）,
		param.put("acceptPeople", jsonObj.getString("acceptPeople")); // int（派单人）
		param.put("isAccept", "1"); // : ,Int （是否受理(监测此处默认传1)）
		param.put("isChecked", jsonObj.getString("isChecked")); // : ,Int
																	// （服务台是否核实(1:是,0:否)）
//		param.put("handlingPeople", request.getParameter("handlingPeople")); // :
																			// Int（处理人）
		param.put("summary", jsonObj.getString("summary")); // ： String 办理意见
		param.put("selectPeopleId", jsonObj.getString("selectPeopleId")); // ：
																				// String
		param.put("selectPeopleName", jsonObj.getString("selectPeopleName")); // ：
		param.put("content", json);
		
		param.put("isSend", jsonObj.getString("isSend")); //是否发送短信
		
		//告警三期新增
		param.put("incidentType", jsonObj.getString("incidentType"));
		param.put("relResCiId", jsonObj.getString("relResCiId"));
		
		
		
		Set<Entry<String, String>> es = param.entrySet();
		for (Entry<String, String> e : es) {
			if(null == e.getValue()){
					param.put(e.getKey(), "");
			}
		}
		String jtemp = JSONObject.toJSONString(param);
		logger.info("[INFO]: 手工派单调用CMDB接口传递参数:" + jtemp);
		// 1.调用接口
		String temp = null;
		try {
			temp = invokeRestInterface("/rest/itsm/alarm/","alarmOrderAllot" , param);
		} catch (Exception e1) {
			logger.error("[ERROR]: 派发意外失败.");
			temp = "{\"workOrderId\":\"\",\"dispatchID\":\"" + dispatchID + "\",\"executeFlag\":\"2\",\"resultDescr\":\"\"}";
			e1.printStackTrace();
		}
		if("".equals(temp)){
			temp = "{\"workOrderId\":\"1\",\"dispatchID\":\"" + dispatchID + "\",\"executeFlag\":\"2\",\"resultDescr\":\"\"}";
		}
		JSONObject  pjson = JSONObject.parseObject(temp);
		// 2.更新表
		AlarmDispatchDetail add = new AlarmDispatchDetail();
		add.setDispatchID(dispatchID);
		add.setAlarmID(2);
		add.setDispatchType(2);
			// add.setAlarmStatus(alarmStatus);
		int dispatchStatus = Integer.parseInt(pjson.getString("executeFlag"));//派发是否成功1-成功、2-失败
		add.setDispatchStatus(dispatchStatus);
		add.setDispatchTime(new Timestamp(new Date().getTime()));
		add.setResultDescr(pjson.getString("resultDescr"));
		add.setWorkOrderId(Integer.parseInt(pjson.getString("workOrderId")));
			
		add.setResendTime(new Timestamp(new Date().getTime()));
		// add.setSendCount(sendCount);
		add.setDispatchJson(JSONObject.toJSONString(param));
		alarmDispatchService.insertAlarmDispatchRecord(add);
		
		if (dispatchStatus == 1) {
			logger.info("[INFO] 更新告警状态为已派发，告警ids:" + ids);
			// 4.更新活动告警,更新状态，更新dispatchId，更新dispatchTime
			Map<String, String> map = new HashMap<String, String>();
			map.put("dispatchID", dispatchID);
			map.put("ids", ids);
			map.put("dispatchTime", jsonObj.getString("transferTime"));
			map.put("dispatchUser", jsonObj.getString("handlingPeople"));
			
			map.put("alarmOperateStatus", "23");
			alarmActiveMapper.updateAlarmActiveDetail(map);
			
			// 3.调用告警派发线程
			logger.info("[INFO] 调用告警派发线程派发告警，告警ids:" + ids);
			SyncAlarmOperate.getInstance().sendAlarmMessage(ids);
			return "true";
		}
		return "false";
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
	public boolean doAlarmActiveConfirm(@RequestBody String queryParam ,HttpServletRequest request){
		AlarmNode vo = new AlarmNode();
		JSONObject json = JSONObject.parseObject(queryParam);
		vo.setMoName("("+json.getString("alarmIDs")+")");
		vo.setConfirmInfo(json.getString("confirmInfo"));
		logger.info("开始...处理确认告警");
		logger.debug("update by param AlarmID={}", vo.getAlarmID());
		// 获取当前用户对象  无法获取
		/*SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");*/
		vo.setConfirmTime(new Timestamp(new Date().getTime()));
		vo.setConfirmer("10000");

		try {
			alarmActiveMapper.bathAlarmActiveConfirm(vo);
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
	public boolean cancelAlarmActiveConfirm(@RequestBody String queryParam ,HttpServletRequest request) {
		JSONObject json = JSONObject.parseObject(queryParam);
		int alarmID = json.getIntValue("alarmID");
		logger.info("开始...取消确认告警");
		logger.debug("update by param AlarmID={}", alarmID);

		try {
			alarmActiveMapper.cancelAlarmActiveConfirm(alarmID);
			SyncAlarmOperate.getInstance().sendAlarmMessage(alarmID + "");// 传送告警消息
		} catch (Exception e) {
			logger.error("取消确认告警异常：" + e.getMessage(), alarmID);
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
	public boolean doClearAlarmActive(@RequestBody String queryParam ,HttpServletRequest request){
		AlarmNode vo = new AlarmNode();
		JSONObject json = JSONObject.parseObject(queryParam);
		vo.setMoName(json.getString("alarmIDs"));
		vo.setCleanInfo(json.getString("cleanInfo"));
		
		logger.info("开始...告警清除");
		logger.debug("update by param AlarmID={}", vo.getAlarmID());

		// 查询活动告警表中是否存在告警信息
		String newId = alarmActiveMapper.queryAlarmIdsByIds(vo.getMoName());
		if (newId != "" && newId != null) {
			try {
				clearAlarmInfo(vo, newId);
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
	private void clearAlarmInfo(AlarmNode vo, String id) {
		AlarmHistoryInfo historyVo = new AlarmHistoryInfo();
		historyVo.setMoName(id);
		historyVo.setCleanInfo(vo.getCleanInfo());
		historyVo.setCleanTime(new Timestamp(new Date().getTime()));// 当时时间
		historyVo.setCleaner("admin");// 当前用户
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
	public boolean deleteAlarmActive(@RequestParam String queryParam, HttpServletRequest request) {
		JSONObject json = JSONObject.parseObject(queryParam);
		int alarmID = json.getIntValue("alarmID");
		logger.info("开始...删除告警");
		logger.debug("delete by param AlarmID={}", alarmID);
		/* 告警删除前,将该告警信息插入历史告警表中 */
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");

		String newId = alarmActiveMapper.queryAlarmIdsByIds(alarmID + "");

		if (newId != "" && newId != null) {
			AlarmHistoryInfo historyVo = new AlarmHistoryInfo();
			historyVo.setMoName(alarmID + "");
			// historyVo.setAlarmStatus(24);// 表示人工删除
			historyVo.setAlarmOperateStatus(24);// 表示人工删除
			historyVo.setDeleteTime(new Timestamp(new Date().getTime()));
			historyVo.setDeletedUser(userVo.getUsername());

			try {
				alarmHistoryMapper.copyActiveDeleteInfo(historyVo);// 插入历史告警表
				alarmActiveMapper.deleteAlarmActive(alarmID + "");// 删除活动告警表
				SyncAlarmOperate.getInstance().sendAlarmMessage(alarmID + "");// 传送告警消息
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
	public boolean doBathAlarmActiveConfirm(@RequestParam String queryParam, HttpServletRequest request) {
		AlarmNode vo = (AlarmNode)JsonUtil.jsonToBean(queryParam, AlarmNode.class);
		logger.info("开始...批量处理确认告警");
		// 获取当前用户对象
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		vo.setConfirmTime(new Timestamp(new Date().getTime()));
		vo.setConfirmer(userVo.getUserName());
		String id = String.valueOf(vo.getAlarmID());

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
	public boolean doAlarmConfirm(@RequestParam String queryParam, HttpServletRequest request) {
		AlarmNode vo = (AlarmNode)JsonUtil.jsonToBean(queryParam, AlarmNode.class);
		logger.info("开始...批量处理确认告警");
		// 获取当前用户对象
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		vo.setConfirmTime(new Timestamp(new Date().getTime()));
		vo.setConfirmer(userVo.getUserName());
		String id = String.valueOf(vo.getAlarmID());

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
	public boolean bathCancelAlarmActiveConfirm(@RequestParam String queryParam) {
		JSONObject json = JSONObject.parseObject(queryParam);
		String id = json.getString("alarmID");
		logger.info("开始...取消确认告警");

		try {
			if (StringUtils.isNotEmpty(id)) {
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
	public boolean bathDeleteAlarmActive(@RequestParam String queryParam, HttpServletRequest request) {
		JSONObject json = JSONObject.parseObject(queryParam);
		logger.info("开始...删除告警");
		/* 告警删除前,将该告警信息插入历史告警表中 */
		SecurityUserInfoBean userVo = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");

		String id = json.getString("alarmID");
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
	 * 查询该告警状态（该告警不存在历史表中）
	 */
	@RequestMapping("/queryHistoryAlarmData")
	@ResponseBody
	public boolean queryAlarmStatus(@RequestParam String queryParam) {
		JSONObject json = JSONObject.parseObject(queryParam);
		int alarmID = json.getIntValue("alarmID");
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
	@ResponseBody
	public void closeProcessService(@RequestParam String queryParam) {
		JSONObject json = JSONObject.parseObject(queryParam);
		// 关闭其流程
		String dispIds = json.getString("dispatchIds");
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