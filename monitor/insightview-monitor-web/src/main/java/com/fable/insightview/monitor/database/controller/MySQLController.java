package com.fable.insightview.monitor.database.controller;

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

import com.fable.insightview.monitor.database.entity.MOMySQLDBBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLVarsBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLConnectionBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLQueryCacheBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLTableCacheBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLTableLockBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLTmpTableBean;
import com.fable.insightview.monitor.database.service.IMyService;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/myManage")
public class MySQLController {

	@Autowired
	IMyService myService;
	
	@Autowired
	IOracleService orclService;

	private final Logger logger = LoggerFactory
			.getLogger(MySQLController.class);

	/**
	 * 数据库服务信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toShowServerInfo")
	@ResponseBody
	public ModelAndView toShowServerInfo(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载数据库服务详情页面");
		int MOID = Integer.parseInt(request.getParameter("moID"));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("MOID", MOID);
		params.put("mysqlMID", KPINameDef.JOBMYSQLAVAILABLE);
		MOMySQLDBServerBean my = myService.getMyServerInfo(params);
		map.put("my", my);
		map.put("flag", request.getParameter("flag"));
		return new ModelAndView("monitor/database/mysql/myServerInfo");
	}

	/**
	 * 跳转至数据库列表页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toShowMyDBInfo")
	public ModelAndView toShowMyDBInfo(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/database/mysql/myDBInfoList");
	}

	/**
	 * 加载数据库列表列表页面数据
	 * 
	 * @param taskBean
	 * @throws Exception
	 */
	@RequestMapping("/getMyDBInfo")
	@ResponseBody
	public Map<String, Object> getMyDBInfo() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		List<MOMySQLDBBean> dbLst = myService.getMyDB(Integer.parseInt(request
				.getParameter("moID")));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", dbLst);
		return result;
	}

	/**
	 * 跳转数据库可用率
	 * @return
	 */
	@RequestMapping("/toShowMyAvailability")
	public ModelAndView toShowMyAvailability(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/database/mysql/myAvailabilityChart");
	}

	/**
	 * 获取数据库可用率
	 * 
	 * @param deviceIP
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findMyChartAvailability")
	@ResponseBody
	public Map<String, Object> findMyChartAvailability(
			HttpServletRequest request) throws Exception {
		logger.info("根据数据库实例ID获取图表可用性使用率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", Integer.parseInt(request.getParameter("moID")));
		MOMySQLDBServerBean mo = myService.getMyChartAvailability(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (mo != null) {
			map1.put("value", mo.getDeviceAvailability());
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("mysqlAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("mysqlAvailability", array1);
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
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		return params;
	}

	/**
	 * 数据库连接图表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toMyConnectionLineChart")
	public ModelAndView toMyConnectionLineChart(HttpServletRequest request,

	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/myManage/initMyConnection");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}

	/**
	 * * 数据库连接图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initMyConnection")
	@ResponseBody
	public Map<String, Object> initMyConnection(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载数据库连接图表数据");
		Map params = new HashMap();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("seltItem", request.getParameter("moid"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText(" ");//MySQL 数据库连接统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("数据库连接利用率趋势图(%)");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfMySQLConnectionBean> perfList = myService
				.queryMyConnectionPerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if (perf.equals("1")) {
					seriesSbf.append(perfList.get(i).getConnUsage() + ",");
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
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("oracleChartData", oracleChartData);
		logger.info("结束...加载数据库连接图表数据");
		return result;
	}

	/**
	 * 临时表图表页面跳转
	 * @return
	 */
	@RequestMapping("/toMyTmpLineChart")
	public ModelAndView toMyTmpLineChart(HttpServletRequest request,
			ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/myManage/initMyTmp");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}

	/**
	 * * 临时表图表数据
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initMyTmp")
	@ResponseBody
	public Map<String, Object> initMyTmp(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载临时表图表数据");
		Map params = new HashMap();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("seltItem", request.getParameter("moid"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText(" "); //MySQL 临时表统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("临时表使用率趋势图(%)");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfMySQLTmpTableBean> perfList = myService.queryMyTmpPerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if (perf.equals("1")) {
					seriesSbf.append(perfList.get(i).getTmpUsage() + ",");
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
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("oracleChartData", oracleChartData);
		logger.info("结束...加载临时表图表数据");
		return result;
	}

	/**
	 * 表缓存图表页面跳转
	 * @return
	 */
	@RequestMapping("/toMyTableCacheLineChart")
	public ModelAndView toMyTableCacheLineChart(HttpServletRequest request,
			ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/myManage/initMyTableCache");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}

	/**
	 * * 表缓存图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initMyTableCache")
	@ResponseBody
	public Map<String, Object> initMyTableCache(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载表缓存图表数据");
		Map params = new HashMap();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("seltItem", request.getParameter("moid"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText(" ");//MySQL 表缓存统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("表缓存命中率趋势图(%)");
		} else if (perf.equals("2")) {
			jsonData.setLegend("表缓存利用率趋势图(%)");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfMySQLTableCacheBean> perfList = myService
				.queryMyTableCachePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if (perf.equals("1")) {
					seriesSbf.append(perfList.get(i).getTableCacheHits() + ",");
				} else if (perf.equals("2")) {
					seriesSbf
							.append(perfList.get(i).getTableCacheUsage() + ",");
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
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("oracleChartData", oracleChartData);
		logger.info("结束...加载表缓存图表数据");
		return result;
	}

	/**
	 * 查询缓存图表页面跳转
	 * @return
	 */
	@RequestMapping("/toMyQueryCacheLineChart")
	public ModelAndView toMyQueryCacheLineChart(HttpServletRequest request,
			ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/myManage/initMyQueryCache");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}

	/**
	 * * 查询缓存图表数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initMyQueryCache")
	@ResponseBody
	public Map<String, Object> initMyQueryCache(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载查询缓存图表数据");
		Map params = new HashMap();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("seltItem", request.getParameter("moid"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText(" ");//MySQL 查询缓存统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("查询缓存碎片率趋势图(%)");
		} else if (perf.equals("2")) {
			jsonData.setLegend("查询缓存利用率趋势图(%)");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfMySQLQueryCacheBean> perfList = myService
				.queryMyQueryCachePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if (perf.equals("1")) {
					seriesSbf.append(perfList.get(i).getFragMentationRate()
							+ ",");
				} else if (perf.equals("2")) {
					seriesSbf.append(perfList.get(i).getQcacheUsage() + ",");
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
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("oracleChartData", oracleChartData);
		logger.info("结束...加载查询缓存图表数据");
		return result;
	}

	/**
	 * 表锁图表页面跳转
	 * @return
	 */
	@RequestMapping("/toMyTableLockLineChart")
	public ModelAndView toMyTableLockLineChart(HttpServletRequest request,
			ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/myManage/initMyTableLock");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}

	/**
	 * * 表锁图表数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initMyTableLock")
	@ResponseBody
	public Map<String, Object> initMyTableLock(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载表锁图表数据");
		Map params = new HashMap();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("seltItem", request.getParameter("moid"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText(" ");//MySQL 表锁统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("表锁命中率趋势图(%)");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfMySQLTableLockBean> perfList = myService
				.queryMyTableLockPerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if (perf.equals("1")) {
					seriesSbf.append(perfList.get(i).getTableLockHits() + ",");
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
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("oracleChartData", oracleChartData);
		logger.info("结束...加载表锁图表数据");
		return result;
	}

	/**
	 * 访问系统变量页面
	 * 
	 * @return
	 */
	@RequestMapping("/toMySQLVarsList")
	public ModelAndView toMySQLVarsList(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		logger.info("进入页面myVars_list.jsp");
		return new ModelAndView("monitor/database/mysql/myVars_list");

	}

	/**
	 * 加载系统变量列表
	 * 
	 * @param mo
	 * @return
	 */
	@RequestMapping("/findMySQLVarsList")
	@ResponseBody
	public Map<String, Object> findMySQLVarsList(HttpServletRequest request) {
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMySQLVarsBean> page = new Page<MOMySQLVarsBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String varName=request.getParameter("varName");
		String txtValue=request.getParameter("txtValue");
		paramMap.put("MOID", request.getParameter("moID"));
		paramMap.put("varClass", request.getParameter("varClass"));
		paramMap.put(varName,txtValue );
		page.setParams(paramMap);
		List<MOMySQLVarsBean> varsList = new ArrayList<MOMySQLVarsBean>();
		String opera = request.getParameter("opera");
		if ("%".equals(opera) || "".equals(opera) || opera == null) {
			varsList = myService.getMyVarsInfoByLike(page);
		} else if ("=".equals(opera)) {
			varsList = myService.getMyVarsInfoByEq(page);
		}
		int total = page.getTotalRecord();
		logger.info("*******total:" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", varsList);
		return result;
	}
	/**
	 * 跳转至告警列表页
	 * 
	 * @return
	 */
	@RequestMapping("/toShowMySqlAlarmInfo")
	public ModelAndView toShowMySqlAlarmInfo(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/database/mysql/mySqlAlarmInfo");
	}

	/**
	 * Mysql for 最近告警
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getMySqlAlarmInfo")
	@ResponseBody
	public Map<String, Object> getMySqlAlarmInfo() throws Exception {
		logger.info("开始...加载Mysql信息列表 for 最近告警");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		int moID=myService.getInsIdBymoId(Integer.parseInt(request.getParameter("moID")));
		params.put("MOID", moID);
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

		logger.info("结束...加载Mysql信息列表 for 最近告警");
		return result;
	}
}
