package com.fable.insightview.platform.itsm.core.dao.hibernate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;

import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.entity.Entity;



/**
 * 泛型数据访问Hibernate实现
 * 
 * @author 汪朝  2013-9-30
 */
public abstract class GenericDaoHibernate<T extends Entity> extends UniversalDaoHibernate implements
        GenericDao<T> {

    /** 数据实体类型 */
    protected Class<T> entityClass;

    /**
     * 默认构造函数
     */
    @SuppressWarnings("unchecked")
    public GenericDaoHibernate() {
        Type superClassType = getClass().getGenericSuperclass();

        if (superClassType instanceof ParameterizedType) {
            Type[] paramTypes = ((ParameterizedType) superClassType).getActualTypeArguments();
            this.entityClass = (Class<T>) paramTypes[0];
        }
    }

    /**
     * 构造函数
     * @param entityClass 数据实体类型
     */
    public GenericDaoHibernate(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * {@inheritDoc}
     */
    public void insert(T entity) {
        super.insert(entity);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteById(Long id) {
        this.delete(this.getById(id));
    }


    /**
     * {@inheritDoc}
     */
    public void deleteById(int id) {
        this.delete(this.getById(id));
    }
    
    /**
     * {@inheritDoc}
     */
    public void delete(T entity) {
        super.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    public void update(T entity) {
        super.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T getById(Long id) {
        return (T) super.getById(this.entityClass, id);
    }
    
    @SuppressWarnings("unchecked")
    public T getById(int id) {
        return (T) super.getById(this.entityClass, id);
    }
    

    /**
     * 根据ID获取数据实体
     * @param lockMode 锁定方式
     * @param id 主键
     * @return 数据实体
     */
    @SuppressWarnings("unchecked")
    public T getById(LockMode lockMode, Long id) {
        return (T) super.getById(this.entityClass, lockMode, id);
    }

    /**
     * 通过Criteria方式查询数据实体
     * @param criterions 条件
     * @return 数据实体
     */
    @SuppressWarnings("unchecked")
    public T queryByCriteria(Criterion... criterions) {
        return (T) super.queryByCriteria(this.entityClass, criterions);
    }

    /**
     * 通过Criteria方式查询数据实体
     * @param lockMode 锁定方式
     * @param criterions 条件
     * @return 数据实体
     */
    @SuppressWarnings("unchecked")
    public T queryByCriteria(LockMode lockMode, Criterion... criterions) {
        return (T) super.queryByCriteria(this.entityClass, lockMode, criterions);
    }

    /**
     * 通过Criteria方式查询数据实体列表
     * @param criterions 条件
     * @return 数据实体列表
     */
    @SuppressWarnings("unchecked")
    public List<T> queryListByCriteria(Criterion... criterions) {
        return super.queryListByCriteria(this.entityClass, criterions);
    }

    /**
     * 通过Criteria方式查询数据实体列表
     * @param lockMode 锁定方式
     * @param criterions 条件
     * @return 数据实体列表
     */
    @SuppressWarnings("unchecked")
    public List<T> queryListByCriteria(LockMode lockMode, Criterion... criterions) {
        return super.queryListByCriteria(this.entityClass, lockMode, criterions);
    }

}
