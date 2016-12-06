package com.imcode.configuration;

import imcode.services.exceptionhandling.GeneralException;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.http.HttpStatus;

/**
 * Created by ruslan on 06.12.16.
 */
public class ClientCustomization extends ServerProperties {

    private final String prefix = "/WEB-INF/views/errors/";
    private final String suffix = ".jsp";

    @Override
    /*
        Used for error handling
     */
    public void customize(ConfigurableEmbeddedServletContainer container) {
        super.customize(container);
        container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, viewWrap("401")));//For IvisAuthorizedFilter
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, viewWrap("404")));//Not found
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, viewWrap("500")));//Not found
        container.addErrorPages(new ErrorPage(GeneralException.class, viewWrap("api_error")));//For Api access exception
        container.addErrorPages(new ErrorPage(viewWrap("error")));//For Api access exception

    }

    private String viewWrap(String view) {
        return prefix + view + suffix;
    }
}
