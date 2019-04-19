//package alex.com.pchome.adbpfp.spring.init;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
//
//public class FreemarkerContextConfig extends WebMvcConfigurerAdapter  {
//
//	
//  @Bean(name = "viewResolver")
//  public ViewResolver getViewResolver() {
//      FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
//      viewResolver.setCache(true);
//      viewResolver.setPrefix("");
//      viewResolver.setSuffix(".ftl");
//      return viewResolver;
//  }
//  
//	@Bean(name = "freemarkerConfig")
//	public FreeMarkerConfigurer getFreemarkerConfig() {
//		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
//		config.setDefaultEncoding("utf-8");
//		config.setTemplateLoaderPath("/WEB-INF/");
//		return config;
//	}
//	
//  
//}
