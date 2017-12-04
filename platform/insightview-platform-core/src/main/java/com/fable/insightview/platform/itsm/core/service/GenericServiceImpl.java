package com.fable.insightview.platform.itsm.core.service;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.transaction.annotation.Transactional;

import com.fable.insightview.platform.itsm.core.entity.Entity;


/**
 * 泛型服务实现
 * 
 * @author 汪朝  2013-9-30
 */
@Transactional
public class GenericServiceImpl<T extends Entity> extends UniversalServiceImpl implements
        GenericService<T> {

    /** 数据实体类型 */
    protected Class<T> entityClass;

    /**
     * 默认构造函数
     */
    @SuppressWarnings("unchecked")
    public GenericServiceImpl() {
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
    public GenericServiceImpl(Class<T> entityClass) {
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

	@Override
	public T getById(int id) {
		return (T) super.getById(entityClass, id);
	}

}
