package com.algaworks.brewer.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.algaworks.brewer.controller.CervejasController;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * Classe de configuração do Spring deve ser anotada como @Configuration. Spring
 * MVC deve usar o @EnableWebMvc. WebMvcConfigurerAdapter - já tras algumas
 * configurações prontas. Precisamos definir onde o WebConfig irá encontrar as
 * classes Controllers para criar os Beans.
 */
@Configuration
//Procura todas as classes que estão no mesmo pacote CervejasController.class
@ComponentScan( basePackageClasses = { CervejasController.class } )
//Habilita esse projeto para um projeto web MVC
@EnableWebMvc 
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware
{
	// Objeto do Spring
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException
	{
		this.applicationContext = applicationContext;
	}

	/**
	 * Com base no templeteEngine consegue encontrar os templetes para processar
	 * os dados
	 */
	@Bean
	public ViewResolver viewResolver()
	{
		final ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine( templateEngine() );
		resolver.setCharacterEncoding( "UTF-8" );
		return resolver;
	}

	/**
	 * Processa os arquivos HTML
	 */
	@Bean //Com essa anotação fica disponível dentro do contexto Spring podendo acessar a qualquer momento
	public TemplateEngine templateEngine()
	{
		final SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler( true );
		engine.setTemplateResolver( templateResolver() );
		
		// Adicionamos o Dialet para trabalhar com Thymeleaf - Layout Dialect
		engine.addDialect( new LayoutDialect() );
		return engine;
	}

	/**
	 * 
	 * @return
	 */
	private ITemplateResolver templateResolver()
	{
		final SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext( applicationContext );
		resolver.setPrefix( "classpath:/templates/" );
		resolver.setSuffix( ".html" );
		resolver.setTemplateMode( TemplateMode.HTML );
		return resolver;
	}
	
	/**
	 * Mostramos onde os arquivos estáticos devem ser encontrados dentro do classpth
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
}