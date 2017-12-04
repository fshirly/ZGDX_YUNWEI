package com.fable.insightview.platform.restypedef.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.restypedef.dao.IResTypeDefDao;
import com.fable.insightview.platform.restypedef.entity.ResTypeDefineInfo;

@Repository("resTypeDefDao")
public class ResTypeDefDaoImpl extends GenericDaoHibernate<ResTypeDefineInfo> implements IResTypeDefDao {
	
	
	@Override
	public void addInfo(ResTypeDefineInfo vo) {
			super.insert(vo);
	}

	@Override
	public void deleteInfo(ResTypeDefineInfo vo) {
			super.delete(vo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResTypeDefineInfo getInfoById(int id) {
		List<ResTypeDefineInfo> list = this.getHibernateTemplate().find("from ResTypeDefineInfo where ResTypeID="+id);
		if(null !=list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void updateInfo(ResTypeDefineInfo vo) {
			super.update(vo);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ResTypeDefineInfo> getResTypeDefineTree(String paramName,
			String paramValue) {
		String hql = "from ResTypeDefineInfo as org where 1=1 ";
		if (null != paramName && !"".equals(paramName.trim())) {
			hql += " and " + paramName + "='" + paramValue + "'";
		}
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		List<ResTypeDefineInfo> list = query.list();
		return list;
	}

	@Override
	public void deleteBathInfo(String id) {
		String sql ="delete from ResTypeDefine where resTypeID in("+id+")";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public FlexiGridPageInfo queryPage(ResTypeDefineInfo vo, FlexiGridPageInfo flexiGridPageInfo) {
		StringBuffer sbfSql= new StringBuffer();
		sbfSql.append(" select t.resTypeID,t.resTypeName,t.resTypeAlias,t.resTypeDescr,t.parentTypeId,");
		sbfSql.append(" 	  t.createTime,t.lastModifyTime,t.icon");
		sbfSql.append(" from ResTypeDefine t where 1=1");
		//条件查询使用占位符
		if (null != vo.getResTypeName() && !"".equals(vo.getResTypeName())) {			
			sbfSql.append(" and ResTypeName LIKE :resTypeName");
		}	
		//排序
		if (null != flexiGridPageInfo.getSort()	&& flexiGridPageInfo.getSort() != "" 
			&& null != flexiGridPageInfo.getOrder() && flexiGridPageInfo.getOrder() != "") {
			sbfSql.append(" order by " + flexiGridPageInfo.getSort() + " "	+ flexiGridPageInfo.getOrder());
		}
		//创建session对象
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		/*分页查询 */
		SQLQuery query = session.createSQLQuery(sbfSql.toString()).addScalar("resTypeID",Hibernate.INTEGER)
								 .addScalar("resTypeName",Hibernate.STRING).addScalar("resTypeAlias",Hibernate.STRING)
								 .addScalar("resTypeDescr",Hibernate.STRING).addScalar("parentTypeId",Hibernate.INTEGER)
								 .addScalar("createTime",Hibernate.DATE).addScalar("lastModifyTime",Hibernate.DATE)
								 .addScalar("icon",Hibernate.STRING);
		//填充占位符
		query = setQueryParam(query,vo);		
		//设置分页参数
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		//获取分页数据
		List<ResTypeDefineInfo> list = query.setResultTransformer(Transformers.aliasToBean(ResTypeDefineInfo.class)).list();		
		
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
	public SQLQuery setQueryParam(SQLQuery query,ResTypeDefineInfo vo){
		//查询条件
		if (null != vo.getResTypeName() && !"".equals(vo.getResTypeName())) {			
			query.setParameter("resTypeName","%"+vo.getResTypeName()+"%");
		}		
		return query;
	}
	
	
}
