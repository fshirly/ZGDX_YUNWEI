package com.fable.insightview.platform.formdesign.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.formdesign.dao.IFormWidgetDao;
import com.fable.insightview.platform.formdesign.entity.FdWidgetType;
import com.fable.insightview.platform.formdesign.service.IFormWidgetService;
import com.fable.insightview.platform.itsm.core.service.GenericServiceImpl;

/**
 * 表单控件服务类
 * 
 * @author Maowei
 * 
 */
@Service("fromdesign.formWidgetService")
public class FormWidgetServiceImpl extends GenericServiceImpl<FdWidgetType> implements IFormWidgetService {

	@Autowired
	private IFormWidgetDao formWidgetDao;
	

	@Override
	public List<FdWidgetType> getAllFormWidgetType() {
		return formWidgetDao.getAllFormWidgetType();
	}

	@Override
	public List<FdWidgetType> getByCategory(Integer category) {
		if (null != category) {
			return formWidgetDao.getByCategory(category);
		} else {
			return null;
		}
	}

	@Override
	public FdWidgetType getByWidgetType(String widgetType) {
		if (null != widgetType) {
			return formWidgetDao.getByWidgetType(widgetType);
		} else {
			return null;
		}
	}

}
