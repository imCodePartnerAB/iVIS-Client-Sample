package com.imcode.controllers;

import com.imcode.services.PupilService;
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

    public static final String MAIN_PATH = "pupils";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(ModelAndView view, PupilService pupilService) {
        view.setViewName(MAIN_PATH + "/list");
        return view;
    }

}
