package com.fable.insightview.platform.listview.entity;

import com.fable.insightview.platform.common.entity.MessageBean;

/**
 * 系统文件表
 * 
 * @author zhouwei
 * @version 1.0 2015-10-19
 */
//@ApiModel(value = "用于listview导出用文件实体")
public class ListviewExportFileBean extends MessageBean {

	/**
	 * 文件路径
	 */
	//@ApiModelProperty(value = "文件路径")
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}