package com.fable.insightview.monitor.blackwhitelistfordevice.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmmgr.alarmtype.entity.AlarmTypeBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackPortListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.InterfaceOrPort;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListMonitorsBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListOpStatus;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListTaskBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.service.IDeviceWBSettingService;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.perfTask.PerfTaskNotify;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * 设备黑白名单配置controller
 * @author zhouwf
 *
 */
@Controller
@RequestMapping("/monitor/deviceWBListSetting")
public class DeviceWBListSettingController {

	@Autowired
	private IDeviceWBSettingService deviceWBSettingService;
	
	PerfTaskNotify perfTaskNotify = new PerfTaskNotify();
	
	/**
	 * 跳转到黑白名单主页
	 * @param request
	 * @param response
	 * @param navigationBar
	 * @return
	 */
	@RequestMapping("/toWBListMainView")
	public  ModelAndView toWBListMainView(HttpServletRequest request,HttpServletResponse response,String navigationBar){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/deviceWBListSetting/WBListMainView");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	@RequestMapping("/listDeviceWBSettings")
	@ResponseBody
	public Map<String,Object> listDeviceWBSettings(AlarmBlackListBean vo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<AlarmTypeBean> page = new Page<AlarmTypeBean>(); 
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("deviceIP", vo.getDeviceIP());
		paramMap.put("portTypes", InterfaceOrPort.INTERFACE.getValue()+","+InterfaceOrPort.PORT.getValue());
		page.setParams(paramMap);
		List<AlarmBlackListBean> alarmBlackList =  deviceWBSettingService.getAlarmBlackList(page);
		int total = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", total);
		result.put("rows", alarmBlackList);
		return result;
	}
	
	/**
	 * 跳转到增加黑白名单页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toAddWBListView")
	public  ModelAndView toWBListMainView(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("monitor/deviceWBListSetting/WBListView_add");
	}
	
	
	@RequestMapping("/listInterfaceByDeviceIP")
	@ResponseBody
	public Map<String,Object> listInterfaceByDeviceIP(MODevice vo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MODevice> page = new Page<MODevice>(); 
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("deviceIP", vo.getDeviceip());
		paramMap.put("type",vo.getType());
		paramMap.put("sort", request.getParameter("sort"));
		paramMap.put("order", request.getParameter("order"));
		paramMap.put("opt", request.getParameter("opt"));
		page.setParams(paramMap);
		List<MODevice> alarmBlackList =  deviceWBSettingService.getInterfaceListByDeviceIP(page);
		int total = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", total);
		result.put("rows", alarmBlackList);
		return result;
	}
	
	@RequestMapping("/listPortByDeviceIP")
	@ResponseBody
	public Map<String,Object> listPortByDeviceIP(MODevice vo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MODevice> page = new Page<MODevice>(); 
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("deviceIP", vo.getDeviceip());//设备IP
		paramMap.put("type",vo.getType());//黑白名单类型
		paramMap.put("opt", request.getParameter("opt"));//页面操作类型
		paramMap.put("portType", InterfaceOrPort.PORT.getValue());//服务端口
		paramMap.put("type", request.getParameter("type"));//黑白名单类型
		page.setParams(paramMap);
		List<MODevice> alarmBlackList =  deviceWBSettingService.getPortListByDeviceIP(page);
		int total = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", total);
		result.put("rows", alarmBlackList);
		return result;
	}

	@RequestMapping("/addWBSettings")
	@ResponseBody
	public Map<String,Object> addWBSettings(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("success", "true");
		String selectedRowsString = request.getParameter("selectedRows");
		String portType = request.getParameter("portType");//接口/端口类型
		String type = request.getParameter("type");//黑白名单类型
		String operateStatusForIf = request.getParameter("operateStatusForIf");//接口/端口启用状态
		String deviceip = request.getParameter("deviceip");
		List<Map<String,Object>> MODeviceInfoMapList = JsonUtil.toList(selectedRowsString);
		//向alarmBlackList插入数据
		int blackID = -1;
		if (InterfaceOrPort.INTERFACE.getValue().equals(portType)) {// 接口
			// 准备插入设备黑白名单表的数据【AlarmBlackList】
			AlarmBlackListBean alarmBlackListBean = new AlarmBlackListBean();
			alarmBlackListBean.setMOID(deviceWBSettingService.getMOIDByDeviceIp(deviceip).intValue());
			alarmBlackListBean.setDeviceIP(deviceip);
			alarmBlackListBean.setPortType(portType);
			alarmBlackListBean.setOprateStatus("1");// 表示新增即启用
			alarmBlackListBean.setCreateTime(new Date());
			if ((deviceWBSettingService.getBlackIDByDeviceIPAndPortType(deviceip, portType)) != null) {
				blackID = deviceWBSettingService.getBlackIDByDeviceIPAndPortType(deviceip, portType);
			} else {
				blackID = deviceWBSettingService.insertIntoAlarmBlackList(alarmBlackListBean);
			}
		} else if (InterfaceOrPort.PORT.getValue().equals(portType)) {// 服务端口
			// 准备插入设备黑白名单表的数据【AlarmBlackList】
			AlarmBlackListBean alarmBlackListBean = new AlarmBlackListBean();
			alarmBlackListBean.setMOID(deviceWBSettingService.getMOIDByDeviceIp(deviceip).intValue());
			alarmBlackListBean.setDeviceIP(deviceip);
			alarmBlackListBean.setPortType(portType);
			alarmBlackListBean.setOprateStatus(SysBlackListOpStatus.INSERT.getValue());// 表示新增
			alarmBlackListBean.setCreateTime(new Date());
			if ((deviceWBSettingService.getBlackIDByDeviceIPAndPortType(deviceip, portType)) != null) {
				blackID = deviceWBSettingService.getBlackIDByDeviceIPAndPortType(deviceip, portType);
			} else {
				blackID = deviceWBSettingService.insertIntoAlarmBlackList(alarmBlackListBean);
			}
		}
		
		for (Map<String, Object> map : MODeviceInfoMapList) {
			if (InterfaceOrPort.INTERFACE.getValue().equals(portType)) {//接口
				//向设备黑白名单接口/端口列表【AlarmBlackPortList】插入数据
				AlarmBlackPortListBean alarmBlackPortListBean = new AlarmBlackPortListBean();
				alarmBlackPortListBean.setBlackID(blackID);
				alarmBlackPortListBean.setMOID(Integer.valueOf(map.get("ifMOID").toString()));//接口MOID
				alarmBlackPortListBean.setMOName(map.get("ifname").toString());//接口名
				alarmBlackPortListBean.setType(type);//黑白名单类型
				alarmBlackPortListBean.setOprateStatus(operateStatusForIf);//接口启用状态
				alarmBlackPortListBean.setCreateTime(new Date());
				deviceWBSettingService.insertIntoAlarmBlackPortList(alarmBlackPortListBean);
			} else if (InterfaceOrPort.PORT.getValue().equals(portType)) {//服务端口
				//向设备黑白名单接口/端口列表【AlarmBlackPortList】插入数据
				AlarmBlackPortListBean alarmBlackPortListBean = new AlarmBlackPortListBean();
				alarmBlackPortListBean.setBlackID(blackID);
				alarmBlackPortListBean.setPort(Integer.valueOf(map.get("port").toString()));//服务端口号
				alarmBlackPortListBean.setPortType(map.get("portType").toString());//服务端口类型 tcp/udp
				alarmBlackPortListBean.setType(type);//黑白名单类型
				alarmBlackPortListBean.setOprateStatus(map.get("blackOprateStatus").toString());//服务端口启用状态
				alarmBlackPortListBean.setPortService(map.get("portService")==null?null:map.get("portService").toString());//白名单需要的端口对应服务
				alarmBlackPortListBean.setCreateTime(new Date());
				if(deviceWBSettingService.checkBlackIDAndPort(alarmBlackPortListBean)){
					resultMap.put("portExisted","true");
					resultMap.put("port",alarmBlackPortListBean.getPort());
					return resultMap;
				}else{
					deviceWBSettingService.insertIntoAlarmBlackPortList(alarmBlackPortListBean);
				}
				int taskID = -1;//任务ID
				if(taskID<0){
					//向黑白名单任务列表【SysBlackListTask】插入数据
					SysBlackListTaskBean sysBlackListTaskBean = new SysBlackListTaskBean();
					sysBlackListTaskBean.setOperateStatus(1);
					sysBlackListTaskBean.setProgressStatus(1);
					sysBlackListTaskBean.setCollectorID(Integer.valueOf(map.get("collectorID")==null?"0":map.get("collectorID").toString()));
					sysBlackListTaskBean.setBlackID(blackID);
					sysBlackListTaskBean.setCreateTime(new Date());
					sysBlackListTaskBean.setCreator(((SecurityUserInfoBean)request.getSession().getAttribute("sysUserInfoBeanOfSession")).getId().intValue());
					//sysBlackListTaskBean.setOldCollectorID(oldCollectorID);
					//SysBlackListTask表中BlackID唯一，如果BlackID已经存在就不用新增一条记录
					if(!deviceWBSettingService.checkBlackIDInSysBlackListTask(sysBlackListTaskBean.getBlackID())){
						taskID = deviceWBSettingService.insertIntoSysBlackListTask(sysBlackListTaskBean);
						//向黑白名单监测器列表【SysBlackListMonitors】插入数据
						SysBlackListMonitorsBean sysBlackListMonitorsBean = new SysBlackListMonitorsBean();
						//先检索监测器信息
						Map<String, Object> moInfoMap = deviceWBSettingService.getMonitorInfo("JobNmapScan");
						sysBlackListMonitorsBean.setDoIntervals(Integer.valueOf(moInfoMap.get("DoIntervalsBySec")+""));//nmap监测器采集周期
						sysBlackListMonitorsBean.setMID(Integer.valueOf(moInfoMap.get("MID")+""));//nmap监测器MID
						sysBlackListMonitorsBean.setTaskID(taskID);
						deviceWBSettingService.insertIntoSysBlackListMonitors(sysBlackListMonitorsBean);
					}
					//
					perfTaskNotify.portAlarmBlackListDisPatch();
				}
			}
			
		}
		return resultMap;
	}
	
	@RequestMapping("/deleteWBListSetting")
	@ResponseBody
	public Map<String,String> deleteWBListSetting(HttpServletRequest request) {
		String rowsToBeDeleted = request.getParameter("rowsToBeDeleted");
		List<AlarmBlackListBean> AlarmBlackListToDel = JsonUtil.toList(rowsToBeDeleted,AlarmBlackListBean.class);
		AlarmBlackPortListBean alarmBlackPortListBean = new AlarmBlackPortListBean();
		Map<String, String> resultMap = new HashMap<String,String>();
		resultMap.put("success", "false");
		for (AlarmBlackListBean alarmBlackListBean : AlarmBlackListToDel) {
			String portType = alarmBlackListBean.getPortType();//接口/端口类型  '1=接口 2=服务端口 3=互联网',
			if (InterfaceOrPort.INTERFACE.getValue().equals(portType)) {//接口
				boolean r1 = deleteAlarmBlackListRows(alarmBlackListBean);//删除设备黑白名单
				alarmBlackPortListBean.setBlackID(alarmBlackListBean.getBlackID());//设备黑白名单接口/端口列表
				boolean r2 = deleteAlarmBlackPortListRows(alarmBlackPortListBean);//删除设备黑白名单接口/端口列表数据
				if(r1){
					resultMap.put("success", "true");
				}
			}else if(InterfaceOrPort.PORT.getValue().equals(portType)||InterfaceOrPort.INTERNET_ACCESS.getValue().equals(portType)){//服务端口
				boolean r1 = deleteAlarmBlackListRows(alarmBlackListBean);//删除设备黑白名单
				alarmBlackPortListBean.setBlackID(alarmBlackListBean.getBlackID());//设备黑白名单接口/端口列表
				boolean r2 = deleteAlarmBlackPortListRows(alarmBlackPortListBean);//删除设备黑白名单接口/端口列表数据
				//黑白名单任务【SysBlackListTask】状态修改
				updateSysBlackListTask(alarmBlackListBean.getBlackID(),SysBlackListOpStatus.DELETE);
				if(r1&&r2){
					resultMap.put("success", "true");
				}
				perfTaskNotify.portAlarmBlackListDisPatch();
			}
		}
		return resultMap;
	}
	
	/**
	 * 删除设备黑白名单表中行
	 * @param alarmBlackListBean
	 * @return
	 */
	private boolean deleteAlarmBlackListRows(AlarmBlackListBean row){
		return deviceWBSettingService.deleltAlarmBlackListRow(row);
	}
	/**
	 * 删除设备黑白名单接口/端口列表中行
	 * @param alarmBlackListBean
	 * @return
	 */
	private boolean deleteAlarmBlackPortListRows(AlarmBlackPortListBean row){
		return deviceWBSettingService.deleteAlarmBlackPortListRows(row);
	}
	
	/**
	 * 更改任务状态
	 * @param blackID
	 * @param delete
	 */
	private boolean updateSysBlackListTask(Integer blackID, SysBlackListOpStatus delete) {
		return deviceWBSettingService.updateSysBlackListTask(blackID,delete.getValue(),null,null);
	}
	
	/** 
	 * 跳转到设备黑白名单信息展示页
	 * @param request
	 * @return
	 */
	@RequestMapping("/toShowWBListSetting")
	public ModelAndView toShowWBListSetting(HttpServletRequest request){
		return new ModelAndView("monitor/deviceWBListSetting/WBListView_show");
	}
	
	/** 
	 * 跳转到设备黑白名单信息展示页
	 * @param request
	 * @return
	 */
	@RequestMapping("/toEditWBListSetting")
	public ModelAndView toEditWBListSetting(HttpServletRequest request){
		return new ModelAndView("monitor/deviceWBListSetting/WBListView_edit");
	}
	
	/**
	 * 更改设备黑白名单配置
	 * @param request
	 * @return
	 */
	@RequestMapping("/editWBSettings")
	@ResponseBody
	public Map<String,Object> editWBSettings(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("success", "false");
		String selectedRowsString = request.getParameter("selectedRows");
		String portType = request.getParameter("portType");//接口/端口类型
		String type = request.getParameter("type");// 黑白名单类型
		String operateStatus = request.getParameter("operateStatus");//端口黑白名单启用状态
		String operateStatusForDevice = request.getParameter("operateStatusForDevice");//设备黑白名单启用状态
		String operateStatusChanged = request.getParameter("operateStatusChanged");//设备黑白名单启用状态是否变更
		String collectorChanged = request.getParameter("collectorChanged");//采集机是否变更
		List<Map<String,Object>> MODeviceInfoMapList = JsonUtil.toList(selectedRowsString);
		for (Map<String, Object> map : MODeviceInfoMapList) {
			if (InterfaceOrPort.INTERFACE.is(portType)||InterfaceOrPort.INTERFACE.is(map.get("portType")+"")) {
				if (StringUtils.isNotEmpty(operateStatusForDevice)) {
					// 准备插入设备黑白名单表的数据【AlarmBlackList】
					AlarmBlackListBean alarmBlackListBean = new AlarmBlackListBean();
					alarmBlackListBean.setBlackID(Integer.valueOf(map.get("blackID").toString()));
					// alarmBlackListBean.setMOID(Integer.valueOf(map.get("ifDeviceMOID").toString()));
					// alarmBlackListBean.setDeviceIP(map.get("deviceip").toString());
					// alarmBlackListBean.setPortType(portType);
					alarmBlackListBean.setOprateStatus(operateStatusForDevice);// 启用与否
					boolean r = deviceWBSettingService.updateIntoAlarmBlackList(alarmBlackListBean);
					resultMap.put("success", String.valueOf(r));
				}
				// 设备黑白名单接口/端口列表【AlarmBlackPortList】
				if (StringUtils.isNotEmpty(operateStatus)) {
					AlarmBlackPortListBean alarmBlackPortListBean = new AlarmBlackPortListBean();
					if(map.get("blackPortID")!=null){//修改已经启用或禁用的接口黑白名单状态
						alarmBlackPortListBean.setBlackPortID(Integer.valueOf(map.get("blackPortID").toString()));
						// alarmBlackPortListBean.setMOID(Integer.valueOf(map.get("ifMOID").toString()));//接口MOID
						// alarmBlackPortListBean.setMOName(map.get("ifname").toString());//接口名
						// alarmBlackPortListBean.setType(type);//黑白名单类型
						alarmBlackPortListBean.setOprateStatus(operateStatus);// 端口启用黑白名单与否
						boolean r =deviceWBSettingService.updateIntoAlarmBlackPortList(alarmBlackPortListBean);
						resultMap.put("success", String.valueOf(r));
					}else {//启用或禁用状态空白的接口
						Integer blackID = deviceWBSettingService.getBlackIDByDeviceIPAndPortType(map.get("deviceip")+"", portType);
						if(blackID>0){
							alarmBlackPortListBean.setBlackID(blackID);
							alarmBlackPortListBean.setMOID(Integer.valueOf(map.get("ifMOID").toString()));//接口MOID
							alarmBlackPortListBean.setMOName(map.get("ifname").toString());//接口名
							alarmBlackPortListBean.setType(type);//黑白名单类型
							alarmBlackPortListBean.setOprateStatus(operateStatus);//接口启用状态
							alarmBlackPortListBean.setCreateTime(new Date());
							deviceWBSettingService.insertIntoAlarmBlackPortList(alarmBlackPortListBean);
						}
					}
				}
			}else if (InterfaceOrPort.PORT.is(portType)||InterfaceOrPort.PORT.is(map.get("portType")+"")) {
				SysBlackListTaskBean sysBlackListTaskBean = new SysBlackListTaskBean();
				AlarmBlackListBean alarmBlackListBean = new AlarmBlackListBean();
				alarmBlackListBean.setBlackID(Integer.valueOf(map.get("blackID").toString()));
				alarmBlackListBean.setOprateStatus(operateStatusForDevice);// 启用与否
				sysBlackListTaskBean.setBlackID(alarmBlackListBean.getBlackID());
				if (Boolean.valueOf(operateStatusChanged)&&StringUtils.isNotEmpty(operateStatusForDevice)) {//如果启用状态改变则更新
					// 准备插入设备黑白名单表的数据【AlarmBlackList】
					boolean r1 = deviceWBSettingService.updateIntoAlarmBlackList(alarmBlackListBean);//先更新alarmBlackList
					if("1".equals(operateStatusForDevice)){//从不启用变成启用
						sysBlackListTaskBean.setOperateStatus(SysBlackListOpStatus.INSERT.getIntValue());
					}else if("0".equals(operateStatusForDevice)){//从启用变成不启用
						sysBlackListTaskBean.setOperateStatus(SysBlackListOpStatus.DELETE.getIntValue());
					}
					boolean r2 = deviceWBSettingService.updateSysBlackListTask(sysBlackListTaskBean.getBlackID(), String.valueOf(sysBlackListTaskBean.getOperateStatus()),null,null);
					resultMap.put("success", String.valueOf(r1&&r2));
					perfTaskNotify.portAlarmBlackListDisPatch();
				}
				sysBlackListTaskBean.setOperateStatus(SysBlackListOpStatus.MODIFY.getIntValue());
				sysBlackListTaskBean.setCollectorID(request.getParameter("collectorForDevice")!=null?Integer.valueOf(request.getParameter("collectorForDevice")):null);
				sysBlackListTaskBean.setOldCollectorID(request.getParameter("oldCollector")!=null?Integer.valueOf(request.getParameter("oldCollector")):null);
				if(Boolean.valueOf(collectorChanged)){//如果采集机变更则更新
					boolean r = deviceWBSettingService.updateSysBlackListTask(
							sysBlackListTaskBean.getBlackID(),
							String.valueOf(sysBlackListTaskBean.getOperateStatus()),
							String.valueOf(sysBlackListTaskBean.getCollectorID()),
							String.valueOf(sysBlackListTaskBean.getOldCollectorID())
							);
					resultMap.put("success", String.valueOf(r));
					if("0".equals(operateStatusForDevice)&&!Boolean.valueOf(operateStatusChanged)){
						// 如果只改变设备黑白名单使用的采集机，但是启用状态一直是未启用，则不发送通知
					}else{
						perfTaskNotify.portAlarmBlackListDisPatch();
					}
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 获取性能采集机列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCollectorList")
	@ResponseBody
	public Map<String,Object> getCollectorList(HttpServletRequest request) {
		return deviceWBSettingService.getColletorList();
	}
	
	/**
	 * 在增加服务端口或编辑服务端口时，用户可以删除先前保存的行
	 * @param request
	 * @return
	 */
	@RequestMapping("/delPort")
	@ResponseBody
	public Map<String, String> delPort(HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String deviceIP = request.getParameter("deviceip");
		String portType = InterfaceOrPort.PORT.getValue();
		Integer BlackID = deviceWBSettingService.getBlackIDByDeviceIPAndPortType(deviceIP, portType);
		if (BlackID == null) {
			resultMap.put("success", "false");
			return resultMap;
		} else {
			AlarmBlackPortListBean alarmBlackPortListBean = new AlarmBlackPortListBean();
			alarmBlackPortListBean.setBlackID(BlackID);// 设备黑白名单接口/端口列表
			alarmBlackPortListBean.setPort(Integer.valueOf(request.getParameter("port")));// port号
			boolean delResult = deleteAlarmBlackPortListRows(alarmBlackPortListBean);// 删除设备黑白名单接口/端口列表数据
			resultMap.put("success", String.valueOf(delResult));
			return resultMap;
		}
	}
	
	/**
	 * 在增加服务端口或编辑服务端口时，用户可以修改先前保存的行
	 * @param request
	 * @return
	 */
	@RequestMapping("/updPort")
	@ResponseBody
	public Map<String, String> updPort(HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String deviceIP = request.getParameter("deviceip");
		String portType = InterfaceOrPort.PORT.getValue();
		Integer BlackID = deviceWBSettingService.getBlackIDByDeviceIPAndPortType(deviceIP, portType);
		if (BlackID == null) {
			resultMap.put("success", "false");
			return resultMap;
		} else {
			AlarmBlackPortListBean alarmBlackPortListBean = new AlarmBlackPortListBean();
			alarmBlackPortListBean.setBlackPortID(Integer.valueOf(request.getParameter("blackPortID")));//blackPortID
			alarmBlackPortListBean.setBlackID(BlackID);// 设备黑白名单接口/端口列表
			alarmBlackPortListBean.setPort(Integer.valueOf(request.getParameter("port")));// port号
			alarmBlackPortListBean.setPortType(request.getParameter("portType"));// TCP/UDP
			alarmBlackPortListBean.setOprateStatus(request.getParameter("blackOprateStatus"));// 服务端口黑白名单启用状态
			alarmBlackPortListBean.setPortService(request.getParameter("portService"));//白名单需要的端口对应服务
			if(deviceWBSettingService.checkBlackIDAndPort(alarmBlackPortListBean)){
				resultMap.put("portExisted","true");
				resultMap.put("port",alarmBlackPortListBean.getPort().toString());
				return resultMap;
			}
			boolean updResult = deviceWBSettingService.updateIntoAlarmBlackPortList(alarmBlackPortListBean);
			resultMap.put("success", String.valueOf(updResult));
			return resultMap;
		}
	}
	
	/**
	 * 在增加服务端口或编辑服务端口时，用户可以删除先前保存的行
	 * @param request
	 * @return
	 */
	@RequestMapping("/resetWBSettingForInterface")
	@ResponseBody
	public Map<String, String> resetWBSettingForInterface(HttpServletRequest request) {
		String selectedRowsString = request.getParameter("selectedRows");
		List<Map<String, Object>> MODeviceInfoMapList = JsonUtil.toList(selectedRowsString);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("success", "true");
		for (Map<String, Object> map : MODeviceInfoMapList) {
			if(map.get("blackPortID")==null){
				continue;
			}
			AlarmBlackPortListBean alarmBlackPortListBean = new AlarmBlackPortListBean();
			alarmBlackPortListBean.setBlackPortID(Integer.valueOf(map.get("blackPortID")+""));// blackportID
			alarmBlackPortListBean.setBlackID(Integer.valueOf(map.get("blackID")+""));// blackID
			boolean delResult = deleteAlarmBlackPortListRows(alarmBlackPortListBean);// 删除设备黑白名单接口/端口列表数据
			if(!delResult){
				resultMap.put("success", String.valueOf(delResult));
				return resultMap;
			}
		}
		return resultMap;
	}
	
	/**
	 * 检查设备IP在alarmblacklist中是否已经存在
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkDeviceIP")
	@ResponseBody
	public Map<String, String> checkDeviceIP(HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<String, String>();
		boolean r = deviceWBSettingService.checkDeviceIP(request.getParameter("deviceip"), request.getParameter("portType"));
		resultMap.put("settingIsExisted", String.valueOf(r));
		// 根据设备IP获取moid
		Long deviceMOID = deviceWBSettingService.getMOIDByDeviceIp(request.getParameter("deviceip"));
		if (deviceMOID == null) {
			resultMap.put("deviceIllegal", "true");
			return resultMap;
		}
		return resultMap;
	}
}
