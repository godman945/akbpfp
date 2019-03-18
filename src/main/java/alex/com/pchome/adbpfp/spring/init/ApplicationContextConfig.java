//package alex.com.pchome.adbpfp.spring.init;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.view.UrlBasedViewResolver;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
//import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
//import org.springframework.web.servlet.view.tiles3.TilesView;
//import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
//
//@EnableWebMvc
//@Configuration
//@ComponentScan(basePackages = "com.pchome.*")
//public class ApplicationContextConfig extends WebMvcConfigurerAdapter  {
//
//	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		  registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//		  super.addResourceHandlers(registry);
//	  }
//	
//	  @PropertySource({"classpath:config/prop/${spring.profiles.active}/jdbc.properties","classpath:config/prop/${spring.profiles.active}/mongo.properties" })
//	  public class SpringAllConfig {
//	  	   public SpringAllConfig(){
//	  		   
//	  	   }
//	  }
//	
//	
//	
//	
////	@Override
////	public void addResourceHandlers(ResourceHandlerRegistry registry) {
////		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
////	}
////
////	@Override
////	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
////		configurer.enable();
////	}
////
//////	@Bean
//////	public InternalResourceViewResolver jspViewResolver() {
//////		InternalResourceViewResolver bean = new InternalResourceViewResolver();
//////		bean.setPrefix("/WEB-INF/views/");
//////		bean.setSuffix(".jsp");
//////		return bean;
//////	}
////
////	@Bean
////	public UrlBasedViewResolver viewResolver() {
////		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
////		tilesViewResolver.setViewClass(TilesView.class);
////		return tilesViewResolver;
////	}
////	
////	
////	
////	@Override
////    public void configureViewResolvers(ViewResolverRegistry registry) {
////        TilesViewResolver viewResolver = new TilesViewResolver();
////        registry.viewResolver(viewResolver);
////    }
////	
////	
////	@Bean(name = "multipartResolver")
////	public CommonsMultipartResolver getMultipartResolver() {
////		return new CommonsMultipartResolver();
////	}
////
////    @Bean(name = "viewResolver")
////    public ViewResolver getViewResolver() {
////        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
////        viewResolver.setCache(true);
////        viewResolver.setPrefix("");
////        viewResolver.setSuffix(".ftl");
////        return viewResolver;
////    }
////
////	@Bean
////	public ViewResolver tilesViewResolver() {
////		TilesViewResolver viewResolver = new TilesViewResolver();
////		viewResolver.setViewClass(TilesView.class);
////		// viewResolver.setOrder(1);
////		return viewResolver;
////	}
////
////	@Bean(name = "tilesConfigurer")
////	public TilesConfigurer tilesConfigurer() {
////		TilesConfigurer tilesConfigurer = new TilesConfigurer();
////		tilesConfigurer.setDefinitions(new String[] { "classpath:config/tiles2/*.xml" });
////		tilesConfigurer.setCheckRefresh(true);
////		return tilesConfigurer;
////	}
////
////	@Bean(name = "freemarkerConfig")
////	public FreeMarkerConfigurer getFreemarkerConfig() {
////		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
////		config.setDefaultEncoding("utf-8");
////		config.setTemplateLoaderPath("/WEB-INF/ftl/");
////		return config;
////	}
//	
////	 @Override
////	  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
////	    configurer.enable();
////	  }
//
////	  @Override
////	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
////		  registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
////		  super.addResourceHandlers(registry);
////	  }
//
//
////	  // Tiles
////	  @Bean
////	  public TilesConfigurer tilesConfigurer() {
////	    TilesConfigurer tiles = new TilesConfigurer();
////	    tiles.setDefinitions(new String[] {
////	    		"classpath:config/tiles2/*.xml",
////	    });
////	    tiles.setCheckRefresh(true);
////	    return tiles;
////	  }
////
//////	  @Bean(name = "freemarkerConfig")
//////	  public FreeMarkerConfigurer getFreemarkerConfig() {
//////		  FreeMarkerConfigurer config = new FreeMarkerConfigurer();
//////		  config.setDefaultEncoding("utf-8");
//////		  config.setTemplateLoaderPath("/WEB-INF/");
//////		  return config;
//////	  }
////	  
////	  @Bean
////	  public ViewResolver viewResolver() {
////		  TilesViewResolver viewResolver = new TilesViewResolver();
//////		 FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
//////        viewResolver.setCache(true);
//////        viewResolver.setPrefix("");
//////        viewResolver.setSuffix(".ftl");
//////        return viewResolver;
////	    return new TilesViewResolver();
////	  }
//}