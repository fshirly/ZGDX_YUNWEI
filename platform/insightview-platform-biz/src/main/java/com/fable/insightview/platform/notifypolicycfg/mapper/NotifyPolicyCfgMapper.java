package com.fable.insightview.platform.notifypolicycfg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.notifypolicycfg.entity.NotifyPolicyCfgBean;
import com.fable.insightview.platform.page.Page;

/**
 * 通知策略
 *
 */
public interface NotifyPolicyCfgMapper {
	/**
	 * 获得通知策略的列表数据
	 */
	List<NotifyPolicyCfgBean> selectNotifyPolicyCfg(Page<NotifyPolicyCfgBean> page);
	
	/**
	 * 根据id删除通知策略
	 */
	boolean delNotifyCfgByID(int policyId);
	
	/**
	 * 批量删除通知策略
	 */
	boolean delNotifyCfgByIDs(@Param("policyIds")String policyIds);
	
	/**
	 * 根据id获得通知策略信息
	 */
	NotifyPolicyCfgBean getNotifyCfgByID(int policyId);
	
	/**
	 * 根据通知策略名称模糊查询获得数量
	 */
	int getByNameAndID(NotifyPolicyCfgBean bean);
	
	/**
	 * 新增通知策略
	 */
	int insertAlarmNotifyCfg(NotifyPolicyCfgBean bean);
	
	/**
	 * 更新通知策略操作标识
	 */
	int updateNotifyCfgByID(NotifyPolicyCfgBean bean);
	
	/**
	 * 更新通知策略操作标识为删除
	 */
	int updateMFalgByID(int policyId);
	
	/**
	 * 批量更新通知策略操作标识为删除
	 */
	int updateMFalgByIDs(@Param("policyIds")String policyIds);
	
	/**
	 * 获得所有的通知策略
	 */
	List<NotifyPolicyCfgBean> getAllNotifyPolicyCfg();
	
	/**
	 * 获取值班的PolicyId
	 * @return
	 */
	Integer selectPolicyIdForDuty();
}
