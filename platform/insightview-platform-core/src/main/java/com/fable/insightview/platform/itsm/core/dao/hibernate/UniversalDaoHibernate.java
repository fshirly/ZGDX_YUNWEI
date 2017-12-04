package com.fable.insightview.platform.itsm.core.dao.hibernate;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.itsm.core.dao.UniversalDao;
import com.fable.insightview.platform.itsm.core.entity.AuditEntity;
import com.fable.insightview.platform.itsm.core.page.Page;
import com.fable.insightview.platform.itsm.core.page.PageList;


/**
 * 通用数据访问Hibernate实现
 * 
 * <p>
 * 相比Spring提供的HibernateDaoSupport，该类提供了对Hibernate最为常用的操作；并能够根据Dao注解，自动选择数据源。
 * 
 * @author 汪朝  2013-9-30
 */
@Repository("universalDao")
public class UniversalDaoHibernate extends HibernateDaoSupport implements UniversalDao {

    /** logger */
    @SuppressWarnings("unused")

	/**
	 * 此处不能使用setSessionFactory注入，因为setSessionFactory在HibernateDaoSupport
	 * 中已经定义了而且还是final的，所以不能被覆盖
	 * @param sessionFactory
	 */
	@Resource(name="sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

    /**
     * {@inheritDoc}
     */
    public void insert(Object entity) {
        if (entity instanceof AuditEntity) {
            ((AuditEntity) entity).setCreatedTime(new Date());
        }

        this.getHibernateTemplate().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    public void deleteById(Class entityClass, Long id) {
        this.delete(this.getById(entityClass, id));
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Object entity) {
        this.getHibernateTemplate().delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    public void update(Object entity) {
        if (entity instanceof AuditEntity) {
            ((AuditEntity) entity).setLastUpdatedTime(new Date());
        }

        this.getHibernateTemplate().update(entity);
    }

    /**
     * {@inheritDoc}
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object getById(Class entityClass, Long id) {
        return this.getHibernateTemplate().get(entityClass, id);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object getById(Class entityClass, int id) {
        return this.getHibernateTemplate().get(entityClass, id);
    }

    /**
     * 根据ID获取数据实体
     * @param entityClass 数据实体类型
     * @param lockMode 锁定方式
     * @param id 主键
     * @return 数据实体
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object getById(Class entityClass, LockMode lockMode, Long id) {
        return this.getHibernateTemplate().get(entityClass, id, lockMode);
    }

    /**
     * 强制加载对象
     * @param obj 被加载对象
     */
    public void initialize(Object obj) {
        this.getHibernateTemplate().initialize(obj);
    }

    /**
     * 刷新
     */
    public void flush() {
        this.getHibernateTemplate().flush();
    }

    /**
     * 通过Criteria方式查询数据实体
     * @param entityClass 数据实体类型
     * @param criterions 条件
     * @return 数据实体
     */
    @SuppressWarnings("rawtypes")
    public Object queryByCriteria(Class entityClass, Criterion... criterions) {
        return this.queryByCriteria(entityClass, null, criterions);
    }

    /**
     * 通过Criteria方式查询数据实体
     * @param entityClass 数据实体类型
     * @param lockMode 锁定方式
     * @param criterions 条件
     * @return 数据实体
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object queryByCriteria(final Class entityClass, final LockMode lockMode,
            final Criterion... criterions) {
        return this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createCriteria(session, entityClass, lockMode, criterions).uniqueResult();
            }
        });
    }

    /**
     * 通过Criteria方式查询数据实体列表
     * @param entityClass 数据实体类型
     * @param criterions 条件
     * @return 数据实体列表
     */
    @SuppressWarnings("rawtypes")
    public List queryListByCriteria(Class entityClass, Criterion... criterions) {
        return this.queryListByCriteria(entityClass, null, criterions);
    }

    /**
     * 通过Criteria方式查询数据实体列表
     * @param entityClass 数据实体类型
     * @param lockMode 锁定方式
     * @param criterions 条件
     * @return 数据实体列表
     */
    @SuppressWarnings("rawtypes")
    public List queryListByCriteria(final Class entityClass, final LockMode lockMode,
            final Criterion... criterions) {
        return this.getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createCriteria(session, entityClass, lockMode, criterions).list();
            }
        });
    }

    /**
     * 创建Criteria查询
     * @param session 会话
     * @param entityClass 数据实体类型
     * @param lockMode 锁定方式
     * @param criterions 条件
     * @return Criteria对象
     */
    @SuppressWarnings("rawtypes")
    public Criteria createCriteria(Session session, Class entityClass, LockMode lockMode,
            Criterion... criterions) {
        Criteria criteria = session.createCriteria(entityClass);

        if (lockMode != null) {
            criteria.setLockMode(lockMode);
        }

        if (criteria != null) {
            for (Criterion criterion : criterions) {
                criteria.add(criterion);
            }
        }

        return criteria;
    }

    /**
     * 通过HQL方式查询对象
     * @param hql HQL
     * @param conditions 条件
     * @return 查询结果
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object queryByHql(final String hql, final Object... conditions) {
        return this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createQuery(session, hql, conditions).uniqueResult();
            }
        });
    }

    /**
     * 通过HQL方式查询对象
     * @param hql HQL
     * @param conditions 条件
     * @return 查询结果
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object queryByHql(final String hql, final Map<String, ?> conditions) {
        return this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createQuery(session, hql, conditions).uniqueResult();
            }
        });
    }

    /**
     * 通过HQL方式查询列表
     * @param hql HQL
     * @param conditions 条件
     * @return 查询结果
     */
    @SuppressWarnings("rawtypes")
    public List queryListByHql(final String hql, final Object... conditions) {
        return this.getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createQuery(session, hql, conditions).list();
            }
        });
    }

    /**
     * 通过HQL方式查询列表
     * @param hql HQL
     * @param conditions 条件
     * @return 查询结果
     */
    @SuppressWarnings("rawtypes")
    public List queryListByHql(final String hql, final Map<String, ?> conditions) {
        return this.getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createQuery(session, hql, conditions).list();
            }
        });
    }

    /**
     * 通过HQL方式查询分页列表
     * @param page 分页
     * @param countHql 总数HQL
     * @param queryHql 查询HQL
     * @param conditions 条件
     * @return 分页列表
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageList queryPageListByHql(final Page page, final String countHql,
            final String queryHql, final Object... conditions) {
        long count = (Long) this.queryByHql(countHql, conditions);
        page.setCount(count);
        List list = null;

        if (count > 0) {
            list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {
                    Query query = createQuery(session, queryHql, conditions);
                    query.setFirstResult(page.getIndex());
                    query.setMaxResults(page.getPageSize());
                    return query.list();
                }
            });
        }

        return new PageList(page, list);
    }

    /**
     * 通过HQL方式查询分页列表
     * @param page 分页
     * @param countHql 总数HQL
     * @param queryHql 查询HQL
     * @param conditions 条件
     * @return 分页列表
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageList queryPageListByHql(final Page page, final String countHql,
            final String queryHql, final Map<String, ?> conditions) {
        long count = (Long) this.queryByHql(countHql, conditions);
        page.setCount(count);
        List list = null;

        if (count > 0) {
            list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {
                    Query query = createQuery(session, queryHql, conditions);
                    query.setFirstResult(page.getIndex());
                    query.setMaxResults(page.getPageSize());
                    return query.list();
                }
            });
        }

        return new PageList(page, list);
    }

    /**
     * 通过HQL批量修改或删除
     * @param hql HQL
     * @param conditions 条件
     * @return 修改或删除数量
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int batchModifyByHql(final String hql, final Object... conditions) {
        return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createQuery(session, hql, conditions).executeUpdate();
            }
        });
    }

    /**
     * 通过HQL批量修改或删除
     * @param hql HQL
     * @param conditions 条件
     * @return 修改或删除数量
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int batchModifyByHql(final String hql, final Map<String, ?> conditions) {
        return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createQuery(session, hql, conditions).executeUpdate();
            }
        });
    }

    /**
     * 创建HQL查询
     * @param session 会话
     * @param hql HQL
     * @param conditions 条件
     * @return 查询对象
     */
    public Query createQuery(Session session, String hql, Object... conditions) {
        Query query = session.createQuery(hql);

        if (conditions != null) {
            for (int i = 0; i < conditions.length; i++) {
                query.setParameter(i, conditions[i]);
            }
        }

        return query;
    }

    /**
     * 创建HQL查询
     * @param session 会话
     * @param hql HQL
     * @param conditions 条件
     * @return 查询对象
     */
    public Query createQuery(Session session, String hql, Map<String, ?> conditions) {
        Query query = session.createQuery(hql);

        if (conditions != null) {
            query.setProperties(conditions);
        }

        return query;
    }

    /**
     * 通过SQL方式查询对象
     * @param sql SQL
     * @param conditions 条件
     * @return 数据实体
     */
    public Object queryBySql(String sql, Object... conditions) {
        return this.queryBySql(null, sql, conditions);
    }

    /**
     * 通过SQL方式查询对象
     * @param entityClass 数据实体类型
     * @param sql SQL
     * @param conditions 条件
     * @return 数据实体
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object queryBySql(final Class entityClass, final String sql, final Object... conditions) {
        return this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createSqlQuery(session, entityClass, sql, conditions).uniqueResult();
            }
        });
    }

    /**
     * 通过SQL方式查询对象
     * @param sql SQL
     * @param conditions 条件
     * @return 数据实体
     */
    public Object queryBySql(String sql, Map<String, ?> conditions) {
        return this.queryBySql(null, sql, conditions);
    }

    /**
     * 通过SQL方式查询对象
     * @param entityClass 数据实体类型
     * @param sql SQL
     * @param conditions 条件
     * @return 数据实体
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object queryBySql(final Class entityClass, final String sql,
            final Map<String, ?> conditions) {
        return this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createSqlQuery(session, entityClass, sql, conditions).uniqueResult();
            }
        });
    }

    /**
     * 通过SQL方式查询列表
     * @param sql SQL
     * @param conditions 条件
     * @return 数据实体列表
     */
    @SuppressWarnings("rawtypes")
    public List queryListBySql(String sql, Object... conditions) {
        return this.queryListBySql(null, sql, conditions);
    }

    /**
     * 通过SQL方式查询列表
     * @param entityClass 数据实体类型
     * @param sql SQL
     * @param conditions 条件
     * @return 数据实体列表
     */
    @SuppressWarnings("rawtypes")
    public List queryListBySql(final Class entityClass, final String sql,
            final Object... conditions) {
        return this.getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createSqlQuery(session, entityClass, sql, conditions).list();
            }
        });
    }

    /**
     * 通过SQL方式查询列表
     * @param sql SQL
     * @param conditions 条件
     * @return 数据实体列表
     */
    @SuppressWarnings("rawtypes")
    public List queryListBySql(String sql, Map<String, ?> conditions) {
        return this.queryListBySql(null, sql, conditions);
    }

    /**
     * 通过SQL方式查询列表
     * @param entityClass 数据实体类型
     * @param sql SQL
     * @param conditions 条件
     * @return 数据实体列表
     */
    @SuppressWarnings("rawtypes")
    public List queryListBySql(final Class entityClass, final String sql,
            final Map<String, ?> conditions) {
        return this.getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createSqlQuery(session, entityClass, sql, conditions).list();
            }
        });
    }

    /**
     * 通过SQL方式查询分页列表
     * @param page 分页
     * @param entityClass 数据实体类型
     * @param countSql 总数SQL
     * @param querySql 查询SQL
     * @param conditions 条件
     * @return 分页列表
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageList queryPageListBySql(final Page page, final Class entityClass,
            final String countSql, final String querySql, final Object... conditions) {
        long count = ((BigInteger) this.queryBySql(countSql, conditions)).longValue();
        page.setCount(count);
        List list = null;

        if (count > 0) {
            list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {
                    Query query = createSqlQuery(session, entityClass, querySql, conditions);
                    query.setFirstResult(page.getIndex());
                    query.setMaxResults(page.getPageSize());
                    return query.list();
                }
            });
        }

        return new PageList(page, list);
    }

    /**
     * 通过SQL方式查询分页列表
     * @param page 分页
     * @param entityClass 数据实体类型
     * @param countSql 总数SQL
     * @param querySql 查询SQL
     * @param conditions 条件
     * @return 分页列表
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageList queryPageListBySql(final Page page, final Class entityClass,
            final String countSql, final String querySql, final Map<String, ?> conditions) {
        long count = ((BigInteger) this.queryBySql(countSql, conditions)).longValue();
        page.setCount(count);
        List list = null;

        if (count > 0) {
            list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {
                    Query query = createSqlQuery(session, entityClass, querySql, conditions);
                    query.setFirstResult(page.getIndex());
                    query.setMaxResults(page.getPageSize());
                    return query.list();
                }
            });
        }

        return new PageList(page, list);
    }

    /**
     * 通过SQL批量修改或删除
     * @param sql SQL
     * @param conditions 条件
     * @return 修改或删除数量
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int batchModifyBySql(final String sql, final Object... conditions) {
        return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createSqlQuery(session, null, sql, conditions).executeUpdate();
            }
        });
    }

    /**
     * 通过SQL批量修改或删除
     * @param sql SQL
     * @param conditions 条件
     * @return 修改或删除数量
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int batchModifyBySql(final String sql, final Map<String, ?> conditions) {
        return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return createSqlQuery(session, null, sql, conditions).executeUpdate();
            }
        });
    }

    /**
     * 创建SQL查询
     * @param session 会话
     * @param entityClass 数据实体类型
     * @param sql SQL
     * @param conditions 条件
     * @return 查询对象
     */
    @SuppressWarnings("rawtypes")
    public SQLQuery createSqlQuery(Session session, Class entityClass, String sql,
            Object... conditions) {
        SQLQuery sqlQuery = session.createSQLQuery(sql);

        if (entityClass != null) {
            sqlQuery.addEntity(entityClass);
        }

        if (conditions != null) {
            for (int i = 0; i < conditions.length; i++) {
                sqlQuery.setParameter(i, conditions[i]);
            }
        }

        return sqlQuery;
    }

    /**
     * 创建SQL查询
     * @param session 会话
     * @param entityClass 数据实体类型
     * @param sql SQL
     * @param conditions 条件
     * @return 查询对象
     */
    @SuppressWarnings("rawtypes")
    public SQLQuery createSqlQuery(Session session, Class entityClass, String sql,
            Map<String, ?> conditions) {
        SQLQuery sqlQuery = session.createSQLQuery(sql);

        if (entityClass != null) {
            sqlQuery.addEntity(entityClass);
        }

        if (conditions != null) {
            sqlQuery.setProperties(conditions);
        }

        return sqlQuery;
    }

}
