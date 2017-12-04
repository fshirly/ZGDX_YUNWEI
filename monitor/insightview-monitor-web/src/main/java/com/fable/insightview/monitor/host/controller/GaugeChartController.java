package com.fable.insightview.monitor.host.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.host.mapper.HostMapper;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;

@Controller
@RequestMapping("/monitor/gaugeChartManage")
public class GaugeChartController {
	@Autowired
	HostMapper modMaper;
	@Autowired 
	WebSiteMapper webSiteMapper;
	@Autowired
	IOracleService orclService;
	private final Logger logger = LoggerFactory.getLogger(HostController.class);
	String moClass;// 设备类型ID
	String tableName;// 表名
	Integer MOID;
	Integer moClassID;
	String cpuUrl;// CPU 曲线跳转地址
	String memUrl;// 内存 曲线跳转地址
	String diskUrl;// 硬盘 曲线跳转地址
	String storeUrl;// 数据存储 曲线跳转地址
	String ifTableName;// 接口表名
	DecimalFormat   format = new DecimalFormat("0.00");
	/**
	 * 根据设备类型 查询 表名
	 * @param moClass
	 * @return
	 */
	public String getParam(String moClass) {
		if ("pvm".equals(moClass)||"gatekeeper".equals(moClass) ||"probe".equals(moClass) || "mobileappagent".equals(moClass) || "loadbalance".equals(moClass)
				|| "proxygateway".equals(moClass) || "host".equals(moClass) || "phost".toUpperCase().equals(moClass.toUpperCase())||"mobileappmanger".equals(moClass)
				|| "null".equals(moClass) || moClass == null) {
			tableName = "PerfSnapshotHost";
		} else if ("vm".equals(moClass)) {
			tableName = "PerfSnapshotVM";
		} else if ("vhost".equals(moClass)) {
			tableName = "PerfSnapshotVHost";
		} else if ("switcher".equals(moClass) || "router".equals(moClass) ||"switcherl2".equals(moClass)||"switcherl3".equals(moClass)) {
			tableName = "PerfSnapshotNetDevice";
		}
		return tableName;
	}
	/**
	 * 曲线CPU 链接
	 * @return
	 */
	public String getCPUUrl() {
		if ("pvm".equals(moClass)||"gatekeeper".equals(moClass) ||"probe".equals(moClass) || "mobileappagent".equals(moClass)|| "mobileappmanger".equals(moClass) || "loadbalance".equals(moClass)
				|| "proxygateway".equals(moClass) ||"host".equals(moClass) || "null".equals(moClass) || moClass == null) {
			cpuUrl = "/monitor/historyManage/initCpuChartData";
		} else if ("vm".equals(moClass)) {
			cpuUrl = "/monitor/historyManage/virtualCpuChartData";
		} else if ("vhost".equals(moClass)) {
			cpuUrl = "/monitor/historyManage/hostCpuChartData";
		}else if("switcher".equals(moClass) || "router".equals(moClass)||"switcherl2".equals(moClass)||"switcherl3".equals(moClass)){
			cpuUrl = "/monitor/historyManage/netCpuChartData";
		}
		return cpuUrl;
	}

	/**
	 * 曲线内存 链接
	 * @return
	 */
	public String getMemUrl() {
		if ("pvm".equals(moClass)||"gatekeeper".equals(moClass) ||"probe".equals(moClass)||"mobileappmanger".equals(moClass)   || "mobileappagent".equals(moClass) || "loadbalance".equals(moClass)
				|| "proxygateway".equals(moClass) ||"host".equals(moClass) || "null".equals(moClass) || moClass == null) {
			memUrl = "/monitor/historyManage/initMemoryChartData";
		} else if ("vm".equals(moClass)) {
			memUrl = "/monitor/historyManage/virtualMemoryChartData";
		} else if ("vhost".equals(moClass)) {
			memUrl = "/monitor/historyManage/hostMemoryChartData";
		}else if("switcher".equals(moClass) || "router".equals(moClass)||"switcherl2".equals(moClass)||"switcherl3".equals(moClass)){
			memUrl = "/monitor/historyManage/netMemoryChartData";
		}
		return memUrl;
	}

	/**
	 * 曲线硬盘 链接
	 * @return
	 */
	public String getDiskUrl() {
		if ("pvm".equals(moClass)||"gatekeeper".equals(moClass) ||"probe".equals(moClass) || "mobileappagent".equals(moClass) ||"mobileappmanger".equals(moClass) || "loadbalance".equals(moClass)
				|| "proxygateway".equals(moClass) ||"host".equals(moClass) || "null".equals(moClass) || moClass == null) {
			diskUrl = "/monitor/historyManage/initHardDiskChartData";
		} else if ("vm".equals(moClass)) {
			diskUrl = "/monitor/historyManage/virtualHardDiskChartData";
		} else if ("vhost".equals(moClass)) {
			diskUrl = "/monitor/historyManage/hostHardDiskChartData";
		}
		return diskUrl;
	}

	/**
	 * 曲线数据存储 链接
	 * @return
	 */
	public String getStoreUrl() {
		if ("vm".equals(moClass)) {
			storeUrl = "/monitor/historyManage/virtualStoreChartData";
		} else if ("vhost".equals(moClass)) {
			storeUrl = "/monitor/historyManage/hostStoreChartData";
		}
		return storeUrl;
	}

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
		}else if("switcherl2".equals(moClass)){
			moClassID = 59;
		}else if("switcherl3".equals(moClass)){
			moClassID = 60;
		}else if("mobileappagent".equals(moClass)){
			moClassID = 133;
		}else if("loadbalance".equals(moClass)){
			moClassID = 134;
		}else if("proxygateway".equals(moClass)){
			moClassID = 135;
		}else if("probe".equals(moClass)){
			moClassID = 136;
		}else if("gatekeeper".equals(moClass)){
			moClassID = 137;
		}else if("mobileappmanger".equals(moClass)){
			moClassID = 132;
		}else if("pvm".equals(moClass)){
			moClassID = 138;
		}
		return moClassID;
	}
	/**
	 * 获取chart主机设备CPU使用率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartCPU")
	@ResponseBody
	public Map findChartCPU() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备IP获取图表CPU使用率");
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		getParam(moClass);
		logger.debug("param 表名称[tableName]={} ", tableName);
		params.put("KPIName", KPINameDef.PERUSAGE);
		params.put("tableName", tableName);
		Map map = new HashMap();
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			MODevice mo = modMaper.getChartCPU(params);
			getCPUUrl();
			String StrUrl = "/monitor/interfaceChart/toHistoryChartsDetail?proUrl="
					+ cpuUrl
					+ "&perfKind=1&MOID="
					+ request.getParameter("moID");
			int period=0;
			Date collectTime;
			long curr=0;
			// 当前时间
			long currTime=System.currentTimeMillis();
			if (mo != null) {
				// 采集时间
				 collectTime=mo.getCollecttime();
				 logger.debug("主机设备{}cpu采集时间为{}",mo.getDeviceip(),collectTime);
				if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
					//采集周期的倍数
					period=mo.getDefDoIntervals()*getMultiple()*60000;
				}else{
					//采集周期的倍数
					period=mo.getDoIntervals()*getMultiple()*1000;
				}
				logger.debug("主机设备{}cpu采集周期的倍数{}",mo.getDeviceip(),period);
				if (collectTime != null) {
					// 当前时间与采集时间差
					curr=currTime-mo.getCollecttime().getTime();
					logger.debug("主机设备{}cpu当前时间与采集时间差{}",mo.getDeviceip(),curr);
					//当前时间与采集时间差超过采集周期的倍数（可配置）或没有采集时间（即没有采集）
					if(curr<=period){
						//map1.put("value", format.format(Double.parseDouble(mo.getPerValue())));
						if(mo.getPerValue() != null){ //存在值为null的情况，此处抛出空指针异常
							map1.put("value",  format.format(Double.parseDouble(mo.getPerValue())));
							// 倘若单核使用率没有时，cpu的平均值为-1后台采集时入库的数据
							if(mo.getPerValue().equals("-1")){
								map1.put("value", "");
							}
						}
						else{
							map1.put("value",  "");
						}
						if(mo.getDevicestatus().equals("2")) {//down
							map1.put("value", "");
						}
						map1.put("name", "当前CPU使用率");
						array1.add(map1);
						map2.put("chartCPU", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}else{
						//当前时间与采集时间差超过采集周期的倍数（可配置）
						map1.put("value", "");
						map1.put("name", "当前CPU使用率");
						array1.add(map1);
						map2.put("chartCPU", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}
				}else{
					// 没有采集时间（即没有采集）
					map1.put("value", "");
					map1.put("name", "当前CPU使用率");
					array1.add(map1);
					map2.put("chartCPU", array1);
					map.put("data", map2);
					map.put("url", StrUrl);
				}
			} else {
				map1.put("value", "");
				map1.put("name", "当前CPU使用率");
				array1.add(map1);
				map2.put("chartCPU", array1);
				map.put("data", map2);
				map.put("url", StrUrl);
			}
		} catch (Exception e) {
			map1.put("value", "");
			map1.put("name", "当前CPU使用率");
			array1.add(map1);
			map2.put("chartCPU", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}

	public int getMultiple(){
		return webSiteMapper.getConfParamValue();
	}
	
	/**
	 * 获取chart设备可用性使用率
	 * 
	 * @param deviceIP
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartAvailability")
	@ResponseBody
	public Map findChartAvailability() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备IP获取图表可用性使用率");
		moClass = request.getParameter("moClass");
		String seltDate = request.getParameter("time");
		String seltDateValue = "";
		if ("24H".equals(seltDate)) {// 最近一天
			seltDateValue = "24小时";
		} else if ("7D".equals(seltDate)) {// 最近一周
			seltDateValue = "最近一周";
		} else if ("Today".equals(seltDate)) {// 今天
			seltDateValue = "今天";
		} else if ("Yes".equals(seltDate)) {// 昨天
			seltDateValue = "昨天";
		}else if ("Week".equals(seltDate)) {// 本周
			seltDateValue = "本周";
		}else if ("Month".equals(seltDate)) {// 本月
			seltDateValue = "本月";
		}else if ("LastMonth".equals(seltDate)) {// 上月
			seltDateValue = "上月";
		}
		String nameValue = seltDateValue + "可用性";
		
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		getmoClassID(moClass);
		params.put("moClassID", moClassID);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		MODevice mo = modMaper.getChartAvailability(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (mo != null) {
			map1.put("value", mo.getDeviceavailability());
			map1.put("name", nameValue);
			array1.add(map1);
			map2.put("chartAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", nameValue);
			array1.add(map1);
			map2.put("chartAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}

	/**
	 * 获取chart设备丢包率
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartLoss")
	@ResponseBody
	public ModelAndView findChartLoss(HttpServletRequest request, ModelMap map)
			throws Exception {
		logger.info("根据设备IP获取图表丢包率");
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		getmoClassID(moClass);
		params.put("KPIName", KPINameDef.DEVICELOSS);
		params.put("moClassID", moClassID);
		MODevice mo = modMaper.getDeviceLoss(params);
		long value = 0;
		if (mo == null || mo.getPerfvalue() == -1) {
			mo = new MODevice();
			mo.setOne("");
			mo.setTwo("");
			mo.setThree("-2");
		} else {
			int period=0;
			Date collectTime;
			long curr=0;
			// 当前时间
			long currTime=System.currentTimeMillis();
			// 采集时间
			 collectTime=mo.getCollecttime();
			if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
				//采集周期的倍数
				period=mo.getDefDoIntervals()*getMultiple()*60000;
			}else{
				//采集周期的倍数
				period=mo.getDoIntervals()*getMultiple()*1000;
			}
			if (collectTime != null) {
				// 当前时间与采集时间差
				curr=currTime-mo.getCollecttime().getTime();
				//当前时间与采集时间差超过采集周期的倍数（可配置）或没有采集时间（即没有采集）
				if(curr<=period){
					value = mo.getPerfvalue();
					long numValue = value;
					long[] ary = new long[(value + "").length()];
					for (int i = ary.length - 1; i >= 0; i--) {
						ary[i] = value % 10;
						value /= 10;
					}
					if (numValue < 10) {
						mo.setOne(String.valueOf(0));
						mo.setTwo(String.valueOf(0));
						mo.setThree(String.valueOf(ary[0]));
					} else if (numValue < 100 && numValue >= 10) {
						mo.setOne(String.valueOf(0));
						mo.setTwo(String.valueOf(ary[0]));
						mo.setThree(String.valueOf(ary[1]));
					} else {
						mo.setOne(String.valueOf(ary[0]));
						mo.setTwo(String.valueOf(ary[1]));
						mo.setThree(String.valueOf(ary[2]));
					}
				}else{
					// 当前时间与采集时间差超过采集周期的倍数
					mo.setOne("");
					mo.setTwo("");
					mo.setThree("-2");
				}
			}else{
				// 没有采集时间（即没有采集）
				mo.setOne("");
				mo.setTwo("");
				mo.setThree("-2");
			}
		}
		map.put("mo", mo);
		return new ModelAndView("monitor/host/chartLoss");
	}
	/**
	 * 获取chart延时
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartResponse")
	@ResponseBody
	public ModelAndView findChartResponse(HttpServletRequest request,ModelMap map){
		logger.info("根据设备IP获取图表延时");
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		getmoClassID(moClass);
		params.put("KPIName", KPINameDef.DEVICERESPONSE);
		params.put("moClassID", moClassID);
		ModelAndView model = new ModelAndView("monitor/host/chartResponse");
		Double numPer = 0.00;
		try {
			MODevice mo = modMaper.getDeviceLoss(params);
			long value = 0;
			if (mo == null ||  mo.getPerfvalue() == -1) {
				mo = new MODevice();
				mo.setOne("");
				mo.setTwo("");
				mo.setThree("-2");
			} else {
				int period=0;
				Date collectTime;
				long curr=0;
				// 当前时间
				long currTime=System.currentTimeMillis();
				// 采集时间
				 collectTime=mo.getCollecttime();
				if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
					//采集周期的倍数
					period=mo.getDefDoIntervals()*getMultiple()*60000;
				}else{
					//采集周期的倍数
					period=mo.getDoIntervals()*getMultiple()*1000*60;
				}
				if (collectTime != null) {
					// 当前时间与采集时间差
					curr=currTime-mo.getCollecttime().getTime();
					//当前时间与采集时间差超过采集周期的倍数（可配置）或没有采集时间（即没有采集）
					if(curr<=period){
						value =  mo.getPerfvalue();
						Double valueD = Double.parseDouble(mo.getPerValue());
						long numValue = value;
						long[] ary = new long[(value + "").length()];
						for (int i = ary.length - 1; i >= 0; i--) {
							ary[i] = value % 10;
							value /= 10;
						}
						if(valueD > 0 && valueD < 1){
							String val = mo.getPerValue();
							int index = val.indexOf(".");
							mo.setOne(String.valueOf(0));
							mo.setTwo(val.substring(index+1,index+2));
							if(val.length() >= 4){
								mo.setThree(val.substring(index+2,index+3));
							}else{
								mo.setThree(String.valueOf(0));
							}
							numPer = valueD;
						}else if (numValue >= 1 && numValue < 10) {
							mo.setOne(String.valueOf(0));
							mo.setTwo(String.valueOf(0));
							mo.setThree(String.valueOf(ary[0]));
						} else if (numValue < 100 && numValue >= 10) {
							mo.setOne(String.valueOf(0));
							mo.setTwo(String.valueOf(ary[0]));
							mo.setThree(String.valueOf(ary[1]));
						} else if(numValue == 0){
							mo.setOne(String.valueOf(0));
							mo.setTwo(String.valueOf(0));
							mo.setThree(String.valueOf(0));
						}else {
							mo.setOne(String.valueOf(ary[0]));
							mo.setTwo(String.valueOf(ary[1]));
							mo.setThree(String.valueOf(ary[2]));
						}
					}else{
						// 当前时间与采集时间差超过采集周期的倍数
						mo.setOne("");
						mo.setTwo("");
						mo.setThree("-2");
					}
				}else{
					// 没有采集时间（即没有采集）
					mo.setOne("");
					mo.setTwo("");
					mo.setThree("-2");
				}
			}
			map.put("mo", mo);
			model.addObject("numPer", numPer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	/**
	 * 获取接口chart设备可用性使用率
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findHSWChartAvailability")
	@ResponseBody
	public Map findHSWChartAvailability(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备IP获取图表可用性使用率");
		Map params = new HashMap();
		String seltDate = request.getParameter("time");
		String seltDateValue = "";
		if ("24H".equals(seltDate)) {// 最近一天
			seltDateValue = "24小时";
		} else if ("7D".equals(seltDate)) {// 最近一周
			seltDateValue = "最近一周";
		} else if ("Today".equals(seltDate)) {// 今天
			seltDateValue = "今天";
		} else if ("Yes".equals(seltDate)) {// 昨天
			seltDateValue = "昨天";
		}else if ("Week".equals(seltDate)) {// 本周
			seltDateValue = "本周";
		}else if ("Month".equals(seltDate)) {// 本月
			seltDateValue = "本月";
		}else if ("LastMonth".equals(seltDate)) {// 上月
			seltDateValue = "上月";
		}
		String nameValue = seltDateValue + "可用性";
		
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		params.put("IfMOID", request.getParameter("IfMOID"));
		Map map = new HashMap();
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		try {
			MODevice mo = modMaper.getHSWChartAvailability(params);
			ArrayList<Object> array1 = new ArrayList<Object>();
			if (mo != null) {
				map1.put("value", mo.getDeviceavailability());
				map1.put("name", nameValue);
				array1.add(map1);
				map2.put("chartHSWAvailability", array1);
				map.put("data", map2);
				map.put("url", "");
			} else {
				map1.put("value", "");
				map1.put("name", nameValue);
				array1.add(map1);
				map2.put("chartHSWAvailability", array1);
				map.put("data", map2);
				map.put("url", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取chart设备内存使用率
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartMemory")
	@ResponseBody
	public Map findChartMemory(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		
		logger.info("根据设备IP获取图表内存使用率");
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		getParam(moClass);
		logger.debug("param 表名称[tableName]={} ", tableName);
		if("router".equals(moClass)||"switcher".equals(moClass) ||"switcherl2".equals(moClass)||"switcherl3".equals(moClass)){
			params.put("KPIName", KPINameDef.NETMEMUSAGE);
		}else {
		params.put("KPIName", KPINameDef.PHYMEMUSAGE);
		}
		params.put("tableName", tableName);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		getMemUrl();
		String StrUrl = "/monitor/interfaceChart/toHistoryChartsDetail?proUrl="
				+ memUrl + "&perfKind=1&MOID=" + request.getParameter("moID");
		Map map2 = new HashMap();
		try {
			MODevice mo = modMaper.getChartMemory(params);
			int period=0;
			Date collectTime;
			long curr=0;
			// 当前时间
			long currTime=System.currentTimeMillis();
			if (mo != null) {
				// 采集时间
				 collectTime=mo.getCollecttime();
				 logger.debug("设备{}内存采集时间为{}",mo.getDeviceip(),collectTime);
				if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
					//采集周期的倍数
					period=mo.getDefDoIntervals()*getMultiple()*60000;
				}else{
					//采集周期的倍数
					period=mo.getDoIntervals()*getMultiple()*1000;
				}
				logger.debug("设备{}内存采集周期倍数{}",mo.getDeviceip(),period);
				if (collectTime != null) {
					// 当前时间与采集时间差
					curr=currTime-mo.getCollecttime().getTime();
					logger.debug("设备{}内存当前时间与采集时间差{}",mo.getDeviceip(),curr);
					//当前时间与采集时间差超过采集周期的倍数（可配置）或没有采集时间（即没有采集）
					if(curr<=period){
						if(mo.getPerValue() != null){ //存在值为null的情况，此处抛出空指针异常
							map1.put("value",  format.format(Double.parseDouble(mo.getPerValue())));
						}
						else{
							map1.put("value",  "");
						}
						if(mo.getDevicestatus()!=null&&mo.getDevicestatus().equals("2")) {//down
							map1.put("value", "");
						}
						map1.put("name", "当前内存使用率");
						array1.add(map1);
						map2.put("chartMemory", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}else{
						map1.put("value", "");
						map1.put("name", "当前内存使用率");
						array1.add(map1);
						map2.put("chartMemory", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}
				}else{
					map1.put("value", "");
					map1.put("name", "当前内存使用率");
					array1.add(map1);
					map2.put("chartMemory", array1);
					map.put("data", map2);
					map.put("url", StrUrl);
				}
				
			} else {
				map1.put("value", "");
				map1.put("name", "当前内存使用率");
				array1.add(map1);
				map2.put("chartMemory", array1);
				map.put("data", map2);
				map.put("url", StrUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	/**
	 * 获取chart设备硬盘使用率
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartDisk")
	@ResponseBody
	public Map findChartDisk(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备IP获取图表硬盘使用率");
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		getParam(moClass);
		logger.debug("param 表名称[tableName]={} ", tableName);
		params.put("KPIName", KPINameDef.DISKRATE);
		params.put("tableName", tableName);
		Map map = new HashMap();
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		getDiskUrl();
		String StrUrl = "/monitor/interfaceChart/toHistoryChartsDetail?proUrl="
				+ diskUrl + "&perfKind=1&MOID=" + request.getParameter("moID");
		try {
			MODevice mo = modMaper.getChartDisk(params);
			if (mo != null) {
				map1.put("value",  mo.getPerfvalue() / 1000);
				map1.put("name", "硬盘I/O使用率");
				array1.add(map1);
				map2.put("chartDisk", array1);
				map.put("data", map2);
				map.put("url", StrUrl);
			} else {
				map1.put("value", "");
				map1.put("name", "硬盘I/O使用率");
				array1.add(map1);
				map2.put("chartDisk", array1);
				map.put("data", map2);
				map.put("url", StrUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return map;
	}

	/**
	 * 获取chart设备总带宽使用率 仪表盘
	 * 
	 * @param deviceIP
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartIfUsage")
	@ResponseBody
	public Map findChartIfUsage() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备IP获取图表总带宽使用率");
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", KPINameDef.IFUSAGE);
		params.put("IfMOID", request.getParameter("IfMOID"));
		Map map = new HashMap();
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		String ifUsageUrl = "/monitor/interfaceChart/mainInterfaceUsage";
		String StrUrl = "/monitor/interfaceChart/toHistoryChartsDetail?proUrl="
				+ ifUsageUrl + "&perfKind=3&MOID="
				+ request.getParameter("IfMOID");
		try {
			MODevice mo = modMaper.getChartSumIfUsage(params);
			if (mo != null) {
				map1.put("value", mo.getPerfvalue());
				map1.put("name", "总带宽使用率");
				array1.add(map1);
				map2.put("chartIfUsage", array1);
				map.put("data", map2);
				map.put("url", StrUrl);
			} else {
				map1.put("value", "");
				map1.put("name", "总带宽使用率");
				array1.add(map1);
				map2.put("chartIfUsage", array1);
				map.put("data", map2);
				map.put("url", StrUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	/**
	 * 获取chart设备接口带宽流入使用率
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartInUsage")
	@ResponseBody
	public Map findChartInUsage(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备IP获取图表带宽流入使用率");
		String moClass = request.getParameter("moClass");
		Map params = new HashMap();
		getmoClassID(moClass);
		params.put("moClassID", moClassID);
		params.put("KPIName", KPINameDef.INUSAGE);
		params.put("MOID", request.getParameter("moID"));
		params.put("IfMOID", request.getParameter("IfMOID"));
		Map map = new HashMap();
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		String inUsageUrl = "/monitor/interfaceChart/mainInterfaceUsage";
		String StrUrl = "/monitor/interfaceChart/toHistoryChartsDetail?proUrl="
				+ inUsageUrl + "&perfKind=1&MOID="
				+ request.getParameter("IfMOID");
		try {
			MODevice mo = modMaper.getChartIfUsage(params);
			int period=0;
			Date collectTime;
			long curr=0;
			// 当前时间
			long currTime=orclService.getCurrentDate().getTime();
			if (mo != null) {
				// 采集时间
				 collectTime=mo.getCollecttime();
				 logger.debug("设备{}接口带宽流入采集时间{}",mo.getDeviceip(),collectTime);
				if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
					//采集周期的倍数
					period=mo.getDefDoIntervals()*getMultiple()*60000;
				}else{
					//采集周期的倍数
					period=mo.getDoIntervals()*getMultiple()*1000;
				}
				logger.debug("设备{}接口采集周期的倍数{}",mo.getDeviceip(),period);
				if (collectTime != null) {
					// 当前时间与采集时间差
					curr=currTime-mo.getCollecttime().getTime();
					logger.debug("设备{}接口当前时间与采集时间差{}",mo.getDeviceip(),curr);
					//当前时间与采集时间差超过采集周期的倍数（可配置）或没有采集时间（即没有采集）
					if(curr<=period){
						//map1.put("value", format.format(Double.parseDouble(mo.getPerValue())));
						if(mo.getPerValue() != null){ //存在值为null的情况，此处抛出空指针异常
							map1.put("value",  format.format(Double.parseDouble(mo.getPerValue())));
						}
						else{
							map1.put("value",  "");
						}
						map1.put("name", "流入使用率");
						array1.add(map1);
						map2.put("chartInUsage", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}else{
						map1.put("value", "");
						map1.put("name", "流入使用率");
						array1.add(map1);
						map2.put("chartInUsage", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}
				}else{
					map1.put("value", "");
					map1.put("name", "流入使用率");
					array1.add(map1);
					map2.put("chartInUsage", array1);
					map.put("data", map2);
					map.put("url", StrUrl);
				}
			} else {
				map1.put("value", "");
				map1.put("name", "流入使用率");
				array1.add(map1);
				map2.put("chartInUsage", array1);
				map.put("data", map2);
				map.put("url", StrUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return map;
	}

	/**
	 * 获取chart设备接口带宽流出使用率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartOutUsage")
	@ResponseBody
	public Map findChartOutUsage(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备IP获取图表带宽流出使用率");
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		getmoClassID(moClass);
		params.put("moClassID", moClassID);
		params.put("KPIName", KPINameDef.OUTUSAGE);
		params.put("MOID", request.getParameter("moID"));
		params.put("IfMOID", request.getParameter("IfMOID"));
		Map map = new HashMap();
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		String outUsageUrl = "/monitor/interfaceChart/mainInterfaceUsage";
		String StrUrl = "/monitor/interfaceChart/toHistoryChartsDetail?proUrl="
				+ outUsageUrl + "&perfKind=2&MOID="
				+ request.getParameter("IfMOID");
		try {
			MODevice mo = modMaper.getChartIfUsage(params);
			int period=0;
			Date collectTime;
			long curr=0;
			// 当前时间
			long currTime=orclService.getCurrentDate().getTime();
			if (mo != null) {
				// 采集时间
				 collectTime=mo.getCollecttime();
				 logger.debug("设备{}接口带宽流出采集时间{}",mo.getDeviceip(),collectTime);
				if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
					//采集周期的倍数
					period=mo.getDefDoIntervals()*getMultiple()*60000;
				}else{
					//采集周期的倍数
					period=mo.getDoIntervals()*getMultiple()*1000;
				}
				 logger.debug("设备{}接口采集周期的倍数{}",mo.getDeviceip(),period);
				if (collectTime != null) {
					// 当前时间与采集时间差
					curr=currTime-mo.getCollecttime().getTime();
					logger.debug("设备{}接口当前时间与采集时间差{}",mo.getDeviceip(),curr);
					//当前时间与采集时间差超过采集周期的倍数（可配置）或没有采集时间（即没有采集）
					if(curr<=period){
						//map1.put("value", format.format(Double.parseDouble(mo.getPerValue())));
						if(mo.getPerValue() != null){ //存在值为null的情况，此处抛出空指针异常
							map1.put("value",  format.format(Double.parseDouble(mo.getPerValue())));
						}
						else{
							map1.put("value",  "");
						}
						map1.put("name", "流出使用率");
						array1.add(map1);
						map2.put("chartOutUsage", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}else{
						// 当前时间与采集时间差超过采集周期的倍数
						map1.put("value", "");
						map1.put("name", "流出使用率");
						array1.add(map1);
						map2.put("chartOutUsage", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}
				}else{
					// 没有采集时间（即没有采集）
					map1.put("value", "");
					map1.put("name", "流出使用率");
					array1.add(map1);
					map2.put("chartOutUsage", array1);
					map.put("data", map2);
					map.put("url", StrUrl);
				}
			} else {
				map1.put("value", "");
				map1.put("name", "流出使用率");
				array1.add(map1);
				map2.put("chartOutUsage", array1);
				map.put("data", map2);
				map.put("url", StrUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	/**
	 * 获取Bar 数据存储 柱状图
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartDatastore")
	@ResponseBody
	public Map findBarChartDatastore(){
		
		logger.info("开始...加载数据存储页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		getParam(moClass);
		logger.debug("param 表名称[tableName]={} ", tableName);
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", KPINameDef.DATASTOREUSAGE);
		params.put("tableName", tableName);
		Map map = new HashMap();
		Map map1 = new HashMap();
		getStoreUrl();
		String StrUrl = "/monitor/interfaceChart/toHistoryChartsDetail?proUrl="
				+ storeUrl + "&perfKind=1&MOID=" + request.getParameter("moID");
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<MODevice> moLsinfo = modMaper.getBarChartDatastore(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getMoname());
					array1.add(moLsinfo.get(i).getPerfvalue());
				}
				map.put("category", array);
				map1.put("barDatastore", array1);
				map.put("url", StrUrl);
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("barDatastore", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	/**
	 * 获取Bar 硬盘 柱状图
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartDisk")
	@ResponseBody
	public Map findBarChartDisk(){
		logger.info("开始...加载硬盘页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		getParam(moClass);
		logger.debug("param 表名称[tableName]={} ", tableName);
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", KPINameDef.DISKUSAGE);
		params.put("tableName", tableName);
		Map map = new HashMap();
		Map map1 = new HashMap();
		getDiskUrl();
		String StrUrl = "/monitor/interfaceChart/toHistoryChartsDetail?proUrl="
				+ diskUrl + "&perfKind=1&MOID=" + request.getParameter("moID");
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<MODevice> moLsinfo = modMaper.getBarChartDisk(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++)
				{
					String rawdescr=moLsinfo.get(i).getRawdescr();
					if (StringUtils.isNotEmpty(rawdescr))
					{
						if ("/".equals(rawdescr.subSequence(0, 1)))
						{
							array.add(moLsinfo.get(i).getRawdescr());
						} else if (rawdescr.length()>=2&&":".equals(rawdescr.subSequence(1, 2)))
						{
							array.add(moLsinfo.get(i).getRawdescr().substring(0, 2));
						} else if (rawdescr.length()>=10&&"_".equals(rawdescr.subSequence(9, 10)))
						{
							array.add(moLsinfo.get(i).getRawdescr().substring(0, 10));
						} else
						{
							array.add(moLsinfo.get(i).getRawdescr());
						}
					}
					array1.add(moLsinfo.get(i).getPerfvalue());
				}
				map.put("category", array);
				map1.put("barDisk", array1);
				map.put("url", StrUrl);
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("barDisk", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 获取Bar 带宽 柱状图
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartIfUsage")
	@ResponseBody
	public Map findBarChartIfUsage() {
		logger.info("开始...加载带宽页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		Map map = new HashMap();
		Map map1 = new HashMap();
		moClass = request.getParameter("moClass");
		getmoClassID(moClass);
		params.put("moClassID", moClassID);
		params.put("KPIName", KPINameDef.IFUSAGE);
		String ifUsageUrl = "/monitor/historyManage/initInterfaceChartData";
		String StrUrl = "/monitor/interfaceChart/toHistoryChartsDetail?proUrl="
				+ ifUsageUrl + "&perfKind=12&MOID="
				+ request.getParameter("moID");
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		//DecimalFormat df = new DecimalFormat("#.##");
		try {
			List<MODevice> moLsinfo = modMaper.getBarChartIfUsage(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					// 判断接口表中接口名称是否为空，若为空则取moName为接口名称
					if(moLsinfo.get(i).getIfname()==null ||  moLsinfo.get(i).getIfname().equals(""))
					{
						array.add(moLsinfo.get(i).getIfMoName());
					}else
					{
						array.add(moLsinfo.get(i).getIfname());
					}
					array1.add(moLsinfo.get(i).getViewValue());
				}
				map.put("category", array);
				map1.put("barIfUsage", array1);
				map.put("url", StrUrl);
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("barIfUsage", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return map;
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
