package com.fable.insightview.monitor.perf.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.perf.entity.PerfKPIDefBean;
import com.fable.insightview.monitor.perf.entity.SysKPIOfMOClassBean;

public interface IPerfKPIDefService {
	/**
	 * 获得指标列表
	 * 
	 * @param page
	 * @return
	 */
	List<PerfKPIDefBean> getPerfKPIDefList(Page<PerfKPIDefBean> page);

	/**
	 * 删除指标
	 * 
	 * @param kpiIDs
	 * @return
	 */
	boolean delPerKPIDef(List<Integer> kpiIDs);

	/**
	 * 删除前的验证
	 * 
	 * @param kpiID
	 * @return
	 */
	boolean checkBeforeDel(Integer kpiID);

	/**
	 * 获取指标详情
	 * 
	 * @param kpiID
	 * @return
	 */
	PerfKPIDefBean getPerfKPIDefById(int kpiID);

	/**
	 * 验证指标名称的唯一性
	 * 
	 * @param flag
	 * @param perfKPIDefBean
	 * @return
	 */
	Map<String, Object> checkPerKPIName(String flag,
			PerfKPIDefBean perfKPIDefBean);

	/**
	 * 新增指标
	 * 
	 * @param perfKPIDefBean
	 * @return
	 */
	boolean addPerfKPIDef(PerfKPIDefBean perfKPIDefBean);

	/**
	 * 更新指标
	 * 
	 * @param perfKPIDefBean
	 * @return
	 */
	boolean updatePerfKPIDef(PerfKPIDefBean perfKPIDefBean);
	
	/**
	 * 获得所有的指标类别
	 */
	List<PerfKPIDefBean> getAllCategory();
	
	/**
	 * 加载指标与对象关系数据
	 */
	List<SysKPIOfMOClassBean> getKPIOfMOClassList(Page<SysKPIOfMOClassBean> page);
	
	/**
	 * 判断指标与对象关系是否已存在
	 */
	boolean isExistKPIOfMOClass(SysKPIOfMOClassBean bean);
	
	/**
	 * 新增指标与对象关系
	 */
	boolean addKPIOfMOClass(SysKPIOfMOClassBean bean);
	
	/**
	 * 删除指标与对象关系
	 */
	boolean delKPIOfMOClass(int id);
	
	/**
	 * 获得对象的所有父对象的id
	 */
	List<Integer> getAllParentClassID(String classID);
	
	Map<String, Object> initParentTree(List<Integer> classIds);
}
