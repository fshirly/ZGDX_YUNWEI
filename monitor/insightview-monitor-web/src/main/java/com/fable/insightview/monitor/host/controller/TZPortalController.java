package com.fable.insightview.monitor.host.controller;

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

import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.host.entity.PerfSnapsBean;
import com.fable.insightview.monitor.host.mapper.HostMapper;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;


@Controller
@RequestMapping("/monitor/tzManage")
public class TZPortalController {
	@Autowired
	HostMapper modMaper;
	@Autowired
	private AlarmActiveMapper alarmActiveMapper;
	private final Logger logger = LoggerFactory.getLogger(TZPortalController.class);
	String moClass;// 设备类型ID
	Integer MOID;
	Integer moClassID;
	/**
	 * 根据类型获取moClassID
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
		}else if("switcherl2".equals(moClass)){
			moClassID = 59;
		}else if("switcherl3".equals(moClass)){
			moClassID = 60;
		}
		return moClassID;
	}
	/**
	 * 获取chart设备可用性使用率
	 * 
	 * @param deviceIP
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findDeviceInfo")
	@ResponseBody
	public ModelAndView findDeviceInfo(HttpServletRequest request,
			ModelMap map) {
		logger.info("根据设备IP获取图表可用性使用率");
		String moClass = request.getParameter("moClass");
		String moID=request.getParameter("moID");
		Integer MOID=0;
		if(moID!=null || !"".equals(moID)){
			MOID=Integer.parseInt(moID);
		}
		MODevice moInfo=modMaper.getMoDeviceInfo(MOID);
		String seltDate = request.getParameter("time");
		String seltDateValue = "";
		if(seltDate==null){
			seltDateValue = "24小时";
		}
		if ("24H".equals(seltDate)) {// 最近一天
			seltDateValue = "24小时";
		} else if ("7D".equals(seltDate)) {// 最近一周
			seltDateValue = "最近一周";
		} else if ("Today".equals(seltDate)) {// 今天
			seltDateValue = "今天";
		} else if ("Yes".equals(seltDate)) {// 昨天
			seltDateValue = "昨天";
		}else if ("Week".equals(seltDate)) {// 本周
			seltDateValue = "本周";
		}else if ("Month".equals(seltDate)) {// 本月
			seltDateValue = "本月";
		}else if ("LastMonth".equals(seltDate)) {// 上月
			seltDateValue = "上月";
		}
		String nameValue = seltDateValue + "可用性";
		
		Map<String ,Object> AlarmParams = new HashMap<String ,Object>();
		AlarmParams = queryBetweenTime(request, AlarmParams);
		AlarmParams.put("MOID", moID);
		int alarmCount=modMaper.getMoAlarmCount(AlarmParams);//获取某设备的告警数量
		
		Map<String ,Object> avaiParams = new HashMap<String ,Object>();
		getmoClassID(moClass);
		avaiParams = queryBetweenTime(request, avaiParams);
		avaiParams.put("moClassID", moClassID);
		avaiParams.put("MOID", MOID);
		MODevice mo = modMaper.getChartAvailability(avaiParams);//可用性
		
		String cpuTableName="";
		String memTableName="";
		String colName="";
		if("host".equals(moClass) || "vm".equals(moClass) || "vhost".equals(moClass) || moClass == null){
			cpuTableName="PerfServCPU";
			memTableName="PerfServMemory";
			colName="memoryUsage";
		}else if ("switcher".equals(moClass) || "router".equals(moClass) ||"switcherl2".equals(moClass)||"switcherl3".equals(moClass)) {
			cpuTableName="PerfNetworkCPU";
			memTableName="PerfNetworkMemory";
			colName="perMemoryUsage";
		}
		
		Map<String ,Object> avgParams = new HashMap<String ,Object>();
		avgParams = queryBetweenTime(request, avgParams);
		avgParams.put("MOID", MOID);
		avgParams.put("cpuTableName", cpuTableName);
		avgParams.put("memTableName", memTableName);
		avgParams.put("colName", colName);
		MODevice moCPU = modMaper.getAvgCpu(avgParams);//cpu
		MODevice moMemory = modMaper.getAvgMemory(avgParams);
	
		map.put("moID", MOID);
		map.put("moClass", moClass);
		map.put("deviceIP", moInfo.getDeviceip());
		map.put("alarmCount", alarmCount);
		map.put("nameValue", nameValue);//标题名称
		map.put("timeBegin", avgParams.get("timeBegin"));
		map.put("timeEnd", avgParams.get("timeEnd"));
		if (mo != null) {
			map.put("avaiUsage", mo.getDeviceavailability());//可用性
			System.out.println("avaiUsage: "+mo.getDeviceavailability());
		}else{
			map.put("avaiUsage", "''");//可用性
		}
		if (moCPU != null) {
			map.put("CPUsage", moCPU.getCpuvalue()+"%");//cpu使用率
			System.out.println(" CPUsage: "+moCPU.getCpuvalue()+"% ");
		}else{
			map.put("CPUsage", "");//cpu使用率
		}
		if (moMemory != null) {
			map.put("MemUsage", moMemory.getMemoryvalue()+"%");//内存使用率
			System.out.println(" MemUsage: "+ moMemory.getMemoryvalue()+"%");
		}else{
			map.put("MemUsage", "");//内存使用率
		}

		return new ModelAndView("monitor/host/tzDeviceChart");
		
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
			d.add(Calendar.DATE, -1);
			timeBegin = f.format(d.getTime());
		}
		
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		return params;
	}
	/**
	 * 跳转至所有设备快照列表页
	 */
	@RequestMapping("/toTZSnapshotInfo")
	public ModelAndView toTZSnapshotInfo(HttpServletRequest request) {
		String moClass=request.getParameter("moClass");
		if("".equals(moClass)|| moClass==null){
			moClass="mo";
		}
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moClass", moClass);
		return new ModelAndView("monitor/host/tzSnapshotInfoList");
	}
	/**
	 * 设备快照入口，查询设备快照（新修改）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querySnapshotInfo")
	@ResponseBody
	public Map<String, Object> querySnapshotInfo() {
		logger.info("开始...加载设备快照页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String moClass=request.getParameter("moClass");
		List<MODevice> snapsHostCount = new ArrayList<MODevice>();
		List<MODevice> MObjectDef = new ArrayList<MODevice>();
		List<PerfSnapsBean>  moLsinfo = new ArrayList<PerfSnapsBean>();
		List<MODevice> moLsCount = modMaper.getTZMoClassIDCount();//泰州 定制  查询快照类型
		 List<Integer> moClassIDlist = new ArrayList<Integer>();
		 PerfSnapsBean perfSnapObj = null;
		 Map<String,PerfSnapsBean>  MObjectDefMap = new HashMap<String, PerfSnapsBean>(); 
		 for(int i=0;i<moLsCount.size();i++)
		 {
			 moClassIDlist.add(moLsCount.get(i).getMoclassid());
		 }
		 // 查询设备总数
		 params.put("classID", moClassIDlist.toString().replace("[", "").replace("]",""));
		 MObjectDef = modMaper.queryTZAllDeviceInfo(params);
		 int switherCount = 0;
		 for (MODevice SeriousDevice : MObjectDef) {
			 if(SeriousDevice.getSourceMOClassID()==59)
			 {
				 switherCount = SeriousDevice.getDevicealarmcount();
			 }
			 if(SeriousDevice.getSourceMOClassID()==60)
			 {
				 switherCount+= SeriousDevice.getDevicealarmcount();
			 }
			 if(SeriousDevice.getSourceMOClassID()==6)
			 {
				 switherCount+= SeriousDevice.getDevicealarmcount();
			 }
		}
		 // 查询总的设备数
		 for (MODevice modevice : MObjectDef) {
			 String flag=null;
			 perfSnapObj = new PerfSnapsBean();
			  if(modevice.getSourceMOClassID()==59 || modevice.getSourceMOClassID()==60 || modevice.getSourceMOClassID()==6)
			 {
				 flag = "交换机";
			 }else{
				 flag=modevice.getClasslable()+"_" +modevice.getSourceMOClassID();
			 }
			 if(MObjectDefMap.containsKey(flag))
			 {
				 perfSnapObj = MObjectDefMap.get(flag);
				 perfSnapObj.setCountDevice(modevice.getCountdevice()+perfSnapObj.getCountDevice());
				  if(("交换机").equals(flag))
				 {
					 perfSnapObj.setClasslable(flag);
					 perfSnapObj.setAlarmDeviceCount(switherCount);
					 perfSnapObj.setMoClassID("6");
				 }
				 else{
					 perfSnapObj.setClasslable(modevice.getClasslable());
					 perfSnapObj.setAlarmDeviceCount(modevice.getDevicealarmcount());
					 perfSnapObj.setMoClassID(String.valueOf(modevice.getSourceMOClassID()));
				 }
			 }else
			 {
				   if(("交换机").equals(flag))
				 {
					 perfSnapObj.setClasslable(flag);
					 perfSnapObj.setAlarmDeviceCount(switherCount);
					 perfSnapObj.setMoClassID("6");
				 }
				 else{
					 perfSnapObj.setClasslable(modevice.getClasslable());
					 perfSnapObj.setAlarmDeviceCount(modevice.getDevicealarmcount());
					 perfSnapObj.setMoClassID(String.valueOf(modevice.getSourceMOClassID()));
				 }
				 
				 perfSnapObj.setCountDevice(modevice.getCountdevice());
				 MObjectDefMap.put(flag, perfSnapObj);
			 }
			}
		 
		 // 查询总的告警数量
		 snapsHostCount  =modMaper.queryTZAlarmInfoCounts(params);
		 for (MODevice moDevice : snapsHostCount) {
			 perfSnapObj = new PerfSnapsBean();
			 String flag=null; 
			 if(moDevice.getSourceMOClassID()==59 || moDevice.getSourceMOClassID()==60 || moDevice.getSourceMOClassID()==6)
			 {
				 flag = "交换机";
			 }else{
				 flag=moDevice.getClasslable()+"_" +moDevice.getSourceMOClassID();
			 }
			 perfSnapObj = querySnapsOrVmhostInfo(MObjectDefMap,perfSnapObj, moDevice, flag);
			 MObjectDefMap.put(flag,perfSnapObj);
		 }
		
		 for (String mobjectDefKey : MObjectDefMap.keySet()) {
			 moLsinfo.add(MObjectDefMap.get(mobjectDefKey));
		 }
		result.put("rows", moLsinfo);
		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}
	private PerfSnapsBean querySnapsOrVmhostInfo(
			Map<String, PerfSnapsBean> MObjectDefMap,
			PerfSnapsBean perfSnapObj, MODevice moDevice, String flag) {
		if(MObjectDefMap.containsKey(flag)){
			 perfSnapObj = MObjectDefMap.get(flag);
			 perfSnapObj.setAlarmCount(perfSnapObj.getAlarmCount()+moDevice.getAlarmcount());
			 if(("交换机").equals(flag))
			 {
				 perfSnapObj.setClasslable(flag);
				 perfSnapObj.setMoClassID("6");
			 }
			 else if(("虚拟机"+"_"+9).equals(flag))
			 {
				 perfSnapObj.setClasslable("虚拟机");
				 perfSnapObj.setMoClassID("9");
			 }
			 else{
				 perfSnapObj.setClasslable(moDevice.getClasslable());
				 perfSnapObj.setMoClassID(String.valueOf(moDevice.getSourceMOClassID()));
			 }
			 
		 }else
		 {
			 if(("交换机").equals(flag))
			 {
				 perfSnapObj.setClasslable(flag);
				 perfSnapObj.setMoClassID("6");
			 }
			 else if(("虚拟机"+"_"+9).equals(flag))
			 {
				 perfSnapObj.setClasslable("虚拟机");
				 perfSnapObj.setMoClassID("9");
			 }
			 else{
				 perfSnapObj.setClasslable(moDevice.getClasslable());
				 perfSnapObj.setMoClassID(String.valueOf(moDevice.getSourceMOClassID()));
			 }
			 
			 perfSnapObj.setAlarmCount(moDevice.getAlarmcount()); 
			 perfSnapObj.setCountDevice(moDevice.getCountdevice());
		 }
		return perfSnapObj;
	}
	
	/**
	 * 从设备快照中入口，跳转到告警列表
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryTZAlarmActivelist")
	@ResponseBody
	public Map<String, Object> queryTZAlarmActivelist(AlarmNode vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<AlarmNode> page = new Page<AlarmNode>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("timeBegin", vo.getTimeBegin());
		paramMap.put("timeEnd", vo.getTimeEnd());
		paramMap.put("moName", vo.getMoName());
		paramMap.put("alarmLevel", vo.getAlarmLevel());
		paramMap.put("alarmTitle", vo.getAlarmTitle());
		paramMap.put("alarmStatus", vo.getAlarmStatus());
		// TODO 操作
		
		paramMap.put("alarmOperateStatus", vo.getAlarmOperateStatus());
		paramMap.put("alarmType", vo.getAlarmType());
		paramMap.put("dispatchUser", vo.getDispatchUser());
		paramMap.put("dispatchTime", vo.getDispatchTime());
		
		if(!"-1".equals(request.getParameter("moid"))){
			paramMap.put("moId", request.getParameter("moid"));
		}
		if(vo.getMoclassID() != 0){ 
			paramMap.put("moclassID",vo.getMoclassID());
			String alarmSourceSubSql = "";
			if(vo.getMoclassID()==6)//交换机
			{
				alarmSourceSubSql = "MOClassID in(59,60,6)";
			}else if(vo.getMoclassID()==17)//中间件
			{
				alarmSourceSubSql = "MOClassID in(19,20,53)";
			}else if(vo.getMoclassID()==14)//数据库
			{
				alarmSourceSubSql = "(15,16,54,81,86)";
			}else if(vo.getMoclassID()==90)//站点
			{
				alarmSourceSubSql = "(91,92,93,94)";
			}
			else
			{
				alarmSourceSubSql = "MOClassID = "+vo.getMoclassID()+""; 
			}
			paramMap.put("alarmSourceSubSql",alarmSourceSubSql);
		}else {
			paramMap.put("moclassID","");
		}

		page.setParams(paramMap);
		
		// 设置查询参数
		
		// 查询分页数据
		List<AlarmNode> list = alarmActiveMapper.queryTZAlarmList(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
}
