package com.fable.insightview.platform.message.mapper;

import java.util.List;

import com.fable.insightview.platform.message.entity.PfEmailNotificationTaskBean;


public interface PfEmailNotificationTaskMapper {
	List<PfEmailNotificationTaskBean> getNotificationTask();

	int insertNotificationTask(PfEmailNotificationTaskBean bean);

	int updateNotificationTask(PfEmailNotificationTaskBean bean);
}
