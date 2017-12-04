package com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity;

import java.util.Date;

public class AlarmBlackListBean {
    private Integer blackID;

    private Integer MOID;

    private String deviceIP;
    
    private String connIPAddresss;

    private String portType;

    private String oprateStatus;

    private Date createTime;
    
    private Integer collectorID;
    
    private String collectorIPAddr;
    
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

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP == null ? null : deviceIP.trim();
    }

    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType == null ? null : portType.trim();
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
	 * @return the collectorID
	 */
	public Integer getCollectorID() {
		return collectorID;
	}

	/**
	 * @param collectorID the collectorID to set
	 */
	public void setCollectorID(Integer collectorID) {
		this.collectorID = collectorID;
	}

	/**
	 * @return the collectorIPAddr
	 */
	public String getCollectorIPAddr() {
		return collectorIPAddr;
	}

	/**
	 * @param collectorIPAddr the collectorIPAddr to set
	 */
	public void setCollectorIPAddr(String collectorIPAddr) {
		this.collectorIPAddr = collectorIPAddr;
	}

	/**
	 * @return the connIPAddresss
	 */
	public String getConnIPAddresss() {
		return connIPAddresss;
	}

	/**
	 * @param connIPAddresss the connIPAddresss to set
	 */
	public void setConnIPAddresss(String connIPAddresss) {
		this.connIPAddresss = connIPAddresss;
	}
}