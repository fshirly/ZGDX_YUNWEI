package com.fable.insightview.platform.formdesign.dao;

import java.util.List;

import com.fable.insightview.platform.formdesign.entity.FdFormDefault;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface IFormDefaultDao extends GenericDao<FdFormDefault> {

	/**
	 * 根据widgetType查询特殊属性初始化值
	 * @param widgetType
	 * @return
	 */
	List<FdFormDefault> getByWidgetType(String widgetType);


}
