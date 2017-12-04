package com.fable.insightview.monitor.networkchannel.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.networkchannel.mapper.NetworkOverviewMapper;
import com.fable.insightview.monitor.networkchannel.service.INetworkOverviewService;
import com.fable.insightview.platform.sysconf.mapper.SysConfigMapper;

@Service
public class NetworkOverviewServiceImpl implements INetworkOverviewService {
	@Autowired
	private NetworkOverviewMapper overviewMapper;
	@Autowired
	private SysConfigMapper sysConfMapper;
	
	private static final DecimalFormat decimalFormat = new DecimalFormat("0.##");
	
	private static final int bps2MBps = 8 * 1000 * 1000;
	
	@Override
	public List<Map<String, Object>> queryTop10CPUsage(String network,
			String applicationType) {
		HashMap<String ,String> map = getInitQueryMap(network ,applicationType);
		map.put("kpiName", KPINameDef.CPUSAGE);
		map.put("kpiAlias", "cpuUsage");
		List<Map<String, Object>> resultList = overviewMapper.queryTop10CPUMemsage(map);
		
		for(Map<String ,Object> result : resultList){
			if(null == result) continue;
			result.put("cpuUsage", decimalFormat.format(Float.parseFloat(result.get("cpuUsage").toString())));
		}
		resultList.remove(null);
		return resultList;
	}

	@Override
	public List<Map<String, Object>> queryTop10MemoryUsage(String network,
			String applicationType) {
		HashMap<String ,String> map = getInitQueryMap(network ,applicationType);
		map.put("kpiName", KPINameDef.NETMEMUSAGE);
		map.put("kpiAlias", "memUsage");
		
		List<Map<String, Object>> resultList = overviewMapper.queryTop10CPUMemsage(map);
		
		for(Map<String ,Object> result : resultList){
			if(null == result) continue;
			result.put("memUsage", decimalFormat.format(Float.parseFloat(result.get("memUsage").toString())));
		}
		resultList.remove(null);
		
		return resultList;
	}

	@Override
	public List<Map<String, Object>> queryTop10DeviceIfUsage(String network,
			String applicationType) {
		HashMap<String ,String> map = getInitQueryMap(network ,applicationType);
		map.put("kpiNameOrder", KPINameDef.IFUSAGE);
		map.put("kpiName2", KPINameDef.INUSAGE);
		map.put("kpiName3", KPINameDef.OUTUSAGE);
		map.put("kpiAlias1", "ifUsage");
		map.put("kpiAlias2", "inUsage");
		map.put("kpiAlias3", "outUsage");
		
		List<Map<String, Object>> resultList = overviewMapper.queryTop10DeviceIfInfo(map);
		for(Map<String ,Object> result : resultList){
			if(null == result) continue;
			if(null == result.get("inUsage")){
				result.put("inUsage" ,0);
			}
			if(null == result.get("outUsage")){
				result.put("outUsage" ,0);
			}
			if(null == result.get("ifUsage")){
				result.put("ifUsage" ,0);
			}
			result.put("ifUsage" ,decimalFormat.format(Float.parseFloat(result.get("ifUsage").toString())));
			result.put("inUsage", decimalFormat.format(Float.parseFloat(result.get("inUsage").toString())));
			result.put("outUsage", decimalFormat.format(Float.parseFloat(result.get("outUsage").toString())));
		}
		
		return resultList;
	}

	@Override
	public List<Map<String, Object>> queryTop10DeviceIfFlow(String network,
			String applicationType) {
		HashMap<String ,String> map = getInitQueryMap(network ,applicationType);
		map.put("kpiNameOrder", KPINameDef.OUTFLOWS);
		map.put("kpiName2", KPINameDef.INFLOWS);
		map.put("kpiAlias1", "outFlow");
		map.put("kpiAlias2", "inFlow");

		List<Map<String, Object>> resultList = overviewMapper.queryTop10DeviceIfInfo(map);
		for(Map<String ,Object> result : resultList){
			if(null == result) continue;
			result.put("inFlow", decimalFormat.format(Float.parseFloat(result.get("inFlow").toString()) / bps2MBps));
			result.put("outFlow", decimalFormat.format(Float.parseFloat(result.get("outFlow").toString()) / bps2MBps));
		}
		
		return resultList;
	}

	@Override
	public List<Map<String, Object>> queryTop10DeviceIfErrors(String network,
			String applicationType) {
		HashMap<String ,String> map = getInitQueryMap(network ,applicationType);
		map.put("kpiNameOrder", KPINameDef.OUTERRORS);
		map.put("kpiName2", KPINameDef.INERRORS);
		map.put("kpiAlias1", "outErrors");
		map.put("kpiAlias2", "inErrors");
		return overviewMapper.queryTop10DeviceIfInfo(map);
	}

	@Override
	public List<Map<String, Object>> queryTop10DeviceIfDiscards(String network,
			String applicationType) {
		HashMap<String ,String> map = getInitQueryMap(network ,applicationType);
		map.put("kpiNameOrder", KPINameDef.OUTDISCARDS);
		map.put("kpiName2", KPINameDef.INDISCARDS);
		map.put("kpiAlias1", "outDiscards");
		map.put("kpiAlias2", "inDiscards");
		return overviewMapper.queryTop10DeviceIfInfo(map);
	}

	@Override
	public List<Map<String, Object>> queryRecentlyNetworkAlarm(String network,
			String applicationType) {
		HashMap<String ,String> map = getInitQueryMap(network ,applicationType);
		List<Map<String ,String>> resultList = overviewMapper.queryRecentlyNetworkAlarm(map);
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		for(Map<String ,String> result : resultList){
			if(result != null){
				Map<String ,Object> data = new HashMap<String ,Object>();
				Map<String ,Object> rate = new HashMap<String ,Object>();
				
				rate.put("value", result.get("alarmLevel"));
				rate.put("label", result.get("alarmLevelName"));
				
				data.put("rate" ,rate);
				data.put("title", result.get("alarmTitle"));
				data.put("sourceMOName", result.get("sourceMOName"));
				data.put("sourceMOIP", result.get("sourceMOIPAddress"));
				data.put("operateStatus", result.get("alarmOperateStatus"));
				data.put("startTime", result.get("startTime"));
				data.put("operStatusName", result.get("operStatusName"));
				data.put("resName", result.get("resName"));
				data.put("deviceMoid", result.get("deviceMoid"));
				
				datas.add(data);
			}
		}
		
		return datas;
	}

	@Override
	public List<Map<String, String>> queryResChangeRecord(String network,
			String applicationType) {
		HashMap<String ,String> map = getInitQueryMap(network ,applicationType);
		return overviewMapper.queryResChangeRecord(map);
	}
	
	private HashMap<String ,String> getInitQueryMap(String network,
			String applicationType) {
		HashMap<String ,String> map = new HashMap<String ,String>();
		map.put("network", network);
		map.put("applicationType", applicationType);
		
		String dataExpire = sysConfMapper.getConfParamValue(207, "DataExpire");
		if(StringUtils.isEmpty(dataExpire)){
			dataExpire = "2";
		}
		map.put("dataExpire", dataExpire);
		
		return map;
	}

}
