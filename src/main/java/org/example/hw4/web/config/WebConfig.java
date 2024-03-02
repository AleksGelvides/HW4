package org.example.hw4.web.config;

import org.example.hw4.web.handlerInterceptor.ContollerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(contollerInterceptor());
    }

    @Bean
    public ContollerInterceptor contollerInterceptor() {
        return new ContollerInterceptor();
    }
}
