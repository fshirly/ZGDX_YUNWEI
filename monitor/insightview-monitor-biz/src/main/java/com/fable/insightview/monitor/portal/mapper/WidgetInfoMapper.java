package com.fable.insightview.monitor.portal.mapper;

import java.util.List;

import com.fable.insightview.monitor.portal.entity.WidgetInfoBean;




public interface WidgetInfoMapper {
	List<WidgetInfoBean> getAllWidget();

	List<WidgetInfoBean> getWidgetByFilter(WidgetInfoBean bean);

	WidgetInfoBean getWidgetByWidgetId(String widgetId);
	
	int modifyWidgetById(WidgetInfoBean bean);
}
