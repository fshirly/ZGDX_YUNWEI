package com.fable.insightview.platform.itsm.core.dao;

/**
 * 通用数据访问接口<br>
 * 封装对数据库基本的CRUD操作，可以在service层直接使用。
 * 
 * @author 汪朝  2013-9-30
 */
public interface UniversalDao {

    /**
     * 插入数据实体
     * @param entity 数据实体
     */
    void insert(Object entity);

    /**
     * 根据ID删除数据实体
     * @param entityClass 数据实体类型
     * @param id 主键
     */
    @SuppressWarnings("rawtypes")
    void deleteById(Class entityClass, Long id);

    /**
     * 删除数据实体
     * @param entity 数据实体
     */
    void delete(Object entity);

    /**
     * 更新数据实体
     * @param entity 数据实体
     */
    void update(Object entity);

    /**
     * 根据ID获取数据实体
     * @param entityClass 数据实体类型
     * @param id 主键
     * @return 数据实体
     */
    @SuppressWarnings("rawtypes")
    Object getById(Class entityClass, Long id);
    
    /**
     * 根据ID获取数据实体
     * @param entityClass 数据实体类型
     * @param id 主键
     * @return 数据实体
     */
    @SuppressWarnings("rawtypes")
    Object getById(Class entityClass, int id);

}
