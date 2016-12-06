package com.imcode.configuration;


import imcode.services.filter.IvisAuthorizedFilter;
import imcode.services.oauth2.IvisAuthorizationCodeResourceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import javax.servlet.Filter;

/**
 * Created by ruslan on 05.12.16.
 */
@Configuration
public class ClientConfiguration {

    private final ClientProperties clientProperties;

    @Autowired
    public ClientConfiguration(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(ivisAuthorizedFilter());
        registration.addUrlPatterns("/persons/*");
        registration.addUrlPatterns("/pupils/*");
        registration.addInitParameter("roles", "ROLE_ADMIN,ROLE_DEVELOPER");
        registration.setName("ivisAuthorizedFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean(name = "ivisAuthorizedFilter")
    public Filter ivisAuthorizedFilter() {
        return new IvisAuthorizedFilter();
    }

    @Bean
    public ServerProperties clientCustomization() {
        return new ClientCustomization();
    }

    @Bean(name = "clientInformation")
    public AuthorizationCodeResourceDetails clientBean() {
        IvisAuthorizationCodeResourceDetails client = new IvisAuthorizationCodeResourceDetails();
        String userAuthorizationUrl = clientProperties.getApiServerAddress() + clientProperties.getUserAuthorizationRelativeUri();
        String accessTokenUrl = clientProperties.getApiServerAddress() + clientProperties.getAccessTokenRelativeUri();

        client.setClientId(clientProperties.getClientId());
        client.setClientSecret(clientProperties.getClientSecret());
        client.setUserAuthorizationUri(userAuthorizationUrl);
        client.setAccessTokenUri(accessTokenUrl);

        return client;
    }

}
