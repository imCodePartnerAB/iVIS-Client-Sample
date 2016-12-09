package com.imcode.controllers;

import com.imcode.configuration.ClientProperties;
import imcode.services.filter.IvisAuthorizedFilter;
import imcode.services.utils.IvisOAuth2Utils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * Created by ruslan on 02.12.16.
 */
@Controller
public class IvisAuthorizationController {

    private static final Logger logger = LoggerFactory.getLogger(IvisAuthorizationController.class);

    private static final String REDIRECT_RELATIVE_URL = "/code";
    public static final String LOGIN_RELATIVE_URL = "/login";

    private final AuthorizationCodeResourceDetails client;
    private final ClientProperties clientProperties;

    private final String redirectUrl;
    private final String ivisLogoutUrl;

    public static final String START_VIEW_NAME = "welcome";

    @Autowired
    public IvisAuthorizationController(
            @Qualifier("clientInformation")
                    AuthorizationCodeResourceDetails client,
            ClientProperties clientProperties) {
        this.client = client;
        this.clientProperties = clientProperties;
        this.redirectUrl = clientProperties.getClientAddress() + REDIRECT_RELATIVE_URL;
        this.ivisLogoutUrl = clientProperties.getApiServerAddress() + clientProperties.getIvisLogoutRelativeUri();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView welcome(ModelAndView view, WebRequest webRequest, Model model) {
        view.setViewName(START_VIEW_NAME);
        return view;
    }

    @RequestMapping(value = LOGIN_RELATIVE_URL, method = RequestMethod.GET)
    public View login(ModelAndView modelAndView,  WebRequest webRequest) throws URISyntaxException, IOException {
        String oAuth2AuthirizationUrl = IvisOAuth2Utils.getOAuth2AuthirizationUrl(client, redirectUrl, false);
        return new RedirectView(oAuth2AuthirizationUrl, false);
    }

    @RequestMapping(value = REDIRECT_RELATIVE_URL, method = RequestMethod.GET)
    public View authorizationClientProcess(HttpServletRequest request,
                                           HttpServletResponse response,
                                           WebRequest webRequest,
                                           @SessionAttribute(value = IvisAuthorizedFilter.REQUEST_URI_ATTRIBUTE_NAME, required = false) String protectedResourcesUrl,
                                           @RequestParam("code") String code) throws IOException {
        OAuth2AccessToken accessToken = IvisOAuth2Utils.getAccessToken(client, code, redirectUrl);
        if (accessToken == null) {
            return new RedirectView(LOGIN_RELATIVE_URL, true);
        }
        IvisOAuth2Utils.setAccessToken(request, accessToken);
        IvisOAuth2Utils.setRefreshTokenAsCokie(response, accessToken.getRefreshToken(), clientProperties.getRefreshTokenValiditySeconds());
        String redirect = StringUtils.isEmpty(protectedResourcesUrl) ? "/" : protectedResourcesUrl;
        if (redirect.startsWith(request.getContextPath())) {
            redirect = redirect.replace(request.getContextPath(), "");
        }
        return new RedirectView(redirect, true);
    }

}
