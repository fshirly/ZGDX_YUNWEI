package com.fable.insightview.platform.itsm.core.dao;

import com.fable.insightview.platform.itsm.core.entity.Entity;


/**
 * 泛型数据访问接口
 * 
 * @author 汪朝 2013-9-30
 */
public interface GenericDao<T extends Entity> {

    /**
     * 插入数据实体
     * @param entity 数据实体
     */
    void insert(T entity);

    /**
     * 根据ID删除数据实体
     * @param id 主键
     */
    void deleteById(Long id);

    /**
     * 根据ID删除数据实体
     * @param id 主键
     */
    void deleteById(int id);
    
    /**
     * 删除数据实体
     * @param entity 数据实体
     */
    void delete(T entity);

    /**
     * 更新数据实体
     * @param entity 数据实体
     */
    void update(T entity);

    /**
     * 根据ID获取数据实体
     * @param id 主键
     * @return 数据实体
     */
    T getById(Long id);
    
    /**
     * 根据ID获取数据实体
     * @param id 主键
     * @return 数据实体
     */
    T getById(int id);

}
