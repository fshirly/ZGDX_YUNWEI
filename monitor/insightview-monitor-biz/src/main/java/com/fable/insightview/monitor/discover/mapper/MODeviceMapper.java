package com.fable.insightview.monitor.discover.mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.discover.entity.Count;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.platform.page.Page;

public interface MODeviceMapper {
	int deleteByPrimaryKey(Integer moid);

	int insert(MODeviceObj record);

	int insertSelective(MODeviceObj record);

	MODeviceObj selectByPrimaryKey(Integer moid);

	int updateByPrimaryKeySelective(MODeviceObj record);

	int updateByPrimaryKey(MODeviceObj record);

	int updateStateByPrimaryKey(String moid);

	// LinkedList<Count> getDiscoverResultCount(Integer taskID);
	// 获取类型集合
	LinkedList<Count> getCategoryByDeviceType();

	// 获取设备集合
	LinkedList<Count> getMoDeviceCountByNeCategoryID(Integer taskID);
	
	// 获取设备集合
	LinkedList<Count> getCountByMOClassID(Integer taskID);

	// 从表里取入库进度
	int getDiscoverStoreProcess(Integer taskID);

	// 根据OS树形结构
	LinkedList<MODeviceObj> getDiscoverResultByOS(Integer taskID);
	
	// 根据设备类型来判断树形结构
	LinkedList<MODeviceObj> getDiscoverResultByMOClassID(Integer taskID);

	// 列表
	LinkedList<MODeviceObj> MoDeviceProInfoList2(Page<MODeviceObj> page);
	
	// 获取SNMPDeviceSysObjectID表中所有的数据
	LinkedList<MODeviceObj> getSNMPDeviceCount();
	

	// 根据moid获取moclass
	String getMoClassByMoId(MODeviceObj moDevice);

	String getClassNameByMoId(int id);
	
	String getErrorMessByErrorID(String errorID);

	MODeviceObj getFirstMoDevice(Integer moClassId);

	// 条件过滤设备列表
	LinkedList<MODeviceObj> queryMoDeviceList(Page<MODeviceObj> page);
	
	// 条件过滤设备列表
	LinkedList<MODeviceObj> queryPhysicsDeviceList(Page<MODeviceObj> page);

	// 根据新告警信息查询设备的告警级别
	MODeviceObj getMOdeviceByAlarm(@Param("SourceMOID") Integer SourceMOID);
	
	// 根据新告警信息查询数据库的告警级别
	MODeviceObj getMOdeviceDB(@Param("SourceMOID") String SourceMOID, @Param("dbTabName") String dbTabName);
	
	//DB2告警级别
	MODeviceObj getMOdeviceDB2(@Param("SourceMOID") String SourceMOID);
	
	// 根据新告警信息查询中间件的告警级别
	MODeviceObj getMOdeviceMiddleWare(@Param("SourceMOID") Integer SourceMOID);

	// 根据新告警信息修改设备的告警级别
	int updateModeviceAlarmLevel(@Param("MOID") Integer MOID, @Param("currLevel") Integer currLevel);
	
	// 根据新告警信息中间件的告警级别
	int updateMiddleWareAlarmLevel(@Param("MOID") Integer MOID, @Param("currLevel") Integer currLevel);
	
	// 根据新告警信息修改数据库的告警级别
	int updateDBAlarmLevel(@Param("MOID") Integer MOID, @Param("currLevel") Integer currLevel);

	// 更新设备的告警级别
	int updateModeviceAllAlarmLevel();
	
	// 更新中间件的告警级别
	int updateMiddleWareAllAlarmLevel();
	
	// 更新数据库的告警级别
	int updateDBAllAlarmLevel();

	LinkedList<MODeviceObj> getMODeviceByResId(Map map);

	LinkedList<MODeviceObj> synchronMoDeviceToRes(Page<MODeviceObj> page);
	
	LinkedList<MODeviceObj> synchronMoHttpToRes(Page<MODeviceObj> page);
	
	LinkedList<MODeviceObj> synchronMoTcpToRes(Page<MODeviceObj> page);

	// 根据返回的映射更新资源ID
	int updateResId(Map map);

	List<Integer> getVmMoIds(Integer moId);

	MODBMSServerBean selectMODBMSServerByKey(Integer moId);

	List<MODeviceObj> getDeviceListByIP(String deviceip);

	MODeviceObj queryMODeviceByResId(Map map);

	// 查询所有设备
	List<MODeviceObj> listAllMODevice();

	MODeviceObj getInfoByIPAndClass(MODeviceObj bean);

	int updateModeviceMOClassID(MODeviceObj bean);
	
	//子网下节点设备 
	LinkedList<MODeviceObj> querySubNetDeviceList(Map map);
	
	/**
	 * auther: 黄振骁
	 * date: 2015/04/16
	 * description: 根据设备id查找设备
	 * input: Map{
	 * 		ids: 域,以逗号隔开 
	 * 		moClassID: 设备类型,以逗号隔开 ,例：(5,6)		
	 * } 
	 * output: List<MODevice>
	 * 
	 * @return
	 */
	List<MODeviceObj> queryMODeviceByIds(Map<String, String> map);
	/**
	 * auther: 黄振骁
	 * date: 2014/12/26
	 * description: 根据用户的作用域查下面的设备
	 * input: Map{
	 * 		domainId: 域,以逗号隔开 
	 * 		moClassID: 设备类型,以逗号隔开 ,例：(5,6)		
	 * } 
	 * output: List<MODevice>
	 * 
	 * @return
	 */
	List<MODeviceObj> queryDeviceByDomainsAndTypes(Map<String, String> map);
	/**
	 * auther: 黄振骁
	 * date: 2014/12/26
	 * description: 查询用户包含的域
	 * input: Map{
	 * 		userId
	 * } 
	 * output: List<MODevice>
	 * 
	 * @return
	 */
	List<String> queryDomainsByUserId(Map<String, String> map);
	
	List<MODeviceObj>  queryUnknownDeviceList(Page<MODeviceObj> page);
	
	List<MODeviceObj> getVms(Integer moId);
	
	List<MODeviceObj> listDeviceByConditions(@Param("taskId")int taskId,@Param("className")String className);
	
	List<MODeviceObj> getPhs(@Param("vCenterIP")String vCenterIP);
	
	int getMoinfoCount(@Param("deviceIp")String deviceIp);
	
	List<MODeviceObj> getDeviceByIpInfo(String deviceip);
	
	List<MODeviceObj> getVCenterDevice(@Param("moId")int moId);
	
	LinkedList<MODeviceObj> getMoDeviceListByAppType(Page<MODeviceObj> page);
	
	List<String> getMoinfoByMOID(@Param("moId")int moId);
	List<MODeviceObj> getMODeviceByMoId(Map<String, Object> map);
    void updateMoAlias(Map<String,Object> map);
}