package com.fable.insightview.monitor.database.controller;

import java.util.ArrayList;
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

import com.fable.insightview.monitor.database.entity.MOMsSQLDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDeviceBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MSSQLServerKPINameDef;
import com.fable.insightview.monitor.database.entity.PerfMSSQLDatabaseBean;
import com.fable.insightview.monitor.database.service.IMsServerService;
import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/monitor/msDbManage")
public class MsDBController {
	@Autowired
	IMsServerService msService;

	private final Logger logger = LoggerFactory.getLogger(MsDBController.class);
	
	/**
	 * 加载msServer列表页面
	 * @return
	 */
	@RequestMapping("/toMsServerList")
	@ResponseBody
	public ModelAndView toMsServerList(HttpServletRequest request,String navigationBar) {
		String flag = request.getParameter("flag");
		String dbmsMoId = request.getParameter("dbmsMoId");
		request.setAttribute("flag", flag);
		request.setAttribute("dbmsMoId", dbmsMoId);
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("monitor/device/msServer_list");
	}

	/**
	 * 加载msServer列表页面
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listMsServerInfos")
	@ResponseBody
	public Map<String, Object> listMsServerInfos() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MOMySQLDBServerBean> page = new Page<MOMySQLDBServerBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(!"".equals(request.getParameter("dbmsMoId")) && request.getParameter("dbmsMoId") != null){
			paramMap.put("dbmsMoId", request.getParameter("dbmsMoId"));
		}
		paramMap.put("serverName", request.getParameter("serverName"));
		paramMap.put("IP", request.getParameter("ip"));
		page.setParams(paramMap);
		List<MOMySQLDBServerBean> serverLst =msService.queryMOMsSQLServer(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", serverLst);
		return result;
	}
	/**
	 * 加载ms Device列表页面
	 * @return
	 */
	@RequestMapping("/toMsDeviceList")
	@ResponseBody
	public ModelAndView toMsDeviceList(HttpServletRequest request,String navigationBar) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String serverMoId = request.getParameter("serverMoId");
		request.setAttribute("serverMoId", serverMoId);
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("monitor/device/msDevice_list");
	}

	/**
	 * 加载ms设备列表页面
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listMsDeviceInfos")
	@ResponseBody
	public Map<String, Object> listMsDeviceInfos() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MOMsSQLDeviceBean> page = new Page<MOMsSQLDeviceBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String serverMoId = request.getParameter("serverMoId");
		if(!"".equals(serverMoId) && serverMoId != null && !"null".equals(serverMoId)){
			paramMap.put("serverMoId", Integer.parseInt(serverMoId));
		}
		paramMap.put("deviceName", request.getParameter("deviceName"));
		paramMap.put("IP", request.getParameter("ip"));
		page.setParams(paramMap);
		List<MOMsSQLDeviceBean> deviceLst =msService.queryMOMsSQLDevice(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", deviceLst);
		return result;
	}
	/**
	 * 加载msDB列表页面
	 * @return
	 */
	@RequestMapping("/toMsDBList")
	@ResponseBody
	public ModelAndView toMsDBList(HttpServletRequest request,String navigationBar) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String serverMoId = request.getParameter("serverMoId");
		request.setAttribute("serverMoId", serverMoId);
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("monitor/device/msDB_list");
	}

	/**
	 * 加载ms DB列表页面
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listMsDBInfos")
	@ResponseBody
	public Map<String, Object> listMsDBInfos()  {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MOMsSQLDatabaseBean> page = new Page<MOMsSQLDatabaseBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String serverMoId = request.getParameter("serverMoId");
		if(!"".equals(serverMoId) && serverMoId != null && !"null".equals(serverMoId)){
			paramMap.put("serverMoId", Integer.parseInt(serverMoId));
		}
		paramMap.put("databaseName", request.getParameter("databaseName"));
		paramMap.put("IP", request.getParameter("ip"));
		page.setParams(paramMap);
		List<MOMsSQLDatabaseBean> dbLst =msService.queryMOMsSQLDatabase(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", dbLst);
		return result;
	}
	/**
	 * 服务曲线页面跳转
	 * @return
	 */
	@RequestMapping("/toDababaseLineChart")
	@ResponseBody
	public ModelAndView toDababaseLineChart(HttpServletRequest request,	ModelMap map) {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/msDbManage/initDababaseLine");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}
	/**
	 *  数据库曲线图表数据
	 */
	@RequestMapping("/initDababaseLine")
	@ResponseBody
	public Map<String, Object> initDababaseLine(HttpServletRequest request){
		logger.info("开始...加载数据库曲线趋势图表数据");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		// 默认取最近24小时数据
		params = MSSQLServerKPINameDef.queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moid"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setText("每秒事务数统计 ");
			jsonData.setLegend("每秒事务数统计");
		}else if(perf.equals("2")){
			jsonData.setText("活跃事物数统计 ");
			jsonData.setLegend("活跃事物数统计");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		try {
		List<PerfMSSQLDatabaseBean> perfList = msService.queryMSSQLDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if(perf.equals("1")){
					seriesSbf.append(perfList.get(i).getTransactions() + ",");
				}else if(perf.equals("2")){
					seriesSbf.append(perfList.get(i).getActiveTransactions() + ",");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...加载数据库曲线图表数据");
		return result;
	}
	/**
	 * Server 数统计页面跳转
	 * @return
	 */
	@RequestMapping("/toLogLineChart")
	@ResponseBody
	public ModelAndView toLogLineChart(HttpServletRequest request,ModelMap map) {
	
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/msDbManage/initLog");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}
	/**
	 * 数据库 日志 数趋势数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initLog")
	@ResponseBody
	public Map<String, Object> initLog(HttpServletRequest request)  {
		logger.info("开始...加载日志数趋势数据");
		Map<String, Object> params = new HashMap<String, Object>();
		params = MSSQLServerKPINameDef.queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moid"));
		String perf = request.getParameter("perfKind");
		//存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[");	
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer lineDataA = null ;
		StringBuffer lineDataB = null ;
		StringBuffer lineDataC = null ;
		 if(perf.equals("1")) {
			 lineDataA = new StringBuffer("{name:'日志增长次数',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'日志收缩次数',type:'line',data:[");
			 lineDataC = new StringBuffer("{name:'日志截断次数',type:'line',data:[");
			 jsonData.setText("日志统计");
			 jsonData.setLegend("日志增长次数,日志收缩次数,日志截断次数");
		}
		try {
			List<PerfMSSQLDatabaseBean> serverChartList = msService.queryMSSQLDatabasePerf(params);
			int size = serverChartList.size();
			
			if(serverChartList != null && size >0){
				for(int j=0; j<size; j++){
					xAxisSbf.append(serverChartList.get(j).getFormatTime()+",");
					 if(perf.equals("1")) {
						lineDataA.append(serverChartList.get(j).getLogGrowths()+",");
						lineDataB.append(serverChartList.get(j).getLogShrinks()+",");
						lineDataC.append(serverChartList.get(j).getLogTruncations()+",");
					}
				}			
				xAxisSbf.deleteCharAt(xAxisSbf.length()-1);
				lineDataA.deleteCharAt(lineDataA.length()-1);
				lineDataB.deleteCharAt(lineDataB.length()-1);
				lineDataC.deleteCharAt(lineDataC.length()-1);
				 if(perf.equals("1")) {
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
		logger.info("结束...日志 数趋势数据");
		return result;
	}
	/**
	 * Server 数统计页面跳转
	 * @return
	 */
	@RequestMapping("/toLogWaitLineChart")
	@ResponseBody
	public ModelAndView toLogWaitLineChart(HttpServletRequest request,ModelMap map) {
		Map<String,Object> params = new HashMap<String,Object>();
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("proUrl", "/monitor/msDbManage/initLogWait");
		params.put("moID", moId);
		params.put("kpiName", MSSQLServerKPINameDef.LOGFLUSHWAITTIME);
		PerfMSSQLDatabaseBean cache=msService.getLogPerfValue(params);
		if(cache!=null){
			map.put("logFlushWaitTime", cache.getPerfValue());
		}else{
		map.put("logFlushWaitTime", "");
		}
		return new ModelAndView("monitor/database/sqlserver/logLineChart");
	}
	/**
	 * 数据库 日志 数趋势数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initLogWait")
	@ResponseBody
	public Map<String, Object> initLogWait(HttpServletRequest request) {
		logger.info("开始...加载日志数趋势数据");
		Map<String, Object> params = new HashMap<String, Object>();
		params = MSSQLServerKPINameDef.queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moid"));
		String perf = request.getParameter("perfKind");
		//存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[");	
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer lineDataA = null ;
		StringBuffer lineDataB = null ;
		if (perf.equals("1")) {
			 lineDataA = new StringBuffer("{name:'每秒日志刷新数',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'每秒等待日志刷新的提交数',type:'line',data:[");
			 jsonData.setText("日志刷新数统计");
			 jsonData.setLegend("每秒日志刷新数,每秒等待日志刷新的提交数");
		}
		try {
			List<PerfMSSQLDatabaseBean> serverChartList = msService.queryMSSQLDatabasePerf(params);
			int size = serverChartList.size();
			
			if(serverChartList != null && size >0){
				for(int j=0; j<size; j++){
					xAxisSbf.append(serverChartList.get(j).getFormatTime()+",");
					if (perf.equals("1")) {
						lineDataA.append(serverChartList.get(j).getLogFlushes()+",");
						lineDataB.append(serverChartList.get(j).getLogFlushWaits()+",");	
					}
				}			
				xAxisSbf.deleteCharAt(xAxisSbf.length()-1);
				lineDataA.deleteCharAt(lineDataA.length()-1);
				lineDataB.deleteCharAt(lineDataB.length()-1);
				if (perf.equals("1")) {
					seriesSbf.append(lineDataA.toString()+"]},"+lineDataB.toString()+"]}]");
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
		logger.info("结束...日志 数趋势数据");
		return result;
	}
	/**
	 * 获取日志空间使用率
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findMsDatabaseLogUsage")
	@ResponseBody
	public Map<String, Object> findMsDatabaseLogUsage(
			HttpServletRequest request) {
		logger.info("日志空间使用率 ");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("moID", Integer.parseInt(request.getParameter("moID")));
		params.put("kpiName", MSSQLServerKPINameDef.LOGUSAGE);
		PerfMSSQLDatabaseBean cache=msService.getLogPerfValue(params);
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		if (cache != null) {
			map1.put("value", cache.getPerfValue());
			map1.put("name", "日志空间使用率");
			array1.add(map1);
			map2.put("msDBLogUsage", array1);
			map.put("data", map2);
		} else {
			map1.put("value", "");
			map1.put("name", "日志空间使用率");
			array1.add(map1);
			map2.put("msDBLogUsage", array1);
			map.put("data", map2);
		}
		return map;
	}
	
	/**
	 * 提供默认SybaseServerDB
	 */
	@RequestMapping("/initMsSqlDbName")
	@ResponseBody
	public MOMsSQLDatabaseBean initMsSqlDbName(HttpServletRequest request) {
		MOMsSQLDatabaseBean msDbBean = msService.getFirstMsDbBean();
		return msDbBean;
	}
	
	/**
	 * 提供默认SybaseServerDB
	 */
	@RequestMapping("/findMsSqlDbInfo")
	@ResponseBody
	public MOMsSQLDatabaseBean findMsSqlDbInfo(Integer moId) {
		MOMsSQLDatabaseBean msDbBean = msService.getMsDbById(moId);
		return msDbBean;
	}
	
	/**
	 * msSqlServer信息
	 */
	@RequestMapping("/findMsSqlServerInfo")
	@ResponseBody
	public MOMySQLDBServerBean findMsSqlServerInfo(int moId){
		return msService.findMsSqlServerInfo(moId);
	}
	
	/**
	 * msSql数据库设备信息
	 */
	@RequestMapping("/findMsDeviceInfo")
	@ResponseBody
	public MOMsSQLDeviceBean findMsDeviceInfo(int moId){
		return msService.findMsDeviceInfo(moId);
	}
	
	/**
	 * msSql数据库信息
	 */
	@RequestMapping("/findMsSQLDatabaseInfo")
	@ResponseBody
	public MOMsSQLDatabaseBean findMsSQLDatabaseInfo(int moId){
		return msService.findMsSQLDatabaseInfo(moId);
	}
}
