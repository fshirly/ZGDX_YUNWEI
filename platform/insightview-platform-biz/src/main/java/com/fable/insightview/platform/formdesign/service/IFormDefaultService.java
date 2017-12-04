package com.fable.insightview.platform.formdesign.service;


import java.util.List;

import com.fable.insightview.platform.formdesign.entity.FdFormDefault;
import com.fable.insightview.platform.itsm.core.service.GenericService;

/**
 * 表单控件类型核心API
 * 控件类型的加载、控件属性值的初始化
 * @author Administrator
 *
 */
public interface IFormDefaultService extends GenericService<FdFormDefault> {

	/**
	 * 根据widgetType查询特殊属性初始化值
	 * @param id
	 * @return
	 */
	List<FdFormDefault> getItems(String widgetType);

}
