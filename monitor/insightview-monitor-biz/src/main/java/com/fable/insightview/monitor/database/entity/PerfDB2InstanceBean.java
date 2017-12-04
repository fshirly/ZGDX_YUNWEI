package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfDB2InstanceBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private Integer totalConns;
	private Integer localConns;
	private Integer localConnsExec;
	private Integer remConns;
	private Integer remConnsExec;
	private Integer activeAgents;
	private Integer idleAgents;
	private Integer totalAgents;
	private String formatTime;

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

	public Integer getTotalConns() {
		return totalConns;
	}

	public void setTotalConns(Integer totalConns) {
		this.totalConns = totalConns;
	}

	public Integer getLocalConns() {
		return localConns;
	}

	public void setLocalConns(Integer localConns) {
		this.localConns = localConns;
	}

	public Integer getLocalConnsExec() {
		return localConnsExec;
	}

	public void setLocalConnsExec(Integer localConnsExec) {
		this.localConnsExec = localConnsExec;
	}

	public Integer getRemConns() {
		return remConns;
	}

	public void setRemConns(Integer remConns) {
		this.remConns = remConns;
	}

	public Integer getRemConnsExec() {
		return remConnsExec;
	}

	public void setRemConnsExec(Integer remConnsExec) {
		this.remConnsExec = remConnsExec;
	}

	public Integer getActiveAgents() {
		return activeAgents;
	}

	public void setActiveAgents(Integer activeAgents) {
		this.activeAgents = activeAgents;
	}

	public Integer getIdleAgents() {
		return idleAgents;
	}

	public void setIdleAgents(Integer idleAgents) {
		this.idleAgents = idleAgents;
	}

	public Integer getTotalAgents() {
		return totalAgents;
	}

	public void setTotalAgents(Integer totalAgents) {
		this.totalAgents = totalAgents;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

}
