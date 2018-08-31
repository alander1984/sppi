package com.egartech.sppi.utils;

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
        System.out.println("#########################");
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(step);
        kieSession.fireAllRules();
        return null;
    }
}