package com.imcode.controllers;

import com.imcode.entities.SchoolClass;
import com.imcode.services.PupilService;
import com.imcode.services.SchoolClassService;
import com.imcode.services.SchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ruslan on 06.12.16.
 */
@Controller
@RequestMapping("/services")
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @RequestMapping(value = "/classes", method = RequestMethod.GET)
    public ModelAndView createSchoolClass(ModelAndView view,
                                          PupilService pupilService,
                                          SchoolService schoolService) {
        view.setViewName("school_classes/edit");
        view.addObject("schoolClass", new SchoolClass());
        view.addObject(pupilService.findAll());
        view.addObject(schoolService.findAll());
        view.addObject("title", "School class");
        return view;
    }

    @RequestMapping(value = "/classes", method = RequestMethod.POST)
    public ModelAndView saveSchoolClass(ModelAndView view,
                                        WebRequest webRequest,
                                        @ModelAttribute("schoolClass") SchoolClass schoolClass,
                                        SchoolClassService classService) {
        classService.save(schoolClass);
        view.setViewName("school_classes/created");
        return view;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

}
