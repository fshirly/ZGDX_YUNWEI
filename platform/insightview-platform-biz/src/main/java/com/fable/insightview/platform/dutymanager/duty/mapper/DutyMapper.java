package com.fable.insightview.platform.dutymanager.duty.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.dutymanager.duty.entity.DutyStatistics;
import com.fable.insightview.platform.dutymanager.duty.entity.PfDuty;
import com.fable.insightview.platform.page.Page;

public interface DutyMapper {

	/**
	 * 查询单位下的值班组下的值班人员
	 * 
	 * @param userId
	 * @return
	 */
	List<Map<String, String>> queryDutyers(Map<String, Object> params);

	/**
	 * 查询值班列表信息
	 * 
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> list(Page page);

	/**
	 * 查询所有值班班次信息
	 * 
	 * @return
	 */
	List<Map<String, Object>> queryOrders(@Param("ids") String ids);

	/**
	 * 根据值班日期查询值班信息
	 * 
	 * @param dutyDate
	 * @return
	 */
	Map<String, Object> queryByDutyDate(@Param("dutyDate") String dutyDate, @Param("dutyId") Integer dutyId);

	/**
	 * 新增值班信息
	 * 
	 * @param duty
	 */
	void insert(PfDuty duty);

	/**
	 * 删除值班信息
	 * 
	 * @param dutyId
	 */
	void delete(@Param("dutyId") int dutyId);

	/**
	 * 修改值班信息
	 * 
	 * @param duty
	 */
	void update(PfDuty duty);

	/**
	 * 查询值班信息
	 * 
	 * @param dutyId
	 * @return
	 */
	Map<String, Object> query(@Param("dutyId") int dutyId);

	/**
	 * 删除值班记录中的日志
	 * 
	 * @param dutyId
	 */
	void deleteLogs(@Param("dutyId") int dutyId);

	/**
	 * 删除值班记录变更信息
	 * 
	 * @param dutyId
	 */
	void deleteChanges(@Param("dutyId") int dutyId);

	/**
	 * 查询多个值班信息记录
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryMulti(Map<String, Object> params);

	/**
	 * 根据userId获取OrgId
	 */
	int selectOrgidByUserid(int userId);
	
	/**
	 * 获取日期范围中的值班人信息
	 * @param start
	 * @param end
	 * @param userType 0-所有, 1-负责人,2-值班人
	 * @return
	 */
	List<Map<String, Object>> queryRange(@Param("start")Date start, @Param("end")Date end, @Param("userType")int userType);
	
	List<Map<String, Object>> selectAllDutiesAfterDate(@Param("date")Date date);
	
	/**
	 * 获取某天的所有值班班次信息
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> selectOrdersOfDutyByDate(@Param("date")Date date);
	
	/**
	 * 获取某一值班班次下的所有值班人
	 * @param orderId
	 * @return
	 */
	List<String> selectAllDutyersByOrderId(@Param("orderId")Integer orderId);
	
	/**
	 * 紧急告警数量
	 */
	int findUrgencyAlarmCount(Map<String,Object> dateParam);
	
	/**
	 * 紧急告警解决数量
	 */
	int findUrgencyAlarmAlreadyDoneCount(Map<String,Object> dateParam);
	
	/**
	 * 已经解决告警工单数量
	 */
	int findAllAlarmDoneCount(Map<String,Object> dateParam);
	
	/**
	 * 已经解决的故障单数量
	 */
	int findEventAlreadyDoneCount(Map<String,Object> dateParam); 

	/**
	 * 根据值班班次获取值班人员信息
	 * userType 1-值班负责人  2-值班人
	 * @param orderId
	 * @param userType
	 * @return
	 */
	List<Map<String,Object>> selectUsersOfOrderByUserType(@Param("orderId")Integer orderId, @Param("userType")Integer userType);
	
	/*=========贵州值班服务台Begin============*/
	
	/**
	 * 获取当前时间之前的所有的值班班次信息
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> selectOrdersOfDutyBeforeCurrent(@Param("date")Date date);
	
	/**
	 * 获取领导交班时的记录
	 * @param dutyId
	 * @return
	 */
	String selectLeaderNoticesByDutyId(@Param("dutyId")Integer dutyId);
	
	/**
	 * 编辑领导交班时的记录
	 * @param dutyId
	 * @return
	 */
	int updateLeaderNoticesByDutyId(@Param("notice")String notice, @Param("dutyId")Integer dutyId);
	
	/**
	 * 查询当前时间之前的最新的已交班的班次信息
	 */
	List<Map<String, Object>> selectLatestOrdersOfDuty(@Param("date")Date date);
	
	/**
	 * 根据值班班次id和类型查询值班统计记录
	 * @param orderId
	 * @param type 1-值班巡检、2-故障处理、3-申告服务、4-综合处理
	 * @return
	 */
	List<DutyStatistics> selectDutyStatisticsByOrderIdAndType(@Param("orderId")Integer orderId, @Param("type")Integer type);
	
	/*=========贵州值班服务台End============*/
	
	/**
	 * 查询值班班次中所有值班人信息
	 * @param orderId
	 * @return
	 */
	List<Map<String, Object>> queryDutyersOfOder(@Param("orderId")String orderId);
	
	/**
	 * 查询值班班次信息及值班人员
	 * @param orderId
	 * @return
	 */
	List<String> queryDutyersInfo(@Param("orderId")String orderId);

}
