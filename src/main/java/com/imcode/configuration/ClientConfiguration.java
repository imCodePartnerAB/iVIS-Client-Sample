package com.imcode.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

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

    private class IvisAuthorizationCodeResourceDetails extends AuthorizationCodeResourceDetails {
        private boolean clientOnly = true;

        @Override
        public boolean isClientOnly() {
            return clientOnly;
        }

        public void setClientOnly(boolean clientOnly) {
            this.clientOnly = clientOnly;
        }
    }


}
