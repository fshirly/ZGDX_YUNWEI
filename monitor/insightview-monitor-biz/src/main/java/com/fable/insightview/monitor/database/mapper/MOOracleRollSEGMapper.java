package com.fable.insightview.monitor.database.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.database.entity.MOOracleRollSEGBean;
import com.fable.insightview.monitor.database.entity.PerfOrclRollbackBean;
import com.fable.insightview.platform.page.Page;

public interface MOOracleRollSEGMapper {

	List<MOOracleRollSEGBean> getOrclRollSEGList(Map map);

	MOOracleRollSEGBean getRollSEGByMoID(int moID);
	/**
	 * 根据moid查询所有回滚段信息
	 * @author zhengxh
	 * @param moId
	 * @return
	 */
	public List<MOOracleRollSEGBean> queryAllOrclRollback(@Param("moId")String moId);
	/**
	 * 查询回滚段指标信息
	  * @author zhengxh
	 * @param map
	 * @return
	 */
	public List<PerfOrclRollbackBean> queryOrclRollBackPerf(Map map);
	/**
	 * 获取所有oracle回滚段信息
	 * @param map
	 * @return
	 */
	List<MOOracleRollSEGBean> getAllOrclRollSEGList(Page<MOOracleRollSEGBean> page);
}