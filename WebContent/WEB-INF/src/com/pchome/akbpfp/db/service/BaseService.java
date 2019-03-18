package com.pchome.akbpfp.db.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pchome.akbpfp.db.dao.IBaseDAO;

import java.io.Serializable;
import java.util.List;

public abstract class BaseService<T, PK extends Serializable> implements IBaseService<T, PK> {
    protected Log log = LogFactory.getLog(this.getClass());

    protected IBaseDAO<T, PK> dao;

    public T get(Serializable id) {
    	log.info(id);
        return dao.get(id);
    }

    public long loadAllSize() {
        return dao.loadAllSize();
    }

    public List<T> loadAll() {
        return dao.loadAll();
    }

    public List<T> loadAll(int firstResult, int maxResults) {
        return dao.loadAll(firstResult, maxResults);
    }

    public PK save(T entity) {
        return dao.save(entity);
    }

    public void update(T entity) {
        dao.update(entity);
    }

    public void saveOrUpdate(T entity) {
        dao.saveOrUpdate(entity);
    }

    public void delete(T entity) {
        dao.delete(entity);
    }

    public IBaseDAO<T, PK> getDao() {
        return dao;
    }
    public void setDao(IBaseDAO<T, PK> dao) {
        this.dao = dao;
    }
}