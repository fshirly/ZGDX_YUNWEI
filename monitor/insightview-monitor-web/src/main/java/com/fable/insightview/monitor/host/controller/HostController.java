package com.fable.insightview.monitor.host.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.host.entity.PerfSnapsBean;
import com.fable.insightview.monitor.host.mapper.HostMapper;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.DateUtil;
import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.sysconf.service.SysConfigService;

@Controller
@RequestMapping("/monitor/hostManage")
public class HostController {

	@Autowired
	HostMapper modMaper;
	@Autowired
	private AlarmActiveMapper alarmActiveMapper;
	@Autowired
	IOracleService orclService;
	@Autowired
	WebSiteMapper webSiteMapper;

	@Autowired
	private SysConfigService sysConfigServiceImpl;
	DecimalFormat   format = new DecimalFormat("#.##");
	private final Logger logger = LoggerFactory.getLogger(HostController.class);
	String moClass;// 设备类型ID
	String tableName;// 表名
	Integer MOID;
	Integer moClassID;
	String cpuUrl;// CPU 曲线跳转地址
	String memUrl;// 内存 曲线跳转地址
	String diskUrl;// 硬盘 曲线跳转地址
	String storeUrl;// 数据存储 曲线跳转地址
	String ifTableName;// 接口表名
	/**
	 * 根据设备类型 查询 表名
	 * 
	 * @param moClass
	 * @return
	 */
	public String getParam(String moClass) {
		if ("host".equals(moClass) || "null".equals(moClass) || moClass == null) {
			tableName = "PerfSnapshotHost";
		} else if ("vm".equals(moClass)) {
			tableName = "PerfSnapshotVM";
		} else if ("vhost".equals(moClass)) {
			tableName = "PerfSnapshotVHost";
		} else if ("switcher".equals(moClass) || "router".equals(moClass) || "switcherl2".equals(moClass)
				|| "switcherl3".equals(moClass)) {
			tableName = "PerfSnapshotNetDevice";
		}
		return tableName;
	}

	/**
	 * 根据设备类型 查询接口表名
	 * 
	 * @param moClass
	 * @return
	 */
	public String getIfTableName(String moClass) {
		if ("host".equals(moClass) || "null".equals(moClass) || moClass == null || "switcher".equals(moClass)
				|| "router".equals(moClass) || "switcherl2".equals(moClass) || "switcherl3".equals(moClass)||"loadbalance".equals(moClass))  {
			ifTableName = "PerfSnapshotNetDevice";
		} else if ("vm".equals(moClass)) {
			ifTableName = "PerfSnapshotVM";
		} else if ("vhost".equals(moClass)) {
			ifTableName = "PerfSnapshotVHost";
		}
		return ifTableName;
	}

	/**
	 * 根据类型获取moClassID
	 * 
	 * @param moClass
	 */
	public Integer getmoClassID(String moClass) {
		if ("PHost".equals(moClass) || "null".equals(moClass) || moClass == null) {
			moClassID = 7;
		} else if ("vm".equals(moClass) || "VM".equals(moClass)) {
			moClassID = 9;
		} else if ("vhost".equals(moClass) || "VHost".equals(moClass)) {
			moClassID = 8;
		} else if ("switcher".equals(moClass) || "Switcher".equals(moClass)) {
			moClassID = 6;
		} else if ("router".equals(moClass) || "Router".equals(moClass)) {
			moClassID = 5;
		} else if ("mo".equals(moClass)) {
			moClassID = 1;
		} else if ("SwitcherL2".equals(moClass) || "switcherl2".equals(moClass)) {
			moClassID = 59;
		} else if ("SwitcherL3".equals(moClass) || "switcherl3".equals(moClass)) {
			moClassID = 60;
		} else if("vpn".equals(moClass)){
			moClassID = 118;
		} else if("proxygateway".equals(moClass)){
			moClassID =135;  
		} else if("mobileappagent".equals(moClass)){
			moClassID = 133;
		}else if("loadbalance".equals(moClass)){
			moClassID = 134;
		}else if("probe".equals(moClass)){
			moClassID=136;
		}else if("mobileappmanger".equals(moClass)){
			moClassID=132;
		}else if("gatekeeper".equals(moClass)){
			moClassID=137;
		}else if("PVM".equals(moClass) || "PVM".equals(moClass)){
			moClassID=138;
		}else if("host".equals(moClass)){
			moClassID=3;
		}else if("SafeDevice".toUpperCase().equals(moClass.toUpperCase())){
			moClassID=106;
		}
		return moClassID;
	}

	@RequestMapping("/toHostInfo")
	public ModelAndView toHostInfo() {
		return new ModelAndView("monitor/host/hostInfoList");
	}
	@RequestMapping("/shiyi")
	public ModelAndView toshiyi() {
		return new ModelAndView("shiy");
	}
	/**
	 * 跳转至CPU列表页
	 */
	@RequestMapping("/toCPUInfo")
	public ModelAndView toCPUInfo(HttpServletRequest request, ModelMap map) {
		String cpuNum = request.getParameter("num");
		String moClass = request.getParameter("moClass");
		String topOrder = request.getParameter("topOrder");
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");
		if ("".equals(topOrder) || topOrder == null) {
			topOrder = "desc";
		}
		if ("".equals(timeBegin) || timeBegin == null) {
			timeBegin = KPINameDef.queryBetweenDay().get("timeBegin").toString();
		}
		if ("".equals(timeEnd) || timeEnd == null) {
			timeEnd = KPINameDef.queryBetweenDay().get("timeEnd").toString();
		}
		String liInfo = request.getParameter("liInfo");
		map.put("liInfo", liInfo);
		map.put("moClass", moClass);
		map.put("cpuNum", cpuNum);
		map.put("topOrder", topOrder);
		map.put("timeBegin", timeBegin);
		map.put("timeEnd", timeEnd);
		return new ModelAndView("monitor/host/cpuInfoList");
	}

	/**
	 * 跳转至内存列表页
	 */
	@RequestMapping("/toMemoryInfo")
	public ModelAndView toMemoryInfo(HttpServletRequest request, ModelMap map) {
		String num = request.getParameter("num");
		String moClass = request.getParameter("moClass");
		String topOrder = request.getParameter("topOrder");
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");
		if ("".equals(topOrder) || topOrder == null) {
			topOrder = "desc";
		}
		if ("".equals(timeBegin) || timeBegin == null) {
			timeBegin = KPINameDef.queryBetweenDay().get("timeBegin").toString();
		}
		if ("".equals(timeEnd) || timeEnd == null) {
			timeEnd = KPINameDef.queryBetweenDay().get("timeEnd").toString();
		}
		String liInfo = request.getParameter("liInfo");
		map.put("liInfo", liInfo);
		map.put("moClass", moClass);
		map.put("num", num);
		map.put("topOrder", topOrder);
		map.put("timeBegin", timeBegin);
		map.put("timeEnd", timeEnd);
		return new ModelAndView("monitor/host/memoryInfoList");
	}

	/**
	 * 跳转至虚拟内存列表页
	 */
	@RequestMapping("/toVirMemoryInfo")
	public ModelAndView toVirMemoryInfo(HttpServletRequest request) {
		String moClass = request.getParameter("moClass");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moClass", moClass);
		return new ModelAndView("monitor/host/virmemoryInfoList");
	}

	/**
	 * 跳转至硬盘列表页
	 */
	@RequestMapping("/toDiskInfo")
	public ModelAndView toDiskInfo(HttpServletRequest request, ModelMap map) {
		String num = request.getParameter("num");
		String moClass = request.getParameter("moClass");
		String topOrder = request.getParameter("topOrder");
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");
		if ("".equals(topOrder) || topOrder == null) {
			topOrder = "desc";
		}
		if ("".equals(timeBegin) || timeBegin == null) {
			timeBegin = KPINameDef.queryBetweenDay().get("timeBegin").toString();
		}
		if ("".equals(timeEnd) || timeEnd == null) {
			timeEnd = KPINameDef.queryBetweenDay().get("timeEnd").toString();
		}
		String liInfo = request.getParameter("liInfo");
		map.put("liInfo", liInfo);
		map.put("moClass", moClass);
		map.put("num", num);
		map.put("topOrder", topOrder);
		map.put("timeBegin", timeBegin);
		map.put("timeEnd", timeEnd);
		return new ModelAndView("monitor/host/diskInfoList");
	}

	/**
	 * 跳转至宿主机 I/O 硬盘使用率 列表页
	 */
	@RequestMapping("/toVhostIODiskInfo")
	public ModelAndView toVhostIODiskInfo(HttpServletRequest request) {
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		return new ModelAndView("monitor/host/vHostdiskInfoList");
	}

	/**
	 * 跳转至设备快照列表页
	 */
	@RequestMapping("/toSnapshotInfo")
	public ModelAndView toSnapshotInfo(HttpServletRequest request) {
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		return new ModelAndView("monitor/host/snapshotInfoList");
	}

	/**
	 * 跳转至所有设备快照列表页
	 */
	@RequestMapping("/toAllSnapshotInfo")
	public ModelAndView toAllSnapshotInfo(HttpServletRequest request) {
		String moClass = request.getParameter("moClass");
		if ("".equals(moClass) || moClass == null) {
			moClass = "mo";
		}
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moClass", moClass);
		return new ModelAndView("monitor/host/allSnapshotInfoList");
	}

	/**
	 * 跳转至设备带宽列表页
	 */
	@RequestMapping("/toIfUsageInfo")
	public ModelAndView toIfUsageInfo(HttpServletRequest request, ModelMap map) {
		String num = request.getParameter("num");
		String moClass = request.getParameter("moClass");
		String topOrder = request.getParameter("topOrder");
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");
		if ("".equals(topOrder) || topOrder == null) {
			topOrder = "desc";
		}
		if ("".equals(timeBegin) || timeBegin == null) {
			timeBegin = KPINameDef.queryBetweenDay().get("timeBegin").toString();
		}
		if ("".equals(timeEnd) || timeEnd == null) {
			timeEnd = KPINameDef.queryBetweenDay().get("timeEnd").toString();
		}
		String liInfo = request.getParameter("liInfo");
		map.put("liInfo", liInfo);
		map.put("moClass", moClass);
		map.put("num", num);
		map.put("topOrder", topOrder);
		map.put("timeBegin", timeBegin);
		map.put("timeEnd", timeEnd);
		return new ModelAndView("monitor/host/ifusageInfoList");
	}

	/**
	 * 跳转至设备接口流量列表页
	 */
	@RequestMapping("/toFlowsInfo")
	public ModelAndView toFlowsInfo(HttpServletRequest request, ModelMap map) {
		String num = request.getParameter("num");
		String moClass = request.getParameter("moClass");
		String topOrder = request.getParameter("topOrder");
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");
		if ("".equals(topOrder) || topOrder == null) {
			topOrder = "desc";
		}
		if ("".equals(timeBegin) || timeBegin == null) {
			timeBegin = KPINameDef.queryBetweenDay().get("timeBegin").toString();
		}
		if ("".equals(timeEnd) || timeEnd == null) {
			timeEnd = KPINameDef.queryBetweenDay().get("timeEnd").toString();
		}
		String liInfo = request.getParameter("liInfo");
		map.put("liInfo", liInfo);
		map.put("moClass", moClass);
		map.put("num", num);
		map.put("topOrder", topOrder);
		map.put("timeBegin", timeBegin);
		map.put("timeEnd", timeEnd);
		return new ModelAndView("monitor/host/flowsInfoList");
	}

	/**
	 * 跳转至最近告警列表页
	 */
	@RequestMapping("/toAlarmActiveInfo")
	public ModelAndView toAlarmActiveInfo(HttpServletRequest request, ModelMap map) {
		String moClass = request.getParameter("moClass");
		String alarmNum = request.getParameter("num");
		map.put("moClass", moClass);
		map.put("alarmNum", alarmNum);
		map.put("flag", request.getParameter("flag"));
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		return new ModelAndView("monitor/host/alarmActiveInfoList");
	}

	/**
	 * 主机跳转至详情列表页
	 */
	@RequestMapping("/toHostDetailListInfo")
	public ModelAndView toHostDetailListInfo(HttpServletRequest request) {
		MOID = Integer.parseInt(request.getParameter("moID"));
		moClass = request.getParameter("moClass");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		return new ModelAndView("monitor/host/hostDetailList");
	}

	/**
	 * 宿主机跳转至详情列表页
	 * 
	 * @return
	 */
	@RequestMapping("/toVHostDetailListInfo")
	public ModelAndView toVHostDetailListInfo(HttpServletRequest request) {
		MOID = Integer.parseInt(request.getParameter("moID"));
		moClass = request.getParameter("moClass");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		return new ModelAndView("monitor/host/vhostDetailList");
	}

	/**
	 * 虚拟机跳转至详情列表页
	 * 
	 * @return
	 */
	@RequestMapping("/toVMDetailListInfo")
	public ModelAndView toVMDetailListInfo(HttpServletRequest request) {
		MOID = Integer.parseInt(request.getParameter("moID"));
		moClass = request.getParameter("moClass");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		return new ModelAndView("monitor/host/vmDetailList");
	}

	/**
	 * 跳转至告警列表页
	 * 
	 * @return
	 */
	@RequestMapping("/toHostAlarmDetailInfo")
	public ModelAndView toHostAlarmDetailInfo(HttpServletRequest request) {
		MOID = Integer.parseInt(request.getParameter("moID"));
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		return new ModelAndView("monitor/host/hostAlarmDetailInfo");
	}

	/**
	 * 跳转至宿主机 for 虚拟机列表页
	 * 
	 * @return
	 */
	@RequestMapping("/toVHostForVMInfo")
	public ModelAndView toVHostForVMInfo(HttpServletRequest request) {
		MOID = Integer.parseInt(request.getParameter("moID"));
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		return new ModelAndView("monitor/host/vhostForVMInfo");
	}

	/**
	 * 告警详情页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toAlarmActiveDetail")
	public ModelAndView toAlarmActiveDetail(HttpServletRequest request, ModelMap map) throws Exception {
		int alarmID = Integer.parseInt(request.getParameter("alarmID"));
		logger.info("告警详情id:" + alarmID);
		AlarmNode vo = new AlarmNode();
		vo = alarmActiveMapper.getInfoById(alarmID);
		SecurityUserInfoBean user = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		map.put("userId", user.getId());
		map.put("username", user.getUserName());
		map.put("alarmVo", vo);
		map.put("flag", request.getParameter("flag"));
		// 是否启用新告警派单
		String workOrderVersion = this.sysConfigServiceImpl.getConfParamValue(
				SystemFinalValue.SYS_CONFIG_TYPE_PROCESS_WOA, SystemFinalValue.SCT_PROC_WOA_CITY_WORKORDER_FLAG);
		if (StringUtils.isNotEmpty(workOrderVersion)) {
			map.put("alarmOrderVersion", workOrderVersion);
		}
		map.put("transferTimeStr", DateUtil.date2String(new Date()));
		return new ModelAndView("monitor/alarmMgr/alarmactive/alarmActive_detail");
	}

	/**
	 * 设备快照
	 * 
	 * @return
	 */
	@RequestMapping("/getSnapshotInfo")
	@ResponseBody
	public Map<String, Object> getSnapshotInfo() throws Exception {
		logger.info("开始...加载设备快照页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String par = request.getParameter("type");
		String moClass = request.getParameter("moClass");
		List<MODevice> moLsCount = new ArrayList<MODevice>();
		List<MODevice> snapsHostCount = new ArrayList<MODevice>();
		List<MODevice> MObjectDef = new ArrayList<MODevice>();
		// 严重告警设备数量
		List<MODevice> SeriousDeviceCount = new ArrayList<MODevice>();
		Map<String, Object> moClassIdMap = new HashMap<String, Object>();
		List<PerfSnapsBean> moLsinfo = new ArrayList<PerfSnapsBean>();
		List<PerfSnapsBean> vmSnapshotList = new ArrayList<PerfSnapsBean>();
		if ("all".equals(par)) {
			if(moClass.toLowerCase().equals("mo".toLowerCase())){
				moLsCount = modMaper.getMoClassIDAllCount(null);
			}else{
				List<Integer> classIDs=modMaper.getMobjectRelationClassIDS(getmoClassID(moClass));
				String aa=null;
				for(int i=0;i<classIDs.size();i++){
					if(aa==null){
						aa=String.valueOf(classIDs.get(i));
					}else{
						aa=aa+","+String.valueOf(classIDs.get(i));
					}
				}
				moLsCount = modMaper.getMoClassIDAllCount(aa);
			}
		
		} else {
			List<Integer> classIDs=modMaper.getMobjectRelationClassIDS(getmoClassID(moClass));
			String aa=null;
			for(int i=0;i<classIDs.size();i++){
				if(aa==null){
					aa=String.valueOf(classIDs.get(i));
				}else{
					aa=aa+","+String.valueOf(classIDs.get(i));
				}
			}
			moLsCount = modMaper.getMoClassIDCount( aa);
		}
		List<Integer> moClassIDlist = new ArrayList<Integer>();
		String moClassID = "";
		PerfSnapsBean perfSnapObj = null;
		Map<String, PerfSnapsBean> MObjectDefMap = new HashMap<String, PerfSnapsBean>();
		for (int i = 0; i < moLsCount.size(); i++) {
			moClassIDlist.add(moLsCount.get(i).getMoclassid());
		}
		// 查询设备总数
		params.put("classID", moClassIDlist.toString().replace("[", "").replace("]", ""));
		MObjectDef = modMaper.queryMObjectDef(params);
		// 查询总的设备数
		for (MODevice modevice : MObjectDef) {
			String flag = null;
			perfSnapObj = new PerfSnapsBean();
			if (modevice.getSourceMOClassID() == 59 || modevice.getSourceMOClassID() == 60
					|| modevice.getSourceMOClassID() == 6) {
				flag = "交换机";
			} else {
				flag = modevice.getClasslable() + "_" + modevice.getSourceMOClassID();
			}
			if (MObjectDefMap.containsKey(flag)) {
				perfSnapObj = MObjectDefMap.get(flag);
				perfSnapObj.setCountDevice(modevice.getCountdevice() + perfSnapObj.getCountDevice());
				if (("交换机").equals(flag)) {
					perfSnapObj.setClasslable(flag);
					perfSnapObj.setMoClassID("6");
				} else {
					perfSnapObj.setClasslable(modevice.getClasslable());
					perfSnapObj.setMoClassID(String.valueOf(modevice.getSourceMOClassID()));
				}
			} else {
				if (("交换机").equals(flag)) {
					perfSnapObj.setClasslable(flag);
					perfSnapObj.setMoClassID("6");
				} else {
					perfSnapObj.setClasslable(modevice.getClasslable());
					perfSnapObj.setMoClassID(String.valueOf(modevice.getSourceMOClassID()));
				}

				perfSnapObj.setCountDevice(modevice.getCountdevice());
				MObjectDefMap.put(flag, perfSnapObj);
			}
		}

		// 如果moclassid为9时需要特殊处理
		for (int j = 0; j < moClassIDlist.size(); j++) {
			// 虚拟机告警信息moclassid =9
			if (moClassIDlist.get(j) == 9) {
				moClassIdMap.put("moClassID",
						"(alarm.SourceMOClassID=9 OR (alarm.SourceMOClassID=8 AND alarm.MOClassID=9))");
				// 查询总的告警数量
				snapsHostCount = modMaper.querySnapshotInfo(moClassIdMap);
				// 查询严重设备告警数量
				SeriousDeviceCount = modMaper.querySeriousDeviceCount(moClassIdMap);
				for (MODevice moDevice : snapsHostCount) {
					perfSnapObj = new PerfSnapsBean();
					String flag = null;
					if (moDevice.getSourceMOClassID() == 8 || moDevice.getSourceMOClassID() == 9) {
						flag = "虚拟机" + "_" + 9;
					}
					perfSnapObj = querySnapsOrVmhostInfo(MObjectDefMap, perfSnapObj, moDevice, flag);
					MObjectDefMap.put(flag, perfSnapObj);
				}

				for (MODevice SeriousDevice : SeriousDeviceCount) {
					String flag = null;
					if (SeriousDevice.getSourceMOClassID() == 8 || SeriousDevice.getSourceMOClassID() == 9) {
						flag = "虚拟机" + "_" + 9;
					}
					MObjectDefMap.get(flag).setAlarmDeviceCount(SeriousDevice.getCountdevice());
				}
				moClassIDlist.remove(j);
				j = 0;
			}
			// 虚拟宿主机告警信息moclassid =8
			if (moClassIDlist.get(j) == 8) {
				moClassIdMap.put("moClassID", "(alarm.SourceMOClassID=8 AND alarm.MOClassID !=9)");
				// 查询总的告警数量
				snapsHostCount = modMaper.querySnapshotInfo(moClassIdMap);
				// 查询严重设备告警数量
				SeriousDeviceCount = modMaper.querySeriousDeviceCount(moClassIdMap);
				for (MODevice moDevice : snapsHostCount) {
					perfSnapObj = new PerfSnapsBean();
					String flag = moDevice.getClasslable() + "_" + moDevice.getSourceMOClassID();
					perfSnapObj = querySnapsOrVmhostInfo(MObjectDefMap, perfSnapObj, moDevice, flag);
					MObjectDefMap.put(flag, perfSnapObj);
				}

				for (MODevice SeriousDevice : SeriousDeviceCount) {
					String flag = SeriousDevice.getClasslable() + "_" + SeriousDevice.getSourceMOClassID();
					MObjectDefMap.get(flag).setAlarmDeviceCount(SeriousDevice.getCountdevice());
				}
				moClassIDlist.remove(j);
				j = 0;
			}

		}

		moClassID = moClassIDlist.toString().replace("[", "").replace("]", "");
		moClassIdMap.put("moClassID", "alarm.SourceMOClassID in(" + moClassID + ")");
		snapsHostCount = modMaper.querySnapshotInfo(moClassIdMap);
		// 查询严重设备告警数量
		SeriousDeviceCount = modMaper.querySeriousDeviceCount(moClassIdMap);
		for (MODevice moDevice : snapsHostCount) {
			perfSnapObj = new PerfSnapsBean();
			String flag = null;
			if (moDevice.getSourceMOClassID() == 59 || moDevice.getSourceMOClassID() == 60
					|| moDevice.getSourceMOClassID() == 6) {
				flag = "交换机";
			} else {
				flag = moDevice.getClasslable() + "_" + moDevice.getSourceMOClassID();
			}
			perfSnapObj = querySnapsOrVmhostInfo(MObjectDefMap, perfSnapObj, moDevice, flag);
			MObjectDefMap.put(flag, perfSnapObj);
		}
		// 计算交换机1-3告警级别的告警设备数
		int switherCount = 0;
		for (MODevice SeriousDevice : SeriousDeviceCount) {
			if (SeriousDevice.getSourceMOClassID() == 59) {
				switherCount = SeriousDevice.getCountdevice();
			}
			if (SeriousDevice.getSourceMOClassID() == 60) {
				switherCount += SeriousDevice.getCountdevice();
			}
			if (SeriousDevice.getSourceMOClassID() == 6) {
				switherCount += SeriousDevice.getCountdevice();
			}
		}

		for (MODevice SeriousDevice : SeriousDeviceCount) {
			String flag = null;
			perfSnapObj = new PerfSnapsBean();
			if (SeriousDevice.getSourceMOClassID() == 59 || SeriousDevice.getSourceMOClassID() == 60
					|| SeriousDevice.getSourceMOClassID() == 6) {
				flag = "交换机";
				MObjectDefMap.get(flag).setAlarmDeviceCount(switherCount);
			} else {
				flag = SeriousDevice.getClasslable() + "_" + SeriousDevice.getSourceMOClassID();
				MObjectDefMap.get(flag).setAlarmDeviceCount(SeriousDevice.getCountdevice());
			}
		}

		for (String mobjectDefKey : MObjectDefMap.keySet()) {
			moLsinfo.add(MObjectDefMap.get(mobjectDefKey));
		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}

	private PerfSnapsBean querySnapsOrVmhostInfo(Map<String, PerfSnapsBean> MObjectDefMap, PerfSnapsBean perfSnapObj,
			MODevice moDevice, String flag) {
		if (MObjectDefMap.containsKey(flag)) {
			perfSnapObj = MObjectDefMap.get(flag);
			perfSnapObj.setAlarmCount(perfSnapObj.getAlarmCount() + moDevice.getAlarmcount());
			if (("交换机").equals(flag)) {
				perfSnapObj.setClasslable(flag);
				perfSnapObj.setMoClassID("6");
			} else if (("虚拟机" + "_" + 9).equals(flag)) {
				perfSnapObj.setClasslable("虚拟机");
				perfSnapObj.setMoClassID("9");
			} else {
				perfSnapObj.setClasslable(moDevice.getClasslable());
				perfSnapObj.setMoClassID(String.valueOf(moDevice.getSourceMOClassID()));
			}

		} else {
			if (("交换机").equals(flag)) {
				perfSnapObj.setClasslable(flag);
				perfSnapObj.setMoClassID("6");
			} else if (("虚拟机" + "_" + 9).equals(flag)) {
				perfSnapObj.setClasslable("虚拟机");
				perfSnapObj.setMoClassID("9");
			} else {
				perfSnapObj.setClasslable(moDevice.getClasslable());
				perfSnapObj.setMoClassID(String.valueOf(moDevice.getSourceMOClassID()));
			}

			perfSnapObj.setAlarmCount(moDevice.getAlarmcount());
			perfSnapObj.setCountDevice(moDevice.getCountdevice());
		}
		return perfSnapObj;
	}

	/**
	 * 设备快照入口，查询设备快照（新修改）
	 * 
	 * @return
	 * @crea by zhuk 20150722
	 * @throws Exception
	 */
	@RequestMapping("/querySnapshotInfo")
	@ResponseBody
	public Map<String, Object> querySnapshotInfo() throws Exception {
		logger.info("开始...加载设备快照页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String par = request.getParameter("type");
		String moClass = request.getParameter("moClass");
		List<MODevice> moLsCount = new ArrayList<MODevice>();
		List<MODevice> snapsHostCount = new ArrayList<MODevice>();
		List<MODevice> MObjectDef = new ArrayList<MODevice>();
		List<PerfSnapsBean> moLsinfo = new ArrayList<PerfSnapsBean>();
		if ("all".equals(par)) {
			if(moClass.toLowerCase().equals("mo".toLowerCase())){
				moLsCount = modMaper.getMoClassIDAllCount(null);
			}else{
				List<Integer> classIDs=modMaper.getMobjectRelationClassIDS(getmoClassID(moClass));
				String aa=null;
				for(int i=0;i<classIDs.size();i++){
					if(aa==null){
						aa=String.valueOf(classIDs.get(i));
					}else{
						aa=aa+","+String.valueOf(classIDs.get(i));
					}
				}
				moLsCount = modMaper.getMoClassIDAllCount(aa);
			}
			
		} else {
			List<Integer> classIDs=modMaper.getMobjectRelationClassIDS(getmoClassID(moClass));
			String aa=null;
			for(int i=0;i<classIDs.size();i++){
				if(aa==null){
					aa=String.valueOf(classIDs.get(i));
				}else{
					aa=aa+","+String.valueOf(classIDs.get(i));
				}
			}
			moLsCount = modMaper.getMoClassIDCount(aa);
			//moLsCount = modMaper.getMoClassIDCount();
		}
		List<Integer> moClassIDlist = new ArrayList<Integer>();
		PerfSnapsBean perfSnapObj = null;
		Map<String, PerfSnapsBean> MObjectDefMap = new HashMap<String, PerfSnapsBean>();
		for (int i = 0; i < moLsCount.size(); i++) {
			moClassIDlist.add(moLsCount.get(i).getMoclassid());
		}
		// 查询设备总数
		params.put("classID", moClassIDlist.toString().replace("[", "").replace("]", ""));
		MObjectDef = modMaper.queryAllDeviceInfo(params);
		int switherCount = 0;
		for (MODevice SeriousDevice : MObjectDef) {
			if (SeriousDevice.getSourceMOClassID() == 59) {
				switherCount = SeriousDevice.getDevicealarmcount();
			}
			if (SeriousDevice.getSourceMOClassID() == 60) {
				switherCount += SeriousDevice.getDevicealarmcount();
			}
			if (SeriousDevice.getSourceMOClassID() == 6) {
				switherCount += SeriousDevice.getDevicealarmcount();
			}
		}
		// 查询总的设备数
		for (MODevice modevice : MObjectDef) {
			String flag = null;
			perfSnapObj = new PerfSnapsBean();
			if (modevice.getSourceMOClassID() == 59 || modevice.getSourceMOClassID() == 60
					|| modevice.getSourceMOClassID() == 6) {
				flag = "交换机";
			} else {
				flag = modevice.getClasslable() + "_" + modevice.getSourceMOClassID();
			}
			if (MObjectDefMap.containsKey(flag)) {
				perfSnapObj = MObjectDefMap.get(flag);
				perfSnapObj.setCountDevice(modevice.getCountdevice() + perfSnapObj.getCountDevice());
				if (("交换机").equals(flag)) {
					perfSnapObj.setClasslable(flag);
					perfSnapObj.setAlarmDeviceCount(switherCount);
					perfSnapObj.setMoClassID("6");
				} else {
					perfSnapObj.setClasslable(modevice.getClasslable());
					perfSnapObj.setAlarmDeviceCount(modevice.getDevicealarmcount());
					perfSnapObj.setMoClassID(String.valueOf(modevice.getSourceMOClassID()));
				}
			} else {
				if (("交换机").equals(flag)) {
					perfSnapObj.setClasslable(flag);
					perfSnapObj.setAlarmDeviceCount(switherCount);
					perfSnapObj.setMoClassID("6");
				} else {
					perfSnapObj.setClasslable(modevice.getClasslable());
					perfSnapObj.setAlarmDeviceCount(modevice.getDevicealarmcount());
					perfSnapObj.setMoClassID(String.valueOf(modevice.getSourceMOClassID()));
				}

				perfSnapObj.setCountDevice(modevice.getCountdevice());
				MObjectDefMap.put(flag, perfSnapObj);
			}
		}

		// 查询总的告警数量
		snapsHostCount = modMaper.queryAlarmInfoCounts(params);
		for (MODevice moDevice : snapsHostCount) {
			perfSnapObj = new PerfSnapsBean();
			String flag = null;
			if (moDevice.getSourceMOClassID() == 59 || moDevice.getSourceMOClassID() == 60
					|| moDevice.getSourceMOClassID() == 6) {
				flag = "交换机";
			} else {
				flag = moDevice.getClasslable() + "_" + moDevice.getSourceMOClassID();
			}
			perfSnapObj = querySnapsOrVmhostInfo(MObjectDefMap, perfSnapObj, moDevice, flag);
			MObjectDefMap.put(flag, perfSnapObj);
		}

		for (String mobjectDefKey : MObjectDefMap.keySet()) {
			moLsinfo.add(MObjectDefMap.get(mobjectDefKey));
		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}

	public MODevice getSnapObj(List<MODevice> list) {
		MODevice mo = new MODevice();
		int flag;
		for (int i = 0; i < list.size(); i++) {
			flag = list.get(i).getFlag();
			if (flag == 1) {
				mo.setAlarmcount(list.get(i).getCount());
			} else if (flag == 2) {
				mo.setDevicealarmcount(list.get(i).getCount());
			} else if (flag == 3) {
				mo.setDevicecount(list.get(i).getCount());
			}
			mo.setClasslable(list.get(i).getClasslable());
			mo.setMoclassid(list.get(i).getMoclassid());
		}
		return mo;
	}

	/**
	 * TOP10CPU使用率
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/topPerfCpuInfo")
	@ResponseBody
	public Map<String, Object> topPerfCpuInfo() throws Exception {
		logger.info("开始...加载TOP10CPU页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		String moClass = request.getParameter("moClass");
		String num = request.getParameter("cpuNum");
		String topOrder = request.getParameter("topOrder");
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");
		Integer cpuNum = null;
		if (num == null || "".equals(num)) {
			cpuNum = 10;
		} else {
			cpuNum = Integer.parseInt(num);
		}
		getParam(moClass);
		getmoClassID(moClass);
		String smoClassID = "";
		if ("switcher".equals(moClass)) {
			smoClassID = "59,60";
			params.put("moClassID", smoClassID);
		} else {
			List<Integer> classIDs=modMaper.getMobjectRelationClassIDS(moClassID);
			String aa=null;
			for(int i=0;i<classIDs.size();i++){
				if(aa==null){
					aa=String.valueOf(classIDs.get(i));
				}else{
					aa=aa+","+String.valueOf(classIDs.get(i));
				}
			}
			params.put("moClassID", aa);
			//System.out.println("aa===="+aa);
		}
		params.put("KPIName", KPINameDef.PERUSAGE);
		//System.out.println("tableName===="+tableName);
		params.put("tableName", tableName);
		params.put("cpuNum", cpuNum);
		params.put("topOrder", topOrder);
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		try {
			List<MODevice> moLsinfo = modMaper.getPerfCPU(params);
			result.put("rows", moLsinfo);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage());
		}
		logger.info("结束...加载TOP10CPU页面数据");
		return result;
	}

	/**
	 * TOP10内存 使用率
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/topPerfMemoryInfo")
	@ResponseBody
	public Map<String, Object> topPerfMemoryInfo() throws Exception {
		logger.info("开始...加载TOP10内存页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		String moClass = request.getParameter("moClass");
		String num = request.getParameter("num");
		String topOrder = request.getParameter("topOrder");
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");
		Integer memNum = null;
		if (num == null || "".equals(num)) {
			memNum = 10;
		} else {
			memNum = Integer.parseInt(num);
		}
		getParam(moClass);
		getmoClassID(moClass);
		String smoClassID = "";
		if ("switcher".equals(moClass)) {
			smoClassID = "59,60";
			params.put("moClassID", smoClassID);
		} else {
			List<Integer> classIDs=modMaper.getMobjectRelationClassIDS(moClassID);
			String aa=null;
			for(int i=0;i<classIDs.size();i++){
				if(aa==null){
					aa=String.valueOf(classIDs.get(i));
				}else{
					aa=aa+","+String.valueOf(classIDs.get(i));
				}
			}
			params.put("moClassID", aa);
			//params.put("moClassID", moClassID);
		}

		if ("router".equals(moClass) || "switcher".equals(moClass)) {
			params.put("KPIName", KPINameDef.NETMEMUSAGE);
		} else {
			params.put("KPIName", KPINameDef.PHYMEMUSAGE);
		}
		params.put("tableName", tableName);
		params.put("num", memNum);
		params.put("topOrder", topOrder);
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		logger.debug("param 表名称[tableName]={} ", tableName);
		try {
			List<MODevice> moLsinfo = modMaper.getPerfMemory(params);
			result.put("rows", moLsinfo);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage());
		}

		logger.info("结束...加载TOP10内存页面数据");
		return result;
	}

	/**
	 * TOP10虚拟内存 使用率
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/topPerfVirMemoryInfo")
	@ResponseBody
	public Map<String, Object> topPerfVirMemoryInfo() throws Exception {
		logger.info("开始...加载TOP10虚拟内存页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		String moClass = request.getParameter("moClass");
		getParam(moClass);
		getmoClassID(moClass);
		List<Integer> classIDs=modMaper.getMobjectRelationClassIDS(moClassID);
		String aa=null;
		for(int i=0;i<classIDs.size();i++){
			if(aa==null){
				aa=String.valueOf(classIDs.get(i));
			}else{
				aa=aa+","+String.valueOf(classIDs.get(i));
			}
		}
		params.put("moClassID", aa);
		//params.put("moClassID", moClassID);
		params.put("KPIName", KPINameDef.VIRMEMUSAGE);
		params.put("tableName", tableName);
		params.put("topOrder", "Desc");
		params.put("num", 10);
		params.put("timeBegin", KPINameDef.queryBetweenDay().get("timeBegin").toString());
		params.put("timeEnd", KPINameDef.queryBetweenDay().get("timeEnd").toString());
		logger.debug("param 表名称[tableName]={} ", tableName);
		try {
			List<MODevice> moLsinfo = modMaper.getPerfMemory(params);
			result.put("rows", moLsinfo);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage());
		}

		logger.info("结束...加载TOP10虚拟内存页面数据");
		return result;
	}

	/**
	 * TOP10主机硬盘 使用率
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/topPerfDiskInfo")
	@ResponseBody
	public Map<String, Object> topPerfDiskInfo() throws Exception {
		logger.info("开始...加载硬盘页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		String moClass = request.getParameter("moClass");
		String num = request.getParameter("num");
		String topOrder = request.getParameter("topOrder");
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");
		Integer diskNum = null;
		if (num == null || "".equals(num)) {
			diskNum = 10;
		} else {
			diskNum = Integer.parseInt(num);
		}
		getParam(moClass);
		getmoClassID(moClass);
		List<Integer> classIDs=modMaper.getMobjectRelationClassIDS(moClassID);
		String aa=null;
		for(int i=0;i<classIDs.size();i++){
			if(aa==null){
				aa=String.valueOf(classIDs.get(i));
			}else{
				aa=aa+","+String.valueOf(classIDs.get(i));
			}
		}
		params.put("moClassID", aa);
		//params.put("moClassID", moClassID);
		params.put("KPIName", KPINameDef.DISKUSAGE);
		params.put("tableName", tableName);
		params.put("num", diskNum);
		params.put("topOrder", topOrder);
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		logger.debug("param 表名称[tableName]={} ", tableName);
		List<MODevice> moLsinfo = modMaper.getPerfDisk(params);
		for (int i = 0; i < moLsinfo.size(); i++) { 
			if (moLsinfo.get(i).getRawdescr() != "") {
				if (moLsinfo.get(i).getRawdescr().indexOf(":")>-1 && ":".equals(moLsinfo.get(i).getRawdescr().subSequence(1, 2))) {
					moLsinfo.get(i).setRawdescr(moLsinfo.get(i).getRawdescr().substring(0, 2));
				} else if (moLsinfo.get(i).getRawdescr().indexOf("/")>-1 &&"/".equals(moLsinfo.get(i).getRawdescr().subSequence(0, 1))) {
					moLsinfo.get(i).setRawdescr(moLsinfo.get(i).getRawdescr());
				} else if (moLsinfo.get(i).getRawdescr().indexOf("_")>-1 && "_".equals(moLsinfo.get(i).getRawdescr().subSequence(9, 10))) {
					moLsinfo.get(i).setRawdescr(moLsinfo.get(i).getRawdescr().substring(0, 10));
				} else {
					moLsinfo.get(i).setRawdescr(moLsinfo.get(i).getRawdescr());
				}
			}
		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载TOP10硬盘页面数据");
		return result;
	}

	/**
	 * TOP10宿主机硬盘 使用率
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/topPerfVhostDiskInfo")
	@ResponseBody
	public Map<String, Object> topPerfVhostDiskInfo() throws Exception {
		logger.info("开始...加载硬盘页面数据");
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("moClassID", 8);
		params.put("KPIName", KPINameDef.DISKRATE);
		params.put("tableName", "PerfSnapshotVHost");
		params.put("topOrder", "Desc");
		params.put("num", 10);
		params.put("timeBegin", KPINameDef.queryBetweenDay().get("timeBegin").toString());
		params.put("timeEnd", KPINameDef.queryBetweenDay().get("timeEnd").toString());
		List<MODevice> moLsinfo = modMaper.getPerfDisk(params);
		for (int i = 0; i < moLsinfo.size(); i++) {
			if (moLsinfo.get(i).getRawdescr() != "") {
				if (":".equals(moLsinfo.get(i).getRawdescr().subSequence(1, 2))) {
					moLsinfo.get(i).setRawdescr(moLsinfo.get(i).getRawdescr().substring(0, 2));
				} else if ("/".equals(moLsinfo.get(i).getRawdescr().subSequence(0, 1))) {
					moLsinfo.get(i).setRawdescr(moLsinfo.get(i).getRawdescr());
				} else if ("_".equals(moLsinfo.get(i).getRawdescr().subSequence(9, 10))) {
					moLsinfo.get(i).setRawdescr(moLsinfo.get(i).getRawdescr().substring(0, 10));
				} else {
					moLsinfo.get(i).setRawdescr(moLsinfo.get(i).getRawdescr());
				}
			}
			moLsinfo.get(i).setPerfvalue(moLsinfo.get(i).getPerfvalue()/ 1024);
		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载TOP10硬盘页面数据");

		return result;
	}

	/**
	 * TOP10列表 for 接口流量公共
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/topPerfFlowsInfo")
	@ResponseBody
	public Map<String, Object> topPerfFlowsInfo() throws Exception {
		logger.info("开始...加载公共详情信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		String moClass = request.getParameter("moClass");
		String num = request.getParameter("num");
		String topOrder = request.getParameter("topOrder");
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");
		getmoClassID(moClass);
		getIfTableName(moClass);
		Integer flowsNum = null;
		if (num == null || "".equals(num)) {
			flowsNum = 10;
		} else {
			flowsNum = Integer.parseInt(num);
		}
		String smoClassID = "";
		if ("switcher".equals(moClass)) {
			smoClassID = "59,60";
			params.put("moClassID", smoClassID);
		} else {
			List<Integer> classIDs=modMaper.getMobjectRelationClassIDS(moClassID);
			String aa=null;
			for(int i=0;i<classIDs.size();i++){
				if(aa==null){
					aa=String.valueOf(classIDs.get(i));
				}else{
					aa=aa+","+String.valueOf(classIDs.get(i));
				}
			}
			params.put("moClassID", aa);
			//params.put("moClassID", moClassID);
		}
		params.put("KPISUM", KPINameDef.FLOWS);
		params.put("snapTable", ifTableName);
		params.put("num", flowsNum);
		params.put("topOrder", topOrder);
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		List<MODevice> moLsCount = modMaper.getKPIIFCount(params);
		Map params2 = new HashMap();
		params2.put("KPIIN", KPINameDef.INFLOWS);
		params2.put("KPIOUT", KPINameDef.OUTFLOWS);
		params2.put("snapTable", ifTableName);
		List<MODevice> moLsinfo = new ArrayList<MODevice>();
		if (moLsCount.size() > 0) {
			for (int i = 0; i < moLsCount.size(); i++) {
				params2.put("COMMID", moLsCount.get(i).getMoid());
				List<MODevice> moLsinfo1 = modMaper.getKPIIFValue(params2);
				if (moLsinfo1 != null && moLsinfo1.size() > 0) {
					MODevice mo = getIfowsObj(moLsinfo1);
					moLsinfo.add(mo);
				}
			}
		}
		result.put("rows", moLsinfo);

		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}

	public MODevice getIfowsObj(List<MODevice> list) {
		MODevice mo = new MODevice();
		DecimalFormat df = new DecimalFormat("#.##");
		String name;
		String value;
		for (int i = 0; i < list.size(); i++) {
			name = list.get(i).getKpiname();
			value = df.format(list.get(i).getViewValue());
			mo.setMoname(list.get(i).getMoname());
			mo.setIfname(list.get(i).getIfname());
			mo.setDeviceip(list.get(i).getDeviceip());
			mo.setMoid(list.get(i).getMoid());
			mo.setMoclassid(list.get(i).getMoclassid());
			mo.setMoalias(list.get(i).getMoalias());
			mo.setIfMOID(list.get(i).getIfMOID());
			if (name.equals(KPINameDef.INFLOWS)) {
				mo.setInflows(HostComm.getBytesToFlows(Double.parseDouble(value)));
			} else if (name.equals(KPINameDef.OUTFLOWS)) {
				mo.setOutflows(HostComm.getBytesToFlows(Double.parseDouble(value)));
			}

		}
		return mo;
	}

	/**
	 * TOP10列表 for 接口带宽公共
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/topPerfIfUsageInfo")
	@ResponseBody
	public Map<String, Object> topPerfIfUsageInfo() throws Exception {
		logger.info("开始...加载公共详情信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		String moClass = request.getParameter("moClass");
		String num = request.getParameter("num");
		String topOrder = request.getParameter("topOrder");
		String timeBegin = request.getParameter("timeBegin");
		String timeEnd = request.getParameter("timeEnd");
		getmoClassID(moClass);
		getIfTableName(moClass);
		Integer flowsNum = null;
		if (num == null || "".equals(num)) {
			flowsNum = 10;
		} else {
			flowsNum = Integer.parseInt(num);
		}
		Map params = new HashMap();
		getmoClassID(moClass);
		getIfTableName(moClass);
		String smoClassID = "";
		if ("switcher".equals(moClass)) {
			smoClassID = "59,60";
			params.put("moClassID", smoClassID);
		} else {
			List<Integer> classIDs=modMaper.getMobjectRelationClassIDS(moClassID);
			String aa=null;
			for(int i=0;i<classIDs.size();i++){
				if(aa==null){
					aa=String.valueOf(classIDs.get(i));
				}else{
					aa=aa+","+String.valueOf(classIDs.get(i));
				}
			}
			params.put("moClassID", aa);
			//params.put("moClassID", moClassID);
		}
		params.put("KPISUM", KPINameDef.IFUSAGE);
		params.put("snapTable", ifTableName);
		params.put("num", flowsNum);
		params.put("topOrder", topOrder);
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		Map params2 = new HashMap();
		params2.put("KPIIN", KPINameDef.INUSAGE);
		params2.put("KPIOUT", KPINameDef.OUTUSAGE);
		params2.put("snapTable", ifTableName);
		List<MODevice> moLsCount = modMaper.getKPIIFCount(params);
		List<MODevice> moLsinfo = new ArrayList<MODevice>();
		if (moLsCount.size() > 0) {
			for (int i = 0; i < moLsCount.size(); i++) {
				params2.put("COMMID", moLsCount.get(i).getMoid());
				List<MODevice> moLsinfo1 = modMaper.getKPIIFValue(params2);
				if (moLsinfo1 != null && moLsinfo1.size() > 0) {
					MODevice mo = getIfUsageObj(moLsinfo1);
					moLsinfo.add(mo);
				}
			}
		}
		result.put("rows", moLsinfo);

		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}

	public MODevice getIfUsageObj(List<MODevice> list) {
		MODevice mo = new MODevice();
		DecimalFormat df = new DecimalFormat("#.##");
		String name;
		String value;
		for (int i = 0; i < list.size(); i++) {
			name = list.get(i).getKpiname();
			value = df.format(list.get(i).getViewValue());
			mo.setMoname(list.get(i).getMoname());
			mo.setIfname(list.get(i).getIfname());
			mo.setDeviceip(list.get(i).getDeviceip());
			mo.setMoid(list.get(i).getMoid());
			mo.setMoalias(list.get(i).getMoalias());
			mo.setMoclassid(list.get(i).getMoclassid());
			mo.setIfMOID(list.get(i).getIfMOID());
			if (name.equals(KPINameDef.INUSAGE)) {
				mo.setInusage(value);
			} else if (name.equals(KPINameDef.OUTUSAGE)) {
				mo.setOutusage(value);
			}

		}
		return mo;
	}

	/**
	 * 最新告警
	 * 
	 * @return
	 */
	@RequestMapping("/topAlarmActiveInfo")
	@ResponseBody
	public Map<String, Object> topAlarmActiveInfo(HttpServletRequest request) {
		logger.info("开始...加载TOPN最新告警页面数据");
		Map<String, Object> result = new HashMap<String, Object>();
		String moClass = request.getParameter("moClass");
		String num = request.getParameter("num");
		Integer alarmNum = null;
		if (num == null || "".equals(num)) {
			alarmNum = 10;
		} else {
			alarmNum = Integer.parseInt(num);
		}
		Map<String, Integer> mapping = new HashMap<String, Integer>();
		mapping.put("mo", 1);
		mapping.put("PHost", 7);
		mapping.put("VHost", 8);
		mapping.put("VM", 9);
		mapping.put("Router", 5);
		mapping.put("Switcher", 6);
		mapping.put("router", 5);
		mapping.put("switcher", 6);
		mapping.put("host", 3);
		mapping.put("safeDevice",106);
		try {

			// 设置查询参数
			Map<String, Object> paramMap = new HashMap<String, Object>();
			// paramMap.put("num",alarmNum);
			List<AlarmActiveDetail> moLsinfo = null;
			// 查询分页数据
			if ("indexAll".equals(moClass)) {
				moLsinfo = modMaper.getIndexAlarmActive(alarmNum);// 首页告警
			} else {
				moLsinfo = modMaper.getAlarmActive(mapping.get(moClass));

				if (moLsinfo.size() >= alarmNum) {
					moLsinfo = moLsinfo.subList(0, alarmNum);
				}
			}
			// 查询总记录数
			result.put("rows", moLsinfo);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage());
		}

		logger.info("结束...加载TOPN最新告警页面数据");
		return result;
	}

	/**
	 * 打开主机设备详情
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/toShowHostDetail")
	@ResponseBody
	public ModelAndView toShowHostDetail(HttpServletRequest request, ModelMap map) {
		if (request.getParameter("moID") != null) {
			MOID = Integer.parseInt(request.getParameter("moID"));
		}
		MODevice mo = modMaper.getHostDetail(MOID);
		try {
			if (mo != null) {
				if (mo.getHmemorysize() != null && !"".equals(mo.getHmemorysize())) {
					mo.setHmemorysize(HostComm.getBytesToSize(Long.parseLong(mo.getHmemorysize())));
				} else {
					mo.setHmemorysize("");
				}
				if (mo.getHmVirMemorysize() != null && !"".equals(mo.getHmVirMemorysize())) {
					mo.setHmVirMemorysize(HostComm.getBytesToSize(Long.parseLong(mo.getHmVirMemorysize())));
				} else {
					mo.setHmVirMemorysize("");
				}
				if (mo.getSumdisksize() != null && !"".equals(mo.getSumdisksize())) {
					mo.setSumdisksize(HostComm.getBytesToSize(Long.parseLong(mo.getSumdisksize())));
				} else {
					mo.setSumdisksize("");
				}
				if ("".equals(mo.getMoalias()) || "null".equals(mo.getMoalias()) || mo.getMoalias() == null) {
					mo.setMoalias("");
				} else {
					mo.setMoalias("(" + mo.getMoalias() + ")");
				}
			}
			map.put("mo", mo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("monitor/host/hostInfoDetail");
	}

	/**
	 * 打开虚拟机设备详情
	 */
	@RequestMapping("/toShowVMDetail")
	@ResponseBody
	public ModelAndView toShowVMDetail(HttpServletRequest request, ModelMap map) {
		MOID = Integer.parseInt(request.getParameter("moID"));
		MODevice mo = modMaper.getVMDetail(MOID);
		if (mo != null) {
			if (mo.getCpuallocated() != null && !"".equals(mo.getCpuallocated())) {
				mo.setCpuallocated(HostComm.getBytesToHz(Long.parseLong(mo.getCpuallocated())));
			} else {
				mo.setCpuallocated("");
			}
			if (mo.getMemory() != null && !"".equals(mo.getMemory())) {
				mo.setMemory(HostComm.getBytesToSize(Long.parseLong(mo.getMemory())));
			} else {
				mo.setMemory("");
			}
			if (mo.getMemoryoverhead() != null && !"".equals(mo.getMemoryoverhead())) {
				mo.setMemoryoverhead(HostComm.getBytesToSize(Long.parseLong(mo.getMemoryoverhead())));
			} else {
				mo.setMemoryoverhead("");
			}

			// 1-已连接 2-已断开 3-不可取的 4-不合法的 5-独立的
			if (KPINameDef.CONNECTED.equals(mo.getConnectstatus())) {
				mo.setConnectstatus("已连接");
			} else if (KPINameDef.DISCONNECTED.equals(mo.getConnectstatus())) {
				mo.setConnectstatus("已断开");
			} else if (KPINameDef.INACCESSIBLE.equals(mo.getConnectstatus())) {
				mo.setConnectstatus("不可取");
			} else if (KPINameDef.INVALID.equals(mo.getConnectstatus())) {
				mo.setConnectstatus("非合法");
			} else if (KPINameDef.ORPHANED.equals(mo.getConnectstatus())) {
				mo.setConnectstatus("独立的");
			} else {
				mo.setConnectstatus("unknown");
			}
			if ("".equals(mo.getMoalias()) || "null".equals(mo.getMoalias()) || mo.getMoalias() == null) {
				mo.setMoalias("");
			} else {
				mo.setMoalias("(" + mo.getMoalias() + ")");
			}
			map.put("mo", mo);
		}
		return new ModelAndView("monitor/host/vmInfoDetail");
	}

	/**
	 * 接口详情
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/toShowIfDetail")
	@ResponseBody
	public ModelAndView toShowIfDetail(HttpServletRequest request, ModelMap map) {
		logger.info("加载接口详情页面");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("MOID", Integer.parseInt(request.getParameter("moID")));
		params.put("IfMOID", request.getParameter("IfMOID"));
		params.put("timeBegin", KPINameDef.queryBetweenTime().get("timeBegin"));
		params.put("timeEnd", KPINameDef.queryBetweenTime().get("timeEnd"));
		MODevice mo = modMaper.getIfDetail(params);
		if (mo != null) {
			Map<Integer, String> imMap = DictionaryLoader.getConstantItems("IfAdminStatus");// 操作状态
			mo.setIfadminstatus(imMap.get(Integer.parseInt(mo.getIfadminstatus())));
			if (KPINameDef.DEVICE_UP.equals(mo.getIfoperstatus())) {
				mo.setOperstatus("up.png");
				mo.setOperaTip("UP");
			} else if (KPINameDef.DEVICE_DOWN.equals(mo.getIfoperstatus())) {
				mo.setOperstatus("down.png");
				mo.setOperaTip("DOWN");
			} else {
				mo.setOperstatus("unknown.png");
				mo.setOperaTip("未知");
			}
		} else {
			mo.setOperstatus("unknown.png");
			mo.setOperaTip("未知");
		}

		if (mo.getIfdescr().length() > 60) {
			mo.setDescStr(mo.getIfdescr().substring(0, 60) + "......");
		} else {
			mo.setDescStr(mo.getIfdescr());
		}
		Map<Integer, String> imMap = DictionaryLoader.getConstantItems("InterfaceType");// 接口类型
		mo.setIftype(imMap.get(Integer.parseInt(mo.getIftype())));
		map.put("mo", mo);
		return new ModelAndView("monitor/host/ifInfoDetail");
	}

	public int getMultiple() {
		return webSiteMapper.getConfParamValue();
	}

	/**
	 * 打开设备设备详情
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/toShowDeviceDetail")
	@ResponseBody
	public ModelAndView toShowDeviceDetail(HttpServletRequest request, ModelMap map) {
		logger.info("加载设备详情页面");
		if (request.getParameter("moID") != null && !"".equals(request.getParameter("moID"))) {
			MOID = Integer.parseInt(request.getParameter("moID"));
		} else {
			MOID = 0;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("MOID", MOID);
		params.put("MID", KPINameDef.JOBPINGPOLL);
		MODevice mo = modMaper.getDeviceDetail(params);
		int period = 1;
		long curr = 0;
		long currTime = orclService.getCurrentDate().getTime();
		try {
			if (mo != null) {
				Date updateAlarmTime = mo.getUpdateAlarmTime();
				Date collectTime = mo.getCollecttime();
				if (mo.getDoIntervals() == null || "".equals(mo.getDoIntervals())) {
					period = mo.getDefDoIntervals() * getMultiple() * 60000;
				} else {
					period = mo.getDoIntervals() * getMultiple() * 1000;
				}
				if (collectTime != null) {
					curr = currTime - mo.getCollecttime().getTime();
					if (curr <= period) {
						if (KPINameDef.DEVICE_UP.equals(mo.getOperstatus())) {
							mo.setOperstatus("up.png");
							mo.setOperaTip("UP");
						} else if (KPINameDef.DEVICE_DOWN.equals(mo.getOperstatus())) {
							mo.setOperstatus("down.png");
							mo.setOperaTip("DOWN");
						}
						if (updateAlarmTime != null) {
							String durationTime = HostComm.getDurationToTime(currTime - updateAlarmTime.getTime());
							mo.setDurationTime(durationTime);
						} else {
							mo.setDurationTime("");
						}
					} else {
						mo.setOperstatus("unknown.png");
						mo.setOperaTip("未知");
						String durationTime = HostComm.getDurationToTime(currTime - collectTime.getTime());
						mo.setDurationTime(durationTime);
					}
				} else {
					mo.setOperstatus("unknown.png");
					mo.setOperaTip("未知");
					mo.setDurationTime("");
				}
				if ("".equals(mo.getMoalias()) || "null".equals(mo.getMoalias()) || mo.getMoalias() == null) {
					mo.setMoalias("");
				} else {
					mo.setMoalias("(" + mo.getMoalias() + ")");
				}
			}
			map.put("mo", mo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("monitor/host/deviceInfoDetail");
	}

	/**
	 * 打开宿主机详情
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/toShowPhysicsDetail")
	@ResponseBody
	public ModelAndView toShowPhysicsDetail(HttpServletRequest request, ModelMap map) {
		logger.info("加载宿主机详情页面");
		MOID = Integer.parseInt(request.getParameter("moID"));
		MODevice mo = modMaper.getPhysicsDetail(MOID);
		if (mo != null) {
			if (mo.getCpucores() != null && !"".equals(mo.getCpucores())) {
				mo.setCpucores(HostComm.getBytesToHz(Long.parseLong(mo.getCpucores())));
			} else {
				mo.setCpucores("");
			}
			if (mo.getMemoryusage() != null && !"".equals(mo.getMemoryusage())) {
				mo.setMemoryusage(HostComm.getBytesToSize(Long.parseLong(mo.getMemoryusage())));
			} else {
				mo.setMemoryusage("");
			}

			// 1-连接;2-未连接;3-无反应
			if (KPINameDef.CONNECTED.equals(mo.getConnectionstatus())) {
				mo.setConnectionstatus("连接");
			} else if (KPINameDef.DISCONNECTED.equals(mo.getConnectionstatus())) {
				mo.setConnectionstatus("未连接");
			} else if (KPINameDef.NOTRESPONDING.equals(mo.getConnectionstatus())) {
				mo.setConnectionstatus("无反应");
			} else {
				mo.setConnectionstatus("未知");
			}
			if ("".equals(mo.getMoalias()) || "null".equals(mo.getMoalias()) || mo.getMoalias() == null) {
				mo.setMoalias("");
			} else {
				mo.setMoalias("(" + mo.getMoalias() + ")");
			}
		}
		map.put("mo", mo);
		return new ModelAndView("monitor/host/physicsInfoDetail");
	}

	/**
	 * 打开设备类型相关信息
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/toShowTypeInfo")
	@ResponseBody
	public ModelAndView toShowTypeInfo(HttpServletRequest request) {
		logger.info("加载类型信息页面");
		String moClass = request.getParameter("moClass");
		ModelAndView mode = new ModelAndView();
		if ("7".equals(moClass)) {
			mode.setViewName("monitor/host/hostViewList");
		} else if ("8".equals(moClass)) {
			mode.setViewName("monitor/host/vhostViewList");
		} else if ("9".equals(moClass)) {
			mode.setViewName("monitor/host/vhostViewList");
		}
		return mode;
	}

	/**
	 * 获取主机视图列表信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getHostViewList")
	@ResponseBody
	public Map<String, Object> getHostViewList() throws Exception {
		logger.info("开始...加载主机视图列表页面数据");
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		List<MODevice> moLsCount = modMaper.getHostViewCount();
		List<MODevice> moLsinfo = new ArrayList<MODevice>();
		params.put("timeBegin", KPINameDef.queryBetweenTime().get("timeBegin"));
		params.put("timeEnd", KPINameDef.queryBetweenTime().get("timeEnd"));
		Integer alarmLevel = 0;
		if (moLsCount.size() > 0) {
			for (int i = 0; i < moLsCount.size(); i++) {
				params.put("MOID", moLsCount.get(i).getDevicemoid());
				List<MODevice> moLsinfo1 = new ArrayList<MODevice>();
				alarmLevel = modMaper.getViewLevel(moLsCount.get(i).getDevicemoid());
				if (alarmLevel != null && !"".equals(alarmLevel) && !"null".equals(alarmLevel)) {
					if (alarmLevel != 0) {
						moLsinfo1 = modMaper.getHostViewInfo(params);
					} else {
						moLsinfo1 = modMaper.getHostViewLevInfo(params);
					}
				}

				if (moLsinfo1 != null && moLsinfo1.size() > 0) {
					MODevice mo = gethostViewObj(moLsinfo1, alarmLevel);
					moLsinfo.add(mo);
				}
			}
		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}

	public MODevice gethostViewObj(List<MODevice> list, Integer alarmLevel) {
		MODevice mo = new MODevice();
		DecimalFormat df = new DecimalFormat("#.##");
		int flag;
		for (int i = 0; i < list.size(); i++) {
			flag = list.get(i).getFlag();
			if (flag == 1) {
				mo.setCpuvalue(df.format(list.get(i).getViewValue()) + "%");
			} else if (flag == 2) {
				mo.setMemoryvalue(df.format(list.get(i).getViewValue()) + "%");
			} else if (flag == 3) {
				if (KPINameDef.up == list.get(i).getViewValue()) {
					mo.setDevicestatus("up.png");
					mo.setOperaTip("UP");
				} else if (KPINameDef.down == list.get(i).getViewValue()) {
					mo.setDevicestatus("down.png");
					mo.setOperaTip("DOWN");
				} else {
					mo.setDevicestatus("unknown.png");
					mo.setOperaTip("未知");
				}
			} else if (flag == 4) {
				mo.setIfcount(list.get(i).getViewValue());
			}
			if (alarmLevel == 0) {
				mo.setLevelicon("right.png");
				mo.setAlarmLevelName("正常");
			} else {
				mo.setLevelicon(list.get(i).getLevelicon());
				mo.setAlarmLevelName(list.get(i).getAlarmLevelName());
			}
			mo.setDeviceip(list.get(i).getDeviceip());
			mo.setMoname(list.get(i).getMoname());
			mo.setOs(list.get(i).getOs());
			mo.setMoclassid(list.get(i).getMoclassid());
			mo.setMoid(list.get(i).getMoid());
		}
		return mo;
	}

	/**
	 * 获取宿主机视图列表信息
	 * 
	 * @return
	 */
	@RequestMapping("/getVHostViewList")
	@ResponseBody
	public Map<String, Object> getVHostViewList() throws Exception {
		logger.info("开始...加载宿主机视图列表页面数据");
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		List<MODevice> moLsCount = modMaper.getVHostViewCount();
		List<MODevice> moLsinfo = new ArrayList<MODevice>();
		params.put("timeBegin", KPINameDef.queryBetweenTime().get("timeBegin"));
		params.put("timeEnd", KPINameDef.queryBetweenTime().get("timeEnd"));
		Integer alarmLevel = 0;
		if (moLsCount.size() > 0) {
			for (int i = 0; i < moLsCount.size(); i++) {
				params.put("MOID", moLsCount.get(i).getDevicemoid());
				List<MODevice> moLsinfo1 = new ArrayList<MODevice>();
				alarmLevel = modMaper.getViewLevel(moLsCount.get(i).getDevicemoid());
				if (alarmLevel != null && !"".equals(alarmLevel) && !"null".equals(alarmLevel)) {
					if (alarmLevel != 0) {
						moLsinfo1 = modMaper.getVHostViewInfo(params);
					} else {
						moLsinfo1 = modMaper.getVHostViewLevInfo(params);
					}
				}
				if (moLsinfo1 != null && moLsinfo1.size() > 0) {
					MODevice mo = getVhostViewObj(moLsinfo1, alarmLevel);
					moLsinfo.add(mo);
				}
			}
		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}

	public MODevice getVhostViewObj(List<MODevice> list, Integer alarmLevel) {
		MODevice mo = new MODevice();
		DecimalFormat df = new DecimalFormat("#.##");
		int flag;
		for (int i = 0; i < list.size(); i++) {
			flag = list.get(i).getFlag();
			if (flag == 1) {
				mo.setCpuvalue(df.format(list.get(i).getViewValue()) + "%");
			} else if (flag == 2) {
				mo.setMemoryvalue(df.format(list.get(i).getViewValue()) + "%");
			} else if (flag == 3) {
				if (KPINameDef.up == list.get(i).getViewValue()) {
					mo.setDevicestatus("up.png");
					mo.setOperaTip("UP");
				} else if (KPINameDef.down == list.get(i).getViewValue()) {
					mo.setDevicestatus("down.png");
					mo.setOperaTip("DOWN");
				} else {
					mo.setDevicestatus("unknown.png");
					mo.setOperaTip("未知");
				}
			}
			if (alarmLevel == 0) {
				mo.setLevelicon("right.png");
				mo.setAlarmLevelName("正常");
			} else {
				mo.setLevelicon(list.get(i).getLevelicon());
				mo.setAlarmLevelName(list.get(i).getAlarmLevelName());
			}
			mo.setDeviceip(list.get(i).getDeviceip());
			mo.setMoname(list.get(i).getMoname());
			mo.setMoclassid(list.get(i).getMoclassid());
			mo.setMoid(list.get(i).getMoid());
		}
		return mo;
	}

	/*********************************************************
	 * 主机详情列表*************
	 */

	/**
	 * 主机详情信息列表 for 接口
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findDetailFlowsInfo")
	@ResponseBody
	public Map<String, Object> findDetailFlowsInfo() {
		logger.info("开始...加载主机详情信息列表 for 接口");
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", MOID);
		try {
			List<MODevice> moLsinfo = modMaper.getDetailFlowsInfo(params);
			if (moLsinfo != null) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					if (KPINameDef.CONNECTED.equals(moLsinfo.get(i).getOperstatus())) {
						moLsinfo.get(i).setOperstatus("up");
					} else if (KPINameDef.DISCONNECTED.equals(moLsinfo.get(i).getOperstatus())) {
						moLsinfo.get(i).setOperstatus("down");
					} else if (KPINameDef.NOTRESPONDING.equals(moLsinfo.get(i).getOperstatus())) {
						moLsinfo.get(i).setOperstatus("testing");
					} else {
						moLsinfo.get(i).setDevicestatus("unknown");
					}
				}

			}
			result.put("rows", moLsinfo);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage());
		}

		logger.info("结束...加载主机详情信息列表 for 接口");
		return result;
	}

	/**
	 * 主机详情信息列表 for 最近告警
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findDetailAlarmInfo")
	@ResponseBody
	public Map<String, Object> findDetailAlarmInfo() throws Exception {
		logger.info("开始...加载主机详情信息列表 for 最近告警");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", MOID);
		int alarmNum = Integer.parseInt(request.getParameter("alarmNum"));
		try {
			List<AlarmActiveDetail> moLsinfo = modMaper.getDetailAlarmInfo(params);
			if (moLsinfo.size() >= alarmNum) {
				moLsinfo = moLsinfo.subList(0, alarmNum);
			}
			result.put("rows", moLsinfo);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage());
		}

		logger.info("结束...加载主机详情信息列表 for 最近告警");
		return result;
	}

	/*********************************************************
	 * 宿主机详情列表************
	 */
	/**
	 * 宿主机详情信息列表 for 虚拟机列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findVHostForVMInfo")
	@ResponseBody
	public Map<String, Object> findVHostForVMInfo() {
		logger.info("开始...加载宿主机详情信息列表 for 虚拟机列表");
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", MOID);
		try {
			List<MODevice> moLsinfo = modMaper.getVHostForVMInfo(params);
			if (moLsinfo != null) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					moLsinfo.get(i).setMemory(HostComm.getBytesToSize(Long.parseLong(moLsinfo.get(i).getMemory())));
					if (KPINameDef.POWEREDON.equals(moLsinfo.get(i).getOperstatus())) {
						moLsinfo.get(i).setOperstatus("start.png");
						moLsinfo.get(i).setOperaTip("打开");
					} else if (KPINameDef.POWEREDOFF.equals(moLsinfo.get(i).getOperstatus())) {
						moLsinfo.get(i).setOperstatus("stop.png");
						moLsinfo.get(i).setOperaTip("关闭");
					} else if (KPINameDef.SUSPENDED.equals(moLsinfo.get(i).getOperstatus())) {
						moLsinfo.get(i).setOperstatus("pause.png");
						moLsinfo.get(i).setOperaTip("挂起");
					}
				}
			}
			result.put("rows", moLsinfo);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage());
		}

		logger.info("结束...加载宿主机详情信息列表 for 虚拟机列表");
		return result;
	}

	/**************************************************** TAB页公共 ******************/
	/**
	 * 详情信息列表 for CPU公共
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findCPUDetailInfo")
	@ResponseBody
	public Map<String, Object> findCPUDetailInfo() throws Exception {
		logger.info("开始...加载公共详情信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Integer, MODevice> cpuMap = new HashMap<Integer, MODevice>();
		Map params = new HashMap();
		params.put("MOID", MOID);
		params.put("tabName", "MOCPUs");
		String moClass = request.getParameter("moClass");
		String snapTable = "";
		if ("vhost".equals(moClass)) {
			snapTable = "PerfSnapshotVHost";
		} else if ("vm".equals(moClass)) {
			snapTable = "PerfSnapshotVM";
		} else if ("host".equals(moClass)||"loadbalance".equals(moClass)) {
			snapTable = "PerfSnapshotHost";
		}
		params.put("snapTable", snapTable);
		// List<MODevice> moLsCount = modMaper.getKPICount(params);

		List<MODevice> moLsinfo = new ArrayList<MODevice>();
		List<MODevice> moLsinfo1 = modMaper.getKPICPUValue(params);
		MODevice mo = null;
		if (moLsinfo1.size() > 0) {
			for (MODevice cpu : moLsinfo1) {
				mo = new MODevice();
				if (cpuMap.get(cpu.getMoid()) != null) {
					getCPUObj(cpuMap.get(cpu.getMoid()), cpu, moClass);
				} else {
					cpuMap.put(cpu.getMoid(), getCPUObj(mo, cpu, moClass));
				}
			}

			for (Map.Entry<Integer, MODevice> entry : cpuMap.entrySet()) {
				moLsinfo.add(entry.getValue());
			}

		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}

	public MODevice getCPUObj(MODevice mo, MODevice cpu, String snapTable) {
		String name;
		long value;
		name = cpu.getKpiname();
		value =  cpu.getPerfvalue();
		double valueD = Double.parseDouble(cpu.getPerValue());
		String valueStr = valueD == 0?"0":format.format(valueD);
		mo.setInstance(cpu.getInstance());
		if ("vhost".equals(snapTable)) {
			if (name.equals(KPINameDef.PERUSAGE)) {
				mo.setPerusage(valueStr + "%");
			} else if (name.equals(KPINameDef.CPUSED)) {
				mo.setCpused(HostComm.getMsToTime(value));
			} else if (name.equals(KPINameDef.CPUIDLE)) {
				mo.setCpuidle(HostComm.getMsToTime(value));
			}
		} else if ("vm".equals(snapTable)) {
			if (name.equals(KPINameDef.PERUTIL)) {
				mo.setPerUtil(HostComm.getBytesToHz(value));
			} else if (name.equals(KPINameDef.CPUSED)) {
				mo.setCpused(HostComm.getMsToTime(value));
			} else if (name.equals(KPINameDef.CPUREADY)) {
				mo.setCPUReady(HostComm.getMsToTime(value));
			} else if (name.equals(KPINameDef.CPUWAIT)) {
				mo.setCPUWait(HostComm.getMsToTime(value));
			}
		} else if ("host".equals(snapTable)) {
			if (name.equals(KPINameDef.PERUSAGE)) {
				mo.setPerusage(valueStr + "%");
			}
		}
		return mo;
	}

	/**
	 * 详情信息列表 for 内存公共
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findMemoryDetailInfo")
	@ResponseBody
	public Map<String, Object> findMemoryDetailInfo() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		logger.info("开始...加载公共详情信息列表");
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", MOID);
		params.put("tabName", "MOMemories");
		String moClass = request.getParameter("moClass");

		String snapTable = "";
		if ("vhost".equals(moClass)) {
			snapTable = "PerfSnapshotVHost";
		} else if ("vm".equals(moClass)) {
			snapTable = "PerfSnapshotVM";
		} else if ("host".equals(moClass)) {
			snapTable = "PerfSnapshotHost";
		}
		params.put("snapTable", snapTable);

		List<MODevice> memoryList = modMaper.getKPIMemoryValue(params);

		// return data
		Map<Integer, MODevice> memoryMap = new HashMap<Integer, MODevice>();
		MODevice memory = null;
		if (memoryList.size() > 0) {
			for (MODevice moMemory : memoryList) {
				memory = new MODevice();
				if (memoryMap.get(moMemory.getMoid()) != null) {
					getMemoryObj(memoryMap.get(moMemory.getMoid()), moMemory, moClass);
				} else {
					memoryMap.put(moMemory.getMoid(), getMemoryObj(memory, moMemory, moClass));
				}
			}
		}

		memoryList.clear();
		for (Map.Entry<Integer, MODevice> entry : memoryMap.entrySet()) {
			memoryList.add(entry.getValue());
		}

		if ("host".equals(moClass)) {
			memory = getMemValue(memoryList);
			memoryList.clear();
			memoryList.add(memory);
		}

		result.put("rows", memoryList);

		logger.info("结束...加载公共详情信息列表 " + memoryList.size());
		return result;
	}

	public MODevice getMemValue(List<MODevice> list) {
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() == 2 && list.get(0).getVirmemsize() == null) {
			MODevice mo = list.get(0);
			mo.setVirmemsize(list.get(1).getVirmemsize());
			mo.setVirmemfree(list.get(1).getVirmemfree());
			mo.setVirmemusage(list.get(1).getVirmemusage());
			return mo;
		} else if (list.size() == 2 && list.get(0).getVirmemsize() != null) {
			MODevice mo = list.get(0);
			mo.setPhymemsize(list.get(1).getPhymemsize());
			mo.setPhymemfree(list.get(1).getPhymemfree());
			mo.setPhymemusage(list.get(1).getPhymemusage());
			return mo;
		}
		return null;
	}

	/**
	 * 内存
	 * 
	 * @param list
	 * @return
	 */
	public MODevice getMemoryObj(MODevice mo, MODevice old_obj, String snapTable) {
		String name;
		long value;
		name = old_obj.getKpiname();
		value =  old_obj.getPerfvalue();
		double valueD = Double.parseDouble(old_obj.getPerValue());
		String valueStr = valueD == 0?"0":format.format(valueD);
		if ("vhost".equals(snapTable) || "vm".equals(snapTable)) {
			if (name.equals(KPINameDef.PHYMEMSIZE)) {
				mo.setPhymemsize(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.PHYMEMFREE)) {
				mo.setPhymemfree(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.PHYMEMUSAGE)) {
				mo.setPhymemusage(valueStr + "%");
			} else if (name.equals(KPINameDef.PHYMEMACTIVE)) {
				mo.setPhymemactive(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.PHYMEMOVERHEAD)) {
				mo.setPhymemoverhead(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.PHYMEMSHARED)) {
				mo.setPhymemshared(HostComm.getBytesToSize(value));
			}
			mo.setMemorysize(HostComm.getBytesToSize(Long.parseLong(old_obj.getMemorysize())));
		} else if ("host".equals(snapTable)) {
			if (name.equals(KPINameDef.PHYMEMSIZE)) {
				mo.setPhymemsize(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.PHYMEMFREE)) {
				mo.setPhymemfree(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.PHYMEMUSAGE)) {
				mo.setPhymemusage(valueStr + "%");
			} else if (name.equals(KPINameDef.VIRMEMFREE)) {
				mo.setVirmemfree(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.VIRMEMSIZE)) {
				mo.setVirmemsize(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.VIRMEMUSAGE)) {
				mo.setVirmemusage(valueStr + "%");
			}
		}
		return mo;
	}

	/**
	 * 详情信息列表 for 硬盘公共
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findDiskDetailInfo")
	@ResponseBody
	public Map<String, Object> findDiskDetailInfo() throws Exception {
		logger.info("开始...加载公共详情信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Integer, MODevice> diskMap = new HashMap<Integer, MODevice>();
		Map params = new HashMap();
		params.put("MOID", MOID);
		params.put("tabName", "MOVolumes");
		String moClass = request.getParameter("moClass");
		String snapTable = "";
		String volType = "";
		if ("vhost".equals(moClass)) {
			snapTable = "PerfSnapshotVHost";
			volType = "Volume";
		} else if ("vm".equals(moClass)) {
			snapTable = "PerfSnapshotVM";
			volType = "Volume";
		} else if ("host".equals(moClass)) {
			snapTable = "PerfSnapshotHost";
			volType = "Volume";
		}
		params.put("volType", volType);
		params.put("snapTable", snapTable);
		List<MODevice> moLsinfo = new ArrayList<MODevice>();
		List<MODevice> moLsinfo1 = modMaper.getKPIDiskValue(params);
		MODevice mo = null;
		if (moLsinfo1.size() > 0) {
			for (MODevice disk : moLsinfo1) {
				mo = new MODevice();
				if (diskMap.get(disk.getMoid()) != null) {
					getDiskObj(diskMap.get(disk.getMoid()), disk, moClass);
				} else {
					diskMap.put(disk.getMoid(), getDiskObj(mo, disk, moClass));
				}
			}
			for (Map.Entry<Integer, MODevice> entry : diskMap.entrySet()) {
				moLsinfo.add(entry.getValue());
			}
		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}

	/**
	 * 详情信息列表 for 硬盘公共
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findVolDetailInfo")
	@ResponseBody
	public Map<String, Object> findVolDetailInfo() throws Exception {
		logger.info("开始...加载公共详情信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Integer, MODevice> diskMap = new HashMap<Integer, MODevice>();
		Map params = new HashMap();
		String moClass = request.getParameter("moClass");
		params.put("MOID", MOID);
		params.put("tabName", "MOVolumes");
		params.put("volType", "VmDisk");
		params.put("snapTable", "PerfSnapshotVM");
		List<MODevice> moLsinfo = new ArrayList<MODevice>();
		List<MODevice> moLsinfo1 = modMaper.getKPIDiskValue(params);
		MODevice mo = null;
		if (moLsinfo1.size() > 0) {
			for (MODevice disk : moLsinfo1) {
				mo = new MODevice();
				if (diskMap.get(disk.getMoid()) != null) {
					getDiskObj(diskMap.get(disk.getMoid()), disk, moClass);
				} else {
					diskMap.put(disk.getMoid(), getDiskObj(mo, disk, moClass));
				}
			}
			for (Map.Entry<Integer, MODevice> entry : diskMap.entrySet()) {
				moLsinfo.add(entry.getValue());
			}
		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}

	public MODevice getDiskObj(MODevice mo, MODevice disk, String snapTable) {
		String name;
		long value;
		name = disk.getKpiname();
		value = disk.getPerfvalue();
		mo.setRawdescr(disk.getRawdescr());
		double valueD = Double.parseDouble(disk.getPerValue());
		String valueStr = valueD == 0?"0":format.format(valueD);
		if ("vhost".equals(snapTable)) {
			if (name.equals(KPINameDef.DISKRATE)) {
				mo.setDiskrate(HostComm.getBytesToSpeed(value));
			} else if (name.equals(KPINameDef.DISKRSPEED)) {
				mo.setDiskrspeed(HostComm.getBytesToSpeed(value));
			} else if (name.equals(KPINameDef.DISKWSPEED)) {
				mo.setDiskwspeed(HostComm.getBytesToSpeed(value));
			} else if (name.equals(KPINameDef.DISKRREQUEST)) {
				mo.setDiskrrequest(value);
			} else if (name.equals(KPINameDef.DISKWREQUEST)) {
				mo.setDiskwrequest(value);
			}
		} else if ("vm".equals(snapTable)) {
			if (name.equals(KPINameDef.DISKRSPEED)) {
				mo.setDiskrspeed(HostComm.getBytesToSpeed(value));
			} else if (name.equals(KPINameDef.DISKWSPEED)) {
				mo.setDiskwspeed(HostComm.getBytesToSpeed(value));
			} else if (name.equals(KPINameDef.DISKRREQUEST)) {
				mo.setDiskrrequest(value);
			} else if (name.equals(KPINameDef.DISKWREQUEST)) {
				mo.setDiskwrequest(value);
			} else if (name.equals(KPINameDef.DISKSIZE)) {
				mo.setDisksize(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.DISKUSAGE)) {
				mo.setDiskusage(valueStr + "%");
			} else if (name.equals(KPINameDef.DISKFREE)) {
				mo.setDiskfree(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.DISKUSED)) {
				mo.setDiskused(HostComm.getBytesToSize(value));
			}
		} else if ("host".equals(snapTable)) {

			if (name.equals(KPINameDef.DISKSIZE)) {
				mo.setDisksize(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.DISKFREE)) {
				mo.setDiskfree(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.DISKUSAGE)) {
				mo.setDiskusage(valueStr + "%");
			}
		}
		return mo;
	}

	/**
	 * 详情信息列表 for 数据存储公共
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findDatastoreDetailInfo")
	@ResponseBody
	public Map<String, Object> findDatastoreDetailInfo() throws Exception {
		logger.info("开始...加载公共详情信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Integer, MODevice> storeMap = new HashMap<Integer, MODevice>();
		Map params = new HashMap();
		params.put("MOID", MOID);
		params.put("tabName", "MOStorage");
		String moClass = request.getParameter("moClass");
		String snapTable = "";
		if ("vhost".equals(moClass)) {
			snapTable = "PerfSnapshotVHost";
		} else if ("vm".equals(moClass)) {
			snapTable = "PerfSnapshotVM";
		} else if ("host".equals(moClass)) {
			snapTable = "PerfSnapshotHost";
		}
		params.put("snapTable", snapTable);
		List<MODevice> moLsinfo = new ArrayList<MODevice>();
		List<MODevice> moLsinfo1 = modMaper.getKPIDatastoreValue(params);
		MODevice mo = null;
		if (moLsinfo1.size() > 0) {
			for (MODevice store : moLsinfo1) {
				mo = new MODevice();
				if (storeMap.get(store.getMoid()) != null) {
					getDatastoreObj(storeMap.get(store.getMoid()), store, moClass);
				} else {
					storeMap.put(store.getMoid(), getDatastoreObj(mo, store, moClass));
				}
			}
			for (Map.Entry<Integer, MODevice> entry : storeMap.entrySet()) {
				moLsinfo.add(entry.getValue());
			}
		}
		result.put("rows", moLsinfo);

		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}

	public MODevice getDatastoreObj(MODevice mo, MODevice store, String snapTable) {
		String name;
		long value;
		name = store.getKpiname();
		value = store.getPerfvalue();
		mo.setMoname(store.getMoname());
		double valueD = Double.parseDouble(store.getPerValue());
		String valueStr = valueD == 0?"0":format.format(valueD);
		if ("vhost".equals(snapTable) || "vm".equals(snapTable)) {
			if (name.equals(KPINameDef.DATASTOREUSAGE)) {
				mo.setDatastoreusage(valueStr + "%");
			} else if (name.equals(KPINameDef.DATASTOREFREE)) {
				mo.setDatastorefree(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.DATASTORERSPEED)) {
				mo.setDatastorerspeed(HostComm.getBytesToSpeed(value));
			} else if (name.equals(KPINameDef.DATASTOREWSPEED)) {
				mo.setDatastorewspeed(HostComm.getBytesToSpeed(value));
			} else if (name.equals(KPINameDef.DATASTORERREQUEST)) {
				mo.setDatastorerrequest(value);
			} else if (name.equals(KPINameDef.DATASTOREWREQUEST)) {
				mo.setDatastorewrequest(value);
			}
		}

		return mo;
	}
	/**
	 * 详情信息列表 for 接口
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findFlowsDetailInfo")
	@ResponseBody
	public Map<String, Object> findFlowsDetailInfo() throws Exception {
		logger.info("开始...加载公共详情信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", MOID);//10816
		getmoClassID(moClass);
		params.put("moClassID", moClassID);//7
		params.put("tabName", "MONetworkIf");
		String moClass = request.getParameter("moClass");//HOST
		getIfTableName(moClass);
		params.put("snapTable", ifTableName);//PerfSnapshotNetDevice

		List<MODevice> moLsCount = modMaper.getKPIFlowsValue2(params);
		DecimalFormat df = new DecimalFormat("#.##");
		Map<Integer, MODevice> resultMap = new HashMap<Integer, MODevice>();
		for (MODevice moDevice : moLsCount) {
			int ifMOID = moDevice.getMoid();
			if (resultMap.get(ifMOID) != null) {
				MODevice keyMoDevice = resultMap.get(ifMOID);
				if ("vhost".equals(moClass)) {
					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
						String inflow = HostComm.getBytesToFlows(moDevice.getViewValue());
						keyMoDevice.setInflows(inflow);
					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
						keyMoDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					} else if (KPINameDef.IFUSAGE.equals(moDevice.getKpiname())) {
						keyMoDevice.setIfUsage(df.format(moDevice.getViewValue()) + "%");
					} else if (KPINameDef.INUSAGE.equals(moDevice.getKpiname())) {
						keyMoDevice.setInusage(df.format(moDevice.getViewValue()) + "%");
					} else if (KPINameDef.OUTUSAGE.equals(moDevice.getKpiname())) {
						keyMoDevice.setOutusage(df.format(moDevice.getViewValue()) + "%");
					} else if (KPINameDef.IFSPEED.equals(moDevice.getKpiname())) {
						keyMoDevice.setIfspeed(HostComm.getBytesToFlows(moDevice.getViewValue()));
					}
				} else if ("vm".equals(moClass)) {
					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
						String inflow = HostComm.getBytesToFlows(moDevice.getViewValue());
						keyMoDevice.setInflows(inflow);
					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
						keyMoDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					}
				} else if ("host".equals(moClass) || "router".equals(moClass) || "switcher".equals(moClass)) {

					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
						keyMoDevice.setInflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
						keyMoDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					} else if (KPINameDef.INDISCARDS.equals(moDevice.getKpiname())) {
						keyMoDevice.setIndiscards(moDevice.getViewValue());
					} else if (KPINameDef.OUTDISCARDS.equals(moDevice.getKpiname())) {
						keyMoDevice.setOutdiscards(moDevice.getViewValue());
					} else if (KPINameDef.INERRORS.equals(moDevice.getKpiname())) {
						keyMoDevice.setInerrors(moDevice.getViewValue());
					} else if (KPINameDef.OUTERRORS.equals(moDevice.getKpiname())) {
						keyMoDevice.setOuterrors(moDevice.getViewValue());
					} else if (KPINameDef.IFOPERSTATUS.equals(moDevice.getKpiname())) {
						if (KPINameDef.upd == moDevice.getViewValue()) {
							keyMoDevice.setOperstatus("UP");
						} else {
							keyMoDevice.setOperstatus("DOWN");
						}
					}

				}
			} else {
				if ("vhost".equals(moClass)) {
					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
						String inflow = HostComm.getBytesToFlows(moDevice.getViewValue());
						moDevice.setInflows(inflow);
					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
						moDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					} else if (KPINameDef.IFUSAGE.equals(moDevice.getKpiname())) {
						moDevice.setIfUsage(df.format(moDevice.getViewValue()) + "%");
					} else if (KPINameDef.INUSAGE.equals(moDevice.getKpiname())) {
						moDevice.setInusage(df.format(moDevice.getViewValue()) + "%");
					} else if (KPINameDef.OUTUSAGE.equals(moDevice.getKpiname())) {
						moDevice.setOutusage(df.format(moDevice.getViewValue()) + "%");
					} else if (KPINameDef.IFSPEED.equals(moDevice.getKpiname())) {
						moDevice.setIfspeed(HostComm.getBytesToFlows(moDevice.getViewValue()));
					}
				} else if ("vm".equals(moClass)) {
					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
						String inflow = HostComm.getBytesToFlows(moDevice.getViewValue());
						moDevice.setInflows(inflow);
					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
						moDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					}
				} else if ("host".equals(moClass) || "router".equals(moClass) || "switcher".equals(moClass)) {

					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
						moDevice.setInflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
						moDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					} else if (KPINameDef.INDISCARDS.equals(moDevice.getKpiname())) {
						moDevice.setIndiscards(moDevice.getViewValue());
					} else if (KPINameDef.OUTDISCARDS.equals(moDevice.getKpiname())) {
						moDevice.setOutdiscards(moDevice.getViewValue());
					} else if (KPINameDef.INERRORS.equals(moDevice.getKpiname())) {
						moDevice.setInerrors(moDevice.getViewValue());
					} else if (KPINameDef.OUTERRORS.equals(moDevice.getKpiname())) {
						moDevice.setOuterrors(moDevice.getViewValue());
					} else if (KPINameDef.IFOPERSTATUS.equals(moDevice.getKpiname())) {
						if (KPINameDef.upd == moDevice.getViewValue()) {
							moDevice.setOperstatus("UP");
						} else {
							moDevice.setOperstatus("DOWN");
						}
					}

				}
				resultMap.put(ifMOID, moDevice);
			}
		}

		// 遍历map，将接口列表数据放入数组中返回
		List<MODevice> resultList = new ArrayList<MODevice>();
		for (Iterator it = resultMap.keySet().iterator(); it.hasNext();) {
			resultList.add(resultMap.get(it.next()));
		}
		result.put("rows", resultList);
		logger.info("结束...加载公共详情信息列表 " + resultList);
		return result;
	}
/*    public static void main(String[] args){
    	System.out.println("aaa".indexOf("a"));
    }*/
}
