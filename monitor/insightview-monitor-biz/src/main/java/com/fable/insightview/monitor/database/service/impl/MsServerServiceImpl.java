package com.fable.insightview.monitor.database.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDeviceBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.PerfMSSQLDatabaseBean;
import com.fable.insightview.monitor.database.entity.PerfMSSQLServerBean;
import com.fable.insightview.monitor.database.mapper.MsServerMapper;
import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.database.service.IMsServerService;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.page.Page;

@Service
public class MsServerServiceImpl implements IMsServerService {

	@Autowired
	MsServerMapper msMapper;
	@Autowired
	OracleMapper orclMapper;
	@Autowired 
	WebSiteMapper webSiteMapper;
	
	public int getMultiple(){
		return webSiteMapper.getConfParamValue();
	}
	
	@Override
	public MODBMSServerBean getMsServerInfo(Map map) {
		MODBMSServerBean mo = msMapper.getMsServerInfo(map);
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
						if (KPINameDef.up==mo.getPerfValueAvai()) {
							mo.setOperstatus("up.png");
							mo.setOperaTip("UP");
						} else if (KPINameDef.down==mo.getPerfValueAvai()) {
							mo.setOperstatus("down.png");
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
						mo.setOperstatus("unknown.png");
						mo.setOperaTip("未知");
						String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
						mo.setDurationTime(durationTime);
					}
				}else{
					mo.setOperstatus("unknown.png");
					mo.setOperaTip("未知");
					mo.setDurationTime("");
				}
				if ("null".equals(mo.getAlarmlevel()) || "".equals(mo.getAlarmlevel())
						|| mo.getAlarmlevel() == null || 0==mo.getAlarmlevel() || "0".equals(mo.getAlarmlevel())  ) {
					mo.setLevelicon("right.png");
					mo.setAlarmLevelName("正常");
				}
				if("".equals(mo.getMoalias())||"null".equals(mo.getMoalias())||mo.getMoalias()==null){
					mo.setMoalias("");
				}else{
					mo.setMoalias("("+mo.getMoalias()+")");
				}	
		}else{
			mo = new MODBMSServerBean();
			mo.setOperstatus("unknown.png");
			mo.setOperaTip("未知");
			mo.setLevelicon("5.png");
			mo.setAlarmLevelName("未知");
			mo.setDurationTime("");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mo;
	}

	@Override
	public List<MOMySQLDBServerBean> queryMOMsSQLServer(
			Page<MOMySQLDBServerBean> page) {
		return msMapper.queryMOMsSQLServer(page);
	}

	@Override
	public List<MOMsSQLDeviceBean> queryMOMsSQLDevice(
			Page<MOMsSQLDeviceBean> page) {
		List<MOMsSQLDeviceBean> deviceLst = msMapper.queryMOMsSQLDevice(page);
		if (deviceLst != null) {
			for (MOMsSQLDeviceBean mo : deviceLst) {
				if (mo.getTotalSize() != null && !"".equals(mo.getTotalSize())) {
					mo.setTotalSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getTotalSize())));
				} else {
					mo.setTotalSize("0KB");
				}
				if (mo.getUsedSize() != null && !"".equals(mo.getUsedSize())) {
					mo.setUsedSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getUsedSize())));
				} else {
					mo.setUsedSize("0KB");
				}
				if (mo.getFreeSize() != null && !"".equals(mo.getFreeSize())) {
					mo.setFreeSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getFreeSize())));
				} else {
					mo.setFreeSize("0KB");
				}
			}
		}

		return deviceLst;
	}

	@Override
	public List<MOMsSQLDatabaseBean> queryMOMsSQLDatabase(
			Page<MOMsSQLDatabaseBean> page) {
		List<MOMsSQLDatabaseBean> dbLst = msMapper.queryMOMsSQLDatabase(page);
		if (dbLst != null) {
			for (MOMsSQLDatabaseBean mo : dbLst) {
				if (mo.getDataSize() != null && !"".equals(mo.getDataSize())) {
					mo.setDataSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getDataSize())));
				} else {
					mo.setDataSize("0KB");
				}
				if (mo.getLogSize() != null && !"".equals(mo.getLogSize())) {
					mo.setLogSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getLogSize())));
				} else {
					mo.setLogSize("0KB");
				}
				if (mo.getTotalSize() != null && !"".equals(mo.getTotalSize())) {
					mo.setTotalSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getTotalSize())));
				} else {
					mo.setTotalSize("0KB");
				}
				if (mo.getUsedSize() != null && !"".equals(mo.getUsedSize())) {
					mo.setUsedSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getUsedSize())));
				} else {
					mo.setUsedSize("0KB");
				}
				if (mo.getFreeSize() != null && !"".equals(mo.getFreeSize())) {
					mo.setFreeSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getFreeSize())));
				} else {
					mo.setFreeSize("0KB");
				}
			}
		}
		return dbLst;
	}

	@Override
	public List<MOMsSQLDatabaseBean> getDBListInfo(Map map) {
		List<MOMsSQLDatabaseBean> dbLst = this.msMapper.getDBListInfo(map);
		if (dbLst != null) {
			for (MOMsSQLDatabaseBean mo : dbLst) {
				if (mo.getDataSize() != null && !"".equals(mo.getDataSize())) {
					mo.setDataSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getDataSize())));
				} else {
					mo.setDataSize("0KB");
				}
				if (mo.getLogSize() != null && !"".equals(mo.getLogSize())) {
					mo.setLogSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getLogSize())));
				} else {
					mo.setLogSize("0KB");
				}
				if (mo.getTotalSize() != null && !"".equals(mo.getTotalSize())) {
					mo.setTotalSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getTotalSize())));
				} else {
					mo.setTotalSize("0KB");
				}
				if (mo.getUsedSize() != null && !"".equals(mo.getUsedSize())) {
					mo.setUsedSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getUsedSize())));
				} else {
					mo.setUsedSize("0KB");
				}
				if (mo.getPageSize() != null && !"".equals(mo.getPageSize())) {
					mo.setPageSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getPageSize())));
				} else {
					mo.setPageSize("0KB");
				}
				if (mo.getFreeSize() != null && !"".equals(mo.getFreeSize())) {
					mo.setFreeSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getFreeSize())));
				} else {
					mo.setFreeSize("0KB");
				}
				if (mo.getSpaceUsage() != null
						&& !"".equals(mo.getSpaceUsage())) {
					mo.setSpaceUsage(mo.getSpaceUsage() + "%");
				} else {
					mo.setSpaceUsage("0%");
				}

			}
		}
		return dbLst;
	}

	@Override
	public List<MOMsSQLDeviceBean> getDeviceListInfo(Map map) {
		List<MOMsSQLDeviceBean> deviceLst = msMapper.getDeviceListInfo(map);
		if (deviceLst != null) {
			for (MOMsSQLDeviceBean mo : deviceLst) {
				if (mo.getTotalSize() != null && !"".equals(mo.getTotalSize())) {
					mo.setTotalSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getTotalSize())));
				} else {
					mo.setTotalSize("0KB");
				}
				if (mo.getUsedSize() != null && !"".equals(mo.getUsedSize())) {
					mo.setUsedSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getUsedSize())));
				} else {
					mo.setUsedSize("0KB");
				}
				if (mo.getFreeSize() != null && !"".equals(mo.getFreeSize())) {
					mo.setFreeSize(HostComm.getBytesToSize(Long.parseLong(mo
							.getFreeSize())));
				} else {
					mo.setFreeSize("0KB");
				}
			}
		}
		return deviceLst;
	}

	@Override
	public List<PerfMSSQLServerBean> queryMSSQLServerPerf(Map map) {
		return msMapper.queryMSSQLServerPerf(map);
	}

	@Override
	public PerfMSSQLServerBean getDatabaseDetail(Map map) {
		return msMapper.getDatabaseDetail(map);
	}

	@Override
	public MODBMSServerBean getMsChartAvailability(Map map) {
		return msMapper.getMsChartAvailability(map);
	}

	@Override
	public PerfMSSQLServerBean getPerfValue(Map map) {
		return msMapper.getPerfValue(map);
	}

	@Override
	public List<PerfMSSQLDatabaseBean> queryMSSQLDatabasePerf(Map map) {
		return msMapper.queryMSSQLDatabasePerf(map);
	}

	@Override
	public PerfMSSQLDatabaseBean getLogPerfValue(Map map) {
		return msMapper.getLogPerfValue(map);
	}

	@Override
	public MOMsSQLDatabaseBean getDBDetailInfo(Map map) {
		MOMsSQLDatabaseBean mo = msMapper.getDBDetailInfo(map);
		if (mo != null) {
			mo.setPageSize(HostComm.getBytesToSize(Long.parseLong(mo
					.getPageSize())));
			mo.setTotalSize(HostComm.getBytesToSize(Long.parseLong(mo
					.getTotalSize())));
			mo.setUsedSize(HostComm.getBytesToSize(Long.parseLong(mo
					.getUsedSize())));
			mo.setFreeSize(HostComm.getBytesToSize(Long.parseLong(mo
					.getFreeSize())));
			mo.setSpaceUsage(mo.getSpaceUsage() + "%");
		}
		return mo;
	}

	@Override
	public MOMySQLDBServerBean getFirstBean() {
		return msMapper.getFirstBean();
	}

	@Override
	public MOMsSQLDatabaseBean getFirstMsDbBean() {
		return msMapper.getFirstMsDbBean();
	}

	@Override
	public MOMsSQLDatabaseBean getMsDbById(Integer moId) {
		return msMapper.getMsDbById(moId);
	}

	@Override
	public MOMySQLDBServerBean getMsServerById(Integer moId) {
		return msMapper.getMsServerById(moId);
	}

@Override
	public MOMySQLDBServerBean findMsSqlServerInfo(int moId) {
		return msMapper.findMsSqlServerInfo(moId);
	}

	@Override
	public MOMsSQLDeviceBean findMsDeviceInfo(int moId) {
		return msMapper.findMsDeviceInfo(moId);
	}

	@Override
	public MOMsSQLDatabaseBean findMsSQLDatabaseInfo(int moId) {
		return msMapper.findMsSQLDatabaseInfo(moId);
	}
}
