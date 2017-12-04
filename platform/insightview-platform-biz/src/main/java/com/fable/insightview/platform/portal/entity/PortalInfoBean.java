package com.fable.insightview.platform.portal.entity;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;


@Alias("portalInfo")
public class PortalInfoBean {
	@NumberGenerator(name = "MonitorPortalPKportalId")
	private Integer portalId;
	private String portalName;
	private String portalContent;
	private Integer portalType;
	private String portalDesc;
	private Integer creator;
	private Timestamp createTime;
	private Integer updator;
	private Timestamp lastUpdateTime;
	private Integer ownerUserId;
	private Integer ownerRoleId;
	private String portalIds;
	public PortalInfoBean() {
	}
	public Integer getPortalId() {
		return portalId;
	}
	public void setPortalId(Integer portalId) {
		this.portalId = portalId;
	}
	public String getPortalName() {
		return portalName;
	}
	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}
	public String getPortalContent() {
		return portalContent;
	}
	public void setPortalContent(String portalContent) {
		this.portalContent = portalContent;
	}
	public Integer getPortalType() {
		return portalType;
	}
	public void setPortalType(Integer portalType) {
		this.portalType = portalType;
	}
	public String getPortalDesc() {
		return portalDesc;
	}
	public void setPortalDesc(String portalDesc) {
		this.portalDesc = portalDesc;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Integer getUpdator() {
		return updator;
	}
	public void setUpdator(Integer updator) {
		this.updator = updator;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Integer getOwnerUserId() {
		return ownerUserId;
	}
	public void setOwnerUserId(Integer ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	public Integer getOwnerRoleId() {
		return ownerRoleId;
	}
	public void setOwnerRoleId(Integer ownerRoleId) {
		this.ownerRoleId = ownerRoleId;
	}
	public String getPortalIds() {
		return portalIds;
	}
	public void setPortalIds(String portalIds) {
		this.portalIds = portalIds;
	}
	
}
