package com.fable.insightview.monitor.database.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;


@Alias("PerfOrclDataFileBean")
public class PerfOrclDataFileBean {
	private long id;
	private long moId;
	private Date collectTime;
	private long phyReads;
	private long phyWrites;
	private long blockRead;
	private long blockWrite;
	private long readTime;
	private long writeTime;
	private String fileStatus;
	private long createSize;
	private long currSize;
	private String formatTime;
	public PerfOrclDataFileBean() {
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMoId() {
		return moId;
	}
	public void setMoId(long moId) {
		this.moId = moId;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public long getPhyReads() {
		return phyReads;
	}
	public void setPhyReads(long phyReads) {
		this.phyReads = phyReads;
	}
	public long getPhyWrites() {
		return phyWrites;
	}
	public void setPhyWrites(long phyWrites) {
		this.phyWrites = phyWrites;
	}
	public long getBlockRead() {
		return blockRead;
	}
	public void setBlockRead(long blockRead) {
		this.blockRead = blockRead;
	}
	public long getBlockWrite() {
		return blockWrite;
	}
	public void setBlockWrite(long blockWrite) {
		this.blockWrite = blockWrite;
	}
	public long getReadTime() {
		return readTime;
	}
	public void setReadTime(long readTime) {
		this.readTime = readTime;
	}
	public long getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(long writeTime) {
		this.writeTime = writeTime;
	}
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
	public long getCreateSize() {
		return createSize;
	}
	public void setCreateSize(long createSize) {
		this.createSize = createSize;
	}
	public long getCurrSize() {
		return currSize;
	}
	public void setCurrSize(long currSize) {
		this.currSize = currSize;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	
}
