package com.fable.insightview.platform.managedDomain.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;

public interface IManagedDomainService {
	public List<ManagedDomain> getManagedDomainTreeVal();
	public  Map<String, Object> getManagedDomainList(ManagedDomain managedDomain,FlexiGridPageInfo flexiGridPageInfo);
	public int getManagedDomainListCount(ManagedDomain managedDomain);
	public boolean isLeaf(int domainId);
	public boolean delManagedDomain(int domainId);
	public boolean addManagedDomain(ManagedDomain managedDomain);
	public List<ManagedDomain>  getManagedDomainInfo(int domainId);
	public boolean updateManagedDomainInfo(ManagedDomain managedDomain);
	public boolean checkDomainName(String domainName,String flag);
	public ManagedDomain getIdByDomainName(String domainName);
}
