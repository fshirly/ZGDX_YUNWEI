package com.fable.insightview.platform.snmppen.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmppen.dao.ISnmpPenDao;
import com.fable.insightview.platform.snmppen.entity.SnmpPenInfo;

@Repository("snmpPenDao")
public class SnmpPenDaoImpl extends GenericDaoHibernate<SnmpPenInfo> implements ISnmpPenDao {
	
	@Override
	public void addInfo(SnmpPenInfo vo) {
			super.insert(vo);
	}

	@Override
	public void deleteInfo(SnmpPenInfo vo) {
			super.delete(vo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SnmpPenInfo getInfoById(String paramName,String paramVal) {
		List<SnmpPenInfo> list = this.getHibernateTemplate().find("from SnmpPenInfo where " + paramName +"='" + paramVal + "'");
		if(null !=list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void updateInfo(SnmpPenInfo vo) {
			super.update(vo);		
	}

	public String commonConditions(String hql, SnmpPenInfo vo) {
		if (null != vo.getOrganization() && !"".equals(vo.getOrganization())) {
			hql += " and organization LIKE '%" + vo.getOrganization()+ "%'";
		}
		return hql;
	}

	@Override
	public void deleteBathInfo(String id) {
		String sql ="delete from SNMPPEN where id in("+id+")";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public FlexiGridPageInfo queryPage(SnmpPenInfo vo, FlexiGridPageInfo flexiGridPageInfo) {
		StringBuffer sbfSql= new StringBuffer();
		sbfSql.append(" select t.id,t.pen,t.enterpriseOID,t.organization,t.orgAddress,t.postCode,t.contactTelephone,");
		sbfSql.append(" 	   t.contactPerson,t.orgEmail,t.resManufacturerID,m.resManufacturerName");
		sbfSql.append(" from   SNMPPEN t left join ManufacturerInfo m on t.resManufacturerID = m.resManufacturerID where 1=1");
		//条件查询使用占位符
		if (null != vo.getOrganization() && !"".equals(vo.getOrganization())) {
			sbfSql.append(" and organization LIKE :organization");
		}		
		//排序
		if (null != flexiGridPageInfo.getSort()	&& flexiGridPageInfo.getSort() != "" 
			&& null != flexiGridPageInfo.getOrder() && flexiGridPageInfo.getOrder() != "") {
			sbfSql.append(" order by " + flexiGridPageInfo.getSort() + " "	+ flexiGridPageInfo.getOrder());
		}
		//创建session对象
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		/*分页查询 */
		SQLQuery query = session.createSQLQuery(sbfSql.toString()).addScalar("id",Hibernate.INTEGER)
						.addScalar("pen",Hibernate.INTEGER).addScalar("enterpriseOID",Hibernate.STRING).addScalar("organization",Hibernate.STRING)
						.addScalar("orgAddress",Hibernate.STRING).addScalar("postCode",Hibernate.STRING).addScalar("contactTelephone",Hibernate.STRING)
						.addScalar("contactPerson",Hibernate.STRING).addScalar("orgEmail",Hibernate.STRING).addScalar("resManufacturerID",Hibernate.INTEGER)
						.addScalar("resManufacturerName",Hibernate.STRING);
		//填充占位符
		query = setQueryParam(query,vo);		
		//设置分页参数
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		//获取分页数据
		List<SnmpPenInfo> list = query.setResultTransformer(Transformers.aliasToBean(SnmpPenInfo.class)).list();		
		
		/*总记录数查询 */		
		String countHql = "select count(1) from (" + sbfSql.toString() +") p";
		SQLQuery countQuery = session.createSQLQuery(countHql);
		//填充占位符
		countQuery = setQueryParam(countQuery,vo);
		//获取总记录数		
		int count = ((Number) countQuery.uniqueResult()).intValue();
		
		//结果放入分页对象中
		flexiGridPageInfo.setRows(list);		
		flexiGridPageInfo.setTotal(count);
		return flexiGridPageInfo;
	}
	
	/**
	 * 填充占位符数据
	 * @param query
	 * @param vo
	 * @param flexiGridPageInfo
	 * @return
	 */
	public SQLQuery setQueryParam(SQLQuery query,SnmpPenInfo vo){
		//查询条件
		if (null != vo.getOrganization() && !"".equals(vo.getOrganization())) {
			 query.setParameter("organization","%"+vo.getOrganization()+"%");
		}		
		return query;
	}
}
