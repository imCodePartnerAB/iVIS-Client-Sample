package com.imcode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ruslan on 02.12.16.
 */
@Controller
public class LoginController {

    @RequestMapping("/")
    public ModelAndView welcome(ModelAndView modelAndView, WebRequest webRequest) {
        modelAndView.setViewName("welcome");
        modelAndView.addObject("helloWorld", "HelloWorld!!!");
        return modelAndView;
    }

}
