/**
 * 
 */
package com.fable.insightview.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.ISysEmploymentDao;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

/**
 * 
 * @author maow
 * 
 */
@Repository("sysEmploymentDao")
public class SysEmploymentDaoImpl
		extends
			GenericDaoHibernate<SysEmploymentBean> implements ISysEmploymentDao {

	@Override
	public List<Integer> getUserIdByOrgId(int orgId) {
		String sql="select UserID from SysEmployment where OrganizationID="+orgId;
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Integer> userIds=query.list();
		return userIds;
	}

	@Override
	public List<Integer> getUserIdByDeptId(String deptId) {
		List<Integer> userIds = null;
		List<Integer> deptIds=new ArrayList<Integer>();
		deptIds.add(Integer.parseInt(deptId));
		if ("0".equals(deptId)) {
			String sql="select UserID from SysEmployment ";
			Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			userIds=query.list();
		}else{
			while(true){
				String sql="select DeptID from SysDepartment where ParentDeptID in ("+deptId+")";
				Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
				List<Integer> deptLst=query.list();
				if(deptLst==null || deptLst.size()==0){
					break;
				}else{
					StringBuffer sbuff=new StringBuffer();
					for (int i = 0; i < deptLst.size(); i++) {
						deptIds.add(deptLst.get(i));
						sbuff.append(deptLst.get(i)).append(",");
					}
					String deptTemp=sbuff.toString().substring(0, sbuff.toString().lastIndexOf(","));
					if(deptId.equals(deptTemp)){
						break;
					}else{
						deptId=deptTemp;
					}
				}
			}
			StringBuffer sbuff=new StringBuffer();
			for (int i = 0; i < deptIds.size(); i++) {
				sbuff.append(deptIds.get(i)).append(",");
			}
			String sql="select UserID from SysEmployment where DeptID in ("+sbuff.toString().substring(0, sbuff.toString().lastIndexOf(","))+")";
			Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			userIds=query.list();
		}
		return userIds;
	}

	@Override
	public boolean addSysEmp(SysEmploymentBean sysEmploymentBean) {
		try {
			super.insert(sysEmploymentBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<SysEmploymentBean> getEmploymentByUserId(int userId) {
		String hql="from SysEmploymentBean where UserID="+userId;
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		List<SysEmploymentBean> empLst=query.list();
		return empLst;
	}

	@Override
	public boolean updateSysEmp(SysEmploymentBean sysEmploymentBean) {
		try {
			super.update(sysEmploymentBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	

	@Override
	public boolean updateSysEmpSyn(SysEmploymentBean sysEmploymentBean) {
		String sql = "update SysEmployment set EmployeeCode = ?,OrganizationID = ? , DeptID = ? where UserID = ?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, sysEmploymentBean.getEmployeeCode());
		query.setParameter(1, sysEmploymentBean.getOrganizationID());
		query.setParameter(2, sysEmploymentBean.getDeptID());
		query.setParameter(3, sysEmploymentBean.getUserId());
		query.executeUpdate();
		return true;
	}

	@Override
	public boolean delSysEmpByUserId(int userId) {
		String sql="delete from SysEmployment where UserID="+userId;
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		int i=query.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean delSysEmpByGradeID(int gradeID) {
		// TODO Auto-generated method stub
		String sql="delete from SysEmployment where GradeID="+gradeID;
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		int i=query.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}
