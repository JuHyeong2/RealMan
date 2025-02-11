package com.example.demo.common.config;

import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfiguration implements WebMvcConfigurer{
	
	// index페이지 templates하위로 잡도록 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**/")
		.addResourceLocations("classpath:/templates/")
		.setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
	}
}
