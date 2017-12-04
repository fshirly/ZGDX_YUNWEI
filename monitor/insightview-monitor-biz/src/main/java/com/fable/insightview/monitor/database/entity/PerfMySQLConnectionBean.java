package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfMySQLConnectionBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private long maxConnections;
	private long connections;
	private double connUsage;
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

	public long getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(long maxConnections) {
		this.maxConnections = maxConnections;
	}

	public long getConnections() {
		return connections;
	}

	public void setConnections(long connections) {
		this.connections = connections;
	}

	public double getConnUsage() {
		return connUsage;
	}

	public void setConnUsage(double connUsage) {
		this.connUsage = connUsage;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

}
