package com.fable.insightview.monitor.middelware.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareThreadPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MiddleWareKPINameDef;
import com.fable.insightview.monitor.middleware.tomcat.service.ITomcatService;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMidWareJdbcDsBean;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMidWareJdbcPoolBean;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMiddleWareJ2eeAppBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfDeviceAvailabilityBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfWASJDBCPoolBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfWASJVMHeapBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfWASThreadPoolBean;
import com.fable.insightview.monitor.middleware.websphere.entity.WebEjbMoudleInfoBean;
import com.fable.insightview.monitor.middleware.websphere.entity.WebMoudleInfosBean;
import com.fable.insightview.monitor.middleware.websphere.entity.WebSphereOperSituationBean;
import com.fable.insightview.monitor.middleware.websphere.service.IWebsphereService;
import com.fable.insightview.platform.common.util.JsonUtil;

@Controller
@RequestMapping("/monitor/websphereManage")
public class WebsphereController {
	@Autowired
	IWebsphereService webService;
	
	@Autowired
	ITomcatService tomService;

	private final Logger logger = LoggerFactory
			.getLogger(WebsphereController.class);

	/**
	 * 数据库服务信息
	 * @param request
	 * @param map
	 */
	@RequestMapping("/toShowServerInfo")
	@ResponseBody
	public ModelAndView toShowServerInfo(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载数据库服务详情页面");
		int MOID = Integer.parseInt(request.getParameter("moID"));
		List<MOMiddleWareJMXBean> was = webService.getWasInfo(MOID);
		if(was!=null){
			map.put("was", was.get(0));
		}
		return new ModelAndView("monitor/middleware/websphere/wasInfo");
	}

	/**
	 * 内存堆趋势图表页面跳转
	 * @return
	 */
	@RequestMapping("/toWasMemoryLineChart")
	public ModelAndView toWasMemoryLineChart(HttpServletRequest request,ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/websphereManage/initWasMemory");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}

	/**
	 * 内存使用率趋势图表数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initWasMemory")
	@ResponseBody
	public Map<String, Object> initWasMemory(HttpServletRequest request) {
		logger.info("开始...加载内存使用率趋势图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("seltItem", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");//Websphere 统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("内存使用率(%)");
		} else if (perf.equals("2")) {
			jsonData.setLegend("CPU使用率(%)");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<PerfWASJVMHeapBean> perfList = webService
			.queryWasMemoryPerf(params);
				int size = perfList.size();
				if (perfList != null && size > 0) {
					for (int i = 0; i < size; i++) {
						xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
						if (perf.equals("1")) {
							seriesSbf.append(perfList.get(i).getHeapUsage() + ",");
						} else if (perf.equals("2")) {
							seriesSbf
									.append(perfList.get(i).getProcessCpuUsage() + ",");
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
				logger.info(">>>>>>>>>===" + oracleChartData);
				result.put("oracleChartData", oracleChartData);
				logger.info("结束...加载内存使用率趋势图表数据");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * 响应趋势图表页面跳转
	 * @return
	 */
	@RequestMapping("/toWasResponseLineChart")
	public ModelAndView toWasResponseLineChart(HttpServletRequest request,ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/websphereManage/initWasResponse");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}

	/**
	 * 响应趋势图表数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initWasResponse")
	@ResponseBody
	public Map<String, Object> initWasResponse(HttpServletRequest request) {
		
		logger.info("开始...加载响应趋势图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("seltItem", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");//响应趋势 统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("响应趋势(ms)");
		} 
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<PerfDeviceAvailabilityBean> perfList = webService.queryWasResponsePerf(params);
			int size = perfList.size();
			if (perfList != null && size > 0) {
				for (int i = 0; i < size; i++) {
					xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
					if (perf.equals("1")) {
						seriesSbf.append(perfList.get(i).getResponseTime() + ",");
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
			logger.info(">>>>>>>>>===" + oracleChartData);
		
			result.put("oracleChartData", oracleChartData);
			logger.info("结束...加载响应趋势图表数据");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
	 * 线程池趋势图表页面跳转
	 * @return
	 */
	@RequestMapping("/toWasThreadLineChart")
	public ModelAndView toWasThreadLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/websphereManage/initWasThread");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}
	/**
	 * 线程池图表数据
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initWasThread")
	@ResponseBody
	public Map<String, Object> initWasThread(HttpServletRequest request) throws Exception {
		logger.info("开始...加载线程数据");	
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("MOID", request.getParameter("moID"));
		//存放返回页面json数据对象
		ChartColItem jsonData = new ChartColItem();
		String perf = request.getParameter("perfKind");
		jsonData.setText("");
		String legend="";
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[");
		int flag = 0;//标志是否作为x轴数据,默认否
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//查询线程个数	
			List<MOMiddleWareThreadPoolBean> cpuDeviceList =tomService.queryNumsByMoID(params);
			for(int i=0; i<cpuDeviceList.size(); i++){			
				params.put("MOID", cpuDeviceList.get(i).getMoId());
				List<PerfWASThreadPoolBean> cpuDeviceDetailList =webService.queryWasThreadPoolPerf(params);
				int size = cpuDeviceDetailList.size();
				if(cpuDeviceDetailList !=null && size>0){
					legend+=cpuDeviceList.get(i).getThreadName()+",";
					seriesSbf.append("{name:'"+cpuDeviceList.get(i).getThreadName()+"',type:'line',data:[");
					flag++;
				}			
				for(int j=0; j<size; j++){
					if(size>0 && flag == 1){//x轴只取一个有数据的
						xAxisSbf.append(cpuDeviceDetailList.get(j).getFormatTime()+",");
					}
					if(perf.equals("1")){
						seriesSbf.append(cpuDeviceDetailList.get(j).getPercentMaxed()+",");
					}else if(perf.equals("2")){
						seriesSbf.append(cpuDeviceDetailList.get(j).getActiveThreads()+",");
					}else if(perf.equals("3")){
						seriesSbf.append(cpuDeviceDetailList.get(j).getDestroyThreads()+",");
					}
					
				}					
				if(size>0){
					seriesSbf.deleteCharAt(seriesSbf.length()-1);
					seriesSbf.append("]},");
				}	
			}
			if(cpuDeviceList !=null && cpuDeviceList.size()>0 && xAxisSbf.length()>0 ){
				xAxisSbf.deleteCharAt(xAxisSbf.length()-1);
				seriesSbf.deleteCharAt(seriesSbf.length()-1);
			}		
			seriesSbf.append("]");
			jsonData.setLegend(legend.length()>0?legend.substring(0, legend.length()-1):legend);
			jsonData.setxAxisData(xAxisSbf.toString());
			
			jsonData.setSeriesData(seriesSbf.toString());
			if(jsonData.getSeriesData().indexOf("name:") ==-1){
				jsonData.setSeriesData("");
			}
			String oracleChartData =  JsonUtil.toString(jsonData);	
			result.put("oracleChartData", oracleChartData);
			logger.info("结束...加载当前线程数据");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	/**
	 * 跳转至线程池列表
	 * @return
	 */
	@RequestMapping("/toWasThreadPoolList")
	public ModelAndView toWasThreadPoolList(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/websphere/wasThreadPoolInfoList");
	}
	
	/**
	 * 加载线程池模块列表数据
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getWasThreadPoolInfo")
	@ResponseBody
	public Map<String, Object> getWasThreadPoolInfo() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("moId", request.getParameter("moID"));
		params.put("KPIcreateThreads", MiddleWareKPINameDef.CREATETHREADS);
		params.put("KPIdestroyThreads", MiddleWareKPINameDef.DESTROYTHREADS);
		params.put("KPIactiveThreads", MiddleWareKPINameDef.ACTIVETHREADS);
		params.put("KPIthreadPoolSize", MiddleWareKPINameDef.THREADPOOLSIZE);
		params.put("KPIpercentMaxed", MiddleWareKPINameDef.PERCENTMAXED);
		List<PerfWASThreadPoolBean> threadInfoList = webService.getWasThreadPoolInfo(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", threadInfoList);
		return result;
	}
	/**
	 * JDBC池使用率趋势图表页面跳转
	 * @return
	 */
	@RequestMapping("/toWasJdbcPoolLineChart")
	public ModelAndView toWasJdbcPoolLineChart(HttpServletRequest request,ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/websphereManage/initWasJdbcPool");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}

	
	/**
	 * JDBC池图表数据
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initWasJdbcPool")
	@ResponseBody
	public Map<String, Object> initWasJdbcPool(HttpServletRequest request) throws Exception {
		logger.info("开始...加载JDBC连接池数据");	
		Map params = new HashMap();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("MOID", request.getParameter("moID"));
		//查询线程个数	
		List<MoMidWareJdbcPoolBean> cpuDeviceList =webService.queryNumsByMoID(params);
		//存放返回页面json数据对象
		ChartColItem jsonData = new ChartColItem();
		String perf = request.getParameter("perfKind");
		if(perf.equals("1")){
			jsonData.setText("");//连接池使用率
		}
		String legend="";
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[");
		int flag = 0;//标志是否作为x轴数据,默认否
		for(int i=0; i<cpuDeviceList.size(); i++){			
			params.put("MOID", cpuDeviceList.get(i).getMoId());
			List<PerfWASJDBCPoolBean> cpuDeviceDetailList =webService.getPerfJdbcPoolList(params);
			int size = cpuDeviceDetailList.size();
			if(cpuDeviceDetailList !=null && size>0){
				legend+=cpuDeviceList.get(i).getDsName()+",";
				seriesSbf.append("{name:'"+cpuDeviceList.get(i).getDsName()+"',type:'line',data:[");
				flag++;
			}			
			for(int j=0; j<size; j++){
				if(size>0 && flag == 1){//x轴只取一个有数据的
					xAxisSbf.append(cpuDeviceDetailList.get(j).getFormatTime()+",");
				}
				if(perf.equals("1")){
					seriesSbf.append(cpuDeviceDetailList.get(j).getPoolUsage()+",");
				}
				
			}					
			if(size>0){
				seriesSbf.deleteCharAt(seriesSbf.length()-1);
				seriesSbf.append("]},");
			}	
		}
		if(cpuDeviceList !=null && cpuDeviceList.size()>0 && xAxisSbf.length()>0 ){
			xAxisSbf.deleteCharAt(xAxisSbf.length()-1);
			seriesSbf.deleteCharAt(seriesSbf.length()-1);
		}		
		seriesSbf.append("]");
		
		jsonData.setLegend(legend.length()>0?legend.substring(0, legend.length()-1):legend);
		jsonData.setxAxisData(xAxisSbf.toString());
		
		jsonData.setSeriesData(seriesSbf.toString());
		if(jsonData.getSeriesData().indexOf("name:") ==-1){
			jsonData.setSeriesData("");
		}
		String oracleChartData =  JsonUtil.toString(jsonData);	
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("oracleChartData", oracleChartData);
		logger.info("结束...加载连接池使用率数据");
		System.out.println("当前连接池使用率>>"+oracleChartData);
		return result;
	}

	/**
	 * 跳转至websphere运行现状模块
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toWebSphereOperSituation")
	public ModelAndView toWebSphereOperSituation(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/websphere/webSphereOperSituation");
	}
	
	/**
	 * 加载websphere运行现状模块数据
	 * @throws Exception
	 */
	@RequestMapping("/getWebSphereOperSituationInfos")
	@ResponseBody
	public Map<String, Object> getWebSphereOperSituationInfos() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		DecimalFormat df = new DecimalFormat("#.##");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("moId", request.getParameter("moID"));
		params.put("MID", KPINameDef.JOBURLPOLL);
		params.put("KPIserviceAvailability", MiddleWareKPINameDef.SERVICEAVAILABILITY);
		List<WebSphereOperSituationBean> webOperSituInfos = webService.getWebSphereOperSituationInfos(params);
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
		List<WebSphereOperSituationBean> statusInfos = webService.getDeviceStatus(map);
		double statusValues1 = 0;
		double statusValues2 = 0;
		//double responseTimes=0.0;
		for (int i = 0; i < statusInfos.size(); i++) {
			if(Integer.parseInt(statusInfos.get(i).getDeviceStatus()) == 1){
				statusValues1 += Double.parseDouble(statusInfos.get(i).getDeviceStatus());
			}else{
				statusValues2 += Double.parseDouble(statusInfos.get(i).getDeviceStatus());
			}
		}
		if(webOperSituInfos.size()>0){
			String avgAvailability = df.format((double)(Math.round((statusValues1/(statusValues1+statusValues2))*100)/100.0)*100)+"%";
			webOperSituInfos.get(0).setAvgAvailability(avgAvailability);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", webOperSituInfos);
		return result;
	}
	
	/**
	 * 应用程序信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toShowAppDetail")
	@ResponseBody
	public ModelAndView toShowAppDetail(HttpServletRequest request, ModelMap map) {
		int	moID = Integer.parseInt(request.getParameter("moID"));
		MoMiddleWareJ2eeAppBean appBean = webService.getWebAppInfos(moID);
		map.put("appBean", appBean);
		return new ModelAndView("monitor/middleware/websphere/webAppInfos");
	}
	
	/**
	 * 跳转至web模块列表
	 * @return
	 */
	@RequestMapping("/toWebMoudleList")
	public ModelAndView toWebMoudleList(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/websphere/webMoudleList");
	}
	
	/**
	 * 加载web模块列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getWebMoudleInfos")
	@ResponseBody
	public Map<String, Object> getWebMoudleInfos() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("moId", request.getParameter("moID"));
		params.put("KPItotalRequests", MiddleWareKPINameDef.TOTALREQUESTS);
		params.put("KPIconcurrentRequests", MiddleWareKPINameDef.CONCURRENTREQUESTS);
		params.put("KPIresponseTime", MiddleWareKPINameDef.RESPONSETIME);
		params.put("KPInumErrors", MiddleWareKPINameDef.NUMERRORS);
		List<WebMoudleInfosBean> webMoudleInfoList = webService.getWebMoudleInfos(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", webMoudleInfoList);
		return result;
	}
	
	/**
	 * 跳转至ejb模块列表
	 * @return
	 */
	@RequestMapping("/toWebEjbMoudleList")
	public ModelAndView toWebEjbMoudleList(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/websphere/webEjbMoudleList");
	}
	
	/**
	 * 加载ejb模块列表数据
	 * @param taskBean
	 * @throws Exception
	 */
	@RequestMapping("/getWebEjbMoudleInfos")
	@ResponseBody
	public Map<String, Object> getWebEjbMoudleInfos() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		List<WebEjbMoudleInfoBean> webEjbMoudleInfoList = webService.getWebEjbInfos(Integer.parseInt(request
				.getParameter("moID")));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", webEjbMoudleInfoList);
		return result;
	}
	
	/**
	 * webApp性能曲线图
	 */
	@RequestMapping("/toWebAppLineChart")
	public ModelAndView toWebAppLineChart(HttpServletRequest request,

	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/websphereManage/initWebAppServletInfos");
		return new ModelAndView("monitor/middleware/websphere/webAppLineChart");
	}
	
	/**
	 * * 内存池趋势图表数据
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initWebAppServletInfos")
	@ResponseBody
	public Map<String, Object> initWebAppServletInfos(HttpServletRequest request){
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moId", request.getParameter("moid"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setText("请求数");
		}else if(perf.equals("2")){
			jsonData.setText("并发请求数");
		}else{
			jsonData.setText("响应时间(ms)");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		String legend = "";
		StringBuffer seriesSbf = new StringBuffer("[");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<WebMoudleInfosBean> perfList = webService.queryWebAppPerf(params);
			Set  set=new HashSet();   
			Set  timeSet=new HashSet();   
			Map<String,Integer> timeMap = new HashMap<String,Integer>();
			for (int i = 0; i < perfList.size(); i++) {
				timeSet.add(perfList.get(i).getFormatTime());
				set.add(perfList.get(i).getServletName());
				timeMap.put(perfList.get(i).getFormatTime(), 1);
			}
			for (int i = 0; i < perfList.size(); i++) {
				for (Iterator iterator = timeMap.keySet().iterator(); iterator.hasNext();) {
					if(perfList.get(i).getFormatTime().equals(iterator.next())){
						int value = timeMap.get(perfList.get(i).getFormatTime());
						timeMap.put(perfList.get(i).getFormatTime(), value+1);
					}
					
				}
			}
			List<String> list = new ArrayList<String>(); 
			for (Iterator it = timeSet.iterator();  it.hasNext(); ){
				String setValue = it.next().toString();
				if(timeMap.get(setValue) > 2){
					list.add(setValue);  
				}
			}
			Collections.sort(list);  
			for (int i = 0; i < list.size(); i++) {
				xAxisSbf.append(list.get(i) + ",");
			}
			if (perfList != null) {
				for (Iterator it = set.iterator();  it.hasNext(); ) {
					String servletName = it.next().toString();
					legend += servletName + ",";
					seriesSbf.append("{name:'" + servletName
							+ "',type:'line',data:[");
					if (perf.equals("1")) {
						for (int j = 0; j < perfList.size(); j++) {
							if(servletName.equals(perfList.get(j).getServletName())){
								seriesSbf.append(perfList.get(j).getTotalRequests() + ",");
							}
						}
					}else if (perf.equals("2")) {
						for (int j = 0; j < perfList.size(); j++) {
							if(servletName.equals(perfList.get(j).getServletName())){
								seriesSbf.append(perfList.get(j).getConcurrentRequests() + ",");
							}
							
						}
					}else{
						for (int j = 0; j < perfList.size(); j++) {
							if(servletName.equals(perfList.get(j).getServletName())){
								seriesSbf.append(perfList.get(j).getResponseTime() + ",");
							}
						}
					}
					if (perfList.size() > 0) {
						seriesSbf.deleteCharAt(seriesSbf.length() - 1);
						seriesSbf.append("]},");
					}
				}
				if (perfList.size() > 0) {
					xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
					seriesSbf.deleteCharAt(seriesSbf.length() - 1);
				}
				seriesSbf.append("]");
				jsonData.setLegend(legend.length() > 0 ? legend.substring(0, legend
						.length() - 1) : legend);
				jsonData.setxAxisData(xAxisSbf.toString());
				jsonData.setSeriesData(seriesSbf.toString());
			}
			String webAppChartData = JsonUtil.toString(jsonData);
			logger.info("webAppChartData===" + webAppChartData);
		
			result.put("webAppChartData", webAppChartData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * 跳转至JDBC连接池列表
	 * @return
	 */
	@RequestMapping("/toJdbcPoolInfoList")
	public ModelAndView toJdbcPoolInfoList(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/websphere/jdbcPoolInfoList");
	}
	
	/**
	 * 加载JDBC连接池列表数据
	 * @throws Exception
	 */
	@RequestMapping("/getJdbcPoolInfos")
	@ResponseBody
	public Map<String, Object> getJdbcPoolInfos() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		List<MoMidWareJdbcPoolBean> jdbcPoolInfoList = webService.getJdbcPoolInfos(Integer.parseInt(request
				.getParameter("moID")));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", jdbcPoolInfoList);
		return result;
	}
	
	/**
	 * 跳转至JDBC数据源列表
	 */
	@RequestMapping("/toJdbcDsInfoList")
	public ModelAndView toJdbcDsInfoList(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/websphere/jdbcDsInfoList");
	}
	
	/**
	 * 加载JDBC数据源列表数据
	 * @throws Exception
	 */
	@RequestMapping("/getJdbcDsInfos")
	@ResponseBody
	public Map<String, Object> getJdbcDsInfos() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		List<MoMidWareJdbcDsBean> jdbcDsInfoList = webService.getJdbcDsInfos(Integer.parseInt(request
				.getParameter("moID")));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", jdbcDsInfoList);
		return result;
	}
	
	/**
	 * JDBC数据源详情
	 * @param request
	 * @param map
	 * @return
	 */

	@RequestMapping("/toShowJdbcDsDetail")
	@ResponseBody
	public ModelAndView toShowJdbcDsDetail(HttpServletRequest request, ModelMap map) {
		int	moID = Integer.parseInt(request.getParameter("moID"));
		MoMidWareJdbcDsBean jdbcDs = webService.getJdbcDsDetailInfos(moID);
		map.put("jdbcDs", jdbcDs);
		map.put("flag", request.getParameter("flag"));
		return new ModelAndView("monitor/middleware/websphere/jdbcDsDetailInfos");
	}
}
