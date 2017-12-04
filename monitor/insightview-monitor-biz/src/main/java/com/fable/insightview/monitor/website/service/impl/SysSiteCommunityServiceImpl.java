package com.fable.insightview.monitor.website.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.website.entity.SysSiteCommunityBean;
import com.fable.insightview.monitor.website.mapper.SysSiteCommunityMapper;
import com.fable.insightview.monitor.website.service.ISysSiteCommunityService;
import com.fable.insightview.platform.page.Page;
@Service
public class SysSiteCommunityServiceImpl implements ISysSiteCommunityService {
	private Logger logger = LoggerFactory.getLogger(SysSiteCommunityServiceImpl.class);
	@Autowired SysSiteCommunityMapper siteCommunityMapper;

	@Override
	public SysSiteCommunityBean getByIPAndPort(SysSiteCommunityBean bean) {
		return siteCommunityMapper.getByIPAndPort(bean);
	}

	@Override
	public SysSiteCommunityBean getByIPAndSiteType(SysSiteCommunityBean bean) {
		return siteCommunityMapper.getByIPAndSiteType(bean);
	}

	@Override
	public List<SysSiteCommunityBean> getCommunityByConditions(
			Page<SysSiteCommunityBean> page) {
		return siteCommunityMapper.getCommunityByConditions(page);
	}

	@Override
	public boolean checkCommunity(SysSiteCommunityBean siteCommunityBean) {
		if(siteCommunityBean.getSiteType() == 2){
			siteCommunityBean.setPort(null);
		}
		List<SysSiteCommunityBean> comLst = siteCommunityMapper.getByConditions(siteCommunityBean);
		if(comLst.size() <= 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean addCommunity(SysSiteCommunityBean siteCommunityBean) {
		try {
			if(siteCommunityBean.getSiteType() == 2){
				siteCommunityBean.setPort(null);
			}
			siteCommunityMapper.insertSiteCommunity(siteCommunityBean);
			return true;
		} catch (Exception e) {
			logger.error("新增站点凭证异常："+e);
		}
		return false;
	}

	@Override
	public SysSiteCommunityBean initSiteCommunity(int id) {
		return siteCommunityMapper.getByID(id);
	}

	@Override
	public boolean updateCommunity(SysSiteCommunityBean siteCommunityBean) {
		try {
			if(siteCommunityBean.getSiteType() == 2){
				siteCommunityBean.setPort(null);
			}
			siteCommunityMapper.updateSiteCommunity(siteCommunityBean);
			return true;
		} catch (Exception e) {
			logger.error("更新站点凭证异常："+e);
		}
		return false;
	}

	@Override
	public boolean delById(Integer id) {
		try {
			siteCommunityMapper.delById(id);
			return true;
		} catch (Exception e) {
			logger.error("删除站点凭证异常："+e);
		}
		return false;
	}

	@Override
	public boolean delByIds(String ids) {
		try {
			siteCommunityMapper.delByIds(ids);
			return true;
		} catch (Exception e) {
			logger.error("批量删除站点凭证异常："+e);
		}
		return false;
	}
	

}
