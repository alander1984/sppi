package com.egartech.sppi.web;

import com.egartech.sppi.configuration.CommonUtils;
import com.egartech.sppi.configuration.ProcessUtils;
import com.egartech.sppi.configuration.StepUtils;
import com.egartech.sppi.model.*;
import com.egartech.sppi.model.Process;
import com.egartech.sppi.repo.ProcessRepository;
import com.egartech.sppi.repo.ProcessStepRepository;
import com.egartech.sppi.repo.QuestionRepository;
import com.egartech.sppi.repo.VerifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.egartech.sppi.specification.QuestionSpecification.byCode;

@Controller
@RequestMapping("/process")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = "transactionManager")
public class StepController {

    @Autowired
    QuestionRepository questionRepository;
    
    @Autowired
    VerifierRepository verifierRepository;

    @Autowired
    StepUtils stepUtils;

    @Autowired
    ProcessRepository processRepository;

    @Autowired
    ProcessStepRepository processStepRepository;
    
    
    @Autowired
    ProcessUtils processUtils;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ModelAndView getStepPage(@PathVariable(value="id") Long id) {
        ModelAndView model = new ModelAndView("step");
        model.addObject("questionid",id);
        return model;
    }

    @RequestMapping(value="/{processId}/showquestion/{questionId}", method=RequestMethod.GET)
    public ModelAndView showStep(@PathVariable(value = "processId") Long processId,
                                 @PathVariable(value = "questionId") Long questionId) {
        Question question = questionRepository.findOne(questionId);
        System.out.println("1");
        Process process = processRepository.findOne(processId);
        System.out.println("2");
        boolean isFirstStep = false;
        if (process.getProcessSteps().isEmpty()) {
            isFirstStep = true;
        }
        System.out.println("3");
        List<Verifier> verifiers = verifierRepository.findAll();
        Map<String, String> answers = CommonUtils.getAnswersMap(question.getAnswers());
        ModelAndView modelAndView = new ModelAndView("step");
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.addObject("questionid", questionId);
        modelAndView.addObject("answers", answers);
        modelAndView.addObject("answerBtnStyles", new String[] {"btn-success", "btn-warning", "btn-info", "btn-danger", "btn-dark"});
        modelAndView.addObject("processId", processId);
        modelAndView.addObject("isFirstStep", isFirstStep);
        modelAndView.addObject("verifiersList", verifiers);
        return modelAndView;
    }

    @RequestMapping(value="/{processId}/getnext/{questionId}", method=RequestMethod.POST)
    public ResponseEntity<Question> getNextQuestion(@PathVariable(value = "processId") Long processId,
                                                    @PathVariable(value = "questionId") Long questionId,
                                                    RequestEntity<GetNextQuestionDataDTO> getNextQuestionData) {
    	String answer = getNextQuestionData.getBody().getAnswer();
    	String fullNameVerifier = getNextQuestionData.getBody().getFullNameVerifier();
        Process process = processRepository.findOne(processId);
        Question currentQuestion = questionRepository.findOne(questionId);
        Question nextQuestion = stepUtils.getNextQuestion(currentQuestion, answer);
        int stepNumber;
        if (process.getProcessSteps().isEmpty()) {
            stepNumber = 1;
        } else {
            stepNumber = process.getProcessSteps().size() + 1;
        }
        ProcessStep processStep = new ProcessStep(answer, stepNumber, currentQuestion, process, CommonUtils.getAnswersMap(currentQuestion.getAnswers()).get(answer));
        if(fullNameVerifier != null) {
        	processStep.setFullNameVerifier(fullNameVerifier);
        	processStep.setDateOfVerification(new Date());
        }
        String nextQuestionCode = nextQuestion.getCode();
        if (nextQuestionCode.equals("SUCCESS") || nextQuestionCode.equals("FAIL")) {
            process.setPassed(nextQuestionCode.equals("SUCCESS") ? Boolean.TRUE : Boolean.FALSE);
            process.setDateEnd(new Date());
            process.setUti(processUtils.getUTI(process.getPassed(), process.getDateEnd()));
            processStepRepository.save(processStep);
            processRepository.save(process);
            return new ResponseEntity<>(nextQuestion, HttpStatus.OK);
        }
        Question question = questionRepository.findOne(byCode(nextQuestionCode));
        process.setPausedQuestion(question);
        processStepRepository.save(processStep);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @RequestMapping(value="/{processId}/result/{result}", method=RequestMethod.GET)
    public ModelAndView showStep(@PathVariable(value="result") String result,
    							 @PathVariable(value="processId") Long processId) {
        ModelAndView modelAndView = new ModelAndView("result");
        Process process = processRepository.findOne(processId);
        process.setColor(processUtils.getProcessColorRuleResult(process));
        if (process.getColor()!=null) {
        	modelAndView.addObject("color",process.getColor().getCode());
        }
        modelAndView.addObject("result", result);
        modelAndView.addObject("processId", processId);
        modelAndView.addObject("uti", process.getUti());
        //modelAndView.addObject("color", process.getColor().getCode());
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @RequestMapping(value="/{processId}/deleteUnusedProcess", method=RequestMethod.POST)
    public ResponseEntity<String> deleteUnusedProcess(@PathVariable(value="processId") Long processId) {
        Process process = processRepository.findOne(processId);
        if (!process.getProcessSteps().isEmpty()) {
            processStepRepository.delete(process.getProcessSteps());
        }
        processRepository.delete(process);
        return new ResponseEntity<>("Deleted unused process", HttpStatus.OK);
    }
    
    @RequestMapping(value="{processId}/testReport", method=RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getTestReport(@PathVariable(value="processId") Long processId) {
        try {
            byte[] testReportBytes = processUtils.getTestReport(processId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION)
                    .body(testReportBytes);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
