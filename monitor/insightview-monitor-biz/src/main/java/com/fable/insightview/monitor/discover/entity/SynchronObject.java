package com.fable.insightview.monitor.discover.entity;

import java.util.Date; 
import java.util.List;

public class SynchronObject {
	
//	{"transferor":"monitor"," transfertime":"2014-08-19 12:12:12","batchid":"1000","process":"1/10","data":
//	[{"resTypeId":1, "moId":123321,moTypeId:1,resId:0,"content":{"moname":"dev1"}}]}
	
	private String transferor;
	private Date transfertime;
	private long batchid;
	private String process;
	private int size;
	private List<SynchronConstant> data;
	 
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public List<SynchronConstant> getData() {
		return data;
	}
	public void setData(List<SynchronConstant> data) {
		this.data = data;
	}
	public String getTransferor() {
		return transferor;
	}
	public void setTransferor(String transferor) {
		this.transferor = transferor;
	}
	public Date getTransfertime() {
		return transfertime;
	}
	public void setTransfertime(Date transfertime) {
		this.transfertime = transfertime;
	}
	public long getBatchid() {
		return batchid;
	}
	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
}