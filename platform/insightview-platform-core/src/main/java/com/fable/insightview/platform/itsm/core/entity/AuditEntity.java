package com.fable.insightview.platform.itsm.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 含有审计信息的数据实体抽象类
 * 
 * @author 汪朝  2013-9-30
 */
@MappedSuperclass
public abstract class AuditEntity extends IdEntity {

	private static final long serialVersionUID = -4837674581902502156L;

	/** 创建人ID */
	@Column(name = "CREATED_BY", updatable = false)
	protected String createdBy;

	/** 创建时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", updatable = false)
	protected Date createdTime;

	/** 最后修改人ID */
	@Column(name = "LAST_UPDATED_BY")
	protected String lastUpdatedBy;

	/** 最后修改时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_TIME")
	protected Date lastUpdatedTime;

	/**
	 * 获取创建人ID
	 * 
	 * @return 创建人ID
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * 设置创建人ID
	 * 
	 * @param createdBy
	 *            创建人ID
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * 获取创建时间
	 * 
	 * @return 创建时间
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createdTime
	 *            创建时间
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * 获取最后修改人ID
	 * 
	 * @return 最后修改人ID
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * 设置最后修改人ID
	 * 
	 * @param lastUpdatedBy
	 *            最后修改人ID
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * 获取最后修改时间
	 * 
	 * @return 最后修改时间
	 */
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * 设置最后修改时间
	 * 
	 * @param lastUpdatedTime
	 *            最后修改时间
	 */
	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

}
