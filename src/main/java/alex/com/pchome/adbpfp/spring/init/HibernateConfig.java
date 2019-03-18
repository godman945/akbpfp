//package alex.com.pchome.adbpfp.spring.init;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.hibernate4.HibernateTransactionManager;
//import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//public class HibernateConfig {
// 
//    @Autowired
//    private ApplicationContext context;
// 
////    @Bean
////    public LocalSessionFactoryBean getSessionFactory() {
////        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
////        factoryBean.setConfigLocation(context.getResource("config/hibernate/hibernate.cfg.xml"));
////        return factoryBean;
////    }
// 
////    @Bean
////    public HibernateTransactionManager getTransactionManager() {
////        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
////        transactionManager.setSessionFactory(getSessionFactory().getObject());
////        return transactionManager;
////    }
//}