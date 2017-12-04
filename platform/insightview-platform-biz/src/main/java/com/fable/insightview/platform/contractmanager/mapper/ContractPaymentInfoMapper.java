package com.fable.insightview.platform.contractmanager.mapper;

import java.util.Date;
import java.util.List;

import com.fable.insightview.platform.contractmanager.entity.ContractPayment;
import com.fable.insightview.platform.page.Page;

public interface ContractPaymentInfoMapper {
  /*
   * 付款信息查询
   */
	public List<ContractPayment> queryContractPaymengList(Page page);
  /*
   * 增加付款计划
   */
	public void insertContractPaymentInfo(ContractPayment contractPayment);
	/*
	 * 增加计划外付款
	 */
	public void insertContractPaymentInfoNoPlan(ContractPayment contractPayment);
	/*
	 * 获取签订时间以便进行时间比较
	 */
	public Date getContractSingTime(Integer contractid);
	/*
	 * 获取一个付款信息
	 */
	public ContractPayment queryContractPaymentInfo(Integer paymentID);
	/*
	 * 修改付款信息
	 */
	public void updateContractPaymentInfo(ContractPayment contractPayment);
	/*
	 * 删除付款信息
	 */
	public void deleteContractPaymentInfo(Integer paymentID);
	/*
	 * 获取计划时间次数
	 */
	public Integer validatorPlanTimeDouble(ContractPayment contractPayment);
	/*
	 * 判断计划付款金额
	 */
	public Double validatorPlanment(Integer contractid);
	/*
	 * 判断非计划付款金额
	 */
	public Double validatorNoPlanment(Integer contractid);
}
