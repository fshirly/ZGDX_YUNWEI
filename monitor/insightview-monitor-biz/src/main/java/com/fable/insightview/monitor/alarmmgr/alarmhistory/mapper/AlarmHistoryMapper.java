package com.fable.insightview.monitor.alarmmgr.alarmhistory.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmHistoryInfo;
import com.fable.insightview.platform.page.Page;

/**
 * @Description:   历史告警
 * @author         zhengxh
 * @Date           2014-7-25
 */
public interface AlarmHistoryMapper {
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	List<AlarmHistoryInfo>  queryList(Page<AlarmHistoryInfo> page);
	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	AlarmHistoryInfo getInfoById(int id);
	/**
	 * 插入历史告警信息
	 * @param vo
	 * @return
	 */
	int insertInfo(AlarmHistoryInfo vo);
	/**
	 * 复制活动告警信息,删除告警信息
	 * @param vo
	 * @return
	 */
	int copyActiveDeleteInfo(AlarmHistoryInfo vo);
	/**
	 * 复制活动告警信息,清除告警信息
	 * @param vo
	 * @return
	 */
	int copyActiveClearInfo(AlarmHistoryInfo vo);
	
	/**
	 * 复制告警信息,
	 * @param vo
	 * @return
	 */
	int insertActiveInfo(Map  parMap);
	
}
