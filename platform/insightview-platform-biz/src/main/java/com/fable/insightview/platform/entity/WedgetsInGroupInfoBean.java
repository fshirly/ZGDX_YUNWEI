package com.fable.insightview.platform.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;

/**
 * 
 * @TABLE_NAME: WedgetsInGroupInfo
 * @Description:
 * @author: wul
 * @Create at: Fri Jan 03 10:18:36 CST 2014
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "WedgetsInGroupInfo")
public class WedgetsInGroupInfoBean extends com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "GroupID")
	private int groupId;

	@Column(name = "WedgetsID")
	private int wedgetsId;

	@Column(name = "BizViewType")
	private String bizViewType;

	public WedgetsInGroupInfoBean() {
		super();
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getWedgetsId() {
		return wedgetsId;
	}

	public void setWedgetsId(int wedgetsId) {
		this.wedgetsId = wedgetsId;
	}

	public String getBizViewType() {
		return bizViewType;
	}

	public void setBizViewType(String bizViewType) {
		this.bizViewType = bizViewType;
	}

	
}
