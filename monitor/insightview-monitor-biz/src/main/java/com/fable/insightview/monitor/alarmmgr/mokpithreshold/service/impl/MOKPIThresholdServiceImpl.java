package com.fable.insightview.monitor.alarmmgr.mokpithreshold.service.impl;

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
import com.fable.insightview.monitor.alarmmgr.mokpithreshold.service.IMOKPIThresholdService;
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
import com.fable.insightview.monitor.database.service.IDb2Service;
import com.fable.insightview.monitor.database.service.IMsServerService;
import com.fable.insightview.monitor.database.service.IMyService;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.database.service.ISyBaseService;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.environmentmonitor.entity.MOReader;
import com.fable.insightview.monitor.environmentmonitor.entity.MOTag;
import com.fable.insightview.monitor.environmentmonitor.service.IEnvMonitorService;
import com.fable.insightview.monitor.environmentmonitor.service.IZoneManagerService;
import com.fable.insightview.monitor.host.entity.HostComm;
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
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
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
import com.fable.insightview.monitor.website.entity.SiteDns;
import com.fable.insightview.monitor.website.entity.SiteFtp;
import com.fable.insightview.monitor.website.entity.SiteHttp;
import com.fable.insightview.monitor.website.entity.SitePort;
import com.fable.insightview.monitor.website.entity.WebSite;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Service
public class MOKPIThresholdServiceImpl implements IMOKPIThresholdService {
	@Autowired
	MOKPIThresholdMapper mokpiThresholdMapper;
	@Autowired
	IOracleService orclService;
	@Autowired
	IMiddlewareService middlewareService;
	@Autowired
	ITomcatService tomcatService;
	@Autowired
	IMyService myService;
	@Autowired
	MODeviceMapper moDeviceMapper;
	@Autowired
	MOVolumesMapper moVolumesMapper;
	@Autowired
	MOMemoriesMapper moMemoriesMapper;
	@Autowired
	MONetworkIfMapper moNetworkIfMapper;
	@Autowired
	MOCPUsMapper mocpUsMapper;
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	@Autowired
	PerfKPIDefMapper perfKPIDefMapper;
	@Autowired
	IZoneManagerService zoneManagerService;
	@Autowired
	IEnvMonitorService envMonitorService;
	@Autowired
	IDb2Service db2Service;
	@Autowired
	ISyBaseService sybaseService;
	@Autowired
	IMsServerService msService;
	@Autowired
	WebSiteMapper webSiteMapper;

	private Logger logger = LoggerFactory
			.getLogger(MOKPIThresholdServiceImpl.class);

	/**
	 * 查询列表数据
	 */
	@Override
	public List<MOKPIThresholdBean> selectThreshold(
			Page<MOKPIThresholdBean> page) {
		List<MOKPIThresholdBean> thresholdList = mokpiThresholdMapper
				.selectThreshold(page);
		// 源对象名称
		for (int i = 0; i < thresholdList.size(); i++) {
			setSourceName(thresholdList.get(i));
		}

		// 管理对象名称
		for (int i = 0; i < thresholdList.size(); i++) {
			setMoName(thresholdList.get(i));
		}
		return thresholdList;
	}

	/**
	 * 获得管理对象的名称
	 */
	@Override
	public void setMoName(MOKPIThresholdBean bean) {
		int kpiID = bean.getKpiID();
		PerfKPIDefBean perfKPIDefBean = perfKPIDefMapper.getPerfKPIDefById(kpiID);
		int classID = -1;
		if(perfKPIDefBean != null){
			classID = perfKPIDefBean.getClassID();
		}
		bean.setKpiClassID(classID);
		int moID = bean.getMoID();
		String moName = null;
		if (classID > 1 && classID < 10) {
			if (!"".equals(moID) && moID != -1) {
				com.fable.insightview.monitor.discover.entity.MODeviceObj device = moDeviceMapper
						.selectByPrimaryKey(moID);
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
					moName = sgaBean.getInstanceName();
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
				MOMiddleWareWebModuleBean webModuleBean = tomcatService
						.getWebModuleByID(moID);
				if (webModuleBean != null) {
					moName = webModuleBean.getWarName();
				}
			}
		} else if (classID == 34) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareServletBean servletBean = tomcatService
						.getServletByID(moID);
				if (servletBean != null) {
					moName = servletBean.getServletName();
				}
			}
		} else if (classID == 36) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareThreadPoolBean threadPoolBean = tomcatService
						.getThreadPoolByID(moID);
				if (threadPoolBean != null) {
					moName = threadPoolBean.getThreadName();
				}
			}
		} else if (classID == 37) {
			if (!"".equals(moID) && moID != -1) {
				PerfTomcatClassLoadBean classLoadBean = tomcatService
						.getClassLoadByID(moID);
				if (classLoadBean != null) {
					moName = classLoadBean.getServerName();
				}
			}
		} else if (classID == 38) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareJdbcPoolBean jdbcPoolBean = tomcatService
						.getJdbcPoolByID(moID);
				if (jdbcPoolBean != null) {
					moName = jdbcPoolBean.getDsName();
				}
			}
		} else if (classID == 40) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareJTABean jtaBean = tomcatService
						.getMiddleWareJTAByID(moID);
				if (jtaBean != null) {
					moName = jtaBean.getName();
				}
			}
		} else if (classID == 41) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareMemoryBean memoryBean = tomcatService
						.getMemPoolByID(moID);
				if (memoryBean != null) {
					moName = memoryBean.getMemName();
				}
			}
		} else if (classID == 42) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareJVMBean jvmBean = tomcatService
						.getMiddlewareJvmByID(moID);
				if (jvmBean != null) {
					moName = jvmBean.getJvmName();
				}
			}
		} else if (classID == 43) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareJdbcDSBean jdbcDSBean = tomcatService
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
				MoMiddleWareJ2eeAppBean j2eeAppBean = tomcatService
						.getJ2eeAppByID(moID);
				if (j2eeAppBean != null) {
					moName = j2eeAppBean.getAppName();
				}
			}
		} else if (classID == 39) {
			if (!"".equals(moID) && moID != -1) {
				MOMiddleWareJMSBean jmsBean = tomcatService
						.getMiddleJMSByID(moID);
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
					moName = moTag.getTagID();
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

	/**
	 * 获得源对象的名称
	 */
	@Override
	public void setSourceName(MOKPIThresholdBean bean) {
		String sourceMOName = null;
		int classID = bean.getClassID();
		int sourceMOID = bean.getSourceMOID();
		int kpiClassID = bean.getKpiClassID();
		if (classID == 15 || classID == 22 || classID == 24 || classID == 25
				|| classID == 26 || classID == 27) {
			MODBMSServerBean instance = orclService
					.getOrclInstanceByMoid(sourceMOID);
			if (instance != null) {
				sourceMOName = instance.getInstancename();
			}
		} else if (classID == 16 || classID == 28 || classID == 29
				|| classID == 30 || classID == 31) {
			MOMySQLDBServerBean serverBean = myService
					.getMysqlServerByID(sourceMOID);
			if (serverBean != null) {
				sourceMOName = serverBean.getIp();
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
			MOMiddleWareJMXBean middleWareJMXBean = middlewareService
					.getMiddleWareInfo(sourceMOID);
			if (middleWareJMXBean != null) {
				sourceMOName = middleWareJMXBean.getIp();
			}
		} else if (classID == 44 || classID == 45 || classID == 46
				|| classID == 47 || classID == 48 || classID == 49
				|| classID == 50 || classID == 51 || classID == 52) {
			MOReader moReader = envMonitorService.getMoReaderByMOID(sourceMOID);
			if (moReader != null) {
				sourceMOName = moReader.getReaderLabel();
			}
		} else if (classID == 54 || classID == 55 || classID == 56) {
			
			if(kpiClassID == 23 || kpiClassID == 57){
				Db2InfoBean db2Info = db2Service.getDb2DatabseByMoId(sourceMOID);
				if(db2Info != null){
					sourceMOName = db2Info.getDatabaseName();
				}
			}else{
				MODBMSServerBean db2Ins = db2Service
				.getDb2InstanceByMoId(sourceMOID);
				if (db2Ins != null) {
					sourceMOName = db2Ins.getInstancename();
				}
			}
		} else if (classID == 57) {
			Db2InfoBean dbInfo = db2Service.getDb2DatabseByMoId(sourceMOID);
			if (dbInfo != null) {
				sourceMOName = dbInfo.getDatabaseName();
			}
		} else if (classID == 23) {
			MODBMSServerBean instance = orclService
					.getOrclInstanceByMoid(sourceMOID);
			if (instance != null) {
				sourceMOName = instance.getInstancename();
				bean.setParentClassID(22);
			} else {
				Db2InfoBean dbInfo = db2Service.getDb2DatabseByMoId(sourceMOID);
				if (dbInfo != null) {
					sourceMOName = dbInfo.getDatabaseName();
					bean.setParentClassID(56);
				}
			}
		} else if (classID == 90 || classID == 91 || classID == 92
				|| classID == 93 || classID == 94) {
			sourceMOName = getSiteName(bean);
		} else {
			sourceMOName = bean.getSourceMOName();
		}
		if (bean.getSourceMOID() == -1) {
			sourceMOName = "所有";
		}
		bean.setSourceMOName(sourceMOName);
	}

	/**
	 * 删除
	 */
	@Override
	public boolean delThreshold(List<Integer> ruleIDs) {
		try {
			mokpiThresholdMapper.delThreshold(ruleIDs);
			return true;
		} catch (Exception e) {
			logger.info("删除异常：" + e.getMessage());
			return false;
		}
	}

	/**
	 * 初始化对象类型树
	 */
	@Override
	public Map<String, Object> findMObject(MObjectDefBean mobjectDefBean) {
		logger.info("初始化对象类型树........start");
		List<MObjectDefBean> mobjectDefList = mobjectInfoMapper.getMObject();
		String mobjectLstJson = JsonUtil.toString(mobjectDefList);
		logger.info("mobjectLstJson=======" + mobjectLstJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("mobjectLstJson", mobjectLstJson);
		return result;
	}

	/**
	 * 加载指标数据表格
	 */
	@Override
	public Map<String, Object> listPerfKPIDef(PerfKPIDefBean perfKPIDefBean) {
		logger.info("开始加载管理对象定义数据。。。。。。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<PerfKPIDefBean> page = new Page<PerfKPIDefBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", perfKPIDefBean.getName());
//		String classIds = getClassIds(classId);
//		paramMap.put("classIDs", classIds);
		paramMap.put("kpiCategory", perfKPIDefBean.getKpiCategory());
		paramMap.put("classID", perfKPIDefBean.getClassID());
		page.setParams(paramMap);
		List<PerfKPIDefBean> perfKPIDefList = perfKPIDefMapper
				.selectPerfKPIDefs(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", perfKPIDefList);
		logger.info("加载数据结束。。。");
		return result;
	}

	public String getClassIds(int classId) {
		String classIds = "";
		List<Integer> allOrgIDList = new ArrayList<Integer>();
		allOrgIDList.add(classId);
		List<Integer> childIdList = new ArrayList<Integer>();
		// if (classId > 13 ) {
		childIdList.add(classId);
		while (true) {
			List<Integer> moreChildIDList = getChildIdByMobjectIDList(childIdList);
			for (int i = 0; i < childIdList.size(); i++) {
				childIdList.remove(i);
			}
			if (moreChildIDList.size() != 0) {
				for (int i = 0; i < moreChildIDList.size(); i++) {
					int childId = moreChildIDList.get(i);
					childIdList.add(childId);
					allOrgIDList.add(childId);
				}
			} else {
				break;
			}
		}
		for (int i = 0; i < allOrgIDList.size(); i++) {
			classIds += allOrgIDList.get(i) + ",";
		}
		classIds = classIds.substring(0, classIds.lastIndexOf(","));
		// } else {
		// classIds = classId + "";
		// }
		return classIds;
	}

	public List<Integer> getChildIdByMobjectIDList(List<Integer> mobjectIDList) {
		String ordIds = "";
		for (int i = 0; i < mobjectIDList.size(); i++) {
			ordIds += mobjectIDList.get(i) + ",";
		}
		ordIds = ordIds.substring(0, ordIds.lastIndexOf(","));
		List<Integer> childIDList = mobjectInfoMapper
				.getChildIdByParentIDs(ordIds);
		return childIDList;
	}

	/**
	 * 阈值规则定义唯一性的验证(新增)
	 */
	@Override
	public boolean checkBeforeAdd(MOKPIThresholdBean mokpiThresholdBean) {
		if ("".equals(mokpiThresholdBean.getMoID())
				|| mokpiThresholdBean.getMoID() == null) {
			mokpiThresholdBean.setMoID(-1);
		}
		if ("".equals(mokpiThresholdBean.getSourceMOID())
				|| mokpiThresholdBean.getSourceMOID() == null) {
			mokpiThresholdBean.setSourceMOID(-1);
		}
		int checkRs = mokpiThresholdMapper.getByConditions(mokpiThresholdBean);
		if (checkRs > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 新增阈值规则定义
	 */
	@Override
	public boolean addThreshold(MOKPIThresholdBean mokpiThresholdBean) {
		if ("".equals(mokpiThresholdBean.getMoID())
				|| mokpiThresholdBean.getMoID() == null) {
			mokpiThresholdBean.setMoID(-1);
		}
		if ("".equals(mokpiThresholdBean.getSourceMOID())
				|| mokpiThresholdBean.getSourceMOID() == null) {
			mokpiThresholdBean.setSourceMOID(-1);
		}
		try {
			mokpiThresholdMapper.insertThreshold(mokpiThresholdBean);
			return true;
		} catch (Exception e) {
			logger.error("新增异常：" + e);
			return false;
		}
	}

	/**
	 * 查看页面详情
	 */
	@Override
	public MOKPIThresholdBean initThresholdDetail(
			MOKPIThresholdBean mokpiThresholdBean) {
		logger.info("查看行的ID：" + mokpiThresholdBean.getRuleID());
		MOKPIThresholdBean mokpiThreshold = mokpiThresholdMapper
				.getThresholdById(mokpiThresholdBean.getRuleID());
		setSourceName(mokpiThreshold);
		setMoName(mokpiThreshold);
		return mokpiThreshold;
	}

	/**
	 * 阈值规则定义唯一性的验证(编辑)
	 */
	@Override
	public boolean checkBeforeUpdate(MOKPIThresholdBean mokpiThresholdBean) {
		if ("".equals(mokpiThresholdBean.getMoID())
				|| mokpiThresholdBean.getMoID() == null) {
			mokpiThresholdBean.setMoID(-1);
		}
		if ("".equals(mokpiThresholdBean.getSourceMOID())
				|| mokpiThresholdBean.getSourceMOID() == null) {
			mokpiThresholdBean.setSourceMOID(-1);
		}
		int checkRs = mokpiThresholdMapper.getByIDs(mokpiThresholdBean);
		if (checkRs > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 编辑阈值规则定义
	 */
	@Override
	public boolean updateThreshold(MOKPIThresholdBean mokpiThresholdBean) {
		if ("".equals(mokpiThresholdBean.getMoID())
				|| mokpiThresholdBean.getMoID() == null) {
			mokpiThresholdBean.setMoID(-1);
		}
		if ("".equals(mokpiThresholdBean.getSourceMOID())
				|| mokpiThresholdBean.getSourceMOID() == null) {
			mokpiThresholdBean.setSourceMOID(-1);
		}
		try {
			mokpiThresholdMapper.updateThreshold(mokpiThresholdBean);
			return true;
		} catch (Exception e) {
			logger.error("更新阈值异常：" + e);
			return false;
		}
	}

	/**
	 * 获得内存列表数据
	 */
	@Override
	public List<MOMemoriesBean> selectMOMemories(Page<MOMemoriesBean> page) {
		List<MOMemoriesBean> memoriesList = moMemoriesMapper
				.selectMOMemories(page);
		for (int i = 0; i < memoriesList.size(); i++) {
			if (memoriesList.get(i).getMemorySize() != null
					|| "".equals(memoriesList.get(i).getMemorySize())) {
				String memorySize = HostComm.getBytesToSize(Long
						.parseLong(memoriesList.get(i).getMemorySize()));
				memoriesList.get(i).setMemorySize(memorySize);
			} else {
				memoriesList.get(i).setMemorySize("");
			}
		}
		return memoriesList;
	}

	/**
	 * 获得磁盘列表数据
	 */
	@Override
	public List<MOVolumesBean> selectMOVolumes(Page<MOVolumesBean> page) {
		List<MOVolumesBean> volumnList = moVolumesMapper.selectMOVolumes(page);
		for (int i = 0; i < volumnList.size(); i++) {
			if (volumnList.get(i).getDiskSize() != null
					|| "".equals(volumnList.get(i).getDiskSize())) {
				String diskSize = HostComm.getBytesToSize(Long
						.parseLong(volumnList.get(i).getDiskSize()));
				volumnList.get(i).setDiskSize(diskSize);
			} else {
				volumnList.get(i).setDiskSize("");
			}
		}
		return volumnList;
	}

	/**
	 * 验证接口的源对象与管理对象是否一致
	 */
	@Override
	public boolean checkNetworkIfMOID(MONetworkIfBean bean) {
		int rs = moNetworkIfMapper.getByDeviceMOIDAndMOID(bean);
		if (rs > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证磁盘的源对象与管理对象是否一致
	 */
	@Override
	public boolean checkVolMOID(MOVolumesBean bean) {
		int rs = moVolumesMapper.getByDeviceMOIDAndMOID(bean);
		if (rs > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证CPU的源对象与管理对象是否一致
	 */
	@Override
	public boolean checkCPUtMOID(String deviceMOIDStr, String moIDStr) {
		MOCPUsBean bean = new MOCPUsBean();
		if ("".equals(deviceMOIDStr) || null == deviceMOIDStr) {
			bean.setDeviceMOID(-1);
		} else {
			bean.setDeviceMOID(Integer.parseInt(deviceMOIDStr));
		}
		if ("".equals(moIDStr) || null == moIDStr) {
			bean.setMoID(-1);
		} else {
			bean.setMoID(Integer.parseInt(moIDStr));
		}
		int rs = mocpUsMapper.getByDeviceMOIDAndMOID(bean);
		if (rs > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证内存的源对象与管理对象是否一致
	 */
	@Override
	public boolean checkMemMOID(String deviceMOIDStr, String moID) {
		MOMemoriesBean bean = new MOMemoriesBean();
		bean.setDeviceMOID(Integer.parseInt(deviceMOIDStr));
		bean.setMoID(Integer.parseInt(moID));
		int rs = moMemoriesMapper.getByDeviceMOIDAndMOID(bean);
		if (rs > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public MOKPIThresholdBean getInfoBySourceMOID(String moID,
			String sourceMOID, String classID) {
		return mokpiThresholdMapper.getInfoBySourceMOID(moID, sourceMOID,
				classID);
	}

	@Override
	public String getSiteName(MOKPIThresholdBean bean) {
		int classID = bean.getClassID();
		int sourceMOID = bean.getSourceMOID();
		String siteName = "";
		if (classID == 90) {
			WebSite webSite = webSiteMapper.getSiteNameAnMOID(sourceMOID);
			if (webSite != null) {
				siteName = webSite.getSiteName();
			}
		} else if (classID == 91) {
			SiteDns dns = webSiteMapper.getSiteDnsByMoId(sourceMOID);
			if (dns != null) {
				siteName = dns.getSiteName();
			}
		} else if (classID == 92) {
			SiteFtp ftp = webSiteMapper.getSiteFtpByMoId(sourceMOID);
			if (ftp != null) {
				siteName = ftp.getSiteName();
			}
		} else if (classID == 93) {
			SiteHttp http = webSiteMapper.getSiteHttpByMoId(sourceMOID);
			if (http != null) {
				siteName = http.getSiteName();
			}
		} else if (classID == 94) {
			SitePort sitePort = webSiteMapper.getSitePortByMoId(sourceMOID);
			if (sitePort != null) {
				siteName = sitePort.getSiteName();
			}
		}
		return siteName;
	}

	@Override
	public List<MObjectDefBean> initObject(int kpiID) {
		return perfKPIDefMapper.getKPIObject(kpiID);
	}
}
