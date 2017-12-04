package com.fable.insightview.platform.common.entity;

public class ResAssetClassChange extends
		com.fable.insightview.platform.itsm.core.entity.Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exportItsmClass;
	private String resAssetItsmClass;
	private String resCiItsmClass;

	public ResAssetClassChange() {
	}

	public ResAssetClassChange(String exportItsmClass, String resAssetItsmClass) {
		super();
		this.exportItsmClass = exportItsmClass;
		this.resAssetItsmClass = resAssetItsmClass;
	}

	public ResAssetClassChange(String exportItsmClass,
			String resAssetItsmClass, String resCiItsmClass) {
		super();
		this.exportItsmClass = exportItsmClass;
		this.resAssetItsmClass = resAssetItsmClass;
		this.resCiItsmClass = resCiItsmClass;
	}


	public ResAssetClassChange(String exportItsmClass) {
		super();
		this.exportItsmClass = exportItsmClass;
	}

	public String getExportItsmClass() {
		return exportItsmClass;
	}

	public void setExportItsmClass(String exportItsmClass) {
		this.exportItsmClass = exportItsmClass;
	}

	public String getResAssetItsmClass() {
		return resAssetItsmClass;
	}

	public void setResAssetItsmClass(String resAssetItsmClass) {
		this.resAssetItsmClass = resAssetItsmClass;
	}

	public String getResCiItsmClass() {
		return resCiItsmClass;
	}

	public void setResCiItsmClass(String resCiItsmClass) {
		this.resCiItsmClass = resCiItsmClass;
	}

}
