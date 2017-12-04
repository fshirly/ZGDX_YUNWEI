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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fable.insightview.monitor.database.entity.DB2KPINameDef;
import com.fable.insightview.monitor.database.entity.PerfDB2DatabaseBean;
import com.fable.insightview.monitor.database.service.IDb2Service;

@Controller
@RequestMapping("/monitor/db2DatabaseBarManage")
public class Db2DatabaseChartBarController {

	@Autowired
	IDb2Service db2Service;
	private final Logger logger = LoggerFactory.getLogger(Db2DatabaseChartBarController.class);

	/**
	 * 获取排序溢出率柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartSpilledUsage")
	@ResponseBody
	public Map findBarChartSpilledUsage() {

		logger.info("开始...加载排序溢出率数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.SPILLEDUSAGE);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2DatabaseBean> moLsinfo = db2Service
					.queryDbPerfByInstanceMoId(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getDatabaseName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				map1.put("SpilledUsage", array1);
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("SpilledUsage", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取日志利用率柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartLogUsage")
	@ResponseBody
	public Map findBarChartLogUsage() {

		logger.info("开始...加载日志利用率数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.LOGUSAGE);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2DatabaseBean> moLsinfo = db2Service
					.queryDbPerfByInstanceMoId(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getDatabaseName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				map1.put("LogUsage", array1);
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("LogUsage", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取共享排序内存利用率柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartShareMemUsage")
	@ResponseBody
	public Map findBarChartShareMemUsage() {

		logger.info("开始...加载共享排序内存利用率数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.SHAREMEMUSAGE);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2DatabaseBean> moLsinfo = db2Service
					.queryDbPerfByInstanceMoId(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getDatabaseName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				map1.put("ShareMemUsage", array1);
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("ShareMemUsage", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取等待锁定的应用程序比率柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartWaitingLockUsage")
	@ResponseBody
	public Map findBarChartWaitingLockUsage() {

		logger.info("开始...加载等待锁定的应用程序比率数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.WAITINGLOCKUSAGE);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2DatabaseBean> moLsinfo = db2Service
					.queryDbPerfByInstanceMoId(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getDatabaseName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				map1.put("WaitingLockUsage", array1);
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("WaitingLockUsage", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取程序包高速缓存命中率柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartPkgCacheHits")
	@ResponseBody
	public Map findBarChartPkgCacheHits() {

		logger.info("开始...加载程序包高速缓存命中率数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.PKGCACHEHITS);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2DatabaseBean> moLsinfo = db2Service
					.queryDbPerfByInstanceMoId(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getDatabaseName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				map1.put("PkgCacheHits", array1);
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("PkgCacheHits", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取目录高速缓存命中率柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartCatCacheHits")
	@ResponseBody
	public Map findBarChartCatCacheHits() {

		logger.info("开始...加载目录高速缓存命中率数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.CATCACHEHITS);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2DatabaseBean> moLsinfo = db2Service
					.queryDbPerfByInstanceMoId(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getDatabaseName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				map1.put("CatCacheHits", array1);
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("CatCacheHits", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取 死锁数柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartDeadLocks")
	@ResponseBody
	public Map findBarChartDeadLocks() {

		logger.info("开始...加载 死锁数数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.DEADLOCKS);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2DatabaseBean> moLsinfo = db2Service
					.queryDbPerfByInstanceMoId(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getDatabaseName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				map1.put("DeadLocks", array1);
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("DeadLocks", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取锁定升级次数柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartLockEscals")
	@ResponseBody
	public Map findBarChartLockEscals() {

		logger.info("开始...加载锁定升级次数数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.LOCKESCALS);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2DatabaseBean> moLsinfo = db2Service
					.queryDbPerfByInstanceMoId(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getDatabaseName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				map1.put("LockEscals", array1);
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("LockEscals", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取成功执行的 SQL 语句数柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartSuccSqls")
	@ResponseBody
	public Map findBarChartSuccSqls() {

		logger.info("开始...加载成功执行的 SQL 语句数数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.SUCCSQLS);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2DatabaseBean> moLsinfo = db2Service
					.queryDbPerfByInstanceMoId(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getDatabaseName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				map1.put("SuccSqls", array1);
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("SuccSqls", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取执行失败的SQL语句数柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartFailedSqls")
	@ResponseBody
	public Map findBarChartFailedSqls() {

		logger.info("开始...加载执行失败的SQL语句数数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.FAILEDSQLS);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2DatabaseBean> moLsinfo = db2Service
					.queryDbPerfByInstanceMoId(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getDatabaseName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				map1.put("FailedSqls", array1);
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("FailedSqls", array1);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取工作单元总数柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarChartWorkUnits")
	@ResponseBody
	public Map findBarChartWorkUnits() {

		logger.info("开始...加载工作单元总数数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map params = new HashMap();
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.WORKUNITS);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2DatabaseBean> moLsinfo = db2Service
					.queryDbPerfByInstanceMoId(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getDatabaseName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				map1.put("WorkUnits", array1);
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				map1.put("WorkUnits", array1);
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
	 * 
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
		String timeBegin = f.format(d.getTime());
		if (seltDate != null) {
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

		if (time != null) {
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
			} else if ("Week".equals(time)) {// 本周
				if (d.get(Calendar.DAY_OF_WEEK) == 1) {
					d.add(Calendar.DAY_OF_WEEK,
							-(d.get(Calendar.DAY_OF_WEEK) + 5));
				} else {
					d.add(Calendar.DAY_OF_WEEK,
							-(d.get(Calendar.DAY_OF_WEEK) - 2));
				}

				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
			} else if ("Month".equals(time)) {// 本月
				d.add(Calendar.MONTH, 0);
				// 设置为1号,当前日期既为本月第一天
				d.set(Calendar.DAY_OF_MONTH, 1);
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
			} else if ("LastMonth".equals(time)) {// 上月
				d.add(Calendar.MONTH, -1);
				d.set(Calendar.DAY_OF_MONTH, 1);// 上月第一天
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
				Calendar cale = Calendar.getInstance();
				// 设置为1号,当前日期既为本月第一天
				cale.set(Calendar.DAY_OF_MONTH, 0);
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
