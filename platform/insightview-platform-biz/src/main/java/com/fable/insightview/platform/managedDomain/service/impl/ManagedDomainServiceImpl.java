package com.fable.insightview.platform.managedDomain.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.managedDomain.dao.IManagedDomainDao;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;
import com.fable.insightview.platform.managedDomain.service.IManagedDomainService;

@Service("managedDomainService")
public class ManagedDomainServiceImpl implements IManagedDomainService {
	@Autowired IManagedDomainDao managedDomainDao;
	

	@Override
	public Map<String, Object> getManagedDomainList(
			ManagedDomain managedDomain, FlexiGridPageInfo flexiGridPageInfo) {
		Map<String, Object> result=new HashMap<String, Object>();
		List<ManagedDomain> managedDomainList=new ArrayList<ManagedDomain>();
		managedDomainList=managedDomainDao.getManagedDomainList(managedDomain, flexiGridPageInfo);
		int count=managedDomainDao.getManagedDomainListCount(managedDomain);
		result.put("managedDomainList", managedDomainList);
		result.put("total", count);
		return result;
	}

	@Override
	public List<ManagedDomain> getManagedDomainTreeVal() {
		return managedDomainDao.getManagedDomainTreeVal();
	}

	@Override
	public int getManagedDomainListCount(ManagedDomain managedDomain) {
		return managedDomainDao.getManagedDomainListCount(managedDomain);
	}

	@Override
	public boolean isLeaf(int domainId) {
		return managedDomainDao.isLeaf(domainId);
	}

	@Override
	public boolean delManagedDomain(int domainId) {
		return managedDomainDao.delManagedDomain(domainId);
	}

	@Override
	public boolean addManagedDomain(ManagedDomain managedDomain) {
		return managedDomainDao.addManagedDomain(managedDomain);
	}

	@Override
	public List<ManagedDomain> getManagedDomainInfo(int domainId) {
		return managedDomainDao.getManagedDomainInfo(domainId);
	}

	@Override
	public boolean updateManagedDomainInfo(ManagedDomain managedDomain) {
		return managedDomainDao.updateManagedDomainInfo(managedDomain);
	}

	@Override
	public boolean checkDomainName(String domainName,String flag) {
		return managedDomainDao.checkDomainName(domainName,flag);
	}

	@Override
	public ManagedDomain getIdByDomainName(String domainName) {
		// TODO Auto-generated method stub
		return managedDomainDao.getIdByDomainName(domainName);
	}


}
