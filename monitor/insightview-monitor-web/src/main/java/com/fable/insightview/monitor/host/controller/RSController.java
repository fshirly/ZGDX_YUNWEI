package com.fable.insightview.monitor.host.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.host.entity.CurveObj;
import com.fable.insightview.monitor.host.entity.ECharObj;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.host.entity.Mine2Obj;
import com.fable.insightview.monitor.host.entity.MineObj;
import com.fable.insightview.monitor.host.entity.PerfNetworkCPU;
import com.fable.insightview.monitor.host.entity.PerfNetworkMemory;
import com.fable.insightview.monitor.host.entity.PerfSnapsBean;
import com.fable.insightview.monitor.host.mapper.HostMapper;
import com.fable.insightview.monitor.perf.service.IPerfGeneralConfigService;
import com.fable.insightview.monitor.utils.DecimalForTwo;
 

@Controller
@RequestMapping("/monitor/rsManage")
public class RSController {
	@Autowired
	HostMapper modMaper;
	@Autowired
	IPerfGeneralConfigService configService;
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final Logger logger = LoggerFactory.getLogger(RSController.class);
	String moClass;// 设备类型ID
	String tableName;// 表面
	Integer MOID;
	Integer moClassID;
	String cpuUrl;// CPU 曲线跳转地址
	String memUrl;// 内存 曲线跳转地址
	String diskUrl;// 硬盘 曲线跳转地址
	String storeUrl;// 数据存储 曲线跳转地址
	String ifTableName;// 接口表名
	DecimalForTwo decimalForTwo = new DecimalForTwo();
	/**
	 * 根据类型获取moClassID
	 * @param moClass
	 */
	public Integer getmoClassID(String moClass) {
		if ("host".equals(moClass) || "null".equals(moClass) || moClass == null) {
			moClassID = 7;
		} else if ("vm".equals(moClass)) {
			moClassID = 9;
		} else if ("vhost".equals(moClass)) {
			moClassID = 8;
		} else if ("switcher".equals(moClass)) {
			moClassID = 6;
		} else if ("router".equals(moClass)) {
			moClassID = 5;
		} else if ("switcherl2".equals(moClass)) {
			moClassID = 59;
		}else if ("switcherl3".equals(moClass)) {
			moClassID = 60;
		}
		return moClassID;
	}
	/**
	 * ***********************************路由器交换机********************************
	 */
	/**
	 * 跳转至最近告警列表页
	 */
	@RequestMapping("/toRSAlarmActiveInfo")
	public ModelAndView toRSAlarmActiveInfo(HttpServletRequest request) {
		moClass = request.getParameter("moClass");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		return new ModelAndView("monitor/host/rsalarmActiveInfoList");
	}

	/**
	 * 路由器交换机 最新告警
	 */
	@RequestMapping("/topRSAlarmActiveInfo")
	@ResponseBody
	public Map<String, Object> topRSAlarmActiveInfo() throws Exception {
		logger.info("开始...加载TOP5最新告警页面数据");
		Map<String, Object> result = new HashMap<String, Object>();
		getmoClassID(moClass);
		try {
			List<AlarmActiveDetail> moLsinfo = modMaper
					.getRSAlarmActive(moClassID);
			if (moLsinfo.size() >= 5) {
				moLsinfo = moLsinfo.subList(0, 5);
			}
			result.put("rows", moLsinfo);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage());
		}

		logger.info("结束...加载TOP5最新告警页面数据");
		return result;
	}

	/**
	 * 跳转至设备快照列表页
	 */
	@RequestMapping("/toRsSnapshotInfo")
	public ModelAndView toRsSnapshotInfo(HttpServletRequest request) {
		String moClass = request.getParameter("moClass");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moClass", moClass);
		return new ModelAndView("monitor/host/rsSnapshotInfoList");
	}

	/**
	 * 设备快照
	 */
	@RequestMapping("/getRsSnapshotInfo")
	@ResponseBody
	public Map<String, Object> getRsSnapshotInfo() throws Exception {
		logger.info("开始...加载路由器 交换机设备快照页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		String moClass = request.getParameter("moClass");
		String moClassID="";
		String deviceMoClassID ="";
		List<MODevice> moLsCount =null;
		if("switcher".equals(moClass)){
			moClassID="59,60";
			deviceMoClassID="and alarm.SourceMOClassID in(59,60)";
		}else{
			moClassID="5";
			deviceMoClassID="and alarm.SourceMOClassID in(5)";
		}	
		param.put("newMoClassID", moClassID);
		//查询所有厂商信息
		List<MODevice> factoryList = modMaper.getfactoryInfomation(param);
		
		params.put("moClassID", deviceMoClassID);
		// 查询告警总数 
		moLsCount =modMaper.getRsSnapshotInfomation(params);
		// 查询严重的告警设备数
		List<MODevice> alarmDevcieCount =modMaper.getAlarmdeviceCount(params);
		
		 List<PerfSnapsBean>  prefSnapList = new ArrayList<PerfSnapsBean>();
		 // 告警总数
		Map<Integer,PerfSnapsBean>  facturerMap = new HashMap<Integer, PerfSnapsBean>(); 
		PerfSnapsBean perfSnapObj = null;
		for (MODevice moDevice : moLsCount){
				perfSnapObj = new PerfSnapsBean();
				perfSnapObj.setNeManufacturerID(moDevice.getNemanufacturerid());
				perfSnapObj.setResManufacturerName(moDevice.getResmanufacturername());
				perfSnapObj.setAlarmCount(moDevice.getAlarmcount()); 
				perfSnapObj.setMoClassID(moClassID);
//				perfSnapObj.setCountDevice(moDevice.getCountdevice());
			facturerMap.put(moDevice.getNemanufacturerid(),perfSnapObj);
		 }
		
		// 查询所有厂商信息(设备总数)
		for (MODevice moDevice : factoryList) {
			
			if(facturerMap.containsKey(moDevice.getNemanufacturerid()))
			{
				facturerMap.get(moDevice.getNemanufacturerid()).setCountDevice(moDevice.getCountdevice());
			}else
			{
				perfSnapObj = new PerfSnapsBean();
				perfSnapObj.setNeManufacturerID(moDevice.getNemanufacturerid());
				perfSnapObj.setCountDevice(moDevice.getCountdevice());
				perfSnapObj.setResManufacturerName(moDevice.getResmanufacturername());
				perfSnapObj.setMoClassID(moClassID);
				facturerMap.put(moDevice.getNemanufacturerid(), perfSnapObj);
			}
		}
		
		// 严重告警设备
		for (MODevice alarmDevice : alarmDevcieCount) {
			
			facturerMap.get(alarmDevice.getNemanufacturerid()).setAlarmDeviceCount(alarmDevice.getCountdevice());
		}
		 
		for (Integer  mapKey : facturerMap.keySet()) {
			prefSnapList.add(facturerMap.get(mapKey));  
		}
	 
		result.put("rows", prefSnapList);
		logger.info("结束...加载公共详情信息列表 " + prefSnapList);
		return result;
	}
	
	
	
	
	/**
	 * 查询设备快照(新修改的)
	 * @create by 20150722 zhuk
	 */
	@RequestMapping("/queryRsSnapshotInfo")
	@ResponseBody
	public Map<String, Object> queryRsSnapshotInfo() throws Exception {
		logger.info("开始...加载路由器 交换机设备快照页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		String moClass = request.getParameter("moClass");
		String moClassID="";
		String deviceMoClassID ="";
		List<MODevice> moLsCount =null;
		if("switcher".equals(moClass)){
			moClassID="59,60,6";
			deviceMoClassID="and mo.MOClassID in (59,60,6)";
		}else{
			moClassID="5";
			deviceMoClassID="and mo.MOClassID in (5)";
		}	
		param.put("newMoClassID", moClassID);
		//查询所有厂商信息
		List<MODevice> factoryList = modMaper.queryfactoryInfs(param);
		
		params.put("moClassID", deviceMoClassID);
		// 查询告警总数 
		moLsCount =modMaper.getAlarmCountInfomation(params);
		 List<PerfSnapsBean>  prefSnapList = new ArrayList<PerfSnapsBean>();
		 // 告警总数
		Map<Integer,PerfSnapsBean>  facturerMap = new HashMap<Integer, PerfSnapsBean>(); 
		PerfSnapsBean perfSnapObj = null;
		for (MODevice moDevice : moLsCount){
				perfSnapObj = new PerfSnapsBean();
				perfSnapObj.setNeManufacturerID(moDevice.getNemanufacturerid());
				perfSnapObj.setResManufacturerName(moDevice.getResmanufacturername());
				perfSnapObj.setAlarmCount(moDevice.getAlarmcount()); 
				perfSnapObj.setMoClassID(moClassID);
			facturerMap.put(moDevice.getNemanufacturerid(),perfSnapObj);
		 }
		
		// 查询所有厂商信息(设备总数)
		for (MODevice moDevice : factoryList) {
			
			if(facturerMap.containsKey(moDevice.getNemanufacturerid()))
			{
				facturerMap.get(moDevice.getNemanufacturerid()).setCountDevice(moDevice.getCountdevice());
				//新修改的有问题的告警设备数
				facturerMap.get(moDevice.getNemanufacturerid()).setAlarmDeviceCount(moDevice.getDevicealarmcount());
			}else
			{
				perfSnapObj = new PerfSnapsBean();
				perfSnapObj.setNeManufacturerID(moDevice.getNemanufacturerid());
				perfSnapObj.setCountDevice(moDevice.getCountdevice());
				perfSnapObj.setResManufacturerName(moDevice.getResmanufacturername());
				perfSnapObj.setMoClassID(moClassID);
				facturerMap.put(moDevice.getNemanufacturerid(), perfSnapObj);
			}
		}
		for (Integer  mapKey : facturerMap.keySet()) {
			prefSnapList.add(facturerMap.get(mapKey));  
		}
	 
		result.put("rows", prefSnapList);
		logger.info("结束...加载公共详情信息列表 " + prefSnapList);
		return result;
	}	
	
	
	
	

	public MODevice getRsSnapObj(List<MODevice> list) {
		MODevice mo = new MODevice();
		int flag;
		for (int i = 0; i < list.size(); i++) {
			flag = list.get(i).getFlag();
			if (flag == 1) {
				mo.setAlarmcount(list.get(i).getCount());
			} else if (flag == 2) {
				mo.setDevicealarmcount(list.get(i).getCount());
			} else if (flag == 3) {
				mo.setDevicecount(list.get(i).getCount());
			}
			mo.setResmanufacturername(list.get(i).getResmanufacturername());
			mo.setMoclassid(list.get(i).getMoclassid());
			mo.setNemanufacturerid(list.get(i).getNemanufacturerid());
			
		}
		return mo;
	}

	/**
	 * 路由器交换机 内存图表页面跳转
	 * @return
	 */
	@RequestMapping("/toRSMemoryLineChart")
	public ModelAndView toRSMemoryLineChart(HttpServletRequest request,
			ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("moClass", request.getParameter("moClass"));
		map.put("proUrl", "/monitor/rsManage/initRSMemory");
		map.put("preUrl", "/monitor/historyManage/netMemoryChartData");
		return new ModelAndView("monitor/host/rsLineChart");
	}

	/**
	 * * 内存图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initRSMemory")
	@ResponseBody
	public Map<String, Object> initRSMemory(HttpServletRequest request) {
		logger.info("开始...加载内存数据");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		String moClass = request.getParameter("moClass");
		String moClassID = "";
		if ("router".equals(moClass)) {
			moClassID = "5";
		} else if ("switcher".equals(moClass)) {
			moClassID = "6";
		}else if ("switcherl2".equals(moClass)) {
			moClassID = "59";
		}else if ("switcherl3".equals(moClass)) {
			moClassID = "60";
		}
		params.put("moClassID", moClassID);
		params.put("tabName", "MOMemories");
		 
//		Map params2 = new HashMap();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		// 存放返回页面json数据对象
		ECharObj jsonData = new ECharObj();
		jsonData.setText("内存使用率(%)");
		
		StringBuffer axisTime = new StringBuffer("");
		
		
		// 获取所有路由器 交换机 内存使用率 曲线图
		List<PerfNetworkMemory> allqueryRSMemoryData = modMaper.queryRSMemoryPerfCurve(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 所有路由器 交换机 内存使用率 Id collection
		Set<Integer> RSMemoryIdSet = new TreeSet();
		// 获取路由器 交换机 内存使用率的MOID和MONAME
		Map<Integer, String> RSMemoryMap = new HashMap<Integer, String>();
		// 获取所有相对应的性能表中的数据
		Map<String, PerfNetworkMemory> allRSMemoryData = new HashMap<String, PerfNetworkMemory>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,路由器 交换机 内存使用率Id集合...
		for (int i = 0; i < allqueryRSMemoryData.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(allqueryRSMemoryData.get(i).getCollectTime()));
			// 放入路由器 交换机 内存使用率 ID节点
			RSMemoryIdSet.add(allqueryRSMemoryData.get(i).getMoId());
			// 放入路由器 交换机 内存使用率 数据节点
			RSMemoryMap.put(allqueryRSMemoryData.get(i).getMoId(), allqueryRSMemoryData.get(i).getMoName());
			// 重新组织路由器 交换机 内存使用率数据map
			String key = String.valueOf(allqueryRSMemoryData.get(i).getMoId()) 
					   + "_" + simple.format(allqueryRSMemoryData.get(i).getCollectTime());
			allRSMemoryData.put(key, allqueryRSMemoryData.get(i));
		}
		
		
		// 只记录第一次出现
		boolean firstOne = false;
		// 标记是否出现过
		boolean existFlag = false;
		// 所有平均值
		String avgDataStr = "";
		
		// 构造相关数据
		for (String timePoint : timeSet) {
			firstOne = true;
			existFlag = false;
			axisTime.append(timePoint + ",");

			for (Integer rsmemoryId : RSMemoryIdSet) {
				String mapKey = rsmemoryId + "_" + timePoint;
				// 判断mapKey在allRSMemoryData中是否有相关数据
				if (allRSMemoryData.containsKey(mapKey)) {
					if (RSMemoryIdSet.size() > 1 && firstOne == true) {
						existFlag = true;
						avgDataStr += decimalForTwo.DecimalFormatDouble(allRSMemoryData.get(mapKey).getMemoryUsage()) + ",";
					}
					firstOne = false;
					if (tmpDataMap.get(rsmemoryId) != null) {
						String dataValue = tmpDataMap.get(rsmemoryId);
							dataValue += decimalForTwo.DecimalFormatDouble(allRSMemoryData.get(mapKey) .getPerMemoryUsage())+ ",";
						tmpDataMap.put(rsmemoryId, dataValue);
					} else {
						tmpDataMap.put(rsmemoryId, decimalForTwo.DecimalFormatDouble(allRSMemoryData.get(mapKey) .getPerMemoryUsage())+ ",");
					}
				} else {
					if (tmpDataMap.get(rsmemoryId) != null) {
						String dataValue = tmpDataMap.get(rsmemoryId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(rsmemoryId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(rsmemoryId, "0,");
					}
				}
			}
			if (existFlag == false) {
				avgDataStr += "0,";
			}
		}
		// 组织性能数据
		String legend = "";
		List<Float> RSMemoryDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : RSMemoryMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			RSMemoryDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// cpu name arr[]
			legend += entry.getValue() + ",";
			
			// 找出cpu对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				RSMemoryDatalist.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(RSMemoryDatalist);
			// 展示部件时不显示最值
			if(params.get("markPoint") != null)
			{
				mineList.add(new Mine2Obj("min","最小值"));
				mineList.add(new Mine2Obj("max","最大值"));
				mineObj.setData(mineList); 
				curveObj.setMarkPoint(mineObj); 
			}
			seriesData.add(curveObj);
		}
		
		if (RSMemoryMap.size() > 1) {
			legend += "平均使用率,";
			curveObj = new CurveObj();
			RSMemoryDatalist = new ArrayList<Float>();
			curveObj.setName("平均使用率");
			curveObj.setType("line");
			
			if(avgDataStr.indexOf(",")>0){
				String[] arr = avgDataStr.split(","); 
				for (int i = 0; i < arr.length; i++) {
					RSMemoryDatalist.add(Float.parseFloat(arr[i]));
				}
			}
			curveObj.setData(RSMemoryDatalist);
			seriesData.add(curveObj); 
		}
		
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
		
		
		
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("oracleChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载宿主机cpu数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
	/**
	 * 路由器交换机 CPU图表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toRSCPULineChart")
	public ModelAndView toRSCPULineChart(HttpServletRequest request,
			ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("moClass", request.getParameter("moClass"));
		map.put("proUrl", "/monitor/rsManage/initRSCPU");
		map.put("preUrl", "/monitor/historyManage/netCpuChartData");
		return new ModelAndView("monitor/host/rsLineChart");
	}

	/**
	 * * CPU图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initRSCPU")
	@ResponseBody
	public Map<String, Object> initRSCPU(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载CPU数据");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		String moClass = request.getParameter("moClass");
		String moClassID = "";
		if ("router".equals(moClass)) {
			moClassID = "5";
		} else if ("switcher".equals(moClass)) {
			moClassID = "6";
		}else if ("switcherl2".equals(moClass)) {
			moClassID = "59";
		}else if ("switcherl3".equals(moClass)) {
			moClassID = "60";
		}
		params.put("moClassID", moClassID);
		params.put("tabName", "MOCPUs");
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		
		// 存放返回页面json数据对象
		ECharObj jsonData = new ECharObj();
		jsonData.setText("CPU使用率(%)");
		
		StringBuffer axisTime = new StringBuffer("");
		
		// 获取所有路由器 交换机 CPU使用率 曲线图的数据
		List<PerfNetworkCPU> allRSCpuData = modMaper.queryRSCPUPerfCurve(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 路由器 交换机 CPU使用率 曲线图Id collection
		Set<Integer> RSCpuIdSet = new TreeSet();
		// 获取路由器 交换机 CPU使用率 曲线图的MOID和MONAME
		Map<Integer, String> RSCpuMap = new HashMap<Integer, String>();
		// 获取所有相对应的性能表中的数据
		Map<String, PerfNetworkCPU> allRSCpuDataList = new HashMap<String, PerfNetworkCPU>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,路由器 交换机 CPU使用率 曲线图Id集合...
		for (int i = 0; i < allRSCpuData.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(allRSCpuData.get(i).getCollectTime()));
			// 放入路由器 交换机 CPU使用率 曲线图 ID节点
			RSCpuIdSet.add(allRSCpuData.get(i).getMoId());
			// 放入路由器 交换机 CPU使用率 曲线图 数据节点
			RSCpuMap.put(allRSCpuData.get(i).getMoId(), allRSCpuData.get(i).getMoName());
			// 重新组织路由器 交换机 CPU使用率 曲线图数据map
			String key = String.valueOf(allRSCpuData.get(i).getMoId() 
					   + "_" + simple.format(allRSCpuData.get(i).getCollectTime()));
			allRSCpuDataList.put(key, allRSCpuData.get(i));
		}
		
		// 只记录第一次出现
		boolean firstOne = false;
		// 标记是否出现过
		boolean existFlag = false;
		// 所有平均值
		String avgDataStr = "";
		
		// 构造相关数据
		for (String timePoint : timeSet) {
			firstOne = true;
			existFlag = false;
			axisTime.append(timePoint + ",");

			for (Integer RSCpuId : RSCpuIdSet) {
				String mapKey = RSCpuId + "_" + timePoint;
				// 判断mapKey在allRSCpuDataList中是否有相关数据
				if (allRSCpuDataList.containsKey(mapKey)) {
					if (RSCpuIdSet.size() > 1 && firstOne == true) {
						existFlag = true;
						avgDataStr += decimalForTwo.DecimalFormatDouble(allRSCpuDataList.get(mapKey).getCpUsage()) + ",";
					}
					firstOne = false;
					if (tmpDataMap.get(RSCpuId) != null) {
						String dataValue = tmpDataMap.get(RSCpuId);
						
						dataValue+=decimalForTwo.DecimalFormatDouble(allRSCpuDataList.get(mapKey).getPerUsage()) + ",";
						
						tmpDataMap.put(RSCpuId, dataValue);
					} else {
						tmpDataMap.put(RSCpuId, decimalForTwo.DecimalFormatDouble(allRSCpuDataList.get(mapKey).getPerUsage()) + ",");
					}
				} else {
					if (tmpDataMap.get(RSCpuId) != null) {
						String dataValue = tmpDataMap.get(RSCpuId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(RSCpuId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(RSCpuId, "0,");
					}
				}
			}

			if (existFlag == false) {
				avgDataStr += "0,";
			}
		}
		
		// 组织路由器 交换机 CPU使用率 曲线图数据
		String legend = "";
		List<Float> RSCpuDatas = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : RSCpuMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			RSCpuDatas = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 路由器 交换机 CPU使用率 曲线图 name arr[]
			legend += entry.getValue() + ",";
			
			// 找出路由器 交换机 CPU使用率 曲线图对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				RSCpuDatas.add(Float.parseFloat(arr[i]));
			}
			curveObj.setData(RSCpuDatas);
			// 展示部件时不显示曲线最值
			if(params.get("markPoint") != null)
			{
				mineList.add(new Mine2Obj("min","最小值"));
				mineList.add(new Mine2Obj("max","最大值"));
				mineObj.setData(mineList); 
				curveObj.setMarkPoint(mineObj); 
			}
			seriesData.add(curveObj);
		}
		
		if (RSCpuMap.size() > 1) {
			legend += "平均使用率,";
			curveObj = new CurveObj();
			RSCpuDatas = new ArrayList<Float>();
			curveObj.setName("平均使用率");
			curveObj.setType("line");
			
			if(avgDataStr.indexOf(",")>0){
				String[] arr = avgDataStr.split(","); 
				for (int i = 0; i < arr.length; i++) {
					RSCpuDatas.add(Float.parseFloat(arr[i]));
				}
			}
			curveObj.setData(RSCpuDatas);
			seriesData.add(curveObj); 
		}
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
	 
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("oracleChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载CPU数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}


	/**
	 * 设置查询时间(公共调用方法)
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map queryBetweenTime(HttpServletRequest request, Map params) {
		String seltDate = request.getParameter("time");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		String timeEnd = f.format(d.getTime());
		String timeBegin = "";

		if ("24H".equals(seltDate)) {// 最近一天
			d.add(Calendar.DATE, -1);
			timeBegin = f.format(d.getTime());
		} else if ("7D".equals(seltDate)) {// 最近一周
			d.add(Calendar.DATE, -7);
			timeBegin = f.format(d.getTime());
		} else if ("Today".equals(seltDate)) {// 今天
			d.add(Calendar.DATE, 0);
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = f.format(d.getTime());
		} else if ("Yes".equals(seltDate)) {// 昨天
			d.add(Calendar.DAY_OF_MONTH, -1);
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = f.format(d.getTime());
			d.set(Calendar.HOUR_OF_DAY, 23);
			d.set(Calendar.MINUTE, 59);
			d.set(Calendar.SECOND, 59);
			timeEnd = f.format(d.getTime());
		}else if ("Week".equals(seltDate)) {// 本周
			if(d.get(Calendar.DAY_OF_WEEK)==1){
				d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK)+5));
			}else{
				d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK)-2));
			}
			
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = f.format(d.getTime());
		}else if ("Month".equals(seltDate)) {// 本月
			d.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			d.set(Calendar.DAY_OF_MONTH,1);
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = f.format(d.getTime());
		}else if ("LastMonth".equals(seltDate)) {// 上月
			 d.add(Calendar.MONTH, -1);
		     d.set(Calendar.DAY_OF_MONTH,1);//上月第一天
		     d.set(Calendar.HOUR_OF_DAY, 0);
			 d.set(Calendar.MINUTE, 0);
			 d.set(Calendar.SECOND, 0);
		     timeBegin = f.format(d.getTime());
		     Calendar cale = Calendar.getInstance();
		     //设置为1号,当前日期既为本月第一天 
		     cale.set(Calendar.DAY_OF_MONTH,0);
		     cale.set(Calendar.HOUR_OF_DAY, 23);
		     cale.set(Calendar.MINUTE, 59);
		     cale.set(Calendar.SECOND, 59);
		     timeEnd = f.format(cale.getTime());

		}else{
			d.add(Calendar.DATE, -1);
		}/* else if ("1Y".equals(seltDate)) {// 
			d.add(Calendar.MONTH, -3);
		} else if ("".equals(seltDate)) {// 最近半年
			d.add(Calendar.MONTH, -6);
		} */
		
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		return params;
	}
	
}
