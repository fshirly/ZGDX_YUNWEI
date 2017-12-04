package com.fable.insightview.monitor.database.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MOSybaseDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOSybaseDeviceBean;
import com.fable.insightview.monitor.database.entity.PerfSybaseDatabaseBean;
import com.fable.insightview.monitor.database.entity.PerfSybaseServerBean;
import com.fable.insightview.monitor.database.mapper.SyBaseMapper;
import com.fable.insightview.monitor.database.service.ISyBaseService;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.platform.page.Page;

@Service
public class SyBaseServiceImpl implements ISyBaseService {

	@Autowired
	SyBaseMapper sybaseMapper;
	
	@Override
	public MODBMSServerBean getOrclInstanceDetail(Map map) {
		MODBMSServerBean modbserv = sybaseMapper.getOrclInstanceDetail(map);
		try {
			if (modbserv != null) {
				if (KPINameDef.up == modbserv.getPerfValueAvai()) {
					modbserv.setOperstatus("up.png");
					modbserv.setOperaTip("UP");
				} else if (KPINameDef.down == modbserv.getPerfValueAvai()) {
					modbserv.setOperstatus("down.png");
					modbserv.setOperaTip("DOWN");
				} else {
					modbserv.setOperstatus("unknown.png");
					modbserv.setOperaTip("未知");
				}
				if (0 == modbserv.getAlarmlevel()
						|| "".equals(modbserv.getAlarmlevel())
						|| modbserv.getAlarmlevel() == null) {
					modbserv.setLevelicon("right.png");
					modbserv.setAlarmLevelName("正常");
				}
			} else {
				modbserv = new MODBMSServerBean();
				modbserv.setOperstatus("unknown.png");
				modbserv.setOperaTip("未知");
				modbserv.setLevelicon("5.png");
				modbserv.setAlarmLevelName("未知");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return modbserv;
	}

	@Override
	public List<MOMySQLDBServerBean> queryMOSybaseServer(
			Page<MOMySQLDBServerBean> page) {
		return sybaseMapper.queryMOSybaseServer(page);
	}

	@Override
	public List<MOSybaseDeviceBean> queryMOSybaseDevice(
			Page<MOSybaseDeviceBean> page) {
		List<MOSybaseDeviceBean> deviceLst =sybaseMapper.queryMOSybaseDevice(page);
		if(deviceLst!=null){
			for (MOSybaseDeviceBean mo : deviceLst) {
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
	public List<MOSybaseDatabaseBean> queryMOSybaseDatabase(
			Page<MOSybaseDatabaseBean> page) {
		List<MOSybaseDatabaseBean> dbLst=sybaseMapper.queryMOSybaseDatabase(page);
		if(dbLst!=null){
			for (MOSybaseDatabaseBean mo : dbLst) {
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
	public List<PerfSybaseServerBean> querySybaseServerPerf(Map map) {
		return sybaseMapper.querySybaseServerPerf(map);
	}

	@Override
	public PerfSybaseServerBean getPerfValue(Map map) {
		return sybaseMapper.getPerfValue(map);
	}

	@Override
	public PerfSybaseDatabaseBean getDatabasefPerfValue(Map map) {
		return sybaseMapper.getDatabasefPerfValue(map);
	}

	@Override
	public PerfSybaseServerBean getDatabaseDetail(Map map) {
		PerfSybaseServerBean servBean=sybaseMapper.getDatabaseDetail(map);
		if(servBean!=null){
			servBean.setCpuBusy(HostComm.getMsToTime(Long.parseLong(servBean.getCpuBusy())));
			servBean.setCpuIdle(HostComm.getMsToTime(Long.parseLong(servBean.getCpuIdle())));
		}
		return servBean;
	}

	@Override
	public MODBMSServerBean getSybaseChartAvailability(Map map) {
		return sybaseMapper.getSybaseChartAvailability(map);
	}

	@Override
	public MOMySQLDBServerBean getFirstBean() {
		return sybaseMapper.getFirstBean();
	}

	@Override
	public MOSybaseDatabaseBean getFirstSybaseDbBean() {
		return sybaseMapper.getFirstSybaseDbBean();
	}

	@Override
	public MOSybaseDatabaseBean getSybaseDbById(Integer moId) {
		return sybaseMapper.getSybaseDbById(moId);
	}

	@Override
	public MOMySQLDBServerBean getSybaseServerById(Integer moId) {
		return sybaseMapper.getSybaseServerById(moId);
	}
	
	@Override
	public MOMySQLDBServerBean findSyBaseServerInfo(int moId) {
		return sybaseMapper.findSyBaseServerInfo(moId);
	}

	@Override
	public MOSybaseDeviceBean findSybaseDeviceInfo(int moId) {
		return sybaseMapper.findSybaseDeviceInfo(moId);
	}

	@Override
	public MOSybaseDatabaseBean findSybaseDatabase(int moId) {
		return sybaseMapper.findSybaseDatabase(moId);
	}

}
