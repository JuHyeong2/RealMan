package com.example.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class TemplateResolverConfig {

    @Bean
    public ClassLoaderTemplateResolver prefsResolver() {
        ClassLoaderTemplateResolver pResolver = new ClassLoaderTemplateResolver();
        pResolver.setPrefix("templates/preferences/");
        pResolver.setSuffix(".html");
        pResolver.setTemplateMode(TemplateMode.HTML);
        pResolver.setCharacterEncoding("UTF-8");
        pResolver.setCacheable(false); // 자동 새로고침 기능
        pResolver.setCheckExistence(true);
        return pResolver;
    }

}
