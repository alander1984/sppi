package com.egartech.sppi.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StepController {
    
    @RequestMapping(value="process/{id}", method=RequestMethod.GET)
    public ModelAndView getStepPage(@PathVariable(value="id") Long id) {
        ModelAndView model = new ModelAndView("step");
        model.addObject("questionid",id);
        return model;
    }       

        
    @RequestMapping(value="showquestion/{id}", method=RequestMethod.GET)
    public ModelAndView showStep(@PathVariable(value="id") Long id) {
        ModelAndView modelAndView = new ModelAndView("step");
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.addObject("questionid", id);
        return modelAndView;
    }
    
    
    
}
