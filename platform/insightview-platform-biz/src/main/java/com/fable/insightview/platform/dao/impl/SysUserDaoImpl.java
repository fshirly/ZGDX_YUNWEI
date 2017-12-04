package com.fable.insightview.platform.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hsqldb.User;
import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.common.util.CryptoUtil;
import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;

/**
 * 单位组织Dao
 * 
 * @author 武林
 * 
 */
@Repository("sysUserDao")
public class SysUserDaoImpl extends GenericDaoHibernate<SysUserInfoBean>
		implements ISysUserDao {
	private final Logger logger = LoggerFactory.getLogger(SysUserDaoImpl.class);

	public String commonConditions(String hql, SysUserInfoBean sysUserBean) {

		/*if (!"".equals(sysUserBean.getUserIds())
				&& null != sysUserBean.getUserIds()) {
			hql += " and userId in (" + sysUserBean.getUserIds() + ")";
		}*/
		if (null != sysUserBean.getUserAccount()
				&& !"".equals(sysUserBean.getUserAccount())) {
			hql += " and userAccount like '%" + sysUserBean.getUserAccount()
					+ "%'";
		}
		if (null != sysUserBean.getUserName()
				&& !"".equals(sysUserBean.getUserName())) {
			hql += " and userName like '%" + sysUserBean.getUserName() + "%'";
		}
		if (!"".equals(sysUserBean.getIsAutoLock())
				&& -1 != sysUserBean.getIsAutoLock()) {
			hql += " and isAutoLock='" + sysUserBean.getIsAutoLock() + "'";
		}
		if (null != sysUserBean.getMobilePhone()
				&& !"".equals(sysUserBean.getMobilePhone())) {
			hql += " and MobilePhone like '%" + sysUserBean.getMobilePhone()
					+ "%'";
		}
		if (!"".equals(sysUserBean.getState()) && -1 != sysUserBean.getState()) {
			hql += " and State='" + sysUserBean.getState() + "'";
		}
		if (!"".equals(sysUserBean.getStatus())
				&& -1 != sysUserBean.getStatus()) {
			hql += " and Status='" + sysUserBean.getStatus() + "'";
		}
		/*if (!"".equals(sysUserBean.getUserType())
				&& -1 != sysUserBean.getUserType()) {
			hql += " and userType='" + sysUserBean.getUserType() + "'";
		}*/
		if (null != sysUserBean.getGroupIdFilter()
				&& !"".equals(sysUserBean.getGroupIdFilter())) {
			hql += " and t.UserId NOT IN(SELECT UserId FROM SysUserInGroups WHERE GroupId="
					+ sysUserBean.getGroupIdFilter() + ")";
		}
		if (null != sysUserBean.getBelongGroupId()
				&& !"".equals(sysUserBean.getBelongGroupId())) {
			hql += " and t.UserId  IN(SELECT UserId FROM SysUserInGroups WHERE GroupId="
					+ sysUserBean.getBelongGroupId() + ")";
		}
		if (null != sysUserBean.getNotifyToUserFilter()
				&& !"".equals(sysUserBean.getNotifyToUserFilter())) {
			hql += " and t.UserID NOT IN(SELECT UserID FROM AlarmNotifyToUsers WHERE PolicyID="
					+ sysUserBean.getNotifyToUserFilter()
					+ " and UserID is not null)";
		}
		if(!"".equals(sysUserBean.getExceptUserIds()) && sysUserBean.getExceptUserIds() != null){
			hql += " and userId not in (" + sysUserBean.getExceptUserIds() + ")";
		}
		return hql;
	}
	public String commonConditions_1(String hql, SysUserInfoBean sysUserBean) {

		if (!"".equals(sysUserBean.getUserIds())
				&& null != sysUserBean.getUserIds()) {
			hql += " and org.userId in (" + sysUserBean.getUserIds() + ")";
		}
		if (null != sysUserBean.getUserAccount()
				&& !"".equals(sysUserBean.getUserAccount())) {
			hql += " and org.userAccount like '%" + sysUserBean.getUserAccount()
					+ "%'";
		}
		if (null != sysUserBean.getUserName()
				&& !"".equals(sysUserBean.getUserName())) {
			hql += " and org.userName like '%" + sysUserBean.getUserName() + "%'";
		}
		if (!"".equals(sysUserBean.getIsAutoLock())
				&& -1 != sysUserBean.getIsAutoLock()) {
			hql += " and org.isAutoLock='" + sysUserBean.getIsAutoLock() + "'";
		}
		if (null != sysUserBean.getMobilePhone()
				&& !"".equals(sysUserBean.getMobilePhone())) {
			hql += " and org.MobilePhone like '%" + sysUserBean.getMobilePhone()
					+ "%'";
		}
		if (!"".equals(sysUserBean.getState()) && -1 != sysUserBean.getState()) {
			hql += " and org.State='" + sysUserBean.getState() + "'";
		}
		if (!"".equals(sysUserBean.getStatus())
				&& -1 != sysUserBean.getStatus()) {
			hql += " and org.Status='" + sysUserBean.getStatus() + "'";
		}
		/*if (!"".equals(sysUserBean.getUserType())
				&& -1 != sysUserBean.getUserType()) {
			hql += " and userType='" + sysUserBean.getUserType() + "'";
		}*/
		if (null != sysUserBean.getGroupIdFilter()
				&& !"".equals(sysUserBean.getGroupIdFilter())) {
			hql += " and t.UserId NOT IN(SELECT UserId FROM SysUserInGroups WHERE GroupId="
					+ sysUserBean.getGroupIdFilter() + ")";
		}
		if (null != sysUserBean.getBelongGroupId()
				&& !"".equals(sysUserBean.getBelongGroupId())) {
			hql += " and t.UserId  IN(SELECT UserId FROM SysUserInGroups WHERE GroupId="
					+ sysUserBean.getBelongGroupId() + ")";
		}
		if (null != sysUserBean.getNotifyToUserFilter()
				&& !"".equals(sysUserBean.getNotifyToUserFilter())) {
			hql += " and t.UserID NOT IN(SELECT UserID FROM AlarmNotifyToUsers WHERE PolicyID="
					+ sysUserBean.getNotifyToUserFilter()
					+ " and UserID is not null)";
		}
		if(!"".equals(sysUserBean.getExceptUserIds()) && sysUserBean.getExceptUserIds() != null){
			hql += " and userId not in (" + sysUserBean.getExceptUserIds() + ")";
		}
		return hql;
	}
	
	
	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> queryUserByAuto(SysUserInfoBean sysUserBean) {
		String hql = "SELECT UserID,UserAccount,UserName FROM SysUserInfo where 1=1 and State=0";
		if (null != sysUserBean.getUserAccount()
				&& !"".equals(sysUserBean.getUserAccount())
				&& sysUserBean.getUserAccount().trim().length() > 0) {
			hql += " and userAccount like '%" + sysUserBean.getUserAccount()
					+ "%'";
		}
		
		if(0 != sysUserBean.getUserID() && -1!=sysUserBean.getUserID()) {
			hql += " and UserID = " + sysUserBean.getUserID();
		}

		List<SysUserInfoBean> SysUserInfoBeanLst = this.getHibernateTemplate()
				.getSessionFactory().getCurrentSession().createSQLQuery(hql)
				.addScalar("userID", IntegerType.INSTANCE).addScalar(
						"userAccount").addScalar("userName")
				.setResultTransformer(
						Transformers.aliasToBean(SysUserInfoBean.class)).list();

		return SysUserInfoBeanLst;
	}

	/*
	 * 根据用户查找当前部门下所有人
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> queryUserByDepartment(SysUserInfoBean sysUserBean) {
		//String hql = "SELECT UserID,UserAccount,UserName FROM SysUserInfo where 1=1 and State=0";
		String hql = "select us.UserID, us.UserAccount, us.UserName from SysUserInfo us ";
		if (null != sysUserBean.getUserAccount()
				&& !"".equals(sysUserBean.getUserAccount())
				&& sysUserBean.getUserAccount().trim().length() > 0) {
			hql += " and userAccount like '%" + sysUserBean.getUserAccount()
					+ "%'";
		}
		
		if(0 != sysUserBean.getUserID() && -1!=sysUserBean.getUserID()) {
			if(1 == sysUserBean.getUserID()) {
				hql += "where us.UserID = "+sysUserBean.getUserID()+"";
			}else{
				hql += "left join SysEmployment em on us.userId=em.userId where em.deptId=(select DeptID from SysEmployment where UserID = " + sysUserBean.getUserID()+")";
			}
		}
		
		List<SysUserInfoBean> SysUserInfoBeanLst = this.getHibernateTemplate()
				.getSessionFactory().getCurrentSession().createSQLQuery(hql)
				.addScalar("userID", IntegerType.INSTANCE).addScalar(
						"userAccount").addScalar("userName")
				.setResultTransformer(
						Transformers.aliasToBean(SysUserInfoBean.class)).list();

		return SysUserInfoBeanLst;
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@Override
	public boolean updateSysUser(SysUserInfoBean sysUserBean) {
		try {
			super.update(sysUserBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//根据账户更新用户名
	public void updateSysUserSyn(SysUserInfoBean sysUserBean){
		String sql = "update SysUserInfo set userName = ? ,MobilePhone = ? ,UserPassword = ? ,LastModifyTime = ? where UserAccount=?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, sysUserBean.getUserName());
		query.setParameter(1, sysUserBean.getTelephone());
		query.setParameter(2, sysUserBean.getUserPassword());
		query.setParameter(3, sysUserBean.getLastModifyTime());
		query.setParameter(4, sysUserBean.getUserAccount());
		query.executeUpdate();
	}
	
	@Override
	public void updateStuts(String userAccount){
		String sql = "update SysUserInfo set Status = 0,IsAutoLock = 0 where UserAccount = ?";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, userAccount);
		query.executeUpdate();
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@Override
	public int getTotalCount(SysUserInfoBean sysUserBean) {
		String hql = "select count(1) as count from SysUserInfo  a where State = 0 ";
		hql = commonConditions(hql, sysUserBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();

		return count;
	}

	/*
	 * 新增单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addSysUser(SysUserInfoBean sysUserBean) {
		try {
			sysUserBean.setUserPassword(CryptoUtil.Encrypt(sysUserBean
					.getUserPassword()));
			super.insert(sysUserBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 删除单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public int delSysUserById(SysUserInfoBean sysUserBean) {
		int rtVal = 0;
		try {

			// SysUserInfoBean userBean=this.getSysUserByConditions("UserID",
			// sysUserBean.getUserID()+"").get(0);
			// Query
			// query2=this.getHibernateTemplate().getSessionFactory().getCurrentSession()
			// .createQuery("delete from OrganizationalEntityBean where id='"+userBean.getUserAccount()+"'");
			// int result2=query2.executeUpdate();
			String hql = "UPDATE SysUserInfo SET State = 1 WHERE UserID ="
					+ sysUserBean.getUserID();
			SQLQuery query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(hql);
			int result1 = query.executeUpdate();
			if (result1 >= 1) {
				rtVal = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return rtVal;
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> chkUserInfo(SysUserInfoBean sysUserBean) {
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();

		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		Criteria criteria = sess.createCriteria(SysUserInfoBean.class);
		criteria.add(Restrictions.eq("userAccount", sysUserBean
				.getUserAccount()));
		criteria.add(Restrictions.eq("userPassword", sysUserBean
				.getUserPassword()));

		List<SysUserInfoBean> list = criteria.list();
		return list;
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> getSysUserByConditions(
			SysUserInfoBean sysUserBean, FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "select org.*,orgizat.organizationName,depart.DeptName  "
				+ "   from SysUserInfo org LEFT JOIN SysEmployment emp on org.userid=emp.userid"+ 
                      " LEFT JOIN SysOrganization orgizat on emp.organizationID=orgizat.organizationID "+
                      " LEFT JOIN SysDepartment depart on emp.deptid=depart.deptid where org.state=0 ";
		hql = commonConditions_1(hql, sysUserBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		query
				.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
						.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		List<SysUserInfoBean> SysUserInfoBeanLst = query.setResultTransformer(
				Transformers.aliasToBean(SysUserInfoBean.class)).list();
		return SysUserInfoBeanLst;
	}
	
	/*
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> getSysUserByCondition(SysUserInfoBean sysUserBean) {
		String sql = "select sysuser.*,emp.EmployeeCode,dept.DeptName,grade.GradeName from SysUserInfo sysuser " +
				"left join SysEmployment emp on sysuser.UserID = emp.UserID " +
				"left join SysEmploymentGrade grade on emp.GradeID = grade.GradeID " +
				"left join SysDepartment dept on emp.DeptID = dept.DeptID " +
				"where State = 0 ";
		sql = conditionsWithTbName(sql, sysUserBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<SysUserInfoBean> SysUserInfoBeanLst = query.setResultTransformer(
				Transformers.aliasToBean(SysUserInfoBean.class)).list();
		return SysUserInfoBeanLst;
		
		/*String hql = "from SysUserInfoBean  org where State = 0 ";
		hql = commonConditions(hql, sysUserBean);

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		List<SysUserInfoBean> SysUserInfoBeanLst = query.list();
		return SysUserInfoBeanLst;*/
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> getSysUserByGroup(SysUserInfoBean sysUserBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "SELECT * from SysUserInfo  t where 1=1 ";
		hql = commonConditions(hql, sysUserBean);
		hql += " ORDER BY UserAccount ";
		logger.info("所属的用户组为：" + sysUserBean.getBelongGroupId());
		logger.info("执行的hql语句==========" + hql);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		query
				.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
						.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		List<SysUserInfoBean> SysUserInfoBeanLst = query.setResultTransformer(Transformers.aliasToBean(SysUserInfoBean.class)).list();
		return SysUserInfoBeanLst;
	}

	@Override
	public int getTotalCountByGroup(SysUserInfoBean sysUserBean) {
		String hql = "select count(1) from (SELECT * from SysUserInfo  t where State=0 and 1=1 ";
		hql = commonConditions(hql, sysUserBean);
		hql += ") t";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();

		return count;
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> getSysUserByConditions(String paramName,
			String paramValue) {
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from SysUserInfoBean  org where 1=1 and org.state=0 and "
				+ paramName + "='" + paramValue + "'";

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysUserInfoBean> SysUserInfoBeanLst = query.list();

		return SysUserInfoBeanLst;
	}

	/**
	 * 根据用户ids查询用户信息
	 * 
	 * @author chengdawei
	 * @param userIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> getUserNameByUserIds(List<Integer> userIds) {
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();

		Criteria criteria = sess.createCriteria(SysUserInfoBean.class);
		if (!userIds.isEmpty()) {
			criteria.add(Restrictions.in("userID", userIds.toArray()));
		}
		return criteria.list();
	}

	/**
	 * 获取所有用户
	 * 
	 * @author Jiuwei Li
	 * @param userIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> getAllSysUsers() {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from SysUserInfoBean";

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysUserInfoBean> SysUserInfoBeanLst = query.list();
		return SysUserInfoBeanLst;
	}

	/**
	 * 根据部门查询人员
	 * 
	 * @author Maowei
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> getSysUserByDept(DepartmentBean departmentBean) {
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select us.userID, us.userAccount, us.userName from SysUserInfo us left join SysEmployment em on us.userId=em.userId where em.deptId in "
				+ "(SELECT DeptID FROM SysDepartment WHERE FIND_IN_SET(DeptId, getRecursionDeptLst("+departmentBean.getDeptId()+")))";
		SQLQuery sqlQuery = sess.createSQLQuery(sql);
		List<SysUserInfoBean> sysUserList = sqlQuery.setResultTransformer(
				Transformers.aliasToBean(SysUserInfoBean.class)).list();

		return sysUserList;
	}

	@Override
	public boolean lockSysUser(SysUserInfoBean sysUserBean) {
		try {
			String sql = "update SysUserInfo set LockedReason=?,Status=?,LockedTime=?, LastModifyTime=? where UserID=?";
			if (sysUserBean.getLockedTime() != null) {
				sql = "update SysUserInfo set LockedReason=?,Status=?,LastModifyTime=? where UserID=?";
			}
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			if (sysUserBean.getLockedTime() == null) {
				query.setString(0, sysUserBean.getLockedReason());
				query.setInteger(1, sysUserBean.getStatus());
				query.setTimestamp(2, new Date());
				query.setTimestamp(3, new Date());
				query.setInteger(4, sysUserBean.getUserID());
			} else {
				query.setString(0, "");
				query.setInteger(1, sysUserBean.getStatus());
				query.setTimestamp(2, new Date());
				query.setInteger(3, sysUserBean.getUserID());
			}

			int i = query.executeUpdate();
			if (i >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean modifyPwd(SysUserInfoBean sysUserBean) {
		try {
			String sql = "update SysUserInfo set UserPassword=?,LastModifyTime=? where UserID=?";
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			query.setString(0, sysUserBean.getUserPassword());
			query.setTimestamp(1, new Date());
			query.setInteger(2, sysUserBean.getUserID());
			int i = query.executeUpdate();
			if (i >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getUserNameByUserId(int userId) {
		String sql = "select UserName from SysUserInfo where UserID=" + userId;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		String userName = (String) query.uniqueResult();
		return userName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysUserInfoBean getTreeIdByTreeName(String treeName) {
		String hql_dept = "from DepartmentBean  org where DeptName like '%"
				+ treeName + "%'";
		Query query_dept = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql_dept);
		List<DepartmentBean> depts = query_dept.list();
		
		String hql_org = "from OrganizationBean  org where OrganizationName like '%"
				+ treeName + "%'";
		Query query_org = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql_org);
		List<OrganizationBean> orgs = query_org.list();

		String hql_pro = "from ProviderInfoBean  org where ProviderName like '%"
				+ treeName + "%'";
		Query query_pro = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql_pro);
		List<ProviderInfoBean> pros = query_pro.list();

		String treeType = "-1";
		if (depts.size() > 0) {
			String deptIds = "";
			for (int i = 0; i < depts.size(); i++) {
				deptIds = deptIds + ",D" + depts.get(i).getDeptId();
			}
			deptIds.substring(1);
			treeType = treeType + deptIds;
		}
		if (orgs.size() > 0) {
			String orgIds = "";
			for (int i = 0; i < orgs.size(); i++) {
				orgIds = orgIds + ",O" + orgs.get(i).getOrganizationID();
			}
			orgIds.substring(1);
			treeType = treeType + orgIds;
		}
		if (pros.size() > 0) {
			String proIds = "";
			for (int i = 0; i < pros.size(); i++) {
				proIds = proIds + ",P" + pros.get(i).getProviderId();
			}
			proIds.substring(1);
			treeType = treeType + proIds;
		}
		SysUserInfoBean bean = new SysUserInfoBean();
		bean.setTreeType(treeType);
		return bean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> getSysUserByConditions(
			SysUserInfoBean sysUserBean) {
		String hql = "from SysUserInfoBean  org where 1=1 and State = 0 and UserAccount='"
				+ sysUserBean.getUserAccount()
				+ "' and UserID <> "
				+ sysUserBean.getUserID();
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysUserInfoBean> lst = query.list();
		return lst;
	}

	@Override
	public int getOrganization(String userAccount) {
		try {
			String hql = "select count(1) as count from OrganizationalEntity  a where 1=1 and id='"
					+ userAccount + "'";
			logger.info("OrganizationalEntity检查用户名hql= " + hql);
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(hql);
			int i = ((Number) query.uniqueResult()).intValue();
			return i;
		} catch (Exception e) {
			logger.error("OrganizationalEntity检查用户名异常！", e);
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> getSysUserByNotifyFilter(
			SysUserInfoBean sysUserBean, FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "SELECT t.UserID,t.UserName,t.MobilePhone,t.Email from SysUserInfo t where 1=1 and t.State=0";
		hql = commonConditions(hql, sysUserBean);
		hql += " ORDER BY UserAccount ";
		logger.info("所属的告警策略为：" + sysUserBean.getNotifyToUserFilter());
		logger.info("执行的hql语句==========" + hql);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql).addScalar("userID",
						IntegerType.INSTANCE).addScalar("userName").addScalar(
						"mobilePhone").addScalar("email");
		query
				.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
						.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		List<SysUserInfoBean> SysUserInfoBeanLst = query.setResultTransformer(
				Transformers.aliasToBean(SysUserInfoBean.class)).list();
		;
		return SysUserInfoBeanLst;
	}

	@Override
	public int getTotalCountByNotifyFilter(SysUserInfoBean sysUserBean) {
		String hql = "select count(1) from (SELECT * from SysUserInfo  t where t.State=0 and 1=1 ";
		hql = commonConditions(hql, sysUserBean);
		hql += ") t";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();

		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserInfoBean> querySysUser(SysUserInfoBean sysUserBean) {
		String sql = "select sysuser.*,emp.EmployeeCode,dept.DeptName,grade.GradeName from SysUserInfo sysuser " +
				"left join SysEmployment emp on sysuser.UserID = emp.UserID " +
				"left join SysEmploymentGrade grade on emp.GradeID = grade.GradeID " +
				"left join SysDepartment dept on emp.DeptID = dept.DeptID " +
				"where State = 0 ";
		sql = conditionsWithTbName(sql, sysUserBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<SysUserInfoBean> SysUserInfoBeanLst = query.setResultTransformer(
				Transformers.aliasToBean(SysUserInfoBean.class)).list();
		return SysUserInfoBeanLst;
	}
	
	public String conditionsWithTbName(String hql, SysUserInfoBean sysUserBean) {

		if (!"".equals(sysUserBean.getUserIds())
				&& null != sysUserBean.getUserIds()) {
			hql += " and sysuser.userId in (" + sysUserBean.getUserIds() + ")";
		}
		if (null != sysUserBean.getUserAccount()
				&& !"".equals(sysUserBean.getUserAccount())) {
			hql += " and sysuser.userAccount like '%" + sysUserBean.getUserAccount()
					+ "%'";
		}
		if (null != sysUserBean.getUserName()
				&& !"".equals(sysUserBean.getUserName())) {
			hql += " and sysuser.userName like '%" + sysUserBean.getUserName() + "%'";
		}
		if (!"".equals(sysUserBean.getIsAutoLock())
				&& -1 != sysUserBean.getIsAutoLock()) {
			hql += " and sysuser.isAutoLock='" + sysUserBean.getIsAutoLock() + "'";
		}
		if (null != sysUserBean.getMobilePhone()
				&& !"".equals(sysUserBean.getMobilePhone())) {
			hql += " and sysuser.MobilePhone like '%" + sysUserBean.getMobilePhone()
					+ "%'";
		}
		if (!"".equals(sysUserBean.getState()) && -1 != sysUserBean.getState()) {
			hql += " and sysuser.State='" + sysUserBean.getState() + "'";
		}
		if (!"".equals(sysUserBean.getStatus())
				&& -1 != sysUserBean.getStatus()) {
			hql += " and sysuser.Status='" + sysUserBean.getStatus() + "'";
		}
		if (!"".equals(sysUserBean.getUserType())
				&& -1 != sysUserBean.getUserType()) {
			hql += " and sysuser.userType='" + sysUserBean.getUserType() + "'";
		}
		return hql;
	}

	@Override
	public List<SysUserInfoBean> queryUserByExact(SysUserInfoBean sysUserBean) {
		String hql = "SELECT UserID,UserAccount,UserName FROM SysUserInfo where 1=1 and State=0";
		if (null != sysUserBean.getUserAccount()
				&& !"".equals(sysUserBean.getUserAccount())
				&& sysUserBean.getUserAccount().trim().length() > 0) {
			hql += " and userAccount = '" + sysUserBean.getUserAccount()+"'";
		}

		List<SysUserInfoBean> SysUserInfoBeanLst = this.getHibernateTemplate()
				.getSessionFactory().getCurrentSession().createSQLQuery(hql)
				.addScalar("userID", IntegerType.INSTANCE).addScalar(
						"userAccount").addScalar("userName")
				.setResultTransformer(
						Transformers.aliasToBean(SysUserInfoBean.class)).list();

		return SysUserInfoBeanLst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> queryUsers(String userType, String orgId, Map<String, String> conditions, FlexiGridPageInfo flexiGridPageInfo) {
		String sql = "select";
		if ("1".equals(userType)) {
			sql += " sui.UserID,sui.UserName,sui.UserAccount,sye.EmployeeCode,seg.GradeName,sd.DeptName company ,sui.UserType userType from SysUserInfo sui,SysEmployment sye,SysEmploymentGrade seg ,SysDepartment sd where sd.DeptID=sye.DeptID and sye.GradeID=seg.GradeID and sui.State=0 and sui.UserID=sye.UserID and sye.OrganizationID=" + orgId;
			sql = concateIn(sql, conditions);
		}
		if ("2".equals(userType)) {
			sql += " sui.UserID,sui.UserName,sui.UserAccount,pd.ProviderName company,sui.UserType userType from SysUserInfo sui,SysProviderUser spu, ProviderInfo pd, SysProviderOrganization spo where pd.ProviderID=spo.ProviderId and pd.ProviderID=spu.ProviderID and sui.State=0 and sui.UserID=spu.UserID and spo.OrganizationId=" + orgId;
			sql = concateOut(sql, conditions);
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public Integer queryUsersCount(String userType, String orgId, Map<String, String> conditions) {
		String sql = "select count(1) ";
		if ("1".equals(userType)) {
			sql += " from SysUserInfo sui,SysEmployment sye,SysEmploymentGrade seg ,SysDepartment sd where sd.DeptID=sye.DeptID and sye.GradeID=seg.GradeID and sui.State=0 and sui.UserID=sye.UserID and sye.OrganizationID=" + orgId;
			sql = concateIn(sql, conditions);
		}
		if ("2".equals(userType)) {
			sql += " from SysUserInfo sui,SysProviderUser spu, ProviderInfo pd, SysProviderOrganization spo where pd.ProviderID=spo.ProviderId and pd.ProviderID=spu.ProviderID and sui.State=0 and sui.UserID=spu.UserID and spo.OrganizationId=" + orgId;
			sql = concateOut(sql, conditions);
		}
		
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		return ((Number) query.uniqueResult()).intValue();
	}

	/*连接查询内部用户sql*/
	private String concateIn(String sql,Map<String, String> conditions) {
		if (null != conditions.get("deptId") && !"".equals(conditions.get("deptId"))) {
			sql += " and sye.DeptID="+conditions.get("deptId");
		}
		if (null != conditions.get("grade") && !"".equals(conditions.get("grade"))) {
			sql += " and sye.GradeID="+conditions.get("grade");
		}
		if (null != conditions.get("name") && !"".equals(conditions.get("name"))) {
			sql += " and sui.UserName like '%"+conditions.get("name") +"%'";
		}
		if(null != conditions.get("userNo")&& !"".equals(conditions.get("userNo"))){
			sql += " and sye.EmployeeCode like'%"+conditions.get("userNo") +"%'";
		}
		if(null != conditions.get("userIds")&& !"".equals(conditions.get("userIds"))){
			sql += " and sui.UserID not in ("+conditions.get("userIds")+") ";
		}
		return sql;
	}
	/*连接查询外部用户sql*/
	private String concateOut(String sql,Map<String, String> conditions) {
		if (null != conditions.get("provider") && !"".equals(conditions.get("provider"))) {
			sql += " and spu.ProviderID="+conditions.get("provider");
		}
		if (null != conditions.get("name") && !"".equals(conditions.get("name"))) {
			sql += " and sui.UserName like '%"+conditions.get("name") +"%'";
		}
		if(null != conditions.get("userIds")&&!"".equals(conditions.get("userIds"))){
			sql += " and sui.UserID not in ("+conditions.get("userIds")+")";
		}
		return sql;
	}
	/*@Override
	public List querySysUserByDeptId(Integer deptId) {
		// TODO Auto-generated method stub
		String hql = "from SysUserInfoBean s where s.deptId=" + deptId;
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		List departList = query.list();
		return departList;
	}*/

	@Override
	public Integer queryOrgIdByUserInfo(Integer userId) {
		Session sess = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select OrganizationID from SysEmployment where UserID=" + userId;
		SQLQuery sqlQuery = sess.createSQLQuery(sql);
		sqlQuery.addScalar("OrganizationID", IntegerType.INSTANCE);
		Integer orgId = (Integer) sqlQuery.uniqueResult();
		return orgId;
	}


	@Override
	public Integer queryUserIdByIdCard(String idCard) {
		Session sess = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select UserId from SysUserInfo where IDCard='" + idCard +"'";
		SQLQuery sqlQuery = sess.createSQLQuery(sql);
		sqlQuery.addScalar("UserId", IntegerType.INSTANCE);
		Integer userId = (Integer) sqlQuery.uniqueResult();
		return userId;
	}


	@Override
	public List<SysUserInfoBean> findUsersWithinChildDept(DepartmentBean qryDept) {
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select us.userID, us.userAccount, us.userName from SysUserInfo us left join SysEmployment em on us.userId=em.userId "
				+ "where em.deptId in (SELECT DeptID FROM SysDepartment WHERE FIND_IN_SET(DeptId, getRecursionDeptLst("+qryDept.getDeptId()+")))";
		SQLQuery sqlQuery = sess.createSQLQuery(sql);
		List<SysUserInfoBean> sysUserList = sqlQuery.setResultTransformer(
				Transformers.aliasToBean(SysUserInfoBean.class)).list();
		return sysUserList;
	}
	
	
}
