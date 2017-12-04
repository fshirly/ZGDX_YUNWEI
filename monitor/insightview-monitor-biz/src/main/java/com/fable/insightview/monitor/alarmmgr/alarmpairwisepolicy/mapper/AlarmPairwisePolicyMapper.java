package com.fable.insightview.monitor.alarmmgr.alarmpairwisepolicy.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmPairwisePolicyBean;
import com.fable.insightview.platform.page.Page;

public interface AlarmPairwisePolicyMapper {
	List<AlarmPairwisePolicyBean> selectAlarmPairwisePolicy(
			Page<AlarmPairwisePolicyBean> page); // 查询所有任务

	/* 根据ID删除 */
	boolean delPairwisePolicy(int ploicyID);

	AlarmPairwisePolicyBean getByCauseIDAndRecoverID(int alarmDefineID);
	
	List<AlarmPairwisePolicyBean> getByCauseIDAndRecoverID2(int alarmDefineID);

	int insertAlarmPairPolicy(AlarmPairwisePolicyBean bean);

	int updateAlarmPairPolicy(AlarmPairwisePolicyBean bean);
}
