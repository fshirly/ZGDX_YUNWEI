package com.fable.insightview.monitor.servermanageddomain.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.servermanageddomain.entity.ServerManagedDomainBean;
import com.fable.insightview.monitor.servermanageddomain.mapper.ServerManagedDomainMapper;
import com.fable.insightview.monitor.servermanageddomain.service.IServerManagedDomainService;

@Service
public class ServerManagedDomainServiceImpl implements IServerManagedDomainService{
	private Logger logger = LoggerFactory
	.getLogger(ServerManagedDomainServiceImpl.class);
	
	@Autowired
	ServerManagedDomainMapper serverManagedDomainMapper;
	
	/**
	 * 获得采集机所辖的管理域
	 */
	@Override
	public String getDomainIds(int serverId) {
		List<ServerManagedDomainBean> serverDomainList = serverManagedDomainMapper.getByServerID(serverId);
		StringBuffer sbuff = new StringBuffer();
		for (int i = 0; i < serverDomainList.size(); i++) {
			if(serverDomainList.get(i).getDomainId()!=1){
				sbuff.append(serverDomainList.get(i).getDomainId()).append(",");
			}else{
				sbuff.append("0,");
			}
		}
		logger.info("长度："+sbuff.length());
		String domainIds = "";
		if(sbuff.length() > 0){
			domainIds = sbuff.substring(0, sbuff.lastIndexOf(",")).toString();
		}
		return domainIds;
	}

	/**
	 * 根据采集机删除
	 */
	@Override
	public boolean delByServerId(int serverId) {
		return serverManagedDomainMapper.delByServerId(serverId);
	}

	/**
	 * 新增
	 */
	@Override
	public boolean insertInfo(ServerManagedDomainBean bean) {
		try {
			serverManagedDomainMapper.insertInfo(bean);
			return true;
		} catch (Exception e) {
			logger.error("新增采集机与管理域关系异常"+e);
			return false;
		}
	}
	

}
