package com.fable.insightview.monitor.AcUPS.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.AcUPS.entity.AirConditionBean;
import com.fable.insightview.monitor.AcUPS.entity.DomainBean;
import com.fable.insightview.monitor.AcUPS.entity.PerfRoomConditionsBean;
import com.fable.insightview.monitor.AcUPS.entity.perfEastUpsBean;
import com.fable.insightview.monitor.AcUPS.entity.perfInvtUpsBean;
import com.fable.insightview.monitor.AcUPS.mapper.AirConditionMapper;
import com.fable.insightview.monitor.AcUPS.service.IAcConditionService;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.machineRoom.service.DBUtil;
import com.fable.insightview.monitor.molist.entity.SysMoInfoBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateUsedBean;
import com.fable.insightview.monitor.perf.entity.MoInfoBean;
import com.fable.insightview.monitor.perf.entity.PerfTaskInfoBean;
import com.fable.insightview.monitor.perf.mapper.PerfPollTaskMapper;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.perf.service.IObjectPerfConfigService;
import com.fable.insightview.monitor.util.Ac_UPSCommon;
import com.fable.insightview.monitor.util.ConfigParameterCommon;
import com.fable.insightview.monitor.util.Utils;
import com.fable.insightview.monitor.utils.DbConnection;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.page.Page;

@Service
public class AcConditionService implements IAcConditionService {
	private byte[] lock = new byte[0];	//控制moid的获取
	private static final Logger log = LoggerFactory.getLogger(AcConditionService.class);
	public static String url = null;
	public static String driver = null;
	public static String username = null;
	public static String password = null;
	public static String  Switch = null;
	static {
		Properties p = new Properties();
		try {
			p.load(DBUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
				url = p.getProperty("jdbc.url");
				driver = p.getProperty("jdbc.driverClassName");
				username = p.getProperty("jdbc.username");
				password = p.getProperty("jdbc.password");
					Class.forName(driver);
		} catch (Exception e) {
			log.error("获取数据库连接失败",e);
		}
	}
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 空调OID
	private static final String CONDITIONINFO ="1.3.6.1.4.1.13400.2.10.1";
	// 空调厂商
	private static final String CONDITIONMAN =".1.3.6.1.4.1.13400";
	// 空调
	private static final  String CONDITION = "96";
	// ups
	private static final  String UPS = "73";
	// UPS EASTOID
	private static final String UPSEASTINFO ="1.3.6.1.4.1.2350.1.1.4";
	// UPS east厂商
	private static final String UPSEASTMAN =".1.3.6.1.4.1.2350";
	// UPS INVTOID
	private static final String UPSINVTINFO ="1.3.6.1.4.1.935.1.1.1.1.1";
	// UPS INVT厂商
	private static final String UPSINVTMAN =".1.3.6.1.4.1.935";
		
	private static final Logger logger = LoggerFactory.getLogger(AcConditionService.class);
	@Autowired
	AirConditionMapper  acConditionMapper;
	@Autowired PerfPollTaskMapper perfPollTaskMapper;
	@Autowired PerfTaskInfoMapper perfTaskInfoMapper;
	@Autowired IObjectPerfConfigService objectPerfConfigService;
	
	@Override
	public JSONObject getTestResult(ConfigParameterCommon snmp,String mOClassID) {
		Ac_UPSCommon acUPS = new Ac_UPSCommon();
		JSONObject obj = new JSONObject();
		if(null !=snmp.getAddress() && !"".equals(snmp.getAddress())){
		Map<OID,String> map = acUPS.getCondition(snmp);
		int domainID = getDomainID(snmp.getAddress());
		AirConditionBean condition = new AirConditionBean();
		if(mOClassID.equals(CONDITION)){
			if(map !=null && map.size()> 0){
				String moName= Utils.getChinese(map.get(new OID(CONDITIONINFO+".2.0")));
				int ManufacturerID =  getResManufacturerID(CONDITIONMAN);
				condition.setMoName(moName);
				condition.setNeManufacturerID(ManufacturerID);
			}
		}else if(mOClassID.equals(UPS)){
			if(map !=null && map.size()> 0){
				String moName = null;
				int ManufacturerID;
					// 易事特UPS
					moName= Utils.getChinese(map.get(new OID(UPSEASTINFO+".2.0")));
					if(null !=moName && !"".equals(moName)){
						ManufacturerID =  getResManufacturerID(UPSEASTMAN);
					}else{
						// 英威腾UPS
						moName=  Utils.getChinese(map.get(new OID(UPSINVTINFO+".1.0")));
						ManufacturerID = getResManufacturerID(UPSINVTMAN);
					}
					condition.setMoName(moName);
					condition.setNeManufacturerID(ManufacturerID);
			}
		}
		if(null !=condition && null != condition.getMoName() && !"".equals(condition.getMoName())){
			condition.setDomainID(domainID);
			obj.put("flag", true);
			obj.put("result", condition);
		}
	 }
		return obj;
	}
	
	/***
	 * 获取厂商ID
	 * @param OID
	 * @return
	 */
	private int getResManufacturerID(String OID){
		int i =-1;
		 i = acConditionMapper.getResManufacturerID(OID);
		return i;
	}

	@Override
	public boolean insertData(AirConditionBean condition) {
		boolean snmpFlag =false;
		AirConditionBean ac = acConditionMapper.selectIPinfo(condition);
		 // 如果moIPinfo没有该条记录那么需要新增
		 if(ac !=null && ac.getMoID() !=null){
			 condition.setMoID(ac.getMoID());
		 }else{
			 condition.setMoID(getMOID(1)); 
			 acConditionMapper.insertMOIPInfo(condition);
		 }
		 int k = acConditionMapper.updateModevice(condition);
		 // 如果MODevice没有该条记录那么需要新增
		 if(k==0){
			  acConditionMapper.addData(condition);
		 }
		 int j= acConditionMapper.updateSNMPInfo(condition.getSNMPbean());
		 // 如果SNMPCommunityCache没有该条记录那么需要新增
		 if(j==0){
			 condition.getSNMPbean().setMoID(condition.getMoID());
			 condition.getSNMPbean().setID(DbConnection.getID("SNMPCommunityTempPK", 1));
			 snmpFlag = acConditionMapper.insertSNMP(condition.getSNMPbean());
		 }
		  /*if(snmpFlag == true){
				//采集任务
				return  addSitePerfTask(condition,templateID,moTypeLstJson);
			}*/
		 return snmpFlag;
	}

	@Override
	public List<AirConditionBean> queryAcConditionList(Page<AirConditionBean> page) {
		return acConditionMapper.queryAcConditionList(page);
	}

	@Override
	public ArrayList<DomainBean> DomainList() {
		 ArrayList<DomainBean> domainArray = acConditionMapper.DomainList();
		 
		return domainArray;
	}

	
	/**获取管理域的ID
	 * 1 - 所有管理域
	 * @param startIP
	 * @return
	 */
	public  int getDomainID(String startIP) {
		ArrayList<DomainBean> domainArray = DomainList();
		
		for(DomainBean dom : domainArray) {
			int ScopeType = dom.getScopeType();
			
			if(ScopeType == 1) { //子网
				String ip = dom.getIP1();
				String mask = dom.getIP2();
				if(IPTool.subNetContainsIP(ip, mask, startIP)) {
					return dom.getDomainID();
				}
			} else if(ScopeType == 2) {	//ip范围
				String start = dom.getIP1();
				String end = dom.getIP2();
				if(IPTool.ContainsIP(start, end, startIP)) {
					return dom.getDomainID();
				}
			}
			
		}
		return 1;
	}

	@Override
	public  AirConditionBean  queryAcditionByMOID( Map<String, Object> paramMap) {
		return acConditionMapper.queryAcditionByMOID(paramMap);
	}

	@Override
	public boolean checkbeforeAdd(AirConditionBean bean) {
		 int i = acConditionMapper.checkbeforeAdd(bean);
		 return i>0?true:false;
	}

	@Override
	public boolean updateData(AirConditionBean condition,int templateID, String moTypeLstJson) {
		acConditionMapper.updateMOIpinfo(condition);
		acConditionMapper.updateModevice(condition);
		condition.getSNMPbean().setMoID(condition.getMoID());
		int i = acConditionMapper.updateSNMPInfo(condition.getSNMPbean());
		if(i>0){
			//采集任务
			return this.addSitePerfTask(condition, templateID, moTypeLstJson);
		}
		return true;
	}
 
	@Override
	public boolean addSitePerfTask(AirConditionBean condition,int templateID,String moTypeLstJson) {
		// 新增采集任务结果
		boolean addTaskFlag = false;
		//新增监测器结果
		boolean insertMoFlag = false;
		//删除监测器结果
		boolean deleteMoFlag = true;
		// 采集任务id
		int taskId = -1;
		int moId = condition.getMoID();
		int moClassID = condition.getMoClassID();
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		PerfTaskInfoBean perfTask = new PerfTaskInfoBean();
//		perfTask = perfTaskInfoMapper.getTaskByMOIDAndClassAndDBName(moId,moClassID,"");
		// 检查采集任务是否存在
		int i  = perfTaskInfoMapper.isExsitTask(moId);
		//没有采集任务
		if(i == 0){
			PerfTaskInfoBean perfTask2 = new PerfTaskInfoBean();
			perfTask2.setMoId(moId);
			perfTask2.setStatus(1);
			perfTask2.setOperateStatus(1);// 操作状态
			perfTask2.setProgressStatus(1);// 进度状态
			perfTask2.setLastOPResult(0);
			perfTask2.setCollectorId(-1);
			perfTask2.setOldCollectorId(-1);
			perfTask2.setCreator(sysUserInfoBeanTemp.getId());
			perfTask2.setCreateTime(dateFormat.format(new Date()));
			perfTask2.setMoClassId(moClassID);
			try {
				perfPollTaskMapper.insertTaskInfo(perfTask2);
				taskId = perfTask2.getTaskId();
				addTaskFlag = true;
			} catch (Exception e) {
				addTaskFlag = false;
				logger.error("新增采集任务异常：" + e);
			}
		}
		//已经存在采集任务的 更新采集任务
		else{
			perfTask = perfTaskInfoMapper.getTaskInfoByMoId(moId);
			if(perfTask !=null){
				taskId = perfTask.getTaskId();
			//删除原来采集任务的监测器
			perfTaskInfoMapper.deleteMoList(taskId);
			deleteMoFlag = true;
			// 更新采集任务
			perfTask.setMoId(moId);
			perfTask.setStatus(perfTask.getStatus());
			perfTask.setCreator(sysUserInfoBeanTemp.getId());
			perfTask.setCreateTime(dateFormat.format(new Date()));
			perfTask.setTaskId(perfTask.getTaskId());
			perfTask.setOperateStatus(2); // 操作状态
			perfTask.setMoClassId(perfTask.getMoClassId());
			perfTask.setProgressStatus(1);
			perfTask.setLastStatusTime(dateFormat.format(new Date()));// 最近状态时间
			try {
				perfTaskInfoMapper.updateTask(perfTask);
				addTaskFlag = true;
			} catch (Exception e) {
				addTaskFlag = false;
				logger.error("新增采集任务异常：" + e);
			}
		  }else{
			  logger.debug("没有获取采集任务信息======");
		  }
		}
		
		//添加监测器
		insertMoFlag = this.insertMonitors(condition,taskId,templateID,moTypeLstJson);
		
		if(addTaskFlag == true && insertMoFlag == true && deleteMoFlag == true){
			Dispatcher dispatcher = Dispatcher.getInstance();
			ManageFacade facade = dispatcher.getManageFacade();

			List<TaskDispatcherServer> servers = facade
					.listActiveServersOf(TaskDispatcherServer.class);
			if (servers.size() > 0) {
				String topic = "TaskDispatch";
				TaskDispatchNotification message = new TaskDispatchNotification();
				message.setDispatchTaskType(TaskType.TASK_COLLECT);
				facade.sendNotification(servers.get(0).getIp(), topic, message);
			}
			return true;
		}
		return false;
	}
	
	
	public boolean insertMonitors(AirConditionBean condition,int taskId,int templateID,String moTypeLstJson){
		boolean insertMoFlag = false;
		//绑定模板结果
		boolean setTemplateFlag = false;
		
		//绑定模板
		 SysMonitorsTemplateUsedBean templateUsedBean = new SysMonitorsTemplateUsedBean();
		templateUsedBean.setMoID(condition.getMoID());
		templateUsedBean.setMoClassID(condition.getMoClassID());
		templateUsedBean.setTemplateID(templateID);
		setTemplateFlag = objectPerfConfigService.setTmemplate(templateUsedBean);
		
		if(setTemplateFlag == true){
			//没有使用模板
			if(templateID == -1){
				String[] moList = condition.getMoList().split(",");
				String[] moIntervalList = condition.getMoIntervalList().split(",");
				String[] moTimeUnitList = condition.getMoTimeUnitList().split(",");
				int moInterval = -1;
				for (int i = 0; i < moList.length; i++) {
					logger.info("新增任务：监测器ID=" + moList[i] + "的监测器入库");
					MoInfoBean moBean = new MoInfoBean();
					moBean.setMid(Integer.parseInt(moList[i]));
					moBean.setTaskId(taskId);
					if(Integer.parseInt(moTimeUnitList[i]) == 1){
						moInterval = Integer.parseInt(moIntervalList[i]) * 60;
					}else if(Integer.parseInt(moTimeUnitList[i]) == 2){
						moInterval = Integer.parseInt(moIntervalList[i]) * 3600;
					}else if(Integer.parseInt(moTimeUnitList[i]) == 3){
						moInterval = Integer.parseInt(moIntervalList[i]) * 3600 * 24;
					}
					moBean.setDoIntervals(moInterval);
					
					try {
						perfPollTaskMapper.insertTaskMo(moBean);
						insertMoFlag = true;
					} catch (Exception e) {
						logger.error("新增监测器异常：" + e);
						insertMoFlag = false;
						break;
					}
				}
			}
			//使用模板
			else{
				List<String> moTypeList = JSON.parseArray(moTypeLstJson,String.class);
				for (int i = 0; i <moTypeList.size(); i++) {
					String[] moTypeInfoLst = moTypeList.get(i).split(",");
					int monitorTypeID = Integer.parseInt(moTypeInfoLst[0]);
					int timeInterval = Integer.parseInt(moTypeInfoLst[2]);
					int timeUnit = Integer.parseInt(moTypeInfoLst[3]);
					
					List<SysMoInfoBean> monitorList = objectPerfConfigService.getMoList(condition.getMoID(), condition.getMoClassID());
					for (int j = 0; j < monitorList.size(); j++) {
						if(monitorList.get(j).getMonitorTypeID() == monitorTypeID){
							MoInfoBean moBean = new MoInfoBean();
							moBean.setMid(monitorList.get(j).getMid());
							moBean.setTaskId(taskId);
							int interval = timeInterval;
							if(timeUnit == 1){
								interval = timeInterval * 60;
							}else if(timeUnit == 2){
								interval = timeInterval * 3600;
							}else if(timeUnit == 3){
								interval = timeInterval * 3600 * 24;
							}
							moBean.setDoIntervals(interval);
							try {
								perfPollTaskMapper.insertTaskMo(moBean);
								insertMoFlag = true;
							} catch (Exception e) {
								logger.error("新增监测器异常：" + e);
								insertMoFlag = false;
								break;
							}
						}
					}
				}
			}
		}
		if(insertMoFlag == true && setTemplateFlag == true){
			return true;
		}
		return false;
	}

	@Override
	public  PerfRoomConditionsBean  queryperfRoomInfo(int moid) {
		return acConditionMapper.queryperfRoomInfo(moid);
	}

	@Override
	public int getConfParamValue() {
		return acConditionMapper.getConfParamValue();
	}

	@Override
	public perfEastUpsBean getUpsInfo(int moid) {
		// TODO Auto-generated method stub
		return acConditionMapper.getUpsInfo(moid);
	}

	@Override
	public perfInvtUpsBean getInvtUpsInfo(int moid) {
		// TODO Auto-generated method stub
		return acConditionMapper.getInvtUpsInfo(moid);
	}
	
	
	public int getMOID(int len) {
		return getID("MO", len);
	}

	public int getID(String tableName, int len) {
		Connection conn = null;
		CallableStatement proc = null;
		int ID = -1;

		if(len <= 0)
			return -1;

		try {
			synchronized(lock) {
				conn = getConnection();
				proc = conn.prepareCall("{ call getTableSequence(?,?,?) }");
				proc.setString(1, tableName);
				proc.setInt(2, len);
				proc.registerOutParameter(3, Types.INTEGER);

				proc.execute();

				ID = proc.getInt(3);
			}
		} catch (Exception e) {
			log.error("获取表"+tableName+"ID异常. ", e);
		} finally {
			try {
				if(proc != null)
					proc.close();
				if (conn != null)
					conn.close();
			} catch(Exception e) {

			}
		}
		return ID;
	}
	
	public  Connection getConnection() {
		Connection con = null;
		try {
			if(null !=url && null !=username && null !=password){
				con = DriverManager.getConnection(url, username, password);
			}
		} catch (SQLException e) {
			log.error("the method getConnection() occurs exception:\n" + e.getMessage());
		}
		return con;
	}
}
