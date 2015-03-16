package com.pchome.akbpfp.db.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pchome.enumerate.report.EnumReport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T, PK extends Serializable> extends HibernateDaoSupport implements IBaseDAO<T, PK> {
    protected Log log = LogFactory.getLog(this.getClass());

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
                public BigInteger doInHibernate(Session session) throws HibernateException, SQLException {
                    return (BigInteger) session.createSQLQuery(sql).uniqueResult();
                }
            });
        return nextval.intValue();
    }
}
