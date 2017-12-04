package com.fable.insightview.monitor.host.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.host.entity.CurveObj;
import com.fable.insightview.monitor.host.entity.ECharObj;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.host.entity.Mine2Obj;
import com.fable.insightview.monitor.host.entity.MineObj;
import com.fable.insightview.monitor.host.entity.PerfNetworkMemory;
import com.fable.insightview.monitor.host.mapper.HostMapper;
import com.fable.insightview.monitor.perf.service.IPerfGeneralConfigService;
import com.fable.insightview.monitor.utils.DecimalForTwo;

/**
 * @Description:   历史报告详情图表
 * @author         zhengxh
 * @Date           2014-8-13
 */
@Controller
@RequestMapping("/monitor/historyManage")
public class HistoryController {
	String moClass;// 设备类型ID
	@Autowired
	HostMapper modMaper;
	@Autowired
	IPerfGeneralConfigService configService;
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final Logger logger = LoggerFactory.getLogger(HistoryController.class);
	DecimalFormat df = new DecimalFormat("#.00");
	//表示曲线最大值、最小值
	private String markPoint = "markPoint:{data:[{type : 'max', name: '最大值'},{type : 'min', name: '最小值'}]}";
	DecimalForTwo decimalForTwo = new DecimalForTwo();
	/**
	 * 跳转主机历史详情页面
	 */
	@RequestMapping("/toHostDetailChartLine")
	public ModelAndView toHostDetailChartLine(HttpServletRequest request, ModelMap map) {
		String MOID=request.getParameter("moID");
		moClass = request.getParameter("moClass");
		map.put("MOID", MOID);
		if(moClass.equals("VPN")){
			return new ModelAndView("monitor/host/vpnDetailChart");
		}
		return new ModelAndView("monitor/host/mainDetailChart");
	}
	
	/**
	 * 主机详情cpu图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initCpuChartData")
	@ResponseBody
	public Map<String, Object> initCpuChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载主机cpu数据");	
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOCPUs");
		params.put("avgUsage", "perUsage");
		params.put("perfTabName", "PerfServCPU");
		
		// 性能数据json对象
		ECharObj jsonData = new ECharObj();
		jsonData.setText("使用率  (%)");
		
		StringBuffer axisTime = new StringBuffer("");
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 获取所有性能表中的数据
		List<MODevice> allPropertyData = modMaper.queryAllMainCpu(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// cpuId collection
		Set<Integer> cpuIdSet = new TreeSet();
		// 获取cpu的MOID和MONAME
		Map<Integer, String> cpuMap = new HashMap<Integer, String>();
		// 获取所有相对应的性能表中的数据
		Map<String, MODevice> allCpuData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,cpuId集合...
		for (int i = 0; i < allPropertyData.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(allPropertyData.get(i).getCollecttime()));
			// 放入CPU ID节点
			cpuIdSet.add(allPropertyData.get(i).getMoid());
			// 放入CPU 数据节点
			cpuMap.put(allPropertyData.get(i).getMoid(), allPropertyData.get(i).getMoname());
			// 重新组织CPU性能数据map
			String key = String.valueOf(allPropertyData.get(i).getMoid()) 
					   + "_" + simple.format(allPropertyData.get(i).getCollecttime());
			allCpuData.put(key, allPropertyData.get(i));
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

			for (Integer cpuId : cpuIdSet) {
				String mapKey = cpuId + "_" + timePoint;
				// 判断mapKey在allCpuData中是否有相关数据
				if (allCpuData.containsKey(mapKey)) {
					if (cpuIdSet.size() > 1 && firstOne == true) {
						existFlag = true;
						avgDataStr += decimalForTwo.DecimalFormatDouble(allCpuData.get(mapKey).getCpusage()) + ",";
					}
					firstOne = false;
					if (tmpDataMap.get(cpuId) != null) {
						String dataValue = tmpDataMap.get(cpuId);
						dataValue += decimalForTwo.DecimalFormatString(allCpuData.get(mapKey) .getPerusage())+ ",";
						tmpDataMap.put(cpuId, dataValue);
					} else {
						tmpDataMap.put(cpuId, decimalForTwo.DecimalFormatString(allCpuData.get(mapKey).getPerusage())+ ",");
					}
				} else {
					if (tmpDataMap.get(cpuId) != null) {
						String dataValue = tmpDataMap.get(cpuId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(cpuId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(cpuId, "0,");
					}
				}
			}

			if (existFlag == false) {
				avgDataStr += "0,";
			}
		}
		
		// 组织性能数据
		String legend = "";
		List<Float> cpuDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : cpuMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			cpuDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// cpu name arr[]
			legend += entry.getValue() + ",";
			
			// 找出cpu对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				cpuDatalist.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(cpuDatalist);
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
		
		if (cpuMap.size() > 1) {
			legend += "平均使用率,";
			curveObj = new CurveObj();
			cpuDatalist = new ArrayList<Float>();
			curveObj.setName("平均使用率");
			curveObj.setType("line");
			
			if(avgDataStr.indexOf(",")>0){
				String[] arr = avgDataStr.split(","); 
				for (int i = 0; i < arr.length; i++) {
					cpuDatalist.add(Float.parseFloat(arr[i]));
				}
			}
			curveObj.setData(cpuDatalist);
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
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载主机cpu数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;

	}
	
	/**
	 * * 主机详情硬盘图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initHardDiskChartData")
	@ResponseBody
	public Map<String, Object> initHardDiskChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载主机硬盘数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOVolumes");
		params.put("avgUsage", "DiskUsage"); 
		params.put("perfTabName", "PerfServDisk");
		
		StringBuffer axisTime = new StringBuffer("");
		
		//存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		String perf = request.getParameter("perfKind");
		
		if(perf.equals("1")){
			jsonData.setText("使用率 (%)");
		}else if(perf.equals("2")){
			jsonData.setText("空闲容量 (GB)");
		}
		
		// 获取所有主机磁盘历史数据
		List<MODevice> hardDeviceDataList = modMaper.queryMainHardList(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 主机磁盘Id collection
		Set<Integer> hardDeviceIdSet = new TreeSet();
		// 获取主机磁盘的MOID和MONAME
		Map<Integer, String> hardDeviceMap = new HashMap<Integer, String>();
		// 获取所有相对应的主机磁盘表中的数据
		Map<String, MODevice> allHardDeviceData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,主机磁盘Id集合...
		for (int i = 0; i < hardDeviceDataList.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(hardDeviceDataList.get(i).getCollecttime()));
			// 放入主机磁盘 ID节点
			hardDeviceIdSet.add(hardDeviceDataList.get(i).getMoid());
			// 放入主机磁盘 数据节点
			hardDeviceMap.put(hardDeviceDataList.get(i).getMoid(), hardDeviceDataList.get(i).getMoname());
			// 重新组织主机磁盘数据map
			String key = String.valueOf(hardDeviceDataList.get(i).getMoid()) 
					   + "_" + simple.format(hardDeviceDataList.get(i).getCollecttime());
			allHardDeviceData.put(key, hardDeviceDataList.get(i));
		}
		
		// 构造相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer hardDeviceId : hardDeviceIdSet) {
				String mapKey = hardDeviceId + "_" + timePoint;
				// 判断mapKey在allHardDeviceData中是否有相关数据
				if (allHardDeviceData.containsKey(mapKey)) {
					if (tmpDataMap.get(hardDeviceId) != null) {
						String dataValue = tmpDataMap.get(hardDeviceId);
						if(perf.equals("1")){
							dataValue+=decimalForTwo.DecimalFormatString(allHardDeviceData.get(mapKey).getDiskusage())+",";
						}else if(perf.equals("2")){
							dataValue+=df.format(Double.valueOf(allHardDeviceData.get(mapKey).getFreesize())/1024/1024/1024)+",";//b转换成GB
						}
						tmpDataMap.put(hardDeviceId, dataValue);
					} else {
						
						if(perf.equals("1")){
							tmpDataMap.put(hardDeviceId,decimalForTwo.DecimalFormatString(allHardDeviceData.get(mapKey).getDiskusage())+",");
						}else if(perf.equals("2")){
							tmpDataMap.put(hardDeviceId,df.format(Double.valueOf(allHardDeviceData.get(mapKey).getFreesize())/1024/1024/1024)+",");//b转换成GB
						}
					}
				} else {
					if (tmpDataMap.get(hardDeviceId) != null) {
						String dataValue = tmpDataMap.get(hardDeviceId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(hardDeviceId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(hardDeviceId, "0,");
					}
				}
			}
		}
		
		// 组织主机磁盘数据
		String legend = "";
		List<Float> hardDevicelists = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : hardDeviceMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			hardDevicelists = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 主机磁盘 name arr[]
			legend += entry.getValue() + ",";
			
			// 找出主机磁盘 对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				hardDevicelists.add(Float.parseFloat(arr[i]));
			}
			curveObj.setData(hardDevicelists);
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
		
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
		
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载主机磁盘数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
	/**
	 * * 主机详情接口图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initInterfaceChartData")
	@ResponseBody
	public Map<String, Object> initInterfaceChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载主机接口数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MONetworkIf");
		params.put("perfTabName", "PerfNetworkIf");
		params.put("IfOperStatus", "IfOperStatus");
		ECharObj jsonData = new ECharObj();;//存放返回页面json数据
		String perf = request.getParameter("perfKind");
		
		StringBuffer axisTime = new StringBuffer("");
		
		if(perf.equals("2")){
			jsonData.setText("流入流量 (KBps)");
		}else if(perf.equals("3")){
			jsonData.setText("流出流量 (KBps)");
		}else if(perf.equals("4")){
			jsonData.setText("流入单播包");
		}else if(perf.equals("5")){
			jsonData.setText("流出单播包");
		}else if(perf.equals("6")){
			jsonData.setText("流入非单播包");
		}else if(perf.equals("7")){
			jsonData.setText("流出非单播包");
		}else if(perf.equals("8")){
			jsonData.setText("流入丢包");
		}else if(perf.equals("9")){
			jsonData.setText("流出丢包");
		}else if(perf.equals("10")){
			jsonData.setText("流入错包");
		}else if(perf.equals("11")){
			jsonData.setText("流出错包");
		}else if(perf.equals("12")){
			jsonData.setText("带宽使用率(%)");
		}

		// 获取所有主机接口中的数据
		List<MODevice> interfaceDeviceList = modMaper.queryMainInterfaceList(params);
		
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 主机接口Id collection
		Set<Integer> interfaceDeviceIdSet = new TreeSet();
		// 获取主机接口的MOID和MONAME
		Map<Integer, String> interfaceDeviceMap = new HashMap<Integer, String>();
		// 获取所有相对应的性能表中的数据
		Map<String, MODevice> allInterfaceDeviceMapData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,cpuId集合...
		for (int i = 0; i < interfaceDeviceList.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(interfaceDeviceList.get(i).getCollecttime()));
			// 放入主机接口 ID节点
			interfaceDeviceIdSet.add(interfaceDeviceList.get(i).getMoid());
			// 放入主机接口 数据节点
			interfaceDeviceMap.put(interfaceDeviceList.get(i).getMoid(), interfaceDeviceList.get(i).getMoname());
			// 重新组织主机接口数据map
			String key = String.valueOf(interfaceDeviceList.get(i).getMoid()) 
					   + "_" + simple.format(interfaceDeviceList.get(i).getCollecttime());
			allInterfaceDeviceMapData.put(key, interfaceDeviceList.get(i));
		}
		
		// 构造相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer interfaceId : interfaceDeviceIdSet) {
				String mapKey = interfaceId + "_" + timePoint;
				// 判断mapKey在allInterfaceDeviceMapData中是否有相关数据
				if (allInterfaceDeviceMapData.containsKey(mapKey)) {
					if (tmpDataMap.get(interfaceId) != null) {
						String dataValue = tmpDataMap.get(interfaceId);
						if(perf.equals("2")){
							dataValue+=decimalForTwo.DecimalFormatString(allInterfaceDeviceMapData.get(mapKey).getInflows())+",";
						}else if(perf.equals("3")){
							dataValue+=decimalForTwo.DecimalFormatString(allInterfaceDeviceMapData.get(mapKey).getOutflows())+",";
						}else if(perf.equals("4")){
							dataValue+=allInterfaceDeviceMapData.get(mapKey).getInflowpkts()+",";
						}else if(perf.equals("5")){
							dataValue+=allInterfaceDeviceMapData.get(mapKey).getOutflowpkts()+",";
						}else if(perf.equals("6")){
							dataValue+=allInterfaceDeviceMapData.get(mapKey).getInflownpkts()+",";
						}else if(perf.equals("7")){
							dataValue+=allInterfaceDeviceMapData.get(mapKey).getOutflownpkts()+",";
						}else if(perf.equals("8")){
							dataValue+=allInterfaceDeviceMapData.get(mapKey).getInflowloss()+",";
						}else if(perf.equals("9")){
							dataValue+=allInterfaceDeviceMapData.get(mapKey).getOutflowloss()+",";
						}else if(perf.equals("10")){
							dataValue+=allInterfaceDeviceMapData.get(mapKey).getInflowerrors()+",";
						}else if(perf.equals("11")){
							dataValue+=allInterfaceDeviceMapData.get(mapKey).getOutflowerrors()+",";
						}else if(perf.equals("12")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allInterfaceDeviceMapData.get(mapKey).getIousage())+",";
						}
						tmpDataMap.put(interfaceId, dataValue);
					} else {
						if(perf.equals("2")){
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatString(allInterfaceDeviceMapData.get(mapKey).getInflows())+",");
						}else if(perf.equals("3")){
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatString(allInterfaceDeviceMapData.get(mapKey).getOutflows())+",");
						}else if(perf.equals("4")){
							tmpDataMap.put(interfaceId,allInterfaceDeviceMapData.get(mapKey).getInflowpkts()+",");
						}else if(perf.equals("5")){
							tmpDataMap.put(interfaceId,allInterfaceDeviceMapData.get(mapKey).getOutflowpkts()+",");
						}else if(perf.equals("6")){
							tmpDataMap.put(interfaceId,allInterfaceDeviceMapData.get(mapKey).getInflownpkts()+",");
						}else if(perf.equals("7")){
							tmpDataMap.put(interfaceId,allInterfaceDeviceMapData.get(mapKey).getOutflownpkts()+",");
						}else if(perf.equals("8")){
							tmpDataMap.put(interfaceId,allInterfaceDeviceMapData.get(mapKey).getInflowloss()+",");
						}else if(perf.equals("9")){
							tmpDataMap.put(interfaceId,allInterfaceDeviceMapData.get(mapKey).getOutflowloss()+",");
						}else if(perf.equals("10")){
							tmpDataMap.put(interfaceId,allInterfaceDeviceMapData.get(mapKey).getInflowerrors()+",");
						}else if(perf.equals("11")){
							tmpDataMap.put(interfaceId,allInterfaceDeviceMapData.get(mapKey).getOutflowerrors()+",");
						}else if(perf.equals("12")){
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatFloat(allInterfaceDeviceMapData.get(mapKey).getIousage())+",");
						}
					}
				} else {
					if (tmpDataMap.get(interfaceId) != null) {
						String dataValue = tmpDataMap.get(interfaceId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(interfaceId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(interfaceId, "0,");
					}
				}
			}
		}
		
		// 组织主机接口数据
		String legend = "";
		List<Float> interfaceDevicelists = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : interfaceDeviceMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			interfaceDevicelists = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 主机接口 name arr[]
			legend += entry.getValue() + ",";
			
			// 找出主机接口对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				interfaceDevicelists.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(interfaceDevicelists);
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
		
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");

		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载主机接口数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
	/**
	 * * 主机详情内存图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initMemoryChartData")
	@ResponseBody
	public Map<String, Object> initMemoryChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载主机内存数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOMemories");
		params.put("avgUsage", "MemoryUsage");
		params.put("perfTabName", "PerfServMemory");
		String perf = request.getParameter("perfKind");
		
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer axisTime = new StringBuffer("");
		// 主机内存数据json对象
		ECharObj jsonData = new ECharObj();
		if(perf.equals("1")){
			jsonData.setText("内存使用率 (%)");
		}else if(perf.equals("2")){
			jsonData.setText("内存空闲容量 (MB)");
		}
		
		// 获取所有主机内存中的数据
		List<MODevice> allPropertyData = modMaper.queryAllMainMemory(params);
		for(int i=0;i<allPropertyData.size();i++){
			if(allPropertyData.get(i).getMemType()!=null){
				if( allPropertyData.get(i).getMemType().equals("hrStorageRam")){
					allPropertyData.get(i).setMoname(allPropertyData.get(i).getMoname()+"(物理内存)");
				}else if(allPropertyData.get(i).getMemType().equals("hrStorageVirtualMemory")){
					allPropertyData.get(i).setMoname(allPropertyData.get(i).getMoname()+"(虚拟内存)");
				}else if(allPropertyData.get(i).getMemType().equals("hrStorageFlashMemory")){
					allPropertyData.get(i).setMoname(allPropertyData.get(i).getMoname()+"(闪存)");
				}
			}
			
		}
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// cpuId collection
		Set<Integer> mainMemoryIdSet = new TreeSet();
		// 获取主机的MOID和MONAME
		Map<Integer, String> cpuMap = new HashMap<Integer, String>();
		// 获取所有相对应的表中的数据
		Map<String, MODevice> allMainMemoryData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,cpuId集合...
		for (int i = 0; i < allPropertyData.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(allPropertyData.get(i).getCollecttime()));
			// 放入主机内存 ID节点
			mainMemoryIdSet.add(allPropertyData.get(i).getMoid());
			// 放入主机内存 数据节点
			cpuMap.put(allPropertyData.get(i).getMoid(), allPropertyData.get(i).getMoname());
			// 重新组织主机内存数据map
			String key = String.valueOf(allPropertyData.get(i).getMoid()) 
					   + "_" + simple.format(allPropertyData.get(i).getCollecttime());
			allMainMemoryData.put(key, allPropertyData.get(i));
		}
		
		// 构造相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer mainMemoryId : mainMemoryIdSet) {
				String mapKey = mainMemoryId + "_" + timePoint;		
				if (allMainMemoryData.containsKey(mapKey)) {
				// 判断mapKey在allMainMemoryData中是否有相关数据
					if (tmpDataMap.get(mainMemoryId) != null) {
						String dataValue = tmpDataMap.get(mainMemoryId);
						
						if(perf.equals("1")){
							dataValue+=decimalForTwo.DecimalFormatString(allMainMemoryData.get(mapKey).getMemoryusage())+",";
						}else if(perf.equals("2")){
							dataValue+= df.format(Double.valueOf(allMainMemoryData.get(mapKey).getFreesize())/1024/1024)+",";//b转换成MB
						}
						tmpDataMap.put(mainMemoryId, dataValue);
					} else {
						
						if(perf.equals("1")){
							tmpDataMap.put(mainMemoryId,decimalForTwo.DecimalFormatString(allMainMemoryData.get(mapKey).getMemoryusage())+",");
						}else if(perf.equals("2")){
							tmpDataMap.put(mainMemoryId,df.format(Double.valueOf(allMainMemoryData.get(mapKey).getFreesize())/1024/1024)+",");
						}
					}
				} else {
					if (tmpDataMap.get(mainMemoryId) != null) {
						String dataValue = tmpDataMap.get(mainMemoryId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(mainMemoryId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(mainMemoryId, "0,");
					}
				}
			}
		}
		

		// 组织主机内存数据
		String legend = "";
		List<Float> memoryDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : cpuMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			memoryDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 主机内存 name arr[]
			legend += entry.getValue() + ",";
			
			// 找出主机内存对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				memoryDatalist.add(Float.parseFloat(arr[i]));
			}
			curveObj.setData(memoryDatalist);
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
		
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
		
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载主机内存数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
	
	/**
	 * 跳转虚拟机历史详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toVMDetailChartLine")
	public ModelAndView toVMDetailChartLine(HttpServletRequest request, ModelMap map) {
		String MOID=request.getParameter("moID");
		map.put("MOID", MOID);
		return new ModelAndView("monitor/host/virtualDetailChart");
	}
	
	/**
	 * * 虚拟机详情cpu图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/virtualCpuChartData")
	@ResponseBody
	public Map<String, Object> virtualCpuChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载虚拟机cpu数据");	
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOCPUs");
		params.put("avgUsage", "Usagecore");
		params.put("perfTabName", "PerfVmCPU");
		
		// 存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		String perf = request.getParameter("perfKind");
		
		StringBuffer axisTime = new StringBuffer("");
		
		if(perf.equals("1")){
			jsonData.setText("单核使用量 (MHz)");
		}else if(perf.equals("2")){
			jsonData.setText("总使用时间 (ms)");
		}else if(perf.equals("3")){
			jsonData.setText("准备时间 (ms)");
		}else if(perf.equals("4")){
			jsonData.setText("等待时间 (ms)");
		}
		
		// 获取所有虚拟机cpu中的数据
		List<MODevice> cpuDeviceDataList = modMaper.queryVirtualCPUList(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 虚拟机cpuId collection
		Set<Integer> cpuIdSet = new TreeSet();
		// 获取虚拟机cpu的MOID和MONAME
		Map<Integer, String> cpuMap = new HashMap<Integer, String>();
		// 获取所有相对应的表中的数据
		Map<String, MODevice> allCpuData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,虚拟机cpuId集合...
		for (int i = 0; i < cpuDeviceDataList.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(cpuDeviceDataList.get(i).getCollecttime()));
			// 放入虚拟机CPU ID节点
			cpuIdSet.add(cpuDeviceDataList.get(i).getMoid());
			// 放入虚拟机CPU 数据节点
			cpuMap.put(cpuDeviceDataList.get(i).getMoid(), cpuDeviceDataList.get(i).getMoname());
			// 重新组织CPU性能数据map
			String key = String.valueOf(cpuDeviceDataList.get(i).getMoid()) 
					   + "_" + simple.format(cpuDeviceDataList.get(i).getCollecttime());
			allCpuData.put(key, cpuDeviceDataList.get(i));
		}
		
		// 构造虚拟机相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer cpuId : cpuIdSet) {
				String mapKey = cpuId + "_" + timePoint;
				// 判断mapKey在allCpuData中是否有相关数据
				if (allCpuData.containsKey(mapKey)) {
					if (tmpDataMap.get(cpuId) != null) {
						String dataValue = tmpDataMap.get(cpuId);
						if(perf.equals("1")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allCpuData.get(mapKey).getUsagecore())+",";
						}else if(perf.equals("2")){
							dataValue+=allCpuData.get(mapKey).getUsed()+",";
						}else if(perf.equals("3")){
							dataValue+=allCpuData.get(mapKey).getReady()+",";
						}else if(perf.equals("4")){
							dataValue+=allCpuData.get(mapKey).getWait()+",";
						}
						tmpDataMap.put(cpuId, dataValue);
					} else {
						if(perf.equals("1")){
							tmpDataMap.put(cpuId,decimalForTwo.DecimalFormatFloat(allCpuData.get(mapKey).getUsagecore())+",");
						}else if(perf.equals("2")){
							tmpDataMap.put(cpuId,allCpuData.get(mapKey).getUsed()+",");
						}else if(perf.equals("3")){
							tmpDataMap.put(cpuId,allCpuData.get(mapKey).getReady()+",");
						}else if(perf.equals("4")){
							tmpDataMap.put(cpuId,allCpuData.get(mapKey).getWait()+",");
						}
					}
				} else {
					if (tmpDataMap.get(cpuId) != null) {
						String dataValue = tmpDataMap.get(cpuId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(cpuId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(cpuId, "0,");
					}
				}
			}
		}

		// 组织虚拟机数据
		String legend = "";
		List<Float> cpuDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : cpuMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			cpuDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 虚拟机 name arr[]
			legend += entry.getValue() + ",";
			
			// 找出虚拟机对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				cpuDatalist.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(cpuDatalist);
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
		
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
	 
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载虚拟机cpu数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
	/**
	 * * 虚拟机详情内存图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/virtualMemoryChartData")
	@ResponseBody
	public Map<String, Object> virtualMemoryChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载虚拟机内存数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOMemories");
		params.put("perfTabName", "PerfVmMemory");
		
		//存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		String perf = request.getParameter("perfKind");
		
		StringBuffer axisTime = new StringBuffer("");
		
		if(perf.equals("1")){
			jsonData.setText("使用率 (%)");
		}else if(perf.equals("2")){
			jsonData.setText("内存控制 (MB)");
		}else if(perf.equals("3")){
			jsonData.setText("活动内存 (MB)");
		}else if(perf.equals("4")){
			jsonData.setText("系统内存开销 (MB)");
		}else if(perf.equals("5")){
			jsonData.setText("共享内存 (MB)");
		}else if(perf.equals("6")){
			jsonData.setText("交换内存 (MB)");
		}else if(perf.equals("7")){
			jsonData.setText("消耗内存 (MB)");
		}
		
		
		// 获取所有虚拟机内存历史中的数据
		List<MODevice> memoryDeviceDataList = modMaper.queryVirtualMemoryList(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 虚拟机内存Id collection
		Set<Integer> memoryDeviceIdSet = new TreeSet();
		// 获取虚拟机内存的MOID和MONAME
		Map<Integer, String> memoryDeviceMap = new HashMap<Integer, String>();
		// 获取所有相对应的虚拟机内存中的数据
		Map<String, MODevice> allmemoryDeviceData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,cpuId集合...
		for (int i = 0; i < memoryDeviceDataList.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(memoryDeviceDataList.get(i).getCollecttime()));
			// 放入CPU ID节点
			memoryDeviceIdSet.add(memoryDeviceDataList.get(i).getMoid());
			// 放入CPU 数据节点
			memoryDeviceMap.put(memoryDeviceDataList.get(i).getMoid(), memoryDeviceDataList.get(i).getMoname());
			// 重新组织CPU性能数据map
			String key = String.valueOf(memoryDeviceDataList.get(i).getMoid()) 
					   + "_" + simple.format(memoryDeviceDataList.get(i).getCollecttime());
			allmemoryDeviceData.put(key, memoryDeviceDataList.get(i));
		}
		
		// 构造相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer memoryDeviceId : memoryDeviceIdSet) {
				String mapKey = memoryDeviceId + "_" + timePoint;
				// 判断mapKey在allmemoryDeviceData中是否有相关数据
				if (allmemoryDeviceData.containsKey(mapKey)) {
					if (tmpDataMap.get(memoryDeviceId) != null) {
						String dataValue = tmpDataMap.get(memoryDeviceId);
						if(perf.equals("1")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allmemoryDeviceData.get(mapKey).getMemusage())+",";
						}else if(perf.equals("2")){
							dataValue+=df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getBalloon())/1024/1024)+",";//b转换成MB
						}else if(perf.equals("3")){
							dataValue+=df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getActive())/1024/1024)+",";//b转换成MB
						}else if(perf.equals("4")){
							dataValue+=df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getOverhead())/1024/1024)+",";//b转换成MB
						}else if(perf.equals("5")){
							dataValue+=df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getShared())/1024/1024)+",";//b转换成MB
						}else if(perf.equals("6")){
							dataValue+=df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getSwapped())/1024/1024)+",";//b转换成MB
						}else if(perf.equals("7")){
							dataValue+=df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getConsumed())/1024/1024)+",";//b转换成MB
						}
						tmpDataMap.put(memoryDeviceId, dataValue);
					} else {
						if(perf.equals("1")){
							tmpDataMap.put(memoryDeviceId,decimalForTwo.DecimalFormatFloat(allmemoryDeviceData.get(mapKey).getMemusage())+",");
						}else if(perf.equals("2")){
							tmpDataMap.put(memoryDeviceId,df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getBalloon())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("3")){
							tmpDataMap.put(memoryDeviceId,df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getActive())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("4")){
							tmpDataMap.put(memoryDeviceId,df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getOverhead())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("5")){
							tmpDataMap.put(memoryDeviceId,df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getShared())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("6")){
							tmpDataMap.put(memoryDeviceId,df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getSwapped())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("7")){
							tmpDataMap.put(memoryDeviceId,df.format(Double.valueOf(allmemoryDeviceData.get(mapKey).getConsumed())/1024/1024)+",");//b转换成MB
						}
					}
				} else {
					if (tmpDataMap.get(memoryDeviceId) != null) {
						String dataValue = tmpDataMap.get(memoryDeviceId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(memoryDeviceId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(memoryDeviceId, "0,");
					}
				}
			}
		}
		
		// 组织性能数据
		String legend = "";
		List<Float> memoryDeviceDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : memoryDeviceMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			memoryDeviceDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 虚拟机内存name arr[]
			legend += entry.getValue() + ",";
			
			// 找出虚拟机内存对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				memoryDeviceDatalist.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(memoryDeviceDatalist);
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
		
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
	 
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载虚拟机内存数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
	/**
	 * * 虚拟机详情硬盘图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/virtualHardDiskChartData")
	@ResponseBody
	public Map<String, Object> virtualHardDiskChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载虚拟机硬盘数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOVolumes");
		params.put("perfTabName", "PerfVmDisk");
		
		StringBuffer axisTime = new StringBuffer("");
		
		//存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		String perf = request.getParameter("perfKind");
		
		if(perf.equals("1")){
			jsonData.setText("读盘速度 (KBps)");
		}else if(perf.equals("2")){
			jsonData.setText("写盘速度 (KBps)");
		}else if(perf.equals("3")){
			jsonData.setText("读盘请求");
		}else if(perf.equals("4")){
			jsonData.setText("写盘请求");
		}else if(perf.equals("5")){
			jsonData.setText("磁盘总线重置");
		}else if(perf.equals("6")){
			jsonData.setText("磁盘大小(MB)");
		}else if(perf.equals("7")){
			jsonData.setText("磁盘空闲大小(MB)");
		}else if(perf.equals("8")){
			jsonData.setText("磁盘已用大小(MB)");
		}else if(perf.equals("9")){
			jsonData.setText("磁盘使用率(%)");
		}
		List<MODevice> hardDeviceDataList = null;
		if(perf.equals("1")||perf.equals("2")||perf.equals("3")||perf.equals("4")||perf.equals("5")){
			params.put("volType", "Volume");
			// 获取所有虚拟机磁盘表中的数据
			hardDeviceDataList = modMaper.queryVirtualHardList(params);
		}else{
			params.put("volType", "VmDisk");
			hardDeviceDataList =  modMaper.queryVirtualHardList(params);
		}
		
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// hardDeviceId collection
		Set<Integer> hardDeviceIdSet = new TreeSet();
		// 获取虚拟机磁盘的MOID和MONAME
		Map<Integer, String> hardDeviceMap = new HashMap<Integer, String>();
		// 获取所有相对应的性能表中的数据
		Map<String, MODevice> allHardDeviceData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,cpuId集合...
		for (int i = 0; i < hardDeviceDataList.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(hardDeviceDataList.get(i).getCollecttime()));
			// 放入虚拟机磁盘 ID节点
			hardDeviceIdSet.add(hardDeviceDataList.get(i).getMoid());
			// 放入虚拟机磁盘 数据节点
			hardDeviceMap.put(hardDeviceDataList.get(i).getMoid(), hardDeviceDataList.get(i).getMoname());
			// 重新组织虚拟机磁盘性能数据map
			String key = String.valueOf(hardDeviceDataList.get(i).getMoid()) 
					   + "_" + simple.format(hardDeviceDataList.get(i).getCollecttime());
			allHardDeviceData.put(key, hardDeviceDataList.get(i));
		}
		
		
		// 构造相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer hardDeviceId : hardDeviceIdSet) {
				String mapKey = hardDeviceId + "_" + timePoint;
				// 判断mapKey在allCpuData中是否有相关数据
				if (allHardDeviceData.containsKey(mapKey)) {
					if (tmpDataMap.get(hardDeviceId) != null) {
						String dataValue = tmpDataMap.get(hardDeviceId);
						if(perf.equals("1")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getReadspeed())+",";
						}else if(perf.equals("2")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getWritespeed())+",";
						}else if(perf.equals("3")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getReadrequests())+",";
						}else if(perf.equals("4")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getWriterequests())+",";
						}else if(perf.equals("5")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getBusresets())+",";
						}else if(perf.equals("6")){
							dataValue+= df.format(Double.valueOf(allHardDeviceData.get(mapKey).getDisksize())/1024/1024)+",";
						}else if(perf.equals("7")){
							dataValue+= df.format(Double.valueOf(allHardDeviceData.get(mapKey).getDiskfree())/1024/1024)+",";
						}else if(perf.equals("8")){
							dataValue+= df.format(Double.valueOf(allHardDeviceData.get(mapKey).getDiskused())/1024/1024)+",";
						}else if(perf.equals("9")){
							dataValue+=decimalForTwo.DecimalFormatString(allHardDeviceData.get(mapKey).getDiskusage())+",";
						}
						tmpDataMap.put(hardDeviceId, dataValue);
					} else {
						if(perf.equals("1")){
							tmpDataMap.put(hardDeviceId, decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getReadspeed())+",");
						}else if(perf.equals("2")){
							tmpDataMap.put(hardDeviceId, decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getWritespeed())+",");
						}else if(perf.equals("3")){
							tmpDataMap.put(hardDeviceId, decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getReadrequests())+",");
						}else if(perf.equals("4")){
							tmpDataMap.put(hardDeviceId, decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getWriterequests())+",");
						}else if(perf.equals("5")){
							tmpDataMap.put(hardDeviceId,decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getBusresets())+",");
						}else if(perf.equals("6")){
							tmpDataMap.put(hardDeviceId,df.format(Double.valueOf(allHardDeviceData.get(mapKey).getDisksize())/1024/1024)+",");
						}else if(perf.equals("7")){
							tmpDataMap.put(hardDeviceId,df.format(Double.valueOf(allHardDeviceData.get(mapKey).getDiskfree())/1024/1024)+",");
						}else if(perf.equals("8")){
							tmpDataMap.put(hardDeviceId,df.format(Double.valueOf(allHardDeviceData.get(mapKey).getDiskused())/1024/1024)+",");
						}else if(perf.equals("9")){
							tmpDataMap.put(hardDeviceId,decimalForTwo.DecimalFormatString(allHardDeviceData.get(mapKey).getDiskusage())+",");
						}
					}
				} else {
					if (tmpDataMap.get(hardDeviceId) != null) {
						String dataValue = tmpDataMap.get(hardDeviceId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(hardDeviceId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(hardDeviceId, "0,");
					}
				}
			}
		}
		
		
		// 组织虚拟机磁盘数据
		String legend = "";
		List<Float> hardDevicelists = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : hardDeviceMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			hardDevicelists = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 虚拟机磁盘 name arr[]
			legend += entry.getValue() + ",";
			
			// 找出虚拟机磁盘对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				hardDevicelists.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(hardDevicelists);
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
		
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
	 
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载宿主机cpu数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
	/**
	 * * 虚拟机详情接口图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/virtualInterfaceChartData")
	@ResponseBody
	public Map<String, Object> virtualInterfaceChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载虚拟机接口数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MONetworkIf");
		params.put("perfTabName", "PerfVmNetwork");
		
		StringBuffer axisTime = new StringBuffer("");
		
		//存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		String perf = request.getParameter("perfKind");
		
		if(perf.equals("1")){
			jsonData.setText("总流量 (KBps)");
		}else if(perf.equals("2")){
			jsonData.setText("流入流量 (KBps)");
		}else if(perf.equals("3")){
			jsonData.setText("流出流量 (KBps)");
		}else if(perf.equals("4")){
			jsonData.setText("流入包数");
		}else if(perf.equals("5")){
			jsonData.setText("流出包数");
		}
		
		// 获取所有虚拟机接口中的数据
		List<MODevice> interfaceDeviceData = modMaper.queryVirtualInterfaceList(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 虚拟机接口Id collection
		Set<Integer> interfaceDeviceIdSet = new TreeSet();
		// 获取虚拟机接口的MOID和MONAME
		Map<Integer, String> interfaceDeviceMap = new HashMap<Integer, String>();
		// 获取所有相对应的虚拟机接口表中的数据
		Map<String, MODevice> allinterfaceDeviceData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,虚拟机接口Id集合...
		for (int i = 0; i < interfaceDeviceData.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(interfaceDeviceData.get(i).getCollecttime()));
			// 放入虚拟机接口 ID节点
			interfaceDeviceIdSet.add(interfaceDeviceData.get(i).getMoid());
			// 放入虚拟机接口 数据节点
			interfaceDeviceMap.put(interfaceDeviceData.get(i).getMoid(), interfaceDeviceData.get(i).getMoname());
			// 重新组织虚拟机接口数据map
			String key = String.valueOf(interfaceDeviceData.get(i).getMoid()) 
					   + "_" + simple.format(interfaceDeviceData.get(i).getCollecttime());
			allinterfaceDeviceData.put(key, interfaceDeviceData.get(i));
		}
		
		// 构造相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer interfaceDeviceId : interfaceDeviceIdSet) {
				String mapKey = interfaceDeviceId + "_" + timePoint;
				// 判断mapKey在allinterfaceDeviceData中是否有相关数据
				if (allinterfaceDeviceData.containsKey(mapKey)) {
					if (tmpDataMap.get(interfaceDeviceId) != null) {
						String dataValue = tmpDataMap.get(interfaceDeviceId);
						if(perf.equals("1")){					
							dataValue+=decimalForTwo.DecimalFormatFloat(allinterfaceDeviceData.get(mapKey).getNetworkusage())+",";
						}else if(perf.equals("2")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allinterfaceDeviceData.get(mapKey).getReceivedspeed())+",";
						}else if(perf.equals("3")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allinterfaceDeviceData.get(mapKey).getTransmittedspeed())+",";
						}else if(perf.equals("4")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allinterfaceDeviceData.get(mapKey).getPacketsreceived())+",";
						}else if(perf.equals("5")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allinterfaceDeviceData.get(mapKey).getPacketstransmitted())+",";
						}		
						tmpDataMap.put(interfaceDeviceId, dataValue);
					} else {
						if(perf.equals("1")){					
							tmpDataMap.put(interfaceDeviceId,decimalForTwo.DecimalFormatFloat(allinterfaceDeviceData.get(mapKey).getNetworkusage())+",");
						}else if(perf.equals("2")){
							tmpDataMap.put(interfaceDeviceId,decimalForTwo.DecimalFormatFloat(allinterfaceDeviceData.get(mapKey).getReceivedspeed())+",");
						}else if(perf.equals("3")){
							tmpDataMap.put(interfaceDeviceId,decimalForTwo.DecimalFormatFloat(allinterfaceDeviceData.get(mapKey).getTransmittedspeed())+",");
						}else if(perf.equals("4")){
							tmpDataMap.put(interfaceDeviceId,decimalForTwo.DecimalFormatFloat(allinterfaceDeviceData.get(mapKey).getPacketsreceived())+",");
						}else if(perf.equals("5")){
							tmpDataMap.put(interfaceDeviceId,decimalForTwo.DecimalFormatFloat(allinterfaceDeviceData.get(mapKey).getPacketstransmitted())+",");
						}		
					}
				} else {
					if (tmpDataMap.get(interfaceDeviceId) != null) {
						String dataValue = tmpDataMap.get(interfaceDeviceId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(interfaceDeviceId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(interfaceDeviceId, "0,");
					}
				}
			}
		}
		
		// 组织虚拟机接口数据
		String legend = "";
		List<Float> interfaceDatalists = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : interfaceDeviceMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			interfaceDatalists = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 虚拟机接口name arr[]
			legend += entry.getValue() + ",";
			
			// 找出虚拟机接口对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				interfaceDatalists.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(interfaceDatalists);
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
		
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
	 
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载虚拟机接口数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
	/**
	 * * 虚拟机详情数据存储图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/virtualStoreChartData")
	@ResponseBody
	public Map<String, Object> virtualStoreChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载虚拟机数据存储数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOStorage");
		params.put("perfTabName", "PerfVmDatastore");
	
		//存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		String perf = request.getParameter("perfKind");
		
		StringBuffer axisTime = new StringBuffer("");
		
		if(perf.equals("1")){
			jsonData.setText("读速度 (KBps)");
		}else if(perf.equals("2")){
			jsonData.setText("写速度 (KBps)");
		}else if(perf.equals("3")){
			jsonData.setText("读请求");
		}else if(perf.equals("4")){
			jsonData.setText("写请求");
		}else if(perf.equals("5")){
			jsonData.setText("读延时 (ms)");
		}else if(perf.equals("6")){
			jsonData.setText("写延时 (ms)");
		}

		// 获取所有虚拟机数据存储中的数据
		List<MODevice> storeDeviceDataList = modMaper.queryVirtualStoreList(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 虚拟机数据存储Id collection
		Set<Integer> storeDeviceIdSet = new TreeSet();
		// 获取虚拟机数据存储的MOID和MONAME
		Map<Integer, String> storeDeviceMap = new HashMap<Integer, String>();
		// 获取所有相对应的虚拟机数据存储表中的数据
		Map<String, MODevice> allStoreDeviceData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,虚拟机数据存储Id集合...
		for (int i = 0; i < storeDeviceDataList.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(storeDeviceDataList.get(i).getCollecttime()));
			// 放入虚拟机数据存储ID节点
			storeDeviceIdSet.add(storeDeviceDataList.get(i).getMoid());
			// 放入虚拟机数据存储 数据节点
			storeDeviceMap.put(storeDeviceDataList.get(i).getMoid(), storeDeviceDataList.get(i).getMoname());
			// 重新组织虚拟机数据存储数据map
			String key = String.valueOf(storeDeviceDataList.get(i).getMoid()) 
					   + "_" + simple.format(storeDeviceDataList.get(i).getCollecttime());
			allStoreDeviceData.put(key, storeDeviceDataList.get(i));
		}
		
		// 构造相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer storeDeviceId : storeDeviceIdSet) {
				String mapKey = storeDeviceId + "_" + timePoint;
				// 判断mapKey在allStoreDeviceData中是否有相关数据
				if (allStoreDeviceData.containsKey(mapKey)) {
					if (tmpDataMap.get(storeDeviceId) != null) {
						String dataValue = tmpDataMap.get(storeDeviceId);
						if(perf.equals("1")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getReadspeed())+",";
						}else if(perf.equals("2")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getWritespeed())+",";
						}else if(perf.equals("3")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getReadrequests())+",";
						}else if(perf.equals("4")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getWriterequests())+",";
						}else if(perf.equals("5")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getReadlatency())+",";
						}else if(perf.equals("6")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getWritelatency())+",";
						}
						tmpDataMap.put(storeDeviceId, dataValue);
					} else {
						if(perf.equals("1")){
							tmpDataMap.put(storeDeviceId,decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getReadspeed())+",");
						}else if(perf.equals("2")){
							tmpDataMap.put(storeDeviceId,decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getWritespeed())+",");
						}else if(perf.equals("3")){
							tmpDataMap.put(storeDeviceId,decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getReadrequests())+",");
						}else if(perf.equals("4")){
							tmpDataMap.put(storeDeviceId,decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getWriterequests())+",");
						}else if(perf.equals("5")){
							tmpDataMap.put(storeDeviceId,decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getReadlatency())+",");
						}else if(perf.equals("6")){
							tmpDataMap.put(storeDeviceId,decimalForTwo.DecimalFormatFloat(allStoreDeviceData.get(mapKey).getWritelatency())+",");
						}
					}
				} else {
					if (tmpDataMap.get(storeDeviceId) != null) {
						String dataValue = tmpDataMap.get(storeDeviceId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(storeDeviceId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(storeDeviceId, "0,");
					}
				}
			}
		}
		
		
		// 组织虚拟机数据存储数据
		String legend = "";
		List<Float> storeDeviceDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : storeDeviceMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			storeDeviceDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 虚拟机数据存储name arr[]
			legend += entry.getValue() + ",";
			
			// 找出虚拟机数据存储对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				storeDeviceDatalist.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(storeDeviceDatalist);
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
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
		
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载虚拟机数据存储数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
	/**
	 * 跳转宿主机历史详情页面
	 */
	@RequestMapping("/toVHostDetailChartLine")
	public ModelAndView toVHostDetailChartLine(HttpServletRequest request, ModelMap map) {
		String MOID=request.getParameter("moID");
		map.put("MOID", MOID);
		return new ModelAndView("monitor/host/hostDetailChart");
	}
	
	/**
	 * * 宿主机详情cpu图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/hostCpuChartData")
	@ResponseBody
	public Map<String, Object> hostCpuChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载宿主机cpu数据");
		String perf = request.getParameter("perfKind");
		
		Map params = new HashMap();
		StringBuffer axisTime = new StringBuffer("");
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOCPUs");
		params.put("avgUsage", "CPUsage");
		params.put("perfTabName", "PerfVHostCPU");
		
		// 性能数据json对象
		ECharObj jsonData = new ECharObj();
		if (perf.equals("1")) {
			jsonData.setText("单核使用率 (%)");
		} else if (perf.equals("2")) {
			jsonData.setText("总使用时间 (ms)");
		} else if (perf.equals("3")) {
			jsonData.setText("空闲时间 (ms)");
		}

		// 获取所有性能表中的数据
		List<MODevice> allPropertyData = modMaper.queryAll(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// cpuId collection
		Set<Integer> cpuIdSet = new TreeSet();
		// 获取cpu的MOID和MONAME
		Map<Integer, String> cpuMap = new HashMap<Integer, String>();
		// 获取所有相对应的性能表中的数据
		Map<String, MODevice> allCpuData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,cpuId集合...
		for (int i = 0; i < allPropertyData.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(allPropertyData.get(i).getCollecttime()));
			// 放入CPU ID节点
			cpuIdSet.add(allPropertyData.get(i).getMoid());
			// 放入CPU 数据节点
			cpuMap.put(allPropertyData.get(i).getMoid(), allPropertyData.get(i).getMoname());
			// 重新组织CPU性能数据map
			String key = String.valueOf(allPropertyData.get(i).getMoid()) 
					   + "_" + simple.format(allPropertyData.get(i).getCollecttime());
			allCpuData.put(key, allPropertyData.get(i));
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

			for (Integer cpuId : cpuIdSet) {
				String mapKey = cpuId + "_" + timePoint;
				// 判断mapKey在allCpuData中是否有相关数据
				if (allCpuData.containsKey(mapKey)) {
					if (cpuIdSet.size() > 1 && firstOne == true) {
						existFlag = true;
						avgDataStr += allCpuData.get(mapKey).getCpusage() + ",";
					}
					firstOne = false;
					if (tmpDataMap.get(cpuId) != null) {
						String dataValue = tmpDataMap.get(cpuId);
						if (perf.equals("1")) {
							dataValue += decimalForTwo.DecimalFormatFloat(allCpuData.get(mapKey) .getUtilizationcore())+ ",";
						} else if (perf.equals("2")) {
							dataValue += allCpuData.get(mapKey).getUsed() + ",";
						} else if (perf.equals("3")) {
							dataValue += allCpuData.get(mapKey).getIdle() + ",";
						}
						tmpDataMap.put(cpuId, dataValue);
					} else {
						if (perf.equals("1")) {
							tmpDataMap.put(cpuId, decimalForTwo.DecimalFormatFloat(allCpuData.get(mapKey).getUtilizationcore())+ ",");
						} else if (perf.equals("2")) {
							tmpDataMap.put(cpuId, allCpuData.get(mapKey).getUsed()+ ",");
						} else if (perf.equals("3")) {
							tmpDataMap.put(cpuId, allCpuData.get(mapKey).getIdle()+ ",");
						}
					}
				} else {
					if (tmpDataMap.get(cpuId) != null) {
						String dataValue = tmpDataMap.get(cpuId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(cpuId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(cpuId, "0,");
					}
				}
			}

			if (existFlag == false) {
				avgDataStr += "0,";
			}
		}

		// 组织性能数据
		String legend = "";
		List<Float> cpuDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : cpuMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			cpuDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// cpu name arr[]
			legend += entry.getValue() + ",";
			
			// 找出cpu对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				cpuDatalist.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(cpuDatalist);
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
		
		if (cpuMap.size() > 1) {
			legend += "平均使用率,";
			curveObj = new CurveObj();
			cpuDatalist = new ArrayList<Float>();
			curveObj.setName("平均使用率");
			curveObj.setType("line");
			
			if(avgDataStr.indexOf(",")>0){
				String[] arr = avgDataStr.split(","); 
				for (int i = 0; i < arr.length; i++) {
					cpuDatalist.add(Float.parseFloat(arr[i]));
				}
			}
			
			curveObj.setData(cpuDatalist);
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
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载宿主机cpu数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}

		return null;
	}
	
	/**
	 * * 宿主机详情内存图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/hostMemoryChartData")
	@ResponseBody
	public Map<String, Object> hostMemoryChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载宿主机内存数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOMemories");
		params.put("avgUsage", "MemUsage");
		params.put("perfTabName", "PerfVHostMemory");
		//查询设备内存的个数	
		List<MODevice> memoryDeviceList = modMaper.queryMainMemoryData(params);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer axisTime = new StringBuffer("");
		// 宿主机内存数据json对象
		ECharObj jsonData = new ECharObj();
		String perf = request.getParameter("perfKind");
		
		if(perf.equals("1")){
			jsonData.setText("使用率 (%)");
		}else if(perf.equals("2")){
			jsonData.setText("活动内存 (MB)");
		}else if(perf.equals("3")){
			jsonData.setText("空闲容量 (MB)");
		}else if(perf.equals("4")){
			jsonData.setText("系统开销内存 (MB)");
		}else if(perf.equals("5")){
			jsonData.setText("共享内存 (MB)");
		}else if(perf.equals("6")){
			jsonData.setText("共享的通用内存 (MB)");
		}else if(perf.equals("7")){
			jsonData.setText("写入交换内存 (MB)");
		}else if(perf.equals("8")){
			jsonData.setText("读出交换内存 (MB)");
		}else if(perf.equals("9")){
			jsonData.setText("已用交换内存 (MB)");
		}
		
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 宿主机内存Id collection
		Set<Integer> memoryIdSet = new TreeSet();
		// 获取宿主机内存的MOID和MONAME
		Map<Integer, String> memoryMap = new HashMap<Integer, String>();
		// 获取所有相对应的内存的数据
		Map<String, MODevice> allMemoryData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,宿主机内存Id集合...
		for (int i = 0; i < memoryDeviceList.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(memoryDeviceList.get(i).getCollecttime()));
			// 放入宿主机内存 ID节点
			memoryIdSet.add(memoryDeviceList.get(i).getMoid());
			// 放入宿主机内存 数据节点
			memoryMap.put(memoryDeviceList.get(i).getMoid(), memoryDeviceList.get(i).getMoname());
			// 重新组织CPU宿主机内存数据map
			String key = String.valueOf(memoryDeviceList.get(i).getMoid()) 
					   + "_" + simple.format(memoryDeviceList.get(i).getCollecttime());
			allMemoryData.put(key, memoryDeviceList.get(i));
		}
		
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer  memoryId : memoryIdSet) {
				String mapKey = memoryId + "_" + timePoint;
				// 判断mapKey在allMemoryData中是否有相关数据
				if (allMemoryData.containsKey(mapKey)) {
					if (tmpDataMap.get(memoryId) != null) {
						String dataValue = tmpDataMap.get(memoryId);
						if(perf.equals("1")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allMemoryData.get(mapKey).getMemusage())+",";
						}else if(perf.equals("2")){
							dataValue+=df.format(Double.valueOf(allMemoryData.get(mapKey).getActive())/1024/1024)+",";//b转换成MB
						}else if(perf.equals("3")){
							dataValue+=df.format(Double.valueOf(allMemoryData.get(mapKey).getFreesize())/1024/1024)+",";
						}else if(perf.equals("4")){
							dataValue+=df.format(Double.valueOf(allMemoryData.get(mapKey).getOverhead())/1024/1024)+",";
						}else if(perf.equals("5")){
							dataValue+=df.format(Double.valueOf(allMemoryData.get(mapKey).getShared())/1024/1024)+",";
						}else if(perf.equals("6")){
							dataValue+=df.format(Double.valueOf(allMemoryData.get(mapKey).getSharedcommon())/1024/1024)+",";
						}else if(perf.equals("7")){
							dataValue+=df.format(Double.valueOf(allMemoryData.get(mapKey).getSwapin())/1024/1024)+",";
						}else if(perf.equals("8")){
							dataValue+=df.format(Double.valueOf(allMemoryData.get(mapKey).getSwapout())/1024/1024)+",";
						}else if(perf.equals("9")){
							dataValue+=df.format(Double.valueOf(allMemoryData.get(mapKey).getSwapused())/1024/1024)+",";
						}
						tmpDataMap.put(memoryId, dataValue);
					} else {
						if(perf.equals("1")){
							tmpDataMap.put(memoryId, decimalForTwo.DecimalFormatFloat(allMemoryData.get(mapKey).getMemusage())+",");
						}else if(perf.equals("2")){
							tmpDataMap.put(memoryId,df.format(Double.valueOf(allMemoryData.get(mapKey).getActive())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("3")){
							tmpDataMap.put(memoryId,df.format(Double.valueOf(allMemoryData.get(mapKey).getFreesize())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("4")){
							tmpDataMap.put(memoryId,df.format(Double.valueOf(allMemoryData.get(mapKey).getOverhead())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("5")){
							tmpDataMap.put(memoryId,df.format(Double.valueOf(allMemoryData.get(mapKey).getShared())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("6")){
							tmpDataMap.put(memoryId,df.format(Double.valueOf(allMemoryData.get(mapKey).getSharedcommon())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("7")){
							tmpDataMap.put(memoryId,df.format(Double.valueOf(allMemoryData.get(mapKey).getSwapin())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("8")){
							tmpDataMap.put(memoryId,df.format(Double.valueOf(allMemoryData.get(mapKey).getSwapout())/1024/1024)+",");//b转换成MB
						}else if(perf.equals("9")){
							tmpDataMap.put(memoryId,df.format(Double.valueOf(allMemoryData.get(mapKey).getSwapused())/1024/1024)+",");//b转换成MB
						}
					}
				} else {
					if (tmpDataMap.get(memoryId) != null) {
						String dataValue = tmpDataMap.get(memoryId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(memoryId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(memoryId, "0,");
					}
				}
			}
		}
		
		
		
		// 组织宿主机内存数据
		String legend = "";
		List<Float> memoryDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : memoryMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			memoryDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 宿主机内存 name arr[]
			legend += entry.getValue() + ",";
			
			// 找出宿主机内存对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				memoryDatalist.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(memoryDatalist);
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
		
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");

		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载宿主机内存数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		
		return null;
	}
	
	/**
	 * * 宿主机详情硬盘图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/hostHardDiskChartData")
	@ResponseBody
	public Map<String, Object> hostHardDiskChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载宿主机硬盘数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOVolumes");
		params.put("perfTabName", "PerfVHostDisk");
		
		//存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		
		String perf = request.getParameter("perfKind");
		
		StringBuffer axisTime = new StringBuffer("");
		
		if(perf.equals("1")){
			jsonData.setText("I/O使用率 (KBps)");
		}else if(perf.equals("2")){
			jsonData.setText("读盘速度 (KBps)");
		}else if(perf.equals("3")){
			jsonData.setText("写盘速度 (KBps)");
		}else if(perf.equals("4")){
			jsonData.setText("读盘请求");
		}else if(perf.equals("5")){
			jsonData.setText("写盘请求");
		}else if(perf.equals("6")){
			jsonData.setText("磁盘总线重置");
		}else if(perf.equals("7")){
			jsonData.setText("中止磁盘命令");
		}else if(perf.equals("8")){
			jsonData.setText("写盘延时 (ms)");
		}else if(perf.equals("9")){
			jsonData.setText("读盘延时 (ms)");
		}
		
		// 获取所有表中的数据
		List<MODevice> hardDeviceDataList = modMaper.queryHostHardDataList(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 宿主机磁盘Id collection
		Set<Integer> hardDeviceIdSet = new TreeSet();
		// 获取宿主机磁盘的MOID和MONAME
		Map<Integer, String> hardDeviceMap = new HashMap<Integer, String>();
		// 获取所有相对应的表中的数据
		Map<String, MODevice> allHardDeviceData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,宿主机磁盘Id集合...
		for (int i = 0; i < hardDeviceDataList.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(hardDeviceDataList.get(i).getCollecttime()));
			// 放入宿主机磁盘 ID节点
			hardDeviceIdSet.add(hardDeviceDataList.get(i).getMoid());
			// 放入宿主机磁盘 数据节点
			hardDeviceMap.put(hardDeviceDataList.get(i).getMoid(), hardDeviceDataList.get(i).getMoname());
			// 重新组织宿主机磁盘数据map
			String key = String.valueOf(hardDeviceDataList.get(i).getMoid()) 
					   + "_" + simple.format(hardDeviceDataList.get(i).getCollecttime());
			allHardDeviceData.put(key, hardDeviceDataList.get(i));
		}
		// 构造相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer hardDeviceId : hardDeviceIdSet) {
				String mapKey = hardDeviceId + "_" + timePoint;
				// 判断mapKey在allCpuData中是否有相关数据
				if (allHardDeviceData.containsKey(mapKey)) {
					if (tmpDataMap.get(hardDeviceId) != null) {
						String dataValue = tmpDataMap.get(hardDeviceId);
						// 判断展示哪个类型的曲线图
						if(perf.equals("1")){
							dataValue += decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getIousage())+",";
						}else if(perf.equals("2")){
							dataValue += decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getReadspeed())+",";
						}else if(perf.equals("3")){
							dataValue +=decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getWritespeed())+",";
						}else if(perf.equals("4")){
							dataValue += decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getReadrequests())+",";
						}else if(perf.equals("5")){
							dataValue += decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getWriterequests())+",";
						}else if(perf.equals("6")){
							dataValue += decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getBusresets())+",";
						}else if(perf.equals("7")){
							dataValue += decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getCommandaborts())+",";
						}else if(perf.equals("8")){
							dataValue +=decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getWritelatency())+",";
						}else if(perf.equals("9")){
							dataValue +=decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getReadlatency())+",";
						}
						tmpDataMap.put(hardDeviceId, dataValue);
					} else {
						// 判断展示哪个类型的曲线图
						if(perf.equals("1")){
							tmpDataMap.put(hardDeviceId,decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getIousage())+",");
						}else if(perf.equals("2")){
							tmpDataMap.put(hardDeviceId, decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getReadspeed())+",");
						}else if(perf.equals("3")){
							tmpDataMap.put(hardDeviceId,decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getWritespeed())+",");
						}else if(perf.equals("4")){
							tmpDataMap.put(hardDeviceId,decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getReadrequests())+",");
						}else if(perf.equals("5")){
							tmpDataMap.put(hardDeviceId,decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getWriterequests())+",");
						}else if(perf.equals("6")){
							tmpDataMap.put(hardDeviceId,decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getBusresets())+",");
						}else if(perf.equals("7")){
							tmpDataMap.put(hardDeviceId,decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getCommandaborts())+",");
						}else if(perf.equals("8")){
							tmpDataMap.put(hardDeviceId,decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getWritelatency())+",");
						}else if(perf.equals("9")){
							tmpDataMap.put(hardDeviceId,decimalForTwo.DecimalFormatFloat(allHardDeviceData.get(mapKey).getReadlatency())+",");
						}
					}
				} else {
					if (tmpDataMap.get(hardDeviceId) != null) {
						String dataValue = tmpDataMap.get(hardDeviceId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(hardDeviceId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(hardDeviceId, "0,");
					}
				}
			}
		}
		
		// 组织宿主机磁盘数据
		String legend = "";
		List<Float> hardDeviceDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : hardDeviceMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			hardDeviceDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 宿主机磁盘name arr[]
			legend += entry.getValue() + ",";
			
			// 找出宿主机磁盘对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				hardDeviceDatalist.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(hardDeviceDatalist);
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
		
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
		
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载宿主机磁盘数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}

	 
	
	/**
	 * * 宿主机详情数据存储图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/hostStoreChartData")
	@ResponseBody
	public Map<String, Object> hostStoreChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载宿主机数据存储数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOStorage");
		params.put("perfTabName", "PerfVHostDatastore");
		//查询设备相关信息
		List<MODevice> storeDeviceDataList = modMaper.queryHostStoreDataList(params);
		//存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		
		String perf = request.getParameter("perfKind");
		
		StringBuffer axisTime = new StringBuffer("");
		
		if(perf.equals("1")){
			jsonData.setText("使用率 (%)");
		}else if(perf.equals("2")){
			jsonData.setText("空闲容量 (GB)");
		}else if(perf.equals("3")){
			jsonData.setText("读速度 (KBps)");
		}else if(perf.equals("4")){
			jsonData.setText("写速度 (KBps)");
		}else if(perf.equals("5")){
			jsonData.setText("读请求");
		}else if(perf.equals("6")){
			jsonData.setText("写请求");
		}else if(perf.equals("7")){
			jsonData.setText("读延时 (ms)");
		}else if(perf.equals("8")){
			jsonData.setText("写延时 (ms)");
		}else if(perf.equals("9")){
			jsonData.setText("规范延时 (ms)");
		}else if(perf.equals("10")){
			jsonData.setText("IO操作数据存储总数");
		}
		
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 宿主机Id collection
		Set<Integer> hostStoreIdSet = new TreeSet();
		// 获取宿主机MOID和MONAME
		Map<Integer, String> hostStoreMap = new HashMap<Integer, String>();
		// 获取所有相对应的表中的数据
		Map<String, MODevice> allHostStoreData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,宿主机id集合...
		for (int i = 0; i < storeDeviceDataList.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(storeDeviceDataList.get(i).getCollecttime()));
			// 放入宿主机 ID节点
			hostStoreIdSet.add(storeDeviceDataList.get(i).getMoid());
			// 放入宿主机 数据节点
			hostStoreMap.put(storeDeviceDataList.get(i).getMoid(), storeDeviceDataList.get(i).getMoname());
			// 重新组织宿主机数据map
			String key = String.valueOf(storeDeviceDataList.get(i).getMoid()) 
					   + "_" + simple.format(storeDeviceDataList.get(i).getCollecttime());
			allHostStoreData.put(key, storeDeviceDataList.get(i));
		}
		
		
		
		// 构造相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer hostStoreId : hostStoreIdSet) {
				String mapKey = hostStoreId + "_" + timePoint;
				// 判断mapKey在allHostStoreData中是否有相关数据
				if (allHostStoreData.containsKey(mapKey)) {
					if (tmpDataMap.get(hostStoreId) != null) {
						String dataValue = tmpDataMap.get(hostStoreId);
						if(perf.equals("1")){
							dataValue+=decimalForTwo.DecimalFormatString(allHostStoreData.get(mapKey).getDatastoreusage())+",";
						}else if(perf.equals("2")){
							dataValue+=df.format(Double.valueOf(allHostStoreData.get(mapKey).getFreesize())/1024/1024/1024)+",";
						}else if(perf.equals("3")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getReadspeed())+",";
						}else if(perf.equals("4")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getWritespeed())+",";
						}else if(perf.equals("5")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getReadrequests())+",";
						}else if(perf.equals("6")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getWriterequests())+",";
						}else if(perf.equals("7")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getReadlatency())+",";
						}else if(perf.equals("8")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getWritelatency())+",";
						}else if(perf.equals("9")){
							dataValue+=allHostStoreData.get(mapKey).getNormalizedlatency()+",";
						}else if(perf.equals("10")){
							dataValue+=allHostStoreData.get(mapKey).getIops()+",";
						}
						tmpDataMap.put(hostStoreId, dataValue);
					} else {
						if(perf.equals("1")){
							tmpDataMap.put(hostStoreId,decimalForTwo.DecimalFormatString(allHostStoreData.get(mapKey).getDatastoreusage())+",");
						}else if(perf.equals("2")){
							tmpDataMap.put(hostStoreId,df.format(Double.valueOf(allHostStoreData.get(mapKey).getFreesize())/1024/1024/1024)+",");
						}else if(perf.equals("3")){
							tmpDataMap.put(hostStoreId,decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getReadspeed())+",");
						}else if(perf.equals("4")){
							tmpDataMap.put(hostStoreId,decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getWritespeed())+",");
						}else if(perf.equals("5")){
							tmpDataMap.put(hostStoreId,decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getReadrequests())+",");
						}else if(perf.equals("6")){
							tmpDataMap.put(hostStoreId,decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getWriterequests())+",");
						}else if(perf.equals("7")){
							tmpDataMap.put(hostStoreId,decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getReadlatency())+",");
						}else if(perf.equals("8")){
							tmpDataMap.put(hostStoreId,decimalForTwo.DecimalFormatFloat(allHostStoreData.get(mapKey).getWritelatency())+",");
						}else if(perf.equals("9")){
							tmpDataMap.put(hostStoreId,allHostStoreData.get(mapKey).getNormalizedlatency()+",");
						}else if(perf.equals("10")){
							tmpDataMap.put(hostStoreId,allHostStoreData.get(mapKey).getIops()+",");
						}
					}
				} else {
					if (tmpDataMap.get(hostStoreId) != null) {
						String dataValue = tmpDataMap.get(hostStoreId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(hostStoreId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(hostStoreId, "0,");
					}
				}
			}
		}
		
		// 组织宿主机数据
		String legend = "";
		List<Float> hostStoreDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : hostStoreMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			hostStoreDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 宿主机 name arr[]
			legend += entry.getValue() + ",";
			
			// 找出宿主机对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				hostStoreDatalist.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(hostStoreDatalist);
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
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
	 
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载宿主机数据存储数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
	/**
	 * * 宿主机详情接口图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/hostInterfaceChartData")
	@ResponseBody
	public Map<String, Object> hostInterfaceChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载宿主机接口数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MONetworkIf");
		params.put("perfTabName", "PerfVHostNetwork");
		 
		//存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		String perf = request.getParameter("perfKind");
		
		StringBuffer axisTime = new StringBuffer("");
		
		if(perf.equals("1")){
			jsonData.setText("总流量 (KBps)");
		}else if(perf.equals("2")){
			jsonData.setText("流入流量 (KBps)");
		}else if(perf.equals("3")){
			jsonData.setText("流出流量 (KBps)");
		}else if(perf.equals("4")){
			jsonData.setText("流入包数");
		}else if(perf.equals("5")){
			jsonData.setText("流出包数");
		}else if(perf.equals("6")){
			jsonData.setText("总利用率(%)");
		}else if(perf.equals("7")){
			jsonData.setText("流入利用率(%)");
		}else if(perf.equals("8")){
			jsonData.setText("流出利用率(%)");
		}else if(perf.equals("9")){
			jsonData.setText("接口速率(%)");
		}

		// 获取所有宿主机接口表中的数据
		List<MODevice> interfaceDeviceDataList = modMaper.queryVhostInterfaceList(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 宿主机接口Id collection
		Set<Integer> interfaceIdSet = new TreeSet();
		// 获取宿主机接口的MOID和MONAME
		Map<Integer, String> interfaceMap = new HashMap<Integer, String>();
		// 获取所有相对应的宿主机接口表中的数据
		Map<String, MODevice> allInterfaceData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,宿主机接口Id集合...
		for (int i = 0; i < interfaceDeviceDataList.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(interfaceDeviceDataList.get(i).getCollecttime()));
			// 放入宿主机接口 ID节点
			interfaceIdSet.add(interfaceDeviceDataList.get(i).getMoid());
			// 放入宿主机接口 数据节点
			interfaceMap.put(interfaceDeviceDataList.get(i).getMoid(), interfaceDeviceDataList.get(i).getMoname());
			// 重新组织宿主机接口数据map
			String key = String.valueOf(interfaceDeviceDataList.get(i).getMoid()) 
					   + "_" + simple.format(interfaceDeviceDataList.get(i).getCollecttime());
			allInterfaceData.put(key, interfaceDeviceDataList.get(i));
		}
		
		// 构造相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer interfaceId : interfaceIdSet) {
				String mapKey = interfaceId + "_" + timePoint;
				// 判断mapKey在allCpuData中是否有相关数据
				if (allInterfaceData.containsKey(mapKey)) {
					 
					if (tmpDataMap.get(interfaceId) != null) {
						String dataValue = tmpDataMap.get(interfaceId);
						if(perf.equals("1")){					
							dataValue+=decimalForTwo.DecimalFormatFloat(allInterfaceData.get(mapKey).getNetworkusage())+",";
						}else if(perf.equals("2")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allInterfaceData.get(mapKey).getReceivedspeed())+",";
						}else if(perf.equals("3")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allInterfaceData.get(mapKey).getTransmittedspeed())+",";
						}else if(perf.equals("4")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allInterfaceData.get(mapKey).getPacketsreceived())+",";
						}else if(perf.equals("5")){
							dataValue+=decimalForTwo.DecimalFormatFloat(allInterfaceData.get(mapKey).getPacketstransmitted())+",";
						}else if(perf.equals("6")){
							dataValue+=decimalForTwo.DecimalFormatString(allInterfaceData.get(mapKey).getIfUsage())+",";
						}else if(perf.equals("7")){
							dataValue+=decimalForTwo.DecimalFormatString(allInterfaceData.get(mapKey).getInusage())+",";
						}else if(perf.equals("8")){
							dataValue+=decimalForTwo.DecimalFormatString(allInterfaceData.get(mapKey).getOutusage())+",";
						}else if(perf.equals("9")){
							dataValue+=decimalForTwo.DecimalFormatString(allInterfaceData.get(mapKey).getRate())+",";
						}	
						tmpDataMap.put(interfaceId, dataValue);
					} else {
						
						if(perf.equals("1")){					
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatFloat(allInterfaceData.get(mapKey).getNetworkusage())+",");
						}else if(perf.equals("2")){
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatFloat(allInterfaceData.get(mapKey).getReceivedspeed())+",");
						}else if(perf.equals("3")){
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatFloat(allInterfaceData.get(mapKey).getTransmittedspeed())+",");
						}else if(perf.equals("4")){
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatFloat(allInterfaceData.get(mapKey).getPacketsreceived())+",");
						}else if(perf.equals("5")){
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatFloat(allInterfaceData.get(mapKey).getPacketstransmitted())+",");
						}else if(perf.equals("6")){
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatString(allInterfaceData.get(mapKey).getIfUsage())+",");
						}else if(perf.equals("7")){
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatString(allInterfaceData.get(mapKey).getInusage())+",");
						}else if(perf.equals("8")){
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatString(allInterfaceData.get(mapKey).getOutusage())+",");
						}else if(perf.equals("9")){
							tmpDataMap.put(interfaceId,decimalForTwo.DecimalFormatString(allInterfaceData.get(mapKey).getRate())+",");
						}	
					}
				} else {
					if (tmpDataMap.get(interfaceId) != null) {
						String dataValue = tmpDataMap.get(interfaceId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(interfaceId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(interfaceId, "0,");
					}
				}
			}
		}
		
		// 组织宿主机接口数据
		String legend = "";
		List<Float> interfaceDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : interfaceMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			interfaceDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 接口 name arr[]
			legend += entry.getValue() + ",";
			
			// 找出接口对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				interfaceDatalist.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(interfaceDatalist);
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
		// remove last "," 
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(axisTime.length()-1).toString() : "");
		
		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData).toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载宿主机接口数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
	
	/**
	 * 跳转cpu曲线图
	 */
	@RequestMapping("/toCPUChartLine")
	public ModelAndView toCPUChartLine(HttpServletRequest request, ModelMap map) {
		String MOID=request.getParameter("moID");
		map.put("MOID", MOID);
		return new ModelAndView("monitor/host/cpuLineChart");
	}
	/**
	 * 跳转内存曲线图
	 */
	@RequestMapping("/toMemoryChartLine")
	public ModelAndView toMemoryChartLine(HttpServletRequest request, ModelMap map) {
		String MOID=request.getParameter("moID");
		map.put("MOID", MOID);
		return new ModelAndView("monitor/host/memoryLineChart");
	}
	
	/**
	 * 路由器 交换机 cpu图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/netCpuChartData")
	@ResponseBody
	public Map<String, Object> netCpuChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载主机cpu数据");	
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOCPUs");
		params.put("avgUsage", "perUsage");
		params.put("perfTabName", "PerfNetworkCPU");
		
		StringBuffer axisTime = new StringBuffer("");
		
		//存放返回页面json数据对象
		ECharObj jsonData = new ECharObj();
		jsonData.setText("使用率  (%)");
		
		// 获取所有路由器 交换机CPU表中的数据
		List<MODevice> allCpuDeviceList = modMaper.queryNetCPUList(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 路由器 交换机CPUId collection
		Set<Integer> cpuIdSet = new TreeSet();
		// 获取路由器 交换机CPU的MOID和MONAME
		Map<Integer, String> cpuMap = new HashMap<Integer, String>();
		// 获取所有相对应的路由器 交换机CPU表中的数据
		Map<String, MODevice> allCpuData = new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,路由器 交换机CPUId集合...
		for (int i = 0; i < allCpuDeviceList.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(allCpuDeviceList.get(i).getCollecttime()));
			// 放入路由器 交换机CPU ID节点
			cpuIdSet.add(allCpuDeviceList.get(i).getMoid());
			// 放入路由器 交换机CPU 数据节点
			cpuMap.put(allCpuDeviceList.get(i).getMoid(), allCpuDeviceList.get(i).getMoname());
			// 重新组织路由器 交换机CPU数据map
			String key = String.valueOf(allCpuDeviceList.get(i).getMoid()) 
					   + "_" + simple.format(allCpuDeviceList.get(i).getCollecttime());
			allCpuData.put(key, allCpuDeviceList.get(i));
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

			for (Integer cpuId : cpuIdSet) {
				String mapKey = cpuId + "_" + timePoint;
				// 判断mapKey在allCpuData中是否有相关数据
				if (allCpuData.containsKey(mapKey)) {
					if (cpuIdSet.size() > 1 && firstOne == true) {
						existFlag = true;
						avgDataStr += decimalForTwo.DecimalFormatDouble(allCpuData.get(mapKey).getCpusage()) + ",";
					}
					firstOne = false;
					if (tmpDataMap.get(cpuId) != null) {
						String dataValue = tmpDataMap.get(cpuId);
						dataValue += decimalForTwo.DecimalFormatString(allCpuData.get(mapKey).getPerusage())+ ",";
						tmpDataMap.put(cpuId, dataValue);
					} else {
						tmpDataMap.put(cpuId, decimalForTwo.DecimalFormatString(allCpuData.get(mapKey).getPerusage())+ ",");
					}
				} else {
					if (tmpDataMap.get(cpuId) != null) {
						String dataValue = tmpDataMap.get(cpuId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(cpuId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(cpuId, "0,");
					}
				}
			}

			if (existFlag == false) {
				avgDataStr += "0,";
			}
		}
		
		// 组织路由器 交换机CPU数据
		String legend = "";
		List<Float> cpuDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : cpuMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			cpuDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 路由器 交换机CPU name arr[]
			legend += entry.getValue() + ",";
			
			// 找出路由器 交换机CPU对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				cpuDatalist.add(Float.parseFloat(arr[i]));
			}
			
			curveObj.setData(cpuDatalist);
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
		
		if (cpuMap.size() > 1) {
			legend += "平均使用率,";
			curveObj = new CurveObj();
			cpuDatalist = new ArrayList<Float>();
			curveObj.setName("平均使用率");
			curveObj.setType("line");
			
			if(avgDataStr.indexOf(",")>0){
				String[] arr = avgDataStr.split(","); 
				for (int i = 0; i < arr.length; i++) {
					cpuDatalist.add(Float.parseFloat(arr[i]));
				}
			}
			
			curveObj.setData(cpuDatalist);
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
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...路由器 交换机CPUcpu数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		
		return null;
	}
	
	/**
	 * * 路由器 交换机内存图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/netMemoryChartData")
	@ResponseBody
	public Map<String, Object> netMemoryChartData(HttpServletRequest request) throws Exception {
		logger.info("开始...加载主机内存数据");
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("DeviceMOID", request.getParameter("MOID"));
		params.put("tableName", "MOMemories");
		params.put("avgUsage", "PerMemoryUsage");
		params.put("perfTabName", "PerfNetworkMemory");
		//存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		jsonData.setText("内存使用率 (%)");
			
		StringBuffer axisTime = new StringBuffer("");
		
		// 获取所有路由器 交换机 内存历史的数据
		List<MODevice> allNetMemoryData = modMaper.queryNetMemoryList(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 路由器 交换机 内存历史Id collection
		Set<Integer> netMemoryIdSet = new TreeSet();
		// 获取路由器 交换机 内存历史的MOID和MONAME
		Map<Integer, String> netMemoryMap = new HashMap<Integer, String>();
		// 获取所有相对应的路由器 交换机 内存历史中的数据
		Map<String, MODevice> allNetMemoryDatas= new HashMap<String, MODevice>(); 
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>(); 
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>(); 
		// 构建时间对象,路由器 交换机 内存历史Id集合...
		for (int i = 0; i < allNetMemoryData.size(); i++) { 
			// 放入时间节点
			timeSet.add(simple.format(allNetMemoryData.get(i).getCollecttime()));
			// 放入路由器 交换机 内存历史 ID节点
			netMemoryIdSet.add(allNetMemoryData.get(i).getMoid());
			// 放入路由器 交换机 内存历史 数据节点
			netMemoryMap.put(allNetMemoryData.get(i).getMoid(), allNetMemoryData.get(i).getMoname());
			// 重新组织路由器 交换机 内存历史数据map
			String key = String.valueOf(allNetMemoryData.get(i).getMoid()) 
					   + "_" + simple.format(allNetMemoryData.get(i).getCollecttime());
			allNetMemoryDatas.put(key, allNetMemoryData.get(i));
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

			for (Integer netMemoryId : netMemoryIdSet) {
				String mapKey = netMemoryId + "_" + timePoint;
				// 判断mapKey在allNetMemoryDatas中是否有相关数据
				if (allNetMemoryDatas.containsKey(mapKey)) {
					if (netMemoryIdSet.size() > 1 && firstOne == true) {
						existFlag = true;
						avgDataStr +=decimalForTwo.DecimalFormatString(allNetMemoryDatas.get(mapKey).getMemoryusage())+ ",";
					}
					firstOne = false;
					if (tmpDataMap.get(netMemoryId) != null) {
						String dataValue = tmpDataMap.get(netMemoryId);
						
						dataValue += decimalForTwo.DecimalFormatString(allNetMemoryDatas.get(mapKey) .getPerMemoryUsage())+ ",";
					 
						tmpDataMap.put(netMemoryId, dataValue);
					} else {
						tmpDataMap.put(netMemoryId, decimalForTwo.DecimalFormatString(allNetMemoryDatas.get(mapKey) .getPerMemoryUsage())+ ",");
					}
				} else {
					if (tmpDataMap.get(netMemoryId) != null) {
						String dataValue = tmpDataMap.get(netMemoryId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(netMemoryId, dataValue + ""+dataArray[dataArray.length-1]+",");
					} else {
						tmpDataMap.put(netMemoryId, "0,");
					}
				}
			}
			if (existFlag == false) {
				avgDataStr += "0,";
			}
		}
		
		// 组织路由器 交换机 内存历史数据
		String legend = "";
		List<Float> netMemoryDatalist = null;
		CurveObj curveObj = null; 
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : netMemoryMap.entrySet()) {
			curveObj = new CurveObj(); 
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			netMemoryDatalist = new ArrayList<Float>();
			
			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 路由器 交换机 内存历史 name arr[]
			legend += entry.getValue() + ",";
			
			// 找出路由器 交换机 内存历史对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				netMemoryDatalist.add(Float.parseFloat(arr[i]));
			}
			curveObj.setData(netMemoryDatalist);
			// 展示部件时曲线不显示最值
			if(params.get("markPoint") != null)
			{
				mineList.add(new Mine2Obj("min","最小值"));
				mineList.add(new Mine2Obj("max","最大值"));
				mineObj.setData(mineList); 
				curveObj.setMarkPoint(mineObj); 
			}
			seriesData.add(curveObj);
		}
		
		if (netMemoryMap.size() > 1) {
			legend += "平均使用率,";
			curveObj = new CurveObj();
			netMemoryDatalist = new ArrayList<Float>();
			curveObj.setName("平均使用率");
			curveObj.setType("line");
			
			if(avgDataStr.indexOf(",")>0){
				String[] arr = avgDataStr.split(","); 
				for (int i = 0; i < arr.length; i++) {
					netMemoryDatalist.add(Float.parseFloat(arr[i]));
				}
			}
			
			curveObj.setData(netMemoryDatalist);
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
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载路由器 交换机内存数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	/**
	 * 参数分别为原始数据、起始时间、结束时间、时间间隔
	 */
	public List<MODevice> fillData(List<MODevice> data,List<PerfNetworkMemory> memTimeList){
		// 将原始数据转化为Map，方便获取指定日期数据
		Map<String, MODevice> tmpData = new HashMap<String, MODevice>();
		for(MODevice p : data){
			// 键为日期，值为对象
			tmpData.put(p.getCollTime(), p);
		}

		// 最终结果
		List<MODevice> result = new ArrayList<MODevice>();
		
		for (int i = 0; i < memTimeList.size(); i++) {
			MODevice p = tmpData.get(memTimeList.get(i).getFormatTime());
			// 没有该日期数据，补数据
			if(null == p){
				p = new MODevice();
				// 以下代码填充数据
				p.setPerMemoryUsage(String.valueOf(0));
				p.setCollTime(memTimeList.get(i).getFormatTime());
				p.setMemoryusage(String.valueOf(0));
			}
			result.add(p);
		}
		return result;
	}
	
	/**
	 * 设置查询时间,默认当前24小时
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map queryBetweenTime(HttpServletRequest request,Map params){
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");
		
		if(timeBegin == null || timeBegin == "" || timeEnd == null || timeEnd == "" ){
			SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = new Date();
			timeBegin = f.format(d.getTime()-24*60*60*1000);
			timeEnd = f.format(d);
		}
		
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		//表示是否展示最大值、最小值
		params.put("markPoint", request.getParameter("markPoint"));
		return params;
	}
	

}
