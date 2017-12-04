package com.fable.insightview.monitor.portal.service;

import java.util.List;

import com.fable.insightview.monitor.portal.entity.WidgetInfoBean;




public interface IWidgetInfoService {
	WidgetInfoBean getWidgetByWidgetId(String widgetId);
	int modifyWidgetById(WidgetInfoBean bean);
	List<WidgetInfoBean> getAllWidget();

	List<WidgetInfoBean> getWidgetByFilter(WidgetInfoBean bean);
	
}
