package com.fable.insightview.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ItsmEventType")
public class ItsmEventTypeDict extends com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@Column(name="ID")
	private Integer id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="PARENTTYPEID")
	private Integer parentTypeId;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="ISDEFAULT")
	private int isDefault;

	public ItsmEventTypeDict() {
		super();
	}

	public ItsmEventTypeDict(Integer id, String title, Integer parentTypeId,
			String remark, int isDefault) {
		super();
		this.id = id;
		this.title = title;
		this.parentTypeId = parentTypeId;
		this.remark = remark;
		this.isDefault = isDefault;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(Integer parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	
}
