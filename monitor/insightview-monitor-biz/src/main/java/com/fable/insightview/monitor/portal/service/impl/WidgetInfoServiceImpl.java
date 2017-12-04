package com.fable.insightview.monitor.portal.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.portal.entity.WidgetInfoBean;
import com.fable.insightview.monitor.portal.mapper.WidgetInfoMapper;
import com.fable.insightview.monitor.portal.service.IWidgetInfoService;




@Service
public class WidgetInfoServiceImpl implements IWidgetInfoService {
	
	@Autowired
	WidgetInfoMapper widgetInfoMapper;

	@Override
	public WidgetInfoBean getWidgetByWidgetId(String widgetId) {
		return widgetInfoMapper.getWidgetByWidgetId(widgetId);
	}

	@Override
	public int modifyWidgetById(WidgetInfoBean bean) {
		return widgetInfoMapper.modifyWidgetById(bean);
	}

	@Override
	public List<WidgetInfoBean> getAllWidget() {
		return widgetInfoMapper.getAllWidget();
	}

	@Override
	public List<WidgetInfoBean> getWidgetByFilter(WidgetInfoBean bean) {
		return widgetInfoMapper.getWidgetByFilter(bean);
	}
	

}
