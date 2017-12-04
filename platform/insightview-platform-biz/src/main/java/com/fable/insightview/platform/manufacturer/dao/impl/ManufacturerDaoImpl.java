package com.fable.insightview.platform.manufacturer.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;
import com.fable.insightview.platform.manufacturer.dao.IManufacturerDao;
import com.fable.insightview.platform.manufacturer.entity.ManufacturerInfoBean;

/**
 * @Description:厂商DAO实现
 * @author zhurt
 * @date 2014-7-9
 */
@Repository("manufacturerDao")
public class ManufacturerDaoImpl extends
		GenericDaoHibernate<ManufacturerInfoBean> implements IManufacturerDao {

	public String commonConditions(String hql,ManufacturerInfoBean manufacturerInfoBean) {
		if (null != manufacturerInfoBean.getResManuFacturerName()
				&& !"".equals(manufacturerInfoBean.getResManuFacturerName())) {
			hql += " and resManuFacturerName like '%"
					+ manufacturerInfoBean.getResManuFacturerName() + "%'";
		}
		return hql;
	}

	/**
	 *新增厂商信息
	 */
	@Override
	public void addManufacturerInfo(ManufacturerInfoBean manufacturerInfoBean) {
		super.insert(manufacturerInfoBean);
	}

	/**
	 * 删除厂商信息
	 */
	@Override
	public void delManufacturerInfoById(ManufacturerInfoBean manufacturerInfoBean) {
		super.delete(manufacturerInfoBean);
	}

	/**
	 * 查询所有厂商信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ManufacturerInfoBean> queryAllManufacturerInfo() {
		String hql = "from ManufacturerInfoBean as mfc where 1=1 ";
		
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);

		List<ManufacturerInfoBean> manufacturerInfoBeanList = query.list();
		return manufacturerInfoBeanList;
	}

	/**
	 * 查询显示列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ManufacturerInfoBean> getManufacturerInfoBeanByConditions(
			ManufacturerInfoBean manufacturerInfoBean,FlexiGridPageInfo flexiGridPageInfo) {
		String hql = "from ManufacturerInfoBean as mfc where 1=1 ";
		hql = commonConditions(hql, manufacturerInfoBean);
		
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());		
		
		List<ManufacturerInfoBean> manufacturerList = query.list();
		return manufacturerList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ManufacturerInfoBean getManufacturerInfoBeanByConditions(String paramName, String paramValue) {

		List<ManufacturerInfoBean> manufacturerList = this
				.getHibernateTemplate().find(
						"from ManufacturerInfoBean where " + paramName + "='"+ paramValue + "'");
		if (null != manufacturerList && manufacturerList.size() > 0) {
			return manufacturerList.get(0);
		} else {
			return null;
		}
	}

	/**
	 *获得数据库中总记录数
	 */
	@Override
	public int getTotalCount(ManufacturerInfoBean manufacturerInfoBean) {
		String hql = "select count(1) as count from ManufacturerInfo  where 1=1";
		hql = commonConditions(hql, manufacturerInfoBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 修改
	 */
	@Override
	public void updateManufacturer(ManufacturerInfoBean manufacturerInfoBean) {
		super.update(manufacturerInfoBean);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delBatchInfo(String resManuFacturerId) {
		String sql = "delete from ManufacturerInfo where resManufacturerId in ("+ resManuFacturerId + ")";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql).executeUpdate();
	}

	@Override
	public boolean checkName(ManufacturerInfoBean manufacturerInfoBean) {
		
		String hql = "from ManufacturerInfoBean where 1=1 and resManuFacturerName <> '"
					+ manufacturerInfoBean.getResManuFacturerName()+"'";
		
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		
		List<ManufacturerInfoBean> manufacturerList = query.list();
		
		String checkResManuFacturerName = manufacturerInfoBean.getCheckResManuFacturerName();
		
		for(int i = 0; i < manufacturerList.size(); i++){
			if(checkResManuFacturerName.equals(manufacturerList.get(i).getResManuFacturerName())){
				return false;
			}
		}
		return true;
	}	
	
	/**
	 * 查询
	 */
	@SuppressWarnings("unchecked")
	public List<ManufacturerInfoBean> getManuFacInfoByConditions(
			ManufacturerInfoBean manufacturerInfoBean) {
		String hql = "from ManufacturerInfoBean as org where 1=1 ";
		// hql = commonConditions(hql, ManufacturerInfoBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);

		List<ManufacturerInfoBean> manuFacInfoLst = query.list();
		return manuFacInfoLst;
	}

	@Override
	public int getResCategoryByManuFacturerId(int resManuFacturerId) {
		String hql = "select count(1) as count from ResCategoryDefine  where ResManufacturerID="+resManuFacturerId;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	@Override
	public ManufacturerInfoBean getManufacturerInfoByID(int resManuFacturerId) {
		String hql = "from ManufacturerInfoBean where 1=1 and resManuFacturerId ="	+ resManuFacturerId;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<ManufacturerInfoBean> managedDomainList = query.list();
		if(managedDomainList.size() > 0){
			return managedDomainList.get(0);
		}
		return null;
	}
}
