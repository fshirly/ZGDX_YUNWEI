package com.fable.insightview.monitor.process.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("moProcessInfo")
public class MoProcessBean {
	private Integer id;
	private Date collectTime;
	private Integer deviceMOID;
	private String processName;
	private Integer processID;
	private Integer processType;//1-未知(unknown)，2-系统进程(operatingSystem)，3-设备驱动(deviceDriver)，4-应用进程(application)
	private String deviceIp;
	private String deviceName;
	private Integer processState;
	private Integer processCpu;
	private double processMem;
	private String cpuValue;
	private String memValue;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public Integer getDeviceMOID() {
		return deviceMOID;
	}
	public void setDeviceMOID(Integer deviceMOID) {
		this.deviceMOID = deviceMOID;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public Integer getProcessID() {
		return processID;
	}
	public void setProcessID(Integer processID) {
		this.processID = processID;
	}
	public Integer getProcessType() {
		return processType;
	}
	public void setProcessType(Integer processType) {
		this.processType = processType;
	}
	public String getDeviceIp() {
		return deviceIp;
	}
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public Integer getProcessState() {
		return processState;
	}
	public void setProcessState(Integer processState) {
		this.processState = processState;
	}
	public Integer getProcessCpu() {
		return processCpu;
	}
	public void setProcessCpu(Integer processCpu) {
		this.processCpu = processCpu;
	}
	public double getProcessMem() {
		return processMem;
	}
	public void setProcessMem(double processMem) {
		this.processMem = processMem;
	}
	public String getCpuValue() {
		return cpuValue;
	}
	public void setCpuValue(String cpuValue) {
		this.cpuValue = cpuValue;
	}
	public String getMemValue() {
		return memValue;
	}
	public void setMemValue(String memValue) {
		this.memValue = memValue;
	}
	
}
