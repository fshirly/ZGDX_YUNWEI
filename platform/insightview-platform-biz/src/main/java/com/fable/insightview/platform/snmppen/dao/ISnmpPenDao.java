package com.fable.insightview.platform.snmppen.dao;

import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmppen.entity.SnmpPenInfo;

/**
 * @Description:   PEN维护Dao 
 * @author         郑小辉
 * @Date           2014-6-30 上午10:18:50 
 */
public interface ISnmpPenDao extends GenericDao<SnmpPenInfo>{
	/**
	 * ID查询
	 * @param paramName
	 * @param paramVal
	 * @return
	 */
	SnmpPenInfo getInfoById(String paramName,String paramVal);
	/**
	 * 更新数据
	 * @param vo
	 * @return
	 */
	void updateInfo(SnmpPenInfo vo);
	/**
	 * 删除数据
	 * @param vo
	 * @return
	 */
	void deleteInfo(SnmpPenInfo vo);
	/**
	 * 插入数据
	 * @param vo
	 * @return
	 */
	void addInfo(SnmpPenInfo vo); 
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
	FlexiGridPageInfo queryPage(SnmpPenInfo vo,FlexiGridPageInfo flexiGridPageInfo);
}
