package com.fable.insightview.monitor.database.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLRunObjectsBean;
import com.fable.insightview.monitor.database.entity.MOMySQLVarsBean;
import com.fable.insightview.monitor.database.entity.OracleKPINameDef;
import com.fable.insightview.monitor.database.entity.PerfMySQLConnectionBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLQueryCacheBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLTableCacheBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLTableLockBean;
import com.fable.insightview.monitor.database.entity.PerfMySQLTmpTableBean;
import com.fable.insightview.monitor.database.mapper.MySQLMapper;
import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.database.service.IMyService;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.page.Page;

@Service
public class MyServiceImpl implements IMyService {

	@Autowired
	MySQLMapper myMapper;
	@Autowired
	OracleMapper orclMapper;
	@Autowired 
	WebSiteMapper webSiteMapper;

	private final Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);
	
	public int getMultiple(){
		return webSiteMapper.getConfParamValue();
	}
	
	@Override
	public MOMySQLDBServerBean getMyServerInfo(Map map) {
		MOMySQLDBServerBean mo = myMapper.getMyServerInfo(map);
			int period=1;
			long curr=0;
			long currTime=orclMapper.getCurrentDate().getTime();
			try {
				if(mo!=null){
					Date updateAlarmTime = mo.getUpdateAlarmTime();
					Date collectTime=mo.getCollectTime();
					if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
						period=mo.getDefDoIntervals()*getMultiple()*60000;
					}else{
						period=mo.getDoIntervals()*getMultiple()*1000;
					}
					if (collectTime != null) {
						curr=currTime-mo.getCollectTime().getTime();
						if(curr<=period){
							if (KPINameDef.up==mo.getPerfValue()) {
								mo.setOperStatus("up.png");
								mo.setOperaTip("UP");
							} else if (KPINameDef.down==mo.getPerfValue()) {
								mo.setOperStatus("down.png");
								mo.setOperaTip("DOWN");
							}else{
								
							}
							if (updateAlarmTime != null) {
								String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
								mo.setDurationTime(durationTime);
							}else{
								mo.setDurationTime("");
							}
						}else{
							mo.setOperStatus("unknown.png");
							mo.setOperaTip("未知");
							String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
							mo.setDurationTime(durationTime);
						}
					}else{
						mo.setOperStatus("unknown.png");
						mo.setOperaTip("未知");
						mo.setDurationTime("");
					}
					if ("null".equals(mo.getAlarmLevel()) || "".equals(mo.getAlarmLevel())
							|| mo.getAlarmLevel() == null || 0==mo.getAlarmLevel() || "0".equals(mo.getAlarmLevel())  ) {
						mo.setLevelIcon("right.png");
						mo.setAlarmLevelName("正常");
					}
			}else{
				mo = new MOMySQLDBServerBean();
				mo.setOperStatus("unknown.png");
				mo.setOperaTip("未知");
				mo.setLevelIcon("5.png");
				mo.setAlarmLevelName("未知");
				mo.setDurationTime("");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mo;
	}	

	@Override
	public List<MOMySQLDBBean> getMyDB(Integer moID) {
		return myMapper.getMyDB(moID);
	}

	@Override
	public MOMySQLDBServerBean getMyChartAvailability(Map map) {
		return myMapper.getMyChartAvailability(map);
	}

	@Override
	public List<PerfMySQLConnectionBean> queryMyConnectionPerf(Map map) {
		return myMapper.queryMyConnectionPerf(map);
	}

	@Override
	public List<PerfMySQLTableCacheBean> queryMyTableCachePerf(Map map) {
		return myMapper.queryMyTableCachePerf(map);
	}

	@Override
	public List<PerfMySQLTmpTableBean> queryMyTmpPerf(Map map) {
		return myMapper.queryMyTmpPerf(map);
	}

	@Override
	public List<PerfMySQLQueryCacheBean> queryMyQueryCachePerf(Map map) {
		return myMapper.queryMyQueryCachePerf(map);
	}

	@Override
	public List<PerfMySQLTableLockBean> queryMyTableLockPerf(Map map) {
		return myMapper.queryMyTableLockPerf(map);
	}

	@Override
	public List<MOMySQLVarsBean> getMyVarsInfoByEq(Page<MOMySQLVarsBean> page) {
		return myMapper.getMyVarsInfoByEq(page);
	}

	@Override
	public List<MOMySQLVarsBean> getMyVarsInfoByLike(Page<MOMySQLVarsBean> page) {
		return myMapper.getMyVarsInfoByLike(page);
	}

	@Override
	public List<MOMySQLDBServerBean> queryMOMySQLDBServer(
			Page<MOMySQLDBServerBean> page) {
		return myMapper.queryMOMySQLDBServer(page);
	}

	@Override
	public List<MOMySQLDBBean> queryMOMySQLDB(Page<MOMySQLDBBean> page) {
		return myMapper.queryMOMySQLDB(page);
	}

	@Override
	public List<MOMySQLRunObjectsBean> queryMOMySQLRunObj(
			Page<MOMySQLRunObjectsBean> page) {
		return myMapper.queryMOMySQLRunObj(page);
	}

	@Override
	public List<MOMySQLVarsBean> queryMOMySQLVarsByEq(Page<MOMySQLVarsBean> page) {
		return myMapper.queryMOMySQLVarsByEq(page);
	}

	@Override
	public List<MOMySQLVarsBean> queryMOMySQLVarsByLike(
			Page<MOMySQLVarsBean> page) {
		return myMapper.queryMOMySQLVarsByLike(page);
	}

	@Override
	public MOMySQLRunObjectsBean getMySQLRunObjByID(int moId) {
		return myMapper.getMySQLRunObjByID(moId);
	}

	@Override
	public MOMySQLDBServerBean getMysqlServerByID(int moId) {
		return myMapper.getMysqlServerByID(moId);
	}

	@Override
	public MOMySQLDBBean getMysqlDBByID(int moId) {
		return myMapper.getMysqlDBByID(moId);
	}

	@Override
	public MOMySQLVarsBean getMysqlVarsByID(int moId) {
		return myMapper.getMysqlVarsByID(moId);
	}

	@Override
	public Integer getInsIdBymoId(int moId) {
		return myMapper.getInsIdBymoId(moId);
	}

}
