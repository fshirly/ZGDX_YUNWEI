package com.fable.insightview.platform.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.DictDao;
import com.fable.insightview.platform.entity.Dict;
import com.fable.insightview.platform.entity.SysConstantTypeBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

@Repository("dictDao")
public class DictDaoImpl extends GenericDaoHibernate<Dict> implements DictDao {

	/**
	 * 根据id查询字典列表
	 */
	@SuppressWarnings("unchecked")
	public List<Dict> getItemsById(Long id) {
		Session sess = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteria = sess.createCriteria(Dict.class);
		criteria.add(Restrictions.eq("constantTypeId", id));
		List<Dict> list = criteria.list();  
		return list;
	}

	@Override
	public SysConstantTypeBean getTypeBeanById(String typeName) {
		Session sess = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = sess.createCriteria(SysConstantTypeBean.class);
		criteria.add(Restrictions.eq("constantTypeName", typeName));
		SysConstantTypeBean bean=(SysConstantTypeBean) criteria.list().get(0);
		return bean;
	}

	@Override
	public Dict getItemsById(Long typeId, Long itemId) {
		Session sess = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteria = sess.createCriteria(Dict.class);
		criteria.add(Restrictions.eq("constantTypeId", typeId));
		criteria.add(Restrictions.eq("constantItemId", itemId));
		Dict dictBean = (Dict) criteria.list().get(0);  
		return dictBean;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysConstantTypeBean> getTypeList(){
		Session sess = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = sess.createCriteria(SysConstantTypeBean.class);
		List<SysConstantTypeBean> list = criteria.list();
		return list;

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public void loadConstant(Map<String, Map<Integer,String>> constantMap) {
		String sql="select SysConstantTypeDef.ConstantTypeId,SysConstantTypeDef.ConstantTypeName,SysConstantTypeDef.ConstantTypeCName, " +
		"SysConstantItemDef.ConstantItemId,SysConstantItemDef.ConstantItemName  " +
		"from SysConstantTypeDef,SysConstantItemDef " +
		"where SysConstantTypeDef.ConstantTypeId=SysConstantItemDef.ConstantTypeId " +
		"order by ConstantItemId,ConstantTypeId,ConstantItemName ";
		Query query=getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).addScalar("constantTypeId", Hibernate.LONG)
					.addScalar("constantTypeName", Hibernate.STRING).addScalar("constantTypeCName", Hibernate.STRING).addScalar("constantItemId", Hibernate.LONG)
					.addScalar("constantItemName", Hibernate.STRING);
		List<Dict> lst = query.setResultTransformer(
				Transformers.aliasToBean(Dict.class)).list();
		String typeName, itemName, typeId;
		Integer itemId;
		for (int i = 0; i < lst.size(); i++) {
			typeId = lst.get(i).getConstantTypeId().toString();
			typeName = lst.get(i).getConstantTypeName();
			itemId = Integer.parseInt(String.valueOf(lst.get(i).getConstantItemId()));
			itemName = lst.get(i).getConstantItemName();
			if (constantMap.containsKey(typeName)) {
				constantMap.get(typeName).put(itemId, itemName);
			} else {
				Map<Integer,String> itemMap = new LinkedHashMap<Integer,String>();
				itemMap.put(itemId, itemName);
				constantMap.put(typeName, itemMap);

			}
			if (constantMap.containsKey(typeId)) {
				constantMap.get(typeId).put(itemId, itemName);
			} else {
				Map<Integer,String> itemMap = new LinkedHashMap<Integer,String>();
				itemMap.put(itemId, itemName);
				constantMap.put(typeId, itemMap);

			}
		}
	}
}
