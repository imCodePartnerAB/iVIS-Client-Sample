package com.imcode.configuration;

import imcode.services.argumentresolver.IvisServiceArgumentResolver;
import imcode.services.converters.IvisIdToDomainClassConverter;
import imcode.services.filter.IvisAuthorizedFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.Filter;
import java.util.List;

/**
 * Created by ruslan on 07.12.16.
 */
@Configuration
public class ClientMvcConfiguration extends WebMvcConfigurerAdapter {

    @Value("${spring.mvc.view.prefix}")
    private String viewPrefix;

    @Value("${spring.mvc.view.suffix}")
    private String viewSuffix;

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(viewPrefix);
        resolver.setSuffix(viewSuffix);
        return resolver;
    }

    @Bean(name = "ivisAuthorizedFilter")
    public Filter ivisAuthorizedFilter() {
        IvisAuthorizedFilter ivisAuthorizedFilter = new IvisAuthorizedFilter();
        return ivisAuthorizedFilter;
    }

    @Bean
    public FilterRegistrationBean ivisAuthorizedFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(ivisAuthorizedFilter());
        registration.addUrlPatterns("/services/classes/*");
        registration.setName("ivisAuthorizedFilter");
        registration.setOrder(1);
        return registration;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(ivisServiceArgumentResolver());
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/WEB-INF/web-resources/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(ivisIdToDomainClassConverter());
    }


    @Bean
    public ServerProperties errorHandling() {
        return new ClientCustomization();
    }

    @Bean
    public HandlerMethodArgumentResolver ivisServiceArgumentResolver() {
        return new IvisServiceArgumentResolver();
    }

    @Bean
    public IvisIdToDomainClassConverter ivisIdToDomainClassConverter() {
        return new IvisIdToDomainClassConverter(conversionServiceFactoryBean().getObject());
    }

    @Bean
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        return new ConversionServiceFactoryBean();
    }

}
