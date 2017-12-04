package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.FdSysValidateRule;
import com.fable.insightview.platform.itsm.core.service.GenericService;


public interface IFdSysValidateRuleService extends GenericService<FdSysValidateRule> {
	
	public List<FdSysValidateRule> queryAllList();
	
	FdSysValidateRule getById(int id);
}
