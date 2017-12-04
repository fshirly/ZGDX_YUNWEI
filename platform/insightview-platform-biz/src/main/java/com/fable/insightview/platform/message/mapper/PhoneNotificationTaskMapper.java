package com.fable.insightview.platform.message.mapper;

import java.util.List;

import com.fable.insightview.platform.message.entity.PhoneNotificationTaskBean;


public interface PhoneNotificationTaskMapper {
	List<PhoneNotificationTaskBean> getNotificationTask();

	int insertNotificationTask(PhoneNotificationTaskBean bean);

	int updateNotificationTask(PhoneNotificationTaskBean bean);
}
