package com.fable.insightview.platform.managedDomain.dao;

import java.util.List;

import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;

public interface IManagedDomainDao extends GenericDao<ManagedDomain>{
	public List<ManagedDomain> getManagedDomainTreeVal();
	public List<ManagedDomain> getManagedDomainList(ManagedDomain managedDomain,FlexiGridPageInfo flexiGridPageInfo);
	public int getManagedDomainListCount(ManagedDomain managedDomain);
	public int getLevel(int domainId);
	public String getOrganizationName(int organizationId);
	public boolean isLeaf(int domainId);
	public boolean delManagedDomain(int domainId);
	public boolean addManagedDomain(ManagedDomain managedDomain);
	public List<ManagedDomain> getManagedDomainInfo(int domainId);
	public boolean updateManagedDomainInfo(ManagedDomain managedDomain);
	public boolean checkDomainName(String domainName,String flag);
	public ManagedDomain getIdByDomainName(String domainName);
}
