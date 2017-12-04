package com.fable.insightview.monitor.middelware.controller;

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

import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareMemoryBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareThreadPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MiddleWareKPINameDef;
import com.fable.insightview.monitor.middleware.tomcat.entity.TomcatOperSituationBean;
import com.fable.insightview.monitor.middleware.tomcat.service.ITomcatService;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJDBCPoolBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJMSBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJTABean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJVMHeapBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicMemoryPoolBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicThreadPoolBean;
import com.fable.insightview.monitor.middleware.weblogic.service.IWeblogicService;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMidWareJdbcPoolBean;
import com.fable.insightview.monitor.middleware.websphere.service.IWebsphereService;
import com.fable.insightview.platform.common.util.JsonUtil;

@Controller
@RequestMapping("/monitor/weblogicManage")
public class WeblogicController {

	@Autowired
	IWeblogicService weblogicService;
	@Autowired
	IWebsphereService webService;
	@Autowired
	ITomcatService tomService;

	private final Logger logger = LoggerFactory
			.getLogger(WeblogicController.class);
	/**
	 * 跳转至WebLogic运行现状模块
	 */
	@RequestMapping("/toWebLogicOperSituation")
	public ModelAndView toWebLogicOperSituation(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/weblogic/webLogicOperSituation");
	}
	/**
	 * 加载WebLogic运行现状模块数据
	 */
	@RequestMapping("/getWebLogicOperSituationInfos")
	@ResponseBody
	public Map<String, Object> getWebLogicOperSituationInfos() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("MOID", request.getParameter("moID"));
		params.put("MID", KPINameDef.JOBURLPOLL);
		params.put("KPIserviceAvailability",MiddleWareKPINameDef.SERVICEAVAILABILITY);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<TomcatOperSituationBean> tmOperSituInfos = weblogicService.getWebLogicOperSituationInfos(params);
			if (null != tmOperSituInfos.get(0).getUpTime() &&  !"".equals(tmOperSituInfos.get(0).getUpTime())) {
				tmOperSituInfos.get(0).setUpTime(HostComm.getMsToTime(Long.parseLong(tmOperSituInfos
								.get(0).getUpTime())));
			}
		
			result.put("rows", tmOperSituInfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return result;
	}

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
		int moID = Integer.parseInt(request.getParameter("moID"));
		List<MOMiddleWareJMXBean> was = weblogicService.getWeblogicInfo(moID);
		if(was!=null){
			map.put("weblogic", was.get(0));
		}
		return new ModelAndView("monitor/middleware/weblogic/webLogicInfo");
	}
	/**
	 * 内存堆趋势图表页面跳转
	 * @return
	 */
	@RequestMapping("/toWebLogicMemoryLineChart")
	public ModelAndView toWebLogicMemoryLineChart(HttpServletRequest request,ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/weblogicManage/initWebLogicMemory");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}

	/**
	 * 内存使用率趋势图表数据
	 */
	@RequestMapping("/initWebLogicMemory")
	@ResponseBody
	public Map<String, Object> initWebLogicMemory(HttpServletRequest request) {
		logger.info("开始...加载内存使用率趋势图表数据");
		Map<String, Object> params = new HashMap<String, Object>();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("seltItem", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");//Weblogic 统计
		String perf = request.getParameter("perfKind");
		jsonData.setLegend("内存使用率(%)");
		
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<PerfWebLogicJVMHeapBean> perfList = weblogicService.queryWebLogicMemoryPerf(params);
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
				logger.info(">>>>>>>>>===" + oracleChartData);
				result.put("oracleChartData", oracleChartData);
				logger.info("结束...加载内存使用率趋势图表数据");
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
	public Map<String, Object> queryBetweenTime(HttpServletRequest request, Map<String, Object> params) {
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
	 * JMS服务器个数统计页面跳转
	 * @return
	 */
	@RequestMapping("/toJmsServerLineChart")
	public ModelAndView toJmsServerLineChart(HttpServletRequest request,ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/weblogicManage/initJmsServer");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}
	/**
	 * * JMS服务器数趋势数据(JMS服务器总数,最大JMS服务器数,当前JMS服务器数)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initJmsServer")
	@ResponseBody
	public Map<String, Object> initJmsServer(HttpServletRequest request) throws Exception {
		logger.info("开始...加载JMS服务器数趋势数据");
		Map<String, Object> params = new HashMap<String, Object>();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		String perf = request.getParameter("perfKind");
		//存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[");	
		StringBuffer lineDataA = null ;
		StringBuffer lineDataB = null ;
		StringBuffer lineDataC = null ;
		if (perf.equals("1")) {
			 lineDataA = new StringBuffer("{name:'JMS服务器总数',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'最大JMS服务器数',type:'line',data:[");
			 lineDataC = new StringBuffer("{name:'当前JMS服务器数',type:'line',data:[");
			 jsonData.setText("JMS服务器数统计 (个)");
			 jsonData.setLegend("JMS服务器总数,最大JMS服务器数,当前JMS服务器数");
		}else if(perf.equals("2")) {
			 lineDataA = new StringBuffer("{name:'JMS连接总数',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'最大连接数',type:'line',data:[");
			 lineDataC = new StringBuffer("{name:'当前连接数',type:'line',data:[");
			 jsonData.setText("JMS连接数统计(个)");
			 jsonData.setLegend("JMS连接总数,最大连接数,当前连接数");
			}
		
		
		List<PerfWebLogicJMSBean> serverChartList =weblogicService.queryServerPerf(params);
		int size = serverChartList.size();
		
		if(serverChartList != null && size >0){
			for(int j=0; j<size; j++){
				xAxisSbf.append(serverChartList.get(j).getFormatTime()+",");
				if (perf.equals("1")) {
					lineDataA.append(serverChartList.get(j).getJmsServersTotal()+",");
					lineDataB.append(serverChartList.get(j).getJmsServersHigh()+",");	
					lineDataC.append(serverChartList.get(j).getJmsServersCurrent()+",");
				}else if(perf.equals("2")) {
					lineDataA.append(serverChartList.get(j).getConnectionsTotal()+",");
					lineDataB.append(serverChartList.get(j).getConnectionsHigh()+",");	
					lineDataC.append(serverChartList.get(j).getConnectionsCurrent()+",");
				}
			}			
			xAxisSbf.deleteCharAt(xAxisSbf.length()-1);
			lineDataA.deleteCharAt(lineDataA.length()-1);
			lineDataB.deleteCharAt(lineDataB.length()-1);
			lineDataC.deleteCharAt(lineDataC.length()-1);
			
			seriesSbf.append(lineDataA.toString()+"]},"+lineDataB.toString()+"]},"+lineDataC.toString()+"]}]");
			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String jsonServerChartData =  JsonUtil.toString(jsonData);	
		logger.info("jsonData>>>>>>>>>"+jsonServerChartData);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("oracleChartData", jsonServerChartData);
		
		logger.info("结束...加载JMS服务器数趋势数据");
		return result;
	}
	/**
	 * JTA信息
	 * @param request
	 * @param map
	 */
	@RequestMapping("/toShowJTAInfo")
	@ResponseBody
	public ModelAndView toShowJTAInfo(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载JTA详情页面");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("MOID", moID);
		params.put("JtaHeuristics", MiddleWareKPINameDef.JTAHEURISTICS);
		params.put("JtaTwoPhaseCommitted", MiddleWareKPINameDef.JTATWOPHASECOMMITTED);
		params.put("JtaRolledBackResource", MiddleWareKPINameDef.JTAROLLEDBACKRESOURCE);
		params.put("JtaOneResourceOnePhaseCommitted", MiddleWareKPINameDef.JTAONERESOURCEONEPHASECOMMITTED);
		params.put("JtaRolledBackApp", MiddleWareKPINameDef.JTAROLLEDBACKAPP);
		params.put("JtaLLRCommitted", MiddleWareKPINameDef.JTALLRCOMMITTED);
		params.put("JtaRolledBackTimeout", MiddleWareKPINameDef.JTAROLLEDBACKTIMEOUT);
		params.put("JtaReadOnlyOnePhaseCommitted", MiddleWareKPINameDef.JTAREADONLYONEPHASECOMMITTED);
		params.put("JtaAbandoned", MiddleWareKPINameDef.JTAABANDONED);
		params.put("JtaActive", MiddleWareKPINameDef.JTAACTIVE);
		params.put("JtaRolledBackSystem", MiddleWareKPINameDef.JTAROLLEDBACKSYSTEM);
		params.put("JtaRolledBack", MiddleWareKPINameDef.JTAROLLEDBACK);
		params.put("JtaNoResourcesCommitted", MiddleWareKPINameDef.JTANORESOURCESCOMMITTED);
		params.put("JtaTotal", MiddleWareKPINameDef.JTATOTAL);
		params.put("JtaCommitted", MiddleWareKPINameDef.JTACOMMITTED);
		PerfWebLogicJTABean weblogic = weblogicService.getJTAInfo(params);
		if (weblogic != null ) {
			map.put("weblogic", weblogic);
		}
		return new ModelAndView("monitor/middleware/weblogic/jtaInfo");
	}

	/**
	 * JTA事务回滚原因分类
	 * @param request
	 * @param map
	 */
	@RequestMapping("/toShowJTARollbackPie")
	@ResponseBody
	public ModelAndView toShowJTARollbackPie(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载JTA详情页面");
		int moID = Integer.parseInt(request.getParameter("moID"));
		//PerfWebLogicJTABean weblogic = weblogicService.getPieInfo(moID);
		PerfWebLogicJTABean weblogic = weblogicService.getJTAInfo(map);
		
		StringBuffer rollName = new StringBuffer();
		StringBuffer rollLstNumJson = new StringBuffer();
		if(weblogic!=null){
			rollName.append("'系统错误',");
			rollName.append("'超时',");
			rollName.append("'资源错误',");
			rollName.append("'应用程序'");
			
			rollLstNumJson.append("{value:"+weblogic.getTransRollBackSysTotal()+",name:'系统错误'},");
			rollLstNumJson.append("{value:"+weblogic.getTransRollBackTimeoutTotal()+",name:'超时'},");
			rollLstNumJson.append("{value:"+weblogic.getTransRollBackResTotal()+",name:'资源错误'},");
			rollLstNumJson.append("{value:"+weblogic.getTransRollBackAppTotal()+",name:'应用程序'}");
			map.put("rollName",rollName);
			map.put("rollLstNumJson",rollLstNumJson);
		}
		return new ModelAndView("monitor/middleware/weblogic/webLogicPieChart");
	}
	/**
	 * JDBC池使用率趋势图表页面跳转
	 * @return
	 */
	@RequestMapping("/toWebLogicJdbcPoolLineChart")
	public ModelAndView toWebLogicJdbcPoolLineChart(HttpServletRequest request,ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/weblogicManage/initWebLogicJdbcPool");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}
	
	/**
	 * * JDBC池图表数据
	 * @param request
	 */
	@RequestMapping("/initWebLogicJdbcPool")
	@ResponseBody
	public Map<String, Object> initWebLogicJdbcPool(HttpServletRequest request) throws Exception {
		logger.info("开始...加载JDBC连接池数据");	
		Map<String, Object> params = new HashMap<String, Object>();
		//默认取最近24小时数据
		params = queryBetweenTime(request,params);
		params.put("MOID", request.getParameter("moID"));
		Map<String, Object> result = new HashMap<String, Object>();
		try {
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
				List<PerfWebLogicJDBCPoolBean> cpuDeviceDetailList =weblogicService.getPerfJdbcPoolList(params);
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
						seriesSbf.append(cpuDeviceDetailList.get(j).getConnectionPoolUsage()+",");
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
			logger.info("结束...加载连接池使用率数据");
			System.out.println("当前连接池使用率>>"+oracleChartData);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * JTA事务回滚原因分类
	 * @param request
	 * @param map
	 */
	@RequestMapping("/toShowCurrMemoryUsage")
	@ResponseBody
	public ModelAndView toShowCurrMemoryUsage(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载当前内存使用率");
		Map<String,Object> params = new HashMap<String,Object>();
		int moID = Integer.parseInt(request.getParameter("moID"));
		params.put("MOID", moID);
		params.put("heapSize", MiddleWareKPINameDef.HEAPSIZE);
		params.put("heapUsed", MiddleWareKPINameDef.HEAPUSED);
		params.put("heapUsage", MiddleWareKPINameDef.HEAPUSAGE);		
		PerfWebLogicJVMHeapBean weblogic = weblogicService.getCurrMemUsage(params);
		map.put("heapUsage",weblogic.getHeapUsage());
		map.put("heapSize",HostComm.getBytesToSize(Long.parseLong(weblogic.getHeapSize())));
		map.put("heapUsed",HostComm.getBytesToSize(Long.parseLong(weblogic.getHeapUsed())));
		return new ModelAndView("monitor/middleware/weblogic/webLogicCurrMemoryChart");
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
		return new ModelAndView("monitor/middleware/weblogic/jdbcPoolInfoList");
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
	 * JDBC数据源详情
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toShowJdbcPoolDetail")
	@ResponseBody
	public ModelAndView toShowJdbcPoolDetail(HttpServletRequest request, ModelMap map) {
		int	moID = Integer.parseInt(request.getParameter("moID"));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("MOID", moID);
		params.put("CurrConnections", MiddleWareKPINameDef.CURRCONNECTIONS);
		params.put("AvailableSessions", MiddleWareKPINameDef.AVAILABLESESSIONS);
		params.put("CurrWaitingConnections", MiddleWareKPINameDef.CURRWAITINGCONNECTIONS);
		params.put("LeakedConnections", MiddleWareKPINameDef.LEAKEDCONNECTIONS);
		params.put("CurrActiveConnections", MiddleWareKPINameDef.CURRACTIVECONNECTIONS);
		params.put("FailuresReconnect", MiddleWareKPINameDef.FAILURESRECONNECT);
		params.put("TotalConnections", MiddleWareKPINameDef.TOTALCONNECTIONS);
		params.put("JDBCPoolUsage", MiddleWareKPINameDef.JDBCPOOLUSAGE);
		PerfWebLogicJDBCPoolBean jdbcPool = weblogicService.getJdbcPoolDetailInfos(params);
		map.put("jdbcPool", jdbcPool);
		map.put("flag", request.getParameter("flag"));
		return new ModelAndView("monitor/middleware/weblogic/jdbcPoolDetailInfos");
	}
	/**
	 * JMS服务器个数统计页面跳转
	 * @return
	 */
	@RequestMapping("/toJdbcLineChart")
	public ModelAndView toJdbcLineChart(HttpServletRequest request,ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/weblogicManage/initJdbc");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}
	/**
	 * JDBC连接数趋势数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initJdbc")
	@ResponseBody
	public Map<String, Object> initJdbc(HttpServletRequest request) throws Exception {
		logger.info("开始...加载JDBC连接数趋势数据");
		Map<String, Object> params = new HashMap<String, Object>();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		String perf = request.getParameter("perfKind");
		//存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[");	
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer lineDataA = null ;
		StringBuffer lineDataB = null ;
		StringBuffer lineDataC = null ;
		StringBuffer lineDataD = null ;
		if (perf.equals("1")) {
			 lineDataA = new StringBuffer("{name:'当前连接数',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'活动连接数',type:'line',data:[");
			 lineDataC = new StringBuffer("{name:'等待连接数',type:'line',data:[");
			 lineDataD = new StringBuffer("{name:'泄漏连接数',type:'line',data:[");
			 jsonData.setText("连接数统计 (个)");
			 jsonData.setLegend("当前连接数,活动连接数,等待连接数,泄漏连接数");
		}else if(perf.equals("2")) {
			 lineDataA = new StringBuffer("{name:'可用数量',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'连接总数',type:'line',data:[");
			 lineDataC = new StringBuffer("{name:'重新连接失败数',type:'line',data:[");
			 jsonData.setText("连接数统计(个)");
			 jsonData.setLegend("可用数量,连接总数,重新连接失败数");
			}
		try {
			List<PerfWebLogicJDBCPoolBean> serverChartList =weblogicService.queryJdbcPerf(params);
			int size = serverChartList.size();
			
			if(serverChartList != null && size >0){
				for(int j=0; j<size; j++){
					xAxisSbf.append(serverChartList.get(j).getFormatTime()+",");
					if (perf.equals("1")) {
						lineDataA.append(serverChartList.get(j).getCurrCapacity()+",");
						lineDataB.append(serverChartList.get(j).getActiveConnectionsCurrent()+",");	
						lineDataC.append(serverChartList.get(j).getWaitingForConnectionCurrent()+",");
						lineDataD.append(serverChartList.get(j).getLeakedConnection()+",");
					}else if(perf.equals("2")) {
						lineDataA.append(serverChartList.get(j).getNumAvailable()+",");
						lineDataB.append(serverChartList.get(j).getConnectionsTotal()+",");	
						lineDataC.append(serverChartList.get(j).getFailuresToReconnect()+",");
					}
				}			
				xAxisSbf.deleteCharAt(xAxisSbf.length()-1);
				lineDataA.deleteCharAt(lineDataA.length()-1);
				lineDataB.deleteCharAt(lineDataB.length()-1);
				lineDataC.deleteCharAt(lineDataC.length()-1);
				if (perf.equals("1")) {
					lineDataD.deleteCharAt(lineDataD.length()-1);
					seriesSbf.append(lineDataA.toString()+"]},"+lineDataB.toString()+"]},"+lineDataC.toString()+"]},"+lineDataD.toString()+"]}]");
				}else if(perf.equals("2")) {
					seriesSbf.append(lineDataA.toString()+"]},"+lineDataB.toString()+"]},"+lineDataC.toString()+"]}]");
				}
				
				jsonData.setxAxisData(xAxisSbf.toString());
				jsonData.setSeriesData(seriesSbf.toString());
			}
			
			String jsonServerChartData =  JsonUtil.toString(jsonData);	
			logger.info("jsonData>>>>>>>>>"+jsonServerChartData);
			result.put("oracleChartData", jsonServerChartData);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...JDBC连接数趋势数据");
		return result;
	}
	
	/**
	 * 线程池趋势图表页面跳转
	 * @return
	 */
	@RequestMapping("/toWebLogicThreadLineChart")
	public ModelAndView toWebLogicThreadLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/weblogicManage/initWebLogicThread");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}
	/**
	 * 线程池图表数据
	 * @param request
	 */
	@RequestMapping("/initWebLogicThread")
	@ResponseBody
	public Map<String, Object> initWebLogicThread(HttpServletRequest request) throws Exception {
		logger.info("开始...加载线程数据");	
		Map<String, Object> params = new HashMap<String, Object>();
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
				List<PerfWebLogicThreadPoolBean> cpuDeviceDetailList =weblogicService.queryWebLogicThreadPoolPerf(params);
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
						seriesSbf.append(cpuDeviceDetailList.get(j).getExecuteThreadTotal()+",");
					}else if(perf.equals("2")){
						seriesSbf.append(cpuDeviceDetailList.get(j).getExecuteThreadIdle()+",");
					}else if(perf.equals("3")){
						seriesSbf.append(cpuDeviceDetailList.get(j).getPendingUserRequest()+",");
					}else if(perf.equals("4")){
						seriesSbf.append(cpuDeviceDetailList.get(j).getThreadPoolUsage()+",");
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
	@RequestMapping("/toWebLogicThreadPoolList")
	public ModelAndView toWebLogicThreadPoolList(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/middleware/weblogic/threadPoolInfoList");
	}
	/**
	 * 加载线程池模块列表数据
	 * @return
	 */
	@RequestMapping("/getWebLogicThreadPoolInfo")
	@ResponseBody
	public Map<String, Object> getWebLogicThreadPoolInfo(){
		logger.info("开始...加载线程池信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("MOID", request.getParameter("moID"));
		params.put("tabName", " MOMiddleWareThreadPool");
		List<MOMiddleWareThreadPoolBean> moLsinfo = new ArrayList<MOMiddleWareThreadPoolBean>();
		try {
			List<MOMiddleWareThreadPoolBean> moLsCount = tomService.getThreadPoolCount(params);
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
			Collections.sort(moLsinfo, new Comparator<MOMiddleWareThreadPoolBean>() {  
		           public int compare(MOMiddleWareThreadPoolBean arg0, MOMiddleWareThreadPoolBean arg1) {  
		               double hits0 = arg0.getThreadPoolUsage();
		               double hits1 = arg1.getThreadPoolUsage();  
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
			if (name.equals(MiddleWareKPINameDef.TOTALTHREADS)) {
				mo.setExecuteThreadTotal(value);
			} else if (name.equals(MiddleWareKPINameDef.IDLETHREADS)) {
				mo.setExecuteThreadIdle(value);
			}  else if (name.equals(MiddleWareKPINameDef.PENDINGUSERREQUEST)) {
				mo.setPendingUserRequest(value);
			}else if (name.equals(MiddleWareKPINameDef.THREADPOOLUSAGE)) {
				mo.setThreadPoolUsage(value);
			}
			mo.setThreadName(list.get(i).getThreadName());
		}
		return mo;
	}
	/**
	 * 内存池趋势图表页面跳转
	 * @return
	 */
	@RequestMapping("/toWebLogicMemoryPoolLineChart")
	public ModelAndView toWebLogicMemoryPoolLineChart(HttpServletRequest request,

	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/weblogicManage/initWebLogicMemoryPool");
		return new ModelAndView("monitor/middleware/tomcat/tmLineChart");
	}

	/**
	 *  内存池趋势图表数据
	 */
	@RequestMapping("/initWebLogicMemoryPool")
	@ResponseBody
	public Map<String, Object> initWebLogicMemoryPool(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载内存池使用率趋势图表数据");
		Map<String, Object> params = new HashMap<String, Object>();
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
				List<PerfWebLogicMemoryPoolBean> cpuDeviceDetailList = weblogicService
						.queryWebLogicMemoryPoolPerf(params);
				int size = cpuDeviceDetailList.size();
				if (cpuDeviceDetailList != null && size > 0) {
					legend += cpuDeviceList.get(i).getMemName() + ",";
					seriesSbf.append("{name:'" + cpuDeviceList.get(i).getMemName()
							+ "',type:'line',data:[");
					flag++;
				}
				
				for (int j = 0; j < size; j++) {
					if (size > 0 && flag == 1) {// x轴只取一个有数据的
						xAxisSbf.append(cpuDeviceDetailList.get(j).getFormatTime()
								+ ",");
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
}
