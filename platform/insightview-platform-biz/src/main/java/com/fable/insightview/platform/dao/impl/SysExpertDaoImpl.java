package com.fable.insightview.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.ISysExpertDao;
import com.fable.insightview.platform.entity.SysExpertBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;


@Repository("sysExportDao")
public class SysExpertDaoImpl extends GenericDaoHibernate<SysExpertBean> implements ISysExpertDao{

	
	/**
	 * 专家模糊查询
	 * @param hql
	 * @param SysExpertBean
	 * @return
	 */
	public String commonConditions(String hql,
			SysExpertBean sysExpertBean) {
		if (null != sysExpertBean.getName() && !"".equals(sysExpertBean.getName())) {
			hql += " and Name LIKE '%" + sysExpertBean.getName() + "%'";
		}
		if(null != sysExpertBean.getSpecialty() && !"".equals(sysExpertBean.getSpecialty())){
			hql += " and Specialty='" + sysExpertBean.getSpecialty()+"'";
		}
		return hql;
	}
	
	@Override
	public List<SysExpertBean> getExpertByConditions(
			SysExpertBean sysExpertBean, FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		String hql = "from SysExpertBean "
				+ "where 1=1";
		hql = commonConditions(hql, sysExpertBean);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		//List<SysExpertBean> exportList = query.setResultTransformer(Transformers.aliasToBean(SysExpertBean.class)).list();
		List<SysExpertBean> exportList = query.list();
		return exportList;
	}

	@Override
	public int getTotalCount(SysExpertBean sysExpertBean) {
		String hql = "select count(1) from (select * from SysExpert s where 1=1 ";
		hql = commonConditions(hql, sysExpertBean);
		hql += ") s";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	@Override
	public List<SysExpertBean> getSysExpertsInCurrentStep(String ids) {
		String hql = "select ID as id,name,affiliation,mobileNumber,profession from SysExpert where ID in ("+ids+")";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addScalar("id",IntegerType.INSTANCE)
				.addScalar("name")
				.addScalar("affiliation")
				.addScalar("mobileNumber")
				.addScalar("profession");
		List<SysExpertBean> sysExpertList=query.setResultTransformer(Transformers.aliasToBean(SysExpertBean.class)).list();
		return sysExpertList;
	}
	
	
//	@Override
//	public SysExpertBean getExpertByCondition(Integer participateStep,
//			Integer projectStepId, int id) {
//		String sql = "select se.Name from SysExpert se left join ProjectExpert pe on se.ID=pe.ExpertId where pe.ParticipateStep ="+participateStep+" and pe.ProjectStepId="+projectStepId+" and pe.ExpertId="+id;
//		Query query = this.getHibernateTemplate().getSessionFactory()
//				.getCurrentSession().createSQLQuery(sql);
//		List<SysExpertBean> expertList=query.setResultTransformer(Transformers.aliasToBean(SysExpertBean.class)).list();
//		if(expertList.size()>0){
//			return expertList.get(0);
//		}
//		return null;
//	}

	@Override
	public List<SysExpertBean> getExpertByConditions(
			SysExpertBean sysExpertBean, FlexiGridPageInfo flexiGridPageInfo,
			String ids) {
		String hql = "from SysExpertBean where id not in"+ids;
		hql = commonConditions(hql, sysExpertBean);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		//List<SysExpertBean> exportList = query.setResultTransformer(Transformers.aliasToBean(SysExpertBean.class)).list();
		List<SysExpertBean> exportList = query.list();
		return exportList;
	}

	@Override
	public int getTotalExceptExist(SysExpertBean sysExpertBean, String ids) {
		String hql = "select count(1) from (select * from SysExpert s where id not in"+ids;
		hql = commonConditions(hql, sysExpertBean);
		hql += ") s";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}
	
}
