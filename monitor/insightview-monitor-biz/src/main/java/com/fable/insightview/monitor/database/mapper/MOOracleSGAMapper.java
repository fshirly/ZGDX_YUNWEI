package com.fable.insightview.monitor.database.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.database.entity.MOOracleSGABean;
import com.fable.insightview.monitor.database.entity.PerfOrclSGABean;
import com.fable.insightview.platform.page.Page;

public interface MOOracleSGAMapper {
	/**
	 * 根据实例ID查询SGA详情
	 * 
	 * @param MOID
	 * @return
	 */
	MOOracleSGABean getOrclSGADetail(Integer MOID);

	List<PerfOrclSGABean> queryOrclSGAPerf(Map map);

	/**
	 * 获取所有SGA列表
	 * 
	 * @param page
	 * @return
	 */
	List<MOOracleSGABean> getOrclSGAList(Page<MOOracleSGABean> page);

	/**
	 * 根据SGA的id获得SGA详情
	 * 
	 * @param moID
	 * @return
	 */
	MOOracleSGABean getOrclSGAByMoID(int moID);
}