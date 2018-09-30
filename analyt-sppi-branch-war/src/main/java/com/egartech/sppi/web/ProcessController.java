package com.egartech.sppi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by alander on 30.09.18.
 */
@Controller
public class ProcessController {

    @RequestMapping(value = "/process/{id}/editAttributes", method = RequestMethod.GET)
    public ModelAndView showEditAttributesForm() {
        ModelAndView editAttributes = new ModelAndView();
        return null;
    }
}
