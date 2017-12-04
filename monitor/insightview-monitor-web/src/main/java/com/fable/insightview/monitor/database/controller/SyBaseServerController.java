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

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDeviceBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MSSQLServerKPINameDef;
import com.fable.insightview.monitor.database.entity.PerfMSSQLServerBean;
import com.fable.insightview.monitor.database.entity.PerfSybaseServerBean;
import com.fable.insightview.monitor.database.entity.SybaseServerKPINameDef;
import com.fable.insightview.monitor.database.service.IMsServerService;
import com.fable.insightview.monitor.database.service.ISyBaseService;
import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.platform.common.util.JsonUtil;

@Controller
@RequestMapping("/monitor/sybaseManage")
public class SyBaseServerController {
	@Autowired
	IMsServerService msService;
	@Autowired
	ISyBaseService sybaseService;

	private final Logger logger = LoggerFactory
			.getLogger(SyBaseServerController.class);

	@RequestMapping("/toShowServerDetail")
	@ResponseBody
	public ModelAndView toShowServerDetail(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载服务详情页面");
		int MOID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serverMOID", MOID);
		params.put("moTableName", "MOSybaseServer");
		params.put("msMID", KPINameDef.JOBSYBASESERVERAVAILABLE);
		MODBMSServerBean ms = msService.getMsServerInfo(params);
		map.put("ms", ms);
		return new ModelAndView("monitor/database/sqlserver/msServerInfo");
	}

	/**
	 * 获取Server 可用率
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findSybaseChartAvailability")
	@ResponseBody
	public Map<String, Object> findSybaseChartAvailability(
			HttpServletRequest request) throws Exception {
		logger.info("根据数据库实例ID获取图表可用性使用率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", Integer.parseInt(request.getParameter("moID")));
		MODBMSServerBean mo = sybaseService.getSybaseChartAvailability(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (mo != null) {
			map1.put("value", mo.getDeviceavailability());
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("sybaseAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("sybaseAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}

	/**
	 * 查询数据库列表
	 * 
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
		request.setAttribute("type", "sybase");
		return new ModelAndView("monitor/database/sybase/sybaseDBInfoList");
	}

	/**
	 * 获取数据库列表
	 * 
	 * @return
	 */
	@RequestMapping("/getDBInfoList")
	@ResponseBody
	public Map<String, Object> getDBInfoList() throws Exception {
		logger.info("开始...加载数据库列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serverMOID", request.getParameter("moID"));
		params.put("dbTableName", "MOSybaseDatabase");
		params.put("serverTableName", "MOSybaseServer");
		params.put("kpiName", "SpaceUsage");
		List<MOMsSQLDatabaseBean> dbLst = msService.getDBListInfo(params);
		result.put("rows", dbLst);
		return result;
	}

	/**
	 * 查询数据库列表
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toShowDeviceList")
	@ResponseBody
	public ModelAndView toShowDeviceList(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载设备页面");
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		request.setAttribute("type", "sybase");
		return new ModelAndView("monitor/database/sqlserver/msDeviceInfoList");
	}

	@RequestMapping("/toShowDBDetail")
	@ResponseBody
	public ModelAndView toShowDBDetail(HttpServletRequest request, ModelMap map) {
		logger.info("加载数据库详情页面");
		int MOID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moID", MOID);
		params.put("dbTableName", "MOSybaseDatabase");
		params.put("kpiName", SybaseServerKPINameDef.SPACEUSAGE);
		MOMsSQLDatabaseBean ms = msService.getDBDetailInfo(params);
		map.put("ms", ms);
		return new ModelAndView("monitor/database/sybase/sybaseDBDetailInfo");
	}

	/**
	 * 获取数据库列表
	 * 
	 * @return
	 */
	@RequestMapping("/getServiceInfoList")
	@ResponseBody
	public Map<String, Object> getServiceInfoList() {
		logger.info("开始...加载数据库列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serverMOID", request.getParameter("moID"));
		params.put("deviceTableName", "MOSybaseDevice");
		params.put("serverTableName", "MOSybaseServer");
		List<MOMsSQLDeviceBean> dbLst = msService.getDeviceListInfo(params);
		result.put("rows", dbLst);
		return result;
	}

	/**
	 * 服务曲线页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toSybaseLineChart")
	public ModelAndView toSybaseLineChart(HttpServletRequest request,
			ModelMap map) {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/sybaseManage/initSybaseLine");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}

	/**
	 * 服务曲线图表数据
	 */
	@RequestMapping("/initSybaseLine")
	@ResponseBody
	public Map<String, Object> initSybaseLine(HttpServletRequest request) {
		logger.info("开始...加载服务曲线趋势图表数据");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		// 默认取最近24小时数据
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moid"));
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		String perf = request.getParameter("perfKind");
		if (perf.equals("1")) {
			jsonData.setText("活跃用户数统计 ");
			jsonData.setLegend("活跃用户数统计");
		} else if (perf.equals("2")) {
			jsonData.setText("用户连接数统计 ");
			jsonData.setLegend("用户连接数统计");
		}
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[{name:'"
				+ jsonData.getLegend() + "',type:'line',data:[");
		try {
			List<PerfSybaseServerBean> perfList = sybaseService
					.querySybaseServerPerf(params);
			int size = perfList.size();
			if (perfList != null && size > 0) {
				for (int i = 0; i < size; i++) {
					xAxisSbf.append(perfList.get(i).getFormatTime() + ",");
					if (perf.equals("1")) {
						seriesSbf.append(perfList.get(i).getActiveUser() + ",");
					} else if (perf.equals("2")) {
						seriesSbf.append(perfList.get(i).getUserConnections()
								+ ",");
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
	 * 
	 * @return
	 */
	@RequestMapping("/toServerLineChart")
	public ModelAndView toServerLineChart(HttpServletRequest request,
			ModelMap map) {
		String moId = request.getParameter("moID");
		map.put("perfKind", request.getParameter("perfKind"));
		map.put("moid", moId);
		map.put("proUrl", "/monitor/sybaseManage/initServer");
		return new ModelAndView("monitor/database/oracle/orclLineChart");
	}

	/**
	 * Server 数趋势数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initServer")
	@ResponseBody
	public Map<String, Object> initServer(HttpServletRequest request) {
		logger.info("开始...加载Server数趋势数据");
		Map<String, Object> params = new HashMap<String, Object>();
		params = queryBetweenTime(request, params);
		params.put("moID", request.getParameter("moid"));
		String perf = request.getParameter("perfKind");
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[");
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer lineDataA = null;
		StringBuffer lineDataB = null;
		StringBuffer lineDataC = null;
		StringBuffer lineDataD = null;
		StringBuffer lineDataE = null;
		if (perf.equals("1")) {
			lineDataA = new StringBuffer("{name:'外部事务数',type:'line',data:[");
			lineDataB = new StringBuffer("{name:'本地事务数',type:'line',data:[");
			lineDataC = new StringBuffer("{name:'事务数',type:'line',data:[");
			jsonData.setText("事务数统计");
			jsonData.setLegend("外部事务数,本地事务数,事务数");
		} else if (perf.equals("2")) {
			lineDataA = new StringBuffer("{name:'活跃锁个数',type:'line',data:[");
			lineDataB = new StringBuffer("{name:'活跃页级锁个数',type:'line',data:[");
			lineDataC = new StringBuffer("{name:'活跃表级锁个数',type:'line',data:[");
			jsonData.setText("活跃锁数统计");
			jsonData.setLegend("活跃锁个数,活跃页级锁个数,活跃表级锁个数");
		} else if (perf.equals("3")) {
			lineDataA = new StringBuffer("{name:'进程总数',type:'line',data:[");
			lineDataB = new StringBuffer("{name:'阻塞进程数数',type:'line',data:[");
			lineDataC = new StringBuffer("{name:'活跃进程数',type:'line',data:[");
			lineDataD = new StringBuffer("{name:'系统进程数',type:'line',data:[");
			lineDataE = new StringBuffer("{name:'空闲进程数',type:'line',data:[");
			jsonData.setText("进程数统计");
			jsonData.setLegend("进程总数,阻塞进程数,活跃进程数,系统进程数,空闲进程数");
		}
		try {
			List<PerfSybaseServerBean> serverChartList = sybaseService
					.querySybaseServerPerf(params);
			int size = serverChartList.size();

			if (serverChartList != null && size > 0) {
				for (int j = 0; j < size; j++) {
					xAxisSbf.append(serverChartList.get(j).getFormatTime()
							+ ",");
					if (perf.equals("1")) {
						lineDataA.append(serverChartList.get(j)
								.getExternalTransactionNum() + ",");
						lineDataB.append(serverChartList.get(j)
								.getLocalTransactionNum() + ",");
						lineDataC.append(serverChartList.get(j)
								.getTransactionNum() + ",");
					} else if (perf.equals("2")) {
						lineDataA.append(serverChartList.get(j)
								.getActiveLockNum() + ",");
						lineDataB.append(serverChartList.get(j)
								.getActivePageLockNum() + ",");
						lineDataC.append(serverChartList.get(j)
								.getActiveLockNum() + ",");
					} else if (perf.equals("3")) {
						lineDataA.append(serverChartList.get(j)
								.getTotalSysProcesses() + ",");
						lineDataB.append(serverChartList.get(j)
								.getBlockingSysProcesses() + ",");
						lineDataC.append(serverChartList.get(j)
								.getActiveProcesses() + ",");
						lineDataD.append(serverChartList.get(j)
								.getSysProcesses() + ",");
						lineDataE.append(serverChartList.get(j)
								.getIdleSysProcesses() + ",");
					}
				}
				xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
				lineDataA.deleteCharAt(lineDataA.length() - 1);
				lineDataB.deleteCharAt(lineDataB.length() - 1);
				lineDataC.deleteCharAt(lineDataC.length() - 1);
				if (perf.equals("1") || perf.equals("2")) {
					seriesSbf.append(lineDataA.toString() + "]},"
							+ lineDataB.toString() + "]},"
							+ lineDataC.toString() + "]}]");
				} else if (perf.equals("3")) {
					lineDataD.deleteCharAt(lineDataD.length() - 1);
					lineDataE.deleteCharAt(lineDataE.length() - 1);
					seriesSbf.append(lineDataA.toString() + "]},"
							+ lineDataB.toString() + "]},"
							+ lineDataC.toString() + "]},"
							+ lineDataD.toString() + "]},"
							+ lineDataE.toString() + "]}]");
				}

				jsonData.setxAxisData(xAxisSbf.toString());
				jsonData.setSeriesData(seriesSbf.toString());
			}

			String jsonServerChartData = JsonUtil.toString(jsonData);
			logger.info("jsonData>>>>>>>>>" + jsonServerChartData);
			result.put("oracleChartData", jsonServerChartData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...Server 数趋势数据");
		return result;
	}

	/**
	 * server 事物饼图
	 */
	@RequestMapping("/findTransactionPie")
	public Map<String, Object> findTransactionPie(HttpServletRequest request,
			ModelMap map) {
		logger.info("事物使用情况");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> mapName = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		StringBuffer pieName = new StringBuffer();
		StringBuffer pieJson = new StringBuffer();
		params.put("moID", moID);
		String[] snapName = { "外部事务数", "本地事务数" };
		String[] kpiName = { SybaseServerKPINameDef.EXTERNALTRANSACTIONNUM,
				SybaseServerKPINameDef.LOCALTRANSACTIONNUM };

		for (int i = 0; i < snapName.length; i++) {
			pieName.append(",'" + snapName[i] + "'");
			params.put("kpiName", kpiName[i]);
			PerfSybaseServerBean db = sybaseService.getPerfValue(params);
			if (db != null) {
				pieJson.append(",{value:" + db.getPerfValue() + ",name:'"
						+ snapName[i] + "'}");// 拼接json数据
			} else {
				pieJson.append(",{value:'',name:'" + snapName[i] + "'}");// 拼接json数据
			}

		}
		pieName.deleteCharAt(0);
		if (pieJson.length() != 0) {
			pieJson.deleteCharAt(0);
		}

		// map1.put("textName","缓存页数统计");
		array1.add(pieJson);
		array2.add(pieName);
		map2.put("msTransPie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;
	}

	/**
	 * server 进程 饼图
	 */
	@RequestMapping("/findProcessesPie")
	public Map<String, Object> findProcessesPie(HttpServletRequest request,
			ModelMap map) {
		logger.info("进程使用情况");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> mapName = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		StringBuffer pieName = new StringBuffer();
		StringBuffer pieJson = new StringBuffer();
		params.put("moID", moID);
		String[] snapName = { "阻塞进程数", "活跃进程数", "系统进程数", "空闲进程数" };
		String[] kpiName = { SybaseServerKPINameDef.BLOCKINGSYSPROCESSES,
				SybaseServerKPINameDef.ACTIVEPROCESSES,
				SybaseServerKPINameDef.SYSPROCESSES,
				SybaseServerKPINameDef.IDLESYSPROCESSES };

		for (int i = 0; i < snapName.length; i++) {
			pieName.append(",'" + snapName[i] + "'");
			params.put("kpiName", kpiName[i]);
			PerfSybaseServerBean db = sybaseService.getPerfValue(params);
			if (db != null) {
				pieJson.append(",{value:" + db.getPerfValue() + ",name:'"
						+ snapName[i] + "'}");// 拼接json数据
			} else {
				pieJson.append(",{value:'',name:'" + snapName[i] + "'}");// 拼接json数据
			}

		}
		pieName.deleteCharAt(0);
		if (pieJson.length() != 0) {
			pieJson.deleteCharAt(0);
		}

		// map1.put("textName","缓存页数统计");
		array1.add(pieJson);
		array2.add(pieName);
		map2.put("msProcessPie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;
	}

	@RequestMapping("/toShowDBPageDetail")
	@ResponseBody
	public ModelAndView toShowDBPageDetail(HttpServletRequest request,
			ModelMap map) {
		logger.info("加载数据库读写效率详情页面");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("MOID", moID);
		params.put("TxLog", SybaseServerKPINameDef.TXLOG);
		params.put("PackReceive", SybaseServerKPINameDef.PACKRECEIVE);
		params.put("PackSent", SybaseServerKPINameDef.PACKSENT);
		params.put("PacketErrors", SybaseServerKPINameDef.PACKETERRORS);
		params.put("TotalDiskRead", SybaseServerKPINameDef.TOTALDISKREAD);
		params.put("TotalDiskWrite", SybaseServerKPINameDef.TOTALDISKWRITE);
		params.put("TotalDiskErrors", SybaseServerKPINameDef.TOTALDISKERRORS);
		params.put("CpuBusy", SybaseServerKPINameDef.CPUBUSY);
		params.put("CpuIdle", SybaseServerKPINameDef.CPUIDLESERV);
		PerfSybaseServerBean db = sybaseService.getDatabaseDetail(params);
		map.put("db", db);
		return new ModelAndView("monitor/database/sybase/sybaseDBPageInfo");
	}

	/**
	 * 设置查询时间(公共调用方法)
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	public Map<String, Object> queryBetweenTime(HttpServletRequest request,
			Map<String, Object> params) {
		String time = request.getParameter("time");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		String timeEnd = f.format(d.getTime());
		String timeBegin = "";

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

	/**
	 * 提供默认SybaseServer
	 */
	@RequestMapping("/initSybaseServerName")
	@ResponseBody
	public MOMySQLDBServerBean initSybaseServerName(HttpServletRequest request) {
		MOMySQLDBServerBean msBean = sybaseService.getFirstBean();
		return msBean;
	}

	/**
	 * 提供默认SybaseServer
	 */
	@RequestMapping("/findSybaseServerInfo")
	@ResponseBody
	public MOMySQLDBServerBean findSybaseServerInfo(Integer moId) {
		MOMySQLDBServerBean msBean = sybaseService.getSybaseServerById(moId);
		return msBean;
	}
}
