package com.egartech.sppi.web;

import com.egartech.sppi.model.Process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import static com.egartech.sppi.specification.QuestionSpecification.byCode;
import java.util.Optional;
import com.egartech.sppi.model.*;
import com.egartech.sppi.repo.QuestionRepository;

@Controller
public class HomeController {
    
    @Autowired
    QuestionRepository questionRepository;
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView getHomeView() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @RequestMapping(value="/start_process", method = RequestMethod.GET)
    public ModelAndView /*String*/ startProcess() {
        Process process = new Process();
        Optional<Question> q = questionRepository.findOne(byCode("FIRST"));
        ModelAndView modelAndView =  new ModelAndView("redirect:/showquestion/"+q.get().getId());
        return modelAndView;
    }
}
