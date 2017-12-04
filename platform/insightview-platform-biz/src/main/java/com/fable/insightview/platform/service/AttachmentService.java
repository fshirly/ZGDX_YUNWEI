package com.fable.insightview.platform.service;

import com.fable.insightview.platform.entity.Attachment;

/**
 * 
 * @author Administrator 事件管理的接口
 */
public interface AttachmentService {
	/**
	 * 新纪录返回null,更新记录返回原来的附件路径以供上层删除
	 * 
	 * @param attachment
	 * @return
	 */
	public String saveOrUpdateAttachment(Attachment attachment);

	public String deleteAttachmentByCondition(String dataTableName,
			int applicationId, String title);

	public Attachment queryAttachmentByCondition(String dataTableName,
			int applicationId, String title);

	public Attachment getAttachmentById(Integer id);

}
