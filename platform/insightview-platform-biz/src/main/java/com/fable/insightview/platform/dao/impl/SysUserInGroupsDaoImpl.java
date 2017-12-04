package com.fable.insightview.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.ISysUserInGroupsDao;
import com.fable.insightview.platform.entity.SysUserInGroupsBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
/**
 * 用户与组的关联表的信息处理
 * @author Administrator  sanyou
 *
 */
@Repository("sysUserInGroupsDao")
public class SysUserInGroupsDaoImpl extends GenericDaoHibernate<SysUserInGroupsBean> implements ISysUserInGroupsDao{
	private final Logger logger = LoggerFactory.getLogger(SysUserInGroupsDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInGroupsBean> findUserInGroupsByGroupId(int groupId) {
		return this.getHibernateTemplate().find("from SysUserInGroupsBean where groupId="+groupId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInGroupsBean> findUserInGroupsByuserId(int userId) {
		return this.getHibernateTemplate().find("from SysUserInGroupsBean where userId="+userId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInGroupsBean> findUserInGroupsByuserId(String userId) {
		Session sess = getHibernateTemplate().getSessionFactory().getCurrentSession();
	
		String sql="select * from SysUserInGroups where UserId="+userId;		
		return sess.createSQLQuery(sql).addEntity(SysUserInGroupsBean.class).list();
	}

	@Override
	public boolean delete(int userId, int groupId) {
		String hql = "delete from SysUserInGroups where UserID="+userId+" and GroupID="+groupId;
		logger.info("执行的hql语句:"+hql);
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql);
		int rs = query.executeUpdate();
		logger.info("运行的结果："+rs);
		if(rs>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean deleteByGroupID(int groupId) {
		String hql = "delete from SysUserInGroups where GroupID="+groupId;
		logger.info("执行的hql语句:"+hql);
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql);
		int rs = query.executeUpdate();
		logger.info("运行的结果："+rs);
		if(rs>0){
			return true;
		}else{
			return false;
		}
	}
        
}
