package com.fable.insightview.monitor.middleware.tomcat.entity;

/**
 * WEBModule资源
 * 
 */
public class MOMiddleWareWebModuleBean {
	private Integer moId;
	private Integer parentMOID;
	private String warName;
	private String status;
	private String parentMoName;
	private String parentServerName;

	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public Integer getParentMOID() {
		return parentMOID;
	}

	public void setParentMOID(Integer parentMOID) {
		this.parentMOID = parentMOID;
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

	public String getParentMoName() {
		return parentMoName;
	}

	public void setParentMoName(String parentMoName) {
		this.parentMoName = parentMoName;
	}

	public String getParentServerName() {
		return parentServerName;
	}

	public void setParentServerName(String parentServerName) {
		this.parentServerName = parentServerName;
	}
}
