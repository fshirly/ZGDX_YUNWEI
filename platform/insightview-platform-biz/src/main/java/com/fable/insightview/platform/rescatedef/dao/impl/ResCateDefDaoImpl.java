package com.fable.insightview.platform.rescatedef.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.common.util.CTD;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.rescatedef.dao.IResCateDefDao;
import com.fable.insightview.platform.rescatedef.entity.ResCateDefInfo;
@Repository("resCateDefDao")
public class ResCateDefDaoImpl extends GenericDaoHibernate<ResCateDefInfo> implements IResCateDefDao {
	

	@Override
	public void addInfo(ResCateDefInfo vo) {
			super.insert(vo);
	}

	@Override
	public void deleteInfo(ResCateDefInfo vo) {
			super.delete(vo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResCateDefInfo getInfoById(int id) {
		List<ResCateDefInfo> list = this.getHibernateTemplate().find("from ResCateDefInfo where ResCategoryID="+id);
		if(null !=list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void updateInfo(ResCateDefInfo vo) {
			super.update(vo);
	}
	
	@Override
	public void deleteBathInfo(String id) {
		String sql ="delete from ResCategoryDefine where resCategoryID in("+id+")";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public FlexiGridPageInfo queryPage(ResCateDefInfo vo, FlexiGridPageInfo flexiGridPageInfo) {
		StringBuffer sbfSql= new StringBuffer();
		sbfSql.append(" select org.resCategoryID,org.resCategoryName,org.resCategoryAlias,org.resCategoryDescr,");
		sbfSql.append(" 	   org.resManufacturerID,org.resModel,orb.resManufacturerName, ");
		sbfSql.append("        org.createTime,org.lastModifyTime ");
		sbfSql.append(" from   ResCategoryDefine org left join ManufacturerInfo orb on org.resManufacturerID=orb.resManufacturerID where 1=1");
		//条件查询使用占位符
		if (null != vo.getResCategoryName() && !"".equals(vo.getResCategoryName())) {			
			sbfSql.append(" and ResCategoryName LIKE :resCategoryName");
		}	
		//排序
		if (null != flexiGridPageInfo.getSort()	&& flexiGridPageInfo.getSort() != "" 
			&& null != flexiGridPageInfo.getOrder() && flexiGridPageInfo.getOrder() != "") {
			sbfSql.append(" order by " + flexiGridPageInfo.getSort() + " "	+ flexiGridPageInfo.getOrder());
		}
		//创建session对象
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
						  
		/*分页查询 */
		SQLQuery query = session.createSQLQuery(sbfSql.toString()).addScalar("resCategoryID",Hibernate.INTEGER)
						  .addScalar("resCategoryName",Hibernate.STRING)
						  .addScalar("resCategoryAlias",Hibernate.STRING).addScalar("resCategoryDescr",Hibernate.STRING)
						  .addScalar("resManufacturerID",Hibernate.INTEGER).addScalar("resModel",Hibernate.STRING)
						  .addScalar("resManufacturerName",Hibernate.STRING).addScalar("createTime",Hibernate.DATE)
						  .addScalar("lastModifyTime",Hibernate.DATE);	
		//填充占位符
		query = setQueryParam(query,vo);		
		//设置分页参数
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		//获取分页数据
		List<ResCateDefInfo> list = query.setResultTransformer(Transformers.aliasToBean(ResCateDefInfo.class)).list();		
		
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
	public SQLQuery setQueryParam(SQLQuery query,ResCateDefInfo vo){
		//查询条件
		if (null != vo.getResCategoryName() && !"".equals(vo.getResCategoryName())) {
			query.setParameter("resCategoryName","%"+vo.getResCategoryName()+"%");
		}
		return query;
	}

	@Override
	public String getCanDelId(String id) {
		String sql = " select case when m.ResCategoryID is null then '-1' else m.ResCategoryID end  ResCategoryID " +
				" FROM ( select   GROUP_CONCAT(t.ResCategoryID) ResCategoryID from ResCategoryDefine t" +
				" where t.ResCategoryID in("+id+") and t.DepreciateID is null and t.AssetTypeID is null) m ";
		//如果是oracle,替换函数
		if(CTD.isOracle()){
			sql = sql.replace("GROUP_CONCAT", "WM_CONCAT");
		}
		id =this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult().toString();
		return id;
	}
}
