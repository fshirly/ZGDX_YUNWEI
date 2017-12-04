package com.fable.insightview.platform.dictionary.dao.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.common.util.CryptoUtil;
import com.fable.insightview.platform.dictionary.dao.IDataDictManageDao;
import com.fable.insightview.platform.dictionary.entity.ConstantItemDef;
import com.fable.insightview.platform.dictionary.entity.ConstantTypeDef;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;

@Repository("dataDictManageDao")
public class DataDictManageDaoImpl extends GenericDaoHibernate<ConstantTypeDef> implements IDataDictManageDao {
	
	DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 根据字典项属性拼接hql
	 * @param hql
	 * @param constantItemDef
	 * @return
	 */
	public String commonConditions(String hql, ConstantItemDef constantItemDef) {
		if (null != constantItemDef.getId()
				&& !"".equals(constantItemDef.getId())) {
			hql += " and id = '" + constantItemDef.getId()
					+ "'";
		}
		if (null != constantItemDef.getConstantItemId()
				&& !"".equals(constantItemDef.getConstantItemId())) {
			hql += " and constantItemId = '" + constantItemDef.getConstantItemId() + "'";
		}
		if (!"".equals(constantItemDef.getConstantItemName())
				&& null != constantItemDef.getConstantItemName()) {
			hql += " and constantItemName like '%" + constantItemDef.getConstantItemName() + "%'";
		}
		if (!"".equals(constantItemDef.getRemark())
				&& null != constantItemDef.getRemark()) {
			hql += " and remark like '%" + constantItemDef.getRemark() + "%'";
		}
		if (null != constantItemDef.getConstantTypeId()
				&& !"".equals(constantItemDef.getConstantTypeId())) {
			if(!"-1".equals( constantItemDef.getConstantTypeId()) && !"0".equals( constantItemDef.getConstantTypeId())){
				hql += " and constantTypeId in (" + constantItemDef.getConstantTypeId() + ")";
			}
			
		}
		return hql;
	}
	
	/**
	 * 根据字典类型拼接hql
	 * @param hql
	 * @param constantTypeDef
	 * @return
	 */
	public String typeCommonConditions(String hql, ConstantTypeDef constantTypeDef) {
		if (null != constantTypeDef.getConstantTypeId()
				&& !"".equals(constantTypeDef.getConstantTypeId())) {
			hql += " and constantTypeId in (" + constantTypeDef.getConstantTypeId()
					+ ")";
		}
		if (null != constantTypeDef.getConstantTypeName()
				&& !"".equals(constantTypeDef.getConstantTypeName())) {
			hql += " and constantTypeName like '%" + constantTypeDef.getConstantTypeName() + "%'";
		}
		if (!"".equals(constantTypeDef.getConstantTypeCName())
				&& null != constantTypeDef.getConstantTypeCName()) {
			hql += " and constantTypeCName like '%" + constantTypeDef.getConstantTypeCName() + "%'";
		}
		if (!"".equals(constantTypeDef.getParentTypeID())
				&& null != constantTypeDef.getParentTypeID()) {
			hql += " and parentTypeID = '" + constantTypeDef.getParentTypeID() + "'";
		}
		if (null != constantTypeDef.getRemark()
				&& !"".equals(constantTypeDef.getRemark())) {
			hql += " and remark like '%" + constantTypeDef.getRemark() + "5'";
		}
		return hql;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConstantTypeDef> getConstantTypeTreeVal() {
		String hql = "FROM ConstantTypeDef";

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<ConstantTypeDef> constantTypeList = query.list();
		return constantTypeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConstantItemDef> getConstantItemList(ConstantItemDef constantItemDef,FlexiGridPageInfo flexiGridPageInfo) {
		List<ConstantItemDef> constantItemList = new ArrayList<ConstantItemDef>();
//		if(constantItemDef.getConstantTypeId() ==null || constantItemDef.getConstantTypeId() ==""){
			String hql = "from ConstantItemDef  org where 1=1";
			hql = commonConditions(hql, constantItemDef);
			hql = hql + " order by ShowOrder";
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql);
			query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
					.getRp()));
			query.setMaxResults(flexiGridPageInfo.getRp());
			constantItemList = query.list();
			for (int i = 0; i < constantItemList.size(); i++) {
				ConstantItemDef itemBean=constantItemList.get(i);
				int level=this.getLevel(Integer.parseInt(itemBean.getConstantTypeId()));
				itemBean.setLevel(level+1);
				itemBean.setTypeOrItem(2);
			}
//		}
		/*else{
			boolean isleaf=true;
			ConstantTypeDef constantTypeDef=new ConstantTypeDef();
			constantTypeDef.setConstantTypeCName(constantItemDef.getConstantItemName());
			String constantTypeId=constantItemDef.getConstantTypeId();
			if(constantTypeId.contains(",")==true){
				String[] typeIds=constantTypeId.split(",");
				for (int j = 0; j < typeIds.length; j++) {
					int typeId=Integer.parseInt(typeIds[j]);

					if(typeId != 0){
						isleaf=this.isLeafType(typeId);
					}
					
					if(typeId==0){
						
						List<ConstantTypeDef> constantTypeList = new ArrayList<ConstantTypeDef>();
						constantTypeList=this.getConstantTypeList(constantTypeDef, flexiGridPageInfo);
						for (int i = 0; i < constantTypeList.size(); i++) {
							ConstantItemDef item=new ConstantItemDef();
							int level=this.getLevel(constantTypeList.get(i).getConstantTypeId());
							item.setLevel(level);
							item.setTypeOrItem(1);
							item.setId(constantTypeList.get(i).getConstantTypeId());
							item.setConstantTypeId(constantTypeList.get(i).getParentTypeID()+"");
							item.setConstantItemId(constantTypeList.get(i).getConstantTypeId());
							item.setConstantItemName(constantTypeList.get(i).getConstantTypeCName());
							item.setRemark(constantTypeList.get(i).getRemark());
							constantItemList.add(item);
						}
					}else if(isleaf==false){
						constantTypeDef.setParentTypeID(typeId);
						List<ConstantTypeDef> constantTypeList = new ArrayList<ConstantTypeDef>();
						constantTypeList=this.getConstantTypeList(constantTypeDef, flexiGridPageInfo);
						for (int i = 0; i < constantTypeList.size(); i++) {
							ConstantItemDef item=new ConstantItemDef();
							int level=this.getLevel(constantTypeList.get(i).getConstantTypeId());
							item.setLevel(level);
							item.setTypeOrItem(1);
							item.setId(constantTypeList.get(i).getConstantTypeId());
							item.setConstantTypeId(constantTypeList.get(i).getParentTypeID()+"");
							item.setConstantItemId(constantTypeList.get(i).getConstantTypeId());
							item.setConstantItemName(constantTypeList.get(i).getConstantTypeCName());
							item.setRemark(constantTypeList.get(i).getRemark());
							constantItemList.add(item);
						}
					}
					else{
						String hql = "from ConstantItemDef  org where 1=1";
						hql = commonConditions(hql, constantItemDef);

						Query query = this.getHibernateTemplate().getSessionFactory()
								.getCurrentSession().createQuery(hql);
						query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
								.getRp()));
						query.setMaxResults(flexiGridPageInfo.getRp());
						constantItemList = query.list();
						//增加级数
						for (int i = 0; i < constantItemList.size(); i++) {
							ConstantItemDef itemBean=constantItemList.get(i);
							int level=this.getLevel(Integer.parseInt(itemBean.getConstantTypeId()));
							itemBean.setLevel(level+1);
							itemBean.setTypeOrItem(2);
						}
					}
				
				}
			}else{
				if(Integer.parseInt(constantTypeId) != 0){
					isleaf=this.isLeafType(Integer.parseInt(constantTypeId));
				}
				
				if(Integer.parseInt(constantTypeId)==0){
					
					List<ConstantTypeDef> constantTypeList = new ArrayList<ConstantTypeDef>();
					constantTypeList=this.getConstantTypeList(constantTypeDef, flexiGridPageInfo);
					for (int i = 0; i < constantTypeList.size(); i++) {
						ConstantItemDef item=new ConstantItemDef();
						int level=this.getLevel(constantTypeList.get(i).getConstantTypeId());
						item.setLevel(level);
						item.setTypeOrItem(1);
						item.setId(constantTypeList.get(i).getConstantTypeId());
						item.setConstantTypeId(constantTypeList.get(i).getParentTypeID()+"");
						item.setConstantItemId(constantTypeList.get(i).getConstantTypeId());
						item.setConstantItemName(constantTypeList.get(i).getConstantTypeCName());
						item.setRemark(constantTypeList.get(i).getRemark());
						constantItemList.add(item);
					}
				}else if(isleaf==false){
					constantTypeDef.setParentTypeID(Integer.parseInt(constantTypeId));
					List<ConstantTypeDef> constantTypeList = new ArrayList<ConstantTypeDef>();
					constantTypeList=this.getConstantTypeList(constantTypeDef, flexiGridPageInfo);
					for (int i = 0; i < constantTypeList.size(); i++) {
						ConstantItemDef item=new ConstantItemDef();
						int level=this.getLevel(constantTypeList.get(i).getConstantTypeId());
						item.setLevel(level);
						item.setTypeOrItem(1);
						item.setId(constantTypeList.get(i).getConstantTypeId());
						item.setConstantTypeId(constantTypeList.get(i).getParentTypeID()+"");
						item.setConstantItemId(constantTypeList.get(i).getConstantTypeId());
						item.setConstantItemName(constantTypeList.get(i).getConstantTypeCName());
						item.setRemark(constantTypeList.get(i).getRemark());
						constantItemList.add(item);
					}
				}
				else{
					String hql = "from ConstantItemDef  org where 1=1";
					hql = commonConditions(hql, constantItemDef);

					Query query = this.getHibernateTemplate().getSessionFactory()
							.getCurrentSession().createQuery(hql);
					query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
							.getRp()));
					query.setMaxResults(flexiGridPageInfo.getRp());
					constantItemList = query.list();
					//增加级数
					for (int i = 0; i < constantItemList.size(); i++) {
						ConstantItemDef itemBean=constantItemList.get(i);
						int level=this.getLevel(Integer.parseInt(itemBean.getConstantTypeId()));
						itemBean.setLevel(level+1);
						itemBean.setTypeOrItem(2);
					}
				}
			}
		}*/
		
		
		return constantItemList;
	}

	@Override
	public int getConstantTtemListCount(ConstantItemDef constantItemDef) {
		int count=0;
		if(constantItemDef.getConstantTypeId() ==null || constantItemDef.getConstantTypeId() ==""){
			String hql = "select count(1) as count from SysConstantItemDef a where 1=1 ";
			hql = commonConditions(hql, constantItemDef);

			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(hql);
			count = ((Number) query.uniqueResult()).intValue();
		}else{
			boolean isleaf=true;
			ConstantTypeDef constantTypeDef=new ConstantTypeDef();
			constantTypeDef.setConstantTypeCName(constantItemDef.getConstantItemName());
			String constantTypeId=constantItemDef.getConstantTypeId();
			if(constantTypeId.contains(",")==true){
				String[] typeIds=constantTypeId.split(",");
				for (int j = 0; j < typeIds.length; j++) {
					int typeId=Integer.parseInt(typeIds[j]);
					if(typeId != 0){
						isleaf=this.isLeafType(typeId);
					}
					if(typeId==0){
						count=this.getConstantTypeListCount(constantTypeDef);
					}else if(isleaf==false){
						constantTypeDef.setParentTypeID(typeId);
						count=this.getConstantTypeListCount(constantTypeDef);
					}else{
						String hql = "select count(1) as count from SysConstantItemDef  a where 1=1 ";
						hql = commonConditions(hql, constantItemDef);

						Query query = this.getHibernateTemplate().getSessionFactory()
								.getCurrentSession().createSQLQuery(hql);
						count= ((Number) query.uniqueResult()).intValue();
					}
				}
			}else{
				if(Integer.parseInt(constantTypeId) != 0){
					isleaf=this.isLeafType(Integer.parseInt(constantTypeId));
				}
				if(Integer.parseInt(constantTypeId)==0){
					count=this.getConstantTypeListCount(constantTypeDef);
				}else if(isleaf==false){
					constantTypeDef.setParentTypeID(Integer.parseInt(constantTypeId));
					count=this.getConstantTypeListCount(constantTypeDef);
				}else{
					String hql = "select count(1) as count from SysConstantItemDef a where 1=1 ";
					hql = commonConditions(hql, constantItemDef);

					Query query = this.getHibernateTemplate().getSessionFactory()
							.getCurrentSession().createSQLQuery(hql);
					count= ((Number) query.uniqueResult()).intValue();
				}
			}
			
		}
		
		

		return count;
	}

	@Override
	public boolean addConstantItem(ConstantItemDef constantItemDef) {
		try {
//			int constantItemId=this.getMaxConstItemId(constantItemDef);
//			constantItemDef.setConstantItemId(constantItemId+1);
			constantItemDef.setEffTime(new Timestamp(new Date().getTime()));
			constantItemDef.setUpdateTime(new Timestamp(new Date().getTime()));
			super.insert(constantItemDef);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public int getMaxConstItemId(ConstantItemDef constantItemDef) {
		int constantItemId=0;
		String sql="select max(ConstantItemId) as ConstantItemId  from SysConstantItemDef  a where ConstantTypeId="+constantItemDef.getConstantTypeId();
		Query query = this.getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sql);
		if(query.uniqueResult()!=null ){
			constantItemId=((Number)query.uniqueResult()).intValue();
		}
		
		return constantItemId;
	}
	
	public int getMaxConstItemTypeId() {
		int constantItemTypeId=0;
		String sql="select max(ConstantTypeId) as ConstantTypeId  from SysConstantTypeDef  a where 1=1";
		Query query = this.getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sql);
		if(query.uniqueResult()!=null ){
			constantItemTypeId=((Number)query.uniqueResult()).intValue();
		}
		
		return constantItemTypeId;
	}
	
	@Override
	public List<ConstantTypeDef> getConstantTypeList(
			ConstantTypeDef constantTypeDef, FlexiGridPageInfo flexiGridPageInfo) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		List<ConstantTypeDef> constantTypeList = new ArrayList<ConstantTypeDef>();
		String hql = "from ConstantTypeDef  org where 1=1";
		hql = typeCommonConditions(hql, constantTypeDef);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		constantTypeList = query.list();
		return constantTypeList;
	}


	@Override
	public int getConstantTypeListCount(ConstantTypeDef constantTypeDef) {
		String hql = "select count(1) as count from SysConstantTypeDef  a where 1=1 ";
		hql = typeCommonConditions(hql, constantTypeDef);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();

		return count;
	}

	@Override
	public boolean isLeafType(int constantTypeId) {
		Query query = this.getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(
				"select count(*) from SysConstantTypeDef where ParentTypeID = "
				+ constantTypeId);
		int count = ((Number)query.uniqueResult()).intValue();
		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isLeafItem(int constantTypeId) {
		Query query = this.getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(
				"select count(*) from SysConstantItemDef where ConstantTypeId = "
				+ constantTypeId);
		int count = ((Number)query.uniqueResult()).intValue();
		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public List<ConstantTypeDef> getConstantTypeName(int constantTypeId) {
		
		List<ConstantTypeDef> list =new ArrayList<ConstantTypeDef>();
		Query query = this.getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createQuery("from ConstantTypeDef  org where ConstantTypeId = "
						+ constantTypeId);
		list= query.list();
		return list;
	}

	@Override
	public boolean addDataDictType(ConstantTypeDef constantTypeDef) {
		try {
			int typeId=this.getMaxConstItemTypeId()+1;
			constantTypeDef.setConstantTypeId(typeId);
//			String sql="insert into SysConstantTypeDef (ConstantTypeId,ConstantTypeName,ConstantTypeCName,ParentTypeID,Remark,UpdateTime) values (" +
//			typeId+",'"+constantTypeDef.getConstantTypeName()+"','"+constantTypeDef.getConstantTypeCName()+"'"
//			+","+constantTypeDef.getParentTypeID()+",'"+constantTypeDef.getRemark()+"','"+constantTypeDef.getUpdateTime()+"')";
//			Query query= this.getHibernateTemplate().getSessionFactory()
//			.getCurrentSession().createSQLQuery(sql);
//			int i=query.executeUpdate();
//			if(i>=0){
//				return true;
//			}else{
//				return false;
//			}
			super.insert(constantTypeDef);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
//		return true;
	}

	@Override
	public List<ConstantTypeDef> getParentTypeName(int parentTypeID) {
		List<ConstantTypeDef> list =new ArrayList<ConstantTypeDef>();
		Query query = this.getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createQuery("from ConstantTypeDef  org where ConstantTypeId = "
						+ parentTypeID);
		list= query.list();
		return list;
	}

	@Override
	public boolean deleteConstantItemById(int id) {
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession()
		.createQuery("delete from ConstantItemDef where ID="+id);
		int i=query.executeUpdate();
		if(i>=0){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean deleteConstantTypeById(int constantTypeId) {
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession()
		.createQuery("delete from ConstantTypeDef where ConstantTypeId="+constantTypeId+" or ParentTypeID="+constantTypeId);
		int i=query.executeUpdate();
		if(i>=0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<ConstantItemDef> getConstantItem(int id) {
		List<ConstantItemDef> list =new ArrayList<ConstantItemDef>();
		Query query = this.getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createQuery("from ConstantItemDef as org where ID = "
						+ id);
		list= query.list();
		return list;
	}  

	@Override
	public List<ConstantTypeDef> getConstantType(int constantTypeId) {
		List<ConstantTypeDef> list =new ArrayList<ConstantTypeDef>();
		Query query = this.getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createQuery("from ConstantTypeDef as org where ConstantTypeId = "
						+ constantTypeId);
		list= query.list();
		return list;
	}

	@Override
	public boolean updateDataDictItem(ConstantItemDef constantItemDef) {
		constantItemDef.setUpdateTime(new Timestamp(new Date().getTime()));
//		String hql="update ConstantItemDef set ConstantTypeId='"+constantItemDef.getConstantTypeId()+"',ConstantItemName='"+constantItemDef.getConstantItemName()+"'"
//		+",Remark='"+constantItemDef.getRemark()+"',UpdateTime="+constantItemDef.getUpdateTime()+
//		" where ID="+constantItemDef.getId();
//		Query query= this.getHibernateTemplate().getSessionFactory()
//		.getCurrentSession().createQuery(hql);
//		int i=query.executeUpdate();
//		if(i>=0){
//			return true;
//		}else{
//			return false;
//		}
		try {
			super.update(constantItemDef);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateDataDictType(ConstantTypeDef constantTypeDef) {
		constantTypeDef.setUpdateTime(new Timestamp(new Date().getTime()));
//		String hql="update ConstantTypeDef set ConstantTypeName='"+constantTypeDef.getConstantTypeName()+"',ConstantTypeCName='"+constantTypeDef.getConstantTypeCName()+"'"
//		+",ParentTypeID='"+constantTypeDef.getParentTypeID()+"',Remark='"+constantTypeDef.getRemark()+"',UpdateTime='"+constantTypeDef.getUpdateTime()+
//		"' where ConstantTypeId="+constantTypeDef.getConstantTypeId();
//		if(constantTypeDef.getConstantTypeName()==null || "".equals(constantTypeDef.getConstantTypeName())){
//			hql="update ConstantTypeDef set ConstantTypeCName='"+constantTypeDef.getConstantTypeCName()+"'"
//			+",ParentTypeID='"+constantTypeDef.getParentTypeID()+"',Remark='"+constantTypeDef.getRemark()+"',UpdateTime='"+constantTypeDef.getUpdateTime()+
//			"' where ConstantTypeId="+constantTypeDef.getConstantTypeId();
//		}
//		Query query= this.getHibernateTemplate().getSessionFactory()
//		.getCurrentSession().createQuery(hql);
//		int i=query.executeUpdate();
//		if(i>=0){
//			return true;
//		}else{
//			return false;
//		}
		
		try {
			super.update(constantTypeDef);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int getLevel(int constantTypeId) {
		int i=1;
		int parentTypeId=0;
		
		while(true){
			String hql="select ParentTypeID from SysConstantTypeDef where ConstantTypeId="+constantTypeId;
			Query query= this.getHibernateTemplate().getSessionFactory()
							.getCurrentSession().createSQLQuery(hql);
			parentTypeId=((Number)query.uniqueResult()).intValue();
			if(parentTypeId==0){
				break;
			}else{
				constantTypeId=parentTypeId;
				i++;
			}
		}
		
		return i;
	}

	@Override
	public ConstantTypeDef getTypeIdByTypeName(String typeName) {
		String hql="from ConstantTypeDef where ConstantTypeCName like '%"+typeName+"%'";
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		List<ConstantTypeDef> types=query.list();
		String constantTypeId="";
		for (int i = 0; i < types.size(); i++) {
			constantTypeId=constantTypeId+","+types.get(i).getConstantTypeId();
		}
		ConstantTypeDef constantTypeDef=new ConstantTypeDef();
		constantTypeDef.setConstantTypeIds(constantTypeId.substring(1));
		return constantTypeDef;
	}

	@Override
	public List<ConstantTypeDef> getConstantTypeList(
			ConstantTypeDef constantTypeDef,
			FlexiGridPageInfo flexiGridPageInfo, String type) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		List<ConstantTypeDef> constantTypeList = new ArrayList<ConstantTypeDef>();
		String hql = "from ConstantTypeDef as org where 1=1";
		hql = typeCommonConditions(hql, constantTypeDef);
		if(!"".equals(type)){
			hql +=" and ConstantTypeId <> "+type;
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		constantTypeList = query.list();
		return constantTypeList;
	}

	public int getConstantTypeListCount(ConstantTypeDef constantTypeDef,String type) {
		String hql = "select count(1) as count from SysConstantTypeDef as a where 1=1 ";
		hql = typeCommonConditions(hql, constantTypeDef);
		if(!"".equals(type)){
			hql += " and ConstantTypeId <> "+type;
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();

		return count;
	}

	@Override
	public boolean checkItemId(ConstantItemDef constantItemDef) {
		String hql = "select count(1) as count from SysConstantItemDef as a where ConstantTypeId=? and ConstantItemId=?";
		Query query = this.getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(hql);
		query.setInteger(0, Integer.parseInt(constantItemDef.getConstantTypeId()));
		query.setInteger(1, constantItemDef.getConstantItemId());
		int count = ((Number) query.uniqueResult()).intValue();
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean deleteItemByConstantTypeId(int constantTypeId) {
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession()
		.createQuery("delete from ConstantItemDef where ConstantTypeId="+constantTypeId);
		int i=query.executeUpdate();
		if(i>=0){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkDataDictItemName(ConstantItemDef constantItemDef) {
		String hql = "select count(1) as count from SysConstantItemDef as a where ConstantTypeId=? and ConstantItemName=?";
		Query query = this.getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(hql);
		query.setInteger(0, Integer.parseInt(constantItemDef.getConstantTypeId()));
		query.setString(1, constantItemDef.getConstantItemName());
		int count = ((Number) query.uniqueResult()).intValue();
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean checkDataDictItemAlias(ConstantItemDef constantItemDef) {
		String hql = "select count(1) as count from SysConstantItemDef as a where ConstantTypeId=? and ConstantItemAlias=?";
		Query query = this.getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(hql);
		query.setInteger(0, Integer.parseInt(constantItemDef.getConstantTypeId()));
		query.setString(1, constantItemDef.getConstantItemAlias());
		int count = ((Number) query.uniqueResult()).intValue();
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<ConstantItemDef> findDictDataByDictTypeId(String constantTypeId) {
		List<ConstantItemDef> list = null;
		String hql = "from ConstantItemDef  org where 1=1 and constantTypeId=" + constantTypeId;
		hql += " order by ID desc";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		list = query.list();
		return list;
	}

	@Override
	public List<ConstantTypeDef> getByTypeNameAndId(
			ConstantTypeDef constantTypeDef) {
		List<ConstantTypeDef> typelist = new ArrayList<ConstantTypeDef>();
		int constantTypeId = constantTypeDef.getConstantTypeId();
		String constantTypeName = constantTypeDef.getConstantTypeName();
		String hql = "select * from SysConstantTypeDef where 1=1 and ConstantTypeName='"
				+ constantTypeName + "'";
		if (constantTypeId != -1) {
			hql += " and ConstantTypeId != " + constantTypeId;
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		typelist = query.list();
		return typelist;
	}
	
}
