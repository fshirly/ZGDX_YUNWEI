package com.fable.insightview.platform.tasklog;

import java.util.List;

public class PfOrgTree {

	private int orgId;
	private String orgName;
	private List<PfDeptTree> depts;

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<PfDeptTree> getDepts() {
		return depts;
	}

	public void setDepts(List<PfDeptTree> depts) {
		this.depts = depts;
	}

}
