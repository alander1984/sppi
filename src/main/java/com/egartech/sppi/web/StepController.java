package com.egartech.sppi.web;

import com.egartech.sppi.configuration.CommonUtils;
import com.egartech.sppi.configuration.StepUtils;
import com.egartech.sppi.model.Process;
import com.egartech.sppi.model.ProcessStep;
import com.egartech.sppi.model.Question;
import com.egartech.sppi.repo.ProcessRepository;
import com.egartech.sppi.repo.ProcessStepRepository;
import com.egartech.sppi.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static com.egartech.sppi.specification.QuestionSpecification.byCode;

@Controller
@RequestMapping("/process")
public class StepController {
    
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    StepUtils stepUtils;

    @Autowired
    ProcessRepository processRepository;

    @Autowired
    ProcessStepRepository processStepRepository;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ModelAndView getStepPage(@PathVariable(value="id") Long id) {
        ModelAndView model = new ModelAndView("step");
        model.addObject("questionid",id);
        return model;
    }       

    @RequestMapping(value="/{processId}/showquestion/{questionId}", method=RequestMethod.GET)
    public ModelAndView showStep(@PathVariable(value = "processId") Long processId,
                                 @PathVariable(value = "questionId") Long questionId) {
        Question question = questionRepository.findById(questionId).get();
        Process process = processRepository.findById(processId).get();
        boolean isFirstStep = false;
        if (process.getProcessSteps().isEmpty()) {
            isFirstStep = true;
        }
        Map<String, String> answers = CommonUtils.getAnswersMap(question.getAnswers());
        ModelAndView modelAndView = new ModelAndView("step");
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.addObject("questionid", questionId);
        modelAndView.addObject("answers", answers);
        modelAndView.addObject("answerBtnStyles", new String[] {"btn-success", "btn-warning", "btn-info", "btn-danger", "btn-dark"});
        modelAndView.addObject("processId", processId);
        modelAndView.addObject("isFirstStep", isFirstStep);
        return modelAndView;
    }
    
    @RequestMapping(value="/{processId}/getnext/{questionId}", method=RequestMethod.POST)
    public ResponseEntity<Question> getNextQuestion(@PathVariable(value = "processId") Long processId,
                                                    @PathVariable(value = "questionId") Long questionId,
                                                    @RequestBody String answer) {
        Process process = processRepository.findById(processId).get();
        Question currentQuestion = questionRepository.findById(questionId).get();
        Question nextQuestion = stepUtils.getNextQuestion(currentQuestion, answer);
        int stepNumber;
        if (process.getProcessSteps().isEmpty()) {
            stepNumber = 1;
        } else {
            stepNumber = process.getProcessSteps().size() + 1;
        }
        ProcessStep processStep = new ProcessStep(answer, stepNumber, currentQuestion, process);
        processStepRepository.save(processStep);
        String nextQuestionCode = nextQuestion.getCode();
        if (nextQuestionCode.equals("SUCCESS") || nextQuestionCode.equals("FAIL")) {
            process.setFinished(Boolean.TRUE);
            process.setPassed(nextQuestionCode.equals("SUCCESS") ? Boolean.TRUE : Boolean.FALSE);
            processRepository.save(process);
            return new ResponseEntity<>(nextQuestion, HttpStatus.OK);
        }
         return new ResponseEntity<>(questionRepository.findOne(byCode(nextQuestionCode)).get(), HttpStatus.OK);
    }

    @RequestMapping(value="/result/{result}", method=RequestMethod.GET)
    public ModelAndView showStep(@PathVariable(value="result") String result) {
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.addObject("result", result);
        return modelAndView;
    }

    @RequestMapping(value="/{processId}/delete_unused_process", method=RequestMethod.POST)
    public ResponseEntity<String> deleteUnusedProcess(@PathVariable(value="processId") Long processId) {
        Process process = processRepository.findById(processId).get();
        processRepository.delete(process);
        return new ResponseEntity<>("Deleted unused process during answer to the first question because of switching to another form", HttpStatus.OK);
    }
}
