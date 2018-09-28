package com.egartech.sppi.configuration;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by alander on 31.08.18.
 */
@Configuration
@ComponentScan("com.egartech.sppi.configuration")
public class StepRuleConfiguration {
    @Bean
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/Rules(credit).drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/Rules(securities).drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/Rules(update).drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/Rules(loans_individual).drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/Rules(gray_zone).drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/color.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        return kieServices.newKieContainer(kieModule.getReleaseId());
    }
}
