package com.example.demospringboot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class ApiVersionConfiguration extends WebMvcConfigurationSupport {

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        ApiVersionHandlerMapping handlerMapping = new ApiVersionHandlerMapping("v");
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        return handlerMapping;
    }

    /**
     * 发现如果继承了 WebMvcConfigurationSupport，则在 yml 中配置的相关内容会失效。
     * 需要重新指定静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
    /**
     * 配置 servlet 处理
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
