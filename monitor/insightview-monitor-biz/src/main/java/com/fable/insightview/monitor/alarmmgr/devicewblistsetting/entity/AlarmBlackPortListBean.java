package com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity;

import java.util.Date;

public class AlarmBlackPortListBean {
    private Integer blackPortID;

    private Integer blackID;

    private Integer MOID;

    private String MOName;

    private String instance;

    private Integer port;

    private String portService;
    
    private String gatewayIP;

    private String portType;

    private String type;

    private String oprateStatus;

    private Date createTime;

    public Integer getBlackPortID() {
        return blackPortID;
    }

    public void setBlackPortID(Integer blackPortID) {
        this.blackPortID = blackPortID;
    }

    public Integer getBlackID() {
        return blackID;
    }

    public void setBlackID(Integer blackID) {
        this.blackID = blackID;
    }

    public Integer getMOID() {
        return MOID;
    }

    public void setMOID(Integer MOID) {
        this.MOID = MOID;
    }

    public String getMOName() {
        return MOName;
    }

    public void setMOName(String MOName) {
        this.MOName = MOName == null ? null : MOName.trim();
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance == null ? null : instance.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPortService() {
        return portService;
    }

    public void setPortService(String portService) {
        this.portService = portService == null ? null : portService.trim();
    }

    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType == null ? null : portType.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getOprateStatus() {
        return oprateStatus;
    }

    public void setOprateStatus(String oprateStatus) {
        this.oprateStatus = oprateStatus == null ? null : oprateStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	/**
	 * @return the gatewayIP
	 */
	public String getGatewayIP() {
		return gatewayIP;
	}

	/**
	 * @param gatewayIP the gatewayIP to set
	 */
	public void setGatewayIP(String gatewayIP) {
		this.gatewayIP = gatewayIP;
	}
}