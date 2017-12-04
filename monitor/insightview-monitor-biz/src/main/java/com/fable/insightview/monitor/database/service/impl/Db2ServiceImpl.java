package com.fable.insightview.monitor.database.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.database.entity.Db2InfoBean;
import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MoDb2BufferPoolBean;
import com.fable.insightview.monitor.database.entity.MoDbTabsBean;
import com.fable.insightview.monitor.database.entity.PerfDB2BufferPoolBean;
import com.fable.insightview.monitor.database.entity.PerfDB2DatabaseBean;
import com.fable.insightview.monitor.database.entity.PerfDB2InstanceBean;
import com.fable.insightview.monitor.database.entity.PerfDB2TbsBean;
import com.fable.insightview.monitor.database.entity.PerfSnapshotDatabaseBean;
import com.fable.insightview.monitor.database.mapper.Db2Mapper;
import com.fable.insightview.monitor.database.service.IDb2Service;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.platform.page.Page;

@Service
public class Db2ServiceImpl implements IDb2Service {

	@Autowired
	Db2Mapper db2Mapper;
	HostComm hostComm = new HostComm();
	
	@Override
	public List<MODBMSServerBean> getDb2InstanceList(Page<MODBMSServerBean> page) {
		return db2Mapper.getDb2InstanceList(page);
	}

	@Override
	public List<Db2InfoBean> getDb2InfoList(Page<Db2InfoBean> page) {
		return db2Mapper.getDb2InfoList(page);
	}

	@Override
	public List<MoDbTabsBean> getDb2TabsList(Page<MoDbTabsBean> page) {
		List<MoDbTabsBean> moDbTsbsLst = db2Mapper.getDb2TabsList(page);
		for (int i = 0; i < moDbTsbsLst.size(); i++) {
			moDbTsbsLst.get(i).setFormatExtentSize(
					hostComm.getBytesToSize(moDbTsbsLst.get(i).getExtentSize()));
		}
		return moDbTsbsLst;
	}

	@Override
	public List<MoDb2BufferPoolBean> getDb2BufferPoolList(
			Page<MoDb2BufferPoolBean> page) {
		List<MoDb2BufferPoolBean> moDbBpLst = db2Mapper.getDb2BufferPoolList(page);
		for (int i = 0; i < moDbBpLst.size(); i++) {
			moDbBpLst.get(i).setFormatBufferSize(
					hostComm.getBytesToSize(moDbBpLst.get(i).getBufferSize()));
			moDbBpLst.get(i).setFormatPageSize(
					hostComm.getBytesToSize(moDbBpLst.get(i).getPageSize()));
		}
		return moDbBpLst;
	}
	@Override
	public MODBMSServerBean getMODBMSServerByMoId(Map map) {
		return db2Mapper.getMODBMSServerByMoId(map);
	}
	@Override
	public List<PerfDB2InstanceBean> queryInstancePerf(Map map) {
		return db2Mapper.queryInstancePerf(map);
	}
	@Override
	public PerfDB2InstanceBean queryInsPerfLastest(Map map) {
		return db2Mapper.queryInsPerfLastest(map);
	}
	

	@Override
	public MODBMSServerBean getDb2InstanceByMoId(Integer moid) {
		return db2Mapper.getDb2InstanceByMoId(moid);
	}

	@Override
	public Db2InfoBean getDb2DatabseByMoId(Integer moId) {
		return db2Mapper.getDb2DatabseByMoId(moId);
	}

	@Override
	public MoDb2BufferPoolBean getDb2BufferPoolByMoId(int moId) {
		return db2Mapper.getDb2BufferPoolByMoId(moId);
	}

	@Override
	public MoDbTabsBean getDb2TabsByMoId(int moId) {
		return db2Mapper.getDb2TabsByMoId(moId);
	}

	@Override
	public List<PerfDB2TbsBean> getDb2TBUsage(Map map) {
		return db2Mapper.getDb2TBUsage(map);
	}

	@Override
	public List<PerfDB2BufferPoolBean> getBufferPoolByDbMoId(Integer dbMoId) {
		
		List<PerfDB2BufferPoolBean> perfDB2BpLst= db2Mapper.getBufferPoolByDbMoId(dbMoId);
		for (int i = 0; i < perfDB2BpLst.size(); i++) {
			perfDB2BpLst.get(i).setFormatBufferSize(
					hostComm.getBytesToSize(perfDB2BpLst.get(i).getBufferSize()));
			perfDB2BpLst.get(i).setFormatPageSize(
					hostComm.getBytesToSize(perfDB2BpLst.get(i).getPageSize()));
		}
		return perfDB2BpLst;
	}

	@Override
	public List<Db2InfoBean> getDb2InfoByInstanceMoId(Integer instanceMOID) {
		return db2Mapper.getDb2InfoByInstanceMoId(instanceMOID);
	}

	@Override
	public List<PerfDB2TbsBean> getDb2TabsByDbMoId(Integer dbMoId) {
		List<PerfDB2TbsBean> perfDB2TbsLst = db2Mapper.getDb2TabsByDbMoId(dbMoId);
		for (int i = 0; i < perfDB2TbsLst.size(); i++) {
			perfDB2TbsLst.get(i).setFormatTotalSize(
					hostComm.getBytesToSize(perfDB2TbsLst.get(i).getTotalSize()));
			perfDB2TbsLst.get(i).setFormatUsedSize(
					hostComm.getBytesToSize(perfDB2TbsLst.get(i).getUsedSize()));
		}
		return perfDB2TbsLst;
	}

	@Override
	public List<PerfDB2DatabaseBean> queryDatabasePerf(Map map) {
		return db2Mapper.queryDatabasePerf(map);
	}

	@Override
	public List<PerfDB2DatabaseBean> queryDbPerfByInstanceMoId(Map map) {
		return db2Mapper.queryDbPerfByInstanceMoId(map);
	}

	@Override
	public PerfDB2BufferPoolBean getPerfBufferPool(Map map) {
		return db2Mapper.getPerfBufferPool(map);
	}

	@Override
	public List<PerfDB2BufferPoolBean> queryBufferPoolPerf(Map map) {
		return db2Mapper.queryBufferPoolPerf(map);
	}

	@Override
	public List<PerfDB2BufferPoolBean> queryBufferPoolBarPerf(Map map) {
		return db2Mapper.queryBufferPoolBarPerf(map);
	}

	@Override
	public PerfSnapshotDatabaseBean queryProxyInfo(Map map) {
		return db2Mapper.queryProxyInfo(map);
	}

	@Override
	public MODBMSServerBean getFirstDb2InsInfo() {
		return db2Mapper.getFirstDb2InsInfo();
	}

	@Override
	public Db2InfoBean getFirstDb2dbInfo() {
		return db2Mapper.getFirstDb2dbInfo();
	}

	@Override
	public MODBMSServerBean getDb2ChartAvailability(Map map) {
		return db2Mapper.getDb2ChartAvailability(map);
	}

	@Override
	public List<AlarmActiveDetail> getTbsAlarmInfo(Map map) {
		return db2Mapper.getTbsAlarmInfo(map);
	}

}
