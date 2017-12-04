package com.fable.insightview.monitor.perf.service;

import java.util.List;

import com.fable.insightview.monitor.molist.entity.SysMoInfoBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateUsedBean;

/**
 * 监测列表采集配置
 *
 */
public interface IObjectPerfConfigService {
	/**
	 * 获得模板Id
	 */
	public int getTemplateID(int moId,int moClassId);
	
	/**
	 * 根据templateID获取监测器类型信息
	 */
	public List<String> listMoListByTemplete(int templateID);
	
	/**
	 * 获得设备的监测器
	 */
	public  List<SysMoInfoBean> getMoList(int moId,int moClassId);
	
	/**
	 * 绑定模板
	 */
	public boolean setTmemplate(SysMonitorsTemplateUsedBean bean);
}
