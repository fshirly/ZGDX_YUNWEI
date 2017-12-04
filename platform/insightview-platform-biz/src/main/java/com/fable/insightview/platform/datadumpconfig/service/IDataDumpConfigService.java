package com.fable.insightview.platform.datadumpconfig.service;

import java.util.Map;

import com.fable.insightview.platform.datadumpconfig.entity.DataDumpConfigBean;
import com.fable.insightview.platform.datadumpconfig.entity.SysDumpBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.sysconf.entity.SysConfig;

/**
 * 数据转储配置
 *
 */
public interface IDataDumpConfigService {
	/**
	 * 新增
	 */
	public boolean addConfig(SysConfig sysConfig);
	
	/**
	 * 获得数据转储设置
	 */
	public DataDumpConfigBean initDataDumpConfig();
	
	/**
	 * 配置数据转储设置
	 */
	public boolean doSetDataDumpConfig(DataDumpConfigBean bean);
	
	/**
	 * 分页查询转储列表
	 */
	Map<String, Object> listDump(Page<SysDumpBean> page);
	
	/**
	 * 删除
	 */
	boolean delDump(int id);
	
	/**
	 * 批量删除
	 */
	boolean delDumps(String ids);
	
	/**
	 * 验证是否存在
	 * @return true:不存在 
	 * 		   false：已经存在
	 */
	boolean isExist(SysDumpBean bean);
	
	/**
	 * 新增转储表信息
	 */
	boolean addDataDump(SysDumpBean bean);
	
	/**
	 * 初始化编辑转储表信息页面
	 */
	SysDumpBean initDumpDetail(int id);
	
	/**
	 * 编辑转储表信息
	 */
	boolean updateDataDump(SysDumpBean bean);
}
