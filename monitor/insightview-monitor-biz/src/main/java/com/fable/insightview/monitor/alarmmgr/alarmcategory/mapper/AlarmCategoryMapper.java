package com.fable.insightview.monitor.alarmmgr.alarmcategory.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.alarmcategory.entity.AlarmCategoryBean;
import com.fable.insightview.platform.page.Page;

/**
 * 
 * @Description:AlarmCategoryMapper
 * @author zhurt
 */
public interface AlarmCategoryMapper {
	
	/**
	 *@Description:删除
	 *@param id
	 *@returnType int
	 */
	int deleteByPrimaryKey(Integer id);
	
	/**
	 *@Description:查询
	 *@param page
	 *@returnType List<AlarmGategoryBean>
	 */
	List<AlarmCategoryBean> queryInfoList(Page<AlarmCategoryBean> page);
	
	/**
	 *@Description:
	 *@param id
	 *@returnType AlarmCategoryBean
	 */
	AlarmCategoryBean queryByID(Integer id);
	
	/**
	 *@Description:增加
	 *@param vo
	 *@returnType int
	 */
	int insertSelective(AlarmCategoryBean vo); 
	
	/**
	 *@Description:修改
	 *@param vo
	 *@returnType int
	 */
	int  updateByPrimaryKey(AlarmCategoryBean vo);
	
	/**
	 *@Description:	 判断名称是否存在
	 *@param name
	 *@returnType int
	 */
	int checkName(String name);
	
	int checkNameBeforeUpdate(AlarmCategoryBean vo);
	
    List<AlarmCategoryBean> getAllAlarmGategory();
	
	AlarmCategoryBean getAlarmGategoryById(int categoryID);
	
	/**
	 *@Description:判断是否被使用，当(NUM > 0),已被使用
	 *@param id
	 *@returnType int
	 */
    int  getIsUsed (Integer id ); 
	
}
