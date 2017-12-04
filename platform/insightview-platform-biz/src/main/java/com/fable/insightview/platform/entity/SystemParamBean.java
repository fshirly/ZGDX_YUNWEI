package com.fable.insightview.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * 系统基础参数表
 * 
 * @author 武林
 */
@Entity
@Table(name = "SystemParam")
public class SystemParamBean extends com.fable.insightview.platform.itsm.core.entity.Entity
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ParamID")
	private int paramID;

	@Column(name = "ParamClass")
	private String paramClass;

	@Column(name = "ParamName")
	private String paramName;

	@Column(name = "ParamValue")
	private String paramValue;

	@Column(name = "ParamDescr")
	private String paramDescr;

	public SystemParamBean() {
	}

	public SystemParamBean(String paramName) {
		this.paramName = paramName;
	}

	public int getParamID() {
		return paramID;
	}

	public void setParamID(int paramID) {
		this.paramID = paramID;
	}

	public String getParamClass() {
		return paramClass;
	}

	public void setParamClass(String paramClass) {
		this.paramClass = paramClass;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamDescr() {
		return paramDescr;
	}

	public void setParamDescr(String paramDescr) {
		this.paramDescr = paramDescr;
	}

}