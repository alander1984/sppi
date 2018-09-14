package com.egartech.sppi.web;

import com.egartech.sppi.model.Question;
import com.egartech.sppi.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class QuestionController {
    
    @Autowired
    QuestionRepository questionRepository;
    
    @RequestMapping(value="question/{id}", method=RequestMethod.GET)
    public ResponseEntity<Question> getQuestion(@PathVariable(value="id") Long id) {
        Optional<Question> q = questionRepository.findById(id);
        if (q.isPresent()) {
            return new ResponseEntity<Question>(q.get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Question>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value="showall", method = RequestMethod.GET)
    public ResponseEntity<List<Question>> getQuestions() {
        Optional<List<Question>> q = Optional.of(questionRepository.findAll());
        if(q.isPresent()) {
            ResponseEntity<List<Question>> responseEntity = new ResponseEntity<List<Question>>(q.get(), HttpStatus.OK);
            return responseEntity;
        }
        else {
            return new ResponseEntity<List<Question>>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "updateQuestion", method = RequestMethod.PUT)
    public ResponseEntity<Question>updateQuestion(RequestEntity<Question> questionRequestEntity) {
        questionRepository.save(questionRequestEntity.getBody());
        return new ResponseEntity<Question>(questionRequestEntity.getBody(), HttpStatus.OK);
    }

    @RequestMapping(value = "addQuestion", method = RequestMethod.POST)
    public ModelAndView addQuestion(RequestEntity<Question> questionRequestEntity) {
        questionRequestEntity.getBody().setId(new Question().getId());
        questionRepository.save(questionRequestEntity.getBody());
        return new ModelAndView("questions_dictionary");
    }

    @RequestMapping(value = "deleteQuestion", method = RequestMethod.DELETE)
    public ModelAndView deleteQuestion(RequestEntity<Question> questionRequestEntity) {
        questionRepository.deleteById(questionRequestEntity.getBody().getId());
        return new ModelAndView("questions_dictionary");
    }






}
