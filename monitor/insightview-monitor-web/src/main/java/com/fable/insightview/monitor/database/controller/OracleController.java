package com.fable.insightview.monitor.database.controller;

import java.text.DecimalFormat;
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

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOOracleDataFileBean;
import com.fable.insightview.monitor.database.entity.MOOracleRollSEGBean;
import com.fable.insightview.monitor.database.entity.MOOracleSGABean;
import com.fable.insightview.monitor.database.entity.MOOracleTBSBean;
import com.fable.insightview.monitor.database.entity.OracleKPINameDef;
import com.fable.insightview.monitor.database.entity.PerfOrclDataFileBean;
import com.fable.insightview.monitor.database.entity.PerfOrclRollbackBean;
import com.fable.insightview.monitor.database.entity.PerfOrclSGABean;
import com.fable.insightview.monitor.database.entity.PerfOrclTBsBean;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.platform.common.util.JsonUtil;

@Controller
@RequestMapping("/monitor/orclManage")
public class OracleController {
	@Autowired
	IOracleService orclService;

	private final Logger logger = LoggerFactory
			.getLogger(OracleController.class);
	Integer MOID;

	/**
	 * 概览实例
	 * @param request
	 * @param map
	 * @return
	 */

	@RequestMapping("/toShowInsDetail")
	@ResponseBody
	public ModelAndView toShowInsDetail(HttpServletRequest request, ModelMap map) {
		logger.info("加载实例详情页面");
		int MOID = Integer.parseInt(request.getParameter("moID"));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("InsMOID", MOID);
		params.put("orclMID", KPINameDef.JOBORACLEAVAILABLE);
	
		MODBMSServerBean orcl = orclService.getOrclInstanceDetail(params);
		if (orcl != null) {
			if (orcl.getTotalsize() == null) {
				orcl.setTotalsize(HostComm.getBytesToSize(0));
			} else {
				orcl.setTotalsize(HostComm.getBytesToSize(Long.parseLong(orcl
						.getTotalsize())));
			}
			if (orcl.getFreesize() == null) {
				orcl.setFreesize(HostComm.getBytesToSize(0));
			} else {
				orcl.setFreesize(HostComm.getBytesToSize(Long.parseLong(orcl
						.getFreesize())));
			}
			if("".equals(orcl.getMoalias())||"null".equals(orcl.getMoalias())||orcl.getMoalias()==null){
				orcl.setMoalias("");
			}else{
				orcl.setMoalias("("+orcl.getMoalias()+")");
			}
		}
		map.put("orcl", orcl);
		return new ModelAndView("monitor/database/oracle/orclInstanceBaseInfo");
	}

	/**
	 * 回滚段图表页面跳转
	 * @author zhengxh
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toOrclRollSegLineChart")
	public ModelAndView toOrclRollSegLineChart(HttpServletRequest request,
			ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		Map<Integer, String> seltItem = new HashMap<Integer, String>();
		List<MOOracleRollSEGBean> seltList = orclService
				.queryAllOrclRollback(moId);
		for (int i = 0; i < seltList.size(); i++) {
			seltItem.put(seltList.get(i).getMoID(), seltList.get(i)
					.getSegName());
		}
		map.put("seltItem", seltItem);
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/orclManage/initOraclRollSeg");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}

	/**
	 * 回滚段图表数据
	 * @author zhengxh
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initOraclRollSeg")
	@ResponseBody
	public Map<String, Object> initOraclRollSeg(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载回滚段图表数据");
		Map params = new HashMap();
		// 增加时间过滤条件
		params = queryBetweenTime(request, params);
		params.put("moid", request.getParameter("moid"));
		params.put("seltItem", request.getParameter("seltItem"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText(" ");// oracle回滚段统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("Segment大小趋势图(MB)");
		} else if (perf.equals("2")) {
			jsonData.setLegend("Shrink次数趋势图");
		} else if (perf.equals("3")) {
			jsonData.setLegend("Wrap次数大小趋势图");
		} else if (perf.equals("4")) {
			jsonData.setLegend("扩展次数趋势图");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfOrclRollbackBean> perfList = orclService
				.queryOrclRollBackPerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getColTime() + ",");
				if (perf.equals("1")) {
					seriesSbf.append(perfList.get(i).getCurrsize() + ",");
				} else if (perf.equals("2")) {
					seriesSbf.append(perfList.get(i).getShrinks() + ",");
				} else if (perf.equals("3")) {
					seriesSbf.append(perfList.get(i).getWraps() + ",");
				} else if (perf.equals("4")) {
					seriesSbf.append(perfList.get(i).getExtend() + ",");
				}
			}
			xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
			seriesSbf.deleteCharAt(seriesSbf.length() - 1);
			seriesSbf.append("]}]");

			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}
		String oracleChartData = JsonUtil.toString(jsonData);
		logger.info(">>>>>>>>>===" + oracleChartData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("oracleChartData", oracleChartData);
		logger.info("结束...加载回滚段图表数据");
		return result;
	}

	/**
	 * 跳转至表空间列表页
	 * @return
	 */
	@RequestMapping("/toShowOrclTbsInfo")
	public ModelAndView toShowOrclTbsInfo(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/database/oracle/orclTbsInfoList");
	}

	/**
	 * 获取表空间列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getOrclTbsInfo")
	@ResponseBody
	public Map<String, Object> getOrclTbsInfo() throws Exception {
		logger.info("开始...加载表空间信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", Integer.parseInt(request.getParameter("moID")));
		List<MODBMSServerBean> moLsCount = orclService.getTbsCount(params);
		List<MODBMSServerBean> moLsinfo = new ArrayList<MODBMSServerBean>();
		if (moLsCount.size() > 0) {
			for (int i = 0; i < moLsCount.size(); i++) {
				params.put("COMMID", moLsCount.get(i).getMoid());
				List<MODBMSServerBean> moLsinfo1 = orclService
						.getOrclTbsInfo(params);
				if (moLsinfo1 != null && moLsinfo1.size() > 0) {
					MODBMSServerBean mo = getTbsObj(moLsinfo1);
					moLsinfo.add(mo);
				}
			}
		}
		Collections.sort(moLsinfo, new Comparator<MODBMSServerBean>() {
			public int compare(MODBMSServerBean arg0, MODBMSServerBean arg1) {
				double hits0 = arg0.getTbsusage();
				double hits1 = arg1.getTbsusage();
				if (hits1 > hits0) {
					return 1;
				} else if (hits1 == hits0) {
					return 0;
				} else {
					return -1;
				}
			}
		});

		result.put("rows", moLsinfo);
		logger.info("结束...加载表空间信息列表 " + moLsinfo);
		return result;
	}

	public MODBMSServerBean getTbsObj(List<MODBMSServerBean> list) {
		MODBMSServerBean mo = new MODBMSServerBean();
		String name;
		String value;
		DecimalFormat df = new DecimalFormat(".00");
		try {
			for (int i = 0; i < list.size(); i++) {
				name = list.get(i).getKpiname();
				value = list.get(i).getPerfvalue();
				mo.setTbsname(list.get(i).getTbsname());
				mo.setMoid(list.get(i).getMoid());
				boolean isNum = value.matches("[0-9]+");
				/*
				 * if(isNum) {
				 * value=HostComm.getBytesToSize(Long.parseLong(value)); }
				 */
				if (name.equals(OracleKPINameDef.TABLESPACEUSAGE)) {
					mo.setTbsusage(Double.parseDouble(df.format(Double
							.parseDouble(value))));
				} else if (name.equals(OracleKPINameDef.TABLESPACESIZE)) {
					mo.setTotalsize(HostComm.getBytesToSize(Long
							.parseLong(value)));
				} else if (name.equals(OracleKPINameDef.TABLESPACEFREE)) {
					mo.setFreesize(HostComm.getBytesToSize(Long
							.parseLong(value)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mo;
	}

	/**
	 * 跳转至告警列表页
	 * @return
	 */
	@RequestMapping("/toShowTbsAlarmInfo")
	public ModelAndView toShowTbsAlarmInfo(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/database/oracle/orclTbsAlarmInfo");
	}

	/**
	 * 表空间 for 最近告警
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getTbsAlarmInfo")
	@ResponseBody
	public Map<String, Object> getTbsAlarmInfo() throws Exception {
		logger.info("开始...加载表空间信息列表 for 最近告警");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		int moID = 0;
		if (orclService.getInsIdBymoId(Integer.parseInt(request
				.getParameter("moID"))) != null) {
			moID = orclService.getInsIdBymoId(Integer.parseInt(request
					.getParameter("moID")));
		}
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

		logger.info("结束...加载表空间信息列表 for 最近告警");
		return result;
	}

	/**
	 * 跳转数据库实例可用率
	 * 
	 * @return
	 */
	@RequestMapping("/toShowOrclAvailability")
	public ModelAndView toShowOrclAvailability(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/database/oracle/orclAvailabilityChart");
	}

	/**
	 * 获取数据库实例可用率
	 * @param deviceIP
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findOrclChartAvailability")
	@ResponseBody
	public Map<String, Object> findOrclChartAvailability(
			HttpServletRequest request) throws Exception {
		logger.info("根据数据库实例ID获取图表可用性使用率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", Integer.parseInt(request.getParameter("moID")));
		MODBMSServerBean mo = orclService.getOrclChartAvailability(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (mo != null) {
			map1.put("value", mo.getDeviceavailability());
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("oracleAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("oracleAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}

	/**
	 * 表空间曲线图表页面跳转
	 * @return
	 */
	@RequestMapping("/toOrclTbsLineChart")
	public ModelAndView toOrclTbsLineChart(HttpServletRequest request,
			ModelMap map) throws Exception {
		String MOID = request.getParameter("moID");
		Map<Integer, String> seltItem = new HashMap<Integer, String>();
		List<MOOracleTBSBean> seltList = orclService.queryAllOrclTbs(MOID);
		for (int i = 0; i < seltList.size(); i++) {
			seltItem.put(seltList.get(i).getMoid(), seltList.get(i)
					.getTbsname());
		}
		map.put("seltItem", seltItem);
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", MOID);
		map.put("proUrl", "/monitor/orclManage/initOraclTbs");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}

	/**
	 * * 表空间图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initOraclTbs")
	@ResponseBody
	public Map<String, Object> initOraclTbs(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载表空间图表数据");
		Map params = new HashMap();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("seltItem", request.getParameter("seltItem"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText(" ");// oracle表空间统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("表空间可用率趋势图(%)");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		if (request.getParameter("seltItem") != null) {
			List<PerfOrclTBsBean> perfList = orclService
					.queryOrclTbsPerf(params);
			int size = perfList.size();
			if (perfList != null && size > 0) {
				for (int i = 0; i < size; i++) {
					xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
					if (perf.equals("1")) {
						seriesSbf.append(perfList.get(i).getTbusage() + ",");
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
		}
		String oracleChartData = JsonUtil.toString(jsonData);
		System.out.println(">>>>>>>>>===" + oracleChartData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("oracleChartData", oracleChartData);
		logger.info("结束...加载表空间图表数据");
		return result;
	}

	/**
	 * 加载SGA详情页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toShowOrclSGADetail")
	@ResponseBody
	public ModelAndView toShowOrclSGADetail(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载SGA详情页面");
		MOID = Integer.parseInt(request.getParameter("moID"));
		MOOracleSGABean sga = orclService.getOrclSGADetail(MOID);
		map.put("sga", sga);
		map.put("flag", request.getParameter("flag"));
		map.put("openFlag", request.getParameter("openFlag"));
		return new ModelAndView("monitor/database/oracle/orclSGAInfo");
	}

	/**
	 * 加载数据文件列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toOrclDataFileList")
	public ModelAndView toOrclDataFileList(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/database/oracle/orclDataFileInfoList");
	}

	/**
	 * 加载数据文件列表页面数据
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listOrclDataFile")
	@ResponseBody
	public Map<String, Object> listOrclDataFile() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		List<MOOracleDataFileBean> dataFileLst = orclService
				.getAllDataFiles(Integer.parseInt(request.getParameter("moID")));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", dataFileLst);
		return result;
	}

	/**
	 * 数据文件详情页面跳转
	 * @return
	 */
	@RequestMapping("/toOrclDataFileDetail")
	public ModelAndView toOrclDataFileDetail(HttpServletRequest request,
			ModelMap map) throws Exception {
		int moId = Integer.parseInt(request.getParameter("moId"));
		MOOracleDataFileBean dataFileBean = orclService
				.getDataDetailByMoId(moId);
		map.put("dataFileBean", dataFileBean);
		map.put("flag", request.getParameter("flag"));
		return new ModelAndView("monitor/database/oracle/orclDataFile_detail");
	}

	/**
	 * 表空间详情页面跳转
	 * @return
	 */
	@RequestMapping("/toOrclTbsDetail")
	public ModelAndView toOrclTbsDetail(HttpServletRequest request, ModelMap map)
			throws Exception {
		int moId = Integer.parseInt(request.getParameter("moId"));
		MOOracleTBSBean tbsBean = orclService.getTbsDetailByMoId(moId);
		map.put("tbsBean", tbsBean);
		map.put("flag", request.getParameter("flag"));
		return new ModelAndView("monitor/database/oracle/orclTbs_detail");
	}

	/**
	 * 加载回滚段监测对象列表页面
	 * @return
	 */
	@RequestMapping("/toOrclRollSEGList")
	public ModelAndView toOrclRollSEGList(HttpServletRequest request,
			ModelMap map) {
		MOID = Integer.parseInt(request.getParameter("moID"));
		return new ModelAndView("monitor/database/oracle/orclRollSEGInfoList");
	}

	/**
	 * 加载回滚段监测对象列表页面数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listOrclRollSEG")
	@ResponseBody
	public Map<String, Object> listOrclRollSEG() throws Exception {
		logger.info("开始...加载回滚段监测对象列表");
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("MOID", MOID);
		List<MOOracleRollSEGBean> rollSEGLst = orclService
				.getOrclRollSEGList(params);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", rollSEGLst);
		logger.info("结束...加载回滚段监测对象列表 " + rollSEGLst);
		return result;
	}

	/**
	 * 回滚段监测对象详情页面跳转
	 * @return
	 */
	@RequestMapping("/toOrclRollSEGDetail")
	public ModelAndView toOrclRollSEGDetail(HttpServletRequest request,
			ModelMap map) throws Exception {
		int moID = Integer.parseInt(request.getParameter("moID"));
		MOOracleRollSEGBean rollSEG = orclService.getOrclRollSEGDetail(moID);
		map.put("rollSEG", rollSEG);
		map.put("flag", request.getParameter("flag"));
		return new ModelAndView(
				"monitor/database/oracle/orclRollSEGInfo_detail");
	}

	/**
	 * 数据文件曲线图表页面跳转
	 * @return
	 */
	@RequestMapping("/toOrclDataFileLineChart")
	public ModelAndView toOrclDataFileLineChart(HttpServletRequest request,
			ModelMap map) throws Exception {
		Map<Integer, String> seltItem = new HashMap<Integer, String>();
		List<MOOracleDataFileBean> dataFileLst = orclService
				.getAllDataFiles(Integer.parseInt(request.getParameter("moID")));
		for (int i = 0; i < dataFileLst.size(); i++) {
			String dataFileName = dataFileLst.get(i).getDataFileName();
			dataFileName = dataFileName
					.substring(dataFileName.lastIndexOf("/") + 1);
			seltItem.put(dataFileLst.get(i).getMoId(), dataFileName);
		}
		map.put("seltItem", seltItem);
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", request.getParameter("moID"));
		map.put("proUrl", "/monitor/orclManage/initOrclDataFileChart");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}

	/**
	 * * 数据文件曲线图表数据获取
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initOrclDataFileChart")
	@ResponseBody
	public Map<String, Object> initOrclDataFileChart(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载数据文件数据");
		String seltItem = request.getParameter("seltItem");
		Map params = new HashMap();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		params.put("seltItem", seltItem);
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText(" ");// oracle数据文件统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("物理读次数趋势图");
		} else if (perf.equals("2")) {
			jsonData.setLegend("物理写次数趋势图");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfOrclDataFileBean> perfList = orclService
				.queryOrclDataFilePerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
				if (perf.equals("1")) {
					seriesSbf.append(perfList.get(i).getPhyReads() + ",");
				} else if (perf.equals("2")) {
					seriesSbf.append(perfList.get(i).getPhyWrites() + ",");
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
		logger.info("结束...加载数据文件性能数据");
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
	 * SGA图表页面跳转
	 * @return
	 */
	@RequestMapping("/toOrclSGALineChart")
	public ModelAndView toOrclSGALineChart(HttpServletRequest request,ModelMap map) throws Exception {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/orclManage/initOraclSGA");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}

	/**
	 * * SGA图表数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initOraclSGA")
	@ResponseBody
	public Map<String, Object> initOraclSGA(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载SGA图表数据");
		Map params = new HashMap();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("DeviceMOID", request.getParameter("moid"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		jsonData.setText(" ");// oracle SGA统计
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setLegend("空闲内存趋势(MB)");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		List<PerfOrclSGABean> perfList = orclService.queryOrclSGAPerf(params);
		int size = perfList.size();
		if (perfList != null && size > 0) {
			for (int i = 0; i < size; i++) {
				xAxisSbf.append(perfList.get(i).getTime() + ",");
				if (perf.equals("1")) {
					seriesSbf.append(perfList.get(i).getFreeMemory() + ",");
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
		logger.info("结束...加载SGA图表数据");
		return result;
	}
}
