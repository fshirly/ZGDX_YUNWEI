package com.fable.insightview.monitor.database.entity;

import org.apache.ibatis.type.Alias;

/**
 * ORACLE回滚段监测对象
 * 
 */

@Alias("moOracleRollSEG")
public class MOOracleRollSEGBean {
	/* 监测对象ID */
	private Integer moID;
	/* 数据库对象ID */
	private Integer dbMOID;
	/* 所属表空间对象 */
	private Integer tbsMOID;
	/* 所属表空间名称 */
	private String tbsName;
	/* 段名称 */
	private String segName;
	/* 段ID */
	private Integer segID;
	/* 段大小 */
	private String segSize;
	/* Extent初始大小 */
	private String initialExtent;
	/* Extent缺省增长百分比 */
	private Integer pctIncrease;
	/* Extent 最小数量 */
	private Integer minExtents;
	/* Extent 最大数量 */
	private Integer maxExtents;
	/* 状态 */
	private String segStatus;

	private String dbName;
	
	private String ip;
	
	public Integer getMoID() {
		return moID;
	}

	public void setMoID(Integer moID) {
		this.moID = moID;
	}

	public Integer getDbMOID() {
		return dbMOID;
	}

	public void setDbMOID(Integer dbMOID) {
		this.dbMOID = dbMOID;
	}

	public Integer getTbsMOID() {
		return tbsMOID;
	}

	public void setTbsMOID(Integer tbsMOID) {
		this.tbsMOID = tbsMOID;
	}

	public String getTbsName() {
		return tbsName;
	}

	public void setTbsName(String tbsName) {
		this.tbsName = tbsName;
	}

	public String getSegName() {
		return segName;
	}

	public void setSegName(String segName) {
		this.segName = segName;
	}

	public Integer getSegID() {
		return segID;
	}

	public void setSegID(Integer segID) {
		this.segID = segID;
	}

	public Integer getPctIncrease() {
		return pctIncrease;
	}

	public void setPctIncrease(Integer pctIncrease) {
		this.pctIncrease = pctIncrease;
	}

	public Integer getMinExtents() {
		return minExtents;
	}

	public void setMinExtents(Integer minExtents) {
		this.minExtents = minExtents;
	}

	public Integer getMaxExtents() {
		return maxExtents;
	}

	public void setMaxExtents(Integer maxExtents) {
		this.maxExtents = maxExtents;
	}

	public String getSegStatus() {
		return segStatus;
	}

	public void setSegStatus(String segStatus) {
		this.segStatus = segStatus;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSegSize() {
		return segSize;
	}

	public void setSegSize(String segSize) {
		this.segSize = segSize;
	}

	public String getInitialExtent() {
		return initialExtent;
	}

	public void setInitialExtent(String initialExtent) {
		this.initialExtent = initialExtent;
	}
	
}
