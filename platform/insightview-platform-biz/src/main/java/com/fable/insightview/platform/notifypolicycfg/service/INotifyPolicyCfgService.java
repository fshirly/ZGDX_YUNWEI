package com.fable.insightview.platform.notifypolicycfg.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.fable.insightview.platform.message.entity.PhoneVoiceBean;
import com.fable.insightview.platform.notifypolicycfg.entity.NotifyPolicyCfgBean;
import com.fable.insightview.platform.notifypolicycfg.entity.PolicyTypeBean;
import com.fable.insightview.platform.page.Page;

/**
 * 通知策略配置
 *
 */
public interface INotifyPolicyCfgService {
	/**
	 * 获得所有的策略类型
	 */
	List<PolicyTypeBean> getAllPolicyType();
	
	/**
	 * 获得通知策略的列表数据
	 */
	List<NotifyPolicyCfgBean> selectNotifyPolicyCfg(Page<NotifyPolicyCfgBean> page);
	
	/**
	 * 删除通知策略
	 */
	boolean delNotifyCfg(int policyId);
	
	/**
	 * 批量删除通知策略
	 */
	boolean delNotifyCfgs(String policyIds);
	
	/**
	 * 获得初始化策略类型对应的模板内容
	 */
	Map<String, Object> getTypeContent(int typeId);

	/**
	 * 初始化通知策略的模板内容
	 */
	Map<String, Object> getPolicyContent(int policyId);
	
	/**
	 * 获得所有的录音
	 */
	List<PhoneVoiceBean> getAllVoice();
	
	/**
	 * 校验通知策略规则名称是否已存在
	 */
	boolean checkCfgName(NotifyPolicyCfgBean bean);
	
	/**
	 * 新增或编辑通知策略
	 * @param editFlag 新增或编辑的操作标识
	 */
	boolean doEditPolicyCfg(NotifyPolicyCfgBean bean,String editFlag);
	
	/**
	 * 初始化通知策略
	 * @param policyId
	 * @return
	 */
	Map<String, Object> doInitPolicyCfg(int policyId);
	
	/**
	 * h通知策略
	 * @param policyId
	 * @return
	 */
	List<NotifyPolicyCfgBean> getAllNotifyPolicy();
	
	/**
	 * 
	 * @param 创建通知任务
	 * @param entityName
	 * @param param
	 */
	void notify(Map<String, Object> param);
	
	/**
	 * 
	 * @param 创建通知任务(值班)
	 * @param entityName
	 * @param param
	 */
	void notify(Map<String, Object> param, Integer orderId);
	
	/**
	 * 根据NotifyType获取模板名称
	 * @author chenly
	 */
	String findNameByNotifyType(Integer notifyType);
	
	/**
	 * 获取值班的policyId,PolicyType默认为2
	 * @return
	 */
	Integer findPolicyIdForDuty();
}
