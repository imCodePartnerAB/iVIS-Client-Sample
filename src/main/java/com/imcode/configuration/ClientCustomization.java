package com.imcode.configuration;

import imcode.services.exceptionhandling.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;

import java.nio.file.AccessDeniedException;


/**
 * Created by ruslan on 06.12.16.
 */
public class ClientCustomization extends ServerProperties {
    private static final Logger logger = LoggerFactory.getLogger(ClientCustomization.class);

    @Value("${spring.mvc.view.prefix}")
    private String viewPrefix;

    @Value("${spring.mvc.view.suffix}")
    private String viewSuffix;

    @Override
    /*
        Used for error handling
     */
    public void customize(ConfigurableEmbeddedServletContainer container) {
        super.customize(container);
        container.addErrorPages(new ErrorPage(GeneralException.class, viewWrap("api_error")));//For Api access exception
        container.addErrorPages(new ErrorPage(UnauthorizedUserException.class, viewWrap("401")));//For Api access exception
        container.addErrorPages(new ErrorPage(AccessDeniedException.class, viewWrap("403")));//For Api access exception
        container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, viewWrap("401")));//For IvisAuthorizedFilter
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, viewWrap("404")));//Not found
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, viewWrap("500")));//Not found
        container.addErrorPages(new ErrorPage(viewWrap("500")));//For Api access exception
    }

    private String viewWrap(String view) {
        String errorPrefix = "errors/";
        String uri = viewPrefix + errorPrefix + view + viewSuffix;
        logger.info("Error uri: " + uri);
        return uri;
    }
}
