package com.fable.insightview.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "ItsmAttachment")
public class Attachment extends com.fable.insightview.platform.itsm.core.entity.Entity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "attachment_gen")
	@TableGenerator(initialValue=10001, name = "attachment_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "AttachmentPK", allocationSize = 1)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "Title")
	private String title;
	
	@Column(name = "DataTableName")
	private String dataTableName;
	
	@Column(name = "ApplicationId")
	private Integer applicationId;
	
	@Column(name = "Format")
	private String format;
	
	@Column(name = "Creater")
	private Integer creater;
	
	@Column(name = "URL")
	private String url;
	
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

	public String getDataTableName() {
		return dataTableName;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getCreater() {
		return creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFilename() {
		String filename = getUrl();
		
		int idx = filename.lastIndexOf("\\");
		if(idx != -1) {
			filename = filename.substring(idx + 1);//去路径
		}
		
		idx = filename.indexOf("_");
		if(idx != -1 ) {
			filename = filename.substring(idx + 1);//去时间戳
		}
		
		return filename;
	}
	
}
