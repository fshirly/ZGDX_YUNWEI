package com.fable.insightview.monitor.DutyService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.monitor.dutydesk.bean.ResDeviceBean;
import com.fable.insightview.monitor.dutydesk.service.IDutyDeskService;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.host.entity.MODevice;

/**
 * 值班服务台监控模块rest接口
 * @author hanl
 *
 */
@Controller
@RequestMapping("/rest/monitor/dutyService")
public class DutyServiceInvokeController {
	@Autowired
	IDutyDeskService dutyDeskService;
	
	/**
	 * 资源监控的数据
	 */
	@RequestMapping(value = "/getResMonitorData", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<ResDeviceBean> getResMonitorData() {
		//获得列表的数据
		return dutyDeskService.getResMODevice();
	}
	
	/**
	 * 获得所有的topo及默认展示的topo
	 */
	@RequestMapping(value = "/getTopos", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> getTopos() {
		return dutyDeskService.getTopos();
	}
	
	/**
	 * 获得所有的3D机房及默认展示的机房
	 */
	@RequestMapping(value = "/get3dRooms", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> get3dRooms() {
		return dutyDeskService.get3dRooms();
	}
	
	/**
	 * 根据条件获得告警列表信息
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/getTopAlarm", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> getTopAlarm(@RequestBody Map<String, Object> paramMap) {
		return dutyDeskService.getTopAlarm(paramMap);
	}
	
	/**
	 * 根据ID告警详细信息
	 */
	@RequestMapping(value = "/getAlarmDtail", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	AlarmNode getAlarmDtail(@RequestBody Map<String, Integer> paramMap) {
		return dutyDeskService.getAlarmDtail(paramMap);
	}
	
	
	
	/**
	 * 获得告警更多列表数据
	 */
	@RequestMapping(value = "/getAlarmList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> getAlarmList(@RequestBody Map<String, Object> paramMap) {
		return dutyDeskService.getAlarmList(paramMap);
	}
	
	
	/**
	 * 根据条件获得cpu信息
	 */
	@RequestMapping(value = "/getTopCpu", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<MODevice> getTopCpu(@RequestBody Map<String, Object> paramMap) {
		return dutyDeskService.getTopCpu(paramMap);
	}
	
	/**
	 * 根据条件获得top内存信息
	 */
	@RequestMapping(value = "/getTopMemory", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<MODevice> getTopMemory(@RequestBody Map<String, Object> paramMap) {
		return dutyDeskService.getTopMemory(paramMap);
	}
	
	/**
	 * 根据条件获得top磁盘信息
	 */
	@RequestMapping(value = "/getTopVolumes", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<MODevice> getTopVolumes(@RequestBody Map<String, Object> paramMap) {
		return dutyDeskService.getTopVolumes(paramMap);
	}
	
	/**
	 * 获得cpu使用率更多列表数据
	 */
	@RequestMapping(value = "/getCpuList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> getCpuList(@RequestBody Map<String, Object> paramMap) {
		return dutyDeskService.getCpuList(paramMap);
	}
	
	/**
	 * 获得内存使用率更多列表数据
	 */
	@RequestMapping(value = "/getMemList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> getMemList(@RequestBody Map<String, Object> paramMap) {
		return dutyDeskService.getMemList(paramMap);
	}
	
	/**
	 * 获得磁盘使用率更多列表数据
	 */
	@RequestMapping(value = "/getVolList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> getVolList(@RequestBody Map<String, Object> paramMap) {
		return dutyDeskService.getVolList(paramMap);
	}
	
	/**
	 * 获得告警列表中查询条件：告警级别、告警类型、告警状态下拉框数据
	 */
	@RequestMapping(value = "/getAlarmConditions", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> getAlarmConditions() {
		return dutyDeskService.getAlarmConditions();
	}
	
}
