package com.algaworks.brewer.config.init;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.algaworks.brewer.config.WebConfig;

/**
 * AbstractAnnotationConfigDispatcherServletInitializer - responsável por
 * inicializar a aplicação (Spring com TomCat vão utilizar um recurso do Servlet 3.0 ou 3.1)
 *
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return null;
	}

	/**
	 * Encontra os controllers pelo WebConfig
	 */
	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return new Class<?>[] { WebConfig.class };
	}

	/**
	 * Responsável por mapear o servlet da aplicação. Apartir do barra todas as
	 * requisições apartir do barra o servlet fica responsável
	 */
	@Override
	protected String[] getServletMappings()
	{
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters()
	{
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding( "UTF-8" );
		characterEncodingFilter.setForceEncoding( true );
		
		return new Filter[] { characterEncodingFilter };
	}
}