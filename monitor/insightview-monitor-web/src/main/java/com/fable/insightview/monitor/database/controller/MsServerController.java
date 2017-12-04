package com.fable.insightview.monitor.database.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.fable.insightview.monitor.database.entity.Db2InfoBean;
import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDeviceBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MSSQLServerKPINameDef;
import com.fable.insightview.monitor.database.entity.PerfMSSQLServerBean;
import com.fable.insightview.monitor.database.entity.PerfSybaseDatabaseBean;
import com.fable.insightview.monitor.database.service.IMsServerService;
import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.platform.common.util.JsonUtil;

@Controller
@RequestMapping("/monitor/msManage")
public class MsServerController {
	@Autowired
	IMsServerService msService;

	private final Logger logger = LoggerFactory.getLogger(MsServerController.class);
	
	DecimalFormat df = new DecimalFormat("#.##");
	@RequestMapping("/toShowServerDetail")
	@ResponseBody
	public ModelAndView toShowServerDetail(HttpServletRequest request, ModelMap map) {
		logger.info("加载服务详情页面");
		int MOID = Integer.parseInt(request.getParameter("moID"));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("serverMOID", MOID);
		params.put("moTableName", "MOMsSQLServer");
		params.put("msMID", KPINameDef.JOBMSSQLSERVERAVAILABLE);
		MODBMSServerBean ms = msService.getMsServerInfo(params);
		map.put("ms", ms);
		return new ModelAndView("monitor/database/sqlserver/msServerInfo");
	}
	@RequestMapping("/toShowDBDetail")
	@ResponseBody
	public ModelAndView toShowDBDetail(HttpServletRequest request, ModelMap map) {
		logger.info("加载数据库详情页面");
		int MOID = Integer.parseInt(request.getParameter("moID"));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("moID", MOID);
		params.put("dbTableName", "MOMsSQLDatabase");
		params.put("kpiName", MSSQLServerKPINameDef.LOGUSAGE);
		MOMsSQLDatabaseBean  ms=msService.getDBDetailInfo(params);
		map.put("ms", ms);
		return new ModelAndView("monitor/database/sqlserver/msDBDetailInfo");
	}
	@RequestMapping("/toShowDBPageDetail")
	@ResponseBody
	public ModelAndView toShowDBPageDetail(HttpServletRequest request, ModelMap map) {
		logger.info("加载数据库读写效率详情页面");
		int	moID = Integer.parseInt(request.getParameter("moID"));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("MOID", moID);
		params.put("CheckpointPages",MSSQLServerKPINameDef.CHECKPOINTPAGES);
		params.put("FreeListStalls", MSSQLServerKPINameDef.FREELISTSTALLS);
		params.put("LazyWrites", MSSQLServerKPINameDef.LAZYWRITES);
		params.put("PageReads", MSSQLServerKPINameDef.PAGEREADS);
		params.put("PageWrites", MSSQLServerKPINameDef.PAGEWRITES);
		params.put("PageLookups", MSSQLServerKPINameDef.PAGELOOKUPS);
		PerfMSSQLServerBean db = msService.getDatabaseDetail(params);
		map.put("db", db);
		return new ModelAndView("monitor/database/sqlserver/msDatabasePageInfo");
	}

	/**
	 * 获取Server 可用率
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findMsChartAvailability")
	@ResponseBody
	public Map<String, Object> findMsChartAvailability(
			HttpServletRequest request) throws Exception {
		logger.info("根据数据库实例ID获取图表可用性使用率");
		Map<String,Object> params = new HashMap<String,Object>();
		params = queryBetweenTime(request, params);
		params.put("MOID", Integer.parseInt(request.getParameter("moID")));
		MODBMSServerBean mo = msService.getMsChartAvailability(params);
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		if (mo != null) {
			map1.put("value", mo.getDeviceavailability());
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("msAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("msAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	/**
	 * 获取Server缓存命中率 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findMsChartBuffCacheHits")
	@ResponseBody
	public Map<String, Object> findMsChartBuffCacheHits(
			HttpServletRequest request) throws Exception {
		logger.info("根据数据库实例ID获取图表可用性使用率");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("moID", Integer.parseInt(request.getParameter("moID")));
		params.put("kpiName", MSSQLServerKPINameDef.BUFFCACHEHITS);
		PerfMSSQLServerBean cache=msService.getPerfValue(params);
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		if (cache != null) {
			map1.put("value", cache.getPerfValue());
			map1.put("name", "缓存命中率");
			array1.add(map1);
			map2.put("msBuffCacheHits", array1);
			map.put("data", map2);
		} else {
			map1.put("value", "");
			map1.put("name", "缓存命中率");
			array1.add(map1);
			map2.put("msBuffCacheHits", array1);
			map.put("data", map2);
		}
		return map;
	}
	/**
	 * server 缓存
	 */
	@RequestMapping("/findCachePie")
	@ResponseBody
	public Map<String, Object>  findCachePie(HttpServletRequest request,ModelMap map){
		logger.info("缓存使用情况");
		int	moID = Integer.parseInt(request.getParameter("moID"));
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		Map<String,Object> mapName = new HashMap<String,Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		params.put("moID", moID);
		String[] snapName = {"缓存空闲页数","缓存中有数据库内容的页数","缓存丢失页数"};
		String[] kpiName = {MSSQLServerKPINameDef.FREEPAGES,MSSQLServerKPINameDef.DATABASEPAGES,MSSQLServerKPINameDef.STOLENPAGES};
		
		for (int i = 0; i < snapName.length; i++) {
			params.put("kpiName", kpiName[i]);
			PerfMSSQLServerBean db = msService.getPerfValue(params);
			if (db != null) {
				String perfValue = HostComm.getKBytesToSize(db.getPerfValue());
				array1.add("{value:" + Double.parseDouble(perfValue) + ",name:'"
						+ snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			} else {
				array1.add("{value:'',name:'" + snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			}

		}
		
			map2.put("msCachePie", array1);
			mapName.put("pieName",array2);
			mapResult.put("data", map2);
			mapResult.put("dataName", mapName);
		 return mapResult;
	}

	/**
	 * 查询数据库列表
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toShowDBList")
	@ResponseBody
	public ModelAndView toShowDBList(HttpServletRequest request, ModelMap map) {
		logger.info("加载数据库列表页面");
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		request.setAttribute("type", "ms");
		return new ModelAndView("monitor/database/sqlserver/msDBInfoList");
	}
	/**
	 * 获取数据库列表
	 * @return
	 */
	@RequestMapping("/getDBInfoList")
	@ResponseBody
	public Map<String, Object> getDBInfoList(){
		logger.info("开始...加载数据库列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serverMOID", request.getParameter("moID"));
		params.put("dbTableName", "MOMsSQLDatabase");
		params.put("serverTableName", "MOMsSQLServer");
		params.put("kpiName", "LogUsage");
		List<MOMsSQLDatabaseBean> dbLst = msService.getDBListInfo(params);
		result.put("rows", dbLst);
		return result;
	}
	/**
	 * 查询数据库列表
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toShowDeviceList")
	@ResponseBody
	public ModelAndView toShowDeviceList(HttpServletRequest request, ModelMap map) {
		logger.info("加载设备页面");
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		request.setAttribute("type", "ms");
		return new ModelAndView("monitor/database/sqlserver/msDeviceInfoList");
	}
	/**
	 * 获取数据库列表
	 * @return
	 */
	@RequestMapping("/getServiceInfoList")
	@ResponseBody
	public Map<String, Object> getServiceInfoList(){
		logger.info("开始...加载数据库列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serverMOID", request.getParameter("moID"));
		params.put("deviceTableName", "MOMsSQLDevice");
		params.put("serverTableName", "MOMsSQLServer");
		List<MOMsSQLDeviceBean> dbLst = msService.getDeviceListInfo(params);
		result.put("rows", dbLst);
		return result;
	}
	/**
	 * 服务曲线页面跳转
	 * @return
	 */
	@RequestMapping("/toMsLineChart")
	@ResponseBody
	public ModelAndView toMsLineChart(HttpServletRequest request,ModelMap map){
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/msManage/initMs");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}
	/**
	 *  服务曲线图表数据
	 */
	@RequestMapping("/initMs")
	@ResponseBody
	public Map<String, Object> initMs(HttpServletRequest request) {
		logger.info("开始...加载服务曲线趋势图表数据");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moid"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		String perf = request.getParameter("perfKind");
		jsonData.setText("");
		if (perf.equals("1")) {
			jsonData.setText("缓存命中率趋势(%)");
			jsonData.setLegend("缓存命中率");
		}else if(perf.equals("2")){
			jsonData.setLegend("用户连接数统计");
		}else if(perf.equals("3")){
			jsonData.setLegend("高速缓存对象数统计");
		}else if(perf.equals("4")){
			jsonData.setLegend("高速缓存对象使用的页数统计");
		}else if(perf.equals("5")){
			jsonData.setLegend("锁请求平均等待时间统计(ms)");
		}else if(perf.equals("6")){
			jsonData.setLegend("闩锁请求平均等待时间统计(ms)");
		}else if(perf.equals("7")){
			jsonData.setLegend("每秒未能立即授予的闩锁请求数");
		}else if(perf.equals("8")){
			jsonData.setLegend("每秒表上的锁升级次数");
		}else if(perf.equals("9")){
			jsonData.setLegend("每秒创建的工作表数");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		try {
		List<PerfMSSQLServerBean> perfList = msService
				.queryMSSQLServerPerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if(perf.equals("1")){
					seriesSbf.append(perfList.get(i).getBuffCacheHits() + ",");
				}else if(perf.equals("2")){
					seriesSbf.append(perfList.get(i).getUserConnections() + ",");
				}else if(perf.equals("3")){
					seriesSbf.append(perfList.get(i).getCacheObjects() + ",");
				}else if(perf.equals("4")){
					seriesSbf.append(perfList.get(i).getCachePages() + ",");
				}else if(perf.equals("5")){
					seriesSbf.append(perfList.get(i).getAvgWaitTime() + ",");
				}else if(perf.equals("6")){
					seriesSbf.append(perfList.get(i).getAvgLatchWaitTime()+ ",");
				}else if(perf.equals("7")){
					seriesSbf.append(perfList.get(i).getLatchWaits() + ",");
				}else if(perf.equals("8")){
					seriesSbf.append(perfList.get(i).getTableLockEscalations() + ",");
				}else if(perf.equals("9")){
					seriesSbf.append(perfList.get(i).getWorktablesCreated() + ",");
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
		logger.info("结束...加载服务曲线图表数据");
		return result;
	}
	
	/**
	 * Server 数统计页面跳转
	 * @return
	 */
	@RequestMapping("/toServerLineChart")
	@ResponseBody
	public ModelAndView toServerLineChart(HttpServletRequest request,ModelMap map){
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/msManage/initServer");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}
	/**
	 * Server 数趋势数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initServer")
	@ResponseBody
	public Map<String, Object> initServer(HttpServletRequest request){
		logger.info("开始...加载Server数趋势数据");
		Map<String, Object> params = new HashMap<String, Object>();
		params = queryBetweenTime(request, params);
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
		StringBuffer lineDataD = null ;
		StringBuffer lineDataE = null ;
		StringBuffer lineDataF = null ;
		if (perf.equals("1")) {
			 lineDataA = new StringBuffer("{name:'每秒收到的批SQL命令数',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'每秒SQL编译数',type:'line',data:[");
			 lineDataC = new StringBuffer("{name:'每秒SQL重编译数',type:'line',data:[");
			 jsonData.setText("SQL执行情况统计");
			 jsonData.setLegend("每秒收到的批SQL命令数,每秒SQL编译数,每秒SQL重编译数");
		}else if(perf.equals("2")) {
			 lineDataA = new StringBuffer("{name:'每秒完全扫描数',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'每秒通过索引进行的限定范围的扫描数',type:'line',data:[");
			 jsonData.setText("数据库扫描情况统计");
			 jsonData.setLegend("每秒完全扫描数,每秒通过索引进行的限定范围的扫描数");
		}else if (perf.equals("3")) {
			 lineDataA = new StringBuffer("{name:'缓存空闲页数',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'缓存中有数据库内容的页数',type:'line',data:[");
			 lineDataC = new StringBuffer("{name:'每秒刷新到磁盘的页数',type:'line',data:[");
			 lineDataD = new StringBuffer("{name:'缓存总页数',type:'line',data:[");
			 jsonData.setText("缓存页数统计");
			 jsonData.setLegend("缓存空闲页数,缓存中有数据库内容的页数,每秒刷新到磁盘的页数,缓存总页数");
		}else if (perf.equals("4")) {
			 lineDataA = new StringBuffer("{name:'总内存',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'用来维护连接的内存',type:'line',data:[");
			 lineDataC = new StringBuffer("{name:'用于锁的内存',type:'line',data:[");
			 lineDataD = new StringBuffer("{name:'用于查询优化的内存',type:'line',data:[");
			 lineDataE = new StringBuffer("{name:'用于SQL高速缓存的内存',type:'line',data:[");
			 lineDataF = new StringBuffer("{name:'执行排序等操作的内存',type:'line',data:[");
			 jsonData.setText("内存使用情况统计(MB)");
			 jsonData.setLegend("总内存,用来维护连接的内存,用于锁的内存,用于查询优化的内存,用于SQL高速缓存的内存,执行排序等操作的内存");
		}else if(perf.equals("5")) {
			 lineDataA = new StringBuffer("{name:'每秒登录数',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'每秒注销数',type:'line',data:[");
			 jsonData.setText("登录注销情况统计");
			 jsonData.setLegend("每秒登录数,每秒注销数");
		}else if (perf.equals("6")) {
			 lineDataA = new StringBuffer("{name:'每秒锁请求数',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'每秒超时的锁请求数',type:'line',data:[");
			 lineDataC = new StringBuffer("{name:'每秒要求调用者等待的锁请求数',type:'line',data:[");
			 lineDataD = new StringBuffer("{name:'每秒导致死锁的锁请求数',type:'line',data:[");
			 jsonData.setText("锁请求数统计");
			 jsonData.setLegend("每秒锁请求数,每秒超时的锁请求数,每秒要求调用者等待的锁请求数,每秒导致死锁的锁请求数");
		}else if(perf.equals("7")) {
			 lineDataA = new StringBuffer("{name:'每秒的自动参数化尝试数',type:'line',data:[");
			 lineDataB = new StringBuffer("{name:'每秒自动参数化尝试失败次数',type:'line',data:[");
			 jsonData.setText("自动参数尝试统计");
			 jsonData.setLegend("每秒的自动参数化尝试数,每秒自动参数化尝试失败次数");
		}
		try {
			List<PerfMSSQLServerBean> serverChartList = msService.queryMSSQLServerPerf(params);
			int size = serverChartList.size();
			
			if(serverChartList != null && size >0){
				for(int j=0; j<size; j++){
					xAxisSbf.append(serverChartList.get(j).getFormatTime()+",");
					if (perf.equals("1")) {
						lineDataA.append(serverChartList.get(j).getBatchRequests()+",");
						lineDataB.append(serverChartList.get(j).getSqlCompilations()+",");	
						lineDataC.append(serverChartList.get(j).getSqlReCompilations()+",");
					}else if(perf.equals("2")) {
						lineDataA.append(serverChartList.get(j).getFullScans()+",");
						lineDataB.append(serverChartList.get(j).getRangeScans()+",");	
					}else if(perf.equals("3")) {
						lineDataA.append(serverChartList.get(j).getFreePages()+",");
						lineDataB.append(serverChartList.get(j).getDatabasePages()+",");
						lineDataC.append(serverChartList.get(j).getCheckpointPages()+",");
						lineDataD.append(serverChartList.get(j).getTotalPages()+",");
					}else if(perf.equals("4")) {
						lineDataA.append(df.format(serverChartList.get(j).getTotalServMemory()/1024/1024)+",");
						lineDataB.append(df.format(serverChartList.get(j).getConnectionMemory()/1024/1024)+",");
						lineDataC.append(df.format(serverChartList.get(j).getLockMemory()/1024/1024)+",");
						lineDataD.append(df.format(serverChartList.get(j).getOptimizerMemory()/1024/1024)+",");
						lineDataE.append(df.format(serverChartList.get(j).getSqlCacheMemory()/1024/1024)+",");
						lineDataF.append(df.format(serverChartList.get(j).getGrantedMemory()/1024/1024)+",");
					}else if(perf.equals("5")) {
						lineDataA.append(serverChartList.get(j).getLogins()+",");
						lineDataB.append(serverChartList.get(j).getLogouts()+",");	
					}else if(perf.equals("6")) {
						lineDataA.append(serverChartList.get(j).getLockRequests()+",");
						lineDataB.append(serverChartList.get(j).getLockTimeouts()+",");
						lineDataC.append(serverChartList.get(j).getLockWaits()+",");
						lineDataD.append(serverChartList.get(j).getDeadLocks()+",");
					}else if(perf.equals("7")) {
						lineDataA.append(serverChartList.get(j).getAutoParamAttempts()+",");
						lineDataB.append(serverChartList.get(j).getFailedAutoParams()+",");	
					}
				}			
				xAxisSbf.deleteCharAt(xAxisSbf.length()-1);
				lineDataA.deleteCharAt(lineDataA.length()-1);
				lineDataB.deleteCharAt(lineDataB.length()-1);
				if (perf.equals("1")) {
					lineDataC.deleteCharAt(lineDataC.length()-1);
					seriesSbf.append(lineDataA.toString()+"]},"+lineDataB.toString()+"]},"+lineDataC.toString()+"]}]");
				}else if(perf.equals("2") || perf.equals("5")|| perf.equals("7")) {
					seriesSbf.append(lineDataA.toString()+"]},"+lineDataB.toString()+"]}]");
				}else if(perf.equals("3")|| perf.equals("6")) {
					lineDataC.deleteCharAt(lineDataC.length()-1);
					lineDataD.deleteCharAt(lineDataD.length()-1);
					seriesSbf.append(lineDataA.toString()+"]},"+lineDataB.toString()+"]},"+lineDataC.toString()+"]},"+lineDataD.toString()+"]}]");
				}else if(perf.equals("4")) {
					lineDataC.deleteCharAt(lineDataC.length()-1);
					lineDataD.deleteCharAt(lineDataD.length()-1);
					lineDataE.deleteCharAt(lineDataE.length()-1);
					lineDataF.deleteCharAt(lineDataF.length()-1);
					seriesSbf.append(lineDataA.toString()+"]},"+lineDataB.toString()+"]},"+lineDataC.toString()+"]},"+lineDataD.toString()+"]},"+lineDataE.toString()+"]},"+lineDataF.toString()+"]}]");
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
		logger.info("结束...Server 数趋势数据");
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
	 * 提供默认MsServer
	 */
	@RequestMapping("/initMsServerName")
	@ResponseBody
	public MOMySQLDBServerBean initMsServerName(HttpServletRequest request) {
		MOMySQLDBServerBean msBean = msService.getFirstBean();
		return msBean;
	}
	
	/**
	 * 提供默认MsServer
	 */
	@RequestMapping("/findMsServerInfo")
	@ResponseBody
	public MOMySQLDBServerBean findMsServerInfo(Integer moId) {
		MOMySQLDBServerBean msBean = msService.getMsServerById(moId);
		return msBean;
	}
}
