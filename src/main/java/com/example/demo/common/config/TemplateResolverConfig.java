package com.example.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class TemplateResolverConfig {
	
	@Bean
	public ClassLoaderTemplateResolver memberResolver() {
		ClassLoaderTemplateResolver mResolver = new ClassLoaderTemplateResolver();
		mResolver.setPrefix("templates/member/");
		mResolver.setSuffix(".html");
		mResolver.setTemplateMode(TemplateMode.HTML);
		mResolver.setCharacterEncoding("UTF-8");
		mResolver.setCacheable(false);
		mResolver.setCheckExistence(true);
		return mResolver;
	}
	
	@Bean
	public ClassLoaderTemplateResolver chatResolver() {
		ClassLoaderTemplateResolver cResolver = new ClassLoaderTemplateResolver();
		cResolver.setPrefix("templates/chat/");
		cResolver.setSuffix(".html");
		cResolver.setTemplateMode(TemplateMode.HTML);
		cResolver.setCharacterEncoding("UTF-8");
		cResolver.setCacheable(false);
		cResolver.setCheckExistence(true);
		return cResolver;
	}

	@Bean
	public ClassLoaderTemplateResolver prefsResolver() {
		ClassLoaderTemplateResolver pResolver = new ClassLoaderTemplateResolver();
		pResolver.setPrefix("templates/preferences/");
		pResolver.setSuffix(".html");
		pResolver.setTemplateMode(TemplateMode.HTML);
		pResolver.setCharacterEncoding("UTF-8");
		pResolver.setCacheable(false);
		pResolver.setCheckExistence(true);
		return pResolver;
	}
}
