package com.fable.insightview.monitor.host.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
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

import com.fable.insightview.monitor.host.entity.ChartColItem;
import com.fable.insightview.monitor.host.entity.CurveObj;
import com.fable.insightview.monitor.host.entity.ECharObj;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.host.entity.Mine2Obj;
import com.fable.insightview.monitor.host.entity.MineObj;
import com.fable.insightview.monitor.host.mapper.HostMapper;
import com.fable.insightview.monitor.utils.DecimalForTwo;
import com.fable.insightview.platform.common.util.JsonUtil;

/**
 * @Description: 设备接口图表统计
 * @author zhengxh
 * @Date 2014-8-13
 */
@Controller
@RequestMapping("/monitor/interfaceChart")
public class InterfaceChartController {
	@Autowired
	HostMapper modMaper;
	String moClass;// 设备类型ID
	String tableName;// 表面
	Integer MOID;
	Integer moClassID;
	String ifTableName;// 接口表名
	private final Logger logger = LoggerFactory
			.getLogger(InterfaceChartController.class);
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 表示曲线最大值、最小值
	private String markPoint = "markPoint:{data:[{type : 'max', name: '最大值'},{type : 'min', name: '最小值'}]}";
	DecimalForTwo decimalForTwo = new DecimalForTwo();
	DecimalFormat   format = new DecimalFormat("0.00");
	/**
	 * 根据类型获取moClassID
	 * 
	 * @param moClass
	 */
	public Integer getmoClassID(String moClass) {
		if ("host".equals(moClass) || "null".equals(moClass) || moClass == null) {
			moClassID = 7;
		} else if ("vm".equals(moClass)) {
			moClassID = 9;
		} else if ("vhost".equals(moClass)) {
			moClassID = 8;
		} else if ("switcher".equals(moClass)) {
			moClassID = 6;
		} else if ("router".equals(moClass)) {
			moClassID = 5;
		} else if ("switcherl2".equals(moClass)) {
			moClassID = 59;
		} else if ("switcherl3".equals(moClass)) {
			moClassID = 60;
		}
		return moClassID;
	}

	/**
	 * 根据设备类型 查询接口表名
	 * 
	 * @param moClass
	 * @return
	 */
	public String getIfTableName(String moClass) {
		if ("host".equals(moClass) || "null".equals(moClass) || moClass == null
				|| "switcher".equals(moClass) || "router".equals(moClass)
				|| "switcherl2".equals(moClass) || "switcherl3".equals(moClass)) {
			ifTableName = "PerfSnapshotNetDevice";
		} else if ("vm".equals(moClass)) {
			ifTableName = "PerfSnapshotVM";
		} else if ("vhost".equals(moClass)) {
			ifTableName = "PerfSnapshotVHost";
		}
		return ifTableName;
	}

	/**
	 * 跳转主机接口(接口流入量,接口流出量)
	 */
	@RequestMapping("/toMainInterfaceIOFlows")
	public ModelAndView toMainInterfaceIOFlows(HttpServletRequest request,
			ModelMap map) {
		String MOID = request.getParameter("IfMOID");
		map.put("interfaceId", MOID);
		map.put("proUrl", "/monitor/interfaceChart/mainInterfaceFlows");
		return new ModelAndView("monitor/host/mainInterface_IOFlows");
	}

	/**
	 * 跳转主机接口(接口流入丢包数,接口流出丢包数)
	 */
	@RequestMapping("/toMainInterfaceLossflows")
	public ModelAndView toMainInterfaceLossflows(HttpServletRequest request,
			ModelMap map) {
		String MOID = request.getParameter("IfMOID");
		map.put("interfaceId", MOID);
		map.put("proUrl", "/monitor/interfaceChart/mainInterfaceFlows");
		return new ModelAndView("monitor/host/mainInterface_LossFlows");
	}

	/**
	 * 跳转主机接口(接口流入错包数,接口流出错包数)
	 */
	@RequestMapping("/toMainInterfaceErrorsFlows")
	public ModelAndView toMainInterfaceErrorsFlows(HttpServletRequest request,
			ModelMap map) {
		String MOID = request.getParameter("IfMOID");
		map.put("interfaceId", MOID);
		map.put("proUrl", "/monitor/interfaceChart/mainInterfaceFlows");
		return new ModelAndView("monitor/host/mainInterface_ErrorsFlows");
	}

	/**
	 * * 主机接口图表数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/mainInterfaceFlows")
	@ResponseBody
	public Map<String, Object> mainInterfaceFlows(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载主机接口图表数据");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("MOID"));
		params.put("tableName", "MONetworkIf");
		params.put("perfTabName", "PerfNetworkIf");
		// 存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		String perf = request.getParameter("perfKind");
		String nameA = "";
		String nameB = "";

		if (perf.equals("1")) {
			jsonData.setText("接口流量统计 (Kbps)");
			jsonData.setLegend("接口流入量,接口流出量");
			nameA = "接口流入量";
			nameB = "接口流出量";
		} else if (perf.equals("2")) {
			jsonData.setText("接口丢包统计");
			jsonData.setLegend("接口流入丢包数,接口流出丢包数");
			nameA = "接口流入丢包数";
			nameB = "接口流出丢包数";
		} else if (perf.equals("3")) {
			jsonData.setText("接口错包统计");
			jsonData.setLegend("接口流入错包数,接口流出错包数");
			nameA = "接口流入错包数";
			nameB = "接口流出错包数";
		}
		StringBuffer axisTime = new StringBuffer("");

		// 获取所有主机接口历史的数据
		List<MODevice> allMainInterfaceData = modMaper
				.queryInterfaceDataList(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// 主机接口历史Id collection
		Set<Integer> mainInterfaceIdSet = new TreeSet();
		// 获取主机接口历史的MOID和MONAME
		Map<Integer, String> mainInterfaceMap = new HashMap<Integer, String>();
		// 获取所有相对应的主机接口历史中的数据
		Map<String, MODevice> allMainInterface = new HashMap<String, MODevice>();
		// 临时数据
		Map<Integer, String> tmpinFlowDataMap = new HashMap<Integer, String>();
		Map<Integer, String> tmpoutFlowDataMap = new HashMap<Integer, String>();
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>();
		// 构建时间对象,主机接口历史Id集合...
		for (int i = 0; i < allMainInterfaceData.size(); i++) {
			// 放入时间节点
			timeSet.add(simple.format(allMainInterfaceData.get(i)
					.getCollecttime()));
			// 放入主机接口历史 ID节点
			mainInterfaceIdSet.add(allMainInterfaceData.get(i).getMoid());
			// 放入主机接口历史 数据节点
			mainInterfaceMap.put(allMainInterfaceData.get(i).getMoid(),
					allMainInterfaceData.get(i).getMoname());
			// 重新组织主机接口历史数据map
			String key = String.valueOf(allMainInterfaceData.get(i).getMoid())
					+ "_"
					+ simple.format(allMainInterfaceData.get(i)
							.getCollecttime());
			allMainInterface.put(key, allMainInterfaceData.get(i));
		}

		// 构造相关主机接口历史数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer mainInterfaceId : mainInterfaceIdSet) {
				String mapKey = mainInterfaceId + "_" + timePoint;
				// 判断mapKey在allMainInterface中是否有相关数据
				if (allMainInterface.containsKey(mapKey)) {
					if (tmpinFlowDataMap.get(mainInterfaceId) != null
							&& tmpoutFlowDataMap.get(mainInterfaceId) != null) {
						String inFlowdataValue = tmpinFlowDataMap
								.get(mainInterfaceId);
						String outFlowdataValue = tmpoutFlowDataMap
								.get(mainInterfaceId);
						if (perf.equals("1")) {
							inFlowdataValue += decimalForTwo
									.DecimalFormatString(allMainInterface.get(
											mapKey).getInflows())
									+ ",";
							outFlowdataValue += decimalForTwo
									.DecimalFormatString(allMainInterface.get(
											mapKey).getOutflows())
									+ ",";
						} else if (perf.equals("2")) {
							inFlowdataValue += allMainInterface.get(mapKey)
									.getInflowloss() + ",";
							outFlowdataValue += allMainInterface.get(mapKey)
									.getOutflowloss() + ",";
						} else if (perf.equals("3")) {
							inFlowdataValue += allMainInterface.get(mapKey)
									.getInflowerrors() + ",";
							outFlowdataValue += allMainInterface.get(mapKey)
									.getOutflowerrors() + ",";
						}
						tmpinFlowDataMap.put(mainInterfaceId, inFlowdataValue);
						tmpoutFlowDataMap
								.put(mainInterfaceId, outFlowdataValue);
					} else {
						if (perf.equals("1")) {
							tmpinFlowDataMap
									.put(mainInterfaceId,
											decimalForTwo
													.DecimalFormatString(allMainInterface
															.get(mapKey)
															.getInflows())
													+ ",");
							tmpoutFlowDataMap
									.put(mainInterfaceId,
											decimalForTwo
													.DecimalFormatString(allMainInterface
															.get(mapKey)
															.getOutflows())
													+ ",");
						} else if (perf.equals("2")) {
							tmpinFlowDataMap.put(mainInterfaceId,
									allMainInterface.get(mapKey)
											.getInflowloss() + ",");
							tmpoutFlowDataMap.put(mainInterfaceId,
									allMainInterface.get(mapKey)
											.getOutflowloss() + ",");
						} else if (perf.equals("3")) {
							tmpinFlowDataMap.put(mainInterfaceId,
									allMainInterface.get(mapKey)
											.getInflowerrors() + ",");
							tmpoutFlowDataMap.put(mainInterfaceId,
									allMainInterface.get(mapKey)
											.getOutflowerrors() + ",");
						}
					}
				} else {
					if (tmpinFlowDataMap.get(mainInterfaceId) != null) {
						String inFlowdataValue = tmpinFlowDataMap
								.get(mainInterfaceId);
						String[] dataArray = inFlowdataValue.split(",");
						tmpinFlowDataMap.put(mainInterfaceId, inFlowdataValue
								+ "" + dataArray[dataArray.length - 1] + ",");
					} else {
						tmpinFlowDataMap.put(mainInterfaceId, "0,");
					}
					if (tmpoutFlowDataMap.get(mainInterfaceId) != null) {
						String outFlowdataValue = tmpoutFlowDataMap
								.get(mainInterfaceId);
						String[] dataArray = outFlowdataValue.split(",");
						tmpoutFlowDataMap.put(mainInterfaceId, outFlowdataValue
								+ "" + dataArray[dataArray.length - 1] + ",");
					} else {
						tmpoutFlowDataMap.put(mainInterfaceId, "0,");
					}
				}
			}
		}

		// 组织主机接口历史数据
		String legend = "";
		List<Float> mainInterfaceDatalist = null;
		CurveObj curveObj = null;
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		// 找出主机接口历史流入使用率对应数据
		for (Entry<Integer, String> entry : mainInterfaceMap.entrySet()) {
			curveObj = new CurveObj();
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			mainInterfaceDatalist = new ArrayList<Float>();

			curveObj.setName(nameA);
			curveObj.setType("line");

			// 找出主机接口历史流入使用率对应数据
			String[] inFlowArr = tmpinFlowDataMap.get(entry.getKey())
					.split(",");
			for (int i = 0; i < inFlowArr.length; i++) {
				mainInterfaceDatalist.add(Float.parseFloat(inFlowArr[i]));
			}
			curveObj.setData(mainInterfaceDatalist);
			// 展示部件时不显示曲线最值
			if (params.get("markPoint") != null) {
				mineList.add(new Mine2Obj("min", "最小值"));
				mineList.add(new Mine2Obj("max", "最大值"));
				mineObj.setData(mineList);
				curveObj.setMarkPoint(mineObj);
			}
			seriesData.add(curveObj);
		}

		// 找出主机接口历史流出使用率对应数据
		for (Entry<Integer, String> entry : mainInterfaceMap.entrySet()) {
			curveObj = new CurveObj();
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			mainInterfaceDatalist = new ArrayList<Float>();

			curveObj.setName(nameB);
			curveObj.setType("line");

			// 找出主机接口历史流出使用率对应数据
			String[] outFlowArr = tmpoutFlowDataMap.get(entry.getKey()).split(
					",");
			for (int i = 0; i < outFlowArr.length; i++) {
				mainInterfaceDatalist.add(Float.parseFloat(outFlowArr[i]));
			}

			curveObj.setData(mainInterfaceDatalist);
			// 展示部件时不显示曲线最值
			if (params.get("markPoint") != null) {
				mineList.add(new Mine2Obj("min", "最小值"));
				mineList.add(new Mine2Obj("max", "最大值"));
				mineObj.setData(mineList);
				curveObj.setMarkPoint(mineObj);
			}
			seriesData.add(curveObj);
		}

		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(
				axisTime.length() - 1).toString() : "");

		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData)
						.toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载主机接口图表数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;

	}

	/**
	 * 跳转虚拟机接口,宿主机接口(发送速度,接收速度)两者使用表一样
	 */
	@RequestMapping("/toVmHostInterfaceSRFlows")
	public ModelAndView toVmHostInterfaceSRFlows(HttpServletRequest request,
			ModelMap map) {
		String MOID = request.getParameter("IfMOID");
		map.put("interfaceId", MOID);
		map.put("proUrl", "/monitor/interfaceChart/vmHostInterfaceSRFlows");
		return new ModelAndView("monitor/host/vmHostInterface_SRFlows");
	}

	/**
	 * * 虚拟机接口,宿主机接口图表数据(发送速度,接收速度)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/vmHostInterfaceSRFlows")
	@ResponseBody
	public Map<String, Object> vmHostInterfaceSRFlows(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载虚拟机接口,宿主机接口图表数据(发送速度,接收速度)");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("MOID"));
		params.put("tableName", "MONetworkIf");
		// 存放返回页面json数据
		ChartColItem jsonData = new ChartColItem();
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[");
		StringBuffer lineDataA = new StringBuffer(
				"{name:'发送速度',type:'line',data:[");
		StringBuffer lineDataB = new StringBuffer(
				"{name:'接收速度',type:'line',data:[");
		jsonData.setText("接口流量统计 (Kbps)");
		jsonData.setLegend("发送速度,接收速度");

		List<MODevice> interfaceChartList = modMaper
				.virtualInterfaceList(params);
		int size = interfaceChartList.size();

		if (interfaceChartList != null && size > 0) {
			for (int j = 0; j < size; j++) {
				xAxisSbf.append(interfaceChartList.get(j).getCollTime() + ",");
				lineDataA.append(interfaceChartList.get(j)
						.getTransmittedspeed() + ",");
				lineDataB.append(interfaceChartList.get(j).getReceivedspeed()
						+ ",");
			}
			xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
			lineDataA.deleteCharAt(lineDataA.length() - 1);
			lineDataB.deleteCharAt(lineDataB.length() - 1);

			if (params.get("markPoint") != null) {
				seriesSbf.append(lineDataA.toString() + "]," + markPoint + "},"
						+ lineDataB.toString() + "]," + markPoint + "}]");
			} else {
				seriesSbf.append(lineDataA.toString() + "]},"
						+ lineDataB.toString() + "]}]");
			}
			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}

		String jsonInterfaceChartData = JsonUtil.toString(jsonData);
		logger.debug("jsonData>>>>>>>>>" + jsonInterfaceChartData);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("jsonChartData", jsonInterfaceChartData);

		logger.info("结束...加载虚拟机接口,宿主机接口图表数据(发送速度,接收速度)");
		return result;
	}

	/**
	 * 跳转虚拟机接口,宿主机接口(接口使用率)
	 */
	@RequestMapping("/toVmHostInterfaceUseFlows")
	public ModelAndView toVmHostInterfaceUseFlows(HttpServletRequest request,
			ModelMap map) {
		String MOID = request.getParameter("IfMOID");
		map.put("interfaceId", MOID);
		map.put("proUrl", "/monitor/interfaceChart/vmHostInterfaceUseFlows");
		return new ModelAndView("monitor/host/vmHostInterface_UseFlows");
	}

	/**
	 * * 虚拟机接口,宿主机接口图表数据(接口使用率)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/vmHostInterfaceUseFlows")
	@ResponseBody
	public Map<String, Object> vmHostInterfaceUseFlows(
			HttpServletRequest request) throws Exception {
		logger.info("开始...虚拟机接口,宿主机接口图表数据(接口使用率)");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("MOID"));
		params.put("tableName", "MONetworkIf");
		ChartColItem jsonData = new ChartColItem();// 存放返回页面json数据
		StringBuffer xAxisSbf = new StringBuffer("");
		StringBuffer seriesSbf = new StringBuffer("[");
		StringBuffer lineDataA = new StringBuffer(
				"{name:'接口使用率',type:'line',data:[");
		jsonData.setText("接口流量统计 (%)");
		jsonData.setLegend("接口使用率");

		List<MODevice> interfaceChartList = modMaper
				.virtualInterfaceList(params);
		int size = interfaceChartList.size();

		if (interfaceChartList != null && size > 0) {
			for (int j = 0; j < size; j++) {
				xAxisSbf.append(interfaceChartList.get(j).getCollTime() + ",");
				lineDataA.append(interfaceChartList.get(j).getNetworkusage()
						+ ",");
			}
			xAxisSbf.deleteCharAt(xAxisSbf.length() - 1);
			lineDataA.deleteCharAt(lineDataA.length() - 1);

			if (params.get("markPoint") != null) {
				seriesSbf
						.append(lineDataA.toString() + "]," + markPoint + "}]");
			} else {
				seriesSbf.append(lineDataA.toString() + "]}]");
			}
			jsonData.setxAxisData(xAxisSbf.toString());
			jsonData.setSeriesData(seriesSbf.toString());
		}

		String jsonInterfaceChartData = JsonUtil.toString(jsonData);
		logger.debug("jsonData>>>>>>>>>" + jsonInterfaceChartData);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("jsonChartData", jsonInterfaceChartData);

		logger.info("结束...虚拟机接口,宿主机接口图表数据(接口使用率)");
		return result;
	}

	/**
	 * 设置查询时间,默认当前24小时
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map queryBetweenTime(HttpServletRequest request, Map params) {
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");

		if (timeBegin == null || timeBegin == "" || timeEnd == null
				|| timeEnd == "") {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = new Date();
			timeBegin = f.format(d.getTime() - 24 * 60 * 60 * 1000);
			timeEnd = f.format(d);
		}

		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		// 表示是否展示最大值、最小值
		params.put("markPoint", request.getParameter("markPoint"));
		return params;
	}

	/**
	 * 图表详情查询页面
	 */
	@RequestMapping("/toHistoryChartsDetail")
	public ModelAndView toHistoryChartsDetail(HttpServletRequest request,
			ModelMap map) throws Exception {
		String perfKind = request.getParameter("perfKind");// 显示指标的类型
		String proUrl = request.getParameter("proUrl");// 获取数据的url
		String MOID = request.getParameter("MOID");// 获取数据的MOID

		map.put("perfKind", perfKind);
		map.put("proUrl", proUrl);
		map.put("MOID", MOID);
		return new ModelAndView("monitor/host/historyChart_detail");
	}

	/**
	 * * 主机接口使用率图表数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/mainInterfaceUsage")
	@ResponseBody
	public Map<String, Object> mainInterfaceUsage(HttpServletRequest request)
			throws Exception {
		logger.info("开始...加载主机接口使用率图表数据");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("MOID"));
		params.put("tableName", "MONetworkIf");
		params.put("perfTabName", "PerfNetworkIf");
		params.put("IfOperStatus", "1");
		// 存放返回页面json数据
		ECharObj jsonData = new ECharObj();
		String perf = request.getParameter("perfKind");
		String nameA = "";

		if (perf.equals("1")) {
			jsonData.setText("接口流入使用率(%)");
			jsonData.setLegend("接口流入使用率");
			nameA = "接口流入使用率";
		} else if (perf.equals("2")) {
			jsonData.setText("接口流出使用率(%)");
			jsonData.setLegend("接口流出使用率");
			nameA = "接口流出使用率";
		} else if (perf.equals("3")) {
			jsonData.setText("总带宽使用率(%)");
			jsonData.setLegend("总带宽使用率");
			nameA = "总带宽使用率";
		}
		StringBuffer axisTime = new StringBuffer("");

		// 获取所有主机接口历史信息的数据
		List<MODevice> allMainInterfaceData = modMaper
				.queryInterfaceDataList(params);
		// 时间节点
		Set<String> timeSet = new TreeSet();
		// cpuId collection
		Set<Integer> mainInterfaceIdSet = new TreeSet();
		// 获取主机接口历史信息的MOID和MONAME
		Map<Integer, String> mainInterfaceMap = new HashMap<Integer, String>();
		// 获取所有相对应的主机接口历史信息中的数据
		Map<String, MODevice> allMainInterface = new HashMap<String, MODevice>();
		// 临时数据
		Map<Integer, String> tmpDataMap = new HashMap<Integer, String>();
		// 构造Y轴的数据
		List<CurveObj> seriesData = new ArrayList<CurveObj>();
		// 构建时间对象,cpuId集合...
		for (int i = 0; i < allMainInterfaceData.size(); i++) {
			// 放入时间节点
			timeSet.add(simple.format(allMainInterfaceData.get(i)
					.getCollecttime()));
			// 放入主机接口历史信息 ID节点
			mainInterfaceIdSet.add(allMainInterfaceData.get(i).getMoid());
			// 放入主机接口历史信息数据节点
			mainInterfaceMap.put(allMainInterfaceData.get(i).getMoid(),
					allMainInterfaceData.get(i).getMoname());
			// 重新组织主机接口历史信息数据map
			String key = String.valueOf(allMainInterfaceData.get(i).getMoid())
					+ "_"
					+ simple.format(allMainInterfaceData.get(i)
							.getCollecttime());
			allMainInterface.put(key, allMainInterfaceData.get(i));
		}

		// 构造相关数据
		for (String timePoint : timeSet) {
			axisTime.append(timePoint + ",");
			for (Integer mainInterfaceId : mainInterfaceIdSet) {
				String mapKey = mainInterfaceId + "_" + timePoint;
				// 判断mapKey在allCpuData中是否有相关数据
				if (allMainInterface.containsKey(mapKey)) {
					if (tmpDataMap.get(mainInterfaceId) != null) {
						String dataValue = tmpDataMap.get(mainInterfaceId);
						if (perf.equals("1")) {
							dataValue += decimalForTwo
									.DecimalFormatString(allMainInterface.get(
											mapKey).getInusage())
									+ ",";
						} else if (perf.equals("2")) {
							dataValue += decimalForTwo
									.DecimalFormatString(allMainInterface.get(
											mapKey).getOutusage())
									+ ",";
						} else if (perf.equals("3")) {
							dataValue += decimalForTwo
									.DecimalFormatFloat(allMainInterface.get(
											mapKey).getIousage())
									+ ",";
						}
						tmpDataMap.put(mainInterfaceId, dataValue);
					} else {
						if (perf.equals("1")) {
							tmpDataMap
									.put(mainInterfaceId,
											decimalForTwo
													.DecimalFormatString(allMainInterface
															.get(mapKey)
															.getInusage())
													+ ",");
						} else if (perf.equals("2")) {
							tmpDataMap
									.put(mainInterfaceId,
											decimalForTwo
													.DecimalFormatString(allMainInterface
															.get(mapKey)
															.getOutusage())
													+ ",");
						} else if (perf.equals("3")) {
							tmpDataMap
									.put(mainInterfaceId,
											decimalForTwo
													.DecimalFormatFloat(allMainInterface
															.get(mapKey)
															.getIousage())
													+ ",");
						}
					}
				} else {
					if (tmpDataMap.get(mainInterfaceId) != null) {
						String dataValue = tmpDataMap.get(mainInterfaceId);
						String[] dataArray = dataValue.split(",");
						tmpDataMap.put(mainInterfaceId, dataValue + ""
								+ dataArray[dataArray.length - 1] + ",");
					} else {
						tmpDataMap.put(mainInterfaceId, "0,");
					}
				}
			}
		}

		// 组织主机接口历史信息数据
		String legend = "";
		List<Float> mainInterfaceDatalist = null;
		CurveObj curveObj = null;
		MineObj mineObj = null;
		List<Mine2Obj> mineList = null;
		for (Entry<Integer, String> entry : mainInterfaceMap.entrySet()) {
			curveObj = new CurveObj();
			mineObj = new MineObj();
			mineList = new ArrayList<Mine2Obj>();
			mainInterfaceDatalist = new ArrayList<Float>();

			curveObj.setName(entry.getValue());
			curveObj.setType("line");
			// 主机接口历史信息 name arr[]
			legend += entry.getValue() + ",";

			// 找出主机接口历史信息对应数据
			String[] arr = tmpDataMap.get(entry.getKey()).split(",");
			for (int i = 0; i < arr.length; i++) {
				mainInterfaceDatalist.add(Float.parseFloat(arr[i]));
			}

			curveObj.setData(mainInterfaceDatalist);
			// 展示部件时不显示曲线最值
			if (params.get("markPoint") != null) {
				mineList.add(new Mine2Obj("min", "最小值"));
				mineList.add(new Mine2Obj("max", "最大值"));
				mineObj.setData(mineList);
				curveObj.setMarkPoint(mineObj);
			}
			seriesData.add(curveObj);
		}

		// remove last ","
		jsonData.setLegend(legend.length() > 0 ? legend.substring(0,
				legend.length() - 1) : legend);
		jsonData.setxAxisData(axisTime.length() > 0 ? axisTime.deleteCharAt(
				axisTime.length() - 1).toString() : "");

		if (seriesData != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				jsonData.setSeriesData(mapper.writeValueAsString(seriesData)
						.toString());
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("jsonChartData", mapper.writeValueAsString(jsonData));
				logger.info("结束...加载主机接口使用率图表数据");
				return result;
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}

	/**
	 * *************************************************hl接口********************
	 */

	@RequestMapping("/toVHostOrVMDetailList")
	public ModelAndView toVHostOrVMDetailList(HttpServletRequest request) {
		MOID = Integer.parseInt(request.getParameter("moID"));
		moClass = request.getParameter("moClass");
		return new ModelAndView("monitor/host/vhostOrVM");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/findVHostOrVM")
	@ResponseBody
	public Map<String, Object> findVHostOrVM() throws Exception {
		logger.info("开始...加载接口数据");
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		// String moClass = request.getParameter("moClass");
		getmoClassID(moClass);
		getIfTableName(moClass);
		String kpiNames = "'" + KPINameDef.IFOPERSTATUS + "','"
				+ KPINameDef.INFLOWS + "','" + KPINameDef.OUTFLOWS + "','"
				+ KPINameDef.INPACKETS + "','" + KPINameDef.OUTPACKETS + "'";

		params.put("moClassID", moClassID);
		params.put("MOID", MOID);
		params.put("tableName", ifTableName);
		params.put("kpiNames", kpiNames);

		List<MODevice> moLsinfo = modMaper.getNetworkIf(params);
		logger.info("未去重的数组的长度====" + moLsinfo.size());

		Map<Integer, MODevice> resultMap = new HashMap<Integer, MODevice>();
		for (MODevice moDevice : moLsinfo) {
			int ifMOID = moDevice.getIfMOID();
			// int ifDeviceMOID = moDevice.getIfDeviceMOID();
			if (resultMap.get(ifMOID) != null) {
				MODevice keyMoDevice = resultMap.get(ifMOID);
				if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
					keyMoDevice.setInflows(moDevice.getPerfvalue() + "");
				} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
					keyMoDevice.setOutflows(moDevice.getPerfvalue() + "");
				} else if (KPINameDef.INPACKETS.equals(moDevice.getKpiname())) {
					keyMoDevice.setInpackets(moDevice.getPerfvalue());
				} else if (KPINameDef.OUTPACKETS.equals(moDevice.getKpiname())) {
					keyMoDevice.setOutpackets(moDevice.getPerfvalue());
				} else if (KPINameDef.IFOPERSTATUS
						.equals(moDevice.getKpiname())) {
					if (KPINameDef.DEVICE_UP.equals(moDevice.getPerfvalue()
							+ "")) {
						keyMoDevice.setOperstatus("up.png");
						keyMoDevice.setOperaTip("UP");
					} else if (KPINameDef.DEVICE_DOWN.equals(moDevice
							.getPerfvalue() + "")) {
						keyMoDevice.setOperstatus("down.png");
						keyMoDevice.setOperaTip("DOWN");
					} else {
						keyMoDevice.setOperstatus("unknown.png");
						keyMoDevice.setOperaTip("未知");
					}
				}
			} else {
				if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
					moDevice.setInflows(moDevice.getPerfvalue() + "");
				} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
					moDevice.setOutflows(moDevice.getPerfvalue() + "");
				} else if (KPINameDef.INPACKETS.equals(moDevice.getKpiname())) {
					moDevice.setInpackets(moDevice.getPerfvalue());
				} else if (KPINameDef.OUTPACKETS.equals(moDevice.getKpiname())) {
					moDevice.setOutpackets(moDevice.getPerfvalue());
				} else if (KPINameDef.IFOPERSTATUS
						.equals(moDevice.getKpiname())) {
					if (KPINameDef.DEVICE_UP.equals(moDevice.getPerfvalue()
							+ "")) {
						moDevice.setOperstatus("up.png");
						moDevice.setOperaTip("UP");
					} else if (KPINameDef.DEVICE_DOWN.equals(moDevice
							.getPerfvalue() + "")) {
						moDevice.setOperstatus("down.png");
						moDevice.setOperaTip("DOWN");
					} else {
						moDevice.setOperstatus("unknown.png");
						moDevice.setOperaTip("未知");
					}
				}
				resultMap.put(ifMOID, moDevice);
			}
		}

		List<MODevice> resultList = new ArrayList<MODevice>();
		for (Iterator it = resultMap.keySet().iterator(); it.hasNext();) {
			resultList.add(resultMap.get(it.next()));
		}
		logger.info("resultList的長度" + resultList.size());
		result.put("rows", resultList);
		return result;
	}

	@RequestMapping("/toHostOrNetWorkDetailList")
	public ModelAndView toHostOrNetWorkDetailList(HttpServletRequest request) {
		MOID = Integer.parseInt(request.getParameter("moID"));
		moClass = request.getParameter("moClass");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		return new ModelAndView("monitor/host/hostOrNetWork");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/findHostOrNetWork")
	@ResponseBody
	public Map<String, Object> findHostOrNetWork() throws Exception {
		logger.info("开始...加载接口数据");
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		logger.debug("param 表名称[tableName]={} ", tableName);
		getmoClassID(moClass);
		getIfTableName(moClass);

		// 所有需要的参数
		String kpiNames = "'" + KPINameDef.IFOPERSTATUS + "','"
				+ KPINameDef.INUSAGE + "','" + KPINameDef.OUTUSAGE + "','"
				+ KPINameDef.INERRORS + "','" + KPINameDef.OUTERRORS + "','"
				+ KPINameDef.INDISCARDS + "','" + KPINameDef.OUTDISCARDS + "','"
				+ KPINameDef.FLOWS+ "'";

		params.put("moClassID", moClassID);
		params.put("MOID", MOID);
		params.put("tableName", ifTableName);
		// params.put("timeBegin",
		// KPINameDef.queryBetweenTime().get("timeBegin"));
		// params.put("timeEnd", KPINameDef.queryBetweenTime().get("timeEnd"));
		params.put("kpiNames", kpiNames);
        int ifCount=modMaper.getNetworkIfCount(MOID);
        params.put("ifCount", ifCount);
		List<MODevice> moLsinfo = modMaper.getNetworkIf(params);
		logger.info("未去重的数组的长度====" + moLsinfo.size());

		Map<Integer, MODevice> resultMap = new HashMap<Integer, MODevice>();
		for (MODevice moDevice : moLsinfo) {
			int ifMOID = moDevice.getIfMOID();
			// int ifDeviceMOID = moDevice.getIfDeviceMOID();

			if (resultMap.get(ifMOID) != null) {
				// 如果map中已经存在对应的设备，则获得该设备，并判断入个参数的值
				MODevice keyMoDevice = resultMap.get(ifMOID);
				if (KPINameDef.INUSAGE.equals(moDevice.getKpiname())) {
					keyMoDevice.setInusage(format.format(Double.parseDouble(moDevice.getPerValue())));
				} else if (KPINameDef.OUTUSAGE.equals(moDevice.getKpiname())) {
					keyMoDevice.setOutusage(format.format(Double.parseDouble(moDevice.getPerValue())));
				} else if (KPINameDef.INERRORS.equals(moDevice.getKpiname())) {
					keyMoDevice.setInerrors(Double.parseDouble(format.format(Double.parseDouble(moDevice.getPerValue()))));
					// System.out.println("INERRORS==="+keyMoDevice.getInerrors());
				} else if (KPINameDef.OUTERRORS.equals(moDevice.getKpiname())) {
					keyMoDevice.setOuterrors(Double.parseDouble(format.format(Double.parseDouble(moDevice.getPerValue()))));
					// System.out.println("OUTERRORS======"+keyMoDevice.getOuterrors());
				} else if (KPINameDef.INDISCARDS.equals(moDevice.getKpiname())) {
					keyMoDevice.setIndiscards(Double.parseDouble(format.format(Double.parseDouble(moDevice.getPerValue()))));
					// System.out.println("INDISCARDS======="+keyMoDevice.getIndiscards());
				} else if (KPINameDef.OUTDISCARDS.equals(moDevice.getKpiname())) {
					keyMoDevice.setOutdiscards(Double.parseDouble(format.format(Double.parseDouble(moDevice.getPerValue()))));
					// System.out.println("OUTDISCARDS========="+keyMoDevice.getOutdiscards());
				}else if (KPINameDef.FLOWS.equals(moDevice.getKpiname())) {
					keyMoDevice.setFlows(HostComm.getBytesToFlows(Double.parseDouble(moDevice.getPerValue())));
				} else if (KPINameDef.IFOPERSTATUS
						.equals(moDevice.getKpiname())) {
					if (KPINameDef.DEVICE_UP.equals(moDevice.getPerValue())) {
						keyMoDevice.setOperstatus("up.png");
						keyMoDevice.setOperaTip("UP");
					} else if (KPINameDef.DEVICE_DOWN.equals(moDevice.getPerValue())) {
						keyMoDevice.setOperstatus("down.png");
						keyMoDevice.setOperaTip("DOWN");
					} else {
						keyMoDevice.setOperstatus("unknown.png");
						keyMoDevice.setOperaTip("未知");
					}
				}

				keyMoDevice.setErrors(keyMoDevice.getInerrors()
						+ keyMoDevice.getOuterrors());
				keyMoDevice.setDiscards(keyMoDevice.getIndiscards()
						+ keyMoDevice.getOutdiscards());
			}
			// 如果map中不存在，则入map
			else {
				if(ifCount==0){ 
						moDevice.setInusage("");
						moDevice.setOutusage("");
//						moDevice.setOperstatus("unknown.png");
//					    moDevice.setOperaTip("未知");
						if(moDevice.getIfoperstatus().equals("1")){
							moDevice.setOperstatus("up.png");
							moDevice.setOperaTip("UP");
						}else if(moDevice.getIfoperstatus().equals("2")){
							moDevice.setOperstatus("down.png");
							moDevice.setOperaTip("DOWN");
						}else{
							moDevice.setOperstatus("unknown.png");
						    moDevice.setOperaTip("未知");
						}

					    moDevice.setErrors(-1);
					    moDevice.setDiscards(-1);
					resultMap.put(ifMOID, moDevice);
				}else{
					if (KPINameDef.INUSAGE.equals(moDevice.getKpiname())) {
						moDevice.setInusage(format.format(Double.parseDouble(moDevice.getPerValue())));
					} else if (KPINameDef.OUTUSAGE.equals(moDevice.getKpiname())) {
						moDevice.setOutusage(format.format(Double.parseDouble(moDevice.getPerValue())));
					} else if (KPINameDef.INERRORS.equals(moDevice.getKpiname())) {
						moDevice.setInerrors(Double.parseDouble(format.format(Double.parseDouble(moDevice.getPerValue()))));
					} else if (KPINameDef.OUTERRORS.equals(moDevice.getKpiname())) {
						moDevice.setOuterrors(Double.parseDouble(format.format(Double.parseDouble(moDevice.getPerValue()))));
					} else if (KPINameDef.INDISCARDS.equals(moDevice.getKpiname())) {
						moDevice.setIndiscards(Double.parseDouble(format.format(Double.parseDouble(moDevice.getPerValue()))));
					} else if (KPINameDef.OUTDISCARDS.equals(moDevice.getKpiname())) {
						moDevice.setOutdiscards(Double.parseDouble(format.format(Double.parseDouble(moDevice.getPerValue()))));
					}else if (KPINameDef.FLOWS.equals(moDevice.getKpiname())) {
						moDevice.setFlows(HostComm.getBytesToFlows(Double.parseDouble(moDevice.getPerValue())));
					} else if (KPINameDef.IFOPERSTATUS
							.equals(moDevice.getKpiname())) {
						if (KPINameDef.DEVICE_UP.equals(moDevice.getPerValue())) {
							moDevice.setOperstatus("up.png");
							moDevice.setOperaTip("UP");
						} else if (KPINameDef.DEVICE_DOWN.equals(moDevice.getPerValue())) {
							moDevice.setOperstatus("down.png");
							moDevice.setOperaTip("DOWN");
						} else {
							moDevice.setOperstatus("unknown.png");
							moDevice.setOperaTip("未知");
						}
					}else if(null == moDevice.getKpiname() || "".equals( moDevice.getKpiname())){
						// 倘若有多个接口，其中有部分接口已采集数据，另外未采集，那么未采集接口的状态如以下。
						if(moDevice.getIfoperstatus().equals("1")){
							moDevice.setOperstatus("up.png");
							moDevice.setOperaTip("UP");
						}else if(moDevice.getIfoperstatus().equals("2")){
							moDevice.setOperstatus("down.png");
							moDevice.setOperaTip("DOWN");
						}else{
							moDevice.setOperstatus("unknown.png");
						    moDevice.setOperaTip("未知");
						}
					}
	
					moDevice.setErrors(moDevice.getInerrors()
							+ moDevice.getOuterrors());
					moDevice.setDiscards(moDevice.getIndiscards()
							+ moDevice.getOutdiscards());
					resultMap.put(ifMOID, moDevice);
				}
			}
		}

		// 遍历map，将接口列表数据放入数组中返回
		List<MODevice> resultList = new ArrayList<MODevice>();
		for (Iterator it = resultMap.keySet().iterator(); it.hasNext();) {
			MODevice mo = resultMap.get(it.next());
			if (mo.getIfspeed() != null && !"".equals(mo.getIfspeed())) {
				mo.setIfspeed(HostComm.getBytesToSpeed(Long.parseLong(mo.getIfspeed())));
			}
			resultList.add(mo);
		}
		logger.info("resultList的长度" + resultList.size());
		result.put("rows", resultList);
		return result;
	}

	@RequestMapping("/toShowVHostOrVMDetail")
	public ModelAndView toShowVHostOrVMDetail(HttpServletRequest request) {
		String moID = request.getParameter("moID");
		String ifMOID = request.getParameter("IfMOID");
		request.setAttribute("moID", moID);
		request.setAttribute("ifMOID", ifMOID);
		return new ModelAndView("monitor/host/vhostOrVMDetail");
	}

	@RequestMapping("/findVHostOrVMDetail")
	@ResponseBody
	public Map<String, Object> findVHostOrVMDetail() throws Exception {
		logger.info("开始...加载接口数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("#.##");
		int moID = Integer.parseInt(request.getParameter("moID"));
		int ifMOID = Integer.parseInt(request.getParameter("IfMOID"));
		logger.info("MOID====" + moID + ",ifMOID===" + ifMOID);
		List<MODevice> detaiList = new ArrayList<MODevice>();
		MODevice modevice1 = new MODevice();
		modevice1.setKpiname("带宽");
		if (modMaper.getPerfValue(KPINameDef.INUSAGE, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.INUSAGE, ifMOID,
						moID))) {
			modevice1.setIns("0%");
		} else {
			modevice1.setIns(df.format(Double.parseDouble(modMaper.getPerfValue(KPINameDef.INUSAGE, ifMOID,
					moID)))+"%");
		}

		if (modMaper.getPerfValue(KPINameDef.OUTUSAGE, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.OUTUSAGE, ifMOID,
						moID))) {
			modevice1.setOuts("0%");
		} else {
			modevice1.setOuts(df.format(Double.parseDouble(modMaper.getPerfValue(KPINameDef.OUTUSAGE,
					ifMOID, moID)))+"%");
		}

		MODevice modevice2 = new MODevice();
		modevice2.setKpiname("总包");
		if (modMaper.getPerfValue(KPINameDef.INPACKETS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.INPACKETS,
						ifMOID, moID))) {
			modevice2.setIns("0个");
		} else {
			modevice2.setIns(getStringNum(modMaper.getPerfValue(KPINameDef.INPACKETS,
					ifMOID, moID)));
		}

		if (modMaper.getPerfValue(KPINameDef.OUTPACKETS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.OUTPACKETS,
						ifMOID, moID))) {
			modevice2.setOuts("0个");
		} else {
			modevice2.setOuts(getStringNum(modMaper.getPerfValue(KPINameDef.OUTPACKETS,
					ifMOID, moID)));
		}
		detaiList.add(modevice1);
		detaiList.add(modevice2);
		result.put("rows", detaiList);

		return result;
	}

	@RequestMapping("/toShowHostOrNetWorkDetail")
	public ModelAndView toShowHostOrNetWorkDetail(HttpServletRequest request) {
		String moID = request.getParameter("moID");
		String ifMOID = request.getParameter("IfMOID");
		request.setAttribute("moID", moID);
		request.setAttribute("ifMOID", ifMOID);
		return new ModelAndView("monitor/host/hostOrNetWorkDetail");
	}

	/*
	 * @RequestMapping("/findHostOrNetWorkDetailhl")
	 * 
	 * @ResponseBody public Map<String, Object> findHostOrNetWorkDetailhl()
	 * throws Exception { logger.info("开始...加载接口数据"); HttpServletRequest request
	 * = ((ServletRequestAttributes) RequestContextHolder
	 * .getRequestAttributes()).getRequest(); Map<String, Object> result = new
	 * HashMap<String, Object>(); int moID =
	 * Integer.parseInt(request.getParameter("moID")); int ifMOID =
	 * Integer.parseInt(request.getParameter("IfMOID")); DecimalFormat df = new
	 * DecimalFormat("#"); logger.info("MOID====" + moID + ",ifMOID===" +
	 * ifMOID); List<MODevice> detaiList = new ArrayList<MODevice>(); MODevice
	 * modevice1 = new MODevice(); modevice1.setKpiname("带宽利用率(%)"); if
	 * (modMaper.getPerfValue(KPINameDef.INUSAGE, ifMOID, moID) == null ||
	 * "".equals(modMaper.getPerfValue(KPINameDef.INUSAGE, ifMOID, moID))) {
	 * modevice1.setIns(0); } else {
	 * modevice1.setIns(Double.parseDouble(df.format
	 * (modMaper.getPerfValue(KPINameDef.INUSAGE, ifMOID, moID)))); } if
	 * (modMaper.getPerfValue(KPINameDef.OUTUSAGE, ifMOID, moID) == null ||
	 * "".equals(modMaper.getPerfValue(KPINameDef.OUTUSAGE, ifMOID, moID))) {
	 * modevice1.setOuts(0); } else {
	 * modevice1.setOuts(Double.parseDouble(df.format
	 * (modMaper.getPerfValue(KPINameDef.OUTUSAGE, ifMOID, moID)))); }
	 * 
	 * MODevice modevice2 = new MODevice(); modevice2.setKpiname("流量(KBps)"); if
	 * (modMaper.getPerfValue(KPINameDef.INFLOWS, ifMOID, moID) == null ||
	 * "".equals(modMaper.getPerfValue(KPINameDef.INFLOWS, ifMOID, moID))) {
	 * modevice2.setIns(0); } else {
	 * if(modMaper.getPerfValue(KPINameDef.INFLOWS, ifMOID,moID)/8<1000 ) {
	 * modevice2
	 * .setIns(Double.parseDouble(df.format(modMaper.getPerfValue(KPINameDef
	 * .INFLOWS, ifMOID, moID)/8))); }else{
	 * modevice2.setIns(Double.parseDouble(df
	 * .format(modMaper.getPerfValue(KPINameDef.INFLOWS, ifMOID, moID)
	 * /8/1000))); } } if (modMaper.getPerfValue(KPINameDef.OUTFLOWS, ifMOID,
	 * moID) == null || "".equals(modMaper.getPerfValue(KPINameDef.OUTFLOWS,
	 * ifMOID, moID))) { modevice2.setOuts(0); } else {
	 * if(modMaper.getPerfValue(KPINameDef.INFLOWS, ifMOID,moID)/8<1000 ) {
	 * modevice2
	 * .setOuts(Double.parseDouble(df.format(modMaper.getPerfValue(KPINameDef
	 * .OUTFLOWS, ifMOID, moID)/8))); }else{
	 * modevice2.setOuts(Double.parseDouble
	 * (df.format(modMaper.getPerfValue(KPINameDef.OUTFLOWS, ifMOID, moID)
	 * /8/1000))); } }
	 * 
	 * MODevice modevice3 = new MODevice(); modevice3.setKpiname("错包(个)"); if
	 * (modMaper.getPerfValue(KPINameDef.INERRORS, ifMOID, moID) == null ||
	 * "".equals(modMaper.getPerfValue(KPINameDef.INERRORS, ifMOID, moID))) {
	 * modevice3.setIns(0); } else {
	 * modevice3.setIns(Double.parseDouble(df.format
	 * (modMaper.getPerfValue(KPINameDef.INERRORS, ifMOID, moID)))); } if
	 * (modMaper.getPerfValue(KPINameDef.OUTERRORS, ifMOID, moID) == null ||
	 * "".equals(modMaper.getPerfValue(KPINameDef.OUTERRORS, ifMOID, moID))) {
	 * modevice3.setOuts(0); } else {
	 * modevice3.setOuts(Double.parseDouble(df.format
	 * (modMaper.getPerfValue(KPINameDef.OUTERRORS, ifMOID, moID)))); }
	 * 
	 * MODevice modevice4 = new MODevice(); modevice4.setKpiname("丢包(个)"); if
	 * (modMaper.getPerfValue(KPINameDef.INDISCARDS, ifMOID, moID) == null ||
	 * "".equals(modMaper.getPerfValue(KPINameDef.INDISCARDS, ifMOID, moID))) {
	 * modevice4.setIns(0); } else {
	 * modevice4.setIns(Double.parseDouble(df.format
	 * (modMaper.getPerfValue(KPINameDef.INDISCARDS, ifMOID, moID)))); } if
	 * (modMaper.getPerfValue(KPINameDef.OUTDISCARDS, ifMOID, moID) == null ||
	 * "".equals(modMaper.getPerfValue(KPINameDef.OUTDISCARDS, ifMOID, moID))) {
	 * modevice4.setOuts(0); } else {
	 * modevice4.setOuts(Double.parseDouble(df.format
	 * (modMaper.getPerfValue(KPINameDef.OUTDISCARDS, ifMOID, moID)))); }
	 * 
	 * MODevice modevice5 = new MODevice(); modevice5.setKpiname("单播包(个)"); if
	 * (modMaper.getPerfValue(KPINameDef.INPACKETS, ifMOID, moID) == null ||
	 * "".equals(modMaper.getPerfValue(KPINameDef.INPACKETS, ifMOID, moID))) {
	 * modevice5.setIns(0); } else {
	 * modevice5.setIns(Double.parseDouble(df.format
	 * (modMaper.getPerfValue(KPINameDef.INPACKETS, ifMOID, moID)))); } if
	 * (modMaper.getPerfValue(KPINameDef.OUTPACKETS, ifMOID, moID) == null ||
	 * "".equals(modMaper.getPerfValue(KPINameDef.OUTPACKETS, ifMOID, moID))) {
	 * modevice5.setOuts(0); } else {
	 * modevice5.setOuts(Double.parseDouble(df.format
	 * (modMaper.getPerfValue(KPINameDef.OUTPACKETS, ifMOID, moID)))); }
	 * 
	 * MODevice modevice6 = new MODevice(); modevice6.setKpiname("非单播包(个)"); if
	 * (modMaper.getPerfValue(KPINameDef.INNPACKETS, ifMOID, moID) == null ||
	 * "".equals(modMaper.getPerfValue(KPINameDef.INNPACKETS, ifMOID, moID))) {
	 * modevice6.setIns(0); } else {
	 * modevice6.setIns(Double.parseDouble(df.format
	 * (modMaper.getPerfValue(KPINameDef.INNPACKETS, ifMOID, moID)))); } if
	 * (modMaper.getPerfValue(KPINameDef.OUTNPACKETS, ifMOID, moID) == null ||
	 * "".equals(modMaper.getPerfValue(KPINameDef.OUTNPACKETS, ifMOID, moID))) {
	 * modevice6.setOuts(0); } else {
	 * modevice6.setOuts(Double.parseDouble(df.format
	 * (modMaper.getPerfValue(KPINameDef.OUTNPACKETS, ifMOID, moID)))); }
	 * detaiList.add(modevice1); detaiList.add(modevice2);
	 * detaiList.add(modevice3); detaiList.add(modevice4);
	 * detaiList.add(modevice5); detaiList.add(modevice6); result.put("rows",
	 * detaiList);
	 * 
	 * return result; }
	 */
	@RequestMapping("/findHostOrNetWorkDetail")
	@ResponseBody
	public Map<String, Object> findHostOrNetWorkDetail() throws Exception {
		logger.info("开始...加载接口数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("#.##");
		int moID = Integer.parseInt(request.getParameter("moID"));
		int ifMOID = Integer.parseInt(request.getParameter("IfMOID"));
		logger.info("MOID====" + moID + ",ifMOID===" + ifMOID);
		List<MODevice> detaiList = new ArrayList<MODevice>();
		MODevice modevice1 = new MODevice();
		modevice1.setKpiname("带宽利用率");
		if (modMaper.getPerfValue(KPINameDef.INUSAGE, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.INUSAGE, ifMOID,
						moID))) {
			modevice1.setIns("0%");
		} else {
			modevice1.setIns(df.format(Double.parseDouble(modMaper
					.getPerfValue(KPINameDef.INUSAGE, ifMOID, moID)))+"%");
		}
		if (modMaper.getPerfValue(KPINameDef.OUTUSAGE, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.OUTUSAGE, ifMOID,
						moID))) {
			modevice1.setOuts("0%");
		} else {
			modevice1.setOuts(df.format(Double.parseDouble(modMaper
					.getPerfValue(KPINameDef.OUTUSAGE, ifMOID, moID)))+"%");
		}

		MODevice modevice2 = new MODevice();
		modevice2.setKpiname("流量");
		if (modMaper.getPerfValue(KPINameDef.INFLOWS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.INFLOWS, ifMOID,
						moID))) {
			modevice2.setIns("0bps");
		} else {

			modevice2.setIns(HostComm.getBytesToFlows(Double
					.parseDouble(modMaper.getPerfValue(KPINameDef.INFLOWS,
							ifMOID, moID))));

		}
		if (modMaper.getPerfValue(KPINameDef.OUTFLOWS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.OUTFLOWS, ifMOID,
						moID))) {
			modevice2.setOuts("0bps");
		} else {
			modevice2.setOuts(HostComm.getBytesToFlows(Double
					.parseDouble(modMaper.getPerfValue(KPINameDef.OUTFLOWS,
							ifMOID, moID))));

		}

		MODevice modevice3 = new MODevice();
		modevice3.setKpiname("错包");
		if (modMaper.getPerfValue(KPINameDef.INERRORS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.INERRORS, ifMOID,
						moID))) {
			modevice3.setIns("0个");
		} else {
			modevice3.setIns(getStringNum(modMaper.getPerfValue(KPINameDef.INERRORS, ifMOID,
					moID)));
		}
		if (modMaper.getPerfValue(KPINameDef.OUTERRORS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.OUTERRORS,
						ifMOID, moID))) {
			modevice3.setOuts("0个");
		} else {
			modevice3.setOuts(getStringNum(modMaper.getPerfValue(KPINameDef.OUTERRORS,
					ifMOID, moID)));
		}

		MODevice modevice4 = new MODevice();
		modevice4.setKpiname("丢包");
		if (modMaper.getPerfValue(KPINameDef.INDISCARDS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.INDISCARDS,
						ifMOID, moID))) {
			modevice4.setIns("0个");
		} else {
			modevice4.setIns(getStringNum(modMaper.getPerfValue(KPINameDef.INDISCARDS,
					ifMOID, moID)));
		}
		if (modMaper.getPerfValue(KPINameDef.OUTDISCARDS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.OUTDISCARDS,
						ifMOID, moID))) {
			modevice4.setOuts("0个");
		} else {
			modevice4.setOuts(getStringNum(modMaper.getPerfValue(KPINameDef.OUTDISCARDS,
					ifMOID, moID)));
		}

		MODevice modevice5 = new MODevice();
		modevice5.setKpiname("单播包");
		if (modMaper.getPerfValue(KPINameDef.INPACKETS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.INPACKETS,
						ifMOID, moID))) {
			modevice5.setIns("0个");
		} else {
			modevice5.setIns(getStringNum(modMaper.getPerfValue(KPINameDef.INPACKETS,
					ifMOID, moID)));
		}
		if (modMaper.getPerfValue(KPINameDef.OUTPACKETS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.OUTPACKETS,
						ifMOID, moID))) {
			modevice5.setOuts("0个");
		} else {
			modevice5.setOuts(getStringNum(modMaper.getPerfValue(KPINameDef.OUTPACKETS,
					ifMOID, moID)));
		}

		MODevice modevice6 = new MODevice();
		modevice6.setKpiname("非单播包");
		if (modMaper.getPerfValue(KPINameDef.INNPACKETS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.INNPACKETS,
						ifMOID, moID))) {
			modevice6.setIns("0个");
		} else {
			modevice6.setIns(getStringNum(modMaper.getPerfValue(KPINameDef.INNPACKETS,
					ifMOID, moID)));
		}
		if (modMaper.getPerfValue(KPINameDef.OUTNPACKETS, ifMOID, moID) == null
				|| "".equals(modMaper.getPerfValue(KPINameDef.OUTNPACKETS,
						ifMOID, moID))) {
			modevice6.setOuts("0个");
		} else {
			modevice6.setOuts(getStringNum(modMaper.getPerfValue(KPINameDef.OUTNPACKETS,
					ifMOID, moID)));
		}
		detaiList.add(modevice1);
		detaiList.add(modevice2);
		detaiList.add(modevice3);
		detaiList.add(modevice4);
		detaiList.add(modevice5);
		detaiList.add(modevice6);
		result.put("rows", detaiList);

		return result;
	}
	public static String getStringNum(String num) {
		double dstr = Double.valueOf(num);
		long lstr = (long) dstr;
		String str=lstr+"个";
		return str;
	}
	
	
}
