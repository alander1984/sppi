package com.egartech.sppi.web;

import com.egartech.sppi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(Model model, String error, String logout){
        if(error != null){
            model.addAttribute("error", 1);
        }
        if(logout != null){
            model.addAttribute("message", "Logged out successfully");
        }
        return new ModelAndView("login");
    }
}
