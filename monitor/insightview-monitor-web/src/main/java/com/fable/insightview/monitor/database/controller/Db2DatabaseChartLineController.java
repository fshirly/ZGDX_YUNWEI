package com.fable.insightview.monitor.database.controller;

import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.database.entity.PerfDB2DatabaseBean;
import com.fable.insightview.monitor.database.service.IDb2Service;
import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.platform.common.util.JsonUtil;

@Controller
@RequestMapping("/monitor/db2DatabaseLineManage")
public class Db2DatabaseChartLineController {
	
	@Autowired
	IDb2Service db2Service;
	private final Logger logger = LoggerFactory.getLogger(Db2DatabaseChartLineController.class);
	
	/**
	 * 获取排序溢出率趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toSpilledUsageLineChart")
	public ModelAndView toSpilledUsageLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2DatabaseLineManage/getSpilledUsage");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 排序溢出率数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSpilledUsage")
	@ResponseBody
	public Map<String, Object> getSpilledUsage(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载排序溢出率图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("排序溢出率(%)");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2DatabaseBean> perfList = db2Service.queryDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				seriesSbf.append(perfList.get(i).getSpilledUsage() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String db2SpilledUsageData = JsonUtil.toString(jsonData);
		logger.info("db2SpilledUsageData==" + db2SpilledUsageData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2SpilledUsageData);
		logger.info("结束...加载排序溢出率趋势图表数据");
		
		return result;
	}
	
	/**
	 * 获取日志利用率趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toLogUsageLineChart")
	public ModelAndView toLogUsageLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2DatabaseLineManage/getLogUsage");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 日志利用率数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getLogUsage")
	@ResponseBody
	public Map<String, Object> getLogUsage(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载日志利用率图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("日志利用率(%)");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2DatabaseBean> perfList = db2Service.queryDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				seriesSbf.append(perfList.get(i).getLogUsage() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String db2LogUsageData = JsonUtil.toString(jsonData);
		logger.info("db2LogUsageData==" + db2LogUsageData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2LogUsageData);
		logger.info("结束...加载日志利用率趋势图表数据");
		
		return result;
	}
	
	/**
	 * 获取共享排序内存利用率趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toShareMemUsageLineChart")
	public ModelAndView toShareMemUsageLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2DatabaseLineManage/getShareMemUsage");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 共享排序内存利用率数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getShareMemUsage")
	@ResponseBody
	public Map<String, Object> getShareMemUsage(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载共享排序内存利用率图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("共享排序内存利用率(%)");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2DatabaseBean> perfList = db2Service.queryDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				seriesSbf.append(perfList.get(i).getShareMemUsage() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String db2ShareMemUsageData = JsonUtil.toString(jsonData);
		logger.info("db2ShareMemUsageData==" + db2ShareMemUsageData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2ShareMemUsageData);
		logger.info("结束...加载共享排序内存利用率图表数据");
		
		return result;
	}
	
	
	/**
	 * 获取等待锁定的应用程序比率趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toWaitingLockUsageLineChart")
	public ModelAndView toWaitingLockUsageLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2DatabaseLineManage/getWaitingLockUsage");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 等待锁定的应用程序比率数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getWaitingLockUsage")
	@ResponseBody
	public Map<String, Object> getWaitingLockUsage(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载等待锁定的应用程序比率图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("等待锁定的应用程序比率(%)");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2DatabaseBean> perfList = db2Service.queryDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				seriesSbf.append(perfList.get(i).getWaitingLockUsage() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String db2WaitingLockUsageData = JsonUtil.toString(jsonData);
		logger.info("db2WaitingLockUsageData==" + db2WaitingLockUsageData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2WaitingLockUsageData);
		logger.info("结束...加载等待锁定的应用程序比率图表数据");
		
		return result;
	}
	
	
	/**
	 * 获取程序包高速缓存命中率趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toPkgCacheHitsLineChart")
	public ModelAndView toPkgCacheHitsLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2DatabaseLineManage/getPkgCacheHits");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 程序包高速缓存命中率数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getPkgCacheHits")
	@ResponseBody
	public Map<String, Object> getPkgCacheHits(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载程序包高速缓存命中率图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("程序包高速缓存命中率(%)");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2DatabaseBean> perfList = db2Service.queryDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				seriesSbf.append(perfList.get(i).getPkgCacheHits() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String db2PkgCacheHitsData = JsonUtil.toString(jsonData);
		logger.info("db2PkgCacheHitsData==" + db2PkgCacheHitsData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2PkgCacheHitsData);
		logger.info("结束...加载程序包高速缓存命中率图表数据");
		
		return result;
	}
	
	/**
	 * 获取目录高速缓存命中率趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toCatCacheHitsLineChart")
	public ModelAndView toCatCacheHitsLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2DatabaseLineManage/getCatCacheHits");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 目录高速缓存命中率数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getCatCacheHits")
	@ResponseBody
	public Map<String, Object> getCatCacheHits(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载目录高速缓存命中率图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("目录高速缓存命中率(%)");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2DatabaseBean> perfList = db2Service.queryDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				seriesSbf.append(perfList.get(i).getCatCacheHits() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String db2CatCacheHitsData = JsonUtil.toString(jsonData);
		logger.info("db2CatCacheHitsData==" + db2CatCacheHitsData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2CatCacheHitsData);
		logger.info("结束...加载目录高速缓存命中率图表数据");
		
		return result;
	}
	
	/**
	 * 获取 死锁数趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toDeadLocksLineChart")
	public ModelAndView toDeadLocksLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2DatabaseLineManage/getDeadLocks");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 死锁数数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getDeadLocks")
	@ResponseBody
	public Map<String, Object> getDeadLocks(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载死锁数图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("死锁数");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2DatabaseBean> perfList = db2Service.queryDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				seriesSbf.append(perfList.get(i).getDeadLocks() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String db2DeadLocksData = JsonUtil.toString(jsonData);
		logger.info("db2DeadLocksData==" + db2DeadLocksData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2DeadLocksData);
		logger.info("结束...加载死锁数图表数据");
		
		return result;
	}
	
	/**
	 * 获取锁定升级次数趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toLockEscalsLineChart")
	public ModelAndView toLockEscalsLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2DatabaseLineManage/getLockEscals");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 锁定升级次数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getLockEscals")
	@ResponseBody
	public Map<String, Object> getLockEscals(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载锁定升级次数图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("锁定升级次数");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2DatabaseBean> perfList = db2Service.queryDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				seriesSbf.append(perfList.get(i).getLockEscals() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String db2LockEscalsData = JsonUtil.toString(jsonData);
		logger.info("db2LockEscalsData==" + db2LockEscalsData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2LockEscalsData);
		logger.info("结束...加载锁定升级次数图表数据");
		
		return result;
	}
	
	/**
	 * 获取成功执行的 SQL 语句数趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toSuccSqlsLineChart")
	public ModelAndView toSuccSqlsLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2DatabaseLineManage/geSuccSqls");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 成功执行的 SQL 语句数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSuccSqls")
	@ResponseBody
	public Map<String, Object> getSuccSqls(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载成功执行的 SQL 语句数图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("成功执行的 SQL 语句数");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2DatabaseBean> perfList = db2Service.queryDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				seriesSbf.append(perfList.get(i).getSuccSqls() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String db2SuccSqlsData = JsonUtil.toString(jsonData);
		logger.info("db2SuccSqlsData==" + db2SuccSqlsData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2SuccSqlsData);
		logger.info("结束...加载成功执行的 SQL 语句数图表数据");
		
		return result;
	}
	
	/**
	 * 获取执行失败的SQL语句数趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toFailedSqlsLineChart")
	public ModelAndView toFailedSqlsLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2DatabaseLineManage/geFailedSqls");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 执行失败的SQL语句数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getFailedSqls")
	@ResponseBody
	public Map<String, Object> getFailedSqls(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载执行失败的SQL语句数图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("执行失败的SQL语句数");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2DatabaseBean> perfList = db2Service.queryDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				seriesSbf.append(perfList.get(i).getFailedSqls() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String db2FailedSqlsData = JsonUtil.toString(jsonData);
		logger.info("db2FailedSqlsData==" + db2FailedSqlsData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2FailedSqlsData);
		logger.info("结束...加载执行失败的SQL语句数图表数据");
		
		return result;
	}
	
	/**
	 * 获取工作单元总数趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toWorkUnitsLineChart")
	public ModelAndView toWorkUnitsLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2DatabaseLineManage/geWorkUnits");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 工作单元总数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getWorkUnits")
	@ResponseBody
	public Map<String, Object> getWorkUnits(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载工作单元总数图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("工作单元总数");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2DatabaseBean> perfList = db2Service.queryDatabasePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				seriesSbf.append(perfList.get(i).getWorkUnits() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String db2WorkUnitsData = JsonUtil.toString(jsonData);
		logger.info("db2WorkUnitsData==" + db2WorkUnitsData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2WorkUnitsData);
		logger.info("结束...加载工作单元总数图表数据");
		
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
		String seltDate = request.getParameter("seltDate");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		String timeEnd = f.format(d.getTime());
		String timeBegin = "";
		if(seltDate != null){
			if (seltDate.endsWith("0")) {// 最近1小时
				d.add(Calendar.HOUR, -1);
			} else if (seltDate.endsWith("1")) {// 最近一天
				d.add(Calendar.DATE, -1);
			} else if (seltDate.endsWith("2")) {// 最近一周
				d.add(Calendar.DATE, -7);
			} else if (seltDate.endsWith("3")) {// 最近一月
				d.add(Calendar.MONTH, -1);
			} else if (seltDate.endsWith("4")) {// 最近三个月
				d.add(Calendar.MONTH, -3);
			} else if (seltDate.endsWith("5")) {// 最近半年
				d.add(Calendar.MONTH, -6);
			} else if (seltDate.endsWith("6")) {// 最近一年
				d.add(Calendar.YEAR, -1);
			}
			timeBegin = f.format(d.getTime());
		}
		
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
}
