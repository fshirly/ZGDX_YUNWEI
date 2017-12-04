package com.fable.insightview.monitor.database.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.database.entity.MOMySQLDBBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLRunObjectsBean;
import com.fable.insightview.monitor.database.entity.MOMySQLVarsBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLConnectionBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLQueryCacheBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLTableCacheBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLTableLockBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLTmpTableBean;
import com.fable.insightview.platform.page.Page;

public interface MySQLMapper {
	// 根据实例ID查询数据库服务信息
	MOMySQLDBServerBean getMyServerInfo(Map map);

	// 获取所有数据库
	List<MOMySQLDBBean> getMyDB(Integer moID);

	/**
	 * 获取 数据库可用率 图表
	 * 
	 * @return
	 */
	MOMySQLDBServerBean getMyChartAvailability(Map map);

	/**
	 * 获取数据库连接性能
	 * 
	 * @param map
	 * @return
	 */
	List<PerfMySQLConnectionBean> queryMyConnectionPerf(Map map);

	/**
	 * 获取临时表曲线数据
	 * 
	 * @param map
	 * @return
	 */
	List<PerfMySQLTmpTableBean> queryMyTmpPerf(Map map);

	/**
	 * 获取表缓存曲线数据
	 * 
	 * @param map
	 * @return
	 */
	List<PerfMySQLTableCacheBean> queryMyTableCachePerf(Map map);

	/**
	 * 获取表锁曲线数据
	 * 
	 * @param map
	 * @return
	 */
	List<PerfMySQLTableLockBean> queryMyTableLockPerf(Map map);

	/**
	 * 获取查询缓存曲线数据
	 * 
	 * @param map
	 * @return
	 */
	List<PerfMySQLQueryCacheBean> queryMyQueryCachePerf(Map map);

	/**
	 * 分页查询 系统变量 模糊查询 like
	 * 
	 * @author
	 * @param page
	 * @return
	 */
	public List<MOMySQLVarsBean> getMyVarsInfoByLike(Page<MOMySQLVarsBean> page);

	/**
	 * 分页查询 系统变量 精确查询 =
	 * 
	 * @author
	 * @param page
	 * @return
	 */
	public List<MOMySQLVarsBean> getMyVarsInfoByEq(Page<MOMySQLVarsBean> page);

	/**
	 * 分页查询Mysqlserver
	 * 
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	public List<MOMySQLDBServerBean> queryMOMySQLDBServer(
			Page<MOMySQLDBServerBean> page);

	/**
	 * 分页查询MOMySQLDB
	 * 
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	public List<MOMySQLDBBean> queryMOMySQLDB(Page<MOMySQLDBBean> page);

	/**
	 * 分页查询MOMySQLRunObjects
	 * 
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	public List<MOMySQLRunObjectsBean> queryMOMySQLRunObj(
			Page<MOMySQLRunObjectsBean> page);

	/**
	 * 分页查询MOMySQLVars 模糊查询 like
	 * 
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	public List<MOMySQLVarsBean> queryMOMySQLVarsByLike(
			Page<MOMySQLVarsBean> page);

	/**
	 * 分页查询MOMySQLVars 精确查询 =
	 * 
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	public List<MOMySQLVarsBean> queryMOMySQLVarsByEq(Page<MOMySQLVarsBean> page);

	/**
	 * 根据Id获得运行对象信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMySQLRunObjectsBean getMySQLRunObjByID(int moId);

	/**
	 * 根据Id获得MySQLServer信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMySQLDBServerBean getMysqlServerByID(int moId);

	/**
	 * 根据Id获得MySQLServer信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMySQLDBBean getMysqlDBByID(int moId);

	/**
	 * 根据Id获得MySQLServer信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMySQLVarsBean getMysqlVarsByID(int moId);
	
	Integer getInsIdBymoId(int moId);
	
	List<Integer> getMySQLDBServerIDByMOID(int moId);
	
	List<MOMySQLDBServerBean> getMySQLDBServer(Map map);
	
	int getDbmsMoIdBySqlserver(@Param("moId")int moId);
	
	List<Integer> getDBChildId(Map<String, Object> param);
}