package com.fable.insightview.monitor.discover.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fable.insightview.monitor.alarmmgr.entity.MOKPIThresholdBean;
import com.fable.insightview.monitor.alarmmgr.mokpithreshold.mapper.MOKPIThresholdMapper;
import com.fable.insightview.monitor.database.entity.Db2InfoBean;
import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDeviceBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLRunObjectsBean;
import com.fable.insightview.monitor.database.entity.MOMySQLVarsBean;
import com.fable.insightview.monitor.database.entity.MOOracleDataFileBean;
import com.fable.insightview.monitor.database.entity.MOOracleRollSEGBean;
import com.fable.insightview.monitor.database.entity.MOOracleSGABean;
import com.fable.insightview.monitor.database.entity.MOOracleTBSBean;
import com.fable.insightview.monitor.database.entity.MOSybaseDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOSybaseDeviceBean;
import com.fable.insightview.monitor.database.entity.MoDb2BufferPoolBean;
import com.fable.insightview.monitor.database.entity.MoDbTabsBean;
import com.fable.insightview.monitor.database.mapper.Db2Mapper;
import com.fable.insightview.monitor.database.mapper.MsServerMapper;
import com.fable.insightview.monitor.database.mapper.MySQLMapper;
import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.database.mapper.SyBaseMapper;
import com.fable.insightview.monitor.database.service.IDb2Service;
import com.fable.insightview.monitor.database.service.IMsServerService;
import com.fable.insightview.monitor.database.service.IMyService;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.database.service.ISyBaseService;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.environmentmonitor.entity.MOReader;
import com.fable.insightview.monitor.environmentmonitor.entity.MOTag;
import com.fable.insightview.monitor.environmentmonitor.entity.MOZoneManagerBean;
import com.fable.insightview.monitor.environmentmonitor.mapper.EnvMonitorMapper;
import com.fable.insightview.monitor.environmentmonitor.service.IEnvMonitorService;
import com.fable.insightview.monitor.environmentmonitor.service.IZoneManagerService;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMSBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJTABean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJVMBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcDSBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareMemoryBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareServletBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareThreadPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareWebModuleBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatClassLoadBean;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareService;
import com.fable.insightview.monitor.middleware.tomcat.service.ITomcatService;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMiddleWareJ2eeAppBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.mocpus.entity.MOCPUsBean;
import com.fable.insightview.monitor.mocpus.mapper.MOCPUsMapper;
import com.fable.insightview.monitor.momemories.entity.MOMemoriesBean;
import com.fable.insightview.monitor.momemories.mapper.MOMemoriesMapper;
import com.fable.insightview.monitor.monetworkIif.entity.MONetworkIfBean;
import com.fable.insightview.monitor.monetworkIif.mapper.MONetworkIfMapper;
import com.fable.insightview.monitor.movolumes.entity.MOVolumesBean;
import com.fable.insightview.monitor.movolumes.mapper.MOVolumesMapper;
import com.fable.insightview.monitor.perf.entity.PerfKPIDefBean;
import com.fable.insightview.monitor.perf.mapper.PerfKPIDefMapper;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * 监测列表采集配置
 * 
 */
@Service
public class ConfigObjServiceImpl implements IConfigObjService {
	private final static Logger logger = LoggerFactory
			.getLogger(ConfigObjServiceImpl.class);
	@Autowired
	MODeviceMapper moDeviceMapper;
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	@Autowired
	MONetworkIfMapper moNetworkIfMapper;
	@Autowired
	MOVolumesMapper moVolumesMapper;
	@Autowired
	MOCPUsMapper mocpUsMapper;
	@Autowired
	MOMemoriesMapper moMemoriesMapper;
	@Autowired
	MOKPIThresholdMapper mokpiThresholdMapper;
	@Autowired
	IOracleService orclService;
	@Autowired
	IMyService myService;
	@Autowired
	ISyBaseService sybaseService;
	@Autowired
	IMsServerService msService;
	@Autowired
	IMiddlewareService imiddlewareService;
	@Autowired
	IZoneManagerService zoneManagerService;
	@Autowired
	IEnvMonitorService envMonitorService;
	@Autowired
	IDb2Service db2Service;
	@Autowired
	ITomcatService tomService;
	@Autowired
	WebSiteMapper webSiteMapper;
	@Autowired
	PerfKPIDefMapper perfKPIDefMapper;
	@Autowired 
	OracleMapper oracleMapper;
	@Autowired
	MySQLMapper mySQLMapper;
	@Autowired
	EnvMonitorMapper envMonitorMapper;
	@Autowired
	Db2Mapper db2Mapper;
	@Autowired
	SyBaseMapper syBaseMapper;
	@Autowired
	MsServerMapper msServerMapper;
	@Override
	public Map<String, Object> listThreshold(
			MOKPIThresholdBean mokpiThresholdBean) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOKPIThresholdBean> page = new Page<MOKPIThresholdBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int classId = mokpiThresholdBean.getClassID();

		paramMap.put("classIDs", classId);
		if (classId != 15 && classId != 16 && classId != 54 && classId != 81
				&& classId != 86 && classId != 44) {
			paramMap.put("sourceMOID", mokpiThresholdBean.getSourceMOID());
		}else{
			String sourceMoIds = getDBAndRoomSourceMOID(classId,mokpiThresholdBean.getSourceMOID());
			paramMap.put("sourceMoIds", sourceMoIds);
		}
		page.setParams(paramMap);
		List<MOKPIThresholdBean> thresholdList = mokpiThresholdMapper
				.getThreshold(page);
		// 源对象名称
		for (int i = 0; i < thresholdList.size(); i++) {
			MOKPIThresholdBean bean = thresholdList.get(i);
			this.setSourceName(bean);
		}
		// 管理对象名称
		for (int i = 0; i < thresholdList.size(); i++) {
			MOKPIThresholdBean bean = thresholdList.get(i);
			this.setMoName(bean);
		}

		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("rows", thresholdList);
		result.put("total", total);

		logger.info("加载数据结束。。。");
		return result;
	}
	
	/**
	 * 获得数据库、机房环境的源对象
	 * @param sourceMOID
	 */
	public String getDBAndRoomSourceMOID(int classId,int sourceMOID){
		List<Integer> idLst = new ArrayList<Integer>();
		String ids = "";
		if(classId == 15){
			idLst = oracleMapper.getInstanceIDsByMoID(sourceMOID);
		}else if(classId == 16){
			idLst = mySQLMapper.getMySQLDBServerIDByMOID(sourceMOID);
		}else if(classId == 44){
			idLst = envMonitorMapper.getReadIDByMOID(sourceMOID);
		}else if(classId == 54){
			List<Db2InfoBean> db2InfoLst = db2Mapper.getDB2DatabaseByMOID(sourceMOID);
			for (int i = 0; i < db2InfoLst.size(); i++) {
				ids += db2InfoLst.get(i).getMoId() + "," + db2InfoLst.get(i).getInstanceMOID() + ",";
			}
		}else if(classId == 81){
			idLst = syBaseMapper.getServerIDByMOID(sourceMOID);
		}else if(classId == 86){
			idLst = msServerMapper.getServerIDByMOID(sourceMOID);
		}
		if(idLst != null){
			for (int i = 0; i < idLst.size(); i++) {
				ids += idLst.get(i) + ",";
			}
		}
		if(!"".equals(ids) && ids != null){
			ids = ids.substring(0, ids.lastIndexOf(","));
		}
		return ids;
	}

	/**
	 * 设置源对象名称
	 */
	private void setSourceName(MOKPIThresholdBean bean) {
		String sourceMOName = null;
		int classID = bean.getClassID();
		int sourceMOID = bean.getSourceMOID();
		if (classID == 15 || classID == 22 || classID == 24 || classID == 25
				|| classID == 26 || classID == 27) {
			MODBMSServerBean instance = orclService
					.getOrclInstanceByMoid(sourceMOID);
			if (instance != null) {
				sourceMOName = instance.getInstancename();
			}
			bean.setParentClassID(15);
		} else if (classID == 16 || classID == 28 || classID == 29
				|| classID == 30 || classID == 31) {
			MOMySQLDBServerBean serverBean = myService
					.getMysqlServerByID(sourceMOID);
			if (serverBean != null) {
				sourceMOName = serverBean.getServerName();
			}
		} else if (classID == 81 || classID == 82 || classID == 83
				|| classID == 84) {
			MOMySQLDBServerBean serverBean = sybaseService
					.findSyBaseServerInfo(sourceMOID);
			if (serverBean != null) {
				sourceMOName = serverBean.getServerName();
			}
		} else if (classID == 86 || classID == 87 || classID == 88
				|| classID == 80) {
			MOMySQLDBServerBean serverBean = msService
					.findMsSqlServerInfo(sourceMOID);
			if (serverBean != null) {
				sourceMOName = serverBean.getServerName();
			}
		} else if (classID == 17 || classID == 18 || classID == 19
				|| classID == 20 || classID == 32 || classID == 33
				|| classID == 34 || classID == 35 || classID == 36
				|| classID == 37 || classID == 38 || classID == 39
				|| classID == 40 || classID == 41 || classID == 42
				|| classID == 43 || classID == 53) {
			MOMiddleWareJMXBean middleWareJMXBean = imiddlewareService
					.getMiddleWareInfo(sourceMOID);
			if (middleWareJMXBean != null) {
				sourceMOName = middleWareJMXBean.getIp();
			}
		} else if (classID == 44 || classID == 45 || classID == 46
				|| classID == 47 || classID == 48 || classID == 49
				|| classID == 50 || classID == 51 || classID == 52) {
			MOZoneManagerBean zoneManagerBean = zoneManagerService
					.getZoneManagerInfo(sourceMOID);
			if (zoneManagerBean != null) {
				sourceMOName = zoneManagerBean.getIpAddress();
			}
		} else if (classID == 54 || classID == 55 || classID == 56) {
			MODBMSServerBean db2Ins = db2Service
					.getDb2InstanceByMoId(sourceMOID);
			if (db2Ins != null) {
				sourceMOName = db2Ins.getInstancename();
			}
			bean.setParentClassID(54);
		} else if (classID == 57) {
			Db2InfoBean dbInfo = db2Service.getDb2DatabseByMoId(sourceMOID);
			if (dbInfo != null) {
				sourceMOName = dbInfo.getDatabaseName();
			}
		} else if (classID == 23) {
			Db2InfoBean dbInfo = db2Service.getDb2DatabseByMoId(sourceMOID);
			if (dbInfo != null) {
				sourceMOName = dbInfo.getDatabaseName();
				bean.setParentClassID(54);
			} else {
				MODBMSServerBean instance = orclService
						.getOrclInstanceByMoid(sourceMOID);
				if (instance != null) {
					sourceMOName = instance.getInstancename();
				}
				bean.setParentClassID(15);
			}
		} else {
			sourceMOName = bean.getSourceMOName();
		}
		if (bean.getSourceMOID() == -1) {
			sourceMOName = "所有";
		}
		bean.setSourceMOName(sourceMOName);
	}

	/**
	 * 设置管理对象名称
	 */
	private void setMoName(MOKPIThresholdBean bean) {
		int kpiID = bean.getKpiID();
		PerfKPIDefBean perfKPIDefBean = perfKPIDefMapper
				.getPerfKPIDefById(kpiID);
		int classID = -1;
		if (perfKPIDefBean != null) {
			classID = perfKPIDefBean.getClassID();
		}
		bean.setKpiClassID(classID);
		int moID = bean.getMoID();
		String moName = null;
		if (classID > 1 && classID < 10) {
			if (!"".equals(moID) && moID != -1) {
				MODeviceObj device = moDeviceMapper.selectByPrimaryKey(moID);
				if (device != null) {
					moName = device.getMoname();
				}
			}
		} else if (classID == 10) {
			if (!"".equals(moID) && moID != -1) {
				MONetworkIfBean networkIf = moNetworkIfMapper
						.getMONetworkIfById(moID);
				if (networkIf != null) {
					moName = networkIf.getIfName();
				}
			}
		} else if (classID == 11) {
			if (!"".equals(moID) && moID != -1) {
				MOVolumesBean volumes = moVolumesMapper.getVolumesById(moID);
				if (volumes != null) {

					moName = volumes.getMoName();
				}
			}
		} else if (classID == 12) {
			if (!"".equals(moID) && moID != -1) {
				MOCPUsBean cpu = mocpUsMapper.getMOCPUsId(moID);
				if (cpu != null) {
					moName = cpu.getMoName();
				}
			}
		} else if (classID == 13) {
			if (!"".equals(moID) && moID != -1) {
				MOMemoriesBean memories = moMemoriesMapper
						.getMOMemoriesById(moID);
				if (memories != null) {
					moName = memories.getMoName();
				}
			}
		} else if (classID == 23) {
			if (!"".equals(moID) && moID != -1) {
				MOOracleTBSBean tbsBean = orclService.getTbsDetailByMoId(moID);
				if (tbsBean != null) {
					moName = tbsBean.getTbsname();
				} else {
					MoDbTabsBean db2Tabs = db2Service.getDb2TabsByMoId(moID);
					if (db2Tabs != null) {
						moName = db2Tabs.getTbsName();
					}
				}

			}
		} else if (classID == 24) {
			if (!"".equals(moID) && moID != -1) {
				MOOracleRollSEGBean rollSEGBean = orclService
						.getOrclRollSEGDetail(moID);
				if (rollSEGBean != null) {
					moName = rollSEGBean.getSegName();
				}
			}
		} else if (classID == 25) {
			if (!"".equals(moID) && moID != -1) {
				MOOracleDataFileBean fileBean = orclService
						.getDataFileByMoId(moID);
				if (fileBean != null) {
					moName = fileBean.getDataFileName();
				}
			}
		} else if (classID == 27) {
			if (!"".equals(moID) && moID != -1) {
				MOOracleSGABean sgaBean = orclService.getOrclSGAByMoID(moID);
				if (sgaBean != null) {
					moName = sgaBean.getMoID() + "";
				}
			}
		} else if (classID == 30) {
			if (!"".equals(moID) && moID != -1) {
				MOMySQLRunObjectsBean runBean = myService
						.getMySQLRunObjByID(moID);
				if (runBean != null) {
					moName = runBean.getMoName();
				}
			}
		} else if (classID == 33) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareWebModuleBean webModuleBean = tomService
						.getWebModuleByID(moID);
				if (webModuleBean != null) {
					moName = webModuleBean.getWarName();
				}
			}
		} else if (classID == 34) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareServletBean servletBean = tomService
						.getServletByID(moID);
				if (servletBean != null) {
					moName = servletBean.getServletName();
				}
			}
		} else if (classID == 36) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareThreadPoolBean threadPoolBean = tomService
						.getThreadPoolByID(moID);
				if (threadPoolBean != null) {
					moName = threadPoolBean.getThreadName();
				}
			}
		} else if (classID == 37) {
			if (!"".equals(moID) && moID != -1) {
				PerfTomcatClassLoadBean classLoadBean = tomService
						.getClassLoadByID(moID);
				if (classLoadBean != null) {
					moName = classLoadBean.getServerName();
				}
			}
		} else if (classID == 38) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareJdbcPoolBean jdbcPoolBean = tomService
						.getJdbcPoolByID(moID);
				if (jdbcPoolBean != null) {
					moName = jdbcPoolBean.getDsName();
				}
			}
		} else if (classID == 40) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareJTABean jtaBean = tomService
						.getMiddleWareJTAByID(moID);
				if (jtaBean != null) {
					moName = jtaBean.getName();
				}
			}
		} else if (classID == 41) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareMemoryBean memoryBean = tomService
						.getMemPoolByID(moID);
				if (memoryBean != null) {
					moName = memoryBean.getMemName();
				}
			}
		} else if (classID == 42) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareJVMBean jvmBean = tomService
						.getMiddlewareJvmByID(moID);
				if (jvmBean != null) {
					moName = jvmBean.getJvmName();
				}
			}
		} else if (classID == 43) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareJdbcDSBean jdbcDSBean = tomService
						.getJdbcDSByID(moID);
				if (jdbcDSBean != null) {
					moName = jdbcDSBean.getdSName();
				}
			}
		} else if (classID == 29) {
			if (!"".equals(moID) && moID != -1) {
				MOMySQLDBBean sqldbBean = myService.getMysqlDBByID(moID);
				if (sqldbBean != null) {
					moName = sqldbBean.getDbName();
				}
			}
		} else if (classID == 31) {
			if (!"".equals(moID) && moID != -1) {
				MOMySQLVarsBean sqlVarsBean = myService.getMysqlVarsByID(moID);
				if (sqlVarsBean != null) {
					moName = sqlVarsBean.getVarName();
				}
			}
		} else if (classID == 32) {
			if (!"".equals(moID) && moID != -1) {
				MoMiddleWareJ2eeAppBean j2eeAppBean = tomService
						.getJ2eeAppByID(moID);
				if (j2eeAppBean != null) {
					moName = j2eeAppBean.getAppName();
				}
			}
		} else if (classID == 39) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareJMSBean jmsBean = tomService.getMiddleJMSByID(moID);
				if (jmsBean != null) {
					moName = jmsBean.getName();
				}
			}
		} else if (classID == 45) {
			if (!"".equals(moID) && moID != -1) {
				MOReader moReader = envMonitorService.getMoReaderByMOID(moID);
				if (moReader != null) {
					moName = moReader.getReaderLabel();
				}
			}
		} else if (classID == 46 || classID == 47 || classID == 48
				|| classID == 49 || classID == 50 || classID == 51
				|| classID == 52) {
			if (!"".equals(moID) && moID != -1) {
				MOTag moTag = envMonitorService.getMOTagBYMOID(moID);
				if (moTag != null) {
					moName = moTag.getTagGroupID();
				}
			}
		} else if (classID == 56) {
			if (!"".equals(moID) && moID != -1) {
				Db2InfoBean db2Info = db2Service.getDb2DatabseByMoId(moID);
				if (db2Info != null) {
					moName = db2Info.getDatabaseName();
				}
			}
		} else if (classID == 57) {
			if (!"".equals(moID) && moID != -1) {
				MoDb2BufferPoolBean db2Bufferpool = db2Service
						.getDb2BufferPoolByMoId(moID);
				if (db2Bufferpool != null) {
					moName = db2Bufferpool.getBufferPoolName();
				}
			}
		} else if (classID == 80) {
			if (!"".equals(moID) && moID != -1) {
				MOMsSQLDatabaseBean msSQLDatabaseBean = msService
						.findMsSQLDatabaseInfo(moID);
				if (msSQLDatabaseBean != null) {
					moName = msSQLDatabaseBean.getDatabaseName();
				}
			}
		} else if (classID == 83) {
			if (!"".equals(moID) && moID != -1) {
				MOSybaseDeviceBean sybaseDeviceBean = sybaseService
						.findSybaseDeviceInfo(moID);
				if (sybaseDeviceBean != null) {
					moName = sybaseDeviceBean.getDeviceName();
				}
			}
		} else if (classID == 84) {
			if (!"".equals(moID) && moID != -1) {
				MOSybaseDatabaseBean databaseBean = sybaseService
						.findSybaseDatabase(moID);
				if (databaseBean != null) {
					moName = databaseBean.getDatabaseName();
				}
			}
		} else if (classID == 88) {
			if (!"".equals(moID) && moID != -1) {
				MOMsSQLDeviceBean msSQLDeviceBean = msService
						.findMsDeviceInfo(moID);
				if (msSQLDeviceBean != null) {
					moName = msSQLDeviceBean.getDeviceName();
				}
			}
		}
		if (moID == -1) {
			moName = "所有";
		}
		bean.setMoName(moName);
	}

}
