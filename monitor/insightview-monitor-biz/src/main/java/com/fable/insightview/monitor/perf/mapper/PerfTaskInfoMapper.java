package com.fable.insightview.monitor.perf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.perf.entity.PerfPollTaskBean;
import com.fable.insightview.monitor.perf.entity.PerfTaskInfoBean;
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;
import com.fable.insightview.platform.snmpcommunity.entity.SysVMIfCommunityBean;

public interface PerfTaskInfoMapper {
	PerfTaskInfoBean getDeviceById(int moId);// 根据设备ID查询设备信息

	PerfTaskInfoBean getDeviceByTaskId(int taskId);// 根据任务ID查询设备信息

	int updateTask(PerfTaskInfoBean bean);
	
	int updateTask2(PerfTaskInfoBean bean);

	int deleteMoList(int taskId);

	int updateTaskStatus(PerfPollTaskBean taskBean);

	int delTask(PerfPollTaskBean taskBean);

	int updateSnmpInfo(PerfTaskInfoBean bean);

	int isExsitSnmp(PerfTaskInfoBean bean);

	int isExsitTask(int moId);// 任务是否存在

	int exsitTaskId(int moId);

	void updateTaskStatusByZookeeper(PerfTaskInfoBean taskInfo);

	PerfTaskInfoBean getTaskInfoByMoId(int moId);

	int updateVMIfCommunity(SysVMIfCommunityBean bean);

	PerfTaskInfoBean getTaskInfoByTaskId(int taskId);

	PerfTaskInfoBean getPerTaskInfoByTaskId(int taskId);

	SysVMIfCommunityBean getVmIfCommunityInfo(int taskId);

	PerfTaskInfoBean getByTaskIdAndMOID(int moId);

	PerfTaskInfoBean getVmwareDeviceInfo(int moId);

	int updateTaskProgressStatus(PerfPollTaskBean taskBean);

	PerfTaskInfoBean getMiddleWareTask(int taskId);

	int updateSnmpCommunity(SNMPCommunityBean bean);

	PerfTaskInfoBean getTaskByMOIDAndClass(@Param("moid") int moid,
			@Param("moClassId") int moClassId);

	PerfTaskInfoBean getTaskByTaskId(int taskId);
	
	SysVMIfCommunityBean getVmIfCommunityByIP(String deviceIP);
	
	PerfTaskInfoBean getRoomTask(int taskId);
	
	int isExsitTask2(PerfTaskInfoBean bean);// 任务是否存在
	
	PerfTaskInfoBean getTaskInfoByMoId2(int moId);
	
	PerfTaskInfoBean getTaskByMOIDAndClassAndDBName(@Param("moid") int moid,@Param("moClassId") int moClassId,@Param("dbName") String dbName);
	
	int updateTask3(PerfTaskInfoBean bean);
	
	int getByCollector(int collectorId);// 任务是否存在
	
	PerfTaskInfoBean getDNSTask(int taskId);
	
	PerfTaskInfoBean getFtpTask(int taskId);
	
	PerfTaskInfoBean getHttpTask(int taskId);
	
	PerfTaskInfoBean getTcpTask(int taskId);
	
	PerfTaskInfoBean getDeviceById2(int moId);
	
	PerfTaskInfoBean getTaskByMOIDAndDBName(@Param("moid") int moid,@Param("dbName") String dbName);
	
	int isExsitTask3(PerfTaskInfoBean bean);
	
	PerfTaskInfoBean getTaskByMOIDAndDBName2(PerfTaskInfoBean bean);
	
	int isExistOfflineTask(int moId);// 任务是否存在

	int existOfflineTaskId(int moId);
	
	List<PerfTaskInfoBean> getOfflineTaskByHost(int collectorId);
}
