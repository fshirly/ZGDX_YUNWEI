package com.fable.insightview.monitor.alarmmgr.alarmsendsingle.controller.rest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmdispatch.service.IAlarmDispatchService;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmDispatchDetail;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.platform.common.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/***
 * 省市工单
 * 工单下发以后，匹配到地市有此告警，需修改此告警的操作状态等等操作信息
 * @author myaluka
 *
 */
@Controller
@RequestMapping("/rest/monitor/alarm")
public class AlarmUpdateRestController {
	
	@Autowired
	private IAlarmDispatchService alarmDispatchService;
	
	@Autowired
	private AlarmActiveMapper activeMapper;
	/***
	 * 修改AlarmActiveDetial 的操作状态，插入AlarmDispatchDetail
	 * @param paramMap 
	 * @return
	 */
	@RequestMapping(value = "/updateAlarmActiveDetialRest", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String,Object> updateAlarmActiveDetialRest(@RequestBody String param){
		Map<String,Object> paramMap = new Gson().fromJson(param, Map.class);
		//AlarmActiveDetial修改
		Map<String,Object> resMap = new HashMap<String,Object>();
		try {
			Double alarmId = Double.parseDouble(String.valueOf(paramMap.get("alarmId")));
			String dispatchId = String.valueOf(paramMap.get("dispatchId"));
			String dispatchUser = String.valueOf(paramMap.get("dispatchUser"));
			String dispatchTime = String.valueOf(paramMap.get("dispatchTime"));
			//AlarmDispatchDetail新增
			String jsonStr = String.valueOf(paramMap.get("jsonStr"));
			Double workOrderId = Double.parseDouble(String.valueOf(paramMap.get("workOrderId")));
			Double executeFlag = Double.parseDouble(String.valueOf(paramMap.get("executeFlag")));
			String resultDescr = String.valueOf(paramMap.get("resultDescr"));
			// 2.插入表AlarmDispatchDetail
			AlarmDispatchDetail add = new AlarmDispatchDetail();
			add.setDispatchID(dispatchId);
			if(alarmId != null){
				add.setAlarmID(alarmId.intValue());
			}
			add.setDispatchType(2);
			if(executeFlag != null){
				add.setDispatchStatus(executeFlag.intValue());
			}
			add.setDispatchTime(new Timestamp(new Date().getTime()));
			add.setResultDescr(resultDescr);
			add.setResendTime(new Timestamp(new Date().getTime()));
			add.setDispatchJson(jsonStr);
			if(workOrderId != null){
				add.setWorkOrderId(workOrderId.intValue());
			}
			alarmDispatchService.insertAlarmDispatchRecord(add);
			//更新AlarmActiveDetial
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dispatchID", dispatchId);
			map.put("ids", alarmId.intValue());
			map.put("dispatchTime", dispatchTime);
			map.put("dispatchUser", dispatchUser);
			map.put("alarmOperateStatus", "23");
			activeMapper.updateAlarmActiveDetail(map);
			resMap.put("statusCode", "success");
		} catch (NumberFormatException e) {
			String msg = e.getMessage().length()>200?e.getMessage().substring(0,200):e.getMessage();
			resMap.put("statusCode", "error");
			resMap.put("msg", msg);
		}
		return resMap;
		
	}
}
