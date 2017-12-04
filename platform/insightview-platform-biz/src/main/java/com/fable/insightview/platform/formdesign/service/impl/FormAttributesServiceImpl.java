package com.fable.insightview.platform.formdesign.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.formdesign.dao.IFormAttributesDao;
import com.fable.insightview.platform.formdesign.dao.IFormWidgetDao;
import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.formdesign.entity.FdFormAttributes;
import com.fable.insightview.platform.formdesign.entity.FdWidgetType;
import com.fable.insightview.platform.formdesign.service.IFormAttributesService;
import com.fable.insightview.platform.formdesign.vo.FormAttributeUrlVO;
import com.fable.insightview.platform.itsm.core.service.GenericServiceImpl;

@Service("fromdesign.formAttributesService")
public class FormAttributesServiceImpl extends GenericServiceImpl<FdFormAttributes> implements IFormAttributesService {
	
	@Autowired
	private IFormAttributesDao formAttributesDao;
	
	@Autowired
	private IFormWidgetDao formWidgetDao;

	/**
	 * 根据表单的id查询该表单下的所有属性
	 */
	@Override
	public List<FdFormAttributes> getByFormId(Integer formId) {
		return formAttributesDao.getByFormId(formId);
	}

	/**
	 * 根据表单的id查询出属性的相关信息
	 */
	@Override
	public List<FormAttributeUrlVO> getFormAttrUrlInfo(Integer formId) {
		List<FormAttributeUrlVO> formAttrUrlVO = new ArrayList<FormAttributeUrlVO>();
		List<FdFormAttributes> attrs = formAttributesDao.getByFormId(formId);
		for (FdFormAttributes fdFormAttr : attrs) {
			FormAttributeUrlVO vo = new FormAttributeUrlVO();
			FdWidgetType fdWidgetType = formWidgetDao.getByWidgetType(fdFormAttr.getWidgetType());
			vo.setId(fdFormAttr.getId());
			vo.setEditUrl(fdWidgetType.getEditUrl());
			vo.setViewUrl(fdWidgetType.getViewUrl());
			vo.setWidgetType(fdWidgetType.getWidgetType());
			formAttrUrlVO.add(vo);
		}
		
		return formAttrUrlVO;
	}

	@Override
	public FdFormAttributes insertFormAttributes(FdForm form,
			Map<String, String> map) {
		FdFormAttributes formAttr = new FdFormAttributes();
		formAttr.setFormId(form.getId());
		formAttr.setAttributesLabel(map.get("attributesLabel"));
		formAttr.setWidgetType(map.get("widgetType"));
		if (null != map.get("initSQL")) {
			formAttr.setInitSQL(map.get("initSQL"));
		}
		formAttributesDao.insert(formAttr);
		if (null != map.get("columnName")) {
			formAttr.setColumnName(map.get("columnName"));
		} else {
			formAttr.setColumnName("COL_" + formAttr.getId());
		}
		formAttributesDao.update(formAttr);
		
		return formAttr;
	}

	@Override
	public void updateFormAttributes(Map<String, String> map) {
		FdFormAttributes formAttr = formAttributesDao
				.getById(Integer.valueOf(map.get("id")));
		if (null != formAttr) {
			formAttr.setAttributesLabel(map.get("attributesLabel"));
			formAttr.setColumnName(map.get("columnName"));
			formAttr.setWidgetType(map.get("widgetType"));
			formAttr.setInitSQL(map.get("initSQL"));
			formAttributesDao.update(formAttr);
		}
	}

}
