package com.fable.insightview.monitor.networkchannel.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.networkchannel.service.INetworkOverviewService;

/**
 * 网络频道概览视图Controller
 *
 */
@Controller
@RequestMapping("/rest/monitor/networkOverview")
public class NetworkOverviewController {
	@Autowired
	private INetworkOverviewService overviewService;
	
	@RequestMapping("/top10CPUsage")
	@ResponseBody
	public List<Map<String ,Object>> queryTop10CPUsage(@RequestBody String queryParam){
		JSONObject json = JSONObject.parseObject(queryParam);
		String applicationType = json.getString("applicationType");
		String network = json.getString("network");
		return overviewService.queryTop10CPUsage(network, applicationType);
	}
	
	@RequestMapping("/top10MemoryUsage")
	@ResponseBody
	public List<Map<String ,Object>> queryTop10MemoryUsage(@RequestBody String queryParam){
		JSONObject json = JSONObject.parseObject(queryParam);
		String applicationType = json.getString("applicationType");
		String network = json.getString("network");
		return overviewService.queryTop10MemoryUsage(network, applicationType);
	}
	
	@RequestMapping("/top10DeviceIfUsage")
	@ResponseBody
	public List<Map<String ,Object>> queryTop10DeviceIfUsage(@RequestBody String queryParam){
		JSONObject json = JSONObject.parseObject(queryParam);
		String applicationType = json.getString("applicationType");
		String network = json.getString("network");
		return overviewService.queryTop10DeviceIfUsage(network, applicationType);
	}
	
	@RequestMapping("/top10DeviceIfFlow")
	@ResponseBody
	public List<Map<String ,Object>> queryTop10DeviceIfFlow(@RequestBody String queryParam){
		JSONObject json = JSONObject.parseObject(queryParam);
		String applicationType = json.getString("applicationType");
		String network = json.getString("network");
		return overviewService.queryTop10DeviceIfFlow(network, applicationType);
	}
	
	@RequestMapping("/top10DeviceIfErrors")
	@ResponseBody
	public List<Map<String ,Object>> queryTop10DeviceIfErrors(@RequestBody String queryParam){
		JSONObject json = JSONObject.parseObject(queryParam);
		String applicationType = json.getString("applicationType");
		String network = json.getString("network");
		return overviewService.queryTop10DeviceIfErrors(network, applicationType);
	}
	
	@RequestMapping("/top10DeviceIfDiscards")
	@ResponseBody
	public List<Map<String ,Object>> queryTop10DeviceIfDiscards(@RequestBody String queryParam){
		JSONObject json = JSONObject.parseObject(queryParam);
		String applicationType = json.getString("applicationType");
		String network = json.getString("network");
		return overviewService.queryTop10DeviceIfDiscards(network, applicationType);
	}
	
	@RequestMapping("/recentlyNetworkAlarm")
	@ResponseBody
	public List<Map<String ,Object>> queryRecentlyNetworkAlarm(@RequestBody String queryParam){
		JSONObject json = JSONObject.parseObject(queryParam);
		String applicationType = json.getString("applicationType");
		String network = json.getString("network");
		return overviewService.queryRecentlyNetworkAlarm(network, applicationType);
	}
	
	@RequestMapping("/resChangeRecord")
	@ResponseBody
	public List<Map<String ,String>> queryResChangeRecord(@RequestBody String queryParam){
		JSONObject json = JSONObject.parseObject(queryParam);
		String applicationType = json.getString("applicationType");
		String network = json.getString("network");
		return overviewService.queryResChangeRecord(network, applicationType);
	}
}
