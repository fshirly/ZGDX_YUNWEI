package com.fable.insightview.platform.common.dynamicdb.jdbc;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.common.dynamicdb.api.IDynamicDB;
import com.fable.insightview.platform.common.dynamicdb.jdbc.sqltype.SqlTypeUtil;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.AddWork;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.CreateWork;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.DropWork;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.RemoveWork;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.BaseWork.WorkType;
import com.fable.insightview.platform.common.util.CTD;

/**
 * 动态表结构JDBC实现方式 目前先采用HibernaOte执行原始SQL的方式实现
 * 
 * @author 郑自辉
 * 
 */
@Repository("jdbcDB")
public class JdbcDBImpl extends BaseJdbc implements IDynamicDB {

	@Override
	public void createTable(final String tableName) {
		createTable(tableName, "CiId");
	}

	@Override
	public void addColumn(String tableName, String columnName, String javaType) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Work work = new AddWork(WorkType.ADD, tableName, columnName,
				SqlTypeUtil.sqlType(javaType));
		session.doWork(work);
	}

	@Override
	public void removeColumn(String tableName, String colomnName,
			boolean isLogic) {
		if (!isLogic) {
			Session session = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
			Work work = new RemoveWork(WorkType.REMOVE, tableName, colomnName);
			session.doWork(work);
		}
	}

	@Override
	public void dropTable(String tableName) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Work work = new DropWork(WorkType.DROP, tableName);
		// 如果是oracle数据库则删除sequence
		if (CTD.isOracle() && isSeqExist(tableName, session)) {
			session.createQuery("DROP SEQUENCE " + "seq" + tableName);
		}
		session.doWork(work);
	}

	@Override
	public void createTable(String tableName, String primaryKey) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Work work = new CreateWork(WorkType.CREATE, tableName, primaryKey);
		session.doWork(work);
		if (CTD.isOracle()) {
			if (isSeqExist(tableName,session)) {
				// 创建sequence，因为每次的表名称都不同，所以不需要判断sequence是否会重复
				String sequence = "create sequence " + "seq" + tableName + " "
						+ " minvalue 1"
						+ " maxvalue 999999999999999999999999999"
						+ " start with 1" + "increment by 1" + " nocache";
				session.createSQLQuery(sequence).executeUpdate();
			}
		}
	}

	/**
	 * 判断sequence是否存在
	 * @param tableName
	 * @param session
	 * @return
	 */
	private boolean isSeqExist(String tableName, Session session) {
		// 判断sequence是否存在
		String sql = "select 1 from all_sequences where sequence_name="
				+ ("'seq" + tableName).toUpperCase() + "'";
		String countSeq = String.valueOf(session.createSQLQuery(sql)
				.uniqueResult());
		if (!"1".equals(countSeq)) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void addColumn(String tableName, String columnName, String javaType,
			int length) {
		String columnType = SqlTypeUtil.dataType(javaType);
		columnType += "(" + length + ")";
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Work work = new AddWork(WorkType.ADD, tableName, columnName, columnType);
		session.doWork(work);
	}
}
