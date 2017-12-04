package com.fable.insightview.monitor.alarmmgr.alarmactive.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmStatusInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.monitor.alarmmgr.entity.MOActiveAlarmStInfo;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.topo.entity.TopoCurrentPerBean;
import com.fable.insightview.platform.page.Page;

/**
 * @Description: 活动告警Dao
 * @author zhengxh
 * @Date 2014-7-16
 */
public interface AlarmActiveMapper {
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmNode> queryList(Page<AlarmNode> page);
	
	/**
	 * 设备快照入口分页查询
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmNode> queryAlarmList(Page<AlarmNode> page);
	
	/**
	 * 网络设备快照入口分页查询
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmNode> queryAlarmListWithDevice(Page<AlarmNode> page);
	
	/**
	 * 3d机房分页查询
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmNode> queryListBy3dRoom(Page<AlarmNode> page);
	
	/**
	 * Topo分页查询
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmNode> queryListByTopo(Page<AlarmNode> page);

	/**
	 * 查询告警状态
	 * 
	 * @return
	 */
	List<AlarmStatusInfo> queryStatusInfo();

	/**
	 * 加载活动告警
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmNode> loadActiveAlarm(Map parmetermap);

	/**
	 * 统计加载活动告警的数量
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmLevelInfo> loadActiveAlarmCount(Map parmetermap);
	
	/**
	 * 3d机房加载活动告警
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmNode>  load3dRoomActiveAlarm(Map parmetermap);
	
	/**
	 * Topo加载活动告警
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmNode>  loadTopoActiveAlarm(Map parmetermap);
	
	/**
	 * Topo加载Link活动告警
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmNode>  loadTopoActiveLinkAlarm(Map parmetermap);
	
	
	/**
	 * 3d机房加载电子标签活动告警
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmNode>  load3dRoomTagActiveAlarm(Map parmetermap);
	
	/**
	 * 3d机房加载最近一条活动告警
	 * 
	 * @param page
	 * @return
	 */
	AlarmNode  load3dRoomLastActiveAlarm(Map parmetermap);

	/**
	 * 查询告警类型
	 * 
	 * @return
	 */
	List<AlarmTypeInfo> queryTypeInfo();
	/**
	 * 过滤条件：查询告警类型
	 * 
	 * @return
	 */
	List<AlarmTypeInfo> queryAlarmTypeList(Page<AlarmTypeInfo> page);
	/**
	 * 查询告警级别
	 * 
	 * @return
	 */
	List<AlarmLevelInfo> queryAlarmLevelList(Page<AlarmLevelInfo> page);
	/**
	 * 查询告警级别
	 * 
	 * @return
	 */
	List<AlarmLevelInfo> queryLevelInfo();

	/**
	 * 查询告警级别的种类并统计个数
	 * 
	 * @return
	 */
	List<AlarmLevelInfo> queryLevelNumByCondition(AlarmNode vo);

	/**
	 * 查询告警类型的种类并统计个数
	 * 
	 * @return
	 */
	List<AlarmTypeInfo> queryTypeNumByCondition(AlarmNode vo);

	/**
	 * 根据id查询对象
	 * 
	 * @param id
	 * @return
	 */
	AlarmNode getInfoById(int id);

	/**
	 * 根据告警ID查询告警列表
	 * @param page
	 * @return
	 */
	List<AlarmNode> getAlarmListByAlarmIds(Page<AlarmNode> page);
	/**
	 * 根据id到历史告警查询
	 * 
	 * @param id
	 * @return
	 */
	AlarmNode getHisInfoById(int id);

	/**
	 * 告警确认操作
	 * 
	 * @param vo
	 * @return
	 */
	int doAlarmActiveConfirm(AlarmNode vo);

	/**
	 * 取消告警确认操作
	 * 
	 * @param id
	 * @return
	 */	
	int cancelAlarmActiveConfirm(int id);
	
	/**
	 * 告警确认操作
	 * 
	 * @param vo
	 * @return
	 */
	int bathAlarmActiveConfirm(AlarmNode vo);

	/**
	 * 取消告警确认操作
	 * 
	 * @param id
	 * @return
	 */	
	int bathCancelActiveConfirm(String id);
	
	/**
	 * 批量告警派发操作
	 * 
	 * @param id
	 * @return
	 */
	int updateAlarmActiveDetail(Map map);
	
	
	/**
	 * 查询是否派发过
	 * 
	 * @param id
	 * @return
	 */
	AlarmNode isExistDispatchRecord(int alarmID);
	
	/**
	 * 取消告警确认操作（区别状态）
	 * 
	 * @param id
	 * @return
	 */
	int cancelConfirm(int id);

	/**
	 * 告警清除
	 * 
	 * @param id
	 * @return
	 */
	int clearAlarmActive(AlarmNode vo);

	/**
	 * 告警删除
	 * 
	 * @param id
	 * @return
	 */
	int deleteAlarmActive(@Param("id")String id);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int deleteAlarminfos(Map dispatchIDMap);
	
	
	/**
	 * 通过SourceMOID和MOClassID查询告警等级	 
	 * @param vo
	 * @return
	 */
	AlarmNode queryAlarmLevelByDevice(AlarmNode vo);
	
	/**
	 * 插入监测对象活动告警统计表
	 * @param parmetermap
	 * @return
	 */
	int insertMOActiveAlarmSt(MOActiveAlarmStInfo vo);
	
	/**
	 * 活动告警统计表累加
	 * @param vo
	 * @return
	 */	
	int addMOActiveAlarmSt(@Param("level") Integer level, @Param("moId") Integer moId);
	
	/**
	 * 活动告警统计表累减
	 * @param vo
	 * @return
	 */	
	int minusMOActiveAlarmSt(@Param("level") Integer level, @Param("moId") Integer moId);
	
	/**
	 * 获取监测对象活动告警统计对象
	 * @param vo
	 * @return
	 */
	MOActiveAlarmStInfo getMOActiveAlarmStObj(@Param("moid") Integer moid);
	
	/**
	 * 获取监测对象活动告警统计对象
	 * @param vo
	 * @return
	 */
	List<MOActiveAlarmStInfo> queryMOActiveAlarmStObj(@Param("resId") String resId);
	
	/**
	 * 获取监测对象活动告警统计对象
	 * @param vo
	 * @return
	 */
	MOActiveAlarmStInfo queryMOActiveAlarmStObjByResId(@Param("resId") String resId);
	
	/**
	 * 清除告警统计数据
	 * @return
	 */
	int clearMOActiveAlarmStInfo();
	
	/**
	 * SourceMoid分组查询最新告警级别
	 * @return
	 */
	List<AlarmNode> distinctSourceMoid();
	
	void alarmDeviceStatisBySourceMOID();
	
	void alarmZoomStatisByMOID();
	
	/**
	 * 统计告警级别的个数	 
	 * @param SourceMOID
	 * @return
	 */
	List<AlarmLevelInfo> queryLevelNumBySourceMOID(@Param("SourceMOID") Integer SourceMOID);
	
	List<AlarmLevelInfo> queryLevelNumByMOID(@Param("MOID") Integer MOID);
	

	int getByAlarmDefineID(int alarmDefineID);
	
	/**
	 * 根据设备MOID查询告警统计对象
	 * @param moIds
	 * @return
	 */
	List<MOActiveAlarmStInfo> queryAlarmNumsByMODevice(@Param("moIds") String moIds);
	
	List<AlarmNode> queryListByAlarmStatis(Page<AlarmNode> page);
	
	// 查询链路告警
	List<AlarmNode> queryLinkAlarm(Page<AlarmNode> page);
	
	List<AlarmLevelInfo> queryAlarmLevelName();
	
	List<AlarmStatusInfo> queryAlarmStatusName();
	
	List<AlarmTypeInfo> queryAlarmTypeName();
	
	// 查询链路当前性能值
	List<TopoCurrentPerBean> queryCurrentPerformance(Map map);
	
	//查询告警Ids
	String queryAlarmIdsByIds(@Param("alarmIds") String alarmIds);
	/**
	 * 设备快照入口分页查询 泰州定制
	 * 
	 * @param page
	 * @return
	 */
	List<AlarmNode> queryTZAlarmList(Page<AlarmNode> page);
	
	/**
	 * 根据告警ID查询告警状态
	 * @param alarmId
	 * @return
	 */
	List<Integer> getAlarmStatusById(List<String> alarmIds);
	
	List<Map<String, Object>> getTopAlarm(Map paramMap);
	
	/**
	 * 分页查询值班服务台告警
	 * 
	 */
	List<AlarmNode> queryListByDutyDesk(Page<AlarmNode> page);
	
	/**
	 * 获得值班服务台的值班详情
	 */
	Map<String, Object> getDutyDeskAlarmDetail(int alarmId);
}
