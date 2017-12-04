package com.fable.insightview.monitor.notificationtasktracker.mapper;

import java.util.List;

import com.fable.insightview.monitor.notificationtasktracker.entity.SmsNotificationTaskBean;

public interface SmsNotificationTaskMapper {
	List<SmsNotificationTaskBean> getNotificationTask();

	int insertNotificationTask(SmsNotificationTaskBean bean);

	int updateNotificationTask(SmsNotificationTaskBean bean);
}
