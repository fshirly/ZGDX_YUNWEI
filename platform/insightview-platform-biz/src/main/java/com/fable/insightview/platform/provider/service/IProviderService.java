package com.fable.insightview.platform.provider.service;

import java.util.List;

import com.fable.insightview.platform.entity.SysProviderUserBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;

/**
 * 
 * @Description:供应商service接口
 * @author zhurt
 * @date 2014-7-9
 */
public interface IProviderService {

	/**
	 * 查询供应商信息
	 * 
	 * @param providerInfo
	 * @return
	 */
	List<ProviderInfoBean> queryProviderInfos(ProviderInfoBean providerInfo);

	/**
	 * @Description:查询所有供应商信息
	 * @param providerInfoBean
	 * @param flexiGridPageInfo
	 * @returnType List<ProviderInfoBean>
	 */
	List<ProviderInfoBean> getProviderInfoBeanByConditions(
			ProviderInfoBean providerInfoBean,
			FlexiGridPageInfo flexiGridPageInfo);

	/**
	 * @Description:查询所有供应商信息
	 * @param paramName
	 * @param paramValue
	 * @returnType ProviderInfoBean
	 */
	ProviderInfoBean getProviderInfoBeanByConditions(String paramName,
			String paramValue);

	/**
	 * @Description:增加供应商
	 * @param providerInfoBean
	 * @returnType void
	 */
	void addProviderInfo(ProviderInfoBean providerInfoBean);

	/**
	 * @Description:删除供应商
	 * @param providerInfoBean
	 * @returnType void
	 */
	boolean delProviderInfoById(ProviderInfoBean providerInfoBean);

	/**
	 * @Description:批量删除供应商
	 * @param providerId
	 * @returnType void
	 */
	void delBatchInfo(String providerId);

	/**
	 * @Description:修改供应商
	 * @param providerInfoBean
	 * @returnType void
	 */
	void updateProvider(ProviderInfoBean providerInfoBean);

	/**
	 * @Description:获取总记录数
	 * @param providerInfoBean
	 * @returnType int
	 */
	int getTotalCount(ProviderInfoBean providerInfoBean);

	/**
	 * @Description:
	 * @return
	 * @returnType List<ProviderInfoBean>
	 */
	List<ProviderInfoBean> findProvideTreeVal();

	/**
	 * @Description:
	 * @param proId
	 * @returnType List<Integer>
	 */
	List<Integer> getUserIdByProId(int proId);

	/**
	 * @Description:
	 * @param providerUserBean
	 * @returnType boolean
	 */
	boolean addProviderUser(SysProviderUserBean providerUserBean);

	/**
	 * @Description:
	 * @param userId
	 * @returnType List<SysProviderUserBean>
	 */
	List<SysProviderUserBean> getProUserByUserId(int userId);
	
	List<SysUserInfoBean> getProUserByProviderID(ProviderInfoBean providerUserBean);

	/**
	 * @Description:
	 * @param providerUserBean
	 * @returnType boolean
	 */
	boolean updateProUserInfo(SysProviderUserBean providerUserBean);

	/**
	 * @Description:
	 * @param userId
	 * @returnType boolean
	 */
	boolean deleteProUserByUserId(int userId);

	/**
	 * 根据id集合查询供应商集合
	 * 
	 * @param providerIdList
	 * @param projectStepId
	 * @return
	 */
	List<ProviderInfoBean> getproviderList(List<Integer> providerIdList,
			int projectStepId);

	/**
	 * 查询当前修改的供应商信息
	 * 
	 * @param projectidBidProvider
	 */
	ProviderInfoBean getProjectidBidProvider(Integer bidId, Integer providerID);

	/**
	 * 查询已存在的供应商的名字
	 * 
	 * @param bidId
	 * @param ids
	 * @return
	 */
	String getProviderExistNames(int bidId, String ids);

	/**
	 * 过滤已存在的供应商列表
	 * 
	 * @param providerInfoBean
	 * @param flexiGridPageInfo
	 * @param idList
	 * @return
	 */
	List<ProviderInfoBean> getProviderInfoBeanByConditions(
			ProviderInfoBean providerInfoBean,
			FlexiGridPageInfo flexiGridPageInfo, List<Integer> idList);

	/**
	 * 查询过滤后的总记录数
	 * 
	 * @param providerInfoBean
	 * @param idList
	 * @return
	 */
	int getTotalExceptExist(ProviderInfoBean providerInfoBean,
			List<Integer> idList);

	/**
	 * 根据id得到供应商
	 * 
	 * @param id
	 * @return
	 */
	public ProviderInfoBean getProviderInfoById(Integer id);

	ProviderInfoBean getById(Integer providerId);
	
	public boolean checkBeforeDel(ProviderInfoBean providerInfoBean);

	/**
	 * 查询用户所在单位的供应商列表
	 * @param userId
	 * @return
	 */
	List<ProviderInfoBean> queryProviderListByOragWithUser(int userId);
}
