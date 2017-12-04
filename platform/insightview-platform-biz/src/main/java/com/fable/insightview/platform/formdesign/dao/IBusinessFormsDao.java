package com.fable.insightview.platform.formdesign.dao;

import java.util.List;

import com.fable.insightview.platform.formdesign.entity.FdBusinessForms;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface IBusinessFormsDao extends GenericDao<FdBusinessForms> {

	List<FdBusinessForms> getListByBusinessNodeId(String businessNodeId);

}
