package com.imcode.controllers.error;

import com.imcode.configuration.ClientProperties;
import com.imcode.controllers.IvisAuthorizationController;
import imcode.services.filter.IvisAuthorizedFilter;
import imcode.services.utils.IvisOAuth2Utils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * Created by ruslan on 08.12.16.
 */
@Controller
public class UnauthorizedErrorController implements ErrorController {

    public static final String PATH = "/unauthorized";

    private static final Logger logger = LoggerFactory.getLogger(IvisAuthorizationController.class);

    private final AuthorizationCodeResourceDetails client;
    private final ClientProperties clientProperties;

    private final String ivisLogoutUrl;

    @Autowired
    public UnauthorizedErrorController(
            @Qualifier("clientInformation")
                    AuthorizationCodeResourceDetails client,
            ClientProperties clientProperties) {
        this.client = client;
        this.clientProperties = clientProperties;
        this.ivisLogoutUrl = clientProperties.getApiServerAddress() + clientProperties.getIvisLogoutRelativeUri();
    }

    @RequestMapping(value = PATH)
    public View unauthorizedUsers(ModelAndView view,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  @CookieValue(value = "refreshToken", required = false) String refreshTokenCookie) throws UnsupportedEncodingException, URISyntaxException {
        logger.info("User unauthorized!");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OAuth2AccessToken accessToken = IvisOAuth2Utils.getAccessToken(client, refreshTokenCookie);
        //logout client
        if (accessToken == null) {
            String loginUrl = clientProperties.getClientAddress() + IvisAuthorizationController.LOGIN_RELATIVE_URL;
            String redirectUrl = new URIBuilder(ivisLogoutUrl)
                    .addParameter("redirect_url", loginUrl)
                    .build()
                    .toString();
            logger.debug(redirectUrl);
            return new RedirectView(redirectUrl, false);
        }

        IvisOAuth2Utils.setAccessToken(request, accessToken);
        return new RedirectView("/", true);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
