package com.imcode.configuration;


import imcode.services.filter.IvisAuthorizedFilter;
import imcode.services.oauth2.IvisAuthorizationCodeResourceDetails;
import imcode.services.restful.ProxyIvisServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
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
    public ServerProperties errorHandling() {
        return new ClientCustomization();
    }

    @Bean(name = "clientInformation")
    public AuthorizationCodeResourceDetails ivisClient() {
        IvisAuthorizationCodeResourceDetails client = new IvisAuthorizationCodeResourceDetails();
        String userAuthorizationUrl = clientProperties.getApiServerAddress() + clientProperties.getUserAuthorizationRelativeUri();
        String accessTokenUrl = clientProperties.getApiServerAddress() + clientProperties.getAccessTokenRelativeUri();

        client.setClientId(clientProperties.getClientId());
        client.setClientSecret(clientProperties.getClientSecret());
        client.setUserAuthorizationUri(userAuthorizationUrl);
        client.setAccessTokenUri(accessTokenUrl);

        return client;
    }

    @Bean
    public OAuth2ClientContext clientContext() {
        return new DefaultOAuth2ClientContext();
    }

    @Bean
    public ProxyIvisServiceFactory ivisServiceFactory() {
        String apiUrl = clientProperties.getApiServerAddress() + clientProperties.getApiRelativeUri();
        return new ProxyIvisServiceFactory(apiUrl, clientContext(), ivisClient());
    }

}
