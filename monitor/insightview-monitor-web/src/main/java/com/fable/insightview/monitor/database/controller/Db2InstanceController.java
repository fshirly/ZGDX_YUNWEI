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

import com.fable.insightview.monitor.database.entity.PerfDB2InstanceBean;
import com.fable.insightview.monitor.database.service.IDb2Service;
import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.platform.common.util.JsonUtil;

@Controller
@RequestMapping("/monitor/db2InstanceManage")
public class Db2InstanceController {
	
	@Autowired
	IDb2Service db2Service;
	private final Logger logger = LoggerFactory.getLogger(Db2InstanceController.class);
	/**
	 * 总连接数趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toTotalConnsLineChart")
	public ModelAndView toTotalConnsLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2InstanceManage/getTotalConns");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 总连接数图表数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getTotalConns")
	@ResponseBody
	public Map<String, Object> getTotalConns(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载总连接数图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("总连接数图");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2InstanceBean> perfList = db2Service.queryInstancePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if (perf.equals("1")) {
					seriesSbf.append(perfList.get(i).getTotalConns() + ",");
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
		
		String db2TotalConnsData = JsonUtil.toString(jsonData);
		logger.info("db2TotalConnsData==" + db2TotalConnsData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2TotalConnsData);
		logger.info("结束...加载总连接数趋势图表数据");
		
		return result;
	}
	
	/**
	 * 本地连接数趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toLocalConnsLineChart")
	public ModelAndView toLocalConnsLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2InstanceManage/getLocalConns");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 本地连接数图表数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getLocalConns")
	@ResponseBody
	public Map<String, Object> getLocalConns(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载本地连接数图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		String perf = request.getParameter("perfKind");
		if (perf.equals("2")) {
			jsonData.setLegend("本地连接数图");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2InstanceBean> perfList = db2Service.queryInstancePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if (perf.equals("2")) {
					seriesSbf.append(perfList.get(i).getLocalConns() + ",");
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
		
		String db2LocalConnsData = JsonUtil.toString(jsonData);
		logger.info("db2LocalConnsData==" + db2LocalConnsData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2LocalConnsData);
		logger.info("结束...加载本地连接数趋势图表数据");
		
		return result;
	}
	
	/**
	 * 异地连接数趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toRemConnsLineChart")
	public ModelAndView toRemConnsLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2InstanceManage/getRemConns");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 异地连接数图表数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getRemConns")
	@ResponseBody
	public Map<String, Object> getRemConns(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载异地连接数图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		String perf = request.getParameter("perfKind");
		if (perf.equals("3")) {
			jsonData.setLegend("异地连接数图");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2InstanceBean> perfList = db2Service.queryInstancePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if (perf.equals("3")) {
					seriesSbf.append(perfList.get(i).getRemConns() + ",");
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
		
		String db2RemConnsData = JsonUtil.toString(jsonData);
		logger.info("db2RemConnsData==" + db2RemConnsData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2RemConnsData);
		logger.info("结束...加载异地连接数趋势图表数据");
		
		return result;
	}
	
	/**
	 * 执行连接数趋势
	 * 
	 * @return
	 */
	@RequestMapping("/toExecConnsLineChart")
	public ModelAndView toExecConnsLineChart(HttpServletRequest request,
	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2InstanceManage/getExecConns");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}

	/**
	 * * 执行连接数图表数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getExecConns")
	@ResponseBody
	public Map<String, Object> getExecConns(HttpServletRequest request)
			throws Exception {
		
		logger.info("开始...加载执行连接数图表数据");
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");// 
		String perf = request.getParameter("perfKind");
		if (perf.equals("4")) {
			jsonData.setLegend("执行连接数图");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfDB2InstanceBean> perfList = db2Service.queryInstancePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if (perf.equals("4")) {
					seriesSbf.append((perfList.get(i).getLocalConnsExec()+perfList.get(i).getRemConnsExec()) + ",");
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
		
		String db2ExecConnsData = JsonUtil.toString(jsonData);
		logger.info("db2ExecConnsData==" + db2ExecConnsData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("db2ChartData", db2ExecConnsData);
		logger.info("结束...加载执行连接数趋势图表数据");
		
		return result;
	}
	
	/**
	 * 打开连接统计
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toAgentsCountInfo")
	@ResponseBody
	public ModelAndView toAgentsCountInfo(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载连接统计页面");
		Integer moId = Integer.parseInt(request.getParameter("moID"));
		Map params = new HashMap();
		params.put("moID", moId);
		PerfDB2InstanceBean perfInstanceBean = db2Service.queryInsPerfLastest(params);
		map.put("instanceBean", perfInstanceBean);
		return new ModelAndView("monitor/database/db2/agentsCountInfo");
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