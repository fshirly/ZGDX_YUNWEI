package com.fable.insightview.monitor.perf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.perf.entity.PerfKPIDefBean;

public interface PerfKPIDefMapper {
	List<PerfKPIDefBean> selectPerfKPIDefs(Page<PerfKPIDefBean> page);

	/**
	 * 指标列表
	 * 
	 * @param page
	 * @return
	 */
	List<PerfKPIDefBean> getPerfKPIDefList(Page<PerfKPIDefBean> page);

	/**
	 * 获取指标详情
	 * 
	 * @param kpiID
	 * @return
	 */
	PerfKPIDefBean getPerfKPIDefById(int kpiID);

	/**
	 * 删除指标
	 * 
	 * @param kpiIDs
	 * @return
	 */
	boolean delPerKPIDef(List<Integer> kpiIDs);

	/**
	 * 根据kpiID获得KPI计算条件表
	 * 
	 * @param kpiID
	 * @return
	 */
	int getKPIExprByKPIID(int kpiID);

	/**
	 * 根据kpiID获得阈值
	 * 
	 * @param kpiID
	 * @return
	 */
	int getThresholdByKPIID(int kpiID);

	/**
	 * 根据kpiID获得管理对象性能指标定义
	 * 
	 * @param kpiID
	 * @return
	 */
	int getMObjectKPIDefByKPIID(int kpiID);

	int getKPIByName(PerfKPIDefBean bean);

	int getKPIByNameAndID(PerfKPIDefBean bean);

	int getKPIByEnName(PerfKPIDefBean bean);

	int getKPIByEnNameAndID(PerfKPIDefBean bean);

	/**
	 * 新增指标
	 * 
	 * @param perfKPIDefBean
	 * @return
	 */
	int insertPerfKPIDef(PerfKPIDefBean perfKPIDefBean);

	/**
	 * 更新指标
	 * 
	 * @param perfKPIDefBean
	 * @return
	 */
	int updatePerfKPIDef(PerfKPIDefBean perfKPIDefBean);
	
	/**
	 * 获得所有的指标类别
	 */
	List<PerfKPIDefBean> getAllCategory();
	
	List<MObjectDefBean> getKPIObject(@Param("kpiID") Integer kpiID);
}
