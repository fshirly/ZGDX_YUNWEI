package com.fable.insightview.platform.provider.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.ByteType;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.provider.dao.IProviderDao;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;
import com.fable.insightview.platform.entity.SysProviderUserBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

/**
 * @Description:供应商Dao实现
 * @author zhurt
 * @date 2014-7-9
 */
@Repository("providerDao")
public class ProviderDaoImpl extends GenericDaoHibernate<ProviderInfoBean>
		implements IProviderDao {

	public String commonConditions(String hql, ProviderInfoBean providerInfoBean) {
		if (null != providerInfoBean.getProviderName()
				&& !"".equals(providerInfoBean.getProviderName())) {
			hql += " and providerName like '%"+ providerInfoBean.getProviderName() + "%'";
		}
		return hql;
	}

	@Override
	public void addProviderInfo(ProviderInfoBean providerInfoBean) {
		super.insert(providerInfoBean);
	}

	@Override
	public boolean delProviderInfoById(ProviderInfoBean providerInfoBean) {
		try{
			super.delete(providerInfoBean);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProviderInfoBean> getProviderInfoBeanByConditions(ProviderInfoBean providerInfoBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		String hql = "from ProviderInfoBean as provider where 1=1 ";
		hql = commonConditions(hql, providerInfoBean);
		
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());

		List<ProviderInfoBean> providerInfoBeanList = query.list();
		return providerInfoBeanList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProviderInfoBean getProviderInfoBeanByConditions(String paramName,String paramValue) {
		List<ProviderInfoBean> providerInfoBeanList = this
				.getHibernateTemplate().find(
						"from ProviderInfoBean where " + paramName + "='"+ paramValue + "'");
		if (null != providerInfoBeanList && providerInfoBeanList.size() > 0) {
			return providerInfoBeanList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int getTotalCount(ProviderInfoBean providerInfoBean) {
		String hql = "select count(1) count from ProviderInfo a where 1=1 ";
		hql = commonConditions(hql, providerInfoBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	@Override
	public void updateProvider(ProviderInfoBean providerInfoBean) {
		super.update(providerInfoBean);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProviderInfoBean> findProvideTreeVal() {
		String hql = "from ProviderInfoBean as provider where 1=1";
		
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		
		List<ProviderInfoBean> proLst = query.list();
		return proLst;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getUserIdByProId(int proId) {
		String sql = "select UserID from SysProviderUser where ProviderID="	+ proId;
		if(proId==0){
			sql = "select UserID from SysProviderUser where 1=1 ";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		
		List<Integer> userIdLst = query.list();
		return userIdLst;
	}
	
	@Override
	public boolean addProviderUser(SysProviderUserBean providerUserBean) {
		try {
			super.insert(providerUserBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysProviderUserBean> getProUserByUserId(int userId) {
		String hql = "from SysProviderUserBean where UserID=" + userId;
		
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		
		List<SysProviderUserBean> proUserLst = query.list();
		return proUserLst;
	}
	
	@Override
	public boolean updateProUserInfo(SysProviderUserBean providerUserBean) {
		try {
			super.update(providerUserBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean deleteProUserByUserId(int userId) {
		String sql = "delete from SysProviderUser where UserID=" + userId;
		
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		
		int i = query.executeUpdate();
		if (i > 0) {
			return true;
		} else {
			return false;
		}

	}
	
	@Override
	public void delBatchInfo(String providerId) {
		String sql = "delete from ProviderInfo where providerId in ("+ providerId + ")";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql).executeUpdate();
	}
	
	@Override
	public List<ProviderInfoBean> queryProviderInfo(
			ProviderInfoBean providerInfo) {
		List<ProviderInfoBean> providerInfos = new ArrayList<ProviderInfoBean>();
		
		String hql = "from ProviderInfoBean where 1=1 ";
		
		Query  query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		
		providerInfos = query.list();
		
		return providerInfos;
	}


	/**
	 * 根据id集合查询供应商
	 */
	@Override
	public List<ProviderInfoBean> getProviderByIds(String ids, int projectStepId) {
		String sql = "select p.providerID as providerId, p.ProviderName,p.Contacts,p.ContactsTelCode,b.BidPrice,b.IsWin from ProviderInfo p left join ProjectidBidProvider b on p.ProviderID=b.ProviderID where b.BidId="+projectStepId+" and p.ProviderID in ("+ids+")";
		Query query = this. getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql)
				.addScalar("providerId",IntegerType.INSTANCE)
				.addScalar("providerName")
				.addScalar("contacts")
				.addScalar("contactsTelCode")
				.addScalar("bidPrice",BigDecimalType.INSTANCE)
				.addScalar("isWin",ByteType.INSTANCE);
		List<ProviderInfoBean> providerList=query.setResultTransformer(Transformers.aliasToBean(ProviderInfoBean.class)).list();
		return providerList;
	}


	@Override
	public ProviderInfoBean getProviderByConditions(Integer bidId, Integer providerID) {
		String sql = "select p.providerID as providerId, p.ProviderName,p.Contacts,p.ContactsTelCode,b.BidPrice,b.IsWin from ProviderInfo p left join ProjectidBidProvider b on p.ProviderID=b.ProviderID where p.ProviderID ="+providerID+" and b.bidId="+bidId;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql)
				.addScalar("providerId",IntegerType.INSTANCE)
				.addScalar("providerName")
				.addScalar("contacts")
				.addScalar("contactsTelCode")
				.addScalar("bidPrice",BigDecimalType.INSTANCE)
				.addScalar("isWin",ByteType.INSTANCE);
		List<ProviderInfoBean> providerList=query.setResultTransformer(Transformers.aliasToBean(ProviderInfoBean.class)).list();
		if(providerList.size()>0){
			return providerList.get(0);
		}
		return null;
	}

	@Override
	public List<ProviderInfoBean> getProviderInfoBeanByConditions(
			ProviderInfoBean providerInfoBean,
			FlexiGridPageInfo flexiGridPageInfo, String ids) {
		String hql = "from ProviderInfoBean provider where ProviderID not in "+ids;
		hql = commonConditions(hql, providerInfoBean);
		
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());

		List<ProviderInfoBean> providerInfoBeanList = query.list();
		return providerInfoBeanList;
	}

	@Override
	public int getTotalExceptExist(ProviderInfoBean providerInfoBean, String ids) {
		String hql = "select count(1) count from ProviderInfo a where ProviderID not in "+ids;
		hql = commonConditions(hql, providerInfoBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 根据供应商ID获得供应商登录用户
	 */
	@Override
	public List<SysProviderUserBean> getProUserByUserId(
			ProviderInfoBean providerInfoBean) {
		String hql = "from SysProviderUserBean where 1=1 and providerId = '"
					+ providerInfoBean.getProviderId() + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysProviderUserBean> sysProviderUserList = query.list();
		return sysProviderUserList;
	}

	@Override
	public List<SysUserInfoBean> getProUserByProviderID(
			ProviderInfoBean providerUserBean) {
		System.out.println("******DAOIMPL"+providerUserBean.getProviderId());
		String sql  ="select p.ProviderID as providerId ,p.ProviderName,s.UserID,su.UserName from SysProviderUser s left join ProviderInfo p on s.ProviderID=p.ProviderID left  join  SysUserInfo su on s.UserID=su.UserID where "
			+"p.ProviderID="+ providerUserBean.getProviderId();
		System.out.println("******DAOIMPL2222");
		Query query = this.getHibernateTemplate().getSessionFactory()
			.getCurrentSession().createSQLQuery(sql)
			.addScalar("providerId",IntegerType.INSTANCE)
			.addScalar("providerName").addScalar("userName").addScalar("userID",IntegerType.INSTANCE);
		System.out.println("******DAOIMPL333333");
		List<SysUserInfoBean> sysProviderUserList = query.setResultTransformer(Transformers.aliasToBean(SysUserInfoBean.class)).list();
		System.out.println("******DAOIMPL444444");
		String u = sysProviderUserList.get(0).getUserName();
		System.out.println("**************"+sysProviderUserList.get(0).getUserName());
		if(sysProviderUserList.size() > 0){
			return sysProviderUserList;
		}
		return null;
	}

	@Override
	public List<ProviderInfoBean> queryProviderListByOragWithUser(int userId) {
		// TODO Auto-generated method stub
		String sql = "SELECT DISTINCT p.ProviderID AS providerId,p.ProviderName FROM ProviderInfo p LEFT JOIN SysProviderOrganization sp ON p.ProviderID = sp.ProviderId WHERE sp.OrganizationId = (SELECT so.OrganizationID FROM SysOrganization so LEFT JOIN SysEmployment se ON so.OrganizationID= se.OrganizationID WHERE se.UserID = " + userId + ")";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).addScalar("providerId",IntegerType.INSTANCE).addScalar("providerName");
		List<ProviderInfoBean> providerInfos = query.setResultTransformer(Transformers.aliasToBean(ProviderInfoBean.class)).list();
		return providerInfos;
	}

}
