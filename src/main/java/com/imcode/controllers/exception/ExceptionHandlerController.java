package com.imcode.controllers.exception;

import imcode.services.exceptionhandling.GeneralException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ruslan on 05.12.16.
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(GeneralException.class)
    public ModelAndView apiErrorProcess(GeneralException e) {
        ModelAndView view = new ModelAndView();
        view.addObject("apiError", e);
        view.setViewName("errors/api_error");
        return view;
    }


}
