package com.fable.insightview.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.ISysMailServerDao;
import com.fable.insightview.platform.entity.SysMailServerConfigBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

/**
 * 邮件配置Dao
 * 
 * @author 王淑平
 * 
 */
@Repository("mailServerDao")
public class SysMailServerDaoImpl extends GenericDaoHibernate<SysMailServerConfigBean> implements
ISysMailServerDao {
	/**
	 * 邮件服务器设置新增
	 */

	@Override
	public boolean addSysMailConfig(SysMailServerConfigBean mailBean) {
		try {
			super.insert(mailBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
/**
 * 修改邮件服务器设置
 */
	@Override
	public boolean updateSysMailConfig(SysMailServerConfigBean mailBean) {
		try {
			super.update(mailBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
@Override
public List<SysMailServerConfigBean> getMailServerConfigInfo() {
	String hql = "from SysMailServerConfigBean as org where 1=1";
	Query query = this.getHibernateTemplate().getSessionFactory()
			.getCurrentSession().createQuery(hql);
	List<SysMailServerConfigBean> mailBean = query.list();
	return mailBean;
}

}
