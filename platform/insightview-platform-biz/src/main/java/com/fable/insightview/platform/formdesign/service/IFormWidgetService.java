package com.fable.insightview.platform.formdesign.service;

import java.util.List;

import com.fable.insightview.platform.formdesign.entity.FdWidgetType;
import com.fable.insightview.platform.itsm.core.service.GenericService;

/**
 * 表单控件类型核心API
 * 控件类型的加载、控件属性值的初始化
 * @author Administrator
 *
 */
public interface IFormWidgetService extends GenericService<FdWidgetType> {

	List<FdWidgetType> getAllFormWidgetType();

	List<FdWidgetType> getByCategory(Integer category);

	FdWidgetType getByWidgetType(String widgetType);

}
