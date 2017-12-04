package com.fable.insightview.monitor.middleware.tomcat.entity;

import org.apache.ibatis.type.Alias;


@Alias("tomcatWebModuleBean")
public class TomcatWebModuleBean {
	private Integer moId;
	private Integer parentMoId;
	private String warName;
	private String status;
	private String uri;
	private String appName;
	public TomcatWebModuleBean() {
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

	public String getWarName() {
		return warName;
	}
	public void setWarName(String warName) {
		this.warName = warName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
}
