package com.fable.insightview.platform.dao;

import com.fable.insightview.platform.entity.Attachment;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface AttachmentDao extends GenericDao<Attachment> {
	
	public String saveOrUpdate(Attachment attachment);

	public String deleteByCondition(String dataTableName, int applicationId,String title);
	
	public Attachment queryByCondition(String dataTableName, int applicationId,String title);
}
