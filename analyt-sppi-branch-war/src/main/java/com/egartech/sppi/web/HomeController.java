package com.egartech.sppi.web;

import com.egartech.sppi.configuration.StepUtils;
import com.egartech.sppi.configuration.UserUtils;
import com.egartech.sppi.model.Process;
import com.egartech.sppi.model.ProcessAttributesDTO;
import com.egartech.sppi.model.ProductType;
import com.egartech.sppi.model.Step;
import com.egartech.sppi.repo.ProcessRepository;
import com.egartech.sppi.repo.ProductTypeRepository;
import com.egartech.sppi.repo.QuestionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;

import java.io.IOException;
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
    
    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    UserUtils userUtils;
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView getHomeView() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<ProductType> productTypes= productTypeRepository.findAll();
        modelAndView.addObject("product_types",productTypes);
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
        List<Process> cleanedProcessList = new ArrayList<>();
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
        for (Process process : processList) {
        	if (process.getLastProcessStep().getQuestion() != null) {
        		cleanedProcessList.add(process);
        	}
        }
        modelAndView.addObject("processList", cleanedProcessList);
        modelAndView.addObject("status",status);
        return modelAndView;
    }

   @RequestMapping(value = "/question_dictionary", method = RequestMethod.GET)
    public ModelAndView getQuestionDictionaryView() {
        ModelAndView modelAndView = new ModelAndView("questions_dictionary");
        return modelAndView;
   }
   
   @RequestMapping(value="/first-sign", method= RequestMethod.GET)
   public ModelAndView getFirstSignView() {
	   ModelAndView modelAndView = new ModelAndView("first_sign");
       return modelAndView;
   }

   @RequestMapping(value="/self-registration", method= RequestMethod.GET)
   public ModelAndView getSelfRegistrationView() {
       ModelAndView modelAndView = new ModelAndView("self_registration");
       return modelAndView;
   }

    @RequestMapping(value="/selfRegisteredUsers", method=RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getSelfRegisteredUsers() {
        try {
            byte[] testReportBytes = userUtils.getSelfRegisteredUsers();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=selfRegisteredUsers.xls")
                    .body(testReportBytes);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
   @RequestMapping(value = "/productAttributes/{quizTreeCode}", method = RequestMethod.GET)
   public ModelAndView getProductAttributes(@PathVariable(value = "quizTreeCode") String quizTreeCode)
   throws IOException {
       ModelAndView modelAndView = new ModelAndView("productAttributes");
       String attributesJson = productTypeRepository.findByQuizTreeCode(quizTreeCode).getAttributes();
       ObjectMapper mapper = new ObjectMapper();
       Map<String, String> attributes;
       attributes = mapper.readValue(attributesJson,
                   new TypeReference<Map<String, String>>() {});
       List<String> attributesList = new ArrayList<>(attributes.keySet());
       modelAndView.addObject("attributesList", attributesList);
       return modelAndView;
   }
    @RequestMapping(value = "/saveAttributes"/*/{quizTreeCode}*/, method = RequestMethod.POST)
    public ModelAndView saveAttributes(@ModelAttribute ProcessAttributesDTO processAttributesDTO)
            throws IOException {
        /*ModelAndView modelAndView = new ModelAndView("productAttributes");
        String attributesJson = productTypeRepository.findByQuizTreeCode(quizTreeCode).getAttributes();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> attributes;
        attributes = mapper.readValue(attributesJson,
                new TypeReference<Map<String, String>>() {});
        List<String> attributesList = new ArrayList<>(attributes.keySet());
        modelAndView.addObject("attributesList", attributesList)*/;
        return null;
    }

}
