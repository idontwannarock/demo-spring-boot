package com.example.demospringboot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RequiredArgsConstructor
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    private final BuildProperties buildProperties;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demospringboot.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Demo Spring Boot project: " + buildProperties.getArtifact())
                .contact(new Contact("Howard", "https://github.com/idontwannarock/demo-spring-boot", "idontwannarock@gmail.com"))
                .version(buildProperties.getVersion())
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        return List.of(new SecurityReference(
                HttpHeaders.AUTHORIZATION,
                new AuthorizationScope[] { new AuthorizationScope("global", "accessEverything") }));
    }

    private ApiKey apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, "JWT", "header");
    }
}
