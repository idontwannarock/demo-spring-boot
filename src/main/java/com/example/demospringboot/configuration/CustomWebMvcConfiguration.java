package com.example.demospringboot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Extends DelegatingWebMvcConfiguration instead of WebMvcConfigurationSupport in order to make use of Spring's auto configuration function.
 */
@Configuration
public class CustomWebMvcConfiguration extends DelegatingWebMvcConfiguration {

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        ApiVersionHandlerMapping handlerMapping = new ApiVersionHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        return handlerMapping;
    }
}
