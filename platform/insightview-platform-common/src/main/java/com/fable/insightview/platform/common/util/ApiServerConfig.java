package com.fable.insightview.platform.common.util;

public class ApiServerConfig {

	private static final String ip ;
	private static final String port;
	private static final String application;

	static{
		ip = SysConfig.getValueByKey("kscc.ip");
		port = SysConfig.getValueByKey("kscc.port");
		application = SysConfig.getValueByKey("kscc.application");
	}
	
	public ApiServerConfig() {}
	
	public static String getSocketAddr() {
		return String.format("http://%s:%s/%s/", ip, port,application) ;
	}

}
