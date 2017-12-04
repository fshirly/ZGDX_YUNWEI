package com.fable.insightview.platform.restSecurity.service;

import java.util.List;

import com.fable.insightview.platform.restSecurity.entity.MethodBean;
import com.fable.insightview.platform.restSecurity.entity.ModuleRestDto;
import com.fable.insightview.platform.restSecurity.entity.RestBean;

/**
 * @author fangang
 * Rest接口权限Service接口
 */
public interface RestSecurityService {

	/**
	 * 通过反射获取系统的Rest接口
	 * @return
	 * @throws Exception
	 */
	List<RestBean> getRestData() throws Exception;
	
	/**
	 * 获取模块关联的Rest接口名称
	 * @param moduleId 模块id
	 * @return 关联的Rest接口名称列表
	 * @throws Exception
	 */
	public String[] queryRestByModuleId(String moduleId) throws Exception;
	
	/**
	 * 保存模块关联的Rest接口
	 * @param moduleId 模块id
	 * @param methodBeans
	 * @throws Exception
	 */
	public void saveRest(String moduleId, MethodBean[] methodBeans) throws Exception;
	
	/**
	 * 获取模块Rest接口数据
	 * @return
	 * @throws Exception
	 */
	public List<ModuleRestDto> queryModuleRestList() throws Exception;
	
//	/**
//	 * 获取角色关联的Rest接口
//	 * @param roleId 角色ID
//	 * @return
//	 * @throws Exception
//	 */
//	public String[] getRestByRoleId(String roleId) throws Exception;
//	
//	/**
//	 * 保存角色关联的Rest接口
//	 * @param roleId 角色ID
//	 * @param restNames Rest接口名称列表
//	 * @throws Exception
//	 */
//	public void saveRoleRest(String roleId, String[] restNames) throws Exception;
//	
//	/**
//	 * 
//	 * @param userId
//	 * @param method
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean isRight(String userId, String method) throws Exception;
}
