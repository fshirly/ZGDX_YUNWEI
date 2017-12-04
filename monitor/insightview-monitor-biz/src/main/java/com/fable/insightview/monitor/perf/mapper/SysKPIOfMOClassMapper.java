package com.fable.insightview.monitor.perf.mapper;

import java.util.List;

import com.fable.insightview.monitor.perf.entity.PerfKPIDefBean;
import com.fable.insightview.monitor.perf.entity.SysKPIOfMOClassBean;
import com.fable.insightview.platform.page.Page;

public interface SysKPIOfMOClassMapper {
	List<PerfKPIDefBean> selectPerfKPIDefs(Page<PerfKPIDefBean> page);

	/**
	 * 指标与对象类型关系列表
	 * 
	 */
	List<SysKPIOfMOClassBean> getKPIOfMOClassList(Page<SysKPIOfMOClassBean> page);

	/**
	 * 根据指标和对象获得
	 * 
	 */
	List<SysKPIOfMOClassBean> getByKPIAndClass(SysKPIOfMOClassBean bean);

	/**
	 * 新增指标与对象类型关系
	 */
	int insertKPIOfMOClass(SysKPIOfMOClassBean bean);

	/**
	 * 删除指标与对象类型关系
	 * 
	 */
	boolean delKPIOfMOClass(int id);
	
	/**
	 * 根据指标id获得关系
	 */
	int getCountByKPIID(int kpiID);
}
