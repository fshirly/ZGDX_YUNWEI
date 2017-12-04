package com.fable.insightview.platform.workplan.entity;

import java.util.Date;

/**
 * 封装工作计划列表页面的查询参数
 * @author chenly
 *
 */
public class WorkPlanParams {
	
	private String title;
	private Date planStart1;
	private Date planStart2;
	private Integer planType;
	private Date planEnd1;
	private Date planEnd2;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPlanStart1() {
		return planStart1;
	}
	public void setPlanStart1(Date planStart1) {
		this.planStart1 = planStart1;
	}
	public Date getPlanStart2() {
		return planStart2;
	}
	public void setPlanStart2(Date planStart2) {
		this.planStart2 = planStart2;
	}
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public Date getPlanEnd1() {
		return planEnd1;
	}
	public void setPlanEnd1(Date planEnd1) {
		this.planEnd1 = planEnd1;
	}
	public Date getPlanEnd2() {
		return planEnd2;
	}
	public void setPlanEnd2(Date planEnd2) {
		this.planEnd2 = planEnd2;
	}
	
	
}
