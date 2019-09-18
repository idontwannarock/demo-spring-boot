package com.example.demospringboot.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ObjectMapper mapper;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("v1/**/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "v1/attendance/**").hasAuthority("CREATE_ATTENDANCE")
                .antMatchers(HttpMethod.POST, "v1/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "v1/auth/register").permitAll()
                .antMatchers(HttpMethod.GET, "v1/home/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "v1/home/username").permitAll()
                .anyRequest().authenticated()
                .and()
                // Add a filter to validate jwt token for all requests
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), mapper));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                // Swagger
                .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**");
    }
}
