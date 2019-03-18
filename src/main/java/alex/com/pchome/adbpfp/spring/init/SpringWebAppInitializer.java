//package alex.com.pchome.adbpfp.spring.init;
//
//import javax.servlet.FilterRegistration;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRegistration;
// 
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//import org.springframework.web.servlet.DispatcherServlet;
// 
//public class SpringWebAppInitializer implements WebApplicationInitializer {
// 
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//    	//新建WebApplicationContext,註冊配置類，將其和當前ServletContext關聯 context為應用程式容器
//    	AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//    	rootContext.register(ApplicationContextConfig.class);
//
//    	
//    	//1.spring setup
//    	ServletRegistration.Dynamic mvcDispatcher = servletContext.addServlet("springDispatcherMvc",new DispatcherServlet(rootContext));
//    	
//    	//2.請求資源會交給dispatcher分配給servlet
//    	mvcDispatcher.setLoadOnStartup(1);
//    	mvcDispatcher.addMapping("*.html");
//    	
//    	//3.設定一般API處理
//    	ServletRegistration.Dynamic apiDispatcher = servletContext.addServlet("springDispatcherApi", new DispatcherServlet(rootContext));
//    	apiDispatcher.setLoadOnStartup(1);
//    	apiDispatcher.addMapping("/api/");
//    	
//    	servletContext.addListener(new ContextLoaderListener(rootContext));
//		
//    	
//		
//		
//		
//		
//		
//    	//log4j2 setup
////    	servletContext.setInitParameter("log4jConfigLocation", "classpath:config/log4j/log4j2.xml");
////    	servletContext.setInitParameter("log4jRefreshInterval", "10000");
////    	servletContext.setInitParameter("log4jExposeWebAppRoot", "false");
//    	
//    	
//    	
//////    	Log4jConfigListener log4jListener = new Log4jConfigListener();
////    			servletContext.addListener(log4jListener);
////
////    			
//
//    			
//    			
////    			// 註冊 servlet
////    			 AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
////    			// 自動掃描路徑
////    			 dispatcherContext.register(DispatcherConfig.class);
////    			// 註冊多檔上傳
////    			 dispatcherContext.register(MultipartConfig.class);
////
////    			// rest setup
////    			 ServletRegistration.Dynamic dispatcher =
////    			 servletContext.addServlet("pcbookapi", new DispatcherServlet(dispatcherContext));
////    			 dispatcher.setLoadOnStartup(1);
////    			 dispatcher.addMapping("/api/");
//    			 
//    			
////    			 AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
////    			 mvcContext.register(SpringMvcConfig.class);
////    			 mvcContext.setServletContext(servletContext);
////    		 
////    			 ServletRegistration.Dynamic mvcServlet = servletContext.addServlet("mvcdispatcher", new DispatcherServlet(mvcContext));
////    			 mvcServlet.setLoadOnStartup(1);
////    			 mvcServlet.addMapping("*.html");
////    			
////    			 
////    			//struts setup
////    			FilterRegistration.Dynamic strutsPrepareAndExecuteFilter = servletContext.addFilter("StrutsDispatcher",	new StrutsPrepareAndExecuteFilter());
////    			strutsPrepareAndExecuteFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "*.html");
//    			
//    			
//    			
//    			//strutsPrepareAndExecuteFilter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), true, "*.do");
//    			
//    			//freemarker setup
////    			ServletRegistration.Dynamic freemarker = servletContext.addServlet("freemarker", new MemberFreemarkerServlet());
////    			// freemarker.setInitParameter("TemplatePath", "/WEB-INF/");
////    			freemarker.setInitParameter("NoCache", "true");
////    			freemarker.setInitParameter("ContentType", "text/html; charset=UTF-8");
////    			freemarker.setInitParameter("template_update_delay", "0");
////    			freemarker.setInitParameter("default_encoding", "UTF-8");
////    			freemarker.setInitParameter("number_format", "true");
////    			freemarker.setInitParameter("NoCache", "0.##########");
////    			freemarker.setLoadOnStartup(1);
////    			freemarker.addMapping("*.ftl");
//    			
//
////    			FilterRegistration.Dynamic filter = servletContext.addFilter("openSessionInViewFilter",OpenSessionInViewFilter.class);
////    			filter.setInitParameter("singleSession", "true");
////    			filter.setInitParameter("flushMode", "AUTO");
////    			filter.addMappingForServletNames(null, true, "pcapirest");
////
////    			FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter",	CharacterEncodingFilter.class);
////    			characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
////    			characterEncodingFilter.setInitParameter("encoding", "UTF-8");
////    			characterEncodingFilter.setInitParameter("forceEncoding", "true");
////
////    			FilterRegistration.Dynamic disableUrlSessionFilter = servletContext.addFilter("disableUrlSessionFilter",DisableUrlSessionFilter.class);
////    			disableUrlSessionFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
////    	AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
////        context.register(ApplicationContextConfig.class);
////        context.register(FreemarkerContextConfig.class);
////        context.setServletContext(servletContext);
////        // Dispatcher Servlet
////        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher",new DispatcherServlet(context));
////        dispatcher.setLoadOnStartup(1);
////        dispatcher.addMapping("/");
////        dispatcher.setInitParameter("contextClass", context.getClass().getName());
////        servletContext.addListener(new ContextLoaderListener(context));
////        // UTF8 Charactor Filter.
////        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
////        fr.setInitParameter("encoding", "UTF-8");
////        fr.setInitParameter("forceEncoding", "true");
////        fr.addMappingForUrlPatterns(null, true, "/*");
//    }
// 
//}