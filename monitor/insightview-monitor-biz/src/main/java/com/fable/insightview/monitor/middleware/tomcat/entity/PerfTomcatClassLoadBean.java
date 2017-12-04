package com.fable.insightview.monitor.middleware.tomcat.entity;

import org.apache.ibatis.type.Alias;


@Alias("perfTomcatClassLoadBean")
public class PerfTomcatClassLoadBean {
	private Integer moId;
	private Integer parentMoId;
//	private Date collectTime;//采集时间
	private long loadedClasses;//当前加载类数
	private long unloadedClasses;//已卸载类总数
	private long totalLoadedClasses;//已加载类总数
	private String ip;
//	private String formatTime;
	
	private String serverName;
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public long getLoadedClasses() {
		return loadedClasses;
	}
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
	public void setLoadedClasses(long loadedClasses) {
		this.loadedClasses = loadedClasses;
	}
	public long getUnloadedClasses() {
		return unloadedClasses;
	}
	public void setUnloadedClasses(long unloadedClasses) {
		this.unloadedClasses = unloadedClasses;
	}
	public long getTotalLoadedClasses() {
		return totalLoadedClasses;
	}
	public void setTotalLoadedClasses(long totalLoadedClasses) {
		this.totalLoadedClasses = totalLoadedClasses;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
