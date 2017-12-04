package com.fable.insightview.platform.dutymanager.duty.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 为信息发布网站提供接口
 */
public interface IDutyForPublish {

	/**
	 * 获取相应时间点值班信息
	 * @param dutyDate
	 * @return
	 */
	Map<String, Object> getDutying(Date dutyDate);

	/**
	 * 获取时间点明天的值班信息
	 * @param dutyDate
	 * @return
	 */
	Map<String, Object> getTomorrowDutying(Date dutyDate);
	
	/**
	 * 查询信息发布值班信息
	 * @param dutyDate
	 * @return
	 */
	Map<String, Object> getPublishDuty(Date dutyDate);

	/**
	 * 依据条件查询值班信息
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getDuties(Map<String, Object> params) throws Exception;
}
