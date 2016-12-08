package com.imcode.controllers;

import com.imcode.services.PupilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ruslan on 06.12.16.
 */
@Controller
@RequestMapping("/services")
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @RequestMapping(value = "/pupils", method = RequestMethod.GET)
    public ModelAndView list(ModelAndView view, PupilService pupilService) {
        view.addObject("pupils", pupilService.findAll());
        view.setViewName("pupils/list");
        logger.debug("pupils findAll invoked");
        return view;
    }



}
