package com.egartech.sppi.web;

import com.egartech.sppi.model.Question;
import com.egartech.sppi.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class QuestionController {
    
    @Autowired
    QuestionRepository questionRepository;
    
    @RequestMapping(value="question/{id}", method=RequestMethod.GET)
    public ResponseEntity<Question> getQuestion(@PathVariable(value="id") Long id) {
        Optional<Question> q = questionRepository.findById(id);
        return q
                .map(question -> new ResponseEntity<>(question, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
