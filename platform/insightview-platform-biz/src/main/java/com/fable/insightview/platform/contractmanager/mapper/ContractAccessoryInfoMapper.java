package com.fable.insightview.platform.contractmanager.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.contractmanager.entity.ContractAccessoryInfo;
import com.fable.insightview.platform.page.Page;

public interface ContractAccessoryInfoMapper {
 /*
  * 添加合同附件信息
  */
 void insertContractAccessoryInfo(ContractAccessoryInfo ContractAccessoryInfo);
 /*
  * 查看合同附件信息
  */
 List<ContractAccessoryInfo> selectContractFileInfoList(Integer contractid);
 /*
  * 删除合同附件信息
  */
 void deleteContractAccessoryInfo(Integer contractID);
}
