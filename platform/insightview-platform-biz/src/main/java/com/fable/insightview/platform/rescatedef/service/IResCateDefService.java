package com.fable.insightview.platform.rescatedef.service;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.rescatedef.entity.ResCateDefInfo;

/**
 * @Description:   产品目录Service 
 * @author         郑小辉
 * @Date           2014-6-30 上午10:11:56 
 */
public interface IResCateDefService {
	/**
	 * ID查询
	 * @param id
	 * @return
	 */
	ResCateDefInfo getInfoById(int id);
	/**
	 * 更新数据
	 * @param vo
	 * @return
	 */
	void updateInfo(ResCateDefInfo vo);
	/**
	 * 删除数据
	 * @param vo
	 * @return
	 */
	void deleteInfo(ResCateDefInfo vo);
	/**
	 * 插入数据
	 * @param vo
	 * @return
	 */
	void addInfo(ResCateDefInfo vo); 
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
	FlexiGridPageInfo queryPage(ResCateDefInfo vo,FlexiGridPageInfo flexiGridPageInfo);
	/**
	 * 查询可以删除的id,不存在外键关系
	 * @param id
	 * @return
	 */
	String getCanDelId(String id);
}
