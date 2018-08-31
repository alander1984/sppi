package com.egartech.sppi.web;

import com.egartech.sppi.configuration.StepUtils;
import com.egartech.sppi.model.Process;
import com.egartech.sppi.model.Question;
import com.egartech.sppi.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class HomeController {
    
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    StepUtils stepUtils;
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView getHomeView() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @PostMapping(value="/start_process", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Question> startProcess(@RequestBody Map<String, String> product) {
        Process process = new Process();

        Question q1 = stepUtils.getFirstQuestion(product.get("productCode"));
        return new ResponseEntity<>(q1, HttpStatus.OK);
    }
}
