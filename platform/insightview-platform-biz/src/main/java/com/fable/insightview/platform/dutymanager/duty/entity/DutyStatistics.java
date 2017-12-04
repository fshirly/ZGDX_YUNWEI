package com.fable.insightview.platform.dutymanager.duty.entity;

/**
 * 值班统计记录表
 * @author chenly
 *
 */
public class DutyStatistics {
	
	private Integer id;
	//值班班次表PfOrdersOfDuty主键
	private Integer orderId;
	//值班人Id
	private Integer userId;
	//值班统计类型：1-值班巡检、2-故障处理、3-申告服务、4-综合处理
	private Integer type;
	//相应类型所展示的label信息
	private String label;
	//相应类型所展示的label信息对应的Value值
	private String val;
	//相应类型展示信息顺序
	private Integer sequence;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}
