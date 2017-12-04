package com.fable.insightview.monitor.middleware.websphere.entity;

import org.apache.ibatis.type.Alias;


@Alias("moMidWareJdbcPoolBean")
public class MoMidWareJdbcPoolBean {
	private Integer moId;
	private Integer parentMOID;
	private String dsName;
	private String jdbcDriver;
	private String poolUrl;
	private String driverName;
	private Integer minConns;
	private Integer maxConns;
	private Integer curConns;
	private Integer connTimeOut;
	private String status;
	public MoMidWareJdbcPoolBean() {
	}
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public Integer getParentMOID() {
		return parentMOID;
	}
	public void setParentMOID(Integer parentMOID) {
		this.parentMOID = parentMOID;
	}
	public String getDsName() {
		return dsName;
	}
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	public String getJdbcDriver() {
		return jdbcDriver;
	}
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}
	public String getPoolUrl() {
		return poolUrl;
	}
	public void setPoolUrl(String poolUrl) {
		this.poolUrl = poolUrl;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Integer getMinConns() {
		return minConns;
	}
	public void setMinConns(Integer minConns) {
		this.minConns = minConns;
	}
	public Integer getMaxConns() {
		return maxConns;
	}
	public void setMaxConns(Integer maxConns) {
		this.maxConns = maxConns;
	}
	public Integer getCurConns() {
		return curConns;
	}
	public void setCurConns(Integer curConns) {
		this.curConns = curConns;
	}
	public Integer getConnTimeOut() {
		return connTimeOut;
	}
	public void setConnTimeOut(Integer connTimeOut) {
		this.connTimeOut = connTimeOut;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
