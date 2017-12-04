package com.fable.insightview.monitor.servermanageddomain.service;

import com.fable.insightview.monitor.servermanageddomain.entity.ServerManagedDomainBean;


public interface IServerManagedDomainService {
	String getDomainIds(int serverId);

	boolean delByServerId(int serverId);
	
	boolean insertInfo(ServerManagedDomainBean bean);
}
