package com.fable.insightview.platform.datadumpconfig.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.datadumpconfig.entity.SysDumpBean;
import com.fable.insightview.platform.page.Page;

/**
 * 转储
 *
 */
public interface SysDumpMapper {
	/**
	 * 分页查询转储数据
	 */
	List<SysDumpBean> selectDump(Page<SysDumpBean> page);
	
	/**
	 * 根据条件查询
	 */
	List<SysDumpBean> getByCondition(SysDumpBean bean);
	
	/**
	 * 根据id查询
	 */
	SysDumpBean getByID(int id);
	
	/**
	 * 新增
	 */
	int insertDump(SysDumpBean bean);
	
	/**
	 * 根据id更新
	 */
	int updateDumpByID(SysDumpBean bean);
	
	/**
	 * 根据id删除
	 */
	boolean delById(int id);
	
	/**
	 * 根据ids批量删除
	 */
	boolean delByIds(@Param("ids") String ids);
}
