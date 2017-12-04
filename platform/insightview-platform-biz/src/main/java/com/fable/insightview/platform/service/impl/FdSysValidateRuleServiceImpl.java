package com.fable.insightview.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.IFdSysValidateRuleDao;
import com.fable.insightview.platform.entity.FdSysValidateRule;
import com.fable.insightview.platform.service.IFdSysValidateRuleService;


@Service("fdSysValidateRuleService")
public class FdSysValidateRuleServiceImpl implements IFdSysValidateRuleService{

	@Autowired
	private IFdSysValidateRuleDao fdSysValidateRuleDao;
	
	@Override
	public List<FdSysValidateRule> queryAllList(){
		return this.fdSysValidateRuleDao.queryAllList();
	}

	@Override
	public void insert(FdSysValidateRule entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(FdSysValidateRule entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(FdSysValidateRule entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FdSysValidateRule getById(Long id) {
		return fdSysValidateRuleDao.getById(id);
	}
	
	@Override
	public FdSysValidateRule getById(int id) {
		return fdSysValidateRuleDao.getById(id);
	}
}
