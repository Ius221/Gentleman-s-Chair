package com.example.project.saloon.gentlemanChair.config;

import com.example.project.saloon.gentlemanChair.component.PasswordChangeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private PasswordChangeInterceptor passwordChangeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passwordChangeInterceptor)
                .addPathPatterns("/**") // trigger for All apis
                .excludePathPatterns("/auth/**");  // remove restriction from api started with /auth
    }
}
