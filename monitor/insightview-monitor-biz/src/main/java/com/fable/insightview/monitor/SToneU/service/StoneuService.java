package com.fable.insightview.monitor.SToneU.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.SToneU.entity.MODeviceSt;
import com.fable.insightview.monitor.SToneU.entity.PerfStoneuBean;
import com.fable.insightview.monitor.SToneU.entity.StoneuDeviceKpiBean;
import com.fable.insightview.monitor.util.ConfigParameterCommon;
import com.fable.insightview.platform.page.Page;

public interface StoneuService {

	/**
	 * 获取机房监控信息
	 * @param page
	 * @return
	 */
	List<MODeviceSt>  getStoneuList(Page<MODeviceSt> page);
	/**
	 * 获取测试结果
	 * @param snmp
	 * @param type 
	 * @return
	 */
	boolean getTestResult(ConfigParameterCommon snmp);
	
	/**
	 * 获取厂商ID
	 * @param OID
	 * @return
	 */
	int getResManufacturerID(String OID);
 
	/**
	 * 新增采集任务
	 * @param stone
	 * @param templateID
	 * @param moTypeLstJson
	 * @return
	 */
	boolean addSitePerfTask(MODeviceSt stoneu, int templateID, String moTypeLstJson);
	/**
	 * 新增
	 * @param stList
	 * @param templateID
	 * @param moTypeLstJson
	 * @return
	 */
	boolean addDoBatch(MODeviceSt stoneu, int templateID, String moTypeLstJson);
	
	/**
	 * 更新数据
	 * @param stoneu
	 * @param moTypeLstJson 
	 * @param templateID 
	 * @return
	 */
	boolean updateInfo(MODeviceSt stoneu, int templateID, String moTypeLstJson);
	
	/**
	 * 根据MOID查询相关信息
	 * @param paramMap
	 * @return
	 */
	MODeviceSt queryByMOID(Map<String, Object> paramMap);
	
	/**
	 * 获取设备下子对象的性能数据
	 * @return
	 */
	List<PerfStoneuBean> perfList(String deviceIP);
	/**
	 * 校验该对象是否已存在modevice表中
	 * @param paramMap
	 * @return
	 */
	int checkMOdevice(Map<String, Object> paramMap);
	
	Date getDateNow();
}
