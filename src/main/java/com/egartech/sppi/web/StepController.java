package com.egartech.sppi.web;

import com.egartech.sppi.configuration.StepUtils;
import com.egartech.sppi.model.Question;
import com.egartech.sppi.repo.QuestionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.egartech.sppi.specification.QuestionSpecification.byCode;

@Controller
public class StepController {
    
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    StepUtils stepUtils;

    private static final String STANDARD_ANSWERS = "{\"yes\":\"Да\",\"no\":\"Нет\"}";
    
    @RequestMapping(value="process/{id}", method=RequestMethod.GET)
    public ModelAndView getStepPage(@PathVariable(value="id") Long id) {
        ModelAndView model = new ModelAndView("step");
        model.addObject("questionid",id);
        return model;
    }       

    @RequestMapping(value="showquestion/{id}", method=RequestMethod.GET)
    public ModelAndView showStep(@PathVariable(value="id") Long id) {
        Question question = questionRepository.findById(id).get();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> answers = new HashMap<>();
        try {
            answers = mapper.readValue(question.getAnswers() == null || question.getAnswers().isEmpty() ?
                    STANDARD_ANSWERS : question.getAnswers().replace("\'", "\""),
                    new TypeReference<Map<String, String>>() {});
        } catch (IOException ignored) {}
        ModelAndView modelAndView = new ModelAndView("step");
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.addObject("questionid", id);
        modelAndView.addObject("answers", answers);
        modelAndView.addObject("answerBtnStyles", new String[] {"btn-success", "btn-warning", "btn-info", "btn-danger", "btn-dark"});
        return modelAndView;
    }
    
    @RequestMapping(value="getnext/{id}", method=RequestMethod.POST)
    public ResponseEntity<Question> getNextQuestion(@PathVariable(value="id") Long id,@RequestBody String answer) {
         Question q1 = stepUtils.getNextQuestion(questionRepository.findById(id).get(),answer);
        if (q1.getCode().equals("SUCCESS") || q1.getCode().equals("FAIL")) {
            return new ResponseEntity<>(q1, HttpStatus.OK);
        }
         return new ResponseEntity<>(questionRepository.findOne(byCode(q1.getCode())).get(), HttpStatus.OK);
    }

    @RequestMapping(value="showresult/{result}", method=RequestMethod.GET)
    public ModelAndView showStep(@PathVariable(value="result") String result) {
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.addObject("result", result);
        return modelAndView;
    }
}
