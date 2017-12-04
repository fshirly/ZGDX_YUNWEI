package com.fable.insightview.platform.notifypolicycfg.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.notifypolicycfg.entity.NotifyPolicyContentBean;

public interface PolicyContentMapper {
	/**
	 * 获得初始化策略类型对应的模板内容
	 */
	List<NotifyPolicyContentBean> getPolicyTypeContent(Map map);
	
	/**
	 * 根据策略id获得通知模板
	 */
	List<NotifyPolicyContentBean> getContentByPolicyId(int policyId);
	
	/**
	 * 根据策略id删除通知模板
	 */
	boolean delByPolicyId(int policyId);
	
	/**
	 * 根据策略id批量删除通知模板
	 */
	boolean delByPolicyIds(@Param("policyIds")String policyIds);
	
	/**
	 * 新增通知模板
	 */
	int insertNotifyToUsers(NotifyPolicyContentBean bean);
	
	/**
	 * 根据策略及名称获得通知模板
	 * @param bean
	 * @return
	 */
	NotifyPolicyContentBean getByPolicyAndName(NotifyPolicyContentBean bean);
	
	/**
	 * 根据NotifyType获取模板名称
	 * @param notifyType
	 * @return
	 */
	String selectNameByNotifyType(@Param("notifyType")Integer notifyType);
}
