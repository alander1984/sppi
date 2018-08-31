package com.egartech.sppi.configuration;

import com.egartech.sppi.model.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepUtils {

    @Autowired
    private KieContainer kieContainer;

    public Question getNextQuestion(Question q, String answer) {
        Step step = new Step(q,answer);
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(step);
        int i = kieSession.fireAllRules();
        System.out.println("^^^^^^^^^^^^"+i+step.getNext().getCode());
        return step.getNext();
    }
}