package com.fable.insightview.platform.dutymanager.duty.service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.dutymanager.duty.entity.DutyStatistics;
import com.fable.insightview.platform.dutymanager.duty.entity.PfDuty;
import com.fable.insightview.platform.message.entity.PfMessage;
import com.fable.insightview.platform.page.Page;

public interface IDutyService {

	/**
	 * 查询本单位下值班组中的�?班人
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
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> list(Page page);

	/**
	 * 查询�?��的�?班班次信�?
	 * 
	 * @return
	 */
	List<Map<String, Object>> queryOrders(String ids);

	/**
	 * 验证值班日期是否存在
	 * 
	 * @param dutyDate
	 * @return
	 */
	boolean validateDutyDate(String dutyDate, Integer dutyId);

	/**
	 * 新增值班信息
	 * 
	 * @param duty
	 */
	void insert(PfDuty duty);

	/**
	 * 查询值班信息
	 * 
	 * @param dutyId
	 * @return
	 */
	Map<String, Object> query(int dutyId);

	/**
	 * 查询值班下的值班班次信息
	 * 
	 * @param dutyId
	 * @return
	 */
	List<Map<String, Object>> queryOrders(int dutyId);

	/**
	 * 修改值班记录信息
	 * 
	 * @param duty
	 */
	void update(PfDuty duty);

	/**
	 * 删除值班记录下的�?��班次信息
	 * 
	 * @param orderIdOfDuty
	 */
	void deleteSingleOrder(int orderIdOfDuty);

	/**
	 * 批量删除值班下的班次信息
	 * 
	 * @param orderIds
	 */
	void deleteMultiOrder(String orderIds);

	/**
	 * 删除值班记录
	 * 
	 * @param dutyId
	 */
	void delete(int dutyId);

	/**
	 * 查询多个值班记录信息
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryMulti(Map<String, Object> params);
	
	/**
	 * 查询多个原始值班记录信息
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryMultiOriginal(Map<String, Object> params);

	/**
	 * 导入值班信息
	 * 
	 * @param titles
	 * @param data
	 * @return
	 */
	Map<String, Object> importData(String[] titles, List<Map<Integer, Object>> data, int userId, String minD, String maxD, int recordCount);

	/**
	 * 导出数据
	 * 
	 * @param response
	 * @param data
	 */
	void exportData(OutputStream out, List<Map<String, Object>> data, String order);

	/**
	 * 根据userid查找组织id
	 */
	int findOrgIdByUserId(int userId);

	/**
	 * 获取值班管理的短信提醒信息
	 * 
	 * @return
	 */
	List<PfMessage> queryMsg();
	
	/**
	 * 获取特定时间点的值班信息
	 * @param pointTime
	 * @return
	 */
	Map<String, Object> queryPointInfo (Date pointTime);
	
	/**
	 * 获取特定时间点的值班信息
	 * userType 0-所有, 1-负责人,2-值班人
	 * @param pointTime
	 * @return
	 */
	List<Map<String, Object>> queryPointInfo(Date pointTime, int userType);
	
	/**
	 * 获取时间段,相关值班人信息
	 * @param start
	 * @param end
	 * @param userType
	 * @return
	 */
	List<Map<String, Object>> betweenDuty (Date start, Date end, int userType);
	
	/**
	 * 获取特定时间点的值班负责人
	 * @param pointTime
	 */
	String getPointDuyter (Date pointTime);
	
	/**
	 * 查询特定时间之后所有的值班信息
	 */
	List<Map<String, Object>> findAllDutiesAfterDate(Date date);
	
	/**
	 * 查询某天的所有值班班次信息
	 */
	List<Map<String, Object>> findOrdersOfDutyByDate(Date date);
	
	List<String> findAllDutyersByOrderId(Integer orderId);
	
	/**
	 * 查询特定时间之内的告警信息
	 * @param dateParam  时间格式 startTime,endTime
	 * @return  
	 */
	Map<String,Object> findAlarmInfoByDate(Map<String,Object> dateParam);
	
	/**
	 * 获得当前时间的值班带班领导和值班成员
	 */
	List<String> findCurrentDutyers();
	
	/**
	 * 获得当前时间值班班次的值班负责人
	 */
	List<Map<String, Object>> findCurrentDutyersForZJW();
	
	/**
	 * 根据值班班次获取值班人员信息
	 * @param userType 1-值班负责人  2-值班人
	 * @return
	 */
	List<Map<String,Object>> findUsersOfOrderByUserType(Integer orderId, Integer userType);
	
	/*=========贵州值班服务台Begin============*/
	
	/**
	 * 获取当前时间之前的所有的未交班的值班班次信息
	 * @return
	 */
	List<Map<String, Object>> findOrdersOfDutyBeforeCurrent();
	
	/**
	 * 根据值班ID获取领导交班记录
	 * @param dutyId
	 * @return
	 */
	String findLeaderNoticesByDutyId(Integer dutyId);
	
	/**
	 * 根据值班ID编辑领导交班记录
	 * @param dutyId
	 */
	void editLeaderNoticesByDutyId(String notice, Integer dutyId);
	
	/**
	 * 查询当前时间之前的最新的已交班的班次信息
	 * @return
	 */
	List<Map<String, Object>> findLatestFinishedOrdersOfDuty();
	
	/**
	 * 根据值班班次id和类型查询值班统计记录
	 * @param orderId
	 * @param type 1-值班巡检、2-故障处理、3-申告服务、4-综合处理
	 * @return
	 */
	List<DutyStatistics> findDutyStatisticsByOrderIdAndType(Integer orderId, Integer type);
	
	/*=========贵州值班服务台End============*/
	
	/**
	 * 查询值班班次中所有值班人信息
	 * @param orderId
	 * @return
	 */
	List<Map<String, Object>> queryDutyersOfOder(String orderId);
	
	/**
	 * 验证当前登陆人是否有值班操作权限
	 * @param userAccount
	 * @return
	 */
	String isOperator(String userAccount);
	
	/**
	 * 验证当前是否可以进行交班操作
	 * @return
	 */
	boolean isExchange();
	
	/**
	 * 获取当前时间所在值班班次的开始时间和结束时间
	 * @return map中的key为start和end,类型为Date
	 */
	Map<String, Object> findCurrentOrderTimeForHL();
	
	/**
	 * 查询值班班次信息
	 * @param orderId
	 * @return
	 */
	Map<String, Object> queryOrder(String orderId);
	
	/**
	 * 查询值班班次值班人数
	 * @param orderId
	 * @return
	 */
	int queryUsers(String orderId);
	
	/**
	 * 查询值班班次信息及值班人员
	 * @param orderId
	 * @return
	 */
	List<String> queryDutyersInfo(@Param("orderId")String orderId);
	
	/**
	 * 查询交班信息
	 * @return
	 */
	Map<String, Object> queryExchanges();
}
