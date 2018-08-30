package com.egartech.sppi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StepController {
    
    @RequestMapping(value="step", method=RequestMethod.GET)
    public ModelAndView getStepPage() {
        return new ModelAndView("step");
    }
    
}
