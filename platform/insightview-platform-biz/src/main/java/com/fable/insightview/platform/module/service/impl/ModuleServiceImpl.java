package com.fable.insightview.platform.module.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.common.util.Tool;
import com.fable.insightview.platform.module.entity.ModuleBean;
import com.fable.insightview.platform.module.mapper.ModuleBeanMapper;
import com.fable.insightview.platform.module.service.ModuleService;

/**
 * @author fangang
 * 模块管理Service实现类
 */
@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	private ModuleBeanMapper moduleBeanMapper;
	
	@Override
	public List<ModuleBean> queryModuleList() throws Exception {
		return moduleBeanMapper.query();
	}

	@Override
	public void saveModuleData(ModuleBean[] moduleBeans) throws Exception {
		//更新数据
		for(ModuleBean bean : moduleBeans) {
			moduleBeanMapper.updateByPrimaryKeySelective(bean);
		}
	}

	@Override
	public ModuleBean getModule(String id) throws Exception {
		return moduleBeanMapper.selectByPrimaryKey(id);
	}

	@Override
	public int addModule(ModuleBean moduleBean) throws Exception {
		//requestUtil.boToUserbo(moduleBean); //TODO 
		moduleBean.setId(Cast.guid2Str(Tool.newGuid()));
		return moduleBeanMapper.insert(moduleBean);
	}

	@Override
	public int updateModule(ModuleBean moduleBean) throws Exception {
		//requestUtil.boToUserbo(moduleBean) //TODO 
		return moduleBeanMapper.updateByPrimaryKey(moduleBean);
	}

	@Override
	public int deleteModule(String id) throws Exception {
		String[] ids = moduleBeanMapper.queryChildren(id);
		return moduleBeanMapper.batchDelete(ids);
	}

	@Override
	public int batchDeleteModule(String[] ids) throws Exception {
		return moduleBeanMapper.batchDelete(ids);
	}
}
