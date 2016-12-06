package com.imcode.controllers;

import com.imcode.configuration.ClientProperties;
import imcode.services.utils.IvisOAuth2Utils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * Created by ruslan on 02.12.16.
 */
@Controller
public class IvisAuthorizationController {

    private static final String REDIRECT_RELATIVE_URL = "/code";

    private final AuthorizationCodeResourceDetails client;
    private final ClientProperties clientProperties;

    private final String redirectUrl;
    private final String ivisLogoutUrl;

    private static final String START_VIEW_NAME = "welcome";

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
    public ModelAndView welcome(ModelAndView view, WebRequest webRequest) {
        view.setViewName(START_VIEW_NAME);
        return view;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView view, WebRequest webRequest) throws URISyntaxException {
        String oAuth2AuthirizationUrl = IvisOAuth2Utils.getOAuth2AuthirizationUrl(client, redirectUrl, false);
        view.setViewName("redirect:" + oAuth2AuthirizationUrl);
        return view;
    }

    @RequestMapping(value = REDIRECT_RELATIVE_URL, method = RequestMethod.GET)
    public ModelAndView authorizationClientProcess(ModelAndView view,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   @RequestParam("code") String code) throws UnsupportedEncodingException {
        OAuth2AccessToken accessToken = IvisOAuth2Utils.getAccessToken(client, code, redirectUrl);
        IvisOAuth2Utils.setAccessToken(request, accessToken);
        IvisOAuth2Utils.setRefreshTokenAsCokie(response, accessToken.getRefreshToken(), clientProperties.getRefreshTokenValiditySeconds());
        view.setViewName("redirect:/");
        return view;
    }


    @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
    public ModelAndView unauthorizedUsers(ModelAndView view,
                                          HttpServletRequest request,
                                          @CookieValue("refreshToken") String refreshTokenCookie) throws UnsupportedEncodingException, URISyntaxException {
        OAuth2AccessToken accessToken = IvisOAuth2Utils.getAccessToken(client, refreshTokenCookie);
        //logout client
        if (accessToken == null) {
            String redirectUrl = new URIBuilder(ivisLogoutUrl)
                    .addParameter("redirect_url", clientProperties.getClientAddress())
                    .build()
                    .toString();
            view.setViewName("redirect:" + redirectUrl);
            return view;
        }

        IvisOAuth2Utils.setAccessToken(request, accessToken);
        view.setViewName(START_VIEW_NAME);
        return view;
    }


}
