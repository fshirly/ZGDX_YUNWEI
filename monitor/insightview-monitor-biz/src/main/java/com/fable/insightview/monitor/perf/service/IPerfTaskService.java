package com.fable.insightview.monitor.perf.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateBean;
import com.fable.insightview.monitor.perf.entity.PerfTaskInfoBean;
import com.fable.insightview.monitor.website.entity.SiteDns;
import com.fable.insightview.monitor.website.entity.SiteFtp;
import com.fable.insightview.monitor.website.entity.SiteHttp;
import com.fable.insightview.monitor.website.entity.SitePort;
import com.fable.insightview.monitor.website.entity.WebSite;

public interface IPerfTaskService {
	boolean addPerfTasks(List<Integer> moidList);

	List<String> getByMOClassID(int moClassId);
	
	List<String> getMOList(List<String> moList,int taskId);
	
	Map<String, Object> findHostTree();
	
	/**
	 * 获得模板Id
	 */
	public int getTemplateID(int moId,int moClassId);
	
	List<SysMonitorsTemplateBean> getAllTemplate(int moClassId);
	
	boolean doSetPerfTask(PerfTaskInfoBean bean,String moIDs,int templateID,String moTypeLstJson);
	
	boolean doSetDb2PerfTask(PerfTaskInfoBean bean,String moIDs,int templateID,String moTypeLstJson);
	
	List<String> listMoList(int moId);
	/***
	 * TODO
	 * 为机房监控中的空调、ups定制
	 * @param moId
	 * @return
	 */
	List<String> listMoListForRoom(int ResManufacturerID);
	
	public boolean isInScope(String deviceIp,int collectorId);
	
	public boolean delVMsByVHost(PerfTaskInfoBean bean);
	
	String getSiteName(int moId,int moClassId);
	
	/**
	 * 获得DNS信息
	 */
	SiteDns findSiteDnsInfo(int moID);
	 
	/**
	 * 获得FTP信息
	 */
	SiteFtp findSiteFtpnfo(int moID);
	
	/**
	 * 获得HTTP信息
	 */
	SiteHttp findSiteHttpnfo(int moID);
	
	/**
	 * 查找tcp信息
	 */
	SitePort findSitePortInfo(int moID);
	
	int operateSiteCommunity(WebSite webSite);
	
	PerfTaskInfoBean getDNSTask(int taskId);
	
	PerfTaskInfoBean getFtpTask(int taskId);
	
	PerfTaskInfoBean getHttpTask(int taskId);
	
	PerfTaskInfoBean getTcpTask(int taskId);
	
	/**
	 * 批量暂停
	 */
	public Map<String, Object> doBatchStop(String taskIds);
	
	/**
	 * 选择对象类型树
	 */
	public Map<String, Object> initTree();
	
	/**
	 * 批量恢复
	 */
	public Map<String, Object> doBatchStart(String taskIds);
}
