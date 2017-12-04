package com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.mapper;

import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.entity.AlarmDispatchFilter;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.entity.AlarmDispatchFilterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AlarmDispatchFilterMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table Alarm_Dispatch_Filter
	 * @mbggenerated  Thu May 21 09:52:14 GMT+08:00 2015
	 */
	int countByExample(AlarmDispatchFilterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table Alarm_Dispatch_Filter
	 * @mbggenerated  Thu May 21 09:52:14 GMT+08:00 2015
	 */
	int deleteByExample(AlarmDispatchFilterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table Alarm_Dispatch_Filter
	 * @mbggenerated  Thu May 21 09:52:14 GMT+08:00 2015
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table Alarm_Dispatch_Filter
	 * @mbggenerated  Thu May 21 09:52:14 GMT+08:00 2015
	 */
	int insert(AlarmDispatchFilter record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table Alarm_Dispatch_Filter
	 * @mbggenerated  Thu May 21 09:52:14 GMT+08:00 2015
	 */
	int insertSelective(AlarmDispatchFilter record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table Alarm_Dispatch_Filter
	 * @mbggenerated  Thu May 21 09:52:14 GMT+08:00 2015
	 */
	List<AlarmDispatchFilter> selectByExample(AlarmDispatchFilterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table Alarm_Dispatch_Filter
	 * @mbggenerated  Thu May 21 09:52:14 GMT+08:00 2015
	 */
	AlarmDispatchFilter selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table Alarm_Dispatch_Filter
	 * @mbggenerated  Thu May 21 09:52:14 GMT+08:00 2015
	 */
	int updateByExampleSelective(@Param("record") AlarmDispatchFilter record, @Param("example") AlarmDispatchFilterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table Alarm_Dispatch_Filter
	 * @mbggenerated  Thu May 21 09:52:14 GMT+08:00 2015
	 */
	int updateByExample(@Param("record") AlarmDispatchFilter record, @Param("example") AlarmDispatchFilterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table Alarm_Dispatch_Filter
	 * @mbggenerated  Thu May 21 09:52:14 GMT+08:00 2015
	 */
	int updateByPrimaryKeySelective(AlarmDispatchFilter record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table Alarm_Dispatch_Filter
	 * @mbggenerated  Thu May 21 09:52:14 GMT+08:00 2015
	 */
	int updateByPrimaryKey(AlarmDispatchFilter record);

	List<AlarmDispatchFilter> selectByExampleLimit(AlarmDispatchFilterExample example);
}