package com.fable.insightview.platform.contractmanager.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.contractmanager.entity.ComboxBean;
import com.fable.insightview.platform.contractmanager.entity.ContractAccessoryInfo;
import com.fable.insightview.platform.contractmanager.entity.ContractPayment;
import com.fable.insightview.platform.contractmanager.entity.ProjectContract;
import com.fable.insightview.platform.contractmanager.entity.ProjectContractChange;
import com.fable.insightview.platform.contractmanager.mapper.ContractAccessoryInfoMapper;
import com.fable.insightview.platform.contractmanager.mapper.ContractManagerIntoMapper;
import com.fable.insightview.platform.contractmanager.mapper.ContractPaymentInfoMapper;
import com.fable.insightview.platform.contractmanager.mapper.ProjectContractChangeMapper;
import com.fable.insightview.platform.contractmanager.service.ContractManagerService;
import com.fable.insightview.platform.page.Page;
/** 
 * @author liyang
 */
@Service
public class ContractManagerServiceImpl implements ContractManagerService {
	@Autowired
	private ContractManagerIntoMapper  contractManagerIntoMapper;
	@Autowired
	private ContractAccessoryInfoMapper contractAccessoryInfoMapper;
	@Autowired
	private ContractPaymentInfoMapper contractPaymentInfoMapper;
	@Autowired
	private ProjectContractChangeMapper projectContractChangeMapper;
	/*
	 *关联项目下拉框 
	 */
	public List<ComboxBean> queryProjectCombox(){
		return  contractManagerIntoMapper.queryProjectCombox();
	}
	/*
	 * 合同类型下来框
	 */
	public  List<ComboxBean> queryContractTypeCombox(){
		return contractManagerIntoMapper.queryContractTypeCombox();
	}
	/*
	 * 添加合同信息
	 */
	public void insertContractInfo(ProjectContract projectContract,List<ContractAccessoryInfo> list){
		 contractManagerIntoMapper.insertContractInfo(projectContract);
		 Integer ProjectId=projectContract.getId();
		 for(int i=0;i<list.size();i++){
			 ContractAccessoryInfo ContractAccessoryInfobean=list.get(i);
			 ContractAccessoryInfobean.setContractID(ProjectId);
			 contractAccessoryInfoMapper.insertContractAccessoryInfo(ContractAccessoryInfobean);
		 }
		 
		 
	}
	/*
	 * 显示合同信息
	 */
	public List<ProjectContract> queryContractManagerList(Page page){
		return  contractManagerIntoMapper.queryContractList(page);
	}
	/*
	 * 显示合同详细信息
	 */
	public ProjectContract contractManageEdit(Integer id){
		return contractManagerIntoMapper.queryContractInfoDetail(id);
	}
	/*
	 * 显示合同附件
	 */
	 public List<ContractAccessoryInfo> queryContractFileInfoList(Integer contractID){
		 return contractAccessoryInfoMapper.selectContractFileInfoList(contractID);
	 }
	 /*
	  * 合同信息修改
	  */
	 public void updatecontractManagerInfo(ProjectContract projectContract,List<ContractAccessoryInfo> list){
		 contractManagerIntoMapper.updateContractInfo(projectContract);
		 Integer ProjectId=projectContract.getId();
		 contractAccessoryInfoMapper.deleteContractAccessoryInfo(ProjectId);
		 for(int i=0;i<list.size();i++){
			 ContractAccessoryInfo ContractAccessoryInfobean=list.get(i);
			 ContractAccessoryInfobean.setContractID(ProjectId);
			 contractAccessoryInfoMapper.insertContractAccessoryInfo(ContractAccessoryInfobean);
		 }
	 }
	 /*
	  * 合同信息详情
	  */
	 public ProjectContract contractManageDetail(Integer id){
		 return contractManagerIntoMapper.queryContractInfoDetail(id);
	 }
	 /*
	  * 合同付款信息
	  */
	 public List<ContractPayment> queryContractPaymentList(Page page){
		 return contractPaymentInfoMapper.queryContractPaymengList(page);
	 }
	 /*
	  * 增加付款计划
	  */
	 public void addContractPaymentPlan(ContractPayment contractPayment){
		  contractPaymentInfoMapper.insertContractPaymentInfo(contractPayment);
	 }
	 /*
	  * 计划外付款
	  */
	 public void addContractPaymentNoPlan(ContractPayment contractPayment){
		  contractPaymentInfoMapper.insertContractPaymentInfoNoPlan(contractPayment);
	 }
	 /*
	  * 效验计划付款时间是否大于签订时间
	  */
	 public boolean validatorPlanPayTime(ContractPayment contractPayment){
		 Date signTime=contractPaymentInfoMapper.getContractSingTime(contractPayment.getContractID());
		 boolean flag=signTime.before(contractPayment.getPlanPayTime());
		 int a=signTime.compareTo(contractPayment.getPlanPayTime());
		 if(a==0){
			 flag=true;
		 }
		 return flag;
	 }
	 /*
	  * 效验付款时间是否大于签订时间
	  */
	 public boolean validatorNoPlanPayTime(ContractPayment contractPayment){
		 Date signTime=contractPaymentInfoMapper.getContractSingTime(contractPayment.getContractID());
		 boolean flag=signTime.before(contractPayment.getPaymentTime());
		 int a=signTime.compareTo(contractPayment.getPaymentTime());
		 if(a==0){
			 flag=true;
		 }
		 return flag;
	 }
	 /*
	  * 计划付款余额不能超过合同剩余金额
	  */
	 public Double validatorPlanPaymeny(ContractPayment contractPayment){
	    Double count=contractPaymentInfoMapper.validatorNoPlanment(contractPayment.getContractID());//获取计划付款时间总数
	    if(count==null){
	    	count=0.0;
	    }
	    Double total=contractManagerIntoMapper.queryContractInfoDetail(contractPayment.getContractID()).getMoneyAmount().doubleValue();//合同总数
	    Double current_planPay=contractPayment.getPlanPayAmount();
	    BigDecimal dec_count=new BigDecimal(count.toString());//当前付款总数
	    BigDecimal dec_total=new BigDecimal(total.toString());//合同总金额
	    BigDecimal dec_planpay=new BigDecimal(current_planPay.toString());//计划付款金额
	    BigDecimal dec_difference=dec_total.subtract(dec_count);//算出差额
	    boolean flag=true;
	    if(dec_planpay.compareTo(dec_difference)>0){
	    	return dec_planpay.subtract(dec_difference).doubleValue();
	    }else{
	    	return 0.00;
	    }
	 }
	 /*
	  * 付款余额不能超过合同剩余金额
	  */
	 public Double validatorNoPlanPaymeny(ContractPayment contractPayment){
		    Double count=contractPaymentInfoMapper.validatorNoPlanment(contractPayment.getContractID());//获取付款总数
		    if(count==null){
		    	count=0.0;
		    }
		    Double total=contractManagerIntoMapper.queryContractInfoDetail(contractPayment.getContractID()).getMoneyAmount().doubleValue();//合同总数
		    Double current_planPay=contractPayment.getAmount();
		    BigDecimal dec_count=new BigDecimal(count.toString());//当前付款总数
		    BigDecimal dec_total=new BigDecimal(total.toString());//合同总金额
		    BigDecimal dec_planpay=new BigDecimal(current_planPay.toString());//付款金额
		    BigDecimal dec_difference=dec_total.subtract(dec_count);//算出差额
		    boolean flag=true;
		    if(dec_planpay.compareTo(dec_difference)>0){
		    	return dec_planpay.doubleValue();
		    }else{
		    	return 0.00;
		    }
		 }
	 /*
	  * 一条付款计划详细信息
	  */
	 public ContractPayment queryContractPaymentInfo(Integer paymentID){
		 return contractPaymentInfoMapper.queryContractPaymentInfo(paymentID);
	 }
	 /*
	  * 修改付款信息
	  */
	 public void updateContractPaymentInfo(ContractPayment contractPayment){
		  contractPaymentInfoMapper.updateContractPaymentInfo(contractPayment);
	 }
	 /*
	  * 添加合同变更信息
	  */
	 public void  addContractChangeInfo(ProjectContractChange projectContractChange){
		 projectContractChangeMapper.insertProjectContractChangeInfo(projectContractChange);
	 }
	 /*
	  * 查看变更信息（list）
	  */
	 public List<ProjectContractChange> queryProjectContractChangeInfo(Page page){
		 return projectContractChangeMapper.queryProjectContractChangeList(page);
	 }
	 /*
	  * 修改合同变更信息
	  */
	 public void updateProjectContractChangeInfo(ProjectContractChange projectContractChange){
		 projectContractChangeMapper.updateProjectContractChangeInfo(projectContractChange);
	 }
	 /*
	  * 查询合同变更详情
	  */
	 public ProjectContractChange queryProjectContractChange(Integer id){
		 return  projectContractChangeMapper.queryProjectContractChangeInfo(id);
	 }
	 /*
	  * 删除合同变更信息
	  */
	 public void deleteProjectContractChangeInfo(Integer id){
		 projectContractChangeMapper.deleteProjectContractChangeInfo(id);
	 }
	 /*
	  * 批量删除合同变更信息
	  */
	 public void deleteBatchProjectContractChangeInfo(String ids){
	      if(!ids.isEmpty()){
	    	  String[] arrayid=ids.split(",");
	    	  for(int i=0;i<arrayid.length;i++){
	    		  Integer id=Integer.parseInt(arrayid[i]);
	    		  projectContractChangeMapper.deleteProjectContractChangeInfo(id);
	    	  }
	    	  
	      }   
	 }
	 /*
	  * 删除合同付款信息
	  */
	 public void deleteContractPaymentInfo(Integer paymentID){
		 contractPaymentInfoMapper.deleteContractPaymentInfo(paymentID);
	 }
	 /*
	  * 删除合同信息
	  */
	 public void deleteProjectContractInfo(Integer id){
		 contractManagerIntoMapper.deleteProjectContractInfo(id);
	 }
	 /*
	  * 批量删除合同信息
	  */
	 public void deleteBatchProjectContractInfo(String ids){
		 if(!ids.isEmpty()){
	    	  String[] arrayid=ids.split(",");
	    	  for(int i=0;i<arrayid.length;i++){
	    		  Integer id=Integer.parseInt(arrayid[i]);
	    		  contractManagerIntoMapper.deleteProjectContractInfo(id);
	    	  }
	    	  
	      }   
	 }
	 
	 @Override
	 public List<ProjectContract> queryContractListByProjectId(Page<ProjectContract> page) {
		 // TODO Auto-generated method stub
		 return contractManagerIntoMapper.queryContractListByProjectId(page);
	 }
	 
	 @Override
	 public boolean updateContractToProject(ProjectContract projectContract, List<String> contractIdsList, Integer projectId) {
		 // TODO Auto-generated method stub
		 for (int i = 0; i < contractIdsList.size(); i++) {
		 	ProjectContract pContract = contractManageEdit(Integer.parseInt(contractIdsList.get(i)));
		 	pContract.setProjectId(projectId);
		 	contractManagerIntoMapper.updateContractInfo(pContract);
		 }
		 return true;
	 }
	 
	 @Override
	 public List<ProjectContract> queryContractExculdeProjectId(Page<ProjectContract> page) {
		 // TODO Auto-generated method stub
		 return contractManagerIntoMapper.queryContractExculdeProjectId(page);
	 }
	 
	 @Override
	 public boolean delProjectCompact(ProjectContract projectContract, Integer contractId) {
		 // TODO Auto-generated method stub
		 ProjectContract pContract = contractManageEdit(contractId);
		 pContract.setProjectId(null);
		 contractManagerIntoMapper.updateContractInfo(pContract);
		 return true;
	 }
	 /*
	  * 判断是否重复计划时间
	  * @see com.fable.insightview.platform.contractmanager.service.ContractManagerService#validatorTimePlanTimeDouble(com.fable.insightview.platform.contractmanager.entity.ContractPayment)
	  */
	 public boolean validatorTimePlanTimeDouble(ContractPayment contractPayment){
		 boolean flag=true;
		 Integer count_planTime=contractPaymentInfoMapper.validatorPlanTimeDouble(contractPayment);
		 if(count_planTime.intValue()>0){
			 flag=false;
		 }
		 return flag;
	 }
	 /*
	  * 获取已付款总数
	  */
	 public Double getContractPaymentCount(Integer contractid){
		 return contractPaymentInfoMapper.validatorNoPlanment(contractid);
	 }
	 
	 @Override
	 public int selectCountExculdeProjectId(Map<String, Object> paramMap, Integer projectId) {
	 	// TODO Auto-generated method stub
	 	return contractManagerIntoMapper.selectCountExculdeProjectId(paramMap, projectId);
	 }
	 
	 @Override
	 public int getCountContractByProjectId(Integer projectId) {
	 	// TODO Auto-generated method stub
	 	return contractManagerIntoMapper.getCountContractByProjectId(projectId);
	 }
	 /*
	  * 计划付款修改校验
	  */
	 public Double getPlanPyment_update(ContractPayment contractPayment){
		 Double total=contractManagerIntoMapper.queryContractInfoDetail(contractPayment.getContractID()).getMoneyAmount().doubleValue();//合同总金额
		 if(total==null){
			 total=0.0;
		 }
		 Double count=contractPaymentInfoMapper.validatorNoPlanment(contractPayment.getContractID());//当前计划付款总数
		 if(count==null){
			 count=0.0;
		 }
		 //之前的计划付款数
		 Double update_before_planpayment=contractPaymentInfoMapper.queryContractPaymentInfo(contractPayment.getPaymentID()).getPlanPayAmount();
		 if(update_before_planpayment==null){
			 update_before_planpayment=0.0; 
		 }
		 BigDecimal dec_total=new BigDecimal(total.toString());//合同总金额
		 BigDecimal dec_count=new BigDecimal(count.toString());//当前已付款总数
		 BigDecimal dec_update_before_pay=new BigDecimal(update_before_planpayment.toString());//修改前的数据
		 BigDecimal dec_update_after_pay=new BigDecimal(contractPayment.getPlanPayAmount().toString());//修改后的数据
		 BigDecimal dec_difference=dec_count.subtract(dec_update_before_pay).add(dec_update_after_pay);
		 if(dec_difference.compareTo(dec_total)>0){
			 return dec_total.subtract(dec_count).doubleValue();
		 }else{
			 return 0.00;
		 }
	 }
	 /*
	  * 付款修改校验
	  */
	 public Double getNoPlanPayment_update(ContractPayment contractPayment){
		//合同总金额
		 Double total=contractManagerIntoMapper.queryContractInfoDetail(contractPayment.getContractID()).getMoneyAmount().doubleValue();
		 if(total==null){
			 total=0.0;
		 }
		 //当前付款金额
		 Double count=contractPaymentInfoMapper.validatorNoPlanment(contractPayment.getContractID());//获取计划付款总数
		    if(count==null){
		    	count=0.0;
		 }
		 //之前的付款数
		 Double update_before_payment=contractPaymentInfoMapper.queryContractPaymentInfo(contractPayment.getPaymentID()).getAmount();
		 if(update_before_payment==null){
			 update_before_payment=0.0;
		 }
		 BigDecimal dec_total=new BigDecimal(total.toString());//合同总金额
		 BigDecimal dec_count=new BigDecimal(count.toString());//当前已付款总数
		 BigDecimal dec_update_before_pay=new BigDecimal(update_before_payment.toString());//修改前的数据
		 BigDecimal dec_update_after_pay=new BigDecimal(contractPayment.getAmount().toString());//修改后的数据
		 BigDecimal dec_difference=dec_count.subtract(dec_update_before_pay).add(dec_update_after_pay);
		 if(dec_difference.compareTo(dec_total)>0){
			 return dec_total.subtract(dec_count).doubleValue();
		 }else{
			 return 0.00;
		 }
		 
	 }
}
