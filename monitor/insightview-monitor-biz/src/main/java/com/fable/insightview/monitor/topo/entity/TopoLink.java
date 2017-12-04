package com.fable.insightview.monitor.topo.entity;

public class TopoLink {
	private Integer id;
	private Integer sourceMOID;
	private Integer sourceMOClassID;
	private Integer sourcePort;
	private Integer desMOID;
	private Integer desMOClassID;
	private Integer desPort;
	private Integer linkType;
	private Integer linkStatus;
	private Integer alarmLevel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSourceMOID() {
		return sourceMOID;
	}

	public void setSourceMOID(Integer sourceMOID) {
		this.sourceMOID = sourceMOID;
	}

	public Integer getSourceMOClassID() {
		return sourceMOClassID;
	}

	public void setSourceMOClassID(Integer sourceMOClassID) {
		this.sourceMOClassID = sourceMOClassID;
	}

	public Integer getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(Integer sourcePort) {
		this.sourcePort = sourcePort;
	}

	public Integer getDesMOID() {
		return desMOID;
	}

	public void setDesMOID(Integer desMOID) {
		this.desMOID = desMOID;
	}

	public Integer getDesMOClassID() {
		return desMOClassID;
	}

	public void setDesMOClassID(Integer desMOClassID) {
		this.desMOClassID = desMOClassID;
	}

	public Integer getDesPort() {
		return desPort;
	}

	public void setDesPort(Integer desPort) {
		this.desPort = desPort;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public Integer getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(Integer linkStatus) {
		this.linkStatus = linkStatus;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
}
