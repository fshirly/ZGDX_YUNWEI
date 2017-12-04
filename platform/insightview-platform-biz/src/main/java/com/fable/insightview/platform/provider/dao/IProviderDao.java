package com.fable.insightview.platform.provider.dao;

import java.util.List;

import com.fable.insightview.platform.entity.SysProviderUserBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;
/**
 * @Description:供应商Dao接口
 * @author zhurt
 * @date 2014-7-9
 */
public interface IProviderDao extends GenericDao<ProviderInfoBean> {
	
	
	List<ProviderInfoBean> queryProviderInfo(ProviderInfoBean providerInfo);
	
	/**
	 *@Description:
	 *@param providerInfoBean
	 *@param flexiGridPageInfo
	 *@returnType List<ProviderInfoBean>
	 */
	List<ProviderInfoBean> getProviderInfoBeanByConditions(	ProviderInfoBean providerInfoBean,
			FlexiGridPageInfo flexiGridPageInfo);

	/**
	 *@Description:
	 *@param paramName
	 *@param paramValue
	 *@returnType ProviderInfoBean
	 */
	ProviderInfoBean getProviderInfoBeanByConditions(String paramName,String paramValue);

	/**
	 *@Description:TODO
	 *@param providerInfoBean 
	 *@returnType void
	 */
	void addProviderInfo(ProviderInfoBean providerInfoBean);
	
	/**
	 *@Description:TODO
	 *@param providerInfoBean 
	 *@returnType void
	 */
	boolean delProviderInfoById(ProviderInfoBean providerInfoBean);
	
	/**
	 *@Description:TODO
	 *@param providerId 
	 *@returnType void
	 */
	void delBatchInfo(String providerId);

	/**
	 *@Description:TODO
	 *@param providerInfoBean 
	 *@returnType void
	 */
	void updateProvider(ProviderInfoBean providerInfoBean);

	/**
	 *@Description:TODO
	 *@param providerInfoBean
	 *@returnType int
	 */
	int getTotalCount(ProviderInfoBean providerInfoBean);

	/**
	 *@Description: 获取供应商树
	 *@returnType List<ProviderInfoBean>
	 */
	List<ProviderInfoBean> findProvideTreeVal();

	/**
	 *@Description:TODO
	 *@param proId
	 *@returnType List<Integer>
	 */
	List<Integer> getUserIdByProId(int proId);

	/**
	 *@Description:TODO
	 *@param providerUserBean
	 *@returnType boolean
	 */
	boolean addProviderUser(SysProviderUserBean providerUserBean);

	/**
	 *@Description:TODO
	 *@param userId
	 *@returnType List<SysProviderUserBean>
	 */
	List<SysProviderUserBean> getProUserByUserId(int userId);
	
	List<SysProviderUserBean> getProUserByUserId(ProviderInfoBean providerInfoBean);
	
	List<SysUserInfoBean> getProUserByProviderID(ProviderInfoBean providerUserBean);

	/**
	 *@Description:TODO
	 *@param providerUserBean
	 *@returnType boolean
	 */
	boolean updateProUserInfo(SysProviderUserBean providerUserBean);

	/**
	 *@Description:TODO
	 *@param userId
	 *@returnType boolean
	 */
	boolean deleteProUserByUserId(int userId);

	/**
	 * 根据id几个获取供应商集合
	 * @param ids
	 * @param projectStepId 
	 * @return
	 */
	List<ProviderInfoBean> getProviderByIds(String ids, int projectStepId); 

	/**
	 * 根据条件查询供应商信息
	 * @param bidId
	 * @param providerID
	 * @return
	 */
	ProviderInfoBean getProviderByConditions(Integer bidId, Integer providerID);

	/**
	 * 过滤已存在供应商，显示供应商列表
	 * @param providerInfoBean
	 * @param flexiGridPageInfo
	 * @param ids
	 * @return
	 */
	List<ProviderInfoBean> getProviderInfoBeanByConditions( 
			ProviderInfoBean providerInfoBean,
			FlexiGridPageInfo flexiGridPageInfo, String ids);

	/**
	 * 查询过滤后的总记录数
	 * @param providerInfoBean
	 * @param ids
	 * @return
	 */
	int getTotalExceptExist(ProviderInfoBean providerInfoBean, String ids);

	List<ProviderInfoBean> queryProviderListByOragWithUser(int userId);

}
