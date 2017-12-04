package com.fable.insightview.platform.provider.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.provider.dao.IProviderDao;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;
import com.fable.insightview.platform.entity.SysProviderUserBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.provider.service.IProviderService;

/**
 * @Description:供应商实现类
 * @author zhurt
 * @date 2014-7-9
 */
@Service("providerService")
public class ProviderServiceImpl implements IProviderService {

	@Autowired
	private IProviderDao providerDao;

	@Override
	public ProviderInfoBean getById(Integer providerId) {
		return providerDao.getById(providerId);
	}

	@Override
	public List<ProviderInfoBean> queryProviderInfos(
			ProviderInfoBean providerInfo) {
		return providerDao.queryProviderInfo(providerInfo);
	}

	@Override
	public void addProviderInfo(ProviderInfoBean providerInfoBean) {
		providerDao.addProviderInfo(providerInfoBean);
	}

	@Override
	public boolean delProviderInfoById(ProviderInfoBean providerInfoBean) {
		return providerDao.delProviderInfoById(providerInfoBean);
	}

	@Override
	public void delBatchInfo(String providerId) {
		providerDao.delBatchInfo(providerId);
	}

	@Override
	public List<ProviderInfoBean> getProviderInfoBeanByConditions(
			ProviderInfoBean providerInfoBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		return providerDao.getProviderInfoBeanByConditions(providerInfoBean,
				flexiGridPageInfo);
	}

	@Override
	public ProviderInfoBean getProviderInfoBeanByConditions(String paramName,
			String paramValue) {
		return providerDao.getProviderInfoBeanByConditions(paramName,
				paramValue);
	}

	@Override
	public int getTotalCount(ProviderInfoBean providerInfoBean) {
		return providerDao.getTotalCount(providerInfoBean);
	}

	@Override
	public void updateProvider(ProviderInfoBean providerInfoBean) {
		providerDao.updateProvider(providerInfoBean);
	}

	@Override
	public List<ProviderInfoBean> findProvideTreeVal() {
		return providerDao.findProvideTreeVal();
	}

	@Override
	public List<Integer> getUserIdByProId(int proId) {
		return providerDao.getUserIdByProId(proId);
	}

	@Override
	public boolean addProviderUser(SysProviderUserBean providerUserBean) {
		return providerDao.addProviderUser(providerUserBean);
	}

	@Override
	public List<SysProviderUserBean> getProUserByUserId(int userId) {
		return providerDao.getProUserByUserId(userId);
	}

	@Override
	public boolean updateProUserInfo(SysProviderUserBean providerUserBean) {
		return providerDao.updateProUserInfo(providerUserBean);
	}

	@Override
	public boolean deleteProUserByUserId(int userId) {
		return providerDao.deleteProUserByUserId(userId);
	}

	@Override
	public List<ProviderInfoBean> getproviderList(List<Integer> providerIdList,
			int projectStepId) {
		if (providerIdList.size() != 0) {
			String ids = "";
			for (int i = 0; i < providerIdList.size() - 1; i++) {
				ids = ids + providerIdList.get(i) + ",";
			}
			ids += providerIdList.get(providerIdList.size() - 1);
			return providerDao.getProviderByIds(ids, projectStepId);
		}
		return providerDao.getProviderByIds(null, projectStepId);
	}

	@Override
	public ProviderInfoBean getProjectidBidProvider(Integer bidId,
			Integer providerID) {
		return providerDao.getProviderByConditions(bidId, providerID);
	}

	@Override
	public String getProviderExistNames(int bidId, String ids) {
		String[] splitIds = ids.split(",");
		String providerNames = "";
		for (String id : splitIds) {
			ProviderInfoBean providerInfoBean = providerDao
					.getProviderByConditions(bidId, Integer.parseInt(id));
			if (null != providerInfoBean) {
				providerNames = providerNames + "["
						+ providerInfoBean.getProviderName() + "]";
			}
		}
		return providerNames;
	}

	@Override
	public List<ProviderInfoBean> getProviderInfoBeanByConditions(
			ProviderInfoBean providerInfoBean,
			FlexiGridPageInfo flexiGridPageInfo, List<Integer> idList) {
		if (idList.size() != 0) {
			String ids = "(";
			for (int i = 0; i < idList.size() - 1; i++) {
				ids = ids + idList.get(i) + ",";
			}
			ids += idList.get(idList.size() - 1) + ")";
			return providerDao.getProviderInfoBeanByConditions(
					providerInfoBean, flexiGridPageInfo, ids);
		} else {
			return providerDao.getProviderInfoBeanByConditions(
					providerInfoBean, flexiGridPageInfo);
		}

	}

	@Override
	public int getTotalExceptExist(ProviderInfoBean providerInfoBean,
			List<Integer> idList) {
		if (idList.size() != 0) {
			String ids = "(";
			for (int i = 0; i < idList.size() - 1; i++) {
				ids = ids + idList.get(i) + ",";
			}
			ids += idList.get(idList.size() - 1) + ")";
			return providerDao.getTotalExceptExist(providerInfoBean, ids);
		} else {
			return providerDao.getTotalCount(providerInfoBean);
		}
	}

	@Override
	public ProviderInfoBean getProviderInfoById(Integer id) {
		// TODO Auto-generated method stub
		return providerDao.getById(id);
	}

	@Override
	public boolean checkBeforeDel(ProviderInfoBean providerInfoBean) {
		List<SysProviderUserBean> sysProviderUserList = providerDao.getProUserByUserId(providerInfoBean);
		boolean flag = false;
		if(sysProviderUserList != null && sysProviderUserList.size() > 0){
			flag = false;
		}else{
			flag = true;
		}
		return flag;
	}

	@Override
	public List<SysUserInfoBean> getProUserByProviderID(
			ProviderInfoBean providerUserBean) {
		System.out.println("****进入serviceImpl");
		return providerDao.getProUserByProviderID(providerUserBean);
	}

	@Override
	public List<ProviderInfoBean> queryProviderListByOragWithUser(int userId) {
		// TODO Auto-generated method stub
		return providerDao.queryProviderListByOragWithUser(userId);
	}

}
