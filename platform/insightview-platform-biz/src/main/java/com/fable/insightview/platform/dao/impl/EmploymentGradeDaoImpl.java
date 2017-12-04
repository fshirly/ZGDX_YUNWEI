package com.fable.insightview.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.IDepartmentDao;
import com.fable.insightview.platform.dao.IEmploymentGradeDao;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.EmploymentGradeBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

/**
 * 单位组织Dao
 * 
 * @author 武林
 * 
 */
@Repository("employmentGradeDao")
public class EmploymentGradeDaoImpl extends
		GenericDaoHibernate<EmploymentGradeBean> implements IEmploymentGradeDao {
	@Autowired
	IDepartmentDao departmentDao;
	
	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EmploymentGradeBean> getEmploymentGradeByConditions(
			EmploymentGradeBean employmentGradeBean) {
		String hql = "from EmploymentGradeBean as org where organizationID ="+employmentGradeBean.getOrganizationID();
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);

		List<EmploymentGradeBean> employmentGradeBeanLst = query.list();
		return employmentGradeBeanLst;
	}
	/*
	 * 新增单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addEmploymentGrade(EmploymentGradeBean employmentGradeBean) {
		try {
			super.insert(employmentGradeBean);
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
	public boolean delEmploymentGradeById(
			EmploymentGradeBean employmentGradeBean) {
		try {
			super.delete(employmentGradeBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EmploymentGradeBean> getEmploymentGradeByConditions(
			EmploymentGradeBean employmentGradeBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		// String pageLimit = super.pageLimit(flexiGridPageInfo);
		String hql = "from EmploymentGradeBean as org ";
		System.out.println(hql);

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getTotal()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		List<EmploymentGradeBean> employmentGradeBeanLst = query.list();
		return employmentGradeBeanLst;
	}

	@Override
	public List<EmploymentGradeBean> getDepartmentGradeTreeVal(int deptId) {
		List<DepartmentBean> deptBean=departmentDao.getDepartmentBeanByConditions("DeptID", deptId+"");
		String hql = "from EmploymentGradeBean where OrganizationID = "+deptBean.get(0).getOrganizationBean().getOrganizationID() ;
//				"(select OrganizationID from SysDepartment where DeptID="+deptId+")";
		System.out.println(">>>>>>>>>>hql= "+hql);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		List<EmploymentGradeBean> departmentGradeList = query.list();
		return departmentGradeList;
	}

	@Override
	public List<EmploymentGradeBean> getGradeName(int gradeId) {
		String hql = "from EmploymentGradeBean where GradeID = "+gradeId ;
		Query query = this.getHibernateTemplate().getSessionFactory()
			.getCurrentSession().createQuery(hql);
		List<EmploymentGradeBean> departmentGradeList = query.list();
		return departmentGradeList;
	}
	@Override
	public List<EmploymentGradeBean> getGradeInfo(int organizationID) {
		String hql = "from EmploymentGradeBean where OrganizationID = "+organizationID ;
		Query query = this.getHibernateTemplate().getSessionFactory()
			.getCurrentSession().createQuery(hql);
		List<EmploymentGradeBean> departmentGradeList = query.list();
		return departmentGradeList;
	}
}
