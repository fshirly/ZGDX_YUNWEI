package com.fable.insightview.platform.formdesign.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.formdesign.entity.FdBusinessAttributes;
import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.formdesign.entity.FdFormAttributes;
import com.fable.insightview.platform.formdesign.vo.ProcessFormVO;
import com.fable.insightview.platform.itsm.core.service.GenericService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface IBusinessAttributesService extends GenericService<FdBusinessAttributes> {
	
	boolean deleteByAttributeId(Integer attributeId);
	
	void insertBAttributes(FdForm form, FdFormAttributes formAttr, ProcessFormVO processFormVO);

	List<Map<String, String>> queryProcessInstanceLst(String processId, List<Integer> processInstanceIds, String title, FlexiGridPageInfo flexiGridPageInfo);
	
	int queryProcessInstanceCount(String processId, List<Integer> processInstanceIds, String title);

}
