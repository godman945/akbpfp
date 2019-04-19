package com.pchome.akbpfp.db.service;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public abstract class BaseService<T, PK extends Serializable> implements IBaseService<T, PK> {
	protected Logger log = LogManager.getRootLogger();

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