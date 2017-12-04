package com.fable.insightview.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.AttachmentDao;
import com.fable.insightview.platform.entity.Attachment;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

@Repository("attachmentDao")
public class AttachmentDaoImpl extends GenericDaoHibernate<Attachment> implements AttachmentDao {

	public String saveOrUpdate(Attachment attachment) {
		Attachment originalAttachment = queryByCondition(attachment.getDataTableName(),attachment.getApplicationId(),attachment.getTitle());
		if(originalAttachment != null) {
			String oldFilePath =  null;
			oldFilePath = originalAttachment.getUrl();
			
			originalAttachment.setDataTableName(attachment.getDataTableName());
			originalAttachment.setApplicationId(attachment.getApplicationId());
			originalAttachment.setTitle(attachment.getTitle());
			originalAttachment.setFormat(attachment.getFormat());
			originalAttachment.setCreater(attachment.getCreater());
			originalAttachment.setUrl(attachment.getUrl());
			
			super.update(originalAttachment);
			return oldFilePath;
		} else {
			super.insert(attachment);
			return null;
		}
	}
	
	@Override
	public String deleteByCondition(String dataTableName, int applicationId,String title) {
		Attachment attachment = queryByCondition(dataTableName,applicationId,title);
		
		if(attachment != null) {
			String url = attachment.getUrl();
			String hql = "delete from Attachment where dataTableName = '" + dataTableName + "' and applicationId = " + applicationId + " and title = '" + title + "'";
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql);
			query.executeUpdate();
			return url;
		}
		return null;
	}
	
	@Override
	public Attachment queryByCondition(String dataTableName, int applicationId,String title) {
		String hql = "from Attachment where dataTableName = '" + dataTableName + "' and applicationId = " + applicationId + " and title = '" + title + "'";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		List<Attachment> list = query.list();
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
