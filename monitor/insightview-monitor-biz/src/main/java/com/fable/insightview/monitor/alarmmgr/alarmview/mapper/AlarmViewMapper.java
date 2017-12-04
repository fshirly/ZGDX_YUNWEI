package com.fable.insightview.monitor.alarmmgr.alarmview.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.alarmmgr.alarmview.entity.ParamterVo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewColCfgInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewColDefInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewFilterInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewSoundCfgInfo;
import com.fable.insightview.monitor.alarmmgr.entity.MOSourceBean;
import com.fable.insightview.platform.page.Page;

/**
 * @Description:   告警视图自定义
 * @author         zhengxh
 * @Date           2014-7-22
 */
public interface AlarmViewMapper {
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	List<AlarmViewInfo>  queryList(Page<AlarmViewInfo> page);
	/**
	 * 通过id,查询单条告警视图信息	 
	 * @param vo
	 * @return
	 */
	AlarmViewInfo queryViewInfoById(AlarmViewInfo vo);
	/**
	 * 更新告警视图信息	
	 * @param vo
	 * @return
	 */
	int updateInfo(AlarmViewInfo vo);
	/**
	 * 取消当前用户所有告警视图默认标志	
	 * @param vo
	 * @return
	 */
	int updateUserDefault(AlarmViewInfo vo);
	/**
	 * 删除告警视图信息
	 * @param id
	 * @return
	 */
	int deleteInfo(int id);
	/**
	 * 插入告警视图信息
	 * @param vo
	 * @return
	 */
	int insertInfo(AlarmViewInfo vo);
	/**
	 * 过滤查询告警事件
	 * @return
	 */
	List<AlarmEventDefineBean>  queryEventList(Page<AlarmEventDefineBean> page);
	/**
	 * 查询所有告警源对象
	 * @return
	 */
	List<MOSourceBean>  queryMOSourceList(Page<MOSourceBean> page);
	/**
	 * 分页查询所有告警声音根据视图viewCfgID
	 * @param page
	 * @return
	 */	 
	List<AlarmViewSoundCfgInfo>  querySoundList(Page<AlarmViewSoundCfgInfo> page);
	/**
	 * 分页查询显示列根据视图viewCfgID
	 * @param page
	 * @return
	 */
	List<AlarmViewColCfgInfo>  queryColCfgList(Page<AlarmViewColCfgInfo> page);
	/**
	 * 分页查询显示过滤条件根据视图viewCfgID
	 * @param page
	 * @return
	 */
	List<AlarmViewFilterInfo>  queryFilterList(Page<AlarmViewFilterInfo> page);
	/**
	 * 查询所有列定义
	 * @param id
	 * @return
	 */
	List<AlarmViewColDefInfo>  queryColDefList();
	/**
	 * 插入告警显示列
	 * @param vo
	 * @return
	 */
	int insertColCfg(AlarmViewColCfgInfo vo);
	/**
	 * 通过id,查询 告警显示列
	 * @param vo
	 * @return
	 */
	AlarmViewColCfgInfo queryColCfgInfoById(AlarmViewColCfgInfo vo);
	/**
	 * 通过视图ID及colID,查询 告警显示列
	 * @param vo
	 * @return
	 */
	AlarmViewColCfgInfo queryColCfgInfoByColID(AlarmViewColCfgInfo vo);
	/**
	 * 修改告警显示列
	 * @param vo
	 * @return
	 */
	int updateCfgDlg(AlarmViewColCfgInfo vo);
	/**
	 * 删除告警显示列
	 * @param name
	 * @param id
	 * @return
	 */	 
	int deleteColCfgInfo(ParamterVo vo);
	/**
	 * 插入过滤条件
	 * @param vo
	 * @return
	 */
	int insertFilterInfo(AlarmViewFilterInfo vo);
	/**
	 * 删除过滤条件
	 * @param name
	 * @param id
	 * @return
	 */	 
	int deleteFilterInfo(ParamterVo vo);
	/**
	 * 验证对象声音能否插入
	 * @param vo
	 * @return
	 */
	AlarmViewSoundCfgInfo checkSoundLevelInfo(AlarmViewSoundCfgInfo vo);	
	/**
	 * 插入声音
	 * @param vo
	 * @return
	 */
	int insertSoundInfo(AlarmViewSoundCfgInfo vo);
	/**
	 * 删除声音
	 * @param name
	 * @param id
	 * @return
	 */	 
	int deleteSoundInfo(ParamterVo vo);
	/**
	 * 根据用户id查询默认过滤条件	
	 * @param vo 
	 * 		  用户ID放入对象key字段中
	 * @return
	 */
	List<AlarmViewFilterInfo>  queryDefaultFilterByUser(Map map);
	 
	/**
	 * 根据用户id查询默认显示列	
	 * @param 
	 * @return
	 */
	List<AlarmViewColCfgInfo> queryColCfgListByUserID(Map map);
	/**
	 * 如果当前用户有对个视图，取最新的那个视图展示
	 * @param map
	 * @return
	 */
	List<AlarmViewInfo> queryNewViewByUserID(Map map);
	
	
	/**
	 * 根据用户id查询默认视图
	 * @param 
	 * @return
	 */
	List<AlarmViewInfo> queryViewListByUserID(Map map);
	
	/**
	 * 根据用户id查询默认显示列	
	 * @param 
	 * @return
	 */
	List<AlarmViewInfo>  queryAllViewByUserID(ParamterVo vo);
	
	/**
	 *
	 */
	AlarmViewInfo queryViewInfoByCfgID(Map map);
}
