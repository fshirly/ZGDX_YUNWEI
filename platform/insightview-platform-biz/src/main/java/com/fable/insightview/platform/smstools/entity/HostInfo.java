package com.fable.insightview.platform.smstools.entity;

/**
 * 短息网关鑫鑫
 *
 */
public class HostInfo {
	// 短信网关端口号
	private int serverPort;

	// 短信网关IP地址
	private String serverIpAddress;

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerIpAddress() {
		return serverIpAddress;
	}

	public void setServerIpAddress(String serverIpAddress) {
		this.serverIpAddress = serverIpAddress;
	}

	
}
