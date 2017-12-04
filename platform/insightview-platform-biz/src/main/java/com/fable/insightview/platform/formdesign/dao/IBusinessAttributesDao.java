package com.fable.insightview.platform.formdesign.dao;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.formdesign.entity.FdBusinessAttributes;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface IBusinessAttributesDao extends GenericDao<FdBusinessAttributes> {

	void deleteByAttributeId(Integer attributeId);

	List<Map<String, String>> queryProcessInstanceLst(String processId,List<Integer> processInstanceIds, String title, FlexiGridPageInfo flexiGridPageInfo);

	int queryProcessInstanceCount(String processId, List<Integer> processInstanceIds, String title);

}
