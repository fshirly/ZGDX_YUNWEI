package com.fable.insightview.platform.common.dao.impl;

import java.util.List;

import javassist.bytecode.analysis.Type;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.common.dao.ISecurityUserDao;
import com.fable.insightview.platform.common.entity.ResourcesVO;
import com.fable.insightview.platform.common.entity.SecurityRoleBean;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

/**
 * 单位组织Dao
 * 
 * @author 武林
 * 
 */
@Repository("securityUserDao")
public class SecurityUserDaoImpl extends GenericDaoHibernate<SecurityUserInfoBean>
		implements ISecurityUserDao {

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<SecurityUserInfoBean> chkUserInfo(SecurityUserInfoBean sysUserBean) {
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String hql = "from SecurityUserInfoBean where state=0 and userAccount = :userAccount and userPassword = :userPassword";
		Query query = sess.createQuery(hql);
		query.setParameter("userAccount", sysUserBean.getUserAccount());
		query.setParameter("userPassword", sysUserBean.getUserPassword());
		List<SecurityUserInfoBean> list = query.list();
		return list;
	}
	
	@Override
	public SecurityUserInfoBean getUserInfoByUsername(String userAccount) {
		// "SELECT * FROM USER_INFO WHERE USER_NAME = " + username;
		String hql = "from SecurityUserInfoBean as user where state=0 and user.userAccount = :userAccount";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query.setParameter("userAccount", userAccount);
		return (SecurityUserInfoBean) query.uniqueResult();
	}
	
	@Override
	public List<ResourcesVO> queryAllURLRoles(){
		String sql = "select me.LinkURL linkURL,role.RoleName roleName,role.RoleID roleID from SysMenuModule me,SysRoleMenus roMe,SysRole role where me.MenuID = roMe.MenuID and roMe.RoleID=role.RoleID";
		SQLQuery query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).
				addScalar("linkURL").addScalar("roleName").addScalar("roleID",IntegerType.INSTANCE);
		return query.setResultTransformer(Transformers.aliasToBean(ResourcesVO.class)).list();
	}
	
	@Override
	public List<Object> getRolesByUserAccount(String userAccount){
		String sql = "select a.RoleID id from SysRole a,SysUserGroupRoles b,SysUserInGroups c,SysUserInfo d "
				+ "where d.State=0 and a.RoleID = b.RoleID and b.GroupID = c.GroupID and c.UserID = d.UserID and d.UserAccount = ?";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, userAccount);
		return query.list();
	}

	@Override
	public void insert(SecurityUserInfoBean entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(SecurityUserInfoBean entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(SecurityUserInfoBean entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public SecurityUserInfoBean getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecurityUserInfoBean getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecurityUserInfoBean getBaseInfoByUserId(Integer id) {
		String hql = "from SecurityUserInfoBean as user where state=0 and user.id = :userId";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("userId", new Long(id));
		return (SecurityUserInfoBean) query.uniqueResult();
	}

}
