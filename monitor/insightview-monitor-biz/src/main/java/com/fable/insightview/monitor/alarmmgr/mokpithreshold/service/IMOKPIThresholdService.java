package com.fable.insightview.monitor.alarmmgr.mokpithreshold.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.alarmmgr.entity.MOKPIThresholdBean;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.momemories.entity.MOMemoriesBean;
import com.fable.insightview.monitor.monetworkIif.entity.MONetworkIfBean;
import com.fable.insightview.monitor.movolumes.entity.MOVolumesBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.perf.entity.PerfKPIDefBean;

/**
 * 阈值规则定义
 *
 */
public interface IMOKPIThresholdService {
	/**
	 * 查询列表数据
	 */
	List<MOKPIThresholdBean> selectThreshold(Page<MOKPIThresholdBean> page);
	
	/**
	 * 获得源对象的名称
	 */
	void setSourceName(MOKPIThresholdBean bean);
	
	/**
	 * 获得管理对象的名称
	 */
	void setMoName(MOKPIThresholdBean bean);
	
	
	boolean delThreshold(List<Integer> ruleIDs);
	
	Map<String, Object> findMObject(MObjectDefBean mobjectDefBean);
	
	Map<String, Object> listPerfKPIDef(PerfKPIDefBean perfKPIDefBean);
	
	boolean checkBeforeAdd(MOKPIThresholdBean mokpiThresholdBean);
	
	boolean addThreshold(MOKPIThresholdBean mokpiThresholdBean);
	
	MOKPIThresholdBean initThresholdDetail(
			MOKPIThresholdBean mokpiThresholdBean);
	
	boolean checkBeforeUpdate(MOKPIThresholdBean mokpiThresholdBean);
	
	boolean updateThreshold(MOKPIThresholdBean mokpiThresholdBean);
	
	List<MOMemoriesBean> selectMOMemories(Page<MOMemoriesBean> page);
	
	List<MOVolumesBean> selectMOVolumes(Page<MOVolumesBean> page);
	
	boolean checkNetworkIfMOID(MONetworkIfBean bean);
	
	boolean checkVolMOID(MOVolumesBean bean);
	
	boolean checkCPUtMOID(String deviceMOIDStr,String moID);
	
	boolean checkMemMOID(String deviceMOIDStr,String moID);
	
	MOKPIThresholdBean getInfoBySourceMOID(String moID, String sourceMOID, String classID);
	
	String getSiteName(MOKPIThresholdBean bean);
	
	List<MObjectDefBean> initObject(int kpiID);
}
