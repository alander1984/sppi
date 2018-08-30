package com.egartech.sppi.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;
import com.egartech.sppi.model.*;
import com.egartech.sppi.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.springframework.http.HttpStatus;

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
}
