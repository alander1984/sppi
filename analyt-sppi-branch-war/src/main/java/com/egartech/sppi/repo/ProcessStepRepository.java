package com.egartech.sppi.repo;

import com.egartech.sppi.model.ProcessStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProcessStepRepository extends JpaRepository<ProcessStep, Long>, JpaSpecificationExecutor<ProcessStep> {
}
