package com.fable.insightview.platform.ipmanager.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 子网与部门关系
 * 
 */
public class IPManSubNetRDeptBean {
	// id
	@NumberGenerator(name = "ipManSubNetRDeptPK")
	private Integer id;
	// 部门id
	private Integer deptId;
	// 子网id
	private Integer subNetId;
	// 占用地址数
	private Integer usedNum;
	//预占地址数
	private Integer preemptNum;
	
	//总数
	private Integer totalNum;
	private String deptName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getSubNetId() {
		return subNetId;
	}

	public void setSubNetId(Integer subNetId) {
		this.subNetId = subNetId;
	}

	public Integer getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getPreemptNum() {
		return preemptNum;
	}

	public void setPreemptNum(Integer preemptNum) {
		this.preemptNum = preemptNum;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

}
