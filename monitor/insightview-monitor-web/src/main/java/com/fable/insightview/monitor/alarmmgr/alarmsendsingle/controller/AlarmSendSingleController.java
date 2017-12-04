package com.fable.insightview.monitor.alarmmgr.alarmsendsingle.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.alarmdispatcher.core.SyncAlarmOperate;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmdispatch.service.IAlarmDispatchService;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmDispatchDetail;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.platform.common.helper.RestHepler;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.sysconf.service.SysConfigService;
import com.fable.insightview.platform.sysinit.SystemParamInit;
import com.google.gson.Gson;

/**
 * @Description: 告警派单控制器
 * @author huangzx
 * @Date 2015-5-6
 */
@Controller
@RequestMapping("/monitor/AlarmSendSingle")
@SuppressWarnings("all")
public class AlarmSendSingleController {
	private final static Logger logger = LoggerFactory.getLogger(AlarmSendSingleController.class);

	@Autowired
	private IAlarmDispatchService alarmDispatchService;
	
	@Autowired
	private AlarmActiveMapper activeMapper;
	
	@Autowired
	private SysConfigService sysConfigService;

	
	@RequestMapping("/toAlarmStatisList")
	public ModelAndView toAlarmActiveList(AlarmNode vo, ModelMap map) throws Exception {
		return new ModelAndView("monitor/alarmMgr/alarmstatis/alarmStatis_list");
	}
	
	/***
	 * 地市告警派单，新增功能告警匹配
	 * @return与receive匹配
	 * @throws Exception 
	 */
	@RequestMapping("/alarmMaching")
	@ResponseBody
	public String alarmMaching(HttpServletRequest request,String ids) throws Exception{
		String url = SystemParamInit.getValueByKey("rest.bpmConsole.machingAlarm")+"/rest/maching/machingAlarmOrderReceive";
		String cityWorkOrderFlag = this.sysConfigService.getConfParamValue(SystemFinalValue.SYS_CONFIG_TYPE_PROCESS_WOA ,SystemFinalValue.SCT_PROC_WOA_CITY_WORKORDER_FLAG);
		if("3".equals(cityWorkOrderFlag)){ //启用新的告警匹配
			url = SystemParamInit.getValueByKey("rest.bpmConsole.machingAlarm")+"/rest/maching/machingAlarmOrderReceiveV3";
		}
		//String url = "http://192.168.20.176:8088/insightview/rest/maching/machingAlarmOrderReceive";
		String[] idStr = ids.split(",");
		Random random = new Random();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String dispatchID = formatter.format(curDate) + String.valueOf(random.nextInt(1000));
		int count = 0;
		for (String id : idStr) {
			if(activeMapper.getInfoById(Integer.parseInt(id)).getAlarmOperateStatus() == 23){ //表示已经派发
				count++;
			}
			Map<String,Object> alarmOrderReceive = null;
			//匹配匹配iaAlarmReceive中是否有告警
			Map<String,Object> machMap = new HashMap<String, Object>();
			AlarmNode  alarmNode = activeMapper.getInfoById(Integer.parseInt(id));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			machMap.put("tableName", "IAAlarmOrderReceive");
			machMap.put("sourceMoClassId", alarmNode.getSourceMOClassID());
			machMap.put("mOName", alarmNode.getMoName());
			machMap.put("soucrceIPAddress", alarmNode.getSourceMOIPAddress());
			machMap.put("alarmDefineID", alarmNode.getAlarmDefineID());
			if(alarmNode.getLastTime() != null){
				machMap.put("lastTime",sdf.format(alarmNode.getLastTime()));
			}
			machMap.put("alarmId", id);
			Map<String,Object> resMap = RestHepler.exchangeEX(url, Map.class, new Gson().toJson(machMap), new Object());
			if(resMap != null){
				if(String.valueOf(resMap.get("statusCode")).equals("200")){
					String alarmOrderReceiveStr = String.valueOf(resMap.get("alarmOrderReceive"));
					String jsonStr = String.valueOf(resMap.get("jsonStr"));
					alarmOrderReceive = new Gson().fromJson(alarmOrderReceiveStr, Map.class);
					count ++;
					AlarmDispatchDetail add = new AlarmDispatchDetail();
					add.setDispatchID(dispatchID);
					add.setAlarmID(Integer.valueOf(id));
					add.setDispatchType(2);
					Date dispatchTime = null;
					if(resMap.get("dispatchTime") != null){
						 dispatchTime = sdf.parse(String.valueOf(resMap.get("dispatchTime")));
						 add.setDispatchTime(new Timestamp(dispatchTime.getTime()));
					}
					add.setWorkOrderId(Integer.valueOf(id));
					add.setDispatchJson(jsonStr);
					alarmDispatchService.insertAlarmDispatchRecord(add);
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("dispatchID", dispatchID);
					map.put("ids", id);
					map.put("dispatchTime", String.valueOf(resMap.get("dispatchTime")));
					map.put("dispatchUser", String.valueOf(alarmOrderReceive.get("dispatcher")));
					map.put("alarmOperateStatus", "23");
					activeMapper.updateAlarmActiveDetail(map);
				}else{
					logger.info("调用接口失败，错误原因"+String.valueOf(resMap.get("msg")));
				}
			}else{
				logger.info("网络或者参数异常！调用失败！");
			}
		}
		if(count>0){
			return "unPass";
		}else{
			return "pass";
		}
		
	}
	
	/***
	 * 告警派发
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajaxSendSingleAlarm")
	@ResponseBody
	public String ajaxSendSingleAlarm(HttpServletRequest request) throws Exception {
		Random random = new Random();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String dispatchID = formatter.format(curDate) + String.valueOf(random.nextInt(1000));
		String json = "";
		String ids = request.getParameter("ids");
		if(!ids.contains(",")){
			AlarmNode alarmNode = activeMapper.getInfoById(Integer.parseInt(ids));
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
		
		param.put("orderTitle", request.getParameter("title")); // （消息来源）：inner,
		param.put("transferor", "inner"); // （消息来源）：inner,
		param.put("transferTime", request.getParameter("transferTime")); // （发送时间）:,
		// TODO=======不确定
		param.put("opType", "1"); // （操作类型）：1.新告警4.自动清除 5.人工清除 7.次数重复 8.级别升级
		param.put("acceptTime", request.getParameter("transferTime")); // :
																		// Date（受理(派单)时间）,
		param.put("currentCount", "1"); // (当前次数):,
		param.put("alarmOrderStatus", "1"); // : （此处传1）,Int
											// （派单状态）【1、已登记2、处理中3、已恢复4、已关闭】
		// TODO====

		param.put("dispatchID", dispatchID); // (int),
		param.put("firstTransferTime", request.getParameter("transferTime")); // (首次发送时间):
		param.put("remark", request.getParameter("remark")); // : String （派单备注）,
		param.put("acceptPeople", request.getParameter("acceptPeople")); // int（派单人）
		param.put("isAccept", "1"); // : ,Int （是否受理(监测此处默认传1)）
		param.put("isChecked", request.getParameter("isChecked")); // : ,Int
																	// （服务台是否核实(1:是,0:否)）
//		param.put("handlingPeople", request.getParameter("handlingPeople")); // :
																			// Int（处理人）
		param.put("summary", request.getParameter("summary")); // ： String 办理意见
		param.put("selectPeopleId", request.getParameter("selectPeopleId")); // ：
																				// String
		param.put("selectPeopleName", request.getParameter("selectPeopleName")); // ：
		param.put("content", json);
		
		param.put("isSend", request.getParameter("isSend")); //是否发送短信
		
		//告警三期新增
		param.put("incidentType", request.getParameter("incidentType"));
		param.put("relResCiId", request.getParameter("relResCiId"));
		
		
		
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
			temp = invokeParty("/rest/itsm/alarm/alarmOrderAllot", param);
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
			map.put("dispatchTime", request.getParameter("transferTime"));
			map.put("dispatchUser", request.getParameter("handlingPeople"));
			
			map.put("alarmOperateStatus", "23");
			activeMapper.updateAlarmActiveDetail(map);
			
			// 3.调用告警派发线程
			logger.info("[INFO] 调用告警派发线程派发告警，告警ids:" + ids);
			SyncAlarmOperate.getInstance().sendAlarmMessage(ids);
			return "true";
		}
		return "false";
	}
	@RequestMapping("/ajaxFindTreeDepartments")
	@ResponseBody
	public String ajaxFindTreeDepartments(HttpServletRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		String pid = request.getParameter("pid");
		if (null == pid) {
			pid = "0";
		}
		param.put("parentId", pid);
		return invokeParty("/rest/itsm/alarm/findTreeDepartments", param);
	}

	@RequestMapping("/ajaxFindStaffInfo")
	@ResponseBody
	public String ajaxFindStaffInfo(HttpServletRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("deptId", request.getParameter("id"));
		return invokeParty("/rest/itsm/alarm/findStaffInfo", param);
	}

	@RequestMapping("/ajaxFindAllGroups")
	@ResponseBody
	public String ajaxFindAllGroups(HttpServletRequest request) {
		Map<String, String> param = new HashMap<String, String>();

		return invokeParty("/rest/itsm/alarm/findAllGroups", param);
	}

	@RequestMapping("/ajaxFindStaffInfo2")
	@ResponseBody
	public String ajaxFindStaffInfo2(HttpServletRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("groupId", request.getParameter("id"));
		return invokeParty("/rest/itsm/alarm/findStaffInfo2", param);
	}
	
	
	
	
	// 派单详细接口
	// /rest/itsm/alarm/alarmSendOrderDetailView
	// 需传入的参数：
	// JSON
	// {
	// “workOrderId” ： String （派单id）
	// }

	/**
	 * 调用接口
	 * 
	 * @param path
	 * @param data
	 * @return
	 */
	private String invokeParty(String path, Map<String, String> data) {
		try {
			String url = SystemParamInit.getValueByKey("rest.room3d.url");
//			url = "http://192.168.16.120:8080/insightview-bpmconsole-war";
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
			ResponseEntity<String> rssResponse = rest.exchange(basePath.toString(), HttpMethod.POST, requestEntity, String.class);

			if (null != rssResponse) {
				logger.info(rssResponse.getBody());
			}
			return rssResponse.getBody();
		} catch (Exception e) {
			logger.error("调用接口同步,有错误!");
		}
		return "";
	}
}
