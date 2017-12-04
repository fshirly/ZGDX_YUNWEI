package com.fable.insightview.monitor.middelware.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJVMBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcDSBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareMemoryBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareThreadPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MiddleWareKPINameDef;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatClassLoadBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatJVMHeapBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatMemoryPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatThreadPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.TomcatOperSituationBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.TomcatWebModuleBean;
import com.fable.insightview.monitor.middleware.tomcat.service.ITomcatService;
import com.fable.insightview.platform.common.util.JsonUtil;

@Controller
@RequestMapping("/monitor/tomcatManage")
public class TomcatController {
	@Autowired
	ITomcatService tomService;
	
	@Autowired
	IOracleService orclService;

	private final Logger logger = LoggerFactory
			.getLogger(TomcatController.class);

	/**
	 * 数据库服务信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toShowServerInfo")
	@ResponseBody
	public ModelAndView toShowServerInfo(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载数据库服务详情页面");
		int MOID = Integer.parseInt(request.getParameter("moID"));
		Map params = new HashMap();
		params.put("MOID", MOID);
		params.put("MID", KPINameDef.JOBURLPOLL);
		MOMiddleWareJMXBean tm = tomService.getTmInfo(params);
		map.put("tm", tm);
		return new ModelAndView("monitor/middleware/tomcat/tmInfo");
	}

	/**
	 * 跳转Tomcat可用率
	 */
	@RequestMapping("/toShowTmAvailability")
	public ModelAndView toShowTmAvailability(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/tomcat/tmAvailabilityChart");
	}

	/**
	 * 获取Tomcat可用率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findTmChartAvailability")
	@ResponseBody
	public Map<String, Object> findTmChartAvailability(
			HttpServletRequest request) throws Exception {
		logger.info("根据Tomcat实例ID获取图表可用性使用率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", Integer.parseInt(request.getParameter("moID")));
		MOMiddleWareJMXBean mo = tomService.getTmChartAvailability(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (mo != null) {
			map1.put("value", mo.getDeviceAvailability());
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("middleAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("middleAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
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
		String time = request.getParameter("time"); 
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		String timeEnd = f.format(d.getTime());
		String timeBegin = "";
		if(time != null){
			if ("24H".equals(time)) {// 最近一天
				d.add(Calendar.DATE, -1);
				timeBegin = f.format(d.getTime());
			} else if ("7D".equals(time)) {// 最近一周
				d.add(Calendar.DATE, -7);
				timeBegin = f.format(d.getTime());
			} else if ("Today".equals(time)) {// 今天
				d.add(Calendar.DATE, 0);
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
			} else if ("Yes".equals(time)) {// 昨天
				d.add(Calendar.DAY_OF_MONTH, -1);
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
				d.set(Calendar.HOUR_OF_DAY, 23);
				d.set(Calendar.MINUTE, 59);
				d.set(Calendar.SECOND, 59);
				timeEnd = f.format(d.getTime());
			}else if ("Week".equals(time)) {// 本周
				if(d.get(Calendar.DAY_OF_WEEK)==1){
					d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK)+5));
				}else{
					d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK)-2));
				}
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
			}else if ("Month".equals(time)) {// 本月
				d.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				d.set(Calendar.DAY_OF_MONTH,1);
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
			}else if ("LastMonth".equals(time)) {// 上月
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

			}
		}
		System.out.println("timeBegin=" + timeBegin + "\ntimeEnd=" + timeEnd);
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		return params;
	}

	/**
	 * 内存堆趋势图表页面跳转
	 * @return
	 */
	@RequestMapping("/toTmMemoryUsedLineChart")
	public ModelAndView toTmMemoryUsedLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/tomcatManage/initTmMemoryUsed");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}

	/**
	 * * 内存堆使用率趋势图表数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initTmMemoryUsed")
	@ResponseBody
	public Map<String, Object> initTmMemoryUsed(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载内存堆使用率趋势图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("seltItem", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// Tomcat 统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("内存堆使用率趋势图(%)");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfTomcatJVMHeapBean> perfList = tomService
				.queryTmMemoryUsedPerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if (perf.equals("1")) {
					seriesSbf.append(perfList.get(i).getHeapUsage() + ",");
				}
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String oracleChartData = JsonUtil.toString(jsonData);
		logger.info("oracleChartData==" + oracleChartData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("oracleChartData", oracleChartData);
		logger.info("结束...加载内存堆使用率趋势图表数据");
		
		return result;
	}

	/**
	 * 内存池趋势图表页面跳转
	 * @return
	 */
	@RequestMapping("/toTmMemoryPoolLineChart")
	public ModelAndView toTmMemoryPoolLineChart(HttpServletRequest request,	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/tomcatManage/initTmMemoryPool");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}

	/**
	 *  内存池趋势图表数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initTmMemoryPool")
	@ResponseBody
	public Map<String, Object> initTmMemoryPool(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载内存池使用率趋势图表数据");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		// 存放返回页面json数据对象
		ChartColItem jsonData = new ChartColItem();
		try {
			// 查询线程个数
			List<MOMiddleWareMemoryBean> cpuDeviceList = tomService
					.queryMemNameByMoID(params);
			// 默认取最近24小时数据
			params = queryBetweenTime(request, params);
			String perf = request.getParameter("perfKind");
			if (perf.equals("1")) {
				jsonData.setText("");// 内存池使用率趋势
			}
			String legend = "";
			StringBuffer xAxisSbf = new StringBuffer("");
			StringBuffer seriesSbf = new StringBuffer("[");
			int flag = 0;// 标志是否作为x轴数据,默认否
			for (int i = 0; i < cpuDeviceList.size(); i++) {
				params.put("seltItem", cpuDeviceList.get(i).getMoId());
				List<PerfTomcatMemoryPoolBean> cpuDeviceDetailList = tomService
						.queryTmMemoryPoolPerf(params);
				int size = cpuDeviceDetailList.size();
				if (cpuDeviceDetailList != null && size > 0) {
					legend += cpuDeviceList.get(i).getMemName() + ",";
					seriesSbf.append("{name:'" + cpuDeviceList.get(i).getMemName()
							+ "',type:'line',data:[");
					flag++;
				}
				
				for (int j = 0; j < size; j++) {
					if (size > 0 && flag == 1) {// x轴只取一个有数据的
						xAxisSbf.append(cpuDeviceDetailList.get(j).getFormatTime()+ ",");
					}
					if (perf.equals("1")) {
						seriesSbf.append(cpuDeviceDetailList.get(j).getMemoryUsage()+ ",");
					}
				}
				
				if (size > 0) {
					seriesSbf.deleteCharAt(seriesSbf.length() - 1);
					seriesSbf.append("]},");
				}
			}
			
			if (cpuDeviceList != null && cpuDeviceList.size() > 0
					&& xAxisSbf.length() > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]");
			jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend
					.length() - 1) : legend);
			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
			if (jsonData.getSeriesData().indexOf("name:") == -1) {
				jsonData.setSeriesData("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		String oracleChartData = JsonUtil.toString(jsonData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("oracleChartData", oracleChartData);
		logger.info("结束...加载内存池使用率趋势图表数据");
		
		return result;
	}

	/**
	 * 数据源JDBC信息
	 * @param request
	 * @param map
	 * @return
	 */

	@RequestMapping("/toShowJdbcDSInfo")
	@ResponseBody
	public ModelAndView toShowJdbcDSInfo(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/tomcat/tmJdbcDSInfoList");
	}

	/**
	 * 数据源JDBC信息列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getTmJdbcDSInfo")
	@ResponseBody
	public Map<String, Object> getTmJdbcDSInfo() throws Exception {
		logger.info("开始...加载数据源JDBC信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		params.put("tabName", "MOMiddleWareJdbcDS");
		List<MOMiddleWareJdbcDSBean> moLsinfo = new ArrayList<MOMiddleWareJdbcDSBean>();
		try {
			List<MOMiddleWareJdbcDSBean> moLsCount = tomService.getJdbcDSCount(params);
		    if (moLsCount.size() > 0) {
					for (int i = 0; i < moLsCount.size(); i++) {
						params.put("MOID", moLsCount.get(i).getMoId());
						List<MOMiddleWareJdbcDSBean> moLsinfo1 = tomService
								.getJdbcDSValue(params);
						if (moLsinfo1 != null && moLsinfo1.size() > 0) {
							MOMiddleWareJdbcDSBean mo = getJdbcObj(moLsinfo1);
							moLsinfo.add(mo);
						}
					}
		    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		result.put("rows", moLsinfo);
		logger.info("结束...加载数据源JDBC信息列表 " + moLsinfo);
		return result;
	}

	public MOMiddleWareJdbcDSBean getJdbcObj(List<MOMiddleWareJdbcDSBean> list) {
		MOMiddleWareJdbcDSBean mo = new MOMiddleWareJdbcDSBean();
		for (int i = 0; i < list.size(); i++) {
			mo.setdSName(list.get(i).getdSName());
			mo.setJdbcDriver(list.get(i).getJdbcDriver());
			mo.setStatus(list.get(i).getStatus());
			mo.setdBUrl(list.get(i).getdBUrl());
			mo.setUserName(list.get(i).getUserName());
			mo.setPassWord(list.get(i).getPassWord());
			mo.setMaxActive(list.get(i).getMaxActive());
			mo.setMaxIdle(list.get(i).getMaxIdle());
			mo.setMinIdle(list.get(i).getMinIdle());
			if(list.get(i).getMaxWait()!=null &&  !"".equals(list.get(i).getMaxWait())){
				mo.setMaxWait(HostComm.getMsToTime(Long.parseLong(list.get(i).getMaxWait())));
			}else{
				mo.setMaxWait("0毫秒");
			}
		
			mo.setInitialSize(list.get(i).getInitialSize());
		}
		return mo;
	}
	/**
	 * 线程池信息
	 */
	@RequestMapping("/toShowThreadPoolInfo")
	@ResponseBody
	public ModelAndView toShowThreadPoolInfo(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/tomcat/tmThreadPoolInfoList");
	}

	/**
	 * 线程池信息列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getTmThreadPoolInfo")
	@ResponseBody
	public Map<String, Object> getTmThreadPoolInfo() throws Exception {
		logger.info("开始...加载线程池信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		params.put("tabName", "MOMiddleWareThreadPool");
		List<MOMiddleWareThreadPoolBean> moLsinfo = new ArrayList<MOMiddleWareThreadPoolBean>();
		try {
			List<MOMiddleWareJdbcDSBean> moLsCount = tomService.getJdbcDSCount(params);
			
				if (moLsCount.size() > 0) {
					for (int i = 0; i < moLsCount.size(); i++) {
						params.put("MOID", moLsCount.get(i).getMoId());
						List<MOMiddleWareThreadPoolBean> moLsinfo1 = tomService
								.getThreadPoolValue(params);
						if (moLsinfo1 != null && moLsinfo1.size() > 0) {
							MOMiddleWareThreadPoolBean mo = getThreadPoolObj(moLsinfo1);
							moLsinfo.add(mo);
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载线程池信息列表 " + moLsinfo);
		return result;
	}

	public MOMiddleWareThreadPoolBean getThreadPoolObj(
			List<MOMiddleWareThreadPoolBean> list) {
		MOMiddleWareThreadPoolBean mo = new MOMiddleWareThreadPoolBean();
		String name;
		long value;
		for (int i = 0; i < list.size(); i++) {
			name = list.get(i).getKpiName();
			value = list.get(i).getPerfValue();
			if (name != null && !"".equals(name)) {
				if (name.equals(MiddleWareKPINameDef.CURRTHREADS)) {
					mo.setCurrThreads(value);
				} else if (name.equals(MiddleWareKPINameDef.BUSYTHREADS)) {
					mo.setBusyThreads(value);
				} else if (name.equals(MiddleWareKPINameDef.MAXTHREADS)) {
					mo.setMaxConns(value);
				}
			}
			mo.setThreadName(list.get(i).getThreadName());
			mo.setMinConns(list.get(i).getMinConns());
			mo.setInactivityTimeOut(list.get(i).getInactivityTimeOut());
			mo.setGrowable(list.get(i).getGrowable());
			mo.setInitCount(list.get(i).getInitCount());
			mo.setMaxSpareSize(list.get(i).getMaxSpareSize());
		}
		return mo;
	}

	/**
	 * 内存池信息
	 */

	@RequestMapping("/toShowMemoryPoolInfo")
	@ResponseBody
	public ModelAndView toShowMemoryPoolInfo(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView(
				"monitor/middleware/tomcat/tmMemoryPoolInfoList");
	}

	/**
	 * 内存池信息列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getTmMemoryPoolInfo")
	@ResponseBody
	public Map<String, Object> getTmMemoryPoolInfo(){
		
		logger.info("开始...加载内存池信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		params.put("tabName", " MOMiddleWareMemory");
		List<MOMiddleWareMemoryBean> moLsinfo = new ArrayList<MOMiddleWareMemoryBean>();
		try {
			List<MOMiddleWareJdbcDSBean> moLsCount = tomService.getJdbcDSCount(params);
			if (moLsCount.size() > 0) {
				for (int i = 0; i < moLsCount.size(); i++) {
					params.put("MOID", moLsCount.get(i).getMoId());
					List<MOMiddleWareMemoryBean> moLsinfo1 = tomService
							.getMemoryPoolValue(params);
					if (moLsinfo1 != null && moLsinfo1.size() > 0) {
						MOMiddleWareMemoryBean mo = getMemoryPoolObj(moLsinfo1);
						moLsinfo.add(mo);
					}
				}
			}
			Collections.sort(moLsinfo, new Comparator<MOMiddleWareMemoryBean>() {  
		           public int compare(MOMiddleWareMemoryBean arg0, MOMiddleWareMemoryBean arg1) {  
		               double hits0 = arg0.getMemoryUsage();
		               double hits1 = arg1.getMemoryUsage();  
		               if (hits1 > hits0) {  
		                    return 1;  
		                } else if (hits1 == hits0) {  
		                    return 0;  
		               } else {  
		                   return -1;  
		               }  
		            }  
		        }); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载内存池信息列表 " + moLsinfo);
		return result;
	}

	public MOMiddleWareMemoryBean getMemoryPoolObj(
			List<MOMiddleWareMemoryBean> list) {
		MOMiddleWareMemoryBean mo = new MOMiddleWareMemoryBean();
		String name;
		long value;
		for (int i = 0; i < list.size(); i++) {
			name = list.get(i).getKpiName();
			value = list.get(i).getPerfValue();
			if (name.equals(MiddleWareKPINameDef.MEMORYFREE)) {
				mo.setMemoryFree(HostComm.getBytesToSize(value));
			} else if (name.equals(MiddleWareKPINameDef.MEMORYUSAGE)) {
				mo.setMemoryUsage(value);
			}
			mo.setMemName(list.get(i).getMemName());
			mo.setMemType(list.get(i).getMemType());
			mo.setmGRName(list.get(i).getmGRName());
			mo.setInitSize(HostComm.getBytesToSize(Long.parseLong(list.get(i).getInitSize())));
			mo.setMaxSize(HostComm.getBytesToSize(Long.parseLong(list.get(i).getMaxSize())));
		}
		return mo;
	}

	/**
	 * 获取Bar 内存池柱状图
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartMemoryPool")
	@ResponseBody
	public Map<String, Object> findBarChartMemoryPool() {
		logger.info("开始...加载数据存储页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", MiddleWareKPINameDef.MEMORYUSAGE);
		Map map = new HashMap();
		Map Datamap = new HashMap();
		try {
			 List<MOMiddleWareMemoryBean> moLsinfo = tomService.getBarChartMemory(params);
			ArrayList<Object> arrayName = new ArrayList<Object>();
			ArrayList<Object> arrayValue = new ArrayList<Object>();
				if (moLsinfo.size() > 0) {
					for (int i = 0; i < moLsinfo.size(); i++) {
						arrayName.add(moLsinfo.get(i).getMemName());
						arrayValue.add(moLsinfo.get(i).getPerfValue());
					}
					map.put("category", arrayName);
					Datamap.put("barMemoryPool", arrayValue);
					map.put("url", "");
					map.put("data", Datamap);
				} else {
					arrayName.add("");
					map.put("category", arrayName);
					Datamap.put("barMemoryPool", arrayValue);
					map.put("url", "");
					map.put("data", Datamap);
				} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 跳转至Web模块详情一览列表页
	 */
	@RequestMapping("/toShowTmInstanceInfo")
	public ModelAndView toShowTmInstanceInfo(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/tomcat/tmInstanceInfoList");
	}

	/**
	 * 加载Web模块详情一览列表页面数据
	 * @param taskBean
	 * @throws Exception
	 */
	@RequestMapping("/getTmInstanceInfo")
	@ResponseBody
	public Map<String, Object> getTmInstanceInfo() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		List<TomcatWebModuleBean> dbLst = tomService.getTmInstanceList(Integer
				.parseInt(request.getParameter("moID")));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", dbLst);
		return result;
	}

	/**
	 * 跳转至tomcat运行现状模块
	 */
	@RequestMapping("/toTomcatOperSituation")
	public ModelAndView toTomcatOperSituation(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/tomcat/tomcatOperSituation");
	}

	/**
	 * 加载tomcat运行现状模块数据
	 */
	@RequestMapping("/getTomcatOperSituationInfos")
	@ResponseBody
	public Map<String, Object> getTomcatOperSituationInfos() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> params = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("#.##");
		params.put("moId", request.getParameter("moID"));
		params.put("KPIserviceAvailability",MiddleWareKPINameDef.SERVICEAVAILABILITY);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<TomcatOperSituationBean> tmOperSituInfos = tomService.getTomcatOperSituationInfos(params);
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar d = Calendar.getInstance();
			String timeEnd = f.format(d.getTime());
			String timeBegin = "";
			d.add(Calendar.MONTH, -1);
			timeBegin = f.format(d.getTime());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("moId", Integer.parseInt(request.getParameter("moID")));
			map.put("timeBegin", timeBegin);
			map.put("timeEnd", timeEnd);
			
			List<TomcatOperSituationBean> statusAndResTimeInfos = tomService
					.getDeviceStatusAndResponseTime(map);
			double statusValues1 = 0;
			double statusValues2 = 0;
			double responseTimes = 0.0;
			for (int i = 0; i < statusAndResTimeInfos.size(); i++) {
				if (Integer
						.parseInt(statusAndResTimeInfos.get(i).getDeviceStatus()) == 1) {
					statusValues1 ++ ;
				} else {
					statusValues2 ++;
				}
				responseTimes += Double.parseDouble(statusAndResTimeInfos.get(i)
						.getResponseTime());
		
			}
			
			if (tmOperSituInfos.size() > 0) {
				String avgAvailability =df.format((double) (Math
						.round((statusValues1 / (statusValues1 + statusValues2)) * 100) / 100.0)
						* 100) + "%";
				tmOperSituInfos.get(0).setAvgAvailability(avgAvailability);
				String avgResponseTime = (double) (Math
						.round((responseTimes / statusAndResTimeInfos.size()) * 100) / 100.0)
						+ "";
					if ("0.0".equals(avgResponseTime) || "".equals(avgResponseTime)) {
						tmOperSituInfos.get(0).setAvgResponseTime("0毫秒");
					}else if (avgResponseTime.contains("-") == true ) {
						tmOperSituInfos.get(0).setAvgResponseTime("N/A");
					} else {
						tmOperSituInfos.get(0).setAvgResponseTime(getMsToTime(Double.parseDouble((avgResponseTime))));
					}
					if (null != tmOperSituInfos.get(0).getUpTime() &&  !"".equals(tmOperSituInfos.get(0).getUpTime())) {
						tmOperSituInfos.get(0).setUpTime(HostComm.getMsToTime(Long.parseLong(tmOperSituInfos
										.get(0).getUpTime())));
					} else {
						tmOperSituInfos.get(0).setUpTime("");
					}
			}
			result.put("rows", tmOperSituInfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return result;
	}

	/**
	 * 跳转至tomcatClassLoader模块
	 * @return
	 */
	@RequestMapping("/toTomcatClassLoader")
	public ModelAndView toTomcatClassLoader(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/tomcat/tomcatClassLoaderInfos");
	}

	/**
	 * 加载tomcatClassLoader模块数据
	 */
	@RequestMapping("/getTomcatClassLoaderInfos")
	@ResponseBody
	public Map<String, Object> getTomcatClassLoaderInfos() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("moId", request.getParameter("moID"));
		params.put("KPIclassunLoaded", MiddleWareKPINameDef.CLASSUNLOADED);
		params.put("KPIclassTotalLoaded", MiddleWareKPINameDef.CLASSTOTALLOADED);
		params.put("KPIclassLoaded", MiddleWareKPINameDef.CLASSLOADED);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<PerfTomcatClassLoadBean> tmOperSituInfos = tomService.getTomcatClassLoaderInfos(params);
			result.put("rows", tmOperSituInfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * 跳转至JVM heap模块
	 */
	@RequestMapping("/toJvmHeap")
	public ModelAndView toJvmHeap(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/tomcat/jvmHeapInfos");
	}

	/**
	 * 加载tomcatClassLoader模块数据
	 */
	@RequestMapping("/getJvmHeapInfos")
	@ResponseBody
	public Map<String, Object> getJvmHeapInfos(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> params = new HashMap<String, Object>();
		MOMiddleWareJVMBean jvmBean = new MOMiddleWareJVMBean();
		List<MOMiddleWareJVMBean> jvmHeapBeans = new ArrayList<MOMiddleWareJVMBean>();
		params.put("moId", request.getParameter("moID"));
		params.put("KPIheapMax", MiddleWareKPINameDef.HEAPMAX);
		params.put("KPIheapSize", MiddleWareKPINameDef.HEAPSIZE);
		params.put("KPIheapFree", MiddleWareKPINameDef.HEAPFREE);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<PerfTomcatJVMHeapBean> jvmHeapInfos = tomService.getJvmHeapInfos(params);
			jvmBean.setMoId(Integer.parseInt(request.getParameter("moID")));
			if (jvmHeapInfos.size() > 0) {
				String heapSizeValue=HostComm.getBytesToSize(jvmHeapInfos.get(0).getHeapSize());
				jvmBean.setHeapSizeValue(heapSizeValue);
				String heapMaxValue=HostComm.getBytesToSize(jvmHeapInfos.get(0).getHeapMax());
				jvmBean.setHeapMaxValue(heapMaxValue);
				String heapFreeValue=HostComm.getBytesToSize(jvmHeapInfos.get(0).getHeapFree());
				jvmBean.setHeapFreeValue(heapFreeValue);
			} else {
				jvmBean.setHeapSizeValue("0KB");
				jvmBean.setHeapMaxValue("0KB");
				jvmBean.setHeapFreeValue("0KB");
			}
			jvmHeapBeans.add(jvmBean);
			result.put("rows", jvmHeapBeans);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * 线程池趋势图表页面跳转
	 */
	@RequestMapping("/toTmThreadLineChart")
	public ModelAndView toTmThreadLineChart(HttpServletRequest request,

	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/tomcatManage/initTmThread");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}

	/**
	 * 线程池图表数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initTmThread")
	@ResponseBody
	public Map<String, Object> initTmThread(HttpServletRequest request){
		
		logger.info("开始...加载线程数据");
		Map params = new HashMap();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询线程个数
			List<MOMiddleWareThreadPoolBean> cpuDeviceList = tomService.queryNumsByMoID(params);
			// 存放返回页面json数据对象
			ChartColItem jsonData = new ChartColItem();
			String perf = request.getParameter("perfKind");
			if (perf.equals("1")) {
				jsonData.setText("");// 当前线程数
			} else if (perf.equals("2")) {
				jsonData.setText("");// 繁忙线程数
			}
			String legend = "";
			StringBuffer xAxisSbf = new StringBuffer("");
			StringBuffer seriesSbf = new StringBuffer("[");
			int flag = 0;// 标志是否作为x轴数据,默认否
			for (int i = 0; i < cpuDeviceList.size(); i++) {
				params.put("MOID", cpuDeviceList.get(i).getMoId());
				List<PerfTomcatThreadPoolBean> cpuDeviceDetailList = tomService
						.getPerfThreadPoolList(params);
				int size = cpuDeviceDetailList.size();
				if (cpuDeviceDetailList != null && size > 0) {
					legend += cpuDeviceList.get(i).getThreadName() + ",";
					seriesSbf.append("{name:'"+ cpuDeviceList.get(i).getThreadName()+ "',type:'line',data:[");
					flag++;
				}
				
				for (int j = 0; j < size; j++) {
					if (size > 0 && flag == 1) {// x轴只取一个有数据的
						xAxisSbf.append(cpuDeviceDetailList.get(j).getFormatTime()+ ",");
					}
					if (perf.equals("1")) {
						seriesSbf.append(cpuDeviceDetailList.get(j).getCurrThreads()+ ",");
					} else if (perf.equals("2")) {
						seriesSbf.append(cpuDeviceDetailList.get(j).getBusyThreads()+ ",");
					}
				}
				
				if (size > 0) {
					seriesSbf.deleteCharAt(seriesSbf.length() - 1);
					seriesSbf.append("]},");
				}
			}
			if (cpuDeviceList != null && cpuDeviceList.size() > 0
					&& xAxisSbf.length() > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]");
			jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend
					.length() - 1) : legend);
			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
			if (jsonData.getSeriesData().indexOf("name:") == -1) {
				jsonData.setSeriesData("");
			}
			String oracleChartData = JsonUtil.toString(jsonData);
			result.put("oracleChartData", oracleChartData);
			logger.info("当前线程>>" + oracleChartData);
			logger.info("结束...加载当前线程数据");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	/**
	 * 跳转至告警列表页
	 * @return
	 */
	@RequestMapping("/toShowMiddleWareAlarmInfo")
	public ModelAndView toShowMiddleWareAlarmInfo(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/tomcat/middleWareAlarmInfo");
	}

	/**
	 * 中间件for 最近告警
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getMiddleWareAlarmInfo")
	@ResponseBody
	public Map<String, Object> getMiddleWareAlarmInfo() throws Exception {
		logger.info("开始...加载中间件for 最近告警");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", Integer.parseInt(request.getParameter("moID")));
		int alarmNum = Integer.parseInt(request.getParameter("alarmNum"));
		try {
			List<AlarmActiveDetail> moLsinfo = orclService
					.getTbsAlarmInfo(params);
			if (moLsinfo.size() >= alarmNum) {
				moLsinfo = moLsinfo.subList(0, alarmNum);
			}
			result.put("rows", moLsinfo);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage());
		}

		logger.info("结束...加载中间件信息列表 for 最近告警");
		return result;
	}
	/*
	 * 入库为 ms
	 * 1、 若小于1s，用毫秒显示。 2、 若大于等于1s、小于1min，用秒显示。 3、 若大于等于1min、小于1h，用分钟显示。 4、
	 * 若大于等于1h、小于1d，用小时显示。 5、 若大于等于1d，用天显示。 1s=1000 1min=60000 1h=3600000
	 * 1d=86400000
	 */
	public static String getMsToTime(double num) {
		DecimalFormat df = new DecimalFormat("#");
		String valueByNum = "";
		try {
			if (num < 1000) {
				valueByNum = df.format(num) + "毫秒";
			} else if (num >= 1000 && num < 60000) {
				valueByNum = df.format(num / 1000) + "秒";
			} else if (num >= 60000 && num < 3600000) {
				valueByNum = df.format(num / 60000) + "分钟";
			} else if (num >= 3600000 && num < 86400000) {
				valueByNum = df.format(num / 3600000) + "小时";
			} else if (num >= 86400000) {
				valueByNum = df.format(num / 86400000) + "天";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueByNum;
	}
}
