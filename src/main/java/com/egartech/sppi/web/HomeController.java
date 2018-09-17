package com.egartech.sppi.web;

import com.egartech.sppi.configuration.StepUtils;
import com.egartech.sppi.model.Process;
import com.egartech.sppi.model.Step;
import com.egartech.sppi.repo.ProcessRepository;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    StepUtils stepUtils;

    @Autowired
    ProcessRepository processRepository;
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView getHomeView() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @PostMapping(value="/start_process", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Step> startProcess(@RequestBody Map<String, String> product) {
        Process process = new Process();
        process.setDateStart(new Date());
        process.setProductName(product.get("productName"));
        process.setProductCode(product.get("productCode"));
        processRepository.save(process);
        Step firstStep = stepUtils.getFirstStep(product.get("productCode"), process);
        return new ResponseEntity<>(firstStep, HttpStatus.OK);
    }

    @RequestMapping(value="/my_processes", method = RequestMethod.GET)
    public ModelAndView getMyTestsPage() {
        ModelAndView modelAndView = new ModelAndView("my_processes");
        List<Process> processList = processRepository.findAll();
        modelAndView.addObject("processList", processList);
        return modelAndView;
    }

   @RequestMapping(value = "/question_dictionary", method = RequestMethod.GET)
    public ModelAndView getQuestionDictionaryView() {
        ModelAndView modelAndView = new ModelAndView("questions_dictionary");
        return modelAndView;
   }
}
