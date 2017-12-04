package com.fable.insightview.monitor.database.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.database.entity.Db2InfoBean;
import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MoDb2BufferPoolBean;
import com.fable.insightview.monitor.database.entity.MoDbTabsBean;
import com.fable.insightview.monitor.database.entity.PerfDB2BufferPoolBean;
import com.fable.insightview.monitor.database.entity.PerfDB2DatabaseBean;
import com.fable.insightview.monitor.database.entity.PerfDB2InstanceBean;
import com.fable.insightview.monitor.database.entity.PerfDB2TbsBean;
import com.fable.insightview.monitor.database.entity.PerfSnapshotDatabaseBean;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.platform.page.Page;

public interface Db2Mapper {
	/**
	 * 获取所有数据库实例
	 * 
	 * @param MOID
	 * @return
	 */
	List<MODBMSServerBean> getDb2InstanceList(Page<MODBMSServerBean> page);
	
	List<Db2InfoBean> getDb2InfoList(Page<Db2InfoBean> page);

	List<MoDbTabsBean> getDb2TabsList(Page<MoDbTabsBean> page);

	List<MoDb2BufferPoolBean> getDb2BufferPoolList(
			Page<MoDb2BufferPoolBean> page);

	MODBMSServerBean getDb2InstanceByMoId(Integer moid);

	Db2InfoBean getDb2DatabseByMoId(Integer moId);

	MoDb2BufferPoolBean getDb2BufferPoolByMoId(int moId);

	MoDbTabsBean getDb2TabsByMoId(int moId);
	
	MODBMSServerBean getMODBMSServerByMoId(Map map);
	
	List<PerfDB2InstanceBean> queryInstancePerf(Map map);
	
	PerfDB2InstanceBean queryInsPerfLastest(Map map);
	
	List<PerfDB2TbsBean> getDb2TBUsage(Map map);
	
	List<PerfDB2BufferPoolBean> getBufferPoolByDbMoId(Integer dbMoId);
	
	List<Db2InfoBean> getDb2InfoByInstanceMoId(Integer instanceMOID);
	
	List<PerfDB2TbsBean> getDb2TabsByDbMoId(Integer dbMoId);
	
	List<PerfDB2DatabaseBean> queryDatabasePerf(Map map);
	
	List<PerfDB2DatabaseBean> queryDbPerfByInstanceMoId(Map map);
	
	PerfDB2BufferPoolBean getPerfBufferPool(Map map);
	
	List<PerfDB2BufferPoolBean> queryBufferPoolPerf(Map map);
	
	List<PerfDB2BufferPoolBean> queryBufferPoolBarPerf(Map map);
	
	PerfSnapshotDatabaseBean queryProxyInfo(Map<String,Object>  map);
	
	MODBMSServerBean getFirstDb2InsInfo();
	
	Db2InfoBean getFirstDb2dbInfo();
	
	/**
	 * 获取 实例可用率 图表
	 * 
	 * @return
	 */
	MODBMSServerBean getDb2ChartAvailability(Map map);
	
	List<AlarmActiveDetail> getTbsAlarmInfo(Map map);
	
	List<Db2InfoBean> getDataBaseByserver(int moId);
	
	List<Db2InfoBean> getDB2DatabaseByMOID(int moId);
	
	List<MODBMSServerBean> getDb2InsList(Map map);
	
	List<Db2InfoBean> getDb2DBInfoList(Map map);
	
	List<Integer> getDB2AlarmSourceId(@Param("dbmsMoId")int dbmsMoId);
}
