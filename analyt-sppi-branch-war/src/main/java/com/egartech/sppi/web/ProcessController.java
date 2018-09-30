package com.egartech.sppi.web;

import com.egartech.sppi.configuration.StepUtils;
import com.egartech.sppi.model.Process;
import com.egartech.sppi.model.ProductType;
import com.egartech.sppi.model.Step;
import com.egartech.sppi.repo.ProcessRepository;
import com.egartech.sppi.repo.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

/**
 * Created by alander on 30.09.18.
 */
@Controller
public class ProcessController {

    @Autowired
    ProductTypeRepository productTypeRepository;
    @Autowired
    ProcessRepository processRepository;

    @Autowired
    StepUtils stepUtils;

    @RequestMapping(value = "/process/{id}/editAttributes", method = RequestMethod.GET)
    public ModelAndView showEditAttributesForm() {
        ModelAndView editAttributes = new ModelAndView();
        return null;
    }

    @RequestMapping(value="/start_process/{productTypeId}", method = RequestMethod.POST)
    public ResponseEntity<Step> startProcess(@PathVariable("productTypeId") Long productTypeId, @RequestBody String attributesValues) {
        Process process = new Process();
        process.setDateStart(new Date());
        ProductType productType = productTypeRepository.findOne(productTypeId);
        process.setProductCode(productType.getQuizTreeCode());
        process.setAttributes(attributesValues);
        processRepository.save(process);
        Step firstStep = stepUtils.getFirstStep(productType.getQuizTreeCode(), process);
        return new ResponseEntity<>(firstStep, HttpStatus.OK);
    }

    @RequestMapping(value="/process/{id}/getpaused", method = RequestMethod.GET)
    public ResponseEntity<Long> getPausedQuestion(@PathVariable("id") Long id) {
        Process process = processRepository.findOne(id);
        return new ResponseEntity<Long>(process.getPausedQuestion().getId(),HttpStatus.OK);
    }
}
