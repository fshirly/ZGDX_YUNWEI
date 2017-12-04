package com.fable.insightview.monitor.discover.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * CMDB返回的同步数据处理结果
 *
 */
public class SynchronResResult {
	@NumberGenerator(name = "synchronreslogid")
	private int id;
	private int moID;
	private int moClassID;
	private int resID;
	private int resTypeID;
	private int execFlag;
	private String errorDescr;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMoID() {
		return moID;
	}

	public void setMoID(int moID) {
		this.moID = moID;
	}

	public int getMoClassID() {
		return moClassID;
	}

	public void setMoClassID(int moClassID) {
		this.moClassID = moClassID;
	}

	public int getResID() {
		return resID;
	}

	public void setResID(int resID) {
		this.resID = resID;
	}

	public int getResTypeID() {
		return resTypeID;
	}

	public void setResTypeID(int resTypeID) {
		this.resTypeID = resTypeID;
	}

	public int getExecFlag() {
		return execFlag;
	}

	public void setExecFlag(int execFlag) {
		this.execFlag = execFlag;
	}

	public String getErrorDescr() {
		return errorDescr;
	}

	public void setErrorDescr(String errorDescr) {
		this.errorDescr = errorDescr;
	}
}
