package com.fable.insightview.platform.formdesign.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.formdesign.dao.IBusinessAttributesDao;
import com.fable.insightview.platform.formdesign.entity.FdBusinessAttributes;
import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.formdesign.entity.FdFormAttributes;
import com.fable.insightview.platform.formdesign.service.IBusinessAttributesService;
import com.fable.insightview.platform.formdesign.vo.ProcessFormVO;
import com.fable.insightview.platform.itsm.core.service.GenericServiceImpl;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

@Service("fromdesign.businessAttributesService")
public class BusinessAttributesServiceImpl extends GenericServiceImpl<FdBusinessAttributes> implements
		IBusinessAttributesService {
	
	@Autowired
	private IBusinessAttributesDao businessAttributesDao;
	
	@Override
	public boolean deleteByAttributeId(Integer attributeId) {
		if (null != attributeId) {
			businessAttributesDao.deleteByAttributeId(attributeId);
			return true;
		}
		return false;
	}

	@Override
	public void insertBAttributes(FdForm form, FdFormAttributes formAttr,
			ProcessFormVO processFormVO) {
		FdBusinessAttributes businessAttributes = new FdBusinessAttributes();
		businessAttributes.setBusinessId(processFormVO
				.getBusinessId());
		businessAttributes.setBusinessType(processFormVO
				.getBusinessType());
		businessAttributes.setAttributeId(formAttr.getId());
		businessAttributes.setAttributeTable("Form_T_"
				+ form.getId());
		businessAttributesDao.insert(businessAttributes);
	}

	@Override
	public List<Map<String, String>> queryProcessInstanceLst(String processId, List<Integer> processInstanceIds, String title, FlexiGridPageInfo flexiGridPageInfo) {
		if (null != processId) {
			return businessAttributesDao.queryProcessInstanceLst(processId, processInstanceIds, title, flexiGridPageInfo);
		}
		return null;
	}

	@Override
	public int queryProcessInstanceCount(String processId, List<Integer> processInstanceIds, String title) {
		if (null != processId) {
			return businessAttributesDao.queryProcessInstanceCount(processId, processInstanceIds, title);
		}
		return 0;
	}
}
