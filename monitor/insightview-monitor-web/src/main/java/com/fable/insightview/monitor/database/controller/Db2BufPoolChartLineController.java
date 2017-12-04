package com.fable.insightview.monitor.database.controller;

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

import com.fable.insightview.monitor.database.entity.DB2KPINameDef;
import com.fable.insightview.monitor.database.entity.PerfDB2BufferPoolBean;
import com.fable.insightview.monitor.database.entity.PerfDB2DatabaseBean;
import com.fable.insightview.monitor.database.service.IDb2Service;
import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.monitor.middleware.websphere.entity.WebMoudleInfosBean;
import com.fable.insightview.platform.common.util.JsonUtil;

@Controller
@RequestMapping("/monitor/db2BufPoolLineManage")
public class Db2BufPoolChartLineController {
	
	@Autowired
	IDb2Service db2Service;
	private final Logger logger = LoggerFactory.getLogger(Db2BufPoolChartLineController.class);
	
	/**
	 * webApp性能曲线图
	 */
	@RequestMapping("/toBufferPoolLineChart")
	public ModelAndView toBufferPoolLineChart(HttpServletRequest request,

	ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/db2BufPoolLineManage/initBufferPoolInfos");
		return new ModelAndView("monitor/database/db2/db2LineChart");
	}
	
	/**
	 * * 内存池趋势图表数据
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initBufferPoolInfos")
	@ResponseBody
	public Map<String, Object> initBufferPoolInfos(HttpServletRequest request){
		Map params = new HashMap();
		// 默认取最近1小时数据
		params = queryBetweenTime(request, params);
		params.put("moId", request.getParameter("moID"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText("");
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setText("缓冲池命中率(%)");
		}else if(perf.equals("2")){
			jsonData.setText("索引缓冲池命中率(%)");
		}else if(perf.equals("3")){
			jsonData.setText("数据页命中率(%)");
		}else if(perf.equals("4")){
			jsonData.setText("直接读取数");
		}else if(perf.equals("5")){
			jsonData.setText("直接写入数");
		}else if(perf.equals("6")){
			jsonData.setText("直接读取时间(ms)");
		}else{
			jsonData.setText("直接写入时间(ms)");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		String legend = "";
		StringBuffer seriesSbf = new StringBuffer("[");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<PerfDB2BufferPoolBean> perfList = db2Service.queryBufferPoolPerf(params);
			Set  set=new HashSet();   
			Set  timeSet=new HashSet();   
			Map<String,Integer> timeMap = new HashMap<String,Integer>();
			for (int i = 0; i < perfList.size(); i++) {
				timeSet.add(perfList.get(i).getFormatTime());
				set.add(perfList.get(i).getBufferPoolName());
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
				if(timeMap.get(setValue) >= 2){
					list.add(setValue);  
				}
			}
			Collections.sort(list);  
			for (int i = 0; i < list.size(); i++) {
				xAxisSbf.append(list.get(i) + ",");
			}
			if (perfList != null) {
				for (Iterator it = set.iterator();  it.hasNext(); ) {
					String bufferPoolName = it.next().toString();
					legend += bufferPoolName + ",";
					seriesSbf.append("{name:'" + bufferPoolName
							+ "',type:'line',data:[");
					if (perf.equals("1")) {
						for (int j = 0; j < perfList.size(); j++) {
							if(bufferPoolName.equals(perfList.get(j).getBufferPoolName())){
								seriesSbf.append(perfList.get(j).getBufferPoolHits() + ",");
							}
						}
					}else if (perf.equals("2")) {
						for (int j = 0; j < perfList.size(); j++) {
							if(bufferPoolName.equals(perfList.get(j).getBufferPoolName())){
								seriesSbf.append(perfList.get(j).getIndexHits() + ",");
							}
							
						}
					}else if (perf.equals("3")) {
						for (int j = 0; j < perfList.size(); j++) {
							if(bufferPoolName.equals(perfList.get(j).getBufferPoolName())){
								seriesSbf.append(perfList.get(j).getDataPageHits() + ",");
							}
							
						}
					}else if (perf.equals("4")) {
						for (int j = 0; j < perfList.size(); j++) {
							if(bufferPoolName.equals(perfList.get(j).getBufferPoolName())){
								seriesSbf.append(perfList.get(j).getDirectReads() + ",");
							}
							
						}
					}else if (perf.equals("5")) {
						for (int j = 0; j < perfList.size(); j++) {
							if(bufferPoolName.equals(perfList.get(j).getBufferPoolName())){
								seriesSbf.append(perfList.get(j).getDirectWrites() + ",");
							}
							
						}
					}else if (perf.equals("6")) {
						for (int j = 0; j < perfList.size(); j++) {
							if(bufferPoolName.equals(perfList.get(j).getBufferPoolName())){
								seriesSbf.append(perfList.get(j).getDirectReadTime() + ",");
							}
							
						}
					}else{
						for (int j = 0; j < perfList.size(); j++) {
							if(bufferPoolName.equals(perfList.get(j).getBufferPoolName())){
								seriesSbf.append(perfList.get(j).getDirectWriteTime() + ",");
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
			String bufferPoolChartData = JsonUtil.toString(jsonData);
			logger.info("bufferPoolChartData===" + bufferPoolChartData);
		
			result.put("db2ChartData", bufferPoolChartData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 获取缓冲池命中率柱状图
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBufferPoolBarChart")
	@ResponseBody
	public Map findBufferPoolBarChart() {

		logger.info("开始...加载缓冲池命中率数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		String timeEnd = f.format(d.getTime());
		String timeBegin = f.format(d.getTime());
		Map params = new HashMap();
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		// 实例moId
		params.put("MOID", request.getParameter("moID"));
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			params.put("KPIName", DB2KPINameDef.BUFFERPOOLHITS);
		}else if(perf.equals("2")){
			params.put("KPIName", DB2KPINameDef.INDEXHITS);
		}else if(perf.equals("3")){
			params.put("KPIName", DB2KPINameDef.DATAPAGEHITS);
		}else if(perf.equals("4")){
			params.put("KPIName", DB2KPINameDef.DIRECTREADS);
		}else if(perf.equals("5")){
			params.put("KPIName", DB2KPINameDef.DIRECTWRITES);
		}else if(perf.equals("6")){
			params.put("KPIName", DB2KPINameDef.DIRECTREADTIME);
		}else{
			params.put("KPIName", DB2KPINameDef.DIRECTWRITETIME);
		}
		
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			List<PerfDB2BufferPoolBean> moLsinfo = db2Service.queryBufferPoolBarPerf(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					array.add(moLsinfo.get(i).getBufferPoolName());
					array1.add(moLsinfo.get(i).getPerfValue());
				}
				map.put("category", array);
				if (perf.equals("1")) {
					map1.put("BufferPoolHits", array1);
				}else if(perf.equals("2")){
					map1.put("IndexHits", array1);
				}else if(perf.equals("3")){
					map1.put("DataPageHits", array1);
				}else if(perf.equals("4")){
					map1.put("DirectReads", array1);
				}else if(perf.equals("5")){
					map1.put("DirectWrites", array1);
				}else if(perf.equals("6")){
					map1.put("DirectReadTime", array1);
				}else{
					map1.put("DirectWriteTime", array1);
				}
				map.put("url", "");
				map.put("data", map1);
			} else {
				array.add("");
				map.put("category", array);
				if (perf.equals("1")) {
					map1.put("BufferPoolHits", array1);
				}else if(perf.equals("2")){
					map1.put("IndexHits", array1);
				}else if(perf.equals("3")){
					map1.put("DataPageHits", array1);
				}else if(perf.equals("4")){
					map1.put("DirectReads", array1);
				}else if(perf.equals("5")){
					map1.put("DirectWrites", array1);
				}else if(perf.equals("6")){
					map1.put("DirectReadTime", array1);
				}else{
					map1.put("DirectWriteTime", array1);
				}
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
