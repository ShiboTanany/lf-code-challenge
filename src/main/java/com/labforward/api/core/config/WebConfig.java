package com.labforward.api.core.config;

import com.labforward.api.core.interceptors.RequestAcceptContentTypeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Shaaban Ebrahim
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestAcceptContentTypeInterceptor()).addPathPatterns("/**");
    }
}
