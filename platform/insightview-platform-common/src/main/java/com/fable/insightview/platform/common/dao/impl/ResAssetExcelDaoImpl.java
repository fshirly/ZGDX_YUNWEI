package com.fable.insightview.platform.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.common.dao.IResAssetExcelDao;
import com.fable.insightview.platform.common.entity.ResAssetClassChange;
import com.fable.insightview.platform.common.entity.ResAssetExcelBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;


/**
 * 导入数据Dao
 * 
 * @author 武林
 * 
 */
@Repository("resAssetExcelDao")
public class ResAssetExcelDaoImpl extends
		GenericDaoHibernate<ResAssetExcelBean> implements IResAssetExcelDao {

	public void delResAssetTemp() {
		String hql = "delete from ResAssetExcelBean where 1=1";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = query.executeUpdate();
	}

	public int queryCount() {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String countHql = "select count(1) from ResAssetExcelBean r";
		SQLQuery countQuery = session.createSQLQuery(countHql);
		int count = ((Number) countQuery.uniqueResult()).intValue();
		return count;
	}

	public void updateConfigItem(ResAssetClassChange resAssetClassChange) {
		try {
			Session sess = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
			String hql = "update ResAssetExcelBean as r set r.resAssetType='"
					+ resAssetClassChange.getResAssetItsmClass()
					+ "',r.resTypeId='"
					+ resAssetClassChange.getResCiItsmClass()
					+ "' where r.resAssetType='"
					+ resAssetClassChange.getExportItsmClass() + "'";
			Query query = sess.createQuery(hql);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addResAssetExcel(ResAssetExcelBean resAssetExcelBean) {
		try {
			resAssetExcelBean.setStatus("库存");  //设置默认状态 :库存  nlb
			resAssetExcelBean.setImportTime(new Date());
			super.insert(resAssetExcelBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> configurationItemLst() {
		String sql = "select distinct resAssetType from ResAssetExcelBean";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<String> confItemName = query.list();
		return confItemName;
	}

	@Override
	public List<ResAssetExcelBean> getSerialNoIsNullList() {
		String sql = "select assetName, resAssetType from ResAssetExcelBean where serialNo = ''";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<ResAssetExcelBean> resAssetExcelBeanList = query.setResultTransformer(Transformers.aliasToBean(ResAssetExcelBean.class)).list();
		return resAssetExcelBeanList;
	}

	@Override
	public void updateSerialNo(String assetName, String serialNo) {
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String hql = "update ResAssetExcelBean as r set r.serialNo='"
				+ serialNo + "' where r.assetName='" + assetName + "'";
		Query query = sess.createQuery(hql);
		query.executeUpdate();

	}

	@Override
	public List<String> initCodeTableList(String codeTableName, String name) {
		// TODO Auto-generated method stub
		String sql = "select " + name + " from " + codeTableName;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		return query.list();
	}

	@Override
	public List<ResAssetExcelBean> getAllList() {
		// TODO Auto-generated method stub
		String sql = "select resTypeId, resCiId from ResAssetExcelBean";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<ResAssetExcelBean> resAssetExcelBeanList = query.setResultTransformer(Transformers.aliasToBean(ResAssetExcelBean.class)).list();
		return resAssetExcelBeanList;
	}

}
