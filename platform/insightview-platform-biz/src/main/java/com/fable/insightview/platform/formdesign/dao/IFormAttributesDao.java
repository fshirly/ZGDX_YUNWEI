package com.fable.insightview.platform.formdesign.dao;

import java.util.List;

import com.fable.insightview.platform.formdesign.entity.FdFormAttributes;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface IFormAttributesDao extends GenericDao<FdFormAttributes> {

	List<FdFormAttributes> getByFormId(Integer formId);

}
