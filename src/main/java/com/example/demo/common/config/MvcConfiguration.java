package com.example.demo.common.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.common.interceptor.CheckLoginInterceptor;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
			.addResourceLocations("classpath:/static/")
			.setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
		registry.addResourceHandler("/profile-images/**")
			.addResourceLocations("file:///c:/RealMan/profile-images/");
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CheckLoginInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/member/signin", "/member/signup",
					"/member/findMyId", "/member/findMyPwd","/",
					"/member/sendEmail", "/member/findId",
					"/css/**", "/js/**", "/image/**");
	}
}
