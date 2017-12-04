package com.fable.insightview.platform.itsm.core.service;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.fable.insightview.platform.itsm.core.dao.UniversalDao;



/**
 * 通用服务实现
 * 
 * @author 汪朝  2013-9-30
 */
@Transactional
public class UniversalServiceImpl implements UniversalService {

    /** 通用数据访问 */
	@Resource(name="platform.universalDao")
    protected UniversalDao universalDao;

    /**
     * {@inheritDoc}
     */
    public void insert(Object entity) {
        this.universalDao.insert(entity);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    public void deleteById(Class entityClass, Long id) {
        this.universalDao.deleteById(entityClass, id);
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Object entity) {
        this.universalDao.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    public void update(Object entity) {
        this.universalDao.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    public Object getById(Class entityClass, Long id) {
        return this.universalDao.getById(entityClass, id);
    }

    /**
     * 设置通用数据访问
     * @param universalDao 通用数据访问
     */
    public void setUniversalDao(UniversalDao universalDao) {
        this.universalDao = universalDao;
    }

	@Override
	public Object getById(Class entityClass, int id) {
		return this.universalDao.getById(entityClass, id);
	}

}
