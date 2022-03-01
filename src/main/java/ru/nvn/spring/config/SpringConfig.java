package ru.nvn.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;

/**
 * @author NVN
 */
@Configuration
@ComponentScan("ru.nvn.spring")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer
{

		private final ApplicationContext applicationContext;
//
		@Autowired
		public SpringConfig(ApplicationContext applicationContext)
		{
				this.applicationContext = applicationContext;
		}

//		@Bean
//		public SpringResourceTemplateResolver templateResolver()
//		{
//				SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//				templateResolver.setApplicationContext(applicationContext);
//				templateResolver.setPrefix("/WEB-INF/views/");
//				templateResolver.setSuffix(".html");
//				return templateResolver;
//		}

//		@Bean
//		public SpringTemplateEngine templateEngine()
//		{
//				SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//				templateEngine.setTemplateResolver(templateResolver());
//				templateEngine.setEnableSpringELCompiler(true);
//				return templateEngine;
//		}

//		@Autowired
//		WebApplicationContext webApplicationContext;
//
		@Bean
		public SpringResourceTemplateResolver templateResolver(){
				SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
				templateResolver.setApplicationContext(applicationContext);
				templateResolver.setPrefix("/WEB-INF/views/");
				templateResolver.setSuffix("");
				return templateResolver;
		}
//
		@Bean
		public SpringTemplateEngine templateEngine() {
				SpringTemplateEngine springTemplateEngine= new SpringTemplateEngine();
				springTemplateEngine.setTemplateResolver(templateResolver());
				springTemplateEngine.setEnableSpringELCompiler(true);
				return springTemplateEngine;
		}
//
		@Bean
		public ThymeleafViewResolver thymeleafViewResolver(){
				final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
				viewResolver.setViewNames(new String[] {"*.html"});
				viewResolver.setExcludedViewNames(new String[] {"*.jsp"});
				viewResolver.setTemplateEngine(templateEngine());
				viewResolver.setCharacterEncoding("UTF-8");
				return viewResolver;
		}
//
		@Bean
		public InternalResourceViewResolver jspViewResolver(){
				final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
				viewResolver.setOrder(10);
				viewResolver.setViewClass(JstlView.class);
				viewResolver.setPrefix("/WEB-INF/views/");
				viewResolver.setSuffix("");
				viewResolver.setViewNames("*.jsp");
				return viewResolver;
		}


		@Override
		public void configureViewResolvers(ViewResolverRegistry registry)
		{
				ThymeleafViewResolver resolver = new ThymeleafViewResolver();
				resolver.setTemplateEngine(templateEngine());
				registry.viewResolver(resolver);
		}

		@Bean
		public DataSource dataSource()
		{
				DriverManagerDataSource dataSource = new DriverManagerDataSource();
				dataSource.setDriverClassName("org.postgresql.Driver");
				dataSource.setUrl("jdbc:postgresql://localhost:5432/firstDB");
				dataSource.setUsername("postgres");
				dataSource.setPassword("123321");
				return dataSource;
		}

		@Bean
		public JdbcTemplate jdbcTemplate()
		{
				return new JdbcTemplate(dataSource());
		}



		@Bean(name = "multipartResolver")
		public CommonsMultipartResolver multipartResolver()
		{
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
				multipartResolver.setMaxUploadSize(100000);
				return multipartResolver;
		}
}
