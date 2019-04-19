package com.pchome.akbpfp.db.dao;

import java.io.Serializable;
import java.util.List;

public interface IBaseDAO<T, PK extends Serializable> {
    public T get(Serializable id);

    public long loadAllSize();

    public List<T> loadAll();

    public List<T> loadAll(int firstResult, int maxResults);

    public PK save(T entity);

    public void update(T entity);

    public void saveOrUpdate(T entity);

    public void delete(T entity);
}