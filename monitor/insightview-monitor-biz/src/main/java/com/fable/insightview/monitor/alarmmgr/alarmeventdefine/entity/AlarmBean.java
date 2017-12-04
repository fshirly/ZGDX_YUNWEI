package com.fable.insightview.monitor.alarmmgr.alarmeventdefine.entity;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmOriginalEventFilterBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmPairwisePolicyBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmRepeatPolicyBean;
import com.fable.insightview.monitor.alarmmgr.entity.SysTrapTaskBean;

public class AlarmBean {
	private AlarmEventDefineBean causeAlarmEvent;
	private AlarmEventDefineBean recoverAlamEvent;
	private AlarmRepeatPolicyBean alarmRepeat;
	private AlarmPairwisePolicyBean alarmPairwisePolicy;
	private AlarmOriginalEventFilterBean alarmFilter;
	private SysTrapTaskBean trapTask;

	public AlarmEventDefineBean getCauseAlarmEvent() {
		return causeAlarmEvent;
	}

	public void setCauseAlarmEvent(AlarmEventDefineBean causeAlarmEvent) {
		this.causeAlarmEvent = causeAlarmEvent;
	}

	public AlarmEventDefineBean getRecoverAlamEvent() {
		return recoverAlamEvent;
	}

	public void setRecoverAlamEvent(AlarmEventDefineBean recoverAlamEvent) {
		this.recoverAlamEvent = recoverAlamEvent;
	}

	public AlarmRepeatPolicyBean getAlarmRepeat() {
		return alarmRepeat;
	}

	public void setAlarmRepeat(AlarmRepeatPolicyBean alarmRepeat) {
		this.alarmRepeat = alarmRepeat;
	}

	public AlarmPairwisePolicyBean getAlarmPairwisePolicy() {
		return alarmPairwisePolicy;
	}

	public void setAlarmPairwisePolicy(
			AlarmPairwisePolicyBean alarmPairwisePolicy) {
		this.alarmPairwisePolicy = alarmPairwisePolicy;
	}

	public AlarmOriginalEventFilterBean getAlarmFilter() {
		return alarmFilter;
	}

	public void setAlarmFilter(AlarmOriginalEventFilterBean alarmFilter) {
		this.alarmFilter = alarmFilter;
	}

	public SysTrapTaskBean getTrapTask() {
		return trapTask;
	}

	public void setTrapTask(SysTrapTaskBean trapTask) {
		this.trapTask = trapTask;
	}

}
