package com.fable.insightview.platform.restypedef.service;

import java.util.List;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.restypedef.entity.ResTypeDefineInfo;

/**
 * @Description:   产品类型 Service
 * @author         郑小辉
 * @Date           2014-6-30 上午10:14:40 
 */
public interface IResTypeDefService {
	/**
	 * ID查询
	 * @param id
	 * @return
	 */
	ResTypeDefineInfo getInfoById(int id);
	/**
	 * 更新数据
	 * @param vo
	 * @return
	 */
	void updateInfo(ResTypeDefineInfo vo);
	/**
	 * 删除数据
	 * @param vo
	 * @return
	 */
	void deleteInfo(ResTypeDefineInfo vo);
	/**
	 * 插入数据
	 * @param vo
	 * @return
	 */
	void addInfo(ResTypeDefineInfo vo); 
	/**
	 * 根据条件查询所有数据
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	List<ResTypeDefineInfo> getResTypeDefineTree(String paramName,String paramValue);
	/**
	 * 批量删除
	 * @param id
	 */
	void deleteBathInfo(String id);	
	/**
	 * 分页查询
	 * @param vo
	 * @param flexiGridPageInfo
	 * @return
	 */
	FlexiGridPageInfo queryPage(ResTypeDefineInfo vo,FlexiGridPageInfo flexiGridPageInfo);
}
