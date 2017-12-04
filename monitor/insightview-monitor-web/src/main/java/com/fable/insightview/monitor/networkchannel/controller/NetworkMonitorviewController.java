package com.fable.insightview.monitor.networkchannel.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmStatusInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.host.mapper.HostMapper;
import com.fable.insightview.monitor.networkchannel.service.INetworkMonitorviewService;
import com.fable.insightview.platform.page.Page;

/**
 * 网络频道运行监测 监测视图Controller
 *
 */

@Controller
@RequestMapping("/rest/monitor/networkMonitorview")
public class NetworkMonitorviewController {
	@Autowired
	private AlarmActiveMapper alarmActiveMapper;
	@Autowired
	private HostMapper hostMapper;
	@Autowired
	private INetworkMonitorviewService monitorService;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping("/deviceAvalibility")
	@ResponseBody
	public Map<String ,Object> queryDeviceAvalibility(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		String timeInterval = object.getString("time");
		String timeTips = "";
		
		if ("24H".equals(timeInterval)) {// 最近一天
			timeTips = "24小时";
		} else if ("7D".equals(timeInterval)) {// 最近一周
			timeTips = "最近一周";
		} else if ("Today".equals(timeInterval)) {// 今天
			timeTips = "今天";
		} else if ("Yes".equals(timeInterval)) {// 昨天
			timeTips = "昨天";
		}else if ("Week".equals(timeInterval)) {// 本周
			timeTips = "本周";
		}else if ("Month".equals(timeInterval)) {// 本月
			timeTips = "本月";
		}else if ("LastMonth".equals(timeInterval)) {// 上月
			timeTips = "上月";
		}
		String nameValue = timeTips + "可用性";
		
		HashMap<String ,Object> params = new HashMap<String ,Object>();
		setQueryTimeArea(timeInterval ,params);
		
		return monitorService.queryDeviceAvalibilityInfo(deviceMoid, params.get("timeBegin").toString()
				, params.get("timeEnd").toString() ,nameValue);
	}
	
	@RequestMapping("/deviceNameIp")
	@ResponseBody
	public Map<String ,Object> queryDeviceNameIp(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		
		return monitorService.queryDeviceNameIp(deviceMoid);
	}
	
	@RequestMapping("/deviceInfo")
	@ResponseBody
	public List<Map<String ,Object>> queryDeviceInfo(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("MOID", deviceMoid);
		params.put("MID", KPINameDef.JOBPINGPOLL);
		MODevice mo = hostMapper.getDeviceDetail(params);
		
		List<Map<String ,Object>> resultList = new ArrayList<Map<String ,Object>>();
		HashMap<String ,Object> result = new HashMap<String ,Object>();
		result.put("text", "设备名称");
		result.put("value", mo.getMoname());
		result.put("rid", "1");
		resultList.add(result);
		
		result = new HashMap<String ,Object>();
		result.put("text", "IP地址");
		result.put("value", mo.getDeviceip());
		result.put("rid", "2");
		resultList.add(result);
		
		result = new HashMap<String ,Object>();
		result.put("text", "设备类型");
		result.put("value", mo.getClasslable());
		result.put("rid", "3");
		resultList.add(result);
		
		result = new HashMap<String ,Object>();
		result.put("text", "发现时间");
		result.put("value", dateFormat.format(mo.getCreatetime()));
		result.put("rid", "4");
		resultList.add(result);
		
		result = new HashMap<String ,Object>();
		result.put("text", "厂商");
		result.put("value", mo.getResmanufacturername());
		result.put("rid", "5");
		resultList.add(result);
		
		result = new HashMap<String ,Object>();
		result.put("text", "设备型号");
		result.put("value", mo.getRescategoryname());
		result.put("rid", "6");
		resultList.add(result);
		
		return resultList;
	}
	
	@RequestMapping("/importantIfAvalibility")
	@ResponseBody
	public List<Map<String ,Object>> queryImportantIfAvalibility(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		
		return monitorService.queryImportantIfInfo(deviceMoid);
	}
	
	@RequestMapping("/importantIfUsage")
	@ResponseBody
	public Map<String ,Object> queryImportantIfUsage(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		String timeInterval = object.getString("time");
		
		HashMap<String ,Object> params = new HashMap<String ,Object>();
		setQueryTimeArea(timeInterval ,params);
		
		return monitorService.queryImportantIfUsage(deviceMoid, params.get("timeBegin").toString()
				, params.get("timeEnd").toString() ,getDataSplit(timeInterval));
	}
	
	@RequestMapping("/ifStatusList")
	@ResponseBody
	public List<Map<String ,Object>> queryIfStatusList(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		return monitorService.queryIfStatusList(deviceMoid);
	}
	
	@RequestMapping("/ifAlarm")
	@ResponseBody
	public Map<String ,Object> queryIfAlarm(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		int pageStart = object.getIntValue("pageStart");
		int pageSize = object.getIntValue("pageSize");
		String alarmLevel = object.getString("alarmLevel");
		String operatStatus = object.getString("operatStatus");
		boolean query = object.getBooleanValue("query");
		
		Page<Map<String ,Object>> page = new Page<Map<String ,Object>>();
		// 设置分页参数
		page.setPageNo(pageStart + 1);
		page.setPageSize(pageSize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceMoid", deviceMoid);
		paramMap.put("alarmLevel", alarmLevel);
		paramMap.put("operatStatus", operatStatus);
		page.setParams(paramMap);
		
		List<Map<String ,String>> list = monitorService.queryIfAlarm(page);
		
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("recordsTotal", page.getTotalRecord());
		result.put("recordsFiltered", page.getTotalRecord());
		result.put("data", list);
		if(!query){
			// 获取告警级别下拉框数据
			List<AlarmLevelInfo> levelList = alarmActiveMapper.queryLevelInfo();
			// 获取告警类型下拉框数据
			List<AlarmTypeInfo> typeList = alarmActiveMapper.queryTypeInfo();
			// 获取告警状态下拉框数据
			List<AlarmStatusInfo> statusList = alarmActiveMapper.queryStatusInfo();
			
			result.put("level", levelList);
			result.put("type", typeList);
			result.put("status", statusList);
		}
		
		return result;
	}
	
	@RequestMapping("/top10IfUsage")
	@ResponseBody
	public List<Map<String ,Object>> queryTop10IfUsage(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		
		return monitorService.queryTop10IfUsage(deviceMoid);
	}
	
	@RequestMapping("/top10IfDiscardsAndErrors")
	@ResponseBody
	public List<Map<String ,Object>> queryTop10IfDiscardsAndErrors(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		
		return monitorService.queryTop10IfDiscardsAndErrors(deviceMoid);
	}
	
	@RequestMapping("/cpuUsage")
	@ResponseBody
	public Map<String ,Object> queryCPUsage(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		return monitorService.queryCPUsage(deviceMoid);
	}
	
	@RequestMapping("/cpuUsageLine")
	@ResponseBody
	public Map<String ,Object> queryCpuUsageLine(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		String timeInterval = object.getString("time");
		
		HashMap<String ,Object> params = new HashMap<String ,Object>();
		setQueryTimeArea(timeInterval ,params);
		
		return monitorService.queryCpuUsageLine(deviceMoid, params.get("timeBegin").toString()
				, params.get("timeEnd").toString() ,getDataSplit(timeInterval));
	}
	
	@RequestMapping("/memoryUsage")
	@ResponseBody
	public Map<String ,Object> queryMemUsage(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		return monitorService.queryMemUsage(deviceMoid);
	}
	
	@RequestMapping("/memUsageLine")
	@ResponseBody
	public Map<String ,Object> queryMemUsageLine(@RequestBody String queryParam){
		JSONObject object = JSONObject.parseObject(queryParam);
		int deviceMoid = object.getIntValue("deviceMoid");
		String timeInterval = object.getString("time");
		
		HashMap<String ,Object> params = new HashMap<String ,Object>();
		setQueryTimeArea(timeInterval ,params);
		
		return monitorService.queryMemUsageLine(deviceMoid, params.get("timeBegin").toString()
				, params.get("timeEnd").toString() ,getDataSplit(timeInterval));
	}
	
	private void setQueryTimeArea(String timeInterval, Map<String ,Object> params) {
		Calendar d = Calendar.getInstance();
		String timeEnd = dateFormat.format(d.getTime());
		String timeBegin = "";

		if ("24H".equals(timeInterval)) {// 最近一天
			d.add(Calendar.DATE, -1);
			timeBegin = dateFormat.format(d.getTime());
		} else if ("7D".equals(timeInterval)) {// 最近一周
			d.add(Calendar.DATE, -7);
			timeBegin = dateFormat.format(d.getTime());
		} else if ("Today".equals(timeInterval)) {// 今天
			d.add(Calendar.DATE, 0);
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = dateFormat.format(d.getTime());
		} else if ("Yes".equals(timeInterval)) {// 昨天
			d.add(Calendar.DAY_OF_MONTH, -1);
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = dateFormat.format(d.getTime());
			d.set(Calendar.HOUR_OF_DAY, 23);
			d.set(Calendar.MINUTE, 59);
			d.set(Calendar.SECOND, 59);
			timeEnd = dateFormat.format(d.getTime());
		} else if ("Week".equals(timeInterval)) {// 本周
			if (d.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK) + 5));
			} else {
				d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK) - 2));
			}

			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = dateFormat.format(d.getTime());
		} else if ("Month".equals(timeInterval)) {// 本月
			d.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			d.set(Calendar.DAY_OF_MONTH, 1);
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = dateFormat.format(d.getTime());
		} else if ("LastMonth".equals(timeInterval)) {// 上月
			d.add(Calendar.MONTH, -1);
			d.set(Calendar.DAY_OF_MONTH, 1);// 上月第一天
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = dateFormat.format(d.getTime());
			Calendar cale = Calendar.getInstance();
			// 设置为1号,当前日期既为本月第一天
			cale.set(Calendar.DAY_OF_MONTH, 0);
			cale.set(Calendar.HOUR_OF_DAY, 23);
			cale.set(Calendar.MINUTE, 59);
			cale.set(Calendar.SECOND, 59);
			timeEnd = dateFormat.format(cale.getTime());

		} else {
			d.add(Calendar.DATE, -1);
		}
		
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
	}
	
	private String getDataSplit(String timeInterval){
		String dataSplit = "";
		if ("24H".equals(timeInterval)) {// 最近一天
			dataSplit = "C";
		} else if ("Today".equals(timeInterval)) {// 今天
			dataSplit = "C";
		} else if ("Yes".equals(timeInterval)) {// 昨天
			dataSplit = "C";
		} else if ("7D".equals(timeInterval)) {// 最近一周
			dataSplit = "D";
		} else if ("Week".equals(timeInterval)) {// 本周
			dataSplit = "D";
		} else if ("Month".equals(timeInterval)) {// 本月
			dataSplit = "D";
		} else if ("LastMonth".equals(timeInterval)) {// 上月
			dataSplit = "D";
		}
		return dataSplit;
	}
}
