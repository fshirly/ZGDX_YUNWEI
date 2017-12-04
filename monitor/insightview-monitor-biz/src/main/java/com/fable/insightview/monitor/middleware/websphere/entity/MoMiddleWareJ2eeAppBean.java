package com.fable.insightview.monitor.middleware.websphere.entity;

import org.apache.ibatis.type.Alias;


@Alias("moMiddleWareJ2eeAppBean")
public class MoMiddleWareJ2eeAppBean {
	private Integer moId;

	private Integer parentMoId;

	private String uri;

	private String appName;

	private String parentMoName;

	public MoMiddleWareJ2eeAppBean() {
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

	public String getParentMoName() {
		return parentMoName;
	}

	public void setParentMoName(String parentMoName) {
		this.parentMoName = parentMoName;
	}

}
