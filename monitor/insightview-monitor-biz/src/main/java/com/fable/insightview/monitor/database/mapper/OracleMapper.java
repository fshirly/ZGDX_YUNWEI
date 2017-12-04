package com.fable.insightview.monitor.database.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOOracleDataFileBean;
import com.fable.insightview.monitor.database.entity.MOOracleTBSBean;
import com.fable.insightview.monitor.database.entity.OracleDbInfoBean;
import com.fable.insightview.monitor.database.entity.PerfOrclDataFileBean;
import com.fable.insightview.monitor.database.entity.PerfOrclTBsBean;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.platform.page.Page;

public interface OracleMapper {
	// 根据实例ID查询实例详情
	MODBMSServerBean getOrclInstanceDetail(Map map);

	// 获取表空间列表 FOR 表空间个数
	List<MODBMSServerBean> getTbsCount(Map map);

	// 根据实例ID查询相关表空间列表信息
	List<MODBMSServerBean> getOrclTbsInfo(Map map);

	/**
	 * 根据表空间ID获取详细信息
	 * 
	 * @return
	 */
	MOOracleTBSBean getTbsDetailByMoId(Integer moId);

	// 表空间活动告警
	List<AlarmActiveDetail> getTbsAlarmInfo(Map map);

	/**
	 * 获取 实例可用率 图表
	 * 
	 * @return
	 */
	MODBMSServerBean getOrclChartAvailability(Map map);

	/**
	 * 表空间曲线 表名称
	 * 
	 * @param MOID
	 * @return
	 */
	List<MOOracleTBSBean> queryAllOrclTbs(@Param("MOID") String MOID);

	/**
	 * 获取表空间曲线数据
	 * 
	 * @param map
	 * @return
	 */
	List<PerfOrclTBsBean> queryOrclTbsPerf(Map map);

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
	 * 分页查询
	 * 
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	public List<MODBMSServerBean> queryList(Page<MODBMSServerBean> page);

	/**
	 * 获取数据文件性能
	 * 
	 * @param map
	 * @return
	 */
	List<PerfOrclDataFileBean> queryOrclDataFilePerf(Map map);

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
	 * 获取所有数据库实例
	 * 
	 * @param MOID
	 * @return
	 */
	List<MODBMSServerBean> getOrclInstanceList(Page<MODBMSServerBean> page);

	/**
	 * 根据文件ID获取数据文件
	 * 
	 * @return
	 */
	MOOracleDataFileBean getDataFileByMoId(Integer moId);
	
	/**
	 * 根据数据库类型获取一条数据库信息
	 * @param dbmstype
	 * @return
	 */
	MODBMSServerBean getFirstDbInfo(String dbmstype);

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
    
    List<Integer> getInstanceIDsByMoID(int moId);
    
    List<MODBMSServerBean> getOrclInsList(Map map);
    
    int getDbmsMoIdByOrclIns(@Param("moId")int moId);
    
    List<Integer> getOrclAlarmSourceId(@Param("dbmsMoId")int dbmsMoId);
}