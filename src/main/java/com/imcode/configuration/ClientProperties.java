package com.imcode.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by ruslan on 05.12.16.
 */
@Component
@ConfigurationProperties(locations = "classpath:client.properties")
public class ClientProperties {

    private String apiServerAddress;
    private String clientAddress;
    private String clientId;
    private String clientSecret;
    private String redirectRelativeUri;
    private String userAuthorizationRelativeUri;
    private String accessTokenRelativeUri;
    private String ivisLogoutRelativeUri;
    private Integer refreshTokenValiditySeconds;

    public String getApiServerAddress() {
        return apiServerAddress;
    }

    public void setApiServerAddress(String apiServerAddress) {
        this.apiServerAddress = apiServerAddress;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectRelativeUri() {
        return redirectRelativeUri;
    }

    public void setRedirectRelativeUri(String redirectRelativeUri) {
        this.redirectRelativeUri = redirectRelativeUri;
    }

    public String getUserAuthorizationRelativeUri() {
        return userAuthorizationRelativeUri;
    }

    public void setUserAuthorizationRelativeUri(String userAuthorizationRelativeUri) {
        this.userAuthorizationRelativeUri = userAuthorizationRelativeUri;
    }

    public String getAccessTokenRelativeUri() {
        return accessTokenRelativeUri;
    }

    public void setAccessTokenRelativeUri(String accessTokenRelativeUri) {
        this.accessTokenRelativeUri = accessTokenRelativeUri;
    }

    public String getIvisLogoutRelativeUri() {
        return ivisLogoutRelativeUri;
    }

    public void setIvisLogoutRelativeUri(String ivisLogoutRelativeUri) {
        this.ivisLogoutRelativeUri = ivisLogoutRelativeUri;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }
}
