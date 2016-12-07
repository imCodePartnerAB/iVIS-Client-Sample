package com.imcode.configuration;

import imcode.services.argumentresolver.IvisServiceArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by ruslan on 07.12.16.
 */
@Configuration
@EnableWebMvc
public class ClientMvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public HandlerMethodArgumentResolver ivisServiceArgumentResolver() {
        return new IvisServiceArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(ivisServiceArgumentResolver());
    }
}
