package com.fable.insightview.monitor.perf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.perf.entity.MoInfoBean;
import com.fable.insightview.monitor.perf.entity.PerfPollTaskBean;
import com.fable.insightview.monitor.perf.entity.PerfTaskInfoBean;
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;

public interface PerfPollTaskMapper {
	List<PerfPollTaskBean> selectPerfPollTasks(Page<PerfPollTaskBean> page); // 查询所有任务

	int getTotalTasks(PerfPollTaskBean taskBean);

	// List<String> getMoList(int neManufacturerID,int neCategoryID);
	List<String> getMoList(int moId); // 根据设备Id获取其所有监测器

	List<String> getMoListByTaskId(int taskId);// 根据任务ID查询此任务包含的监测器

	boolean delTask(int taskId);

	int insertTaskInfo(PerfTaskInfoBean bean);// 向表SysPerfPollTask插数据

	int addTaskSnmp(PerfTaskInfoBean bean);// 新增snmp认证信息

	int insertTaskMo(MoInfoBean bean); // 向表SysPerfPollMonitors插数据

	int getMaxId();

	String getProcessByTaskId(int taskId);

	int getMonitorCounts(int taskId);

	String getServerNameByTaskId(int taskId);

	PerfPollTaskBean getTaskByTaskId(int taskId);

	int addSnmpCommunity(SNMPCommunityBean bean);
	
	int updateTaskClassID(PerfTaskInfoBean bean);
	
	//根据厂商获取监测器
	List<String> getMoByManufacturer(int moId);
	
	List<SNMPCommunityBean> getByIP(SNMPCommunityBean bean);
	
	List<PerfPollTaskBean> getVCenterTask(int moId);
	
	/**
	 * 获得分页查询的离线采集任务
	 */
	List<PerfPollTaskBean> getOfflinePerfTask(Page<PerfPollTaskBean> page);
	
	List<PerfPollTaskBean> getVCenterTask2(PerfTaskInfoBean bean);
}
