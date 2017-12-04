package com.fable.insightview.platform.providerOrg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.IOrganizationDao;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;
import com.fable.insightview.platform.providerOrg.entity.SysProviderOrganization;
import com.fable.insightview.platform.providerOrg.mapper.SysProviderOrgMapper;
import com.fable.insightview.platform.providerOrg.service.ISysProviderOrgService;

@Service("sysProviderOrgService")
public class SysProviderOrgServiceImpl implements ISysProviderOrgService{
	@Autowired
	SysProviderOrgMapper sysProviderOrgMapper;
	
	@Autowired
	protected IOrganizationDao organizationDao;
	
	@Override
	public boolean addSysProviderOrg(
			SysProviderOrganization sysProviderOrganization) {
		SysProviderOrganization providerOrg = new SysProviderOrganization();
		providerOrg.setProviderId(sysProviderOrganization.getProviderId());
		int count = sysProviderOrgMapper.getOrgByProviderId(sysProviderOrganization.getProviderId()).size();
		int delFlag = sysProviderOrgMapper.delByProviderId(sysProviderOrganization.getProviderId());
		String[] OrgIdArr = sysProviderOrganization.getOrgIdAttr().split(",");
		if((OrgIdArr.length >0 && count >0 &&delFlag > 0) || count==0){
			for (String orgId : OrgIdArr) {
				if (orgId.trim().length() > 0) {
					if(Integer.parseInt(orgId)!=0){
						SysProviderOrganization bean = new SysProviderOrganization(
								sysProviderOrganization.getProviderId(), Integer.parseInt(orgId));
						sysProviderOrgMapper.insertProviderOrg(bean);
					}
				}
			}
			delFlag =0;
		}
		return true;
	}

	@Override
	public String queryOrgByProvider(Integer providerId) {
		String OrgIdArr = "";
		List<SysProviderOrganization> sysproviderOrgLst = sysProviderOrgMapper.getOrgByProviderId(providerId);
		
		for (int i = 0; i < sysproviderOrgLst.size(); i++) {
			
			int OrgId = sysproviderOrgLst.get(i).getOrganizationId();
			boolean flag = isLeaf(OrgId);
			if(flag == true){
				OrgIdArr += OrgId+",";
			}
		}
		return OrgIdArr;
	}
	
	public boolean isLeaf(int orgId) {
		 List<OrganizationBean> orgList = organizationDao.findByParentId(orgId);
		 if(orgList.size() > 0){
			 return false;
		 }else{
			 return true;
		 }
	}

	@Override
	public List<ProviderInfoBean> queryProviderByOrgId(int orgId) {
		// TODO Auto-generated method stub
		return sysProviderOrgMapper.queryProviderByOrgId(orgId);
	}

	@Override
	public List<SysUserInfoBean> getProviderUser(Page<SysUserInfoBean> page) {
		// TODO Auto-generated method stub
		return sysProviderOrgMapper.getProviderUser(page);
	}

	@Override
	public int getTotalCount(SysUserInfoBean sysUserBean) {
		// TODO Auto-generated method stub
		return sysProviderOrgMapper.getTotalCount(sysUserBean);
	}
	
}
