package com.fable.insightview.monitor.database.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOOracleDataFileBean;
import com.fable.insightview.monitor.database.entity.MOOracleRollSEGBean;
import com.fable.insightview.monitor.database.entity.MOOracleSGABean;
import com.fable.insightview.monitor.database.entity.MOOracleTBSBean;
import com.fable.insightview.monitor.database.entity.OracleDbInfoBean;
import com.fable.insightview.monitor.database.entity.PerfOrclDataFileBean;
import com.fable.insightview.monitor.database.entity.PerfOrclRollbackBean;
import com.fable.insightview.monitor.database.entity.PerfOrclSGABean;
import com.fable.insightview.monitor.database.entity.PerfOrclTBsBean;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.platform.page.Page;

public interface IOracleService {
	/**
	 * 根据实例ID查询实例详情
	 * 
	 * @param MOID
	 * @return
	 */
	MODBMSServerBean getOrclInstanceDetail(Map map);

	/**
	 * 获取表空间列表 FOR 表空间个数
	 */
	List<MODBMSServerBean> getTbsCount(Map map);

	/**
	 * 根据实例ID查询相关表空间列表信息
	 */
	List<MODBMSServerBean> getOrclTbsInfo(Map map);

	/**
	 * 获取 实例可用率 图表
	 * 
	 * @return
	 */
	MODBMSServerBean getOrclChartAvailability(Map map);

	/* 根据实例ID查询SGA详情 */
	MOOracleSGABean getOrclSGADetail(Integer MOID);

	/**
	 * 表空间曲线表名称
	 * 
	 * @param MOID
	 * @return
	 */
	List<MOOracleTBSBean> queryAllOrclTbs(String MOID);

	/**
	 * 获取表空间曲线数据
	 * 
	 * @param map
	 * @return
	 */
	List<PerfOrclTBsBean> queryOrclTbsPerf(Map map);

	/**
	 * 表空间活动告警
	 * 
	 * @param map
	 * @return
	 */
	List<AlarmActiveDetail> getTbsAlarmInfo(Map map);

	/**
	 * 获取所有数据文件
	 * 
	 * @return
	 */
	List<MOOracleDataFileBean> getAllDataFiles(Integer moId);

	/**
	 * 根据文件ID获取详细信息
	 * 
	 * @return
	 */
	MOOracleDataFileBean getDataDetailByMoId(Integer moId);

	/**
	 * 根据表空间ID获取详细信息
	 * 
	 * @return
	 */
	MOOracleTBSBean getTbsDetailByMoId(Integer moId);

	/**
	 * 分页查询
	 * 
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	List<MODBMSServerBean> queryList(Page<MODBMSServerBean> page);

	/**
	 * 根据moid查询所有回滚段信息
	 * 
	 * @author zhengxh
	 * @param moId
	 * @return
	 */
	public List<MOOracleRollSEGBean> queryAllOrclRollback(String moId);

	/**
	 * 查询回滚段指标信息
	 * 
	 * @author zhengxh
	 * @param map
	 * @return
	 */
	public List<PerfOrclRollbackBean> queryOrclRollBackPerf(Map map);

	/**
	 * 获取所有回滚段监测对象
	 * 
	 * @return
	 */
	List<MOOracleRollSEGBean> getOrclRollSEGList(Map map);

	/**
	 * 获取回滚段监测对象详细信息
	 * 
	 * @return
	 */
	MOOracleRollSEGBean getOrclRollSEGDetail(Integer moID);

	/**
	 * 获取数据文件性能
	 * 
	 * @param map
	 * @return
	 */
	List<PerfOrclDataFileBean> queryOrclDataFilePerf(Map map);

	/**
	 * 获取数据文件性能
	 * 
	 * @param map
	 * @return
	 */
	List<PerfOrclSGABean> queryOrclSGAPerf(Map map);

	MODBMSServerBean selectMoDbmsByPrimaryKey(Integer moId);

	/**
	 * 获得数据库服务列表
	 * 
	 * @param page
	 * @return
	 */
	List<MODBMSServerBean> getDBMSServerList(Page<MODBMSServerBean> page);

	MODBMSServerBean getDBMSServerInfo(Integer moId);

	int getDBMSServerByIp(MODBMSServerBean bean);

	MODBMSServerBean getDBMSServerByIpAndType(MODBMSServerBean bean);
	MODBMSServerBean getDBMSServerByIpAndTypeAlias(MODBMSServerBean bean);

	/**
	 * 获取ORACLE数据库信息列表
	 * 
	 * @return
	 */
	List<OracleDbInfoBean> getOracleDB(Page<OracleDbInfoBean> page);

	/**
	 * 获取所有oracle数据文件
	 */
	List<MOOracleDataFileBean> getAllOracleDataFiles(
			Page<MOOracleDataFileBean> page);

	/**
	 * 获取Oracle表空间个数
	 */
	List<MODBMSServerBean> getOracleTbsCount();

	/**
	 * 查询所有相关表空间列表信息
	 */
	List<MOOracleTBSBean> getAllOrclTbsInfo(Page<MOOracleTBSBean> page);

	/**
	 * 获取所有oracle回滚段信息
	 * 
	 * @param map
	 * @return
	 */
	List<MOOracleRollSEGBean> getAllOrclRollSEGList(
			Page<MOOracleRollSEGBean> page);

	/**
	 * 获取所有数据库实例
	 * 
	 * @param MOID
	 * @return
	 */
	List<MODBMSServerBean> getOrclInstanceList(Page<MODBMSServerBean> page);

	/**
	 * 获取所有SGA列表
	 * 
	 * @param page
	 * @return
	 */
	List<MOOracleSGABean> getOrclSGAList(Page<MOOracleSGABean> page);

	/**
	 * 根据文件ID获取数据文件
	 * 
	 * @return
	 */
	MOOracleDataFileBean getDataFileByMoId(Integer moId);

	/**
	 * 根据SGA的id获得SGA详情
	 * 
	 * @param moID
	 * @return
	 */
	MOOracleSGABean getOrclSGAByMoID(int moID);

	/**
	 * 根据ID获取oracle数据库信息
	 * 
	 * @return
	 */
	OracleDbInfoBean getOracleDbByMoId(int moId);

	/**
	 * 根据ID获取数据库实例信息
	 * 
	 * @return
	 */
	MODBMSServerBean getOrclInstanceByMoid(Integer moId);
	
	int updateDBMSServerMOClassID(MODBMSServerBean bean);
	
	Integer getInsIdBymoId(int moId);
	 List<MODBMSServerBean> queryAll();
	 Date getCurrentDate();
}
