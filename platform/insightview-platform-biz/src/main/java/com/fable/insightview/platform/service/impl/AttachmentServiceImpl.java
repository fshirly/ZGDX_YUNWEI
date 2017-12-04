package com.fable.insightview.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.AttachmentDao;
import com.fable.insightview.platform.entity.Attachment;
import com.fable.insightview.platform.service.AttachmentService;

/**
 * 事件管理的service
 * 
 * @author Administrator
 * 
 */
@Service("eventManageService")
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private AttachmentDao attachmentDao;

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	@Override
	public String saveOrUpdateAttachment(Attachment attachment) {
		return attachmentDao.saveOrUpdate(attachment);
	}

	@Override
	public String deleteAttachmentByCondition(String dataTableName,
			int applicationId, String title) {
		return attachmentDao.deleteByCondition(dataTableName, applicationId,
				title);
	}

	@Override
	public Attachment queryAttachmentByCondition(String dataTableName,
			int applicationId, String title) {
		return attachmentDao.queryByCondition(dataTableName, applicationId,
				title);
	}

	@Override
	public Attachment getAttachmentById(Integer id) {
		return attachmentDao.getById(id);
	}

}
