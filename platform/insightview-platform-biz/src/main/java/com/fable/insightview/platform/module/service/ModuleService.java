package com.fable.insightview.platform.module.service;

import java.util.List;

import com.fable.insightview.platform.module.entity.ModuleBean;


/**
 * @author fangang
 * 模块管理Service接口
 */
public interface ModuleService {

	/**
	 * 获取模块列表数据
	 * @return
	 * @throws Exception
	 */
	public List<ModuleBean> queryModuleList() throws Exception;
	
	/**
	 * 保存模块列表数据(拖动或调整顺序时需要保存)
	 * @param moduleBeans
	 * @throws Exception
	 */
	public void saveModuleData(ModuleBean[] moduleBeans) throws Exception;
	
	/**
	 * 根据id获取模块信息
	 * @param id 模块id
	 * @return
	 * @throws Exception
	 */
	public ModuleBean getModule(String id) throws Exception;
	
	/**
	 * 添加模块
	 * @param moduleBean
	 * @return
	 * @throws Exception
	 */
	public int addModule(ModuleBean moduleBean) throws Exception;
	
	/**
	 * 更新模块
	 * @param moduleBean
	 * @return
	 * @throws Exception
	 */
	public int updateModule(ModuleBean moduleBean) throws Exception;
	
	/**
	 * 根据id删除模块
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int deleteModule(String id) throws Exception;
	
	/**
	 * 批量删除模块
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public int batchDeleteModule(String[] ids) throws Exception;
}
