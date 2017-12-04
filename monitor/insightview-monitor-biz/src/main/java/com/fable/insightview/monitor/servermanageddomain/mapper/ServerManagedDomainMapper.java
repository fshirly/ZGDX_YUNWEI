package com.fable.insightview.monitor.servermanageddomain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.servermanageddomain.entity.ServerManagedDomainBean;

public interface ServerManagedDomainMapper {
	List<ServerManagedDomainBean> getByServerID(@Param("serverId")int serverId);
	
	boolean delByServerId(int serverId);
	
	int insertInfo(ServerManagedDomainBean bean);

}
