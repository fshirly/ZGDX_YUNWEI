package com.fable.insightview.monitor.database.entity;

import org.apache.ibatis.type.Alias;


@Alias("moOracleDataFile")
public class MOOracleDataFileBean {
	private Integer moId;
	private Integer dbMoId;
	private Integer tbsMoId;
	private String tbsName;
	private String dataFileName;
	private Integer dataFileId;
	private String dataFileBytes;
	private long dataFileBlocks;
	private String dataFileStatus;
	private Integer relativeID;
	private String autoExtensible;
	private String maxSize;
	private long maxBlocks;
	private Integer increamentBlocks;
	private String userBytes;
	private long userBlocks;
	private String dbName;
	private String relativeFileName;
	private String ip;
	public MOOracleDataFileBean() {
	}
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public Integer getDbMoId() {
		return dbMoId;
	}
	public void setDbMoId(Integer dbMoId) {
		this.dbMoId = dbMoId;
	}
	public Integer getTbsMoId() {
		return tbsMoId;
	}
	public void setTbsMoId(Integer tbsMoId) {
		this.tbsMoId = tbsMoId;
	}
	public String getTbsName() {
		return tbsName;
	}
	public void setTbsName(String tbsName) {
		this.tbsName = tbsName;
	}
	public String getDataFileName() {
		return dataFileName;
	}
	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}
	public Integer getDataFileId() {
		return dataFileId;
	}
	public void setDataFileId(Integer dataFileId) {
		this.dataFileId = dataFileId;
	}
	public long getDataFileBlocks() {
		return dataFileBlocks;
	}
	public void setDataFileBlocks(long dataFileBlocks) {
		this.dataFileBlocks = dataFileBlocks;
	}
	public String getDataFileStatus() {
		return dataFileStatus;
	}
	public void setDataFileStatus(String dataFileStatus) {
		this.dataFileStatus = dataFileStatus;
	}
	public Integer getRelativeID() {
		return relativeID;
	}
	public void setRelativeID(Integer relativeID) {
		this.relativeID = relativeID;
	}
	public String getAutoExtensible() {
		return autoExtensible;
	}
	public void setAutoExtensible(String autoExtensible) {
		this.autoExtensible = autoExtensible;
	}
	public long getMaxBlocks() {
		return maxBlocks;
	}
	public void setMaxBlocks(long maxBlocks) {
		this.maxBlocks = maxBlocks;
	}
	public Integer getIncreamentBlocks() {
		return increamentBlocks;
	}
	public void setIncreamentBlocks(Integer increamentBlocks) {
		this.increamentBlocks = increamentBlocks;
	}
	public long getUserBlocks() {
		return userBlocks;
	}
	public void setUserBlocks(long userBlocks) {
		this.userBlocks = userBlocks;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getRelativeFileName() {
		return relativeFileName;
	}
	public void setRelativeFileName(String relativeFileName) {
		this.relativeFileName = relativeFileName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDataFileBytes() {
		return dataFileBytes;
	}
	public void setDataFileBytes(String dataFileBytes) {
		this.dataFileBytes = dataFileBytes;
	}
	public String getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(String maxSize) {
		this.maxSize = maxSize;
	}
	public String getUserBytes() {
		return userBytes;
	}
	public void setUserBytes(String userBytes) {
		this.userBytes = userBytes;
	}
	
}
