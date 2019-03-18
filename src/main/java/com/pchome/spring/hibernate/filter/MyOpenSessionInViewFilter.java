package com.pchome.spring.hibernate.filter;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.support.OpenSessionInViewInterceptor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MyOpenSessionInViewFilter extends org.springframework.orm.hibernate4.support.OpenSessionInViewFilter {
    /**
     * we do a different flushmode than in the codebase here
     */
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public MyOpenSessionInViewFilter() {
		
		
		
		
		
		
		
		
		
		
		
//		System.out.println(super.getSessionFactoryBeanName());
//		System.out.println(sessionFactory == null);
//		
//		System.out.println(sessionFactory.getCurrentSession() == null);
		System.out.println("DDDFG");
		
		
		
	}
	
	
    protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
    	
    	
    	System.out.println(this.sessionFactory == null);
    	
    	
    	
    	return null;
//    	sessionFactory.getSessionFactoryOptions().
//    	Session session = sessionFactory.getCurrentSession();
//    	session.setFlushMode(FlushMode.AUTO);
//    	return session;
//    	System.out.println(sessionFactory == null);
//    	System.out.println(sessionFactory.getCurrentSession() == null);
////    	SessionFactoryUtils.getJtaTransactionManager(sessionFactory, session)
//    	
//    	
//    	
////    	System.out.println(sessionFactory == null);
////    	
////    	System.out.println(sessionFactory.getCurrentSession() == null);
////    	
////    	
////    	
//    	Session session = sessionFactory.getCurrentSession();
////    	session.setFlushMode(FlushMode.AUTO);
//    	return session;
    	
    	
    	
    	
//    	Session session = SessionFactoryUtils.getSession(sessionFactory, true);
//        session.setFlushMode(FlushMode.AUTO);
//        return session;
    	
    	
//    	Session session = SessionFactoryUtils.getSession(sessionFactory, true);
//        session.setFlushMode(FlushMode.AUTO);
//        return session;
    }
    
    /**
     * we do an explicit flush here just in case
     * we do not have an automated flush
     */
    protected void closeSession(Session session, SessionFactory factory) {
//        session.flush();
//        factory.getCurrentSession().close();
//        super.closeSession(session, factory);
    }


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
    
    
    
}