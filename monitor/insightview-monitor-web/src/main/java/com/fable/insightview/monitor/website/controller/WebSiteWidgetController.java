package com.fable.insightview.monitor.website.controller;

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
import com.fable.insightview.monitor.database.entity.PerfDB2InstanceBean;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.website.entity.SiteDns;
import com.fable.insightview.monitor.website.entity.SiteFtp;
import com.fable.insightview.monitor.website.entity.SiteHttp;
import com.fable.insightview.monitor.website.entity.SitePort;
import com.fable.insightview.monitor.website.entity.WebSiteKPINameDef;
import com.fable.insightview.monitor.website.service.IWebSiteService;
import com.fable.insightview.platform.common.util.JsonUtil;

@Controller
@RequestMapping("/monitor/webSiteWidget")
public class WebSiteWidgetController {
	@Autowired IWebSiteService webSiteService;
	private final Logger logger = LoggerFactory.getLogger(WebSiteWidgetController.class);
	
	@RequestMapping("/toWebSiteInfo")
	public ModelAndView toWebSiteInfo(HttpServletRequest request,ModelMap map) {
		String siteType = request.getParameter("siteType");
		String moID = request.getParameter("moID");
		map.put("siteType", siteType);
		map.put("moID", moID);
		return new ModelAndView("monitor/website/webSiteInfo");
	}
	
	/**
	 * 加载DNS页面数据
	 */
	@RequestMapping("/getSiteDnsInfo")
	@ResponseBody
	public Map<String, Object> getSiteDnsInfo()
			throws Exception {
		logger.info("加载DNS列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moID", request.getParameter("moID"));
		paramMap.put("dnsStateKPI", WebSiteKPINameDef.DNSSTATE);
		paramMap.put("MID", KPINameDef.JOBSITEDNS);
		List<SiteDns> dnsBean = webSiteService.getPerfSiteDnsByMoId(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", dnsBean);
		logger.info("加载DNS列表页面数据over");
		return result;
	}
	
	/**
	 * 加载FTP页面数据
	 */
	@RequestMapping("/getSiteFtpInfo")
	@ResponseBody
	public Map<String, Object> getSiteFtpInfo()
			throws Exception {
		logger.info("加载FTP列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moID", request.getParameter("moID"));
		paramMap.put("connsStateKPI", WebSiteKPINameDef.CONNSSTATE);
		paramMap.put("loginStateKPI", WebSiteKPINameDef.LOGINSTATE);
		paramMap.put("MID", KPINameDef.JOBSITEFTP);
		List<SiteFtp> ftpBean = webSiteService.getPerfSiteFtpByMoId(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", ftpBean);
		logger.info("加载FTP列表页面数据over");
		return result;
	}
	
	/**
	 * 加载HTTP页面数据
	 */
	@RequestMapping("/getSiteHttpInfo")
	@ResponseBody
	public Map<String, Object> getSiteHttpInfo()
			throws Exception {
		logger.info("加载HTTP列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moID", request.getParameter("moID"));
		paramMap.put("connsStateKPI", WebSiteKPINameDef.CONNSSTATE);
		paramMap.put("MID", KPINameDef.JOBSITEHTTP);
		List<SiteHttp> httpBean = webSiteService.getPerfSiteHttpByMoId(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", httpBean);
		logger.info("加载HTTP列表页面数据over");
		return result;
	}
	
	/**
	 * tcp端口监测信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toShowPortInfo")
	@ResponseBody
	public ModelAndView toShowPortInfo(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载tcp端口监测详情页面");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moID", request.getParameter("moID"));
		paramMap.put("portStateKPI", WebSiteKPINameDef.PORTSTATE);
		paramMap.put("MID", KPINameDef.JOBSITETCP);
		List<SitePort> portBeanLst = webSiteService.getPerfSitePortByMoId(paramMap);
		map.put("sitePort", portBeanLst.get(0));
		return new ModelAndView("monitor/website/portInfo");
	}
	
	/**
	 * DNS可用性仪表盘
	 */
	@RequestMapping("/findDnsChartAvailability")
	@ResponseBody
	public Map<String, Object> findDnsChartAvailability() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据站点moID获取DNS可用性");
		Map<String, Object> params = new HashMap<String, Object>();
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		params.put("dnsStateKPI", WebSiteKPINameDef.DNSSTATE);
		SiteDns dnsBean = webSiteService.getDnsChartAvailability(params);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (dnsBean != null) {
			map1.put("value", dnsBean.getPerfAvailable());
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("chartDnsAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("chartDnsAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	/**
	 * FTP可用性仪表盘
	 */
	@RequestMapping("/findFtpChartAvailability")
	@ResponseBody
	public Map<String, Object> findFtpChartAvailability() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据站点moID获取FTP可用性");
		Map<String, Object> params = new HashMap<String, Object>();
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		params.put("connsStateKPI", WebSiteKPINameDef.CONNSSTATE);
		params.put("loginStateKPI", WebSiteKPINameDef.LOGINSTATE);
		SiteFtp ftpBean = webSiteService.getFtpChartAvailability(params);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (ftpBean != null) {
			map1.put("value", ftpBean.getPerfAvailable());
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("chartFtpAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("chartFtpAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	/**
	 * HTTP可用性仪表盘
	 */
	@RequestMapping("/findHttpChartAvailability")
	@ResponseBody
	public Map<String, Object> findHttpChartAvailability() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据站点moID获取HTTP可用性");
		Map<String, Object> params = new HashMap<String, Object>();
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		params.put("connsStateKPI", WebSiteKPINameDef.CONNSSTATE);
		SiteHttp httpBean = webSiteService.getHttpChartAvailability(params);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (httpBean != null) {
			map1.put("value", httpBean.getPerfAvailable());
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("chartHttpAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("chartHttpAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	/**
	 * PORT可用性仪表盘
	 */
	@RequestMapping("/findPortChartAvailability")
	@ResponseBody
	public Map<String, Object> findPortChartAvailability() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据站点moID获取PORT可用性");
		Map<String, Object> params = new HashMap<String, Object>();
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		params.put("portStateKPI", WebSiteKPINameDef.PORTSTATE);
		SitePort portBean = webSiteService.getPortChartAvailability(params);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (portBean != null) {
			map1.put("value", portBean.getPerfAvailable());
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("chartPortAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("chartPortAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}

	/**
	 * DNS响应趋势
	 */
	@RequestMapping("/toResponseTimeChart")
	public ModelAndView toResponseTimeChart(HttpServletRequest request,
			ModelMap map) throws Exception {
		String siteType = request.getParameter("siteType");
		String moID = request.getParameter("moID");
		String proUrl = "";
		if ("1".equals(siteType)) {
			proUrl = "/monitor/webSiteWidget/getFtpResponseTime";
		} else if ("2".equals(siteType)) {
			proUrl = "/monitor/webSiteWidget/getDnsResponseTime";
		} if ("3".equals(siteType)) {
			proUrl = "/monitor/webSiteWidget/getHttpResponseTime";
		} 
		map.put("moID", moID);
		map.put("proUrl", proUrl);
		map.put("siteType", siteType);
		return new ModelAndView("monitor/website/webSiteLineChart");
	}
	
	/**
	 * * DNS响应时间数据获取
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getDnsResponseTime")
	@ResponseBody
	public Map<String, Object> getDnsResponseTime(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载DNS响应时间数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("响应趋势图(ms)");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<SiteDns> dnsfList = webSiteService.queryDnsPerf(params);
		int size = dnsfList.size();
		if (dnsfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(dnsfList.get(i).getFormatTime() + ",");
				seriesSbf.append(dnsfList.get(i).getResponseTime() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String dnsResponseTimeData = JsonUtil.toString(jsonData);
		logger.info("dnsResponseTimeData==" + dnsResponseTimeData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("webSiteChartData", dnsResponseTimeData);
		logger.info("结束...加载DNS响应时间数据趋势图表数据");
		return result;
	}
	
	/**
	 * * FTP响应时间数据获取
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getFtpResponseTime")
	@ResponseBody
	public Map<String, Object> getFtpResponseTime(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载FTP响应时间数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("响应趋势图(ms)");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<SiteFtp> ftpList = webSiteService.queryFtpPerf(params);
		int size = ftpList.size();
		if (ftpList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(ftpList.get(i).getFormatTime() + ",");
				seriesSbf.append(ftpList.get(i).getResponseTime() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String ftpResponseTimeData = JsonUtil.toString(jsonData);
		logger.info("ftpResponseTimeData==" + ftpResponseTimeData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("webSiteChartData", ftpResponseTimeData);
		logger.info("结束...加载FTP响应时间数据趋势图表数据");
		return result;
	}
	
	/**
	 * * DNS响应时间数据获取
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getHttpResponseTime")
	@ResponseBody
	public Map<String, Object> getHttpResponseTime(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载HTTP响应时间数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		jsonData.setLegend("响应趋势图(ms)");
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<SiteHttp> httpList = webSiteService.queryHttpPerf(params);
		int size = httpList.size();
		if (httpList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(httpList.get(i).getFormatTime() + ",");
				seriesSbf.append(httpList.get(i).getResponseTime() + ",");
			}
			if (size > 0) {
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			}
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		
		String httpResponseTimeData = JsonUtil.toString(jsonData);
		logger.info("httpResponseTimeData==" + httpResponseTimeData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("webSiteChartData", httpResponseTimeData);
		logger.info("结束...加载HTTP响应时间数据趋势图表数据");
		return result;
	}
	
	
	/**
	 * 跳转至告警列表页
	 * 
	 * @return
	 */
	@RequestMapping("/toShowWebSiteAlarmInfo")
	public ModelAndView toShowWebSiteAlarmInfo(HttpServletRequest request) {
		String moID = request.getParameter("moID");
		String moClass = request.getParameter("moClass");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moID", moID);
		request.setAttribute("moClass", moClass);
		return new ModelAndView("monitor/website/webSiteAlarmInfo");
	}
	
	/**
	 * 最近告警
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getWebSiteAlarmInfo")
	@ResponseBody
	public Map<String, Object> getWebSiteAlarmInfo() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("moID", Integer.parseInt(request.getParameter("moID")));
		params.put("moClass", request.getParameter("moClass"));
		int alarmNum = Integer.parseInt(request.getParameter("alarmNum"));
		try {
			List<AlarmActiveDetail> moLsinfo = webSiteService
					.getWebSiteAlarmInfo(params);
			if (moLsinfo.size() >= alarmNum) {
				moLsinfo = moLsinfo.subList(0, alarmNum);
			}
			result.put("rows", moLsinfo);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 图表详情查询页面
	 */
	@RequestMapping("/toHistoryChartsDetail")
	public ModelAndView toHistoryChartsDetail(HttpServletRequest request,
			ModelMap map) throws Exception {
		String proUrl = request.getParameter("proUrl");// 获取数据的url
		String moID = request.getParameter("moID");// 获取数据的MOID
		map.put("proUrl", proUrl);
		map.put("moID", moID);
		return new ModelAndView("monitor/website/historyChart_detail");
	}
	
	
	/**
	 * 设置查询时间(公共调用方法)
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object>  queryBetweenTime(HttpServletRequest request, Map params) {
		String seltDate = request.getParameter("time");
		if (seltDate != null && !"".equals(seltDate)) {
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
		} else {
			String timeBegin = request.getParameter("timeBegin");
			String timeEnd = request.getParameter("timeEnd");
			if(timeBegin == null || timeBegin == "" || timeEnd == null || timeEnd == "" ){
				SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d = new Date();
				timeBegin = f.format(d.getTime()-24*60*60*1000);
				timeEnd = f.format(d);
			}
			params.put("timeBegin", timeBegin);
			params.put("timeEnd", timeEnd);
			//表示是否展示最大值、最小值
			params.put("markPoint", request.getParameter("markPoint"));
		
		}
		
		return params;
	}
	
}
