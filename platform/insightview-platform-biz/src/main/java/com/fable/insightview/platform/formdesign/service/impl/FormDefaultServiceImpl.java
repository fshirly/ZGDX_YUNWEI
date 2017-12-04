package com.fable.insightview.platform.formdesign.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.formdesign.dao.IFormDefaultDao;
import com.fable.insightview.platform.formdesign.entity.FdFormDefault;
import com.fable.insightview.platform.formdesign.service.IFormDefaultService;
import com.fable.insightview.platform.itsm.core.service.GenericServiceImpl;

/**
 * 特殊属性初始化值预置表服务类
 * 
 * @author zhengz
 * 
 */
@Service("fromdesign.formDefaultService")
public class FormDefaultServiceImpl extends GenericServiceImpl<FdFormDefault> implements IFormDefaultService {

	@Autowired
	private IFormDefaultDao formDefaultDao;

	@Override
	public List<FdFormDefault> getItems(String widgetType) {
		// TODO Auto-generated method stub
		return formDefaultDao.getByWidgetType(widgetType);
	}

}
