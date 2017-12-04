package com.fable.insightview.platform.importdata;

import java.util.List;

public class ImportResult<T> {

	// 违反约束 key:行号 value：违反约束
	private List<ValidResult> vaildResultList;
	
	// 导入是否成功
	private boolean isSuccess;
	
	// 总条数
	private int totalNum;
	
	// 失败条数
	private int failureNum;
	
	//后台处理报错
	private List<String> processResultList;
	
	public final List<String> getProcessResultList() {
		return processResultList;
	}

	public final void setProcessResultList(List<String> processResultList) {
		this.processResultList = processResultList;
	}
	
	public int getFailureNum() {
		return failureNum;
	}

	public void setFailureNum(int failureNum) {
		this.failureNum = failureNum;
	}

	public final List<ValidResult> getVaildResultList() {
		return vaildResultList;
	}

	public final void setVaildResultList(List<ValidResult> vaildResultList) {
		this.vaildResultList = vaildResultList;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getSuccessNum() {
		return totalNum - failureNum;
	}
	
}
