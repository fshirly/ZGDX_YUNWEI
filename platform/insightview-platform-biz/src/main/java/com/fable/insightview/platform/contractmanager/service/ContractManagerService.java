package com.fable.insightview.platform.contractmanager.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.contractmanager.entity.ComboxBean;
import com.fable.insightview.platform.contractmanager.entity.ContractAccessoryInfo;
import com.fable.insightview.platform.contractmanager.entity.ContractPayment;
import com.fable.insightview.platform.contractmanager.entity.ProjectContract;
import com.fable.insightview.platform.contractmanager.entity.ProjectContractChange;
import com.fable.insightview.platform.page.Page;

public interface ContractManagerService {
	List<ComboxBean> queryProjectCombox();
	List<ComboxBean> queryContractTypeCombox();
	void insertContractInfo(ProjectContract projectContract,List<ContractAccessoryInfo> list);
	List<ProjectContract> queryContractManagerList(Page page);
	ProjectContract contractManageEdit(Integer id);
	List<ContractAccessoryInfo> queryContractFileInfoList(Integer contractID);
	void updatecontractManagerInfo(ProjectContract projectContract,List<ContractAccessoryInfo> list);
	ProjectContract contractManageDetail(Integer id);
	List<ContractPayment> queryContractPaymentList(Page page);
	void addContractPaymentPlan(ContractPayment contractPayment);
	void addContractPaymentNoPlan(ContractPayment contractPayment);
	boolean validatorPlanPayTime(ContractPayment contractPayment);
	boolean validatorNoPlanPayTime(ContractPayment contractPayment);
	Double validatorPlanPaymeny(ContractPayment contractPayment);
	Double validatorNoPlanPaymeny(ContractPayment contractPayment);
	ContractPayment queryContractPaymentInfo(Integer paymentID);
	void updateContractPaymentInfo(ContractPayment contractPayment);
	void  addContractChangeInfo(ProjectContractChange projectContractChange);
	List<ProjectContractChange> queryProjectContractChangeInfo(Page page);
	void updateProjectContractChangeInfo(ProjectContractChange projectContractChange);
	ProjectContractChange queryProjectContractChange(Integer id);
	void deleteProjectContractChangeInfo(Integer id);
	void deleteBatchProjectContractChangeInfo(String ids);
	void deleteContractPaymentInfo(Integer paymentID);
	void deleteProjectContractInfo(Integer id);
	void deleteBatchProjectContractInfo(String ids);
	public boolean updateContractToProject(ProjectContract projectContract, List<String> contractIdsList, Integer projectId);
	public List<ProjectContract> queryContractListByProjectId(Page<ProjectContract> page);
	public List<ProjectContract> queryContractExculdeProjectId(Page<ProjectContract> page);
	public boolean delProjectCompact(ProjectContract projectContract, Integer contractId);
	boolean validatorTimePlanTimeDouble(ContractPayment contractPayment);
	Double getContractPaymentCount(Integer contractid);
	int selectCountExculdeProjectId(Map<String, Object> paramMap, Integer projectId);
	int getCountContractByProjectId(Integer projectId);
	Double getPlanPyment_update(ContractPayment contractPayment);
	Double getNoPlanPayment_update(ContractPayment contractPayment);
}
