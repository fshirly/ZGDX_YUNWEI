package com.fable.insightview.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.IDepartmentDao;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

/**
 * 部门Dao
 * 
 * @author 武林
 * 
 */

@Repository("departmentDao")
public class DepartmentDaoImpl extends GenericDaoHibernate<DepartmentBean>
		implements IDepartmentDao {
	private final Logger logger = LoggerFactory
			.getLogger(DepartmentDaoImpl.class);

	/*
	 * 条件拼接hql
	 */
	public String commonConditions(String hql, DepartmentBean departmentBean) {
		if (null != departmentBean.getDeptName()
				&& !"".equals(departmentBean.getDeptName())) {
			logger.info("过滤的部门名称：" + departmentBean.getDeptName());
			hql += " and deptName LIKE '%" + departmentBean.getDeptName()
					+ "%'";
		}
		if (null != departmentBean.getOrganizationID()
				&& !"".equals(departmentBean.getOrganizationID())
				&& -1 != departmentBean.getOrganizationID()
				&& 0 != departmentBean.getOrganizationID()) {
			hql += " and organizationID = "
					+ departmentBean.getOrganizationID() + "";
		}
		if (null != departmentBean.getOrganizationName()
				&& !"".equals(departmentBean.getOrganizationName())) {
			logger.info("过滤的部门名称：" + departmentBean.getOrganizationName());
			List<Integer> orgIDByorgNameList = getOrganizationIDByOrgName(departmentBean
					.getOrganizationName());
			if (orgIDByorgNameList.size() > 0) {
				hql += " and organizationID  in(" + orgIDByorgNameList.get(0);
				for (int i = 1; i < orgIDByorgNameList.size(); i++) {
					hql += "," + orgIDByorgNameList.get(i);
				}
				hql += ")";
			} else {
				List<DepartmentBean> deptList = getDepartmentByConditions("",
						"");
				List<Integer> alldeptIDLst = new ArrayList<Integer>();
				for (int i = 0; i < deptList.size(); i++) {
					alldeptIDLst.add(deptList.get(i).getDeptId());
				}
				hql += " and deptId not  in(" + alldeptIDLst.get(0);
				for (int i = 1; i < alldeptIDLst.size(); i++) {
					hql += "," + alldeptIDLst.get(i);
				}
				hql += ")";
			}
		}

		if (null != departmentBean.getDeptId()
				&& !"".equals(departmentBean.getDeptId())
				&& departmentBean.getDeptId() != -1) {
			List<Integer> deptIDList = new ArrayList<Integer>();
			deptIDList.add(departmentBean.getDeptId());
			List<Integer> childIDList = getChildIdByDeptIDList(deptIDList);
			if (!"".equals(departmentBean.getTreeType())
					&& null != departmentBean.getTreeType()
					&& departmentBean.getTreeType().startsWith("D")) {
				hql += " and organizationID ="
						+ getOrganizationIDByDeptID(departmentBean.getDeptId());
			}
			for (int id : childIDList) {
				deptIDList.add(id);
			}
			hql += " and deptId  in(" + deptIDList.get(0);
			for (int i = 1; i < deptIDList.size(); i++) {
				hql += "," + deptIDList.get(i);
			}
			hql += ")";
		}

		if (null != departmentBean.getNodeID()
				&& !"".equals(departmentBean.getNodeID())
				&& !"-1".equals(departmentBean.getNodeID())) {
			String[] nodeIds = departmentBean.getNodeID().split(",");
			List<Integer> deptIDList = new ArrayList<Integer>();
			for (int i = 1; i < nodeIds.length; i++) {
				int orgID = Integer.parseInt(nodeIds[i]);
				deptIDList.add(orgID);
			}
			for (int i = 0; i < deptIDList.size(); i++) {
				List<Integer> childIDList = getAllDeptID(deptIDList.get(i));
				for (int id : childIDList) {
					deptIDList.add(id);
				}
			}
			hql += " and deptId  in(" + deptIDList.get(0);
			for (int i = 1; i < deptIDList.size(); i++) {
				hql += "," + deptIDList.get(i);
			}
			hql += ")";
		}
		return hql;
	}

	/*
	 * 查询部门
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentBean> getDepartmentBeanByConditions(String paramName,
			String paramValue) {
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from DepartmentBean where 1=1";
		if (null != paramName && !"".equals(paramName.trim())) {
			hql += " and " + paramName + "='" + paramValue + "'";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<DepartmentBean> departmentBeanLst = query.list();
		return departmentBeanLst;
	}

	/*
	 * 更新部门
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateDepartmentBean(DepartmentBean departmentBean) {
		try {
			super.update(departmentBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getTotalCount(DepartmentBean departmentBean) {
		String hql = "select count(1) as count from SysDepartment where 1=1";
		hql = commonConditions(hql, departmentBean);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();

		return count;
	}

	/*
	 * 新增部门
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addDepartment(DepartmentBean departmentBean) {
		try {
			super.insert(departmentBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 删除部门
	 * 
	 * @author 武林
	 */
	@Override
	public boolean delDepartmentById(DepartmentBean departmentBean) {
		try {
			super.delete(departmentBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 查询部门
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentBean> getDepartmentByConditions(
			DepartmentBean departmentBean, FlexiGridPageInfo flexiGridPageInfo) {
		String hql = "from DepartmentBean where 1=1";
		hql = commonConditions(hql, departmentBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query
				.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
						.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		List<DepartmentBean> departmentBeanLst = query.list();
		for (int i = 0; i < departmentBeanLst.size(); i++) {
			String headName = getHeadNameByHeadID(departmentBeanLst.get(i)
					.getHeadOfDept());
			departmentBeanLst.get(i).setHeadName(headName);
		}
		return departmentBeanLst;
	}
	
	/*
	 * 查询部门
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentBean> getDepartmentByConditions(DepartmentBean departmentBean) {
		String hql = "from DepartmentBean where 1=1";
		hql = commonConditions(hql, departmentBean);
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		List<DepartmentBean> departmentBeanLst = query.list();
		for (int i = 0; i < departmentBeanLst.size(); i++) {
			String headName = getHeadNameByHeadID(departmentBeanLst.get(i)
					.getHeadOfDept());
			departmentBeanLst.get(i).setHeadName(headName);
		}
		return departmentBeanLst;
	}

	/*
	 * 根据用户Id获得部门
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentBean> getDepartmentByUserID(Integer id) {
		Integer deptId = null;
		// 先根据userId查询deptId
		String sql = "select em.DeptId from SysEmployment em where em.UserId="
				+ id;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Object> sysEmploymentInfo = query.list();
		for (Object sysEmploymentBean : sysEmploymentInfo) {
			if (null != sysEmploymentBean) {
				deptId = Integer.parseInt(String.valueOf(sysEmploymentBean));
				break;
			}
		}
		// 在根据deptId查询出deptName
		// hql语句中写的是实体类名称而不是表明
		String hql = "from DepartmentBean where 1=1 and DeptId=" + deptId;
		Query query2 = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<DepartmentBean> departmentInfo = query2.list();
		return departmentInfo;
	}

	/*
	 * 根据部门Id获得用户信息
	 */
	@Override
	public List<SysUserInfoBean> findSysUserInfoByDeptId(int deptId) {
		List<SysUserInfoBean> userInfoList = new ArrayList<SysUserInfoBean>();

		String sql = "SELECT UserID,UserAccount,UserName FROM SysUserInfo WHERE State=0 AND userId IN (SELECT em.UserID FROM SysEmployment em WHERE em.DeptID="
				+ deptId + ")";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		userInfoList = query.setResultTransformer(
				Transformers.aliasToBean(SysUserInfoBean.class)).list();
		return userInfoList;
	}

	/*
	 * 条件查询部门信息
	 */
	@Override
	public List<DepartmentBean> getDepartmentByConditions(String paramName,
			String paramValue) {
		String hql = "from DepartmentBean where 1=1";
		if (null != paramName && !"".equals(paramName.trim())) {
			hql += " and " + paramName + "='" + paramValue + "'";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<DepartmentBean> departmentBeanList = query.list();
		logger.info("部门信息：" + departmentBeanList);
		return departmentBeanList;
	}

	/*
	 * 根据上级部门获得部门信息
	 */
	@Override
	public List<DepartmentBean> getDepartmentByParendID(
			DepartmentBean departmentBean) {
		String hql = "from DepartmentBean where 1=1 and parentDeptID='"
				+ departmentBean.getDeptId() + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<DepartmentBean> departmentBeanList = query.list();
		return departmentBeanList;
	}

	/*
	 * 根据部门Id获得部门员工信息
	 */
	@Override
	public List<SysEmploymentBean> getSysEmploymentByDeptID(
			DepartmentBean departmentBean) {
		String hql = "from SysEmploymentBean where 1=1 and deptID='"
				+ departmentBean.getDeptId() + "'";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysEmploymentBean> sysEmploymentBeanList = query.list();
		return sysEmploymentBeanList;
	}

	/*
	 * 获得部门树信息
	 */
	@Override
	public List<DepartmentBean> getDepartmentTreeVal() {
		String hql = "from DepartmentBean where 1=1 ";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<DepartmentBean> departmentList = query.list();
		return departmentList;
	}
	
	/*
	 * 根据条件获得部门树信息
	 */
	@Override
	public List<DepartmentBean> getDepartmentTreeSelf(String deptId) {
		String sql = "SELECT dept.DeptID deptId,dept.DeptName,dept.DeptCode,dept.OrganizationID,dept.ParentDeptID,dept.HeadOfDept,dept.Descr";
		sql += " FROM SysDepartment dept WHERE dept.OrganizationID = (select d1.OrganizationID from SysDepartment d1 where d1.DeptID = "+ deptId +")";
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		SQLQuery  sqlQuery = sess.createSQLQuery(sql);
		List<DepartmentBean> departmentList = sqlQuery.setResultTransformer(Transformers.aliasToBean(DepartmentBean.class)).list();
		return departmentList;
	}

	/*
	 * 根据部门Id获得本身与所有下属部门
	 */
	@Override
	public List<Integer> getAllDeptID(int departmentID) {
		logger.info("获得本身与所有下属部门......start");
		logger.info("部门Id：" + departmentID);
		List<Integer> allDeptIDList = new ArrayList<Integer>();
		List<Integer> childIDList = new ArrayList<Integer>();
		childIDList.add(departmentID);
		while (true) {
			List<Integer> moreChildIDList = getChildIdByDeptIDList(childIDList);
			for (int i = 0; i < childIDList.size(); i++) {
				childIDList.remove(i);
			}
			if (moreChildIDList.size() != 0) {
				for (int i = 0; i < moreChildIDList.size(); i++) {
					childIDList.add(moreChildIDList.get(i));
					allDeptIDList.add(moreChildIDList.get(i));
				}
			} else {
				break;
			}
		}
		return allDeptIDList;
	}

	/*
	 * 根据部门Id获得子部门的Id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getChildIdByDeptIDList(List<Integer> departmentIDList) {
		logger.info("获得子部门id......start");
		String deptIds = "";
		for (int i = 0; i < departmentIDList.size(); i++) {
			deptIds += departmentIDList.get(i) + ",";
		}
		deptIds = deptIds.substring(0, deptIds.lastIndexOf(","));
		String sql = "select DeptID from SysDepartment where ParentDeptID in ("
				+ deptIds + ")";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Integer> childIDs = query.list();
		List<Integer> childIDList = new ArrayList<Integer>();
		for (int i = 0; i < childIDs.size(); i++) {
			int id = Integer.valueOf(String.valueOf(childIDs.get(i)));
			childIDList.add(id);
		}
		return childIDList;
	}

	/*
	 * 根据部门名称获得部门Id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getOrganizationIDByOrgName(String organizatioName) {
		List<Integer> orgIDList = new ArrayList<Integer>();
		if (null != organizatioName && !"".equals(organizatioName)) {
			String sql = "select OrganizationID from SysOrganization where OrganizationName like '%"
					+ organizatioName + "%'";
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			orgIDList = query.list();
		}
		return orgIDList;
	}

	/*
	 * 根据部门获得所属部门
	 */
	@Override
	public int getOrganizationIDByDeptID(int departmentID) {
		String sql = "select OrganizationID from SysDepartment where DeptID="
				+ departmentID;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Integer> orgIDList = query.list();
		int organizationID = -1;
		if (orgIDList.size() > 0) {
			organizationID = Integer.valueOf(String.valueOf(orgIDList.get(0)));
		}
		return organizationID;
	}

	/*
	 * 根据所属部门及上级部门获得部门
	 */
	@Override
	public int getDeptBYOrgIDAndParentDeptID(DepartmentBean departmentBean) {
		String sql = "select count(1) from SysDepartment where OrganizationID="
				+ departmentBean.getOrganizationID() + " and DeptID="
				+ departmentBean.getParentDeptID();
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	/*
	 * 验证部门名称的唯一性
	 */
	@Override
	public boolean checkDeptName(DepartmentBean departmentBean) {
		logger.info("验证的操作为：" + departmentBean.getDoName());
		String hql = null;
		if ("add".equals(departmentBean.getDoName())) {
			if (departmentBean.getParentDeptID() != 0) {
				hql = "from DepartmentBean where organizationID="
						+ departmentBean.getOrganizationID()
						+ " and parentDeptID="
						+ departmentBean.getParentDeptID();
			} else {
				hql = "from DepartmentBean where organizationID="
						+ departmentBean.getOrganizationID()
						+ " and parentDeptID=0";
			}
		} else if ("update".equals(departmentBean.getDoName())) {
			if (departmentBean.getParentDeptID() != 0) {
				hql = "from DepartmentBean where organizationID="
						+ departmentBean.getOrganizationID()
						+ " and parentDeptID="
						+ departmentBean.getParentDeptID() + " and deptId <> "
						+ departmentBean.getDeptId();
			} else {
				hql = "from DepartmentBean where organizationID="
						+ departmentBean.getOrganizationID()
						+ " and deptId <> " + departmentBean.getDeptId();
			}

		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		String checkDeptName = departmentBean.getDeptName();
		logger.info("所验证的名称：" + checkDeptName);
		List<DepartmentBean> deptList = query.list();
		for (int i = 0; i < deptList.size(); i++) {
			logger.info("查询出的名称为：" + deptList.get(i).getDeptName());
			if (deptList.get(i).getDeptName().equals(checkDeptName)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * 获得部门的所有部门
	 */
	@Override
	public List<Integer> getDeptIdsByOrdId(int orgId) {
		String sql = "select DeptID from SysDepartment where OrganizationID="
				+ orgId;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Integer> deptIdLst = query.list();
		return deptIdLst;
	}

	/*
	 * 根据部门领导Id获得部门领导的名称
	 */
	@Override
	public String getHeadNameByHeadID(int headID) {
		String sql = "select UserName from SysUserInfo where UserID in (select UserID from SysEmployment where EmpID ="
				+ headID + ")";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		String headName = (String) query.uniqueResult();
		return headName;
	}

	/*
	 * 根据部门获得部门员工信息
	 */
	@Override
	public List<SysEmploymentBean> getEmploymentListByOrgID(
			DepartmentBean departmentBean) {
		String sql = null;
		String userIds = getUserIDs();
		if (!"".equals(userIds)) {
			if (departmentBean.getOrganizationID() == 0) {
				sql = "from SysEmploymentBean where 1=1 and userId not in("
						+ getUserIDs() + ")";
			} else {
				sql = "from SysEmploymentBean where organizationID="
						+ departmentBean.getOrganizationID()
						+ " and userId not in(" + getUserIDs() + ")";
			}
		} else {
			if (departmentBean.getOrganizationID() == 0) {
				sql = "from SysEmploymentBean where 1=1";
			} else {
				sql = "from SysEmploymentBean where organizationID="
						+ departmentBean.getOrganizationID();
			}
		}

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(sql);
		List<SysEmploymentBean> empList = query.list();
		return empList;
	}

	/*
	 * 根据部门名称获得部门
	 */
	@Override
	public DepartmentBean getDepartmentByDeptName(String deptName) {
		List<DepartmentBean> deptList = new ArrayList<DepartmentBean>();
		if (null != deptName && !"".equals(deptName)) {
			String hql = " from DepartmentBean where deptName like '%"
					+ deptName + "%'";
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql);
			deptList = query.list();
		}
		DepartmentBean departmentBean = new DepartmentBean();
		String nodeID = "-1";
		for (int i = 0; i < deptList.size(); i++) {
			nodeID = nodeID + "," + deptList.get(i).getDeptId();
		}
		departmentBean.setNodeID(nodeID);
		return departmentBean;
	}

	@Override
	public List<DepartmentBean> getDepartmentTreeList() {
		String sql = "select distinct OrganizationID from SysEmploymentGrade";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Integer> orgIds = query.list();
		StringBuffer sbuff = new StringBuffer();
		for (int i = 0; i < orgIds.size(); i++) {
			sbuff.append(orgIds.get(i)).append(",");
		}
		String orgIdStr = sbuff.substring(0, sbuff.lastIndexOf(",")).toString();
		String hql = "FROM DepartmentBean where OrganizationID in (" + orgIdStr
				+ ")";
		Query query2 = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<DepartmentBean> departmentList = query2.list();
		return departmentList;
	}

	@Override
	public String getUserIDs() {
		String sql = "select UserID from SysUserInfo where State = 1";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Integer> userIDLst = query.list();
		String userids = "";
		for (int i = 0; i < userIDLst.size(); i++) {
			userids += userIDLst.get(i) + ",";
		}
		if (userids.length() > 0) {
			userids = userids.substring(0, userids.length() - 1);
		}
		return userids;
	}

	@Override
	public List<DepartmentBean> querySysDept(DepartmentBean departmentBean) {
		String hql = "from DepartmentBean where 1=1";
		hql = commonConditions(hql, departmentBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<DepartmentBean> departmentBeanLst = query.list();
		for (int i = 0; i < departmentBeanLst.size(); i++) {
			String headName = getHeadNameByHeadID(departmentBeanLst.get(i)
					.getHeadOfDept());
			departmentBeanLst.get(i).setHeadName(headName);
		}
		return departmentBeanLst;
	}

	@Override
	public boolean checkDeptCode(String flag, DepartmentBean departmentBean) {
		String hql = null;
		if ("add".equals(flag)) {
			hql = "from DepartmentBean where deptCode='"
					+ departmentBean.getDeptCode() + "'";
		} else if ("update".equals(flag)) {
			hql = "from DepartmentBean where deptCode='"
					+ departmentBean.getDeptCode() + "' and deptId <> "
					+ departmentBean.getDeptId();
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<DepartmentBean> deptList = query.list();
		if (deptList.size() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<DepartmentBean> getDepartmentByOrgIDs(String organizationIds) {
		String hql = "FROM DepartmentBean where OrganizationID in ("
				+ organizationIds + ")";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<DepartmentBean> departmentList = query.list();
		return departmentList;
	}

	@Override
	public List<DepartmentBean> getDepartmentByOrgId(Integer organizationId) {
		String sql = "FROM DepartmentBean where 1=1 ";
		if(null != organizationId && !"".equals(organizationId) && -1 !=organizationId){
			sql = sql + "and OrganizationID=" + organizationId;
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(sql);
		List<DepartmentBean> departmentList = query.list();
		return departmentList;
	}

	@Override
	public List<Integer> getDepartmentByUserId(String userId) {
		String sql = "SELECT sd.DeptID FROM SysDepartment sd "
				+ "LEFT JOIN SysOrganization so ON sd.OrganizationID=so.OrganizationID "
				+ "LEFT JOIN SysEmployment se ON se.OrganizationID= so.OrganizationID WHERE se.UserID="+userId;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Integer> deptIDLst = query.list();
		return deptIDLst;
	}

	@Override
	public List<DepartmentBean> getByOrgAndDeptName(
			DepartmentBean departmentBean) {
		String hql = "from DepartmentBean where 1=1";
		hql = commonConditions(hql, departmentBean);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<DepartmentBean> departmentBeanLst = query.list();
		return departmentBeanLst;
	}

	@Override
	public DepartmentBean getDepartmentByDeptCode(String deptCode) {
		String sql = "FROM DepartmentBean where 1=1 ";
		if(null != deptCode && !"".equals(deptCode)){
			sql = sql + "and deptCode ='" + deptCode+"'";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(sql);
		List<DepartmentBean> departmentList = query.list();
		if(departmentList.size() == 0){
			return null;
		}else{
			return departmentList.get(0);
		}
	}

	@Override
	public Integer getOrgByUserId(Integer userId) {
//		select OrganizationID from SysEmployment where UserID=#{userId,jdbcType=INTEGER}
		String sql = "select OrganizationID from SysEmployment where UserID=" + userId;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Integer> orgIDList = query.list();
		int organizationID = -1;
		if (orgIDList.size() > 0) {
			organizationID = Integer.valueOf(String.valueOf(orgIDList.get(0)));
		}
		return organizationID;
	}
	
	
	@Override
	public List<Integer> getExistIds(boolean isOrg) {
		String sql = "SELECT DISTINCT OrganizationID FROM SysDepartment";
		if (!isOrg) {
			sql = "SELECT DISTINCT ParentDeptID FROM SysDepartment";
		}
		return this.getHibernateTemplate().getSessionFactory()
 				.getCurrentSession().createSQLQuery(sql).list();
	}
	
	/*
	 * 组织下的第一层部门信息
	 */
	@Override
	public List<DepartmentBean> getDepartments(String paramName,
			String paramValue) {
		String hql = "from DepartmentBean where parentDeptID=0";
		if (null != paramName && !"".equals(paramName.trim())) {
			hql += " and " + paramName + "='" + paramValue + "'";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<DepartmentBean> departmentBeanList = query.list();
		logger.info("部门信息：" + departmentBeanList);
		return departmentBeanList;
	}
}
