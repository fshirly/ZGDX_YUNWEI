package com.fable.insightview.monitor.middleware.weblogic.entity;

import java.util.Date;

public class PerfWebLogicJDBCPoolBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private long currCapacity;// 当前连接数
	private long numAvailable;// 可用数量
	private long waitingForConnectionCurrent;// 当前等待连接数
	private long leakedConnection;// 泄漏连接数
	private long activeConnectionsCurrent;// 当前活动连接数
	private long failuresToReconnect;// 重新连接失败数
	private long connectionsTotal;// 连接总数
	private long connectionPoolUsage;// 连接池使用率
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
	public long getCurrCapacity() {
		return currCapacity;
	}
	public void setCurrCapacity(long currCapacity) {
		this.currCapacity = currCapacity;
	}
	public long getNumAvailable() {
		return numAvailable;
	}
	public void setNumAvailable(long numAvailable) {
		this.numAvailable = numAvailable;
	}
	public long getWaitingForConnectionCurrent() {
		return waitingForConnectionCurrent;
	}
	public void setWaitingForConnectionCurrent(long waitingForConnectionCurrent) {
		this.waitingForConnectionCurrent = waitingForConnectionCurrent;
	}
	public long getLeakedConnection() {
		return leakedConnection;
	}
	public void setLeakedConnection(long leakedConnection) {
		this.leakedConnection = leakedConnection;
	}
	public long getActiveConnectionsCurrent() {
		return activeConnectionsCurrent;
	}
	public void setActiveConnectionsCurrent(long activeConnectionsCurrent) {
		this.activeConnectionsCurrent = activeConnectionsCurrent;
	}
	public long getFailuresToReconnect() {
		return failuresToReconnect;
	}
	public void setFailuresToReconnect(long failuresToReconnect) {
		this.failuresToReconnect = failuresToReconnect;
	}
	public long getConnectionsTotal() {
		return connectionsTotal;
	}
	public void setConnectionsTotal(long connectionsTotal) {
		this.connectionsTotal = connectionsTotal;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	public long getConnectionPoolUsage() {
		return connectionPoolUsage;
	}
	public void setConnectionPoolUsage(long connectionPoolUsage) {
		this.connectionPoolUsage = connectionPoolUsage;
	}
	
}
