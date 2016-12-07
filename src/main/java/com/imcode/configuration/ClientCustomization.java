package com.imcode.configuration;

import imcode.services.exceptionhandling.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


/**
 * Created by ruslan on 06.12.16.
 */
@Component
public class ClientCustomization implements EmbeddedServletContainerCustomizer {
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
        container.addErrorPages(new ErrorPage(GeneralException.class, viewWrap("api_error")));//For Api access exception
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, viewWrap("404")));//Not found
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, viewWrap("500")));//Not found
        container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, viewWrap("401")));//For IvisAuthorizedFilter
        container.addErrorPages(new ErrorPage(viewWrap("500")));//For Api access exception
    }

    private String viewWrap(String view) {
        String errorPrefix = "error/";
        String uri = viewPrefix + errorPrefix + view + viewSuffix;
        logger.debug(uri);
        return uri;
    }
}
