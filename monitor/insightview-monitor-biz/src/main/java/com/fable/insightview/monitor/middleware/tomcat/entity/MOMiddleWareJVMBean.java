package com.fable.insightview.monitor.middleware.tomcat.entity;

import org.apache.ibatis.type.Alias;


@Alias("moMiddleWareJVMBean")
public class MOMiddleWareJVMBean {
	private Integer moId;//资源标识
	private Integer parentMoId;//父对象标识
	private String jvmVendor;//虚拟机厂商
	private String jvmVersion;//虚拟机版本
	private String classPath;//类路径
	private String jvmName;//虚拟机名称
	private String javaVendor;//JAVA厂商
	private String javaVersion;//JAVA版本
	private String vlassPath;//类路径
	private String inputArguments;//运行时参数
	private String upTime;//已运行时间
	private Integer heapSizeInit;//堆内存初始值
	private String heapSizeMax;//堆内存最大值
	private Integer heapSizeMin;//堆内存最小值
	private Integer nheapSizeInit;//非堆内存初始值
	private Integer nheapSizeMax;//非堆内存最大值
	private Integer nheapSizeMin;//非堆内存最小值
	private Integer gcInfo;//GC信息
	private Integer stackInfo;//堆栈信息
	private String ip;
	
	private double heapMax; //内存最大值
	private double heapSize; //总内存
	private double heapFree; //空闲内存
	private String heapMaxValue; //内存最大值 加单位
	private String heapSizeValue; //总内存 加单位
	private String heapFreeValue; //空闲内存 加单位
	
	private String oSName;
	private String oSVersion;
	
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public Integer getParentMoId() {
		return parentMoId;
	}
	public void setParentMoId(Integer parentMoId) {
		this.parentMoId = parentMoId;
	}
	public String getJvmVendor() {
		return jvmVendor;
	}
	public void setJvmVendor(String jvmVendor) {
		this.jvmVendor = jvmVendor;
	}
	public String getJvmVersion() {
		return jvmVersion;
	}
	public void setJvmVersion(String jvmVersion) {
		this.jvmVersion = jvmVersion;
	}
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	public String getInputArguments() {
		return inputArguments;
	}
	public void setInputArguments(String inputArguments) {
		this.inputArguments = inputArguments;
	}
	
	public Integer getHeapSizeInit() {
		return heapSizeInit;
	}
	public void setHeapSizeInit(Integer heapSizeInit) {
		this.heapSizeInit = heapSizeInit;
	}
	
	public Integer getHeapSizeMin() {
		return heapSizeMin;
	}
	public void setHeapSizeMin(Integer heapSizeMin) {
		this.heapSizeMin = heapSizeMin;
	}
	public Integer getNheapSizeInit() {
		return nheapSizeInit;
	}
	public void setNheapSizeInit(Integer nheapSizeInit) {
		this.nheapSizeInit = nheapSizeInit;
	}
	public Integer getNheapSizeMax() {
		return nheapSizeMax;
	}
	public void setNheapSizeMax(Integer nheapSizeMax) {
		this.nheapSizeMax = nheapSizeMax;
	}
	public Integer getNheapSizeMin() {
		return nheapSizeMin;
	}
	public void setNheapSizeMin(Integer nheapSizeMin) {
		this.nheapSizeMin = nheapSizeMin;
	}
	public Integer getGcInfo() {
		return gcInfo;
	}
	public void setGcInfo(Integer gcInfo) {
		this.gcInfo = gcInfo;
	}
	public Integer getStackInfo() {
		return stackInfo;
	}
	public void setStackInfo(Integer stackInfo) {
		this.stackInfo = stackInfo;
	}
	public String getJavaVendor() {
		return javaVendor;
	}
	public void setJavaVendor(String javaVendor) {
		this.javaVendor = javaVendor;
	}
	public String getJavaVersion() {
		return javaVersion;
	}
	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}
	public String getJvmName() {
		return jvmName;
	}
	public void setJvmName(String jvmName) {
		this.jvmName = jvmName;
	}
	public String getVlassPath() {
		return vlassPath;
	}
	public void setVlassPath(String vlassPath) {
		this.vlassPath = vlassPath;
	}
	public double getHeapMax() {
		return heapMax;
	}
	public void setHeapMax(double heapMax) {
		this.heapMax = heapMax;
	}
	public double getHeapSize() {
		return heapSize;
	}
	public void setHeapSize(double heapSize) {
		this.heapSize = heapSize;
	}
	public double getHeapFree() {
		return heapFree;
	}
	public void setHeapFree(double heapFree) {
		this.heapFree = heapFree;
	}
	public String getHeapMaxValue() {
		return heapMaxValue;
	}
	public void setHeapMaxValue(String heapMaxValue) {
		this.heapMaxValue = heapMaxValue;
	}
	public String getHeapSizeValue() {
		return heapSizeValue;
	}
	public void setHeapSizeValue(String heapSizeValue) {
		this.heapSizeValue = heapSizeValue;
	}
	public String getHeapFreeValue() {
		return heapFreeValue;
	}
	public void setHeapFreeValue(String heapFreeValue) {
		this.heapFreeValue = heapFreeValue;
	}
	public String getoSName() {
		return oSName;
	}
	public void setoSName(String oSName) {
		this.oSName = oSName;
	}
	public String getoSVersion() {
		return oSVersion;
	}
	public void setoSVersion(String oSVersion) {
		this.oSVersion = oSVersion;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUpTime() {
		return upTime;
	}
	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}
	public String getHeapSizeMax() {
		return heapSizeMax;
	}
	public void setHeapSizeMax(String heapSizeMax) {
		this.heapSizeMax = heapSizeMax;
	}
	

}
