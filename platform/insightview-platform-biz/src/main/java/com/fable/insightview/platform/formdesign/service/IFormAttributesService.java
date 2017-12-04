package com.fable.insightview.platform.formdesign.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.formdesign.entity.FdFormAttributes;
import com.fable.insightview.platform.formdesign.vo.FormAttributeUrlVO;
import com.fable.insightview.platform.itsm.core.service.GenericService;

public interface IFormAttributesService extends GenericService<FdFormAttributes> {
	
	List<FdFormAttributes> getByFormId(Integer formId);
	
	List<FormAttributeUrlVO> getFormAttrUrlInfo(Integer formId);
	
	FdFormAttributes insertFormAttributes(FdForm form, Map<String, String> map);
	
	void updateFormAttributes(Map<String, String> map);

}
