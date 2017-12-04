package com.fable.insightview.platform.formdesign.dao;

import java.util.List;

import com.fable.insightview.platform.formdesign.entity.FdWidgetType;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface IFormWidgetDao extends GenericDao<FdWidgetType> {

	List<FdWidgetType> getAllFormWidgetType();

	List<FdWidgetType> getByCategory(Integer category);

	FdWidgetType getByWidgetType(String widgetType);

}
