package com.fable.insightview.monitor.dutydesk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmStatusInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.monitor.database.mapper.MsServerMapper;
import com.fable.insightview.monitor.database.mapper.MySQLMapper;
import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.database.mapper.SyBaseMapper;
import com.fable.insightview.monitor.dutydesk.bean.ResDeviceBean;
import com.fable.insightview.monitor.dutydesk.mapper.DutyDeskMapper;
import com.fable.insightview.monitor.dutydesk.service.IDutyDeskService;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.host.mapper.HostMapper;
import com.fable.insightview.monitor.topo.entity.TopoBean;
import com.fable.insightview.monitor.topo.mapper.TopoMapper;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.portal.entity.PortalInfoBean;
import com.fable.insightview.platform.portal.mapper.PortalInfoMapper;
import com.fable.insightview.platform.sysconf.mapper.SysConfigMapper;

@SuppressWarnings({ "unchecked", "serial" })
@Service
public class DutyDeskServiceImpl implements IDutyDeskService {
	private static final Logger logger = LoggerFactory.getLogger(DutyDeskServiceImpl.class);
	//值班服务台系统配置typeId
	private static final int DUTYDESK_TYPE = 1005;
	private static final int PAGENO= 1;
	private static final int PAGESIZE = 10;
	//区域字典类型名称
	private static final String AREA_CONSTANTNAME = "Area";
	private static final String[] alarmLableCNName ={"告警源IP","告警源名称","告警名称","告警标题","最近发生时间"};
	private static final Map<String, Object> alarmLableMap = new HashMap() {{    
	    put("告警源IP", "sourceMOIPAddress");    
	    put("告警源名称", "sourceMOName");
	    put("告警名称", "moName");
	    put("告警标题", "alarmTitle");    
	    put("最近发生时间", "lastTime");
	}}; 
	
	private static final int PHOST = 7;
	private static final int VHOST = 8;
	private static final int VM = 9;
	private static final int ROUTER = 5;
	private static final int SWITCHER = 6;
	private static final int SWITCHERL2 = 59;
	private static final int SWITCHERL3 = 60;
	
	private static final int ORACLEINSTANCE = 26;
	private static final int MYSQLSERVER = 28;
	private static final int DB2INSTANCE = 55;
	private static final int DB2DB = 56;
	private static final int SYBASESERVER = 82;
	private static final int MSSQLSERVER = 87;
	
	private static final int TOMCAT = 20;
	private static final int WEBSPHERE = 19;
	private static final int WEBLOGIC = 53;
	
	private static final int DNS = 91;
	private static final int FTP = 92;
	private static final int HTTP = 93;
	private static final int TCP = 94;
	
	@Autowired
	SysConfigMapper sysConfigMapper;
	@Autowired
	DutyDeskMapper deskMapper;
	@Autowired
	TopoMapper topoMapper;
	@Autowired
	AlarmActiveMapper alarmActiveMapper;
	@Autowired
	HostMapper hostMapper;
	@Autowired
	MySQLMapper mySQLMapper;
	@Autowired
	OracleMapper oracleMapper;
	@Autowired
	SyBaseMapper syBaseMapper;
	@Autowired
	MsServerMapper msServerMapper;
	@Autowired
	PortalInfoMapper portalInfoMapper;
	
	@Override
	public List<ResDeviceBean> getResMODevice() {
		//获得异常告警级别
		int alarmLevel = Integer.parseInt(sysConfigMapper.getConfParamValue(DUTYDESK_TYPE, "resMOAlarmLevel"));
		
		//获得资源监控设备的列表数据
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("alarmLevel", alarmLevel);
		param1.put("constantTypeName", AREA_CONSTANTNAME);
		List<ResDeviceBean> resDeviceList = deskMapper.getResMODeviceInfo(param1);
		//资源监控设备总数
		int resDeviceTotal = 0;
		//资源监控设备总异常设备 
		int resAlarmDeviceTotal = 0; 
		for (ResDeviceBean resDevice : resDeviceList) {
			resDeviceTotal += resDevice.getResourceNumber();
			resAlarmDeviceTotal += resDevice.getValue();
		}
		ResDeviceBean totalRes = new ResDeviceBean();
		totalRes.setRid(-1);
		totalRes.setName("总计");
		totalRes.setResourceNumber(resDeviceTotal);
		totalRes.setValue(resAlarmDeviceTotal);
		resDeviceList.add(resDeviceList.size(),totalRes);
		return resDeviceList;
	}

	@Override
	public Map<String, Object> getTopos() {
		//获得所有的拓扑
		List<TopoBean> topoList = topoMapper.getAllTopo();
		//获得默认展示的拓扑id
		int defaultTopoId = Integer.parseInt(sysConfigMapper.getConfParamValue(DUTYDESK_TYPE, "defaultTopoId"));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("topoList", topoList);
		result.put("defaultTopoId", defaultTopoId);
		return result;
	}

	@Override
	public Map<String, Object> get3dRooms() {
		//获得所有的机房
		List<ResDeviceBean> roomList = deskMapper.getRoom3Ds();
		//获得默认展示的机房id
		int defaultRoomId = Integer.parseInt(sysConfigMapper.getConfParamValue(DUTYDESK_TYPE, "defaultRoomId"));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("roomList", roomList);
		result.put("defaultRoomId", defaultRoomId);
		return result;
	}

	@Override
	public List<Map<String, Object>> getTopAlarm(Map<String, Object> paramMap) {
		List<Map<String, Object>> alarmList = alarmActiveMapper.getTopAlarm(paramMap);
		List<Map<String, Object>> topAlarmData = new ArrayList<Map<String, Object>>();
		if(alarmList != null && !alarmList.isEmpty()){
			for (int i = 0; i < alarmList.size(); i++) {
				Map<String, Object> alarm = alarmList.get(i);
				int alarmID = Integer.valueOf(alarm.get("alarmID").toString());
				int sourceMOClassID = Integer.valueOf(alarm.get("sourceMOClassID").toString());
				int sourceMOID = Integer.valueOf(alarm.get("sourceMOID").toString());
				String url = "";
				if(sourceMOClassID == PHOST || sourceMOClassID == VHOST || sourceMOClassID == VM  || sourceMOClassID == ROUTER || sourceMOClassID == SWITCHER
						 || sourceMOClassID == SWITCHERL2  || sourceMOClassID == SWITCHERL3){
					url = getDeviceViewUrl(sourceMOClassID, sourceMOID);
				}else if(sourceMOClassID == ORACLEINSTANCE  || sourceMOClassID ==  MYSQLSERVER || sourceMOClassID == DB2INSTANCE
						 || sourceMOClassID == DB2DB || sourceMOClassID == SYBASESERVER || sourceMOClassID == MSSQLSERVER){
					url = getDBViewUrl(sourceMOClassID, sourceMOID);
				}else if(sourceMOClassID == TOMCAT  || sourceMOClassID == WEBSPHERE || sourceMOClassID == WEBLOGIC){
					url = getMiddlewareViewUrl(sourceMOClassID, sourceMOID);
				}else if(sourceMOClassID == DNS || sourceMOClassID == FTP || sourceMOClassID == HTTP || sourceMOClassID == TCP ){
					url = getSiteViewUrl(sourceMOClassID, sourceMOID);
				}
				if("".equals(url)){
					url = "#";
				}
				List<Map<String, Object>> alarmDetailData = getAlarmData(alarmID);
				alarm.put("data", alarmDetailData);
				alarm.put("rid", i);
				alarm.put("url", url);
				topAlarmData.add(alarm);
			}
		}
		return alarmActiveMapper.getTopAlarm(paramMap);
	}
	
	private List<Map<String, Object>> getAlarmData(int alarmId){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		//获得告警详情
		Map<String, Object> alarmDetailData = alarmActiveMapper.getDutyDeskAlarmDetail(alarmId);
		for (int i = 0; i < alarmLableCNName.length; i++) {
			Map<String, Object> resBean = new HashMap<String, Object>();
			String lableCNName = alarmLableCNName[i];
			String value = "";
			if(alarmDetailData != null && alarmDetailData.get(alarmLableMap.get(lableCNName))!= null){
				value = alarmDetailData.get(alarmLableMap.get(lableCNName)).toString();
			}
			resBean.put("label", lableCNName);
			resBean.put("value", value);
			result.add(resBean);
		}
		return result;
	}
	
	/**
	 * 获得设备视图的url地址
	 */
	String getDeviceViewUrl(int moClassID,int moId){
		String moClass = "host";
		String moClassName = "";
		if (moClassID == PHOST) {
            moClass = "host";
            moClassName = "PHost";
        }
        if (moClassID == VHOST) {
            moClass = "vhost";
            moClassName = "VHost";
        }
        if (moClassID == VM) {
            moClass = "vm";
            moClassName = "VM";
        }
        if (moClassID == ROUTER) {
            moClass = "router";
            moClassName = "Router";
        }
        if (moClassID == SWITCHER) {
            moClass = "switcher";
            moClassName = "Switcher";
        }
		if(moClassID==SWITCHERL2){
			moClass="switcherl2";
			moClassName = "SwitcherL2";
		}
		if(moClassID==SWITCHERL3){
			moClass="switcherl3";
			moClassName = "SwitcherL3";	
		}
        String urlParams = "?moID=" + moId + "&moClass=" + moClass + "&flag=device&moClassName="+moClassName;
        String url = "/monitor/gridster/showPortalView" + urlParams;
        return url;
	}
	
	/**
	 * 获得数据库视图的url地址
	 */
	String getDBViewUrl(int moClassID,int moId){
		String moClass = "";
		int dbmsMoId = -1;
		String urlParams="";
		String moClassName = "";
		if(moClassID == ORACLEINSTANCE){
			moClass="oracle";
			moClassName = "Oracle";
			dbmsMoId = oracleMapper.getDbmsMoIdByOrclIns(moId);
			urlParams="?moID="+dbmsMoId+"&moClass="+moClass+"&flag=device";
		}else if(moClassID == MYSQLSERVER){
			moClass="mysql";
			moClassName = "Mysql";
			dbmsMoId = mySQLMapper.getDbmsMoIdBySqlserver(moId);
			urlParams="?moID="+dbmsMoId+"&moClass="+moClass+"&flag=device";
		}else if(moClassID == DB2INSTANCE){
			moClass="db2_instance";
			moClassName="DB2_INSTANCE";
			urlParams="?moID="+moId+"&moClass="+moClass+"&flag=device";
		}else if(moClassID == DB2DB){
			moClass="db2_db";
			moClassName="DB2_DB";
			urlParams="?moID="+moId+"&moClass="+moClass+"&flag=device";
		}else if(moClassID == SYBASESERVER){
			moClass="sybase";
			moClassName = "SybaseServer";
			dbmsMoId = syBaseMapper.getDbmsIDByServer(moId);
			urlParams = "?moID=" + dbmsMoId + "&moClass=" + moClass + "&flag=device";
		}else if(moClassID == MSSQLSERVER){
			moClass="mssql";
			moClassName = "MsSqlServer";
			dbmsMoId = msServerMapper.getDbmsIDByServer(moId);
			urlParams = "?moID=" + dbmsMoId + "&moClass=" + moClass + "&flag=device";
		}
		boolean isHaveView = viewDevicePortal(moClassName);
		if(isHaveView){
			String url = "/monitor/gridster/showPortalView" + urlParams+"&moClassName="+moClassName;
			return url;
		}
		return "";
	}
	
	/**
	 * 获得中间件视图的url地址
	 */
	String getMiddlewareViewUrl(int moClassID,int moId){
		String moClass = "";
		String moClassName = "";
		if(moClassID == TOMCAT){
			moClass="tomcat";
			moClassName="Tomcat";
		}else if(moClassID == WEBSPHERE){
			moClass="websphere";
			moClassName="Websphere";
		}else if(moClassID == WEBLOGIC){
			moClass="weblogic";
			moClassName="Weblogic";
		}
		boolean isHaveView = viewDevicePortal(moClassName);
		if(isHaveView){
			String urlParams="?moID="+moId+"&moClass="+moClass+"&flag=device&moClassName="+moClassName;
			String url = "/monitor/gridster/showPortalView" + urlParams;
			return url;
		}
		return "";
	}
	
	/**
	 * 获得站点视图的url地址
	 */
	String getSiteViewUrl(int moClassID,int moId){
		String moClassName = "";
		String moClass = "";
		if(moClassID == DNS){
			moClassName="DNS";
			moClass="DNS";
		}else if(moClassID == FTP){
			moClassName="FTP";
			moClass="FTP";
		}else if(moClassID == HTTP){
			moClassName="HTTP";
			moClass="HTTP";
		}else if(moClassID == TCP){
			moClassName="TCP";
			moClass="TCP";
		}
		boolean isHaveView = viewDevicePortal(moClassName);
		if(isHaveView){
			String urlParams="?moID="+moId+"&moClass="+moClass+"&flag=device&moClassName="+moClassName;
			String url = "/monitor/gridster/showPortalView" + urlParams;
			return url;
		}
		return "";
	}

	public boolean viewDevicePortal(String portalName) {
		PortalInfoBean portalInfoBean = portalInfoMapper
				.getPortalByName(portalName);
		String portalContent = portalInfoBean.getPortalContent();
		if (portalContent != null && !"".equals(portalContent)) {
			return true;
		}
		logger.info("根据portalName获取到的portalContent === " + portalContent);
		return false;
	}
	
	@Override
	public Map<String, Object> getAlarmList(Map<String, Object> map) {
		Page<AlarmNode> page = new Page<AlarmNode>();
		// 获得当前页数
		page.setPageNo( map.get("page") == null ? PAGENO : Integer .parseInt( map.get("page").toString()));
		// 获得每页数据最大量
		page.setPageSize( map.get("rows") == null ? PAGESIZE : Integer .parseInt( map.get("rows").toString()));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if( map.get("area") != null){
			int areaId = deskMapper.getAreaId(map.get("area").toString());
			paramMap.put("area", areaId);
		}
		if( map.get("area") != null){
			//获得异常告警级别
			int alarmLevel = Integer.parseInt(sysConfigMapper.getConfParamValue(DUTYDESK_TYPE, "resMOAlarmLevel"));
			 paramMap.put("resAlarmLevel", alarmLevel);
		}
		paramMap.put("alarmOperateStatus", map.get("alarmOperateStatus"));
		paramMap.put("alarmLevel", map.get("alarmLevel"));
		paramMap.put("timeBegin", map.get("timeBegin"));
		paramMap.put("timeEnd", map.get("timeEnd"));
		paramMap.put("alarmTitle", map.get("alarmTitle"));
		paramMap.put("moName", map.get("moName"));
		paramMap.put("sourceMOIPAddress",map.get("sourceMOIPAddress"));
		paramMap.put("alarmType", map.get("alarmType"));
		paramMap.put("both", map.get("both"));
		page.setParams(paramMap);
		List<AlarmNode> alarmList = alarmActiveMapper.queryListByDutyDesk(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", alarmList);
		return result;
	}

	@Override
	public List<MODevice> getTopCpu(Map<String, Object> paramMap) {
		return hostMapper.getTopCPUByDutyDesk(paramMap);
	}

	@Override
	public List<MODevice> getTopMemory(Map<String, Object> paramMap) {
		return hostMapper.getTopMemoryByDutyDesk(paramMap);
	}

	@Override
	public List<MODevice> getTopVolumes(Map<String, Object> paramMap) {
		return hostMapper.getTopDiskByDutyDesk(paramMap);
	}
	
	@Override
	public Map<String, Object> getCpuList(Map<String, Object> map) {
		Page<MODevice> page = new Page<MODevice>();
		// 获得当前页数
		page.setPageNo( map.get("page") == null ? PAGENO : Integer .parseInt( map.get("page").toString()));
		// 获得每页数据最大量
		page.setPageSize( map.get("rows") == null ? PAGESIZE : Integer .parseInt( map.get("rows").toString()));
		//排序 默认按使用率降序
		String orderType = map.get("orderType") == null ? "desc" : map.get("orderType").toString();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderType", orderType);
		param.put("deviceIp", map.get("deviceIp"));
		param.put("moName", map.get("moName"));
		param.put("lowerLimit", map.get("lowerLimit"));
		param.put("upperLimit", map.get("upperLimit"));
		page.setParams(param);
		List<MODevice> cpuList = hostMapper.getCpuListByDutyDesk(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", cpuList);
		return result;
	}

	@Override
	public Map<String, Object> getMemList(Map<String, Object> map) {
		Page<MODevice> page = new Page<MODevice>();
		// 获得当前页数
		page.setPageNo( map.get("page") == null ? PAGENO : Integer .parseInt( map.get("page").toString()));
		// 获得每页数据最大量
		page.setPageSize( map.get("rows") == null ? PAGESIZE : Integer .parseInt( map.get("rows").toString()));
		//排序  默认按使用率降序
		String orderType = map.get("orderType") == null ? "desc" : map.get("orderType").toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderType", orderType);
		paramMap.put("deviceIp", map.get("deviceIp"));
		paramMap.put("moName", map.get("moName"));
		paramMap.put("lowerLimit", map.get("lowerLimit"));
		paramMap.put("upperLimit", map.get("upperLimit"));
		page.setParams(paramMap);
		List<MODevice> cpuList = hostMapper.getMemListByDutyDesk(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", cpuList);
		return result;
	}

	@Override
	public Map<String, Object> getVolList(Map<String, Object> map) {
		Page<MODevice> page = new Page<MODevice>();
		// 获得当前页数
		page.setPageNo( map.get("page") == null ? PAGENO : Integer .parseInt( map.get("page").toString()));
		// 获得每页数据最大量
		page.setPageSize( map.get("rows") == null ? PAGESIZE : Integer .parseInt( map.get("rows").toString()));
		//排序   默认按使用率降序
		String orderType = map.get("orderType") == null ? "desc" : map.get("orderType").toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderType", orderType);
		paramMap.put("deviceIp", map.get("deviceIp"));
		paramMap.put("moName", map.get("moName"));
		paramMap.put("rawdescr", map.get("rawdescr"));
		paramMap.put("lowerLimit", map.get("lowerLimit"));
		paramMap.put("upperLimit", map.get("upperLimit"));
		page.setParams(paramMap);
		List<MODevice> cpuList = hostMapper.getVolListByDutyDesk(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", cpuList);
		return result;
	}

	@Override
	public Map<String, Object> getAlarmConditions() {
		// 获取告警级别下拉框数据
		List<AlarmLevelInfo> levelList = alarmActiveMapper.queryLevelInfo();
		// 获取告警类型下拉框数据
		List<AlarmTypeInfo> typeList = alarmActiveMapper.queryTypeInfo();
		// 获取告警状态下拉框数据
		List<AlarmStatusInfo> statusList = alarmActiveMapper.queryStatusInfo();
		List<AlarmStatusInfo> activeStatusList = new ArrayList<AlarmStatusInfo>();
		for (int i = 0; i < statusList.size(); i++) {
			if (statusList.get(i).getStatusID() != 24) {
				activeStatusList.add(statusList.get(i));
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("levelList", levelList);
		result.put("typeList", typeList);
		result.put("statusList", activeStatusList);
		return result;
	}

	@Override
	public AlarmNode getAlarmDtail(Map<String, Integer> paramMap) {
		int alarmId = paramMap.get("alarmId");
		//获得告警详情
		return alarmActiveMapper.getInfoById(alarmId);
	}

}
