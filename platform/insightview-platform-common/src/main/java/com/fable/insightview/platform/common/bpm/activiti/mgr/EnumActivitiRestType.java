package com.fable.insightview.platform.common.bpm.activiti.mgr;

public enum EnumActivitiRestType {

	POST ("POST"),
	GET	 ("GET"),
	PUT ("PUT"),
	DELETE ("DELETE"),
	MULTIPART ("MULTIPART"),
	GET_BYTE ("GET_BYTE")
	;
	public String restType;

	private EnumActivitiRestType(String restType) {
		this.restType = restType;
	}

	public String getRestType() {
		return restType;
	}

}
