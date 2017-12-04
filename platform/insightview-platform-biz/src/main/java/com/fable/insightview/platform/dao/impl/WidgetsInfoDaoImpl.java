package com.fable.insightview.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.dao.IWidgetsInfoDao;
import com.fable.insightview.platform.entity.WidgetsInfoBean;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;

/**
 * 单位组织Dao
 * 
 * @author 武林
 * 
 */
@Repository("widgetsInfoDao")
public class WidgetsInfoDaoImpl extends GenericDaoHibernate<WidgetsInfoBean>
		implements IWidgetsInfoDao {

	@Override
	public List<WidgetsInfoBean> getWidgetsInfoLst(
			WidgetsInfoBean widgetsInfoBean, int userid) {
		if (1 == 0) {
			Session sess = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();

			List<WidgetsInfoBean> widgetsInfoLst = new ArrayList<WidgetsInfoBean>();

			String sql = "SELECT w.* FROM WidgetsInfo w, SysUserInGroups g, SysUserInfo u, WidgetsInGroupInfo i WHERE  i.WidgetsID = w.id AND i.GroupID = g.GroupID AND g.UserID = u.UserID and u.UserID = "
					+ userid + " order by w.ID";

			SQLQuery sqlQuery = sess.createSQLQuery(sql)
					.addScalar("id", IntegerType.INSTANCE)
					.addScalar("widgetType", IntegerType.INSTANCE)
					.addScalar("widgetWidth", IntegerType.INSTANCE)
					.addScalar("widgetHeight", IntegerType.INSTANCE)
					.addScalar("widgetName")
					.addScalar("widgetTitle")
					.addScalar("widgetImageUrl")
					.addScalar("widgetUrl");

			widgetsInfoLst = sqlQuery.setResultTransformer(
					Transformers.aliasToBean(WidgetsInfoBean.class)).list();

			return widgetsInfoLst;
		} else {
			String hql = "from WidgetsInfoBean as org where 1=1 order by id";
			// hql = commonConditions(hql, departmentBean);

			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql);

			List<WidgetsInfoBean> widgetsInfoLst = query.list();
			return widgetsInfoLst;
		}
	}
}
