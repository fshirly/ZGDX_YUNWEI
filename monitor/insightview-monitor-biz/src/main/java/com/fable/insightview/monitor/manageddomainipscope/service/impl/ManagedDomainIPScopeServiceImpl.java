package com.fable.insightview.monitor.manageddomainipscope.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.manageddomainipscope.entity.ManagedDomainIPScopeBean;
import com.fable.insightview.monitor.manageddomainipscope.mapper.ManagedDomainIPScopeMapper;
import com.fable.insightview.monitor.manageddomainipscope.service.IManagedDomainIPScopeService;
import com.fable.insightview.platform.managedDomain.dao.IManagedDomainDao;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;
import com.fable.insightview.platform.page.Page;

/**
 * 管理域IP范围
 *
 */
@Service
public class ManagedDomainIPScopeServiceImpl implements IManagedDomainIPScopeService {
	private Logger logger = LoggerFactory
	.getLogger(ManagedDomainIPScopeServiceImpl.class);
	
	@Autowired
	ManagedDomainIPScopeMapper domainIPScopeMapper;
	@Autowired
	IManagedDomainDao managedDomainDao;
	
	@Override
	public ManagedDomainIPScopeBean getDomainIpScopeInfo(int scopeID) {
		return domainIPScopeMapper.getDomainIpScopeInfo(scopeID);
	}

	@Override
	public List<ManagedDomainIPScopeBean> getDomainIpScopeList(
			Page<ManagedDomainIPScopeBean> page) {
		return domainIPScopeMapper.getDomainIpScopeList(page);
	}

	@Override
	public boolean insertDomainIpScope(ManagedDomainIPScopeBean bean) {
		try {
			domainIPScopeMapper.insertDomainIpScope(bean);
			return true;
		} catch (Exception e) {
			logger.error("新增管理域ip范围异常："+e);
			return false;
		}
	}

	@Override
	public boolean updateDomainIpScope(ManagedDomainIPScopeBean bean) {
		try {
			domainIPScopeMapper.updateDomainIpScope(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新管理域ip范围异常："+e);
			return false;
		}
	}

	@Override
	public boolean delDomainIpScope(String scopeIDs) {
		return domainIPScopeMapper.delDomainIpScope(scopeIDs);
	}

	/**
	 * 验证是否存在
	 */
	@Override
	public boolean checkExist(ManagedDomainIPScopeBean bean, String flag) {
		int count = 0;
		if("add".equals(flag)){
			count = domainIPScopeMapper.getByInfo(bean);
		}else{
			count = domainIPScopeMapper.getByInfo2(bean);
		}
		if(count > 0 ){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getDomainDesc(int domainID) {
		ManagedDomain domain = managedDomainDao.getById(domainID);
		if(domain != null){
			return domain.getDomainDescr();
		}
		return null;
	}

}
