package com.egartech.sppi.configuration;

import com.egartech.sppi.model.Product;
import com.egartech.sppi.model.Question;
import com.egartech.sppi.model.Step;
import com.egartech.sppi.repo.QuestionRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.egartech.sppi.specification.QuestionSpecification.byCode;

@Service
public class StepUtils {

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    QuestionRepository questionRepository;

    public Question getNextQuestion(Question q, String answer) {
        Step step = new Step(q,answer);
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(step);
        kieSession.fireAllRules();
        return step.getNext();
    }

    public Question getFirstQuestion(String productCode) {
        Product product = new Product(productCode);
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(product);
        kieSession.fireAllRules();
        return questionRepository.findOne(byCode(product.getFirstQuestionCode())).get();
    }
}