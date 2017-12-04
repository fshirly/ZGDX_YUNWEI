package com.fable.insightview.monitor.database.entity;


import java.util.Date;

import org.apache.ibatis.type.Alias;


@Alias("MOSybaseDatabase")
public class MOSybaseDatabaseBean {
    private Integer moId;

    private Integer serverMoId;
    
    private String ip;

    private String databaseName;

    private String databaseOwner;

    private Integer databaseID;

    private Date createTime;
    
    private String databaseOptions;

    private String totalSize;
    
    private String logSize;

    private String dataSize;
    
    private String pageSize;
    
    private String usedSize;

    private String freeSize;

	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public Integer getServerMoId() {
		return serverMoId;
	}

	public void setServerMoId(Integer serverMoId) {
		this.serverMoId = serverMoId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDatabaseOwner() {
		return databaseOwner;
	}

	public void setDatabaseOwner(String databaseOwner) {
		this.databaseOwner = databaseOwner;
	}

	public Integer getDatabaseID() {
		return databaseID;
	}

	public void setDatabaseID(Integer databaseID) {
		this.databaseID = databaseID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDatabaseOptions() {
		return databaseOptions;
	}

	public void setDatabaseOptions(String databaseOptions) {
		this.databaseOptions = databaseOptions;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getLogSize() {
		return logSize;
	}

	public void setLogSize(String logSize) {
		this.logSize = logSize;
	}

	public String getDataSize() {
		return dataSize;
	}

	public void setDataSize(String dataSize) {
		this.dataSize = dataSize;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getUsedSize() {
		return usedSize;
	}

	public void setUsedSize(String usedSize) {
		this.usedSize = usedSize;
	}

	public String getFreeSize() {
		return freeSize;
	}

	public void setFreeSize(String freeSize) {
		this.freeSize = freeSize;
	}
	
	
}