package com.fable.insightview.monitor.perf.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;


@Alias("moInfo")
public class MoInfoBean {
	@NumberGenerator(name = "moInfoId")
	private int id;
	private int taskId;
	private int mid;
	private int doIntervals;
	public MoInfoBean() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getDoIntervals() {
		return doIntervals;
	}
	public void setDoIntervals(int doIntervals) {
		this.doIntervals = doIntervals;
	}
	public MoInfoBean(int id, int taskId, int mid, int doIntervals) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.mid = mid;
		this.doIntervals = doIntervals;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
}
