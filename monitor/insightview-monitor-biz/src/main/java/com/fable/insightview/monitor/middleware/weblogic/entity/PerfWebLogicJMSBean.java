package com.fable.insightview.monitor.middleware.weblogic.entity;

import java.util.Date;

public class PerfWebLogicJMSBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private long connectionsTotal;//连接总数
	private long jmsServersTotal;//JMS服务器总数
	private long connectionsCurrent;//当前连接数
	private long jmsServersHigh;//最大JMS 服务器数
	private long jmsServersCurrent;//当前JMS 服务器数
	private long connectionsHigh;//最大连接数
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
	public long getConnectionsTotal() {
		return connectionsTotal;
	}
	public void setConnectionsTotal(long connectionsTotal) {
		this.connectionsTotal = connectionsTotal;
	}
	public long getJmsServersTotal() {
		return jmsServersTotal;
	}
	public void setJmsServersTotal(long jmsServersTotal) {
		this.jmsServersTotal = jmsServersTotal;
	}
	public long getConnectionsCurrent() {
		return connectionsCurrent;
	}
	public void setConnectionsCurrent(long connectionsCurrent) {
		this.connectionsCurrent = connectionsCurrent;
	}
	public long getJmsServersHigh() {
		return jmsServersHigh;
	}
	public void setJmsServersHigh(long jmsServersHigh) {
		this.jmsServersHigh = jmsServersHigh;
	}
	public long getJmsServersCurrent() {
		return jmsServersCurrent;
	}
	public void setJmsServersCurrent(long jmsServersCurrent) {
		this.jmsServersCurrent = jmsServersCurrent;
	}
	public long getConnectionsHigh() {
		return connectionsHigh;
	}
	public void setConnectionsHigh(long connectionsHigh) {
		this.connectionsHigh = connectionsHigh;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	
	
}
