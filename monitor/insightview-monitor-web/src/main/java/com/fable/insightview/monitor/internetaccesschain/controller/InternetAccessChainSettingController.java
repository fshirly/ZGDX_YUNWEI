package com.fable.insightview.monitor.internetaccesschain.controller;

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

import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackPortListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.InterfaceOrPort;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListMonitorsBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListOpStatus;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListTaskBean;
import com.fable.insightview.monitor.alarmmgr.internetaccesschain.service.IInternetAccessChainSettingService;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.perfTask.PerfTaskNotify;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * 互联网接入链路配置Controller
 * 
 * @author zhouwf
 *
 */
@Controller
@RequestMapping("/monitor/internetAccessChainSetting")
public class InternetAccessChainSettingController {

	private PerfTaskNotify perfTaskNotify = new PerfTaskNotify();

	@Autowired
	private IInternetAccessChainSettingService internetAccessChainSettingService;

	/**
	 * 跳转到互联网接入链路配置主页
	 * 
	 * @param request
	 * @param response
	 * @param navigationBar
	 * @return
	 */
	@RequestMapping("/toIACSListMainView")
	public ModelAndView toWBListMainView(HttpServletRequest request, HttpServletResponse response, String navigationBar) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/internetAccessChainSetting/IACSListMainView");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 展示主页所有互联网链路配置
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping("/listIACSettings")
	@ResponseBody
	public Map<String, Object> listIACSettings(AlarmBlackListBean vo) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page page = new Page();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceIP", vo.getDeviceIP());
		paramMap.put("portTypes", InterfaceOrPort.INTERNET_ACCESS.getValue());
		page.setParams(paramMap);
		List<AlarmBlackListBean> alarmBlackList = internetAccessChainSettingService.getIACSettingList(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", alarmBlackList);
		return result;
	}
	
	@RequestMapping("/listChainNameAndGateWayByDeviceIP")
	@ResponseBody
	public Map<String,Object> listChainNameAndGateWayByDeviceIP(MODevice vo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MODevice> page = new Page<MODevice>(); 
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("deviceIP", vo.getDeviceip());//设备IP
		paramMap.put("opt", request.getParameter("opt"));//页面操作类型
		paramMap.put("portType", InterfaceOrPort.INTERNET_ACCESS.getValue());//服务端口
		page.setParams(paramMap);
		List<MODevice> alarmBlackList =  internetAccessChainSettingService.getChainListByDeviceIP(page);
		int total = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", total);
		result.put("rows", alarmBlackList);
		return result;
	}

	/**
	 * 跳转到新增互联网接入链路配置页
	 * 
	 * @param request
	 * @param response
	 * @param navigationBar
	 * @return
	 */
	@RequestMapping("/toAddIACSettingView")
	public ModelAndView toAddIACSettingView(HttpServletRequest request, HttpServletResponse response, String navigationBar) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/internetAccessChainSetting/IACSetting_add");
		return mv;
	}
	
	/**
	 * 跳转到查看互联网接入链路配置页
	 * 
	 * @param request
	 * @param response
	 * @param navigationBar
	 * @return
	 */
	@RequestMapping("/toShowIACSettingView")
	public ModelAndView toShowIACSettingView(HttpServletRequest request, HttpServletResponse response, String navigationBar) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/internetAccessChainSetting/IACSetting_show");
		return mv;
	}
	
	/**
	 * 跳转到修改互联网接入链路配置页
	 * 
	 * @param request
	 * @param response
	 * @param navigationBar
	 * @return
	 */
	@RequestMapping("/toModifyIACSettingView")
	public ModelAndView toModifyIACSettingView(HttpServletRequest request, HttpServletResponse response, String navigationBar) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/internetAccessChainSetting/IACSetting_edit");
		return mv;
	}
	
	/**
	 * 链路名称与网关增加
	 * @param request
	 * @return
	 */
	@RequestMapping("/addIACSettings")
	@ResponseBody
	public Map<String, Object> addIACSettings(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", "false");
		String insertedRowsString = request.getParameter("insertedRows");// 链路名称、链路接入网关行数据
		String collectorId = request.getParameter("collectorId");// 采集机ID
		String deviceIp = request.getParameter("deviceIP");// 设备IP
		String operateStatus = request.getParameter("operateStatus");// 设备是否启用
		String connIPAddresss = request.getParameter("connIPAddresss");// 测试连接地址
		List<Map<String, Object>> InfoMapList = JsonUtil.toList(insertedRowsString);
		// 根据设备IP获取moid
		Long deviceMOID = internetAccessChainSettingService.getMOIDByDeviceIp(deviceIp);
		if (deviceMOID == null) {
			resultMap.put("ipExist", "false");
			resultMap.put("success", "false");
			return resultMap;
		}
		try {
			Integer blackID = -1;
			if (blackID < 0) {
				// 准备插入设备黑白名单表的数据【AlarmBlackList】
				AlarmBlackListBean alarmBlackListBean = new AlarmBlackListBean();
				alarmBlackListBean.setMOID(deviceMOID.intValue());
				alarmBlackListBean.setDeviceIP(deviceIp);
				alarmBlackListBean.setConnIPAddresss(connIPAddresss);
				alarmBlackListBean.setPortType(InterfaceOrPort.INTERNET_ACCESS.getValue());
				alarmBlackListBean.setOprateStatus(operateStatus);
				alarmBlackListBean.setCreateTime(new Date());
				if ((blackID = internetAccessChainSettingService.getBlackIDByDeviceIPAndPortType(deviceIp, InterfaceOrPort.INTERNET_ACCESS.getValue())) != null) {
				} else {
					blackID = internetAccessChainSettingService.insertIntoAlarmBlackList(alarmBlackListBean);
					if (blackID == null) {
						return resultMap;
					}
				}
			}
			for (Map<String, Object> map : InfoMapList) {
				// 向设备黑白名单接口/端口列表【AlarmBlackPortList】插入数据
				AlarmBlackPortListBean alarmBlackPortListBean = new AlarmBlackPortListBean();
				alarmBlackPortListBean.setBlackID(blackID);
				alarmBlackPortListBean.setPortService(map.get("portService") + "");
				alarmBlackPortListBean.setGatewayIP(map.get("gatewayIP") + "");
				alarmBlackPortListBean.setOprateStatus("1");
				alarmBlackPortListBean.setCreateTime(new Date());
				boolean r = internetAccessChainSettingService.insertIntoAlarmBlackPortList(alarmBlackPortListBean);
				if (!r) {
					return resultMap;
				}
			}
			// SysBlackListTask表中BlackID唯一，如果BlackID已经存在就不用新增一条记录
			// 向黑白名单任务列表【SysBlackListTask】插入数据
			SysBlackListTaskBean sysBlackListTaskBean = new SysBlackListTaskBean();
			sysBlackListTaskBean.setOperateStatus(operateStatus.equals("1")?1:3);
			sysBlackListTaskBean.setProgressStatus(1);
			sysBlackListTaskBean.setCollectorID(Integer.valueOf(collectorId));
			sysBlackListTaskBean.setBlackID(blackID);
			sysBlackListTaskBean.setCreateTime(new Date());
			sysBlackListTaskBean.setCreator(((SecurityUserInfoBean)request.getSession().getAttribute("sysUserInfoBeanOfSession")).getId().intValue());
			Integer taskID = -1;
			if (!internetAccessChainSettingService.checkBlackIDInSysBlackListTask(sysBlackListTaskBean.getBlackID())) {
				taskID = internetAccessChainSettingService.insertIntoSysBlackListTask(sysBlackListTaskBean);
				if (taskID == null) {
					return resultMap;
				}
				// 向黑白名单监测器列表【SysBlackListMonitors】插入数据
				SysBlackListMonitorsBean sysBlackListMonitorsBean = new SysBlackListMonitorsBean();
				// 先检索监测器信息
				Map<String, Object> moInfoMap = internetAccessChainSettingService.getMonitorInfo("JobInternetAccess");
				sysBlackListMonitorsBean.setDoIntervals(Integer.valueOf(moInfoMap.get("DoIntervalsBySec") + ""));// nmap监测器采集周期
				sysBlackListMonitorsBean.setMID(Integer.valueOf(moInfoMap.get("MID") + ""));// nmap监测器MID
				sysBlackListMonitorsBean.setTaskID(taskID);
				boolean s = internetAccessChainSettingService.insertIntoSysBlackListMonitors(sysBlackListMonitorsBean);
				if (!s) {
					return resultMap;
				}
				perfTaskNotify.portAlarmBlackListDisPatch();
			}
			resultMap.put("success", "true");
			return resultMap;
		} catch (Exception e) {
			resultMap.put("success", "false");
			return resultMap;
		}
	}
	
	/**
	 * 链路名称与网关删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteChainSetting")
	@ResponseBody
	public Map<String, Object> deleteChainSetting(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", "false");
		String deletedRows = request.getParameter("deletedRows");// 链路名称、链路接入网关行数据
		List<Map<String, Object>> InfoMapList = JsonUtil.toList(deletedRows);
		try {
			for (Map<String, Object> map : InfoMapList) {
				// 向设备黑白名单接口/端口列表【AlarmBlackPortList】插入数据
				AlarmBlackPortListBean alarmBlackPortListBean = new AlarmBlackPortListBean();
				alarmBlackPortListBean.setBlackPortID(Integer.valueOf(map.get("blackPortID")+""));
				boolean r = internetAccessChainSettingService.deleteAlarmBlackPortListRows(alarmBlackPortListBean);
				if (!r) {
					return resultMap;
				}
			}
			resultMap.put("success", "true");
			return resultMap;
		} catch (Exception e) {
			resultMap.put("success", "false");
			return resultMap;
		}
	}
	
	/**
	 * 更改设备互联网接入链路启用状态或相应采集机
	 * @param request
	 * @return
	 */
	@RequestMapping("/editIACSettings")
	@ResponseBody
	public Map<String,Object> editIACSettings(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("success", "false");
		String selectedRowsString = request.getParameter("selectedRows");
		String operateStatusForDevice = request.getParameter("operateStatusForDevice");//设备黑白名单启用状态
		String operateStatusChanged = request.getParameter("operateStatusChanged");//设备黑白名单启用状态是否变更
		String collectorChanged = request.getParameter("collectorChanged");//采集机是否变更
		String connIPAddresss = request.getParameter("connIPAddresss");//测试地址
		String connIPAddresssChanged = request.getParameter("connIPAddresssChanged");//测试地址是否变更
		List<Map<String,Object>> MODeviceInfoMapList = JsonUtil.toList(selectedRowsString);
		for (Map<String, Object> map : MODeviceInfoMapList) {
			AlarmBlackListBean alarmBlackListBean = new AlarmBlackListBean();
			alarmBlackListBean.setBlackID(Integer.valueOf(map.get("blackID").toString()));
			SysBlackListTaskBean sysBlackListTaskBean = new SysBlackListTaskBean();
			sysBlackListTaskBean.setBlackID(alarmBlackListBean.getBlackID());
			if (InterfaceOrPort.INTERNET_ACCESS.is(map.get("portType")+"")) {
				if (Boolean.valueOf(connIPAddresssChanged)) {
					alarmBlackListBean.setConnIPAddresss(connIPAddresss);
					internetAccessChainSettingService.updateIntoAlarmBlackList(alarmBlackListBean);//先更新alarmBlackList
				}
				if (Boolean.valueOf(operateStatusChanged)&&StringUtils.isNotEmpty(operateStatusForDevice)) {//如果启用状态改变则更新
					alarmBlackListBean.setOprateStatus(operateStatusForDevice);// 启用与否
					// 准备插入设备黑白名单表的数据【AlarmBlackList】
					boolean r1 = internetAccessChainSettingService.updateIntoAlarmBlackList(alarmBlackListBean);//先更新alarmBlackList
					if("1".equals(operateStatusForDevice)){//从不启用变成启用
						sysBlackListTaskBean.setOperateStatus(SysBlackListOpStatus.INSERT.getIntValue());
					}else if("0".equals(operateStatusForDevice)){//从启用变成不启用
						sysBlackListTaskBean.setOperateStatus(SysBlackListOpStatus.DELETE.getIntValue());
					}
					boolean r2 = internetAccessChainSettingService.updateSysBlackListTask(sysBlackListTaskBean.getBlackID(), String.valueOf(sysBlackListTaskBean.getOperateStatus()),null,null);
					resultMap.put("success", String.valueOf(r1&&r2));
					perfTaskNotify.portAlarmBlackListDisPatch();
				}
				sysBlackListTaskBean.setOperateStatus(SysBlackListOpStatus.MODIFY.getIntValue());
				sysBlackListTaskBean.setCollectorID(Integer.valueOf(request.getParameter("collectorForDevice")));
				sysBlackListTaskBean.setOldCollectorID(Integer.valueOf(request.getParameter("oldCollector")));
				if(Boolean.valueOf(collectorChanged)){//如果采集机变更则更新
					boolean r = internetAccessChainSettingService.updateSysBlackListTask(
							sysBlackListTaskBean.getBlackID(),
							String.valueOf(sysBlackListTaskBean.getOperateStatus()),
							String.valueOf(sysBlackListTaskBean.getCollectorID()),
							String.valueOf(sysBlackListTaskBean.getOldCollectorID())
							);
					resultMap.put("success", String.valueOf(r));
					perfTaskNotify.portAlarmBlackListDisPatch();
				}
			}
		}
		return resultMap;
	}
}
