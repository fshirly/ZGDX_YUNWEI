package com.fable.insightview.monitor.database.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.fable.insightview.monitor.database.entity.DB2KPINameDef;
import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.PerfDB2TbsBean;
import com.fable.insightview.monitor.database.entity.PerfSnapshotDatabaseBean;
import com.fable.insightview.monitor.database.entity.PerfDB2BufferPoolBean;
import com.fable.insightview.monitor.database.service.IDb2Service;

@Controller
@RequestMapping("/monitor/db2GaugeChartManage")
public class Db2GaugeChartController {
	
	@Autowired
	IDb2Service db2Service;
	private final Logger logger = LoggerFactory.getLogger(Db2GaugeChartController.class);
	String storeUrl;// 数据存储 曲线跳转地址
	

	/**
	 * 获取排序溢出率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartSpilledUsage")
	@ResponseBody
	public Map findChartSpilledUsage() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备IP获取排序溢出率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.SPILLEDUSAGE);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		MODBMSServerBean moDbms = db2Service.getMODBMSServerByMoId(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (moDbms != null) {
			map1.put("value", moDbms.getPerfvalue());
			map1.put("name", "排序溢出率");
			array1.add(map1);
			map2.put("chartSpilledUsage", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "排序溢出率");
			array1.add(map1);
			map2.put("chartSpilledUsage", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	/**
	 * 获取表空间使用率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findTSUsage")
	@ResponseBody
	public Map findChartLogUsage(HttpServletRequest request) throws Exception {
		logger.info("取出表空间使用率");
		String moid = request.getParameter("moID");
		Map map = new HashMap();
		Map map1 = new HashMap();
		Map param = new HashMap();
		param.put("moid", Integer.parseInt(moid));
//		param.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		param.put("TBUsage", DB2KPINameDef.TBUsage);
		try {
			
			List<PerfDB2TbsBean> pdb = db2Service.getDb2TBUsage(param);
			List dbname = new ArrayList();
			List dbdata = new ArrayList();
			if (pdb.size() > 0) {
				for(PerfDB2TbsBean p:pdb) {
					dbname.add(p.getTbsName());
					dbdata.add(p.getPerfValue());
				}
				map.put("category", dbname);
				map1.put("UsageBar", dbdata);
				map.put("url", "");
				map.put("data", map1);
			} else {
				dbname.add("");
				map.put("category", dbname);
				map1.put("UsageBar", dbdata);
				map.put("url", "");
				map.put("data", map1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	/**
	 * 代理数页面跳转
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toProxyNoCount")
	@ResponseBody
	public ModelAndView toProxyNoCount(ModelMap map, HttpServletRequest request)throws Exception {
		logger.info("代理数页面跳转");
		String moid = request.getParameter("moID");
		if(null == moid) {
			moid = "0";
		}
		Map param = new HashMap();
		param.put("moid", 0);
		String[] proxyName = {"总代理数","活跃代理数","其他"};
		List<String> proxyName_jsp = new ArrayList<String>();
		List<Integer> data = new ArrayList<Integer>();
		String[] proxyID = {DB2KPINameDef.TotalAgents,DB2KPINameDef.ActiveAgents};
		StringBuffer proxyValue = new StringBuffer();
		StringBuffer proxyNameList = new StringBuffer();
		int other = 0; 
		int proxyData = 0;
		for(int i = 0; i < proxyName.length - 1; i++) {
			if(i != 0) {
				proxyName_jsp.add(proxyName[i]);
				proxyNameList.append("'"+proxyName[i]+"',");
			}
			param.put("kpiname",proxyID[i]);
			PerfSnapshotDatabaseBean proxy = db2Service.queryProxyInfo(param);
			if(null == proxy) {
				if( i == 0 ) {
					other = 0;
				} 
			} else {
				proxyData = Integer.parseInt(null == proxy.getPerfvalue()? "0" : proxy.getPerfvalue());
				if(i == 0) {
					other = proxyData;
				} else {
					other -= proxyData;
				}
			}
			if(i != 0) {
				proxyValue.append("{value:" + proxyData + ",name:'" + proxyName[i] + "'},");
				data.add(proxyData);
			}
		}
		proxyNameList.append("'空闲代理数',");
		proxyNameList.append("'"+proxyName[proxyName.length-1]+"'");
		param.put("kpiname",DB2KPINameDef.IdleAgents);
		PerfSnapshotDatabaseBean proxyIdel = db2Service.queryProxyInfo(param);
		int idleAgents = 0;
		if(null != proxyIdel) {
			idleAgents = Integer.parseInt(null == proxyIdel.getPerfvalue() ? "0" : proxyIdel.getPerfvalue());
			other -= idleAgents;
		}
		proxyName_jsp.add("空闲代理数");
		data.add(idleAgents);
		proxyValue.append("{value:"+idleAgents+",name:'空闲代理数'},");
		proxyName_jsp.add(proxyName[proxyName.length-1]);
		data.add(other);
		proxyValue.append("{value:"+other+",name:'其他'}");
		map.put("proxyValue", proxyValue);
		map.put("proxyName", proxyNameList);
		map.put("proxyData", data);
		map.put("proxyName_jsp", proxyName_jsp);
		return new ModelAndView("monitor/database/db2/proxyNoCount");
	}
	/**
	 * 获取日志利用率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartLogUsage")
	@ResponseBody
	public Map findChartLogUsage() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备IP获取日志利用率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.LOGUSAGE);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		MODBMSServerBean moDbms = db2Service.getMODBMSServerByMoId(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (moDbms != null) {
			map1.put("value", moDbms.getPerfvalue());
			map1.put("name", "日志利用率");
			array1.add(map1);
			map2.put("chartLogUsage", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "日志利用率");
			array1.add(map1);
			map2.put("chartLogUsage", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	/**
	 * 获取共享排序内存利用率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartShareMemUsage")
	@ResponseBody
	public Map findChartShareMemUsage() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备ID获取共享排序内存利用率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.SHAREMEMUSAGE);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		MODBMSServerBean moDbms = db2Service.getMODBMSServerByMoId(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (moDbms != null) {
			map1.put("value", moDbms.getPerfvalue());
			map1.put("name", "共享排序内存利用率");
			array1.add(map1);
			map2.put("chartShareMemUsage", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "共享排序内存利用率");
			array1.add(map1);
			map2.put("chartShareMemUsage", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	/**
	 * 获取等待锁定的应用程序比率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartWaitingLockUsage")
	@ResponseBody
	public Map findChartWaitingLockUsage() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备ID获取等待锁定的应用程序比率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.WAITINGLOCKUSAGE);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		MODBMSServerBean moDbms = db2Service.getMODBMSServerByMoId(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (moDbms != null) {
			map1.put("value", moDbms.getPerfvalue());
			map1.put("name", "等待锁定的应用程序比率");
			array1.add(map1);
			map2.put("chartWaitingLockUsage", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "等待锁定的应用程序比率");
			array1.add(map1);
			map2.put("chartWaitingLockUsage", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	/**
	 * 获取程序包高速缓存命中率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartPkgCacheHits")
	@ResponseBody
	public Map findChartPkgCacheHits() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备ID获取程序包高速缓存命中率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.PKGCACHEHITS);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		MODBMSServerBean moDbms = db2Service.getMODBMSServerByMoId(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (moDbms != null) {
			map1.put("value", moDbms.getPerfvalue());
			map1.put("name", "程序包高速缓存命中率");
			array1.add(map1);
			map2.put("chartPkgCacheHits", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "程序包高速缓存命中率");
			array1.add(map1);
			map2.put("chartPkgCacheHits", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	/**
	 * 获取目录高速缓存命中率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartCatCacheHits")
	@ResponseBody
	public Map findChartCatCacheHits() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备ID获取目录高速缓存命中率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.CATCACHEHITS);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		MODBMSServerBean moDbms = db2Service.getMODBMSServerByMoId(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (moDbms != null) {
			map1.put("value", moDbms.getPerfvalue());
			map1.put("name", "目录高速缓存命中率");
			array1.add(map1);
			map2.put("chartCatCacheHits", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "目录高速缓存命中率");
			array1.add(map1);
			map2.put("chartCatCacheHits", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	/**
	 * 获取缓冲池命中率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartBufferPoolHits")
	@ResponseBody
	public Map findChartBufferPoolHits() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备ID获取缓冲池命中率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.BUFFERPOOLHITS);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		PerfDB2BufferPoolBean moDbms = db2Service.getPerfBufferPool(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (moDbms != null) {
			map1.put("value", moDbms.getPerfValue());
			map1.put("name", "缓冲池命中率");
			array1.add(map1);
			map2.put("chartBufferPoolHits", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "缓冲池命中率");
			array1.add(map1);
			map2.put("chartBufferPoolHits", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	/**
	 * 获取索引缓冲池命中率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartIndexHits")
	@ResponseBody
	public Map findChartIndexHits() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备ID获取索引缓冲池命中率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.INDEXHITS);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		PerfDB2BufferPoolBean moDbms = db2Service.getPerfBufferPool(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (moDbms != null) {
			map1.put("value", moDbms.getPerfValue());
			map1.put("name", "索引缓冲池命中率");
			array1.add(map1);
			map2.put("chartIndexHits", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "索引缓冲池命中率");
			array1.add(map1);
			map2.put("chartIndexHits", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	/**
	 * 获取数据页命中率
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChartDataPageHits")
	@ResponseBody
	public Map findChartDataPageHits() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备ID获取数据页命中率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		params.put("KPIName", DB2KPINameDef.DATAPAGEHITS);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		PerfDB2BufferPoolBean moDbms = db2Service.getPerfBufferPool(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (moDbms != null) {
			map1.put("value", moDbms.getPerfValue());
			map1.put("name", "数据页命中率");
			array1.add(map1);
			map2.put("chartDataPageHits", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "数据页命中率");
			array1.add(map1);
			map2.put("chartDataPageHits", array1);
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
			timeBegin = f.format(d.getTime());
		}
		
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		return params;
	}
}
