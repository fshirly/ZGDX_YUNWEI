package com.fable.insightview.monitor.notificationtasktracker.mapper;

import java.util.List;

import com.fable.insightview.monitor.notificationtasktracker.entity.EmailNotificationTaskBean;

public interface EmailNotificationTaskMapper {
	List<EmailNotificationTaskBean> getNotificationTask();

	int insertNotificationTask(EmailNotificationTaskBean bean);

	int updateNotificationTask(EmailNotificationTaskBean bean);
}
