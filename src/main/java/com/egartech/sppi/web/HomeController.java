package com.egartech.sppi.web;

import com.egartech.sppi.model.Process;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView getHomeView() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @RequestMapping(value="/start_process", method = RequestMethod.GET)
    public ModelAndView /*String*/ startProcess() {
        Process process = new Process();

        ModelAndView modelAndView =  new ModelAndView("redirect:/step_page");
        return modelAndView;
    }
}
