package com.pchome.akbpfp.db.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public abstract class BaseDAO<T, PK extends Serializable> extends HibernateDaoSupport implements IBaseDAO<T, PK> {
    protected Logger log = LogManager.getRootLogger();

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    protected Class<T> getMyClass() {
        if (clazz == null) {
            clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return clazz;
    }

    public T get(Serializable id) {
        return getHibernateTemplate().get(getMyClass(), id);
    }

    public long loadAllSize() {
        return getHibernateTemplate().loadAll(getMyClass()).size();
    }

    public List<T> loadAll() {
        return getHibernateTemplate().loadAll(getMyClass());
    }

    @SuppressWarnings("unchecked")
    public List<T> loadAll(int firstResult, int maxResults) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(getMyClass()).setFirstResult(firstResult).setMaxResults(maxResults).list();
    }

    @SuppressWarnings("unchecked")
    public PK save(T entity) {
        return (PK) getHibernateTemplate().save(entity);
    }

    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    public void saveOrUpdate(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    protected int nextVal(final String sql) {
        BigInteger nextval = getHibernateTemplate().execute(
            new HibernateCallback<BigInteger>() {
                public BigInteger doInHibernate(Session session) throws HibernateException {
                    return (BigInteger) session.createSQLQuery(sql).uniqueResult();
                }
            });
        return nextval.intValue();
    }
}
