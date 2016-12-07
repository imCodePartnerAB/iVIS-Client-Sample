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
@RequestMapping("/" + PupilController.MAIN_PATH)
public class PupilController {

    private static final Logger logger = LoggerFactory.getLogger(PupilController.class);


    public static final String MAIN_PATH = "pupils";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(ModelAndView view, PupilService pupilService) {
        view.addObject("pupils", pupilService.findAll());
        view.setViewName(MAIN_PATH + "/list");
        return view;
    }

}
