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

@Controller
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @RequestMapping(value = "question/{id}", method = RequestMethod.GET)
    public ResponseEntity<Question> getQuestion(@PathVariable(value = "id") Long id) {
        Question question = questionRepository.findOne(id);

        return question == null ? new ResponseEntity<Question>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(question, HttpStatus.OK);
    }

    @RequestMapping(value = "showall", method = RequestMethod.GET)
    public ResponseEntity<List<Question>> getQuestions() {
        List<Question> questions = questionRepository.findAll();

        return questions.isEmpty() ?
                new ResponseEntity<List<Question>>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @RequestMapping(value = "updateQuestion", method = RequestMethod.PUT)
    public ResponseEntity<Question> updateQuestion(RequestEntity<Question> questionRequestEntity) {
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
        questionRepository.delete(questionRequestEntity.getBody().getId());
        return new ModelAndView("questions_dictionary");
    }
}
