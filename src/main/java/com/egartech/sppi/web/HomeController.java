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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import static com.egartech.sppi.specification.ProcessSpecification.paused;
import static com.egartech.sppi.specification.ProcessSpecification.completed;

@Controller
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = "transactionManager")
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
    public ModelAndView getMyTestsPage(@PathParam("status") String status) {
        ModelAndView modelAndView = new ModelAndView("my_processes");
        List<Process> processList = new ArrayList<>();
        if (status.equalsIgnoreCase("paused")) {
            processList = processRepository.findAll(paused());

        }
        else {
            if (status.equalsIgnoreCase("complete")) {
                {
                    processList = processRepository.findAll(completed());
                }
            }
        }
        modelAndView.addObject("processList", processList);
        modelAndView.addObject("status",status);
        return modelAndView;
    }

   @RequestMapping(value = "/question_dictionary", method = RequestMethod.GET)
    public ModelAndView getQuestionDictionaryView() {
        ModelAndView modelAndView = new ModelAndView("questions_dictionary");
        return modelAndView;
   }
}
