package com.fable.insightview.monitor.middleware.weblogic.entity;

import java.util.Date;

public class PerfWebLogicJTABean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private long transAbandonedTotal;// 放弃事务数
	private long transTotal;// 事务总数
	private long transRollBackTotal;//回滚事务数
	private long activeTransTotal;// 活动事务数
	private long transHeuristicsTotal;// 启发式事务数
	private long transLLRCommitedTotal;// 已提交 LLR 事务数
	private long transTwoPhaseTotal;// 两阶段提交事务数
	private long transRollBackSysTotal;// 因系统错误而回滚的事务数
	private long transNoResCommittedTotal;// 已提交无资源事务数
	private long transCommittedTotal;// 已提交事务数
	private long transRollBackAppTotal;// 因应用程序错误而回滚的事务数
	private long transRollBackTimeoutTotal;// 因超时而回滚的事务数
	private long transRollBackResTotal;// 因资源错误而回滚的事务数
	private long transReadOnePhaseTotal;// 一阶段提交只读事务数
	private long transOneResOnePhaseTotal;// 一阶段提交单资源事务数
	
	private String kpiname;
	private long perfvalue;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public long getTransAbandonedTotal() {
		return transAbandonedTotal;
	}
	public void setTransAbandonedTotal(long transAbandonedTotal) {
		this.transAbandonedTotal = transAbandonedTotal;
	}
	public long getTransTotal() {
		return transTotal;
	}
	public void setTransTotal(long transTotal) {
		this.transTotal = transTotal;
	}
	public long getTransRollBackTotal() {
		return transRollBackTotal;
	}
	public void setTransRollBackTotal(long transRollBackTotal) {
		this.transRollBackTotal = transRollBackTotal;
	}
	public long getActiveTransTotal() {
		return activeTransTotal;
	}
	public void setActiveTransTotal(long activeTransTotal) {
		this.activeTransTotal = activeTransTotal;
	}
	public long getTransHeuristicsTotal() {
		return transHeuristicsTotal;
	}
	public void setTransHeuristicsTotal(long transHeuristicsTotal) {
		this.transHeuristicsTotal = transHeuristicsTotal;
	}
	public long getTransLLRCommitedTotal() {
		return transLLRCommitedTotal;
	}
	public void setTransLLRCommitedTotal(long transLLRCommitedTotal) {
		this.transLLRCommitedTotal = transLLRCommitedTotal;
	}
	public long getTransTwoPhaseTotal() {
		return transTwoPhaseTotal;
	}
	public void setTransTwoPhaseTotal(long transTwoPhaseTotal) {
		this.transTwoPhaseTotal = transTwoPhaseTotal;
	}
	public long getTransRollBackSysTotal() {
		return transRollBackSysTotal;
	}
	public void setTransRollBackSysTotal(long transRollBackSysTotal) {
		this.transRollBackSysTotal = transRollBackSysTotal;
	}
	public long getTransNoResCommittedTotal() {
		return transNoResCommittedTotal;
	}
	public void setTransNoResCommittedTotal(long transNoResCommittedTotal) {
		this.transNoResCommittedTotal = transNoResCommittedTotal;
	}
	public long getTransCommittedTotal() {
		return transCommittedTotal;
	}
	public void setTransCommittedTotal(long transCommittedTotal) {
		this.transCommittedTotal = transCommittedTotal;
	}
	public long getTransRollBackAppTotal() {
		return transRollBackAppTotal;
	}
	public void setTransRollBackAppTotal(long transRollBackAppTotal) {
		this.transRollBackAppTotal = transRollBackAppTotal;
	}
	public long getTransRollBackTimeoutTotal() {
		return transRollBackTimeoutTotal;
	}
	public void setTransRollBackTimeoutTotal(long transRollBackTimeoutTotal) {
		this.transRollBackTimeoutTotal = transRollBackTimeoutTotal;
	}
	public long getTransRollBackResTotal() {
		return transRollBackResTotal;
	}
	public void setTransRollBackResTotal(long transRollBackResTotal) {
		this.transRollBackResTotal = transRollBackResTotal;
	}
	public long getTransReadOnePhaseTotal() {
		return transReadOnePhaseTotal;
	}
	public void setTransReadOnePhaseTotal(long transReadOnePhaseTotal) {
		this.transReadOnePhaseTotal = transReadOnePhaseTotal;
	}
	public long getTransOneResOnePhaseTotal() {
		return transOneResOnePhaseTotal;
	}
	public void setTransOneResOnePhaseTotal(long transOneResOnePhaseTotal) {
		this.transOneResOnePhaseTotal = transOneResOnePhaseTotal;
	}
	public String getKpiname() {
		return kpiname;
	}
	public void setKpiname(String kpiname) {
		this.kpiname = kpiname;
	}
	public long getPerfvalue() {
		return perfvalue;
	}
	public void setPerfvalue(long perfvalue) {
		this.perfvalue = perfvalue;
	}

	
}
