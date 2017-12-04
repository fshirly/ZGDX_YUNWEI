package com.fable.insightview.monitor.deleteMonitorObject.entity;

public class ServiceBean {

	private int MOID;
	private int MOClassID;
	/**
	 * 设备上服务的类型
	 */
	private String type;
	/**
	 * 设备上服务的IP
	 */
	private String ip;
	
	private String serviceName;
	private String  MOName;
	private int port;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getMOID() {
		return MOID;
	}
	public void setMOID(int mOID) {
		MOID = mOID;
	}
	public int getMOClassID() {
		return MOClassID;
	}
	public void setMOClassID(int mOClassID) {
		MOClassID = mOClassID;
	}
	public String getMOName() {
		return MOName;
	}
	public void setMOName(String mOName) {
		MOName = mOName;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
