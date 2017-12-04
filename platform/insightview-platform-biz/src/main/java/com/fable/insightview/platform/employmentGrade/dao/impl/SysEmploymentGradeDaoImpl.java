package com.fable.insightview.platform.employmentGrade.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.employmentGrade.dao.ISysEmploymentGradeDao;
import com.fable.insightview.platform.employmentGrade.entity.SysEmploymentGradeBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

@Repository("sysEmploymentGradeDao")
public class SysEmploymentGradeDaoImpl extends
		GenericDaoHibernate<SysEmploymentGradeBean> implements
		ISysEmploymentGradeDao {

	/**
	 * 根据条件拼接hql
	 */
	public String commonconditions(SysEmploymentGradeBean empGrade, String hql) {
		List<Integer> organizationIDList = new ArrayList<Integer>();

		if (null != empGrade.getOrganizationName()
				&& !"".equals(empGrade.getOrganizationName())) {
			List<Integer> orgIDByorgNameList = getOrganizationIDByOrgName(empGrade
					.getOrganizationName());
			if (orgIDByorgNameList.size() > 0) {
				hql += " and organizationID  in(" + orgIDByorgNameList.get(0);
				for (int i = 1; i < orgIDByorgNameList.size(); i++) {
					hql += "," + orgIDByorgNameList.get(i);
				}
				hql += ")";
			} else {
				List<SysEmploymentGradeBean> empGradeList = getEmpGradeByConditions(
						"", "");
				List<Integer> allOrgIDLst = new ArrayList<Integer>();
				for (int i = 0; i < empGradeList.size(); i++) {
					allOrgIDLst.add(empGradeList.get(i).getOrganizationID());
				}
				hql += " and organizationID not  in(" + allOrgIDLst.get(0);
				for (int i = 1; i < allOrgIDLst.size(); i++) {
					hql += "," + allOrgIDLst.get(i);
				}
				hql += ")";
			}
		}
		if (null != empGrade.getOrganizationID()
				&& !"".equals(empGrade.getOrganizationID())
				&& empGrade.getOrganizationID() != -1) {
			organizationIDList.add(empGrade.getOrganizationID());
			List<Integer> childIDList = getChildIdByOrgIDList(organizationIDList);
			for (int id : childIDList) {
				organizationIDList.add(id);
			}
			hql += " and organizationID  in(" + organizationIDList.get(0);
			for (int i = 1; i < organizationIDList.size(); i++) {
				hql += "," + organizationIDList.get(i);
			}
			hql += ")";
		}
		if (null != empGrade.getGradeName()
				&& !"".equals(empGrade.getGradeName())) {
			hql += " and gradeName like '%" + empGrade.getGradeName() + "%'";
		}
		if (null != empGrade.getNodeID() && !"".equals(empGrade.getNodeID())
				&& !"-1".equals(empGrade.getNodeID())) {
			hql += " and organizationID  in(" + empGrade.getNodeID() + ")";
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysEmploymentGradeBean> getEmploymentGradeList(
			SysEmploymentGradeBean employmentGrade,
			FlexiGridPageInfo flexiGridPageInfo) {
		List<SysEmploymentGradeBean> empGradeList = new ArrayList<SysEmploymentGradeBean>();
		String hql = "from SysEmploymentGradeBean where 1=1 ";
		hql = commonconditions(employmentGrade, hql);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query
				.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
						.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		empGradeList = query.list();
		for (int i = 0; i < empGradeList.size(); i++) {
			String organizationName = getOrganizationNameByID(empGradeList.get(
					i).getOrganizationID());
			empGradeList.get(i).setOrganizationName(organizationName);
		}
		return empGradeList;
	}

	/**
	 * 判断是否为叶子节点
	 */
	@Override
	public boolean isLeaf(int organizationID) {
		String sql = "select count(*) from SysOrganization where ParentOrgID = "
				+ organizationID;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		int count = ((Number) query.uniqueResult()).intValue();
		if (count == 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public int getEmpGradeListCount(SysEmploymentGradeBean employmentGrade) {
		String hql = "select count(1) as count from SysEmploymentGrade where 1=1 ";
		hql = commonconditions(employmentGrade, hql);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();
		return count;

	}

	@Override
	public String getOrganizationNameByID(int organizationID) {
		String sql = "select OrganizationName from SysOrganization where OrganizationID = "
				+ organizationID;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		// List<SysEmploymentGradeBean> empGrade = new
		// ArrayList<SysEmploymentGradeBean>();
		String organizationName = (String) query.uniqueResult();
		// String organizationName = empGrade.get(0).getOrganizationName();
		return organizationName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkEmpGradeByOrgID(SysEmploymentGradeBean employmentGrade) {
		String hql = null;
		if ("add".equals(employmentGrade.getDoName())) {
			hql = "from SysEmploymentGradeBean where organizationID='"
					+ employmentGrade.getOrganizationID() + "'";
		} else if ("update".equals(employmentGrade.getDoName())) {
			hql = "from SysEmploymentGradeBean where organizationID='"
					+ employmentGrade.getOrganizationID()
					+ "' and gradeID <> '" + employmentGrade.getGradeID() + "'";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		// System.out.println("sql语句========="+hql);
		String checkGradeName = employmentGrade.getGradeName();
		// System.out.println("要验证的名称======"+checkGradeName);
		List<SysEmploymentGradeBean> empGradeList = query.list();
		for (int i = 0; i < empGradeList.size(); i++) {
			// System.out.println("查询所得的名称========="+empGradeList.get(i).getGradeName());
			if (empGradeList.get(i).getGradeName().equals(checkGradeName)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addEmploymentGrade(SysEmploymentGradeBean employmentGrade) {
		try {
			super.insert(employmentGrade);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateEmploymentGrade(SysEmploymentGradeBean employmentGrade) {
		try {
			super.update(employmentGrade);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysEmploymentGradeBean> getEmpGradeByConditions(
			String paramName, String paramValue) {
		String hql = "from SysEmploymentGradeBean where 1=1";
		if (paramName == "gradeID" || paramName == "OrganizationID") {
			int paramValue2 = 0;
			paramValue2 = Integer.parseInt(paramValue);
			hql += " and " + paramName + "='" + paramValue2 + "'";
		} else {
			if (null != paramName && !"".equals(paramName.trim())) {
				hql += " and " + paramName + "='" + paramValue + "'";
			}
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<SysEmploymentGradeBean> empGradeList = query.list();
		return empGradeList;
	}

	@Override
	public int getEmploymentByGradeID(int gradeID, int organizationID) {
		String sql = "select count(1) from SysEmployment where GradeID="
				+ gradeID;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	@Override
	public boolean delEmpGradeByGradeID(int gradeID) {
		String sql = "delete from SysEmploymentGrade where GradeID=" + gradeID;
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		int rs = query.executeUpdate();
		if (rs > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getLevel(int organizationID) {
		int i = 1;
		int parentTypeId = 0;

		while (true) {
			String sql = "select OrganizationID from SysOrganization where ParentOrgID ="
					+ organizationID;
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			parentTypeId = ((Number) query.uniqueResult()).intValue();
			if (parentTypeId == 0) {
				break;
			} else {
				organizationID = parentTypeId;
				i++;
			}
		}

		return i;
	}

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getChildId(int organizationID) {
		String sql = "select OrganizationID from SysOrganization where ParentOrgID in( "
				+ organizationID + ")";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Integer> childIDList = new ArrayList<Integer>();
		childIDList = query.list();
		return childIDList;

	}

	@Override
	public List<Integer> getAllOrgId(int organizationID) {
		List<Integer> allOrgIDList = new ArrayList<Integer>();
		List<Integer> childIdList = new ArrayList<Integer>();
		childIdList.add(organizationID);
		while (true) {
			List<Integer> moreChildIDList = getChildIdByOrgIDList(childIdList);
			for (int i = 0; i < childIdList.size(); i++) {
				childIdList.remove(i);
			}
			if (moreChildIDList.size() != 0) {
				for (int i = 0; i < moreChildIDList.size(); i++) {
					childIdList.add(moreChildIDList.get(i));
					allOrgIDList.add(moreChildIDList.get(i));
				}
			} else {
				break;
			}
		}
		return allOrgIDList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getChildIdByOrgIDList(List<Integer> organizationIDList) {
		String ordIds = "";
		for (int i = 0; i < organizationIDList.size(); i++) {
			ordIds += organizationIDList.get(i) + ",";
		}
		ordIds = ordIds.substring(0, ordIds.lastIndexOf(","));
		String sql = "select OrganizationID from SysOrganization where ParentOrgID in("
				+ ordIds + ")";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Integer> childIDs = new ArrayList<Integer>();
		List<Integer> childIDList = new ArrayList<Integer>();
		childIDs = query.list();
		for (int i = 0; i < childIDs.size(); i++) {
			int id = Integer.valueOf(String.valueOf(childIDs.get(i)));
			childIDList.add(id);
		}
		return childIDList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysEmploymentGradeBean getEmpploymentByOrgName(
			String organizationName) {
		List<Integer> orgIDByorgNameList = getOrganizationIDByOrgName(organizationName);
		SysEmploymentGradeBean sysEmploymentGrade = new SysEmploymentGradeBean();
		String nodeID = "-1";
		for (int i = 0; i < orgIDByorgNameList.size(); i++) {
			nodeID = nodeID + "," + orgIDByorgNameList.get(i);
		}
		sysEmploymentGrade.setNodeID(nodeID);
		return sysEmploymentGrade;
	}

}
