package com.fable.insightview.monitor.networkchannel.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.networkchannel.mapper.NetworkMonitorviewMapper;
import com.fable.insightview.monitor.networkchannel.mapper.NetworkOverviewMapper;
import com.fable.insightview.monitor.networkchannel.service.INetworkMonitorviewService;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.sysconf.mapper.SysConfigMapper;

@Service
public class NetworkMonitorviewServiceImpl implements
		INetworkMonitorviewService {
	@Autowired
	private NetworkMonitorviewMapper monitorMapper;
	@Autowired
	private SysConfigMapper sysConfMapper;
	@Autowired
	private NetworkOverviewMapper overviewMapper;
	
	private static final DecimalFormat decimalFormat = new DecimalFormat("0.##");
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final SimpleDateFormat dateFormatMid = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private static final SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final Pattern p = Pattern.compile("\\d+(?:\\.\\d+)?");
	
	@Override
	public Map<String, Object> queryDeviceAvalibilityInfo(int deviceMoid,
			String timeBegin, String timeEnd ,String timeTip) {
		HashMap<String ,Object> params = new HashMap<String ,Object>();
		params.put("deviceMoid", deviceMoid);
		params.put("timeBegin", timeBegin);
		params.put("timeEnd" ,timeEnd);
		List<Map<String, Object>> avaList = monitorMapper.queryDeviceAvalibilityInfo(params);
		
		Map<String ,Object> resultMap = new HashMap<String ,Object>();
		long nonAvaTime = 0; //宕机时间
		String unavailableTime = "0 分钟";
		
		if(avaList != null && !avaList.isEmpty()){
			Map<String ,Object> pre = null;
			for(Map<String ,Object> ava : avaList){
				if(pre == null){
					pre = ava;
					continue;
				}
				if(pre.get("deviceStatus").toString().equals(KPINameDef.DEVICE_DOWN)){ //有down，则down之后的时间段计入宕机时间
					nonAvaTime += ((Date)ava.get("collectTime")).getTime() - ((Date)pre.get("collectTime")).getTime();
				}
				pre = ava;
			}
			unavailableTime = HostComm.getMsToTime(nonAvaTime); 
			
			Date begin = null;
			Date end = null;
			try {
				begin = dateFormat.parse(params.get("timeBegin").toString());
				end = dateFormat.parse(params.get("timeEnd").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			long totalTime = end.getTime() - begin.getTime();
			
			resultMap.put("value", decimalFormat.format(((1 - (nonAvaTime + 0.0) / totalTime)) * 100));
		}
		else{
			resultMap.put("value", "");
		}
		Matcher m = p.matcher(unavailableTime);
		
		if(m.find()){
			if(m.group().equals("0")){
				unavailableTime = "0" + "分钟";
			}
		}
		resultMap.put("time" ,unavailableTime);
		resultMap.put("rid" ,0);
		
		return resultMap;
	}
	
	@Override
	public Map<String ,Object> queryDeviceNameIp(int deviceMoid){
		return monitorMapper.queryDeviceNameIp(deviceMoid);
	}

	@Override
	public List<Map<String, Object>> queryImportantIfInfo(int deviceMoid) {
		HashMap<String ,Object> map = new HashMap<String ,Object>();
		map.put("deviceMoid", deviceMoid);
		String dataExpire = sysConfMapper.getConfParamValue(207, "DataExpire"); //数据有效时间
		if(StringUtils.isEmpty(dataExpire)){
			dataExpire = "2";
		}
		map.put("dataExpire", dataExpire);
		List<Map<String, Object>> list = monitorMapper.queryImportantIfInfo(map);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		LinkedHashMap<Integer ,Map<String ,String>> tempMap = new LinkedHashMap<Integer ,Map<String ,String>>();
		
		if(!CollectionUtils.isEmpty(list)){
			for(Map<String ,Object> listMap : list){
				int moid = Integer.parseInt(listMap.get("moid").toString());
				if(!tempMap.containsKey(moid)){
					HashMap<String ,String> resultMap = new HashMap<String ,String>();
					tempMap.put(moid, resultMap);
					resultMap.put("moid", listMap.get("moid").toString());
					resultMap.put("deviceMoid", listMap.get("deviceMOID").toString());
					resultMap.put("ifName", listMap.get("ifName").toString());
					resultMap.put("ifDescr", listMap.get("ifDescr").toString());
					resultMap.put("instance", listMap.get("instance").toString());
					resultMap.put("ifAlias", listMap.get("ifAlias").toString());
					resultMap.put("ifUsage","0");
					resultMap.put("inErrors" ,"0");
					resultMap.put("outErrors" ,"0");
					resultMap.put("errors" ,"0");
					resultMap.put("inDiscards" ,"0");
					resultMap.put("outDiscards" ,"0");
					resultMap.put("discards" ,"0");
					resultMap.put("ifStatus", "未知");
					
				}
				
				Map<String ,String> resultMap = tempMap.get(moid);
				String kpiName = listMap.get("kpiName").toString();
				
				if(KPINameDef.IFOPERSTATUS.equals(kpiName)){
					String opStatus = listMap.get("perfValue").toString();
					if (KPINameDef.DEVICE_UP.equals(opStatus)){
						resultMap.put("ifStatus", "up");
					}
					else if (KPINameDef.DEVICE_DOWN.equals(opStatus)){
						resultMap.put("ifStatus", "down");
					}
					else {
						resultMap.put("ifStatus", "未知");
					}
				}
				
				if(KPINameDef.IFUSAGE.equals(kpiName)){
					resultMap.put("ifUsage", decimalFormat.format(Float.parseFloat(listMap.get("perfValue").toString())));
				}
				else if(KPINameDef.INERRORS.equals(kpiName)){
					resultMap.put("inErrors", listMap.get("perfValue").toString());
					if(resultMap.get("outErrors") != null){
						resultMap.put("errors", String.valueOf(Integer.parseInt(listMap.get("perfValue").toString()) 
								+ Integer.parseInt(resultMap.get("outErrors"))));
					}
					else{
						resultMap.put("errors" ,listMap.get("perfValue").toString());
					}
				}
				else if(KPINameDef.OUTERRORS.equals(kpiName)){
					resultMap.put("outErrors", listMap.get("perfValue").toString());
					if(resultMap.get("inErrors") != null){
						resultMap.put("errors", String.valueOf(Integer.parseInt(listMap.get("perfValue").toString()) 
								+ Integer.parseInt(resultMap.get("inErrors").toString())));
					}
					else{
						resultMap.put("errors" ,listMap.get("perfValue").toString());
					}
				}
				else if(KPINameDef.INDISCARDS.equals(kpiName)){
					resultMap.put("inDiscards", listMap.get("perfValue").toString());
					if(resultMap.get("outDiscards") != null){
						resultMap.put("discards", String.valueOf(Integer.parseInt(listMap.get("perfValue").toString()) 
								+ Integer.parseInt(resultMap.get("outDiscards").toString())));
					}
					else{
						resultMap.put("discards" ,listMap.get("perfValue").toString());
					}
				}
				else if(KPINameDef.OUTDISCARDS.equals(kpiName)){
					resultMap.put("outDiscards", listMap.get("perfValue").toString());
					if(resultMap.get("inDiscards") != null){
						resultMap.put("discards", String.valueOf(Integer.parseInt(listMap.get("perfValue").toString()) 
								+ Integer.parseInt(resultMap.get("inDiscards"))));
					}
					else{
						resultMap.put("discards" ,listMap.get("perfValue").toString());
					}
				}
			}
			int index = 0;
			ArrayList<Map<String ,String>> tempList = new ArrayList<Map<String ,String>>();
			for(Map<String, String> tempResult : tempMap.values()){ //包装成前台要求的数据格式
				if(index % 4 == 0 && index / 4 > 0){
					Map<String ,Object> inner = new HashMap<String ,Object>();
					inner.put("data", tempList);
					resultList.add(inner);
					tempList = new ArrayList<Map<String ,String>>();
					index ++;
					tempResult.put("index", String.valueOf(index));
					tempList.add(tempResult);
				}
				else{
					index ++;
					tempResult.put("index", String.valueOf(index));
					tempList.add(tempResult);
				}
			}
			if(!tempList.isEmpty()){
				Map<String ,Object> inner = new HashMap<String ,Object>();
				inner.put("data", tempList);
				resultList.add(inner);
			}
		}
		return resultList;
	}

	@Override
	public Map<String, Object> queryImportantIfUsage(int deviceMoid,
			String timeBegin, String timeEnd ,String dataSplit) {
		HashMap<String ,Object> map = new HashMap<String ,Object>();
		map.put("deviceMoid", deviceMoid);
		map.put("timeBegin", timeBegin);
		map.put("timeEnd", timeEnd);
		int type = 0;
		
		List<Map<String ,Object>> list = monitorMapper.queryImportantIfUsage(map);
		if(null == list) return new HashMap<String ,Object>();	
		
		for(Map<String ,Object> listMap : list){
			if(listMap == null) continue;
			listMap.put("ifUsage", decimalFormat.format(Float.parseFloat(listMap.get("ifUsage").toString())));
		}
		list.remove(null);
		if(dataSplit.equalsIgnoreCase("C")){
			return getLineData(list ,"ifName");
		}
		
		if(dataSplit.equalsIgnoreCase("H")){
			type = 1;
		}
		else if(dataSplit.equalsIgnoreCase("D")){
			type = 2;
		}
		if(!list.isEmpty()){
			return getLineData(getDateSplitDataAvg(list ,"ifUsage" ,null ,"ifName" ,type) ,"ifName");
		}
		
		return new HashMap<String ,Object>();
	}

	@Override
	public List<Map<String, Object>> queryIfStatusList(int deviceMoid) {
		HashMap<String ,Object> map = new HashMap<String ,Object>();
		map.put("deviceMoid", deviceMoid);
		String dataExpire = sysConfMapper.getConfParamValue(207, "DataExpire");
		if(StringUtils.isEmpty(dataExpire)){
			dataExpire = "2";
		}
		map.put("dataExpire", dataExpire);
		
		List<Map<String ,Object>> ifList = monitorMapper.queryIfStatusList(map);
		HashMap<Integer ,Map<String ,String>> info = new HashMap<Integer ,Map<String ,String>>();
		Map<Integer, String> imMap = DictionaryLoader.getConstantItems("InterfaceType");// 接口类型
		Map<Integer, String> adminMap = DictionaryLoader.getConstantItems("IfAdminStatus");// 管理状态
		LinkedList<Map<String ,String>> resultList = new LinkedList<Map<String ,String>>();
		
		if(!CollectionUtils.isEmpty(ifList)){
			for(Map<String ,Object> item : ifList){
				int moid = Integer.parseInt(item.get("moid").toString());
				if(info.get(moid) == null){
					Map<String ,String> resultMap = new HashMap<String ,String>();
					info.put(moid, resultMap);
					resultMap.put("moid", String.valueOf(moid));
					resultMap.put("deviceMoid", item.get("deviceMOID").toString());
					resultMap.put("ifName", item.get("ifName").toString());
					resultMap.put("ifDescr", item.get("ifDescr").toString());
					resultMap.put("ifAlias" ,item.get("ifAlias").toString());
					resultMap.put("ifType", imMap.get(Integer.parseInt(item.get("ifType").toString())));
				}
				Map<String ,String> resultMap = info.get(moid);
				String kpiName = item.get("kpiName").toString();
				String kpiValue = item.get("perfValue").toString();
				if(KPINameDef.IFOPERSTATUS.equals(kpiName)){
					if(KPINameDef.DEVICE_UP.equals(kpiValue)){
						resultMap.put("operStatus", "up");
						resultMap.put("operator", kpiValue);
					}
					else if(KPINameDef.DEVICE_DOWN.equals(kpiValue)){
						resultMap.put("operStatus", "down");
						resultMap.put("operator", kpiValue);
					}
					else {
						resultMap.put("operStatus", "未知");
						resultMap.put("operator", "1.5");
					}
				}
				if("ifAdminStatus".equals(kpiName)){
					resultMap.put("adminStatus", adminMap.get(Integer.parseInt(kpiValue)));
					resultMap.put("ifAdminStatus", kpiValue);
				}
			}
			resultList.addAll(info.values());
			for(int i=resultList.size();i>=1;i--){
				for(int j=0;j<i-1;j++){
					Map<String ,String> curr = resultList.get(j);
					Map<String ,String> next = resultList.get(j+1);
					if(Float.parseFloat(curr.get("operator")) < Float.parseFloat(next.get("operator"))){
						resultList.set(j, next);
						resultList.set(j+1, curr);
					}
				}
			}
		}
		List<MODevice> ifInfoList = monitorMapper.queryDeviceIfInfo(deviceMoid);
		Set<Integer> infoKey = info.keySet();
		for(MODevice device : ifInfoList){
			HashMap<String ,String> resultMap = new HashMap<String ,String>();
			resultMap.put("moid", device.getMoid().toString());
			resultMap.put("deviceMoid", device.getDevicemoid().toString());
			resultMap.put("ifName", device.getIfname());
			resultMap.put("ifDescr", device.getIfdescr());
			resultMap.put("ifAlias", device.getIfalias());
			resultMap.put("ifType", imMap.get(device.getIftype()));
			
			if(!infoKey.contains(device.getMoid()) && 0 == device.getIsCollect()){ //接口设置成不采集，即未监测
				resultMap.put("operStatus", "未监测");
				resultMap.put("operator", "4");
				resultMap.put("adminStatus", "未监测");
				resultMap.put("ifAdminStatus", "4");
				resultList.add(resultMap);
			}
			else if(!infoKey.contains(device.getMoid()) && 1 == device.getIsCollect()){ //接口设置成采集，但是为监测到，为未知
				resultMap.put("operStatus", "未知");
				resultMap.put("operator", "1.5");
				resultMap.put("adminStatus", "未知");
				resultMap.put("ifAdminStatus", "1.5");
				resultList.add(resultMap);
			}
		}
		
		ArrayList<Map<String ,Object>> result = new ArrayList<Map<String ,Object>>();
		for(Map<String ,String> resultMap : resultList){
			boolean operat = resultMap.get("operator").equals("4");
			Map<String ,Object> inner = new HashMap<String ,Object>();
			inner.put("statusText", resultMap.get("operStatus"));
			inner.put("status", resultMap.get("operator"));
			ArrayList<Map<String ,String>> data = new ArrayList<Map<String ,String>>();
			Map<String ,String> dataMap = new HashMap<String ,String>();
			dataMap.put("label", "接口名称");
			dataMap.put("value", resultMap.get("ifName"));
			data.add(dataMap);
			
			dataMap = new HashMap<String ,String>();
			dataMap.put("label", "接口别名");
			dataMap.put("value", resultMap.get("ifAlias"));
			data.add(dataMap);
			
			dataMap = new HashMap<String ,String>();
			dataMap.put("label", "接口描述");
			dataMap.put("value", resultMap.get("ifDescr"));
			data.add(dataMap);
			
			dataMap = new HashMap<String ,String>();
			dataMap.put("label", "接口类型");
			dataMap.put("value", resultMap.get("ifType"));
			data.add(dataMap);
			
			dataMap = new HashMap<String ,String>();
			dataMap.put("label", "管理状态");
			if(operat){
				dataMap.put("value", resultMap.get("adminStatus"));
			}
			else{
				dataMap.put("text", resultMap.get("adminStatus"));
				dataMap.put("value", resultMap.get("adminStatus"));
			}
			data.add(dataMap);
			
			dataMap = new HashMap<String ,String>();
			dataMap.put("label", "操作状态");
			if(operat){
				dataMap.put("value", resultMap.get("operStatus"));
			}
			else{
				dataMap.put("text", resultMap.get("operStatus"));
				dataMap.put("value", resultMap.get("operStatus"));
			}
			data.add(dataMap);
			
			inner.put("data", data);
			result.add(inner);
		}
		
		return result;
	}
	

	@Override
	public List<MODevice> queryDeviceIfInfo(int deviceMoid) {
		return monitorMapper.queryDeviceIfInfo(deviceMoid);
	}

	@Override
	public List<Map<String, String>> queryIfAlarm(Page<Map<String ,Object>> page) {
		return monitorMapper.queryIfAlarm(page);
	}

	@Override
	public List<Map<String, Object>> queryTop10Usage24h(int deviceMoid,
			String tableName, String tableName1, String kpiName) {
		HashMap<String ,Object> map = new HashMap<String ,Object>();
		map.put("deviceMoid", deviceMoid);
		map.put("tableName", tableName);
		map.put("tableName1", tableName1);
		map.put("kpiName", kpiName);
		List<Map<String, Object>> resultList = monitorMapper.queryTop10Usage24h(map);
		for(Map<String, Object> result : resultList){
			if(result != null){
				if(result.get("avgUsage") != null){
					result.put("avgUsage", decimalFormat.format(Float.parseFloat(result.get("avgUsage").toString())));
				}
			}
		}
		resultList.remove(null);
		
		return resultList;
	}

	@Override
	public Map<String ,Object> queryMemUsage(int deviceMoid){
		HashMap<String ,Object> map = new HashMap<String ,Object>();
		map.put("deviceMoid", deviceMoid);
		String dataExpire = sysConfMapper.getConfParamValue(207, "DataExpire");
		if(StringUtils.isEmpty(dataExpire)){
			dataExpire = "2";
		}
		map.put("dataExpire", dataExpire);
		map.put("kpiName", KPINameDef.NETMEMUSAGE);
		
		Float memUsage = monitorMapper.queryDeviceCurrentUsage(map);
		String memoryUsage = "";
		if(memUsage != null){
			memoryUsage = decimalFormat.format(memUsage);
		}
		String phyMemSize = this.queryDevicePhyMem(deviceMoid);
		List<Map<String ,Object>> top10MemUsage = queryTop10Usage24h(deviceMoid ,"PerfNetworkMemory" ,"MOMemories" ,"PerMemoryUsage");
		
		HashMap<String ,Object> result = new HashMap<String ,Object>();
		result.put("memoryUsage", memoryUsage);
		result.put("phyMemSize", phyMemSize);
		result.put("top10MemUsage", top10MemUsage);
		
		return result;
	}
	
	@Override
	public Map<String ,Object> queryCPUsage(int deviceMoid){
		HashMap<String ,Object> map = new HashMap<String ,Object>();
		map.put("deviceMoid", deviceMoid);
		String dataExpire = sysConfMapper.getConfParamValue(207, "DataExpire");
		if(StringUtils.isEmpty(dataExpire)){
			dataExpire = "2";
		}
		map.put("dataExpire", dataExpire);
		map.put("kpiName", KPINameDef.CPUSAGE);
		
		Float cpu = monitorMapper.queryDeviceCurrentUsage(map);
		String cpuUsage = "";
		if(cpu != null){
			cpuUsage = decimalFormat.format(cpu);
		}
		List<Map<String ,Object>> top10CpuUsage = queryTop10Usage24h(deviceMoid ,"PerfNetworkCPU" ,"MOCPUs", "PerUsage");
		
		HashMap<String ,Object> result = new HashMap<String ,Object>();
		result.put("cpuUsage", cpuUsage);
		result.put("top10CpuUsage", top10CpuUsage);
		
		return result;
	}

	@Override
	public String queryDevicePhyMem(int deviceMoid) {
		return HostComm.getBytesToSize(monitorMapper.queryDevicePhyMem(deviceMoid));
	}

	@Override
	public List<Map<String, Object>> queryUsageLine(int deviceMoid,
			String timeBegin, String timeEnd, String tableName, String tableName1 , String kpiName) {
		HashMap<String ,Object> map = new HashMap<String ,Object>();
		map.put("deviceMoid", deviceMoid);
		map.put("timeBegin", timeBegin);
		map.put("timeEnd", timeEnd);
		map.put("tableName", tableName);
		map.put("tableName1", tableName1);
		map.put("kpiName", kpiName);
		if(kpiName.equals("perUsage")){
			map.put("kpiNameOrder", "cpusage");
		}
		else if(kpiName.equals("perMemoryUsage")){
			map.put("kpiNameOrder", "memoryUsage");
		}
		
		return monitorMapper.queryUsageLine(map);
	}
	
	@Override
	public Map<String ,Object> queryCpuUsageLine(int deviceMoid,
			String timeBegin, String timeEnd ,String dataSplit){
		List<Map<String ,Object>> resultList = queryUsageLine(deviceMoid, timeBegin ,timeEnd ,"PerfNetworkCPU" ,"MOCPUs", "perUsage");
		if(null == resultList) return new HashMap<String ,Object>(); 
		for(Map<String ,Object> map : resultList){
			if(map == null) continue;
			map.put("perUsage", decimalFormat.format(Float.parseFloat(map.get("perUsage").toString())));
			map.put("cpusage", decimalFormat.format(Float.parseFloat(map.get("cpusage").toString())));
		}
		resultList.remove(null);
		int type = 0;
		
		if(dataSplit.equalsIgnoreCase("C")){
			return getLineData(resultList ,"moName");
		}
		
		if(dataSplit.equalsIgnoreCase("H")){
			type = 1;
		}
		else if(dataSplit.equalsIgnoreCase("D")){
			type = 2;
		}
		if(!resultList.isEmpty()){
			return getLineData(getDateSplitDataAvg(resultList ,"perUsage" ,"cpusage" , "moName",type) ,"moName");
		}
		return new HashMap<String ,Object>();
	}
	
	@Override
	public Map<String ,Object> queryMemUsageLine(int deviceMoid,
			String timeBegin, String timeEnd ,String dataSplit){
		List<Map<String ,Object>> resultList = queryUsageLine(deviceMoid, timeBegin ,timeEnd ,"PerfNetworkMemory" ,"MOMemories" ,"perMemoryUsage");
		if(CollectionUtils.isEmpty(resultList)) return new HashMap<String ,Object>(); 
		for(Map<String ,Object> map : resultList){
			if(map == null) continue;
			map.put("perMemoryUsage", decimalFormat.format(Float.parseFloat(map.get("perMemoryUsage").toString())));
			map.put("memoryUsage", decimalFormat.format(Float.parseFloat(map.get("memoryUsage").toString())));
		}
		resultList.remove(null);
		int type = 0;
		
		if(dataSplit.equalsIgnoreCase("C")){
			return getLineData(resultList ,"moName");
		}
		
		if(dataSplit.equalsIgnoreCase("H")){
			type = 1;
		}
		else if(dataSplit.equalsIgnoreCase("D")){
			type = 2;
		}
		if(!resultList.isEmpty()){
			return getLineData(getDateSplitDataAvg(resultList ,"perMemoryUsage" ,"memoryUsage" ,"moName" ,type) ,"moName");
		}
		return new HashMap<String ,Object>();
	}

	@Override
	public List<Map<String, Object>> queryTop10IfUsage(int deviceMoid) {
		HashMap<String ,String> map = new HashMap<String ,String>();
		String dataExpire = sysConfMapper.getConfParamValue(207, "DataExpire");
		if(StringUtils.isEmpty(dataExpire)){
			dataExpire = "2";
		}
		map.put("dataExpire", dataExpire);
		map.put("deviceMoid", String.valueOf(deviceMoid));
		map.put("kpiNameOrder", KPINameDef.IFUSAGE);
		map.put("kpiName2", KPINameDef.INUSAGE);
		map.put("kpiName3", KPINameDef.OUTUSAGE);
		map.put("kpiAlias1", "ifUsage");
		map.put("kpiAlias2", "inUsage");
		map.put("kpiAlias3", "outUsage");
		
		List<Map<String ,Object>> resultList = overviewMapper.queryTop10DeviceIfInfo(map);
		for(Map<String ,Object> result : resultList){
			if(null == result) continue;
			if(result.get("ifUsage") == null){
				result.put("ifUsage" ,0); 
			}
			if(result.get("inUsage") == null){
				result.put("inUsage" ,0); 
			}
			if(result.get("outUsage") == null){
				result.put("outUsage" ,0); 
			}
			result.put("ifUsage", decimalFormat.format(Float.parseFloat(result.get("ifUsage").toString())));
			result.put("inUsage", decimalFormat.format(Float.parseFloat(result.get("inUsage").toString())));
			result.put("outUsage", decimalFormat.format(Float.parseFloat(result.get("outUsage").toString())));
		}
		resultList.remove(null);
		return resultList;
	}

	@Override
	public List<Map<String, Object>> queryTop10IfDiscardsAndErrors(
			int deviceMoid) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		String dataExpire = sysConfMapper.getConfParamValue(207, "DataExpire");
		if(StringUtils.isEmpty(dataExpire)){
			dataExpire = "2";
		}
		map.put("dataExpire", dataExpire);
		map.put("deviceMoid", deviceMoid);
		List<Map<String, Object>> resultList = monitorMapper.queryTop10IfDiscardsAndErrors(map);
		if(null == resultList) 
			return new ArrayList<Map<String, Object>>();
		
		resultList.remove(null);
		for(Map<String ,Object> result : resultList){
			Set<Entry<String, Object>> entrySet = result.entrySet();
			for(Entry<String, Object> entry : entrySet){
				Object obj = entry.getValue();
				if(obj instanceof Double){
					result.put(entry.getKey(), ((Double) obj).intValue());
				}
			}
		}
		return resultList;
	}

	private List<Map<String ,Object>> getDateSplitDataAvg(List<Map<String ,Object>> list ,String valueName ,String valueName1 ,String fieldName ,int type){
		Calendar collectTime = Calendar.getInstance();
		List<Map<String ,Object>> resultList = new ArrayList<Map<String ,Object>>();
		Map<String ,Map<String ,Object>> moMap = new HashMap<String ,Map<String ,Object>>();
		Map<String ,Float> resultAvgMap = new HashMap<String ,Float>();
		Map<String ,Float> resultAvgMap1 = new HashMap<String ,Float>();
		HashMap<String ,String> moidName = new HashMap<String ,String>();
		int preHour = -1;
		int preDay = -1;
		Calendar current = Calendar.getInstance();
		int dataSize = 0 ,moSize = 0;
		
		for(int i=0,len=list.size();i<len;i++){
			Map<String ,Object> result = list.get(i);
			if(1 == type){
				collectTime.setTime((Date) result.get("collectTime"));
				int hour = collectTime.get(Calendar.HOUR_OF_DAY);
				if(-1 == preHour){
					preHour = hour;
				}
				if(preHour == hour){
					String moid = result.get("moid").toString();
					float value = 0;
					float value1 = 0;
					if(result.get(valueName) != null){
						value = Float.parseFloat(result.get(valueName).toString());
					}
					if(!moMap.containsKey(moid)){
						moSize ++;
						moMap.put(moid, result);
						moidName.put(moid ,result.get(fieldName).toString());
					}
					if(!resultAvgMap.containsKey(moid)){
						resultAvgMap.put(moid, value);
					}
					else{
						resultAvgMap.put(moid, resultAvgMap.get(moid)+value);
					}
					
					if(valueName1 != null && result.get(valueName1) != null){
						value1 = Float.parseFloat(result.get(valueName1).toString());
					}
					if(valueName1 != null){
						if(!resultAvgMap1.containsKey(moid)){
							resultAvgMap1.put(moid, value1);
						}
						else{
							resultAvgMap1.put(moid, resultAvgMap1.get(moid)+value1);
						}
					}
					dataSize ++;
				}
				else{
					String collTime = "";
					for(String key : moMap.keySet()){
						Map<String ,Object> temp = moMap.get(key);
						collectTime.setTime((Date) temp.get("collectTime"));
						collectTime.set(Calendar.MINUTE, current.get(Calendar.MINUTE));
						collTime = dateFormatMid.format(collectTime.getTime());
						temp.put(valueName, decimalFormat.format(resultAvgMap.get(key) / dataSize * moSize));
						temp.put("collectTime", collTime);
						if(!resultAvgMap1.isEmpty() && valueName1 != null){
							temp.put(valueName1, decimalFormat.format(resultAvgMap1.get(key) / dataSize * moSize));
						}
						resultList.add(temp);
					}
					for(String key : moidName.keySet()){
						Set<String> moids = moMap.keySet();
						if(!moids.contains(key)){
							Map<String ,Object> temp = new HashMap<String ,Object>();
							temp.put("collectTime", collTime);
							temp.put(valueName, 0);
							if(valueName1 != null){
								temp.put(valueName1, 0);
							}
							temp.put("moid", key);
							temp.put(fieldName, moidName.get(key));
							resultList.add(temp);
						}
					}
					moMap.clear();
					resultAvgMap.clear();
					resultAvgMap1.clear();
					dataSize = 0;
					moSize = 0;
					preHour = hour;
					
					String moid = result.get("moid").toString();
					float value = 0;
					float value1 = 0;
					if(result.get(valueName) != null){
						value = Float.parseFloat(result.get(valueName).toString());
					}
					if(!moMap.containsKey(moid)){
						moSize ++;
						moMap.put(moid, result);
						moidName.put(moid ,result.get(fieldName).toString());
					}
					if(!resultAvgMap.containsKey(moid)){
						resultAvgMap.put(moid, value);
					}
					else{
						resultAvgMap.put(moid, resultAvgMap.get(moid)+value);
					}
					
					if(valueName1 != null && result.get(valueName1) != null){
						value1 = Float.parseFloat(result.get(valueName1).toString());
					}
					if(valueName1 != null){
						if(!resultAvgMap1.containsKey(moid)){
							resultAvgMap1.put(moid, value1);
						}
						else{
							resultAvgMap1.put(moid, resultAvgMap1.get(moid)+value1);
						}
					}
					dataSize ++;
				}
			}
			else if(2 == type){
				collectTime.setTime((Date) result.get("collectTime"));
				int day = collectTime.get(Calendar.DAY_OF_YEAR);
				if(-1 == preDay){
					preDay = day;
				}
				if(preDay == day){
					String moid = result.get("moid").toString();
					float value = 0;
					float value1 = 0;
					if(result.get(valueName) != null){
						value = Float.parseFloat(result.get(valueName).toString());
					}
					if(!moMap.containsKey(moid)){
						moSize ++;
						moMap.put(moid, result);
						moidName.put(moid ,result.get(fieldName).toString());
					}
					if(!resultAvgMap.containsKey(moid)){
						resultAvgMap.put(moid, value);
					}
					else{
						resultAvgMap.put(moid, resultAvgMap.get(moid)+value);
					}
					
					if(valueName1 != null && result.get(valueName1) != null){
						value1 = Float.parseFloat(result.get(valueName1).toString());
					}
					if(valueName1 != null){
						if(!resultAvgMap1.containsKey(moid)){
							resultAvgMap1.put(moid, value1);
						}
						else{
							resultAvgMap1.put(moid, resultAvgMap1.get(moid)+value1);
						}
					}
					dataSize ++;
				}
				else{
					String collTime = "";
					for(String key : moMap.keySet()){
						Map<String ,Object> temp = moMap.get(key);
						collectTime.setTime((Date) temp.get("collectTime"));
						collTime = dateFormatShort.format(collectTime.getTime());
						temp.put(valueName, decimalFormat.format(resultAvgMap.get(key) / dataSize * moSize));
						temp.put("collectTime", collTime);
						if(!resultAvgMap1.isEmpty() && valueName1 != null){
							temp.put(valueName1, decimalFormat.format(resultAvgMap1.get(key) / dataSize * moSize));
						}
						resultList.add(temp);
					}
					for(String key : moidName.keySet()){
						Set<String> moids = moMap.keySet();
						if(!moids.contains(key)){
							Map<String ,Object> temp = new HashMap<String ,Object>();
							temp.put("collectTime", collTime);
							temp.put(valueName, 0);
							if(valueName1 != null){
								temp.put(valueName1, 0);
							}
							temp.put("moid", key);
							temp.put(fieldName, moidName.get(key));
							resultList.add(temp);
						}
					}
					
					moMap.clear();
					resultAvgMap.clear();
					resultAvgMap1.clear();
					dataSize = 0;
					moSize = 0;
					preDay = day;
					
					String moid = result.get("moid").toString();
					float value = 0;
					float value1 = 0;
					if(result.get(valueName) != null){
						value = Float.parseFloat(result.get(valueName).toString());
					}
					if(!moMap.containsKey(moid)){
						moSize ++;
						moMap.put(moid, result);
						moidName.put(moid ,result.get(fieldName).toString());
					}
					if(!resultAvgMap.containsKey(moid)){
						resultAvgMap.put(moid, value);
					}
					else{
						resultAvgMap.put(moid, resultAvgMap.get(moid)+value);
					}
					
					if(valueName1 != null && result.get(valueName1) != null){
						value1 = Float.parseFloat(result.get(valueName1).toString());
					}
					if(valueName1 != null){
						if(!resultAvgMap1.containsKey(moid)){
							resultAvgMap1.put(moid, value1);
						}
						else{
							resultAvgMap1.put(moid, resultAvgMap1.get(moid)+value1);
						}
					}
					dataSize ++;
				}
			}
			
		}
		if(0 != dataSize){ //最后结尾数据或者是都是一个时间点
			String collTime = "";
			for(String key : moMap.keySet()){
				Map<String ,Object> temp = moMap.get(key);
				collectTime.setTime((Date) temp.get("collectTime"));
				if(1 == type){
					collectTime.set(Calendar.MINUTE, current.get(Calendar.MINUTE));
					temp.put("collectTime", dateFormatMid.format(collectTime.getTime()));
					collTime = dateFormatMid.format(collectTime.getTime());
				}
				else if(2 == type){
					temp.put("collectTime", dateFormatShort.format(collectTime.getTime()));
					collTime = dateFormatShort.format(collectTime.getTime());
				}
				temp.put(valueName, decimalFormat.format(resultAvgMap.get(key) / dataSize * moSize));
				if(!resultAvgMap1.isEmpty() && valueName1 != null){
					temp.put(valueName1, decimalFormat.format(resultAvgMap1.get(key) / dataSize * moSize));
				}
				resultList.add(temp);
			}
			for(String key : moidName.keySet()){
				Set<String> moids = moMap.keySet();
				if(!moids.contains(key)){
					Map<String ,Object> temp = new HashMap<String ,Object>();
					temp.put("collectTime", collTime);
					temp.put(valueName, 0);
					if(valueName1 != null){
						temp.put(valueName1, 0);
					}
					temp.put("moid", key);
					temp.put(fieldName, moidName.get(key));
					resultList.add(temp);
				}
			}
			moMap.clear();
			resultAvgMap.clear();
			resultAvgMap1.clear();
			dataSize = 0;
			moSize = 0;
		}
		
		return resultList;
	}
	
	private Map<String ,Object> getLineData(List<Map<String ,Object>> list ,String typeName){
		if(list.isEmpty())
			return new HashMap<String ,Object>();
		TreeMap<String ,List<Map<String ,Object>>> sortedKeyMap = new TreeMap<String ,List<Map<String ,Object>>>();
		List<Map<String ,Object>> series = new LinkedList<Map<String ,Object>>();
		LinkedHashSet<String> xAxis = new LinkedHashSet<String>();
		TreeSet<String> legend = new TreeSet<String>();
		
		for(Map<String ,Object> item : list){
			Object time = item.get("collectTime");
			legend.add(item.get(typeName).toString());
			
			String collectTime = "";
			if(time instanceof Date){
				collectTime = dateFormat.format((Date) time);
				item.put("collectTime", collectTime);
			}
			else{
				collectTime = time.toString();
			}
			xAxis.add(collectTime);
			if(!sortedKeyMap.containsKey(collectTime)){
				sortedKeyMap.put(collectTime, new ArrayList<Map<String ,Object>>());
			}
			
			List<Map<String ,Object>> dataList = sortedKeyMap.get(collectTime);
			dataList.add(item);
		}
		
		List<List<Map<String ,Object>>> values = new ArrayList<List<Map<String ,Object>>>(sortedKeyMap.values());
		for(int i=0,len=values.size();i<len;i++){
			series.addAll(values.get(i));
		}
		
		Map<String ,Object> resultMap = new HashMap<String ,Object>();
		resultMap.put("legend", legend);
		resultMap.put("xAxis", new ArrayList<String>(sortedKeyMap.keySet()));
		resultMap.put("series", series);
		return resultMap;
	}
}
